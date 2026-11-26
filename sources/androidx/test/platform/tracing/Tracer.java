package androidx.test.platform.tracing;

import java.io.Closeable;

/* JADX INFO: loaded from: classes.dex */
public interface Tracer {

    public interface Span extends Closeable {
        Span beginChildSpan(String name);

        @Override // java.io.Closeable, java.lang.AutoCloseable
        void close();
    }

    Span beginSpan(String name);
}
