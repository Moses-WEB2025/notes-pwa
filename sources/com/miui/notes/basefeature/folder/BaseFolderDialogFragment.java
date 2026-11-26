package com.miui.notes.basefeature.folder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Trace;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.fragment.app.FragmentActivity;
import androidx.hardware.DataSpace;
import androidx.loader.content.Loader;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.gridlayoutmanager.NoteGridLayoutManager;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.component.DeleteFolderDialog;
import com.miui.notes.component.RenameFolderDialog;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.feature.folder.CreateFolderDialog;
import com.miui.notes.feature.folder.FolderDialogActivity;
import com.miui.notes.feature.folder.FolderDialogController;
import com.miui.notes.model.FolderModel;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.ItemStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.util.AsyncTaskWithProgressDialog;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.tool.util.LongSparseLongArray;
import com.miui.notes.ui.fragment.DialogManagedFragment;
import com.miui.notes.ui.fragment.ModuleFragment;
import com.miui.notes.ui.view.BaseNoteRecyclerView;
import com.miui.notes.ui.widget.EditableRecyclerViewWrapper;
import java.util.ArrayList;
import java.util.List;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseFolderDialogFragment extends ModuleFragment {
    private static final int QUERY_FOLDER_INFO_WHAT = 256;
    public static final int REQUEST_CODE_QUERY_ALL = 10;
    public static final int REQUEST_CODE_QUERY_FOLDER = 12;
    protected static final int REQUEST_CODE_QUERY_NOTE = 11;
    public static final int REQUEST_CODE_UPDATE_FOLDER_NAME = 13;
    private static String TAG = "BaseFolderDialogFragment";
    protected boolean isDestroyView;
    protected ActionBar mActionBar;
    protected ActionMode mActionMode;
    protected Activity mActivity;
    protected BaseFolderDialogAdapter mAdapter;
    protected BindContext mBindContext;
    protected FolderDialogController mController;
    private CreateFolderDialog mCreateFolderDialog;
    private AlertDialog mDeleteFolderDialog;
    protected AlertDialog mDeleteNotesDialog;
    protected Handler mHandler;
    protected BaseNoteRecyclerView mRecyclerView;
    protected EditableRecyclerViewWrapper mRecyclerViewWrapper;
    protected View mRootView;
    protected NoteGridLayoutManager mRvLayoutManager;
    protected long mFolderId = NoteConfig.getDefaultFolderId();
    private QueryFolderInfoHandler handlerQueryFolderInfo = new QueryFolderInfoHandler();
    protected View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment.3
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (BaseFolderDialogFragment.this.mRecyclerViewWrapper.isInActionMode()) {
                if (!view.isSelected()) {
                    BaseFolderDialogFragment.this.mRecyclerViewWrapper.toggleItemChecked(view);
                    return;
                } else {
                    view.setSelected(false);
                    return;
                }
            }
            if (BaseFolderDialogFragment.this.mAdapter == null || Utils.isFastClick(view.getId(), 400L)) {
                return;
            }
            FolderModel item = BaseFolderDialogFragment.this.mAdapter.getItem(BaseFolderDialogFragment.this.mRecyclerView.getChildLayoutPosition(view));
            if (item != null) {
                BaseFolderDialogFragment.this.mController.finishWithResult(item.getId());
            }
        }
    };
    protected View.OnLongClickListener mItemLongClickListener = new View.OnLongClickListener() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment.4
        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            if (BaseFolderDialogFragment.this.mActionMode != null || view == null || BaseFolderDialogFragment.this.mRecyclerViewWrapper == null) {
                return false;
            }
            view.setSelected(true);
            BaseFolderDialogFragment.this.mRecyclerViewWrapper.setItemChecked(view, true);
            BaseFolderDialogFragment.this.changeActionMode(true);
            BaseFolderDialogFragment.this.mRecyclerViewWrapper.startActionMode(BaseFolderDialogFragment.this.new EditModeCallback(), BaseFolderDialogFragment.this);
            return true;
        }
    };

    private interface EditModeCallbackHandler {
        boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

        boolean onCreateActionMode(ActionMode actionMode, Menu menu);

        void onDestroyActionMode(ActionMode actionMode);

        void updateActionItems();
    }

    protected abstract void changeActionMode(boolean z);

    public abstract int getLayoutId();

    @Override // com.miui.notes.ui.fragment.ModuleFragment
    public String getSearchContent() {
        return null;
    }

    public int getThemeRes() {
        return R.style.NoteTheme_PadFolderDialogFragment;
    }

    public abstract void initActionBar();

    public abstract void initView(View view);

    public abstract void setAccessibility(boolean z);

    protected abstract void updateActionModeButton1(ActionMode actionMode);

    protected abstract void updateActionModeButton2(ActionMode actionMode);

    protected abstract void updateList(List<FolderModel> list, int i);

    public void setController(FolderDialogController folderDialogController) {
        this.mController = folderDialogController;
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mFolderId = arguments.getLong(FolderDialogActivity.FOLDER_LIST_SELECT_ID);
        }
        this.mActivity = getActivity();
        setThemeRes(getThemeRes());
        this.mBindContext = new BindContext();
        this.mHandler = new Handler();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initActionBar();
    }

    protected void showCreateFolderDialog() {
        Logger.INSTANCE.i(TAG, "showCreateFolderDialog");
        dismissCreateFolderDialog();
        if (this.mActivity == null) {
            Logger.INSTANCE.i(TAG, "showCreateFolderDialog/mActivity is null");
            return;
        }
        CreateFolderDialog createFolderDialog = new CreateFolderDialog(this.mActivity);
        this.mCreateFolderDialog = createFolderDialog;
        createFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$showCreateFolderDialog$0(dialogInterface);
            }
        });
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            return;
        }
        Trace.beginAsyncSection("Fluency_Test:ignore_range", 0);
        this.mCreateFolderDialog.show();
        View decorView = this.mCreateFolderDialog.getWindow() != null ? this.mCreateFolderDialog.getWindow().getDecorView() : null;
        if (decorView != null) {
            decorView.getViewTreeObserver().addOnDrawListener(new AnonymousClass1());
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Trace.endAsyncSection("Fluency_Test:ignore_range", 0);
                }
            });
        }
        manageDialog(this.mCreateFolderDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCreateFolderDialog$0(DialogInterface dialogInterface) {
        if (isDetached() || !isAdded() || getActivity() == null) {
            Logger.INSTANCE.i(TAG, "showCreateFolderDialog/onDismiss,abort");
            return;
        }
        long folderId = ((CreateFolderDialog) dialogInterface).getFolderId();
        Logger.INSTANCE.i(TAG, "showCreateFolderDialog/onDismiss --" + folderId);
        queryFolderInfo();
        if (folderId > 0) {
            refreshFolderInfo();
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.basefeature.folder.BaseFolderDialogFragment$1, reason: invalid class name */
    class AnonymousClass1 implements ViewTreeObserver.OnDrawListener {
        AnonymousClass1() {
        }

        @Override // android.view.ViewTreeObserver.OnDrawListener
        public void onDraw() {
            final View decorView = BaseFolderDialogFragment.this.mCreateFolderDialog.getWindow() != null ? BaseFolderDialogFragment.this.mCreateFolderDialog.getWindow().getDecorView() : null;
            if (decorView != null) {
                decorView.post(new Runnable() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDraw$0(decorView);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDraw$0(View view) {
            if (view.isAttachedToWindow()) {
                view.getViewTreeObserver().removeOnDrawListener(this);
            }
            Trace.endAsyncSection("Fluency_Test:ignore_range", 0);
        }
    }

    protected void refreshFolderInfo() {
        Logger.INSTANCE.i(TAG, "refreshFolderInfo");
        FolderStore.INSTANCE.query(getLoaderManager(), 1, 13, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                Logger.INSTANCE.i(BaseFolderDialogFragment.TAG, "refreshFolderInfo->callback");
                FolderStore.updateEnableNewDefaultFolderName(NoteApp.getInstance(), cursor);
                cursor.close();
                BaseFolderDialogFragment.this.getLoaderManager().destroyLoader(13);
            }
        });
    }

    private void dismissCreateFolderDialog() {
        CreateFolderDialog createFolderDialog = this.mCreateFolderDialog;
        if (createFolderDialog != null) {
            if (createFolderDialog.isShowing()) {
                this.mCreateFolderDialog.dismiss();
            }
            this.mCreateFolderDialog = null;
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        this.isDestroyView = false;
        this.mRootView = viewInflate;
        initView(viewInflate);
        return viewInflate;
    }

    protected void queryFolderInfo() {
        FolderStore.INSTANCE.query(getLoaderManager(), 3, 12, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {
                if (BaseFolderDialogFragment.this.mActivity == null || BaseFolderDialogFragment.this.isDestroyView || cursor == null) {
                    return;
                }
                new Thread(new Runnable() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ArrayList arrayList = new ArrayList();
                        cursor.moveToPosition(-1);
                        FolderModel folderModel = null;
                        while (cursor.moveToNext()) {
                            FolderModel folder = ItemStore.parseFolder(cursor);
                            long id = folder.getId();
                            if (id < 0) {
                                folder.setSubject(FolderModel.getSystemSubject(id));
                            } else if (id == 0) {
                                folderModel = folder;
                            }
                            if (id != 0) {
                                arrayList.add(folder);
                            }
                        }
                        int count = cursor.getCount();
                        Logger.INSTANCE.d(BaseFolderDialogFragment.TAG, "sortFolder");
                        Utils.INSTANCE.sortFolder(arrayList);
                        if (folderModel != null && !arrayList.isEmpty()) {
                            arrayList.add(folderModel);
                        }
                        Message message = new Message();
                        message.obj = arrayList;
                        message.arg1 = count;
                        message.what = 256;
                        BaseFolderDialogFragment.this.handlerQueryFolderInfo.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    private class QueryFolderInfoHandler extends Handler {
        private QueryFolderInfoHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 256) {
                BaseFolderDialogFragment.this.updateList((List) message.obj, message.arg1);
            }
            super.handleMessage(message);
        }
    }

    private class FolderEditModeCallbackHandler implements EditModeCallbackHandler {
        private MenuItem mDeleteMenu;
        private MenuItem mGroupMenu;
        private MenuItem mHideMenu;
        private long mLastClickTime;
        private MenuItem mMoveMenu;
        private MenuItem mRenameMenu;
        private MenuItem mStickMenu;

        private FolderEditModeCallbackHandler() {
        }

        @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment.EditModeCallbackHandler
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            BaseFolderDialogFragment.this.getMenuInflater().inflate(R.menu.common_folder_dialog_list_options, menu);
            this.mLastClickTime = System.currentTimeMillis();
            this.mHideMenu = menu.findItem(R.id.hide);
            this.mStickMenu = menu.findItem(R.id.stick);
            this.mDeleteMenu = menu.findItem(R.id.delete);
            this.mMoveMenu = menu.findItem(R.id.move);
            this.mGroupMenu = menu.findItem(R.id.group);
            this.mRenameMenu = menu.findItem(R.id.rename);
            if (BaseFolderDialogFragment.this.mActionStateListener == null) {
                return true;
            }
            BaseFolderDialogFragment.this.mActionStateListener.onEnterMultiMode();
            return true;
        }

        @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment.EditModeCallbackHandler
        public void onDestroyActionMode(ActionMode actionMode) {
            if (BaseFolderDialogFragment.this.mActionStateListener != null) {
                BaseFolderDialogFragment.this.mActionStateListener.onExitMultiMode();
            }
        }

        @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment.EditModeCallbackHandler
        public void updateActionItems() {
            boolean z = BaseFolderDialogFragment.this.mRecyclerViewWrapper.getCheckedItemCount() > 0;
            this.mHideMenu.setVisible(false);
            boolean[] allCheckedItemsFolderStatus = getAllCheckedItemsFolderStatus();
            this.mRenameMenu.setVisible(allCheckedItemsFolderStatus[0]);
            this.mDeleteMenu.setVisible(allCheckedItemsFolderStatus[1]);
            this.mMoveMenu.setVisible(false);
            this.mGroupMenu.setVisible(false);
            if (z) {
                if (isAllCheckedItemSticking()) {
                    this.mStickMenu.setTitle(R.string.menu_unstick);
                    this.mStickMenu.setIcon(R.drawable.miui_ic_unstick);
                } else {
                    this.mStickMenu.setTitle(R.string.menu_stick);
                    this.mStickMenu.setIcon(R.drawable.miui_ic_stick);
                }
                this.mStickMenu.setEnabled(true);
                return;
            }
            this.mStickMenu.setTitle(R.string.menu_stick);
            this.mStickMenu.setIcon(R.drawable.miui_ic_stick);
            this.mStickMenu.setEnabled(false);
        }

        private boolean isAllCheckedItemSticking() {
            LongSparseLongArray checkedItems = BaseFolderDialogFragment.this.getCheckedItems();
            int size = checkedItems.size();
            for (int i = 0; i < size; i++) {
                FolderModel item = BaseFolderDialogFragment.this.mAdapter.getItem((int) checkedItems.valueAt(i));
                if (item != null && item.getStickAt() <= 0) {
                    return false;
                }
            }
            return true;
        }

        private boolean[] getAllCheckedItemsFolderStatus() {
            boolean[] zArr = new boolean[2];
            LongSparseLongArray checkedItems = BaseFolderDialogFragment.this.getCheckedItems();
            int size = checkedItems.size();
            if (size == 1) {
                FolderModel item = BaseFolderDialogFragment.this.mAdapter.getItem((int) checkedItems.valueAt(0));
                if (item != null && FolderModel.isUserFolder(item.getId())) {
                    zArr[0] = true;
                    zArr[1] = true;
                    return zArr;
                }
            } else if (size > 1) {
                for (int i = 0; i < size; i++) {
                    FolderModel item2 = BaseFolderDialogFragment.this.mAdapter.getItem((int) checkedItems.valueAt(i));
                    if (item2 != null && FolderModel.isSpecialFolder(item2.getId())) {
                        zArr[1] = false;
                        return zArr;
                    }
                }
                zArr[1] = true;
            }
            return zArr;
        }

        @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment.EditModeCallbackHandler
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            long jCurrentTimeMillis = System.currentTimeMillis() - this.mLastClickTime;
            this.mLastClickTime = System.currentTimeMillis();
            int itemId = menuItem.getItemId();
            if (itemId == R.id.delete) {
                LongSparseLongArray checkedItems = BaseFolderDialogFragment.this.getCheckedItems();
                ArrayList arrayList = new ArrayList(checkedItems.size());
                for (int i = 0; i < checkedItems.size(); i++) {
                    if (BaseFolderDialogFragment.this.mAdapter.getItem((int) checkedItems.valueAt(i)) != null) {
                        arrayList.add(BaseFolderDialogFragment.this.mAdapter.getItem((int) checkedItems.valueAt(i)));
                    }
                }
                BaseFolderDialogFragment.this.showDeleteFolderDialog(arrayList);
            } else if (itemId != R.id.rename) {
                if (itemId != R.id.stick) {
                    return false;
                }
                Logger.INSTANCE.d(BaseFolderDialogFragment.TAG, "onActionItemClicked/stick");
                BaseFolderDialogFragment.this.stickCheckedItems(!isAllCheckedItemSticking());
            } else {
                if (jCurrentTimeMillis < 300) {
                    return true;
                }
                LongSparseLongArray checkedItems2 = BaseFolderDialogFragment.this.getCheckedItems();
                if (checkedItems2.size() == 1) {
                    FolderModel item = BaseFolderDialogFragment.this.mAdapter.getItem((int) checkedItems2.valueAt(0));
                    if (item != null && item.getId() >= 0) {
                        BaseFolderDialogFragment.this.showRenameFolderDialog(item);
                    }
                }
            }
            return true;
        }
    }

    protected boolean isAllItemChecked() {
        return this.mRecyclerViewWrapper.isAllItemsChecked();
    }

    protected LongSparseLongArray getCheckedItems() {
        return this.mRecyclerViewWrapper.getCheckedItems();
    }

    protected class EditModeCallback implements EditableRecyclerViewWrapper.MultiChoiceModeListener {
        private final EditModeCallbackHandler mCallbackHandler;

        public EditModeCallback() {
            this.mCallbackHandler = new FolderEditModeCallbackHandler();
        }

        @Override // com.miui.notes.ui.widget.EditableRecyclerViewWrapper.MultiChoiceModeListener
        public void onAllItemCheckedStateChanged(ActionMode actionMode, boolean z) {
            BaseFolderDialogFragment.this.updateActionModeButton2(actionMode);
            this.mCallbackHandler.updateActionItems();
            BaseFolderDialogFragment.this.checkTalkBackAndHint(false, z);
        }

        @Override // android.widget.AbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z) {
            BaseFolderDialogFragment.this.updateActionModeButton2(actionMode);
            this.mCallbackHandler.updateActionItems();
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            FragmentActivity activity = BaseFolderDialogFragment.this.getActivity();
            if (activity != null && !UIUtils.isSmallWindowMode(activity) && ((RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(activity)) || RomUtils.isPhoneDevice())) {
                activity.getWindow().addFlags(DataSpace.RANGE_FULL);
            }
            BaseFolderDialogFragment.this.updateActionModeButton2(actionMode);
            BaseFolderDialogFragment.this.mActionMode = actionMode;
            if (BaseFolderDialogFragment.this.mActionStateListener != null) {
                BaseFolderDialogFragment.this.mActionStateListener.onEnterMultiMode();
            }
            return this.mCallbackHandler.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            BaseFolderDialogFragment.this.updateActionModeButton1(actionMode);
            this.mCallbackHandler.updateActionItems();
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == 16908313) {
                actionMode.finish();
            } else if (menuItem.getItemId() == 16908314) {
                BaseFolderDialogFragment.this.mRecyclerViewWrapper.setAllItemsChecked(!BaseFolderDialogFragment.this.isAllItemChecked());
            }
            return this.mCallbackHandler.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            FragmentActivity activity = BaseFolderDialogFragment.this.getActivity();
            if (activity != null && !UIUtils.isSmallWindowMode(activity) && ((RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(activity)) || RomUtils.isPhoneDevice())) {
                activity.getWindow().clearFlags(DataSpace.RANGE_FULL);
            }
            BaseFolderDialogFragment.this.mActionMode = null;
            if (BaseFolderDialogFragment.this.mActionStateListener != null) {
                BaseFolderDialogFragment.this.mActionStateListener.onExitMultiMode();
            }
            this.mCallbackHandler.onDestroyActionMode(actionMode);
            BaseFolderDialogFragment.this.changeActionMode(false);
        }
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void checkTalkBackAndHint(boolean z, boolean z2) {
        View decorView = getActivity() != null ? getActivity().getWindow().getDecorView() : null;
        if (decorView != null) {
            View viewFindViewById = decorView.findViewById(R.id.action_context_bar).findViewById(16908314);
            if (z) {
                viewFindViewById.setContentDescription(getString(z2 ? R.string.miuix_appcompat_deselect_all_description : R.string.miuix_appcompat_select_all_description));
            } else {
                viewFindViewById.announceForAccessibility(getString(z2 ? R.string.note_select_all_talkback : R.string.note_unselect_all_talkback));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stickCheckedItems(boolean z) {
        Logger.INSTANCE.d(TAG, "stickCheckedItems");
        this.mAdapter.markManualModifyDataTime();
        new StickTask(this, this.mRecyclerViewWrapper.getCheckedItemIds(), z).execute(new Void[0]);
    }

    protected void showRenameFolderDialog(FolderModel folderModel) {
        final RenameFolderDialog renameFolderDialog = new RenameFolderDialog(getActivity(), folderModel);
        renameFolderDialog.setNegativeListener(new View.OnClickListener() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                renameFolderDialog.dismiss();
            }
        });
        renameFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$showRenameFolderDialog$3(dialogInterface);
            }
        });
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        renameFolderDialog.show();
        manageDialog(renameFolderDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRenameFolderDialog$3(DialogInterface dialogInterface) {
        this.mRecyclerViewWrapper.finishActionMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: deleteCheckedFolders, reason: merged with bridge method [inline-methods] */
    public void lambda$showDeleteFolderDialog$4(List<FolderModel> list) {
        long[] jArr = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStickAt() > 0) {
                stickCheckedItems(false);
            }
            jArr[i] = list.get(i).getId();
        }
        this.mAdapter.markManualModifyDataTime();
        new FolderDeleteTask(this, jArr).execute(new Void[0]);
        finishActionMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteFolderDialog(List<FolderModel> list) {
        AlertDialog.Builder builderCreate = DeleteFolderDialog.create(getActivity(), list, new DeleteFolderDialog.DeleteDialogCallback() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment$$ExternalSyntheticLambda4
            @Override // com.miui.notes.component.DeleteFolderDialog.DeleteDialogCallback
            public final void onPositiveClick(List list2) {
                this.f$0.lambda$showDeleteFolderDialog$4(list2);
            }
        });
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        AlertDialog alertDialog = this.mDeleteFolderDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDeleteFolderDialog.dismiss();
        }
        AlertDialog alertDialogShow = builderCreate.show();
        this.mDeleteFolderDialog = alertDialogShow;
        manageDialog(alertDialogShow);
    }

    private static class FolderDeleteTask extends AsyncTaskWithProgressDialog<BaseFolderDialogFragment, Void, Void, Void> {
        private long[] iIds;

        FolderDeleteTask(BaseFolderDialogFragment baseFolderDialogFragment, long[] jArr) {
            super(baseFolderDialogFragment);
            this.iIds = jArr;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public Void doBackgroundWork(Context context, Void... voidArr) {
            Logger.INSTANCE.d(getClass().getSimpleName(), "doBackgroundWork");
            FolderStore.delete(context, this.iIds);
            return null;
        }

        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        protected Dialog onCreateDialog(Activity activity) {
            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(activity.getString(R.string.dlg_delete_message));
            progressDialog.setCancelable(false);
            return progressDialog;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public void onPostWork(Activity activity, Void r2) {
            super.onPostWork(activity, r2);
            AudioUtils.playDeleteRingtone();
            if (getFragment() != null) {
                getFragment().refreshFolderInfo();
            }
        }
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
            if (fragment instanceof BaseFolderDialogFragment) {
                ((BaseFolderDialogFragment) fragment).mRecyclerViewWrapper.finishActionMode();
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

    private static class NoteDeleteTask extends NoteOperationTask {
        private boolean iForce;

        public NoteDeleteTask(BaseFolderDialogFragment baseFolderDialogFragment, long[] jArr, boolean z) {
            super(baseFolderDialogFragment, jArr, R.string.dlg_delete_message);
            this.iForce = z;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public Integer doBackgroundWork(Context context, Void... voidArr) {
            return Integer.valueOf(NoteStore.delete(context, this.iIds, this.iForce));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment.NoteOperationTask, com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public void onPostWork(Activity activity, Integer num) {
            super.onPostWork(activity, num);
            checkHasExcerptingNoteAndStop();
            AudioUtils.playDeleteRingtone();
        }

        private void checkHasExcerptingNoteAndStop() {
            if (FloatWindowManagerAgent.isContinuousExcerpt()) {
                for (long j : this.iIds) {
                    if (j == FloatWindowManagerAgent.getExcerptId()) {
                        FloatWindowManagerAgent.stopContinuousExcerpt();
                    }
                }
            }
        }
    }

    private static class StickTask extends NoteOperationTask {
        private boolean iStick;

        public StickTask(BaseFolderDialogFragment baseFolderDialogFragment, long[] jArr, boolean z) {
            super(baseFolderDialogFragment, jArr, R.string.dlg_stick_message);
            this.iStick = z;
            Logger.INSTANCE.d(BaseFolderDialogFragment.TAG, "StickTask");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public Integer doBackgroundWork(Context context, Void... voidArr) {
            return Integer.valueOf(ItemStore.stick(context, this.iIds, this.iStick));
        }
    }

    public boolean finishActionMode() {
        if (this.mActionStateListener != null) {
            this.mActionStateListener.onExitMultiMode();
        }
        EditableRecyclerViewWrapper editableRecyclerViewWrapper = this.mRecyclerViewWrapper;
        if (editableRecyclerViewWrapper == null || !editableRecyclerViewWrapper.isInActionMode()) {
            return false;
        }
        this.mRecyclerViewWrapper.finishActionMode();
        return true;
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment
    public void onVisibilityChanged(boolean z) {
        super.onVisibilityChanged(z);
        if (z) {
            return;
        }
        AlertDialog alertDialog = this.mDeleteNotesDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDeleteNotesDialog.dismiss();
        }
        AlertDialog alertDialog2 = this.mDeleteFolderDialog;
        if (alertDialog2 == null || !alertDialog2.isShowing()) {
            return;
        }
        this.mDeleteFolderDialog.dismiss();
    }

    @Override // com.miui.notes.ui.fragment.ModuleFragment
    public boolean onBackPressed() {
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        if (baseNoteRecyclerView != null) {
            baseNoteRecyclerView.stopScroll();
        }
        return finishActionMode();
    }

    protected void stopQuery() {
        getLoaderManager().destroyLoader(10);
        getLoaderManager().destroyLoader(11);
        getLoaderManager().destroyLoader(12);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.isDestroyView = true;
        this.mRecyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.miui.notes.basefeature.folder.BaseFolderDialogFragment.6
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                BaseFolderDialogFragment.this.mRecyclerViewWrapper.setAdapter(null);
            }
        });
        this.mRecyclerViewWrapper.finishActionMode();
        super.onDestroyView();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        QueryFolderInfoHandler queryFolderInfoHandler = this.handlerQueryFolderInfo;
        if (queryFolderInfoHandler != null) {
            queryFolderInfoHandler.removeMessages(256);
        }
        stopQuery();
        super.onDestroy();
        this.mActivity = null;
        this.mHandler.removeCallbacksAndMessages(null);
    }
}
