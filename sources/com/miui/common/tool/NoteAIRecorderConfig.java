package com.miui.common.tool;

import android.media.AudioFormat;
import android.os.ParcelFileDescriptor;
import com.android.soundrecorder.ai.base.config.RecordConfig;
import com.android.soundrecorder.ai.base.constants.OutputMimeType;
import java.io.File;

/* JADX INFO: loaded from: classes2.dex */
public class NoteAIRecorderConfig extends RecordConfig {
    private static final String TAG = "NoteAIRecorderConfig";
    private String mAudioOutputFilePath;

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public int getCallerSource() {
        return 2;
    }

    public NoteAIRecorderConfig(String str) {
        Logger.INSTANCE.d(TAG, "NoteAIRecorderConfig: outputFilePath -> " + str);
        this.mAudioOutputFilePath = str;
        setOutputFilePath(str);
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public AudioFormat getAudioFormat() {
        return new AudioFormat.Builder().setSampleRate(16000).setChannelMask(16).setEncoding(2).build();
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public Long getAmplitudeNotifyPeriod() {
        return 160L;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public String getOutputMimeType() {
        return OutputMimeType.MP3;
    }

    @Override // com.android.soundrecorder.ai.base.config.RecordConfig
    public ParcelFileDescriptor getOutputPfd() {
        try {
            File file = new File(this.mAudioOutputFilePath);
            if (!file.exists() && !file.createNewFile()) {
                Logger.INSTANCE.d(TAG, "NoteAIRecorderConfig: " + this.mAudioOutputFilePath + " not exist and can not create!");
            }
            return ParcelFileDescriptor.open(file, 805306368);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "NoteAIRecorderConfig: " + e.getMessage());
            return super.getOutputPfd();
        }
    }

    public String getAudioOutputFilePath() {
        return this.mAudioOutputFilePath;
    }

    public void setAudioOutputFilePath(String str) {
        this.mAudioOutputFilePath = str;
    }
}
