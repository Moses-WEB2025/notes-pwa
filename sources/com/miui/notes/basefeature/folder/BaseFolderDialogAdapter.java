package com.miui.notes.basefeature.folder;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.miui.common.tool.Logger;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.model.FolderModel;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.schema.NoteSchema;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseFolderDialogAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    protected BindContext mBindContext;
    protected List<FolderModel> mFolderItemEntities;
    protected View.OnClickListener mOnClickListener;
    protected View.OnLongClickListener mOnLongClickListener;
    protected long mSelectId;
    private long mLastNotifyChangeTime = 0;
    private long mLastManualModifyDataTime = 0;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(T t, int i) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public abstract T onCreateViewHolder(ViewGroup viewGroup, int i);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(T t) {
    }

    public BaseFolderDialogAdapter() {
        setHasStableIds(true);
        initBindResource();
    }

    private void initBindResource() {
        AsyncTask.execute(new InitThemeRunnable());
    }

    private static class InitThemeRunnable implements Runnable {
        private InitThemeRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Logger.INSTANCE.d("BaseFolderDialogAdapter", "doInBackground");
            Thread.currentThread().setName("InitThemeRunnable");
            new NoteSchema.HTMLSchemaHolder();
            ResourceManager.getTheme(-1);
        }
    }

    public void setBindContext(BindContext bindContext) {
        this.mBindContext = bindContext;
    }

    public void setData(List<FolderModel> list) {
        this.mFolderItemEntities = list;
        notifyDataSetChangedWithLock(true);
    }

    public void markManualModifyDataTime() {
        this.mLastManualModifyDataTime = System.currentTimeMillis();
    }

    private void notifyDataSetChangedWithLock(boolean z) {
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

    public FolderModel getItem(int i) {
        if (i < this.mFolderItemEntities.size()) {
            return this.mFolderItemEntities.get(i);
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        FolderModel folderModel;
        List<FolderModel> list = this.mFolderItemEntities;
        if (list == null || i < 0 || i >= list.size() || (folderModel = this.mFolderItemEntities.get(i)) == null) {
            return -1L;
        }
        return folderModel.getId();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<FolderModel> list = this.mFolderItemEntities;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setSelectId(long j) {
        this.mSelectId = j;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mOnLongClickListener = onLongClickListener;
    }
}
