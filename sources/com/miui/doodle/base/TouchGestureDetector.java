package com.miui.doodle.base;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TouchGestureDetector.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001:\u0003\u001d\u001e\u001fB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0015J\u000e\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001cR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000f\"\u0004\b\u0010\u0010\u0011R$\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u000e8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u000f\"\u0004\b\u0019\u0010\u0011¨\u0006 "}, d2 = {"Lcom/miui/doodle/base/TouchGestureDetector;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "listener", "Lcom/miui/doodle/base/TouchGestureDetector$IOnTouchGestureListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/doodle/base/TouchGestureDetector$IOnTouchGestureListener;)V", "gestureDetector", "Landroid/view/GestureDetector;", "scaleGestureDetectorApi27", "Lcom/miui/doodle/base/ScaleGestureDetectorApi27;", "onTouchGestureListener", "isScrollAfterScaled", "", "()Z", "setScrollAfterScaled", "(Z)V", "setScaleMinSpan", "", "minSpan", "", "setScaleSpanSlop", "spanSLop", "isLongpressEnabled", "setLongpressEnabled", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "OnTouchGestureListener", "IOnTouchGestureListener", "OnTouchGestureListenerProxy", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class TouchGestureDetector {
    private final GestureDetector gestureDetector;
    private boolean isScrollAfterScaled;
    private final IOnTouchGestureListener onTouchGestureListener;
    private final ScaleGestureDetectorApi27 scaleGestureDetectorApi27;

    /* JADX INFO: compiled from: TouchGestureDetector.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\nÀ\u0006\u0003"}, d2 = {"Lcom/miui/doodle/base/TouchGestureDetector$IOnTouchGestureListener;", "Landroid/view/GestureDetector$OnGestureListener;", "Landroid/view/GestureDetector$OnDoubleTapListener;", "Lcom/miui/doodle/base/ScaleGestureDetectorApi27$OnScaleGestureListener;", "onUpOrCancel", "", "e", "Landroid/view/MotionEvent;", "onScrollBegin", "onScrollEnd", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface IOnTouchGestureListener extends GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetectorApi27.OnScaleGestureListener {
        void onScrollBegin(MotionEvent e);

        void onScrollEnd(MotionEvent e);

        void onUpOrCancel(MotionEvent e);
    }

    public TouchGestureDetector(Context context, IOnTouchGestureListener listener) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.isScrollAfterScaled = true;
        OnTouchGestureListenerProxy onTouchGestureListenerProxy = new OnTouchGestureListenerProxy(this, listener);
        this.onTouchGestureListener = onTouchGestureListenerProxy;
        GestureDetector gestureDetector = new GestureDetector(context, onTouchGestureListenerProxy);
        this.gestureDetector = gestureDetector;
        gestureDetector.setOnDoubleTapListener(onTouchGestureListenerProxy);
        ScaleGestureDetectorApi27 scaleGestureDetectorApi27 = new ScaleGestureDetectorApi27(context, onTouchGestureListenerProxy, null, 4, null);
        this.scaleGestureDetectorApi27 = scaleGestureDetectorApi27;
        scaleGestureDetectorApi27.setQuickScaleEnabled(false);
    }

    /* JADX INFO: renamed from: isScrollAfterScaled, reason: from getter */
    public final boolean getIsScrollAfterScaled() {
        return this.isScrollAfterScaled;
    }

    public final void setScrollAfterScaled(boolean z) {
        this.isScrollAfterScaled = z;
    }

    public final void setScaleMinSpan(int minSpan) {
        this.scaleGestureDetectorApi27.setMinSpan(minSpan);
    }

    public final void setScaleSpanSlop(int spanSLop) {
        this.scaleGestureDetectorApi27.setSpanSlop(spanSLop);
    }

    public final boolean isLongpressEnabled() {
        return this.gestureDetector.isLongpressEnabled();
    }

    public final void setLongpressEnabled(boolean z) {
        this.gestureDetector.setIsLongpressEnabled(z);
    }

    public final boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getAction() == 1 || event.getAction() == 3 || event.getAction() == 4) {
            this.onTouchGestureListener.onUpOrCancel(event);
        }
        boolean zOnTouchEvent = this.scaleGestureDetectorApi27.onTouchEvent(event);
        return !this.scaleGestureDetectorApi27.getIsInProgress() ? this.gestureDetector.onTouchEvent(event) | zOnTouchEvent : zOnTouchEvent;
    }

    /* JADX INFO: compiled from: TouchGestureDetector.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J*\u0010\n\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016J\u0010\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0005H\u0016J*\u0010\u0014\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0010\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010 \u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016¨\u0006!"}, d2 = {"Lcom/miui/doodle/base/TouchGestureDetector$OnTouchGestureListener;", "Lcom/miui/doodle/base/TouchGestureDetector$IOnTouchGestureListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onDown", "", "e", "Landroid/view/MotionEvent;", "onUpOrCancel", "", "onFling", "e1", "e2", "velocityX", "", "velocityY", "onLongPress", "onScrollBegin", "onScrollEnd", "needTrim", "onScroll", "distanceX", "distanceY", "onShowPress", "onSingleTapUp", "onDoubleTap", "onDoubleTapEvent", "onSingleTapConfirmed", "onScale", "detector", "Lcom/miui/doodle/base/ScaleGestureDetectorApi27;", "onScaleBegin", "onScaleEnd", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static abstract class OnTouchGestureListener implements IOnTouchGestureListener {
        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            return false;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Intrinsics.checkNotNullParameter(e2, "e2");
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
        }

        @Override // com.miui.doodle.base.ScaleGestureDetectorApi27.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetectorApi27 detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
            return false;
        }

        @Override // com.miui.doodle.base.ScaleGestureDetectorApi27.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetectorApi27 detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
            return false;
        }

        @Override // com.miui.doodle.base.ScaleGestureDetectorApi27.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetectorApi27 detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Intrinsics.checkNotNullParameter(e2, "e2");
            return false;
        }

        @Override // com.miui.doodle.base.TouchGestureDetector.IOnTouchGestureListener
        public void onScrollBegin(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
        }

        public void onScrollEnd(MotionEvent e, boolean needTrim) {
            Intrinsics.checkNotNullParameter(e, "e");
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            return false;
        }

        @Override // com.miui.doodle.base.TouchGestureDetector.IOnTouchGestureListener
        public void onUpOrCancel(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
        }

        @Override // com.miui.doodle.base.TouchGestureDetector.IOnTouchGestureListener
        public void onScrollEnd(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            onScrollEnd(e, false);
        }
    }

    /* JADX INFO: compiled from: TouchGestureDetector.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\tH\u0016J*\u0010\u000e\u001a\u00020\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\tH\u0016J*\u0010\u0017\u001a\u00020\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0012H\u0016J\u0010\u0010\u001a\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0010\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0010\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0010\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0010\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0010\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00062\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010#\u001a\u00020\r2\u0006\u0010 \u001a\u00020!H\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/miui/doodle/base/TouchGestureDetector$OnTouchGestureListenerProxy;", "Lcom/miui/doodle/base/TouchGestureDetector$IOnTouchGestureListener;", "listener", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/TouchGestureDetector;Lcom/miui/doodle/base/TouchGestureDetector$IOnTouchGestureListener;)V", "hasScaled", "", "isScrolling", "lastScrollMotionEvent", "Landroid/view/MotionEvent;", "onDown", "e", "onUpOrCancel", "", "onFling", "e1", "e2", "velocityX", "", "velocityY", "onLongPress", "onScrollBegin", "onScrollEnd", "onScroll", "distanceX", "distanceY", "onShowPress", "onSingleTapUp", "onDoubleTap", "onDoubleTapEvent", "onSingleTapConfirmed", "onScale", "detector", "Lcom/miui/doodle/base/ScaleGestureDetectorApi27;", "onScaleBegin", "onScaleEnd", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class OnTouchGestureListenerProxy implements IOnTouchGestureListener {
        private boolean hasScaled;
        private boolean isScrolling;
        private MotionEvent lastScrollMotionEvent;
        private final IOnTouchGestureListener listener;
        final /* synthetic */ TouchGestureDetector this$0;

        public OnTouchGestureListenerProxy(TouchGestureDetector touchGestureDetector, IOnTouchGestureListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.this$0 = touchGestureDetector;
            this.listener = listener;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            this.hasScaled = false;
            this.isScrolling = false;
            return this.listener.onDown(e);
        }

        @Override // com.miui.doodle.base.TouchGestureDetector.IOnTouchGestureListener
        public void onUpOrCancel(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            this.listener.onUpOrCancel(e);
            if (this.isScrolling) {
                this.isScrolling = false;
                this.lastScrollMotionEvent = null;
                onScrollEnd(e);
            }
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Intrinsics.checkNotNullParameter(e2, "e2");
            return this.listener.onFling(e1, e2, velocityX, velocityY);
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            this.listener.onLongPress(e);
        }

        @Override // com.miui.doodle.base.TouchGestureDetector.IOnTouchGestureListener
        public void onScrollBegin(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            this.listener.onScrollBegin(e);
        }

        @Override // com.miui.doodle.base.TouchGestureDetector.IOnTouchGestureListener
        public void onScrollEnd(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            this.listener.onScrollEnd(e);
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Intrinsics.checkNotNullParameter(e2, "e2");
            if (!this.this$0.getIsScrollAfterScaled() && this.hasScaled) {
                this.isScrolling = false;
                return false;
            }
            if (!this.isScrolling) {
                this.isScrolling = true;
                if (e1 != null) {
                    onScrollBegin(e1);
                }
            }
            this.lastScrollMotionEvent = MotionEvent.obtain(e2);
            return this.listener.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            this.listener.onShowPress(e);
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            return this.listener.onSingleTapUp(e);
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            return this.listener.onDoubleTap(e);
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            return this.listener.onDoubleTapEvent(e);
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            return this.listener.onSingleTapConfirmed(e);
        }

        @Override // com.miui.doodle.base.ScaleGestureDetectorApi27.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetectorApi27 detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
            return this.listener.onScale(detector);
        }

        @Override // com.miui.doodle.base.ScaleGestureDetectorApi27.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetectorApi27 detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
            this.hasScaled = true;
            if (this.isScrolling) {
                this.isScrolling = false;
                MotionEvent motionEvent = this.lastScrollMotionEvent;
                if (motionEvent != null) {
                    onScrollEnd(motionEvent);
                }
            }
            return this.listener.onScaleBegin(detector);
        }

        @Override // com.miui.doodle.base.ScaleGestureDetectorApi27.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetectorApi27 detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
            this.listener.onScaleEnd(detector);
        }
    }
}
