package com.miui.notes.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.content.Loader;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.SafeBroadcastReceiver;
import com.miui.common.stat.PadHandWriteStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.handwritecommon.api.util.FilePathUtils;
import com.miui.handwriting.common.listener.CommonExportProcessListener;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPreviewClickListener;
import com.miui.handwriting.common.tools.BitmapUtil;
import com.miui.handwriting.common.tools.FilePathConstant;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.view.thumbnail.ExportImageManager;
import com.miui.libhandwritingpreview.view.MiuiHandWritingPreview;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.feature.folder.ChooserFolderDialogActivity;
import com.miui.notes.feature.folder.CreateFolderDialog;
import com.miui.notes.home.PrivateNotesListActivity;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.ui.IFragmentController;
import com.miui.notes.ui.PhoneExportShareActivity;
import com.miui.notes.ui.PhoneMultiHandWriteHeaderController;
import com.miui.pad.feature.notes.handwrite.HandWriteWorkingNote;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWriteEntity;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;
import net.lingala.zip4j.util.InternalZipConstants;
import xc.engine.utils.FileUtil;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneMultiHandWriteFragment extends DialogManagedFragment implements PhoneMultiHandWriteHeaderController.HeaderOperationCallback, ExportImageManager.ExportCallback {
    private static final int DOUBLE_CLICK_GAP = 500;
    private static final int EXPORT_SIZE = 1440;
    private static final String EXTRA_TAP_SHARE_MULTIPLE_INFO = "xiaomi.tap.share.MULTIPLE_INFO";
    private static final String EXTRA_TAP_SHARE_SUBTITLE = "xiaomi.tap.share.SUBTITLE";
    private static final String EXTRA_TAP_SHARE_SUPPORT = "xiaomi.tap.share.SUPPORT";
    private static final String EXTRA_TAP_SHARE_TITLE = "xiaomi.tap.share.TITLE";
    private static final int REQUEST_CODE_QUERY_FOLDER = 2;
    public static String TAG = "PhoneHandWriteFragment";
    private View customActionBarView;
    private ProgressDialog exportPreviewDialog;
    private ProgressDialog loadingDialog;
    private View loadingView;
    public IFragmentController mController;
    protected BaseFolderChooser mDialogFolderChooser;
    private CommonExportProcessListener<String> mExportProcessListener;
    private long mFolderId;
    private PhoneMultiHandWriteHeaderController mHeaderController;
    private boolean mIsFromPrivate;
    private List<Integer> mLastExportList;
    private long mLastExportingTime;
    private long mNoteId;
    protected PasswordController mPasswordController;
    private FrameLayout mRootView;
    private long mSyncId;
    private MultiHandWriteEntity multiHandWriteEntity;
    private MiuiHandWritingPreview multiHandWriteView;
    private BroadcastReceiver operationReceiver;
    private String queryText;
    private HandWriteWorkingNote workingNote;
    private boolean mNeedConfirmPass = false;
    protected ActivityResultLauncher<Intent> toChooseFolderResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment.1
        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(ActivityResult activityResult) {
        }
    });
    protected PasswordController.OnSetPasswordListener mOnSetPasswordListener = new PasswordController.SimpleOnSetPasswordListener() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment.2
        @Override // com.miui.notes.controller.PasswordController.SimpleOnSetPasswordListener, com.miui.notes.controller.PasswordController.OnSetPasswordListener
        public void onSuccess(int i) {
            if (i == 5102) {
                PhoneMultiHandWriteFragment.this.hideNote();
            }
        }
    };
    protected PasswordController.LockStateListener mLockStateListener = new PasswordController.SimpleLockStateListener() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment.3
        @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
        public void onLockStateChanged(PasswordController passwordController, int i) {
            super.onLockStateChanged(passwordController, i);
            Logger.INSTANCE.d(PhoneMultiHandWriteFragment.TAG, "LockStateListener onLockStateChanged: " + i);
            if (i == 2) {
                PhoneMultiHandWriteFragment.this.mNeedConfirmPass = false;
                Logger.INSTANCE.d(PhoneMultiHandWriteFragment.TAG, "Password unlocked, reset mNeedConfirmPass to false");
            }
        }

        @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
        public boolean shouldAutoLock() {
            return PhoneMultiHandWriteFragment.this.shouldConfirmPassword();
        }
    };
    private final DownloadFileReceiver downloadFileReceiver = new DownloadFileReceiver(this);

    static /* synthetic */ void lambda$initView$0(View view) {
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportThumbnailFinish(long j, ContentValues contentValues, boolean z) {
    }

    protected int getThemeRes() {
        return R.style.NoteTheme_Edit;
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void requirePreviewList(List<Integer> list) {
    }

    public void setController(IFragmentController iFragmentController) {
        this.mController = iFragmentController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldConfirmPassword() {
        if (isNoteHidden()) {
            return !this.mIsFromPrivate || this.mNeedConfirmPass;
        }
        return false;
    }

    protected void initPasswordController(Bundle bundle) {
        int i = bundle != null ? bundle.getInt("password_lock_state", 0) : 0;
        Logger.INSTANCE.d(TAG, "initPasswordController: lockState = " + i);
        PasswordController passwordController = new PasswordController(this, true, i);
        this.mPasswordController = passwordController;
        passwordController.setOnSetPasswordListener(this.mOnSetPasswordListener);
        this.mPasswordController.setLockSateListener(this.mLockStateListener);
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setThemeRes(getThemeRes());
        initPasswordController(bundle);
        initExportListener();
        registerCreationTaskReceiver();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.phone_multi_hand_write_fragment, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    private void initView(View view) {
        View viewFindViewById = view.findViewById(R.id.loading_container);
        this.loadingView = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PhoneMultiHandWriteFragment.lambda$initView$0(view2);
            }
        });
        this.mRootView = (FrameLayout) view;
        MiuiHandWritingPreview miuiHandWritingPreview = new MiuiHandWritingPreview(this.mRootView.getContext());
        this.multiHandWriteView = miuiHandWritingPreview;
        this.mRootView.addView(miuiHandWritingPreview, 0);
        this.multiHandWriteView.initUIProperties();
        this.multiHandWriteView.setPreviewOnClick(new CommonPreviewClickListener() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment$$ExternalSyntheticLambda4
            @Override // com.miui.handwriting.common.listener.CommonPreviewClickListener
            public final void onClick() {
                this.f$0.lambda$initView$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        Toast.makeText(NoteApp.getInstance(), getResources().getString(R.string.hand_write_only_support_tablet_editing), 0).show();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initHeaderController();
        setupIntentData();
        registerDownloadReceiver();
    }

    private void initHeaderController() {
        PhoneMultiHandWriteHeaderController phoneMultiHandWriteHeaderController = new PhoneMultiHandWriteHeaderController(this, this);
        this.mHeaderController = phoneMultiHandWriteHeaderController;
        this.customActionBarView = phoneMultiHandWriteHeaderController.onInflateView(LayoutInflater.from(getContext()), (ViewGroup) getView(), null);
        getActionBar().setCustomView(this.customActionBarView, new ActionBar.LayoutParams(-1, -1));
        FragmentActivity activity = getActivity();
        if (activity != null && activity.getResources() != null) {
            getActionBar().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.v8_placeholder));
        }
        setImmersionMenuEnabled(true);
    }

    private void setupIntentData() {
        Intent intent = getActivity() != null ? getActivity().getIntent() : null;
        if (intent == null) {
            return;
        }
        this.queryText = getExtraUserQuery(intent);
        this.mIsFromPrivate = PrivateNotesListActivity.ACTION_FROM_PRIVACY;
        PrivateNotesListActivity.ACTION_FROM_PRIVACY = false;
        updateSecurityFlags();
        long longExtra = intent.getLongExtra("android.intent.extra.UID", 0L);
        this.mNoteId = longExtra;
        this.multiHandWriteEntity = MultiHandWriteDataUtils.loadNote(longExtra);
        this.workingNote = HandWriteWorkingNote.loadNote(this.mNoteId);
        MultiHandWriteEntity multiHandWriteEntity = this.multiHandWriteEntity;
        if (multiHandWriteEntity != null) {
            this.mFolderId = multiHandWriteEntity.folderId;
            updateUI(this.multiHandWriteEntity);
            MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
            if (miuiHandWritingPreview != null) {
                miuiHandWritingPreview.setTitle(this.multiHandWriteEntity.title);
                this.multiHandWriteView.setTitleTime(this.multiHandWriteEntity.getSortDate());
            }
        }
    }

    private void updateSecurityFlags() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (this.mIsFromPrivate) {
            activity.getWindow().addFlags(8192);
        } else {
            activity.getWindow().clearFlags(8192);
        }
    }

    private void registerDownloadReceiver() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.miui.notes.handwrite.download");
        if (Utils.isAboveSDK34()) {
            activity.registerReceiver(this.downloadFileReceiver, intentFilter, 2);
        } else {
            activity.registerReceiver(this.downloadFileReceiver, intentFilter);
        }
    }

    private void updateUI(MultiHandWriteEntity multiHandWriteEntity) {
        if (multiHandWriteEntity == null || this.multiHandWriteView == null) {
            return;
        }
        boolean zIsSuniaSignalPage = this.multiHandWriteEntity.isSuniaSignalPage();
        if (this.workingNote != null && zIsSuniaSignalPage) {
            String attachmentPath = AttachmentUtils.getAttachmentPath(BaseNoteApp.getInstance(), this.workingNote.entName);
            if (attachmentPath != null) {
                this.multiHandWriteView.load(attachmentPath, createSimplePageLoadListener());
                return;
            }
            return;
        }
        this.multiHandWriteView.load(multiHandWriteEntity.observeEntDir() + "/multi_page_ent.ent", createSimplePageLoadListener());
    }

    private CommonPageLoadListener createSimplePageLoadListener() {
        return new CommonPageLoadListener() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment.4
            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onLoadCompleted(boolean z, boolean z2, String str) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onPageCount(int i) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onPdfError(int i) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public String getSuniaSinglePageBgType() {
                if (PhoneMultiHandWriteFragment.this.workingNote != null && PhoneMultiHandWriteFragment.this.workingNote.canvasBgType != null) {
                    return PhoneMultiHandWriteFragment.this.workingNote.canvasBgType;
                }
                return "";
            }
        };
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            bundle.putInt("password_lock_state", passwordController.mLockState);
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normal_note_edit_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        updateMenu(menu);
        super.onPrepareOptionsMenu(menu);
    }

    public void updateMenu(Menu menu) {
        menu.findItem(R.id.send_to_desktop).setVisible(false);
        menu.findItem(R.id.set_alarm).setVisible(false);
        boolean zIsNoteHidden = isNoteHidden();
        menu.findItem(R.id.hide).setVisible(!zIsNoteHidden);
        menu.findItem(R.id.unhide).setVisible(zIsNoteHidden);
        menu.findItem(R.id.move_to).setVisible(!zIsNoteHidden);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.delete /* 2131362396 */:
                requestDeleteNote();
                return true;
            case R.id.hide /* 2131362702 */:
                tryHideNote();
                return true;
            case R.id.move_to /* 2131362990 */:
                startQueryFolder();
                return true;
            case R.id.unhide /* 2131363736 */:
                unhideNote();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void unhideNote() {
        Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_unhidden, 0).show();
        this.mFolderId = 0L;
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getWindow().clearFlags(8192);
        }
        NoteStore.unhide(getContext(), new long[]{this.mNoteId});
    }

    public boolean isNoteHidden() {
        return this.mFolderId == -4;
    }

    private String getExtraUserQuery(Intent intent) {
        if (intent.hasExtra("intent_extra_data_key")) {
            return intent.getStringExtra("intent_extra_data_key");
        }
        return null;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mPasswordController != null && shouldConfirmPassword()) {
            Logger.INSTANCE.d(TAG, "PhoneMultiHandWriteFragment onResume: mLockState=" + this.mPasswordController.mLockState);
            this.mPasswordController.unlock();
        }
        if (this.mDialogFolderChooser == null || !UIUtils.isInMultiWindowMode(getActivity())) {
            return;
        }
        this.mDialogFolderChooser.cancel();
    }

    public Uri getDeepLink() {
        return HandoffHelper.getNoteDeepLink(this.mSyncId, this.mFolderId);
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mNeedConfirmPass = true;
    }

    public void startQueryFolder() {
        FolderStore.INSTANCE.query(getLoaderManager(), 0, 2, new NoteLoaderCallbacks(), "_id <> " + this.mFolderId);
    }

    public class NoteLoaderCallbacks extends CursorLoaderBuilder.DefaultLoaderCallbacks {
        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoaderReset(Loader<Cursor> loader) {
        }

        public NoteLoaderCallbacks() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if (loader.getId() != 2 || cursor == null) {
                return;
            }
            safeShowFolderChooser(cursor);
        }

        public void safeShowFolderChooser(Cursor cursor) {
            if (cursor != null) {
                PhoneMultiHandWriteFragment.this.showFolderChooser(cursor);
            }
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (this.mPasswordController == null || requireActivity().isChangingConfigurations()) {
            return;
        }
        this.mPasswordController.lockDelay();
    }

    protected void showFolderChooser(Cursor cursor) {
        long[] jArr = {this.mNoteId};
        if (cursor.getCount() > 0 || this.mFolderId > 0) {
            openFolderChooser(jArr, this.mFolderId);
        } else {
            createFolderAndMoveItems(jArr);
        }
        cursor.close();
        getLoaderManager().destroyLoader(2);
    }

    private void openFolderChooser(long[] jArr, long j) {
        if (getActivity() != null) {
            ChooserFolderDialogActivity.INSTANCE.openForResult(this, this.toChooseFolderResult, jArr, j);
        }
    }

    private void createFolderAndMoveItems(final long[] jArr) {
        CreateFolderDialog createFolderDialog = new CreateFolderDialog(getActivity());
        createFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$createFolderAndMoveItems$2(jArr, dialogInterface);
            }
        });
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        createFolderDialog.show();
        manageDialog(createFolderDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createFolderAndMoveItems$2(long[] jArr, DialogInterface dialogInterface) {
        CreateFolderDialog createFolderDialog = (CreateFolderDialog) dialogInterface;
        if (createFolderDialog.getFolderId() > 0) {
            moveCheckedItems(createFolderDialog.getFolderId(), jArr, createFolderDialog.getFolderName());
        }
    }

    public boolean onBackPressed() {
        BaseFolderChooser baseFolderChooser = this.mDialogFolderChooser;
        if (baseFolderChooser == null) {
            return false;
        }
        baseFolderChooser.cancel();
        return true;
    }

    protected void moveCheckedItems(long j, long[] jArr, String str) {
        this.mFolderId = j;
        new EditFragNoteMoveTask(jArr, j, str).execute(new Void[0]);
    }

    private static class EditFragNoteMoveTask extends AsyncTask<Void, Integer, Integer> {
        private final WeakReference<PhoneMultiHandWriteFragment> iActivityRef;
        private final long mFolderId;
        private final String mFolderName;
        private final long[] mIds;

        private EditFragNoteMoveTask(PhoneMultiHandWriteFragment phoneMultiHandWriteFragment, long[] jArr, long j, String str) {
            this.iActivityRef = new WeakReference<>(phoneMultiHandWriteFragment);
            this.mFolderName = str;
            this.mIds = jArr;
            this.mFolderId = j;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Integer doInBackground(Void... voidArr) {
            if (this.iActivityRef == null) {
                return null;
            }
            Logger.INSTANCE.d(PhoneMultiHandWriteFragment.TAG, "doInBackground...");
            return Integer.valueOf(NoteStore.move(this.iActivityRef.get().getActivity(), this.mIds, this.mFolderId));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Integer num) {
            WeakReference<PhoneMultiHandWriteFragment> weakReference;
            super.onPostExecute(num);
            if (num == null || (weakReference = this.iActivityRef) == null) {
                return;
            }
            showToast(weakReference.get().getActivity(), num.intValue());
        }

        protected void showToast(Activity activity, int i) {
            String string;
            if (this.mFolderId == 0) {
                string = activity.getString(R.string.format_move_note_out_folder);
            } else {
                string = activity.getString(R.string.format_move_note_to_folder, new Object[]{this.mFolderName});
            }
            Toast.makeText(NoteApp.getInstance(), string, 0).show();
        }
    }

    private void requestDeleteNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 2131951644);
        builder.setTitle(R.string.new_alert_title_delete);
        builder.setMessage(R.string.alert_message_delete_note);
        builder.setPositiveButton(R.string.menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$requestDeleteNote$3(dialogInterface, i);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestDeleteNote$3(DialogInterface dialogInterface, int i) {
        delete();
    }

    private void delete() {
        AudioUtils.playDeleteRingtone();
        long j = this.mNoteId;
        if (j > 0 && MultiHandWriteDataUtils.deleteNote(j)) {
            this.mFolderId = 0L;
            this.mNoteId = -1L;
            Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_delete_success, 0).show();
            getActivity().onBackPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideNote() {
        this.mFolderId = -4L;
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getWindow().addFlags(8192);
        }
        Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_hidden_edit_new, 0).show();
        NoteStore.hide(getContext(), new long[]{this.mNoteId});
    }

    protected void tryHideNote() {
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        if (miuiHandWritingPreview == null) {
            return;
        }
        if (this.mNoteId < 0 || (TextUtils.isEmpty(miuiHandWritingPreview.getTitle()) && this.multiHandWriteView.isContentEmpty())) {
            Toast.makeText(NoteApp.getInstance(), R.string.error_hide_empty_note, 0).show();
            return;
        }
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.setPassword(5102);
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        cleanupResources();
        ExportImageManager.INSTANCE.getInstance().unRegisterCallback(this);
    }

    private void cleanupResources() {
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        if (miuiHandWritingPreview != null) {
            miuiHandWritingPreview.release();
        }
        try {
            if (getActivity() != null) {
                getActivity().unregisterReceiver(this.downloadFileReceiver);
                LocalBroadcastManager.getInstance(NoteApp.getInstance()).unregisterReceiver(this.operationReceiver);
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "exception:" + e.getMessage());
        }
        this.mExportProcessListener = null;
        dismissExportPreviewDialog();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.destroy();
        }
        this.mDialogFolderChooser = null;
        dismissLoadingDialog();
        super.onDestroy();
    }

    private void initExportListener() {
        if (this.mExportProcessListener == null) {
            this.mExportProcessListener = new CommonExportProcessListener<String>() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment.5
                @Override // com.miui.handwriting.common.listener.CommonExportProcessListener
                public void exportStart() {
                    PhoneMultiHandWriteFragment phoneMultiHandWriteFragment = PhoneMultiHandWriteFragment.this;
                    phoneMultiHandWriteFragment.showLoadingDialog(phoneMultiHandWriteFragment.getResources().getString(R.string.dlg_info_creating_message));
                }

                @Override // com.miui.handwriting.common.listener.CommonExportProcessListener
                public void exportFinish(Boolean bool, List<String> list) {
                    PhoneMultiHandWriteFragment.this.dismissLoadingDialog();
                }
            };
        }
    }

    @Override // com.miui.notes.ui.PhoneMultiHandWriteHeaderController.HeaderOperationCallback
    public void onPerformAction(int i, Object... objArr) {
        if (i == 1) {
            shareHandWriteAsImage();
        }
    }

    private void shareHandWriteAsImage() {
        if (TextUtils.isEmpty(this.multiHandWriteView.getTitle()) && this.multiHandWriteView.isContentEmpty()) {
            Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
        } else {
            getShareImage();
        }
    }

    private void getShareImage() {
        PadHandWriteStat.reportClickEvent(PadHandWriteStat.HAND_WEITE_SHARE_CLICK, null);
        if (this.multiHandWriteEntity == null) {
            return;
        }
        showExportPreviewDialog();
        this.multiHandWriteView.saveEntFile(new CommonPageSaveListener() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment.6
            @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
            public void onDataSaved(boolean z, String str, boolean z2) {
                if (z) {
                    PhoneMultiHandWriteFragment.this.handleSuccessfulSave(str);
                } else {
                    PhoneMultiHandWriteFragment.this.dismissExportPreviewDialog();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSuccessfulSave(String str) {
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        List<Integer> listCreatePageList = createPageList(miuiHandWritingPreview != null ? miuiHandWritingPreview.getPageCount() : 0);
        if (isValidForExport()) {
            generateThumbnails(listCreatePageList, str);
        } else {
            dismissExportPreviewDialog();
        }
    }

    private List<Integer> createPageList(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(Integer.valueOf(i2));
        }
        return arrayList;
    }

    private void generateThumbnails(List<Integer> list, final String str) {
        this.multiHandWriteView.getCurrentItemView().getCreationEngine().getCurveEngine().getExportSystem().getPageThumbnailCustomList(list, 800, new Function1() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.lambda$generateThumbnails$4(str, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$generateThumbnails$4(String str, List list) throws Throwable {
        dismissExportPreviewDialog();
        ExportImageManager.INSTANCE.getInstance().setBitmapPreviewList(list);
        if (!RomUtils.isOS31Support()) {
            PhoneExportShareActivity.launchActivity(requireActivity(), 1, FilePathConstant.getEngineAndPageEntPath(), str, this.multiHandWriteEntity.getTitle());
        } else {
            ArrayList<Uri> arrayList = new ArrayList<>();
            String str2 = "Creation_Image_" + System.currentTimeMillis();
            int i = 0;
            ClipData clipDataNewRawUri = null;
            while (i < list.size()) {
                int i2 = i + 1;
                File file = new File(FilePathUtils.getMultiPageExportFileDir(getContext()), str2 + "_" + i2 + ".png");
                String path = file.getPath();
                BitmapUtil.saveBitmapToFile((Bitmap) list.get(i), file, true);
                if (FileUtil.isFileExists(path)) {
                    Uri uriCreateTempUriWithFilepath = createTempUriWithFilepath(getContext(), path);
                    if (clipDataNewRawUri == null) {
                        clipDataNewRawUri = ClipData.newRawUri("share_image", uriCreateTempUriWithFilepath);
                    } else {
                        clipDataNewRawUri.addItem(new ClipData.Item(uriCreateTempUriWithFilepath));
                    }
                    arrayList.add(uriCreateTempUriWithFilepath);
                }
                i = i2;
            }
            startSystemShare(arrayList, clipDataNewRawUri);
        }
        return null;
    }

    private void startSystemShare(ArrayList<Uri> arrayList, ClipData clipData) {
        long size;
        boolean z = arrayList.size() > 1;
        Intent intent = new Intent(z ? "android.intent.action.SEND_MULTIPLE" : "android.intent.action.SEND");
        intent.setFlags(1);
        intent.setType("image/png");
        Logger.INSTANCE.d(TAG, "shareNote: " + clipData);
        if (!z) {
            intent.putExtra("android.intent.extra.STREAM", arrayList.get(0));
            intent.setClipData(ClipData.newRawUri(null, arrayList.get(0)));
            size = 0;
        } else {
            intent.setClipData(clipData);
            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
            size = arrayList.size();
        }
        intent.putExtra("xiaomi.tap.share.SUPPORT", true);
        intent.putExtra("xiaomi.tap.share.MULTIPLE_INFO", String.valueOf(size));
        startActivity(Intent.createChooser(intent, null));
    }

    public static Uri createTempUriWithFilepath(Context context, String str) {
        File file = new File(str);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                com.miui.common.tool.Logger.INSTANCE.e(TAG, " createNewFile error: " + e);
                return null;
            }
        }
        return CompatUtils.getCompatUri(NoteApp.getInstance(), file);
    }

    private void showExportPreviewDialog() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (this.exportPreviewDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(activity);
            this.exportPreviewDialog = progressDialog;
            progressDialog.setIndeterminate(true);
            this.exportPreviewDialog.setCanceledOnTouchOutside(false);
            this.exportPreviewDialog.setCancelable(false);
        }
        this.exportPreviewDialog.setMessage(activity.getString(R.string.export_preview_loading));
        this.exportPreviewDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissExportPreviewDialog() {
        ProgressDialog progressDialog = this.exportPreviewDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.exportPreviewDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissLoadingDialog() {
        ProgressDialog progressDialog = this.loadingDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.loadingDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoadingDialog(String str) {
        if (!isAdded()) {
            Logger.INSTANCE.e(TAG, "showLoadingDialog isAdded");
            return;
        }
        if (this.loadingDialog == null && getContext() != null) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            this.loadingDialog = progressDialog;
            progressDialog.setIndeterminate(true);
            this.loadingDialog.setCanceledOnTouchOutside(false);
            this.loadingDialog.setCancelable(false);
        }
        this.loadingDialog.setMessage(str);
        this.loadingDialog.show();
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void loadPreviewFinish(int i) {
        dismissExportPreviewDialog();
        if (this.multiHandWriteEntity == null || this.multiHandWriteView == null) {
            return;
        }
        PhoneExportShareActivity.launchActivity(requireActivity(), 1, FilePathConstant.getEngineAndPageEntPath(), this.multiHandWriteEntity.entName, this.multiHandWriteView.getTitle());
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void loadPreviewError(int i) {
        dismissExportPreviewDialog();
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportProgress(int i) {
        Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
        intent.putExtra("operationType", "onProgress");
        intent.putExtra("percentDone", i);
        Log.d(TAG, "Fragment receive: MSG_FRAGMENT_EXPORT_ON_PROGRESS: " + i);
        LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).sendBroadcast(intent);
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void requireNextList(List<Integer> list) {
        releasePage();
        this.mLastExportList = list;
        if (isValidForExport()) {
            exportThumbnailsDirectly(list);
        }
    }

    private boolean isValidForExport() {
        return (this.multiHandWriteView.getCurrentItemView() == null || this.multiHandWriteView.getCurrentItemView().model == null || this.multiHandWriteView.getCurrentItemView().getCreationEngine() == null || this.multiHandWriteView.getCurrentItemView().getCreationEngine().getCurveEngine() == null) ? false : true;
    }

    private void exportThumbnailsDirectly(List<Integer> list) {
        this.multiHandWriteView.getCurrentItemView().getCreationEngine().getCurveEngine().getExportSystem().getPageThumbnailCustomList(list, 1440, new Function1() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PhoneMultiHandWriteFragment.lambda$exportThumbnailsDirectly$5((List) obj);
            }
        });
    }

    static /* synthetic */ Unit lambda$exportThumbnailsDirectly$5(List list) {
        ExportImageManager.INSTANCE.getInstance().addExportBitmap(list);
        return null;
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportFinish(int i, String str) {
        releasePage();
        Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
        intent.putExtra("operationType", "onCompleted");
        intent.putExtra("isCompletedSuccess", true);
        intent.putExtra("completedPath", str);
        Log.d(TAG, "EXPORT_completedPath:" + str);
        intent.putExtra("exportType", i);
        LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).sendBroadcast(intent);
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportError(int i, int i2, String str) {
        dismissExportPreviewDialog();
        Log.e(TAG, "export error:" + i);
        releasePage();
        Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
        intent.putExtra("operationType", "onCompleted");
        intent.putExtra("isCompletedSuccess", false);
        intent.putExtra("completedPath", str);
        Log.d(TAG, "EXPORT_completedPath:$path");
        intent.putExtra("exportType", i2);
        LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).sendBroadcast(intent);
    }

    private boolean isDoubleCLickExport() {
        boolean z = System.currentTimeMillis() - this.mLastExportingTime <= 500;
        this.mLastExportingTime = System.currentTimeMillis();
        return z;
    }

    private void releasePage() {
        List<Integer> list = this.mLastExportList;
        if (list == null || list.isEmpty()) {
            return;
        }
        if (isValidForExport()) {
            releaseDistantPages(4);
        }
        this.mLastExportList = null;
    }

    private void releaseDistantPages(int i) {
        int currentPosition = this.multiHandWriteView.getCurrentItemView().model.getCurrentPosition();
        for (Integer num : this.mLastExportList) {
            if (num.intValue() < currentPosition - i || num.intValue() > currentPosition + i) {
                this.multiHandWriteView.getCurrentItemView().getCreationEngine().getCurveEngine().getPageSystem().releasePageBgImage(num.intValue());
            }
        }
    }

    private void registerCreationTaskReceiver() {
        this.operationReceiver = new BroadcastReceiver() { // from class: com.miui.notes.ui.fragment.PhoneMultiHandWriteFragment.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == null || !TextUtils.equals(Constants.OPERATION_INTENT, intent.getAction())) {
                    return;
                }
                PhoneMultiHandWriteFragment.this.handleOperationIntent(intent);
            }
        };
        IntentFilter intentFilter = new IntentFilter(Constants.OPERATION_INTENT);
        intentFilter.addAction(Constants.ADJUST_PAGE_SIZE_INTENT);
        LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).registerReceiver(this.operationReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOperationIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && TextUtils.equals(extras.getString("operationType"), "export")) {
            handleExportOperation(extras);
        }
    }

    private void handleExportOperation(Bundle bundle) {
        if (isDoubleCLickExport()) {
            Log.e(TAG, "double click export return");
            return;
        }
        int i = bundle.getInt("exportType");
        String string = bundle.getString("folderPath");
        String string2 = bundle.getString("folderName");
        ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList("exportPageIndexList");
        if (integerArrayList == null || integerArrayList.isEmpty()) {
            return;
        }
        startExportProcess(i, string, string2, integerArrayList);
    }

    private void startExportProcess(int i, String str, String str2, ArrayList<Integer> arrayList) {
        String uniqueFileName = i == 1 ? FileUtil.getUniqueFileName(str + InternalZipConstants.ZIP_FILE_SEPARATOR + str2) : str + InternalZipConstants.ZIP_FILE_SEPARATOR + FileUtil.getUniqueFolderName(str, str2);
        if (i == 2) {
            ExportImageManager.INSTANCE.getInstance().startExportImage(i, arrayList, uniqueFileName, this);
        }
        Log.d(TAG, "folder path:" + str + "---folder name:" + str2 + "----out path:" + uniqueFileName);
    }

    private static class DownloadFileReceiver extends SafeBroadcastReceiver<PhoneMultiHandWriteFragment> {
        public DownloadFileReceiver(PhoneMultiHandWriteFragment phoneMultiHandWriteFragment) {
            super(phoneMultiHandWriteFragment);
        }

        @Override // com.miui.common.base.SafeBroadcastReceiver, android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (hasReference()) {
                getWkObject().handleDownloadIntent(intent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDownloadIntent(Intent intent) {
        MultiHandWriteEntity multiHandWriteEntityLoadNote;
        if (this.multiHandWriteView == null || (multiHandWriteEntityLoadNote = MultiHandWriteDataUtils.loadNote(this.mNoteId)) == null || !TextUtils.equals(intent.getStringExtra("entName"), multiHandWriteEntityLoadNote.entName)) {
            return;
        }
        reloadHandWriteView(multiHandWriteEntityLoadNote);
        hideLoadingView();
    }

    private void reloadHandWriteView(MultiHandWriteEntity multiHandWriteEntity) {
        this.multiHandWriteView.load(multiHandWriteEntity.observeEntDir() + "/multi_page_ent.ent", createSimplePageLoadListener());
    }

    private void hideLoadingView() {
        View view = this.loadingView;
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        this.loadingView.setVisibility(8);
    }
}
