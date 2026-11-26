package com.miui.pc.frame;

import android.database.Cursor;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.cache.CacheManager;
import com.miui.notes.cache.FolderCache;
import com.miui.notes.model.FolderModel;
import com.miui.notes.store.FolderStore;
import com.miui.pc.model.PadFolderModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PcFolderListAdapter extends RecyclerView.Adapter<FolderListItem> implements ExpandableInterface {
    public static final long INVALID_FOLDER_ID = Long.MIN_VALUE;
    private static final int VIEW_TYPE_FOLDER = 0;
    private Cursor mCursor;
    private FolderOperationCallBack mFolderOperationCallBack;
    private View.OnClickListener mOnClickListener;
    private long mSelectedFolderId;
    private int mTotalNotesCount;
    private long mEditFolderId = Long.MIN_VALUE;
    private FolderListItem mEditVh = null;
    public List<FolderItem> mItems = new ArrayList();

    public interface FolderOperationCallBack {
        void changeFolder(long j, long j2);

        boolean nameFolder(long j, String str, String str2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return 0;
    }

    public PcFolderListAdapter() {
        setHasStableIds(true);
    }

    public void setCursor(Cursor cursor) {
        this.mCursor = cursor;
        updateFolders(cursor);
        addFolder(0, createAllFolder(getAllNoteCount()));
    }

    private void updateFolders(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        this.mTotalNotesCount = 0;
        cursor.moveToPosition(-1);
        FolderCache folderCache = null;
        FolderCache folderCache2 = null;
        FolderCache folderCache3 = null;
        FolderCache folderCache4 = null;
        FolderCache folderCache5 = null;
        FolderCache folderCache6 = null;
        FolderCache folderCache7 = null;
        while (cursor.moveToNext()) {
            FolderModel folderModelValueOf = FolderStore.valueOf(cursor);
            FolderCache folder = CacheManager.getDefault().getFolder(folderModelValueOf);
            this.mTotalNotesCount += folder.getFolder().getCount();
            long id = folderModelValueOf.getId();
            if (id == 0) {
                folderCache = folder;
            } else if (id == -2) {
                folderCache2 = folder;
            } else if (id == -5) {
                folderCache3 = folder;
            } else if (id == -3) {
                folderCache7 = folder;
            } else if (id == -6) {
                folderCache4 = folder;
            } else if (id == -7) {
                folderCache5 = folder;
            } else if (id == -8) {
                folderCache6 = folder;
            } else {
                arrayList.add(folder);
            }
        }
        clearFolders();
        addFolder(folderCache);
        if (folderCache2 != null && folderCache2.getFolder().getCount() > 0) {
            addFolder(folderCache2);
        }
        if (folderCache3 != null && folderCache3.getFolder().getCount() > 0) {
            addFolder(folderCache3);
        }
        if (folderCache4 != null && folderCache4.getFolder().getCount() > 0) {
            addFolder(folderCache4);
        }
        if (folderCache5 != null && folderCache5.getFolder().getCount() > 0) {
            addFolder(folderCache5);
        }
        if (folderCache6 != null && folderCache6.getFolder().getCount() > 0) {
            addFolder(folderCache6);
        }
        if (!arrayList.isEmpty()) {
            addFolders(arrayList);
        }
        addFolder(folderCache7);
    }

    protected int getAllNoteCount() {
        return this.mTotalNotesCount;
    }

    protected void addFolder(FolderCache folderCache) {
        this.mItems.add(new FolderItem(folderCache));
    }

    protected void addFolder(int i, FolderCache folderCache) {
        this.mItems.add(i, new FolderItem(folderCache));
    }

    protected void addFolders(List<FolderCache> list) {
        Iterator<FolderCache> it = list.iterator();
        while (it.hasNext()) {
            addFolder(it.next());
        }
    }

    protected void clearFolders() {
        this.mItems.clear();
    }

    public void setOnClickListener(final View.OnClickListener onClickListener) {
        this.mOnClickListener = new View.OnClickListener() { // from class: com.miui.pc.frame.PcFolderListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.requestFocus();
                onClickListener.onClick(view);
            }
        };
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        if (i == -1) {
            return -1L;
        }
        return this.mItems.get(i).getFolderId();
    }

    public FolderItem getItem(int i) {
        if (i == -1) {
            return null;
        }
        return this.mItems.get(i);
    }

    public List<FolderItem> getItems() {
        return this.mItems;
    }

    public FolderCache getFolderCache(int i) {
        if (i == -1) {
            return null;
        }
        return this.mItems.get(i).getFolderCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FolderListItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        FolderListItem folderListItemNewInstance = FolderListItem.newInstance(viewGroup);
        folderListItemNewInstance.setOnClickListener(this.mOnClickListener);
        folderListItemNewInstance.itemView.setOnDragListener(new DragListener(null));
        return folderListItemNewInstance;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FolderListItem folderListItem, int i) {
        folderListItem.bind(getItem(i), this.mFolderOperationCallBack, this.mSelectedFolderId, getEditFolderId());
        folderListItem.itemView.setTag(Integer.valueOf(i));
        if (getEditFolderId() == i) {
            this.mEditVh = folderListItem;
        }
    }

    @Override // com.miui.pc.frame.ExpandableInterface
    public int measureItemHeight(miuix.recyclerview.widget.RecyclerView recyclerView) {
        FolderListItem folderListItemNewInstance = FolderListItem.newInstance(recyclerView);
        folderListItemNewInstance.bind(getItem(0), null, -1L, Long.MIN_VALUE);
        recyclerView.getLayoutManager().measureChild(folderListItemNewInstance.itemView, 0, 0);
        return folderListItemNewInstance.itemView.getMeasuredHeight();
    }

    public class DragListener implements View.OnDragListener {
        boolean isDropped = false;
        NotesListAdapter.Listener mListener;

        public DragListener(NotesListAdapter.Listener listener) {
            this.mListener = listener;
        }

        @Override // android.view.View.OnDragListener
        public boolean onDrag(View view, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            long itemId = PcFolderListAdapter.this.getItemId(((Integer) view.getTag()).intValue());
            if (action != 3) {
                if (action != 5) {
                    if (action == 6) {
                        view.setBackground(null);
                    }
                } else if (itemId != -2 && itemId != -5) {
                    view.setBackground(view.getContext().getDrawable(R.drawable.pc_draged_folder_background));
                }
            } else if (dragEvent.getLocalState() instanceof View) {
                View view2 = (View) dragEvent.getLocalState();
                long itemId2 = ((NotesListAdapter) ((miuix.recyclerview.widget.RecyclerView) view2.getParent()).getAdapter()).getItemId(((Integer) view2.getTag()).intValue());
                if (PcFolderListAdapter.this.mFolderOperationCallBack != null && itemId != -2 && itemId != -5) {
                    PcFolderListAdapter.this.mFolderOperationCallBack.changeFolder(itemId2, PcFolderListAdapter.this.getItemId(((Integer) view.getTag()).intValue()));
                }
            }
            if (this.isDropped || !(dragEvent.getLocalState() instanceof View)) {
                return true;
            }
            ((View) dragEvent.getLocalState()).setVisibility(0);
            return true;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItems.size();
    }

    private FolderCache createAllFolder(int i) {
        PadFolderModel padFolderModel = new PadFolderModel();
        padFolderModel.setId(-4097L);
        padFolderModel.setCount(i);
        return CacheManager.getDefault().getFolder(padFolderModel);
    }

    public void setSelectedFolderId(long j) {
        this.mSelectedFolderId = j;
        notifyDataSetChanged();
    }

    public long getSelectedFolderId() {
        return this.mSelectedFolderId;
    }

    public void clearSelectedType() {
        this.mSelectedFolderId = Long.MIN_VALUE;
        notifyDataSetChanged();
    }

    public boolean isFolderSelected() {
        return this.mSelectedFolderId != Long.MIN_VALUE;
    }

    public FolderItem getItemByFolderId(long j) {
        List<FolderItem> list = this.mItems;
        if (list != null && list.size() != 0) {
            for (FolderItem folderItem : this.mItems) {
                if (folderItem.getFolderId() == j) {
                    return folderItem;
                }
            }
        }
        return null;
    }

    public int getPosByFolderId(long j) {
        List<FolderItem> list = this.mItems;
        if (list != null && list.size() != 0) {
            for (FolderItem folderItem : this.mItems) {
                if (folderItem.getFolderId() == j) {
                    return this.mItems.indexOf(folderItem);
                }
            }
        }
        return -1;
    }

    public void setFolderCallBack(FolderOperationCallBack folderOperationCallBack) {
        this.mFolderOperationCallBack = folderOperationCallBack;
    }

    public void setEditableFolder(long j) {
        this.mEditFolderId = j;
        if (j == Long.MIN_VALUE) {
            resetEditItem();
        }
    }

    public long getEditFolderId() {
        return this.mEditFolderId;
    }

    public FolderListItem getEditItem() {
        return this.mEditVh;
    }

    private void resetEditItem() {
        this.mEditVh = null;
    }

    public boolean isFolderExist(String str) {
        if (str.equals(NoteApp.getInstance().getString(R.string.call_folder_name)) || str.equals(NoteApp.getInstance().getString(R.string.hear_note))) {
            return true;
        }
        Iterator<FolderItem> it = this.mItems.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().getFolderName())) {
                return true;
            }
        }
        return false;
    }
}
