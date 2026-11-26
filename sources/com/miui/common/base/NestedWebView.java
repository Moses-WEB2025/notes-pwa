package com.miui.common.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;
import android.webkit.WebView;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import miuix.overscroller.widget.OverScroller;

/* JADX INFO: loaded from: classes2.dex */
public class NestedWebView extends WebView implements NestedScrollingChild2 {
    private static final String TAG = "NestedWebView";
    private boolean isFlinging;
    private int mActivePointerId;
    private float mDisPlayY;
    private boolean mIsBeingDragged;
    private boolean mIsFullWindowGesture;
    private boolean mIsPadDevice;
    private int mLastComputeScrollerY;
    private int mLastMotionY;
    private int mLastScrollerY;
    private int mLastY;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mNestedYOffset;
    private OnCommitContentListener mOnCommitContentListener;
    private final int[] mScrollConsumed;
    private final int[] mScrollOffset;
    private OverScroller mScroller;
    private NestedScrollingChildHelper mScrollingChildHelper;
    private int mTouchSlop;
    private float mTriggerHeight;
    private VelocityTracker mVelocityTracker;
    private int moveDistance;

    public interface OnCommitContentListener {
        boolean onCommitContent(InputContentInfo inputContentInfo, int i, Bundle bundle);
    }

    public void setOnCommitContentListener(OnCommitContentListener onCommitContentListener) {
        this.mOnCommitContentListener = onCommitContentListener;
    }

