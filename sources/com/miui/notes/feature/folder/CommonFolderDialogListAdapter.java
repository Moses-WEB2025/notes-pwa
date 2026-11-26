package com.miui.notes.feature.folder;

import android.view.ViewGroup;
import com.miui.notes.basefeature.folder.BaseFolderDialogAdapter;
import com.miui.notes.basefeature.folder.BaseFolderDialogItem;
import com.miui.notes.model.FolderModel;

/* JADX INFO: loaded from: classes4.dex */
public class CommonFolderDialogListAdapter extends BaseFolderDialogAdapter<BaseFolderDialogItem> {
    public static final int VIEW_TYPE_HEADER = 2;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_UNCATEGORIZED = 3;
    private boolean mFromFolderIcon = true;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (i == 0 && this.mFromFolderIcon) {
            return 2;
        }
        return (i == this.mFolderItemEntities.size() - 1 && this.mFolderItemEntities.get(this.mFolderItemEntities.size() - 1).getId() == 0) ? 3 : 1;
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseFolderDialogItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 2) {
            CommonFolderDialogItemHeader commonFolderDialogItemHeaderNewInstance = CommonFolderDialogItemHeader.newInstance(viewGroup);
            commonFolderDialogItemHeaderNewInstance.setOnClickListener(this.mOnClickListener);
            return commonFolderDialogItemHeaderNewInstance;
        }
        if (i == 3) {
            CommonFolderDialogItem commonFolderDialogItemNewInstance = CommonFolderDialogItem.newInstance(viewGroup);
            commonFolderDialogItemNewInstance.setOnClickListener(this.mOnClickListener);
            commonFolderDialogItemNewInstance.setUncategorized(true);
            return commonFolderDialogItemNewInstance;
        }
        CommonFolderDialogItem commonFolderDialogItemNewInstance2 = CommonFolderDialogItem.newInstance(viewGroup);
        commonFolderDialogItemNewInstance2.setOnClickListener(this.mOnClickListener);
        commonFolderDialogItemNewInstance2.setOnLongClickListener(this.mOnLongClickListener);
        return commonFolderDialogItemNewInstance2;
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseFolderDialogItem baseFolderDialogItem, int i) {
        FolderModel item = getItem(i);
        if (item != null) {
            baseFolderDialogItem.bindData(item, this.mBindContext, i, item.getId() == this.mSelectId);
        }
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseFolderDialogItem baseFolderDialogItem) {
        baseFolderDialogItem.onRecycled();
    }

    public String getFolderName(int i) {
        if (this.mFolderItemEntities != null && this.mFolderItemEntities.get(i) != null) {
            return this.mFolderItemEntities.get(i).getSubject();
        }
        return "";
    }

    public void setFromFolderIcon(boolean z) {
        this.mFromFolderIcon = z;
    }
}
