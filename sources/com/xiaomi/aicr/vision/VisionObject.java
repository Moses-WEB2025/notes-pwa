package com.xiaomi.aicr.vision;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class VisionObject implements Parcelable {
    public static final Parcelable.Creator<VisionObject> CREATOR = new Parcelable.Creator<VisionObject>() { // from class: com.xiaomi.aicr.vision.VisionObject.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisionObject createFromParcel(Parcel parcel) {
            VisionAttribute visionAttribute = (VisionAttribute) parcel.readParcelable(VisionAttribute.class.getClassLoader());
            if ((visionAttribute.getType() & 16777216) == 16777216) {
                return new Text(parcel, visionAttribute);
            }
            return new VisionObject(parcel, visionAttribute);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisionObject[] newArray(int i) {
            return new VisionObject[i];
        }
    };
    private static final String DEF_TITLE = "object";
    VisionAttribute mAttr;
    final Map<String, float[]> mBoundingBox;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VisionObject() {
        this(new VisionAttribute(-1));
    }

    public VisionObject(VisionAttribute visionAttribute) {
        this.mBoundingBox = new HashMap();
        this.mAttr = visionAttribute;
    }

    protected VisionObject(Parcel parcel, VisionAttribute visionAttribute) {
        this(visionAttribute);
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            float[] fArr = new float[parcel.readInt()];
            parcel.readFloatArray(fArr);
            this.mBoundingBox.put((String) arrayList.get(i), fArr);
        }
    }

    public float[] getBox(String str) {
        return this.mBoundingBox.get(str);
    }

    public float[] getBox() {
        return this.mBoundingBox.get(DEF_TITLE);
    }

    public void addBox(String str, float[] fArr) {
        this.mBoundingBox.put(str, fArr);
    }

    public void addBox(float[] fArr) {
        this.mBoundingBox.put(DEF_TITLE, fArr);
    }

    public void setAttr(VisionAttribute visionAttribute) {
        this.mAttr = visionAttribute;
    }

    public VisionAttribute getAttr() {
        return this.mAttr;
    }

    public Text asText() {
        if (this instanceof Text) {
            return (Text) this;
        }
        return null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mAttr, i);
        parcel.writeStringList(new ArrayList(this.mBoundingBox.keySet()));
        for (float[] fArr : this.mBoundingBox.values()) {
            parcel.writeInt(fArr.length);
            parcel.writeFloatArray(fArr);
        }
    }
}
