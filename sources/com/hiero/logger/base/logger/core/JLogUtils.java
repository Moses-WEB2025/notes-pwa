package com.hiero.logger.base.logger.core;

import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.hiero.logger.base.logger.interfaces.ILogger;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0003\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J$\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0016J$\u0010\n\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0016J$\u0010\u000b\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0016J$\u0010\f\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0016J$\u0010\r\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0016J$\u0010\u000e\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lcom/hiero/logger/base/logger/core/JLogUtils;", "Lcom/hiero/logger/base/logger/interfaces/ILogger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "e", "", "tag", "", CallMethod.ARG_EXTRA_STRING, "msg", AnimatedProperty.PROPERTY_NAME_W, DateTokenConverter.CONVERTER_KEY, IntegerTokenConverter.CONVERTER_KEY, "v", "trace", "tr", "", "logger_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class JLogUtils implements ILogger {
    public static final JLogUtils INSTANCE = new JLogUtils();

    private JLogUtils() {
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void d(String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (extra == null) {
            extra = "JLogUtils";
        }
        System.out.println((Object) ("[" + tag + "][" + extra + "][" + msg + "]"));
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void e(String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (extra == null) {
            extra = "JLogUtils";
        }
        System.out.println((Object) ("[" + tag + "][" + extra + "][" + msg + "]"));
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void i(String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (extra == null) {
            extra = "JLogUtils";
        }
        System.out.println((Object) ("[" + tag + "][" + extra + "][" + msg + "]"));
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void trace(String tag, String extra, Throwable tr) {
        Intrinsics.checkNotNullParameter(tr, "tr");
        if (extra == null) {
            extra = "JLogUtils";
        }
        System.out.println((Object) ("[" + tag + "][" + extra + "][exception -> " + tr.getMessage() + "]"));
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void v(String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (extra == null) {
            extra = "JLogUtils";
        }
        System.out.println((Object) ("[" + tag + "][" + extra + "][" + msg + "]"));
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void w(String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (extra == null) {
            extra = "JLogUtils";
        }
        System.out.println((Object) ("[" + tag + "][" + extra + "][" + msg + "]"));
    }
}
