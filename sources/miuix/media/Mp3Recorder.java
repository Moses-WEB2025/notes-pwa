package miuix.media;

import android.media.AudioRecord;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes5.dex */
public class Mp3Recorder {
    public static final int ERR_COULD_NOT_START = 6;
    public static final int ERR_ENCODE_PCM_FAILED = 4;
    public static final int ERR_FILE_NOT_EXIST = 8;
    public static final int ERR_ILLEGAL_STATE = 1;
    public static final int ERR_MAX_SIZE_REACHED = 7;
    public static final int ERR_OUT_STREAM_NOT_READY = 2;
    public static final int ERR_RECORD_PCM_FAILED = 3;
    public static final int ERR_WRITE_TO_FILE = 5;
    private static final String LOG_TAG = "Mp3Recorder";
    private static final int MP3_RECORDER_EVENT_ERROR = 1;
    private static final int STATE_IDEL = 0;
    private static final int STATE_PAUSED = 3;
    private static final int STATE_PREPARED = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_STOPED = 4;
    public static final int VBR_QUALITY_HIGH = 2;
    public static final int VBR_QUALITY_LOW = 9;
    public static final int VBR_QUALITY_MEDIUM = 6;
    private byte[] bufferMP3;
    private short[] bufferPCM;
    private int mAudioChannel;
    private int mAudioSource;
    private int mChannelCount;
    private long mCountRecordingSamples;
    private long mCurrentRecordingSize;
    private Handler mEventHandler;
    private int mMaxAmplitude;
    private long mMaxFileSize;
    private int mMinBufferSize;
    private Mp3Encoder mMp3Encoder;
    private RecordingErrorListener mOnErrorListener;
    private int mOutBitRate;
    private int mOutMode;
    private File mOutputFile;
    private String mOutputFilePath;
    private FileOutputStream mOutputStream;
    private String mParams;
    private int mQuality;
    private AudioRecord mRecorder;
    private int mRecordingState;
    private RecordingThread mRecordingThread;
    private int mSampleRate;
    private int mVBRQuality;

    public interface RecordingErrorListener {
        void onError(Mp3Recorder mp3Recorder, int i);
    }

