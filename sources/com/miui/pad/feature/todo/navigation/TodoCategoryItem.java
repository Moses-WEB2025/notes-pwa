package com.miui.pad.feature.todo.navigation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class TodoCategoryItem extends RecyclerView.ViewHolder {
    private ImageView mFolderImg;
    private TextView mFolderNum;
    private TextView mFolderTitle;

    public static TodoCategoryItem newInstance(ViewGroup viewGroup) {
        return new TodoCategoryItem(UIUtils.inflateView(viewGroup, R.layout.pad_category_list_item));
    }

    public TodoCategoryItem(View view) {
        super(view);
        Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig[0]);
        this.mFolderTitle = (TextView) view.findViewById(R.id.folder_title);
        this.mFolderImg = (ImageView) view.findViewById(R.id.folder_img);
        this.mFolderNum = (TextView) view.findViewById(R.id.folder_num);
        DisplayUtils.setMiuiMediumTypeFace(this.mFolderTitle);
        DisplayUtils.setMiuiMediumTypeFace(this.mFolderNum);
    }

    protected void bind(int i, int i2, int i3) {
        if (i == 0) {
            this.mFolderTitle.setText(this.itemView.getResources().getString(R.string.todo_item_status_unfinished));
            this.mFolderImg.setBackgroundResource(R.drawable.pad_ic_todo_category_undo);
        } else if (i == 1) {
            this.mFolderTitle.setText(this.itemView.getResources().getString(R.string.todo_item_group_finished));
            this.mFolderImg.setBackgroundResource(R.drawable.pad_ic_todo_category_done);
        }
        if (i3 != -1) {
            this.mFolderNum.setText(i3 + "");
        } else {
            this.mFolderNum.setText("");
        }
        if (i == i2) {
            this.itemView.setBackgroundResource(R.drawable.todo_category_list_item_select_bg);
        } else {
            this.itemView.setBackgroundResource(R.drawable.todo_category_list_item_bg);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }
}
