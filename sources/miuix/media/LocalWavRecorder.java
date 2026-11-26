package miuix.media;

import android.media.AudioRecord;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* JADX INFO: loaded from: classes5.dex */
class LocalWavRecorder implements Recorder {
    private static final int EVENT_ERROR = 1;
    private static final String LOG_TAG = "WavRecorder";
    private static final int STATE_IDEL = 0;
    private static final int STATE_PAUSED = 3;
    private static final int STATE_PREPARED = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_STOPED = 4;
    private int mAudioChannel;
    private int mAudioSource;
    private short[] mBufferPCM;
    private int mChannelCount;
    private long mCountRecordingSamples;
    private long mCurrentRecordingSize;
    private Handler mEventHandler;
    private int mMaxAmplitude;
    private long mMaxFileSize;
    private int mMinBufferSize;
    private Recorder.OnErrorListener mOnErrorListener;
    private File mOutputFile;
    private String mOutputFilePath;
    private DataOutputStream mOutputStream;
    private String mParams;
    private AudioRecord mRecorder;
    private int mRecordingState;
    private RecordingThread mRecordingThread;
    private int mSampleRate;
    private final int WAVE_HEADER_LENGTH = 44;
    private final int OFFSET_WAVE_HEADER_DATA_LENGTH = 4;
    private final int OFFSET_WAVE_HEADER_AUDIO_DATA_LENGTH = 40;
    private final long WAV_MAX_FILE_SIZE = 4273492459L;

    @Override // miuix.media.Recorder
    public boolean canPause() {
        return true;
    }

    @Override // miuix.media.Recorder
    public void setAudioEncoder(int i) {
    }

    @Override // miuix.media.Recorder
    public void setAudioEncodingBitRate(int i) {
    }

    @Override // miuix.media.Recorder
    public void setOutputFormat(int i) {
    }

    @Override // miuix.media.Recorder
    public void setQuality(int i) {
    }

