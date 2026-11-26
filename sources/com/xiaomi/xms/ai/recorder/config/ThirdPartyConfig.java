package com.xiaomi.xms.ai.recorder.config;

import android.media.AudioFormat;
import android.os.Environment;
import com.android.soundrecorder.ai.base.config.RecordConfig;
import com.android.soundrecorder.ai.base.constants.OutputMimeType;

/* JADX INFO: loaded from: classes4.dex */
public class ThirdPartyConfig extends RecordConfig {
    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public int getCallerSource() {
        return 50;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public AudioFormat getAudioFormat() {
        return new AudioFormat.Builder().setSampleRate(16000).setChannelMask(16).setEncoding(2).build();
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public String getOutputMimeType() {
        return OutputMimeType.AAC;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public String getOutputFilePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/test333.aac";
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public Long getAmplitudeNotifyPeriod() {
        return 1000L;
    }
}
