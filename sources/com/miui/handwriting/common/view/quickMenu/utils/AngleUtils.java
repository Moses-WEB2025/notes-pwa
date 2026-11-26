package com.miui.handwriting.common.view.quickMenu.utils;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: AngleUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005J\u000e\u0010\u0007\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005J\u0016\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005¨\u0006\u000b"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/utils/AngleUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "adjustAngle", "", "angle", "adjustAngleToMath", "computeExtensionAngle", "circleRadius", "arcRadius", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AngleUtils {
    public static final AngleUtils INSTANCE = new AngleUtils();

    public final float adjustAngle(float angle) {
        return angle > 90.0f ? angle - 90 : 360 - (90 - angle);
    }

    public final float adjustAngleToMath(float angle) {
        return angle <= 90.0f ? 90 - angle : 90 + (360 - angle);
    }

    private AngleUtils() {
    }

    public final float computeExtensionAngle(float circleRadius, float arcRadius) {
        return (float) Math.toDegrees(Math.asin(((double) circleRadius) / ((double) arcRadius)));
    }
}
