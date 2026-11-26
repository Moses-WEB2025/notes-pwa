package com.miui.notes.ui.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.Loader;
import com.miui.common.base.BaseActivity;
import com.miui.common.base.SafeBroadcastReceiver;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.DateFormatUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.handwritecommon.api.HandViewProvider;
import com.miui.handwritecommon.api.callback.ExportListener;
import com.miui.handwritecommon.api.callback.NoteDataLoadListener;
import com.miui.handwritecommon.api.singlepage.ISinglePageHandView;
import com.miui.handwritecommon.api.util.FilePathUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.AigcPicMetaData;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.feature.folder.ChooserFolderDialogActivity;
import com.miui.notes.feature.folder.CreateFolderDialog;
import com.miui.notes.home.PrivateNotesListActivity;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.model.HandWriteDataUtils;
import com.miui.notes.model.HandWriteEntity;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.ui.IFragmentController;
import com.miui.notes.ui.PhoneHandWriteHeaderController;
import com.miui.notes.ui.SnsEditActivity;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneHandWriteFragment extends DialogManagedFragment implements PhoneHandWriteHeaderController.HeaderOperationCallback {
    public static final String BLACK_PAPER_BACKGROUND = "black";
    public static final String DOT_PAPER_BACKGROUND = "dot";
    private static final String EXTRA_TAP_SHARE_MULTIPLE_INFO = "xiaomi.tap.share.MULTIPLE_INFO";
    private static final String EXTRA_TAP_SHARE_SUPPORT = "xiaomi.tap.share.SUPPORT";
    private static final int REQUEST_CODE_QUERY_FOLDER = 2;
    public static String TAG = "PhoneHandWriteFragment";
    public static final String WHITE_PAPER_BACKGROUND = "white";
    private static int bgIndex;
    private View customActionBarView;
    private ISinglePageHandView handWriteView;
    protected HomeViewModel homeViewModel;
    private ProgressDialog loadingDialog;
    private View loadingView;
    public IFragmentController mController;
    protected BaseFolderChooser mDialogFolderChooser;
    private long mFolderId;
    private PhoneHandWriteHeaderController mHeaderController;
    private boolean mIsFromPrivate;
    private long mNoteId;
    protected PasswordController mPasswordController;
    private long mSyncId;
    protected ActivityResultLauncher<Intent> mToShareResult;
    private long modifyDate;
    private String queryText;
    private boolean mNeedConfirmPass = false;
    protected ActivityResultLauncher<Intent> toChooseFolderResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.1
        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(ActivityResult activityResult) {
        }
    });
    protected PasswordController.OnSetPasswordListener mOnSetPasswordListener = new PasswordController.SimpleOnSetPasswordListener() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.2
        @Override // com.miui.notes.controller.PasswordController.SimpleOnSetPasswordListener, com.miui.notes.controller.PasswordController.OnSetPasswordListener
        public void onSuccess(int i) {
            if (i == 5102) {
                PhoneHandWriteFragment.this.hideNote();
            }
        }
    };
    protected PasswordController.LockStateListener mLockStateListener = new PasswordController.SimpleLockStateListener() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.3
        @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
        public void onLockStateChanged(PasswordController passwordController, int i) {
            super.onLockStateChanged(passwordController, i);
            if (PhoneHandWriteFragment.this.getView() != null) {
                if (i == 0) {
                    PhoneHandWriteFragment.this.getView().setVisibility(4);
                } else if (i == 2) {
                    PhoneHandWriteFragment.this.getView().setVisibility(0);
                }
            }
        }

        @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
        public boolean shouldAutoLock() {
            return PhoneHandWriteFragment.this.shouldConfirmPassword();
        }
    };
    private final DownloadFileReceiver downloadFileReceiver = new DownloadFileReceiver(this);

    protected int getThemeRes() {
        return R.style.NoteTheme_Edit;
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

    protected void initPasswordController() {
        PasswordController passwordController = new PasswordController(this, true);
        this.mPasswordController = passwordController;
        passwordController.setOnSetPasswordListener(this.mOnSetPasswordListener);
        this.mPasswordController.setLockSateListener(this.mLockStateListener);
        this.homeViewModel.getToDetailResult().observe(this, new Observer() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$initPasswordController$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPasswordController$0(Boolean bool) {
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.activeUnlock();
        }
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setThemeRes(getThemeRes());
        this.mToShareResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.4
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                if (PhoneHandWriteFragment.this.homeViewModel != null) {
                    PhoneHandWriteFragment.this.homeViewModel.toDetailResultCallback();
                }
            }
        });
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.phone_hand_write_fragment, viewGroup, false);
        this.handWriteView = HandViewProvider.addSinglePageView(viewGroup2, 2, false, false);
        initView(viewGroup2);
        return viewGroup2;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        this.homeViewModel = (HomeViewModel) new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        initPasswordController();
    }

    private void initView(View view) {
        View viewFindViewById = view.findViewById(R.id.loading_container);
        this.loadingView = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
            }
        });
        this.handWriteView.setCanWrite(false);
        this.handWriteView.setPreviewOnClick(new View.OnClickListener() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Toast.makeText(NoteApp.getInstance(), PhoneHandWriteFragment.this.getResources().getString(R.string.hand_write_only_support_tablet_editing), 0).show();
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        PhoneHandWriteHeaderController phoneHandWriteHeaderController = new PhoneHandWriteHeaderController(this, this);
        this.mHeaderController = phoneHandWriteHeaderController;
        this.customActionBarView = phoneHandWriteHeaderController.onInflateView(LayoutInflater.from(getContext()), (ViewGroup) getView(), null);
        getActionBar().setCustomView(this.customActionBarView, new ActionBar.LayoutParams(-1, -1));
        FragmentActivity activity = getActivity();
        if (activity != null && activity.getResources() != null) {
            getActionBar().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.v8_placeholder));
        }
        setImmersionMenuEnabled(true);
        Intent intent = getActivity() != null ? getActivity().getIntent() : null;
        if (intent != null) {
            this.queryText = getExtraUserQuery(intent);
            this.mIsFromPrivate = PrivateNotesListActivity.ACTION_FROM_PRIVACY;
            PrivateNotesListActivity.ACTION_FROM_PRIVACY = false;
            if (this.mIsFromPrivate) {
                getActivity().getWindow().addFlags(8192);
            } else {
                getActivity().getWindow().clearFlags(8192);
            }
            this.mNoteId = intent.getLongExtra("android.intent.extra.UID", 0L);
            HandWriteDataUtils.HandWriteNoteInfo handWriteNoteInfoQuery = HandWriteDataUtils.query(getActivity(), "_id=" + this.mNoteId);
            if (handWriteNoteInfoQuery != null) {
                this.mFolderId = handWriteNoteInfoQuery.getParentId();
                this.mSyncId = handWriteNoteInfoQuery.getSyncId();
                updateUI(HandWriteEntity.toHandWriteEntity(handWriteNoteInfoQuery.getHandWriteContent()), HandWriteDataUtils.getSortDate(handWriteNoteInfoQuery.getCreatedDate(), handWriteNoteInfoQuery.getModifiedDate()));
            }
        }
        if (activity != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.miui.notes.handwrite.download");
            if (Utils.isAboveSDK34()) {
                activity.registerReceiver(this.downloadFileReceiver, intentFilter, 2);
            } else {
                activity.registerReceiver(this.downloadFileReceiver, intentFilter);
            }
        }
    }

    private void updateUI(final HandWriteEntity handWriteEntity, long j) {
        View view;
        this.modifyDate = j;
        ISinglePageHandView iSinglePageHandView = this.handWriteView;
        if (iSinglePageHandView != null) {
            iSinglePageHandView.setCanWrite(false);
        }
        if (handWriteEntity != null) {
            String attachmentPath = !TextUtils.isEmpty(handWriteEntity.entName) ? AttachmentUtils.getAttachmentPath(getContext(), handWriteEntity.entName) : null;
            String str = handWriteEntity.title;
            ISinglePageHandView iSinglePageHandView2 = this.handWriteView;
            if (iSinglePageHandView2 != null) {
                iSinglePageHandView2.clear();
                this.handWriteView.setCanWrite(false);
                this.handWriteView.setTitleTime((String) DateFormatUtils.formatDateText(getContext(), j), (String) DateFormatUtils.formatTimeText(getContext(), j));
                this.handWriteView.setTitle(str);
                setCanvasBg(handWriteEntity.canvasBgType);
                this.handWriteView.load(attachmentPath, new NoteDataLoadListener() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.7
                    @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                    public void onStartLoad() {
                        PhoneHandWriteFragment.this.handWriteView.showLoading(PhoneHandWriteFragment.this.getLoadingBgColor(handWriteEntity.canvasBgType));
                    }

                    @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                    public void onLoadComplete(boolean z) {
                        Logger.INSTANCE.d(PhoneHandWriteFragment.TAG, "init onLoadComplete = " + z);
                        PhoneHandWriteFragment.this.handWriteView.setBackgroundIndex(PhoneHandWriteFragment.bgIndex);
                    }
                });
            }
            View view2 = this.loadingView;
            if (view2 != null) {
                view2.setVisibility(8);
            }
            if (TextUtils.isEmpty(attachmentPath)) {
                return;
            }
            try {
                if (new File(attachmentPath).exists() || (view = this.loadingView) == null) {
                    return;
                }
                view.setVisibility(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getLoadingBgColor(String str) {
        if (TextUtils.isEmpty(str)) {
            return UIUtils.isNightMode(getActivity()) ? R.color.black : R.color.white;
        }
        if ("black".equals(str)) {
            return R.color.black;
        }
        if (!"white".equals(str)) {
            "dot".equals(str);
        }
        return R.color.white;
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
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
        if (isNoteHidden()) {
            menu.findItem(R.id.hide).setVisible(false);
            menu.findItem(R.id.unhide).setVisible(true);
            menu.findItem(R.id.move_to).setVisible(false);
        } else {
            menu.findItem(R.id.hide).setVisible(true);
            menu.findItem(R.id.unhide).setVisible(false);
            menu.findItem(R.id.move_to).setVisible(true);
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.delete /* 2131362396 */:
                requestDeleteNote();
                break;
            case R.id.hide /* 2131362702 */:
                tryHideNote();
                break;
            case R.id.move_to /* 2131362990 */:
                startQueryFolder();
                break;
            case R.id.unhide /* 2131363736 */:
                Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_unhidden, 0).show();
                this.mFolderId = 0L;
                getActivity().getWindow().clearFlags(8192);
                NoteStore.unhide(getContext(), new long[]{this.mNoteId});
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public boolean isNoteHidden() {
        return this.mFolderId == -4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setCanvasBg(String str) {
        bgIndex = HandViewProvider.getSinglePageWhiteBg();
        if (TextUtils.isEmpty(str)) {
            if (UIUtils.isNightMode(NoteApp.getInstance())) {
                bgIndex = HandViewProvider.getSinglePageBlackBg();
            } else {
                bgIndex = HandViewProvider.getSinglePageWhiteBg();
                return;
            }
        }
        str.hashCode();
        switch (str) {
            case "dot":
                bgIndex = HandViewProvider.getSinglePageDotBg();
                break;
            case "black":
                bgIndex = HandViewProvider.getSinglePageBlackBg();
                break;
            case "white":
                bgIndex = HandViewProvider.getSinglePageWhiteBg();
                break;
        }
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
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.unlock();
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
            PhoneHandWriteFragment.this.showFolderChooser(cursor);
        }
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
        createFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.8
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                CreateFolderDialog createFolderDialog2 = (CreateFolderDialog) dialogInterface;
                if (createFolderDialog2.getFolderId() > 0) {
                    PhoneHandWriteFragment.this.moveCheckedItems(createFolderDialog2.getFolderId(), jArr, createFolderDialog2.getFolderName());
                }
            }
        });
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        createFolderDialog.show();
        manageDialog(createFolderDialog);
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
        private WeakReference<PhoneHandWriteFragment> iActivityRef;
        private long mFolderId;
        private String mFolderName;
        private long[] mIds;

        private EditFragNoteMoveTask(PhoneHandWriteFragment phoneHandWriteFragment, long[] jArr, long j, String str) {
            this.iActivityRef = new WeakReference<>(phoneHandWriteFragment);
            this.mFolderName = str;
            this.mIds = jArr;
            this.mFolderId = j;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Integer doInBackground(Void... voidArr) {
            Logger.INSTANCE.d(PhoneHandWriteFragment.TAG, "doInBackground...move");
            Thread.currentThread().setName("EditFragNoteMoveTask");
            WeakReference<PhoneHandWriteFragment> weakReference = this.iActivityRef;
            if (weakReference == null) {
                return null;
            }
            return Integer.valueOf(NoteStore.move(weakReference.get().getActivity(), this.mIds, this.mFolderId));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Integer num) {
            WeakReference<PhoneHandWriteFragment> weakReference;
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
        builder.setPositiveButton(R.string.menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$requestDeleteNote$1(dialogInterface, i);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestDeleteNote$1(DialogInterface dialogInterface, int i) {
        delete();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (this.mPasswordController == null || requireActivity().isChangingConfigurations()) {
            return;
        }
        this.mPasswordController.lockDelay();
    }

    private void delete() {
        AudioUtils.playDeleteRingtone();
        long j = this.mNoteId;
        if (j > 0 && HandWriteDataUtils.deleteNote(j)) {
            this.mFolderId = 0L;
            this.mNoteId = -1L;
            Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_delete_success, 0).show();
            getActivity().onBackPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideNote() {
        this.mFolderId = -4L;
        getActivity().getWindow().addFlags(8192);
        Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_hidden_edit_new, 0).show();
        NoteStore.hide(getContext(), new long[]{this.mNoteId});
    }

    protected void tryHideNote() {
        ISinglePageHandView iSinglePageHandView = this.handWriteView;
        if (iSinglePageHandView == null) {
            return;
        }
        if (this.mNoteId < 0 || (TextUtils.isEmpty(iSinglePageHandView.getTitle()) && this.handWriteView.isEmpty())) {
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
        ISinglePageHandView iSinglePageHandView = this.handWriteView;
        if (iSinglePageHandView != null) {
            iSinglePageHandView.release();
        }
        try {
            getActivity().unregisterReceiver(this.downloadFileReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override // com.miui.notes.ui.PhoneHandWriteHeaderController.HeaderOperationCallback
    public void onPerformAction(int i, Object... objArr) {
        if (i != 1) {
            return;
        }
        if (TextUtils.isEmpty(this.handWriteView.getTitle()) && this.handWriteView.isEmpty()) {
            Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
            return;
        }
        ISinglePageHandView iSinglePageHandView = this.handWriteView;
        if (iSinglePageHandView != null && iSinglePageHandView.getContentRangBottom() <= 10000.0f) {
            getShareImage();
        } else {
            Toast.makeText(NoteApp.getInstance(), R.string.canvas_is_too_large_to_share, 0).show();
        }
    }

    private void getShareImage() {
        this.handWriteView.exportImage(new ExportListener<Uri>() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.9
            @Override // com.miui.handwritecommon.api.callback.ExportListener
            public void exportFinish(boolean z, final List<? extends Uri> list) {
                PhoneHandWriteFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!list.isEmpty()) {
                            PhoneHandWriteFragment.this.shareNote(list);
                        }
                        PhoneHandWriteFragment.this.dismissLoadingDialog();
                    }
                });
            }

            @Override // com.miui.handwritecommon.api.callback.ExportListener
            public void exportStart() {
                PhoneHandWriteFragment phoneHandWriteFragment = PhoneHandWriteFragment.this;
                phoneHandWriteFragment.showLoadingDialog(phoneHandWriteFragment.getResources().getString(R.string.dlg_info_creating_message));
            }
        });
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
        if (this.loadingDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            this.loadingDialog = progressDialog;
            progressDialog.setIndeterminate(true);
            this.loadingDialog.setCanceledOnTouchOutside(false);
            this.loadingDialog.setCancelable(false);
        }
        this.loadingDialog.setMessage(str);
        this.loadingDialog.show();
    }

    public void shareNote(List<Uri> list) {
        if (list != null) {
            if (!RomUtils.isOS31Support()) {
                SnsEditActivity.launchActivity(this.mToShareResult, (BaseActivity) getActivity(), "hand_write", this.handWriteView.getTitle(), ((Object) DateFormatUtils.formatDateText(getContext(), this.modifyDate)) + StringUtils.SPACE + ((Object) DateFormatUtils.formatTimeText(getContext(), this.modifyDate)), 2, 16, (ArrayList<Uri>) list, this.handWriteView.getCurrentBackgroundIndex(), this.mFolderId, true, (AigcPicMetaData) null);
                return;
            }
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            ArrayList<Uri> arrayList2 = new ArrayList(list);
            File singlePageExportDir = FilePathUtils.getSinglePageExportDir(getContext());
            long length = 0;
            ClipData clipDataNewRawUri = null;
            for (Uri uri : arrayList2) {
                if (uri != null) {
                    String lastPathSegment = uri.getLastPathSegment();
                    if (singlePageExportDir != null && lastPathSegment != null) {
                        File file = new File(singlePageExportDir, lastPathSegment);
                        if (file.exists()) {
                            length += file.length();
                        }
                    }
                    if (clipDataNewRawUri == null) {
                        clipDataNewRawUri = ClipData.newRawUri("share_image", uri);
                    } else {
                        clipDataNewRawUri.addItem(new ClipData.Item(uri));
                    }
                    arrayList.add(uri);
                }
            }
            boolean z = arrayList.size() > 1;
            Intent intent = new Intent(z ? "android.intent.action.SEND_MULTIPLE" : "android.intent.action.SEND");
            intent.setFlags(1);
            intent.setType("image/png");
            if (!z) {
                intent.putExtra("android.intent.extra.STREAM", arrayList.get(0));
                intent.setClipData(ClipData.newRawUri(null, (Uri) arrayList.get(0)));
            } else {
                intent.setClipData(clipDataNewRawUri);
                intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
            }
            intent.putExtra("xiaomi.tap.share.SUPPORT", true);
            intent.putExtra("xiaomi.tap.share.MULTIPLE_INFO", String.valueOf(length));
            startActivity(Intent.createChooser(intent, null));
            return;
        }
        Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
    }

    private static class DownloadFileReceiver extends SafeBroadcastReceiver<PhoneHandWriteFragment> {
        public DownloadFileReceiver(PhoneHandWriteFragment phoneHandWriteFragment) {
            super(phoneHandWriteFragment);
        }

        @Override // com.miui.common.base.SafeBroadcastReceiver, android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            HandWriteDataUtils.HandWriteNoteInfo handWriteNoteInfoQuery;
            HandWriteEntity handWriteEntity;
            if (hasReference()) {
                final PhoneHandWriteFragment wkObject = getWkObject();
                if (wkObject.handWriteView == null || (handWriteNoteInfoQuery = HandWriteDataUtils.query(context, "_id=" + wkObject.mNoteId)) == null || (handWriteEntity = HandWriteEntity.toHandWriteEntity(handWriteNoteInfoQuery.getHandWriteContent())) == null) {
                    return;
                }
                String str = handWriteEntity.entName;
                PhoneHandWriteFragment.setCanvasBg(handWriteEntity.canvasBgType);
                if (TextUtils.equals(intent.getStringExtra("entName"), str)) {
                    wkObject.handWriteView.load(AttachmentUtils.getAttachmentPath(context, str), new NoteDataLoadListener() { // from class: com.miui.notes.ui.fragment.PhoneHandWriteFragment.DownloadFileReceiver.1
                        @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                        public void onStartLoad() {
                        }

                        @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                        public void onLoadComplete(boolean z) {
                            Logger.INSTANCE.d(PhoneHandWriteFragment.TAG, "DownloadFileReceiver onLoadComplete = " + z);
                            wkObject.handWriteView.setBackgroundIndex(PhoneHandWriteFragment.bgIndex);
                        }
                    });
                    if (wkObject.loadingView == null || wkObject.loadingView.getVisibility() != 0) {
                        return;
                    }
                    wkObject.loadingView.setVisibility(8);
                }
            }
        }
    }
}
