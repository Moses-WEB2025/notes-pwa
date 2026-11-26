package com.miui.floatwindow.feature.todo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.RomUtils;
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
import com.miui.todo.utils.CharUtils;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;
import miuix.animation.utils.EaseManager;
import miuix.recyclerview.widget.MiuiDefaultItemAnimator;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class FwTodoListItemVh extends FwTodoChildItemVh {
    private static final int INVALID_ID = -1;
    public SubTodoCheckBoxClickCallback checkBoxClickCallback;
    private SubTodoListAdapter mAdapter;
    private ViewGroup mContentGroup;
    private LinearLayout mExpandGroup;
    private boolean mIsAnim;
    private View mItemView;
    private ImageView mIvExpand;
    private RecyclerView.OnScrollListener mOnScrollListener;
    private miuix.recyclerview.widget.RecyclerView mRvSubTodoList;
    private SwipeMenuLayout mSlItemGroup;
    private SubMode mSubModeImp;
    private SubTodoListListener mSubTodoListListener;
    private int mSubtodoItemHeight;
    private boolean mTempCollapse;
    private TextView mTvProgress;

    public interface SubTodoCheckBoxClickCallback {
        boolean checkBoxClick();
    }

    @Override // com.miui.floatwindow.feature.todo.FwTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
    }

    public static FwTodoListItemVh newInstance(ViewGroup viewGroup) {
        if (RomUtils.isPadMode()) {
            return new FwTodoListItemVh(UIUtils.inflateView(viewGroup, R.layout.pad_todo_fw_task_list_item));
        }
        return new FwTodoListItemVh(UIUtils.inflateView(viewGroup, R.layout.todo_fw_task_list_item));
    }

    public FwTodoListItemVh(final View view) {
        super(view);
        this.mSubTodoListListener = null;
        this.mSubtodoItemHeight = 0;
        this.mTempCollapse = false;
        this.checkBoxClickCallback = new SubTodoCheckBoxClickCallback() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh$$ExternalSyntheticLambda1
            @Override // com.miui.floatwindow.feature.todo.FwTodoListItemVh.SubTodoCheckBoxClickCallback
            public final boolean checkBoxClick() {
                return this.f$0.lambda$new$3();
            }
        };
        this.mItemView = view;
        this.mSlItemGroup = (SwipeMenuLayout) view.findViewById(R.id.sl_item_group);
        this.mExpandGroup = (LinearLayout) view.findViewById(R.id.ll_expand_group);
        this.mContentGroup = (ViewGroup) view.findViewById(R.id.content_group);
        this.mIvExpand = (ImageView) view.findViewById(R.id.iv_expand);
        TextView textView = (TextView) view.findViewById(R.id.tv_progress);
        this.mTvProgress = textView;
        DisplayUtils.setMiuiMediumTypeFace(textView);
        miuix.recyclerview.widget.RecyclerView recyclerView = (miuix.recyclerview.widget.RecyclerView) view.findViewById(R.id.rv_subtodo);
        this.mRvSubTodoList = recyclerView;
        recyclerView.setSpringEnabled(false);
        this.mRvSubTodoList.setNestedScrollingEnabled(false);
        this.mRvSubTodoList.setLayoutManager(new TodoLayoutManager(view.getContext()));
        this.mRvSubTodoList.setItemAnimator(new MiuiDefaultItemAnimator());
        SubTodoListAdapter subTodoListAdapter = new SubTodoListAdapter(view.getContext(), new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$0(view2);
            }
        }, new View.OnLongClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return this.f$0.lambda$new$1(view2);
            }
        });
        this.mAdapter = subTodoListAdapter;
        subTodoListAdapter.setBindContext(new SubTodoListBindContext(this.mRvSubTodoList, this.mAdapter));
        this.mAdapter.setSubTodoCheckBoxClickCallback(this.checkBoxClickCallback);
        this.mAdapter.setHasStableIds(true);
        this.mRvSubTodoList.setAdapter(this.mAdapter);
        this.mSubtodoItemHeight = view.getContext().getResources().getDimensionPixelSize(R.dimen.sub_todo_item_height);
        this.mCheckboxMask.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$2(view, view2);
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
    public /* synthetic */ void lambda$new$2(View view, View view2) {
        this.pLastCheckBoxClickTime = Long.valueOf(System.currentTimeMillis());
        if (this.mBindContext == null) {
            return;
        }
        if (this.mCheckBox.isChecked()) {
            onCheckBoxUnChecked();
            return;
        }
        if (this.mBindContext != null && this.mBindContext.getEditView() != null) {
            if (this.mItemOperationListener != null) {
                this.mItemOperationListener.onFinished(view);
            }
            this.mCheckboxMask.setContentDescription(this.mCheckboxMask.getContext().getString(R.string.todo_item_status_finished));
            this.mCheckboxMask.announceForAccessibility(this.mCheckboxMask.getContentDescription());
            return;
        }
        this.mCheckBox.setOnCheckedChangeListener(null);
        onCheckBoxChecked(true);
    }

    public void bind(TodoEntity todoEntity, BaseListAdapterBindContext baseListAdapterBindContext, int i, SubTodoListListener subTodoListListener, boolean z) {
        if (this.mIsAnim) {
            return;
        }
        monitorScrollStateChanged(baseListAdapterBindContext);
        this.mSubTodoListListener = subTodoListListener;
        SubModeImpl subModeImpl = new SubModeImpl(todoEntity.getContent());
        if (baseListAdapterBindContext.getLastFinishId() != -1 && baseListAdapterBindContext.getLastFinishId() == todoEntity.getId()) {
            this.mTempCollapse = true;
        }
        boolean zIsExpand = subModeImpl.isExpand();
        if (z) {
            subModeImpl.setExpand(false);
            todoEntity.setContent(subModeImpl.getContent());
        }
        super.bind(todoEntity, baseListAdapterBindContext, i);
        if (z && zIsExpand) {
            subModeImpl.setExpand(true);
            todoEntity.setContent(subModeImpl.getContent());
        }
        this.mExpandGroup.setClickable(true);
        this.mAdapter.setSubHolderListener(new SubHolderListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh.1
            @Override // com.miui.todo.feature.todolist.SubHolderListener
            public void onDataChanged(SubMode subMode) {
                FwTodoListItemVh.this.mTvProgress.setText(subMode.getCompleteSchedule());
                FwTodoListItemVh.this.mTodoEntity.setContent(subMode.getContent());
                int finishOperation = subMode.getFinishOperation();
                if (finishOperation != 2) {
                    FwTodoListItemVh.this.mTodoEntity.setIsFinish(finishOperation);
                }
                if (finishOperation == 1) {
                    FwTodoListItemVh.this.mSubModeImp = subMode;
                    FwTodoListItemVh.this.collapsePanel(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            FwTodoListItemVh.this.notifyDataFinishStateChanged(true);
                        }
                    });
                } else {
                    subMode.setExpand(FwTodoListItemVh.this.mSubModeImp.isExpand());
                    FwTodoListItemVh.this.mSubModeImp = subMode;
                    FwTodoListItemVh.this.notifyDataChanged();
                }
            }
        });
    }

    @Override // com.miui.floatwindow.feature.todo.FwTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoItemVh
    public void setOtherViewClickListener(final View.OnClickListener onClickListener) {
        super.setOtherViewClickListener(onClickListener);
        LinearLayout linearLayout = this.mExpandGroup;
        if (linearLayout != null) {
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    onClickListener.onClick(view);
                    FwTodoListItemVh.this.onTogglePanelExpandState();
                }
            });
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void setContent(TodoEntity todoEntity) {
        CharSequence title;
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
                title = NoteApp.getInstance().getString(R.string.todo_list);
            } else {
                title = this.mSubModeImp.getTitle();
            }
            int iIndexOf = title.toString().indexOf(StringUtils.LF);
            if (title.length() > 1 && iIndexOf >= 0 && title.subSequence(0, iIndexOf).toString().trim().isEmpty()) {
                title = CharUtils.removeLeadingEmptyLines(title);
            }
            this.mContent.setText(title);
            if (this.mIsFinished) {
                this.mSubModeImp.changeFinishStatusAll(true);
            }
            this.mTvProgress.setText(this.mSubModeImp.getCompleteSchedule());
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRvSubTodoList.getLayoutParams();
            this.mIvExpand.setImageResource(R.drawable.ic_list_expand);
            if (this.mSubModeImp.isExpand()) {
                this.mIvExpand.setRotation(0.0f);
                this.mExpandGroup.setContentDescription(this.itemView.getContext().getString(R.string.action_collapse));
                layoutParams.height = this.mSubtodoItemHeight * this.mSubModeImp.getSubTodoSize();
            } else {
                this.mIvExpand.setRotation(180.0f);
                this.mIvExpand.setContentDescription(this.itemView.getContext().getString(R.string.action_expand));
                layoutParams.height = 0;
            }
            this.mAdapter.bindDataSource(this.mSubModeImp);
            this.mRvSubTodoList.setLayoutParams(layoutParams);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.miui.floatwindow.feature.todo.FwTodoChildItemVh, com.miui.todo.base.IEditTodoView
    public void exitEditMode(boolean z, boolean z2) {
        super.exitEditMode(z, z2);
        SubTodoListAdapter subTodoListAdapter = this.mAdapter;
        if (subTodoListAdapter != null) {
            subTodoListAdapter.notifyDataSetChanged();
        }
    }

    public void onTogglePanelExpandState() {
        if (this.mSubModeImp.isExpand()) {
            collapsePanel(null);
        } else {
            expandPanel();
        }
        SubTodoListListener subTodoListListener = this.mSubTodoListListener;
        if (subTodoListListener != null) {
            subTodoListListener.onDataChanged(this.mTodoEntity, getLayoutPosition(), this.mItemView);
            this.mTodoEntity = this.mTodoEntity.copy();
        }
    }

    private void expandPanel() {
        this.mSubModeImp.setExpand(true);
        this.mAdapter.setExpandStatus(true);
        this.mTodoEntity.setContent(this.mSubModeImp.getContent());
        this.mIvExpand.setImageResource(R.drawable.ic_list_expand);
        this.mExpandGroup.setContentDescription(this.itemView.getContext().getString(R.string.action_collapse));
        this.mIvExpand.animate().rotation(0.0f).start();
        final int subTodoSize = this.mSubtodoItemHeight * this.mSubModeImp.getSubTodoSize();
        this.mRvSubTodoList.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(subTodoSize, 1073741824));
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh.3
            IntEvaluator evaluatorH = new IntEvaluator();

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) FwTodoListItemVh.this.mRvSubTodoList.getLayoutParams();
                layoutParams.height = this.evaluatorH.evaluate(valueAnimator.getAnimatedFraction(), (Integer) 0, Integer.valueOf(subTodoSize)).intValue();
                FwTodoListItemVh.this.mRvSubTodoList.setLayoutParams(layoutParams);
            }
        });
        valueAnimatorOfFloat.setInterpolator(new EaseManager.SpringInterpolator().setDamping(0.9f).setResponse(0.85f));
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                FwTodoListItemVh.this.mIsAnim = false;
            }
        });
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.start();
        this.mIsAnim = true;
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
        this.mIvExpand.setImageResource(R.drawable.ic_list_expand);
        this.mExpandGroup.setContentDescription(this.itemView.getContext().getString(R.string.action_expand));
        this.mIvExpand.animate().rotation(180.0f).start();
        final int height = this.mRvSubTodoList.getHeight();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh.5
            final IntEvaluator evaluatorH = new IntEvaluator();

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) FwTodoListItemVh.this.mRvSubTodoList.getLayoutParams();
                int iIntValue = this.evaluatorH.evaluate(valueAnimator.getAnimatedFraction(), Integer.valueOf(height), (Integer) 0).intValue();
                layoutParams.height = iIntValue >= 0 ? iIntValue : 0;
                FwTodoListItemVh.this.mRvSubTodoList.setLayoutParams(layoutParams);
            }
        });
        valueAnimatorOfFloat.setInterpolator(new EaseManager.SpringInterpolator().setDamping(0.9f).setResponse(0.85f));
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Runnable runnable2;
                FwTodoListItemVh.this.mIsAnim = false;
                if (FwTodoListItemVh.this.getLayoutPosition() == -1 || (runnable2 = runnable) == null) {
                    return;
                }
                runnable2.run();
            }
        });
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.start();
        this.mIsAnim = true;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void onUnChecked() {
        this.mContent.getPaint().setFlags(1);
        this.mContent.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_common_color));
        setTimeTextColor(this.mTime, this.mIvClock, this.mScheduleTime, this.mRepeatType, false);
    }

    private void monitorScrollStateChanged(BaseListAdapterBindContext baseListAdapterBindContext) {
        if (this.mOnScrollListener == null) {
            this.mOnScrollListener = new RecyclerView.OnScrollListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh.7
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                    if (i != 0) {
                        FwTodoListItemVh.this.mRvSubTodoList.setItemAnimator(null);
                    } else {
                        FwTodoListItemVh.this.mRvSubTodoList.setItemAnimator(new MiuiDefaultItemAnimator());
                    }
                }
            };
            baseListAdapterBindContext.getRecyclerView().addOnScrollListener(this.mOnScrollListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDataChanged() {
        SubTodoListListener subTodoListListener = this.mSubTodoListListener;
        if (subTodoListListener != null) {
            subTodoListListener.onDataChanged(this.mTodoEntity, getLayoutPosition(), this.mItemView);
            this.mTodoEntity = this.mTodoEntity.copy();
        }
    }

    protected void notifyDataFinishStateChanged(boolean z) {
        SubTodoListListener subTodoListListener = this.mSubTodoListListener;
        if (subTodoListListener != null) {
            subTodoListListener.onDataFinishStateChanged(this.mTodoEntity, getLayoutPosition(), this.mItemView, z);
            this.mTodoEntity = this.mTodoEntity.copy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$3() {
        if (this.mBindContext.getEditId() == -1 || this.mItemOperationListener == null) {
            return false;
        }
        this.mItemOperationListener.onFinished(this.itemView);
        return true;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void setListItemFinished() {
        this.mAdapter.setSubHolderListener(null);
        this.mTodoEntity.setIsFinish(1);
        this.mSubModeImp.changeFinishStatusAll(true);
        this.mTodoEntity.setContent(this.mSubModeImp.getContent());
        collapsePanel(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListItemVh$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setListItemFinished$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListItemFinished$4() {
        this.mTvProgress.setText(this.mSubModeImp.getCompleteSchedule());
        notifyDataFinishStateChanged(true);
    }
}
