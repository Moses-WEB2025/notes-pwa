package com.miui.floatwindow.feature.todo;

import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.todo.base.todolist.BaseTodoItemVh;
import com.miui.todo.data.bean.TodoEntity;

/* JADX INFO: loaded from: classes2.dex */
public class FwPlaceHolderItemVh extends BaseTodoItemVh {
    public void bind(TodoEntity todoEntity, FwTodoListAdapterBindContext fwTodoListAdapterBindContext, int i) {
    }

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
        return true;
    }

    public static FwPlaceHolderItemVh newInstance(ViewGroup viewGroup) {
        return new FwPlaceHolderItemVh(UIUtils.inflateView(viewGroup, R.layout.todo_task_placeholder_item));
    }

    public FwPlaceHolderItemVh(View view) {
        super(view);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoItemVh
    public void setOtherViewClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }
}
