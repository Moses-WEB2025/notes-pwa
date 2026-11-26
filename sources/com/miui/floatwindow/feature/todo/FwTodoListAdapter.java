package com.miui.floatwindow.feature.todo;

import android.content.Context;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.todo.base.OnDragItemListener;
import com.miui.todo.base.todolist.BaseTodoItemVh;
import com.miui.todo.base.todolist.BaseTodoListAdapter;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.DataGroupInfo;
import com.miui.todo.feature.todolist.SubTodoListListener;

/* JADX INFO: loaded from: classes2.dex */
public class FwTodoListAdapter extends BaseTodoListAdapter implements OnDragItemListener {
    private static final String TAG = "FwTodoListAdapter";
    private boolean mIsEditMode;
    private OnItemEditListener mItemEditListener;

    public FwTodoListAdapter(Context context, SubTodoListListener subTodoListListener) {
        super(context, subTodoListListener);
    }

    public void setOnItemEditListener(OnItemEditListener onItemEditListener) {
        this.mItemEditListener = onItemEditListener;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (getDataItemSize() == i) {
            return 2;
        }
        if (getItemGroupType(i) != -1) {
            return 1;
        }
        TodoEntity childItem = getChildItem(i);
        if (childItem == null) {
            return -1;
        }
        return childItem.getListType() == 1 ? 3 : 0;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseTodoItemVh onCreateViewHolder(ViewGroup viewGroup, int i) {
        BaseTodoItemVh baseTodoItemVhNewInstance;
        if (i == 2) {
            return FwPlaceHolderItemVh.newInstance(viewGroup);
        }
        if (i == 0) {
            baseTodoItemVhNewInstance = FwTodoChildItemVh.newInstance(viewGroup);
        } else if (i == 3) {
            baseTodoItemVhNewInstance = FwTodoListItemVh.newInstance(viewGroup);
        } else {
            baseTodoItemVhNewInstance = FwTodoGroupItemVh.newInstance(viewGroup);
        }
        baseTodoItemVhNewInstance.setOtherViewClickListener(this.mOtherViewClickListener);
        baseTodoItemVhNewInstance.setItemOperationListener(this.mTodoItemOperationListener);
        initViewHolder(baseTodoItemVhNewInstance);
        return baseTodoItemVhNewInstance;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseTodoItemVh baseTodoItemVh, int i) {
        if (i == getDataItemSize()) {
            return;
        }
        if (isUseSearchData()) {
            FwTodoChildItemVh fwTodoChildItemVh = (FwTodoChildItemVh) baseTodoItemVh;
            fwTodoChildItemVh.setSearchMode(true);
            fwTodoChildItemVh.bind(this.mSearchData.get(i), this.mBindContext, i);
            return;
        }
        DataGroupInfo dataGroupInfo = this.mDataSource.getDataGroupInfo(i);
        if (dataGroupInfo.isGroup()) {
            ((FwTodoGroupItemVh) baseTodoItemVh).bind(this.mBindContext, i, dataGroupInfo.mGroupType);
            return;
        }
        if (dataGroupInfo.mSubPosition != -1) {
            FwTodoChildItemVh fwTodoChildItemVh2 = (FwTodoChildItemVh) baseTodoItemVh;
            TodoEntity childItem = this.mDataSource.getChildItem(dataGroupInfo);
            if (childItem.getListType() == 1) {
                ((FwTodoListItemVh) fwTodoChildItemVh2).bind(childItem, this.mBindContext, i, this.mSubTodoListListener, this.mIsEditMode);
            } else {
                fwTodoChildItemVh2.setSearchMode(false);
                fwTodoChildItemVh2.bind(childItem, this.mBindContext, i);
            }
            fwTodoChildItemVh2.setOnItemEditListener(this.mItemEditListener);
        }
    }

    private int getDataItemSize() {
        if (this.mDataSource != null) {
            return this.mDataSource.getItemCount();
        }
        return 0;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListAdapter, com.miui.todo.base.OnDragItemListener
    public void onDragEnd(RecyclerView.ViewHolder viewHolder, int i, boolean z) {
        this.mIsInDrag = false;
        this.mDataSource.afterDragItem(this.mDragStartPos, this.mDragEndPos, z);
        if (this.mOnBtnDragItemListener != null) {
            this.mOnBtnDragItemListener.onEndDrag(viewHolder);
        }
        if (this.mDragStartPos != -1 && this.mDragEndPos != -1 && this.mDragStartPos != this.mDragEndPos && this.mPresenter != null) {
            this.mPresenter.getAndUpdateDataToView();
        }
        this.mDragStartPos = -1;
        this.mDragEndPos = -1;
    }
}
