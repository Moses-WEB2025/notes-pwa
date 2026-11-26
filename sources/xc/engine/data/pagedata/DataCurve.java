package xc.engine.data.pagedata;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import xc.engine.core.graphics.pen.BasePenPropConfig;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.core.graphics.pen.ball.BallPropConfig;
import xc.engine.core.graphics.pen.ink.InkPropConfig;
import xc.engine.core.graphics.pen.pencil.PencilPropConfig;
import xc.engine.core.graphics.pen.watercolor.WaterColorPropConfig;
import xc.engine.data.CurveProp;
import xc.engine.data.curve.CurvePointListOperator;
import xc.engine.data.curve.ICurvePoint;
import xc.engine.data.curve.ICurvePointList;

/* JADX INFO: loaded from: classes6.dex */
public class DataCurve extends BaseData implements ICurve {
    private static final int BASE_CURVE_DATA_SIZE = 120;
    public boolean isMathResult;
    public PointF mBasePoint;
    private ICurveChangeListener mChangeListener;
    public CurveProp mCurveProp;
    public short mCurveType;
    public float mDrawRatio;
    public final Object mLockObj;
    public long mOriginPoints;
    public BasePenPropConfig mPenPropConfig;
    public int mPenTypeValue;
    public RectF mRect;
    public Region mRegion;
    public float mRotateDegrees;
    public long mSavePoints;
    public float mScaleRate;
    public long mScaledPoints;
    public PointF mStartPoint;
    public String mathBlockId;
    public int pageIndex;
    public boolean r;
    public String recordID;
    public float renderOffsetX;
    public float renderOffsetY;
    public String strokeId;

    @Override // xc.engine.data.pagedata.IData
    public short getType() {
        return (short) 1;
    }

    public DataCurve(CurveProp curveProp) {
        this(curveProp, 0L);
    }

    public void setCurveChangeListener(ICurveChangeListener iCurveChangeListener) {
        this.mChangeListener = iCurveChangeListener;
    }

    public DataCurve(CurveProp curveProp, long j) {
        this.isMathResult = false;
        this.renderOffsetX = 0.0f;
        this.renderOffsetY = 0.0f;
        this.pageIndex = 0;
        this.mSavePoints = CurvePointListOperator.createLine();
        this.mOriginPoints = CurvePointListOperator.createLine();
        this.mScaledPoints = CurvePointListOperator.createLine();
        this.mLockObj = new Object();
        this.mScaleRate = 1.0f;
        this.mBasePoint = new PointF();
        this.mRegion = new Region();
        this.mCurveProp = new CurveProp(curveProp);
        this.mCurveType = (short) 0;
        this.mStartPoint = null;
        this.mRect = new RectF();
        this.mDrawRatio = 1.0f;
        this.mTime = j;
        this.mPenTypeValue = curveProp.getPenType().getValue();
        PenType penType = this.mCurveProp.getPenType();
        if (PenType.PENCIL == penType) {
            this.mPenPropConfig = new PencilPropConfig(0.0f, 0.0f);
        } else if (PenType.INK == penType) {
            this.mPenPropConfig = new InkPropConfig(0.0f, 0.0f);
        } else if (PenType.WATER_COLOR == penType) {
            this.mPenPropConfig = new WaterColorPropConfig(0.0f, 0.0f);
        }
    }

    public void printDataCurve() {
        Log.d("DataCurve", " ~~~~~~~~~~~~~~~ print data curve ~~~~~~~~~~~~~~~");
        Log.d("DataCurve", " mDrawRatio " + this.mDrawRatio);
        Log.d("DataCurve", " mCurveType " + ((int) this.mCurveType));
        Log.d("DataCurve", " mRotateDegrees " + this.mRotateDegrees);
        Log.d("DataCurve", " mRect " + this.mRect.toString());
        if (this.mStartPoint == null) {
            Log.d("DataCurve", " mStartPoint = null");
        } else {
            Log.d("DataCurve", " mStartPoint " + this.mStartPoint.toString());
        }
        Log.d("DataCurve", " mScaleRate " + this.mScaleRate);
        Log.d("DataCurve", " mBasePoint " + this.mBasePoint.toString());
        if (this.mPenPropConfig != null) {
            Log.d("DataCurve", " mPenSize " + this.mPenPropConfig.mPenSize);
            Log.d("DataCurve", " mDrawRatio " + this.mPenPropConfig.mDrawRatio);
        }
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        int size = curvePointList.size();
        Log.d("DataCurve", "save points size is" + size);
        for (int i = 0; i < size; i++) {
            Log.d("DataCurve", "saved " + i + StringUtils.SPACE + curvePointList.getX(i) + StringUtils.SPACE + curvePointList.getY(i));
        }
        ICurvePointList curvePointList2 = getCurvePointList(this.mOriginPoints);
        int size2 = curvePointList2.size();
        Log.d("DataCurve", "org points size is" + size2);
        for (int i2 = 0; i2 < size2; i2++) {
            Log.d("DataCurve", "org " + i2 + StringUtils.SPACE + curvePointList2.getX(i2) + StringUtils.SPACE + curvePointList2.getY(i2));
        }
        ICurvePointList curvePointList3 = getCurvePointList(this.mScaledPoints);
        int size3 = curvePointList3.size();
        Log.d("DataCurve", "scaled points size is" + size3);
        for (int i3 = 0; i3 < size3; i3++) {
            Log.d("DataCurve", "scaled " + i3 + StringUtils.SPACE + curvePointList3.getX(i3) + StringUtils.SPACE + curvePointList3.getY(i3));
        }
        Log.d("DataCurve", " ~~~~~~~~~~~~~~~ print data curve end ~~~~~~~~~~~~~~~");
    }

