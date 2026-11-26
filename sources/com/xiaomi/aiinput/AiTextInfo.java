package com.xiaomi.aiinput;

import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes4.dex */
public class AiTextInfo implements Parcelable {
    public static final Parcelable.Creator<AiTextInfo> CREATOR = new Parcelable.Creator<AiTextInfo>() { // from class: com.xiaomi.aiinput.AiTextInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AiTextInfo createFromParcel(Parcel parcel) {
            return new AiTextInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AiTextInfo[] newArray(int i) {
            return new AiTextInfo[i];
        }
    };
    String mContextName;
    int mCursorIndex;
    boolean mHasSelection;
    String mPkgName;
    String mText;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AiTextInfo(String str, boolean z, int i, String str2, String str3) {
        this.mText = str;
        this.mHasSelection = z;
        this.mCursorIndex = i;
        this.mPkgName = str2;
        this.mContextName = str3;
    }

    protected AiTextInfo(Parcel parcel) {
        this.mText = parcel.readString();
        this.mHasSelection = parcel.readBoolean();
        this.mCursorIndex = parcel.readInt();
        this.mPkgName = parcel.readString();
        this.mContextName = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mText);
        parcel.writeBoolean(this.mHasSelection);
        parcel.writeInt(this.mCursorIndex);
        parcel.writeString(this.mPkgName);
        parcel.writeString(this.mContextName);
    }

    public void readFromParcel(Parcel parcel) {
        this.mText = parcel.readString();
        this.mHasSelection = parcel.readBoolean();
        this.mCursorIndex = parcel.readInt();
        this.mPkgName = parcel.readString();
        this.mContextName = parcel.readString();
    }

    public String getText() {
        return this.mText;
    }

    public boolean hasSelection() {
        return this.mHasSelection;
    }

    public int getCursorIndex() {
        return this.mCursorIndex;
    }

    public String getPackageName() {
        return this.mPkgName;
    }

    public String getContextName() {
        return this.mContextName;
    }

    public String toString() {
        return "AiTextInfo{text=" + this.mText + ", selected=" + this.mHasSelection + ", cursorIndex=" + this.mCursorIndex + ", pkg=" + this.mPkgName + ", context=" + this.mContextName + CoreConstants.CURLY_RIGHT;
    }
}
