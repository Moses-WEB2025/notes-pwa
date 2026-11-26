package xc.richtext.operate.scale;

import android.graphics.PointF;
import xc.richtext.data.bean.SpannedScaleInfo;

/* JADX INFO: loaded from: classes2.dex */
public interface IScaleOperator {
    void doScaleReal(float f, PointF pointF, PointF pointF2);

    void endScaleReal();

    float getDrawRatio();

    SpannedScaleInfo getSpannedScaleInfo();

    void setUnLimit(boolean z);

    void setVisibleSize(float f, float f2);

    void updateSpannedScaleInfo(float f, PointF pointF);
}
