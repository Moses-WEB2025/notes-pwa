package com.xiaomi.aicr.vision.imagesegment;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SegmentResult implements Parcelable {
    public static final Parcelable.Creator<SegmentResult> CREATOR = new Parcelable.Creator<SegmentResult>() { // from class: com.xiaomi.aicr.vision.imagesegment.SegmentResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SegmentResult createFromParcel(Parcel parcel) {
            return new SegmentResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SegmentResult[] newArray(int i) {
            return new SegmentResult[i];
        }
    };
    private List<Bitmap> mBitmaps;
    private List<float[]> mContours;
    private List<float[]> mRects;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<Bitmap> getBitmaps() {
        return this.mBitmaps;
    }

    public void setBitmaps(List<Bitmap> list) {
        this.mBitmaps = list;
    }

    public List<float[]> getRects() {
        return this.mRects;
    }

    public void setRects(List<float[]> list) {
        this.mRects = list;
    }

    public List<float[]> getContours() {
        return this.mContours;
    }

    public void setContours(List<float[]> list) {
        this.mContours = list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mBitmaps);
        parcel.writeList(this.mRects);
        parcel.writeList(this.mContours);
    }

    public void readFromParcel(Parcel parcel) {
        this.mBitmaps = parcel.createTypedArrayList(Bitmap.CREATOR);
        ArrayList arrayList = new ArrayList();
        this.mRects = arrayList;
        parcel.readList(arrayList, float[].class.getClassLoader());
        ArrayList arrayList2 = new ArrayList();
        this.mContours = arrayList2;
        parcel.readList(arrayList2, float[].class.getClassLoader());
    }

    public SegmentResult() {
    }

    protected SegmentResult(Parcel parcel) {
        this.mBitmaps = parcel.createTypedArrayList(Bitmap.CREATOR);
        ArrayList arrayList = new ArrayList();
        this.mRects = arrayList;
        parcel.readList(arrayList, float[].class.getClassLoader());
        ArrayList arrayList2 = new ArrayList();
        this.mContours = arrayList2;
        parcel.readList(arrayList2, float[].class.getClassLoader());
    }
}
