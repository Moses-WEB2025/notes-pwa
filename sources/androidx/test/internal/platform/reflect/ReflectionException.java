package androidx.test.internal.platform.reflect;

/* JADX INFO: loaded from: classes.dex */
public class ReflectionException extends RuntimeException {
    ReflectionException(Exception cause) {
        super("Reflection access failed", cause);
    }
}