    @Override // xc.engine.data.pagedata.BaseData
    public void releasePointList() {
        synchronized (this.mLockObj) {
            long j = this.mSavePoints;
            if (j != 0) {
                CurvePointListOperator.releaseLine(j);
                this.mSavePoints = 0L;
            }
            long j2 = this.mOriginPoints;
            if (j2 != 0) {
                CurvePointListOperator.releaseLine(j2);
                this.mOriginPoints = 0L;
            }
            long j3 = this.mScaledPoints;
            if (j3 != 0) {
                CurvePointListOperator.releaseLine(j3);
                this.mScaledPoints = 0L;
            }
        }
    }

    @Override // xc.engine.data.pagedata.BaseData
    public DataCurve clone() throws CloneNotSupportedException {
        DataCurve dataCurve = (DataCurve) super.clone();
        dataCurve.mCurveProp = new CurveProp(this.mCurveProp.clone());
        dataCurve.mPenTypeValue = this.mPenTypeValue;
        PointF pointF = this.mStartPoint;
        if (pointF != null) {
            dataCurve.mStartPoint = new PointF(pointF.x, pointF.y);
        }
        dataCurve.mSavePoints = CurvePointListOperator.createLine(this.mSavePoints);
        dataCurve.mOriginPoints = CurvePointListOperator.createLine();
        dataCurve.mScaledPoints = CurvePointListOperator.createLine();
        if (this.mRect != null) {
            dataCurve.mRect = new RectF(this.mRect);
        }
        PointF pointF2 = this.mBasePoint;
        dataCurve.mBasePoint = new PointF(pointF2.x, pointF2.y);
        dataCurve.mScaleRate = this.mScaleRate;
        return dataCurve;
    }

    public DataCurve fullClone() throws CloneNotSupportedException {
        DataCurve dataCurve = (DataCurve) super.clone();
        dataCurve.mCurveProp = new CurveProp(this.mCurveProp.clone());
        dataCurve.mPenTypeValue = this.mPenTypeValue;
        PointF pointF = this.mStartPoint;
        if (pointF != null) {
            dataCurve.mStartPoint = new PointF(pointF.x, pointF.y);
        }
        dataCurve.mSavePoints = CurvePointListOperator.createLine(this.mSavePoints);
        dataCurve.mOriginPoints = CurvePointListOperator.createLine(this.mOriginPoints);
        dataCurve.mScaledPoints = CurvePointListOperator.createLine();
        if (this.mRect != null) {
            dataCurve.mRect = new RectF(this.mRect);
        }
        PointF pointF2 = this.mBasePoint;
        dataCurve.mBasePoint = new PointF(pointF2.x, pointF2.y);
        dataCurve.mScaleRate = this.mScaleRate;
        return dataCurve;
    }

    @Override // xc.engine.data.pagedata.ICurve
    public float getSensitivity() {
        return this.mCurveProp.getSensitivity();
    }

    @Override // xc.engine.data.pagedata.ICurve
    public float getTipSize() {
        return this.mCurveProp.getTipSize();
    }

    public void setDrawRatio(float f) {
        this.mDrawRatio = f;
    }

    public void setValue(float f, PointF pointF, float f2) {
        this.mDrawRatio = f2;
        this.mBasePoint.set(pointF);
        this.mScaleRate = f;
    }

    @Override // xc.engine.data.pagedata.BaseData
    public void doScale(float f, float f2, float f3) {
        dataScale(f, f2, f3);
        CurveProp curveProp = this.mCurveProp;
        curveProp.setPenSize(curveProp.getPenSize() * f3);
        ICurveChangeListener iCurveChangeListener = this.mChangeListener;
        if (iCurveChangeListener != null) {
            iCurveChangeListener.onCurveChanged(this, "SCALE");
        }
    }

    public void dataScale(float f, float f2, float f3) {
        float f4 = this.mDrawRatio;
        float f5 = f / f4;
        float f6 = f2 / f4;
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        int size = curvePointList.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        for (int i = 0; i < size; i++) {
            float x = ((curvePointList.getX(i) - f5) * f3) + f5;
            float y = ((curvePointList.getY(i) - f6) * f3) + f6;
            fArr[i] = x;
            fArr2[i] = y;
            curvePointList.setX(i, x);
            curvePointList.setY(i, y);
        }
        curvePointList.setNativeX(fArr, 0, size);
        curvePointList.setNativeY(fArr2, 0, size);
        calculateBoundingRect();
        CurvePointListOperator.clearLine(this.mOriginPoints);
        this.mStartPoint = null;
    }

    @Override // xc.engine.data.pagedata.BaseData
    public float getDrawRatio() {
        return this.mDrawRatio;
    }

    @Override // xc.engine.data.pagedata.ICurve
    public int getCurveColor() {
        int i = this.e;
        return (this.r && i != -1 && this.mDataState == 1) ? i : this.mCurveProp.getPenColor();
    }

