package com.miui.handwriting.common.view.quickMenu.utils;

import android.view.View;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.FolmeEase;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;

/* JADX INFO: compiled from: HoverUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t¨\u0006\u000b"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/utils/HoverUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "appearAnimation", "", "animState", "Lmiuix/animation/IStateStyle;", "view", "Landroid/view/View;", "disappearAnimation", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HoverUtils {
    public static final HoverUtils INSTANCE = new HoverUtils();

    private HoverUtils() {
    }

    public final void appearAnimation(IStateStyle animState, View view) {
        Intrinsics.checkNotNullParameter(animState, "animState");
        Intrinsics.checkNotNullParameter(view, "view");
        AnimConfig animConfig = new AnimConfig();
        animConfig.setSpecial(ViewProperty.SCALE_X, FolmeEase.spring(0.78f, 0.34f), new float[0]);
        animConfig.setSpecial(ViewProperty.SCALE_Y, FolmeEase.spring(0.78f, 0.34f), new float[0]);
        IStateStyle to = animState.setTo(ViewProperty.SCALE_X, Float.valueOf(view.getScaleX())).setTo(ViewProperty.SCALE_Y, Float.valueOf(view.getScaleY()));
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        Float fValueOf = Float.valueOf(1.1f);
        to.to(viewProperty, fValueOf, ViewProperty.SCALE_Y, fValueOf, animConfig);
    }

    public final void disappearAnimation(IStateStyle animState, View view) {
        Intrinsics.checkNotNullParameter(animState, "animState");
        Intrinsics.checkNotNullParameter(view, "view");
        AnimConfig animConfig = new AnimConfig();
        animConfig.setSpecial(ViewProperty.SCALE_X, FolmeEase.spring(0.78f, 0.34f), new float[0]);
        animConfig.setSpecial(ViewProperty.SCALE_Y, FolmeEase.spring(0.78f, 0.34f), new float[0]);
        IStateStyle to = animState.setTo(ViewProperty.SCALE_X, Float.valueOf(view.getScaleX())).setTo(ViewProperty.SCALE_Y, Float.valueOf(view.getScaleY()));
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        Float fValueOf = Float.valueOf(1.0f);
        to.to(viewProperty, fValueOf, ViewProperty.SCALE_Y, fValueOf, animConfig);
    }
}
