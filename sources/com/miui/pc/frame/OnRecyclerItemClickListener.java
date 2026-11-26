package com.miui.pc.frame;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);

    public abstract void onItemLongClick(RecyclerView.ViewHolder viewHolder);

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        this.mGestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        this.mGestureDetector.onTouchEvent(motionEvent);
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        private ItemTouchHelperGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            View viewFindChildViewUnder = OnRecyclerItemClickListener.this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (viewFindChildViewUnder == null) {
                return true;
            }
            OnRecyclerItemClickListener.this.onItemClick(OnRecyclerItemClickListener.this.recyclerView.getChildViewHolder(viewFindChildViewUnder));
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            View viewFindChildViewUnder = OnRecyclerItemClickListener.this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (viewFindChildViewUnder != null) {
                OnRecyclerItemClickListener.this.onItemLongClick(OnRecyclerItemClickListener.this.recyclerView.getChildViewHolder(viewFindChildViewUnder));
            }
        }
    }
}
