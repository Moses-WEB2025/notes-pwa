package xc.engine.data.pagedata;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.data.curve.CurvePointListOperator;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.shape.ShapeProp;
import xc.engine.data.shape.StrokeType;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.SkRegionHelper;
import xc.engine.utils.XLog;

/* JADX INFO: loaded from: classes6.dex */
public class DataShape extends DataCurve {
    private static final int BASE_SHAPE_DATA_SIZE = 106;
    public boolean O;
    public float P;
    public float[] mAnchorPoint4;
    public float[] mAnchorPoint8;
    public PointF mFilletPointF;
    public Matrix mMatrix1;
    public Matrix mMatrix2;
    public Region mPathRegion;
    public Path mScaledShapePath;
    public Path mShapePath;
    public ShapeProp mShapeProp;
    public Region mTouchRegion;
    public PointF maxEditBounds;
    public PointF minEditBounds;

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.IData
    public short getType() {
        return (short) 28;
    }

    public DataShape(ShapeProp shapeProp, long j) {
        super(shapeProp, j);
        this.mAnchorPoint4 = new float[4];
        this.mAnchorPoint8 = new float[8];
        this.mMatrix1 = new Matrix();
        this.mMatrix2 = new Matrix();
        this.O = false;
        this.P = 0.0f;
        this.mTouchRegion = new Region();
        this.mPathRegion = null;
        this.mShapeProp = new ShapeProp(shapeProp);
        this.mShapePath = new Path();
        this.mScaledShapePath = new Path();
        this.mFilletPointF = new PointF();
        this.maxEditBounds = new PointF();
        this.minEditBounds = new PointF();
    }

