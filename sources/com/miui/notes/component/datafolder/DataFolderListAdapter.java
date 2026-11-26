package com.miui.notes.component.datafolder;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.stat.GlobalStat;
import com.miui.common.stat.NotesPageStat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DataFolderListAdapter extends RecyclerView.Adapter<FolderItem> {
    protected FolderClickCallBack mFolderClickCallBack;
    protected List<FolderItemEntity> mFolderItemEntities;
    protected long mSelectedId = -1;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FolderItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FolderItem(UIUtils.inflateView(viewGroup, R.layout.data_folder_list_item));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final FolderItem folderItem, int i) {
        final FolderItemEntity folderItemEntity = this.mFolderItemEntities.get(i);
        boolean z = this.mSelectedId == folderItemEntity.getFolderId();
        folderItem.itemView.setSelected(z);
        folderItem.bindFolder(folderItemEntity, z);
        folderItem.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.component.datafolder.DataFolderListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DataFolderListAdapter.this.mFolderClickCallBack.openFolder(folderItem.itemView, folderItemEntity.getFolderId(), folderItemEntity.getFolderName(), false);
                NotesPageStat.reportClickFolderEvent(folderItemEntity.getFolderName());
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<FolderItemEntity> list = this.mFolderItemEntities;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setFolderItemEntities(List<FolderItemEntity> list) {
        this.mFolderItemEntities = list;
        GlobalStat.Status.folderList = list;
    }

    public void setFolderClick(FolderClickCallBack folderClickCallBack) {
        this.mFolderClickCallBack = folderClickCallBack;
    }

    public void refreshSelectedFolder(long j, final RecyclerView recyclerView, LinearLayoutManager linearLayoutManager, boolean z) {
        final int iFindFolderPos = findFolderPos(j);
        this.mSelectedId = j;
        if (iFindFolderPos == -1 || linearLayoutManager == null) {
            return;
        }
        notifyDataSetChanged();
        if (!z || recyclerView == null) {
            return;
        }
        recyclerView.post(new Runnable() { // from class: com.miui.notes.component.datafolder.DataFolderListAdapter.2
            @Override // java.lang.Runnable
            public void run() {
                RecyclerView recyclerView2 = recyclerView;
                if (recyclerView2 != null) {
                    recyclerView2.smoothScrollToPosition(iFindFolderPos);
                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(iFindFolderPos);
                    if (viewHolderFindViewHolderForAdapterPosition == null || viewHolderFindViewHolderForAdapterPosition.itemView == null) {
                        return;
                    }
                    viewHolderFindViewHolderForAdapterPosition.itemView.sendAccessibilityEvent(8);
                    viewHolderFindViewHolderForAdapterPosition.itemView.requestFocus();
                }
            }
        });
    }

    public int findFolderPos(long j) {
        List<FolderItemEntity> list = this.mFolderItemEntities;
        if (list == null || list.isEmpty()) {
            return -1;
        }
        for (FolderItemEntity folderItemEntity : this.mFolderItemEntities) {
            if (folderItemEntity.getFolderId() == j) {
                return this.mFolderItemEntities.indexOf(folderItemEntity);
            }
        }
        return -1;
    }

    public void refreshSelectStatus(long j) {
        this.mSelectedId = j;
        notifyDataSetChanged();
    }
}
