package com.miui.common.tool;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import miuix.device.DeviceUtils;

/* JADX INFO: compiled from: LiteUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\bR\u0012\u0010\t\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\bR\u0012\u0010\n\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\bR\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\bR\u001a\u0010\f\u001a\u00020\u00078FX\u0087\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u0003\u001a\u0004\b\f\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u00078FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u0003\u001a\u0004\b\u000f\u0010\u000eR\u001a\u0010\u0011\u001a\u00020\u00078FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0012\u0010\u0003\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0013\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u000eR\u001a\u0010\u0014\u001a\u00020\u00078FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0015\u0010\u0003\u001a\u0004\b\u0014\u0010\u000eR\u001a\u0010\u0016\u001a\u00020\u00078FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0017\u0010\u0003\u001a\u0004\b\u0016\u0010\u000e¨\u0006\u0018"}, d2 = {"Lcom/miui/common/tool/LiteUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "IS_SUPER_LITE", "", "Ljava/lang/Boolean;", "IS_NEW_LITE", "IS_MIDDLE", "IS_LITE", "isSuperLite", "isSuperLite$annotations", "()Z", "isNewLiteOrMiddle", "isNewLiteOrMiddle$annotations", "isLiteOrMiddle", "isLiteOrMiddle$annotations", "isNewLite", "isMiddle", "isMiddle$annotations", "isLite", "isLite$annotations", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LiteUtils {
    public static final LiteUtils INSTANCE = new LiteUtils();
    private static Boolean IS_LITE = null;
    private static Boolean IS_MIDDLE = null;
    private static Boolean IS_NEW_LITE = null;
    private static Boolean IS_SUPER_LITE = null;
    private static final String TAG = "LiteUtils";

    @JvmStatic
    public static /* synthetic */ void isLite$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isLiteOrMiddle$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isMiddle$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isNewLiteOrMiddle$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isSuperLite$annotations() {
    }

    private LiteUtils() {
    }

    public static final boolean isSuperLite() {
        if (IS_SUPER_LITE == null) {
            IS_SUPER_LITE = Boolean.valueOf(DeviceUtils.isMiuiLiteV2());
        }
        Boolean bool = IS_SUPER_LITE;
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }

    public static final boolean isNewLiteOrMiddle() {
        return INSTANCE.isNewLite() || isMiddle();
    }

    public static final boolean isLiteOrMiddle() {
        return isLite() || isMiddle();
    }

    public final boolean isNewLite() {
        if (IS_NEW_LITE == null) {
            IS_NEW_LITE = Boolean.valueOf(isLite() && !DeviceUtils.isLiteV1Stock());
        }
        Boolean bool = IS_NEW_LITE;
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }

    public static final boolean isMiddle() {
        if (IS_MIDDLE == null) {
            IS_MIDDLE = Boolean.valueOf(DeviceUtils.isMiuiMiddle());
        }
        Boolean bool = IS_MIDDLE;
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }

    public static final boolean isLite() {
        if (IS_LITE == null) {
            IS_LITE = Boolean.valueOf(DeviceUtils.isMiuiLiteRom());
        }
        Boolean bool = IS_LITE;
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }
}
