package com.miui.pc.feature.todo;

import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.todo.feature.todolist.SubTodoItemVh;
import com.miui.todo.utils.TodoUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PcSubTodoItemVh extends SubTodoItemVh {
    public static SubTodoItemVh newInstance(ViewGroup viewGroup) {
        return new PcSubTodoItemVh(UIUtils.inflateView(viewGroup, R.layout.pc_subtodo_item));
    }

    public PcSubTodoItemVh(View view) {
        super(view);
    }

    @Override // com.miui.todo.feature.todolist.SubTodoItemVh
    protected CharSequence getHighLightText(String str, String str2) {
        return ContentUtils.changePcHighLight(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.todo.feature.todolist.SubTodoItemVh
    public void setContentCheckState(boolean z) {
        if (z) {
            this.mTvContent.setTextColor(TodoUtils.getColor(R.color.pc_todo_item_content_text_disable_color));
        } else {
            this.mTvContent.setTextColor(TodoUtils.getColor(R.color.pc_todo_item_content_text_common_color));
        }
    }
}
