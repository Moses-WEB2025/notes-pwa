package com.miui.notes.basefeature.notelist;

import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.adapter.SearchCursorAdapter;
import com.miui.notes.cache.CacheManager;
import com.miui.notes.cache.FolderCache;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.component.BaseItem;
import com.miui.notes.feature.notelist.AiQueryProcess;
import com.miui.notes.feature.notelist.CommonNoteGridItem;
import com.miui.notes.feature.notelist.CommonNoteListItem;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.NoteModel;
import com.miui.notes.store.ItemStore;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.schema.NoteSchema;

/* JADX INFO: loaded from: classes4.dex */
public class BaseNoteListAdapter extends RecyclerView.Adapter<BaseItem> {
    private static final String TAG = "BasePhoneListAdapter";
    public static final int VIEW_TYPE_CALL_GRID = 5;
    public static final int VIEW_TYPE_CALL_LIST = 4;
    public static final int VIEW_TYPE_GRID = 3;
    public static final int VIEW_TYPE_LIST = 2;
    public static final int VIEW_TYPE_SHOW_PROCESS = 6;
    protected boolean isPrivateOrAiSearch;
    protected BindContext mBindContext;
    protected Cursor mCursor;
    protected boolean mGridMode;
    protected View.OnClickListener mOnClickListener;
    protected View.OnLongClickListener mOnLongClickListener;
    protected SearchCursorAdapter mSearchCursorAdapter;
    private long mSelectId;
    protected boolean mTrashMode;
    protected long mLastNotifyChangeTime = 0;
    protected long mLastManualModifyDataTime = 0;
    private boolean mIsSingleScreen = false;
    private boolean mFrozeData = false;
    boolean isRecordForFirst = false;

    public BaseNoteListAdapter(boolean z) {
        setHasStableIds(true);
        initBindResource();
        this.isPrivateOrAiSearch = z;
    }

    private void initBindResource() {
        AsyncTask.execute(new BindResRunnable());
    }

    public void setCursor(Cursor cursor) {
        Cursor cursor2 = this.mCursor;
        if (cursor2 != cursor) {
            boolean zIsCursorContentChanged = isCursorContentChanged(cursor2, cursor);
            if (this.mCursor != null) {
                Logger.INSTANCE.d(TAG, "close old cursor " + this.mCursor.hashCode());
                this.mCursor.close();
            }
            this.mCursor = cursor;
            if (this.mFrozeData || !zIsCursorContentChanged) {
                return;
            }
            notifyDataSetChangedWithLock(true);
        }
    }

