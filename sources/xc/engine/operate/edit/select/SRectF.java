package xc.engine.operate.edit.select;

import android.graphics.RectF;

/* JADX INFO: loaded from: classes6.dex */
public class SRectF extends RectF {
    public float mAngle;
    public int mSelectHasCode;

    public SRectF() {
    }

    public SRectF(float f, float f2, float f3, float f4, float f5) {
        super(f, f2, f3, f4);
        this.mAngle = f5;
    }

    public SRectF(RectF rectF, float f) {
        super(rectF.left, rectF.top, rectF.right, rectF.bottom);
        this.mAngle = f;
    }

    public float getAngle() {
        return this.mAngle;
    }

    public void setAngle(float f) {
        this.mAngle = f;
    }

    public void setSelectHasCode(int i) {
        this.mSelectHasCode = i;
    }

    public boolean isRectEmpty() {
        return ((double) this.left) == 0.0d && ((double) this.right) == 0.0d && ((double) this.top) == 0.0d && ((double) this.bottom) == 0.0d && ((double) this.mAngle) == 0.0d;
    }

    public double[] getRotatedRectangleVertices() {
        double radians = Math.toRadians(this.mAngle);
        double[] dArr = new double[8];
        dArr[0] = this.left;
        dArr[1] = this.top;
        dArr[2] = this.right;
        dArr[3] = this.top;
        dArr[4] = this.right;
        dArr[5] = this.bottom;
        dArr[6] = this.left;
        dArr[7] = this.bottom;
        for (int i = 0; i < 4; i++) {
            int i2 = i * 2;
            double d = dArr[i2];
            int i3 = i2 + 1;
            double d2 = dArr[i3];
            dArr[i2] = (Math.cos(radians) * d) - (Math.sin(radians) * d2);
            dArr[i3] = (d * Math.sin(radians)) + (d2 * Math.cos(radians));
        }
        return dArr;
    }
}
