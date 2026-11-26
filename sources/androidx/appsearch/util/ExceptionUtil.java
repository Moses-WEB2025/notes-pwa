package androidx.appsearch.util;

import android.os.RemoteException;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class ExceptionUtil {
    private static final String TAG = "AppSearchExceptionUtil";

    private static boolean isItOkayToRethrowException() {
        return true;
    }

    public static void handleException(Exception exc) {
        if (isItOkayToRethrowException() && (exc instanceof RuntimeException)) {
            rethrowRuntimeException((RuntimeException) exc);
        }
    }

    public static void handleRemoteException(RemoteException remoteException) {
        Log.w(TAG, "Unable to make a call to AppSearchManagerService!", remoteException);
    }

    private static void rethrowRuntimeException(RuntimeException runtimeException) {
        throw runtimeException;
    }

    private ExceptionUtil() {
    }
}
