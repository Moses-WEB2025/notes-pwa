package com.miui.notes.base.utils;

import android.view.Choreographer;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FrameUpdateMonitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0015B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u0012\u001a\u00020\u000f2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rJ\u0006\u0010\u0014\u001a\u00020\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001c\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/miui/notes/base/utils/FrameUpdateMonitor;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", CallMethod.ARG_IS_START, "", "mMonitorFrameCallback", "Lcom/miui/notes/base/utils/FrameUpdateMonitor$InternalFrameCallback;", "getMMonitorFrameCallback", "()Lcom/miui/notes/base/utils/FrameUpdateMonitor$InternalFrameCallback;", "mMonitorFrameCallback$delegate", "Lkotlin/Lazy;", "mListener", "Lkotlin/Function1;", "", "", "mStartTime", "", "startMonitor", "listener", "stopMonitor", "InternalFrameCallback", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FrameUpdateMonitor {
    private static volatile boolean isStart;
    private static long mStartTime;
    public static final FrameUpdateMonitor INSTANCE = new FrameUpdateMonitor();

    /* JADX INFO: renamed from: mMonitorFrameCallback$delegate, reason: from kotlin metadata */
    private static final Lazy mMonitorFrameCallback = LazyKt.lazy(new Function0() { // from class: com.miui.notes.base.utils.FrameUpdateMonitor$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FrameUpdateMonitor.mMonitorFrameCallback_delegate$lambda$0();
        }
    });
    private static Function1<? super Float, Unit> mListener = new Function1() { // from class: com.miui.notes.base.utils.FrameUpdateMonitor$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return FrameUpdateMonitor.mListener$lambda$1(((Float) obj).floatValue());
        }
    };

    private FrameUpdateMonitor() {
    }

    private final InternalFrameCallback getMMonitorFrameCallback() {
        return (InternalFrameCallback) mMonitorFrameCallback.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final InternalFrameCallback mMonitorFrameCallback_delegate$lambda$0() {
        return new InternalFrameCallback();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit mListener$lambda$1(float f) {
        return Unit.INSTANCE;
    }

    public final void startMonitor(Function1<? super Float, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        if (isStart) {
            return;
        }
        mListener = listener;
        Choreographer.getInstance().postFrameCallback(getMMonitorFrameCallback());
        isStart = true;
    }

    public final void stopMonitor() {
        isStart = false;
        mListener = null;
        Choreographer.getInstance().removeFrameCallback(new Choreographer.FrameCallback() { // from class: com.miui.notes.base.utils.FrameUpdateMonitor$$ExternalSyntheticLambda2
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                FrameUpdateMonitor.stopMonitor$lambda$2(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stopMonitor$lambda$2(long j) {
        INSTANCE.getMMonitorFrameCallback();
    }

    /* JADX INFO: compiled from: FrameUpdateMonitor.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lcom/miui/notes/base/utils/FrameUpdateMonitor$InternalFrameCallback;", "Landroid/view/Choreographer$FrameCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "doFrame", "", "frameTimeNanos", "", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class InternalFrameCallback implements Choreographer.FrameCallback {
        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long frameTimeNanos) {
            if (FrameUpdateMonitor.isStart) {
                if (FrameUpdateMonitor.mStartTime != 0) {
                    float f = (frameTimeNanos - FrameUpdateMonitor.mStartTime) / 1000000.0f;
                    Function1 function1 = FrameUpdateMonitor.mListener;
                    if (function1 != null) {
                        function1.invoke(Float.valueOf(f));
                    }
                } else {
                    FrameUpdateMonitor frameUpdateMonitor = FrameUpdateMonitor.INSTANCE;
                    FrameUpdateMonitor.mStartTime = frameTimeNanos;
                }
                Choreographer.getInstance().postFrameCallback(this);
            }
        }
    }
}
