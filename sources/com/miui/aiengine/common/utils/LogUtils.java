package com.miui.aiengine.common.utils;

import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.NetConfig;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LogUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007J\u001e\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007J\u001e\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007J\u001e\u0010\f\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007J\u001e\u0010\r\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007¨\u0006\u000e"}, d2 = {"Lcom/miui/aiengine/common/utils/LogUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "logDebug", "", "tag", "", "clz", "message", "logInfo", "logVerbose", "logWarn", "logError", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LogUtils {
    public static final LogUtils INSTANCE = new LogUtils();

    private LogUtils() {
    }

    public final void logDebug(String tag, String clz, String message) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(clz, "clz");
        Intrinsics.checkNotNullParameter(message, "message");
        if (NetConfig.INSTANCE.getLogAgent() == null) {
            Log.d(tag, clz + "|" + message);
            return;
        }
        Function4<Integer, String, String, String, Unit> logAgent = NetConfig.INSTANCE.getLogAgent();
        Intrinsics.checkNotNull(logAgent);
        logAgent.invoke(3, tag, "", clz + "|" + message);
    }

    public final void logInfo(String tag, String clz, String message) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(clz, "clz");
        Intrinsics.checkNotNullParameter(message, "message");
        if (NetConfig.INSTANCE.getLogAgent() == null) {
            Log.i(tag, clz + "|" + message);
            return;
        }
        Function4<Integer, String, String, String, Unit> logAgent = NetConfig.INSTANCE.getLogAgent();
        Intrinsics.checkNotNull(logAgent);
        logAgent.invoke(4, tag, "", clz + "|" + message);
    }

    public final void logVerbose(String tag, String clz, String message) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(clz, "clz");
        Intrinsics.checkNotNullParameter(message, "message");
        if (NetConfig.INSTANCE.getLogAgent() == null) {
            Log.v(tag, clz + "|" + message);
            return;
        }
        Function4<Integer, String, String, String, Unit> logAgent = NetConfig.INSTANCE.getLogAgent();
        Intrinsics.checkNotNull(logAgent);
        logAgent.invoke(2, tag, "", clz + "|" + message);
    }

    public final void logWarn(String tag, String clz, String message) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(clz, "clz");
        Intrinsics.checkNotNullParameter(message, "message");
        if (NetConfig.INSTANCE.getLogAgent() == null) {
            Log.w(tag, clz + "|" + message);
            return;
        }
        Function4<Integer, String, String, String, Unit> logAgent = NetConfig.INSTANCE.getLogAgent();
        Intrinsics.checkNotNull(logAgent);
        logAgent.invoke(5, tag, "", clz + "|" + message);
    }

    public final void logError(String tag, String clz, String message) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(clz, "clz");
        Intrinsics.checkNotNullParameter(message, "message");
        if (NetConfig.INSTANCE.getLogAgent() == null) {
            Log.e(tag, clz + "|" + message);
            return;
        }
        Function4<Integer, String, String, String, Unit> logAgent = NetConfig.INSTANCE.getLogAgent();
        Intrinsics.checkNotNull(logAgent);
        logAgent.invoke(6, tag, "", clz + "|" + message);
    }
}
