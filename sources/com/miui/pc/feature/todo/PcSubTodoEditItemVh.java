package com.miui.pc.feature.todo;

import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.todo.feature.todolist.SubTodoEditItemVh;
import com.miui.todo.utils.TodoUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PcSubTodoEditItemVh extends SubTodoEditItemVh {
    public static SubTodoEditItemVh newInstance(ViewGroup viewGroup) {
        return new PcSubTodoEditItemVh(UIUtils.inflateView(viewGroup, R.layout.pc_subtodo_edit_item));
    }

    public PcSubTodoEditItemVh(View view) {
        super(view);
    }

    @Override // com.miui.todo.feature.todolist.SubTodoEditItemVh
    protected void setContentCheckState(boolean z) {
        if (z) {
            this.mSubTodoEditor.getPaint().setFlags(17);
            this.mSubTodoEditor.setTextColor(TodoUtils.getColor(R.color.pc_todo_item_content_text_disable_color));
        } else {
            this.mSubTodoEditor.getPaint().setFlags(1);
            this.mSubTodoEditor.setTextColor(TodoUtils.getColor(R.color.pc_subtodo_edit_item_text_color));
        }
    }
}
