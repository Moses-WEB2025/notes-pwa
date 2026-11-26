package com.miui.pc.frame;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class FolderPickerListItem extends RecyclerView.ViewHolder {
    protected ImageView mIcon;
    protected TextView mTitle;

    public static FolderPickerListItem newInstance(ViewGroup viewGroup) {
        return new FolderPickerListItem(UIUtils.inflateView(viewGroup, R.layout.pc_folder_picker_list_item));
    }

    protected FolderPickerListItem(View view) {
        super(view);
        this.mTitle = (TextView) view.findViewById(R.id.title);
        this.mIcon = (ImageView) view.findViewById(R.id.selected_icon);
    }

    protected void bind(FolderItem folderItem, long j) {
        this.mTitle.setText(folderItem.getFolderName());
        if (folderItem.getFolderId() == j) {
            this.mIcon.setVisibility(0);
        } else {
            this.mIcon.setVisibility(4);
        }
    }
}
