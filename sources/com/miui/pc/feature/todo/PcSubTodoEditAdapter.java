package com.miui.pc.feature.todo;

import android.content.Context;
import android.view.ViewGroup;
import com.miui.todo.feature.todolist.SubTodoEditAdapter;
import com.miui.todo.feature.todolist.SubTodoEditItemVh;

/* JADX INFO: loaded from: classes4.dex */
public class PcSubTodoEditAdapter extends SubTodoEditAdapter {
    public PcSubTodoEditAdapter(Context context) {
        super(context, false);
    }

    @Override // com.miui.todo.feature.todolist.SubTodoEditAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public SubTodoEditItemVh onCreateViewHolder(ViewGroup viewGroup, int i) {
        return PcSubTodoEditItemVh.newInstance(viewGroup);
    }
}
