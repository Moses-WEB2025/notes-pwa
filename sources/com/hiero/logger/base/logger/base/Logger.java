package com.hiero.logger.base.logger.base;

import com.hiero.logger.base.logger.core.LoggerUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0016J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0016J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0016J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/hiero/logger/base/logger/base/Logger;", "", "tag", "", "clz", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;)V", "logDebug", "", "msg", "logVerbose", "logInfo", "logWarn", "logError", "logger_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class Logger {
    private final String clz;
    private final String tag;

    public Logger(String tag, String str) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        this.tag = tag;
        this.clz = str;
    }

    public void logDebug(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        LoggerUtils loggerUtils = LoggerUtils.INSTANCE;
        String str = this.tag;
        String simpleName = this.clz;
        if (simpleName == null) {
            simpleName = getClass().getSimpleName();
        }
        loggerUtils.d(str, simpleName, msg);
    }

    public void logError(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        LoggerUtils loggerUtils = LoggerUtils.INSTANCE;
        String str = this.tag;
        String simpleName = this.clz;
        if (simpleName == null) {
            simpleName = getClass().getSimpleName();
        }
        loggerUtils.e(str, simpleName, msg);
    }

    public void logInfo(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        LoggerUtils loggerUtils = LoggerUtils.INSTANCE;
        String str = this.tag;
        String simpleName = this.clz;
        if (simpleName == null) {
            simpleName = getClass().getSimpleName();
        }
        loggerUtils.i(str, simpleName, msg);
    }

    public void logVerbose(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        LoggerUtils loggerUtils = LoggerUtils.INSTANCE;
        String str = this.tag;
        String simpleName = this.clz;
        if (simpleName == null) {
            simpleName = getClass().getSimpleName();
        }
        loggerUtils.v(str, simpleName, msg);
    }

    public void logWarn(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        LoggerUtils loggerUtils = LoggerUtils.INSTANCE;
        String str = this.tag;
        String simpleName = this.clz;
        if (simpleName == null) {
            simpleName = getClass().getSimpleName();
        }
        loggerUtils.w(str, simpleName, msg);
    }

    public /* synthetic */ Logger(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2);
    }
}
