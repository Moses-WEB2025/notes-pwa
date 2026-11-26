package com.miui.notes.feature.folder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Outline;
import android.os.Bundle;
import android.os.Handler;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.hardware.DataSpace;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.gridlayoutmanager.NoteGridLayoutManager;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.notelist.BaseNoteListAdapter;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.BitmapCacheManager;
import com.miui.notes.tool.DoubleClickDetector;
import com.miui.notes.tool.util.AsyncTaskWithProgressDialog;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.ui.PhoneNumberCacheHelper;
import com.miui.notes.ui.fragment.DialogManagedFragment;
import com.miui.notes.ui.fragment.ModuleFragment;
import com.miui.notes.ui.view.BaseNoteRecyclerView;
import com.miui.notes.ui.widget.EditableRecyclerViewWrapper;
import miuix.animation.Folme;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;
import miuix.miuixbasewidget.widget.EmptyStateView;
import miuix.view.EditActionMode;

/* JADX INFO: loaded from: classes4.dex */
public class TrashDialogFragment extends ModuleFragment {
    private static final int REQUEST_CODE_QUERY_TRASH_NOTE = 1;
    private static final String TAG = "TrashDialogFragment";
    private EmptyStateView blankView;
    private boolean isDestroyView;
    private ActionBar mActionBar;
    protected ActionMode mActionMode;
    private Activity mActivity;
    private BaseNoteListAdapter mAdapter;
    protected BindContext mBindContext;
    private FolderDialogController mController;
    protected AlertDialog mDeleteNotesDialog;
    protected Handler mHandler;
    private BaseNoteRecyclerView mRecyclerView;
    protected EditableRecyclerViewWrapper mRecyclerViewWrapper;
    protected NoteGridLayoutManager mRvLayoutManager;
    private View mTrashContent;
    private RecyclerView.ItemAnimator mDefaultAnimator = null;
    private final int BOUNDARY_COUNT = 3;
    private View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.7
        private Long lastEnterEditTime = null;

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (TrashDialogFragment.this.mRecyclerViewWrapper.isInActionMode()) {
                if (!view.isSelected()) {
                    TrashDialogFragment.this.mRecyclerViewWrapper.toggleItemChecked(view);
                    return;
                } else {
                    view.setSelected(false);
                    return;
                }
            }
            if (TrashDialogFragment.this.mAdapter != null) {
                if (this.lastEnterEditTime != null && System.currentTimeMillis() - this.lastEnterEditTime.longValue() <= 400) {
                    this.lastEnterEditTime = Long.valueOf(System.currentTimeMillis());
                    return;
                }
                Folme.useAt(view).touch().setTouchUp();
                this.lastEnterEditTime = Long.valueOf(System.currentTimeMillis());
                ItemCache item = TrashDialogFragment.this.mAdapter.getItem(TrashDialogFragment.this.mRecyclerView.getChildLayoutPosition(view));
                if (item == null || item.getCacheType() != 0) {
                    return;
                }
                NoteCache noteCache = (NoteCache) item.getCacheObject();
                if (noteCache.getNote().getFolderId() == -3) {
                    TrashDialogFragment.this.trashFolderItemClick(noteCache.getNote().getId());
                }
            }
        }
    };
    private View.OnLongClickListener mItemLongClickListener = new View.OnLongClickListener() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.8
        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            if (TrashDialogFragment.this.mActionMode != null) {
                return false;
            }
            view.setSelected(true);
            TrashDialogFragment.this.mRecyclerViewWrapper.setItemChecked(view, true);
            TrashDialogFragment.this.mRecyclerViewWrapper.startActionMode(TrashDialogFragment.this.new EditModeCallback(), TrashDialogFragment.this);
            return true;
        }
    };

    private interface EditModeCallbackHandler {
        boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

        boolean onCreateActionMode(ActionMode actionMode, Menu menu);

        void onDestroyActionMode(ActionMode actionMode);

        void updateActionItems();
    }

    protected int getLayoutId() {
        return R.layout.pad_folder_dialog_trash_layout;
    }

    @Override // com.miui.notes.ui.fragment.ModuleFragment
    public String getSearchContent() {
        return null;
    }

    public void setController(FolderDialogController folderDialogController) {
        this.mController = folderDialogController;
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = getActivity();
        setThemeRes(getThemeRes());
        this.mBindContext = new BindContext();
        this.mHandler = new Handler();
        initAdapterIfNeeded();
    }

    public int getThemeRes() {
        return !RomUtils.isPadDevice() ? R.style.NoteTheme_CommonPhoneFolderDialogFragment : R.style.NoteTheme_CommonFolderDialogFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActionBar actionBar = getActionBar();
        this.mActionBar = actionBar;
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            this.mActionBar.setDisplayHomeAsUpEnabled(false);
            this.mActionBar.setExpandState(0);
            this.mActionBar.setResizable(false);
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(R.drawable.miui_action_bar_back);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    TrashDialogFragment.this.mController.onBackPressed();
                }
            });
            imageView.setContentDescription(getResources().getString(R.string.actionbar_home_description));
            this.mActionBar.setStartView(imageView);
            this.mActionBar.setTitle(getString(R.string.trash_folder_name));
            this.mActionBar.setHomeActionContentDescription(getString(R.string.trash_folder_name));
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        initView(viewInflate);
        smallWindowAdapter();
        return viewInflate;
    }

    private void initView(View view) {
        this.isDestroyView = false;
        this.mTrashContent = view.findViewById(R.id.trash_content);
        TextView textView = (TextView) view.findViewById(R.id.pad_folder_dialog_trash_remind);
        textView.setText(String.format(getResources().getString(R.string.trash_folder_reminder_new), 30));
        textView.requestFocus();
        BaseNoteRecyclerView baseNoteRecyclerView = (BaseNoteRecyclerView) view.findViewById(R.id.pad_folder_dialog_trash_rv);
        this.mRecyclerView = baseNoteRecyclerView;
        baseNoteRecyclerView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.2
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                outline.setRoundRect(0, 0, view2.getWidth(), view2.getHeight(), TrashDialogFragment.this.getResources().getDimensionPixelOffset(R.dimen.common_note_list_item_bg_radius));
            }
        });
        this.mRecyclerView.setClipToOutline(true);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mDefaultAnimator = this.mRecyclerView.getItemAnimator();
        this.mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        this.mRecyclerViewWrapper = new EditableRecyclerViewWrapper(this.mRecyclerView);
        initAdapterIfNeeded();
        this.mRecyclerViewWrapper.setAdapter(this.mAdapter);
        this.mBindContext.setEditableRecyclerViewWrapper(this.mRecyclerViewWrapper);
        this.mRecyclerView.setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this.mRecyclerView) { // from class: com.miui.notes.feature.folder.TrashDialogFragment.3
            int previousItemPosition = 0;

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view2, AccessibilityEvent accessibilityEvent) {
                if (accessibilityEvent.getEventType() == 32768) {
                    int childAdapterPosition = TrashDialogFragment.this.mRecyclerView.getChildAdapterPosition(view2);
                    scrollToPosition(childAdapterPosition, TrashDialogFragment.this.mRecyclerView.getAdapter().getItemCount());
                    this.previousItemPosition = childAdapterPosition;
                    return true;
                }
                return super.onRequestSendAccessibilityEvent(viewGroup, view2, accessibilityEvent);
            }

            private void scrollToPosition(int i, int i2) {
                int i3 = this.previousItemPosition;
                if (i3 < i) {
                    scrollToNextPosition(i, i2);
                } else if (i3 > i) {
                    scrollToPreviousPosition(i);
                }
            }

            private void scrollToNextPosition(int i, int i2) {
                int i3 = i + 3;
                int i4 = i2 - 1;
                if (i3 <= i4) {
                    TrashDialogFragment.this.mRecyclerView.smoothScrollToPosition(i3);
                } else if (i + 1 <= i4) {
                    TrashDialogFragment.this.mRecyclerView.smoothScrollToPosition(i2);
                }
            }

            private void scrollToPreviousPosition(int i) {
                int i2 = i - 3;
                if (i2 >= 0) {
                    TrashDialogFragment.this.mRecyclerView.smoothScrollToPosition(i2);
                } else if (i - 1 >= 0) {
                    TrashDialogFragment.this.mRecyclerView.smoothScrollToPosition(0);
                }
            }
        });
        updateRecyclerViewGridMode();
        new DoubleClickDetector.DoubleClickListener() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.4
            @Override // com.miui.notes.tool.DoubleClickDetector.DoubleClickListener
            public void onDoubleClick(MotionEvent motionEvent) {
                TrashDialogFragment.this.mRecyclerView.scrollToPosition(0);
            }
        };
        this.blankView = (EmptyStateView) view.findViewById(R.id.trash_blank_view);
        queryTrashNote();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        smallWindowAdapter();
        checkAndCleanExpiredNotes();
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        smallWindowAdapter();
    }

    private void checkAndCleanExpiredNotes() {
        new Thread(new Runnable() { // from class: com.miui.notes.feature.folder.TrashDialogFragment$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkAndCleanExpiredNotes$1();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAndCleanExpiredNotes$1() {
        try {
            Context context = getContext();
            if (context != null) {
                NoteStore.hideExpiredTrashNotes(context);
                Handler handler = this.mHandler;
                if (handler != null) {
                    handler.post(new Runnable() { // from class: com.miui.notes.feature.folder.TrashDialogFragment$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$checkAndCleanExpiredNotes$0();
                        }
                    });
                }
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "checkAndCleanExpiredNotes error: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAndCleanExpiredNotes$0() {
        if (!isAdded() || isDetached() || getActivity() == null) {
            return;
        }
        try {
            queryTrashNote();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "refresh queryTrashNote error: " + e.getMessage());
        }
    }

    private void queryTrashNote() {
        NoteStore.query(getLoaderManager(), -3L, this.mBindContext, 1, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                TrashDialogFragment.this.onNotesLoadFinished(cursor);
            }
        });
    }

    protected void onNotesLoadFinished(Cursor cursor) {
        if (this.mActivity != null && !this.isDestroyView) {
            BaseNoteListAdapter baseNoteListAdapter = this.mAdapter;
            if (baseNoteListAdapter != null) {
                baseNoteListAdapter.notifyDataSetChanged();
                this.mAdapter.setCursor(cursor);
                this.blankView.setVisibility((cursor == null || cursor.getCount() == 0) ? 0 : this.blankView.getVisibility());
                if (this.mRecyclerView.getItemAnimator() == null) {
                    getView().postDelayed(new Runnable() { // from class: com.miui.notes.feature.folder.TrashDialogFragment$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onNotesLoadFinished$2();
                        }
                    }, 100L);
                }
            }
            if (cursor != null) {
                cursor.moveToFirst();
            }
        }
        cachePhoneNumberResource();
        BitmapCacheManager.resize(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNotesLoadFinished$2() {
        this.mRecyclerView.setItemAnimator(this.mDefaultAnimator);
    }

    protected void cachePhoneNumberResource() {
        if (PhoneNumberCacheHelper.hasPhoneNumberResourceCached()) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.6
            @Override // java.lang.Runnable
            public void run() {
                PhoneNumberCacheHelper.cachePhoneNumberResource();
            }
        });
    }

    private void initAdapterIfNeeded() {
        if (this.mAdapter == null) {
            BaseNoteListAdapter baseNoteListAdapter = new BaseNoteListAdapter(false);
            this.mAdapter = baseNoteListAdapter;
            baseNoteListAdapter.setOnClickListener(this.mItemClickListener);
            this.mAdapter.setOnLongClickListener(this.mItemLongClickListener);
            this.mAdapter.setBindContext(this.mBindContext);
            this.mAdapter.setTrashMode(true);
        }
    }

    private void updateRecyclerViewGridMode() {
        NoteGridLayoutManager noteGridLayoutManager = new NoteGridLayoutManager(1, 1);
        this.mRvLayoutManager = noteGridLayoutManager;
        this.mRecyclerView.setLayoutManager(noteGridLayoutManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trashFolderItemClick(final long j) {
        FragmentActivity activity = getActivity();
        String string = getString(R.string.alert_title_restore);
        String string2 = getString(R.string.alert_message_restore);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(string);
        builder.setMessage(string2);
        builder.setPositiveButton(R.string.dialog_button_confirm, new DialogInterface.OnClickListener() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                TrashDialogFragment.this.restoreClickedItem(j);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        manageDialog(builder.show());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreClickedItem(long j) {
        this.mAdapter.markManualModifyDataTime();
        new NoteRestoreTask(this, new long[]{j}).execute(new Void[0]);
    }

    protected static abstract class NoteOperationTask extends AsyncTaskWithProgressDialog<DialogManagedFragment, Void, Void, Integer> {
        protected long[] iIds;
        private int iMsgResId;

        public NoteOperationTask(DialogManagedFragment dialogManagedFragment, long[] jArr, int i) {
            super(dialogManagedFragment);
            this.iIds = jArr;
            this.iMsgResId = i;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public void onPostWork(Activity activity, Integer num) {
            super.onPostWork(activity, num);
            DialogManagedFragment fragment = getFragment();
            if (fragment instanceof TrashDialogFragment) {
                ((TrashDialogFragment) fragment).mRecyclerViewWrapper.finishActionMode();
            }
        }

        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        protected Dialog onCreateDialog(Activity activity) {
            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(activity.getString(this.iMsgResId));
            progressDialog.setCancelable(false);
            return progressDialog;
        }
    }

    protected class EditModeCallback implements EditableRecyclerViewWrapper.MultiChoiceModeListener {
        private TrashFolderEditModeCallbackHandler mCallbackHandler;

        public EditModeCallback() {
            this.mCallbackHandler = new TrashFolderEditModeCallbackHandler();
        }

        @Override // com.miui.notes.ui.widget.EditableRecyclerViewWrapper.MultiChoiceModeListener
        public void onAllItemCheckedStateChanged(ActionMode actionMode, boolean z) {
            TrashDialogFragment.this.updateActionModeButton2(actionMode);
            this.mCallbackHandler.updateActionItems();
        }

        @Override // android.widget.AbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z) {
            TrashDialogFragment.this.updateActionModeButton2(actionMode);
            this.mCallbackHandler.updateActionItems();
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            FragmentActivity activity = TrashDialogFragment.this.getActivity();
            if (activity != null && !UIUtils.isSmallWindowMode(activity) && ((RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(activity)) || RomUtils.isPhoneDevice())) {
                activity.getWindow().addFlags(DataSpace.RANGE_FULL);
            }
            TrashDialogFragment.this.updateActionModeButton2(actionMode);
            TrashDialogFragment.this.mActionMode = actionMode;
            if (TrashDialogFragment.this.mActionStateListener != null) {
                TrashDialogFragment.this.mActionStateListener.onEnterMultiMode();
            }
            return this.mCallbackHandler.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            this.mCallbackHandler.updateActionItems();
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == 16908313) {
                actionMode.finish();
            } else if (menuItem.getItemId() == 16908314) {
                TrashDialogFragment.this.mRecyclerViewWrapper.setAllItemsChecked(!TrashDialogFragment.this.mRecyclerViewWrapper.isAllItemsChecked());
            }
            return this.mCallbackHandler.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            FragmentActivity activity = TrashDialogFragment.this.getActivity();
            if (activity != null && !UIUtils.isSmallWindowMode(activity) && ((RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(activity)) || RomUtils.isPhoneDevice())) {
                activity.getWindow().clearFlags(DataSpace.RANGE_FULL);
            }
            TrashDialogFragment.this.mActionMode = null;
            if (TrashDialogFragment.this.mActionStateListener != null) {
                TrashDialogFragment.this.mActionStateListener.onExitMultiMode();
            }
            this.mCallbackHandler.onDestroyActionMode(actionMode);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void updateActionModeButton2(ActionMode actionMode) {
        EditActionMode editActionMode = (EditActionMode) actionMode;
        if (UIUtils.isMiuiXSdkSupported()) {
            int i = DisplayUtils.isNightMode() ? R.drawable.miuix_action_icon_deselect_all_dark : R.drawable.miuix_action_icon_deselect_all_light;
            int i2 = DisplayUtils.isNightMode() ? R.drawable.miuix_action_icon_select_all_dark : R.drawable.miuix_action_icon_select_all_light;
            if (!this.mRecyclerViewWrapper.isAllItemsChecked()) {
                i = i2;
            }
            editActionMode.setButton(16908314, (CharSequence) null, i);
            return;
        }
        editActionMode.setButton(16908314, this.mRecyclerViewWrapper.isAllItemsChecked() ? R.string.miuix_appcompat_action_mode_deselect_all : R.string.miuix_appcompat_action_mode_select_all);
    }

    private class TrashFolderEditModeCallbackHandler implements EditModeCallbackHandler {
        @Override // com.miui.notes.feature.folder.TrashDialogFragment.EditModeCallbackHandler
        public void onDestroyActionMode(ActionMode actionMode) {
        }

        @Override // com.miui.notes.feature.folder.TrashDialogFragment.EditModeCallbackHandler
        public void updateActionItems() {
        }

        private TrashFolderEditModeCallbackHandler() {
        }

        @Override // com.miui.notes.feature.folder.TrashDialogFragment.EditModeCallbackHandler
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            TrashDialogFragment.this.getMenuInflater().inflate(R.menu.trash_list_options, menu);
            return true;
        }

        @Override // com.miui.notes.feature.folder.TrashDialogFragment.EditModeCallbackHandler
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (TrashDialogFragment.this.mRecyclerView != null) {
                TrashDialogFragment.this.mRecyclerView.stopScroll();
            }
            int itemId = menuItem.getItemId();
            if (itemId == R.id.delete_forever) {
                TrashDialogFragment.this.showDeleteNotesDialog(true);
            } else {
                if (itemId != R.id.restore) {
                    return false;
                }
                TrashDialogFragment.this.restoreCheckedItems();
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreCheckedItems() {
        this.mAdapter.markManualModifyDataTime();
        new NoteRestoreTask(this, this.mRecyclerViewWrapper.getCheckedItemIds()).execute(new Void[0]);
    }

    private static class NoteRestoreTask extends NoteOperationTask {
        public NoteRestoreTask(TrashDialogFragment trashDialogFragment, long[] jArr) {
            super(trashDialogFragment, jArr, R.string.dlg_restore_message);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public Integer doBackgroundWork(Context context, Void... voidArr) {
            Logger.INSTANCE.d(getClass().getSimpleName(), "doBackgroundWork/delete for " + this.iIds + ",trash restore task do!!");
            return Integer.valueOf(NoteStore.restore(context, this.iIds));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteNotesDialog(final boolean z) {
        String quantityString;
        FragmentActivity activity = getActivity();
        String string = getString(R.string.new_alert_title_delete);
        if (Utils.isUseChinese()) {
            quantityString = getString(R.string.alert_message_delete, Utils.getAllItemTerms(this.mRecyclerViewWrapper.getCheckedItemCount(), 0));
        } else {
            int checkedItemCount = this.mRecyclerViewWrapper.getCheckedItemCount();
            quantityString = getResources().getQuantityString(R.plurals.alert_message_delete, checkedItemCount, Integer.valueOf(checkedItemCount));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, 2131951644);
        builder.setTitle(string);
        builder.setMessage(quantityString);
        builder.setPositiveButton(R.string.menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.10
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                TrashDialogFragment.this.deleteCheckedItems(z);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialog = this.mDeleteNotesDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDeleteNotesDialog.dismiss();
        }
        AlertDialog alertDialogShow = builder.show();
        this.mDeleteNotesDialog = alertDialogShow;
        manageDialog(alertDialogShow);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteCheckedItems(boolean z) {
        this.mAdapter.markManualModifyDataTime();
        new NoteDeleteTask(this, this.mRecyclerViewWrapper.getCheckedItemIds(), z).execute(new Void[0]);
    }

    private static class NoteDeleteTask extends NoteOperationTask {
        private final boolean iForce;

        public NoteDeleteTask(TrashDialogFragment trashDialogFragment, long[] jArr, boolean z) {
            super(trashDialogFragment, jArr, R.string.dlg_delete_message);
            this.iForce = z;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public Integer doBackgroundWork(Context context, Void... voidArr) {
            Logger.INSTANCE.d(getClass().getSimpleName(), "doBackgroundWork/delete for " + this.iIds + ",iForce:" + this.iForce);
            return Integer.valueOf(NoteStore.delete(context, this.iIds, this.iForce));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.feature.folder.TrashDialogFragment.NoteOperationTask, com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public void onPostWork(Activity activity, Integer num) {
            super.onPostWork(activity, num);
            checkHasExcerptingNoteAndStop();
            AudioUtils.playDeleteRingtone();
        }

        private void checkHasExcerptingNoteAndStop() {
            if (FloatWindowManagerAgent.isContinuousExcerpt()) {
                for (int i = 0; i < this.iIds.length; i++) {
                    if (this.iIds[i] == FloatWindowManagerAgent.getExcerptId()) {
                        FloatWindowManagerAgent.stopContinuousExcerpt();
                    }
                }
            }
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.isDestroyView = true;
        this.mRecyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.11
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                TrashDialogFragment.this.mRecyclerViewWrapper.setAdapter(null);
            }
        });
        this.mRecyclerViewWrapper.finishActionMode();
        getLoaderManager().destroyLoader(1);
        super.onDestroyView();
    }

    @Override // com.miui.notes.ui.fragment.ModuleFragment
    public boolean onBackPressed() {
        EditableRecyclerViewWrapper editableRecyclerViewWrapper = this.mRecyclerViewWrapper;
        if (editableRecyclerViewWrapper == null || !editableRecyclerViewWrapper.isInActionMode()) {
            return false;
        }
        this.mRecyclerViewWrapper.finishActionMode();
        return true;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    private void smallWindowAdapter() {
        View view = this.mTrashContent;
        if (view != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.feature.folder.TrashDialogFragment.12
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view2);
                    Activity activity = UIUtils.getActivity(TrashDialogFragment.this.mTrashContent.getContext());
                    if (rootWindowInsets != null && activity != null) {
                        Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                        if (UIUtils.isInMultiWindowMode(activity)) {
                            TrashDialogFragment.this.mTrashContent.setPadding(0, 0, insetsIgnoringVisibility.right, 0);
                            return windowInsetsCompat;
                        }
                        TrashDialogFragment.this.mTrashContent.setPadding(0, 0, 0, 0);
                    }
                    return windowInsetsCompat;
                }
            });
        }
    }
}
