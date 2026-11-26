package com.miui.pc.feature.todo;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.base.todolist.BaseTodoChildItemVh;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.utils.CalenderUtils;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.swipemenulayout.SimpleSwipeListener;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;
import miuix.internal.util.ViewUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoChildItemVh extends BaseTodoChildItemVh {
    protected ImageView mIvDrag;
    protected CheckBox mMultiCheckBox;
    protected SwipeMenuLayout mSlItemGroup;
    private boolean mStarted;

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected int getSelectedFg() {
        return R.drawable.pc_todo_item_press_fg;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoItemVh
    public void onAttached(BaseListAdapterBindContext baseListAdapterBindContext) {
    }

    public static PcTodoChildItemVh newInstance(ViewGroup viewGroup) {
        return new PcTodoChildItemVh(UIUtils.inflateView(viewGroup, R.layout.pc_todo_task_normal_item));
    }

    public PcTodoChildItemVh(View view) {
        super(view);
        view.setLongClickable(true);
        SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.sl_item_group);
        this.mSlItemGroup = swipeMenuLayout;
        swipeMenuLayout.setSwipeEnable(false);
        this.mSlItemGroup.setScaleEnable(false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_drag);
        this.mIvDrag = imageView;
        imageView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.pc.feature.todo.PcTodoChildItemVh$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.lambda$new$0(view2, motionEvent);
            }
        });
        this.mRootView.setSwipeListener(new SimpleSwipeListener() { // from class: com.miui.pc.feature.todo.PcTodoChildItemVh.1
            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onStart(boolean z) {
                if (PcTodoChildItemVh.this.mSwipeListener != null) {
                    PcTodoChildItemVh.this.mSwipeListener.onSwipeStart(PcTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onUpdate(SwipeMenuLayout swipeMenuLayout2, float f, boolean z) {
                TodoUtils.setSwiping(f != 0.0f, "onUpdate-PcTodo");
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onEnd(boolean z) {
                if (z) {
                    PcTodoChildItemVh.this.mCheckBox.setChecked(!PcTodoChildItemVh.this.mCheckBox.isChecked());
                }
                if (PcTodoChildItemVh.this.mSwipeListener != null) {
                    PcTodoChildItemVh.this.mSwipeListener.onSwipeFinish(PcTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onMenuOpen(boolean z) {
                PcTodoChildItemVh.this.updateViewStateByMenuState();
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onMenuClose(boolean z) {
                TodoUtils.setSwiping(false, "PcTodoChildVh-onMenuClose");
                PcTodoChildItemVh.this.updateViewStateByMenuState();
                if (PcTodoChildItemVh.this.mSwipeListener != null) {
                    PcTodoChildItemVh.this.mSwipeListener.onSwipeStop(PcTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onReset(boolean z) {
                if (PcTodoChildItemVh.this.mSwipeListener != null) {
                    PcTodoChildItemVh.this.mSwipeListener.onSwipeFinish(PcTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }
        });
        this.mCheckBox.setOnCheckedChangeListener(this.mCheckBoxListener);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.multi_checkbox);
        this.mMultiCheckBox = checkBox;
        checkBox.setClickable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.mDragListener != null) {
                this.mDragListener.onCancelDrag(this);
            }
        } else if (this.mDragListener != null) {
            this.mDragListener.onStartDrag(this);
        }
        return true;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected CharSequence getHighLightText(String str, String str2) {
        return ContentUtils.changePcHighLight(str, str2);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    public void bind(TodoEntity todoEntity, BaseListAdapterBindContext baseListAdapterBindContext, int i) {
        super.bind(todoEntity, baseListAdapterBindContext, i);
        this.itemView.setLongClickable(true);
        if (this.mIsFinished) {
            this.mIvDrag.setVisibility(8);
        }
        if (baseListAdapterBindContext.getMarkIndex() == i) {
            this.mRootView.findViewById(R.id.v_bg).setBackground(this.itemView.getContext().getDrawable(R.drawable.pc_todo_child_item_mark_bg).mutate());
            baseListAdapterBindContext.setMarkIndex(-1);
        } else {
            this.mRootView.findViewById(R.id.v_bg).setBackground(this.itemView.getContext().getDrawable(R.drawable.pc_todo_child_item_bg).mutate());
        }
        if (todoEntity.getRemindType() == 0 && todoEntity.getInputType() != 1) {
            this.mLabelGroup.setVisibility(8);
        } else {
            this.mLabelGroup.setVisibility(0);
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
        if (this.mStarted) {
            onAnimationStop(false);
        }
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    /* JADX INFO: renamed from: hasAnimationStarted */
    public boolean getMStarted() {
        return this.mStarted;
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStart(boolean z) {
        if (z) {
            this.mCheckBox.setVisibility(4);
            this.mCheckboxMask.setVisibility(4);
            if (!this.mIsFinished) {
                this.mIvDrag.setAlpha(0.0f);
                this.mIvDrag.setScaleX(0.8f);
                this.mIvDrag.setScaleY(0.8f);
                this.mIvDrag.setVisibility(0);
            }
            this.mRootView.setSwipeEnable(false);
            this.mRightMenu.setVisibility(8);
            this.mMultiCheckBox.setVisibility(0);
            this.mMultiCheckBox.setAlpha(0.0f);
            this.mMultiCheckBox.setScaleX(0.8f);
            this.mMultiCheckBox.setScaleY(0.8f);
        }
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationUpdate(boolean z, float f) {
        if (!z) {
            f = 1.0f - f;
        }
        ViewUtils.isLayoutRtl(this.itemView);
        this.mMultiCheckBox.setAlpha(f);
        float f2 = 0.2f * f;
        float f3 = 0.8f + f2;
        this.mMultiCheckBox.setScaleX(f3);
        this.mMultiCheckBox.setScaleY(f3);
        if (!this.mIsFinished) {
            this.mIvDrag.setAlpha(f);
            this.mIvDrag.setScaleX(f3);
            this.mIvDrag.setScaleY(f3);
        }
        this.mCheckBox.setAlpha(1.0f - f);
        float f4 = 1.0f - f2;
        this.mCheckBox.setScaleX(f4);
        this.mCheckBox.setScaleY(f4);
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStop(boolean z) {
        if (!z) {
            this.itemView.setHapticFeedbackEnabled(false);
            this.mMultiCheckBox.setVisibility(8);
            this.mIvDrag.setVisibility(8);
            this.mCheckBox.setVisibility(0);
            this.mCheckBox.setAlpha(1.0f);
            this.mCheckBox.setScaleX(1.0f);
            this.mCheckBox.setScaleY(1.0f);
            this.mCheckboxMask.setVisibility(0);
            return;
        }
        this.itemView.setHapticFeedbackEnabled(true);
        this.mCheckBox.setVisibility(4);
        this.mCheckboxMask.setVisibility(4);
        if (!this.mIsFinished) {
            this.mIvDrag.setAlpha(1.0f);
            this.mIvDrag.setScaleX(1.0f);
            this.mIvDrag.setScaleY(1.0f);
        }
        this.mMultiCheckBox.setAlpha(1.0f);
        this.mMultiCheckBox.setScaleX(1.0f);
        this.mMultiCheckBox.setScaleY(1.0f);
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onClearAnimation() {
        this.mIvDrag.setAlpha(0.0f);
        this.mIvDrag.setScaleX(1.0f);
        this.mIvDrag.setScaleY(1.0f);
        this.mCheckBox.setAlpha(1.0f);
        this.mCheckBox.setScaleX(1.0f);
        this.mCheckBox.setScaleY(1.0f);
        this.mMultiCheckBox.setAlpha(0.0f);
        this.mMultiCheckBox.setScaleX(0.8f);
        this.mMultiCheckBox.setScaleY(0.8f);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        super.onUpdateEditable(z, z2);
        boolean z3 = this.mMultiCheckBox.getVisibility() == 0;
        boolean zIsChecked = this.mMultiCheckBox.isChecked();
        this.mMultiCheckBox.setChecked(z2);
        if (z3) {
            this.mCheckBox.setVisibility(4);
            this.mCheckboxMask.setVisibility(4);
            if (!this.mIsFinished) {
                this.mIvDrag.setVisibility(0);
                this.mIvDrag.setAlpha(1.0f);
                this.mIvDrag.setScaleX(1.0f);
                this.mIvDrag.setScaleY(1.0f);
            }
        }
        if (z == z3 && (!z || z2 == zIsChecked)) {
            return false;
        }
        if (z) {
            this.mCheckBox.setVisibility(4);
            this.mCheckboxMask.setVisibility(4);
            if (!this.mIsFinished) {
                this.mIvDrag.setVisibility(0);
                this.mIvDrag.setAlpha(1.0f);
                this.mIvDrag.setScaleX(1.0f);
                this.mIvDrag.setScaleY(1.0f);
            }
            this.mMultiCheckBox.setVisibility(0);
            this.mMultiCheckBox.setAlpha(1.0f);
            this.mMultiCheckBox.setScaleX(1.0f);
            this.mMultiCheckBox.setScaleY(1.0f);
            this.mRootView.setSwipeEnable(false);
            this.mRightMenu.setVisibility(8);
        } else {
            this.mMultiCheckBox.setVisibility(8);
            this.mIvDrag.setVisibility(8);
            this.mCheckBox.setVisibility(0);
            this.mCheckBox.setAlpha(1.0f);
            this.mCheckBox.setScaleX(1.0f);
            this.mCheckBox.setScaleY(1.0f);
            this.mCheckboxMask.setVisibility(0);
            this.mRootView.setSwipeEnable(true);
            this.mRightMenu.setVisibility(0);
        }
        return true;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected int getNormalTimeTextColor() {
        return TodoUtils.getColor(R.color.pc_todo_item_time_text_common_color);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void setTimeTextColor(TextView textView, ImageView imageView, long j, int i, boolean z) {
        Drawable drawableMutate;
        if (z) {
            textView.setTextColor(TodoUtils.getColor(R.color.pc_todo_item_content_text_disable_color));
            Drawable drawableMutate2 = TodoUtils.getDrawable(R.drawable.pc_ic_todo_item_clock_d).mutate();
            drawableMutate2.setBounds(0, 0, drawableMutate2.getMinimumWidth(), drawableMutate2.getMinimumHeight());
            imageView.setImageDrawable(drawableMutate2);
            return;
        }
        if (CalenderUtils.isTimeExpired(j)) {
            textView.setTextColor(getNormalTimeTextColor());
            if (i > 0) {
                drawableMutate = TodoUtils.getDrawable(R.drawable.pc_ic_todo_item_repeat_w).mutate();
                drawableMutate.setBounds(0, 0, drawableMutate.getMinimumWidth(), drawableMutate.getMinimumHeight());
            } else {
                drawableMutate = TodoUtils.getDrawable(R.drawable.pc_ic_todo_item_clock_w).mutate();
                drawableMutate.setBounds(0, 0, drawableMutate.getMinimumWidth(), drawableMutate.getMinimumHeight());
            }
        } else {
            textView.setTextColor(getNormalTimeTextColor());
            if (i > 0) {
                drawableMutate = TodoUtils.getDrawable(R.drawable.pc_ic_todo_item_repeat_n).mutate();
                drawableMutate.setBounds(0, 0, drawableMutate.getMinimumWidth(), drawableMutate.getMinimumHeight());
            } else {
                drawableMutate = TodoUtils.getDrawable(R.drawable.pc_ic_todo_item_clock_n).mutate();
                drawableMutate.setBounds(0, 0, drawableMutate.getMinimumWidth(), drawableMutate.getMinimumHeight());
            }
        }
        imageView.setImageDrawable(drawableMutate);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void updateViewStateByMenuState() {
        super.updateViewStateByMenuState();
        if (this.mRootView != null) {
            if (this.mRootView.isMenuOpen()) {
                this.mRootView.findViewById(R.id.v_bg).setBackground(TodoUtils.getDrawable(R.drawable.pc_todo_child_item_bg_nopress).mutate());
            } else {
                this.mRootView.findViewById(R.id.v_bg).setBackground(TodoUtils.getDrawable(R.drawable.pc_todo_child_item_bg).mutate());
            }
        }
    }
}
