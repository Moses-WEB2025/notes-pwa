package com.android.soundrecorder.ai.base.config;

import android.media.AudioFormat;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;
import com.android.soundrecorder.ai.base.constants.RecordSource;
import com.android.soundrecorder.ai.base.utils.SensitiveInfoUtils;

/* JADX INFO: loaded from: classes.dex */
public class RecordConfig implements Parcelable {
    public static final Parcelable.Creator<RecordConfig> CREATOR = new Parcelable.Creator<RecordConfig>() { // from class: com.android.soundrecorder.ai.base.config.RecordConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecordConfig createFromParcel(Parcel parcel) {
            RecordConfig recordConfig = new RecordConfig();
            recordConfig.callerSource = parcel.readInt();
            recordConfig.mOutputFormat = parcel.readInt();
            recordConfig.mAudioEncoder = parcel.readInt();
            recordConfig.mAudioSource = parcel.readInt();
            recordConfig.mAudioEncodingBitRate = parcel.readInt();
            recordConfig.mQuality = parcel.readInt();
            recordConfig.mOutputMimeType = parcel.readString();
            recordConfig.mOutputFilePath = parcel.readString();
            recordConfig.mExtraParameters = parcel.readString();
            recordConfig.mAmplitudeNotifyPeriod = parcel.readLong();
            recordConfig.needRecordStereoWhenMonoMode = parcel.readBoolean();
            recordConfig.isHighQuality = parcel.readBoolean();
            recordConfig.mAudioFormat = (AudioFormat) AudioFormat.CREATOR.createFromParcel(parcel);
            try {
                recordConfig.mOutputPfd = (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
            } catch (Exception unused) {
                recordConfig.mOutputPfd = null;
            }
            recordConfig.maxOutputFileSize = parcel.readLong();
            recordConfig.mExtraBundle = parcel.readBundle(Bundle.class.getClassLoader());
            return recordConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecordConfig[] newArray(int i) {
            return new RecordConfig[i];
        }
    };
    private int callerSource = -1;
    private AudioFormat mAudioFormat = null;
    private String mOutputMimeType = null;
    private String mOutputFilePath = null;
    private String mExtraParameters = "";
    private long mAmplitudeNotifyPeriod = 1000;
    private int mOutputFormat = -1;
    private int mAudioEncoder = -1;
    private int mAudioSource = 1;
    private int mAudioEncodingBitRate = 0;
    private int mQuality = 0;
    private ParcelFileDescriptor mOutputPfd = null;
    private boolean needRecordStereoWhenMonoMode = false;
    private long maxOutputFileSize = 0;
    private boolean isHighQuality = true;
    private Bundle mExtraBundle = new Bundle();

    public int getCallerSource() {
        return this.callerSource;
    }

    public String getCallerName() {
        return RecordSource.getSourceName(this.callerSource);
    }

    public void setCallerSource(int i) {
        this.callerSource = i;
    }

    public void setAudioFormat(AudioFormat audioFormat) {
        this.mAudioFormat = audioFormat;
    }

    public AudioFormat getAudioFormat() {
        return this.mAudioFormat;
    }

    public void setOutputMimeType(String str) {
        this.mOutputMimeType = str;
    }

    public String getOutputFileSuffix() {
        String str = this.mOutputMimeType;
        str.hashCode();
        switch (str) {
            case "audio/amr-wb":
                return ".3gp";
            case "audio/wav":
                return ".wav";
            case "audio/3gpp":
                return ".amr";
            case "audio/mpeg":
                return ".mp3";
            default:
                return ".aac";
        }
    }

    public String getOutputMimeType() {
        return this.mOutputMimeType;
    }

    public void setOutputFilePath(String str) {
        this.mOutputFilePath = str;
    }

    public String getOutputFilePath() {
        return this.mOutputFilePath;
    }

    public void setExtraParameters(String str) {
        this.mExtraParameters = str;
    }

    public String getExtraParameters() {
        return this.mExtraParameters;
    }

    public void setAmplitudeNotifyPeriod(long j) {
        this.mAmplitudeNotifyPeriod = j;
    }

    public Long getAmplitudeNotifyPeriod() {
        return Long.valueOf(this.mAmplitudeNotifyPeriod);
    }

    public void setOutputFormat(int i) {
        this.mOutputFormat = i;
    }

    public int getOutputFormat() {
        return this.mOutputFormat;
    }

    public void setAudioEncoder(int i) {
        this.mAudioEncoder = i;
    }

    public int getAudioEncoder() {
        return this.mAudioEncoder;
    }

    public void setAudioSource(int i) {
        this.mAudioSource = i;
    }

    public int getAudioSource() {
        return this.mAudioSource;
    }

    public void setAudioEncodingBitRate(int i) {
        this.mAudioEncodingBitRate = i;
    }

    public int getAudioEncodingBitRate() {
        return this.mAudioEncodingBitRate;
    }

    public void setQuality(int i) {
        this.mQuality = i;
    }

    public int getQuality() {
        return this.mQuality;
    }

    public void setOutputPfd(ParcelFileDescriptor parcelFileDescriptor) {
        this.mOutputPfd = parcelFileDescriptor;
    }

    public ParcelFileDescriptor getOutputPfd() {
        return this.mOutputPfd;
    }

    public Bundle getExtraBundle() {
        return this.mExtraBundle;
    }

    public void setExtraBundle(Bundle bundle) {
        this.mExtraBundle = bundle;
    }

    public void setNeedRecordStereoWhenMonoMode(boolean z) {
        this.needRecordStereoWhenMonoMode = z;
    }

    public Boolean needRecordStereoWhenMonoMode() {
        return Boolean.valueOf(this.needRecordStereoWhenMonoMode);
    }

    public void setMaxFileSize(long j) {
        this.maxOutputFileSize = j;
    }

    public long getMaxFileSize() {
        return this.maxOutputFileSize;
    }

    public void setIsHighQuality(boolean z) {
        this.isHighQuality = z;
    }

    public Boolean isHighQuality() {
        return Boolean.valueOf(this.isHighQuality);
    }

    public String toString() {
        return "RecordConfig{callerSource=" + this.callerSource + ", " + this.mAudioFormat + ", mOutputMimeType='" + this.mOutputMimeType + "', mOutputFilePath='" + SensitiveInfoUtils.sensitiveInfo(this.mOutputFilePath) + "', mExtraParameters='" + this.mExtraParameters + "', mAmplitudeNotifyPeriod=" + this.mAmplitudeNotifyPeriod + ", mOutputFormat=" + this.mOutputFormat + ", mAudioEncoder=" + this.mAudioEncoder + ", mAudioSource=" + this.mAudioSource + ", mAudioEncodingBitRate=" + this.mAudioEncodingBitRate + ", maxOutputFileSize=" + this.maxOutputFileSize + ", mQuality=" + this.mQuality + ", mOutputPfd=" + this.mOutputPfd + ", needRecordStereoWhenMonoMode=" + this.needRecordStereoWhenMonoMode + ", isHighQuality=" + this.isHighQuality + ", mExtraBundle=" + this.mExtraBundle + CoreConstants.CURLY_RIGHT;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mOutputPfd != null ? 1 : 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getCallerSource());
        parcel.writeInt(getOutputFormat());
        parcel.writeInt(getAudioEncoder());
        parcel.writeInt(getAudioSource());
        parcel.writeInt(getAudioEncodingBitRate());
        parcel.writeInt(getQuality());
        parcel.writeString(getOutputMimeType());
        parcel.writeString(getOutputFilePath());
        parcel.writeString(getExtraParameters());
        parcel.writeLong(getAmplitudeNotifyPeriod().longValue());
        parcel.writeBoolean(needRecordStereoWhenMonoMode().booleanValue());
        parcel.writeBoolean(isHighQuality().booleanValue());
        getAudioFormat().writeToParcel(parcel, i);
        if (getOutputPfd() != null) {
            getOutputPfd().writeToParcel(parcel, i);
        }
        parcel.writeLong(getMaxFileSize());
        parcel.writeBundle(getExtraBundle());
    }
}
