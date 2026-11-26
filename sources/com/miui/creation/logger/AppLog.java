package com.miui.creation.logger;

import com.miui.creation.logger.inner.LoggerBase;
import com.miui.creation.logger.inner.Markers;
import com.miui.creation.logger.inner.XCLoggerFactory;
import org.slf4j.Logger;

/* JADX INFO: loaded from: classes2.dex */
public class AppLog extends LoggerBase {
    private static Logger getLogger(String str) {
        return XCLoggerFactory.getAppLogger(str);
    }

    public static void d(String str, Object obj) {
        if (obj == null) {
            return;
        }
        getLogger(str).debug(obj.toString());
    }

    public static void fd(String str, Object obj) {
        if (obj == null) {
            return;
        }
        getLogger(str).debug(Markers.getFileMarker(), obj.toString());
    }

    public static void d(String str, String str2) {
        getLogger(str).debug(str2);
    }

    public static void fd(String str, String str2) {
        getLogger(str).debug(Markers.getFileMarker(), str2);
    }

    public static void d(String str, Throwable th) {
        getLogger(str).debug("", th);
    }

    public static void fd(String str, Throwable th) {
        getLogger(str).debug(Markers.getFileMarker(), "", th);
    }

    public static void d(String str, String str2, Object obj) {
        getLogger(str).debug(logFormat(str2, obj));
    }

    public static void fd(String str, String str2, Object obj) {
        getLogger(str).debug(Markers.getFileMarker(), logFormat(str2, obj));
    }

    public static void d(String str, String str2, Object obj, Object obj2) {
        getLogger(str).debug(logFormat(str2, obj, obj2));
    }

    public static void fd(String str, String str2, Object obj, Object obj2) {
        getLogger(str).debug(Markers.getFileMarker(), logFormat(str2, obj, obj2));
    }

    public static void d(String str, String str2, Object obj, Object obj2, Object obj3) {
        getLogger(str).debug(logFormat(str2, obj, obj2, obj3));
    }

    public static void fd(String str, String str2, Object obj, Object obj2, Object obj3) {
        getLogger(str).debug(Markers.getFileMarker(), logFormat(str2, obj, obj2, obj3));
    }

    public static void d(String str, String str2, Object... objArr) {
        getLogger(str).debug(logFormat(str2, objArr));
    }

    public static void fd(String str, String str2, Object... objArr) {
        getLogger(str).debug(Markers.getFileMarker(), logFormat(str2, objArr));
    }

    public static void w(String str, String str2) {
        getLogger(str).warn(str2);
    }

    public static void w(String str, Throwable th) {
        getLogger(str).warn("", th);
    }

    public static void w(String str, String str2, Object obj) {
        getLogger(str).warn(logFormat(str2, obj));
    }

    public static void w(String str, String str2, Object obj, Object obj2) {
        getLogger(str).warn(logFormat(str2, obj, obj2));
    }

    public static void w(String str, String str2, Object obj, Object obj2, Object obj3) {
        getLogger(str).warn(logFormat(str2, obj, obj2, obj3));
    }

    public static void w(String str, String str2, Object... objArr) {
        getLogger(str).warn(logFormat(str2, objArr));
    }

    public static void e(String str, String str2) {
        getLogger(str).error(str2);
    }

    public static void e(String str, Throwable th) {
        getLogger(str).error("", th);
    }

    public static void e(String str, String str2, Object obj) {
        getLogger(str).error(logFormat(str2, obj));
    }

    public static void e(String str, String str2, Object obj, Object obj2) {
        getLogger(str).error(logFormat(str2, obj, obj2));
    }

    public static void e(String str, String str2, Object obj, Object obj2, Object obj3) {
        getLogger(str).error(logFormat(str2, obj, obj2, obj3));
    }

    public static void e(String str, String str2, Object... objArr) {
        getLogger(str).error(logFormat(str2, objArr));
    }

    public static void v(String str, String str2) {
        getLogger(str).trace(str2);
    }

    public static void v(String str, Throwable th) {
        getLogger(str).trace("", th);
    }

    public static void v(String str, String str2, Object obj) {
        getLogger(str).trace(logFormat(str2, obj));
    }

    public static void v(String str, String str2, Object obj, Object obj2) {
        getLogger(str).trace(logFormat(str2, obj, obj2));
    }

    public static void v(String str, String str2, Object obj, Object obj2, Object obj3) {
        getLogger(str).trace(logFormat(str2, obj, obj2, obj3));
    }

    public static void v(String str, String str2, Object... objArr) {
        getLogger(str).trace(logFormat(str2, objArr));
    }

    public static void i(String str, String str2) {
        getLogger(str).info(str2);
    }

    public static void fi(String str, String str2) {
        getLogger(str).info(Markers.getFileMarker(), str2);
    }

    public static void i(String str, Throwable th) {
        getLogger(str).info("", th);
    }

    public static void fi(String str, Throwable th) {
        getLogger(str).info(Markers.getFileMarker(), "", th);
    }

    public static void i(String str, String str2, Object obj) {
        getLogger(str).info(logFormat(str2, obj));
    }

    public static void fi(String str, String str2, Object obj) {
        getLogger(str).info(Markers.getFileMarker(), logFormat(str2, obj));
    }

    public static void i(String str, String str2, Object obj, Object obj2) {
        getLogger(str).info(logFormat(str2, obj, obj2));
    }

    public static void fi(String str, String str2, Object obj, Object obj2) {
        getLogger(str).info(Markers.getFileMarker(), logFormat(str2, obj, obj2));
    }

    public static void i(String str, String str2, Object obj, Object obj2, Object obj3) {
        getLogger(str).info(logFormat(str2, obj, obj2, obj3));
    }

    public static void fi(String str, String str2, Object obj, Object obj2, Object obj3) {
        getLogger(str).info(Markers.getFileMarker(), logFormat(str2, obj, obj2, obj3));
    }

    public static void i(String str, String str2, Object... objArr) {
        getLogger(str).info(logFormat(str2, objArr));
    }

    public static void fi(String str, String str2, Object... objArr) {
        getLogger(str).info(Markers.getFileMarker(), logFormat(str2, objArr));
    }
}
