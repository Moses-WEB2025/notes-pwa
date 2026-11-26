package androidx.test.internal.util;

import android.os.Looper;
import androidx.test.internal.platform.ServiceLoaderWrapper;
import androidx.test.internal.platform.ThreadChecker;

/* JADX INFO: loaded from: classes.dex */
public final class Checks {
    private Checks() {
    }

    public static <T> T checkNotNull(T reference) {
        reference.getClass();
        return reference;
    }

    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    public static <T> T checkNotNull(T reference, String errorMessageTemplate, Object... errorMessageArgs) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    public static void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static void checkMainThread() {
        ThreadCheckerSingleton.INSTANCE.checkMainThread();
    }

    public static void checkNotMainThread() {
        ThreadCheckerSingleton.INSTANCE.checkNotMainThread();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ThreadCheckerSingleton {
        private static final ThreadChecker INSTANCE = (ThreadChecker) ServiceLoaderWrapper.loadSingleService(ThreadChecker.class, new ServiceLoaderWrapper.Factory() { // from class: androidx.test.internal.util.Checks$ThreadCheckerSingleton$$ExternalSyntheticLambda0
            @Override // androidx.test.internal.platform.ServiceLoaderWrapper.Factory
            public final Object create() {
                return Checks.ThreadCheckerSingleton.lambda$static$0();
            }
        });

        private ThreadCheckerSingleton() {
        }

        static /* synthetic */ ThreadChecker lambda$static$0() {
            return new ThreadChecker() { // from class: androidx.test.internal.util.Checks.ThreadCheckerSingleton.1
                @Override // androidx.test.internal.platform.ThreadChecker
                public void checkMainThread() {
                    Checks.checkState(Thread.currentThread().equals(Looper.getMainLooper().getThread()), "Method cannot be called off the main application thread (on: %s)", Thread.currentThread().getName());
                }

                @Override // androidx.test.internal.platform.ThreadChecker
                public void checkNotMainThread() {
                    Checks.checkState(!Thread.currentThread().equals(Looper.getMainLooper().getThread()), "Method cannot be called on the main application thread (on: %s)", Thread.currentThread().getName());
                }
            };
        }
    }

    private static String format(String template, Object... args) {
        int iIndexOf;
        String strValueOf = String.valueOf(template);
        StringBuilder sb = new StringBuilder(strValueOf.length() + (args.length * 16));
        int i = 0;
        int i2 = 0;
        while (i < args.length && (iIndexOf = strValueOf.indexOf("%s", i2)) != -1) {
            sb.append(strValueOf.substring(i2, iIndexOf));
            sb.append(args[i]);
            i2 = iIndexOf + 2;
            i++;
        }
        sb.append(strValueOf.substring(i2));
        if (i < args.length) {
            sb.append(" [");
            sb.append(args[i]);
            for (int i3 = i + 1; i3 < args.length; i3++) {
                sb.append(", ");
                sb.append(args[i3]);
            }
            sb.append(']');
        }
        return sb.toString();
    }
}
