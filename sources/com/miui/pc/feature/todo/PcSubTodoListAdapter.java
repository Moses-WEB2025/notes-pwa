package com.miui.pc.feature.todo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.miui.todo.feature.todolist.SubTodoItemVh;
import com.miui.todo.feature.todolist.SubTodoListAdapter;

/* JADX INFO: loaded from: classes4.dex */
public class PcSubTodoListAdapter extends SubTodoListAdapter {
    public PcSubTodoListAdapter(Context context, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        super(context, onClickListener, onLongClickListener);
    }

    @Override // com.miui.todo.feature.todolist.SubTodoListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public SubTodoItemVh onCreateViewHolder(ViewGroup viewGroup, int i) {
        return PcSubTodoItemVh.newInstance(viewGroup);
    }
}
