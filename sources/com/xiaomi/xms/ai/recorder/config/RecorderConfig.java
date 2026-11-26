package com.xiaomi.xms.ai.recorder.config;

import android.media.AudioFormat;
import com.android.soundrecorder.ai.base.config.RecordConfig;
import com.android.soundrecorder.ai.base.constants.OutputMimeType;
import defpackage.R2;

/* JADX INFO: loaded from: classes4.dex */
public class RecorderConfig extends RecordConfig {
    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public int getAudioEncoder() {
        return 3;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public int getAudioEncodingBitRate() {
        return R2.dimen.global_ai_text_panel_card_margin_vertical;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public int getCallerSource() {
        return 0;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public int getOutputFormat() {
        return 1;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public AudioFormat getAudioFormat() {
        return new AudioFormat.Builder().setSampleRate(R2.dimen.miuix_appcompat_action_bar_subtitle_bg_end_margin).setChannelMask(16).setEncoding(2).build();
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public String getOutputMimeType() {
        return OutputMimeType.AMR;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public Boolean needRecordStereoWhenMonoMode() {
        return true;
    }
}
