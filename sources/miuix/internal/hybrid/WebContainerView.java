package miuix.internal.hybrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import miuix.hybrid.R;

/* JADX INFO: loaded from: classes5.dex */
public class WebContainerView extends FrameLayout {
    private boolean mIsPulling;
    private float mLastX;
    private float mLastY;
    private boolean mPullable;
    private int mTouchSlop;
    private View mWebView;

    public WebContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsPulling = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HybridViewStyle, 0, 0);
        this.mPullable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.HybridViewStyle_hybridPullable, true);
        typedArrayObtainStyledAttributes.recycle();
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setWebView(View view) {
        View view2;
        if (view == null || (view2 = this.mWebView) == view) {
            return;
        }
        if (view2 != null) {
            removeView(view2);
        }
        this.mWebView = view;
        addView(view, 0, new FrameLayout.LayoutParams(-1, -1));
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mWebView != null && this.mPullable) {
            int actionMasked = motionEvent.getActionMasked();
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            if (actionMasked != 3 && actionMasked != 1) {
                if (actionMasked == 0) {
                    this.mLastX = rawX;
                    this.mLastY = rawY;
                } else if (actionMasked == 2) {
                    if (this.mIsPulling) {
                        return true;
                    }
                    float f = this.mLastX - rawX;
                    float f2 = this.mLastY - rawY;
                    float fAbs = Math.abs(f);
                    float fAbs2 = Math.abs(f2);
                    this.mLastX = rawX;
                    this.mLastY = rawY;
                    if (this.mWebView.getScrollY() == 0 && f2 < 0.0f && fAbs2 > fAbs && fAbs2 > this.mTouchSlop) {
                        this.mIsPulling = true;
                        return true;
                    }
                }
                return false;
            }
            this.mIsPulling = false;
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0030  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mPullable && this.mIsPulling) {
            float rawY = motionEvent.getRawY();
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mLastY = rawY;
            } else if (action == 1) {
                this.mIsPulling = false;
                springBack();
            } else if (action == 2) {
                setTranslationY(getTranslationY() + ((rawY - this.mLastY) * 0.5f));
                this.mLastY = rawY;
            } else if (action == 3) {
                this.mIsPulling = false;
                springBack();
            }
        }
        return false;
    }

    private void springBack() {
        if (getTranslationY() == 0.0f) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimatorAnimate = animate();
        viewPropertyAnimatorAnimate.translationY(0.0f);
        viewPropertyAnimatorAnimate.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        viewPropertyAnimatorAnimate.start();
    }
}
