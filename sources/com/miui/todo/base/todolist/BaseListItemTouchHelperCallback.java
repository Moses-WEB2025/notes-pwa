package com.miui.todo.base.todolist;

import android.animation.Animator;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.common.view.PhysicBasedInterpolator;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.base.OnDragItemListener;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class BaseListItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private OnDragItemListener itemDragListener;
    private final double ICON_MAX_SIZE = 40.0d;
    private boolean mLastDragResult = false;
    private boolean mEnableDrag = false;
    private PhysicBasedInterpolator mPhysicBasedInterpolator = new PhysicBasedInterpolator.Builder().setDamping(0.9f).setResponse(0.85714287f).build();

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
    }

    public BaseListItemTouchHelperCallback(OnDragItemListener onDragItemListener) {
        this.itemDragListener = onDragItemListener;
    }

    public void setEnableDrag(boolean z) {
        this.mEnableDrag = z;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getItemViewType() == 0 || viewHolder.getItemViewType() == 3) {
            return makeMovementFlags(3, 0);
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isLongPressDragEnabled() {
        return this.mEnableDrag;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        boolean zOnDragMove = this.itemDragListener.onDragMove(viewHolder, viewHolder2);
        this.mLastDragResult = zOnDragMove;
        if (zOnDragMove) {
            HapticFeedbackTool.getInstance().performSwapItem(recyclerView);
        }
        return this.mLastDragResult;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(final RecyclerView.ViewHolder viewHolder, int i) {
        if (i != 0) {
            Logger.INSTANCE.i("BaseListItemTouchHelperCallback", " viewHolder.itemView.isLongClickable() = " + viewHolder.itemView.isLongClickable());
            if (viewHolder.itemView.isLongClickable() && this.itemDragListener.onDragStart(viewHolder, i)) {
                viewHolder.itemView.findViewById(R.id.item_content).animate().setDuration(300L).translationZ(5.0f).setInterpolator(this.mPhysicBasedInterpolator).setListener(new Animator.AnimatorListener() { // from class: com.miui.todo.base.todolist.BaseListItemTouchHelperCallback.1
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        View viewFindViewById = viewHolder.itemView.findViewById(R.id.sl_item_group);
                        Folme.useAt(viewFindViewById).touch().cancel();
                        Folme.useAt(viewFindViewById).touch().setScale(1.04f, ITouchStyle.TouchType.UP).touchUp(new AnimConfig[0]);
                    }
                }).start();
            }
        } else {
            this.itemDragListener.onDragEnd(viewHolder, i, true);
        }
        super.onSelectedChanged(viewHolder, i);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void clearView(RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.findViewById(R.id.item_content).animate().setInterpolator(this.mPhysicBasedInterpolator).setDuration(200L).translationZ(0.0f).setListener(new Animator.AnimatorListener() { // from class: com.miui.todo.base.todolist.BaseListItemTouchHelperCallback.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                Folme.useAt(viewHolder.itemView.findViewById(R.id.sl_item_group)).touch().setScale(1.0f, ITouchStyle.TouchType.UP).touchUp(new AnimConfig[0]);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                viewHolder.itemView.setAlpha(1.0f);
                viewHolder.itemView.setScaleX(1.0f);
                viewHolder.itemView.setScaleY(1.0f);
                viewHolder.itemView.setScrollX(0);
            }
        });
        super.clearView(recyclerView, viewHolder);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
        if (i != 1) {
            super.onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, z);
            return;
        }
        if (Math.abs(f) <= getSlideLimitation(viewHolder)) {
            viewHolder.itemView.scrollTo(-((int) f), 0);
        } else if (Math.abs(f) <= recyclerView.getWidth() / 2.0f) {
            recyclerView.getWidth();
            getSlideLimitation(viewHolder);
            Math.abs(f);
            getSlideLimitation(viewHolder);
        }
    }

    public int getSlideLimitation(RecyclerView.ViewHolder viewHolder) {
        return ((ViewGroup) viewHolder.itemView).getChildAt(2).getLayoutParams().width;
    }
}