    public NestedWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsBeingDragged = false;
        this.mActivePointerId = -1;
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mDisPlayY = 0.0f;
        this.mTriggerHeight = 0.0f;
        this.mIsFullWindowGesture = false;
        this.mIsPadDevice = false;
        this.mScroller = new OverScroller(getContext());
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        setNestedScrollingEnabled(true);
        boolean zIsPadDevice = RomUtils.isPadDevice();
        this.mIsPadDevice = zIsPadDevice;
        if (zIsPadDevice) {
            this.mDisPlayY = DisplayUtils.getRealHeight(NoteApp.getInstance());
            this.mTriggerHeight = NoteApp.getInstance().getResources().getDimension(R.dimen.pad_trigger_height);
            this.mIsFullWindowGesture = UIUtils.isInFullWindowGestureMode(NoteApp.getInstance());
        }
    }

    public NestedWebView(Context context) {
        this(context, null);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return this.mScrollingChildHelper;
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0056  */
    /* JADX WARN: Code duplicated, block: B:52:0x0128  */
    /* JADX WARN: Code duplicated, block: B:53:0x012b  */
    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent;
        ViewParent parent;
        VelocityTracker velocityTracker;
        int i;
        int i2;
        int top;
        int top2;
        boolean z = false;
        if (motionEvent.isFromSource(16386) && motionEvent.getToolType(0) == 2) {
            return super.onTouchEvent(motionEvent);
        }
        initVelocityTrackerIfNotExists();
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mNestedYOffset = 0;
            this.isFlinging = false;
        }
        motionEventObtain.offsetLocation(0.0f, this.mNestedYOffset);
        if (actionMasked == 0) {
            if (this.mIsPadDevice && this.mIsFullWindowGesture && motionEventObtain.getY() + this.mTriggerHeight > this.mDisPlayY) {
                motionEvent.setAction(3);
            }
            boolean zIsFinished = this.mScroller.isFinished();
            this.mIsBeingDragged = !zIsFinished;
            if (!zIsFinished && (parent = getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            int y = (int) motionEvent.getY();
            this.mLastMotionY = y;
            this.mLastY = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
            startNestedScroll(2, 0);
            zOnTouchEvent = super.onTouchEvent(motionEvent);
        } else if (actionMasked == 1) {
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(motionEventObtain);
            }
            caculateV(this.mActivePointerId, (int) motionEvent.getY());
            this.mLastY = (int) motionEvent.getY();
            this.mActivePointerId = -1;
            endDrag();
            VelocityTracker velocityTracker3 = this.mVelocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.clear();
            }
            stopNestedScroll(0);
            zOnTouchEvent = super.onTouchEvent(motionEventObtain);
            z = true;
        } else if (actionMasked == 2) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            if (iFindPointerIndex == -1) {
                zOnTouchEvent = false;
            } else {
                int y2 = (int) motionEvent.getY(iFindPointerIndex);
                int i3 = this.mLastMotionY - y2;
                if (dispatchNestedPreScroll(0, i3, this.mScrollConsumed, this.mScrollOffset, 0)) {
                    Logger.INSTANCE.d(TAG, "dispatchNestedPreScroll消费：" + this.mScrollConsumed[1]);
                    i3 -= this.mScrollConsumed[1];
                    Logger.INSTANCE.d(TAG, "dispatchNestedPreScroll未消费：" + i3);
                }
                int i4 = i3;
                this.moveDistance = i4;
                if (!this.mIsBeingDragged && Math.abs(i4) > this.mTouchSlop) {
                    ViewParent parent2 = getParent();
                    if (parent2 != null) {
                        parent2.requestDisallowInterceptTouchEvent(true);
                    }
                    this.mIsBeingDragged = true;
                }
                if (this.mIsBeingDragged) {
                    this.mLastMotionY = y2 - this.mScrollOffset[1];
                    if (Math.abs(i4) <= 0) {
                        i = 0;
                        i2 = i4;
                    } else if (i4 <= 0) {
                        if (!canScrollVertically(-1)) {
                            i = 0;
                            i2 = i4;
                        } else if (getScrollY() + i4 < 0) {
                            top = -getScrollY();
                            top2 = getScrollY() + i4;
                            motionEventObtain.offsetLocation(0.0f, top2);
                            this.mNestedYOffset += top2;
                            i2 = top2;
                            i = top;
                        } else {
                            i2 = 0;
                            i = i4;
                        }
                    } else if (canScrollVertically(1)) {
                        Logger.INSTANCE.d(TAG, "canScrollVertically:deltaY:" + i4);
                        if (i4 - getTop() > 0) {
                            top = i4 - getTop();
                            top2 = getTop();
                            motionEventObtain.offsetLocation(0.0f, top2);
                            this.mNestedYOffset += top2;
                            i2 = top2;
                            i = top;
                        } else {
                            i2 = 0;
                            i = i4;
                        }
                    } else {
                        i = 0;
                        i2 = i4;
                    }
                    if (dispatchNestedScroll(0, i, 0, i2, this.mScrollOffset)) {
                        int i5 = this.mLastMotionY;
                        int i6 = this.mScrollOffset[1];
                        this.mLastMotionY = i5 - i6;
                        motionEventObtain.offsetLocation(0.0f, i6);
                        this.mNestedYOffset += this.mScrollOffset[1];
                    }
                }
                zOnTouchEvent = (i4 == 0 && this.mIsBeingDragged) ? true : super.onTouchEvent(motionEventObtain);
            }
        } else if (actionMasked != 3) {
            zOnTouchEvent = false;
        } else {
            this.mActivePointerId = -1;
            endDrag();
            VelocityTracker velocityTracker4 = this.mVelocityTracker;
            if (velocityTracker4 != null) {
                velocityTracker4.clear();
            }
            stopNestedScroll(0);
            zOnTouchEvent = super.onTouchEvent(motionEvent);
        }
        if (!z && (velocityTracker = this.mVelocityTracker) != null) {
            velocityTracker.addMovement(motionEventObtain);
        }
        motionEventObtain.recycle();
        return zOnTouchEvent;
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mIsPadDevice) {
            this.mDisPlayY = DisplayUtils.getRealHeight(NoteApp.getInstance());
        }
    }

    private void caculateV(int i, int i2) {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        if (Math.abs((int) this.mVelocityTracker.getYVelocity(i)) > this.mMinimumVelocity) {
            this.mLastScrollerY = getScrollY();
            this.isFlinging = true;
        }
    }

    @Override // android.webkit.WebView, android.view.View
    public void computeScroll() {
        NestedWebView nestedWebView;
        super.computeScroll();
        if (this.isFlinging) {
            int scrollY = getScrollY() - this.mLastScrollerY;
            if (getScrollY() == 0) {
                int i = this.mLastComputeScrollerY;
                startNestedScroll(2, 1);
                if (this.moveDistance < 0) {
                    nestedWebView = this;
                    nestedWebView.dispatchNestedScroll(0, scrollY, 0, -i, null, 1);
                } else {
                    nestedWebView = this;
                }
                nestedWebView.isFlinging = false;
                nestedWebView.stopNestedScroll(1);
            } else {
                nestedWebView = this;
            }
            nestedWebView.mLastComputeScrollerY = nestedWebView.getScrollY();
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        recycleVelocityTracker();
        stopNestedScroll(0);
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    @Override // android.webkit.WebView
    public void flingScroll(int i, int i2) {
        super.flingScroll(i, i2);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean startNestedScroll(int i, int i2) {
        return getScrollingChildHelper().startNestedScroll(i, i2);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public void stopNestedScroll(int i) {
        getScrollingChildHelper().stopNestedScroll(i);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean hasNestedScrollingParent(int i) {
        return getScrollingChildHelper().hasNestedScrollingParent(i);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr, int i5) {
        return getScrollingChildHelper().dispatchNestedScroll(i, i2, i3, i4, iArr, i5);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2, int i3) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i, i2, iArr, iArr2, i3);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void setNestedScrollingEnabled(boolean z) {
        getScrollingChildHelper().setNestedScrollingEnabled(z);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean startNestedScroll(int i) {
        return getScrollingChildHelper().startNestedScroll(i);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return getScrollingChildHelper().dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i, i2, iArr, iArr2);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return getScrollingChildHelper().dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreFling(float f, float f2) {
        return getScrollingChildHelper().dispatchNestedPreFling(f, f2);
    }

    @Override // android.webkit.WebView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        final OnCommitContentListener onCommitContentListener;
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        return (inputConnectionOnCreateInputConnection == null || (onCommitContentListener = this.mOnCommitContentListener) == null) ? inputConnectionOnCreateInputConnection : new InputConnectionWrapper(inputConnectionOnCreateInputConnection, true) { // from class: com.miui.common.base.NestedWebView.1
            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
                if (inputContentInfo != null && inputContentInfo.getDescription().hasMimeType(ShareAppsAdapter.TYPE_IMAGE) && onCommitContentListener.onCommitContent(inputContentInfo, i, bundle)) {
                    return true;
                }
                return super.commitContent(inputContentInfo, i, bundle);
            }
        };
    }
}
