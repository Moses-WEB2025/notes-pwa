package androidx.test.platform.tracing;

import android.util.Log;
import androidx.test.internal.util.Checks;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class Tracing {
    private static final String TAG = "Tracing";
    private static final Tracing singleton = new Tracing();
    private final List<Tracer> tracers = Collections.synchronizedList(new ArrayList());

    private Tracing() {
        registerTracer(new AndroidXTracer().enableTracing());
    }

    public static Tracing getInstance() {
        return singleton;
    }

    public void registerTracer(Tracer tracer) {
        Checks.checkNotNull(tracer, "Tracer cannot be null.");
        if (this.tracers.contains(tracer)) {
            Log.w(TAG, "Tracer already present: " + String.valueOf(tracer.getClass()));
        } else {
            Log.i(TAG, "Tracer added: " + String.valueOf(tracer.getClass()));
            this.tracers.add(tracer);
        }
    }

    public void unregisterTracer(Tracer tracer) {
        this.tracers.remove(tracer);
        Log.i(TAG, "Tracer removed: " + String.valueOf(tracer == null ? null : tracer.getClass()));
    }

    public Tracer.Span beginSpan(String name) {
        HashMap map;
        Checks.checkNotNull(name);
        synchronized (this.tracers) {
            map = new HashMap(this.tracers.size());
            for (Tracer tracer : this.tracers) {
                map.put(tracer, createUnmanagedSpan(tracer, name));
            }
        }
        return new TracerSpan(map);
    }

    class TracerSpan implements Tracer.Span {
        private final Map<Tracer, Tracer.Span> spans;

        private TracerSpan(Map<Tracer, Tracer.Span> spans) {
            this.spans = spans;
        }

        @Override // androidx.test.platform.tracing.Tracer.Span
        public Tracer.Span beginChildSpan(String name) {
            HashMap map;
            Checks.checkNotNull(name);
            synchronized (Tracing.this.tracers) {
                map = new HashMap(Tracing.this.tracers.size());
                for (Tracer tracer : Tracing.this.tracers) {
                    Tracer.Span span = this.spans.get(tracer);
                    if (span != null) {
                        map.put(tracer, Tracing.createUnmanagedChildSpan(span, name));
                    }
                }
            }
            return Tracing.this.new TracerSpan(map);
        }

        @Override // androidx.test.platform.tracing.Tracer.Span, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            Iterator<Tracer.Span> it = this.spans.values().iterator();
            while (it.hasNext()) {
                it.next().close();
            }
        }
    }

    private static Tracer.Span createUnmanagedSpan(Tracer tracer, String name) {
        return tracer.beginSpan(name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Tracer.Span createUnmanagedChildSpan(Tracer.Span parentSpan, String name) {
        return parentSpan.beginChildSpan(name);
    }
}
