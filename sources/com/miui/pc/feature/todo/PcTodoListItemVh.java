package com.miui.pc.feature.todo;

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
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.base.TodoLayoutManager;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubMode;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.feature.todolist.SubHolderListener;
import com.miui.todo.feature.todolist.SubTodoListAdapter;
import com.miui.todo.feature.todolist.SubTodoListBindContext;
import com.miui.todo.feature.todolist.SubTodoListListener;
import com.miui.todo.utils.TodoUtils;
import miuix.animation.utils.EaseManager;
import miuix.internal.util.ViewUtils;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoListItemVh extends PcTodoChildItemVh {
    private static final int INVALID_ID = -1;
    private SubTodoListAdapter mAdapter;
    private BaseListAdapterBindContext mBindContext;
    private LinearLayout mExpandGroup;
    private View.OnClickListener mGroupOnClickListener;
    private boolean mIsAnim;
    private boolean mIsEditMode;
    private View mItemView;
    private ImageView mIvExpand;
    private RecyclerView mRvSubTodoList;
    private RelativeLayout mRvSubTodoListContainer;
    private SubTodoListBindContext mSubBindContext;
    private SubMode mSubModeImp;
    private int mSubTodoItemHeight;
    private SubTodoListListener mSubTodoListListener;
    private boolean mTempCollapse;
    private TodoEntity mTodoEntity;
    private TextView mTvProgress;

    @Override // com.miui.pc.feature.todo.PcTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoItemVh
    public void onAttached(BaseListAdapterBindContext baseListAdapterBindContext) {
    }

    public static PcTodoListItemVh newInstance(ViewGroup viewGroup) {
        return new PcTodoListItemVh(UIUtils.inflateView(viewGroup, R.layout.pc_todo_task_list_item));
    }

    public PcTodoListItemVh(View view) {
        super(view);
        this.mSubTodoListListener = null;
        this.mTempCollapse = false;
        this.mSubTodoItemHeight = 0;
        this.mGroupOnClickListener = new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PcTodoListItemVh.this.mIsEditMode) {
                    return;
                }
                if (PcTodoListItemVh.this.mSubModeImp.isExpand()) {
                    PcTodoListItemVh.this.collapsePanel(null);
                } else {
                    PcTodoListItemVh.this.expandPanel();
                }
                PcTodoListItemVh.this.notifyDataChanged();
            }
        };
        this.mSubTodoItemHeight = view.getContext().getResources().getDimensionPixelSize(R.dimen.sub_todo_item_height);
        this.mItemView = view;
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_expand_group);
        this.mExpandGroup = linearLayout;
        linearLayout.setClickable(true);
        this.mExpandGroup.setOnClickListener(this.mGroupOnClickListener);
        this.mIvExpand = (ImageView) view.findViewById(R.id.iv_expand);
        TextView textView = (TextView) view.findViewById(R.id.tv_progress);
        this.mTvProgress = textView;
        DisplayUtils.setMiuiMediumTypeFace(textView);
        this.mRvSubTodoListContainer = (RelativeLayout) view.findViewById(R.id.rl_subtodo);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_subtodo);
        this.mRvSubTodoList = recyclerView;
        recyclerView.setSpringEnabled(false);
        this.mRvSubTodoList.setLayoutManager(new TodoLayoutManager(view.getContext()));
        this.mRvSubTodoList.setItemAnimator(new DefaultItemAnimator());
        this.mAdapter = new PcSubTodoListAdapter(view.getContext(), new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$0(view2);
            }
        }, new View.OnLongClickListener() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return this.f$0.lambda$new$1(view2);
            }
        });
        SubTodoListBindContext subTodoListBindContext = new SubTodoListBindContext(this.mRvSubTodoList, this.mAdapter);
        this.mSubBindContext = subTodoListBindContext;
        this.mAdapter.setBindContext(subTodoListBindContext);
        this.mAdapter.setHasStableIds(true);
        this.mRvSubTodoList.setAdapter(this.mAdapter);
        this.mCheckboxMask.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$2(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        this.mSlItemGroup.performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$1(View view) {
        this.mSlItemGroup.performLongClick();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        this.pLastCheckBoxClickTime = Long.valueOf(System.currentTimeMillis());
        if (this.mCheckBox.isChecked()) {
            onCheckBoxUnChecked();
        } else {
            this.mCheckBox.setOnCheckedChangeListener(null);
            onCheckBoxChecked(true);
        }
    }

    public void bind(TodoEntity todoEntity, BaseListAdapterBindContext baseListAdapterBindContext, int i, SubTodoListListener subTodoListListener, boolean z) {
        this.mBindContext = baseListAdapterBindContext;
        if (this.mIsAnim) {
            return;
        }
        this.mSubBindContext.setSearchToken(baseListAdapterBindContext.getSearchToken());
        this.mTodoEntity = todoEntity.copy();
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
            this.mExpandGroup.setClickable(false);
        } else {
            this.mExpandGroup.setClickable(true);
        }
        super.bind(todoEntity, baseListAdapterBindContext, i);
        if (this.mIsEditMode && zIsExpand) {
            subModeImpl.setExpand(true);
            todoEntity.setContent(subModeImpl.getContent());
        }
        this.mSlItemGroup.setSwipeEnable(!zIsExpand);
        this.mAdapter.setSubHolderListener(new SubHolderListener() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh$$ExternalSyntheticLambda6
            @Override // com.miui.todo.feature.todolist.SubHolderListener
            public final void onDataChanged(SubMode subMode) {
                this.f$0.lambda$bind$4(subMode);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bind$4(SubMode subMode) {
        this.mTvProgress.setText(subMode.getCompleteSchedule());
        this.mTodoEntity.setContent(subMode.getContent());
        int finishOperation = subMode.getFinishOperation();
        if (finishOperation != 2) {
            this.mTodoEntity.setIsFinish(finishOperation);
        }
        if (finishOperation == 1) {
            this.mSubModeImp = subMode;
            collapsePanel(new Runnable() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$bind$3();
                }
            });
        } else {
            subMode.setExpand(this.mSubModeImp.isExpand());
            this.mSubModeImp = subMode;
            notifyDataChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bind$3() {
        notifyDataFinishStateChanged(true);
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
                this.mContent.setTextColor(NoteApp.getInstance().getColor(R.color.pc_todo_item_content_text_common_color));
            }
            super.setContent(todoEntity);
            if (zIsEmpty) {
                charSequenceChangeHighLight = NoteApp.getInstance().getString(R.string.todo_list);
            } else {
                charSequenceChangeHighLight = ContentUtils.changeHighLight(this.mSubModeImp.getTitle(), this.mBindContext.getSearchToken());
            }
            this.mContent.setText(charSequenceChangeHighLight);
            if (this.mIsFinished) {
                this.mSubModeImp.changeFinishStatusAll(true);
            }
            this.mTvProgress.setText(this.mSubModeImp.getCompleteSchedule());
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRvSubTodoList.getLayoutParams();
            this.mIvExpand.setImageResource(R.drawable.pc_ic_list_expand);
            if (this.mSubModeImp.isExpand()) {
                this.mIvExpand.setRotation(0.0f);
                layoutParams.height = this.mSubTodoItemHeight * this.mSubModeImp.getSubTodoSize();
            } else {
                this.mIvExpand.setRotation(180.0f);
                layoutParams.height = 0;
            }
            this.mRvSubTodoList.setLayoutParams(layoutParams);
            this.mAdapter.bindDataSource(this.mSubModeImp);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.miui.pc.feature.todo.PcTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
        super.onRecycled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void expandPanel() {
        this.mSubModeImp.setExpand(true);
        this.mAdapter.setExpandStatus(true);
        this.mTodoEntity.setContent(this.mSubModeImp.getContent());
        this.mSlItemGroup.setSwipeEnable(false);
        this.mIvExpand.setImageResource(R.drawable.pc_ic_list_expand);
        this.mIvExpand.animate().rotation(0.0f).start();
        int subTodoSize = this.mSubTodoItemHeight * this.mSubModeImp.getSubTodoSize();
        this.mRvSubTodoList.setVisibility(0);
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, subTodoSize);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) PcTodoListItemVh.this.mRvSubTodoList.getLayoutParams();
                layoutParams.height = iIntValue;
                PcTodoListItemVh.this.mRvSubTodoList.setLayoutParams(layoutParams);
            }
        });
        valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PcTodoListItemVh.this.mIsAnim = false;
            }
        });
        valueAnimatorOfInt.setInterpolator(new EaseManager.SpringInterpolator().setDamping(0.9f).setResponse(0.85f));
        valueAnimatorOfInt.setDuration(300L);
        valueAnimatorOfInt.start();
        this.mIsAnim = true;
    }

    private void collapsePanelInActionMode() {
        if (this.mSubModeImp.isExpand()) {
            this.mIvExpand.animate().rotation(180.0f).start();
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mRvSubTodoList.getHeight(), 0);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$collapsePanelInActionMode$5(valueAnimator);
                }
            });
            valueAnimatorOfInt.setInterpolator(new EaseManager.SpringInterpolator().setDamping(0.9f).setResponse(0.85f));
            valueAnimatorOfInt.setDuration(150L);
            valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                }
            });
            valueAnimatorOfInt.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collapsePanelInActionMode$5(ValueAnimator valueAnimator) {
        int iMax = Math.max(0, ((Integer) valueAnimator.getAnimatedValue()).intValue());
        int animatedFraction = (int) ((1.0f - valueAnimator.getAnimatedFraction()) * 16.0f);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRvSubTodoList.getLayoutParams();
        layoutParams.height = iMax;
        this.mRvSubTodoListContainer.setPadding(this.mRvSubTodoList.getPaddingLeft(), this.mRvSubTodoList.getPaddingTop(), this.mRvSubTodoList.getPaddingRight(), animatedFraction);
        this.mRvSubTodoList.setLayoutParams(layoutParams);
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
        this.mIvExpand.setImageResource(R.drawable.pc_ic_list_expand);
        this.mIvExpand.animate().rotation(180.0f).start();
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mRvSubTodoList.getHeight(), 0);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$collapsePanel$6(valueAnimator);
            }
        });
        valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Runnable runnable2;
                PcTodoListItemVh.this.mIsAnim = false;
                if (PcTodoListItemVh.this.getLayoutPosition() == -1 || (runnable2 = runnable) == null) {
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
    public /* synthetic */ void lambda$collapsePanel$6(ValueAnimator valueAnimator) {
        int iMax = Math.max(0, ((Integer) valueAnimator.getAnimatedValue()).intValue());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRvSubTodoList.getLayoutParams();
        int animatedFraction = (int) ((1.0f - valueAnimator.getAnimatedFraction()) * 16.0f);
        layoutParams.height = iMax;
        this.mRvSubTodoListContainer.setPadding(this.mRvSubTodoList.getPaddingLeft(), this.mRvSubTodoList.getPaddingTop(), this.mRvSubTodoList.getPaddingRight(), animatedFraction);
        this.mRvSubTodoList.setLayoutParams(layoutParams);
    }

    @Override // com.miui.pc.feature.todo.PcTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStart(boolean z) {
        if (z) {
            collapsePanelInActionMode();
            this.mExpandGroup.setClickable(false);
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
        this.mExpandGroup.setClickable(true);
    }

    @Override // com.miui.pc.feature.todo.PcTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
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

    @Override // com.miui.pc.feature.todo.PcTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
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

    @Override // com.miui.pc.feature.todo.PcTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onClearAnimation() {
        this.mIvDrag.setAlpha(1.0f);
        this.mCheckBox.setAlpha(1.0f);
        this.mMultiCheckBox.setAlpha(0.0f);
        this.mMultiCheckBox.setScaleX(0.8f);
        this.mMultiCheckBox.setScaleY(0.8f);
        this.mIvExpand.setAlpha(1.0f);
    }

    @Override // com.miui.pc.feature.todo.PcTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
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
        this.mLeftMenu.setBackgroundColor(TodoUtils.getColor(R.color.pc_todo_item_menu_unfinish_color));
        this.mLeftMenu.setText(TodoUtils.getString(R.string.todo_item_status_unfinished));
        this.mContent.getPaint().setFlags(1);
        this.mContent.setTextColor(TodoUtils.getColor(R.color.pc_todo_item_content_text_common_color));
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

    private void notifyDataFinishStateChanged(boolean z) {
        SubTodoListListener subTodoListListener = this.mSubTodoListListener;
        if (subTodoListListener != null) {
            subTodoListListener.onDataFinishStateChanged(this.mTodoEntity, getLayoutPosition(), this.mItemView, z);
            this.mTodoEntity = this.mTodoEntity.copy();
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void setListItemFinished() {
        this.mTodoEntity.setIsFinish(1);
        this.mSubModeImp.changeFinishStatusAll(true);
        this.mTodoEntity.setContent(this.mSubModeImp.getContent());
        collapsePanel(new Runnable() { // from class: com.miui.pc.feature.todo.PcTodoListItemVh$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setListItemFinished$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListItemFinished$7() {
        this.mTvProgress.setText(this.mSubModeImp.getCompleteSchedule());
        notifyDataFinishStateChanged(true);
    }
}
