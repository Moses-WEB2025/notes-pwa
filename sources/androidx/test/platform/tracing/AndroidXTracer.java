package androidx.test.platform.tracing;

import android.util.Log;
import androidx.tracing.Trace;
import java.util.ArrayDeque;

/* JADX INFO: loaded from: classes.dex */
class AndroidXTracer implements Tracer {
    private static final int MAX_SECTION_NAME_LEN = 127;
    private static final String TAG = "AndroidXTracer";

    AndroidXTracer() {
    }

    public AndroidXTracer enableTracing() {
        try {
            Trace.forceEnableAppTracing();
            return this;
        } catch (NoSuchMethodError e) {
            Log.e(TAG, "enableTracing failed. You may need to upgrade your androidx.tracing:tracing version", e);
            return this;
        } catch (RuntimeException e2) {
            Log.e(TAG, "enableTracing failed", e2);
            return this;
        }
    }

    @Override // androidx.test.platform.tracing.Tracer
    public Tracer.Span beginSpan(String name) {
        Trace.beginSection(sanitizeSpanName(name));
        return new AndroidXTracerSpan();
    }

    private static class AndroidXTracerSpan implements Tracer.Span {
        private final ArrayDeque<AndroidXTracerSpan> nestedSpans;

        private AndroidXTracerSpan() {
            this.nestedSpans = new ArrayDeque<>();
        }

        @Override // androidx.test.platform.tracing.Tracer.Span
        public Tracer.Span beginChildSpan(String name) {
            Trace.beginSection(AndroidXTracer.sanitizeSpanName(name));
            AndroidXTracerSpan androidXTracerSpan = new AndroidXTracerSpan();
            this.nestedSpans.add(androidXTracerSpan);
            return androidXTracerSpan;
        }

        @Override // androidx.test.platform.tracing.Tracer.Span, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            while (true) {
                AndroidXTracerSpan androidXTracerSpanPollLast = this.nestedSpans.pollLast();
                if (androidXTracerSpanPollLast != null) {
                    androidXTracerSpanPollLast.close();
                } else {
                    Trace.endSection();
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String sanitizeSpanName(String name) {
        if (name.length() <= 127) {
            return name;
        }
        Log.w(TAG, "Span name exceeds limits: " + name);
        return name.substring(0, 127);
    }
}
