package com.miui.pc.frame;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PcFolderPickerListAdapter extends RecyclerView.Adapter<FolderPickerListItem> {
    public List<FolderItem> mItems;
    private View.OnClickListener mListener;
    private long mSelectedFolderId;

    public PcFolderPickerListAdapter(List<FolderItem> list, long j) {
        ArrayList arrayList = new ArrayList(Arrays.asList(new FolderItem[list.size()]));
        this.mItems = arrayList;
        Collections.copy(arrayList, list);
        Iterator<FolderItem> it = this.mItems.iterator();
        while (it.hasNext()) {
            long folderId = it.next().getFolderId();
            if (folderId == -4097 || folderId == -3 || folderId == -2 || folderId == -5) {
                it.remove();
            }
        }
        this.mSelectedFolderId = j;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FolderPickerListItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        return FolderPickerListItem.newInstance(viewGroup);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FolderPickerListItem folderPickerListItem, int i) {
        folderPickerListItem.bind(getItem(i), this.mSelectedFolderId);
        folderPickerListItem.itemView.setOnClickListener(this.mListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItems.size();
    }

    public void setSelectedFolder(long j) {
        this.mSelectedFolderId = j;
    }

    public FolderItem getItem(int i) {
        return this.mItems.get(i);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mListener = onClickListener;
    }
}
