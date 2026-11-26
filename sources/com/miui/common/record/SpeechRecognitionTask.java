package com.miui.common.record;

import android.content.Context;
import com.miui.common.stat.EditStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import com.xiaomi.ai.AsrListener;
import com.xiaomi.ai.mibrain.MibrainException;
import com.xiaomi.ai.mibrain.MibrainVad;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes2.dex */
public abstract class SpeechRecognitionTask implements AsrListener {
    private static final byte CONSUMER_END_FLAG = 2;
    private static final int MAX_BLOCKING_QUEUE = 1024;
    private static final byte SPEECH_END_FLAG_INVALID = 1;
    private static final byte SPEECH_END_FLAG_SPEAKING = 0;
    protected static final String TAG = "SpeechRecognitionTask";
    protected static final AtomicBoolean isNotify = new AtomicBoolean();
    private LocalThread audioConsumerThread;
    private LocalThread audioProducerThread;
    protected Context mContext;
    protected String mMp3DirName;
    protected volatile boolean mNetWorkValid;
    private volatile Recorder mRecorder;
    protected WeakReference<SpeechRecognitionListener> mSpeechRecognitionListener;
    protected final Object mConsumerLock = new Object();
    private final Object mRecorderSync = new Object();
    private final BlockingQueue<byte[]> mBlockingQueue = new LinkedBlockingDeque();

    protected abstract void onBufferSend(byte[] bArr);

    protected abstract void onRenameMp3File(String str);

    protected abstract void onRequestStart();

    protected abstract void onSpeechEnd(boolean z);

    public abstract void startEngine();

    public abstract void stopEngine();

    protected abstract void updateMaxAmplitude(short[] sArr, int i);

    public SpeechRecognitionTask(Context context, String str, SpeechRecognitionListener speechRecognitionListener) {
        this.mContext = context;
        this.mSpeechRecognitionListener = new WeakReference<>(speechRecognitionListener);
        this.mMp3DirName = str;
    }

    public void setNetWorkValid(boolean z) {
        this.mNetWorkValid = z;
    }

    public void start() throws MibrainException {
        LocalThread localThread;
        if (!isSkipSpeechRecognition() && ((localThread = this.audioConsumerThread) == null || !localThread.isStart)) {
            AudioConsumerThread audioConsumerThread = new AudioConsumerThread();
            this.audioConsumerThread = audioConsumerThread;
            audioConsumerThread.setIsStart(true);
            this.audioConsumerThread.start();
        }
        LocalThread localThread2 = this.audioProducerThread;
        if (localThread2 == null || !localThread2.isStart) {
            AudioProducerThread audioProducerThread = new AudioProducerThread();
            this.audioProducerThread = audioProducerThread;
            audioProducerThread.start();
        }
    }

    public void shutdownNow() {
        LocalThread localThread = this.audioProducerThread;
        if (localThread != null) {
            localThread.setIsStart(false);
            this.audioProducerThread.interrupt();
            this.audioProducerThread = null;
        }
        releaseRecorderIfNeeded();
        if (this.audioConsumerThread != null) {
            this.mBlockingQueue.clear();
            if (!isSkipSpeechRecognition()) {
                appendFlag((byte) 2);
            }
            this.audioConsumerThread.setIsStart(false);
            this.audioConsumerThread.interrupt();
            this.audioConsumerThread = null;
        }
    }

    public void destroy() {
        shutdownNow();
    }

    private abstract class LocalThread extends Thread {
        private volatile boolean isStart;

        protected abstract void runInternal();

        private LocalThread() {
        }

        public void setIsStart(boolean z) {
            this.isStart = z;
        }

        public boolean getIsStart() {
            return this.isStart;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            if (isInterrupted()) {
                return;
            }
            this.isStart = true;
            try {
                runInternal();
            } finally {
                this.isStart = false;
            }
        }

        protected boolean isLoop() {
            return this.isStart && !isInterrupted();
        }
    }

    private void appendFlag(byte b) {
        this.mBlockingQueue.add(new byte[]{b});
    }

    private class AudioProducerThread extends LocalThread {
        private static final int BORDER_PCM_SHORT_BUFFER = 2000;
        private static final int CHANNEL = 1;
        private static final int DEFAULT_COMPENSATE_NUM = 3;
        private static final int FEEDDATA1S = 32000;
        private static final int MAXPACSIZE = 16000;
        private static final int NUMBER_OF_BITS = 16;
        private static final int PACSIZE = 8000;
        private static final float SHORTEST_VOCAL_LIMITATION = 0.2f;
        private static final float SOUND_RELATIVE_ENERGY = 4.0f;
        private static final float TIME_TO_STOP = 0.7f;
        private static final int VADTIME = 3;
        private static final int VAD_COUNT_MULTIPLE = 6;
        private int feedSize;
        private int maxFeedSize;
        private MibrainVad miBrainVad;
        private int vadCount;