    public LocalWavRecorder() {
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            this.mEventHandler = new EventHandler(looperMyLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new EventHandler(mainLooper);
            } else {
                Log.e(LOG_TAG, "Could not create event handler");
                this.mEventHandler = null;
            }
        }
        reset();
    }

    @Override // miuix.media.Recorder
    public void setOutputFile(String str) {
        this.mOutputFilePath = str;
    }

    @Override // miuix.media.Recorder
    public void setAudioSource(int i) {
        this.mAudioSource = i;
    }

    @Override // miuix.media.Recorder
    public void setAudioSamplingRate(int i) {
        this.mSampleRate = i;
    }

    @Override // miuix.media.Recorder
    public void setOnErrorListener(Recorder.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    @Override // miuix.media.Recorder
    public void setExtraParameters(String str) {
        if (!AudioRecordHelper.isExtraParamSupported()) {
            throw new IllegalArgumentException("Do not support extra parameter");
        }
        this.mParams = str;
    }

    public boolean isExtraParamSupported() {
        return AudioRecordHelper.isExtraParamSupported();
    }

    @Override // miuix.media.Recorder
    public void setAudioChannel(int i) {
        this.mAudioChannel = i;
    }

    @Override // miuix.media.Recorder
    public void setMaxFileSize(long j) {
        if (j > 0) {
            this.mMaxFileSize = Math.min(j, 4273492459L);
        } else {
            this.mMaxFileSize = 4273492459L;
        }
    }

    @Override // miuix.media.Recorder
    public void setMaxDuration(int i) {
        if (i > 0) {
            this.mMaxFileSize = Math.min(((((i / 1000) * this.mSampleRate) * 16) * this.mChannelCount) / 8, 4273492459L);
        } else {
            this.mMaxFileSize = 4273492459L;
        }
    }

    @Override // miuix.media.Recorder
    public void reset() {
        this.mRecorder = null;
        this.mAudioSource = 1;
        this.mSampleRate = Mp3Encoder.DEFAULT_SAMPLE_RATE;
        this.mAudioChannel = 16;
        this.mRecordingState = 0;
        this.mCountRecordingSamples = 0L;
        this.mCurrentRecordingSize = 0L;
        this.mMaxFileSize = 4273492459L;
    }

    @Override // miuix.media.Recorder
    public void prepare() throws IllegalStateException, IOException {
        int minBufferSize = AudioRecord.getMinBufferSize(this.mSampleRate, this.mAudioChannel, 2);
        this.mMinBufferSize = minBufferSize;
        if (minBufferSize < 0) {
            Log.e(LOG_TAG, "Error when getting min buffer size");
            throw new IllegalStateException("Could not calculate the min buffer size");
        }
        this.mBufferPCM = new short[minBufferSize * 2];
        AudioRecord audioRecord = new AudioRecord(this.mAudioSource, this.mSampleRate, this.mAudioChannel, 2, this.mBufferPCM.length);
        this.mRecorder = audioRecord;
        prepareExtraParameters(audioRecord, this.mParams);
        Log.v(LOG_TAG, "Apply new AudioRecord");
        this.mChannelCount = this.mAudioChannel != 12 ? 1 : 2;
        this.mOutputFile = new File(this.mOutputFilePath);
        this.mRecordingState = 1;
        Log.v(LOG_TAG, "WAVRecorder prepared");
    }

    @Override // miuix.media.Recorder
    public synchronized void start() throws IllegalStateException, IOException {
        int i;
        if (this.mRecordingThread != null || (i = this.mRecordingState) == 2) {
            Log.e(LOG_TAG, "Recording has started");
            throw new IllegalStateException("Recording has already started");
        }
        if (i != 1) {
            Log.e(LOG_TAG, "Recorder not prepared");
            throw new IllegalStateException("Recorder not prepared");
        }
        this.mCountRecordingSamples = 0L;
        this.mCurrentRecordingSize = 0L;
        this.mRecordingState = 2;
        this.mRecorder.startRecording();
        if (this.mRecorder.getRecordingState() != 3) {
            throw new IllegalStateException("Wav record could not start: other input already started");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(this.mOutputFile);
        this.mOutputStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream));
        Log.v(LOG_TAG, "AudioRecord started");
        fileOutputStream.write(RecorderUtils.getWaveHeader(this.mAudioChannel, this.mSampleRate));
        RecordingThread recordingThread = new RecordingThread();
        this.mRecordingThread = recordingThread;
        recordingThread.start();
        Log.v(LOG_TAG, "WAVRecorder started");
    }

    @Override // miuix.media.Recorder
    public long getRecordingTimeInMillis() {
        return (long) (((this.mCountRecordingSamples * 2.0d) / ((double) ((this.mSampleRate * 2) * this.mChannelCount))) * 1000.0d);
    }

    public long getRecordingSizeInByte() {
        return this.mCurrentRecordingSize;
    }

    @Override // miuix.media.Recorder
    public synchronized void pause() throws IllegalStateException {
        RecordingThread recordingThread;
        if (this.mRecordingState != 2 || (recordingThread = this.mRecordingThread) == null) {
            throw new IllegalStateException("Recording not started");
        }
        this.mRecordingState = 3;
        try {
            recordingThread.join();
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "InterruptedException when pause", e);
        }
        this.mRecorder.stop();
        Log.v(LOG_TAG, "AudioRecord stoped");
        this.mRecorder.release();
        Log.v(LOG_TAG, "AudioRecord released");
        this.mRecorder = null;
        Log.v(LOG_TAG, "WAVRecorder paused");
    }

    @Override // miuix.media.Recorder
    public synchronized void resume() throws IllegalStateException {
        if (this.mRecordingState == 3) {
            this.mRecorder = new AudioRecord(this.mAudioSource, this.mSampleRate, this.mAudioChannel, 2, this.mBufferPCM.length);
            Log.v(LOG_TAG, "Apply new AudioRecord");
            prepareExtraParameters(this.mRecorder, this.mParams);
            this.mRecorder.startRecording();
            if (this.mRecorder.getRecordingState() != 3) {
                throw new IllegalStateException("Mp3 record could not start: other input already started");
            }
            Log.v(LOG_TAG, "AudioRecord started");
            this.mRecordingState = 2;
            RecordingThread recordingThread = new RecordingThread();
            this.mRecordingThread = recordingThread;
            recordingThread.start();
            Log.v(LOG_TAG, "WAVRecorder resumed");
        } else {
            Log.e(LOG_TAG, "Recording is going on");
            throw new IllegalStateException("Recording is going on");
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0067 A[Catch: all -> 0x007d, TryCatch #1 {, blocks: (B:4:0x0005, B:9:0x000e, B:10:0x0036, B:11:0x0037, B:12:0x003a, B:14:0x003e, B:16:0x0044, B:20:0x0052, B:21:0x0055, B:25:0x0063, B:27:0x0067, B:28:0x0071, B:24:0x005c, B:19:0x004b), top: B:36:0x0005, inners: #0, #2 }] */
    @Override // miuix.media.Recorder
    public synchronized void stop() throws IllegalStateException {
        AudioRecord audioRecord;
        int i = this.mRecordingState;
        if (i != 3 && i != 2) {
            Log.e(LOG_TAG, "Recorder should not be stopped in state:" + this.mRecordingState);
            throw new IllegalStateException("Recorder should not be stopped in state : " + this.mRecordingState);
        }
        this.mRecordingState = 4;
        try {
            RecordingThread recordingThread = this.mRecordingThread;
            if (recordingThread != null && recordingThread.isAlive()) {
                this.mRecordingThread.join();
                this.mRecordingThread = null;
                this.mOutputStream.close();
                audioRecord = this.mRecorder;
                if (audioRecord != null) {
                    audioRecord.stop();
                    Log.v(LOG_TAG, "AudioRecord stoped");
                }
                updateWaveHeader();
                Log.v(LOG_TAG, "WavRecorder stoped");
            } else {
                this.mRecordingThread = null;
                try {
                    this.mOutputStream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error file cannot be closed", e);
                }
                audioRecord = this.mRecorder;
                if (audioRecord != null) {
                    audioRecord.stop();
                    Log.v(LOG_TAG, "AudioRecord stoped");
                }
                updateWaveHeader();
                Log.v(LOG_TAG, "WavRecorder stoped");
            }
        } catch (InterruptedException e2) {
            Log.e(LOG_TAG, "InterruptedException when stop", e2);
        }
        throw th;
    }

    @Override // miuix.media.Recorder
    public void release() {
        AudioRecord audioRecord = this.mRecorder;
        if (audioRecord != null) {
            audioRecord.release();
            this.mRecorder = null;
            Log.v(LOG_TAG, "AudioRecord released");
        }
        Log.v(LOG_TAG, "WAVRecorder released");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findMaxAmplitude(short[] sArr, int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            short s = sArr[i2];
            int i4 = s;
            if (s < 0) {
                i4 = -s;
            }
            if (i4 > i3) {
                i3 = i4;
            }
            i2++;
            i3 = i3;
        }
        return i3 == true ? 1 : 0;
    }

    @Override // miuix.media.Recorder
    public int getMaxAmplitude() {
        return this.mMaxAmplitude;
    }

    @Override // miuix.media.Recorder
    public boolean isPaused() {
        return this.mRecordingState == 3;
    }

    private void prepareExtraParameters(AudioRecord audioRecord, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (AudioRecordHelper.isExtraParamSupported()) {
            Log.d(LOG_TAG, "setParameters: " + AudioRecordHelper.setParameters(audioRecord, str));
        } else {
            Log.e(LOG_TAG, "Do not support extra parameters");
        }
    }

    private class RecordingThread extends Thread {
        private RecordingThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Process.setThreadPriority(-19);
            Log.v(LocalWavRecorder.LOG_TAG, "RecordingThread started");
            if (LocalWavRecorder.this.mRecordingState != 2) {
                Log.e(LocalWavRecorder.LOG_TAG, "Error illegal state: " + LocalWavRecorder.this.mRecordingState);
                LocalWavRecorder.this.notifyError(1001);
                return;
            }
            if (LocalWavRecorder.this.mOutputStream != null) {
                while (LocalWavRecorder.this.mRecordingState == 2) {
                    int i = LocalWavRecorder.this.mRecorder.read(LocalWavRecorder.this.mBufferPCM, 0, LocalWavRecorder.this.mBufferPCM.length);
                    LocalWavRecorder.this.mCountRecordingSamples += (long) i;
                    if (i <= 0) {
                        Log.e(LocalWavRecorder.LOG_TAG, "Error record sample failed, read size: " + i);
                        LocalWavRecorder.this.notifyError(1003);
                        break;
                    }
                    LocalWavRecorder localWavRecorder = LocalWavRecorder.this;
                    localWavRecorder.mMaxAmplitude = localWavRecorder.findMaxAmplitude(localWavRecorder.mBufferPCM, i);
                    if (!LocalWavRecorder.this.mOutputFile.exists()) {
                        LocalWavRecorder.this.notifyError(1009);
                        break;
                    }
                    try {
                        LocalWavRecorder.this.mOutputStream.write(RecorderUtils.short2byte(LocalWavRecorder.this.mBufferPCM, i));
                        LocalWavRecorder.this.mCurrentRecordingSize += (long) (i * 2);
                        if (LocalWavRecorder.this.mCurrentRecordingSize >= LocalWavRecorder.this.mMaxFileSize) {
                            LocalWavRecorder.this.notifyError(1007);
                            break;
                        }
                    } catch (IOException unused) {
                        Log.e(LocalWavRecorder.LOG_TAG, "Error when write sample to file: " + LocalWavRecorder.this.mOutputFilePath);
                        LocalWavRecorder.this.notifyError(1005);
                    }
                }
                LocalWavRecorder.this.mMaxAmplitude = 0;
                Log.v(LocalWavRecorder.LOG_TAG, "RecordingThread stoped");
                return;
            }
            Log.e(LocalWavRecorder.LOG_TAG, "Error out put stream not ready");
            LocalWavRecorder.this.notifyError(1002);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int i) {
        Handler handler = this.mEventHandler;
        if (handler != null) {
            handler.sendMessage(handler.obtainMessage(1, i, 0));
        }
    }

    private class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1 && LocalWavRecorder.this.mOnErrorListener != null) {
                LocalWavRecorder.this.mOnErrorListener.onError(LocalWavRecorder.this, RecorderUtils.convertErrorCode(message.arg1, false));
            }
            super.handleMessage(message);
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0068 A[Catch: IOException -> 0x007a, PHI: r3 r6
  0x0068: PHI (r3v5 java.io.RandomAccessFile) = (r3v3 java.io.RandomAccessFile), (r3v6 java.io.RandomAccessFile) binds: [B:32:0x0066, B:43:0x007d] A[DONT_GENERATE, DONT_INLINE]
  0x0068: PHI (r6v7 java.nio.channels.FileChannel) = (r6v5 java.nio.channels.FileChannel), (r6v8 java.nio.channels.FileChannel) binds: [B:32:0x0066, B:43:0x007d] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #2 {IOException -> 0x007a, blocks: (B:6:0x0034, B:8:0x0039, B:31:0x0063, B:33:0x0068, B:39:0x0076), top: B:58:0x0005 }] */
    private void updateWaveHeader() throws Throwable {
        Throwable th;
        FileChannel channel;
        IOException iOException;
        FileNotFoundException fileNotFoundException;
        RandomAccessFile randomAccessFile = null;
        try {
            try {
                try {
                    RandomAccessFile randomAccessFile2 = new RandomAccessFile(this.mOutputFile, "rw");
                    try {
                        channel = randomAccessFile2.getChannel();
                        try {
                            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0L, 44L);
                            RecorderUtils.updateBytes(map, 4, RecorderUtils.getBytes(this.mCurrentRecordingSize + 36));
                            RecorderUtils.updateBytes(map, 40, RecorderUtils.getBytes(this.mCurrentRecordingSize));
                            randomAccessFile2.close();
                            if (channel != null) {
                                channel.close();
                            }
                        } catch (FileNotFoundException e) {
                            fileNotFoundException = e;
                            randomAccessFile = randomAccessFile2;
                            Log.e(LOG_TAG, "FileNotFoundException", fileNotFoundException);
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            if (channel != null) {
                                channel.close();
                            }
                        } catch (IOException e2) {
                            iOException = e2;
                            randomAccessFile = randomAccessFile2;
                            Log.e(LOG_TAG, "IOException", iOException);
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            if (channel != null) {
                                channel.close();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            randomAccessFile = randomAccessFile2;
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                } catch (IOException e3) {
                                    Log.e(LOG_TAG, "file close", e3);
                                    throw th;
                                }
                            }
                            if (channel == null) {
                                throw th;
                            }
                            channel.close();
                            throw th;
                        }
                    } catch (FileNotFoundException e4) {
                        fileNotFoundException = e4;
                        channel = null;
                    } catch (IOException e5) {
                        iOException = e5;
                        channel = null;
                    } catch (Throwable th3) {
                        th = th3;
                        channel = null;
                    }
                } catch (FileNotFoundException e6) {
                    fileNotFoundException = e6;
                    channel = null;
                } catch (IOException e7) {
                    iOException = e7;
                    channel = null;
                } catch (Throwable th4) {
                    th = th4;
                    channel = null;
                }
            } catch (IOException e8) {
                Log.e(LOG_TAG, "file close", e8);
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }
}
