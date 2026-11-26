package com.miui.notes.ui.widget;

import android.R;
import android.content.res.Resources;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.common.tool.UIUtils;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.tool.util.LongSparseLongArray;
import com.miui.notes.ui.view.BaseNoteRecyclerView;
import com.miui.notes.ui.widget.ViewHolderEditableCallback;
import com.xiaomi.passport.utils.LoginPreferenceConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import miui.cloud.CloudPushConstants;
import miuix.appcompat.app.Fragment;
import miuix.view.ActionModeAnimationListener;
import miuix.view.EditActionMode;

/* JADX INFO: compiled from: EditableRecyclerViewWrapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0016\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010 \n\u0002\b\u000b\u0018\u0000 ]*\u0010\b\u0000\u0010\u0001*\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u00032\u00020\u0004:\u0003[\\]B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001aJ\u000e\u0010$\u001a\u00020\"2\u0006\u0010\u0016\u001a\u00020\u001aJ\u000e\u0010%\u001a\u00020\"2\u0006\u0010&\u001a\u00020'J\u0016\u0010(\u001a\u00020\"2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u0016\u001a\u00020\u001aJ\u0016\u0010(\u001a\u00020\"2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u0016\u001a\u00020\u001aJ \u0010(\u001a\u00020\"2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u0016\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020\u001aH\u0002J \u0010,\u001a\u00020\"2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u0016\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020\u001aH\u0002J\u0006\u0010-\u001a\u00020\"J\u0006\u0010.\u001a\u00020\"J\u000e\u00102\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020*J\u000e\u00103\u001a\u00020\u001a2\u0006\u00104\u001a\u000205J\u000e\u0010?\u001a\u00020\"2\u0006\u0010@\u001a\u00020\u001aJ\u0016\u0010A\u001a\u00020\"2\u0006\u0010B\u001a\u00020\u000f2\u0006\u0010C\u001a\u00020DJ\u0016\u0010A\u001a\u00020\"2\u0006\u0010B\u001a\u00020\u000f2\u0006\u0010E\u001a\u00020\u001aJ\u0006\u0010F\u001a\u00020\"J \u0010H\u001a\u00020\"2\u0006\u0010&\u001a\u00020'2\u0006\u0010)\u001a\u00020*2\u0006\u0010I\u001a\u000205H\u0002J\u0018\u0010J\u001a\u00020\"2\u0006\u0010K\u001a\u00020\u00112\u0006\u0010E\u001a\u00020\u001aH\u0002J\u001a\u0010L\u001a\u00020\"2\b\u0010M\u001a\u0004\u0018\u00010\u00112\u0006\u0010N\u001a\u00020*H\u0002J\u000e\u0010O\u001a\u00020\"2\u0006\u0010P\u001a\u00020\u001aJ\b\u0010Q\u001a\u00020\"H\u0002J\u0006\u0010V\u001a\u00020\"J\u000e\u0010W\u001a\u00020\u001a2\u0006\u00104\u001a\u000205J\u0012\u0010X\u001a\u0004\u0018\u00010'2\u0006\u0010I\u001a\u000205H\u0002J\b\u0010Y\u001a\u00020\"H\u0002J\b\u0010Z\u001a\u00020\"H\u0002R\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\"\u0010\u0017\u001a\u0004\u0018\u00010\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\u0013@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u001a8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0011\u0010/\u001a\u00020*8F¢\u0006\u0006\u001a\u0004\b0\u00101R\u0011\u00106\u001a\u0002078F¢\u0006\u0006\u001a\u0004\b8\u00109R4\u0010:\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\r2\u000e\u0010:\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\r8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u0011\u0010G\u001a\u00020\u001a8F¢\u0006\u0006\u001a\u0004\bG\u0010\u001eR\u0017\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00020S8F¢\u0006\u0006\u001a\u0004\bT\u0010U¨\u0006^"}, d2 = {"Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper;", "VH", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/miui/notes/ui/widget/ViewHolderEditableCallback;", "", "recyclerView", "Lcom/miui/notes/ui/view/BaseNoteRecyclerView;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/ui/view/BaseNoteRecyclerView;)V", "mRecyclerView", "getMRecyclerView", "()Lcom/miui/notes/ui/view/BaseNoteRecyclerView;", "mAdapter", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "mChoiceListener", "Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper$MultiChoiceModeListener;", "mActionMode", "Landroid/view/ActionMode;", "checkedItems", "Lcom/miui/notes/tool/util/LongSparseLongArray;", "getCheckedItems", "()Lcom/miui/notes/tool/util/LongSparseLongArray;", "value", "movingItemIds", "getMovingItemIds", "mAllowDel", "", "mAdapterDataObserver", "Landroidx/recyclerview/widget/RecyclerView$AdapterDataObserver;", "isAllItemsChecked", "()Z", "setAllItemsChecked", "(Z)V", "notifyAllItemCheckedState", "", "allChecked", "asyncSetAllItemsChecked", "toggleItemChecked", "view", "Landroid/view/View;", "setItemChecked", "position", "", "quiet", "asyncSetItemChecked", "notifyAllItemsCheckedStateAfterAsync", "clearChoices", "checkedItemCount", "getCheckedItemCount", "()I", "isItemChecked", "isItemIdChecked", "itemId", "", "checkedItemIds", "", "getCheckedItemIds", "()[J", "adapter", "getAdapter", "()Landroidx/recyclerview/widget/RecyclerView$Adapter;", "setAdapter", "(Landroidx/recyclerview/widget/RecyclerView$Adapter;)V", "registerAdapterDataObserverTemp", LoginPreferenceConfig.TYPE_REGISTER, "startActionMode", "listener", "fragment", "Lmiuix/appcompat/app/Fragment;", "isForceDarkmode", "finishActionMode", "isInActionMode", "updateOnScreenCheckedView", "id", "updateActionModeButton1", "mode", "updateCheckStatus", "actionMode", "checkItemCount", "setAllowDelCheckItem", "allow", "confirmCheckedPositionsById", "checkedViewHolders", "", "getCheckedViewHolders", "()Ljava/util/List;", "cacheMovingItemId", "isItemIdMoving", "getViewByItemId", "notifyOffScreenItemsChanged", "notifyOnScreenItemsChanged", "MultiChoiceModeListener", "MultiChoiceModeListenerWrapper", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EditableRecyclerViewWrapper<VH extends RecyclerView.ViewHolder & ViewHolderEditableCallback> {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    private final LongSparseLongArray checkedItems;
    private ActionMode mActionMode;
    private RecyclerView.Adapter<VH> mAdapter;
    private final RecyclerView.AdapterDataObserver mAdapterDataObserver;
    private boolean mAllowDel;
    private MultiChoiceModeListener mChoiceListener;
    private final BaseNoteRecyclerView mRecyclerView;
    private LongSparseLongArray movingItemIds;

    /* JADX INFO: compiled from: EditableRecyclerViewWrapper.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\bÀ\u0006\u0003"}, d2 = {"Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper$MultiChoiceModeListener;", "Landroid/widget/AbsListView$MultiChoiceModeListener;", "onAllItemCheckedStateChanged", "", "mode", "Landroid/view/ActionMode;", "checked", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface MultiChoiceModeListener extends AbsListView.MultiChoiceModeListener {
        void onAllItemCheckedStateChanged(ActionMode mode, boolean checked);
    }

    public EditableRecyclerViewWrapper(BaseNoteRecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        this.mRecyclerView = recyclerView;
        this.checkedItems = new LongSparseLongArray();
        this.mAllowDel = true;
        this.mAdapterDataObserver = new RecyclerView.AdapterDataObserver(this) { // from class: com.miui.notes.ui.widget.EditableRecyclerViewWrapper$mAdapterDataObserver$1
            final /* synthetic */ EditableRecyclerViewWrapper<VH> this$0;

            {
                this.this$0 = this;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                this.this$0.confirmCheckedPositionsById();
            }
        };
    }

    public final BaseNoteRecyclerView getMRecyclerView() {
        return this.mRecyclerView;
    }

    public final LongSparseLongArray getCheckedItems() {
        return this.checkedItems;
    }

    public final LongSparseLongArray getMovingItemIds() {
        return this.movingItemIds;
    }

    public final boolean isAllItemsChecked() {
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        if (adapter == null) {
            return false;
        }
        Intrinsics.checkNotNull(adapter);
        return adapter.getItemCount() == this.checkedItems.size();
    }

    public final void setAllItemsChecked(boolean z) {
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        Intrinsics.checkNotNull(adapter);
        int itemCount = adapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            setItemChecked(i, z, true);
        }
        notifyAllItemCheckedState(z);
    }

    public final void notifyAllItemCheckedState(boolean allChecked) {
        MultiChoiceModeListener multiChoiceModeListener;
        if (this.mActionMode == null || (multiChoiceModeListener = this.mChoiceListener) == null) {
            return;
        }
        Intrinsics.checkNotNull(multiChoiceModeListener);
        multiChoiceModeListener.onAllItemCheckedStateChanged(this.mActionMode, allChecked);
    }

    public final void asyncSetAllItemsChecked(boolean value) {
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        Intrinsics.checkNotNull(adapter);
        int itemCount = adapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            asyncSetItemChecked(i, value, true);
        }
    }

    public final void toggleItemChecked(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        HapticFeedbackTool.INSTANCE.getInstance().performListItemClick(view);
        int childAdapterPosition = this.mRecyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition != -1) {
            setItemChecked(childAdapterPosition, !isItemChecked(childAdapterPosition));
        }
    }

    public final void setItemChecked(View view, boolean value) {
        Intrinsics.checkNotNullParameter(view, "view");
        int childAdapterPosition = this.mRecyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition != -1) {
            setItemChecked(childAdapterPosition, value);
        }
    }

    public final void setItemChecked(int position, boolean value) {
        setItemChecked(position, value, false);
    }

    private final void setItemChecked(int position, boolean value, boolean quiet) {
        MultiChoiceModeListener multiChoiceModeListener;
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        Intrinsics.checkNotNull(adapter);
        long itemId = adapter.getItemId(position);
        if (value != (this.checkedItems.indexOfKey(itemId) >= 0)) {
            if (value) {
                this.checkedItems.put(itemId, position);
            } else {
                this.checkedItems.delete(itemId);
            }
            View viewByItemId = getViewByItemId(itemId);
            if (viewByItemId == null) {
                RecyclerView.Adapter<VH> adapter2 = this.mAdapter;
                Intrinsics.checkNotNull(adapter2);
                adapter2.notifyItemChanged(position);
            } else {
                if (this.mActionMode != null) {
                    updateOnScreenCheckedView(viewByItemId, position, itemId);
                }
                AccessibilityDelegateUtil.setSelectedAccessibilityDelegate(viewByItemId, value);
            }
            if (quiet || this.mActionMode == null || (multiChoiceModeListener = this.mChoiceListener) == null) {
                return;
            }
            Intrinsics.checkNotNull(multiChoiceModeListener);
            multiChoiceModeListener.onItemCheckedStateChanged(this.mActionMode, position, itemId, value);
        }
    }

    private final void asyncSetItemChecked(int position, boolean value, boolean quiet) {
        if (position == -1) {
            return;
        }
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        Intrinsics.checkNotNull(adapter);
        long itemId = adapter.getItemId(position);
        if (value != (this.checkedItems.indexOfKey(itemId) >= 0)) {
            if (value) {
                this.checkedItems.put(itemId, position);
            } else {
                this.checkedItems.delete(itemId);
            }
        }
    }

    public final void notifyAllItemsCheckedStateAfterAsync() {
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        Intrinsics.checkNotNull(adapter);
        int itemCount = adapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            RecyclerView.Adapter<VH> adapter2 = this.mAdapter;
            Intrinsics.checkNotNull(adapter2);
            long itemId = adapter2.getItemId(i);
            View viewByItemId = getViewByItemId(itemId);
            if (viewByItemId == null) {
                RecyclerView.Adapter<VH> adapter3 = this.mAdapter;
                Intrinsics.checkNotNull(adapter3);
                adapter3.notifyItemChanged(i);
            } else if (this.mActionMode != null) {
                updateOnScreenCheckedView(viewByItemId, i, itemId);
            }
        }
    }

    public final void clearChoices() {
        this.checkedItems.clear();
    }

    public final int getCheckedItemCount() {
        return this.checkedItems.size();
    }

    public final boolean isItemChecked(int position) {
        return this.checkedItems.indexOfValue((long) position) >= 0;
    }

    public final boolean isItemIdChecked(long itemId) {
        return this.checkedItems.indexOfKey(itemId) >= 0;
    }

    public final long[] getCheckedItemIds() {
        int size = this.checkedItems.size();
        long[] jArr = new long[this.checkedItems.size()];
        for (int i = 0; i < size; i++) {
            jArr[i] = this.checkedItems.keyAt(i);
        }
        return jArr;
    }

    public final RecyclerView.Adapter<VH> getAdapter() {
        return this.mAdapter;
    }

    public final void setAdapter(RecyclerView.Adapter<VH> adapter) {
        RecyclerView.Adapter<VH> adapter2 = this.mAdapter;
        if (adapter2 != null) {
            try {
                Intrinsics.checkNotNull(adapter2);
                adapter2.unregisterAdapterDataObserver(this.mAdapterDataObserver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mAdapter = adapter;
        if (adapter != null) {
            Intrinsics.checkNotNull(adapter);
            if (!adapter.hasStableIds()) {
                throw new IllegalArgumentException("adapter must has stable id".toString());
            }
            RecyclerView.Adapter<VH> adapter3 = this.mAdapter;
            Intrinsics.checkNotNull(adapter3);
            adapter3.registerAdapterDataObserver(this.mAdapterDataObserver);
        }
        this.mRecyclerView.setAdapter(adapter);
        if (adapter != null) {
            confirmCheckedPositionsById();
        }
    }

    public final void registerAdapterDataObserverTemp(boolean register) {
        if (register) {
            try {
                RecyclerView.Adapter<VH> adapter = this.mAdapter;
                Intrinsics.checkNotNull(adapter);
                adapter.registerAdapterDataObserver(this.mAdapterDataObserver);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            RecyclerView.Adapter<VH> adapter2 = this.mAdapter;
            Intrinsics.checkNotNull(adapter2);
            adapter2.unregisterAdapterDataObserver(this.mAdapterDataObserver);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void startActionMode(MultiChoiceModeListener listener, Fragment fragment) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        MultiChoiceModeListenerWrapper multiChoiceModeListenerWrapper = new MultiChoiceModeListenerWrapper(this, listener, false);
        this.mChoiceListener = multiChoiceModeListenerWrapper;
        fragment.startActionMode(multiChoiceModeListenerWrapper);
    }

    public final void startActionMode(MultiChoiceModeListener listener, boolean isForceDarkmode) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        MultiChoiceModeListenerWrapper multiChoiceModeListenerWrapper = new MultiChoiceModeListenerWrapper(this, listener, isForceDarkmode);
        this.mChoiceListener = multiChoiceModeListenerWrapper;
        this.mRecyclerView.startActionMode(multiChoiceModeListenerWrapper);
    }

    public final void finishActionMode() {
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            Intrinsics.checkNotNull(actionMode);
            actionMode.finish();
        }
    }

    public final boolean isInActionMode() {
        return this.mActionMode != null;
    }

    private final void updateOnScreenCheckedView(View view, int position, long id) {
        Object childViewHolder = this.mRecyclerView.getChildViewHolder(view);
        if (childViewHolder == null || !(childViewHolder instanceof ViewHolderEditableCallback)) {
            return;
        }
        ((ViewHolderEditableCallback) childViewHolder).onUpdateEditable(isInActionMode(), this.checkedItems.indexOfKey(id) >= 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void updateActionModeButton1(ActionMode mode, boolean isForceDarkmode) {
        Intrinsics.checkNotNull(mode, "null cannot be cast to non-null type miuix.view.EditActionMode");
        EditActionMode editActionMode = (EditActionMode) mode;
        if (!UIUtils.isMiuiXSdkSupported()) {
            editActionMode.setButton(16908313, R.string.cancel);
            return;
        }
        if (!isForceDarkmode) {
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
    public final void updateCheckStatus(ActionMode actionMode, int checkItemCount) {
        if (actionMode != null) {
            Resources resources = this.mRecyclerView.getResources();
            if (checkItemCount == 0) {
                actionMode.setTitle(resources.getString(com.miui.notes.R.string.miuix_appcompat_select_item));
            } else {
                String quantityString = resources.getQuantityString(com.miui.notes.R.plurals.miuix_appcompat_items_selected, checkItemCount);
                Intrinsics.checkNotNullExpressionValue(quantityString, "getQuantityString(...)");
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format(quantityString, Arrays.copyOf(new Object[]{Integer.valueOf(checkItemCount)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                actionMode.setTitle(str);
            }
            actionMode.getMenu().setGroupEnabled(0, checkItemCount != 0);
        }
    }

    public final void setAllowDelCheckItem(boolean allow) {
        this.mAllowDel = allow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void confirmCheckedPositionsById() {
        ActionMode actionMode;
        MultiChoiceModeListener multiChoiceModeListener;
        int i = 0;
        boolean z = false;
        while (i < this.checkedItems.size()) {
            long jKeyAt = this.checkedItems.keyAt(i);
            int iValueAt = (int) this.checkedItems.valueAt(i);
            RecyclerView.Adapter<VH> adapter = this.mAdapter;
            Intrinsics.checkNotNull(adapter);
            if (jKeyAt != adapter.getItemId(iValueAt)) {
                int iMax = (int) Math.max(0.0d, iValueAt - 20);
                RecyclerView.Adapter<VH> adapter2 = this.mAdapter;
                Intrinsics.checkNotNull(adapter2);
                int iMin = (int) Math.min(iValueAt + 20, adapter2.getItemCount());
                while (true) {
                    if (iMax < iMin) {
                        RecyclerView.Adapter<VH> adapter3 = this.mAdapter;
                        Intrinsics.checkNotNull(adapter3);
                        if (jKeyAt == adapter3.getItemId(iMax)) {
                            this.checkedItems.put(jKeyAt, iMax);
                            break;
                        }
                        iMax++;
                    } else {
                        if (!this.mAllowDel) {
                            break;
                        }
                        this.checkedItems.delete(jKeyAt);
                        i--;
                        if (this.mActionMode != null && (multiChoiceModeListener = this.mChoiceListener) != null) {
                            Intrinsics.checkNotNull(multiChoiceModeListener);
                            multiChoiceModeListener.onItemCheckedStateChanged(this.mActionMode, iValueAt, jKeyAt, false);
                        }
                        z = true;
                        break;
                    }
                }
                this.mAllowDel = true;
            }
            i++;
        }
        if (!z || (actionMode = this.mActionMode) == null) {
            return;
        }
        Intrinsics.checkNotNull(actionMode);
        actionMode.invalidate();
    }

    public final List<RecyclerView.ViewHolder> getCheckedViewHolders() {
        ArrayList arrayList = new ArrayList();
        int childCount = this.mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRecyclerView.getChildAt(i);
            if (this.checkedItems.get(this.mRecyclerView.getChildItemId(childAt), -1L) != -1) {
                RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(childAt);
                if (childViewHolder.isRecyclable()) {
                    Intrinsics.checkNotNull(childViewHolder);
                    arrayList.add(childViewHolder);
                }
            }
        }
        return arrayList;
    }

    public final void cacheMovingItemId() {
        this.movingItemIds = this.checkedItems.m1744clone();
    }

    public final boolean isItemIdMoving(long itemId) {
        LongSparseLongArray longSparseLongArray = this.movingItemIds;
        if (longSparseLongArray == null) {
            return false;
        }
        Intrinsics.checkNotNull(longSparseLongArray);
        return longSparseLongArray.indexOfKey(itemId) >= 0;
    }

    private final View getViewByItemId(long id) {
        int childCount = this.mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRecyclerView.getChildAt(i);
            if (this.mRecyclerView.getChildViewHolder(childAt).isRecyclable() && this.mRecyclerView.getChildItemId(childAt) == id) {
                return childAt;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyOffScreenItemsChanged() {
        int childCount = this.mRecyclerView.getChildCount();
        HashSet hashSet = new HashSet(childCount);
        for (int i = 0; i < childCount; i++) {
            hashSet.add(Integer.valueOf(this.mRecyclerView.getChildAdapterPosition(this.mRecyclerView.getChildAt(i))));
        }
        RecyclerView.Adapter<VH> adapter = this.mAdapter;
        if (adapter != null) {
            Intrinsics.checkNotNull(adapter);
            int itemCount = adapter.getItemCount();
            for (int i2 = 0; i2 < itemCount; i2++) {
                if (!hashSet.contains(Integer.valueOf(i2))) {
                    RecyclerView.Adapter<VH> adapter2 = this.mAdapter;
                    Intrinsics.checkNotNull(adapter2);
                    adapter2.notifyItemChanged(i2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyOnScreenItemsChanged() {
        int childCount = this.mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRecyclerView.getChildAt(i);
            Object childViewHolder = this.mRecyclerView.getChildViewHolder(childAt);
            if (childViewHolder instanceof ViewHolderEditableCallback) {
                ((ViewHolderEditableCallback) childViewHolder).onUpdateEditable(isInActionMode(), this.checkedItems.indexOfKey(this.mRecyclerView.getChildItemId(childAt)) >= 0);
            }
        }
    }

    /* JADX INFO: compiled from: EditableRecyclerViewWrapper.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J(\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0004H\u0016J\u001a\u0010\u001b\u001a\u00020\u00102\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u001a\u001a\u00020\u0004H\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper$MultiChoiceModeListenerWrapper;", "Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper$MultiChoiceModeListener;", "mWrapped", "isForceDarkMode", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper;Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper$MultiChoiceModeListener;Z)V", "mIsForceDarkmode", "mActionModeAnimationListener", "Lmiuix/view/ActionModeAnimationListener;", "onPrepareActionMode", "mode", "Landroid/view/ActionMode;", "menu", "Landroid/view/Menu;", "onDestroyActionMode", "", "onCreateActionMode", "onActionItemClicked", CloudPushConstants.XML_ITEM, "Landroid/view/MenuItem;", "onItemCheckedStateChanged", "position", "", "id", "", "checked", "onAllItemCheckedStateChanged", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class MultiChoiceModeListenerWrapper implements MultiChoiceModeListener {
        private final ActionModeAnimationListener mActionModeAnimationListener;
        private boolean mIsForceDarkmode;
        private final MultiChoiceModeListener mWrapped;
        final /* synthetic */ EditableRecyclerViewWrapper<VH> this$0;

        public MultiChoiceModeListenerWrapper(final EditableRecyclerViewWrapper editableRecyclerViewWrapper, MultiChoiceModeListener mWrapped, boolean z) {
            Intrinsics.checkNotNullParameter(mWrapped, "mWrapped");
            this.this$0 = editableRecyclerViewWrapper;
            this.mWrapped = mWrapped;
            this.mActionModeAnimationListener = new ActionModeAnimationListener() { // from class: com.miui.notes.ui.widget.EditableRecyclerViewWrapper$MultiChoiceModeListenerWrapper$mActionModeAnimationListener$1
                private Set<ViewHolderEditableCallback> holders = new HashSet();

                public final Set<ViewHolderEditableCallback> getHolders() {
                    return this.holders;
                }

                public final void setHolders(Set<ViewHolderEditableCallback> set) {
                    Intrinsics.checkNotNullParameter(set, "<set-?>");
                    this.holders = set;
                }

                @Override // miuix.view.ActionModeAnimationListener
                public void onStart(boolean animateToVisible) {
                    this.holders.clear();
                    int childCount = editableRecyclerViewWrapper.getMRecyclerView().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        Object childViewHolder = editableRecyclerViewWrapper.getMRecyclerView().getChildViewHolder(editableRecyclerViewWrapper.getMRecyclerView().getChildAt(i));
                        if (childViewHolder instanceof ViewHolderEditableCallback) {
                            ViewHolderEditableCallback viewHolderEditableCallback = (ViewHolderEditableCallback) childViewHolder;
                            this.holders.add(viewHolderEditableCallback);
                            viewHolderEditableCallback.onAnimationStart(animateToVisible);
                        }
                    }
                }

                @Override // miuix.view.ActionModeAnimationListener
                public void onUpdate(boolean animateToVisible, float progress) {
                    int childCount = editableRecyclerViewWrapper.getMRecyclerView().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        Object childViewHolder = editableRecyclerViewWrapper.getMRecyclerView().getChildViewHolder(editableRecyclerViewWrapper.getMRecyclerView().getChildAt(i));
                        if (childViewHolder instanceof ViewHolderEditableCallback) {
                            ViewHolderEditableCallback viewHolderEditableCallback = (ViewHolderEditableCallback) childViewHolder;
                            if (!viewHolderEditableCallback.getMStarted()) {
                                viewHolderEditableCallback.onAnimationStart(animateToVisible);
                            }
                            viewHolderEditableCallback.onAnimationUpdate(animateToVisible, progress);
                            this.holders.add(viewHolderEditableCallback);
                        }
                    }
                }

                @Override // miuix.view.ActionModeAnimationListener
                public void onStop(boolean animateToVisible) {
                    Iterator<ViewHolderEditableCallback> it = this.holders.iterator();
                    while (it.hasNext()) {
                        it.next().onAnimationStop(animateToVisible);
                    }
                    this.holders.clear();
                    if (animateToVisible) {
                        return;
                    }
                    editableRecyclerViewWrapper.notifyOffScreenItemsChanged();
                }
            };
            this.mIsForceDarkmode = z;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(menu, "menu");
            return this.mWrapped.onPrepareActionMode(mode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode mode) {
            Intrinsics.checkNotNullParameter(mode, "mode");
            ((EditableRecyclerViewWrapper) this.this$0).mActionMode = null;
            this.mWrapped.onDestroyActionMode(mode);
            this.this$0.getCheckedItems().clear();
            this.this$0.notifyOnScreenItemsChanged();
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(menu, "menu");
            mode.setTitle(com.miui.notes.R.string.miuix_appcompat_select_item);
            if (!this.mWrapped.onCreateActionMode(mode, menu)) {
                return false;
            }
            this.this$0.updateActionModeButton1(mode, this.mIsForceDarkmode);
            EditableRecyclerViewWrapper<VH> editableRecyclerViewWrapper = this.this$0;
            editableRecyclerViewWrapper.updateCheckStatus(mode, editableRecyclerViewWrapper.getCheckedItems().size());
            this.this$0.notifyOnScreenItemsChanged();
            this.this$0.notifyOffScreenItemsChanged();
            ((EditableRecyclerViewWrapper) this.this$0).mActionMode = mode;
            Object obj = ((EditableRecyclerViewWrapper) this.this$0).mActionMode;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type miuix.view.EditActionMode");
            ((EditActionMode) obj).addAnimationListener(this.mActionModeAnimationListener);
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(item, "item");
            return this.mWrapped.onActionItemClicked(mode, item);
        }

        @Override // android.widget.AbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            Intrinsics.checkNotNullParameter(mode, "mode");
            EditableRecyclerViewWrapper<VH> editableRecyclerViewWrapper = this.this$0;
            editableRecyclerViewWrapper.updateCheckStatus(mode, editableRecyclerViewWrapper.getCheckedItems().size());
            this.mWrapped.onItemCheckedStateChanged(mode, position, id, checked);
        }

        @Override // com.miui.notes.ui.widget.EditableRecyclerViewWrapper.MultiChoiceModeListener
        public void onAllItemCheckedStateChanged(ActionMode mode, boolean checked) {
            EditableRecyclerViewWrapper<VH> editableRecyclerViewWrapper = this.this$0;
            editableRecyclerViewWrapper.updateCheckStatus(((EditableRecyclerViewWrapper) editableRecyclerViewWrapper).mActionMode, this.this$0.getCheckedItems().size());
            this.mWrapped.onAllItemCheckedStateChanged(((EditableRecyclerViewWrapper) this.this$0).mActionMode, checked);
        }
    }
}
