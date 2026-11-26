package com.miui.aiengine.common.utils;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.NetConfig;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Logger.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0011\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003J\u000e\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003J\u000e\u0010\u000b\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/aiengine/common/utils/Logger;", "", "moduleName", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "logDebug", "", "msg", "logVerbose", "logInfo", "logError", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class Logger {
    private final String moduleName;

    public Logger() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public Logger(String moduleName) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        this.moduleName = moduleName;
    }

    public /* synthetic */ Logger(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? NetConfig.TAG : str);
    }

    public final void logDebug(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (NetConfig.INSTANCE.getLogOn()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            LogUtils.INSTANCE.logDebug(this.moduleName, getClass().getSimpleName() + "->" + stackTraceElement.getMethodName(), msg);
        }
    }

    public final void logVerbose(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (NetConfig.INSTANCE.getLogOn()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            LogUtils.INSTANCE.logVerbose(this.moduleName, getClass().getSimpleName() + "->" + stackTraceElement.getMethodName(), msg);
        }
    }

    public final void logInfo(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (NetConfig.INSTANCE.getLogOn()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            LogUtils.INSTANCE.logInfo(this.moduleName, getClass().getSimpleName() + "->" + stackTraceElement.getMethodName(), msg);
        }
    }

    public final void logError(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (NetConfig.INSTANCE.getLogOn()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            LogUtils.INSTANCE.logError(this.moduleName, getClass().getSimpleName() + "->" + stackTraceElement.getMethodName(), msg);
        }
    }
}
