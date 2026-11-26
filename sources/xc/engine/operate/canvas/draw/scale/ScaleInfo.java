package xc.engine.operate.canvas.draw.scale;

import android.graphics.PointF;
import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes6.dex */
public class ScaleInfo {
    private final PointF mScaleCentre;
    private float mScaleRate;
    private final PointF mTranslate;

    public ScaleInfo() {
        this.mScaleRate = 1.0f;
        this.mTranslate = new PointF();
        this.mScaleCentre = new PointF();
    }

    public ScaleInfo(float f, PointF pointF, PointF pointF2) {
        this.mScaleRate = f;
        this.mTranslate = pointF;
        this.mScaleCentre = pointF2;
    }

    public float getScaleRate() {
        return this.mScaleRate;
    }

    public void setScaleRate(float f) {
        this.mScaleRate = f;
    }

    public PointF getTranslate() {
        return this.mTranslate;
    }

    public PointF getScaleCenter() {
        return this.mScaleCentre;
    }

    public String toString() {
        return "ScaleInfo{mScaleRate=" + this.mScaleRate + ", mTranslate=" + this.mTranslate + ", mScaleCentre=" + this.mScaleCentre + CoreConstants.CURLY_RIGHT;
    }
}
