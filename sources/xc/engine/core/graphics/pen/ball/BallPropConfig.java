package xc.engine.core.graphics.pen.ball;

import xc.engine.core.graphics.pen.BasePenPropConfig;

/* JADX INFO: loaded from: classes6.dex */
public class BallPropConfig extends BasePenPropConfig {
    public BallPropConfig(float f, float f2) {
        super(f, f2);
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public float getSpaceDistance(float f, float f2) {
        float fCalculatePenSize = calculatePenSize(f, f2);
        return fCalculatePenSize * Math.max(0.176f, 0.194353f - ((fCalculatePenSize - 2.2f) * 0.01f));
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public float calculatePenSize(float f, float f2) {
        return Math.max(2.2f, this.mPenSize * this.mDrawRatio) + 0.8f;
    }
}
