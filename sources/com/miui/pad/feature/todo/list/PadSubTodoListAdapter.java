package com.miui.pad.feature.todo.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.miui.todo.feature.todolist.SubTodoItemVh;
import com.miui.todo.feature.todolist.SubTodoListAdapter;

/* JADX INFO: loaded from: classes4.dex */
public class PadSubTodoListAdapter extends SubTodoListAdapter {
    public PadSubTodoListAdapter(Context context, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        super(context, onClickListener, onLongClickListener);
    }

    @Override // com.miui.todo.feature.todolist.SubTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public SubTodoItemVh onCreateViewHolder(ViewGroup viewGroup, int i) {
        return PadSubTodoItemVh.newInstance(viewGroup);
    }
}
