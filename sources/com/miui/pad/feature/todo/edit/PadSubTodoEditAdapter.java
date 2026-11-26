package com.miui.pad.feature.todo.edit;

import android.content.Context;
import android.view.ViewGroup;
import com.miui.todo.feature.todolist.SubTodoEditAdapter;
import com.miui.todo.feature.todolist.SubTodoEditItemVh;

/* JADX INFO: loaded from: classes4.dex */
public class PadSubTodoEditAdapter extends SubTodoEditAdapter {
    public PadSubTodoEditAdapter(Context context, boolean z) {
        super(context, z);
    }

    @Override // com.miui.todo.feature.todolist.SubTodoEditAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public SubTodoEditItemVh onCreateViewHolder(ViewGroup viewGroup, int i) {
        return PadSubTodoEditItemVh.newInstance(viewGroup);
    }
}
