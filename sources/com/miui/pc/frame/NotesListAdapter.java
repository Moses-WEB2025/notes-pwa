package com.miui.pc.frame;

import android.content.ClipData;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.adapter.SearchCursorAdapter;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cache.CacheManager;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.model.NoteModel;
import com.miui.notes.store.NoteStore;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.pc.component.PcNoteListItem;
import com.miui.richeditor.schema.NoteSchema;

/* JADX INFO: loaded from: classes4.dex */
public class NotesListAdapter extends RecyclerView.Adapter<PcNoteListItem> {
    private static final String TAG = "PhoneListAdapter";
    public static final int VIEW_TYPE_CALL_LIST = 1;
    public static final int VIEW_TYPE_LIST = 0;
    public static final int VIEW_TYPE_MIND_LIST = 2;
    private final boolean isPrivateSearch;
    private BindContext mBindContext;
    private long mCurrentNoteId = -1;
    private Cursor mCursor;
    private View.OnClickListener mOnClickListener;
    private View.OnLongClickListener mOnLongClickListener;
    private SearchCursorAdapter mSearchCursorAdapter;

    public interface Listener {
        void setEmptyList(boolean z);
    }

    public long getCurrentNoteId() {
        return this.mCurrentNoteId;
    }

    public NotesListAdapter(boolean z) {
        setHasStableIds(true);
        initBindResource();
        this.isPrivateSearch = z;
    }

    private void initBindResource() {
        Logger.INSTANCE.d(TAG, "initBindResource");
        AsyncTask.execute(new Runnable() { // from class: com.miui.pc.frame.NotesListAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotesListAdapter.lambda$initBindResource$0();
            }
        });
    }

    static /* synthetic */ void lambda$initBindResource$0() {
        Logger.INSTANCE.d(TAG, "initBindResource/task noteschema");
        Thread.currentThread().setName("initBindResource");
        new NoteSchema.HTMLSchemaHolder();
        ResourceManager.getTheme(-1);
    }

    public void setCursor(Cursor cursor) {
        if (this.mCursor != cursor) {
            this.mCursor = cursor;
            notifyDataSetChanged();
        }
    }

    public void setCursor(SearchCursorAdapter searchCursorAdapter) {
        if (this.mSearchCursorAdapter != searchCursorAdapter) {
            this.mSearchCursorAdapter = searchCursorAdapter;
            notifyDataSetChanged();
        }
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
    public long getItemId(int i) {
        if (!this.isPrivateSearch && this.mCursor.moveToPosition(i)) {
            return NoteStore.getId(this.mCursor);
        }
        if (this.isPrivateSearch && this.mSearchCursorAdapter.moveToPosition(i)) {
            return NoteStore.getId(this.mSearchCursorAdapter.getCursor());
        }
        return -1L;
    }

    public String getItemNoteType(int i) {
        if (!this.isPrivateSearch && this.mCursor.moveToPosition(i)) {
            return NoteStore.getNoteType(this.mCursor);
        }
        return "common";
    }

    public long getItemModifiedTime(int i) {
        if (this.isPrivateSearch || !this.mCursor.moveToPosition(i)) {
            return Long.MIN_VALUE;
        }
        return NoteStore.getModifiedTime(this.mCursor);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (getItem(i) == null) {
            Logger.INSTANCE.w(TAG, "getItemViewType getItem = null");
            return 0;
        }
        NoteCache noteCache = (NoteCache) getItem(i).getCacheObject();
        if (noteCache.getNote().isCallNote()) {
            return 1;
        }
        return noteCache.getNote().getNoteType().equals(NoteModel.NoteType.TYPE_MIND) ? 2 : 0;
    }

    public ItemCache getItem(int i) {
        if ((this.isPrivateSearch || !this.mCursor.moveToPosition(i)) && !(this.isPrivateSearch && this.mSearchCursorAdapter.moveToPosition(i))) {
            return null;
        }
        Cursor cursor = this.isPrivateSearch ? this.mSearchCursorAdapter.getCursor() : this.mCursor;
        NoteCache note = CacheManager.getDefault().getNote(NoteStore.getId(cursor));
        NoteModel noteModel = NoteStore.parse(cursor);
        if (note == null) {
            note = CacheManager.getDefault().getNote(noteModel);
        } else {
            note.update(noteModel);
        }
        return new ItemCache(0, note);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public PcNoteListItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        PcNoteListItem pcNoteListItemNewInstance;
        if (i == 0) {
            pcNoteListItemNewInstance = PcNoteListItem.newInstance(viewGroup, false, false);
        } else if (i == 1) {
            pcNoteListItemNewInstance = PcNoteListItem.newInstance(viewGroup, true, false);
        } else if (i == 2) {
            pcNoteListItemNewInstance = PcNoteListItem.newInstance(viewGroup, false, true);
        } else {
            Logger.INSTANCE.e(TAG, "Unknown view type: " + i);
            pcNoteListItemNewInstance = null;
        }
        if (pcNoteListItemNewInstance != null) {
            pcNoteListItemNewInstance.setOnClickListener(this.mOnClickListener);
            pcNoteListItemNewInstance.setOnLongClickListener(this.mOnLongClickListener);
        }
        return pcNoteListItemNewInstance;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(PcNoteListItem pcNoteListItem, int i) {
        if (getItem(i) == null) {
            Logger.INSTANCE.e(TAG, "onBindViewHolder getItem == null");
            return;
        }
        pcNoteListItem.bind(getItem(i), this.mBindContext, i, this.mCurrentNoteId);
        pcNoteListItem.itemView.setTag(Integer.valueOf(i));
        if (isSupportDrag(i)) {
            pcNoteListItem.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.miui.pc.frame.NotesListAdapter.1
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    view.startDrag(ClipData.newPlainText("", ""), NotesListAdapter.this.new CustomDragShadowBuilder(view), view, 512);
                    return true;
                }
            });
        }
    }

    private boolean isSupportDrag(int i) {
        ItemCache item = getItem(i);
        if (item == null) {
            return true;
        }
        long folderId = ((NoteCache) item.getCacheObject()).getNote().getFolderId();
        return (folderId == -2 || folderId == -5 || folderId == -3) ? false : true;
    }

    public class CustomDragShadowBuilder extends View.DragShadowBuilder {
        public CustomDragShadowBuilder(View view) {
            super(view);
        }

        @Override // android.view.View.DragShadowBuilder
        public void onProvideShadowMetrics(Point point, Point point2) {
            getView().setBackground(getView().getContext().getDrawable(R.drawable.pc_drag_note_background));
            super.onProvideShadowMetrics(point, point2);
            point2.set(0, 0);
        }

        @Override // android.view.View.DragShadowBuilder
        public void onDrawShadow(Canvas canvas) {
            super.onDrawShadow(canvas);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.isPrivateSearch) {
            SearchCursorAdapter searchCursorAdapter = this.mSearchCursorAdapter;
            if (searchCursorAdapter == null) {
                return 0;
            }
            return searchCursorAdapter.getCount();
        }
        Cursor cursor = this.mCursor;
        if (cursor == null || cursor.isClosed()) {
            return 0;
        }
        return this.mCursor.getCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(PcNoteListItem pcNoteListItem) {
        pcNoteListItem.onRecycled();
    }

    public void setSelectedNodeId(long j) {
        this.mCurrentNoteId = j;
        notifyDataSetChanged();
    }
}