        private AudioProducerThread() {
            super();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r6v5, types: [com.miui.common.record.SpeechRecognitionTask] */
        @Override // com.miui.common.record.SpeechRecognitionTask.LocalThread
        protected void runInternal() {
            synchronized (SpeechRecognitionTask.this.mRecorderSync) {
                if (SpeechRecognitionTask.this.mRecorder == null) {
                    SpeechRecognitionTask.this.mRecorder = Recorder.getRecorder(1);
                }
                SpeechRecognitionTask.this.mRecorder.init();
                if (SpeechRecognitionTask.this.mRecorder.getErrorCode() != 0) {
                    try {
                        SpeechRecognitionTask.this.mRecorder.release();
                    } catch (Exception e) {
                        Logger.INSTANCE.e(SpeechRecognitionTask.TAG, "runInternal release on init error: " + e);
                    }
                    SpeechRecognitionTask.this.mRecorder = null;
                    return;
                }
                SpeechRecognitionTask.this.mRecorder.startRecording();
                try {
                    try {
                        recordingToBuffer();
                    } catch (MibrainException e2) {
                        e2.printStackTrace();
                    }
                } finally {
                    SpeechRecognitionTask.this.releaseRecorderIfNeeded();
                }
            }
        }

        @Override // com.miui.common.record.SpeechRecognitionTask.LocalThread
        protected boolean isLoop() {
            if (SpeechRecognitionTask.this.isSkipSpeechRecognition()) {
                return super.isLoop();
            }
            return super.isLoop() && SpeechRecognitionTask.this.isConsumerAvailable();
        }

        private void recordingToBuffer() throws MibrainException {
            Logger.INSTANCE.i(SpeechRecognitionTask.TAG, "recordingToBuffer");
            Recorder recorder = SpeechRecognitionTask.this.mRecorder;
            if (recorder == null) {
                return;
            }
            int minPCMBufferSize = recorder.getMinPCMBufferSize();
            if (minPCMBufferSize < 2000) {
                minPCMBufferSize *= 2;
            }
            short[] sArr = new short[minPCMBufferSize];
            int mP3BufferSize = recorder.getMP3BufferSize(minPCMBufferSize);
            byte[] bArr = new byte[mP3BufferSize];
            this.miBrainVad = new MibrainVad();
            if (initBrain()) {
                ArrayList arrayList = new ArrayList();
                recorder.setOutputFile(AudioUtils.obtainMp3FilePath(SpeechRecognitionTask.this.mMp3DirName));
                long jCurrentTimeMillis = System.currentTimeMillis();
                int i = 0;
                int i2 = 0;
                boolean z = false;
                int i3 = 0;
                byte[] bArr2 = null;
                while (isLoop()) {
                    Recorder recorder2 = SpeechRecognitionTask.this.mRecorder;
                    if (recorder2 == null) {
                        Logger.INSTANCE.d(SpeechRecognitionTask.TAG, "recordingToBuffer: mRecorder is null, exit loop");
                        break;
                    }
                    int i4 = recorder2.read(sArr, i, minPCMBufferSize);
                    SpeechRecognitionTask.this.updateMaxAmplitude(sArr, i4);
                    if (i4 <= 0) {
                        Logger.INSTANCE.e(SpeechRecognitionTask.TAG, "Audiorecord read error: " + i4);
                        break;
                    }
                    byte[] bArrShortToByte = AudioUtils.shortToByte(sArr, i4);
                    int length = bArrShortToByte.length;
                    minPCMBufferSize = minPCMBufferSize;
                    int iStartMP3Encode = recorder2.startMP3Encode(sArr, sArr, i4, bArr, mP3BufferSize);
                    if (iStartMP3Encode < 0) {
                        Logger.INSTANCE.d(SpeechRecognitionTask.TAG, "MP3 encode error: " + iStartMP3Encode);
                        break;
                    }
                    if (SpeechRecognitionTask.this.isSkipSpeechRecognition()) {
                        i = 0;
                    } else {
                        if (bArr2 == null) {
                            int i5 = (16000 / length) * length;
                            this.maxFeedSize = i5;
                            this.vadCount = 96000 / i5;
                            bArr2 = new byte[i5];
                        }
                        byte[] bArr3 = new byte[length];
                        System.arraycopy(bArrShortToByte, 0, bArr3, 0, length);
                        if (length < 16000) {
                            System.arraycopy(bArrShortToByte, 0, bArr2, i2, length);
                            i2 += length;
                            if (i2 >= 8000) {
                                boolean zIsSpeak = this.miBrainVad.isSpeak(bArr2, i2);
                                if (z) {
                                    if (!zIsSpeak) {
                                        compensate(arrayList);
                                        appendFlag((byte) 0);
                                        if (!initBrain()) {
                                            break;
                                        }
                                    }
                                    SpeechRecognitionTask.this.mBlockingQueue.addAll(arrayList);
                                    SpeechRecognitionTask.this.mBlockingQueue.add(bArr3);
                                    arrayList.clear();
                                    z = zIsSpeak;
                                    i2 = 0;
                                } else {
                                    if (zIsSpeak) {
                                        SpeechRecognitionTask.this.mBlockingQueue.addAll(arrayList);
                                        SpeechRecognitionTask.this.mBlockingQueue.add(bArr3);
                                        arrayList.clear();
                                    } else {
                                        SpeechRecognitionTask.this.mBlockingQueue.addAll(arrayList);
                                        SpeechRecognitionTask.this.mBlockingQueue.add(bArr3);
                                        arrayList.clear();
                                        int i6 = i3 + 1;
                                        int i7 = this.vadCount;
                                        if (i6 < i7 * 6) {
                                            if (i6 % i7 == 0) {
                                                appendFlag((byte) 1);
                                                if (!initBrain()) {
                                                    break;
                                                }
                                            }
                                            i3 = i6;
                                        }
                                        z = zIsSpeak;
                                        i2 = 0;
                                    }
                                    i3 = 0;
                                    z = zIsSpeak;
                                    i2 = 0;
                                }
                            } else {
                                arrayList.add(bArr3);
                            }
                        }
                        i = 0;
                    }
                }
                Recorder recorder3 = SpeechRecognitionTask.this.mRecorder;
                if (recorder3 != null) {
                    recorder3.stopMP3Encode();
                    SpeechRecognitionTask.this.onRenameMp3File(AudioUtils.renameMp3FileToSha1(SpeechRecognitionTask.this.mMp3DirName, recorder3.getOutputFilePath()));
                }
                EditStat.trackVoiceCreate((int) (((System.currentTimeMillis() - jCurrentTimeMillis) / 1000) + 1));
            }
        }

        private void compensate(List<byte[]> list, int i) {
            while (i > 0) {
                if (list.size() > 0) {
                    SpeechRecognitionTask.this.mBlockingQueue.add(list.get(0));
                    list.remove(0);
                }
                i--;
            }
        }

        private void compensate(List<byte[]> list) {
            compensate(list, 3);
        }

        private void appendFlag(byte b) {
            SpeechRecognitionTask.this.mBlockingQueue.add(new byte[]{b});
        }

        private boolean initBrain() {
            this.miBrainVad.release();
            MibrainVad mibrainVad = new MibrainVad();
            this.miBrainVad = mibrainVad;
            return mibrainVad.init(16000, 1, 16, 0.7f, 0.2f, SOUND_RELATIVE_ENERGY) == 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseRecorderIfNeeded() {
        synchronized (this.mRecorderSync) {
            if (this.mRecorder != null) {
                try {
                    this.mRecorder.stopMP3Encode();
                } catch (Exception e) {
                    Logger.INSTANCE.e(TAG, "releaseRecorderIfNeeded stopMP3Encode error: " + e);
                }
                try {
                    this.mRecorder.stopRecord();
                } catch (Exception e2) {
                    Logger.INSTANCE.e(TAG, "releaseRecorderIfNeeded stopRecord error: " + e2);
                }
                try {
                    this.mRecorder.release();
                } catch (Exception e3) {
                    Logger.INSTANCE.e(TAG, "releaseRecorderIfNeeded release error: " + e3);
                }
                this.mRecorder = null;
                Logger.INSTANCE.d(TAG, "releaseRecorderIfNeeded: Recorder released to prevent AudioIn WakeLock leak");
            }
        }
    }

    private boolean isBlockingQueueAvailable() {
        return this.mBlockingQueue.size() < 1024;
    }

    public boolean isConsumerAvailable() {
        LocalThread localThread;
        return isBlockingQueueAvailable() && (localThread = this.audioConsumerThread) != null && localThread.getIsStart();
    }

    private class AudioConsumerThread extends LocalThread {
        private AudioConsumerThread() {
            super();
        }

        @Override // com.miui.common.record.SpeechRecognitionTask.LocalThread
        public void runInternal() {
            SpeechRecognitionTask.this.mBlockingQueue.clear();
            boolean z = true;
            while (isLoop()) {
                try {
                    byte[] bArr = (byte[]) SpeechRecognitionTask.this.mBlockingQueue.take();
                    if (bArr.length == 1) {
                        byte b = bArr[0];
                        if (b == 0) {
                            SpeechRecognitionTask.this.onSpeechEnd(true);
                        } else if (b == 1) {
                            SpeechRecognitionTask.this.onSpeechEnd(false);
                        } else if (b == 2) {
                            SpeechRecognitionTask.this.onSpeechEnd(false);
                            return;
                        }
                        synchronized (SpeechRecognitionTask.this.mConsumerLock) {
                            while (!SpeechRecognitionTask.isNotify.get()) {
                                try {
                                    SpeechRecognitionTask.this.mConsumerLock.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            SpeechRecognitionTask.isNotify.set(false);
                            z = true;
                        }
                    } else {
                        if (z) {
                            SpeechRecognitionTask.this.onRequestStart();
                            z = false;
                        }
                        SpeechRecognitionTask.this.onBufferSend(bArr);
                    }
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public boolean isSkipSpeechRecognition() {
        return RomUtils.isInternationalBuild() || !PreferenceUtils.getAllowXiaoai(NoteApp.getInstance());
    }
}
