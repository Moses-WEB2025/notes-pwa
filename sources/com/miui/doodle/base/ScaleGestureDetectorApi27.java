package com.miui.doodle.base;

import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ScaleGestureDetectorApi27.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 G2\u00020\u0001:\u0003EFGB%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ\u000e\u0010:\u001a\u00020\u00122\u0006\u0010;\u001a\u00020<J\b\u0010=\u001a\u00020\u0012H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000eR\u001e\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000eR\u000e\u0010\u001b\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u000eR\u001e\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u000eR\u001e\u0010 \u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u000eR\u001e\u0010\"\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u000eR\u001e\u0010%\u001a\u00020$2\u0006\u0010\n\u001a\u00020$@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u000e\u0010(\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010)\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u0012@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0014R\u001a\u0010*\u001a\u00020+X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001a\u00100\u001a\u00020+X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010-\"\u0004\b2\u0010/R\u0010\u00103\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020+X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010?\u001a\u00020\u00122\u0006\u0010>\u001a\u00020\u00128F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b?\u0010\u0014\"\u0004\b@\u0010\u0016R\u0011\u0010A\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\bB\u0010\u000eR\u0011\u0010C\u001a\u00020$8F¢\u0006\u0006\u001a\u0004\bD\u0010'¨\u0006H"}, d2 = {"Lcom/miui/doodle/base/ScaleGestureDetectorApi27;", "", "mContext", "Landroid/content/Context;", "mListener", "Lcom/miui/doodle/base/ScaleGestureDetectorApi27$OnScaleGestureListener;", "handler", "Landroid/os/Handler;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/doodle/base/ScaleGestureDetectorApi27$OnScaleGestureListener;Landroid/os/Handler;)V", "value", "", "focusX", "getFocusX", "()F", "focusY", "getFocusY", "mQuickScaleEnabled", "", "isStylusScaleEnabled", "()Z", "setStylusScaleEnabled", "(Z)V", "currentSpan", "getCurrentSpan", "previousSpan", "getPreviousSpan", "mInitialSpan", "currentSpanX", "getCurrentSpanX", "currentSpanY", "getCurrentSpanY", "previousSpanX", "getPreviousSpanX", "previousSpanY", "getPreviousSpanY", "", "eventTime", "getEventTime", "()J", "mPrevTime", "isInProgress", "spanSlop", "", "getSpanSlop", "()I", "setSpanSlop", "(I)V", "minSpan", "getMinSpan", "setMinSpan", "mHandler", "mAnchoredScaleStartX", "mAnchoredScaleStartY", "mAnchoredScaleMode", "mGestureDetector", "Landroid/view/GestureDetector;", "mEventBeforeOrAboveStartingGestureEvent", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "inAnchoredScaleMode", "scales", "isQuickScaleEnabled", "setQuickScaleEnabled", "scaleFactor", "getScaleFactor", "timeDelta", "getTimeDelta", "SimpleOnScaleGestureListener", "OnScaleGestureListener", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ScaleGestureDetectorApi27 {
    private static final int ANCHORED_SCALE_MODE_DOUBLE_TAP = 1;
    private static final int ANCHORED_SCALE_MODE_NONE = 0;
    private static final int ANCHORED_SCALE_MODE_STYLUS = 2;
    private static final float SCALE_FACTOR = 0.5f;
    private static final String TAG = "ScaleGestureDetectorApi27";
    private static final long TOUCH_STABILIZE_TIME = 128;
    private float currentSpan;
    private float currentSpanX;
    private float currentSpanY;
    private long eventTime;
    private float focusX;
    private float focusY;
    private boolean isInProgress;
    private boolean isStylusScaleEnabled;
    private int mAnchoredScaleMode;
    private float mAnchoredScaleStartX;
    private float mAnchoredScaleStartY;
    private final Context mContext;
    private boolean mEventBeforeOrAboveStartingGestureEvent;
    private GestureDetector mGestureDetector;
    private final Handler mHandler;
    private float mInitialSpan;
    private final OnScaleGestureListener mListener;
    private long mPrevTime;
    private boolean mQuickScaleEnabled;
    private int minSpan;
    private float previousSpan;
    private float previousSpanX;
    private float previousSpanY;
    private int spanSlop;

    /* JADX INFO: compiled from: ScaleGestureDetectorApi27.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\tÀ\u0006\u0003"}, d2 = {"Lcom/miui/doodle/base/ScaleGestureDetectorApi27$OnScaleGestureListener;", "", "onScale", "", "detector", "Lcom/miui/doodle/base/ScaleGestureDetectorApi27;", "onScaleBegin", "onScaleEnd", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface OnScaleGestureListener {
        boolean onScale(ScaleGestureDetectorApi27 detector);

        boolean onScaleBegin(ScaleGestureDetectorApi27 detector);

        void onScaleEnd(ScaleGestureDetectorApi27 detector);
    }

    /* JADX INFO: compiled from: ScaleGestureDetectorApi27.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\u000b"}, d2 = {"Lcom/miui/doodle/base/ScaleGestureDetectorApi27$SimpleOnScaleGestureListener;", "Landroid/view/ScaleGestureDetector$OnScaleGestureListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onScale", "", "detector", "Landroid/view/ScaleGestureDetector;", "onScaleBegin", "onScaleEnd", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class SimpleOnScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
            return false;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
            return true;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ScaleGestureDetectorApi27(Context mContext, OnScaleGestureListener mListener) {
        this(mContext, mListener, null, 4, null);
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(mListener, "mListener");
    }

    public ScaleGestureDetectorApi27(Context mContext, OnScaleGestureListener mListener, Handler handler) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(mListener, "mListener");
        this.mContext = mContext;
        this.mListener = mListener;
        this.spanSlop = ViewConfiguration.get(mContext).getScaledTouchSlop() * 2;
        this.minSpan = mContext.getResources().getDimensionPixelSize(R.dimen.androids_api27_config_minScalingSpan);
        this.mHandler = handler;
        int i = mContext.getApplicationInfo().targetSdkVersion;
        if (i > 18) {
            setQuickScaleEnabled(true);
        }
        if (i > 22) {
            this.isStylusScaleEnabled = true;
        }
    }

    public /* synthetic */ ScaleGestureDetectorApi27(Context context, OnScaleGestureListener onScaleGestureListener, Handler handler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, onScaleGestureListener, (i & 4) != 0 ? null : handler);
    }

    public final float getFocusX() {
        return this.focusX;
    }

    public final float getFocusY() {
        return this.focusY;
    }

    /* JADX INFO: renamed from: isStylusScaleEnabled, reason: from getter */
    public final boolean getIsStylusScaleEnabled() {
        return this.isStylusScaleEnabled;
    }

    public final void setStylusScaleEnabled(boolean z) {
        this.isStylusScaleEnabled = z;
    }

    public final float getCurrentSpan() {
        return this.currentSpan;
    }

    public final float getPreviousSpan() {
        return this.previousSpan;
    }

    public final float getCurrentSpanX() {
        return this.currentSpanX;
    }

    public final float getCurrentSpanY() {
        return this.currentSpanY;
    }

    public final float getPreviousSpanX() {
        return this.previousSpanX;
    }

    public final float getPreviousSpanY() {
        return this.previousSpanY;
    }

    public final long getEventTime() {
        return this.eventTime;
    }

    /* JADX INFO: renamed from: isInProgress, reason: from getter */
    public final boolean getIsInProgress() {
        return this.isInProgress;
    }

    public final int getSpanSlop() {
        return this.spanSlop;
    }

    public final void setSpanSlop(int i) {
        this.spanSlop = i;
    }

    public final int getMinSpan() {
        return this.minSpan;
    }

    public final void setMinSpan(int i) {
        this.minSpan = i;
    }

    public final boolean onTouchEvent(MotionEvent event) {
        float f;
        float f2;
        Intrinsics.checkNotNullParameter(event, "event");
        this.eventTime = event.getEventTime();
        int actionMasked = event.getActionMasked();
        if (this.mQuickScaleEnabled) {
            GestureDetector gestureDetector = this.mGestureDetector;
            Intrinsics.checkNotNull(gestureDetector);
            gestureDetector.onTouchEvent(event);
        }
        int pointerCount = event.getPointerCount();
        boolean z = (event.getButtonState() & 32) != 0;
        boolean z2 = this.mAnchoredScaleMode == 2 && !z;
        boolean z3 = actionMasked == 1 || actionMasked == 3 || z2;
        float fAbs = 0.0f;
        if (actionMasked == 0 || z3) {
            if (this.isInProgress) {
                this.mListener.onScaleEnd(this);
                this.isInProgress = false;
                this.mInitialSpan = 0.0f;
                this.mAnchoredScaleMode = 0;
            } else if (inAnchoredScaleMode() && z3) {
                this.isInProgress = false;
                this.mInitialSpan = 0.0f;
                this.mAnchoredScaleMode = 0;
            }
            if (z3) {
                return true;
            }
        }
        if (!this.isInProgress && this.isStylusScaleEnabled && !inAnchoredScaleMode() && !z3 && z) {
            this.mAnchoredScaleStartX = event.getX();
            this.mAnchoredScaleStartY = event.getY();
            this.mAnchoredScaleMode = 2;
            this.mInitialSpan = 0.0f;
        }
        boolean z4 = actionMasked == 0 || actionMasked == 6 || actionMasked == 5 || z2;
        boolean z5 = actionMasked == 6;
        int actionIndex = z5 ? event.getActionIndex() : -1;
        int i = z5 ? pointerCount - 1 : pointerCount;
        if (inAnchoredScaleMode()) {
            f2 = this.mAnchoredScaleStartX;
            f = this.mAnchoredScaleStartY;
            this.mEventBeforeOrAboveStartingGestureEvent = event.getY() < f;
        } else {
            float x = 0.0f;
            float y = 0.0f;
            for (int i2 = 0; i2 < pointerCount; i2++) {
                if (actionIndex != i2) {
                    x += event.getX(i2);
                    y += event.getY(i2);
                }
            }
            float f3 = i;
            float f4 = x / f3;
            f = y / f3;
            f2 = f4;
        }
        float fAbs2 = 0.0f;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            if (actionIndex != i3) {
                fAbs += Math.abs(event.getX(i3) - f2);
                fAbs2 += Math.abs(event.getY(i3) - f);
            }
        }
        float f5 = i;
        float f6 = (fAbs / f5) * 2.0f;
        float f7 = (fAbs2 / f5) * 2.0f;
        float fHypot = inAnchoredScaleMode() ? f7 : (float) Math.hypot(f6, f7);
        boolean z6 = this.isInProgress;
        this.focusX = f2;
        this.focusY = f;
        if (!inAnchoredScaleMode() && this.isInProgress && (fHypot < this.minSpan || z4)) {
            this.mListener.onScaleEnd(this);
            this.isInProgress = false;
            this.mInitialSpan = fHypot;
        }
        if (z4) {
            this.currentSpanX = f6;
            this.previousSpanX = f6;
            this.currentSpanY = f7;
            this.previousSpanY = f7;
            this.currentSpan = fHypot;
            this.previousSpan = fHypot;
            this.mInitialSpan = fHypot;
        }
        int i4 = inAnchoredScaleMode() ? this.spanSlop : this.minSpan;
        if (!this.isInProgress && fHypot >= i4 && (z6 || Math.abs(fHypot - this.mInitialSpan) > this.spanSlop)) {
            this.currentSpanX = f6;
            this.previousSpanX = f6;
            this.currentSpanY = f7;
            this.previousSpanY = f7;
            this.currentSpan = fHypot;
            this.previousSpan = fHypot;
            this.mPrevTime = this.eventTime;
            this.isInProgress = this.mListener.onScaleBegin(this);
        }
        if (actionMasked == 2) {
            this.currentSpanX = f6;
            this.currentSpanY = f7;
            this.currentSpan = fHypot;
            if (this.isInProgress ? this.mListener.onScale(this) : true) {
                this.previousSpanX = this.currentSpanX;
                this.previousSpanY = this.currentSpanY;
                this.previousSpan = this.currentSpan;
                this.mPrevTime = this.eventTime;
            }
        }
        return true;
    }

    private final boolean inAnchoredScaleMode() {
        return this.mAnchoredScaleMode != 0;
    }

    /* JADX INFO: renamed from: isQuickScaleEnabled, reason: from getter */
    public final boolean getMQuickScaleEnabled() {
        return this.mQuickScaleEnabled;
    }

    public final void setQuickScaleEnabled(boolean z) {
        this.mQuickScaleEnabled = z;
        if (z && this.mGestureDetector == null) {
            this.mGestureDetector = new GestureDetector(this.mContext, new GestureDetector.SimpleOnGestureListener() { // from class: com.miui.doodle.base.ScaleGestureDetectorApi27$isQuickScaleEnabled$gestureListener$1
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
                public boolean onDoubleTap(MotionEvent e) {
                    Intrinsics.checkNotNullParameter(e, "e");
                    this.this$0.mAnchoredScaleStartX = e.getX();
                    this.this$0.mAnchoredScaleStartY = e.getY();
                    this.this$0.mAnchoredScaleMode = 1;
                    return true;
                }
            }, this.mHandler);
        }
    }

    public final float getScaleFactor() {
        if (!inAnchoredScaleMode()) {
            float f = this.previousSpan;
            if (f > 0.0f) {
                return this.currentSpan / f;
            }
            return 1.0f;
        }
        boolean z = this.mEventBeforeOrAboveStartingGestureEvent;
        boolean z2 = (z && this.currentSpan < this.previousSpan) || (!z && this.currentSpan > this.previousSpan);
        float fAbs = Math.abs(1.0f - (this.currentSpan / this.previousSpan)) * 0.5f;
        if (this.previousSpan <= 0.0f) {
            return 1.0f;
        }
        return z2 ? fAbs + 1.0f : 1.0f - fAbs;
    }

    public final long getTimeDelta() {
        return this.eventTime - this.mPrevTime;
    }
}
