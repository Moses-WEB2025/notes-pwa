package com.miui.todo.feature.todolist;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.utils.CharUtils;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SubTodoEditAdapter extends RecyclerView.Adapter<SubTodoEditItemVh> implements SubTodoEditor {
    private Context mContext;
    private boolean mIsFloat;
    private RecyclerView mRecyclerView;
    private SubTodoEditAdapterListener mSubTodoEditAdapterListener;
    private SubModeImpl mSubTodoList;
    private SubModeImpl oldSubTodoList;
    private boolean isFirstSave = true;
    private boolean mIsNew = false;
    private Runnable mPendingRefreshChangedTask = new Runnable() { // from class: com.miui.todo.feature.todolist.SubTodoEditAdapter.1
        @Override // java.lang.Runnable
        public void run() {
            SubTodoEditAdapter.this.notifySubTodoDataChanged(-1, -1);
            SubTodoEditAdapter.this.notifyDataSetChanged();
        }
    };
    private SubTodoDataListener mSubTodoDataListener = new SubTodoDataListener() { // from class: com.miui.todo.feature.todolist.SubTodoEditAdapter.2
        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onDataSave(SubTodoEntity subTodoEntity, int i, boolean z, String str) {
            if (!z) {
                SubTodoEditAdapter subTodoEditAdapter = SubTodoEditAdapter.this;
                subTodoEditAdapter.oldSubTodoList = (SubModeImpl) subTodoEditAdapter.mSubTodoList.copy();
                SubTodoEditAdapter.this.oldSubTodoList.update(new SubTodoEntity(i, str, false), i);
            }
            SubTodoEditAdapter.this.isFirstSave = true;
            SubTodoEditAdapter.this.mSubTodoList.update(subTodoEntity, i);
            if (z) {
                SubTodoEditAdapter.this.notifySubTodoDataChanged(i, 0);
            }
            SubTodoEditAdapter.this.notifySubTodoDataChanged(i, 0);
        }

        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onDataDelete(int i) {
            SubTodoEditAdapter subTodoEditAdapter = SubTodoEditAdapter.this;
            subTodoEditAdapter.oldSubTodoList = (SubModeImpl) subTodoEditAdapter.mSubTodoList.copy();
            if (i == 1 && TextUtils.isEmpty(SubTodoEditAdapter.this.mSubTodoList.getTitle()) && SubTodoEditAdapter.this.mSubTodoList.getSubTodoSize() == 2) {
                SubTodoEditAdapter.this.mSubTodoList.setTitle(SubTodoEditAdapter.this.mSubTodoList.getEntity(0).getContent().replace(CharUtils.ZERO_WIDTH_SPACE, ""));
                SubTodoEditAdapter.this.mSubTodoList.delete(i);
                SubTodoEditAdapter.this.mSubTodoList.delete(0);
                SubTodoEditAdapter.this.notifySubTodoDataChanged(i, 1);
                SubTodoEditAdapter.this.notifyDataSetChanged();
                return;
            }
            SubTodoEditAdapter.this.mSubTodoList.delete(i);
            if (i > 0) {
                int i2 = i - 1;
                SubTodoEditAdapter.this.mBindContext.setEditIndex(i2);
                SubTodoEditAdapter.this.mBindContext.setDoEditIndex(i2);
                SubTodoEditAdapter.this.notifyItemChanged(i2);
            } else {
                SubTodoEditAdapter.this.mBindContext.setEditIndex(0);
                SubTodoEditAdapter.this.mBindContext.setDoEditIndex(0);
                SubTodoEditAdapter.this.notifyItemChanged(0);
            }
            SubTodoEditAdapter.this.notifySubTodoDataChanged(i, 1);
            SubTodoEditAdapter.this.notifyDataSetChanged();
        }

        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onDataMerge(int i) {
            SubTodoEditAdapter subTodoEditAdapter = SubTodoEditAdapter.this;
            subTodoEditAdapter.oldSubTodoList = (SubModeImpl) subTodoEditAdapter.mSubTodoList.copy();
            if (i == 0) {
                if (SubTodoEditAdapter.this.mSubTodoList.getSubTodoSize() == 1) {
                    SubTodoEntity entity = SubTodoEditAdapter.this.mSubTodoList.getEntity(i);
                    if (TextUtils.isEmpty(SubTodoEditAdapter.this.mSubTodoList.getTitle())) {
                        SubTodoEditAdapter.this.mBindContext.setCursorIndexOnMergeToNormalTodo(0);
                        SubTodoEditAdapter.this.mSubTodoList.setTitle(entity.getContent().replace(CharUtils.ZERO_WIDTH_SPACE, ""));
                    } else {
                        SubTodoEditAdapter.this.mBindContext.setCursorIndexOnMergeToNormalTodo(SubTodoEditAdapter.this.mSubTodoList.getTitle().length());
                        SubTodoEditAdapter.this.mSubTodoList.setTitle(SubTodoEditAdapter.this.mSubTodoList.getTitle() + entity.getContent().replace(CharUtils.ZERO_WIDTH_SPACE, ""));
                    }
                    SubTodoEditAdapter.this.mSubTodoList.delete(i);
                    SubTodoEditAdapter.this.notifySubTodoDataChanged(i, 2);
                    SubTodoEditAdapter.this.notifyDataSetChanged();
                }
                SubTodoEditAdapter.this.mBindContext.setMergeIndex(-1);
                return;
            }
            SubTodoEntity entity2 = SubTodoEditAdapter.this.mSubTodoList.getEntity(i);
            SubTodoEntity entity3 = SubTodoEditAdapter.this.mSubTodoList.getEntity(i - 1);
            entity3.setContent(entity3.getContent() + entity2.getContent().replace(CharUtils.ZERO_WIDTH_SPACE, ""));
            SubTodoEditAdapter.this.mSubTodoList.delete(i);
            SubTodoEditAdapter.this.notifySubTodoDataChanged(i, 2);
            SubTodoEditAdapter.this.notifyDataSetChanged();
        }

        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onMultiDataAdd(List<SubTodoEntity> list, int i, boolean z, String str) {
            SubTodoEditAdapter subTodoEditAdapter = SubTodoEditAdapter.this;
            subTodoEditAdapter.oldSubTodoList = (SubModeImpl) subTodoEditAdapter.mSubTodoList.copy();
            if (str != null) {
                SubTodoEditAdapter.this.oldSubTodoList.update(new SubTodoEntity(i, str, false), i);
            }
            SubTodoEditAdapter.this.mSubTodoList.update(list.get(0), i);
            for (int i2 = 1; i2 < list.size(); i2++) {
                SubTodoEditAdapter.this.mSubTodoList.add(list.get(i2), i + i2);
            }
            boolean z2 = (list.size() + i) - 1 < SubTodoEditAdapter.this.mSubTodoList.getSubTodoSize();
            if (z) {
                SubTodoEditAdapter.this.notifySubTodoDataChanged(i, 3);
            }
            if (!z2) {
                SubTodoEditAdapter.this.notifyDataSetChanged();
            } else {
                SubTodoEditAdapter.this.notifyItemRangeInserted(i + 1, list.size() - 1);
            }
        }

        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onFinishStatusChanged(boolean z, int i) {
            if (SubTodoEditAdapter.this.mIsNew && SubTodoEditAdapter.this.mSubTodoList.getSubTodoSize() == SubTodoEditAdapter.this.mSubTodoList.getFinishSubTodoSize() + 1 && z) {
                if (SubTodoEditAdapter.this.mIsFloat) {
                    if (SubTodoEditAdapter.this.mSubTodoEditAdapterListener != null) {
                        SubTodoEditAdapter.this.mSubTodoEditAdapterListener.showToast(NoteApp.getInstance().getString(R.string.todo_list_new_finish_all_tip), EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
                    }
                } else {
                    Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.todo_list_new_finish_all_tip), 0).show();
                }
            } else {
                SubTodoEditAdapter.this.mSubTodoList.changeFinishStatus(i, z);
                if (SubTodoEditAdapter.this.mRecyclerView != null) {
                    SubTodoEditAdapter.this.mRecyclerView.removeCallbacks(SubTodoEditAdapter.this.mPendingRefreshChangedTask);
                    SubTodoEditAdapter.this.mRecyclerView.postDelayed(SubTodoEditAdapter.this.mPendingRefreshChangedTask, 100L);
                }
            }
            if (SubTodoEditAdapter.this.mIsNew) {
                SubTodoEditAdapter.this.mIsNew = false;
            }
        }

        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onDataClick(View view, int i) {
            if (SubTodoEditAdapter.this.mSubTodoEditListener != null) {
                SubTodoEditAdapter.this.mSubTodoEditListener.onSubItemClicked(view);
                if (SubTodoEditAdapter.this.mBindContext != null) {
                    SubTodoEditAdapter.this.mBindContext.setEditIndex(i);
                    SubTodoEditAdapter.this.mBindContext.setDoEditIndex(i);
                }
            }
        }
    };
    private SubTodoEditListener mSubTodoEditListener = null;
    private SubTodoEditListBindContext mBindContext = new SubTodoEditListBindContext(this);

    public boolean editNew() {
        return true;
    }

    public SubTodoEditAdapter(Context context, boolean z) {
        this.mContext = context;
        this.mIsFloat = z;
    }

    public void bindDataSource(SubModeImpl subModeImpl) {
        SubModeImpl subModeImpl2 = this.mSubTodoList;
        if (subModeImpl2 == null || subModeImpl == null || !subModeImpl2.getContent().equals(subModeImpl.getContent())) {
            this.mSubTodoList = subModeImpl;
            notifyDataSetChanged();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public SubTodoEditItemVh onCreateViewHolder(ViewGroup viewGroup, int i) {
        return SubTodoEditItemVh.newInstance(viewGroup);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(SubTodoEditItemVh subTodoEditItemVh, int i) {
        subTodoEditItemVh.bind(this.mSubTodoList.getEntity(i), this.mBindContext, i, this.mIsFloat);
        subTodoEditItemVh.setDataListener(this.mSubTodoDataListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        SubTodoEntity entity = this.mSubTodoList.getEntity(i);
        if (entity == null) {
            return -1L;
        }
        return entity.getId();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mSubTodoList.getSubTodoSize();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeCallbacks(this.mPendingRefreshChangedTask);
        if (this.mRecyclerView == recyclerView) {
            this.mRecyclerView = null;
        }
    }

    private Context getContext() {
        return this.mContext;
    }

    public SubTodoEditListBindContext getBindContext() {
        return this.mBindContext;
    }

    @Override // com.miui.todo.feature.todolist.SubTodoEditor
    public void setSubTodoEditListener(SubTodoEditListener subTodoEditListener) {
        this.mSubTodoEditListener = subTodoEditListener;
    }

    @Override // com.miui.todo.feature.todolist.SubTodoEditor
    public void setEditIndex(int i) {
        this.mBindContext.setEditIndex(i);
        this.mBindContext.setDoEditIndex(i);
    }

    @Override // com.miui.todo.feature.todolist.SubTodoEditor
    public void setSplitDataIndex(int i) {
        this.mBindContext.setSplitDataIndex(i);
    }

    @Override // com.miui.todo.feature.todolist.SubTodoEditor
    public boolean editNext() {
        this.oldSubTodoList = (SubModeImpl) this.mSubTodoList.copy();
        int editIndex = this.mBindContext.getEditIndex();
        if (editIndex == -1) {
            return false;
        }
        int i = editIndex + 1;
        if (this.mSubTodoList.getUnFinishSubTodoSize() >= 100) {
            Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.todo_list_overflow_tip), 0).show();
            return false;
        }
        if (this.mSubTodoList.getEntity(editIndex) == null || !this.mSubTodoList.getEntity(editIndex).getIsFinish()) {
            if (!this.mSubTodoList.add(new SubTodoEntity(this.mSubTodoList.getSubTodoSize(), "", false), i)) {
                Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.todo_list_overflow_tip), 0).show();
                return false;
            }
            notifyItemInserted(i);
            this.mBindContext.setEditIndex(i);
            this.mBindContext.setDoEditIndex(i);
            SubTodoEditAdapterListener subTodoEditAdapterListener = this.mSubTodoEditAdapterListener;
            if (subTodoEditAdapterListener == null) {
                return true;
            }
            subTodoEditAdapterListener.onCreateNewData(i);
            return true;
        }
        if (i >= this.mSubTodoList.getSubTodoSize()) {
            return true;
        }
        this.mBindContext.setEditIndex(i);
        this.mBindContext.setDoEditIndex(i);
        notifyDataSetChanged();
        return true;
    }

    public boolean isMaxLimit() {
        return this.mSubTodoList.getSubTodoSize() >= 100;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySubTodoDataChanged(int i, int i2) {
        SubTodoEditListener subTodoEditListener = this.mSubTodoEditListener;
        if (subTodoEditListener != null) {
            subTodoEditListener.onDataChanged(this.mSubTodoList.getContent(), i, i2);
        }
    }

    public void setNew(boolean z) {
        this.mIsNew = z;
    }

    public void setSubTodoEditAdapterListener(SubTodoEditAdapterListener subTodoEditAdapterListener) {
        this.mSubTodoEditAdapterListener = subTodoEditAdapterListener;
    }

    public SubModeImpl getOldSubTodoList() {
        return this.oldSubTodoList;
    }

    public boolean getIsFirstSave() {
        return this.isFirstSave;
    }

    public void setIsFirstSave(boolean z) {
        this.isFirstSave = z;
    }
}
