package xc.engine.core.graphics.pen.mark;

import xc.engine.core.graphics.pen.BasePenPropConfig;

/* JADX INFO: loaded from: classes6.dex */
public class MarkPropConfig extends BasePenPropConfig {
    public static int screenRotation;

    public MarkPropConfig(float f, float f2) {
        super(f, f2);
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public float getSpaceDistance(float f, float f2) {
        return calculatePenSize(f, f2) * 0.02232f;
    }

    @Override // xc.engine.core.graphics.pen.BasePenPropConfig
    public float calculatePenSize(float f, float f2) {
        return Math.max(2.0f, this.mPenSize * this.mDrawRatio);
    }
}
