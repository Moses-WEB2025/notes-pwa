package com.miui.pad.feature.notes.meeting;

import android.media.AudioRecord;
import android.os.Build;
import com.miui.common.record.Recorder;
import com.miui.common.tool.Utils;
import com.miui.notes.base.utils.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import miuix.media.Mp3Encoder;

/* JADX INFO: loaded from: classes4.dex */
public class MeetingRecorder extends Recorder {
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
    private final int CHANNEL_INDEX_MASK_4 = 60;
    private final Object mSyncObj = new Object();

    @Override // com.miui.common.record.Recorder
    public int getMP3BufferSize(int i) {
        return (int) ((((double) i) * 1.25d) + 7200.0d);
    }

    public MeetingRecorder() {
        this.mRecorder = null;
        try {
            this.mRecorder = new AudioRecord(this.mAudioSource, this.mSampleRateInHz, this.mChannelConfig, this.mAudioFormat, this.mBufferSizeInBytes);
        } catch (IllegalArgumentException e) {
            this.mErrorCode = 1;
            Logger.INSTANCE.e(TAG, "IllegalArgumentException,exception:" + e.getMessage());
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
        if (Build.DEVICE.equals("nabu") || Build.DEVICE.equals("elish") || Build.DEVICE.equals("enuma")) {
            this.mAudioSource = 9;
        }
        this.mSampleRateInHz = 16000;
        this.mChannelConfig = 12;
        this.mAudioFormat = 2;
        if (Build.DEVICE.equals("elish") || Build.DEVICE.equals("enuma")) {
            if (Utils.isSupport4micMode()) {
                this.mChannelCount = this.mChannelConfig == 12 ? 4 : 1;
                this.mChannelConfig = 60;
            } else {
                this.mChannelCount = this.mChannelConfig == 12 ? 2 : 1;
            }
        } else {
            this.mChannelCount = this.mChannelConfig == 12 ? 2 : 1;
        }
        this.mBufferSizeInBytes = AudioRecord.getMinBufferSize(this.mSampleRateInHz, this.mChannelConfig, this.mAudioFormat);
        this.mOutMode = this.mChannelConfig == 12 ? 0 : 3;
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
        int iEncodeInterleaved;
        if (this.mChannelConfig == 16) {
            initMp3EncoderIfNeed();
            iEncodeInterleaved = this.mMp3Encoder.encode(sArr, sArr2, i, bArr, i2);
            FileOutputStream fileOutputStream = this.mOutputStream;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.write(bArr, 0, iEncodeInterleaved);
                } catch (IOException unused) {
                    Logger.INSTANCE.d(TAG, "Fail to write MP3 buffer to a file:" + this.mOutputFilePath);
                }
            } else {
                Logger.INSTANCE.d(TAG, "MP3 OutputFilePath is null, so never to write MP3 buffer to a file.");
            }
        } else if (this.mChannelConfig == 12) {
            initMp3EncoderIfNeed();
            iEncodeInterleaved = this.mMp3Encoder.encodeInterleaved(sArr, i / 2, bArr, i2);
            FileOutputStream fileOutputStream2 = this.mOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.write(bArr, 0, iEncodeInterleaved);
                } catch (IOException unused2) {
                    Logger.INSTANCE.d(TAG, "Fail to write MP3 buffer to a file:" + this.mOutputFilePath);
                }
            } else {
                Logger.INSTANCE.d(TAG, "MP3 OutputFilePath is null, so never to write MP3 buffer to a file.");
            }
        } else {
            if (this.mChannelConfig != 60) {
                return -1;
            }
            initMp3EncoderIfNeed();
            iEncodeInterleaved = this.mMp3Encoder.encodeInterleaved(sArr, i / 4, bArr, i2);
            FileOutputStream fileOutputStream3 = this.mOutputStream;
            if (fileOutputStream3 != null) {
                try {
                    fileOutputStream3.write(bArr, 0, iEncodeInterleaved);
                } catch (IOException unused3) {
                    Logger.INSTANCE.d(TAG, "Fail to write MP3 buffer to a file:" + this.mOutputFilePath);
                }
            } else {
                Logger.INSTANCE.d(TAG, "MP3 OutputFilePath is null, so never to write MP3 buffer to a file.");
            }
        }
        return iEncodeInterleaved;
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
