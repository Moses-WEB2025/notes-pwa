package com.miui.pc.feature.todo;

import android.content.Context;
import android.view.ViewGroup;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.base.OnDragItemListener;
import com.miui.todo.base.todolist.BaseTodoItemVh;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.DataGroupInfo;
import com.miui.todo.feature.todolist.SubTodoListListener;
import com.miui.todo.feature.todolist.TodoGroupItemVh;
import com.miui.todo.feature.todolist.TodoListAdapter;
import com.miui.todo.feature.todolist.TodoListAdapterBindContext;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoListAdapter extends TodoListAdapter implements OnDragItemListener {
    private static final String TAG = "PadTodoListAdapter";

    public PcTodoListAdapter(Context context, SubTodoListListener subTodoListListener) {
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
            baseTodoItemVhNewInstance = PcTodoChildItemVh.newInstance(viewGroup);
        } else {
            baseTodoItemVhNewInstance = PcTodoListItemVh.newInstance(viewGroup);
        }
        initViewHolder(baseTodoItemVhNewInstance);
        return baseTodoItemVhNewInstance;
    }

    @Override // com.miui.todo.feature.todolist.TodoListAdapter, com.miui.todo.base.todolist.BaseTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseTodoItemVh baseTodoItemVh, int i) {
        if (isUseSearchData()) {
            TodoEntity todoEntity = this.mSearchData.get(i);
            if (todoEntity.getListType() == 1) {
                ((PcTodoListItemVh) baseTodoItemVh).bind(todoEntity, this.mBindContext, i, this.mSubTodoListListener, this.mIsEditMode);
                return;
            }
            PcTodoChildItemVh pcTodoChildItemVh = (PcTodoChildItemVh) baseTodoItemVh;
            pcTodoChildItemVh.setSearchMode(true);
            pcTodoChildItemVh.bind(todoEntity, this.mBindContext, i);
            return;
        }
        DataGroupInfo dataGroupInfo = this.mDataSource.getDataGroupInfo(i);
        if (dataGroupInfo.isGroup()) {
            ((TodoGroupItemVh) baseTodoItemVh).bind((TodoListAdapterBindContext) this.mBindContext, i, dataGroupInfo.mGroupType);
            return;
        }
        TodoEntity childItem = this.mDataSource.getChildItem(dataGroupInfo);
        if (childItem.getListType() == 1) {
            if (baseTodoItemVh instanceof TodoGroupItemVh) {
                Logger.INSTANCE.i("1", "xx");
            }
            ((PcTodoListItemVh) baseTodoItemVh).bind(childItem, this.mBindContext, i, this.mSubTodoListListener, this.mIsEditMode);
        } else {
            PcTodoChildItemVh pcTodoChildItemVh2 = (PcTodoChildItemVh) baseTodoItemVh;
            pcTodoChildItemVh2.setSearchMode(false);
            pcTodoChildItemVh2.bind(childItem, this.mBindContext, i);
            if (baseTodoItemVh instanceof TodoGroupItemVh) {
                Logger.INSTANCE.i("1", "xx");
            }
        }
    }
}
