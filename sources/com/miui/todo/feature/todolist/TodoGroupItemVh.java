package com.miui.todo.feature.todolist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.todo.base.todolist.BaseTodoItemVh;
import com.miui.todo.utils.TodoUtils;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TodoGroupItemVh extends BaseTodoItemVh {
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

    public static TodoGroupItemVh newInstance(ViewGroup viewGroup) {
        return new TodoGroupItemVh(UIUtils.inflateView(viewGroup, R.layout.todo_task_group_item));
    }

    public TodoGroupItemVh(final View view) {
        super(view);
        this.mCacheGroupType = null;
        this.mCacheExpand = false;
        this.mTvTitle = (TextView) view.findViewById(R.id.tv_time);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_switch);
        this.mIvArrow = imageView;
        imageView.setContentDescription(view.getContext().getString(R.string.miuix_appcompat_accessibility_expand_state) + StringUtils.SPACE + this.mTvTitle.getText().toString());
        DisplayUtils.setMiuiMediumTypeFace(this.mTvTitle);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.feature.todolist.TodoGroupItemVh.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                float f = TodoGroupItemVh.this.mCacheExpand.booleanValue() ? 180.0f : 0.0f;
                if (TodoGroupItemVh.this.mIvArrow.getRotation() == 0.0f) {
                    view.setContentDescription(view.getContext().getString(R.string.miuix_appcompat_accessibility_collapse_state) + "，" + TodoGroupItemVh.this.mTvTitle.getText().toString());
                } else {
                    view.setContentDescription(view.getContext().getString(R.string.miuix_appcompat_accessibility_expand_state) + "，" + TodoGroupItemVh.this.mTvTitle.getText().toString());
                }
                if (TodoGroupItemVh.this.mCacheExpand.booleanValue()) {
                    TodoGroupItemVh.this.mIvArrow.animate().rotation(f).setDuration(200L).start();
                }
                if (TodoGroupItemVh.this.mItemOperationListener != null) {
                    TodoGroupItemVh.this.mItemOperationListener.onToggleGroup(view);
                }
            }
        });
    }

    public void bind(TodoListAdapterBindContext todoListAdapterBindContext, int i, int i2) {
        this.mTvTitle.setText(TodoUtils.getGroupNameInCustMode(i2) + StringUtils.SPACE + TodoUtils.obtainLocalInteger(todoListAdapterBindContext.getTodoListAdapter().getGroupItemSize(i2)));
        this.mCacheExpand = Boolean.valueOf(todoListAdapterBindContext.getTodoListAdapter().isExpandGroup(i2));
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
