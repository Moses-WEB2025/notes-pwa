package miuix.springback.view;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import miuix.core.util.EnvStateManager;
import miuix.core.widget.NestedScrollView;

/* JADX INFO: loaded from: classes6.dex */
public class EmptyStateInflationDelegate {
    public static final float EMPTY_STATE_TOP_FRACTION = 0.4f;
    private NestedScrollView mContainer;
    private FrameLayout.LayoutParams mContainerParams;
    private OnDeflateListener mDeflateListener;
    private int mEmptyStateRes;
    private View mEmptyStateView;
    private OnInflateListener mInflateListener;
    private View.OnLayoutChangeListener mOnLayoutChangeListener;
    private View mOriginalTarget;
    private SpringBackLayout mSpringBack;
    private View mSpringBackParent;
    private Point mWindowSize = new Point();
    private Rect mGlobalVisibleRect = new Rect();
    private boolean mAttachToRoot = false;
    private boolean mInflated = false;
    private boolean mOverlay = false;
    private int mTopInset = 0;
    private int mBottomInset = 0;

    public interface OnDeflateListener {
        void onDeflate(View view);
    }

    public interface OnInflateListener {
        void onInflate(View view);
    }

    public EmptyStateInflationDelegate(SpringBackLayout springBackLayout, int i) {
        Context context = springBackLayout.getContext();
        this.mEmptyStateRes = i;
        this.mContainer = new NestedScrollView(context);
        this.mContainerParams = new FrameLayout.LayoutParams(-1, -1);
        this.mSpringBack = springBackLayout;
    }

