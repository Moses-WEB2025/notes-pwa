package com.miui.handwriting.common.view.quickMenu.utils;

import android.content.Context;
import android.view.WindowManager;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.tools.UIUtils;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import miuix.core.util.EnvStateManager;
import miuix.core.util.WindowBaseInfo;

/* JADX INFO: compiled from: ScreenModeHelper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000bH\u0002J\b\u0010\f\u001a\u00020\tH\u0002J\b\u0010\r\u001a\u00020\tH\u0002J\u0016\u0010\f\u001a\u00020\t2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000bH\u0002J\u0014\u0010\r\u001a\u00020\t2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000bJ\u0006\u0010\u000f\u001a\u00020\tJ\u0006\u0010\u0010\u001a\u00020\tJ\u0006\u0010\u0011\u001a\u00020\tJ\u0006\u0010\u0012\u001a\u00020\tJ\u0006\u0010\u0013\u001a\u00020\tJ\u0006\u0010\u0014\u001a\u00020\tJ\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/utils/ScreenModeHelper;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mWindowBaseInfo", "Lmiuix/core/util/WindowBaseInfo;", "isLandSpace", "", "modeMethod", "Lkotlin/Function0;", "isPortraitHorizontal", "isPortraitVertical", "lambda", "isFreeWindow_9_16", "isFreeWindow_4_3", "isFreeWindow_3_4", "isSplitScreen_1_3", "isSplitScreen_1_2", "isSplitScreen_2_3", "getQuickMenuScale", "Lcom/miui/handwriting/common/view/quickMenu/utils/QuickMenuScale;", "getPopupColorPickerScale", "transformScale", "", "scale", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ScreenModeHelper {
    private final Context context;
    private WindowBaseInfo mWindowBaseInfo;

    /* JADX INFO: compiled from: ScreenModeHelper.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[QuickMenuScale.values().length];
            try {
                iArr[QuickMenuScale.ZERO_POINT_NINE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[QuickMenuScale.ONE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[QuickMenuScale.ZERO_POINT_EIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[QuickMenuScale.ZERO_POINT_SEVEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ScreenModeHelper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        WindowBaseInfo windowInfo = EnvStateManager.getWindowInfo(context);
        Intrinsics.checkNotNullExpressionValue(windowInfo, "getWindowInfo(...)");
        this.mWindowBaseInfo = windowInfo;
    }

    private final boolean isLandSpace() {
        Object systemService = this.context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        int rotation = ((WindowManager) systemService).getDefaultDisplay().getRotation();
        if (rotation != 0) {
            return rotation == 1 || rotation == 2;
        }
        return false;
    }

    private final boolean isLandSpace(Function0<Boolean> modeMethod) {
        return isLandSpace() && modeMethod.invoke().booleanValue();
    }

    private final boolean isPortraitHorizontal() {
        return !isLandSpace() && UIUtils.getWindowHeight(this.context) == UIUtils.getRealScreenSize(this.context).heightPixels;
    }

    private final boolean isPortraitVertical() {
        return !isLandSpace() && UIUtils.getWindowHeight(this.context) < UIUtils.getRealScreenSize(this.context).heightPixels;
    }

    private final boolean isPortraitHorizontal(Function0<Boolean> lambda) {
        return isPortraitHorizontal() && lambda.invoke().booleanValue();
    }

    public final boolean isPortraitVertical(Function0<Boolean> lambda) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        return isPortraitVertical() && lambda.invoke().booleanValue();
    }

    public final boolean isFreeWindow_9_16() {
        return this.mWindowBaseInfo.windowMode == 8193;
    }

    public final boolean isFreeWindow_4_3() {
        return this.mWindowBaseInfo.windowMode == 8194;
    }

    public final boolean isFreeWindow_3_4() {
        return this.mWindowBaseInfo.windowMode == 8195;
    }

    public final boolean isSplitScreen_1_3() {
        return this.mWindowBaseInfo.windowMode == 4097;
    }

    public final boolean isSplitScreen_1_2() {
        return this.mWindowBaseInfo.windowMode == 4098;
    }

    public final boolean isSplitScreen_2_3() {
        return this.mWindowBaseInfo.windowMode == 4099;
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.quickMenu.utils.ScreenModeHelper$getQuickMenuScale$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ScreenModeHelper.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    static final /* synthetic */ class C02441 extends FunctionReferenceImpl implements Function0<Boolean> {
        C02441(Object obj) {
            super(0, obj, ScreenModeHelper.class, "isSplitScreen_1_2", "isSplitScreen_1_2()Z", 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return Boolean.valueOf(((ScreenModeHelper) this.receiver).isSplitScreen_1_2());
        }
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.quickMenu.utils.ScreenModeHelper$getQuickMenuScale$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ScreenModeHelper.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    static final /* synthetic */ class C02452 extends FunctionReferenceImpl implements Function0<Boolean> {
        C02452(Object obj) {
            super(0, obj, ScreenModeHelper.class, "isSplitScreen_1_3", "isSplitScreen_1_3()Z", 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return Boolean.valueOf(((ScreenModeHelper) this.receiver).isSplitScreen_1_3());
        }
    }

    public final QuickMenuScale getQuickMenuScale() {
        if (isPortraitHorizontal(new C02441(this)) || isLandSpace(new C02452(this)) || isFreeWindow_3_4()) {
            return QuickMenuScale.ZERO_POINT_EIGHT;
        }
        if (isFreeWindow_9_16()) {
            return QuickMenuScale.ZERO_POINT_SEVEN;
        }
        return QuickMenuScale.ONE;
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.quickMenu.utils.ScreenModeHelper$getPopupColorPickerScale$1, reason: invalid class name */
    /* JADX INFO: compiled from: ScreenModeHelper.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    static final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0<Boolean> {
        AnonymousClass1(Object obj) {
            super(0, obj, ScreenModeHelper.class, "isSplitScreen_1_2", "isSplitScreen_1_2()Z", 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return Boolean.valueOf(((ScreenModeHelper) this.receiver).isSplitScreen_1_2());
        }
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.quickMenu.utils.ScreenModeHelper$getPopupColorPickerScale$2, reason: invalid class name */
    /* JADX INFO: compiled from: ScreenModeHelper.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    static final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function0<Boolean> {
        AnonymousClass2(Object obj) {
            super(0, obj, ScreenModeHelper.class, "isSplitScreen_1_3", "isSplitScreen_1_3()Z", 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return Boolean.valueOf(((ScreenModeHelper) this.receiver).isSplitScreen_1_3());
        }
    }

    public final QuickMenuScale getPopupColorPickerScale() {
        if (isPortraitHorizontal(new AnonymousClass1(this)) || isLandSpace(new AnonymousClass2(this))) {
            return QuickMenuScale.ZERO_POINT_NINE;
        }
        if (isFreeWindow_9_16() || isFreeWindow_3_4()) {
            return QuickMenuScale.ZERO_POINT_EIGHT;
        }
        return QuickMenuScale.ONE;
    }

    public final float transformScale(QuickMenuScale scale) {
        Intrinsics.checkNotNullParameter(scale, "scale");
        int i = WhenMappings.$EnumSwitchMapping$0[scale.ordinal()];
        if (i == 1) {
            return 0.9f;
        }
        if (i == 2) {
            return 1.0f;
        }
        if (i == 3) {
            return 0.8f;
        }
        if (i == 4) {
            return 0.7f;
        }
        throw new NoWhenBranchMatchedException();
    }
}