    private boolean isCursorContentChanged(Cursor cursor, Cursor cursor2) {
        if (!isCursorValid(cursor) || !isCursorValid(cursor2) || cursor.getCount() != cursor2.getCount()) {
            return true;
        }
        int count = cursor.getCount();
        for (int i = 0; i < count; i++) {
            if (!cursor.moveToPosition(i) || !cursor2.moveToPosition(i) || isRowChanged(cursor, cursor2)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCursorValid(Cursor cursor) {
        return (cursor == null || cursor.isClosed()) ? false : true;
    }

    private boolean isRowChanged(Cursor cursor, Cursor cursor2) {
        return (cursor.getLong(0) == cursor2.getLong(0) && cursor.getLong(9) == cursor2.getLong(9)) ? false : true;
    }

    public void setCursor(SearchCursorAdapter searchCursorAdapter) {
        SearchCursorAdapter searchCursorAdapter2 = this.mSearchCursorAdapter;
        if (searchCursorAdapter2 != searchCursorAdapter) {
            if (searchCursorAdapter2 != null && searchCursorAdapter != null && searchCursorAdapter2.getCursor() != searchCursorAdapter.getCursor()) {
                this.mSearchCursorAdapter.getCursor().close();
                Logger.INSTANCE.e(TAG, "previous cursor close");
            }
            this.mSearchCursorAdapter = searchCursorAdapter;
            if (this.mFrozeData) {
                return;
            }
            notifyDataSetChangedWithLock(false);
        }
    }

    public void setAiSearch(boolean z) {
        this.isPrivateOrAiSearch = z;
    }

    public void markManualModifyDataTime() {
        this.mLastManualModifyDataTime = System.currentTimeMillis();
    }

    public void resetDataSetChangedTime() {
        this.mLastManualModifyDataTime = 0L;
        this.mLastNotifyChangeTime = 0L;
    }

    public void notifyDataSetChangedWithLock(boolean z) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (z) {
            long j = this.mLastNotifyChangeTime;
            if (jCurrentTimeMillis - j > 400) {
                this.mLastNotifyChangeTime = jCurrentTimeMillis;
                notifyDataSetChanged();
                return;
            } else {
                if (j < this.mLastManualModifyDataTime) {
                    this.mLastNotifyChangeTime = jCurrentTimeMillis;
                    notifyDataSetChanged();
                    return;
                }
                return;
            }
        }
        this.mLastNotifyChangeTime = jCurrentTimeMillis;
        notifyDataSetChanged();
    }

    public void frozeData(boolean z) {
        this.mFrozeData = z;
    }

    public void setGridMode(boolean z) {
        this.mGridMode = z;
    }

    public boolean isGridMode() {
        return this.mGridMode;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mOnLongClickListener = onLongClickListener;
    }

    public void setBindContext(BindContext bindContext) {
        this.mBindContext = bindContext;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        long itemFoldIdFromCursor = getItemFoldIdFromCursor(i);
        long itemId = getItemId(i);
        int realItemCount = getRealItemCount();
        BindContext bindContext = this.mBindContext;
        if (bindContext != null && bindContext.isValidAiQueryDBProgress() && realItemCount > 0 && realItemCount == i) {
            return 6;
        }
        if (this.mGridMode) {
            return NoteModel.isCallNote(itemFoldIdFromCursor, itemId) ? 5 : 3;
        }
        return NoteModel.isCallNote(itemFoldIdFromCursor, itemId) ? 4 : 2;
    }

    public ItemCache getItem(int i) {
        Cursor cursor = getCursor(i);
        if (cursor != null) {
            return getItemCacheFromCursor(cursor, i);
        }
        return null;
    }

    private Cursor getCursor(int i) {
        SearchCursorAdapter searchCursorAdapter;
        Cursor cursor;
        if ((this.isPrivateOrAiSearch || (cursor = this.mCursor) == null || cursor.isClosed() || !this.mCursor.moveToPosition(i)) && !((searchCursorAdapter = this.mSearchCursorAdapter) != null && this.isPrivateOrAiSearch && searchCursorAdapter.moveToPosition(i))) {
            return null;
        }
        return this.isPrivateOrAiSearch ? this.mSearchCursorAdapter.getCursor() : this.mCursor;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        long id;
        Cursor cursor;
        if (i < 0 || i >= getItemCount()) {
            Logger.INSTANCE.w(TAG, "getItemId: invalid position " + i + ", itemCount: " + getItemCount());
            return -1L;
        }
        try {
            if (!this.isPrivateOrAiSearch && (cursor = this.mCursor) != null && !cursor.isClosed() && this.mCursor.moveToPosition(i)) {
                try {
                    return ItemStore.getId(this.mCursor);
                } catch (Exception e) {
                    Logger.INSTANCE.e(TAG, "error in getItemId " + e);
                    return -1L;
                }
            }
            if (!this.isPrivateOrAiSearch || !this.mSearchCursorAdapter.moveToPosition(i)) {
                return -1L;
            }
            try {
                id = ItemStore.getId(this.mSearchCursorAdapter.getCursor());
                try {
                    Logger.INSTANCE.v(TAG, "getItemId: " + id + " position: " + i);
                    return id;
                } catch (Exception e2) {
                    e = e2;
                    Logger.INSTANCE.e(TAG, "error in getItemId " + e);
                    return id;
                }
            } catch (Exception e3) {
                e = e3;
                id = -1;
            }
        } catch (Exception e4) {
            Logger.INSTANCE.e(TAG, "getItemId error position: " + i + " exception: " + e4);
            return -1L;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        BaseItem baseItemNewInstance;
        if (i == 2) {
            baseItemNewInstance = CommonNoteListItem.newInstance(viewGroup, false);
            baseItemNewInstance.setTrash(this.mTrashMode);
        } else if (i == 3) {
            baseItemNewInstance = CommonNoteGridItem.newInstance(viewGroup, false);
        } else if (i == 4) {
            baseItemNewInstance = CommonNoteListItem.newInstance(viewGroup, true);
            baseItemNewInstance.setTrash(this.mTrashMode);
        } else if (i == 5) {
            baseItemNewInstance = CommonNoteGridItem.newInstance(viewGroup, true);
        } else if (i == 6) {
            baseItemNewInstance = AiQueryProcess.newInstance(viewGroup);
        } else {
            Logger.INSTANCE.w(TAG, "Unknown view type: " + i);
            baseItemNewInstance = null;
        }
        if (baseItemNewInstance != null && i != 6) {
            baseItemNewInstance.setOnClickListener(this.mOnClickListener);
            baseItemNewInstance.setOnLongClickListener(this.mOnLongClickListener);
        }
        return baseItemNewInstance;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(BaseItem baseItem) {
        super.onViewAttachedToWindow(baseItem);
        baseItem.onViewAttachedToWindow();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final BaseItem baseItem, int i) {
        BindContext bindContext;
        if (i < 0 || i >= getItemCount()) {
            Logger.INSTANCE.w(TAG, "onBindViewHolder: invalid position " + i + ", itemCount: " + getItemCount());
            return;
        }
        ItemCache item = getItem(i);
        baseItem.itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.miui.notes.basefeature.notelist.BaseNoteListAdapter.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                baseItem.onViewAttachedToWindow();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                baseItem.onViewDetachedFromWindow();
            }
        });
        if (item == null && (bindContext = this.mBindContext) != null && bindContext.isValidAiQueryDBProgress()) {
            baseItem.bind(null, this.mBindContext, i);
        } else if (item != null) {
            baseItem.bind(getItem(i), this.mBindContext, i, ((NoteCache) item.getCacheObject()).getNote().getId() == this.mSelectId && !isSingleScreen());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        BindContext bindContext = this.mBindContext;
        if (bindContext != null && bindContext.isValidAiQueryDBProgress()) {
            return getRealItemCount() + 1;
        }
        return getRealItemCount();
    }

    public int getRealItemCount() {
        int count = 0;
        if (this.isPrivateOrAiSearch) {
            SearchCursorAdapter searchCursorAdapter = this.mSearchCursorAdapter;
            if (searchCursorAdapter != null) {
                count = searchCursorAdapter.getCount();
            }
        } else {
            Cursor cursor = this.mCursor;
            if (cursor != null && !cursor.isClosed()) {
                count = this.mCursor.getCount();
            }
        }
        int noteNumLimit = NoteApp.getInstance().getNoteNumLimit();
        return count > noteNumLimit ? noteNumLimit : count;
    }

    private ItemCache getItemCacheFromCursor(Cursor cursor, int i) {
        if (cursor.isClosed() || i >= cursor.getCount()) {
            return null;
        }
        int type = ItemStore.getType(cursor);
        long id = ItemStore.getId(cursor);
        if (type == 1 || type == 2) {
            FolderCache folder = CacheManager.getDefault().getFolder(id);
            FolderModel folder2 = ItemStore.parseFolder(cursor);
            if (folder == null) {
                folder = CacheManager.getDefault().getFolder(folder2);
            } else {
                folder.update(folder2);
            }
            return new ItemCache(1, folder);
        }
        if (type != 0) {
            return null;
        }
        NoteCache note = CacheManager.getDefault().getNote(id);
        NoteModel note2 = ItemStore.parseNote(cursor);
        if (note == null) {
            note = CacheManager.getDefault().getNote(note2);
        } else {
            note.update(note2);
        }
        return new ItemCache(0, note);
    }

    public long getItemStickFromCursor(int i) {
        Cursor cursor = getCursor(i);
        if (cursor == null || cursor.isClosed() || i >= cursor.getCount()) {
            return -1L;
        }
        return ItemStore.getStick(cursor);
    }

    public long getItemFoldIdFromCursor(int i) {
        Cursor cursor = getCursor(i);
        if (cursor == null || cursor.isClosed() || i >= cursor.getCount()) {
            return 0L;
        }
        return ItemStore.getFoldId(cursor);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseItem baseItem) {
        baseItem.onRecycled();
    }

    private static class BindResRunnable implements Runnable {
        private BindResRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Logger.INSTANCE.d(BaseNoteListAdapter.TAG, "BindResRunable Aync Task run...");
            Thread.currentThread().setName("BindResRunnable");
            new NoteSchema.HTMLSchemaHolder();
            ResourceManager.getTheme(-1);
        }
    }

    public void setNewNoteId(long j) {
        this.mBindContext.setNewNoteId(j);
    }

    public long getNewNoteId() {
        BindContext bindContext = this.mBindContext;
        if (bindContext == null) {
            return 0L;
        }
        return bindContext.getNewNoteId();
    }

    public void setSelectId(long j) {
        this.mSelectId = j;
        if (j != this.mBindContext.getNewNoteId()) {
            this.mBindContext.setNewNoteId(-1L);
        }
    }

    public void notifyAllItem() {
        notifyDataSetChanged();
    }

    public long getSelectId() {
        return this.mSelectId;
    }

    public void notifySelectedItem() {
        int searchCursorPositionById = this.isPrivateOrAiSearch ? getSearchCursorPositionById(this.mSelectId) : getPositionById(this.mSelectId);
        if (searchCursorPositionById == -1 || searchCursorPositionById >= getItemCount()) {
            return;
        }
        notifyItemChanged(searchCursorPositionById);
    }

    public int getPositionById(long j) {
        try {
            if (this.mCursor != null) {
                for (int i = 0; i < this.mCursor.getCount() && this.mCursor.moveToPosition(i); i++) {
                    if (j == ItemStore.getId(this.mCursor)) {
                        return i;
                    }
                }
                return -1;
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "error in " + e.toString());
        }
        return -1;
    }

    public int getSearchCursorPositionById(long j) {
        try {
            SearchCursorAdapter searchCursorAdapter = this.mSearchCursorAdapter;
            if (searchCursorAdapter != null && searchCursorAdapter.getCursor() != null) {
                for (int i = 0; i < this.mSearchCursorAdapter.getCursor().getCount() && this.mSearchCursorAdapter.getCursor().moveToPosition(i); i++) {
                    if (j == ItemStore.getId(this.mSearchCursorAdapter.getCursor())) {
                        return i;
                    }
                }
                return -1;
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "error in " + e.toString());
        }
        return -1;
    }

    public void setSingleScreen(boolean z) {
        this.mIsSingleScreen = z;
    }

    public boolean isSingleScreen() {
        if (RomUtils.isPadMode()) {
            return this.mIsSingleScreen;
        }
        return true;
    }

    public void setTrashMode(boolean z) {
        this.mTrashMode = z;
    }
}
