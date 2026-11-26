package com.miui.pad.feature.todo.list;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.base.todolist.BaseTodoChildItemVh;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.swipemenulayout.SimpleSwipeListener;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;
import miuix.internal.util.ViewUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PadTodoChildItemVh extends BaseTodoChildItemVh {
    private static final String TAG = "PadTodoChildItemVh";
    protected ImageView mIvDrag;
    protected CheckBox mMultiCheckBox;
    protected SwipeMenuLayout mSlItemGroup;
    private boolean mStarted;

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected int getSelectedFg() {
        return R.drawable.pad_todo_item_press_fg;
    }

    protected void updateElementMargin(BaseListAdapterBindContext baseListAdapterBindContext) {
    }

    public static PadTodoChildItemVh newInstance(ViewGroup viewGroup) {
        return new PadTodoChildItemVh(UIUtils.inflateView(viewGroup, R.layout.pad_todo_task_normal_item));
    }

    public PadTodoChildItemVh(View view) {
        super(view);
        view.setLongClickable(true);
        SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.sl_item_group);
        this.mSlItemGroup = swipeMenuLayout;
        swipeMenuLayout.setImportantForAccessibility(2);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_drag);
        this.mIvDrag = imageView;
        imageView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.pad.feature.todo.list.PadTodoChildItemVh$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.lambda$new$0(view2, motionEvent);
            }
        });
        this.mRootView.setSwipeListener(new SimpleSwipeListener() { // from class: com.miui.pad.feature.todo.list.PadTodoChildItemVh.1
            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onStart(boolean z) {
                if (PadTodoChildItemVh.this.mSwipeListener != null) {
                    PadTodoChildItemVh.this.mSwipeListener.onSwipeStart(PadTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onUpdate(SwipeMenuLayout swipeMenuLayout2, float f, boolean z) {
                TodoUtils.setSwiping(f != 0.0f, "onUpdate with PadTodoChildItemVh");
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onEnd(boolean z) {
                if (z) {
                    PadTodoChildItemVh.this.mCheckBox.setChecked(!PadTodoChildItemVh.this.mCheckBox.isChecked());
                }
                if (PadTodoChildItemVh.this.mSwipeListener != null) {
                    PadTodoChildItemVh.this.mSwipeListener.onSwipeFinish(PadTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onMenuOpen(boolean z) {
                PadTodoChildItemVh.this.updateViewStateByMenuState();
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onMenuClose(boolean z) {
                TodoUtils.setSwiping(false, "PadTodoChildVh-onMenuClose");
                PadTodoChildItemVh.this.updateViewStateByMenuState();
                if (PadTodoChildItemVh.this.mSwipeListener != null) {
                    PadTodoChildItemVh.this.mSwipeListener.onSwipeStop(PadTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onReset(boolean z) {
                if (PadTodoChildItemVh.this.mSwipeListener != null) {
                    PadTodoChildItemVh.this.mSwipeListener.onSwipeFinish(PadTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }
        });
        this.mCheckBox.setOnCheckedChangeListener(this.mCheckBoxListener);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.multi_checkbox);
        this.mMultiCheckBox = checkBox;
        checkBox.setImportantForAccessibility(2);
        this.mMultiCheckBox.setClickable(false);
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
        return ContentUtils.changeHighLight(str, str2);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    public void bind(TodoEntity todoEntity, BaseListAdapterBindContext baseListAdapterBindContext, int i) {
        this.mCheckBox.setOnCheckedChangeListener(this.mCheckBoxListener);
        super.bind(todoEntity, baseListAdapterBindContext, i);
        this.itemView.setLongClickable(true);
        if (this.mIsFinished) {
            this.mIvDrag.setVisibility(8);
        }
        if (baseListAdapterBindContext.getMarkIndex() == i) {
            this.mRootView.findViewById(R.id.v_bg).setBackground(this.itemView.getContext().getDrawable(R.drawable.pad_todo_child_item_mark_bg).mutate());
            baseListAdapterBindContext.setMarkIndex(-1);
        } else {
            this.mRootView.findViewById(R.id.v_bg).setBackground(this.itemView.getContext().getDrawable(R.drawable.pad_todo_child_item_bg).mutate());
        }
        if (todoEntity.getRemindType() == 0 && todoEntity.getInputType() != 1) {
            this.mLabelGroup.setVisibility(8);
        } else {
            this.mLabelGroup.setVisibility(0);
        }
        updateElementMargin(baseListAdapterBindContext);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
        super.onRecycled();
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
        return TodoUtils.getColor(R.color.pad_todo_item_time_text_common_color);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void updateViewStateByMenuState() {
        super.updateViewStateByMenuState();
        if (this.mRootView != null) {
            if (this.mRootView.isMenuOpen()) {
                this.mRootView.findViewById(R.id.v_bg).setBackground(TodoUtils.getDrawable(R.drawable.pad_todo_child_item_bg_nopress).mutate());
            } else {
                this.mRootView.findViewById(R.id.v_bg).setBackground(TodoUtils.getDrawable(R.drawable.pad_todo_child_item_bg).mutate());
            }
        }
    }
}
