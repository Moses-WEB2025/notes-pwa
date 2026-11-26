package com.miui.todo.base;

import android.R;
import android.content.res.Resources;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.notes.tool.util.LongSparseLongArray;
import com.miui.notes.ui.widget.ViewHolderEditableCallback;
import com.miui.todo.feature.todolist.TodoListAdapter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import miuix.appcompat.app.Fragment;
import miuix.view.ActionModeAnimationListener;
import miuix.view.EditActionMode;

/* JADX INFO: loaded from: classes4.dex */
public class EditableRecyclerViewWrapper<VH extends RecyclerView.ViewHolder & ViewHolderEditableCallback> {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    private static final String TAG = "EditableRecyclerViewWrapper";
    private ActionMode mActionMode;
    private RecyclerView.Adapter<VH> mAdapter;
    private RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.miui.todo.base.EditableRecyclerViewWrapper.1
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            EditableRecyclerViewWrapper.this.confirmCheckedPositionsById();
        }
    };
    private LongSparseLongArray mCheckedIdStates = new LongSparseLongArray();
    private MultiChoiceModeListener mChoiceListener;
    private final RecyclerView mRecyclerView;

    public interface MultiChoiceModeListener extends AbsListView.MultiChoiceModeListener {
        void onAllItemCheckedStateChanged(ActionMode actionMode, boolean z);
    }

    public EditableRecyclerViewWrapper(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public boolean isAllItemsChecked() {
        Logger.INSTANCE.d(TAG, "isAllItemsChecked: getItemCountWithoutHeader: " + ((TodoListAdapter) this.mAdapter).getItemCountWithoutHeader() + " mCheckedIdStates.size() " + this.mCheckedIdStates.size());
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        return adapter != null && ((TodoListAdapter) adapter).getItemCountWithoutHeader() == this.mCheckedIdStates.size();
    }

    public boolean isAllUnfinishItemsChecked() {
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        return adapter != null && ((TodoListAdapter) adapter).getUnFinishedItemCount() == this.mCheckedIdStates.size();
    }

    public boolean isAllFinishedItemsChecked() {
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        return adapter != null && ((TodoListAdapter) adapter).getFinishedItemCount() == this.mCheckedIdStates.size();
    }

    public void setAllItemsChecked(boolean z) {
        MultiChoiceModeListener multiChoiceModeListener;
        int itemCount = this.mAdapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            setItemChecked(i, z, true);
        }
        ActionMode actionMode = this.mActionMode;
        if (actionMode == null || (multiChoiceModeListener = this.mChoiceListener) == null) {
            return;
        }
        multiChoiceModeListener.onAllItemCheckedStateChanged(actionMode, z);
    }

    public void toggleItemChecked(View view) {
        HapticFeedbackTool.getInstance().performListItemClick(view);
        int childAdapterPosition = this.mRecyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition != -1) {
            setItemChecked(childAdapterPosition, !isItemChecked(childAdapterPosition));
        }
    }

    public void setItemChecked(View view, boolean z) {
        int childAdapterPosition;
        if (!(view.getLayoutParams() instanceof RecyclerView.LayoutParams) || (childAdapterPosition = this.mRecyclerView.getChildAdapterPosition(view)) == -1) {
            return;
        }
        setItemChecked(childAdapterPosition, z);
    }

    public void setItemChecked(int i, boolean z) {
        setItemChecked(i, z, false);
    }

    private void setItemChecked(int i, boolean z, boolean z2) {
        ActionMode actionMode;
        MultiChoiceModeListener multiChoiceModeListener;
        long itemId = this.mAdapter.getItemId(i);
        if (z == (this.mCheckedIdStates.indexOfKey(itemId) >= 0) || itemId <= -2) {
            return;
        }
        if (z) {
            this.mCheckedIdStates.put(itemId, i);
        } else {
            this.mCheckedIdStates.delete(itemId);
        }
        View viewByItemId = getViewByItemId(itemId);
        if (viewByItemId == null) {
            this.mAdapter.notifyItemChanged(i);
        } else {
            updateOnScreenCheckedView(viewByItemId, i, itemId);
        }
        if (z2 || (actionMode = this.mActionMode) == null || (multiChoiceModeListener = this.mChoiceListener) == null) {
            return;
        }
        multiChoiceModeListener.onItemCheckedStateChanged(actionMode, i, itemId, z);
    }

    public void swapCheckedStates(int i, int i2) {
        long itemId = this.mAdapter.getItemId(i);
        boolean z = this.mCheckedIdStates.indexOfKey(itemId) >= 0;
        long itemId2 = this.mAdapter.getItemId(i2);
        boolean z2 = this.mCheckedIdStates.indexOfKey(itemId2) >= 0;
        if (z) {
            this.mCheckedIdStates.put(itemId, i2);
        }
        if (z2) {
            this.mCheckedIdStates.put(itemId2, i);
        }
    }

    public void clearChoices() {
        this.mCheckedIdStates.clear();
    }

    public int getCheckedItemCount() {
        return this.mCheckedIdStates.size();
    }

    public boolean isItemChecked(int i) {
        return this.mCheckedIdStates.indexOfValue((long) i) >= 0;
    }

    public boolean isItemIdChecked(long j) {
        return this.mCheckedIdStates.indexOfKey(j) >= 0;
    }

    public LongSparseLongArray getCheckedItems() {
        return this.mCheckedIdStates;
    }

    public long[] getCheckedItemIds() {
        int size = this.mCheckedIdStates.size();
        long[] jArr = new long[this.mCheckedIdStates.size()];
        for (int i = 0; i < size; i++) {
            jArr[i] = this.mCheckedIdStates.keyAt(i);
        }
        return jArr;
    }

    public long[] getCheckedItemIndexs() {
        int size = this.mCheckedIdStates.size();
        long[] jArr = new long[this.mCheckedIdStates.size()];
        for (int i = 0; i < size; i++) {
            jArr[i] = this.mCheckedIdStates.valueAt(i);
        }
        return jArr;
    }

    protected RecyclerView.Adapter<VH> getAdapter() {
        return this.mAdapter;
    }

    public RecyclerView getView() {
        return this.mRecyclerView;
    }

    public void setAdapter(RecyclerView.Adapter<VH> adapter) {
        RecyclerView.Adapter<VH> adapter2 = this.mAdapter;
        if (adapter2 != null) {
            try {
                adapter2.unregisterAdapterDataObserver(this.mAdapterDataObserver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mAdapter = adapter;
        if (adapter != null) {
            if (!adapter.hasStableIds()) {
                throw new IllegalArgumentException("adapter must has stable id");
            }
            this.mAdapter.registerAdapterDataObserver(this.mAdapterDataObserver);
        }
        this.mRecyclerView.setAdapter(adapter);
        if (adapter != null) {
            confirmCheckedPositionsById();
        }
    }

    public void startActionMode(MultiChoiceModeListener multiChoiceModeListener, Fragment fragment) {
        MultiChoiceModeListenerWrapper multiChoiceModeListenerWrapper = new MultiChoiceModeListenerWrapper(multiChoiceModeListener, false);
        this.mChoiceListener = multiChoiceModeListenerWrapper;
        fragment.startActionMode(multiChoiceModeListenerWrapper);
    }

    public void startActionMode(MultiChoiceModeListener multiChoiceModeListener, boolean z) {
        MultiChoiceModeListenerWrapper multiChoiceModeListenerWrapper = new MultiChoiceModeListenerWrapper(multiChoiceModeListener, z);
        this.mChoiceListener = multiChoiceModeListenerWrapper;
        this.mRecyclerView.startActionMode(multiChoiceModeListenerWrapper);
    }

    public void finishActionMode() {
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    public boolean isInActionMode() {
        return this.mActionMode != null;
    }

    private void updateOnScreenCheckedView(View view, int i, long j) {
        Object childViewHolder = this.mRecyclerView.getChildViewHolder(view);
        if (childViewHolder == null || !(childViewHolder instanceof ViewHolderEditableCallback)) {
            return;
        }
        ((ViewHolderEditableCallback) childViewHolder).onUpdateEditable(isInActionMode(), this.mCheckedIdStates.indexOfKey(j) >= 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void updateActionModeButton1(ActionMode actionMode, boolean z) {
        EditActionMode editActionMode = (EditActionMode) actionMode;
        if (!UIUtils.isMiuiXSdkSupported()) {
            editActionMode.setButton(16908313, R.string.cancel);
            return;
        }
        if (!z) {
            if (DisplayUtils.isNightMode()) {
                editActionMode.setButton(16908313, (CharSequence) null, com.miui.notes.R.drawable.miuix_action_icon_cancel_dark);
                return;
            } else {
                editActionMode.setButton(16908313, (CharSequence) null, com.miui.notes.R.drawable.miuix_action_icon_cancel_light);
                return;
            }
        }
        editActionMode.setButton(16908313, (CharSequence) null, com.miui.notes.R.drawable.miuix_action_icon_cancel_dark);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCheckStatus(ActionMode actionMode, int i) {
        if (actionMode != null) {
            Resources resources = this.mRecyclerView.getResources();
            if (i == 0) {
                actionMode.setTitle(resources.getString(com.miui.notes.R.string.miuix_appcompat_select_item));
            } else {
                actionMode.setTitle(String.format(resources.getQuantityString(com.miui.notes.R.plurals.miuix_appcompat_items_selected, i), Integer.valueOf(i)));
            }
            if (((TodoListAdapter) this.mAdapter).isInDrag()) {
                return;
            }
            actionMode.getMenu().setGroupEnabled(0, i != 0);
        }
    }

    public void setActionModeMenuEnable(boolean z) {
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            if (z) {
                updateCheckStatus(actionMode, this.mCheckedIdStates.size());
            } else {
                actionMode.getMenu().setGroupEnabled(0, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmCheckedPositionsById() {
        ActionMode actionMode;
        MultiChoiceModeListener multiChoiceModeListener;
        int i = 0;
        boolean z = false;
        while (i < this.mCheckedIdStates.size()) {
            long jKeyAt = this.mCheckedIdStates.keyAt(i);
            int iValueAt = (int) this.mCheckedIdStates.valueAt(i);
            long itemId = this.mAdapter.getItemId(iValueAt);
            if (itemId == -1) {
                this.mCheckedIdStates.delete(jKeyAt);
            } else if (jKeyAt != itemId) {
                int iMax = Math.max(0, iValueAt - 20);
                int iMin = Math.min(iValueAt + 20, this.mAdapter.getItemCount());
                while (true) {
                    if (iMax >= iMin) {
                        this.mCheckedIdStates.delete(jKeyAt);
                        i--;
                        ActionMode actionMode2 = this.mActionMode;
                        if (actionMode2 != null && (multiChoiceModeListener = this.mChoiceListener) != null) {
                            multiChoiceModeListener.onItemCheckedStateChanged(actionMode2, iValueAt, jKeyAt, false);
                        }
                        z = true;
                        break;
                    }
                    if (jKeyAt == this.mAdapter.getItemId(iMax)) {
                        this.mCheckedIdStates.put(jKeyAt, iMax);
                        break;
                    }
                    iMax++;
                }
            }
            i++;
        }
        if (!z || (actionMode = this.mActionMode) == null) {
            return;
        }
        actionMode.invalidate();
    }

    private View getViewByItemId(long j) {
        int childCount = this.mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRecyclerView.getChildAt(i);
            if (this.mRecyclerView.getChildItemId(childAt) == j) {
                return childAt;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAccessibilitySelectedState() {
        int childCount = this.mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Object childViewHolder = this.mRecyclerView.getChildViewHolder(this.mRecyclerView.getChildAt(i));
            if (childViewHolder instanceof ViewHolderEditableCallback) {
                ((ViewHolderEditableCallback) childViewHolder).onUpdateEditable(false, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOffScreenItemsChanged() {
        int childCount = this.mRecyclerView.getChildCount();
        HashSet hashSet = new HashSet(childCount);
        for (int i = 0; i < childCount; i++) {
            hashSet.add(Integer.valueOf(this.mRecyclerView.getChildAdapterPosition(this.mRecyclerView.getChildAt(i))));
        }
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        if (adapter != null) {
            int itemCount = adapter.getItemCount();
            for (int i2 = 0; i2 < itemCount; i2++) {
                if (!hashSet.contains(Integer.valueOf(i2))) {
                    this.mAdapter.notifyItemChanged(i2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOnScreenItemsChanged() {
        int childCount = this.mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRecyclerView.getChildAt(i);
            Object childViewHolder = this.mRecyclerView.getChildViewHolder(childAt);
            if (childViewHolder instanceof ViewHolderEditableCallback) {
                ((ViewHolderEditableCallback) childViewHolder).onUpdateEditable(isInActionMode(), this.mCheckedIdStates.indexOfKey(this.mRecyclerView.getChildItemId(childAt)) >= 0);
            }
        }
    }

    private class MultiChoiceModeListenerWrapper implements MultiChoiceModeListener {
        private ActionModeAnimationListener mActionModeAnimationListener = new ActionModeAnimationListener() { // from class: com.miui.todo.base.EditableRecyclerViewWrapper.MultiChoiceModeListenerWrapper.1
            Set<ViewHolderEditableCallback> holders = new HashSet();

            @Override // miuix.view.ActionModeAnimationListener
            public void onStart(boolean z) {
                this.holders.clear();
            }

            @Override // miuix.view.ActionModeAnimationListener
            public void onUpdate(boolean z, float f) {
                int childCount = EditableRecyclerViewWrapper.this.mRecyclerView.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    Object childViewHolder = EditableRecyclerViewWrapper.this.mRecyclerView.getChildViewHolder(EditableRecyclerViewWrapper.this.mRecyclerView.getChildAt(i));
                    if (childViewHolder instanceof ViewHolderEditableCallback) {
                        ViewHolderEditableCallback viewHolderEditableCallback = (ViewHolderEditableCallback) childViewHolder;
                        if (!viewHolderEditableCallback.getMStarted()) {
                            viewHolderEditableCallback.onAnimationStart(z);
                        }
                        viewHolderEditableCallback.onAnimationUpdate(z, f);
                        this.holders.add(viewHolderEditableCallback);
                    }
                }
            }

            @Override // miuix.view.ActionModeAnimationListener
            public void onStop(boolean z) {
                Iterator<ViewHolderEditableCallback> it = this.holders.iterator();
                while (it.hasNext()) {
                    it.next().onAnimationStop(z);
                }
                this.holders.clear();
                if (z) {
                    return;
                }
                EditableRecyclerViewWrapper.this.notifyOffScreenItemsChanged();
            }
        };
        private boolean mIsForceDarkmode;
        private MultiChoiceModeListener mWrapped;

        public MultiChoiceModeListenerWrapper(MultiChoiceModeListener multiChoiceModeListener, boolean z) {
            this.mIsForceDarkmode = false;
            this.mWrapped = multiChoiceModeListener;
            this.mIsForceDarkmode = z;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            EditableRecyclerViewWrapper.this.mActionMode = null;
            this.mWrapped.onDestroyActionMode(actionMode);
            EditableRecyclerViewWrapper.this.mCheckedIdStates.clear();
            EditableRecyclerViewWrapper.this.clearAccessibilitySelectedState();
            EditableRecyclerViewWrapper.this.notifyOnScreenItemsChanged();
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.setTitle(com.miui.notes.R.string.miuix_appcompat_select_item);
            if (!this.mWrapped.onCreateActionMode(actionMode, menu)) {
                return false;
            }
            EditableRecyclerViewWrapper.this.updateActionModeButton1(actionMode, this.mIsForceDarkmode);
            EditableRecyclerViewWrapper editableRecyclerViewWrapper = EditableRecyclerViewWrapper.this;
            editableRecyclerViewWrapper.updateCheckStatus(actionMode, editableRecyclerViewWrapper.mCheckedIdStates.size());
            EditableRecyclerViewWrapper.this.notifyOnScreenItemsChanged();
            EditableRecyclerViewWrapper.this.notifyOffScreenItemsChanged();
            EditableRecyclerViewWrapper.this.mActionMode = actionMode;
            ((EditActionMode) EditableRecyclerViewWrapper.this.mActionMode).addAnimationListener(this.mActionModeAnimationListener);
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.widget.AbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z) {
            EditableRecyclerViewWrapper editableRecyclerViewWrapper = EditableRecyclerViewWrapper.this;
            editableRecyclerViewWrapper.updateCheckStatus(actionMode, editableRecyclerViewWrapper.mCheckedIdStates.size());
            this.mWrapped.onItemCheckedStateChanged(actionMode, i, j, z);
        }

        @Override // com.miui.todo.base.EditableRecyclerViewWrapper.MultiChoiceModeListener
        public void onAllItemCheckedStateChanged(ActionMode actionMode, boolean z) {
            EditableRecyclerViewWrapper editableRecyclerViewWrapper = EditableRecyclerViewWrapper.this;
            editableRecyclerViewWrapper.updateCheckStatus(editableRecyclerViewWrapper.mActionMode, EditableRecyclerViewWrapper.this.mCheckedIdStates.size());
            this.mWrapped.onAllItemCheckedStateChanged(EditableRecyclerViewWrapper.this.mActionMode, z);
        }
    }
}
