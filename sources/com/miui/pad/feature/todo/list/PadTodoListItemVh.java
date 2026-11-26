package com.miui.pad.feature.todo.list;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
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
import com.miui.todo.utils.CharUtils;
import miuix.animation.utils.EaseManager;
import miuix.internal.util.ViewUtils;
import miuix.recyclerview.widget.RecyclerView;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PadTodoListItemVh extends PadTodoChildItemVh {
    private static final int INVALID_ID = -1;
    private SubTodoListAdapter mAdapter;
    private BaseListAdapterBindContext mBindContext;
    private Context mContext;
    private LinearLayout mExpandGroup;
    private View.OnClickListener mGroupOnClickListener;
    private boolean mIsAnim;
    private boolean mIsEditMode;
    private View mItemView;
    private ImageView mIvExpand;
    private RecyclerView mRvSubTodoList;
    private RelativeLayout mRvSubTodoListContainer;
    private int mRvSubTodoListContainerPaddingBottomOnExpand;
    private SubTodoListBindContext mSubBindContext;
    private SubMode mSubModeImp;
    private int mSubTodoItemFixedHeight;
    private SubTodoListListener mSubTodoListListener;
    private boolean mTempCollapse;
    private TodoEntity mTodoEntity;
    private TextView mTvProgress;

    @Override // com.miui.pad.feature.todo.list.PadTodoChildItemVh
    protected void updateElementMargin(BaseListAdapterBindContext baseListAdapterBindContext) {
    }

    public static PadTodoListItemVh newInstance(ViewGroup viewGroup, Context context) {
        return new PadTodoListItemVh(UIUtils.inflateView(viewGroup, R.layout.pad_todo_task_list_item), context);
    }

    public PadTodoListItemVh(View view, Context context) {
        super(view);
        this.mSubTodoListListener = null;
        this.mSubTodoItemFixedHeight = 0;
        this.mTempCollapse = false;
        this.mGroupOnClickListener = new View.OnClickListener() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PadTodoListItemVh.this.mIsEditMode) {
                    return;
                }
                if (PadTodoListItemVh.this.mSubModeImp.isExpand()) {
                    PadTodoListItemVh.this.mTvProgress.setContentDescription(((Object) PadTodoListItemVh.this.mTvProgress.getText()) + "," + PadTodoListItemVh.this.mContext.getResources().getString(R.string.action_expand) + "," + PadTodoListItemVh.this.itemView.getContext().getString(R.string.button_talkback) + "," + PadTodoListItemVh.this.itemView.getContext().getString(R.string.miuix_appcompat_accessibility_collapse_state));
                    PadTodoListItemVh.this.collapsePanel(null);
                } else {
                    PadTodoListItemVh.this.mTvProgress.setContentDescription(((Object) PadTodoListItemVh.this.mTvProgress.getText()) + "," + PadTodoListItemVh.this.mContext.getResources().getString(R.string.action_collapse) + "," + PadTodoListItemVh.this.itemView.getContext().getString(R.string.button_talkback) + "," + PadTodoListItemVh.this.itemView.getContext().getString(R.string.miuix_appcompat_accessibility_expand_state));
                    PadTodoListItemVh.this.expandPanel();
                }
                if (PadTodoListItemVh.this.mSubTodoListListener != null) {
                    PadTodoListItemVh.this.mSubTodoListListener.onDataChanged(PadTodoListItemVh.this.mTodoEntity, PadTodoListItemVh.this.getLayoutPosition(), PadTodoListItemVh.this.mItemView);
                    PadTodoListItemVh padTodoListItemVh = PadTodoListItemVh.this;
                    padTodoListItemVh.mTodoEntity = padTodoListItemVh.mTodoEntity.copy();
                }
            }
        };
        this.mItemView = view;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.accessibilityManager = (AccessibilityManager) applicationContext.getSystemService(AccessibilityManager.class);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_expand_group);
        this.mExpandGroup = linearLayout;
        linearLayout.setClickable(true);
        this.mExpandGroup.setOnClickListener(this.mGroupOnClickListener);
        this.mIvExpand = (ImageView) view.findViewById(R.id.iv_expand);
        TextView textView = (TextView) view.findViewById(R.id.tv_progress);
        this.mTvProgress = textView;
        DisplayUtils.setMiuiMediumTypeFace(textView);
        this.mSubTodoItemFixedHeight = view.getResources().getDimensionPixelSize(R.dimen.sub_todo_item_height);
        this.mRvSubTodoListContainer = (RelativeLayout) view.findViewById(R.id.rl_subtodo);
        this.mRvSubTodoListContainerPaddingBottomOnExpand = 22;
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_subtodo);
        this.mRvSubTodoList = recyclerView;
        recyclerView.setSpringEnabled(false);
        this.mRvSubTodoList.setLayoutManager(new TodoLayoutManager(view.getContext()));
        this.mRvSubTodoList.setItemAnimator(new DefaultItemAnimator());
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return this.f$0.lambda$new$0(view2);
            }
        };
        this.mAdapter = new PadSubTodoListAdapter(view.getContext(), new View.OnClickListener() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$1(view2);
            }
        }, onLongClickListener);
        SubTodoListBindContext subTodoListBindContext = new SubTodoListBindContext(this.mRvSubTodoList, this.mAdapter);
        this.mSubBindContext = subTodoListBindContext;
        this.mAdapter.setBindContext(subTodoListBindContext);
        this.mAdapter.setHasStableIds(true);
        this.mRvSubTodoList.setAdapter(this.mAdapter);
        this.mCheckboxMask.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$2(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(View view) {
        this.mSlItemGroup.performLongClick();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        this.mSlItemGroup.performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        if (!this.mCheckBox.isChecked()) {
            onCheckBoxChecked(true);
        } else {
            onCheckBoxUnChecked();
        }
    }

    public void bind(TodoEntity todoEntity, BaseListAdapterBindContext baseListAdapterBindContext, final int i, SubTodoListListener subTodoListListener, boolean z) {
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
        this.mAdapter.setSubHolderListener(new SubHolderListener() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh.1
            @Override // com.miui.todo.feature.todolist.SubHolderListener
            public void onDataChanged(SubMode subMode) {
                PadTodoListItemVh.this.mTvProgress.setText(subMode.getCompleteSchedule());
                PadTodoListItemVh.this.mTodoEntity.setContent(subMode.getContent());
                int finishOperation = subMode.getFinishOperation();
                if (finishOperation != 2) {
                    PadTodoListItemVh.this.mTodoEntity.setIsFinish(finishOperation);
                }
                if (finishOperation == 1) {
                    PadTodoListItemVh.this.mSubModeImp = subMode;
                    PadTodoListItemVh.this.collapsePanel(new Runnable() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PadTodoListItemVh.this.notifyDataFinishStateChanged(true);
                        }
                    });
                    return;
                }
                subMode.setExpand(PadTodoListItemVh.this.mSubModeImp.isExpand());
                PadTodoListItemVh.this.mSubModeImp = subMode;
                if (PadTodoListItemVh.this.mSubTodoListListener != null) {
                    PadTodoListItemVh.this.mSubTodoListListener.onDataChanged(PadTodoListItemVh.this.mTodoEntity, i, PadTodoListItemVh.this.mItemView);
                    PadTodoListItemVh padTodoListItemVh = PadTodoListItemVh.this;
                    padTodoListItemVh.mTodoEntity = padTodoListItemVh.mTodoEntity.copy();
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
            if (this.accessibilityManager != null && this.accessibilityManager.isTouchExplorationEnabled()) {
                if (this.mSubModeImp.isExpand()) {
                    this.mTvProgress.setContentDescription(((Object) this.mTvProgress.getText()) + "," + this.mContext.getResources().getString(R.string.action_collapse) + "," + this.itemView.getContext().getString(R.string.button_talkback) + "," + this.itemView.getContext().getString(R.string.miuix_appcompat_accessibility_expand_state));
                } else {
                    this.mTvProgress.setContentDescription(((Object) this.mTvProgress.getText()) + "," + this.mContext.getResources().getString(R.string.action_expand) + "," + this.itemView.getContext().getString(R.string.button_talkback) + "," + this.itemView.getContext().getString(R.string.miuix_appcompat_accessibility_collapse_state));
                }
            }
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRvSubTodoList.getLayoutParams();
            this.mIvExpand.setImageResource(R.drawable.pad_ic_list_expand);
            if (this.mSubModeImp.isExpand()) {
                this.mIvExpand.setRotation(0.0f);
                layoutParams.height = (this.mSubTodoItemFixedHeight * this.mSubModeImp.getSubTodoSize()) + this.mRvSubTodoList.getPaddingTop();
                this.mRvSubTodoListContainer.setPadding(this.mRvSubTodoList.getPaddingLeft(), 0, this.mRvSubTodoList.getPaddingRight(), this.mRvSubTodoListContainerPaddingBottomOnExpand);
            } else {
                this.mIvExpand.setRotation(180.0f);
                layoutParams.height = 0;
                this.mRvSubTodoListContainer.setPadding(this.mRvSubTodoList.getPaddingLeft(), 0, this.mRvSubTodoList.getPaddingRight(), 0);
            }
            this.mRvSubTodoList.setLayoutParams(layoutParams);
            this.mAdapter.bindDataSource(this.mSubModeImp);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoItemVh
    public void onAttached(BaseListAdapterBindContext baseListAdapterBindContext) {
        super.onAttached(baseListAdapterBindContext);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoItemVh
    public void onDettached(BaseListAdapterBindContext baseListAdapterBindContext) {
        super.onDettached(baseListAdapterBindContext);
    }

    @Override // com.miui.pad.feature.todo.list.PadTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
        super.onRecycled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void expandPanel() {
        this.mSubModeImp.setExpand(true);
        this.mAdapter.setExpandStatus(true);
        this.mTodoEntity.setContent(this.mSubModeImp.getContent());
        this.mIvExpand.setImageResource(R.drawable.pad_ic_list_expand);
        this.mIvExpand.animate().rotation(0.0f).start();
        int subTodoSize = (this.mSubTodoItemFixedHeight * this.mSubModeImp.getSubTodoSize()) + this.mRvSubTodoList.getPaddingTop();
        this.mRvSubTodoList.setVisibility(0);
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, subTodoSize);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                int animatedFraction = (int) (valueAnimator.getAnimatedFraction() * PadTodoListItemVh.this.mRvSubTodoListContainerPaddingBottomOnExpand);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) PadTodoListItemVh.this.mRvSubTodoList.getLayoutParams();
                layoutParams.height = iIntValue;
                PadTodoListItemVh.this.mRvSubTodoListContainer.setPadding(PadTodoListItemVh.this.mRvSubTodoList.getPaddingLeft(), 0, PadTodoListItemVh.this.mRvSubTodoList.getPaddingRight(), animatedFraction);
                PadTodoListItemVh.this.mRvSubTodoList.setLayoutParams(layoutParams);
            }
        });
        valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PadTodoListItemVh.this.mIsAnim = false;
                int[] iArr = new int[2];
                PadTodoListItemVh.this.mItemView.getLocationInWindow(iArr);
                PadTodoListItemVh.this.mSubTodoListListener.scroll(0, iArr[1], PadTodoListItemVh.this.mItemView.getHeight());
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
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$collapsePanelInActionMode$3(valueAnimator);
                }
            });
            valueAnimatorOfInt.setInterpolator(new EaseManager.SpringInterpolator().setDamping(0.9f).setResponse(0.85f));
            valueAnimatorOfInt.setDuration(150L);
            valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                }
            });
            valueAnimatorOfInt.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collapsePanelInActionMode$3(ValueAnimator valueAnimator) {
        int iMax = Math.max(0, ((Integer) valueAnimator.getAnimatedValue()).intValue());
        int animatedFraction = (int) ((1.0f - valueAnimator.getAnimatedFraction()) * 16.0f);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRvSubTodoList.getLayoutParams();
        layoutParams.height = iMax;
        this.mRvSubTodoListContainer.setPadding(this.mRvSubTodoList.getPaddingLeft(), 0, this.mRvSubTodoList.getPaddingRight(), animatedFraction);
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
        this.mIvExpand.setImageResource(R.drawable.pad_ic_list_expand);
        this.mIvExpand.animate().rotation(180.0f).start();
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mRvSubTodoList.getHeight(), 0);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iMax = Math.max(0, ((Integer) valueAnimator.getAnimatedValue()).intValue());
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) PadTodoListItemVh.this.mRvSubTodoList.getLayoutParams();
                int animatedFraction = (int) ((1.0f - valueAnimator.getAnimatedFraction()) * 16.0f);
                layoutParams.height = iMax;
                PadTodoListItemVh.this.mRvSubTodoListContainer.setPadding(PadTodoListItemVh.this.mRvSubTodoList.getPaddingLeft(), 0, PadTodoListItemVh.this.mRvSubTodoList.getPaddingRight(), animatedFraction);
                PadTodoListItemVh.this.mRvSubTodoList.setLayoutParams(layoutParams);
            }
        });
        valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Runnable runnable2;
                PadTodoListItemVh.this.mIsAnim = false;
                if (PadTodoListItemVh.this.getLayoutPosition() == -1 || (runnable2 = runnable) == null) {
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

    @Override // com.miui.pad.feature.todo.list.PadTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
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

    @Override // com.miui.pad.feature.todo.list.PadTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
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

    @Override // com.miui.pad.feature.todo.list.PadTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
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

    @Override // com.miui.pad.feature.todo.list.PadTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onClearAnimation() {
        this.mIvDrag.setAlpha(1.0f);
        this.mCheckBox.setAlpha(1.0f);
        this.mMultiCheckBox.setAlpha(0.0f);
        this.mMultiCheckBox.setScaleX(0.8f);
        this.mMultiCheckBox.setScaleY(0.8f);
        this.mIvExpand.setAlpha(1.0f);
    }

    @Override // com.miui.pad.feature.todo.list.PadTodoChildItemVh, com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
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

    private void notifyDataChanged() {
        SubTodoListListener subTodoListListener = this.mSubTodoListListener;
        if (subTodoListListener != null) {
            subTodoListListener.onDataChanged(this.mTodoEntity, getLayoutPosition(), this.mItemView);
            this.mTodoEntity = this.mTodoEntity.copy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDataFinishStateChanged(boolean z) {
        TodoEntity todoEntity;
        SubTodoListListener subTodoListListener = this.mSubTodoListListener;
        if (subTodoListListener == null || (todoEntity = this.mTodoEntity) == null) {
            return;
        }
        subTodoListListener.onDataFinishStateChanged(todoEntity, getLayoutPosition(), this.mItemView, z);
        this.mTodoEntity = this.mTodoEntity.copy();
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void setListItemFinished() {
        if (this.mTodoEntity != null && this.mSubModeImp != null) {
            this.mAdapter.setSubHolderListener(null);
            this.mTodoEntity.setIsFinish(1);
            this.mSubModeImp.changeFinishStatusAll(true);
            this.mTodoEntity.setContent(this.mSubModeImp.getContent());
        }
        collapsePanel(new Runnable() { // from class: com.miui.pad.feature.todo.list.PadTodoListItemVh$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setListItemFinished$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListItemFinished$4() {
        SubMode subMode;
        TextView textView = this.mTvProgress;
        if (textView == null || (subMode = this.mSubModeImp) == null) {
            return;
        }
        textView.setText(subMode.getCompleteSchedule());
        notifyDataFinishStateChanged(true);
    }
}
