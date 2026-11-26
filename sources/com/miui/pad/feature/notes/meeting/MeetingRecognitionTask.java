package com.miui.pad.feature.notes.meeting;

import android.content.Context;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.record.Recorder;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.RomUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import com.xiaomi.ai.mibrain.MibrainException;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MeetingRecognitionTask.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u0005\n\u0002\b\n\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\b&\u0018\u0000 J2\u00020\u0001:\u0004GHIJB\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u000e\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0019J \u0010+\u001a\u00020)2\u0006\u0010,\u001a\u00020\u00192\u0006\u0010-\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u0019H\u0016J\b\u0010/\u001a\u00020)H\u0016J\b\u00100\u001a\u00020)H\u0016J\u0010\u00101\u001a\u00020)2\u0006\u00102\u001a\u000203H\u0002J\b\u00104\u001a\u00020)H\u0002J\b\u00107\u001a\u00020)H$J\u0012\u00108\u001a\u00020)2\b\u00109\u001a\u0004\u0018\u00010\u001fH$J\u0012\u0010:\u001a\u00020)2\b\u0010;\u001a\u0004\u0018\u00010\u0005H$J\u001a\u0010<\u001a\u00020)2\b\u0010=\u001a\u0004\u0018\u00010>2\u0006\u0010?\u001a\u00020@H$J\b\u0010A\u001a\u00020)H&J\b\u0010B\u001a\u00020)H&J \u0010D\u001a\u00020)2\u0006\u0010,\u001a\u00020\u00192\u0006\u0010E\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u0019H&J\b\u0010F\u001a\u00020)H&R\u001a\u0010\u0002\u001a\u00020\u0003X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0005X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0006\u001a\u00020\u0007X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u0005X\u0084D¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u001a\u0010\u0018\u001a\u00020\u0019X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0018\u00010%R\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\b\u0018\u00010%R\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010'\u001a\u00020\u00198\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u00105\u001a\u00020\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b5\u0010\u001aR\u0011\u00106\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b6\u0010\u001aR\u0011\u0010C\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\bC\u0010\u001a¨\u0006K"}, d2 = {"Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask;", "Lcom/miui/pad/feature/notes/meeting/WebSocketThread$OnMessageReceiveListener;", "mContext", "Landroid/content/Context;", "mMp3DirName", "", "mMeetingRecognitionListener", "Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Ljava/lang/String;Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionListener;)V", "getMContext", "()Landroid/content/Context;", "setMContext", "(Landroid/content/Context;)V", "getMMp3DirName", "()Ljava/lang/String;", "setMMp3DirName", "(Ljava/lang/String;)V", "getMMeetingRecognitionListener", "()Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionListener;", "setMMeetingRecognitionListener", "(Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionListener;)V", "TAG", "getTAG", "isDebug", "", "()Z", "setDebug", "(Z)V", "mBlockingQueue", "Ljava/util/concurrent/BlockingQueue;", "", "mSync", "", "mRecorder", "Lcom/miui/common/record/Recorder;", "audioProducerThread", "Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask$LocalThread;", "audioConsumerThread", "mNetWorkValid", "setNetWorkValid", "", "isValid", "start", "isSingle", "isTrans", "isFold", "shutdownNow", "destroy", "appendFlag", "value", "", "stopRecord", "isBlockingQueueAvailable", "isConsumerAvailable", "onRequestStart", "onBufferSend", "buffer", "onRenameMp3File", "mp3FileName", "updateMaxAmplitude", "pcmShortBuffer", "", "size", "", "stopEngine", "startEngine", "isSkipSpeechRecognition", "updateAsrInfo", "needTranslate", "addEnterFlag", "LocalThread", "AudioProducerThread", "AudioConsumerThread", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class MeetingRecognitionTask implements WebSocketThread.OnMessageReceiveListener {
    private static final int BORDER_PCM_SHORT_BUFFER = 2000;
    private static final int MAX_BLOCKING_QUEUE = 1024;
    private final String TAG;
    private LocalThread audioConsumerThread;
    private LocalThread audioProducerThread;
    private boolean isDebug;
    private final BlockingQueue<byte[]> mBlockingQueue;
    private Context mContext;
    private MeetingRecognitionListener mMeetingRecognitionListener;
    private String mMp3DirName;
    protected volatile boolean mNetWorkValid;
    private Recorder mRecorder;
    private final Object mSync;

    public abstract void addEnterFlag();

    protected abstract void onBufferSend(byte[] buffer);

    protected abstract void onRenameMp3File(String mp3FileName);

    protected abstract void onRequestStart();

    public abstract void startEngine();

    public abstract void stopEngine();

    public abstract void updateAsrInfo(boolean isSingle, boolean needTranslate, boolean isFold);

    protected abstract void updateMaxAmplitude(short[] pcmShortBuffer, int size);

    public MeetingRecognitionTask(Context mContext, String mMp3DirName, MeetingRecognitionListener mMeetingRecognitionListener) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(mMp3DirName, "mMp3DirName");
        Intrinsics.checkNotNullParameter(mMeetingRecognitionListener, "mMeetingRecognitionListener");
        this.mContext = mContext;
        this.mMp3DirName = mMp3DirName;
        this.mMeetingRecognitionListener = mMeetingRecognitionListener;
        this.TAG = "MeetingRecognitionTask";
        this.isDebug = true;
        this.mSync = new Object();
        this.mBlockingQueue = new LinkedBlockingDeque();
    }

    protected final Context getMContext() {
        return this.mContext;
    }

    protected final void setMContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        this.mContext = context;
    }

    protected final String getMMp3DirName() {
        return this.mMp3DirName;
    }

    protected final void setMMp3DirName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.mMp3DirName = str;
    }

    protected final MeetingRecognitionListener getMMeetingRecognitionListener() {
        return this.mMeetingRecognitionListener;
    }

    protected final void setMMeetingRecognitionListener(MeetingRecognitionListener meetingRecognitionListener) {
        Intrinsics.checkNotNullParameter(meetingRecognitionListener, "<set-?>");
        this.mMeetingRecognitionListener = meetingRecognitionListener;
    }

    protected final String getTAG() {
        return this.TAG;
    }

    /* JADX INFO: renamed from: isDebug, reason: from getter */
    protected final boolean getIsDebug() {
        return this.isDebug;
    }

    protected final void setDebug(boolean z) {
        this.isDebug = z;
    }

    public final void setNetWorkValid(boolean isValid) {
        this.mNetWorkValid = isValid;
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0013  */
    public void start(boolean isSingle, boolean isTrans, boolean isFold) {
        if (!isSkipSpeechRecognition()) {
            LocalThread localThread = this.audioConsumerThread;
            if (localThread != null) {
                Intrinsics.checkNotNull(localThread);
                if (!localThread.getIsStart()) {
                    AudioConsumerThread audioConsumerThread = new AudioConsumerThread();
                    this.audioConsumerThread = audioConsumerThread;
                    Intrinsics.checkNotNull(audioConsumerThread);
                    audioConsumerThread.setStart(true);
                    LocalThread localThread2 = this.audioConsumerThread;
                    Intrinsics.checkNotNull(localThread2);
                    localThread2.start();
                }
            } else {
                AudioConsumerThread audioConsumerThread2 = new AudioConsumerThread();
                this.audioConsumerThread = audioConsumerThread2;
                Intrinsics.checkNotNull(audioConsumerThread2);
                audioConsumerThread2.setStart(true);
                LocalThread localThread3 = this.audioConsumerThread;
                Intrinsics.checkNotNull(localThread3);
                localThread3.start();
            }
        }
        LocalThread localThread4 = this.audioProducerThread;
        if (localThread4 != null) {
            Intrinsics.checkNotNull(localThread4);
            if (localThread4.getIsStart()) {
                return;
            }
        }
        AudioProducerThread audioProducerThread = new AudioProducerThread();
        this.audioProducerThread = audioProducerThread;
        Intrinsics.checkNotNull(audioProducerThread);
        audioProducerThread.start();
    }

    public void shutdownNow() {
        LocalThread localThread = this.audioProducerThread;
        if (localThread != null) {
            Intrinsics.checkNotNull(localThread);
            localThread.setStart(false);
            LocalThread localThread2 = this.audioProducerThread;
            Intrinsics.checkNotNull(localThread2);
            localThread2.interrupt();
            this.audioProducerThread = null;
        }
        if (this.audioConsumerThread != null) {
            this.mBlockingQueue.clear();
            LocalThread localThread3 = this.audioConsumerThread;
            Intrinsics.checkNotNull(localThread3);
            localThread3.setStart(false);
            LocalThread localThread4 = this.audioConsumerThread;
            Intrinsics.checkNotNull(localThread4);
            localThread4.interrupt();
            this.audioConsumerThread = null;
        }
    }

    public void destroy() {
        shutdownNow();
    }

    /* JADX INFO: compiled from: MeetingRecognitionTask.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b¢\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH$R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\f\u001a\u00020\u00058TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006¨\u0006\r"}, d2 = {"Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask$LocalThread;", "Ljava/lang/Thread;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask;)V", CallMethod.ARG_IS_START, "", "()Z", "setStart", "(Z)V", "run", "", "runInternal", "isLoop", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private abstract class LocalThread extends Thread {
        private volatile boolean isStart;

        protected abstract void runInternal();

        public LocalThread() {
        }

        /* JADX INFO: renamed from: isStart, reason: from getter */
        public final boolean getIsStart() {
            return this.isStart;
        }

        public final void setStart(boolean z) {
            this.isStart = z;
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

    private final void appendFlag(byte value) {
        this.mBlockingQueue.add(new byte[]{value});
    }

    /* JADX INFO: compiled from: MeetingRecognitionTask.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0014J\b\u0010\n\u001a\u00020\u0006H\u0002R\u0014\u0010\u0007\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\t¨\u0006\u000b"}, d2 = {"Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask$AudioProducerThread;", "Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask$LocalThread;", "Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask;)V", "runInternal", "", "isLoop", "", "()Z", "recordingToBuffer", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class AudioProducerThread extends LocalThread {
        public AudioProducerThread() {
            super();
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask.LocalThread
        protected void runInternal() {
            Object obj = MeetingRecognitionTask.this.mSync;
            MeetingRecognitionTask meetingRecognitionTask = MeetingRecognitionTask.this;
            synchronized (obj) {
                if (meetingRecognitionTask.mRecorder == null) {
                    meetingRecognitionTask.mRecorder = Recorder.getRecorder(3);
                }
                Recorder recorder = meetingRecognitionTask.mRecorder;
                Intrinsics.checkNotNull(recorder);
                if (recorder.getErrorCode() != 0) {
                    return;
                }
                Recorder recorder2 = meetingRecognitionTask.mRecorder;
                Intrinsics.checkNotNull(recorder2);
                recorder2.startRecording();
                try {
                    recordingToBuffer();
                } catch (MibrainException e) {
                    e.printStackTrace();
                }
                Unit unit = Unit.INSTANCE;
            }
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask.LocalThread
        protected boolean isLoop() {
            if (!MeetingRecognitionTask.this.isSkipSpeechRecognition()) {
                return super.isLoop();
            }
            return super.isLoop();
        }

        private final void recordingToBuffer() {
            Logger.INSTANCE.i(MeetingRecognitionTask.this.getTAG(), "recordingToBuffer");
            Recorder recorder = MeetingRecognitionTask.this.mRecorder;
            Intrinsics.checkNotNull(recorder);
            int minPCMBufferSize = recorder.getMinPCMBufferSize();
            if (minPCMBufferSize < 2000) {
                minPCMBufferSize *= 2;
            }
            if (MeetingRecognitionTask.this.getIsDebug()) {
                Logger.INSTANCE.e(MeetingRecognitionTask.this.getTAG(), "recordingToBuffer: " + minPCMBufferSize);
            }
            short[] sArr = new short[minPCMBufferSize];
            if (MeetingRecognitionTask.this.getIsDebug()) {
                Logger.INSTANCE.e(MeetingRecognitionTask.this.getTAG(), "pcmShortBuffer: " + minPCMBufferSize);
            }
            Recorder recorder2 = MeetingRecognitionTask.this.mRecorder;
            Intrinsics.checkNotNull(recorder2);
            int mP3BufferSize = recorder2.getMP3BufferSize(minPCMBufferSize);
            byte[] bArr = new byte[mP3BufferSize];
            Recorder recorder3 = MeetingRecognitionTask.this.mRecorder;
            Intrinsics.checkNotNull(recorder3);
            recorder3.setOutputFile(AudioUtils.obtainMp3FilePath(MeetingRecognitionTask.this.getMMp3DirName()));
            while (isLoop()) {
                Recorder recorder4 = MeetingRecognitionTask.this.mRecorder;
                Intrinsics.checkNotNull(recorder4);
                int i = recorder4.read(sArr, 0, minPCMBufferSize);
                MeetingRecognitionTask.this.updateMaxAmplitude(sArr, i);
                if (i <= 0) {
                    if (!MeetingRecognitionTask.this.getIsDebug()) {
                        break;
                    }
                    Logger.INSTANCE.e(MeetingRecognitionTask.this.getTAG(), "Audiorecord read error: " + i);
                    break;
                }
                byte[] bArrShortToByte = AudioUtils.shortToByte(sArr, i);
                int length = bArrShortToByte.length;
                Recorder recorder5 = MeetingRecognitionTask.this.mRecorder;
                Intrinsics.checkNotNull(recorder5);
                int iStartMP3Encode = recorder5.startMP3Encode(sArr, sArr, i, bArr, mP3BufferSize);
                if (iStartMP3Encode < 0) {
                    if (!MeetingRecognitionTask.this.getIsDebug()) {
                        break;
                    }
                    Logger.INSTANCE.d(MeetingRecognitionTask.this.getTAG(), "MP3 encode error: " + iStartMP3Encode);
                    break;
                } else if (!MeetingRecognitionTask.this.isSkipSpeechRecognition()) {
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(bArrShortToByte, 0, bArr2, 0, length);
                    MeetingRecognitionTask.this.onBufferSend(bArr2);
                }
            }
            Recorder recorder6 = MeetingRecognitionTask.this.mRecorder;
            Intrinsics.checkNotNull(recorder6);
            recorder6.stopMP3Encode();
            String mMp3DirName = MeetingRecognitionTask.this.getMMp3DirName();
            Recorder recorder7 = MeetingRecognitionTask.this.mRecorder;
            Intrinsics.checkNotNull(recorder7);
            MeetingRecognitionTask.this.onRenameMp3File(AudioUtils.renameMp3FileToSha1(mMp3DirName, recorder7.getOutputFilePath()));
            MeetingRecognitionTask.this.stopRecord();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void stopRecord() {
        Logger.INSTANCE.e(this.TAG, "stopRecord: ");
        Recorder recorder = this.mRecorder;
        Intrinsics.checkNotNull(recorder);
        recorder.stopRecord();
        Recorder recorder2 = this.mRecorder;
        Intrinsics.checkNotNull(recorder2);
        recorder2.release();
        this.mRecorder = null;
    }

    private final boolean isBlockingQueueAvailable() {
        return this.mBlockingQueue.size() < 1024;
    }

    public final boolean isConsumerAvailable() {
        LocalThread localThread;
        if (!isBlockingQueueAvailable() || (localThread = this.audioConsumerThread) == null) {
            return false;
        }
        Intrinsics.checkNotNull(localThread);
        return localThread.getIsStart();
    }

    /* JADX INFO: compiled from: MeetingRecognitionTask.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask$AudioConsumerThread;", "Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask$LocalThread;", "Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask;)V", "runInternal", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class AudioConsumerThread extends LocalThread {
        public AudioConsumerThread() {
            super();
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask.LocalThread
        public void runInternal() {
            MeetingRecognitionTask.this.mBlockingQueue.clear();
            while (isLoop()) {
                try {
                    Object objTake = MeetingRecognitionTask.this.mBlockingQueue.take();
                    Intrinsics.checkNotNullExpressionValue(objTake, "take(...)");
                    MeetingRecognitionTask.this.onBufferSend((byte[]) objTake);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final boolean isSkipSpeechRecognition() {
        return RomUtils.isInternationalBuild() || !PreferenceUtils.getAllowXiaoai(BaseNoteApp.INSTANCE.getInstance());
    }
}
