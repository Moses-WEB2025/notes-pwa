package com.miui.floatwindow.feature.todo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.base.todolist.BaseTodoItemVh;
import com.miui.todo.utils.TodoUtils;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class FwTodoGroupItemVh extends BaseTodoItemVh {
    private FwTodoListAdapterBindContext mBindContext;
    private Boolean mCacheExpand;
    private Integer mCacheGroupType;
    private ImageView mIvArrow;
    private TextView mTvTitle;

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    /* JADX INFO: renamed from: hasAnimationStarted */
    public boolean getMStarted() {
        return false;
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStart(boolean z) {
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStop(boolean z) {
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationUpdate(boolean z, float f) {
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onClearAnimation() {
    }

    @Override // com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        return false;
    }

    public static FwTodoGroupItemVh newInstance(ViewGroup viewGroup) {
        return new FwTodoGroupItemVh(UIUtils.inflateView(viewGroup, R.layout.todo_fw_task_group_item));
    }

    public FwTodoGroupItemVh(final View view) {
        super(view);
        this.mCacheGroupType = null;
        this.mCacheExpand = false;
        this.mBindContext = null;
        this.mTvTitle = (TextView) view.findViewById(R.id.tv_time);
        this.mIvArrow = (ImageView) view.findViewById(R.id.iv_switch);
        DisplayUtils.setMiuiMediumTypeFace(this.mTvTitle);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoGroupItemVh$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$0(view, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view, View view2) {
        if (this.mBindContext.getEditView() == null) {
            float f = this.mCacheExpand.booleanValue() ? 180.0f : 0.0f;
            if (this.mCacheExpand.booleanValue()) {
                this.mIvArrow.animate().rotation(f).setDuration(200L).start();
            }
        }
        if (this.mItemOperationListener != null) {
            this.mItemOperationListener.onToggleGroup(view);
        }
    }

    public void bind(BaseListAdapterBindContext baseListAdapterBindContext, int i, int i2) {
        this.mBindContext = (FwTodoListAdapterBindContext) baseListAdapterBindContext;
        this.mTvTitle.setText(TodoUtils.getGroupNameInCustMode(i2) + StringUtils.SPACE + TodoUtils.obtainLocalInteger(baseListAdapterBindContext.getTodoListAdapter().getGroupItemSize(i2)));
        this.mCacheExpand = Boolean.valueOf(baseListAdapterBindContext.getTodoListAdapter().isExpandGroup(i2));
        Integer num = this.mCacheGroupType;
        boolean z = false;
        if (num == null) {
            this.mCacheGroupType = Integer.valueOf(i2);
        } else if (num.intValue() == i2) {
            z = true;
        }
        float f = this.mCacheExpand.booleanValue() ? 0.0f : 180.0f;
        if (f == this.mIvArrow.getRotation()) {
            return;
        }
        if (z) {
            this.mIvArrow.animate().rotation(f).setDuration(200L).start();
        } else {
            this.mIvArrow.setRotation(f);
        }
    }

    public void setOnDragListener(View.OnDragListener onDragListener) {
        this.itemView.setOnDragListener(onDragListener);
    }
}
