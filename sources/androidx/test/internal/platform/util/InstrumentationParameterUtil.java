package androidx.test.internal.platform.util;

import androidx.test.internal.util.Checks;
import androidx.test.platform.app.InstrumentationRegistry;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class InstrumentationParameterUtil {
    public static long getTimeoutMillis(String key, long defaultValue) {
        Checks.checkArgument(defaultValue != 0, "default timeout value cannot be zero");
        long j = Long.parseLong(InstrumentationRegistry.getArguments().getString(key, "0"));
        if (j != 0) {
            defaultValue = j;
        }
        return defaultValue < 0 ? TimeUnit.DAYS.toMillis(1L) : defaultValue;
    }
}
