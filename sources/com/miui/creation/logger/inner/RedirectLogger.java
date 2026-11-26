package com.miui.creation.logger.inner;

import android.util.Log;
import com.miui.creation.logger.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

/* JADX INFO: loaded from: classes2.dex */
class RedirectLogger implements Logger {
    private static final int sLogLevel;
    private final Logger mSubstituteLogger;
    private final String mTag;

    static {
        int i = LoggerConfig.getInstance().getLogcatLevel().levelInt;
        if (i == Integer.MIN_VALUE || i == 5000) {
            sLogLevel = 2;
            return;
        }
        if (i == 10000) {
            sLogLevel = 3;
            return;
        }
        if (i == 20000) {
            sLogLevel = 4;
            return;
        }
        if (i == 30000) {
            sLogLevel = 5;
        } else if (i == 40000) {
            sLogLevel = 6;
        } else {
            sLogLevel = 7;
        }
    }

    public RedirectLogger(Logger logger) {
        String name = logger.getName();
        int iIndexOf = name != null ? name.indexOf(46) : -1;
        if (name != null && iIndexOf > 0) {
            this.mTag = "XCreation_" + name.substring(iIndexOf + 1);
        } else {
            this.mTag = "XCreation_" + name;
        }
        this.mSubstituteLogger = logger;
    }

    private static boolean needLog(int i) {
        return i >= sLogLevel;
    }

    @Override // org.slf4j.Logger
    public String getName() {
        return this.mSubstituteLogger.getName();
    }

    @Override // org.slf4j.Logger
    public boolean isTraceEnabled() {
        return needLog(2);
    }

    @Override // org.slf4j.Logger
    public boolean isTraceEnabled(Marker marker) {
        return isTraceEnabled();
    }

