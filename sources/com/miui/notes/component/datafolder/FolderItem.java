package com.miui.notes.component.datafolder;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class FolderItem extends RecyclerView.ViewHolder {
    private TextView mFolderName;
    private LinearLayout mLayout;
    private ImageView mStickImg;
    private int mTextColorNormal;
    private int mTextColorSelect;

    public FolderItem(View view) {
        super(view);
        this.mTextColorNormal = 0;
        this.mTextColorSelect = 0;
        this.mLayout = (LinearLayout) view.findViewById(R.id.data_folder_list_layout);
        this.mFolderName = (TextView) view.findViewById(R.id.pad_folder_name);
        this.mStickImg = (ImageView) view.findViewById(R.id.pad_folder_stick);
        this.mTextColorNormal = view.getContext().getColor(R.color.data_folder_list_item_text_color_n);
        this.mTextColorSelect = view.getContext().getColor(R.color.data_folder_list_item_text_color_s);
    }

    public void bindFolder(FolderItemEntity folderItemEntity, boolean z) {
        TextView textView = this.mFolderName;
        if (textView != null) {
            textView.setText(folderItemEntity.getFolderName());
        }
        if (folderItemEntity.isStick()) {
            this.mStickImg.setVisibility(0);
        } else {
            this.mStickImg.setVisibility(8);
        }
        if (z) {
            selectFolderListItemBg();
        } else {
            defFolderListItemBg();
        }
    }

    private void defFolderListItemBg() {
        TextView textView = this.mFolderName;
        if (textView != null) {
            textView.setTextColor(this.mTextColorNormal);
            DisplayUtils.setMiuiRegularTypeFace(this.mFolderName);
        }
        LinearLayout linearLayout = this.mLayout;
        if (linearLayout != null) {
            linearLayout.setBackgroundResource(R.drawable.data_folder_list_item_bg);
            if (UIUtils.isHyperOsAndAbove()) {
                return;
            }
            ((GradientDrawable) this.mLayout.getBackground()).setColor(this.mLayout.getResources().getColor(R.color.v12_theme_content_bg_color));
        }
    }

    private void selectFolderListItemBg() {
        TextView textView = this.mFolderName;
        if (textView != null) {
            textView.setTextColor(this.mTextColorSelect);
            if (UIUtils.isHyperOsAndAbove()) {
                DisplayUtils.setMiuiDemiboldTypeFace(this.mFolderName);
            }
        }
        LinearLayout linearLayout = this.mLayout;
        if (linearLayout != null) {
            linearLayout.setBackgroundResource(R.drawable.data_folder_list_item_select_bg);
            if (UIUtils.isHyperOsAndAbove()) {
                return;
            }
            ((GradientDrawable) this.mLayout.getBackground()).setColor(this.mLayout.getResources().getColor(R.color.data_folder_item_select_bg_color));
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }
}
