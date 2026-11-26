package com.miui.notes.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes4.dex */
public class HomepageSpringBackLayout extends SpringBackLayout {
    private boolean mIsInTodo;
    private RecyclerView mTodoRv;

    public HomepageSpringBackLayout(Context context) {
        this(context, null);
    }

    public HomepageSpringBackLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setInTodo(boolean z) {
        this.mIsInTodo = z;
    }

    public void setTodoRecyclerView(RecyclerView recyclerView) {
        this.mTodoRv = recyclerView;
    }

    @Override // miuix.springback.view.SpringBackLayout, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        RecyclerView recyclerView;
        int action = motionEvent.getAction();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        boolean zOnInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        if (action == 2 && (recyclerView = this.mTodoRv) != null && this.mIsInTodo && recyclerView.getChildAdapterPosition(recyclerView.findChildViewUnder(x, y)) >= 0) {
            return false;
        }
        return zOnInterceptTouchEvent;
    }
}
