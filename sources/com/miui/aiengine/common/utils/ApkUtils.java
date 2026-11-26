package com.miui.aiengine.common.utils;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ApkUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\t"}, d2 = {"Lcom/miui/aiengine/common/utils/ApkUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getVersionCode", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "getVersionName", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ApkUtils {
    public static final ApkUtils INSTANCE = new ApkUtils();

    private ApkUtils() {
    }

    public final String getVersionCode(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode());
    }

    public final String getVersionName(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    }
}
