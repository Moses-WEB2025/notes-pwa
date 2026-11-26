package androidx.test.platform.device;

import androidx.test.platform.TestFrameworkException;

/* JADX INFO: loaded from: classes.dex */
public class UnsupportedDeviceOperationException extends RuntimeException implements TestFrameworkException {
    public UnsupportedDeviceOperationException(String description) {
        super(description);
    }

    public UnsupportedDeviceOperationException(String description, Throwable cause) {
        super(description, cause);
    }
}