    @Override // org.slf4j.Logger
    public void trace(String str) {
        if (isTraceEnabled()) {
            Log.v(this.mTag, str);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj) {
        if (isTraceEnabled()) {
            Log.v(this.mTag, MessageFormatter.format(str, obj).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj, Object obj2) {
        if (isTraceEnabled()) {
            Log.v(this.mTag, MessageFormatter.format(str, obj, obj2).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object... objArr) {
        if (isTraceEnabled()) {
            Log.v(this.mTag, MessageFormatter.format(str, objArr).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Throwable th) {
        if (isTraceEnabled()) {
            Log.v(this.mTag, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str) {
        if (isTraceEnabled()) {
            Log.v(this.mTag, str);
        }
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Object obj) {
        if (isTraceEnabled()) {
            Log.v(this.mTag, MessageFormatter.format(str, obj).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Object obj, Object obj2) {
        if (isTraceEnabled()) {
            Log.v(this.mTag, MessageFormatter.format(str, obj, obj2).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Object... objArr) {
        if (isTraceEnabled()) {
            Log.v(this.mTag, MessageFormatter.format(str, objArr).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Throwable th) {
        if (isTraceEnabled()) {
            Log.v(this.mTag, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public boolean isDebugEnabled() {
        return needLog(3);
    }

    @Override // org.slf4j.Logger
    public boolean isDebugEnabled(Marker marker) {
        return isDebugEnabled();
    }

    @Override // org.slf4j.Logger
    public void debug(String str) {
        if (isDebugEnabled()) {
            Log.d(this.mTag, str);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj) {
        if (isDebugEnabled()) {
            Log.d(this.mTag, MessageFormatter.format(str, obj).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj, Object obj2) {
        if (isDebugEnabled()) {
            Log.d(this.mTag, MessageFormatter.format(str, obj, obj2).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object... objArr) {
        if (isDebugEnabled()) {
            Log.d(this.mTag, MessageFormatter.format(str, objArr).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Throwable th) {
        if (isDebugEnabled()) {
            Log.d(this.mTag, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str) {
        if (isDebugEnabled()) {
            Log.d(this.mTag, str);
        }
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Object obj) {
        if (isDebugEnabled()) {
            Log.d(this.mTag, MessageFormatter.format(str, obj).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Object obj, Object obj2) {
        if (isDebugEnabled()) {
            Log.d(this.mTag, MessageFormatter.format(str, obj, obj2).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Object... objArr) {
        if (isDebugEnabled()) {
            Log.d(this.mTag, MessageFormatter.format(str, objArr).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Throwable th) {
        if (isDebugEnabled()) {
            Log.d(this.mTag, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public boolean isInfoEnabled() {
        return needLog(4);
    }

    @Override // org.slf4j.Logger
    public boolean isInfoEnabled(Marker marker) {
        return isInfoEnabled();
    }

    @Override // org.slf4j.Logger
    public void info(String str) {
        if (isInfoEnabled()) {
            Log.i(this.mTag, str);
        }
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj) {
        if (isInfoEnabled()) {
            Log.i(this.mTag, MessageFormatter.format(str, obj).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj, Object obj2) {
        if (isInfoEnabled()) {
            Log.i(this.mTag, MessageFormatter.format(str, obj, obj2).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object... objArr) {
        if (isInfoEnabled()) {
            Log.i(this.mTag, MessageFormatter.format(str, objArr).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void info(String str, Throwable th) {
        if (isInfoEnabled()) {
            Log.i(this.mTag, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str) {
        if (isInfoEnabled()) {
            Log.i(this.mTag, str);
        }
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Object obj) {
        if (isInfoEnabled()) {
            Log.i(this.mTag, MessageFormatter.format(str, obj).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Object obj, Object obj2) {
        if (isInfoEnabled()) {
            Log.i(this.mTag, MessageFormatter.format(str, obj, obj2).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Object... objArr) {
        if (isInfoEnabled()) {
            Log.i(this.mTag, MessageFormatter.format(str, objArr).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Throwable th) {
        if (isInfoEnabled()) {
            Log.i(this.mTag, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public boolean isWarnEnabled() {
        return needLog(5);
    }

    @Override // org.slf4j.Logger
    public boolean isWarnEnabled(Marker marker) {
        return isWarnEnabled();
    }

    @Override // org.slf4j.Logger
    public void warn(String str) {
        if (isWarnEnabled()) {
            Log.w(this.mTag, str);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj) {
        if (isWarnEnabled()) {
            Log.w(this.mTag, MessageFormatter.format(str, obj).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj, Object obj2) {
        if (isWarnEnabled()) {
            Log.w(this.mTag, MessageFormatter.format(str, obj, obj2).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object... objArr) {
        if (isWarnEnabled()) {
            Log.w(this.mTag, MessageFormatter.format(str, objArr).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Throwable th) {
        if (isWarnEnabled()) {
            Log.w(this.mTag, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str) {
        if (isWarnEnabled()) {
            Log.w(this.mTag, str);
        }
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Object obj) {
        if (isWarnEnabled()) {
            Log.w(this.mTag, MessageFormatter.format(str, obj).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Object obj, Object obj2) {
        if (isWarnEnabled()) {
            Log.w(this.mTag, MessageFormatter.format(str, obj, obj2).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Object... objArr) {
        if (isWarnEnabled()) {
            Log.w(this.mTag, MessageFormatter.format(str, objArr).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Throwable th) {
        if (isWarnEnabled()) {
            Log.w(this.mTag, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public boolean isErrorEnabled() {
        return needLog(6);
    }

    @Override // org.slf4j.Logger
    public boolean isErrorEnabled(Marker marker) {
        return isErrorEnabled();
    }

    @Override // org.slf4j.Logger
    public void error(String str) {
        if (isErrorEnabled()) {
            Log.e(this.mTag, str);
        }
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object obj) {
        if (isErrorEnabled()) {
            Log.e(this.mTag, MessageFormatter.format(str, obj).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object obj, Object obj2) {
        if (isErrorEnabled()) {
            Log.e(this.mTag, MessageFormatter.format(str, obj, obj2).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object... objArr) {
        if (isErrorEnabled()) {
            Log.e(this.mTag, MessageFormatter.format(str, objArr).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void error(String str, Throwable th) {
        if (isErrorEnabled()) {
            Log.e(this.mTag, str, th);
        }
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str) {
        if (isErrorEnabled()) {
            Log.e(this.mTag, str);
        }
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Object obj) {
        if (isErrorEnabled()) {
            Log.e(this.mTag, MessageFormatter.format(str, obj).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Object obj, Object obj2) {
        if (isErrorEnabled()) {
            Log.e(this.mTag, MessageFormatter.format(str, obj, obj2).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Object... objArr) {
        if (isErrorEnabled()) {
            Log.e(this.mTag, MessageFormatter.format(str, objArr).getMessage());
        }
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Throwable th) {
        if (isErrorEnabled()) {
            Log.e(this.mTag, str, th);
        }
    }
}
