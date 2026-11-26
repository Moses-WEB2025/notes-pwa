package androidx.test.internal.util;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class LogUtil {
    private static volatile String myProcName;

    public interface Supplier {
        String get();
    }

    static /* synthetic */ String lambda$logDebug$0(String str) {
        return str;
    }

    public static Supplier lazyArg(Supplier supplier) {
        return supplier;
    }

    public static void logDebug(String tag, final String message, Object... args) {
        logDebug(tag, new Supplier() { // from class: androidx.test.internal.util.LogUtil$$ExternalSyntheticLambda1
            @Override // androidx.test.internal.util.LogUtil.Supplier
            public final String get() {
                return LogUtil.lambda$logDebug$0(message);
            }
        }, args);
    }

    private static void logDebug(String tag, Supplier msgSupplier, Object... args) {
        if (isLoggable(tag, 3)) {
            Object[] objArr = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                Object obj = args[i];
                if (obj instanceof Supplier) {
                    objArr[i] = ((Supplier) obj).get();
                } else {
                    objArr[i] = obj;
                }
            }
            Log.d(tag, String.format(msgSupplier.get(), objArr));
        }
    }

    static /* synthetic */ String lambda$logDebugWithProcess$1(String str) {
        return str + " in " + procName();
    }

    public static void logDebugWithProcess(String tag, final String message, Object... args) {
        logDebug(tag, new Supplier() { // from class: androidx.test.internal.util.LogUtil$$ExternalSyntheticLambda0
            @Override // androidx.test.internal.util.LogUtil.Supplier
            public final String get() {
                return LogUtil.lambda$logDebugWithProcess$1(message);
            }
        }, args);
    }

    private static final String procName() {
        String str;
        String str2 = myProcName;
        if (str2 != null) {
            return str2;
        }
        try {
            str = ProcSummary.summarize("self").cmdline;
        } catch (ProcSummary.SummaryException unused) {
            str = "unknown";
        }
        return (str.length() <= 64 || !str.contains("-classpath")) ? str : "robolectric";
    }

    private static boolean isLoggable(String tag, final int level) {
        if (tag.length() > 23) {
            tag = tag.substring(0, 22);
        }
        return Log.isLoggable(tag, level);
    }
}
