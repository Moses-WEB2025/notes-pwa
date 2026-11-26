package com.miui.common.record;

import com.miui.pad.feature.notes.meeting.MeetingRecorder;
import miuix.media.Mp3Encoder;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Recorder {
    public static final int ERR_INIT_RECORDER_AUDIO_OCCUPIED = 2;
    public static final int ERR_INIT_RECORDER_INVALID_BUFFER_SIZE = 1;
    public static final int REC_SRC_MEETING = 3;
    public static final int REC_SRC_MIC = 1;
    public static final int SAMPLE_RATE_IN_HZ_16000 = 16000;
    protected int mAudioFormat;
    protected int mAudioSource;
    protected int mBufferSizeInBytes;
    protected int mChannelConfig;
    protected int mErrorCode;
    protected String mOutputFilePath;
    protected int mSampleRateInHz;

    public abstract int getMP3BufferSize(int i);

    public abstract int getMinPCMBufferSize();

    public abstract Mp3Encoder getMp3Encoder();

    protected abstract void init();

    public abstract int read(byte[] bArr, int i, int i2);

    public abstract int read(short[] sArr, int i, int i2);

    public abstract void release();

    public abstract int startMP3Encode(short[] sArr, short[] sArr2, int i, byte[] bArr, int i2);

    public abstract void startRecording();

    public abstract void stopMP3Encode();

    public abstract void stopRecord();

    public static Recorder getRecorder(int i) {
        if (i == 1) {
            return new NativeRecorder();
        }
        if (i != 3) {
            return null;
        }
        return new MeetingRecorder();
    }

    public Recorder() {
        init();
    }

    public void setOutputFile(String str) {
        this.mOutputFilePath = str;
    }

    public String getOutputFilePath() {
        return this.mOutputFilePath;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }
}
