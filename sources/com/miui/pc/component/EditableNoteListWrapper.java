package com.miui.pc.component;

import android.R;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.ActionMode;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.common.tool.UIUtils;
import com.miui.notes.cache.FolderCache;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.tool.util.LongSparseLongArray;
import com.miui.pc.component.ViewHolderEditableCallback;
import com.miui.pc.frame.NotesListAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import miuix.view.ActionModeAnimationListener;
import miuix.view.EditActionMode;

/* JADX INFO: loaded from: classes4.dex */
public class EditableNoteListWrapper<VH extends RecyclerView.ViewHolder & ViewHolderEditableCallback> {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    private RecyclerView.Adapter<VH> mAdapter;
    private MultiChoiceModeListener mChoiceListener;
    public final RecyclerView mRecyclerView;
    private RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.miui.pc.component.EditableNoteListWrapper.1
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            EditableNoteListWrapper.this.confirmCheckedPositionsById();
        }
    };
    private EditableNoteListWrapper<VH>.PcEditActionMode mPcEditActionMode = new PcEditActionMode();
    private LongSparseLongArray mCheckedIdStates = new LongSparseLongArray();

    public interface MultiChoiceModeListener {
        void onAllItemCheckedStateChanged(boolean z);

        void onItemCheckedStateChanged(int i, long j, boolean z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCheckStatus(int i) {
    }

    public EditableNoteListWrapper(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public boolean isAllItemsChecked() {
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        return adapter != null && adapter.getItemCount() == this.mCheckedIdStates.size();
    }

    public void setAllItemsChecked(boolean z) {
        MultiChoiceModeListener multiChoiceModeListener;
        int itemCount = this.mAdapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            setItemChecked(i, z, true);
        }
        if (!isInActionMode() || (multiChoiceModeListener = this.mChoiceListener) == null) {
            return;
        }
        multiChoiceModeListener.onAllItemCheckedStateChanged(z);
    }

    public void toggleItemChecked(View view) {
        HapticFeedbackTool.getInstance().performListItemClick(view);
        int childAdapterPosition = this.mRecyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition != -1) {
            setItemChecked(childAdapterPosition, !isItemChecked(childAdapterPosition));
        }
    }

    public void setItemChecked(View view, boolean z) {
        int childAdapterPosition = this.mRecyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition != -1) {
            setItemChecked(childAdapterPosition, z);
        }
    }

    public void setItemChecked(int i, boolean z) {
        setItemChecked(i, z, false);
    }

    private void setItemChecked(int i, boolean z, boolean z2) {
        MultiChoiceModeListener multiChoiceModeListener;
        long itemId = this.mAdapter.getItemId(i);
        if (z != (this.mCheckedIdStates.indexOfKey(itemId) >= 0)) {
            if (z) {
                this.mCheckedIdStates.put(itemId, i);
            } else {
                this.mCheckedIdStates.delete(itemId);
            }
            View viewByItemId = getViewByItemId(itemId);
            if (viewByItemId == null) {
                this.mAdapter.notifyItemChanged(i);
            } else if (isInActionMode()) {
                updateOnScreenCheckedView(viewByItemId, i, itemId);
            }
            if (z2 || !isInActionMode() || (multiChoiceModeListener = this.mChoiceListener) == null) {
                return;
            }
            multiChoiceModeListener.onItemCheckedStateChanged(i, itemId, z);
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

    protected RecyclerView.Adapter<VH> getAdapter() {
        return this.mAdapter;
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

    public void registerAdapterDataObserverTemp(boolean z) {
        if (z) {
            try {
                this.mAdapter.registerAdapterDataObserver(this.mAdapterDataObserver);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            this.mAdapter.unregisterAdapterDataObserver(this.mAdapterDataObserver);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void startActionMode(MultiChoiceModeListener multiChoiceModeListener) {
        MultiChoiceModeListenerWrapper multiChoiceModeListenerWrapper = new MultiChoiceModeListenerWrapper(multiChoiceModeListener, false);
        this.mChoiceListener = multiChoiceModeListenerWrapper;
        this.mPcEditActionMode.startActionMode(multiChoiceModeListenerWrapper);
    }

    public void finishActionMode() {
        if (isInActionMode()) {
            this.mPcEditActionMode.finishActionMode();
        } else {
            clearChoices();
        }
    }

    public boolean isInActionMode() {
        return this.mPcEditActionMode.isInMode();
    }

    private void updateOnScreenCheckedView(View view, int i, long j) {
        Object childViewHolder = this.mRecyclerView.getChildViewHolder(view);
        if (childViewHolder == null || !(childViewHolder instanceof ViewHolderEditableCallback)) {
            return;
        }
        ((ViewHolderEditableCallback) childViewHolder).onUpdateEditable(isInActionMode(), this.mCheckedIdStates.indexOfKey(j) >= 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateActionModeButton1(ActionMode actionMode, boolean z) {
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
    public void confirmCheckedPositionsById() {
        MultiChoiceModeListener multiChoiceModeListener;
        int i = 0;
        while (i < this.mCheckedIdStates.size()) {
            long jKeyAt = this.mCheckedIdStates.keyAt(i);
            int iValueAt = (int) this.mCheckedIdStates.valueAt(i);
            if (jKeyAt != this.mAdapter.getItemId(iValueAt)) {
                int iMax = Math.max(0, iValueAt - 20);
                int iMin = Math.min(iValueAt + 20, this.mAdapter.getItemCount());
                while (true) {
                    if (iMax >= iMin) {
                        this.mCheckedIdStates.delete(jKeyAt);
                        i--;
                        if (isInActionMode() && (multiChoiceModeListener = this.mChoiceListener) != null) {
                            multiChoiceModeListener.onItemCheckedStateChanged(iValueAt, jKeyAt, false);
                            break;
                        }
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
    }

    public List<RecyclerView.ViewHolder> getCheckedViewHolders() {
        ArrayList arrayList = new ArrayList();
        int childCount = this.mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRecyclerView.getChildAt(i);
            if (this.mCheckedIdStates.get(this.mRecyclerView.getChildItemId(childAt), -1L) != -1) {
                RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(childAt);
                if (childViewHolder.isRecyclable()) {
                    arrayList.add(childViewHolder);
                }
            }
        }
        return arrayList;
    }

    private View getViewByItemId(long j) {
        int childCount = this.mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRecyclerView.getChildAt(i);
            if (this.mRecyclerView.getChildViewHolder(childAt).isRecyclable() && this.mRecyclerView.getChildItemId(childAt) == j) {
                return childAt;
            }
        }
        return null;
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
        private boolean mIsForceDarkmode;
        private MultiChoiceModeListener mWrapped;

        public MultiChoiceModeListenerWrapper(MultiChoiceModeListener multiChoiceModeListener, boolean z) {
            this.mWrapped = multiChoiceModeListener;
            this.mIsForceDarkmode = z;
        }

        @Override // com.miui.pc.component.EditableNoteListWrapper.MultiChoiceModeListener
        public void onItemCheckedStateChanged(int i, long j, boolean z) {
            MultiChoiceModeListener multiChoiceModeListener = this.mWrapped;
            if (multiChoiceModeListener != null) {
                multiChoiceModeListener.onItemCheckedStateChanged(i, j, z);
            }
        }

        @Override // com.miui.pc.component.EditableNoteListWrapper.MultiChoiceModeListener
        public void onAllItemCheckedStateChanged(boolean z) {
            EditableNoteListWrapper editableNoteListWrapper = EditableNoteListWrapper.this;
            editableNoteListWrapper.updateCheckStatus(editableNoteListWrapper.mCheckedIdStates.size());
            MultiChoiceModeListener multiChoiceModeListener = this.mWrapped;
            if (multiChoiceModeListener != null) {
                multiChoiceModeListener.onAllItemCheckedStateChanged(z);
            }
        }
    }

    private class PcEditActionMode {
        private boolean isToVisible;
        private ActionModeAnimationListener mActionModeAnimationListener;
        private ValueAnimator mActionModeAnimator;
        private MultiChoiceModeListener mChoiceListener;

        void onItemCheckedStateChanged(int i, long j, boolean z) {
        }

        private PcEditActionMode() {
            this.isToVisible = false;
            this.mActionModeAnimationListener = new ActionModeAnimationListener() { // from class: com.miui.pc.component.EditableNoteListWrapper.PcEditActionMode.1
                Set<ViewHolderEditableCallback> holders = new HashSet();

                @Override // miuix.view.ActionModeAnimationListener
                public void onStart(boolean z) {
                    this.holders.clear();
                    int childCount = EditableNoteListWrapper.this.mRecyclerView.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        Object childViewHolder = EditableNoteListWrapper.this.mRecyclerView.getChildViewHolder(EditableNoteListWrapper.this.mRecyclerView.getChildAt(i));
                        if (childViewHolder instanceof ViewHolderEditableCallback) {
                            ViewHolderEditableCallback viewHolderEditableCallback = (ViewHolderEditableCallback) childViewHolder;
                            this.holders.add(viewHolderEditableCallback);
                            viewHolderEditableCallback.onAnimationStart(z);
                        }
                    }
                }

                @Override // miuix.view.ActionModeAnimationListener
                public void onUpdate(boolean z, float f) {
                    int childCount = EditableNoteListWrapper.this.mRecyclerView.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        Object childViewHolder = EditableNoteListWrapper.this.mRecyclerView.getChildViewHolder(EditableNoteListWrapper.this.mRecyclerView.getChildAt(i));
                        if (childViewHolder instanceof ViewHolderEditableCallback) {
                            ViewHolderEditableCallback viewHolderEditableCallback = (ViewHolderEditableCallback) childViewHolder;
                            if (!viewHolderEditableCallback.hasAnimationStarted()) {
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
                    EditableNoteListWrapper.this.mCheckedIdStates.clear();
                    EditableNoteListWrapper.this.notifyOnScreenItemsChanged();
                    EditableNoteListWrapper.this.notifyOffScreenItemsChanged();
                }
            };
            this.mChoiceListener = null;
        }

        void startActionMode(MultiChoiceModeListener multiChoiceModeListener) {
            this.mChoiceListener = multiChoiceModeListener;
            EditableNoteListWrapper.this.notifyOnScreenItemsChanged();
            EditableNoteListWrapper.this.notifyOffScreenItemsChanged();
            if (this.mActionModeAnimator == null) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mActionModeAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.setDuration(300L);
                this.mActionModeAnimator.addListener(new Animator.AnimatorListener() { // from class: com.miui.pc.component.EditableNoteListWrapper.PcEditActionMode.2
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        PcEditActionMode.this.mActionModeAnimationListener.onStart(PcEditActionMode.this.isToVisible);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        PcEditActionMode.this.mActionModeAnimationListener.onStop(PcEditActionMode.this.isToVisible);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        PcEditActionMode.this.mActionModeAnimationListener.onStop(PcEditActionMode.this.isToVisible);
                    }
                });
                this.mActionModeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.pc.component.EditableNoteListWrapper.PcEditActionMode.3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        PcEditActionMode.this.mActionModeAnimationListener.onUpdate(PcEditActionMode.this.isToVisible, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
            }
            this.isToVisible = true;
            if (this.mActionModeAnimator.isRunning()) {
                this.mActionModeAnimator.cancel();
            }
            this.mActionModeAnimator.start();
        }

        void finishActionMode() {
            if (this.mActionModeAnimator.isRunning()) {
                this.mActionModeAnimator.cancel();
            }
            this.isToVisible = false;
            this.mActionModeAnimator.start();
        }

        boolean isInMode() {
            return this.isToVisible;
        }
    }

    public boolean isAllCheckedItemSticking() {
        LongSparseLongArray checkedItems = getCheckedItems();
        int size = checkedItems.size();
        for (int i = 0; i < size; i++) {
            ItemCache item = ((NotesListAdapter) this.mAdapter).getItem((int) checkedItems.valueAt(i));
            if (item != null) {
                if (item.getCacheType() == 0) {
                    NoteCache noteCache = (NoteCache) item.getCacheObject();
                    if (noteCache != null && noteCache.getNote().getStickAt() <= 0) {
                        return false;
                    }
                } else {
                    FolderCache folderCache = (FolderCache) item.getCacheObject();
                    if (folderCache != null && folderCache.getFolder().getStickAt() <= 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
