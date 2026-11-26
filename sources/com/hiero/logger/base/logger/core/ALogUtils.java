package com.hiero.logger.base.logger.core;

import android.util.Log;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.hiero.logger.base.logger.interfaces.ILogger;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.Arrays;
import java.util.Date;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u0003\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0011\u001a\u00020\u000bJ$\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0016J$\u0010\u0015\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0016J$\u0010\u0016\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0016J$\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0016J$\u0010\u0018\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0016J$\u0010\u0019\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u001c"}, d2 = {"Lcom/hiero/logger/base/logger/core/ALogUtils;", "Lcom/hiero/logger/base/logger/interfaces/ILogger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "logEnable", "", "getLogEnable", "()Z", "setLogEnable", "(Z)V", "timeTag", "", "getTimeTag", "()Ljava/lang/String;", "write", "", "tag", "formattedMsg", "e", CallMethod.ARG_EXTRA_STRING, "msg", AnimatedProperty.PROPERTY_NAME_W, DateTokenConverter.CONVERTER_KEY, IntegerTokenConverter.CONVERTER_KEY, "v", "trace", "tr", "", "logger_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ALogUtils implements ILogger {
    public static final ALogUtils INSTANCE = new ALogUtils();
    private static boolean logEnable = true;

    private ALogUtils() {
    }

    private final String getTimeTag() {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("[%s] ", Arrays.copyOf(new Object[]{ALogUtilsKt.getFormat().format(new Date(System.currentTimeMillis()))}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void d(String tag, String extra, String msg) {
        String strIntern;
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (logEnable) {
            if (extra != null) {
                strIntern = extra.intern();
                Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
            } else {
                strIntern = null;
            }
            String strIntern2 = ("[" + strIntern + "][" + msg + "]").intern();
            Intrinsics.checkNotNullExpressionValue(strIntern2, "intern(...)");
            Log.d(tag, strIntern2);
            write(tag, strIntern2);
        }
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void e(String tag, String extra, String msg) {
        String strIntern;
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (logEnable) {
            if (extra != null) {
                strIntern = extra.intern();
                Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
            } else {
                strIntern = null;
            }
            String strIntern2 = ("[" + strIntern + "][" + msg + "]").intern();
            Intrinsics.checkNotNullExpressionValue(strIntern2, "intern(...)");
            Log.e(tag, strIntern2);
            write(tag, strIntern2);
        }
    }

    public final boolean getLogEnable() {
        return logEnable;
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void i(String tag, String extra, String msg) {
        String strIntern;
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (logEnable) {
            if (extra != null) {
                strIntern = extra.intern();
                Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
            } else {
                strIntern = null;
            }
            String strIntern2 = ("[" + strIntern + "][" + msg + "]").intern();
            Intrinsics.checkNotNullExpressionValue(strIntern2, "intern(...)");
            Log.i(tag, strIntern2);
            write(tag, strIntern2);
        }
    }

    public final void setLogEnable(boolean z) {
        logEnable = z;
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void trace(String tag, String extra, Throwable tr) {
        String strIntern;
        Intrinsics.checkNotNullParameter(tr, "tr");
        if (logEnable) {
            if (extra != null) {
                strIntern = extra.intern();
                Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
            } else {
                strIntern = null;
            }
            String strIntern2 = ("[" + strIntern + "][exception -> " + tr.getMessage() + "]").intern();
            Intrinsics.checkNotNullExpressionValue(strIntern2, "intern(...)");
            Log.e(tag, strIntern2);
            write(tag, strIntern2);
        }
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void v(String tag, String extra, String msg) {
        String strIntern;
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (logEnable) {
            if (extra != null) {
                strIntern = extra.intern();
                Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
            } else {
                strIntern = null;
            }
            String strIntern2 = ("[" + strIntern + "][" + msg + "]").intern();
            Intrinsics.checkNotNullExpressionValue(strIntern2, "intern(...)");
            Log.v(tag, strIntern2);
            write(tag, strIntern2);
        }
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void w(String tag, String extra, String msg) {
        String strIntern;
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (logEnable) {
            if (extra != null) {
                strIntern = extra.intern();
                Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
            } else {
                strIntern = null;
            }
            String strIntern2 = ("[" + strIntern + "][" + msg + "]").intern();
            Intrinsics.checkNotNullExpressionValue(strIntern2, "intern(...)");
            Log.w(tag, strIntern2);
            write(tag, strIntern2);
        }
    }

    public final void write(String tag, String formattedMsg) {
        String strIntern;
        Intrinsics.checkNotNullParameter(formattedMsg, "formattedMsg");
        AppLogMgr appLogMgr = AppLogMgr.INSTANCE;
        String timeTag = getTimeTag();
        if (tag != null) {
            strIntern = tag.intern();
            Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
        } else {
            strIntern = null;
        }
        String strIntern2 = formattedMsg.intern();
        Intrinsics.checkNotNullExpressionValue(strIntern2, "intern(...)");
        char[] charArray = (timeTag + strIntern + strIntern2 + StringUtils.CR).toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
        appLogMgr.invokeMethod(charArray);
    }
}
