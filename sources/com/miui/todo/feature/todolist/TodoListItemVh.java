package com.miui.todo.feature.todolist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.base.TodoLayoutManager;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubMode;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.utils.CharUtils;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;
import miuix.animation.utils.EaseManager;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ViewUtils;
import miuix.recyclerview.widget.RecyclerView;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TodoListItemVh extends TodoChildItemVh {
    private static final int INVALID_ID = -1;
    private static int fontLevel;
    private SubTodoListAdapter mAdapter;
    private BaseListAdapterBindContext mBindContext;
    private LinearLayout mExpandGroup;
    private View mExpandView;
    private View.OnClickListener mGroupOnClickListener;
    private boolean mIsAnim;
    private boolean mIsEditMode;
    private View mItemView;
    private ImageView mIvExpand;
    private View.OnClickListener mListener;
    private View.OnLongClickListener mLongClickListener;
    private RecyclerView mRvSubTodoList;
    private SwipeMenuLayout mSlItemGroup;
    private SubMode mSubModeImp;
    private int mSubTodoItemFixedHeight;
    private SubTodoListListener mSubTodoListListener;
    private boolean mTempCollapse;
    private TextView mTvProgress;

    @Override // com.miui.todo.feature.todolist.TodoChildItemVh, com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoItemVh
    public void onAttached(BaseListAdapterBindContext baseListAdapterBindContext) {
    }

    private static boolean isLargeFont() {
        return fontLevel == 2;
    }

    public static TodoListItemVh newInstance(ViewGroup viewGroup) {
        fontLevel = MiuixUIUtils.getFontLevel(NoteApp.getInstance());
        return new TodoListItemVh(UIUtils.inflateView(viewGroup, isLargeFont() ? R.layout.todo_task_list_item_large_font : R.layout.todo_task_list_item));
    }

    private void setExpandClickable(boolean z) {
        if (isLargeFont()) {
            this.mExpandView.setClickable(z);
        } else {
            this.mExpandGroup.setClickable(z);
        }
    }

    public TodoListItemVh(View view) {
        super(view);
        this.mSubTodoListListener = null;
        this.mSubTodoItemFixedHeight = 0;
        this.mTempCollapse = false;
        this.mListener = new View.OnClickListener() { // from class: com.miui.todo.feature.todolist.TodoListItemVh.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                TodoListItemVh.this.mSlItemGroup.performClick();
            }
        };
        this.mLongClickListener = new View.OnLongClickListener() { // from class: com.miui.todo.feature.todolist.TodoListItemVh.3
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                TodoListItemVh.this.mSlItemGroup.performLongClick();
                return true;
            }
        };
        this.mGroupOnClickListener = new View.OnClickListener() { // from class: com.miui.todo.feature.todolist.TodoListItemVh.8
            private long lastClickTime = System.currentTimeMillis();

            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (System.currentTimeMillis() - this.lastClickTime > 300) {
                    this.lastClickTime = System.currentTimeMillis();
                    if (TodoListItemVh.this.mIsEditMode) {
                        return;
                    }
                    if (TodoListItemVh.this.mSubModeImp.isExpand()) {
                        TodoListItemVh.this.collapsePanel(null);
                    } else {
                        TodoListItemVh.this.expandPanel();
                    }
                    if (TodoListItemVh.this.mSubTodoListListener != null) {
                        TodoListItemVh.this.mSubTodoListListener.onDataChanged(TodoListItemVh.this.mTodoEntity, TodoListItemVh.this.getLayoutPosition(), TodoListItemVh.this.mItemView);
                        TodoListItemVh todoListItemVh = TodoListItemVh.this;
                        todoListItemVh.mTodoEntity = todoListItemVh.mTodoEntity.copy();
                    }
                }
            }
        };
        this.mItemView = view;
        this.mSlItemGroup = (SwipeMenuLayout) view.findViewById(R.id.sl_item_group);
        this.mExpandGroup = (LinearLayout) view.findViewById(R.id.ll_expand_group);
        this.mExpandView = view.findViewById(R.id.iv_expand);
        if (isLargeFont()) {
            this.mExpandView.setOnClickListener(this.mGroupOnClickListener);
        } else {
            this.mExpandGroup.setOnClickListener(this.mGroupOnClickListener);
        }
        setExpandClickable(true);
        this.mIvExpand = (ImageView) view.findViewById(R.id.iv_expand);
        TextView textView = (TextView) view.findViewById(R.id.tv_progress);
        this.mTvProgress = textView;
        DisplayUtils.setMiuiMediumTypeFace(textView);
        this.mSubTodoItemFixedHeight = view.getResources().getDimensionPixelSize(R.dimen.sub_todo_item_height);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_subtodo);
        this.mRvSubTodoList = recyclerView;
        recyclerView.setSpringEnabled(false);
        this.mRvSubTodoList.setLayoutManager(new TodoLayoutManager(view.getContext()));
        this.mRvSubTodoList.setItemAnimator(new DefaultItemAnimator());
        SubTodoListAdapter subTodoListAdapter = new SubTodoListAdapter(view.getContext(), this.mListener, this.mLongClickListener);
        this.mAdapter = subTodoListAdapter;
        subTodoListAdapter.setHasStableIds(true);
        this.mRvSubTodoList.setAdapter(this.mAdapter);
        AccessibilityDelegateUtil.setCheckBoxAccessibilityDelegate(this.mCheckboxMask);
        this.mCheckboxMask.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.feature.todolist.TodoListItemVh$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$0(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        this.pLastCheckBoxClickTime = Long.valueOf(System.currentTimeMillis());
        if (this.mBindContext == null) {
            return;
        }
        if (this.mCheckBox.isChecked()) {
            onCheckBoxUnChecked();
        } else {
            onCheckBoxChecked(true);
        }
    }

    public void bind(TodoEntity todoEntity, BaseListAdapterBindContext baseListAdapterBindContext, int i, SubTodoListListener subTodoListListener, boolean z) {
        this.mBindContext = baseListAdapterBindContext;
        if (this.mIsAnim) {
            return;
        }
        this.mTodoEntity = todoEntity.copy();
        this.mAdapter.setBindContext(new SubTodoListBindContext(this.mRvSubTodoList, this.mAdapter, this.mBindContext.getSearchToken()));
        this.mSubTodoListListener = subTodoListListener;
        SubModeImpl subModeImpl = new SubModeImpl(todoEntity.getContent());
        if (baseListAdapterBindContext.getLastFinishId() != -1 && baseListAdapterBindContext.getLastFinishId() == todoEntity.getId()) {
            this.mTempCollapse = true;
        }
        boolean zIsExpand = subModeImpl.isExpand();
        this.mIsEditMode = z;
        if (z) {
            subModeImpl.setExpand(false);
            todoEntity.setContent(subModeImpl.getContent());
            setExpandClickable(false);
        } else {
            setExpandClickable(true);
        }
        super.bind(todoEntity, baseListAdapterBindContext, i);
        if (this.mIsEditMode && zIsExpand) {
            subModeImpl.setExpand(true);
            todoEntity.setContent(subModeImpl.getContent());
        }
        this.mAdapter.setSubHolderListener(new SubHolderListener() { // from class: com.miui.todo.feature.todolist.TodoListItemVh.1
            @Override // com.miui.todo.feature.todolist.SubHolderListener
            public void onDataChanged(SubMode subMode) {
                TodoListItemVh.this.mTvProgress.setText(subMode.getCompleteSchedule());
                TodoListItemVh.this.mTodoEntity.setContent(subMode.getContent());
                int finishOperation = subMode.getFinishOperation();
                if (finishOperation != 2) {
                    TodoListItemVh.this.mTodoEntity.setIsFinish(finishOperation);
                }
                if (finishOperation == 1) {
                    TodoListItemVh.this.mSubModeImp = subMode;
                    TodoListItemVh.this.collapsePanel(new Runnable() { // from class: com.miui.todo.feature.todolist.TodoListItemVh.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TodoListItemVh.this.notifyDataFinishStateChanged(true);
                        }
                    });
                } else {
                    subMode.setExpand(TodoListItemVh.this.mSubModeImp.isExpand());
                    TodoListItemVh.this.mSubModeImp = subMode;
                    TodoListItemVh.this.notifyDataChanged();
                }
            }
        });
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void setContent(TodoEntity todoEntity) {
        CharSequence charSequenceChangeHighLight;
        if (todoEntity.getListType() == 0) {
            super.setContent(todoEntity);
            return;
        }
        if (todoEntity.getListType() == 1) {
            SubModeImpl subModeImpl = new SubModeImpl(todoEntity.getContent());
            this.mSubModeImp = subModeImpl;
            if (this.mTempCollapse) {
                subModeImpl.setExpand(false);
                this.mTempCollapse = false;
            }
            boolean zIsEmpty = TextUtils.isEmpty(this.mSubModeImp.getTitle());
            if (!this.mIsFinished) {
                this.mContent.setTextColor(NoteApp.getInstance().getColor(R.color.todo_item_content_text_common_color));
            }
            super.setContent(todoEntity);
            if (zIsEmpty) {
                charSequenceChangeHighLight = NoteApp.getInstance().getString(R.string.todo_list);
            } else {
                charSequenceChangeHighLight = ContentUtils.changeHighLight(this.mSubModeImp.getTitle(), this.mBindContext.getSearchToken());
            }
            int iIndexOf = charSequenceChangeHighLight.toString().indexOf(StringUtils.LF);
            if (charSequenceChangeHighLight.length() > 1 && iIndexOf >= 0 && charSequenceChangeHighLight.subSequence(0, iIndexOf).toString().trim().isEmpty()) {
                charSequenceChangeHighLight = CharUtils.removeLeadingEmptyLines(charSequenceChangeHighLight);
            }
            this.mContent.setText(charSequenceChangeHighLight);
            if (this.mIsFinished) {
                this.mSubModeImp.changeFinishStatusAll(true);
            }
            this.mTvProgress.setText(this.mSubModeImp.getCompleteSchedule());
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRvSubTodoList.getLayoutParams();
            this.mIvExpand.setImageResource(R.drawable.ic_list_expand);
            if (this.mSubModeImp.isExpand()) {
                this.mIvExpand.setRotation(0.0f);
                layoutParams.height = this.mSubTodoItemFixedHeight * this.mSubModeImp.getSubTodoSize();
                this.mIvExpand.setContentDescription(this.itemView.getContext().getString(R.string.action_collapse) + "," + this.itemView.getContext().getString(R.string.button_talkback) + "," + this.itemView.getContext().getString(R.string.miuix_appcompat_accessibility_expand_state));
            } else {
                this.mIvExpand.setContentDescription(this.itemView.getContext().getString(R.string.action_expand) + "," + this.itemView.getContext().getString(R.string.button_talkback) + "," + this.itemView.getContext().getString(R.string.miuix_appcompat_accessibility_collapse_state));
                this.mIvExpand.setRotation(180.0f);
                layoutParams.height = 0;
            }
            this.mRvSubTodoList.setLayoutParams(layoutParams);
            this.mAdapter.bindDataSource(this.mSubModeImp);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoChildItemVh, com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
        super.onRecycled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void expandPanel() {
        this.mSubModeImp.setExpand(true);
        this.mAdapter.setExpandStatus(true);
        this.mTodoEntity.setContent(this.mSubModeImp.getContent());
        this.mIvExpand.setImageResource(R.drawable.ic_list_expand);
        this.mIvExpand.setContentDescription(this.itemView.getContext().getString(R.string.action_collapse) + "," + this.itemView.getContext().getString(R.string.button_talkback) + "," + this.itemView.getContext().getString(R.string.miuix_appcompat_accessibility_expand_state));
        this.mIvExpand.animate().rotation(0.0f).start();
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, this.mSubTodoItemFixedHeight * this.mSubModeImp.getSubTodoSize());
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.todo.feature.todolist.TodoListItemVh.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) TodoListItemVh.this.mRvSubTodoList.getLayoutParams();
                layoutParams.height = iIntValue;
                TodoListItemVh.this.mRvSubTodoList.setLayoutParams(layoutParams);
            }
        });
        valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.miui.todo.feature.todolist.TodoListItemVh.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                TodoListItemVh.this.mIsAnim = false;
                int[] iArr = new int[2];
                TodoListItemVh.this.mItemView.getLocationInWindow(iArr);
                TodoListItemVh.this.mSubTodoListListener.scroll(0, iArr[1], TodoListItemVh.this.mItemView.getHeight());
            }
        });
        valueAnimatorOfInt.setInterpolator(new EaseManager.SpringInterpolator().setDamping(0.9f).setResponse(0.85f));
        valueAnimatorOfInt.setDuration(300L);
        valueAnimatorOfInt.start();
        this.mIsAnim = true;
    }

    private void collapsePanelInActionMode() {
        if (this.mSubModeImp.isExpand()) {
            this.mIvExpand.setContentDescription(this.itemView.getContext().getString(R.string.action_expand));
            this.mIvExpand.animate().rotation(180.0f).start();
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mRvSubTodoList.getHeight(), 0);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.todo.feature.todolist.TodoListItemVh.6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int iMax = Math.max(0, ((Integer) valueAnimator.getAnimatedValue()).intValue());
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) TodoListItemVh.this.mRvSubTodoList.getLayoutParams();
                    layoutParams.height = iMax;
                    TodoListItemVh.this.mRvSubTodoList.setLayoutParams(layoutParams);
                }
            });
            valueAnimatorOfInt.setInterpolator(new EaseManager.SpringInterpolator().setDamping(0.9f).setResponse(0.85f));
            valueAnimatorOfInt.setDuration(150L);
            valueAnimatorOfInt.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collapsePanel(final Runnable runnable) {
        if (!this.mSubModeImp.isExpand()) {
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        this.mSubModeImp.setExpand(false);
        this.mAdapter.setExpandStatus(false);
        this.mTodoEntity.setContent(this.mSubModeImp.getContent());
        this.mSlItemGroup.setSwipeEnable(true);
        this.mIvExpand.setImageResource(R.drawable.ic_list_expand);
        this.mIvExpand.setContentDescription(this.itemView.getContext().getString(R.string.action_expand) + "," + this.itemView.getContext().getString(R.string.button_talkback) + "," + this.itemView.getContext().getString(R.string.miuix_appcompat_accessibility_collapse_state));
        this.mIvExpand.animate().rotation(180.0f).start();
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mRvSubTodoList.getHeight(), 0);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.todo.feature.todolist.TodoListItemVh$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$collapsePanel$1(valueAnimator);
            }
        });
        valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.miui.todo.feature.todolist.TodoListItemVh.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Runnable runnable2;
                TodoListItemVh.this.mIsAnim = false;
                if (TodoListItemVh.this.getLayoutPosition() == -1 || (runnable2 = runnable) == null) {
                    return;
                }
                runnable2.run();
            }
        });
        valueAnimatorOfInt.setInterpolator(new EaseManager.SpringInterpolator().setDamping(0.9f).setResponse(0.85f));
        valueAnimatorOfInt.setDuration(300L);
        valueAnimatorOfInt.start();
        this.mIsAnim = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collapsePanel$1(ValueAnimator valueAnimator) {
        int iMax = Math.max(0, ((Integer) valueAnimator.getAnimatedValue()).intValue());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRvSubTodoList.getLayoutParams();
        layoutParams.height = iMax;
        this.mRvSubTodoList.setLayoutParams(layoutParams);
    }

    @Override // com.miui.todo.feature.todolist.TodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStart(boolean z) {
        if (z) {
            collapsePanelInActionMode();
            setExpandClickable(false);
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
            return;
        }
        setExpandClickable(true);
    }

    @Override // com.miui.todo.feature.todolist.TodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationUpdate(boolean z, float f) {
        if (!z) {
            f = 1.0f - f;
        }
        ViewUtils.isLayoutRtl(this.itemView);
        this.mMultiCheckBox.setAlpha(f);
        float f2 = (0.2f * f) + 0.8f;
        this.mMultiCheckBox.setScaleX(f2);
        this.mMultiCheckBox.setScaleY(f2);
        if (!this.mIsFinished) {
            this.mIvDrag.setAlpha(f);
            this.mIvDrag.setScaleX(f2);
            this.mIvDrag.setScaleY(f2);
        }
        float f3 = 1.0f - f;
        this.mCheckBox.setAlpha(f3);
        this.mIvExpand.setAlpha(f3);
    }

    @Override // com.miui.todo.feature.todolist.TodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStop(boolean z) {
        if (!z) {
            this.itemView.setHapticFeedbackEnabled(false);
            this.mMultiCheckBox.setVisibility(8);
            this.mIvDrag.setVisibility(8);
            this.mCheckBox.setVisibility(0);
            this.mCheckboxMask.setVisibility(0);
            this.mCheckBox.setAlpha(1.0f);
            this.mCheckBox.setScaleX(1.0f);
            this.mCheckBox.setScaleY(1.0f);
            this.mIvExpand.setAlpha(1.0f);
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
        this.mIvExpand.setAlpha(0.0f);
        this.mMultiCheckBox.setAlpha(1.0f);
        this.mMultiCheckBox.setScaleX(1.0f);
        this.mMultiCheckBox.setScaleY(1.0f);
    }

    @Override // com.miui.todo.feature.todolist.TodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onClearAnimation() {
        this.mIvDrag.setAlpha(1.0f);
        this.mCheckBox.setAlpha(1.0f);
        this.mMultiCheckBox.setAlpha(0.0f);
        this.mMultiCheckBox.setScaleX(0.8f);
        this.mMultiCheckBox.setScaleY(0.8f);
        this.mIvExpand.setAlpha(1.0f);
    }

    @Override // com.miui.todo.feature.todolist.TodoChildItemVh, com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        super.onUpdateEditable(z, z2);
        boolean z3 = this.mMultiCheckBox.getVisibility() == 0;
        boolean zIsChecked = this.mMultiCheckBox.isChecked();
        this.mMultiCheckBox.setChecked(z2);
        if (z3) {
            this.mIvExpand.setAlpha(0.0f);
            this.mCheckBox.setVisibility(4);
            this.mCheckboxMask.setVisibility(4);
            if (!this.mIsFinished) {
                this.mIvDrag.setVisibility(0);
                this.mIvDrag.setAlpha(1.0f);
                this.mIvDrag.setScaleX(1.0f);
                this.mIvDrag.setScaleY(1.0f);
            }
        } else {
            this.mIvExpand.setAlpha(1.0f);
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
            this.mIvExpand.setAlpha(0.0f);
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
            this.mIvExpand.setAlpha(1.0f);
        }
        return true;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void onUnChecked() {
        this.mContent.getPaint().setFlags(1);
        this.mContent.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_common_color));
        setTimeTextColor(this.mTime, this.mIvClock, this.mScheduleTime, this.mRepeatType, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDataChanged() {
        SubTodoListListener subTodoListListener = this.mSubTodoListListener;
        if (subTodoListListener != null) {
            subTodoListListener.onDataChanged(this.mTodoEntity, getLayoutPosition(), this.mItemView);
            this.mTodoEntity = this.mTodoEntity.copy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDataFinishStateChanged(boolean z) {
        SubTodoListListener subTodoListListener = this.mSubTodoListListener;
        if (subTodoListListener != null) {
            subTodoListListener.onDataFinishStateChanged(this.mTodoEntity, getLayoutPosition(), this.mItemView, z);
            this.mTodoEntity = this.mTodoEntity.copy();
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void setListItemFinished() {
        this.mAdapter.setSubHolderListener(null);
        this.mTodoEntity.setIsFinish(1);
        this.mSubModeImp.changeFinishStatusAll(true);
        this.mTodoEntity.setContent(this.mSubModeImp.getContent());
        collapsePanel(new Runnable() { // from class: com.miui.todo.feature.todolist.TodoListItemVh$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setListItemFinished$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListItemFinished$2() {
        this.mTvProgress.setText(this.mSubModeImp.getCompleteSchedule());
        notifyDataFinishStateChanged(true);
    }
}