    public Mp3Recorder() {
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

    public void setOutputFile(String str) {
        this.mOutputFilePath = str;
    }

    public void setAudioSource(int i) {
        this.mAudioSource = i;
    }

    public void setAudioSamplingRate(int i) {
        this.mSampleRate = i;
    }

    public void setOutBitRate(int i) {
        this.mOutBitRate = i;
    }

    public void setOnErrorListener(RecordingErrorListener recordingErrorListener) {
        this.mOnErrorListener = recordingErrorListener;
    }

    public void setQuality(int i) {
        this.mQuality = i;
    }

    public void setExtraParameters(String str) {
        if (!AudioRecordHelper.isExtraParamSupported()) {
            throw new IllegalArgumentException("Do not support extra parameter");
        }
        this.mParams = str;
    }

    public boolean isExtraParamSupported() {
        return AudioRecordHelper.isExtraParamSupported();
    }

    public void setAudioChannel(int i) {
        this.mAudioChannel = i;
    }

    public void setMaxFileSize(long j) {
        if (j > 0) {
            this.mMaxFileSize = j;
        } else {
            this.mMaxFileSize = Long.MAX_VALUE;
        }
    }

    public void setMaxDuration(long j) {
        if (j > 0) {
            this.mMaxFileSize = j * ((long) (this.mOutBitRate / 8));
        } else {
            this.mMaxFileSize = Long.MAX_VALUE;
        }
    }

    public void setVBRQuality(int i) {
        this.mVBRQuality = i;
    }

    public void reset() {
        this.mRecorder = null;
        this.mAudioSource = 1;
        this.mSampleRate = Mp3Encoder.DEFAULT_SAMPLE_RATE;
        this.mAudioChannel = 16;
        this.mQuality = 0;
        this.mRecordingState = 0;
        this.mOutBitRate = 64;
        this.mCountRecordingSamples = 0L;
        this.mCurrentRecordingSize = 0L;
        this.mMaxFileSize = Long.MAX_VALUE;
        this.mVBRQuality = -1;
        this.mMp3Encoder = new Mp3Encoder();
    }

    public void prepare() throws IllegalStateException, IOException {
        int minBufferSize = AudioRecord.getMinBufferSize(this.mSampleRate, this.mAudioChannel, 2);
        this.mMinBufferSize = minBufferSize;
        if (minBufferSize < 0) {
            Log.e(LOG_TAG, "Error when getting min buffer size");
            throw new IllegalStateException("Could not calculate the min buffer size");
        }
        this.bufferPCM = new short[minBufferSize * 2];
        AudioRecord audioRecord = new AudioRecord(this.mAudioSource, this.mSampleRate, this.mAudioChannel, 2, this.bufferPCM.length);
        this.mRecorder = audioRecord;
        prepareExtraParameters(audioRecord, this.mParams);
        Log.v(LOG_TAG, "Apply new AudioRecord");
        int i = this.mAudioChannel;
        this.mOutMode = i == 12 ? 0 : 3;
        this.mChannelCount = i != 12 ? 1 : 2;
        this.bufferMP3 = new byte[(int) ((((double) this.bufferPCM.length) * 1.25d) + 7200.0d)];
        this.mMp3Encoder.setInSampleRate(this.mSampleRate);
        this.mMp3Encoder.setOutMode(this.mOutMode);
        this.mMp3Encoder.setChannelCount(this.mChannelCount);
        this.mMp3Encoder.setOutSampleRate(this.mSampleRate);
        this.mMp3Encoder.setOutBitRate(this.mOutBitRate);
        this.mMp3Encoder.setQuality(this.mQuality);
        this.mMp3Encoder.setVBRQuality(this.mVBRQuality);
        this.mMp3Encoder.init();
        this.mOutputFile = new File(this.mOutputFilePath);
        this.mRecordingState = 1;
        Log.v(LOG_TAG, "Mp3Recorder prepared");
    }

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
            throw new IllegalStateException("Mp3 record could not start: other input already started");
        }
        this.mOutputStream = new FileOutputStream(this.mOutputFile);
        Log.v(LOG_TAG, "AudioRecord started");
        RecordingThread recordingThread = new RecordingThread();
        this.mRecordingThread = recordingThread;
        recordingThread.start();
        Log.v(LOG_TAG, "Mp3Recorder started");
    }

    public long getRecordingTimeInMillis() {
        return (long) (((this.mCountRecordingSamples / ((double) this.mSampleRate)) * 1000.0d) / ((double) this.mChannelCount));
    }

    public long getRecordingSizeInByte() {
        return this.mCurrentRecordingSize;
    }

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
        Log.v(LOG_TAG, "Mp3Recorder paused");
    }

    public synchronized void resume() throws IllegalStateException {
        if (this.mRecordingState == 3) {
            this.mRecorder = new AudioRecord(this.mAudioSource, this.mSampleRate, this.mAudioChannel, 2, this.bufferPCM.length);
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
            Log.v(LOG_TAG, "Mp3Recorder resumed");
        } else {
            Log.e(LOG_TAG, "Recording is going on");
            throw new IllegalStateException("Recording is going on");
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0060 A[Catch: all -> 0x009c, IOException -> 0x009e, TRY_ENTER, TryCatch #3 {all -> 0x009c, blocks: (B:22:0x0060, B:26:0x0070, B:36:0x0094, B:37:0x009b, B:41:0x009f), top: B:68:0x005e, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x0089 A[Catch: all -> 0x00e4, TryCatch #0 {, blocks: (B:4:0x0005, B:9:0x000e, B:10:0x0036, B:11:0x0037, B:12:0x003a, B:14:0x003e, B:16:0x0044, B:20:0x0052, B:27:0x0077, B:31:0x0085, B:33:0x0089, B:34:0x008e, B:49:0x00be, B:30:0x007e, B:52:0x00c7, B:56:0x00d5, B:58:0x00d9, B:59:0x00e3, B:55:0x00ce, B:42:0x00a6, B:46:0x00b4, B:48:0x00b8, B:45:0x00ad, B:19:0x004b, B:22:0x0060, B:26:0x0070, B:36:0x0094, B:37:0x009b, B:41:0x009f), top: B:63:0x0005, inners: #1, #2, #3, #4, #5, #6 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x0094 A[Catch: all -> 0x009c, IOException -> 0x009e, TRY_ENTER, TryCatch #3 {all -> 0x009c, blocks: (B:22:0x0060, B:26:0x0070, B:36:0x0094, B:37:0x009b, B:41:0x009f), top: B:68:0x005e, outer: #0 }] */
    public synchronized void stop() throws IllegalStateException {
        int iFlush;
        String str;
        int i;
        AudioRecord audioRecord;
        int i2 = this.mRecordingState;
        if (i2 != 3 && i2 != 2) {
            Log.e(LOG_TAG, "Recorder should not be stopped in state:" + this.mRecordingState);
            throw new IllegalStateException("Recorder shoul not be stopped in state : " + this.mRecordingState);
        }
        this.mRecordingState = 4;
        try {
            RecordingThread recordingThread = this.mRecordingThread;
            if (recordingThread != null && recordingThread.isAlive()) {
                this.mRecordingThread.join();
                this.mRecordingThread = null;
                Mp3Encoder mp3Encoder = this.mMp3Encoder;
                byte[] bArr = this.bufferMP3;
                iFlush = mp3Encoder.flush(bArr, bArr.length);
                if (iFlush > 0) {
                    this.mOutputStream.write(this.bufferMP3, 0, iFlush);
                    i = this.mVBRQuality;
                    if (i >= 0) {
                        this.mMp3Encoder.writeVBRHeader(this.mOutputFilePath);
                    }
                    this.mOutputStream.close();
                    audioRecord = this.mRecorder;
                    if (audioRecord != null) {
                        audioRecord.stop();
                        str = LOG_TAG;
                        Log.v(str, "AudioRecord stoped");
                    }
                    Log.v(LOG_TAG, "Mp3Recorder stoped");
                } else {
                    throw new IllegalStateException("Buffer flush must greater than 0");
                }
            } else {
                this.mRecordingThread = null;
                Mp3Encoder mp3Encoder2 = this.mMp3Encoder;
                byte[] bArr2 = this.bufferMP3;
                iFlush = mp3Encoder2.flush(bArr2, bArr2.length);
                try {
                    try {
                        if (iFlush > 0) {
                            this.mOutputStream.write(this.bufferMP3, 0, iFlush);
                            i = this.mVBRQuality;
                            if (i >= 0 && i <= 9) {
                                this.mMp3Encoder.writeVBRHeader(this.mOutputFilePath);
                            }
                            try {
                                this.mOutputStream.close();
                            } catch (IOException e) {
                                Log.e(LOG_TAG, "Error file cannot be closed", e);
                            }
                            audioRecord = this.mRecorder;
                            if (audioRecord != null) {
                                audioRecord.stop();
                                str = LOG_TAG;
                                Log.v(str, "AudioRecord stoped");
                            }
                            Log.v(LOG_TAG, "Mp3Recorder stoped");
                        } else {
                            throw new IllegalStateException("Buffer flush must greater than 0");
                        }
                    } catch (IOException e2) {
                        Log.e(LOG_TAG, "Error file cannot be written when flush", e2);
                        try {
                            this.mOutputStream.close();
                        } catch (IOException e3) {
                            Log.e(LOG_TAG, "Error file cannot be closed", e3);
                        }
                        AudioRecord audioRecord2 = this.mRecorder;
                        if (audioRecord2 != null) {
                            audioRecord2.stop();
                            str = LOG_TAG;
                        }
                    }
                } catch (Throwable th) {
                    try {
                        this.mOutputStream.close();
                    } catch (IOException e4) {
                        Log.e(LOG_TAG, "Error file cannot be closed", e4);
                    }
                    AudioRecord audioRecord3 = this.mRecorder;
                    if (audioRecord3 == null) {
                        throw th;
                    }
                    audioRecord3.stop();
                    Log.v(LOG_TAG, "AudioRecord stoped");
                    throw th;
                }
            }
        } catch (InterruptedException e5) {
            Log.e(LOG_TAG, "InterruptedException when stop", e5);
        }
        throw th;
    }

    public void release() {
        AudioRecord audioRecord = this.mRecorder;
        if (audioRecord != null) {
            audioRecord.release();
            this.mRecorder = null;
            Log.v(LOG_TAG, "AudioRecord released");
        }
        this.mMp3Encoder.close();
        Log.v(LOG_TAG, "Mp3Recorder released");
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

    public int getMaxAmplitude() {
        return this.mMaxAmplitude;
    }

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
            Log.v(Mp3Recorder.LOG_TAG, "RecordingThread started");
            if (Mp3Recorder.this.mRecordingState != 2) {
                Log.e(Mp3Recorder.LOG_TAG, "Error illegal state: " + Mp3Recorder.this.mRecordingState);
                Mp3Recorder.this.notifyError(1);
                return;
            }
            if (Mp3Recorder.this.mOutputStream != null) {
                while (Mp3Recorder.this.mRecordingState == 2) {
                    int i = Mp3Recorder.this.mRecorder.read(Mp3Recorder.this.bufferPCM, 0, Mp3Recorder.this.bufferPCM.length);
                    Mp3Recorder.this.mCountRecordingSamples += (long) i;
                    if (i <= 0) {
                        Log.e(Mp3Recorder.LOG_TAG, "Error record sample failed, read size: " + i);
                        Mp3Recorder.this.notifyError(3);
                        break;
                    }
                    Mp3Recorder mp3Recorder = Mp3Recorder.this;
                    mp3Recorder.mMaxAmplitude = mp3Recorder.findMaxAmplitude(mp3Recorder.bufferPCM, i);
                    int iEncode = Mp3Recorder.this.mAudioChannel == 16 ? Mp3Recorder.this.mMp3Encoder.encode(Mp3Recorder.this.bufferPCM, Mp3Recorder.this.bufferPCM, i, Mp3Recorder.this.bufferMP3, Mp3Recorder.this.bufferMP3.length) : Mp3Recorder.this.mMp3Encoder.encodeInterleaved(Mp3Recorder.this.bufferPCM, i / Mp3Recorder.this.mChannelCount, Mp3Recorder.this.bufferMP3, Mp3Recorder.this.bufferMP3.length);
                    if (iEncode <= 0) {
                        if (iEncode == 0) {
                            Log.e(Mp3Recorder.LOG_TAG, "Not a complete frame samples to encode: skiped");
                        } else {
                            Log.e(Mp3Recorder.LOG_TAG, "Error encode PCM failed, encode size: " + iEncode + " read size: " + i);
                            if (Mp3Recorder.this.mOnErrorListener == null) {
                                break;
                            }
                            Mp3Recorder.this.notifyError(4);
                            break;
                        }
                    } else {
                        if (!Mp3Recorder.this.mOutputFile.exists()) {
                            Mp3Recorder.this.notifyError(8);
                            break;
                        }
                        try {
                            Mp3Recorder.this.mOutputStream.write(Mp3Recorder.this.bufferMP3, 0, iEncode);
                            Mp3Recorder.this.mCurrentRecordingSize += (long) iEncode;
                            if (Mp3Recorder.this.mCurrentRecordingSize >= Mp3Recorder.this.mMaxFileSize) {
                                Mp3Recorder.this.notifyError(7);
                                break;
                            }
                        } catch (IOException unused) {
                            Log.e(Mp3Recorder.LOG_TAG, "Error when write sample to file: " + Mp3Recorder.this.mOutputFilePath);
                            Mp3Recorder.this.notifyError(5);
                        }
                    }
                }
                Mp3Recorder.this.mMaxAmplitude = 0;
                Log.v(Mp3Recorder.LOG_TAG, "RecordingThread stoped");
                return;
            }
            Log.e(Mp3Recorder.LOG_TAG, "Error out put stream not ready");
            Mp3Recorder.this.notifyError(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int i) {
        Handler handler = this.mEventHandler;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage(1);
            messageObtainMessage.arg1 = i;
            this.mEventHandler.sendMessage(messageObtainMessage);
        }
    }

    private class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1 && Mp3Recorder.this.mOnErrorListener != null) {
                Mp3Recorder.this.mOnErrorListener.onError(Mp3Recorder.this, message.arg1);
            }
            super.handleMessage(message);
        }
    }
}
