package xc.engine.core.graphics.pen;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BasePenPropConfig {
    public float mDrawRatio;
    public float mPenSize;
    public float mSensitivity = 0.5f;
    public float mTipSize;

    public abstract float calculatePenSize(float f, float f2);

    public abstract float getSpaceDistance(float f, float f2);

    public BasePenPropConfig(float f, float f2) {
        this.mPenSize = f2;
        this.mDrawRatio = f;
    }

    public void updateProps(float f, float f2) {
        this.mPenSize = f2;
        this.mDrawRatio = f;
    }
}
