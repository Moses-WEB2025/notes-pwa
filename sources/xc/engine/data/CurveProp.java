package xc.engine.data;

import java.util.Objects;
import xc.engine.core.graphics.pen.PenType;

/* JADX INFO: loaded from: classes6.dex */
public class CurveProp implements Cloneable {
    private PenType mPenType;
    private float mSensitivity;
    private int penAlpha;
    private int penColor;
    private int penColorWheel;
    private float penSize;
    private float tipSize;

    public CurveProp(PenType penType, int i, float f, int i2) {
        this.penColorWheel = -16777216;
        this.mPenType = penType;
        this.penColor = i;
        this.penSize = f;
        this.penAlpha = i2;
        this.mSensitivity = 1.0f;
        this.tipSize = 0.0f;
    }

    public CurveProp(CurveProp curveProp) {
        this.mPenType = curveProp.mPenType;
        this.penColor = curveProp.penColor;
        this.penSize = curveProp.penSize;
        this.penAlpha = curveProp.penAlpha;
        this.penColorWheel = curveProp.penColorWheel;
        this.mSensitivity = curveProp.mSensitivity;
        this.tipSize = curveProp.tipSize;
    }

    public CurveProp clone() throws CloneNotSupportedException {
        return (CurveProp) super.clone();
    }

    public PenType getPenType() {
        return this.mPenType;
    }

    public void setPenType(PenType penType) {
        this.mPenType = penType;
    }

    public int getPenColor() {
        return this.penColor;
    }

    public void setPenColor(int i) {
        this.penColor = i;
    }

    public int getPenColorWheel() {
        return this.penColorWheel;
    }

    public void setPenColorWheel(int i) {
        this.penColorWheel = i;
    }

    public float getPenSize() {
        return this.penSize;
    }

    public void setPenSize(float f) {
        this.penSize = f;
    }

    public int getPenAlpha() {
        return this.penAlpha;
    }

    public void setPenAlpha(int i) {
        this.penAlpha = i;
    }

    public float getSensitivity() {
        return this.mSensitivity;
    }

    public void setSensitivity(float f) {
        this.mSensitivity = f;
    }

    public float getTipSize() {
        return this.tipSize;
    }

    public void setTipSize(float f) {
        if (f > 4.0f) {
            f = 4.0f;
        }
        this.tipSize = f;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            CurveProp curveProp = (CurveProp) obj;
            if (this.penColor == curveProp.penColor && Float.compare(curveProp.penSize, this.penSize) == 0 && this.penAlpha == curveProp.penAlpha && this.mPenType == curveProp.mPenType && curveProp.penColorWheel == this.penColorWheel && Float.compare(curveProp.mSensitivity, this.mSensitivity) == 0 && Float.compare(curveProp.tipSize, this.tipSize) == 0) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mPenType, Integer.valueOf(this.penColor), Float.valueOf(this.penSize), Integer.valueOf(this.penAlpha), Integer.valueOf(this.penColorWheel), Float.valueOf(this.mSensitivity), Float.valueOf(this.tipSize));
    }
}
