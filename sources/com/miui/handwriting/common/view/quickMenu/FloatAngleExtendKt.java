package com.miui.handwriting.common.view.quickMenu;

import kotlin.Metadata;

/* JADX INFO: compiled from: FloatAngleExtend.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u0012\u0010\u0003\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"addAngle", "", "angle", "minusAngle", "libHandWritingCommon_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class FloatAngleExtendKt {
    public static final float addAngle(float f, float f2) {
        float f3 = f + f2;
        return f3 > 360.0f ? f3 - 360 : f3;
    }

    public static final float minusAngle(float f, float f2) {
        float f3 = f - f2;
        return f3 < 0.0f ? f3 + 360 : f3;
    }
}
