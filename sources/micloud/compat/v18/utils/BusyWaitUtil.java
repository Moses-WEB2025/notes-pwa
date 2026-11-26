package micloud.compat.v18.utils;

import android.os.SystemClock;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes5.dex */
public class BusyWaitUtil {

    public interface Action<ValueType> {
        ValueType doAction(long j, long j2) throws NotAvailableException;
    }

    public static class NotAvailableException extends Exception {
    }

    public static <ValueType> ValueType busyWait(Action<ValueType> action, long j, long j2) throws InterruptedException, TimeoutException {
        if (action == null || j < 0 || j2 <= 0) {
            throw new IllegalArgumentException("null == action || timeoutMillis < 0 || retryIntervalMillis <= 0");
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        long j3 = 0;
        while (true) {
            long j4 = 1 + j3;
            try {
                return action.doAction(jUptimeMillis, j3);
            } catch (NotAvailableException unused) {
                long jUptimeMillis2 = j - (SystemClock.uptimeMillis() - jUptimeMillis);
                if (jUptimeMillis2 <= 0) {
                    throw new TimeoutException();
                }
                Thread.sleep(Math.min(jUptimeMillis2, j2));
                j3 = j4;
            }
        }
    }
}
