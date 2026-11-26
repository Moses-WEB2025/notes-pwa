package com.android.soundrecorder.ai.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class AISentence implements Parcelable {
    public static final Parcelable.Creator<AISentence> CREATOR = new Parcelable.Creator<AISentence>() { // from class: com.android.soundrecorder.ai.base.bean.AISentence.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AISentence createFromParcel(Parcel parcel) {
            AISentence aISentence = new AISentence();
            aISentence.speaker = parcel.readString();
            aISentence.timeLine = parcel.readString();
            aISentence.lyric = parcel.readString();
            aISentence.recordSha1 = parcel.readString();
            aISentence.language = parcel.readString();
            aISentence.content = parcel.readString();
            return aISentence;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AISentence[] newArray(int i) {
            return new AISentence[i];
        }
    };
    private String content;
    private String language;
    private String lyric;
    private String recordSha1;
    private String speaker;
    private String timeLine;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getSpeaker() {
        return this.speaker;
    }

    public void setSpeaker(String str) {
        this.speaker = str;
    }

    public String getTimeLine() {
        return this.timeLine;
    }

    public void setTimeLine(String str) {
        this.timeLine = str;
    }

    public String getLyric() {
        return this.lyric;
    }

    public void setLyric(String str) {
        this.lyric = str;
    }

    public String getRecordSha1() {
        return this.recordSha1;
    }

    public void setRecordSha1(String str) {
        this.recordSha1 = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.speaker);
        parcel.writeString(this.timeLine);
        parcel.writeString(this.lyric);
        parcel.writeString(this.recordSha1);
        parcel.writeString(this.language);
        parcel.writeString(this.content);
    }
}
