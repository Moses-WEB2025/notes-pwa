package xc.engine.utils;

import android.util.Log;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes6.dex */
public class XLog {
    private static final int DEFAULT_LEVEL;
    private static final String TAG = "XLog";
    private static final Map<String, Integer> TAG_LEVEL_MAP;
    private static int sGlobalLevel;

    public enum LogLevel {
        VERBOSE(0),
        DEBUG(1),
        INFO(2),
        WARN(3),
        ERROR(4),
        NONE(5);

        public final int level;

        LogLevel(int i) {
            this.level = i;
        }

        public boolean isLoggable(int i) {
            return this.level >= i;
        }
    }

    static {
        int i = LogLevel.DEBUG.level;
        DEFAULT_LEVEL = i;
        TAG_LEVEL_MAP = new ConcurrentHashMap();
        sGlobalLevel = i;
    }

    public static void setGlobalLevel(LogLevel logLevel) {
        sGlobalLevel = logLevel.level;
    }

    public static void setTagLevel(String str, LogLevel logLevel) {
        TAG_LEVEL_MAP.put(str, Integer.valueOf(logLevel.level));
    }

    public static void resetAllTags() {
        TAG_LEVEL_MAP.clear();
    }

    public static void v(String str, String str2, Object... objArr) {
        log(LogLevel.VERBOSE, str, str2, null, objArr);
    }

    public static void d(String str, String str2, Object... objArr) {
        log(LogLevel.DEBUG, str, str2, null, objArr);
    }

    public static void i(String str, String str2, Object... objArr) {
        log(LogLevel.INFO, str, str2, null, objArr);
    }

    public static void w(String str, String str2, Object... objArr) {
        log(LogLevel.WARN, str, str2, null, objArr);
    }

    public static void e(String str, String str2, Object... objArr) {
        log(LogLevel.ERROR, str, str2, null, objArr);
    }

    public static void e(String str, Throwable th, String str2, Object... objArr) {
        log(LogLevel.ERROR, str, str2, th, objArr);
    }

    public static void e(String str) {
        log(LogLevel.ERROR, TAG, str, new Throwable(), null);
    }

    private static void log(LogLevel logLevel, String str, String str2, Throwable th, Object... objArr) {
        if (logLevel.isLoggable(sGlobalLevel)) {
            Log.println(getAndroidLevel(logLevel), str, buildLogMessage(logLevel, str2 != null ? String.format(str2, objArr) : "", th));
        }
    }

    private static String buildLogMessage(LogLevel logLevel, String str, Throwable th) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(Thread.currentThread().getName()).append("]-> ").append(str);
        if (th != null) {
            sb.append(StringUtils.LF).append(Log.getStackTraceString(th));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: xc.engine.utils.XLog$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$xc$engine$utils$XLog$LogLevel;

        static {
            int[] iArr = new int[LogLevel.values().length];
            $SwitchMap$xc$engine$utils$XLog$LogLevel = iArr;
            try {
                iArr[LogLevel.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$xc$engine$utils$XLog$LogLevel[LogLevel.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$xc$engine$utils$XLog$LogLevel[LogLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$xc$engine$utils$XLog$LogLevel[LogLevel.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$xc$engine$utils$XLog$LogLevel[LogLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static int getAndroidLevel(LogLevel logLevel) {
        int i = AnonymousClass1.$SwitchMap$xc$engine$utils$XLog$LogLevel[logLevel.ordinal()];
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 3;
        }
        if (i != 4) {
            return i != 5 ? 4 : 6;
        }
        return 5;
    }
}
