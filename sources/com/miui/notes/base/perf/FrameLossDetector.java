package com.miui.notes.base.perf;

import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.internal.FolmeEngine;

/* JADX INFO: compiled from: FrameLossDetector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\f\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fBU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012:\b\u0002\u0010\u0006\u001a4\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f\u0018\u00010\u0007¢\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u001e\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010RC\u0010\u0006\u001a4\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0014X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019¨\u0006 "}, d2 = {"Lcom/miui/notes/base/perf/FrameLossDetector;", "Landroid/view/Choreographer$FrameCallback;", "startNaosTs", "", "detectorLostFactor", "", CallMethod.ARG_CALLBACK, "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "frameTimeNanos", "lost", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(JILkotlin/jvm/functions/Function2;)V", "getDetectorLostFactor", "()I", "getCallback", "()Lkotlin/jvm/functions/Function2;", "TAG", "", "getTAG", "()Ljava/lang/String;", "mLastFrameTimeNanos", "getMLastFrameTimeNanos", "()J", "setMLastFrameTimeNanos", "(J)V", "mFrameIntervalNanos", "getMFrameIntervalNanos", "doFrame", "Companion", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FrameLossDetector implements Choreographer.FrameCallback {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String TAG;
    private final Function2<Long, Integer, Unit> callback;
    private final int detectorLostFactor;
    private final long mFrameIntervalNanos;
    private long mLastFrameTimeNanos;
    private final long startNaosTs;

    /* JADX WARN: Multi-variable type inference failed */
    public FrameLossDetector(long j, int i, Function2<? super Long, ? super Integer, Unit> function2) {
        this.startNaosTs = j;
        this.detectorLostFactor = i;
        this.callback = function2;
        this.TAG = "FrameLossDetector";
        this.mFrameIntervalNanos = FolmeEngine.MAX_DELTA;
    }

    public /* synthetic */ FrameLossDetector(long j, int i, Function2 function2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, (i2 & 2) != 0 ? 10 : i, (i2 & 4) != 0 ? null : function2);
    }

    public final Function2<Long, Integer, Unit> getCallback() {
        return this.callback;
    }

    public final int getDetectorLostFactor() {
        return this.detectorLostFactor;
    }

    public final String getTAG() {
        return this.TAG;
    }

    public final long getMLastFrameTimeNanos() {
        return this.mLastFrameTimeNanos;
    }

    public final void setMLastFrameTimeNanos(long j) {
        this.mLastFrameTimeNanos = j;
    }

    public final long getMFrameIntervalNanos() {
        return this.mFrameIntervalNanos;
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long frameTimeNanos) {
        int i;
        if (this.mLastFrameTimeNanos == 0) {
            this.mLastFrameTimeNanos = this.startNaosTs;
        }
        long j = frameTimeNanos - this.mLastFrameTimeNanos;
        long j2 = this.mFrameIntervalNanos;
        if (j >= j2 && (i = (int) (j / j2)) > this.detectorLostFactor) {
            Log.e(this.TAG, "Skipped " + i + " frames!  The application may be doing too much work on its main thread.");
            Function2<Long, Integer, Unit> function2 = this.callback;
            if (function2 != null) {
                function2.invoke(Long.valueOf(frameTimeNanos), Integer.valueOf(i));
            }
        }
        this.mLastFrameTimeNanos = frameTimeNanos;
        Choreographer.getInstance().postFrameCallback(this);
    }

    /* JADX INFO: compiled from: FrameLossDetector.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003JT\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2:\b\u0002\u0010\n\u001a4\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\t¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000b¨\u0006\u0010"}, d2 = {"Lcom/miui/notes/base/perf/FrameLossDetector$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "execute", "", "startNaosTs", "", "detectorLostFactor", "", CallMethod.ARG_CALLBACK, "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "frameTimeNanos", "lost", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void execute$default(Companion companion, long j, int i, Function2 function2, int i2, Object obj) throws Exception {
            if ((i2 & 2) != 0) {
                i = 10;
            }
            if ((i2 & 4) != 0) {
                function2 = null;
            }
            companion.execute(j, i, function2);
        }

        public final void execute(long startNaosTs, int detectorLostFactor, Function2<? super Long, ? super Integer, Unit> callback) throws Exception {
            Log.e("FrameLossDetector", "execute");
            if (!Intrinsics.areEqual(Looper.getMainLooper(), Looper.myLooper())) {
                Log.e("FrameLossDetector", "execute on wrong thread");
                throw new Exception("should invoke FrameLossDetector.execute()  method  on main thread!!!");
            }
            Choreographer.getInstance().postFrameCallback(new FrameLossDetector(startNaosTs, detectorLostFactor, callback));
        }
    }
}
