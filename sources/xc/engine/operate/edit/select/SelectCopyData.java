package xc.engine.operate.edit.select;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.data.curve.CurvePointListOperator;
import xc.engine.data.curve.ICurvePoint;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.data.shape.ShapeProp;
import xc.engine.data.shape.ShapeType;
import xc.engine.data.shape.StrokeType;
import xc.engine.operate.touch.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
public class SelectCopyData {
    public final long mCopyTime;
    public final List<BaseData> mDataList;
    public float mDrawRatio;
    public PenEngine mPenEngin;
    public final RectF mRatioRectF;
    public final SelectTouchPoints mSelectTouchPoints;
    public float mVisibleTop;

    private int getDataState(int i) {
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        return i == 3 ? 3 : 1;
    }

    public SelectCopyData(PenEngine penEngine) {
        this.mPenEngin = penEngine;
        this.mDataList = new ArrayList();
        this.mRatioRectF = new RectF();
        this.mSelectTouchPoints = new SelectTouchPoints();
        this.mCopyTime = System.currentTimeMillis();
        this.mVisibleTop = 0.0f;
    }

    public SelectCopyData(SelectCopyData selectCopyData, PenEngine penEngine) {
        this.mVisibleTop = 0.0f;
        g();
        this.mDataList = new ArrayList();
        this.mRatioRectF = new RectF();
        SelectTouchPoints selectTouchPoints = new SelectTouchPoints();
        this.mSelectTouchPoints = selectTouchPoints;
        this.mCopyTime = System.currentTimeMillis();
        this.mPenEngin = penEngine;
        this.mDrawRatio = selectCopyData.getDrawRatio();
        setRatioRectF(selectCopyData.getRatioRectF());
        selectTouchPoints.copyTouchPoints(selectCopyData.getSelectPoints());
        this.mVisibleTop = selectCopyData.getVisibleTop();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < selectCopyData.getDataList().size(); i++) {
            BaseData baseDataCopyData = selectCopyData.copyData(selectCopyData.getDataList().get(i));
            if (baseDataCopyData != null) {
                arrayList.add(baseDataCopyData);
            }
        }
        this.mDataList.addAll(arrayList);
    }

    public void g() {
        for (int i = 0; i < this.mDataList.size(); i++) {
            this.mDataList.get(i).releasePointList();
        }
    }

    public static SelectCopyData readDataFromBytes(byte[] bArr, PenEngine penEngine) throws IOException {
        int i;
        DataInputStream dataInputStream;
        int i2;
        short s;
        DataCurve dataCurve;
        SelectCopyData selectCopyData = new SelectCopyData(penEngine);
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        dataInputStream2.readLong();
        selectCopyData.mDrawRatio = dataInputStream2.readFloat();
        RectF rectF = new RectF();
        rectF.left = dataInputStream2.readFloat();
        rectF.top = dataInputStream2.readFloat();
        rectF.right = dataInputStream2.readFloat();
        rectF.bottom = dataInputStream2.readFloat();
        selectCopyData.mRatioRectF.set(rectF);
        int i3 = dataInputStream2.readInt();
        int i4 = 0;
        short s2 = 1;
        if (i3 > 0) {
            byte[] bArr2 = new byte[i3];
            dataInputStream2.read(bArr2, 0, i3);
            Path path = new Path();
            ArrayList<TouchPoint> arrayList = new ArrayList();
            DataInputStream dataInputStream3 = new DataInputStream(new ByteArrayInputStream(bArr2));
            boolean z = true;
            while (dataInputStream3.available() > 0) {
                float f = dataInputStream3.readFloat();
                float f2 = dataInputStream3.readFloat();
                arrayList.add(new TouchPoint(f, f2, System.currentTimeMillis()));
                if (z) {
                    path.moveTo(f, f2);
                    z = false;
                } else {
                    path.lineTo(f, f2);
                }
            }
            path.close();
            SelectTouchPoints selectTouchPoints = selectCopyData.mSelectTouchPoints;
            selectTouchPoints.mPath = new Path(path);
            selectTouchPoints.mTouchPoints.clear();
            for (TouchPoint touchPoint : arrayList) {
                selectTouchPoints.mTouchPoints.add(new TouchPoint(touchPoint.getX(), touchPoint.getY(), touchPoint.getTime()));
            }
        }
        selectCopyData.mVisibleTop = dataInputStream2.readFloat();
        int i5 = dataInputStream2.readInt();
        ArrayList arrayList2 = new ArrayList();
        int i6 = 0;
        while (i6 < i5) {
            short s3 = dataInputStream2.readShort();
            if (s3 == s2) {
                int i7 = dataInputStream2.readInt();
                int i8 = dataInputStream2.readInt();
                float f3 = dataInputStream2.readFloat();
                int i9 = dataInputStream2.readInt();
                int i10 = dataInputStream2.readInt();
                CurveProp curveProp = new CurveProp(PenType.getPenType(i9), i7, f3, i8);
                curveProp.setPenColorWheel(i10);
                curveProp.setSensitivity(dataInputStream2.readFloat());
                curveProp.setTipSize(dataInputStream2.readFloat());
                DataCurve dataCurve2 = new DataCurve(curveProp, 0L);
                dataCurve2.mId = dataInputStream2.readInt();
                dataCurve2.mCount = dataInputStream2.readInt();
                dataCurve2.mTableFlag = dataInputStream2.readShort();
                dataCurve2.mDrawRatio = dataInputStream2.readFloat();
                dataCurve2.mScaleRate = dataInputStream2.readFloat();
                dataCurve2.mDataState = selectCopyData.getDataState(dataInputStream2.readInt());
                dataCurve2.mCurveType = dataInputStream2.readShort();
                dataCurve2.mTime = dataInputStream2.readLong();
                if (dataInputStream2.readBoolean()) {
                    dataCurve2.mStartPoint = new PointF(dataInputStream2.readFloat(), dataInputStream2.readFloat());
                }
                dataCurve2.mRotateDegrees = dataInputStream2.readFloat();
                dataCurve2.mPenTypeValue = dataInputStream2.readInt();
                dataCurve2.mItemPositionIndex = dataInputStream2.readInt();
                int i11 = dataInputStream2.readInt();
                float[] fArr = new float[i11];
                float[] fArr2 = new float[i11];
                float[] fArr3 = new float[i11];
                float[] fArr4 = new float[i11];
                float[] fArr5 = new float[i11];
                float[] fArr6 = new float[i11];
                float[] fArr7 = new float[i11];
                i2 = i4;
                long[] jArr = new long[i11];
                s = s2;
                short[] sArr = new short[i11];
                i = i5;
                int i12 = i2;
                while (i12 < i11) {
                    fArr[i12] = dataInputStream2.readFloat();
                    fArr2[i12] = dataInputStream2.readFloat();
                    fArr3[i12] = dataInputStream2.readFloat();
                    int i13 = i12;
                    DataInputStream dataInputStream4 = dataInputStream2;
                    jArr[i13] = dataInputStream2.readInt();
                    fArr4[i13] = dataInputStream4.readFloat();
                    fArr5[i13] = dataInputStream4.readFloat();
                    fArr6[i13] = dataInputStream4.readFloat();
                    fArr7[i13] = dataInputStream4.readFloat();
                    sArr[i13] = s;
                    i12 = i13 + 1;
                    dataInputStream2 = dataInputStream4;
                }
                dataInputStream = dataInputStream2;
                dataCurve2.insertPoints(dataCurve2.mSavePoints, i11, fArr, fArr2, fArr3, fArr4, jArr, fArr5, fArr6, fArr7, sArr);
                int i14 = dataInputStream.readInt();
                float[] fArr8 = new float[i14];
                float[] fArr9 = new float[i14];
                float[] fArr10 = new float[i14];
                float[] fArr11 = new float[i14];
                float[] fArr12 = new float[i14];
                float[] fArr13 = new float[i14];
                float[] fArr14 = new float[i14];
                long[] jArr2 = new long[i14];
                short[] sArr2 = new short[i14];
                int i15 = i2;
                while (i15 < i14) {
                    fArr8[i15] = dataInputStream.readFloat();
                    fArr9[i15] = dataInputStream.readFloat();
                    fArr10[i15] = dataInputStream.readFloat();
                    jArr2[i15] = dataInputStream.readInt();
                    fArr11[i15] = dataInputStream.readFloat();
                    fArr12[i15] = dataInputStream.readFloat();
                    fArr13[i15] = dataInputStream.readFloat();
                    fArr14[i15] = dataInputStream.readFloat();
                    sArr2[i15] = s;
                    i15++;
                    i14 = i14;
                    fArr8 = fArr8;
                }
                dataCurve2.insertPoints(dataCurve2.mScaledPoints, i14, fArr8, fArr9, fArr10, fArr11, jArr2, fArr12, fArr13, fArr14, sArr2);
                dataCurve2.calculateBoundingRect();
                dataCurve = dataCurve2;
            } else {
                i = i5;
                dataInputStream = dataInputStream2;
                i2 = i4;
                s = s2;
                if (s3 == 28) {
                    byte b = dataInputStream.readByte();
                    byte b2 = dataInputStream.readByte();
                    int i16 = dataInputStream.readInt();
                    int i17 = dataInputStream.readInt();
                    float f4 = dataInputStream.readFloat();
                    int i18 = dataInputStream.readInt();
                    int i19 = dataInputStream.readInt();
                    int i20 = dataInputStream.readInt();
                    int i21 = dataInputStream.readInt();
                    ShapeProp shapeProp = new ShapeProp(i16, f4, i17);
                    shapeProp.setShapeType(ShapeType.getShapeType(b));
                    shapeProp.setStrokeType(StrokeType.getStrokeType(b2));
                    shapeProp.setPenColorWheel(i18);
                    shapeProp.setFillColor(i19);
                    shapeProp.setFillAlpha(i20);
                    shapeProp.setFillColorWheel(i21);
                    float f5 = dataInputStream.readFloat();
                    float f6 = dataInputStream.readFloat();
                    shapeProp.setSensitivity(f5);
                    shapeProp.setTipSize(f6);
                    DataShape dataShape = new DataShape(shapeProp, 0L);
                    dataShape.mId = dataInputStream.readInt();
                    dataShape.mCount = dataInputStream.readInt();
                    dataShape.mTableFlag = dataInputStream.readShort();
                    dataShape.mDrawRatio = dataInputStream.readFloat();
                    dataShape.mDataState = selectCopyData.getDataState(dataInputStream.readInt());
                    dataShape.mCurveType = dataInputStream.readShort();
                    dataShape.mTime = dataInputStream.readLong();
                    dataShape.mRotateDegrees = dataInputStream.readFloat();
                    dataShape.mPenTypeValue = dataInputStream.readInt();
                    dataShape.mItemPositionIndex = dataInputStream.readInt();
                    dataShape.setFilletPointF(dataInputStream.readFloat(), dataInputStream.readFloat());
                    dataShape.mAnchorPoint4[i2] = dataInputStream.readFloat();
                    dataShape.mAnchorPoint4[s] = dataInputStream.readFloat();
                    dataShape.mAnchorPoint4[2] = dataInputStream.readFloat();
                    dataShape.mAnchorPoint4[3] = dataInputStream.readFloat();
                    int i22 = dataInputStream.readInt();
                    float[] fArr15 = new float[i22];
                    float[] fArr16 = new float[i22];
                    float[] fArr17 = new float[i22];
                    for (int i23 = i2; i23 < i22; i23++) {
                        fArr15[i23] = dataInputStream.readFloat();
                        fArr16[i23] = dataInputStream.readFloat();
                        fArr17[i23] = dataInputStream.readFloat();
                    }
                    synchronized (dataShape.mLockObj) {
                        CurvePointListOperator.insertPoints(dataShape.mSavePoints, i22, fArr15, fArr16, fArr17, (float[]) null, (long[]) null, (float[]) null, (float[]) null, (float[]) null, (short[]) null);
                    }
                    dataShape.calculateBoundingRect();
                    dataCurve = dataShape;
                } else {
                    dataCurve = null;
                }
            }
            if (dataCurve != null) {
                arrayList2.add(dataCurve);
            }
            i6++;
            i4 = i2;
            s2 = s;
            i5 = i;
            dataInputStream2 = dataInputStream;
        }
        selectCopyData.mDataList.addAll(arrayList2);
        return selectCopyData;
    }

    private float getDrawRatio() {
        return this.mDrawRatio;
    }

    public List<BaseData> getDataList() {
        return new ArrayList(this.mDataList);
    }

    public RectF getRatioRectF() {
        return new RectF(this.mRatioRectF);
    }

    private void setRatioRectF(RectF rectF) {
        this.mRatioRectF.set(rectF);
    }

    public SelectTouchPoints getSelectPoints() {
        SelectTouchPoints selectTouchPoints = new SelectTouchPoints();
        selectTouchPoints.copyTouchPoints(this.mSelectTouchPoints);
        return selectTouchPoints;
    }

    private float getVisibleTop() {
        return this.mVisibleTop;
    }

    private BaseData copyData(BaseData baseData) {
        DataCurve dataCurve;
        if (baseData.getType() != 1 && baseData.getType() != 28) {
            return null;
        }
        DataCurve dataCurve2 = (DataCurve) baseData;
        if (baseData.getType() == 28) {
            DataShape dataShape = (DataShape) dataCurve2;
            DataShape dataShape2 = new DataShape(dataShape.mShapeProp, dataCurve2.mTime);
            PointF pointF = dataShape.mFilletPointF;
            dataShape2.setFilletPointF(pointF.x, pointF.y);
            System.arraycopy(dataShape.mAnchorPoint4, 0, dataShape2.mAnchorPoint4, 0, 4);
            dataCurve = dataShape2;
        } else {
            dataCurve = new DataCurve(dataCurve2.getCurveProp(), dataCurve2.mTime);
        }
        DataCurve dataCurve3 = dataCurve;
        dataCurve3.mId = dataCurve2.mId;
        dataCurve3.mCount = dataCurve2.mCount;
        dataCurve3.mTableFlag = dataCurve2.mTableFlag;
        dataCurve3.mDrawRatio = dataCurve2.mDrawRatio;
        dataCurve3.mDataState = 1;
        dataCurve3.mCurveType = dataCurve2.mCurveType;
        if (dataCurve2.mStartPoint != null) {
            dataCurve3.mStartPoint = new PointF(dataCurve2.mStartPoint.x, dataCurve2.mStartPoint.y);
        }
        dataCurve3.mRotateDegrees = dataCurve2.mRotateDegrees;
        dataCurve3.mPenTypeValue = dataCurve2.mPenTypeValue;
        dataCurve3.mItemPositionIndex = dataCurve2.mItemPositionIndex;
        ICurvePointList curvePointList = dataCurve3.getCurvePointList(dataCurve3.mSavePoints);
        int size = curvePointList.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        float[] fArr3 = new float[size];
        float[] fArr4 = new float[size];
        float[] fArr5 = new float[size];
        float[] fArr6 = new float[size];
        float[] fArr7 = new float[size];
        long[] jArr = new long[size];
        short[] sArr = new short[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = curvePointList.get(i).getX();
            fArr2[i] = curvePointList.get(i).getY();
            fArr3[i] = curvePointList.get(i).getPressure();
            jArr[i] = curvePointList.get(i).getTime();
            fArr4[i] = curvePointList.get(i).getTilt();
            fArr5[i] = curvePointList.get(i).getOrientation();
            fArr6[i] = curvePointList.get(i).getV();
            fArr7[i] = curvePointList.get(i).getRotateAngle();
            sArr[i] = curvePointList.get(i).getState();
        }
        dataCurve3.insertPoints(dataCurve3.mSavePoints, size, fArr, fArr2, fArr3, fArr4, jArr, fArr5, fArr6, fArr7, sArr);
        dataCurve3.calculateBoundingRect();
        return dataCurve3;
    }

    public static void writeDataToStream(BaseData baseData, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(baseData.getType());
        int i = 0;
        if (baseData.getType() == 1) {
            DataCurve dataCurve = (DataCurve) baseData;
            dataOutputStream.writeInt(dataCurve.getCurveProp().getPenColor());
            dataOutputStream.writeInt(dataCurve.getCurveProp().getPenAlpha());
            dataOutputStream.writeFloat(dataCurve.getCurveProp().getPenSize());
            dataOutputStream.writeInt(dataCurve.getCurveProp().getPenType().getValue());
            dataOutputStream.writeInt(dataCurve.getCurveProp().getPenColorWheel());
            dataOutputStream.writeFloat(dataCurve.getCurveProp().getSensitivity());
            dataOutputStream.writeFloat(dataCurve.getCurveProp().getTipSize());
            dataOutputStream.writeInt(dataCurve.mId);
            dataOutputStream.writeInt(dataCurve.mCount);
            dataOutputStream.writeShort(dataCurve.mTableFlag);
            dataOutputStream.writeFloat(dataCurve.mDrawRatio);
            dataOutputStream.writeFloat(dataCurve.mScaleRate);
            dataOutputStream.writeInt(dataCurve.mDataState);
            dataOutputStream.writeShort(dataCurve.mCurveType);
            dataOutputStream.writeLong(dataCurve.mTime);
            if (dataCurve.mStartPoint != null) {
                dataOutputStream.writeBoolean(true);
                dataOutputStream.writeFloat(dataCurve.mStartPoint.x);
                dataOutputStream.writeFloat(dataCurve.mStartPoint.y);
            } else {
                dataOutputStream.writeBoolean(false);
            }
            dataOutputStream.writeFloat(dataCurve.mRotateDegrees);
            dataOutputStream.writeInt(dataCurve.mPenTypeValue);
            dataOutputStream.writeInt(dataCurve.mItemPositionIndex);
            ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
            dataOutputStream.writeInt(curvePointList.size());
            for (int i2 = 0; i2 < curvePointList.size(); i2++) {
                ICurvePoint iCurvePoint = curvePointList.get(i2);
                dataOutputStream.writeFloat(iCurvePoint.getX());
                dataOutputStream.writeFloat(iCurvePoint.getY());
                dataOutputStream.writeFloat(iCurvePoint.getPressure());
                dataOutputStream.writeInt(iCurvePoint.getTime());
                dataOutputStream.writeFloat(iCurvePoint.getTilt());
                dataOutputStream.writeFloat(iCurvePoint.getOrientation());
                dataOutputStream.writeFloat(iCurvePoint.getV());
                dataOutputStream.writeFloat(iCurvePoint.getRotateAngle());
            }
            ICurvePointList curvePointList2 = dataCurve.getCurvePointList(dataCurve.mScaledPoints);
            dataOutputStream.writeInt(curvePointList2.size());
            while (i < curvePointList2.size()) {
                ICurvePoint iCurvePoint2 = curvePointList2.get(i);
                dataOutputStream.writeFloat(iCurvePoint2.getX());
                dataOutputStream.writeFloat(iCurvePoint2.getY());
                dataOutputStream.writeFloat(iCurvePoint2.getPressure());
                dataOutputStream.writeInt(iCurvePoint2.getTime());
                dataOutputStream.writeFloat(iCurvePoint2.getTilt());
                dataOutputStream.writeFloat(iCurvePoint2.getOrientation());
                dataOutputStream.writeFloat(iCurvePoint2.getV());
                dataOutputStream.writeFloat(iCurvePoint2.getRotateAngle());
                i++;
            }
            return;
        }
        if (baseData.getType() == 28) {
            DataShape dataShape = (DataShape) baseData;
            dataOutputStream.writeByte(dataShape.mShapeProp.getShapeType());
            dataOutputStream.writeByte(dataShape.mShapeProp.getStrokeType());
            dataOutputStream.writeInt(dataShape.mShapeProp.getPenColor());
            dataOutputStream.writeInt(dataShape.mShapeProp.getPenAlpha());
            dataOutputStream.writeFloat(dataShape.mShapeProp.getPenSize());
            dataOutputStream.writeInt(dataShape.mShapeProp.getPenColorWheel());
            dataOutputStream.writeInt(dataShape.mShapeProp.getFillColor());
            dataOutputStream.writeInt(dataShape.mShapeProp.getFillAlpha());
            dataOutputStream.writeInt(dataShape.mShapeProp.getFillColorWheel());
            dataOutputStream.writeFloat(dataShape.mShapeProp.getSensitivity());
            dataOutputStream.writeFloat(dataShape.mShapeProp.getTipSize());
            dataOutputStream.writeInt(dataShape.mId);
            dataOutputStream.writeInt(dataShape.mCount);
            dataOutputStream.writeShort(dataShape.mTableFlag);
            dataOutputStream.writeFloat(dataShape.mDrawRatio);
            dataOutputStream.writeInt(dataShape.mDataState);
            dataOutputStream.writeShort(dataShape.mCurveType);
            dataOutputStream.writeLong(dataShape.mTime);
            dataOutputStream.writeFloat(dataShape.mRotateDegrees);
            dataOutputStream.writeInt(dataShape.mPenTypeValue);
            dataOutputStream.writeInt(dataShape.mItemPositionIndex);
            dataOutputStream.writeFloat(dataShape.mFilletPointF.x);
            dataOutputStream.writeFloat(dataShape.mFilletPointF.y);
            dataOutputStream.writeFloat(dataShape.mAnchorPoint4[0]);
            dataOutputStream.writeFloat(dataShape.mAnchorPoint4[1]);
            dataOutputStream.writeFloat(dataShape.mAnchorPoint4[2]);
            dataOutputStream.writeFloat(dataShape.mAnchorPoint4[3]);
            ICurvePointList curvePointList3 = dataShape.getCurvePointList(dataShape.mSavePoints);
            dataOutputStream.writeInt(curvePointList3.size());
            while (i < curvePointList3.size()) {
                ICurvePoint iCurvePoint3 = curvePointList3.get(i);
                dataOutputStream.writeFloat(iCurvePoint3.getX());
                dataOutputStream.writeFloat(iCurvePoint3.getY());
                dataOutputStream.writeFloat(iCurvePoint3.getPressure());
                i++;
            }
        }
    }
}
