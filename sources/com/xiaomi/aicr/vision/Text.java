package com.xiaomi.aicr.vision;

import android.os.Parcel;
import java.util.Arrays;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public class Text extends VisionObject {
    private String info;

    Text(Parcel parcel, VisionAttribute visionAttribute) {
        super(parcel, visionAttribute);
        this.mAttr = visionAttribute;
        this.info = parcel.readString();
    }

    public Text(String str, float[] fArr, VisionAttribute visionAttribute) {
        this.info = str;
        addBox(fArr);
        this.mAttr = visionAttribute;
    }

    @Override // com.xiaomi.aicr.vision.VisionObject
    public void addBox(float[] fArr) {
        addBox("text", fArr);
    }

    public String getText() {
        return this.info;
    }

    @Override // com.xiaomi.aicr.vision.VisionObject
    public float[] getBox() {
        return getBox("text");
    }

    public String toString() {
        String str = "info: " + this.info;
        Iterator<float[]> it = this.mBoundingBox.values().iterator();
        while (it.hasNext()) {
            str = str + "box: " + Arrays.toString(it.next());
        }
        return str;
    }

    @Override // com.xiaomi.aicr.vision.VisionObject, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.info);
    }
}
