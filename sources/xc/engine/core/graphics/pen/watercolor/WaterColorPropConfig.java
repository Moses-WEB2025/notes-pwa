package xc.engine.core.graphics.pen.watercolor;

import xc.engine.core.graphics.pen.BasePenPropConfig;

/* JADX INFO: loaded from: classes6.dex */
public class WaterColorPropConfig extends BasePenPropConfig {
    public WaterColorPropConfig(float f, float f2) {
        super(f, f2);
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public float getSpaceDistance(float f, float f2) {
        return calculatePenSize(f, f2) * 0.0818401f;
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public float calculatePenSize(float f, float f2) {
        return Math.max(2.312f, this.mPenSize * this.mDrawRatio * f);
    }
}
