package xc.engine.core.graphics.pen.pencil;

import xc.engine.core.graphics.pen.BasePenPropConfig;

/* JADX INFO: loaded from: classes6.dex */
public class PencilPropConfig extends BasePenPropConfig {
    public float mBasePenSize;
    public float mPenSizeDiff;

    public PencilPropConfig(float f, float f2) {
        super(f, f2);
        float f3 = 0.48f * f2;
        this.mBasePenSize = f3;
        this.mPenSizeDiff = f2 - f3;
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public float getSpaceDistance(float f, float f2) {
        return calculatePenSize(f, f2) * 0.2862357f * (this.mBasePenSize < 1.0f ? 0.5f : 0.65f);
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public float calculatePenSize(float f, float f2) {
        return (float) Math.max(2.200000047683716d, ((double) this.mBasePenSize) + (((double) this.mPenSizeDiff) * Math.cos(((double) (this.mSensitivity * f2)) - 1.2566370614359172d) * 2.621682643890381d * ((double) this.mDrawRatio)));
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public void updateProps(float f, float f2) {
        this.mPenSize = f2;
        this.mDrawRatio = f;
        float f3 = 0.48f * f2;
        this.mBasePenSize = f3;
        this.mPenSizeDiff = f2 - f3;
    }
}
