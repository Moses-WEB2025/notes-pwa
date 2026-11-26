package com.miui.pad.feature.todo.edit;

import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.todo.feature.todolist.SubTodoEditItemVh;

/* JADX INFO: loaded from: classes4.dex */
public class PadSubTodoEditItemVh extends SubTodoEditItemVh {
    public static PadSubTodoEditItemVh newInstance(ViewGroup viewGroup) {
        return new PadSubTodoEditItemVh(UIUtils.inflateView(viewGroup, R.layout.pad_subtodo_edit_item));
    }

    public PadSubTodoEditItemVh(View view) {
        super(view);
    }
}
