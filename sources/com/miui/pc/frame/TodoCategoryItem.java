package com.miui.pc.frame;

import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class TodoCategoryItem extends FolderListItem {
    private int mPcTodoListType;

    public static TodoCategoryItem newInstance(ViewGroup viewGroup) {
        return new TodoCategoryItem(UIUtils.inflateView(viewGroup, R.layout.pc_list_item));
    }

    public TodoCategoryItem(View view) {
        super(view);
        this.mPcTodoListType = -1;
    }

    protected void bind(int i, int i2) {
        this.mPcTodoListType = i;
        if (i == 0) {
            this.mTitle.setText(this.itemView.getResources().getString(R.string.todo_item_status_unfinished));
        } else if (i == 1) {
            this.mTitle.setText(this.itemView.getResources().getString(R.string.todo_item_group_finished));
        }
        if (i == i2) {
            this.itemView.setBackgroundResource(R.drawable.pc_selected_folder_background);
        } else {
            this.itemView.setBackground(null);
        }
    }

    public int getPcTodoListCategory() {
        return this.mPcTodoListType;
    }
}
