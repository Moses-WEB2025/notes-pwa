package com.miui.todo.feature.todolist;

import android.content.Context;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.base.OnDragItemListener;
import com.miui.todo.base.todolist.BaseTodoItemVh;
import com.miui.todo.base.todolist.BaseTodoListAdapter;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.CustomSortMode;
import com.miui.todo.data.mode.DataGroupInfo;

/* JADX INFO: loaded from: classes4.dex */
public class TodoListAdapter extends BaseTodoListAdapter implements OnDragItemListener {
    private static final String TAG = "TodoListAdapter";
    protected boolean mIsEditMode;

    public TodoListAdapter(Context context, SubTodoListListener subTodoListListener) {
        super(context, subTodoListListener);
    }

    public boolean isLastUnfinishedItem(int i) {
        return (this.mDataSource instanceof CustomSortMode) && i == this.mDataSource.getList(0).size() - 1;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (getItemGroupType(i) != -1 && !isUseSearchData()) {
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
        if (i == 0) {
            baseTodoItemVhNewInstance = TodoChildItemVh.newInstance(viewGroup);
        } else if (i == 3) {
            baseTodoItemVhNewInstance = TodoListItemVh.newInstance(viewGroup);
        } else {
            baseTodoItemVhNewInstance = TodoGroupItemVh.newInstance(viewGroup);
        }
        initViewHolder(baseTodoItemVhNewInstance);
        return baseTodoItemVhNewInstance;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseTodoItemVh baseTodoItemVh, int i) {
        try {
            if (isUseSearchData()) {
                TodoEntity todoEntity = this.mSearchData.get(i);
                if (todoEntity.getListType() == 1) {
                    ((TodoListItemVh) baseTodoItemVh).bind(todoEntity, this.mBindContext, i, this.mSubTodoListListener, this.mIsEditMode);
                    return;
                } else {
                    ((TodoChildItemVh) baseTodoItemVh).setSearchMode(true);
                    ((TodoChildItemVh) baseTodoItemVh).bind(todoEntity, this.mBindContext, i);
                    return;
                }
            }
            DataGroupInfo dataGroupInfo = this.mDataSource.getDataGroupInfo(i);
            if (dataGroupInfo.isGroup()) {
                ((TodoGroupItemVh) baseTodoItemVh).bind((TodoListAdapterBindContext) this.mBindContext, i, dataGroupInfo.mGroupType);
                return;
            }
            TodoEntity childItem = this.mDataSource.getChildItem(dataGroupInfo);
            if (childItem != null) {
                if (childItem.getListType() == 1) {
                    ((TodoListItemVh) baseTodoItemVh).bind(childItem, this.mBindContext, i, this.mSubTodoListListener, this.mIsEditMode);
                } else {
                    ((TodoChildItemVh) baseTodoItemVh).setSearchMode(false);
                    ((TodoChildItemVh) baseTodoItemVh).bind(childItem, this.mBindContext, i);
                }
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "error in onBindViewHolder " + e.toString());
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListAdapter, com.miui.todo.base.OnDragItemListener
    public boolean onDragStart(RecyclerView.ViewHolder viewHolder, int i) {
        return super.onDragStart(viewHolder, i);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListAdapter, com.miui.todo.base.OnDragItemListener
    public void onDragEnd(RecyclerView.ViewHolder viewHolder, int i, boolean z) {
        super.onDragEnd(viewHolder, i, z);
        this.mBindContext.setActionModeMenuEnable(true);
    }

    public void setEditMode(boolean z) {
        this.mIsEditMode = z;
    }
}