    @Override // xc.engine.data.pagedata.ICurve
    public int getCurveColorWheel() {
        int i;
        return (this.r && (i = this.e) != -1 && this.mDataState == 1) ? i : this.mCurveProp.getPenColorWheel();
    }

    @Override // xc.engine.data.pagedata.ICurve
    public float getCurvePenWidth() {
        return this.mCurveProp.getPenSize();
    }

    public void calculateBoundingRect() {
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        if (curvePointList.size() == 0) {
            return;
        }
        if (curvePointList.size() == 1) {
            PointF penPoint = getPenPoint(false);
            float f = penPoint.x;
            float f2 = penPoint.y;
            RectF rectF = new RectF((-f) / 2.0f, (-f2) / 2.0f, f / 2.0f, f2 / 2.0f);
            rectF.offset(curvePointList.getX(0), curvePointList.getY(0));
            this.mRect = new RectF(rectF);
            return;
        }
        ICurvePoint iCurvePoint = curvePointList.get(0);
        ICurvePoint iCurvePoint2 = curvePointList.get(1);
        this.mRect = new RectF(Math.min(iCurvePoint.getX(), iCurvePoint2.getX()), Math.min(iCurvePoint.getY(), iCurvePoint2.getY()), Math.max(iCurvePoint.getX(), iCurvePoint2.getX()), Math.max(iCurvePoint.getY(), iCurvePoint2.getY()));
        for (int i = 2; i < curvePointList.size(); i++) {
            ICurvePoint iCurvePoint3 = curvePointList.get(i);
            this.mRect.union(iCurvePoint3.getX(), iCurvePoint3.getY());
        }
    }

    public void initRectF(RectF rectF) {
        this.mRect.set(rectF);
    }

    @Override // xc.engine.data.pagedata.IData
    public RectF getDrawRect() {
        RectF saveRect = getSaveRect();
        saveRect.left *= this.mDrawRatio;
        saveRect.top *= this.mDrawRatio;
        saveRect.right *= this.mDrawRatio;
        saveRect.bottom *= this.mDrawRatio;
        return saveRect;
    }

    @Override // xc.engine.data.pagedata.IData
    public RectF getSaveRect() {
        return new RectF(this.mRect);
    }

    @Override // xc.engine.data.pagedata.ICurve
    public PenType getPenType() {
        return this.mCurveProp.getPenType();
    }

    @Override // xc.engine.data.pagedata.ICurve
    public float getRotateAngle() {
        return this.mRotateDegrees;
    }