    private View.OnLayoutChangeListener getOnLayoutChangeListener() {
        return new View.OnLayoutChangeListener() { // from class: miuix.springback.view.EmptyStateInflationDelegate.1
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (EmptyStateInflationDelegate.this.mEmptyStateView == null || !EmptyStateInflationDelegate.this.isInflated()) {
                    return;
                }
                if (EmptyStateInflationDelegate.this.mOverlay) {
                    EmptyStateInflationDelegate.this.layoutEmptyStateOverlay();
                } else {
                    EmptyStateInflationDelegate.this.layoutEmptyState();
                }
            }
        };
    }

    private void createEmptyStateView() {
        if (this.mEmptyStateView != null) {
            return;
        }
        View viewInflate = LayoutInflater.from(this.mSpringBack.getContext()).inflate(this.mEmptyStateRes, (ViewGroup) null);
        this.mEmptyStateView = viewInflate;
        this.mContainer.addView(viewInflate);
        Object parent = this.mSpringBack.getParent();
        if (parent instanceof View) {
            this.mSpringBackParent = (View) parent;
            View.OnLayoutChangeListener onLayoutChangeListener = getOnLayoutChangeListener();
            this.mOnLayoutChangeListener = onLayoutChangeListener;
            this.mSpringBackParent.addOnLayoutChangeListener(onLayoutChangeListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void layoutEmptyState() {
        int measuredWidth = this.mContainer.getMeasuredWidth();
        int iMeasureContainerHeightInWindow = measureContainerHeightInWindow(this.mContainer.getMeasuredHeight());
        int measuredWidth2 = this.mEmptyStateView.getMeasuredWidth();
        int measuredHeight = this.mEmptyStateView.getMeasuredHeight();
        int i = (int) ((iMeasureContainerHeightInWindow - measuredHeight) * 0.4f);
        int i2 = (measuredWidth - measuredWidth2) >> 1;
        this.mEmptyStateView.layout(i2, i, measuredWidth2 + i2, measuredHeight + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void layoutEmptyStateOverlay() {
        if (this.mEmptyStateView.getTop() == 0) {
            this.mEmptyStateView.setVisibility(4);
        }
        this.mSpringBack.post(new Runnable() { // from class: miuix.springback.view.EmptyStateInflationDelegate$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4361x425eedc4();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$layoutEmptyStateOverlay$0$miuix-springback-view-EmptyStateInflationDelegate, reason: not valid java name */
    /* synthetic */ void m4361x425eedc4() {
        this.mEmptyStateView.setVisibility(0);
        int measuredWidth = this.mContainer.getMeasuredWidth();
        int iMeasureContainerHeightInWindowOverlay = measureContainerHeightInWindowOverlay();
        int measuredWidth2 = this.mEmptyStateView.getMeasuredWidth();
        int measuredHeight = this.mEmptyStateView.getMeasuredHeight();
        int i = ((int) ((iMeasureContainerHeightInWindowOverlay - measuredHeight) * 0.4f)) + this.mTopInset;
        int i2 = (measuredWidth - measuredWidth2) >> 1;
        this.mEmptyStateView.layout(i2, i, measuredWidth2 + i2, measuredHeight + i);
    }

    private int measureContainerHeightInWindow(int i) {
        this.mContainer.getGlobalVisibleRect(this.mGlobalVisibleRect);
        int i2 = this.mGlobalVisibleRect.top;
        Point windowSize = EnvStateManager.getWindowSize(this.mContainer.getContext());
        this.mWindowSize = windowSize;
        int i3 = windowSize.y;
        int i4 = i2 + i;
        int i5 = this.mBottomInset;
        return i4 <= i3 - i5 ? i : (i3 - i2) - i5;
    }

    private int measureContainerHeightInWindowOverlay() {
        int i;
        int i2;
        this.mContainer.getGlobalVisibleRect(this.mGlobalVisibleRect);
        int i3 = this.mGlobalVisibleRect.top;
        Point windowSize = EnvStateManager.getWindowSize(this.mContainer.getContext());
        this.mWindowSize = windowSize;
        int i4 = windowSize.y;
        if (i3 > 0) {
            this.mTopInset = 0;
            i = i4 - i3;
            i2 = this.mBottomInset;
        } else {
            i = i4 - this.mTopInset;
            i2 = this.mBottomInset;
        }
        return i - i2;
    }

    public void onDetachedFromWindow() {
        View.OnLayoutChangeListener onLayoutChangeListener;
        View view = this.mSpringBackParent;
        if (view == null || (onLayoutChangeListener = this.mOnLayoutChangeListener) == null) {
            return;
        }
        view.removeOnLayoutChangeListener(onLayoutChangeListener);
        this.mOnLayoutChangeListener = null;
    }

    public void inflate() {
        if (isInflated()) {
            return;
        }
        for (int i = 0; i < this.mSpringBack.getChildCount(); i++) {
            this.mSpringBack.getChildAt(i).setVisibility(8);
        }
        if (!this.mAttachToRoot) {
            this.mSpringBack.addView(this.mContainer, this.mContainerParams);
            createEmptyStateView();
            this.mAttachToRoot = true;
        } else {
            this.mContainer.setVisibility(0);
        }
        this.mInflated = true;
        this.mOriginalTarget = this.mSpringBack.getTarget();
        this.mSpringBack.setTarget(this.mContainer);
        OnInflateListener onInflateListener = this.mInflateListener;
        if (onInflateListener != null) {
            onInflateListener.onInflate(this.mEmptyStateView);
        }
    }

    public void deflate() {
        if (isEnabled() && isInflated() && this.mAttachToRoot) {
            for (int i = 0; i < this.mSpringBack.getChildCount(); i++) {
                View childAt = this.mSpringBack.getChildAt(i);
                childAt.setVisibility(childAt.equals(this.mContainer) ? 8 : 0);
            }
            View view = this.mOriginalTarget;
            if (view != null) {
                this.mSpringBack.setTarget(view);
            }
            OnDeflateListener onDeflateListener = this.mDeflateListener;
            if (onDeflateListener != null) {
                onDeflateListener.onDeflate(this.mEmptyStateView);
            }
            this.mInflated = false;
        }
    }

    public View getEmptyState() {
        return this.mEmptyStateView;
    }

    public boolean isEnabled() {
        return this.mEmptyStateView != null;
    }

    public boolean isInflated() {
        return this.mInflated;
    }

    public void setOnInflateListener(OnInflateListener onInflateListener) {
        this.mInflateListener = onInflateListener;
    }

    public void setOnDeflateListener(OnDeflateListener onDeflateListener) {
        this.mDeflateListener = onDeflateListener;
    }

    public void setOverlay(boolean z) {
        this.mOverlay = z;
    }

    public void setTopInset(int i) {
        this.mTopInset = i;
    }

    public void setBottomInset(int i) {
        this.mBottomInset = i;
    }
}
