package com.xiaomi.xms.ai.recorder.config;

import android.media.AudioFormat;
import android.os.Build;
import android.os.Environment;
import com.android.soundrecorder.ai.base.config.RecordConfig;
import com.android.soundrecorder.ai.base.constants.OutputMimeType;

/* JADX INFO: loaded from: classes4.dex */
public class InCallConfig extends RecordConfig {
    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public int getAudioSource() {
        return 1;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public int getCallerSource() {
        return 1;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public int getQuality() {
        return 2;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public AudioFormat getAudioFormat() {
        if (Build.DEVICE.equals("omega") || Build.DEVICE.equals("nikel")) {
            return new AudioFormat.Builder().setSampleRate(16000).setChannelMask(12).setEncoding(2).build();
        }
        return new AudioFormat.Builder().setSampleRate(48000).setChannelMask(16).setEncoding(2).build();
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public String getOutputMimeType() {
        return OutputMimeType.MP3;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public int getAudioEncodingBitRate() {
        return (Build.DEVICE.equals("omega") || Build.DEVICE.equals("nikel")) ? 24 : 32;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public String getOutputFilePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/call.mp3";
    }
}
