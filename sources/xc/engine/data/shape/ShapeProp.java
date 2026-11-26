package xc.engine.data.shape;

import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;

/* JADX INFO: loaded from: classes6.dex */
public class ShapeProp extends CurveProp {
    public int fillAlpha;
    public int fillColor;
    public int fillColorWheel;
    public int mShapeType;
    public int strokeType;

    public ShapeProp(int i, float f, int i2) {
        super(PenType.INK, i, f, i2);
        this.fillAlpha = 255;
        this.fillColorWheel = -16777216;
        this.strokeType = 0;
    }

    public ShapeProp(int i, int i2, float f, int i3, int i4, int i5, int i6) {
        super(PenType.INK, i2, f, i3);
        this.fillColorWheel = -16777216;
        this.mShapeType = i;
        this.fillColor = i4;
        this.fillAlpha = i5;
        this.strokeType = i6;
    }

    public ShapeProp(ShapeProp shapeProp) {
        super(shapeProp);
        this.mShapeType = shapeProp.mShapeType;
        this.fillColor = shapeProp.fillColor;
        this.fillAlpha = shapeProp.fillAlpha;
        this.fillColorWheel = shapeProp.fillColorWheel;
        this.strokeType = shapeProp.strokeType;
    }

    public ShapeProp(CurveProp curveProp) {
        super(curveProp);
        this.fillAlpha = 255;
        this.fillColorWheel = -16777216;
        this.strokeType = 0;
    }

    public int getShapeType() {
        return this.mShapeType;
    }

    public void setShapeType(int i) {
        this.mShapeType = i;
    }

    public int getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(int i) {
        this.fillColor = i;
    }

    public int getFillAlpha() {
        return this.fillAlpha;
    }

    public void setFillAlpha(int i) {
        this.fillAlpha = i;
    }

    public int getFillColorWheel() {
        return this.fillColorWheel;
    }

    public void setFillColorWheel(int i) {
        this.fillColorWheel = i;
    }

    public int getStrokeType() {
        return this.strokeType;
    }

    public void setStrokeType(int i) {
        this.strokeType = i;
    }

    @Override // xc.engine.data.CurveProp
    public float getPenSize() {
        if (super.getPenSize() > 10.0f) {
            return 10.0f;
        }
        int i = this.mShapeType;
        return (i == 1 || i == 2) ? Math.max(super.getPenSize(), 2.0f) : super.getPenSize();
    }
}
