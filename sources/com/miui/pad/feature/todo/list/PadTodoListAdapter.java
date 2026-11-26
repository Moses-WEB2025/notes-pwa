package com.miui.pad.feature.todo.list;

import android.content.Context;
import android.view.ViewGroup;
import com.miui.common.tool.Logger;
import com.miui.todo.base.OnDragItemListener;
import com.miui.todo.base.todolist.BaseTodoItemVh;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.DataGroupInfo;
import com.miui.todo.feature.todolist.SubTodoListListener;
import com.miui.todo.feature.todolist.TodoGroupItemVh;
import com.miui.todo.feature.todolist.TodoListAdapter;
import com.miui.todo.feature.todolist.TodoListAdapterBindContext;

/* JADX INFO: loaded from: classes4.dex */
public class PadTodoListAdapter extends TodoListAdapter implements OnDragItemListener {
    private static final String TAG = "PadTodoListAdapter";

    public PadTodoListAdapter(Context context, SubTodoListListener subTodoListListener) {
        super(context, subTodoListListener);
    }

    @Override // com.miui.todo.feature.todolist.TodoListAdapter, com.miui.todo.base.todolist.BaseTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        TodoEntity childItem = getChildItem(i);
        if (childItem == null) {
            return -1;
        }
        return childItem.getListType() == 1 ? 3 : 0;
    }

    @Override // com.miui.todo.feature.todolist.TodoListAdapter, com.miui.todo.base.todolist.BaseTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseTodoItemVh onCreateViewHolder(ViewGroup viewGroup, int i) {
        BaseTodoItemVh baseTodoItemVhNewInstance;
        if (i == 0) {
            baseTodoItemVhNewInstance = PadTodoChildItemVh.newInstance(viewGroup);
        } else {
            baseTodoItemVhNewInstance = PadTodoListItemVh.newInstance(viewGroup, this.mContext);
        }
        initViewHolder(baseTodoItemVhNewInstance);
        return baseTodoItemVhNewInstance;
    }

    @Override // com.miui.todo.feature.todolist.TodoListAdapter, com.miui.todo.base.todolist.BaseTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseTodoItemVh baseTodoItemVh, int i) {
        if (this.mIsInSearchMode) {
            if (i >= this.mSearchData.size()) {
                Logger.INSTANCE.d(TAG, "onBindViewHolder: mIsInSearchMode return");
                return;
            }
            TodoEntity todoEntity = this.mSearchData.get(i);
            if (todoEntity.getListType() == 1) {
                if (baseTodoItemVh instanceof PadTodoListItemVh) {
                    ((PadTodoListItemVh) baseTodoItemVh).bind(todoEntity, this.mBindContext, i, this.mSubTodoListListener, this.mIsEditMode);
                    return;
                } else {
                    Logger.INSTANCE.w(TAG, "Unknown ViewHolder type at pos " + i);
                    return;
                }
            }
            PadTodoChildItemVh padTodoChildItemVh = (PadTodoChildItemVh) baseTodoItemVh;
            padTodoChildItemVh.setSearchMode(true);
            padTodoChildItemVh.bind(todoEntity, this.mBindContext, i);
            return;
        }
        if (this.mDataSource == null) {
            Logger.INSTANCE.d(TAG, "onBindViewHolder: mIsInSearchMode return");
            return;
        }
        Logger.INSTANCE.d(TAG, "onBindViewHolder: mIsInSearchMode false === " + i);
        DataGroupInfo dataGroupInfo = this.mDataSource.getDataGroupInfo(i);
        if (dataGroupInfo.isGroup()) {
            ((TodoGroupItemVh) baseTodoItemVh).bind((TodoListAdapterBindContext) this.mBindContext, i, dataGroupInfo.mGroupType);
            return;
        }
        TodoEntity childItem = this.mDataSource.getChildItem(dataGroupInfo);
        if (childItem == null) {
            return;
        }
        if (childItem.getListType() == 1) {
            if (baseTodoItemVh instanceof PadTodoListItemVh) {
                ((PadTodoListItemVh) baseTodoItemVh).bind(childItem, this.mBindContext, i, this.mSubTodoListListener, this.mIsEditMode);
                return;
            } else {
                Logger.INSTANCE.w(TAG, "Unknown ViewHolder type at pos: " + i);
                return;
            }
        }
        PadTodoChildItemVh padTodoChildItemVh2 = (PadTodoChildItemVh) baseTodoItemVh;
        padTodoChildItemVh2.setSearchMode(false);
        padTodoChildItemVh2.bind(childItem, this.mBindContext, i);
    }
}
