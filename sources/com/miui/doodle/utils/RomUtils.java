package com.miui.doodle.utils;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.os.Build;

/* JADX INFO: compiled from: RomUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005J\u0006\u0010\u0006\u001a\u00020\u0005J\u0006\u0010\u0007\u001a\u00020\u0005J\u0006\u0010\b\u001a\u00020\u0005J\u0006\u0010\t\u001a\u00020\u0005¨\u0006\n"}, d2 = {"Lcom/miui/doodle/utils/RomUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isFoldDevice", "", "isPadDevice", "isPadMode", "isInternationalBuild", "isSmallFoldDevice", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RomUtils {
    public static final RomUtils INSTANCE = new RomUtils();

    private RomUtils() {
    }

    public final boolean isFoldDevice() {
        return Build.IS_FOLDABLE && !isSmallFoldDevice();
    }

    public final boolean isPadDevice() {
        return miui.os.Build.IS_TABLET;
    }

    public final boolean isPadMode() {
        return isFoldDevice() || isPadDevice();
    }

    public final boolean isInternationalBuild() {
        return Build.IS_INTERNATIONAL_BUILD;
    }

    public final boolean isSmallFoldDevice() {
        return Intrinsics.areEqual(android.os.Build.DEVICE, "ruyi");
    }
}
