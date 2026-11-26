package com.miui.notes.ui.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class SearchFolderHolder extends RecyclerView.ViewHolder {
    private TextView mFolderName;
    private View mItemView;

    public static SearchFolderHolder newInstance(ViewGroup viewGroup) {
        return new SearchFolderHolder(UIUtils.inflateView(viewGroup, R.layout.v12_layout_item_tag));
    }

    public SearchFolderHolder(View view) {
        super(view);
        this.mItemView = view;
        this.mFolderName = (TextView) view.findViewById(R.id.folder_name);
    }

    public void bind(String str) {
        this.mFolderName.setText(str);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mItemView.setOnClickListener(onClickListener);
    }
}
