package com.xiaomi.aicr.nlp;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class Entity implements Parcelable {
    public static final Parcelable.Creator<Entity> CREATOR = new Parcelable.Creator<Entity>() { // from class: com.xiaomi.aicr.nlp.Entity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Entity createFromParcel(Parcel parcel) {
            return new Entity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Entity[] newArray(int i) {
            return new Entity[i];
        }
    };
    public static final int NER_TYPE_BCN = 4;
    public static final int NER_TYPE_CAR = 7;
    public static final int NER_TYPE_ENO = 9;
    public static final int NER_TYPE_FLN = 6;
    public static final int NER_TYPE_ID = 2;
    public static final int NER_TYPE_LOCATION = 1;
    public static final int NER_TYPE_MAIL = 10;
    public static final int NER_TYPE_ORDER = 8;
    public static final int NER_TYPE_TAX_ID = 12;
    public static final int NER_TYPE_TEL = 3;
    public static final int NER_TYPE_TRAIN = 5;
    public static final int NER_TYPE_UNKNOW = 0;
    public static final int NER_TYPE_URL = 11;
    public int end;
    public int mType;
    public int start;
    public String str;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Entity(String str, int i, int i2, int i3) {
        this.str = str;
        this.mType = i;
        this.start = i2;
        this.end = i3;
    }

    protected Entity(Parcel parcel) {
        this.start = -1;
        this.end = -1;
        this.str = parcel.readString();
        this.mType = parcel.readInt();
        this.start = parcel.readInt();
        this.end = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.str);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.start);
        parcel.writeInt(this.end);
    }

    public String toString() {
        return "text: " + this.str + ",mType:" + this.mType + ",start:" + this.start + ",end" + this.end;
    }
}
