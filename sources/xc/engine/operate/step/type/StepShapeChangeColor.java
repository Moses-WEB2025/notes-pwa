package xc.engine.operate.step.type;

import android.graphics.RectF;
import android.util.SparseArray;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.track.shape.ShapeCurveInfo;

/* JADX INFO: loaded from: classes6.dex */
public class StepShapeChangeColor implements IStep {
    public PenEngine a;
    public int b;
    public int c;
    public int d;
    public float e;
    public int f;
    public byte g;
    public RectF h;
    public DataShape i;
    public final int j;
    public final int k;
    public final int l;
    public final float m;
    public final int n;
    public final byte o;

    @Override // xc.engine.operate.step.type.IStep
    public void resetContext(PenEngine penEngine) {
    }

    @Override // xc.engine.operate.step.type.IStep
    public void serialize() {
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
    }

    public StepShapeChangeColor(PenEngine penEngine, DataShape dataShape) {
        this.a = penEngine;
        this.i = dataShape;
        this.j = dataShape.getFillColor();
        this.k = dataShape.getFillAlpha();
        this.n = dataShape.getAlpha();
        this.l = dataShape.getCurveColor();
        this.m = dataShape.getCurvePenWidth();
        this.o = (byte) dataShape.getStrokeType();
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        return this.i.pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void unserialize(SparseArray<DataCurve> sparseArray) {
        DataCurve dataCurve;
        if (sparseArray == null || sparseArray.size() == 0 || (dataCurve = sparseArray.get(this.i.mCount)) == null || !(dataCurve instanceof DataShape)) {
            return;
        }
        this.i = (DataShape) dataCurve;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF undo() {
        this.i.mShapeProp.setFillAlpha(this.k);
        this.i.mShapeProp.setFillColor(this.j);
        this.i.mShapeProp.setPenAlpha(this.n);
        this.i.mShapeProp.setPenColor(this.l);
        this.i.setStrokeType(this.o);
        if (this.i.mShapeProp.getShapeType() == 2) {
            float penSize = this.i.mShapeProp.getPenSize();
            float f = this.m;
            if (penSize != f) {
                this.i.mShapeProp.setPenSize(f);
                synchronized (this.i.mLockObj) {
                    if (this.a.mTouchEventOperator.isScale()) {
                        new ShapeCurveInfo(this.a, this.i, true, false).optimizeAndTransformShapePath();
                    }
                    new ShapeCurveInfo(this.a, this.i, false, false).optimizeAndTransformShapePath();
                }
                return this.h;
            }
        }
        this.i.mShapeProp.setPenSize(this.m);
        return this.h;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        this.i.mShapeProp.setFillColor(this.c);
        this.i.mShapeProp.setFillAlpha(this.b);
        this.i.mShapeProp.setPenAlpha(this.f);
        this.i.mShapeProp.setPenColor(this.d);
        this.i.setStrokeType(this.g);
        if (this.i.mShapeProp.getShapeType() == 2) {
            float penSize = this.i.mShapeProp.getPenSize();
            float f = this.e;
            if (penSize != f) {
                this.i.mShapeProp.setPenSize(f);
                synchronized (this.i.mLockObj) {
                    if (this.a.mTouchEventOperator.isScale()) {
                        new ShapeCurveInfo(this.a, this.i, true, false).optimizeAndTransformShapePath();
                    }
                    new ShapeCurveInfo(this.a, this.i, false, false).optimizeAndTransformShapePath();
                }
                return this.h;
            }
        }
        this.i.mShapeProp.setPenSize(this.e);
        return this.h;
    }
}
