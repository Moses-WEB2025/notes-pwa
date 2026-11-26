package com.xiaomi.aicr.barcode;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class BarcodeBean implements Parcelable {
    public static final Parcelable.Creator<BarcodeBean> CREATOR = new Parcelable.Creator<BarcodeBean>() { // from class: com.xiaomi.aicr.barcode.BarcodeBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BarcodeBean createFromParcel(Parcel parcel) {
            return new BarcodeBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BarcodeBean[] newArray(int i) {
            return new BarcodeBean[i];
        }
    };
    private int mBarcodeFormat;
    private Rect mBoundingBox;
    private Point[] mCornerPoints;
    private String mRawValue;
    private String mValueType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BarcodeBean(int i, String str, String str2, Point[] pointArr, Rect rect) {
        this.mBarcodeFormat = i;
        this.mValueType = str;
        this.mRawValue = str2;
        this.mCornerPoints = pointArr;
        this.mBoundingBox = rect;
    }

    protected BarcodeBean(Parcel parcel) {
        this.mValueType = parcel.readString();
        this.mRawValue = parcel.readString();
        int i = parcel.readInt();
        this.mCornerPoints = new Point[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mCornerPoints[i2] = (Point) parcel.readParcelable(Point.class.getClassLoader());
        }
        this.mBoundingBox = (Rect) parcel.readParcelable(Rect.class.getClassLoader());
        this.mBarcodeFormat = parcel.readInt();
    }

    public String getValueType() {
        return this.mValueType;
    }

    public void setValueType(String str) {
        this.mValueType = str;
    }

    public String getRawValue() {
        return this.mRawValue;
    }

    public void setRawValue(String str) {
        this.mRawValue = str;
    }

    public Point[] getCornerPoints() {
        return this.mCornerPoints;
    }

    public void setCornerPoints(Point[] pointArr) {
        this.mCornerPoints = pointArr;
    }

    public Rect getBoundingBox() {
        return this.mBoundingBox;
    }

    public void setBoundingBox(Rect rect) {
        this.mBoundingBox = rect;
    }

    public int getBarcodeFormat() {
        return this.mBarcodeFormat;
    }

    public void setBarcodeFormat(int i) {
        this.mBarcodeFormat = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mValueType);
        parcel.writeString(this.mRawValue);
        parcel.writeInt(this.mCornerPoints.length);
        for (Point point : this.mCornerPoints) {
            parcel.writeParcelable(point, i);
        }
        parcel.writeParcelable(this.mBoundingBox, i);
        parcel.writeInt(this.mBarcodeFormat);
    }
}
