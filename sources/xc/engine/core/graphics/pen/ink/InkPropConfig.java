package xc.engine.core.graphics.pen.ink;

import xc.engine.core.graphics.pen.BasePenPropConfig;

/* JADX INFO: loaded from: classes6.dex */
public class InkPropConfig extends BasePenPropConfig {
    public float mPenBaseSize;
    public float mPenSizeDiff;

    public InkPropConfig(float f, float f2) {
        super(f, f2);
        float f3 = 0.039f * f2;
        this.mPenBaseSize = f3;
        this.mPenSizeDiff = f2 - f3;
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public float getSpaceDistance(float f, float f2) {
        float fCalculatePenSize = calculatePenSize(f, f2);
        return fCalculatePenSize * Math.max(0.0526f, 0.22f - ((fCalculatePenSize - 1.86f) * 0.01f));
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public float calculatePenSize(float f, float f2) {
        float fPow = this.mPenBaseSize + (this.mPenSizeDiff * ((float) Math.pow(f, 1.34d - ((double) this.mSensitivity))));
        float f3 = this.mTipSize;
        float f4 = this.mPenSize;
        if (f3 > f4) {
            f3 = f4 / 3.14f;
        }
        return Math.max(f3 * this.mDrawRatio, Math.max(1.86f, fPow * this.mDrawRatio));
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public void updateProps(float f, float f2) {
        this.mPenSize = f2;
        this.mDrawRatio = f;
        float f3 = 0.039f * f2;
        this.mPenBaseSize = f3;
        this.mPenSizeDiff = f2 - f3;
    }
}
