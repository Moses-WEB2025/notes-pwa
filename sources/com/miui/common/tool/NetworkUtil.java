package com.miui.common.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NetworkUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"}, d2 = {"Lcom/miui/common/tool/NetworkUtil;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isNetworkAvailable", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NetworkUtil {
    public static final NetworkUtil INSTANCE = new NetworkUtil();

    private NetworkUtil() {
    }

    @JvmStatic
    public static final boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo;
        Intrinsics.checkNotNullParameter(context, "context");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }
}