    public final void fixCoordinates() {
        float[] fArr = this.mAnchorPoint8;
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = f;
        int i = 2;
        float f4 = f2;
        float f5 = f3;
        while (true) {
            float[] fArr2 = this.mAnchorPoint8;
            if (i < fArr2.length) {
                float f6 = fArr2[i];
                float f7 = fArr2[i + 1];
                if (f6 < f5) {
                    f5 = f6;
                } else if (f6 >= f3) {
                    f3 = f6;
                }
                if (f7 < f2) {
                    f2 = f7;
                } else if (f7 >= f4) {
                    f4 = f7;
                }
                i += 2;
            } else {
                this.mRect.left = f5;
                this.mRect.top = f2;
                this.mRect.right = f3;
                this.mRect.bottom = f4;
                return;
            }
        }
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.IData
    public RectF getSaveRect() {
        RectF rectF = new RectF(this.mRect);
        if (this.mShapeProp.getShapeType() == 2) {
            float fMax = Math.max(this.mShapeProp.getPenSize() * 2.0f, 8.0f);
            float[] fArr = this.mAnchorPoint8;
            double degrees = (float) Math.toDegrees(Math.atan2(fArr[5] - fArr[1], fArr[4] - fArr[0]));
            float fSin = (float) Math.sin(Math.toRadians(degrees));
            float fCos = ((float) Math.cos(Math.toRadians(degrees))) * fMax;
            float f = fArr[4] - fCos;
            float f2 = fMax * fSin;
            float f3 = fArr[5] - f2;
            rectF.union(f - f2, f3 + fCos);
            rectF.union(f + f2, f3 - fCos);
        }
        return rectF;
    }

    @Override // xc.engine.data.pagedata.DataCurve
    public void calculateBoundingRect() {
        if (this.mAnchorPoint4.length != 4) {
            return;
        }
        float[] fArr = this.mAnchorPoint4;
        initRectF(new RectF(fArr[0], fArr[1], fArr[2], fArr[3]));
        getEditPoints();
    }

    @Override // xc.engine.data.pagedata.DataCurve
    public void initRectF(RectF rectF) {
        this.mAnchorPoint4[0] = rectF.left;
        this.mAnchorPoint4[1] = rectF.top;
        this.mAnchorPoint4[2] = rectF.right;
        this.mAnchorPoint4[3] = rectF.bottom;
        this.mAnchorPoint8[0] = rectF.left;
        this.mAnchorPoint8[1] = rectF.top;
        this.mAnchorPoint8[2] = rectF.right;
        this.mAnchorPoint8[3] = rectF.top;
        this.mAnchorPoint8[4] = rectF.right;
        this.mAnchorPoint8[5] = rectF.bottom;
        this.mAnchorPoint8[6] = rectF.left;
        this.mAnchorPoint8[7] = rectF.bottom;
        this.mMatrix1.reset();
        this.mMatrix1.postRotate(this.mRotateDegrees, rectF.centerX(), rectF.centerY());
        this.mMatrix1.mapPoints(this.mAnchorPoint8);
        fixCoordinates();
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.IData
    public RectF getDrawRect() {
        RectF rectF = new RectF();
        RectF rectF2 = this.mRect;
        float f = rectF2.left;
        float f2 = rectF2.right;
        float f3 = rectF2.top;
        float f4 = rectF2.bottom;
        float f5 = this.mDrawRatio;
        float f6 = f * f5;
        float f7 = f2 * f5;
        rectF.left = Math.min(f6, f7);
        float f8 = f3 * f5;
        float f9 = f4 * f5;
        rectF.top = Math.min(f8, f9);
        rectF.right = Math.max(f6, f7);
        rectF.bottom = Math.max(f8, f9);
        return rectF;
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.ICurve
    public PenType getPenType() {
        return this.mCurveProp.getPenType();
    }

    public float[] getCornerPoints() {
        char c;
        char c2;
        char c3;
        float[] fArr = new float[8];
        System.arraycopy(this.mAnchorPoint8, 0, fArr, 0, 8);
        if (isLineOrArrow()) {
            c = 0;
            c2 = 6;
            c3 = 7;
        } else {
            Matrix matrix = Thread.currentThread().getName().equals(PenEngineUtils.TAG) ? this.mMatrix1 : this.mMatrix2;
            matrix.reset();
            matrix.setRotate(-this.mRotateDegrees, (fArr[0] + fArr[4]) / 2.0f, (fArr[1] + fArr[5]) / 2.0f);
            matrix.mapPoints(fArr);
            c3 = 7;
            c = 0;
            c2 = 6;
            float fSqrt = ((float) Math.sqrt(Math.pow(fArr[2] - fArr[0], 2.0d) + Math.pow(fArr[3] - fArr[1], 2.0d))) / 2.0f;
            float fSqrt2 = ((float) Math.sqrt(Math.pow(fArr[6] - fArr[0], 2.0d) + Math.pow(fArr[7] - fArr[1], 2.0d))) / 2.0f;
            matrix.reset();
            matrix.postScale(((this.mShapeProp.getPenSize() / 2.0f) + fSqrt) / fSqrt, ((this.mShapeProp.getPenSize() / 2.0f) + fSqrt2) / fSqrt2, (fArr[0] + fArr[4]) / 2.0f, (fArr[1] + fArr[5]) / 2.0f);
            matrix.postRotate(this.mRotateDegrees, (fArr[0] + fArr[4]) / 2.0f, (fArr[1] + fArr[5]) / 2.0f);
            matrix.mapPoints(fArr);
        }
        fArr[c] = fArr[c] * this.mDrawRatio;
        fArr[1] = fArr[1] * this.mDrawRatio;
        fArr[2] = fArr[2] * this.mDrawRatio;
        fArr[3] = fArr[3] * this.mDrawRatio;
        fArr[4] = fArr[4] * this.mDrawRatio;
        fArr[5] = fArr[5] * this.mDrawRatio;
        fArr[c2] = fArr[c2] * this.mDrawRatio;
        fArr[c3] = fArr[c3] * this.mDrawRatio;
        return fArr;
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r25v0 ??, new type: char
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
        	... 5 more
        */
    public float[] getEditPoints() {
        /*
            Method dump skipped, instruction units count: 835
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: xc.engine.data.pagedata.DataShape.getEditPoints():float[]");
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.ICurve
    public float getCurvePenWidth() {
        return this.mShapeProp.getPenSize();
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.BaseData
    public void doScale(float f, float f2, float f3) {
        dataScale(f, f2, f3);
        ShapeProp shapeProp = this.mShapeProp;
        shapeProp.setPenSize(shapeProp.getPenSize() * f3);
    }

    public PointF c(float f, float f2) {
        float fSin = (float) Math.sin(Math.toRadians(this.mRotateDegrees));
        float fCos = (float) Math.cos(Math.toRadians(this.mRotateDegrees));
        float f3 = ((f * fCos) + (f2 * fSin)) / this.mDrawRatio;
        float f4 = ((f2 * fCos) - (f * fSin)) / this.mDrawRatio;
        PointF pointF = this.mFilletPointF;
        float f5 = pointF.x;
        PointF pointF2 = this.maxEditBounds;
        float f6 = pointF2.x;
        if (f5 + f3 >= f6) {
            f3 = f6 - f5;
        }
        float f7 = pointF.y;
        float f8 = pointF2.y;
        if (f7 + f4 >= f8) {
            f4 = f8 - f7;
        }
        float f9 = f5 + f3;
        PointF pointF3 = this.minEditBounds;
        float f10 = pointF3.x;
        if (f9 <= f10) {
            f3 = f10 - f5;
        }
        float f11 = f7 + f4;
        float f12 = pointF3.y;
        if (f11 <= f12) {
            f4 = f12 - f7;
        }
        pointF.x = f5 + f3;
        pointF.y = f7 + f4;
        if (this.mShapeProp.getShapeType() == 3) {
            PointF pointF4 = this.mFilletPointF;
            pointF4.x = Math.min(pointF4.x, pointF4.y);
            PointF pointF5 = this.mFilletPointF;
            pointF5.y = pointF5.x;
        }
        float f13 = this.mDrawRatio;
        float f14 = f3 * f13;
        float f15 = f4 * f13;
        return new PointF((f14 * fCos) - (f15 * fSin), (f14 * fSin) + (f15 * fCos));
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.BaseData
    public void doRotate(float f, float f2, float f3) {
        XLog.d("DataShape", "doRotate: mRotateDegrees=" + this.mRotateDegrees + ", degress=" + f3, new Object[0]);
        this.mRotateDegrees += f3;
        this.mRotateDegrees %= 360.0f;
        this.mMatrix1.reset();
        this.mMatrix1.postRotate(f3, f, f2);
        this.mShapePath.transform(this.mMatrix1);
        this.mMatrix1.reset();
        this.mMatrix1.postRotate(f3, f / this.mDrawRatio, f2 / this.mDrawRatio);
        this.mMatrix1.mapPoints(this.mAnchorPoint8);
        this.mMatrix1.mapPoints(this.mAnchorPoint4);
        fixCoordinates();
        this.mMatrix1.reset();
        this.mMatrix1.postRotate(-f3, this.mRect.centerX(), this.mRect.centerY());
        this.mMatrix1.mapPoints(this.mAnchorPoint4);
    }

    private Region pathToRegion(float f, float f2, Path path) {
        if (!isLineOrArrow()) {
            float[] fArr = this.mAnchorPoint8;
            float f3 = this.mDrawRatio;
            float[] fArr2 = {fArr[0] * f3, fArr[1] * f3, fArr[2] * f3, fArr[3] * f3, fArr[4] * f3, fArr[5] * f3, fArr[6] * f3, fArr[7] * f3};
            Matrix matrix = Thread.currentThread().getName().equals(PenEngineUtils.TAG) ? this.mMatrix1 : this.mMatrix2;
            matrix.reset();
            matrix.setRotate(-this.mRotateDegrees, (fArr2[0] + fArr2[4]) / 2.0f, (fArr2[1] + fArr2[5]) / 2.0f);
            matrix.mapPoints(fArr2);
            path.transform(matrix);
            float fSqrt = ((float) Math.sqrt(Math.pow(fArr2[2] - fArr2[0], 2.0d) + Math.pow(fArr2[3] - fArr2[1], 2.0d))) / 2.0f;
            float fSqrt2 = ((float) Math.sqrt(Math.pow(fArr2[6] - fArr2[0], 2.0d) + Math.pow(fArr2[7] - fArr2[1], 2.0d))) / 2.0f;
            matrix.reset();
            matrix.postScale((((this.mShapeProp.getPenSize() * f3) / 2.0f) + fSqrt) / fSqrt, (((this.mShapeProp.getPenSize() * f3) / 2.0f) + fSqrt2) / fSqrt2, (fArr2[0] + fArr2[4]) / 2.0f, (fArr2[1] + fArr2[5]) / 2.0f);
            matrix.postRotate(this.mRotateDegrees, (fArr2[0] + fArr2[4]) / 2.0f, (fArr2[1] + fArr2[5]) / 2.0f);
            path.transform(matrix);
        }
        Region region = new Region();
        path.offset(-f, -f2);
        Region region2 = new Region();
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        region2.setEmpty();
        region2.set(PenEngineUtils.toIntRect(rectF));
        region.setPath(path, region2);
        return region;
    }

    private Path createRectanglePath() {
        Path path = new Path();
        Matrix matrix = new Matrix();
        float[] fArr = new float[4];
        System.arraycopy(this.mAnchorPoint4, 0, fArr, 0, 4);
        float penSize = this.mShapeProp.getPenSize();
        float degrees = (float) Math.toDegrees(Math.atan2(fArr[3] - fArr[1], fArr[2] - fArr[0]));
        matrix.reset();
        matrix.setRotate(-degrees, (fArr[0] + fArr[2]) / 2.0f, (fArr[1] + fArr[3]) / 2.0f);
        matrix.mapPoints(fArr);
        float f = penSize / 2.0f;
        path.moveTo(fArr[0] * this.mDrawRatio, (fArr[1] - f) * this.mDrawRatio);
        path.lineTo(fArr[0] * this.mDrawRatio, (fArr[1] + f) * this.mDrawRatio);
        path.lineTo(fArr[2] * this.mDrawRatio, (fArr[3] + f) * this.mDrawRatio);
        path.lineTo(fArr[2] * this.mDrawRatio, (fArr[3] - f) * this.mDrawRatio);
        path.close();
        matrix.reset();
        float f2 = degrees + this.mRotateDegrees;
        float f3 = (fArr[0] + fArr[2]) / 2.0f;
        float f4 = this.mDrawRatio;
        matrix.setRotate(f2, f3 * f4, ((fArr[1] + fArr[3]) / 2.0f) * f4);
        path.transform(matrix);
        return path;
    }

    @Override // xc.engine.data.pagedata.DataCurve
    public PointF translateRectByPenPoint(RectF rectF, boolean z) {
        PointF penPoint = getPenPoint(z);
        rectF.left -= penPoint.x;
        rectF.top -= penPoint.y;
        rectF.right += penPoint.x;
        rectF.bottom += penPoint.y;
        return penPoint;
    }

    @Override // xc.engine.data.pagedata.DataCurve
    public PointF getPenPoint(boolean z) {
        float penSize = this.mShapeProp.getPenSize();
        if (z) {
            penSize *= this.mDrawRatio;
        }
        float f = penSize + 1.0f;
        return new PointF(f, f);
    }

    public PointF getFilletPointF() {
        return this.mFilletPointF;
    }

    public void setFilletPointF(float f, float f2) {
        this.mFilletPointF.x = f;
        this.mFilletPointF.y = f2;
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.ICurve
    public int getCurveColor() {
        return this.mShapeProp.getPenColor();
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.ICurve
    public int getCurveColorWheel() {
        return this.mShapeProp.getPenColorWheel();
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.ICurve
    public int getAlpha() {
        return (this.r && this.e == -1 && this.mDataState == 1) ? this.mShapeProp.getPenAlpha() / 3 : this.mShapeProp.getPenAlpha();
    }

    @Override // xc.engine.data.pagedata.DataCurve
    public void setPenSize(float f) {
        this.mShapeProp.setPenSize(f);
    }

    @Override // xc.engine.data.pagedata.DataCurve
    public void setPenAlpha(int i) {
        this.mShapeProp.setPenAlpha(i);
    }

    public int getFillColor() {
        return this.mShapeProp.getFillColor();
    }

    public int getFillAlpha() {
        return this.mShapeProp.getFillAlpha();
    }

    public int getStrokeType() {
        return this.mShapeProp.getStrokeType();
    }

    public void setStrokeType(byte b) {
        this.mShapeProp.setStrokeType(StrokeType.getStrokeType(b));
    }

    public boolean isLineOrArrow() {
        return this.mShapeProp.getShapeType() == 1 || this.mShapeProp.getShapeType() == 2;
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.BaseData
    public DataCurve clone() throws CloneNotSupportedException {
        DataShape dataShape = (DataShape) super.clone();
        dataShape.mShapeProp = new ShapeProp(this.mShapeProp);
        dataShape.mShapePath = new Path();
        dataShape.mScaledShapePath = new Path();
        float[] fArr = new float[4];
        dataShape.mAnchorPoint4 = fArr;
        System.arraycopy(this.mAnchorPoint4, 0, fArr, 0, 4);
        float[] fArr2 = new float[8];
        dataShape.mAnchorPoint8 = fArr2;
        System.arraycopy(this.mAnchorPoint8, 0, fArr2, 0, 8);
        dataShape.mMatrix1 = new Matrix(this.mMatrix1);
        PointF pointF = this.mFilletPointF;
        dataShape.mFilletPointF = new PointF(pointF.x, pointF.y);
        PointF pointF2 = this.maxEditBounds;
        dataShape.maxEditBounds = new PointF(pointF2.x, pointF2.y);
        PointF pointF3 = this.minEditBounds;
        dataShape.minEditBounds = new PointF(pointF3.x, pointF3.y);
        return dataShape;
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.BaseData, xc.engine.data.pagedata.IData
    public void writeData(DataOutputStream dataOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream2.writeShort(28);
        dataOutputStream2.writeByte(this.mShapeProp.getShapeType());
        dataOutputStream2.writeByte(this.mShapeProp.getStrokeType());
        dataOutputStream2.writeInt(this.mShapeProp.getPenColor());
        dataOutputStream2.writeFloat(this.mShapeProp.getPenSize());
        dataOutputStream2.writeInt(this.mShapeProp.getPenAlpha());
        dataOutputStream2.writeInt(this.mShapeProp.getPenColorWheel());
        dataOutputStream2.writeInt(this.mShapeProp.getFillColor());
        dataOutputStream2.writeInt(this.mShapeProp.getFillAlpha());
        dataOutputStream2.writeInt(this.mShapeProp.getFillColorWheel());
        dataOutputStream2.writeInt(this.mId);
        dataOutputStream2.writeInt(this.mCount);
        dataOutputStream2.writeFloat(this.mDrawRatio);
        dataOutputStream2.writeInt(this.mDataState);
        dataOutputStream2.writeShort(this.mCurveType);
        dataOutputStream2.writeLong(this.mTime);
        dataOutputStream2.writeFloat(this.mRotateDegrees);
        dataOutputStream2.writeInt(this.mItemPositionIndex);
        dataOutputStream2.writeFloat(this.mFilletPointF.x);
        dataOutputStream2.writeFloat(this.mFilletPointF.y);
        dataOutputStream2.writeFloat(this.mAnchorPoint4[0]);
        dataOutputStream2.writeFloat(this.mAnchorPoint4[1]);
        dataOutputStream2.writeFloat(this.mAnchorPoint4[2]);
        dataOutputStream2.writeFloat(this.mAnchorPoint4[3]);
        dataOutputStream2.writeFloat(this.mScaleRate);
        dataOutputStream2.writeFloat(this.mBasePoint.x);
        dataOutputStream2.writeFloat(this.mBasePoint.y);
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        dataOutputStream2.writeInt(curvePointList == null ? 0 : curvePointList.size());
        if (curvePointList == null || curvePointList.size() == 0) {
            byteArrayOutputStream.writeTo(dataOutputStream);
            dataOutputStream2.close();
            return;
        }
        for (int i = 0; i < curvePointList.size(); i++) {
            dataOutputStream2.writeFloat(curvePointList.getX(i));
            dataOutputStream2.writeFloat(curvePointList.getY(i));
        }
        byteArrayOutputStream.writeTo(dataOutputStream);
        dataOutputStream2.close();
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.BaseData, xc.engine.data.pagedata.IData
    public IData readData(DataInputStream dataInputStream, int i) throws IOException {
        if (i <= 0) {
            return this;
        }
        byte[] bArr = new byte[i];
        if (dataInputStream.read(bArr) == i) {
            DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
            int i2 = i / 8;
            clearPoints(this.mSavePoints);
            float[] fArr = new float[i2];
            float[] fArr2 = new float[i2];
            float[] fArr3 = new float[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                fArr[i3] = dataInputStream2.readFloat();
                fArr2[i3] = dataInputStream2.readFloat();
                fArr3[i3] = 0.5f;
            }
            CurvePointListOperator.insertPoints(this.mSavePoints, i2, fArr, fArr2, fArr3, (float[]) null, (long[]) null, (float[]) null, (float[]) null, (float[]) null, (short[]) null);
            dataInputStream2.close();
            return this;
        }
        throw new IOException("read point size error");
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.BaseData, xc.engine.data.pagedata.IData
    public int getDataSize() {
        int size = size(this.mSavePoints);
        if (size == 0) {
            return 106;
        }
        return (size * 8) + 106;
    }

    @Override // xc.engine.data.pagedata.DataCurve
    public CurveProp getCurveProp() {
        return this.mShapeProp;
    }

    @Override // xc.engine.data.pagedata.DataCurve
    public void dataScale(float f, float f2, float f3) {
        float f4 = this.mDrawRatio;
        float f5 = f / f4;
        float f6 = f2 / f4;
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        float[] fArr = new float[curvePointList.size()];
        float[] fArr2 = new float[curvePointList.size()];
        for (int i = 0; i < curvePointList.size(); i++) {
            fArr[i] = curvePointList.get(i).getX() + f5;
            fArr2[i] = curvePointList.get(i).getY() + f6;
            curvePointList.setX(i, fArr[i]);
            curvePointList.setY(i, fArr2[i]);
        }
        this.mMatrix1.reset();
        this.mMatrix1.postScale(f3, f3, f, f2);
        this.mMatrix1.reset();
        this.mMatrix1.postScale(f3, f3, f5, f6);
        this.mMatrix1.mapPoints(this.mAnchorPoint8);
        fixCoordinates();
        this.mMatrix1.mapPoints(this.mAnchorPoint4);
        PointF pointF = this.mFilletPointF;
        pointF.x *= f3;
        pointF.y *= f3;
        this.mShapePath.reset();
    }

    @Override // xc.engine.data.pagedata.DataCurve
    public boolean hasIntersectionWithRegion(Region region, float f, float f2) {
        int shapeType = this.mShapeProp.getShapeType();
        if (shapeType == 1) {
            this.mPathRegion = pathToRegion(f, f2, createRectanglePath());
        } else if (shapeType != 2) {
            this.mPathRegion = pathToRegion(f, f2, new Path(this.mShapePath));
        } else {
            Path path = new Path();
            path.addPath(createRectanglePath());
            this.mPathRegion = pathToRegion(f, f2, path);
        }
        this.mTouchRegion.setEmpty();
        this.mTouchRegion.set(region);
        this.mTouchRegion.op(this.mPathRegion, Region.Op.INTERSECT);
        return !this.mTouchRegion.isEmpty();
    }

    public boolean containsPoint(float f, float f2, float f3, float f4) {
        int shapeType = this.mShapeProp.getShapeType();
        Path path = new Path();
        if (shapeType == 1 || shapeType == 2) {
            path.set(createRectanglePath());
        } else {
            path.set(this.mShapePath);
        }
        PointF pointF = new PointF();
        if (!SkRegionHelper.LIMIT_RECT.contains(f, f2)) {
            pointF.set(SkRegionHelper.calculatePathOffsetForClip(path));
            f += pointF.x;
            f2 += pointF.y;
            f4 += pointF.y;
            path.offset(pointF.x, pointF.y);
        }
        if (shapeType != 1 && shapeType == 2) {
            Region regionPathToRegion = pathToRegion(f3, f4, path);
            this.mPathRegion = regionPathToRegion;
            int i = (int) (f - f3);
            int i2 = (int) (f2 - f4);
            if (regionPathToRegion.contains(i, i2)) {
                return true;
            }
            return this.mPathRegion.contains(i, i2);
        }
        this.mPathRegion = pathToRegion(f3, f4, path);
        return this.mPathRegion.contains((int) (f - f3), (int) (f2 - f4));
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.BaseData
    public void doMove(float f, float f2) {
        float f3 = f / this.mDrawRatio;
        float f4 = f2 / this.mDrawRatio;
        ICurvePointList curvePointList = getCurvePointList(this.mSavePoints);
        float[] fArr = new float[curvePointList.size()];
        float[] fArr2 = new float[curvePointList.size()];
        for (int i = 0; i < curvePointList.size(); i++) {
            fArr[i] = curvePointList.get(i).getX() + f3;
            fArr2[i] = curvePointList.get(i).getY() + f4;
            curvePointList.setX(i, fArr[i]);
            curvePointList.setY(i, fArr2[i]);
        }
        curvePointList.setNativeX(fArr, 0, curvePointList.size());
        curvePointList.setNativeY(fArr2, 0, curvePointList.size());
        this.mShapePath.offset(f, f2);
        this.mScaledShapePath.offset(f * this.mScaleRate, f2 * this.mScaleRate);
        this.mMatrix1.reset();
        this.mMatrix1.setTranslate(f3, f4);
        this.mMatrix1.mapPoints(this.mAnchorPoint8);
        fixCoordinates();
        this.mMatrix1.mapPoints(this.mAnchorPoint4);
    }

    @Override // xc.engine.data.pagedata.DataCurve, xc.engine.data.pagedata.BaseData
    public void calcBound(float[] fArr) {
        if (this.mDataState != 3) {
            RectF drawRect = getDrawRect();
            fArr[0] = Math.max(drawRect.left * (-1.0f), fArr[0]);
            fArr[1] = Math.max(drawRect.top * (-1.0f), fArr[1]);
            fArr[2] = Math.min(drawRect.right * (-1.0f), fArr[2]);
            fArr[3] = Math.min(drawRect.bottom * (-1.0f), fArr[3]);
        }
    }
}
