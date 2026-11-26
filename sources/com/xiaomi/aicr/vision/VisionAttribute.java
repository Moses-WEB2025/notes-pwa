package com.xiaomi.aicr.vision;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class VisionAttribute implements Parcelable {
    public static final Parcelable.Creator<VisionAttribute> CREATOR = new Parcelable.Creator<VisionAttribute>() { // from class: com.xiaomi.aicr.vision.VisionAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisionAttribute createFromParcel(Parcel parcel) {
            return new VisionAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisionAttribute[] newArray(int i) {
            return new VisionAttribute[i];
        }
    };
    public static final int VISION_TYPE_CLASS_BUILDING = 83886080;
    public static final int VISION_TYPE_CLASS_CAR = 67108864;
    public static final int VISION_TYPE_CLASS_ENTITY = 536870912;
    public static final int VISION_TYPE_CLASS_FACE = 50331648;
    private static final int VISION_TYPE_CLASS_PRIVACY = 100663296;
    public static final int VISION_TYPE_CLASS_TEXT = 16777216;
    public static final int VISION_TYPE_ENTITY_BCN = 536870916;
    public static final int VISION_TYPE_ENTITY_CAR = 536870919;
    public static final int VISION_TYPE_ENTITY_ENO = 536870921;
    public static final int VISION_TYPE_ENTITY_FLN = 536870918;
    public static final int VISION_TYPE_ENTITY_ID = 536870914;
    public static final int VISION_TYPE_ENTITY_LOCATION = 536870913;
    public static final int VISION_TYPE_ENTITY_MAIL = 536870922;
    public static final int VISION_TYPE_ENTITY_ORDER = 536870920;
    public static final int VISION_TYPE_ENTITY_TAX_ID = 536870924;
    public static final int VISION_TYPE_ENTITY_TEL = 536870915;
    public static final int VISION_TYPE_ENTITY_TRAIN = 536870917;
    public static final int VISION_TYPE_ENTITY_URL = 536870923;
    public static final int VISION_TYPE_PRIVATE_INFO = 100663297;
    public static final int VISION_TYPE_TEXT_LINE = 16777217;
    public static final int VISION_TYPE_TEXT_PARAGRAPH = 16777218;
    public static final int VISION_TYPE_UNKNOW = -1;
    private boolean mIsExtractCombinedSubject;
    private boolean mIsMaxAreaAsSingleSubject;
    private boolean mIsPrivicy;
    private Bundle mParams;
    private int mVisionType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VisionAttribute() {
        this(-1);
    }

    public VisionAttribute(int i) {
        this(i, false);
    }

    private VisionAttribute(int i, boolean z) {
        this(i, z, false, false);
    }

    public VisionAttribute(boolean z, boolean z2) {
        this(-1, false, z, z2);
    }

    private VisionAttribute(int i, boolean z, boolean z2, boolean z3) {
        this.mVisionType = i;
        this.mIsPrivicy = z;
        this.mParams = new Bundle();
        this.mIsExtractCombinedSubject = z2;
        this.mIsMaxAreaAsSingleSubject = z3;
    }

    public static class Builder {
        private boolean mIsExtractCombinedSubject;
        private boolean mIsPrivicy;
        private int mVisionType;
        private boolean misMaxAreaAsSingleSubject;

        public VisionAttribute build() {
            return new VisionAttribute(this.mVisionType, this.mIsPrivicy, this.mIsExtractCombinedSubject, this.misMaxAreaAsSingleSubject);
        }

        public Builder setPrivicy(boolean z) {
            this.mIsPrivicy = z;
            return this;
        }

        public Builder setType(int i) {
            this.mVisionType = i;
            return this;
        }

        public Builder setIsExtractCombinedSubject(boolean z) {
            this.mIsExtractCombinedSubject = z;
            return this;
        }

        public Builder setIsMaxAreaAsSingleSubject(boolean z) {
            this.misMaxAreaAsSingleSubject = z;
            return this;
        }
    }

    public int getType() {
        return this.mVisionType;
    }

    public void setPrivicy(boolean z) {
        this.mIsPrivicy = z;
    }

    public boolean isPrivicy() {
        return this.mIsPrivicy;
    }

    public void putString(String str, String str2) {
        this.mParams.putString(str, str2);
    }

    public String getString(String str) {
        return this.mParams.getString(str);
    }

    public boolean isExtractCombinedSubject() {
        return this.mIsExtractCombinedSubject;
    }

    public boolean isMaxAreaAsSingleSubject() {
        return this.mIsMaxAreaAsSingleSubject;
    }

    protected VisionAttribute(Parcel parcel) {
        this.mVisionType = parcel.readInt();
        this.mIsPrivicy = parcel.readBoolean();
        this.mParams = parcel.readBundle();
        this.mIsExtractCombinedSubject = parcel.readBoolean();
        this.mIsMaxAreaAsSingleSubject = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mVisionType);
        parcel.writeBoolean(this.mIsPrivicy);
        parcel.writeBundle(this.mParams);
        parcel.writeBoolean(this.mIsExtractCombinedSubject);
        parcel.writeBoolean(this.mIsMaxAreaAsSingleSubject);
    }
}
