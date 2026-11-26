package com.miui.todo.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.view.BaseTodoRecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class ProTodoRecyclerView extends BaseTodoRecyclerView {
    private static final String TAG = "PcTodoRecyclerView";
    private GestureDetector mDetector;
    private int mDownX;
    private int mDownY;
    private OnRightClickListener mRightClickListener;
    protected int mScaleTouchSlop;

    public interface OnRightClickListener {
        void onRightClick(float f, float f2, int i);
    }

    public ProTodoRecyclerView(Context context) {
        this(context, null);
    }

    public ProTodoRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ProTodoRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRightClickListener = null;
        this.mScaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setSpringEnabled(false);
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.mRightClickListener = onRightClickListener;
        this.mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.miui.todo.base.ProTodoRecyclerView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnContextClickListener
            public boolean onContextClick(MotionEvent motionEvent) {
                if (motionEvent.getActionButton() == 2 && ProTodoRecyclerView.this.mRightClickListener != null) {
                    Logger.INSTANCE.d(ProTodoRecyclerView.TAG, "onContextClick: right click");
                    ProTodoRecyclerView proTodoRecyclerView = ProTodoRecyclerView.this;
                    ProTodoRecyclerView.this.mRightClickListener.onRightClick(motionEvent.getRawX(), motionEvent.getRawY(), proTodoRecyclerView.getChildAdapterPosition(proTodoRecyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY())));
                }
                return super.onContextClick(motionEvent);
            }
        });
    }

    @Override // androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mDetector != null && motionEvent.getAction() != 8) {
            return this.mDetector.onGenericMotionEvent(motionEvent);
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // com.miui.todo.view.BaseTodoRecyclerView, androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mDownX = (int) motionEvent.getX();
            this.mDownY = (int) motionEvent.getY();
        } else if (action == 1 && findChildViewUnder(this.mDownX, this.mDownY) == null && Math.abs(((int) motionEvent.getX()) - this.mDownX) <= this.mScaleTouchSlop && Math.abs(((int) motionEvent.getY()) - this.mDownY) <= this.mScaleTouchSlop) {
            performClick();
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.miui.todo.view.BaseTodoRecyclerView, androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionButton() == 2) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
