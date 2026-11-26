package com.hiero.logger.base.logger.core;

import android.os.Process;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.hiero.logger.base.logger.interfaces.ILogger;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0003\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J$\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0016J$\u0010\u0017\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0016J$\u0010\u0018\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0016J$\u0010\u0019\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0016J$\u0010\u001a\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0016J$\u0010\u001b\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001e"}, d2 = {"Lcom/hiero/logger/base/logger/core/LoggerUtils;", "Lcom/hiero/logger/base/logger/interfaces/ILogger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "type", "Lcom/hiero/logger/base/logger/core/LoggerType;", "getType", "()Lcom/hiero/logger/base/logger/core/LoggerType;", "setType", "(Lcom/hiero/logger/base/logger/core/LoggerType;)V", "pid", "", "getPid", "()I", "logger", "getLogger", "()Lcom/hiero/logger/base/logger/interfaces/ILogger;", "e", "", "tag", "", CallMethod.ARG_EXTRA_STRING, "msg", AnimatedProperty.PROPERTY_NAME_W, DateTokenConverter.CONVERTER_KEY, IntegerTokenConverter.CONVERTER_KEY, "v", "trace", "tr", "", "logger_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LoggerUtils implements ILogger {
    public static final LoggerUtils INSTANCE = new LoggerUtils();
    private static LoggerType type = LoggerType.ALOG;
    private static final int pid = Process.myPid();

    /* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LoggerType.values().length];
            try {
                iArr[LoggerType.ALOG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LoggerType.PHOENIX_LOG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private LoggerUtils() {
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void d(String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        getLogger().d(tag, extra, "pid@" + pid + "|tid@" + Thread.currentThread().getId() + "-" + Thread.currentThread().getName() + "|" + msg);
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void e(String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        getLogger().e(tag, extra, "pid@" + pid + "|tid@" + Thread.currentThread().getId() + "-" + Thread.currentThread().getName() + "|" + msg);
    }

    public final ILogger getLogger() {
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            return ALogUtils.INSTANCE;
        }
        if (i == 2) {
            return PhoenixLogger.INSTANCE;
        }
        throw new NoWhenBranchMatchedException();
    }

    public final int getPid() {
        return pid;
    }

    public final LoggerType getType() {
        return type;
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void i(String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        getLogger().i(tag, extra, "pid@" + pid + "|tid@" + Thread.currentThread().getId() + "-" + Thread.currentThread().getName() + "|" + msg);
    }

    public final void setType(LoggerType loggerType) {
        Intrinsics.checkNotNullParameter(loggerType, "<set-?>");
        type = loggerType;
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void trace(String tag, String extra, Throwable tr) {
        Intrinsics.checkNotNullParameter(tr, "tr");
        getLogger().trace(tag, extra, tr);
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void v(String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        getLogger().v(tag, extra, "pid@" + pid + "|tid@" + Thread.currentThread().getId() + "-" + Thread.currentThread().getName() + "|" + msg);
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void w(String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        getLogger().w(tag, extra, "pid@" + pid + "|tid@" + Thread.currentThread().getId() + "-" + Thread.currentThread().getName() + "|" + msg);
    }
}