    @Override // xc.engine.data.pagedata.ICurve
    public int getAlpha() {
        return (this.r && this.e == -1 && this.mDataState == 1) ? this.mCurveProp.getPenAlpha() / 3 : this.mCurveProp.getPenAlpha();
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0042  */
    /* JADX WARN: Code duplicated, block: B:23:0x008c  */
    public PointF getPenPoint(boolean z) {
        float f;
        float f2;
        float penSize = this.mCurveProp.getPenSize();
        float f3 = z ? (penSize * (this.mDrawRatio / 2.0f)) + 1.0f : penSize * 0.5f;
        PenType penType = this.mCurveProp.getPenType();
        if (penType == PenType.MARK) {
            f = 0.75f;
        } else if (penType == PenType.PENCIL) {
            f = 0.15f;
        } else {
            if (penType != PenType.INK) {
                if (penType == PenType.BALL) {
                    f = 0.35f;
                }
                f2 = this.mRotateDegrees;
                if (f2 != 0.0f) {
                    double radians = Math.toRadians(f2);
                    double d = f3;
                    double dAtan2 = Math.atan2(d, d);
                    double d2 = radians + dAtan2;
                    double d3 = (radians + 3.141592653589793d) - dAtan2;
                    double dCos = d / Math.cos(dAtan2);
                    return new PointF((float) (Math.max(Math.abs(Math.cos(d2)), Math.abs(Math.cos(d3))) * dCos), (float) (dCos * Math.max(Math.abs(Math.sin(d2)), Math.abs(Math.sin(d3)))));
                }
                return new PointF(f3, f3);
            }
            f = 0.6f;
        }
        f3 /= f;
        f2 = this.mRotateDegrees;
        if (f2 != 0.0f) {
            double radians2 = Math.toRadians(f2);
            double d4 = f3;
            double dAtan3 = Math.atan2(d4, d4);
            double d5 = radians2 + dAtan3;
            double d6 = (radians2 + 3.141592653589793d) - dAtan3;
            double dCos2 = d4 / Math.cos(dAtan3);
            return new PointF((float) (Math.max(Math.abs(Math.cos(d5)), Math.abs(Math.cos(d6))) * dCos2), (float) (dCos2 * Math.max(Math.abs(Math.sin(d5)), Math.abs(Math.sin(d6)))));
        }
        return new PointF(f3, f3);
    }

    public PointF getOutlinePenPoint() {
        float f;
        float penSize = (this.mCurveProp.getPenSize() * (this.mDrawRatio / 2.0f)) + 1.0f;
        PenType penType = this.mCurveProp.getPenType();
        if (penType == PenType.MARK) {
            f = 0.35f;
        } else if (penType == PenType.PENCIL) {
            f = 0.225f;
        } else if (penType == PenType.INK) {
            f = 0.8f;
        } else {
            f = penType == PenType.BALL ? 0.4375f : 1.45f;
        }
        float f2 = penSize / f;
        float f3 = this.mRotateDegrees;
        if (f3 != 0.0f) {
            double radians = Math.toRadians(f3);
            double d = f2;
            double dAtan2 = Math.atan2(d, d);
            double d2 = radians + dAtan2;
            double d3 = (radians + 3.141592653589793d) - dAtan2;
            double dCos = d / Math.cos(dAtan2);
            return new PointF((float) (Math.max(Math.abs(Math.cos(d2)), Math.abs(Math.cos(d3))) * dCos), (float) (dCos * Math.max(Math.abs(Math.sin(d2)), Math.abs(Math.sin(d3)))));
        }
        return new PointF(f2, f2);
    }

    public PointF calCurveOutlineThickness(boolean z, boolean z2) {
        float f;
        float f2;
        float penSize = this.mCurveProp.getPenSize();
        if (z) {
            f = (penSize * ((this.mDrawRatio * (z2 ? this.mScaleRate : 1.0f)) / 2.0f)) + 1.0f;
        } else {
            f = penSize * 0.5f;
        }
        PenType penType = this.mCurveProp.getPenType();
        if (penType == PenType.MARK) {
            float f3 = f;
            f /= 2.5f;
            f2 = f3;
        } else {
            if (penType == PenType.PENCIL) {
                f /= 0.3f;
            }
            f2 = f;
        }
        float f4 = this.mRotateDegrees;
        if (f4 != 0.0f) {
            double radians = Math.toRadians(f4);
            double d = f;
            double dAtan2 = Math.atan2(f2, d);
            double d2 = radians + dAtan2;
            double d3 = (radians + 3.141592653589793d) - dAtan2;
            double dCos = d / Math.cos(dAtan2);
            return new PointF((float) (Math.max(Math.abs(Math.cos(d2)), Math.abs(Math.cos(d3))) * dCos), (float) (dCos * Math.max(Math.abs(Math.sin(d2)), Math.abs(Math.sin(d3)))));
        }
        return new PointF(f, f2);
    }

    public PointF translateRectByPenPoint(RectF rectF, boolean z) {
        PointF penPoint = getPenPoint(z);
        rectF.left -= penPoint.x;
        rectF.top -= penPoint.y;
        rectF.right += penPoint.x;
        rectF.bottom += penPoint.y;
        return penPoint;
    }

    public PointF expandRectByCurveOutlineThickness(RectF rectF, boolean z, boolean z2) {
        PointF pointFCalCurveOutlineThickness = calCurveOutlineThickness(z, z2);
        rectF.left -= pointFCalCurveOutlineThickness.x;
        rectF.top -= pointFCalCurveOutlineThickness.y;
        rectF.right += pointFCalCurveOutlineThickness.x;
        rectF.bottom += pointFCalCurveOutlineThickness.y;
        return pointFCalCurveOutlineThickness;
    }

    @Override // xc.engine.data.pagedata.ICurve
    public short getShapeType() {
        return this.mCurveType;
    }

    public void setPenSize(float f) {
        this.mCurveProp.setPenSize(f);
    }

    public void setPenAlpha(int i) {
        this.mCurveProp.setPenAlpha(i);
    }

    public CurveProp getCurveProp() {
        return this.mCurveProp;
    }

    public void setScaleRate(float f) {
        this.mScaleRate = f;
    }

    public PointF getBasePoint() {
        return this.mBasePoint;
    }

    @Override // xc.engine.data.pagedata.BaseData, xc.engine.data.pagedata.IData
    public int getDataSize() {
        int size = size(this.mSavePoints);
        if (size == 0) {
            return 120;
        }
        return (size * 40) + 120;
    }

    @Override // xc.engine.data.pagedata.BaseData, xc.engine.data.pagedata.IData
    public void clearPointsData() {
        synchronized (this.mLockObj) {
            CurvePointListOperator.clearLine(this.mSavePoints);
            CurvePointListOperator.clearLine(this.mOriginPoints);
            CurvePointListOperator.clearLine(this.mScaledPoints);
        }
    }

    @Override // xc.engine.data.pagedata.BaseData, xc.engine.data.pagedata.IData
    public void writeData(DataOutputStream dataOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream2.writeShort(getType());
        dataOutputStream2.writeInt(this.mCurveProp.getPenType().getValue());
        dataOutputStream2.writeInt(this.mCurveProp.getPenColor());
        dataOutputStream2.writeFloat(this.mCurveProp.getPenSize());
        dataOutputStream2.writeInt(this.mCurveProp.getPenAlpha());
        dataOutputStream2.writeInt(this.mCurveProp.getPenColorWheel());
        dataOutputStream2.writeFloat(this.mCurveProp.getSensitivity());
        dataOutputStream2.writeFloat(this.mCurveProp.getTipSize());
        dataOutputStream2.writeInt(this.mId);
        dataOutputStream2.writeInt(this.mCount);
        dataOutputStream2.writeShort(this.mTableFlag);
        dataOutputStream2.writeFloat(this.mDrawRatio);
        dataOutputStream2.writeInt(this.mDataState);
        dataOutputStream2.writeInt(this.e);
        dataOutputStream2.writeLong(this.mTime);
        dataOutputStream2.writeInt(this.mItemPositionIndex);
        dataOutputStream2.writeShort(this.mCurveType);
        dataOutputStream2.writeFloat(this.mRotateDegrees);
        dataOutputStream2.writeBoolean(this.r);
        dataOutputStream2.writeInt(this.mPenTypeValue);
        String str = this.strokeId;
        if (str == null) {
            str = "";
        }
        dataOutputStream2.writeUTF(str);
        PointF pointF = this.mStartPoint;
        if (pointF == null) {
            dataOutputStream2.writeFloat(-1.0f);
            dataOutputStream2.writeFloat(-1.0f);
        } else {
            dataOutputStream2.writeFloat(pointF.x);
            dataOutputStream2.writeFloat(this.mStartPoint.y);
        }
        dataOutputStream2.writeFloat(this.mRect.left);
        dataOutputStream2.writeFloat(this.mRect.top);
        dataOutputStream2.writeFloat(this.mRect.right);
        dataOutputStream2.writeFloat(this.mRect.bottom);
        dataOutputStream2.writeFloat(this.mScaleRate);
        dataOutputStream2.writeFloat(this.mBasePoint.x);
        dataOutputStream2.writeFloat(this.mBasePoint.y);
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        dataOutputStream2.writeInt(curvePointList.size());
        if (curvePointList.size() == 0) {
            byteArrayOutputStream.writeTo(dataOutputStream);
            dataOutputStream2.close();
            return;
        }
        for (int i = 0; i < curvePointList.size(); i++) {
            dataOutputStream2.writeFloat(curvePointList.getX(i));
            dataOutputStream2.writeFloat(curvePointList.getY(i));
            dataOutputStream2.writeFloat(curvePointList.getPressure(i));
            dataOutputStream2.writeFloat(curvePointList.getTilt(i));
            dataOutputStream2.writeLong(((long) curvePointList.getTime(i)) + this.mTime);
            dataOutputStream2.writeFloat(curvePointList.getOrientation(i));
            dataOutputStream2.writeFloat(curvePointList.getVelocity(i));
            dataOutputStream2.writeFloat(curvePointList.getRotateAngle(i));
        }
        byteArrayOutputStream.writeTo(dataOutputStream);
        dataOutputStream2.close();
    }

    @Override // xc.engine.data.pagedata.BaseData, xc.engine.data.pagedata.IData
    public IData readData(DataInputStream dataInputStream, int i) throws IOException {
        if (dataInputStream == null || i <= 0) {
            return this;
        }
        byte[] bArr = new byte[i];
        if (dataInputStream.read(bArr) == i) {
            DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
            int i2 = i / 40;
            clearPoints(this.mSavePoints);
            float[] fArr = new float[i2];
            float[] fArr2 = new float[i2];
            float[] fArr3 = new float[i2];
            float[] fArr4 = new float[i2];
            float[] fArr5 = new float[i2];
            float[] fArr6 = new float[i2];
            float[] fArr7 = new float[i2];
            long[] jArr = new long[i2];
            short[] sArr = new short[i2];
            int i3 = 0;
            while (i3 < i2) {
                fArr[i3] = dataInputStream2.readFloat();
                fArr2[i3] = dataInputStream2.readFloat();
                fArr3[i3] = dataInputStream2.readFloat();
                fArr4[i3] = dataInputStream2.readFloat();
                int i4 = i3;
                jArr[i4] = dataInputStream2.readLong() - this.mTime;
                fArr5[i4] = dataInputStream2.readFloat();
                fArr6[i4] = dataInputStream2.readFloat();
                fArr7[i4] = dataInputStream2.readFloat();
                sArr[i4] = 1;
                i3 = i4 + 1;
            }
            insertPoints(this.mSavePoints, i2, fArr, fArr2, fArr3, fArr4, jArr, fArr5, fArr6, fArr7, sArr);
            dataInputStream2.close();
            return this;
        }
        throw new IOException("read point size error");
    }

    public boolean hasIntersectionWithRegion(Region region, float f, float f2) {
        BasePenPropConfig waterColorPropConfig;
        DataCurve dataCurve = this;
        PointF penPoint = dataCurve.getPenPoint(true);
        PenType penType = dataCurve.mCurveProp.getPenType();
        if (penType == PenType.PENCIL) {
            waterColorPropConfig = new PencilPropConfig(dataCurve.mDrawRatio, dataCurve.getCurvePenWidth());
        } else if (penType == PenType.INK) {
            waterColorPropConfig = new InkPropConfig(dataCurve.mDrawRatio, dataCurve.getCurvePenWidth());
        } else if (penType == PenType.BALL) {
            waterColorPropConfig = new BallPropConfig(dataCurve.mDrawRatio, dataCurve.getCurvePenWidth());
        } else {
            waterColorPropConfig = penType == PenType.WATER_COLOR ? new WaterColorPropConfig(dataCurve.mDrawRatio, dataCurve.getCurvePenWidth()) : null;
        }
        Path path = new Path();
        ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
        int i = 0;
        while (i < curvePointList.size()) {
            ICurvePoint iCurvePoint = curvePointList.get(i);
            float x = iCurvePoint.getX() * dataCurve.mDrawRatio;
            float y = iCurvePoint.getY() * dataCurve.mDrawRatio;
            if (waterColorPropConfig != null) {
                float sensitivity = dataCurve.mCurveProp.getSensitivity();
                float tipSize = dataCurve.mCurveProp.getTipSize();
                waterColorPropConfig.mSensitivity = sensitivity;
                waterColorPropConfig.mTipSize = tipSize;
                float fCalculatePenSize = waterColorPropConfig.calculatePenSize(iCurvePoint.getPressure(), iCurvePoint.getTilt()) / 2.0f;
                if (fCalculatePenSize < 4.0f) {
                    fCalculatePenSize = 4.0f;
                }
                penPoint.set(fCalculatePenSize, fCalculatePenSize);
            }
            Region region2 = new Region(region);
            Region region3 = new Region();
            RectF rectF = new RectF();
            path.reset();
            if (PenType.INK == penType || PenType.BALL == penType || PenType.WATER_COLOR == penType) {
                path.addCircle(x, y, penPoint.x, Path.Direction.CCW);
            } else {
                path.addRect(x - penPoint.x, y - penPoint.y, x + penPoint.x, y + penPoint.y, Path.Direction.CCW);
            }
            path.offset(-f, -f2);
            path.computeBounds(rectF, true);
            region3.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
            if (region2.op(region3, Region.Op.INTERSECT)) {
                return true;
            }
            i++;
            dataCurve = this;
        }
        return false;
    }

    public boolean hasIntersectionWithRegionLineDelete(Region region, float f, float f2) {
        BasePenPropConfig waterColorPropConfig;
        int i;
        int i2;
        PointF pointF;
        PointF pointF2;
        int iMin;
        DataCurve dataCurve = this;
        int i3 = 1;
        PointF penPoint = dataCurve.getPenPoint(true);
        PenType penType = dataCurve.mCurveProp.getPenType();
        if (penType == PenType.PENCIL) {
            waterColorPropConfig = new PencilPropConfig(dataCurve.mDrawRatio, dataCurve.getCurvePenWidth());
        } else if (penType == PenType.INK) {
            waterColorPropConfig = new InkPropConfig(dataCurve.mDrawRatio, dataCurve.getCurvePenWidth());
        } else if (penType == PenType.BALL) {
            waterColorPropConfig = new BallPropConfig(dataCurve.mDrawRatio, dataCurve.getCurvePenWidth());
        } else {
            waterColorPropConfig = penType == PenType.WATER_COLOR ? new WaterColorPropConfig(dataCurve.mDrawRatio, dataCurve.getCurvePenWidth()) : null;
        }
        Path path = new Path();
        ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
        Rect bounds = region.getBounds();
        int iSqrt = (int) Math.sqrt((bounds.width() * bounds.width()) + (bounds.height() * bounds.height()));
        int i4 = (int) penPoint.x;
        float f3 = iSqrt + i4;
        short type = dataCurve.getType();
        int i5 = 0;
        while (i5 < curvePointList.size()) {
            ICurvePoint iCurvePoint = curvePointList.get(i5);
            int i6 = i3;
            PointF pointF3 = penPoint;
            int i7 = i5;
            float fSqrt = (float) Math.sqrt(Math.pow((iCurvePoint.getX() * dataCurve.mDrawRatio) - f, 2.0d) + Math.pow((iCurvePoint.getY() * dataCurve.mDrawRatio) - f2, 2.0d));
            float fSqrt2 = fSqrt - f3;
            if (type == 28) {
                if (fSqrt > f3) {
                    iMin = i7 + Math.min(Math.min((int) (fSqrt / i4), (curvePointList.size() - i7) - 1), 5);
                    penType = penType;
                    path = path;
                    type = type;
                    pointF2 = pointF3;
                } else {
                    path = path;
                    i = i7;
                    type = type;
                }
                i5 = iMin + 1;
                dataCurve = this;
                penPoint = pointF2;
                path = path;
                i3 = i6;
                type = type;
                penType = penType;
            } else {
                int i8 = i7;
                while (true) {
                    if (fSqrt2 > 0.0f && (i2 = (int) (fSqrt2 / i4)) > i6) {
                        i8 += i2;
                        if (i8 >= curvePointList.size()) {
                            i8 -= i2;
                            break;
                        }
                        ICurvePoint iCurvePoint2 = curvePointList.get(i8);
                        if (iCurvePoint2 != null) {
                            fSqrt2 = ((float) Math.sqrt(Math.pow((iCurvePoint2.getX() * dataCurve.mDrawRatio) - f, 2.0d) + Math.pow((iCurvePoint2.getY() * dataCurve.mDrawRatio) - f2, 2.0d))) - f3;
                        }
                        path = path;
                        type = type;
                        i6 = 1;
                    } else {
                        break;
                    }
                }
                i = i8;
            }
            ICurvePoint iCurvePoint3 = curvePointList.get(i);
            float x = iCurvePoint3.getX() * dataCurve.mDrawRatio;
            float y = iCurvePoint3.getY() * dataCurve.mDrawRatio;
            if (waterColorPropConfig != null) {
                float sensitivity = dataCurve.mCurveProp.getSensitivity();
                float tipSize = dataCurve.mCurveProp.getTipSize();
                waterColorPropConfig.mSensitivity = sensitivity;
                waterColorPropConfig.mTipSize = tipSize;
                float fCalculatePenSize = waterColorPropConfig.calculatePenSize(iCurvePoint3.getPressure(), iCurvePoint3.getTilt()) / 2.0f;
                if (fCalculatePenSize < 4.0f) {
                    fCalculatePenSize = 4.0f;
                }
                pointF = pointF3;
                pointF.set(fCalculatePenSize, fCalculatePenSize);
            } else {
                pointF = pointF3;
            }
            Region region2 = new Region(region);
            Region region3 = new Region();
            RectF rectF = new RectF();
            path.reset();
            int i9 = i;
            if (PenType.INK == penType || PenType.BALL == penType || PenType.WATER_COLOR == penType) {
                pointF2 = pointF;
                path.addCircle(x, y, pointF2.x, Path.Direction.CCW);
            } else {
                Path path2 = path;
                pointF2 = pointF;
                path2.addRect(x - pointF.x, y - pointF.y, x + pointF.x, y + pointF.y, Path.Direction.CCW);
                path = path2;
            }
            path.offset(-f, -f2);
            path.computeBounds(rectF, true);
            region3.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
            i6 = 1;
            if (region2.op(region3, Region.Op.INTERSECT)) {
                return true;
            }
            iMin = i9;
            i5 = iMin + 1;
            dataCurve = this;
            penPoint = pointF2;
            path = path;
            i3 = i6;
            type = type;
            penType = penType;
        }
        return false;
    }

    @Override // xc.engine.data.pagedata.BaseData
    public void doMove(float f, float f2) {
        float f3 = this.mDrawRatio;
        float f4 = f / f3;
        float f5 = f2 / f3;
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        int size = curvePointList.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        for (int i = 0; i < size; i++) {
            float x = curvePointList.getX(i) + f4;
            float y = curvePointList.getY(i) + f5;
            fArr[i] = x;
            fArr2[i] = y;
            curvePointList.setX(i, x);
            curvePointList.setY(i, y);
        }
        curvePointList.setNativeX(fArr, 0, size);
        curvePointList.setNativeY(fArr2, 0, size);
        calculateBoundingRect();
        ICurvePointList curvePointList2 = getCurvePointList(this.mOriginPoints);
        int size2 = curvePointList2.size();
        float[] fArr3 = new float[size2];
        float[] fArr4 = new float[size2];
        for (int i2 = 0; i2 < size2; i2++) {
            float x2 = curvePointList2.getX(i2) + f;
            float y2 = curvePointList2.getY(i2) + f2;
            fArr3[i2] = x2;
            fArr4[i2] = y2;
            curvePointList2.setX(i2, x2);
            curvePointList2.setY(i2, y2);
        }
        curvePointList2.setNativeX(fArr3, 0, size2);
        curvePointList2.setNativeY(fArr4, 0, size2);
        ICurvePointList curvePointList3 = getCurvePointList(this.mScaledPoints);
        int size3 = curvePointList3.size();
        float[] fArr5 = new float[size3];
        float[] fArr6 = new float[size3];
        for (int i3 = 0; i3 < size3; i3++) {
            float x3 = curvePointList3.getX(i3) + (this.mScaleRate * f);
            float y3 = curvePointList3.getY(i3) + (this.mScaleRate * f2);
            fArr5[i3] = x3;
            fArr6[i3] = y3;
            curvePointList3.setX(i3, x3);
            curvePointList3.setY(i3, y3);
        }
        curvePointList3.setNativeX(fArr5, 0, size3);
        curvePointList3.setNativeY(fArr6, 0, size3);
        PointF pointF = this.mStartPoint;
        if (pointF != null) {
            pointF.x += f;
            pointF.y += f2;
        }
        ICurveChangeListener iCurveChangeListener = this.mChangeListener;
        if (iCurveChangeListener != null) {
            iCurveChangeListener.onCurveChanged(this, "MOVE");
        }
    }

    @Override // xc.engine.data.pagedata.BaseData
    public void calcBound(float[] fArr) {
        ICurvePointList curvePointList = getCurvePointList(this.mScaledPoints);
        int size = curvePointList.size();
        for (int i = 0; i < size; i++) {
            if (curvePointList.getState(i) != 3 && this.mDataState != 3) {
                float x = curvePointList.getX(i) * (-1.0f);
                float y = curvePointList.getY(i) * (-1.0f);
                fArr[0] = Math.max(x / this.mScaleRate, fArr[0]);
                fArr[1] = Math.max(y / this.mScaleRate, fArr[1]);
                fArr[2] = Math.min(x / this.mScaleRate, fArr[2]);
                fArr[3] = Math.min(y / this.mScaleRate, fArr[3]);
            }
        }
    }

    public void doScaleAndRotate(float f, float f2, float f3, float f4) {
        float f5 = this.mDrawRatio;
        float f6 = f / f5;
        float f7 = f2 / f5;
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        this.mRotateDegrees += f4;
        double radians = (float) Math.toRadians(-f4);
        int size = curvePointList.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        int i = 0;
        while (i < size) {
            double x = (curvePointList.getX(i) - f6) * f3;
            double y = (curvePointList.getY(i) - f7) * f3;
            int i2 = i;
            float fCos = (float) (((double) f6) + (Math.cos(radians) * x) + (Math.sin(radians) * y));
            float fCos2 = (float) (((y * Math.cos(radians)) - (x * Math.sin(radians))) + ((double) f7));
            fArr[i2] = fCos;
            fArr2[i2] = fCos2;
            curvePointList.setX(i2, fCos);
            curvePointList.setY(i2, fCos2);
            i = i2 + 1;
        }
        curvePointList.setNativeX(fArr, 0, size);
        curvePointList.setNativeY(fArr2, 0, size);
        calculateBoundingRect();
        CurvePointListOperator.clearLine(this.mOriginPoints);
        calculateBoundingRect();
        this.mStartPoint = null;
        CurveProp curveProp = this.mCurveProp;
        curveProp.setPenSize(curveProp.getPenSize() * f3);
    }

    @Override // xc.engine.data.pagedata.BaseData
    public void doRotate(float f, float f2, float f3) {
        this.mRotateDegrees += f3;
        float f4 = this.mDrawRatio;
        float f5 = f / f4;
        float f6 = f2 / f4;
        float f7 = -f3;
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        float[] fArr = new float[curvePointList.size()];
        float[] fArr2 = new float[curvePointList.size()];
        int i = 0;
        while (i < curvePointList.size()) {
            float x = curvePointList.getX(i) - f5;
            float y = curvePointList.getY(i) - f6;
            double radians = (float) Math.toRadians(f7);
            double dCos = Math.cos(radians);
            double dSin = Math.sin(radians);
            int i2 = i;
            double d = x;
            double d2 = y;
            float f8 = (float) ((d * dCos) + (d2 * dSin) + ((double) f5));
            float f9 = (float) (((d2 * dCos) - (dSin * d)) + ((double) f6));
            fArr[i2] = f8;
            fArr2[i2] = f9;
            curvePointList.setX(i2, f8);
            curvePointList.setY(i2, f9);
            i = i2 + 1;
        }
        curvePointList.setNativeX(fArr, 0, curvePointList.size());
        curvePointList.setNativeY(fArr2, 0, curvePointList.size());
        calculateBoundingRect();
        ICurvePointList curvePointList2 = getCurvePointList(this.mOriginPoints);
        float[] fArr3 = new float[curvePointList2.size()];
        float[] fArr4 = new float[curvePointList2.size()];
        for (int i3 = 0; i3 < curvePointList2.size(); i3++) {
            float x2 = curvePointList2.getX(i3) - f;
            float y2 = curvePointList2.getY(i3) - f2;
            double radians2 = Math.toRadians(f7);
            double dCos2 = Math.cos(radians2);
            double dSin2 = Math.sin(radians2);
            double d3 = x2;
            double d4 = y2;
            float f10 = (float) ((d3 * dCos2) + (d4 * dSin2) + ((double) f));
            float f11 = (float) (((dCos2 * d4) - (d3 * dSin2)) + ((double) f2));
            fArr3[i3] = f10;
            fArr4[i3] = f11;
            curvePointList2.setX(i3, f10);
            curvePointList2.setY(i3, f11);
        }
        curvePointList2.setNativeX(fArr3, 0, curvePointList2.size());
        curvePointList2.setNativeY(fArr4, 0, curvePointList2.size());
        this.mStartPoint = null;
        ICurveChangeListener iCurveChangeListener = this.mChangeListener;
        if (iCurveChangeListener != null) {
            iCurveChangeListener.onCurveChanged(this, "ROTATE");
        }
    }

    @Override // xc.engine.data.pagedata.BaseData
    public void scale(PointF pointF, PointF pointF2) {
        float f = pointF.x / this.mDrawRatio;
        float f2 = pointF.y / this.mDrawRatio;
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        int size = curvePointList.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        for (int i = 0; i < size; i++) {
            float x = ((curvePointList.getX(i) - f) * pointF2.x) + f;
            float y = ((curvePointList.getY(i) - f2) * pointF2.y) + f2;
            fArr[i] = x;
            fArr2[i] = y;
            curvePointList.setX(i, x);
            curvePointList.setY(i, y);
        }
        curvePointList.setNativeX(fArr, 0, size);
        curvePointList.setNativeY(fArr2, 0, size);
        calculateBoundingRect();
        CurvePointListOperator.clearLine(this.mOriginPoints);
        this.mStartPoint = null;
    }

    public ICurvePointList getCurvePointList(long j) {
        ICurvePointList iCurvePointListNewCurvePointList;
        synchronized (this.mLockObj) {
            iCurvePointListNewCurvePointList = CurvePointListOperator.newCurvePointList(j);
        }
        return iCurvePointListNewCurvePointList;
    }

    public int size(long j) {
        int size;
        synchronized (this.mLockObj) {
            size = CurvePointListOperator.size(j);
        }
        return size;
    }

    public void addAllPoints(long j, long j2) {
        synchronized (this.mLockObj) {
            CurvePointListOperator.addLine(j, j2);
        }
    }

    public void insertPoints(long j, int i, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, long[] jArr, float[] fArr5, float[] fArr6, float[] fArr7, short[] sArr) {
        synchronized (this.mLockObj) {
            CurvePointListOperator.insertPoints(j, i, fArr, fArr2, fArr3, fArr4, jArr, fArr5, fArr6, fArr7, sArr);
        }
    }

    public void clearPoints(long j) {
        synchronized (this.mLockObj) {
            CurvePointListOperator.clearLine(j);
        }
    }

    public void insertPoint(long j, float f, float f2, float f3, float f4, float f5, float f6, long j2, short s, float f7) {
        synchronized (this.mLockObj) {
            CurvePointListOperator.insertPoint(j, f, f2, f3, f4, j2, f5, f6, s, f7);
        }
    }
}
