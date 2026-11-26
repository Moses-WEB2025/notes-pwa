package com.miui.common.record;

import android.media.AudioRecord;
import com.miui.notes.base.utils.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import miuix.media.Mp3Encoder;

/* JADX INFO: loaded from: classes2.dex */
public class NativeRecorder extends Recorder {
    private static final String TAG = "NativeRecorder";
    private int mChannelCount;
    private Mp3Encoder mMp3Encoder;
    private int mOutBitRate;
    private int mOutMode;
    private File mOutputFile;
    private FileOutputStream mOutputStream;
    private int mQuality;
    private AudioRecord mRecorder;
    private int mVBRQuality;
    private final Object mSyncObj = new Object();
    private volatile boolean mReleased = false;

    @Override // com.miui.common.record.Recorder
    public int getMP3BufferSize(int i) {
        return (int) ((((double) i) * 1.25d) + 7200.0d);
    }

    public NativeRecorder() {
        this.mRecorder = null;
        try {
            this.mRecorder = new AudioRecord(this.mAudioSource, this.mSampleRateInHz, this.mChannelConfig, this.mAudioFormat, this.mBufferSizeInBytes);
        } catch (IllegalArgumentException e) {
            this.mErrorCode = 1;
            Logger.INSTANCE.e(TAG, "IllegalArgumentException:" + e);
        }
        AudioRecord audioRecord = this.mRecorder;
        if (audioRecord == null || audioRecord.getState() == 1) {
            return;
        }
        Logger.INSTANCE.d(TAG, "mRecorder.getState() != AudioRecord.STATE_INITIALIZED");
        this.mErrorCode = 2;
    }

    @Override // com.miui.common.record.Recorder
    public void init() {
        this.mAudioSource = 1;
        this.mSampleRateInHz = 16000;
        this.mChannelConfig = 16;
        this.mAudioFormat = 2;
        this.mBufferSizeInBytes = AudioRecord.getMinBufferSize(this.mSampleRateInHz, this.mChannelConfig, this.mAudioFormat);
        this.mOutMode = this.mChannelConfig == 12 ? 0 : 3;
        this.mChannelCount = this.mChannelConfig == 12 ? 2 : 1;
        this.mOutBitRate = 64;
        this.mQuality = 0;
        this.mVBRQuality = -1;
    }

    @Override // com.miui.common.record.Recorder
    public void setOutputFile(String str) {
        super.setOutputFile(str);
        if (this.mOutputFilePath != null) {
            try {
                File file = new File(this.mOutputFilePath);
                this.mOutputFile = file;
                if (file.exists()) {
                    this.mOutputFile.delete();
                }
                this.mOutputStream = new FileOutputStream(this.mOutputFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void initMp3EncoderIfNeed() {
        if (this.mMp3Encoder == null) {
            Mp3Encoder mp3Encoder = new Mp3Encoder();
            this.mMp3Encoder = mp3Encoder;
            mp3Encoder.setInSampleRate(this.mSampleRateInHz);
            this.mMp3Encoder.setOutMode(this.mOutMode);
            this.mMp3Encoder.setChannelCount(this.mChannelCount);
            this.mMp3Encoder.setOutSampleRate(this.mSampleRateInHz);
            this.mMp3Encoder.setOutBitRate(this.mOutBitRate);
            this.mMp3Encoder.setQuality(this.mQuality);
            this.mMp3Encoder.setVBRQuality(this.mVBRQuality);
            this.mMp3Encoder.init();
        }
    }

    @Override // com.miui.common.record.Recorder
    public void startRecording() {
        synchronized (this.mSyncObj) {
            AudioRecord audioRecord = this.mRecorder;
            if (audioRecord != null && audioRecord.getState() == 1) {
                this.mRecorder.startRecording();
            }
        }
    }

    @Override // com.miui.common.record.Recorder
    public void stopRecord() {
        AudioRecord audioRecord = this.mRecorder;
        if (audioRecord != null) {
            try {
                audioRecord.stop();
            } catch (IllegalStateException e) {
                Logger.INSTANCE.e(TAG, "stopRecord error: " + e);
            }
        }
    }

    @Override // com.miui.common.record.Recorder
    public int read(byte[] bArr, int i, int i2) {
        if (this.mReleased) {
            return -1;
        }
        synchronized (this.mSyncObj) {
            AudioRecord audioRecord = this.mRecorder;
            if (audioRecord == null) {
                return -1;
            }
            return audioRecord.read(bArr, i, i2);
        }
    }

    @Override // com.miui.common.record.Recorder
    public int read(short[] sArr, int i, int i2) {
        if (this.mReleased) {
            return -1;
        }
        synchronized (this.mSyncObj) {
            AudioRecord audioRecord = this.mRecorder;
            if (audioRecord == null) {
                return -1;
            }
            return audioRecord.read(sArr, i, i2);
        }
    }

    @Override // com.miui.common.record.Recorder
    public void release() {
        this.mReleased = true;
        synchronized (this.mSyncObj) {
            AudioRecord audioRecord = this.mRecorder;
            if (audioRecord != null) {
                audioRecord.release();
                this.mRecorder = null;
            }
        }
    }

    @Override // com.miui.common.record.Recorder
    public int getMinPCMBufferSize() {
        return this.mBufferSizeInBytes;
    }

    @Override // com.miui.common.record.Recorder
    public Mp3Encoder getMp3Encoder() {
        initMp3EncoderIfNeed();
        return this.mMp3Encoder;
    }

    @Override // com.miui.common.record.Recorder
    public int startMP3Encode(short[] sArr, short[] sArr2, int i, byte[] bArr, int i2) {
        if (this.mChannelConfig != 16) {
            return -1;
        }
        initMp3EncoderIfNeed();
        int iEncode = this.mMp3Encoder.encode(sArr, sArr2, i, bArr, i2);
        FileOutputStream fileOutputStream = this.mOutputStream;
        if (fileOutputStream != null) {
            try {
                fileOutputStream.write(bArr, 0, iEncode);
            } catch (IOException unused) {
                Logger.INSTANCE.d(TAG, "Fail to write MP3 buffer to a file:" + this.mOutputFilePath);
            }
        } else {
            Logger.INSTANCE.d(TAG, "MP3 OutputFilePath is null, so never to write MP3 buffer to a file.");
        }
        return iEncode;
    }

    @Override // com.miui.common.record.Recorder
    public void stopMP3Encode() {
        FileOutputStream fileOutputStream = this.mOutputStream;
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
