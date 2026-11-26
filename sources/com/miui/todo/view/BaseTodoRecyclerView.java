package com.miui.todo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.base.todolist.BaseTodoChildItemVh;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;
import miuix.recyclerview.widget.RecyclerView;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes4.dex */
public class BaseTodoRecyclerView extends RecyclerView {
    private static final int INVALID_POSITION = -1;
    private static final String TAG = "BaseTodoRecyclerView";
    private int mDownX;
    private int mDownY;
    private boolean mHasMenuSwiping;
    protected SwipeMenuLayout mOldSwipedLayout;
    protected int mOldTouchedPosition;
    protected int mScaleTouchSlop;

    public BaseTodoRecyclerView(Context context) {
        this(context, null);
    }

    public BaseTodoRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseTodoRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOldTouchedPosition = -1;
        this.mHasMenuSwiping = false;
        this.mScaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override // androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return onInterceptTouchEvent(motionEvent, getChildAdapterPosition(findChildViewUnder((int) motionEvent.getX(), (int) motionEvent.getY())));
    }

    @Override // androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mHasMenuSwiping) {
            return false;
        }
        try {
            return super.onTouchEvent(motionEvent);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0031  */
    protected boolean onInterceptTouchEvent(MotionEvent motionEvent, int i) {
        SwipeMenuLayout swipeMenuLayout;
        SwipeMenuLayout swipeMenuLayout2;
        ViewParent parent;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        boolean zOnInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        if (motionEvent.getPointerCount() > 1) {
            return true;
        }
        androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = findViewHolderForAdapterPosition(i);
        if (viewHolderFindViewHolderForAdapterPosition == null || !(viewHolderFindViewHolderForAdapterPosition instanceof BaseTodoChildItemVh)) {
            swipeMenuLayout = null;
        } else {
            View viewFindViewById = viewHolderFindViewHolderForAdapterPosition.itemView.findViewById(R.id.sl_item_group);
            if (viewFindViewById instanceof SwipeMenuLayout) {
                swipeMenuLayout = (SwipeMenuLayout) viewFindViewById;
            } else {
                swipeMenuLayout = null;
            }
        }
        int action = motionEvent.getAction();
        boolean z = false;
        if (action == 0) {
            this.mDownX = x;
            this.mDownY = y;
            if (i != this.mOldTouchedPosition && (swipeMenuLayout2 = this.mOldSwipedLayout) != null && swipeMenuLayout2.isMenuOpen()) {
                this.mOldSwipedLayout.closeMenuByTouchDown();
                requestDisallowParentInterceptTouchEvent(true);
                z = true;
            }
            if (z) {
                this.mOldSwipedLayout = null;
                this.mOldTouchedPosition = -1;
                return z;
            }
            if (swipeMenuLayout != null) {
                this.mOldSwipedLayout = swipeMenuLayout;
                this.mOldTouchedPosition = i;
                requestDisallowParentInterceptTouchEvent(true);
            }
            return z;
        }
        if (action != 1) {
            if (action == 2) {
                boolean zHandleUnDown = handleUnDown(x, y, zOnInterceptTouchEvent);
                if (this.mOldSwipedLayout != null && (parent = getParent()) != null) {
                    handleMoveEvent(this.mDownX - x, parent);
                    if (zHandleUnDown && this.mHasMenuSwiping) {
                        return false;
                    }
                }
                return zHandleUnDown;
            }
            if (action != 3) {
                return zOnInterceptTouchEvent;
            }
        }
        return handleUnDown(x, y, zOnInterceptTouchEvent);
    }

    public void handleMoveEvent(int i, ViewParent viewParent) {
        boolean z = true;
        boolean z2 = i > 0 && (this.mOldSwipedLayout.hasRightMenu() || this.mOldSwipedLayout.isLeftCompleteOpen());
        boolean z3 = i < 0 && (this.mOldSwipedLayout.hasLeftMenu() || this.mOldSwipedLayout.isRightCompleteOpen());
        viewParent.requestDisallowInterceptTouchEvent(z2 || z3);
        if (!z2 && !z3) {
            z = false;
        }
        requestDisallowParentInterceptTouchEvent(z);
    }

    public void setMenuSwiping(boolean z) {
        this.mHasMenuSwiping = z;
    }

    public boolean isInSwipingMode() {
        return this.mHasMenuSwiping;
    }

    public boolean hasMenuOpen() {
        SwipeMenuLayout swipeMenuLayout = this.mOldSwipedLayout;
        return swipeMenuLayout != null && swipeMenuLayout.isMenuOpen();
    }

    public void requestDisallowParentInterceptTouchEvent(boolean z) {
        ViewParent parent = getParent();
        parent.requestDisallowInterceptTouchEvent(z);
        while (parent != null) {
            if (parent instanceof SpringBackLayout) {
                ((SpringBackLayout) parent).internalRequestDisallowInterceptTouchEvent(z);
            }
            parent = parent.getParent();
        }
    }

    public boolean closeOpenedItem() {
        SwipeMenuLayout swipeMenuLayout = this.mOldSwipedLayout;
        if (swipeMenuLayout == null || !swipeMenuLayout.isMenuOpen()) {
            return false;
        }
        this.mOldSwipedLayout.closeMenuByTouchDown();
        this.mOldSwipedLayout = null;
        this.mOldTouchedPosition = -1;
        return true;
    }

    private boolean handleUnDown(int i, int i2, boolean z) {
        int i3 = this.mDownX - i;
        int i4 = this.mDownY - i2;
        Logger.INSTANCE.d(TAG, " handleUnDown defaultValue =" + z + " disX =" + i3 + " disY =" + i4);
        if (Math.abs(i3) > this.mScaleTouchSlop && Math.abs(i3) > Math.abs(i4)) {
            return false;
        }
        if (Math.abs(i4) < this.mScaleTouchSlop && Math.abs(i3) < this.mScaleTouchSlop) {
            return false;
        }
        if (Math.abs(i4) > this.mScaleTouchSlop && Math.abs(i3) > this.mScaleTouchSlop && Math.abs(i3) < Math.abs(i4)) {
            return true;
        }
        if (Math.abs(i4) == 0 && Math.abs(i3) == 0) {
            return false;
        }
        return z;
    }

    @Override // miuix.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        Logger.INSTANCE.i(TAG, "dispatch event = " + motionEvent.toString());
        return super.dispatchTouchEvent(motionEvent);
    }
}
