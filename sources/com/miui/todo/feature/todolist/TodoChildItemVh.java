package com.miui.todo.feature.todolist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import com.miui.common.tool.Logger;
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
public class TodoChildItemVh extends BaseTodoChildItemVh {
    private static final String TAG = "TodoChildItemVh";
    private static final int UI_MODE_NIGHT_NOT_CACHED = -1;
    private static Drawable sCachedItemBg = null;
    private static Drawable sCachedItemBgNoPress = null;
    private static Drawable sCachedItemMarkBg = null;
    private static int sCachedUiModeNight = -1;
    protected ImageView mIvDrag;
    protected CheckBox mMultiCheckBox;
    protected View mNormalItemGroup;
    protected View mSlItemGroup;
    private boolean mStarted;
    protected TodoEntity mTodoEntity;

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected int getSelectedFg() {
        return R.drawable.phone_todo_item_press_fg;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoItemVh
    public void onAttached(BaseListAdapterBindContext baseListAdapterBindContext) {
    }

    public static void clearCachedDrawables() {
        Logger.INSTANCE.d(TAG, "clearCachedDrawables: prevCachedUiModeNight=0x" + Integer.toHexString(sCachedUiModeNight));
        sCachedItemBg = null;
        sCachedItemMarkBg = null;
        sCachedItemBgNoPress = null;
        sCachedUiModeNight = -1;
    }

    private static void invalidateIfUiModeChanged(Context context) {
        int i = context.getResources().getConfiguration().uiMode & 48;
        int i2 = sCachedUiModeNight;
        if (i2 != -1 && i2 != i) {
            Logger.INSTANCE.i(TAG, "invalidateIfUiModeChanged: uiMode changed 0x" + Integer.toHexString(sCachedUiModeNight) + " -> 0x" + Integer.toHexString(i) + ", drop cached bg drawables");
            sCachedItemBg = null;
            sCachedItemMarkBg = null;
            sCachedItemBgNoPress = null;
        }
        sCachedUiModeNight = i;
    }

    private static Drawable getCachedItemBg(Context context) {
        invalidateIfUiModeChanged(context);
        if (sCachedItemBg == null) {
            sCachedItemBg = context.getDrawable(R.drawable.todo_child_item_bg);
            Logger.INSTANCE.d(TAG, "getCachedItemBg: fill cache at uiModeNight=0x" + Integer.toHexString(sCachedUiModeNight));
        }
        return sCachedItemBg.getConstantState().newDrawable().mutate();
    }

    private static Drawable getCachedItemMarkBg(Context context) {
        invalidateIfUiModeChanged(context);
        if (sCachedItemMarkBg == null) {
            sCachedItemMarkBg = context.getDrawable(R.drawable.todo_child_item_mark_bg);
            Logger.INSTANCE.d(TAG, "getCachedItemMarkBg: fill cache at uiModeNight=0x" + Integer.toHexString(sCachedUiModeNight));
        }
        return sCachedItemMarkBg.getConstantState().newDrawable().mutate();
    }

    private static Drawable getCachedItemBgNoPress(Context context) {
        invalidateIfUiModeChanged(context);
        if (sCachedItemBgNoPress == null) {
            sCachedItemBgNoPress = context.getDrawable(R.drawable.todo_child_item_bg_nopress);
            Logger.INSTANCE.d(TAG, "getCachedItemBgNoPress: fill cache at uiModeNight=0x" + Integer.toHexString(sCachedUiModeNight));
        }
        return sCachedItemBgNoPress.getConstantState().newDrawable().mutate();
    }

    public static TodoChildItemVh newInstance(ViewGroup viewGroup) {
        return new TodoChildItemVh(UIUtils.inflateView(viewGroup, R.layout.todo_task_normal_item));
    }

    public TodoChildItemVh(View view) {
        super(view);
        view.setLongClickable(true);
        View viewFindViewById = view.findViewById(R.id.sl_item_group);
        this.mSlItemGroup = viewFindViewById;
        viewFindViewById.setImportantForAccessibility(2);
        View viewFindViewById2 = view.findViewById(R.id.normal_item_group);
        this.mNormalItemGroup = viewFindViewById2;
        viewFindViewById2.setImportantForAccessibility(2);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_drag);
        this.mIvDrag = imageView;
        imageView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.todo.feature.todolist.TodoChildItemVh.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    if (TodoChildItemVh.this.mDragListener != null) {
                        TodoChildItemVh.this.mDragListener.onCancelDrag(TodoChildItemVh.this);
                    }
                } else if (TodoChildItemVh.this.mDragListener != null) {
                    TodoChildItemVh.this.mDragListener.onStartDrag(TodoChildItemVh.this);
                }
                return true;
            }
        });
        this.mRootView.setSwipeListener(new SimpleSwipeListener() { // from class: com.miui.todo.feature.todolist.TodoChildItemVh.2
            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onStart(boolean z) {
                if (TodoChildItemVh.this.mSwipeListener != null) {
                    TodoChildItemVh.this.mSwipeListener.onSwipeStart(TodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onUpdate(SwipeMenuLayout swipeMenuLayout, float f, boolean z) {
                TodoUtils.setSwiping(f != 0.0f, "onUpdate-todoChildItemVh.");
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onEnd(boolean z) {
                if (z) {
                    TodoChildItemVh.this.mCheckBox.setChecked(!TodoChildItemVh.this.mCheckBox.isChecked());
                }
                if (TodoChildItemVh.this.mSwipeListener != null) {
                    TodoChildItemVh.this.mSwipeListener.onSwipeFinish(TodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onMenuOpen(boolean z) {
                TodoUtils.setSwiping(false, "onMenuOpen");
                TodoChildItemVh.this.updateViewStateByMenuState();
                if (TodoChildItemVh.this.mSwipeListener != null) {
                    TodoChildItemVh.this.mSwipeListener.onSwipeStop(TodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onMenuClose(boolean z) {
                TodoUtils.setSwiping(false, "onMenuClose");
                TodoChildItemVh.this.updateViewStateByMenuState();
                if (TodoChildItemVh.this.mSwipeListener != null) {
                    TodoChildItemVh.this.mSwipeListener.onSwipeStop(TodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onReset(boolean z) {
                if (TodoChildItemVh.this.mSwipeListener != null) {
                    TodoChildItemVh.this.mSwipeListener.onSwipeFinish(TodoChildItemVh.this.getAdapterPosition(), z);
                }
            }
        });
        this.mCheckBox.setOnCheckedChangeListener(this.mCheckBoxListener);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.multi_checkbox);
        this.mMultiCheckBox = checkBox;
        checkBox.setImportantForAccessibility(2);
        this.mMultiCheckBox.setClickable(false);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    public void bind(TodoEntity todoEntity, BaseListAdapterBindContext baseListAdapterBindContext, int i) {
        super.bind(todoEntity, baseListAdapterBindContext, i);
        this.itemView.setLongClickable(true);
        if (this.mIsFinished) {
            this.mIvDrag.setVisibility(8);
        }
        if (baseListAdapterBindContext.getMarkIndex() == i) {
            this.mRootView.findViewById(R.id.v_bg).setBackground(getCachedItemMarkBg(this.itemView.getContext()));
            baseListAdapterBindContext.setMarkIndex(-1);
        } else {
            this.mRootView.findViewById(R.id.v_bg).setBackground(getCachedItemBg(this.itemView.getContext()));
        }
        updateContentLayout(todoEntity);
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
        } else {
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
        if (this.mContent != null) {
            this.mContent.requestLayout();
        }
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
        return TodoUtils.getColor(R.color.todo_item_time_text_common_color);
    }

    protected void updateContentLayout(TodoEntity todoEntity) {
        if (todoEntity.getRemindType() == 0 && todoEntity.getInputType() != 1) {
            this.mLabelGroup.setVisibility(8);
        } else {
            this.mLabelGroup.setVisibility(0);
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void updateViewStateByMenuState() {
        super.updateViewStateByMenuState();
        if (this.mRootView != null) {
            if (this.mRootView.isMenuOpen()) {
                this.mRootView.findViewById(R.id.v_bg).setBackground(getCachedItemBgNoPress(this.itemView.getContext()));
            } else {
                this.mRootView.findViewById(R.id.v_bg).setBackground(getCachedItemBg(this.itemView.getContext()));
            }
        }
    }
}
