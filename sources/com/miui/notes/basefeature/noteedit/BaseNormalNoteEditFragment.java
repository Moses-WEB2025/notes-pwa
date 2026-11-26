package com.miui.notes.basefeature.noteedit;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Trace;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.hardware.DataSpace;
import androidx.lifecycle.Observer;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.ibm.icu.text.CharsetDetector;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.NoteLoadDataBuilder;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.base.SafeBroadcastReceiver;
import com.miui.common.constant.FrameConstant;
import com.miui.common.stat.MindNoteStat;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DualScreenManager;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.RxMultiAsyncTask;
import com.miui.common.tool.ShortcutsTools;
import com.miui.common.tool.TextProcessUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.async.Task;
import com.miui.common.tool.async.TaskManager;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.base.DoodleParams;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.maml.component.MamlView;
import com.miui.mishare.tapshare.TapShareDataHandler;
import com.miui.mishare.tapshare.TapShareManager;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.adapter.ChooseFolderAdapter;
import com.miui.notes.adapter.ResolverAdapter;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.assist.CtaActivityResult;
import com.miui.notes.base.utils.ThreadPoolUtils;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.miui.notes.component.ThemeChooserGroup;
import com.miui.notes.component.noteedit.IBaseNoteEditToolbarController;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.cta.CTAActivity;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.data.Contact;
import com.miui.notes.detail.BaseNoteDetailFragment;
import com.miui.notes.doodle.feature.DoodleActivity;
import com.miui.notes.doodle.feature.DoodleFragment;
import com.miui.notes.feature.folder.ChooserFolderDialogActivity;
import com.miui.notes.feature.folder.CreateFolderDialog;
import com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment;
import com.miui.notes.home.PrivateNotesListActivity;
import com.miui.notes.home.viewmodel.MoveToFolderNotifyData;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.ClipDataHelper;
import com.miui.notes.tool.DataUtils;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.tool.LogUtils;
import com.miui.notes.tool.NotesToolService;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SnippetFormatter;
import com.miui.notes.tool.TaskManagerUtils;
import com.miui.notes.tool.tapshareutils.TapShareDataModel;
import com.miui.notes.tool.tapshareutils.TapShareDataPrepareUtils;
import com.miui.notes.tool.tapshareutils.TapShareFileCleaner;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.tool.util.KoreaRegionUtils;
import com.miui.notes.tool.util.PackageNameUtils;
import com.miui.notes.ui.AlarmReceiver;
import com.miui.notes.ui.BaseActionBarController;
import com.miui.notes.ui.BaseDragListener;
import com.miui.notes.ui.IFragmentController;
import com.miui.notes.ui.LoadNoteAnimThemeBgTask;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.notes.ui.activity.WebViewBrainActivity;
import com.miui.notes.ui.fragment.BaseFolderChooser;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.notes.ui.widget.DateTimePickerDialog;
import com.miui.notes.ui.widget.SharePopupGroup;
import com.miui.notes.widget.NoteWidgetProvider;
import com.miui.notes.widget.NoteWidgetProvider_2x2;
import com.miui.notes.widget.NoteWidgetProvider_4x2;
import com.miui.notes.widget.NoteWidgetProvider_4x4;
import com.miui.notes.widget.NoteWidgetProvider_8x4;
import com.miui.pad.feature.notes.commonedit.AiChooserMenu;
import com.miui.pad.feature.notes.commonedit.ImageChooserMenu;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.theme.Theme;
import com.miui.richeditor.util.EditorUtils;
import com.miui.richeditor.util.ResourceParser;
import defpackage.R2;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import miuix.android.content.SystemIntent;
import miuix.animation.Folme;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.view.menu.HyperMenuUtils;
import miuix.core.util.IOUtils;
import miuix.internal.util.ViewUtils;
import miuix.navigator.Navigator;
import miuix.nestedheader.widget.NestedHeaderLayout;
import miuix.responsive.map.ScreenSpec;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseNormalNoteEditFragment extends BaseNoteDetailFragment implements WorkingNote.NoteSettingChangedListener, RecordTaskWorker, BaseActionBarController.EditCallback {
    protected static final String BUNDLE_IS_EDIT_TEXT_ENABLE = "is_edit_text_enable";
    protected static final String BUNDLE_THEME_ID = "last_theme_id";
    protected static final int CREATE_NEW_NOTE = 4;
    private static final String EXTRA_KEY_IS_PRIVACY = "extra_is_privacy";
    protected static final int FIX_SAVE_DURATION = 5000;
    protected static final int FIX_SAVE_DURATION_ON_PC_LIVING = 1000;
    protected static final int REFRESH_ALARM_TIME = 3;
    public static final int REQUEST_CODE_AS_EDIT = 16;
    private static final int REQUEST_CODE_QUERY_FOLDER = 2;
    private static final int REQUEST_CODE_UPDATE_FOLDER = 3;
    protected static final int SHOW_BROWSE_MODE_WARN = 8;
    protected static final int SHOW_CONTENT_OVER_LENGTH = 6;
    protected static final int SHOW_IMAGE_ANNOTATION_OVER_LENGTH = 7;
    protected static final int SHOW_LOADING = 5;
    protected static final int SYNC_SAVE_NOTE = 1;
    protected static final int SYNC_SAVE_NOTE_AND_SHUTDOWN = 2;
    private static final String TAG = "BaseNormalNoteEditFragment";
    public static View mActionbarView;
    protected static Toast mWarnToast;
    private Runnable callbackHandleIntentAction;
    protected boolean isLite;
    protected BaseActionBarController mActionBarController;
    protected AiChooserMenu mAiChooserMenu;
    private String mContactName;
    protected View mContentView;
    protected BaseFolderChooser mDialogFolderChooser;
    protected ActivityResultLauncher<Intent> mDoodleResult;
    protected IBaseNoteEditToolbarController mEditToolbarController;
    protected ValueAnimator mEditUiPaddingAnimator;
    protected ActivityResultLauncher<Intent> mEidtDooleResult;

    @Deprecated
    protected IFragmentController mFragmentController;
    protected ActivityResultLauncher<Intent> mGalleryResult;
    protected Handler mHandler;
    protected ImageChooserMenu mImageChooserMenu;
    protected boolean mIsInFullScreenMode;
    protected boolean mIsOnSaveInstanceCalled;
    protected NoteLoadTask mLoadTask;
    protected NestedHeaderLayout mNestedHeaderLayout;
    protected ActivityResultLauncher<PickVisualMediaRequest> mNewGalleryResult;
    private NoteContentObserver mNoteContentObserver;
    protected INoteInfoHeader mNoteExtraInfoController;
    protected INoteRichEditor mNoteRichEditor;
    protected PasswordController mPasswordController;
    private ActivityResultLauncher<String[]> mRequestOnEnterCallNote;
    protected boolean mResumed;
    protected View mRootView;
    private ViewUtils.RelativePadding mRootViewInitPadding;
    private RxMultiAsyncTask mRxMultiAsyncTask;
    protected SharePopupGroup mSharePopupWindow;
    protected ActivityResultLauncher<Intent> mSyncAccountResult;
    protected ActivityResultLauncher<Intent> mTakePhotoResult;
    protected ViewPropertyAnimator mThemeBgViewAnimator;
    private ActivityResultLauncher<Intent> mToCtaForSetContactName;
    private ActivityResultLauncher<Intent> mToCtaForSetPhoneNumber;
    protected ActivityResultLauncher<Intent> mToShareResult;
    protected ActivityResultLauncher<String[]> requestAudioPermission;
    private ActivityResultLauncher<String[]> requestAudioPermissionWithEditTask;
    private ActivityResultLauncher<String[]> requestAudioPermissionWithHandIntent;
    private AlertDialog sendToDestDialog;
    protected ActivityResultLauncher<Intent> toCtaBeforeAudio;
    private ActivityResultLauncher<Intent> toCtaBeforeAudioWithEditTask;
    private ActivityResultLauncher<Intent> toCtaBeforeAudioWithHandIntent;
    private ActivityResultLauncher<Intent> toCtaLauncher;
    private ActivityResultLauncher<Intent> toKoreaLauncher;
    protected Dialog mUpdateOrInstallDialog = null;
    protected int mWindowInitFlags = 0;
    protected boolean mIsFromPrivate = false;
    protected boolean mIsFromFloatWindow = false;
    protected boolean mIsFromAudioNoteShortCut = false;
    protected boolean mIsFromCallNote = false;
    protected boolean mIsFromAiAction = false;
    protected boolean mIsSendingToDesktop = false;
    protected boolean mPagePrepare = false;
    protected boolean mIsForceDark = false;
    protected boolean mIsChangingTheme = false;
    private boolean mIsRebuildRestoringPrivateNote = false;
    protected boolean mNeedDelayShowInput = false;
    protected boolean mIsKillSaveTaskBeforeDestroy = false;
    protected boolean mActionFromToolbarRichText = false;
    protected int mNavigationBarHeight = 0;
    protected EditorActionMode mActionMode = null;
    protected boolean mIsRecording = false;
    protected boolean mIsEditorTextChanged = false;
    protected boolean mIsNeedSetData = true;
    protected boolean mFromWidget = false;
    protected Theme mCurrentTheme = null;
    protected CompositeDisposable mResLoadDisposables = new CompositeDisposable();
    protected CompositeDisposable mBackTaskDisposables = new CompositeDisposable();
    protected CompositeDisposable mNoteOperateCompositeDisposable = new CompositeDisposable();
    protected Runnable mPendingEditIntentTask = null;
    protected Disposable mFolderNameGetTask = null;
    protected LocalBroadcastManager mLocalBroadcastManager = null;
    protected ExecutorService mExecutorService = Utils.newDiscardPolicySingleThreadExecutor();
    protected MyDualScreenEditReceiver mDualScreenEditReceiver = new MyDualScreenEditReceiver(this);
    protected boolean mIsEditNotes = false;
    public String saveNewCachePath = null;
    protected long mAudioClickTime = 0;
    protected boolean mIsInBrowseMode = false;
    protected boolean mIsInPcNote = false;
    protected boolean mIsFirstCreate = true;
    protected long mToastTime = 0;
    protected boolean mInAiMode = false;
    protected boolean isAudioRecorderShowed = false;
    protected int mInsetBottom = 0;
    public int SHARE_PREVIEW_IMAGE_MIN_HEIGHT = 800;
    public int SHARE_PREVIEW_ANIM_IMAGE_MIN_HEIGHT = 600;
    public int SHARE_PREVIEW_IMAGE_MAX_HEIGHT = 2000;
    protected PasswordController.OnSetPasswordListener mOnSetPasswordListener = new PasswordController.SimpleOnSetPasswordListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.1
        @Override // com.miui.notes.controller.PasswordController.SimpleOnSetPasswordListener, com.miui.notes.controller.PasswordController.OnSetPasswordListener
        public void onSuccess(int i) {
            if (i == 5102) {
                BaseNormalNoteEditFragment.this.hideNote();
            }
        }
    };
    protected PasswordController.LockStateListener mLockStateListener = new PasswordController.SimpleLockStateListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.2
        @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
        public void onLockStateChanged(PasswordController passwordController, int i) {
            super.onLockStateChanged(passwordController, i);
            if (i == 0) {
                BaseNormalNoteEditFragment.this.mNestedHeaderLayout.setVisibility(4);
                BaseHybridNoteEditFragment.hideAiFloatingWindowForLock();
            } else if (i == 2) {
                BaseNormalNoteEditFragment.this.mNestedHeaderLayout.setVisibility(0);
            }
        }

        @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
        public boolean shouldAutoLock() {
            return BaseNormalNoteEditFragment.this.shouldConfirmPassword();
        }
    };
    protected ThemeChooserGroup.OnThemeChosenListener mThemeChosenListener = new ThemeChooserGroup.OnThemeChosenListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda26
        @Override // com.miui.notes.component.ThemeChooserGroup.OnThemeChosenListener
        public final void onThemeChosen(int i) {
            this.f$0.lambda$new$0(i);
        }
    };
    protected MamlView mAnimThemeBgView = null;
    protected ImageView mAnimThemeLiteBgView = null;
    protected PopupWindow mShowingPopupWindow = null;
    protected MyAudioTaskReceiver mAudioTaskEndReceiver = new MyAudioTaskReceiver(this);
    protected AlertDialog mDualScreenEditTipDialog = null;
    protected BroadcastReceiver mAiActionReceiver = new BroadcastReceiver() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "onReceive: action: " + intent.getAction());
            if (BaseNormalNoteEditFragment.this.isAdded() && BaseNormalNoteEditFragment.this.isVisible()) {
                if (NoteIntent.ACTION_SHARE_TEXT_NOTES.equals(intent.getAction())) {
                    BaseNormalNoteEditFragment.this.doneEdit();
                    BaseNormalNoteEditFragment.this.sendAsText();
                    AiActionUtil.asyncNotifyResult(0);
                    return;
                }
                if (NoteIntent.ACTION_SHARE_IMAGE_NOTES.equals(intent.getAction())) {
                    BaseNormalNoteEditFragment.this.sendAsImage();
                    AiActionUtil.asyncNotifyResult(0);
                    return;
                }
                if (NoteIntent.ACTION_INSERT_IMAGE_NOTES.equals(intent.getAction())) {
                    String stringExtra = intent.getStringExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE);
                    boolean zIsEmpty = TextUtils.isEmpty(stringExtra);
                    String stringExtra2 = intent.getStringExtra(NoteIntent.EXTRA_INSERT_IMAGE_NOTE);
                    boolean zIsEmpty2 = TextUtils.isEmpty(stringExtra2);
                    String stringExtra3 = intent.getStringExtra(NoteIntent.EXTRA_INSERT_CONTENT_NOTE);
                    boolean zIsEmpty3 = TextUtils.isEmpty(stringExtra3);
                    if (!zIsEmpty2) {
                        BaseNormalNoteEditFragment.this.insertImages(false, Uri.parse(stringExtra2));
                    }
                    if (!zIsEmpty3) {
                        BaseNormalNoteEditFragment.this.insertContent(stringExtra3);
                    }
                    if (!zIsEmpty) {
                        BaseNormalNoteEditFragment.this.insertTitle(stringExtra);
                    }
                    if (zIsEmpty && zIsEmpty2 && zIsEmpty3) {
                        AiActionUtil.asyncNotifyResult(-1);
                    }
                }
            }
        }
    };
    boolean mNeedConfirmPass = false;
    private final TapShareManager.TapShareSendCallback mTapShareSendCallback = new TapShareManager.TapShareSendCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.5
        @Override // com.miui.mishare.tapshare.TapShareManager.TapShareSendCallback
        public boolean onTapShareSend(final TapShareDataHandler tapShareDataHandler) {
            WorkingNote workingNote;
            synchronized (BaseNormalNoteEditFragment.this) {
                workingNote = BaseNormalNoteEditFragment.this.mWorkingNote;
            }
            if (workingNote == null || BaseNormalNoteEditFragment.this.isWorkingNoteEmpty()) {
                Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
                tapShareDataHandler.cancelSend(1, "Empty note");
                return true;
            }
            BaseNormalNoteEditFragment.this.saveNoteInstantlyIfNeeded();
            final TapShareDataModel.SingleNoteTapShareData singleNoteTapShareDataPrepareSingleNoteDataWithPreview = TapShareDataPrepareUtils.prepareSingleNoteDataWithPreview(NoteApp.getInstance(), workingNote, null, BaseNormalNoteEditFragment.this.getPreviewView());
            if (singleNoteTapShareDataPrepareSingleNoteDataWithPreview == null) {
                tapShareDataHandler.cancelSend(1, "Fail to prepare data");
                return true;
            }
            new Thread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.5.1
                @Override // java.lang.Runnable
                public void run() {
                    if (TapShareDataPrepareUtils.shareSingleNote(BaseNormalNoteEditFragment.this.getContext(), singleNoteTapShareDataPrepareSingleNoteDataWithPreview, tapShareDataHandler)) {
                        return;
                    }
                    tapShareDataHandler.cancelSend(1, "Fail to share data");
                }
            }).start();
            return true;
        }

        @Override // com.miui.mishare.tapshare.TapShareManager.TapShareSendCallback
        public void onTapShareCancel(TapShareDataHandler tapShareDataHandler) {
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "Cancel tapShare");
            BaseNormalNoteEditFragment.this.cleanTapShareFilesAsync();
        }
    };

    public interface RenderGenerateCallback {
        void onComplete(Uri uri, Uri uri2);

        default void onError(Exception exc) {
        }
    }

    static /* synthetic */ void lambda$showInstallRecordDialog$26(DialogInterface dialogInterface, int i) {
    }

    static /* synthetic */ void lambda$showUpdateRecordDialog$28(DialogInterface dialogInterface, int i) {
    }

    protected abstract void checkEditEnable();

    protected abstract BaseActionBarController createHeaderBarController(View view);

    protected abstract INoteInfoHeader createNoteExtraInfoController(View view, View view2);

    protected abstract IBaseNoteEditToolbarController createToolBarController(View view);

    public void dragToEditMode() {
    }

    protected abstract BaseDragListener getActionBarBaseDragListener();

    protected abstract int getAutoSaveCommonDuration();

    abstract EditNoteLoadTask getEditNoteLoadTask(Intent intent);

    protected abstract int getLayoutResId();

    protected abstract int getThemeResId();

    protected abstract void initNoteEditor(View view);

    protected abstract void initNoteInfoHeader();

    protected abstract void initToolbarController(View view);

    protected void initWindowStatusInPhone() {
    }

    public void insertContent(String str) {
    }

    public boolean insertImageFromClipData(ClipData clipData, boolean z) {
        return false;
    }

    public boolean insertImageFromDragEvent(DragEvent dragEvent, boolean z) {
        return false;
    }

    abstract void insertImages(boolean z, Uri... uriArr);

    public void insertTitle(String str) {
    }

    protected boolean isLoadingData() {
        return false;
    }

    protected boolean isWorkingNoteIdInvalid(long j) {
        return j <= 0 && j != -2;
    }

    protected boolean loadNoteFromEntity(NoteLoadDataEntity noteLoadDataEntity) {
        return false;
    }

    public void onLoadDataStart() {
    }

    public void onNetworkStateChanged(boolean z) {
    }

    public void onShowAddNewNote(boolean z) {
    }

    protected void recordNotifyNavigation(boolean z) {
    }

    public void refreshAlertTime() {
    }

    protected void requestXiaoAiPermission() {
    }

    protected abstract void resetEditContent();

    protected void sendAsDuplicate() {
    }

    protected void sendAsImage() {
    }

    protected void sendAsMarkdown() {
    }

    protected abstract void setEnableEdit(boolean z);

    protected void setmEidtDoodleResult(Intent intent) {
    }

    protected void showAttachmentAiChooser(View view) {
    }

    protected void showAttachmentChooserDialog(View view) {
    }

    protected void showDualScreenEditTipDialog() {
    }

    abstract void startInsertDoodle(String str);

    protected void updateEditInfo(boolean z) {
    }

    protected void updateNoteFromDualScreen() {
    }

    protected void updateTitleUserQuery(Editable editable, CharSequence charSequence, ArrayList<String> arrayList) {
    }

    protected void updateUserQuery(String str, boolean z, ArrayList<String> arrayList) {
    }

    protected void updateWorkingTextAndTitle() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i) {
        if (this.mWorkingNote != null) {
            this.mWorkingNote.setThemeId(i);
        }
    }

    public WorkingNote getWorkingNote() {
        return this.mWorkingNote;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public long getNoteId() {
        if (this.mWorkingNote == null || this.mWorkingNote.getIsInvalid()) {
            return this.mLoadTask != null ? 0L : -1L;
        }
        return this.mWorkingNote.getNoteId();
    }

    protected long getAlarmTime() {
        if (this.mWorkingNote != null) {
            return this.mWorkingNote.getAlertDate();
        }
        return 0L;
    }

    @Override // com.miui.notes.ui.BaseActionBarController.EditCallback
    public boolean isInvalid() {
        return this.mWorkingNote == null || this.mWorkingNote.getIsInvalid() || !this.mResumed;
    }

    protected boolean isFromPrivate() {
        return this.mIsFromPrivate;
    }

    protected final boolean isWorkingNoteEmpty() {
        return this.mWorkingNote == null || isWorkingNoteIdInvalid(this.mWorkingNote.getNoteId()) || (TextUtils.isEmpty(this.mWorkingNote.getContent()) && TextUtils.isEmpty(this.mWorkingNote.getTitle()));
    }

    protected boolean shouldConfirmPassword() {
        if (this.mWorkingNote == null || !this.mWorkingNote.isHidden()) {
            return false;
        }
        return !isFromPrivate() || this.mNeedConfirmPass;
    }

    public boolean isSupportCreateMindNote() {
        return FolderModel.isSupportCreateMindNote(this.mFolderId) && !this.mIsFromCallNote;
    }

    protected boolean isHideFolderChooser() {
        return FolderModel.isCannotMoveFolder(this.mFolderId) || this.mIsFromCallNote;
    }

    protected void showContentOverLength(int i) {
        int i2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (Math.abs(this.mToastTime - jCurrentTimeMillis) < 2100) {
            this.mToastTime = jCurrentTimeMillis;
            return;
        }
        this.mToastTime = jCurrentTimeMillis;
        switch (i) {
            case -2:
                i2 = R.string.toast_reach_text_limit_truncate;
                break;
            case -1:
                i2 = R.string.warn_cannot_add_new_content;
                break;
            case 0:
            default:
                i2 = R.string.warn_cannot_add_new_element;
                break;
            case 1:
                i2 = R.string.warn_cannot_add_new_style;
                break;
            case 2:
                i2 = R.string.warn_cannot_add_new_checkbox;
                break;
            case 3:
                i2 = R.string.warn_cannot_add_new_bullet;
                break;
            case 4:
                i2 = R.string.warn_cannot_add_new_picture;
                break;
            case 5:
                i2 = R.string.warn_cannot_add_new_doodle;
                break;
            case 6:
                i2 = R.string.warn_cannot_add_new_link_card;
                break;
        }
        Toast.makeText(getContext(), getContext().getString(i2), 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showImageAnnotationWarning() {
        Toast.makeText(getContext(), String.format(getContext().getString(R.string.image_annotation_edit_length_warning), 1000), 0).show();
    }

    protected boolean isThisNoteDoExcerpt() {
        return this.mWorkingNote != null && this.mWorkingNote.getNoteId() == FloatWindowManagerAgent.getExcerptId();
    }

    public boolean isInEditAction() {
        EditorActionMode editorActionMode = this.mActionMode;
        if (editorActionMode == null) {
            return false;
        }
        return editorActionMode == EditorActionMode.EDIT || this.mActionMode == EditorActionMode.EDIT_TITLE || this.mActionMode == EditorActionMode.EDIT_RECODE;
    }

    protected BaseActionBarController getActionBarController() {
        return this.mActionBarController;
    }

    protected IBaseNoteEditToolbarController getEditToolBarController() {
        return this.mEditToolbarController;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mHandler = new BaseEditHandler();
        setThemeRes(getThemeResId());
        this.isLite = LiteUtils.isLiteOrMiddle();
        if (RomUtils.isPadMode()) {
            LiveDataEventBus.with(LiveDataEventBus.BUS_MODIFY_FOLDER_NAME, Long.class).observe(this, new Observer() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    this.f$0.lambda$onCreate$1((Long) obj);
                }
            });
        }
        this.mIsFirstCreate = true;
        IntentFilter intentFilter = new IntentFilter(NoteIntent.ACTION_SHARE_TEXT_NOTES);
        intentFilter.addAction(NoteIntent.ACTION_SHARE_IMAGE_NOTES);
        intentFilter.addAction(NoteIntent.ACTION_INSERT_IMAGE_NOTES);
        if (getActivity() != null) {
            if (Utils.isAboveSDK34()) {
                getActivity().registerReceiver(this.mAiActionReceiver, intentFilter, 2);
            } else {
                getActivity().registerReceiver(this.mAiActionReceiver, intentFilter);
            }
        }
        Intent intent = getActivity().getIntent();
        boolean z = intent != null && intent.getBooleanExtra(EditActivity.KEYGUARD_SHORTCUT, false);
        Logger.INSTANCE.d(TAG, "isFromKeyGard: " + z);
        if (z) {
            requestDismissKeyguardOrSetShowWhenLocked();
        }
        LiveDataEventBus.with("collapse_theme_panel", Boolean.class).observe(this, new Observer() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onCreate$2((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(Long l) {
        if (l == null || this.mFolderId != l.longValue()) {
            return;
        }
        asyncUpdateFolderName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(Boolean bool) {
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.checkAndCollapseThemePanel();
        }
    }

    private void requestDismissKeyguardOrSetShowWhenLocked() {
        Logger.INSTANCE.d(TAG, "requestDismissKeyguardOrSetShowWhenLocked");
        KeyguardManager keyguardManager = (KeyguardManager) getContext().getSystemService("keyguard");
        if (keyguardManager.isKeyguardLocked()) {
            Logger.INSTANCE.d(TAG, "requestDismissKeyguardOrSetShowWhenLocked KeyguardLocked");
            keyguardManager.requestDismissKeyguard(getActivity(), new KeyguardManager.KeyguardDismissCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.4
                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissSucceeded() {
                    super.onDismissSucceeded();
                    Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "onDismissSucceeded");
                    if (!NotesListActivity.isShowingCta) {
                        BaseNormalNoteEditFragment.this.requestCta();
                    }
                    if (!RomUtils.isInternationalBuild() || Utils.isInThisCountry(KoreaRegionUtils.REGION_KOREA) || PreferenceUtils.getPostNotificationsPermissionInfoShowed(BaseNormalNoteEditFragment.this.getContext())) {
                        return;
                    }
                    PermissionUtils.showNotificationPermission(BaseNormalNoteEditFragment.this.getActivity());
                    PreferenceUtils.setPostNotificationsPermissionInfoShowed(BaseNormalNoteEditFragment.this.getActivity(), true);
                }

                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissCancelled() {
                    super.onDismissCancelled();
                    Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "onDismissCancelled");
                    FragmentActivity activity = BaseNormalNoteEditFragment.this.getActivity();
                    if (activity != null) {
                        activity.finish();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestCta() {
        Logger.INSTANCE.d(TAG, "requestCta");
        if (!Utils.isInThisCountry(KoreaRegionUtils.REGION_KOREA)) {
            Intent intent = getActivity() != null ? getActivity().getIntent() : null;
            if ((intent == null || TextUtils.isEmpty(intent.getStringExtra("android.intent.extra.PHONE_NUMBER"))) && !PreferenceUtils.isCTAAccepted(getContext()) && PermissionUtils.supportNewPermissionStyle() && PreferenceUtils.isFirstHandle(getContext())) {
                PermissionNewUtils.showCtaDialog(this.toCtaLauncher);
                return;
            }
            return;
        }
        KoreaRegionUtils.showPermissionsForKorea(this.toKoreaLauncher);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Logger.INSTANCE.d(TAG, "onInflateView");
        View viewInflate = layoutInflater.inflate(getLayoutResId(), viewGroup, false);
        Logger.INSTANCE.d(TAG, "onInflateView finished");
        return viewInflate;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect rect) {
        super.onContentInsetChanged(rect);
        ViewUtils.RelativePadding relativePadding = new ViewUtils.RelativePadding(this.mRootViewInitPadding);
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this.rootView);
        relativePadding.start += zIsLayoutRtl ? rect.right : rect.left;
        relativePadding.top += rect.top;
        relativePadding.end += zIsLayoutRtl ? rect.left : rect.right;
        relativePadding.applyToView(this.rootView);
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.updateNavigationHeight(rect.bottom);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        this.mRootViewInitPadding = new ViewUtils.RelativePadding(ViewCompat.getPaddingStart(this.rootView), this.rootView.getPaddingTop(), ViewCompat.getPaddingEnd(this.rootView), this.rootView.getPaddingBottom());
        FragmentActivity activity = getActivity();
        this.mLocalBroadcastManager = LocalBroadcastManager.getInstance(activity);
        this.mNavigationBarHeight = UIUtils.getNavigationBarHeightByWindow(activity, view);
        this.mRootView = view;
        this.mActionBarController = createHeaderBarController(view);
        this.mNoteContentObserver = new NoteContentObserver(this, this.mHandler);
        initView(view);
        initNoteInfoHeader();
        initActionBar();
        initPasswordController();
        doRegisterWork();
        this.homeViewModel.getMoveToFolderNotifyLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$3((MoveToFolderNotifyData) obj);
            }
        });
        this.homeViewModel.getNoteMultiModeLiveData().observe(this, new Observer() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$4((Integer) obj);
            }
        });
        if (bundle != null && this.homeViewModel != null) {
            Logger.INSTANCE.d(TAG, "onViewInflated toDetailResultCallback: ");
            this.homeViewModel.toDetailResultCallback();
        }
        initWindowStatusInPhone();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$3(MoveToFolderNotifyData moveToFolderNotifyData) {
        updateFolderIdAndName(moveToFolderNotifyData.getFolderId(), moveToFolderNotifyData.getFolderName(), moveToFolderNotifyData.getIds());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$4(Integer num) {
        if (num.intValue() == 1) {
            onEnterActionMode();
        } else if (num.intValue() == 0) {
            onExitActionMode();
        }
    }

    public void updateFolderIdAndName(long j, String str, long[] jArr) {
        INoteInfoHeader iNoteInfoHeader;
        if (this.mWorkingNote == null || jArr == null) {
            return;
        }
        for (long j2 : jArr) {
            if (this.mWorkingNote.getNoteId() == j2) {
                this.mFolderId = j;
                this.mWorkingNote.setFolderId(j);
                if (TextUtils.isEmpty(str) || (iNoteInfoHeader = this.mNoteExtraInfoController) == null) {
                    return;
                }
                iNoteInfoHeader.updateFolderName(str);
                return;
            }
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        return super.onBackPressed();
    }

    private void doRegisterWork() {
        Logger.INSTANCE.d(TAG, "doRegisterWork");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_REQUEST);
        intentFilter.addAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_CHANGED);
        intentFilter.addAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_CONTENT_SYNC);
        if (getActivity() != null) {
            if (Utils.isAboveSDK34()) {
                getActivity().registerReceiver(this.mDualScreenEditReceiver, intentFilter, 2);
            } else {
                getActivity().registerReceiver(this.mDualScreenEditReceiver, intentFilter);
            }
            Logger.INSTANCE.d(TAG, "doRegisterWork->mDualScreenEditReceiver");
        }
        if (isSupportRecordTaskWorker()) {
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction(FrameConstant.BROADCAST_ACTION_NOTE_EDITOR_AUDIO);
            this.mLocalBroadcastManager.registerReceiver(this.mAudioTaskEndReceiver, intentFilter2);
            Logger.INSTANCE.d(TAG, "doRegisterWork->isSupportRecordTaskWorker,mDualScreenEditReceiver");
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        dismissAllDialogOrPanel();
        registerNoteContentObserver(false);
        boolean booleanExtra = intent.getBooleanExtra(NoteWidgetProvider.INTENT_EXTRA_IS_HIDDEN, false);
        if ("android.intent.action.VIEW".equals(intent.getAction()) && this.mWorkingNote != null && !booleanExtra) {
            long extraNoteIdFromIntent = getExtraNoteIdFromIntent(intent);
            String searchExtraDataKeyFromIntent = getSearchExtraDataKeyFromIntent(intent);
            if (extraNoteIdFromIntent > 0 && extraNoteIdFromIntent == getNoteId() && TextUtils.equals(searchExtraDataKeyFromIntent, this.mSearchText)) {
                return;
            }
        }
        getActivity().setIntent(intent);
        saveNoteInstantlyIfNeeded();
        Theme theme = this.mCurrentTheme;
        if (theme != null) {
            if (theme instanceof AnimTheme) {
                final AnimTheme animTheme = (AnimTheme) theme;
                MamlView mamlView = this.mAnimThemeBgView;
                if (mamlView != null) {
                    ViewPropertyAnimator viewPropertyAnimatorAnimate = mamlView.animate();
                    this.mThemeBgViewAnimator = viewPropertyAnimatorAnimate;
                    viewPropertyAnimatorAnimate.alpha(0.0f).withEndAction(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda28
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onNewIntent$5(animTheme);
                        }
                    }).setDuration(300L).start();
                }
            }
            this.mCurrentTheme = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNewIntent$5(AnimTheme animTheme) {
        animTheme.pause();
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeViewAt(0);
        }
        onAnimThemeBgViewChanged(null);
        onNoteThemeChangeFinish();
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        Logger.INSTANCE.d(TAG, "onSaveInstanceState:");
        if (this.mWorkingNote != null) {
            if (this.mWorkingNote.getNoteId() == -2 && (!this.mWorkingNote.isEmpty() || this.mNewDoodle)) {
                Logger.INSTANCE.d(TAG, "onSaveInstanceState,temp new id");
                saveNoteInstantlyIfNeeded();
            }
            this.mWorkingNote.onSaveInstanceState(bundle);
            bundle.putString("user_query", this.mSearchText);
            if (this.mHighLightKeyWords != null) {
                bundle.putStringArrayList("user_ai_query", this.mHighLightKeyWords);
            }
            if (this.mNewDoodle) {
                bundle.putBoolean("new_doodle", true);
                this.mNewDoodle = false;
            }
            Theme theme = this.mCurrentTheme;
            if (theme != null) {
                bundle.putInt(BUNDLE_THEME_ID, theme.getId());
            } else {
                bundle.putInt(BUNDLE_THEME_ID, this.mWorkingNote.getThemeId());
            }
        }
        EditorActionMode editorActionMode = this.mActionMode;
        if (editorActionMode != null) {
            bundle.putBoolean(BUNDLE_IS_EDIT_TEXT_ENABLE, editorActionMode.isEditTextEnable());
        }
        bundle.putBoolean(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, this.mIsFromFloatWindow);
        super.onSaveInstanceState(bundle);
        this.mIsOnSaveInstanceCalled = true;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        WorkingNote workingNoteLoadNote;
        Handler handler;
        super.onResume();
        Logger.INSTANCE.d(TAG, "onResume");
        FragmentActivity activity = getActivity();
        this.mIsInFullScreenMode = UIUtils.isInFullWindowGestureMode(activity);
        int navigationBarHeightByWindow = UIUtils.getNavigationBarHeightByWindow(activity, this.mRootView);
        this.mNavigationBarHeight = navigationBarHeightByWindow;
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.updateNavigationHeight(navigationBarHeightByWindow);
            this.mEditToolbarController.updateLayout();
        }
        registerTapShareCallBack();
        this.mResumed = true;
        this.mIsOnSaveInstanceCalled = false;
        if (this.mPasswordController != null && !this.mIsFromRebuild) {
            this.mPasswordController.unlock();
        }
        if (this.mIsFromRebuild) {
            this.mIsRebuildRestoringPrivateNote = true;
        }
        this.mIsFromRebuild = false;
        if (this.mWorkingNote != null && this.mWorkingNote.getAlertDate() > 0 && (handler = this.mHandler) != null) {
            handler.removeMessages(3);
            this.mHandler.sendEmptyMessage(3);
        }
        if (this.mDialogFolderChooser != null && UIUtils.isInMultiWindowMode(activity)) {
            this.mDialogFolderChooser.cancel();
        }
        if (!this.isAudioRecorderShowed) {
            resumeAnimThemeBg();
        } else {
            this.isAudioRecorderShowed = false;
        }
        registerNoteContentObserver(true);
        this.mNoteContentObserver.onChange(false);
        synchronized (this) {
            if (this.mWorkingNote != null && this.mWorkingNote.getNoteId() > 0 && NotesToolService.consumeIfChanged(this.mWorkingNote.getNoteId()) && (workingNoteLoadNote = WorkingNote.loadNote("_id=" + this.mWorkingNote.getNoteId(), null)) != null) {
                this.mWorkingNote = workingNoteLoadNote;
                updateEditor(false);
            }
        }
        this.mNoteRichEditor.updateFontSizeId(PreferenceUtils.getFontSize(activity, 1));
        handleBrowseMode();
        if (this.mIsFirstCreate && this.mHandler != null && getActivity() != null && getActivity().isInMultiWindowMode() && this.homeViewModel != null && Navigator.get(this) != null && Navigator.get(this).getNavigationMode() == Navigator.Mode.NLC) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onResume$6();
                }
            }, 300L);
        }
        BaseNoteApp.isEditInForeground = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$6() {
        this.homeViewModel.setKeyboardChange(true);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mHandler.removeMessages(3);
        this.mNeedConfirmPass = true;
        Logger.INSTANCE.d(TAG, "on pause,save note async");
        asyncSaveNoteInstantlyIfNeeded();
        pauseAnimThemeBg();
        registerNoteContentObserver(false);
        this.mIsFirstCreate = false;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
        INoteInfoHeader iNoteInfoHeader;
        super.onResponsiveLayout(configuration, screenSpec, z);
        if (this.mScreenSpec.type == screenSpec.type || (iNoteInfoHeader = this.mNoteExtraInfoController) == null) {
            return;
        }
        iNoteInfoHeader.dismissMenu();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mHandler.removeMessages(3);
        if (this.mPasswordController != null && !requireActivity().isChangingConfigurations()) {
            this.mPasswordController.lockDelay();
        }
        Dialog dialog = this.mUpdateOrInstallDialog;
        if (dialog != null) {
            dialog.dismiss();
            this.mUpdateOrInstallDialog = null;
        }
        this.mResumed = false;
        this.mIsEditNotes = false;
        BaseNoteApp.isEditInForeground = false;
        if (this.mTapShareSendCallback != null) {
            TapShareManager.getInstance().unregisterSendCallback(requireActivity(), this.mTapShareSendCallback);
            Logger.INSTANCE.d(TAG, "onStop unregister mTapShareSendCallback");
        }
    }

    private void cleanTapShareFiles() {
        try {
            TapShareFileCleaner.asyncCleanAllTapShareFiles(getContext());
            Logger.INSTANCE.d(TAG, "Started cleaning TapShare files");
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error cleaning TapShare files: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanTapShareFilesAsync() {
        new Thread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (BaseNormalNoteEditFragment.this.mWorkingNote != null && !BaseNormalNoteEditFragment.this.mWorkingNote.getIsInvalid()) {
                        TapShareDataModel.SingleNoteTapShareData singleNoteTapShareData = new TapShareDataModel.SingleNoteTapShareData();
                        singleNoteTapShareData.noteId = BaseNormalNoteEditFragment.this.mWorkingNote.getNoteId();
                        TapShareFileCleaner.cleanSingleNoteTapShareData(BaseNormalNoteEditFragment.this.getContext(), singleNoteTapShareData);
                    }
                    TapShareFileCleaner.cleanAllTapShareFilesInCache(BaseNormalNoteEditFragment.this.getContext());
                    Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "Cleaned TapShare files on cancel");
                } catch (Exception e) {
                    Logger.INSTANCE.e(BaseNormalNoteEditFragment.TAG, "Error cleaning TapShare files on cancel: " + e.getMessage());
                }
            }
        }).start();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        dismissAllDialogOrPanel();
        registerNoteContentObserver(false);
        if (this.mWorkingNote != null) {
            this.mWorkingNote.removeOnSettingStatusChangedListener(this);
        }
        RxMultiAsyncTask rxMultiAsyncTask = this.mRxMultiAsyncTask;
        if (rxMultiAsyncTask != null) {
            rxMultiAsyncTask.cancel();
        }
        CompositeDisposable compositeDisposable = this.mResLoadDisposables;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        CompositeDisposable compositeDisposable2 = this.mBackTaskDisposables;
        if (compositeDisposable2 != null) {
            compositeDisposable2.clear();
        }
        CompositeDisposable compositeDisposable3 = this.mNoteOperateCompositeDisposable;
        if (compositeDisposable3 != null) {
            compositeDisposable3.clear();
        }
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.hide(false);
            this.mEditToolbarController.onDestroy();
        }
        BaseActionBarController baseActionBarController = this.mActionBarController;
        if (baseActionBarController != null) {
            baseActionBarController.onDestroy();
        }
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.destroy();
        }
        BaseFolderChooser baseFolderChooser = this.mDialogFolderChooser;
        if (baseFolderChooser != null) {
            baseFolderChooser.cancel();
        }
        releaseAnimThemeBgView();
        View view = this.mRootView;
        if (view != null && view.getHandler() != null) {
            this.mRootView.getHandler().removeCallbacksAndMessages(null);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        doUnRegisterWork();
        super.onDestroy();
    }

    private void doUnRegisterWork() {
        MyAudioTaskReceiver myAudioTaskReceiver;
        try {
            if (this.mDualScreenEditReceiver != null && getActivity() != null) {
                getActivity().unregisterReceiver(this.mDualScreenEditReceiver);
                this.mDualScreenEditReceiver.clear();
                Logger.INSTANCE.d(TAG, "unregister mDualScreenEditReceiver");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (getActivity() != null && this.mAiActionReceiver != null) {
                getActivity().unregisterReceiver(this.mAiActionReceiver);
                Logger.INSTANCE.d(TAG, "unregister mAiActionReceiver");
            }
            if (isSupportRecordTaskWorker() && (myAudioTaskReceiver = this.mAudioTaskEndReceiver) != null) {
                this.mLocalBroadcastManager.unregisterReceiver(myAudioTaskReceiver);
                Logger.INSTANCE.d(TAG, "unregister mAudioTaskEndReceiver");
            }
        } catch (Exception e2) {
            Logger.INSTANCE.e(TAG, "error in onDestroy " + e2);
        }
        AlertDialog alertDialog = this.sendToDestDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.sendToDestDialog = null;
            this.mIsSendingToDesktop = false;
        }
        try {
            if (this.mTapShareSendCallback != null) {
                TapShareManager.getInstance().unregisterSendCallback(requireActivity(), this.mTapShareSendCallback);
                Logger.INSTANCE.d(TAG, "unregister mTapShareSendCallback");
                cleanTapShareFiles();
            }
        } catch (Exception e3) {
            Logger.INSTANCE.e(TAG, "error in onDestroy " + e3);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        Logger.INSTANCE.d(TAG, "onDestroyView");
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.destroy();
        }
        NestedHeaderLayout nestedHeaderLayout = this.mNestedHeaderLayout;
        if (nestedHeaderLayout != null) {
            Folme.clean(nestedHeaderLayout);
        }
        releaseAnimThemeBgView();
        super.onDestroyView();
        NoteLoadTask noteLoadTask = this.mLoadTask;
        if (noteLoadTask != null) {
            noteLoadTask.cancel();
            this.mLoadTask = null;
        }
        if (!this.mIsKillSaveTaskBeforeDestroy) {
            killSaveTask();
        }
        this.mIsKillSaveTaskBeforeDestroy = false;
        ValueAnimator valueAnimator = this.mEditUiPaddingAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mEditUiPaddingAnimator.removeAllUpdateListeners();
            this.mEditUiPaddingAnimator.removeAllListeners();
            this.mEditUiPaddingAnimator = null;
        }
    }

    private void releaseAnimThemeBgView() {
        ViewPropertyAnimator viewPropertyAnimator = this.mThemeBgViewAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            this.mThemeBgViewAnimator = null;
        }
        MamlView mamlView = this.mAnimThemeBgView;
        if (mamlView != null) {
            mamlView.clearAnimation();
            if (this.mAnimThemeBgView.getParent() instanceof ViewGroup) {
                ((ViewGroup) this.mAnimThemeBgView.getParent()).removeView(this.mAnimThemeBgView);
            }
            this.mAnimThemeBgView = null;
        }
        Theme theme = this.mCurrentTheme;
        if (theme instanceof AnimTheme) {
            ((AnimTheme) theme).forceCleanup();
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.mNavigationBarHeight = UIUtils.getNavigationBarHeightByWindow(activity, this.mRootView);
        }
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.onConfigChanged();
        }
        handleBrowseMode();
    }

    @Override // androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.mNavigationBarHeight = UIUtils.getNavigationBarHeightByWindow(activity, this.mRootView);
        }
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
            this.mEditToolbarController.updateLayout();
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.delete /* 2131362396 */:
                onPerformAction(24, new Object[0]);
                break;
            case R.id.hide /* 2131362702 */:
                onPerformAction(13, new Object[0]);
                break;
            case R.id.move_to /* 2131362990 */:
                onPerformAction(16, new Object[0]);
                break;
            case R.id.send_to_desktop /* 2131363407 */:
                onPerformAction(6, new Object[0]);
                break;
            case R.id.set_alarm /* 2131363412 */:
                onPerformAction(7, new Object[0]);
                break;
            case R.id.unhide /* 2131363736 */:
                onPerformAction(14, new Object[0]);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Deprecated
    public int[] getOptionResources() {
        ArrayList arrayList = new ArrayList();
        if (this.mWorkingNote.isHidden()) {
            arrayList.add(Integer.valueOf(R.string.menu_unhide));
        } else {
            arrayList.add(Integer.valueOf(R.string.menu_hide));
        }
        arrayList.add(Integer.valueOf(R.string.menu_send_to_desktop));
        if (!this.mWorkingNote.isHidden()) {
            arrayList.add(Integer.valueOf(R.string.menu_move_to));
        }
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        return iArr;
    }

    protected void updateWindowStatus(boolean z) {
        if (!isAdded() || getActivity() == null) {
            return;
        }
        boolean zIsInMultiWindowMode = getActivity().isInMultiWindowMode();
        if (z) {
            if (zIsInMultiWindowMode) {
                return;
            }
            getActivity().getWindow().getDecorView().setSystemUiVisibility(R2.attr.miuix_behavior_halfExpandedRatio);
            getActivity().getWindow().getAttributes().flags = this.mWindowInitFlags;
            return;
        }
        if (zIsInMultiWindowMode) {
            return;
        }
        getActivity().getWindow().getDecorView().setSystemUiVisibility(10000);
        getActivity().getWindow().getAttributes().flags = this.mWindowInitFlags;
    }

    public void onNavigationUp() {
        Logger.INSTANCE.d(TAG, "onNavigationUp");
        saveNoteInstantlyIfNeeded();
    }

    protected void initView(View view) {
        NestedHeaderLayout nestedHeaderLayout = (NestedHeaderLayout) view.findViewById(R.id.nested_header_layout);
        this.mNestedHeaderLayout = nestedHeaderLayout;
        nestedHeaderLayout.setAcceptTriggerRootViewAlpha(true);
    }

    protected void initActionBar() {
        BaseActionBarController actionBarController = getActionBarController();
        View viewOnInflateView = mActionbarView;
        if (viewOnInflateView == null) {
            viewOnInflateView = actionBarController.onInflateView(LayoutInflater.from(getContext()), (ViewGroup) getView(), null);
        }
        mActionbarView = null;
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(viewOnInflateView, new androidx.appcompat.app.ActionBar.LayoutParams(-1, -1));
            actionBar.setBackgroundDrawable(getActivity().getDrawable(R.drawable.v8_placeholder));
            setImmersionMenuEnabled(true);
        }
        viewOnInflateView.setOnDragListener(getActionBarBaseDragListener());
        getActionBarController().initializeView(getActionBar().getCustomView(), this.mNoteExtraInfoController);
    }

    protected void initPasswordController() {
        PasswordController passwordController = new PasswordController(this, true);
        this.mPasswordController = passwordController;
        passwordController.setOnSetPasswordListener(this.mOnSetPasswordListener);
        this.mPasswordController.setLockSateListener(this.mLockStateListener);
        this.homeViewModel.getToDetailResult().observe(this, new Observer() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda24
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$initPasswordController$7((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPasswordController$7(Boolean bool) {
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.activeUnlock();
        }
    }

    protected void setReadonly(boolean z) {
        if (z == this.mWorkingNote.getReadonly()) {
            return;
        }
        this.mWorkingNote.setReadonly(z);
        this.mNoteRichEditor.setExternalClickListener(z ? new View.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setReadonly$9(view);
            }
        } : null);
        updateEditInfo(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setReadonly$9(View view) {
        confirmEditAnyway(new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda20
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$setReadonly$8(dialogInterface, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setReadonly$8(DialogInterface dialogInterface, int i) {
        setReadonly(false);
        Utils.showSoftInput(this.mRootView);
        this.mNoteRichEditor.setCursorVisible(true);
    }

    protected void updateTextCount(int i) {
        this.mNoteExtraInfoController.updateTextCount(i);
    }

    public void processLastWorkingNote(long j) {
        if (this.mWorkingNote == null || this.mWorkingNote.getNoteId() == j) {
            return;
        }
        if (this.mWorkingNote.isEmpty()) {
            NoteStore.delete(getContext(), new long[]{this.mWorkingNote.getNoteId()});
            if (isThisNoteDoExcerpt()) {
                FloatWindowManagerAgent.stopContinuousExcerpt();
                return;
            }
            return;
        }
        saveNoteInstantlyIfNeeded();
    }

    public boolean lastWorkingNoteIsEmpty() {
        return this.mWorkingNote != null && this.mWorkingNote.isEmpty();
    }

    public void onPerformAction(int i, Object... objArr) {
        Logger.INSTANCE.d(TAG, "onPerformAction,action:" + i);
        if (System.currentTimeMillis() - this.mAudioClickTime < 500) {
            return;
        }
        Utils.updatePerformActionClickTime();
        if (i == 5) {
            Logger.INSTANCE.d(TAG, "onPerformAction/sendAsText");
            sendAsText();
            NotesTextStat.reportShareClickEvent("text");
            return;
        }
        if (i == 6) {
            boolean zIsElderlyManEnable = UIUtils.isElderlyManEnable(getContext());
            Logger.INSTANCE.d(TAG, "onPerformAction-SEND_TO_LAUNCHER elderlyManEnable = " + zIsElderlyManEnable);
            if (zIsElderlyManEnable) {
                Toast.makeText(NoteApp.getInstance(), R.string.toast_minimal_mode_widget_add_unsupported, 0).show();
                return;
            } else if (!this.mIsSendingToDesktop) {
                sendToDesktop();
                return;
            } else {
                Toast.makeText(NoteApp.getInstance(), R.string.info_note_enter_desktop, 0).show();
                return;
            }
        }
        if (i == 7) {
            showReminderDialog();
            return;
        }
        if (i == 8) {
            setAlertTime((Long) objArr[0]);
            return;
        }
        if (i == 10) {
            Intent sourceIntent = this.mWorkingNote.getSourceIntent();
            if (sourceIntent != null) {
                try {
                    startActivity(sourceIntent);
                    return;
                } catch (ActivityNotFoundException e) {
                    Logger.INSTANCE.w(TAG, "Fail to launch source activity with intent: " + sourceIntent.toUri(0) + ",exception:" + e);
                    return;
                }
            }
            return;
        }
        if (i == 57) {
            Logger.INSTANCE.d(TAG, "onPerformAction/sendAsMarkDown");
            sendAsMarkdown();
            NotesTextStat.reportShareClickEvent("Markdown");
            return;
        }
        if (i == 58) {
            Logger.INSTANCE.d(TAG, "onPerformAction/sendAsDuplicate");
            sendAsDuplicate();
            NotesTextStat.reportShareClickEvent("Duplicate");
            return;
        }
        switch (i) {
            case 13:
                tryHideNote();
                break;
            case 14:
                unhideNote();
                break;
            case 15:
                Logger.INSTANCE.d(TAG, "onPerformAction/sendAsImg");
                sendAsImage();
                NotesTextStat.reportShareClickEvent("picture");
                break;
            case 16:
                Logger.INSTANCE.d(TAG, "onPerformAction-ACTION_MOVE_TO");
                tryMoveNote();
                break;
            case 17:
                Logger.INSTANCE.d(TAG, "onPerformAction-UNDO");
                this.mNoteRichEditor.undo();
                break;
            case 18:
                Logger.INSTANCE.d(TAG, "onPerformAction-REDO");
                this.mNoteRichEditor.redo();
                break;
            case 19:
                Logger.INSTANCE.d(TAG, "onPerformAction-THEME");
                break;
            case 20:
                Logger.INSTANCE.d(TAG, "onPerformAction-DONE");
                NoteContentStat.QUIT_WAY = NoteContentStat.QUIT_WAY_BTN_BACK;
                NoteContentStat.IS_BTN_BACK = true;
                doneEdit();
                break;
            case 21:
                if (!Utils.isFastClick(-1, 500L)) {
                    Logger.INSTANCE.d(TAG, "onPerformAction-SHARE");
                    onShareOptions(getContext());
                    break;
                }
                break;
            case 22:
                Logger.INSTANCE.d(TAG, "onPerformAction-VIEWTHEME");
                this.mNoteRichEditor.clearFocus();
                Utils.hideSoftInput(this.mRootView);
                this.mEditToolbarController.toggleThemeChooserFromHead();
                LoadNoteAnimThemeBgTask loadNoteAnimThemeBgTask = new LoadNoteAnimThemeBgTask(getContext());
                this.mRxMultiAsyncTask = loadNoteAnimThemeBgTask;
                loadNoteAnimThemeBgTask.execute();
                break;
            case 23:
                if (!Utils.isFastClick(-1, 500L)) {
                    Logger.INSTANCE.d(TAG, "onPerformAction-MORE");
                    onShowOptions((View) objArr[0]);
                    break;
                }
                break;
            case 24:
                requestDeleteNote();
                break;
            case 25:
                NoteContentStat.QUIT_WAY = NoteContentStat.QUIT_WAY_BTN_BACK;
                NoteContentStat.IS_BTN_BACK = true;
                if (isAdded() && getActivity() != null) {
                    if (getMIsKeyboardVisible()) {
                        Utils.hideSoftInput(getActivity());
                    }
                    getActivity().getWindow().clearFlags(524288);
                    onNavigationUp();
                    break;
                }
                break;
        }
    }

    public void onShowOptions(View view) {
        if (isAdded() && !isInvalid()) {
            if (isLoadingData()) {
                Toast.makeText(NoteApp.getInstance(), R.string.refuse_to_open_as_pic_because_of_loading, 0).show();
                return;
            }
            this.mEditToolbarController.checkAndCollapseThemePanel();
            saveNoteInstantlyIfNeeded();
            if (view.isAttachedToWindow()) {
                showImmersionMenu(view, null);
            }
        }
    }

    public void onShareOptions(Context context) {
        if (isAdded()) {
            IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
            if (iBaseNoteEditToolbarController != null) {
                iBaseNoteEditToolbarController.collapsePanel(null);
            }
            Utils.hideSoftInput(this.mRootView);
            if (LiteUtils.isLiteOrMiddle() || !RomUtils.isNewTablet()) {
                setShareOptions(context, new String[]{context.getString(R.string.dialog_share_as_text), context.getString(R.string.dialog_share_as_pic)});
            } else if (RomUtils.isOS31Support()) {
                setShareOptions(context, new String[]{context.getString(R.string.dialog_share_as_text), context.getString(R.string.dialog_share_as_pic), context.getString(R.string.dialog_share_as_duplicate), context.getString(R.string.dialog_expert_as_markdown)});
            } else {
                setShareOptions(context, new String[]{context.getString(R.string.dialog_share_as_text), context.getString(R.string.dialog_share_as_pic), context.getString(R.string.dialog_expert_as_markdown)});
            }
        }
    }

    public void setShareOptions(Context context, String[] strArr) {
        dismissDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.dialog_share_title));
        builder.setActionSheetEnabled(true);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda21
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setItems(strArr, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda23
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$setShareOptions$11(dialogInterface, i);
            }
        }).create();
        AlertDialog alertDialogShow = builder.show();
        if (alertDialogShow.getWindow() != null && getContext() != null && getContext().getResources() != null && UIUtils.isFreeFormScreen(getContext().getResources().getConfiguration())) {
            Logger.INSTANCE.d(TAG, "setShareOptions: in small window clear flag FLAG_ALT_FOCUSABLE_IM");
            alertDialogShow.getWindow().clearFlags(131072);
        }
        manageDialog(alertDialogShow);
        Logger.INSTANCE.d(TAG, "showShareDialog");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setShareOptions$11(DialogInterface dialogInterface, int i) {
        if (i == 0) {
            onPerformAction(5, new Object[0]);
            return;
        }
        if (i == 1) {
            onPerformAction(15, new Object[0]);
            return;
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            onPerformAction(57, new Object[0]);
        } else if (RomUtils.isOS31Support()) {
            onPerformAction(58, new Object[0]);
        } else {
            onPerformAction(57, new Object[0]);
        }
    }

    public void onContentOverLength(int i) {
        Logger.INSTANCE.d(TAG, "content over length!!");
        if (this.mHandler.hasMessages(6)) {
            return;
        }
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 6;
        messageObtainMessage.arg1 = i;
        this.mHandler.sendMessageDelayed(messageObtainMessage, 200L);
    }

    public void onImageAnnotationOverLength() {
        if (this.mHandler.hasMessages(7)) {
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(7, 200L);
    }

    public void onAddAttachment(final int i) {
        if (!this.mNoteRichEditor.canAddNewElement(51)) {
            onContentOverLength(4);
        } else if (this.mWorkingNote.getReadonly()) {
            confirmEditAnyway(new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.7
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    BaseNormalNoteEditFragment.this.setReadonly(false);
                    BaseNormalNoteEditFragment.this.addAttachment(i);
                }
            });
        } else {
            addAttachment(i);
        }
    }

    protected boolean dismissAllDialogOrPanel() {
        boolean zDismissDialog = dismissDialog();
        SharePopupGroup sharePopupGroup = this.mSharePopupWindow;
        if (sharePopupGroup != null && sharePopupGroup.isShowing()) {
            this.mSharePopupWindow.dismiss();
            zDismissDialog = true;
        }
        PopupWindow popupWindow = this.mShowingPopupWindow;
        if (popupWindow == null || !popupWindow.isShowing()) {
            return zDismissDialog;
        }
        this.mShowingPopupWindow.dismiss();
        this.mShowingPopupWindow = null;
        return true;
    }

    protected final boolean handleViewIntent(Intent intent) {
        long extraNoteIdFromIntent = getExtraNoteIdFromIntent(intent);
        if (extraNoteIdFromIntent <= 0) {
            Logger.INSTANCE.w(TAG, "Miss note id when parse intent");
            return false;
        }
        this.mIsFromPrivate = PrivateNotesListActivity.ACTION_FROM_PRIVACY;
        PrivateNotesListActivity.ACTION_FROM_PRIVACY = false;
        this.mIsFromFloatWindow = intent.getBooleanExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, false);
        if (intent.hasExtra(NoteIntent.KEY_INIT_BG_COLOR)) {
            getView().setBackground(new ColorDrawable(intent.getIntExtra(NoteIntent.KEY_INIT_BG_COLOR, getResources().getColor(R.color.v8_theme_bg_color, null))));
        }
        Logger.INSTANCE.d(TAG, "handleViewIntent/loadNoteTask->run");
        ViewNoteLoadTask viewNoteLoadTask = new ViewNoteLoadTask(intent, extraNoteIdFromIntent);
        this.mLoadTask = viewNoteLoadTask;
        viewNoteLoadTask.commit();
        updateActionMode(EditorActionMode.VIEW, false);
        return true;
    }

    protected boolean handleEditIntent(final Intent intent) {
        Logger.INSTANCE.d(TAG, "handleEditIntent");
        long longExtra = intent.getLongExtra(Notes.Intents.INTENT_EXTRA_CALL_DATE, 0L);
        this.mIsEditNotes = (longExtra == 0 || longExtra != PreferenceUtils.getLastCallNoteData()) && !NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE.equals(intent.getType());
        boolean booleanExtra = intent.getBooleanExtra(NoteIntent.KEY_IS_PENDING, false);
        this.mIsFromPrivate = PrivateNotesListActivity.ACTION_FROM_PRIVACY;
        PrivateNotesListActivity.ACTION_FROM_PRIVACY = false;
        boolean booleanExtra2 = intent.getBooleanExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, false);
        this.mIsFromFloatWindow = booleanExtra2;
        if (booleanExtra2) {
            this.mNeedDelayShowInput = true;
        }
        this.mPendingEditIntentTask = new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleEditIntent$12(intent);
            }
        };
        if (!booleanExtra) {
            this.mHandler.sendEmptyMessage(4);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleEditIntent$12(Intent intent) {
        EditNoteLoadTask editNoteLoadTask = getEditNoteLoadTask(intent);
        this.mLoadTask = editNoteLoadTask;
        editNoteLoadTask.commit();
        Logger.INSTANCE.d(TAG, "handleEditIntent.load tsk run");
    }

    protected boolean handleSendIntent(Intent intent) {
        Logger.INSTANCE.d(TAG, "handleSendIntent-doSendTask");
        SendNoteLoadTask sendNoteLoadTask = new SendNoteLoadTask(intent);
        this.mLoadTask = sendNoteLoadTask;
        sendNoteLoadTask.commit();
        return false;
    }

    protected boolean handleTapShareIntent(Intent intent) {
        Logger.INSTANCE.d(TAG, "handleTapShareIntent-doTapShareTask");
        TapShareNoteLoadTask tapShareNoteLoadTask = new TapShareNoteLoadTask(intent);
        this.mLoadTask = tapShareNoteLoadTask;
        tapShareNoteLoadTask.commit();
        return false;
    }

    protected boolean handleDeleteIntent(Intent intent) {
        Logger.INSTANCE.d(TAG, "handleDeleteIntent");
        DeleteNoteTask deleteNoteTask = new DeleteNoteTask(intent);
        this.mLoadTask = deleteNoteTask;
        deleteNoteTask.commit();
        return true;
    }

    protected void onNoteDeleted(long j) {
        if (isAdded()) {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_delete_success, 0).show();
            getActivity().onBackPressed();
        }
    }

    protected final long getExtraNoteIdFromIntent(Intent intent) {
        long longExtra = intent.getLongExtra("android.intent.extra.UID", 0L);
        if (longExtra <= 0) {
            Uri data = intent.getData();
            if (data == null || !"notes".equals(data.getAuthority())) {
                Logger.INSTANCE.e(TAG, "Invalid data uri: " + (data != null ? data.toString() : null));
            } else {
                try {
                    return ContentUris.parseId(data);
                } catch (Exception e) {
                    Logger.INSTANCE.e(TAG, "Fail to parse note id from uri: " + data + ",exception:" + e);
                }
            }
        }
        return longExtra;
    }

    protected final String getSearchExtraDataKeyFromIntent(Intent intent) {
        if (intent.hasExtra("intent_extra_data_key")) {
            return intent.getStringExtra("intent_extra_data_key");
        }
        return null;
    }

    protected final ArrayList<String> getHighLightKeyWordsFromIntent(Intent intent) {
        if (intent.hasExtra(NoteIntent.ACTION_WITH_AI_QUERY)) {
            return intent.getStringArrayListExtra(NoteIntent.ACTION_WITH_AI_QUERY);
        }
        return null;
    }

    public void updateActionMode(EditorActionMode editorActionMode, boolean z) {
        Logger.INSTANCE.i(TAG, "BaseNormalNoteEditFragment/updateActionMode-do nothing");
    }

    public void onDragDropFromBlankView(DragEvent dragEvent) {
        ClipDataHelper.getImageUrisFromDragEventInFragment(this, dragEvent, new ClipDataHelper.UriGetCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.8
            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onException(String str) {
            }

            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onGetUri(ArrayList<Uri> arrayList) throws Throwable {
                if (arrayList == null || arrayList.size() <= 0) {
                    return;
                }
                int size = arrayList.size();
                Uri[] uriArr = new Uri[size];
                arrayList.toArray(uriArr);
                NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(BaseNormalNoteEditFragment.this.getContext());
                for (int i = 0; i < size; i++) {
                    String strSaveImageFile = AttachmentUtils.saveImageFile((Context) BaseNormalNoteEditFragment.this.getActivity(), uriArr[i], true);
                    if (strSaveImageFile != null) {
                        if (!simpleBuilder.isEmpty()) {
                            simpleBuilder.appendNewLine();
                        }
                        simpleBuilder.appendImage(strSaveImageFile);
                        simpleBuilder.appendNewLine();
                    }
                }
                long j = BaseNormalNoteEditFragment.this.mFolderId;
                if (j == -4097 || j < 0) {
                    j = 0;
                }
                WorkingNote workingNoteCreateEmptyNote = WorkingNote.createEmptyNote(j, 0, -1, ResourceParser.getDefaultBgId(BaseNormalNoteEditFragment.this.getActivity()));
                String ancient = simpleBuilder.toAncient();
                if (ancient.length() > 20000) {
                    ancient = ancient.substring(0, 20000);
                }
                workingNoteCreateEmptyNote.setWorkingText(ancient);
                workingNoteCreateEmptyNote.handleSent();
                workingNoteCreateEmptyNote.saveNote(true);
            }
        });
    }

    public void showReminderDialog() {
        if (isAdded()) {
            DateTimePickerDialog dateTimePickerDialog = new DateTimePickerDialog(getActivity(), new DateTimePickerDialog.OnTimeSetListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.9
                @Override // com.miui.notes.ui.widget.DateTimePickerDialog.OnTimeSetListener
                public void onTimeSet(DateTimePickerDialog dateTimePickerDialog2, long j) {
                    BaseNormalNoteEditFragment.this.onPerformAction(8, Long.valueOf(j));
                }

                @Override // com.miui.notes.ui.widget.DateTimePickerDialog.OnTimeSetListener
                public void cancelAlarm() {
                    if (BaseNormalNoteEditFragment.this.getAlarmTime() > 0) {
                        BaseNormalNoteEditFragment.this.onPerformAction(8, 0L);
                    }
                }
            }, 5);
            dateTimePickerDialog.update(getAlarmTime());
            Calendar calendar = Calendar.getInstance();
            dateTimePickerDialog.setMinDateTime(calendar.getTimeInMillis());
            calendar.set(10, 23);
            calendar.set(12, 59);
            calendar.add(1, 1);
            dateTimePickerDialog.setMaxDateTime(calendar.getTimeInMillis());
            dateTimePickerDialog.show();
            manageDialog(dateTimePickerDialog);
        }
    }

    protected void addAttachment(int i) {
        if (isAdded()) {
            if (i == 1) {
                notifyAction(3, null);
                ExternalAppUtils.selectImage(this, this.mGalleryResult, this.mNewGalleryResult);
            } else {
                if (i != 2) {
                    return;
                }
                notifyAction(3, null);
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$addAttachment$13();
                    }
                }, RomUtils.isPadMode() ? 150L : 0L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addAttachment$13() {
        if (isAdded()) {
            ExternalAppUtils.takePhoto(this, this.mTakePhotoResult);
        }
    }

    protected void viewImage(String str) {
        if (this.mWorkingNote != null) {
            long noteId = this.mWorkingNote.getNoteId();
            Pair<Long, String> dataIdAndMimeTypeByAttachmentName = AttachmentUtils.getDataIdAndMimeTypeByAttachmentName(getContext(), noteId, str);
            if (dataIdAndMimeTypeByAttachmentName == null) {
                dataIdAndMimeTypeByAttachmentName = AttachmentUtils.getDataIdAndMimeTypeByAttachmentName(getContext(), noteId + 1, str);
            }
            if (dataIdAndMimeTypeByAttachmentName != null) {
                ExternalAppUtils.viewImage(getContext(), ContentUris.withAppendedId(Notes.Data.MEDIA_URI, ((Long) dataIdAndMimeTypeByAttachmentName.first).longValue()), (String) dataIdAndMimeTypeByAttachmentName.second);
            } else {
                Toast.makeText(NoteApp.getInstance(), R.string.toast_pic_is_not_really, 0).show();
            }
        }
    }

    protected void startToDoodle(DoodleParams doodleParams, ActivityResultLauncher<Intent> activityResultLauncher) {
        Intent intent = new Intent(getActivity(), (Class<?>) DoodleActivity.class);
        intent.putExtra(EXTRA_KEY_IS_PRIVACY, getWorkingNote() != null && getWorkingNote().getFolderId() == -4);
        intent.putExtra(DoodleFragment.KEY_PARAMS, doodleParams);
        if (this.mWorkingNote != null) {
            intent.putExtra(DoodleActivity.EXTRA_KEY_IS_HIDDEN, this.mWorkingNote.isHidden());
        }
        activityResultLauncher.launch(intent);
    }

    protected void createNewDoodle() {
        if (Utils.isFastClick(0, 500L)) {
            return;
        }
        DoodleApplication.cancelAndDeletePreloadData();
        DoodleParams doodleParams = new DoodleParams();
        doodleParams.setFullScreen(false);
        startToDoodle(doodleParams, this.mDoodleResult);
    }

    protected DoodleParams createEditImageParams(String str) {
        if (TextUtils.isEmpty(str) || getContext() == null) {
            return null;
        }
        DoodleParams doodleParams = new DoodleParams();
        doodleParams.setFullScreen(false);
        doodleParams.setImagePath(AttachmentUtils.getAttachmentPath(getContext(), str));
        doodleParams.setSavePath(str);
        return doodleParams;
    }

    protected void requestDeleteNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 2131951644);
        builder.setTitle(R.string.new_alert_title_delete);
        if (isThisNoteDoExcerpt()) {
            builder.setMessage(R.string.alert_message_delete_excerpt);
        } else {
            builder.setMessage(R.string.alert_message_delete_note);
        }
        builder.setPositiveButton(R.string.menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda16
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$requestDeleteNote$14(dialogInterface, i);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        manageDialog(builder.show());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestDeleteNote$14(DialogInterface dialogInterface, int i) {
        doDeleteNote();
    }

    protected void doDeleteNote() {
        deleteNote();
    }

    protected void deleteNote() {
        Logger.INSTANCE.i(TAG, "deleteNote");
        if (isThisNoteDoExcerpt()) {
            deleteAndExitExcerpt();
        } else {
            deleteAndLoad();
        }
    }

    protected void tryMoveNote() {
        if (isAdded()) {
            if (isThisNoteDoExcerpt()) {
                Toast.makeText(NoteApp.getInstance(), R.string.excerpting, 0).show();
            } else {
                startQueryFolder();
            }
        }
    }

    public void startQueryMenuFolder(int i) {
        if (getActivity() == null || !isAdded() || isDetached()) {
            return;
        }
        FolderStore.INSTANCE.query(getLoaderManager(), 0, 2, new NoteMenuLoaderCallbacks(i), "_id <> " + this.mFolderId);
    }

    protected void tryHideNote() {
        if (isAdded()) {
            if (isWorkingNoteEmpty()) {
                Toast.makeText(NoteApp.getInstance(), R.string.error_hide_empty_note, 0).show();
                return;
            }
            if (isThisNoteDoExcerpt()) {
                Toast.makeText(NoteApp.getInstance(), R.string.excerpting, 0).show();
                return;
            }
            PasswordController passwordController = this.mPasswordController;
            if (passwordController != null) {
                passwordController.setPassword(5102);
            }
        }
    }

    protected void hideNote() {
        this.mWorkingNote.setFolderId(-4L);
        this.mNoteExtraInfoController.setFolderGroupVisibility(8);
        getActionBarController().updateNoteHiddenState(this.mWorkingNote);
        this.mNestedHeaderLayout.setAutoTriggerOpen(true);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getWindow().addFlags(8192);
        }
        this.mFolderId = -4L;
        if (RomUtils.isPadMode()) {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_hidden_pad, 0).show();
        } else {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_hidden_edit_new, 0).show();
        }
        updateWorkingTextAndTitle();
        asyncSaveNoteInstantly();
    }

    protected void unhideNote() {
        if (this.mWorkingNote == null) {
            return;
        }
        this.mWorkingNote.setFolderId(0L);
        this.mFolderId = 0L;
        getActionBarController().updateNoteHiddenState(this.mWorkingNote);
        this.mNestedHeaderLayout.setAutoTriggerOpen(true);
        this.mNoteExtraInfoController.setFolderGroupVisibility(0);
        this.mNoteExtraInfoController.updateFolderName(NoteApp.getInstance().getString(R.string.unclassfied_folder_name));
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getWindow().clearFlags(8192);
        }
        Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_unhidden, 0).show();
        updateWorkingTextAndTitle();
        asyncSaveNoteInstantly();
        asyncSaveNote();
    }

    protected void deleteAndExitExcerpt() {
        AudioUtils.playDeleteRingtone();
        final long noteId = getNoteId();
        if (noteId <= 0) {
            Logger.INSTANCE.e(TAG, "deleteAndLoad: invalid note id " + noteId);
            return;
        }
        ThreadPoolUtils.INSTANCE.getBackgroundWorker().execute(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                BaseNormalNoteEditFragment.lambda$deleteAndExitExcerpt$15(noteId);
            }
        });
        FloatWindowManagerAgent.stopContinuousExcerpt();
        if (RomUtils.isPadMode() || getActivity() == null) {
            return;
        }
        getActivity().finish();
    }

    static /* synthetic */ void lambda$deleteAndExitExcerpt$15(long j) {
        if (NoteStore.delete(NoteApp.getInstance(), new long[]{j}) > 0) {
            Logger.INSTANCE.d(TAG, "deleteAndExitExcerpt success: ");
        }
    }

    protected void deleteAndLoad() {
        AudioUtils.playDeleteRingtone();
        long noteId = getNoteId();
        if (getNoteId() == -2) {
            if (!isAdded() || getActivity() == null) {
                return;
            }
            onBackPressed();
            getActivity().getOnBackPressedDispatcher().onBackPressed();
            return;
        }
        if (noteId <= 0) {
            Logger.INSTANCE.e(TAG, "deleteAndLoad: invalid note id " + noteId);
            return;
        }
        Intent intent = new Intent(NoteIntent.ACTION_DELETE);
        intent.putExtra("android.intent.extra.UID", noteId);
        intent.putExtra(NoteIntent.KEY_FOLDER_ID, this.mWorkingNote.getFolderId());
        if (PreferenceUtils.getNoteSortWay(getActivity(), 1) == 1) {
            intent.putExtra(NoteIntent.KEY_DATE, this.mWorkingNote.getModifiedDate());
        } else {
            intent.putExtra(NoteIntent.KEY_DATE, this.mWorkingNote.getCreatedDate());
        }
        onNewIntent(intent);
    }

    protected final void sendToDesktop() {
        String[] strArr;
        updateWorkingTextAndTitle();
        if (isWorkingNoteEmpty()) {
            Logger.INSTANCE.w(TAG, "Send to desktop error");
            Toast.makeText(NoteApp.getInstance(), R.string.error_note_empty_for_send_to_desktop, 0).show();
            return;
        }
        if (this.mWorkingNote.getNoteId() < 0) {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_pic_is_not_really, 0).show();
            return;
        }
        int[] widgetInfoByNote = DataUtils.getWidgetInfoByNote(getActivity(), this.mWorkingNote.getNoteId());
        if (widgetInfoByNote != null) {
            if (this.mWorkingNote.getWidgetId() != widgetInfoByNote[0]) {
                this.mWorkingNote.applyWidgetId(widgetInfoByNote[0]);
            }
            if (this.mWorkingNote.getWidgetType() != widgetInfoByNote[1]) {
                this.mWorkingNote.applyWidgetType(widgetInfoByNote[1]);
            }
        }
        AppWidgetProviderInfo appWidgetInfo = AppWidgetManager.getInstance(getActivity()).getAppWidgetInfo(this.mWorkingNote.getWidgetId());
        if (this.mWorkingNote.getWidgetId() != 0 && appWidgetInfo != null) {
            Toast.makeText(NoteApp.getInstance(), R.string.error_widget_exist_for_send_to_desktop, 0).show();
            return;
        }
        if (RomUtils.isPadDevice()) {
            strArr = new String[]{getResources().getString(R.string.widget_size_item_small), getResources().getString(R.string.widget_size_item_medium), getResources().getString(R.string.widget_size_item_big), getResources().getString(R.string.widget_size_item_super_big)};
        } else {
            strArr = new String[]{getResources().getString(R.string.widget_size_item_small), getResources().getString(R.string.widget_size_item_medium), getResources().getString(R.string.widget_size_item_big)};
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.widget_size_item_title));
        builder.setActionSheetEnabled(true);
        builder.setHapticFeedbackEnabled(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda27
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                this.f$0.lambda$sendToDesktop$16(dialogInterface);
            }
        });
        builder.setItems(strArr, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.10
            private Long lastClickTime = null;

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Class cls;
                if (this.lastClickTime != null && System.currentTimeMillis() - this.lastClickTime.longValue() <= 400) {
                    this.lastClickTime = Long.valueOf(System.currentTimeMillis());
                    return;
                }
                this.lastClickTime = Long.valueOf(System.currentTimeMillis());
                BaseNormalNoteEditFragment.this.mIsSendingToDesktop = true;
                if (i == 0) {
                    cls = NoteWidgetProvider_2x2.class;
                } else if (i == 1) {
                    cls = NoteWidgetProvider_4x2.class;
                } else if (i == 2) {
                    cls = NoteWidgetProvider_4x4.class;
                } else {
                    cls = i != 3 ? null : NoteWidgetProvider_8x4.class;
                }
                Intent intent = new Intent();
                intent.putExtra("android.intent.extra.UID", BaseNormalNoteEditFragment.this.mWorkingNote.getNoteId());
                intent.putExtra(SystemIntent.EXTRA_RESULT_RECEIVER_COMPONENT_NAME, new ComponentName(BaseNormalNoteEditFragment.this.getActivity(), (Class<?>) cls));
                intent.putExtra(SystemIntent.EXTRA_PROVIDER_COMPONENT_NAME, new ComponentName(BaseNormalNoteEditFragment.this.getActivity(), (Class<?>) cls));
                intent.setAction(SystemIntent.ACTION_INSTALL_WIDGET);
                CompatUtils.sendImplicitBroadcast(BaseNormalNoteEditFragment.this.getActivity(), intent);
                Logger.INSTANCE.i(BaseNormalNoteEditFragment.TAG, "send broadcast to desktop success,id:" + BaseNormalNoteEditFragment.this.mWorkingNote.getNoteId() + " content length:" + (!TextUtils.isEmpty(BaseNormalNoteEditFragment.this.mWorkingNote.getContent()) ? BaseNormalNoteEditFragment.this.mWorkingNote.getContent().length() : -1));
            }
        });
        builder.setNeutralButton(getResources().getString(android.R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.11
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                BaseNormalNoteEditFragment.this.mIsSendingToDesktop = false;
            }
        });
        AlertDialog alertDialog = this.sendToDestDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.sendToDestDialog.dismiss();
        }
        AlertDialog alertDialogShow = builder.show();
        this.sendToDestDialog = alertDialogShow;
        manageDialog(alertDialogShow);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendToDesktop$16(DialogInterface dialogInterface) {
        this.mIsSendingToDesktop = false;
    }

    protected void showSharePopupWindow(Intent intent) {
        if (isAdded()) {
            Logger.INSTANCE.d(TAG, "showSharePopupWindow");
            dismissAllDialogOrPanel();
            this.mSharePopupWindow = new SharePopupGroup(getActivity(), UIUtils.inflateView((ViewGroup) getView(), R.layout.share_layout), this.mCurrentTheme);
            View viewFindViewById = getView().getRootView().findViewById(R.id.sv_note_edit);
            if (viewFindViewById != null) {
                viewFindViewById.setImportantForAccessibility(4);
            }
            View viewFindViewById2 = getView().getRootView().findViewById(R.id.header);
            if (viewFindViewById2 != null) {
                viewFindViewById2.setImportantForAccessibility(4);
            }
            Rect rect = new Rect();
            getView().getLocalVisibleRect(rect);
            this.mSharePopupWindow.queryInfo(intent);
            this.mSharePopupWindow.setOnIntentSelectedListener(new ResolverAdapter.OnIntentSelectedListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda1
                @Override // com.miui.notes.adapter.ResolverAdapter.OnIntentSelectedListener
                public final void onIntentSelected(Intent intent2) {
                    this.f$0.lambda$showSharePopupWindow$17(intent2);
                }
            });
            this.mSharePopupWindow.updatePaddingBottom(this.mInsetBottom);
            this.mSharePopupWindow.show(rect);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSharePopupWindow$17(Intent intent) {
        Logger.INSTANCE.d(TAG, "showSharePopupWindow/onIntentSelected..");
        SharePopupGroup sharePopupGroup = this.mSharePopupWindow;
        if (sharePopupGroup != null && sharePopupGroup.isShowing()) {
            this.mSharePopupWindow.dismiss();
        }
        if (isIgnoreNewTask(intent.getComponent().getPackageName())) {
            intent.removeFlags(DataSpace.RANGE_LIMITED);
        }
        startActivity(intent);
        NoteContentStat.IS_SHARE = true;
    }

    private boolean isIgnoreNewTask(String str) {
        return PackageNameUtils.isWechatPackage(str);
    }

    protected void sendAsText() {
        Logger.INSTANCE.d(TAG, "sendAsText isWorkingNoteEmpty:" + isWorkingNoteEmpty());
        if (isAdded()) {
            if (this.mWorkingNote == null) {
                Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
                return;
            }
            updateWorkingTextAndTitle();
            if (isWorkingNoteEmpty()) {
                Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
                return;
            }
            String content = this.mWorkingNote.getContent();
            String string = EditorUtils.normalizeSnippet(content, new ReplaceImageRichParserHandler(getActivity(), AttachmentUtils.INSTANCE.getImageInfosByName(getActivity(), this.mWorkingNote.getNoteId(), EditorUtils.retrieveImages(getActivity(), content))), getContext()).toString();
            String strTrim = SnippetFormatter.getFormattedSnippet(string).toString().trim();
            if (strTrim.length() > 30) {
                strTrim = strTrim.substring(0, 30);
            }
            Intent intent = new Intent();
            intent.putExtra("android.intent.extra.SUBJECT", strTrim);
            if (!TextUtils.isEmpty(this.mWorkingNote.getTitle())) {
                intent.putExtra("android.intent.extra.TEXT", this.mWorkingNote.getTitle() + StringUtils.LF + string);
            } else {
                intent.putExtra("android.intent.extra.TEXT", string);
            }
            if (!RomUtils.isOS31Support()) {
                intent.setClipData(new ClipData(getString(R.string.app_name), new String[]{"text/plain"}, new ClipData.Item(string)));
                intent.setAction("android.intent.action.SEND");
                intent.setType("text/plain");
                Uri uriWithAppendedId = ContentUris.withAppendedId(Notes.Note.CONTENT_URI, this.mWorkingNote.getNoteId());
                Intent intent2 = new Intent("android.intent.action.VIEW");
                intent2.addFlags(DataSpace.RANGE_LIMITED);
                intent2.setDataAndType(uriWithAppendedId, Notes.TextData.CONTENT_ITEM_TYPE);
                intent.putExtra(Notes.Intents.INTENT_EXTRA_SOURCE_NAME, getString(R.string.app_name));
                intent.putExtra(Notes.Intents.INTENT_EXTRA_SOURCE_INTENT, intent2);
                showSharePopupWindow(intent);
                return;
            }
            Intent intent3 = new Intent("android.intent.action.SEND");
            intent3.setType("text/plain");
            intent3.putExtra("android.intent.extra.TEXT", string);
            intent3.putExtra("android.intent.extra.TITLE", this.mWorkingNote.getTitle());
            intent3.addFlags(1);
            startActivity(Intent.createChooser(intent3, null));
        }
    }

    public void startSharePageByRenderUri(Uri uri, Uri uri2) {
        Intent intent = new Intent();
        intent.addFlags(1);
        if (getContext() != null) {
            getContext().grantUriPermission("com.android.intentresolver", uri2, 1);
        }
        intent.putExtra("extra_content_preview_notes", uri2);
        intent.setClipData(ClipData.newRawUri("share_image", uri));
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("extra_tap_share_support", true);
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setType("image/png");
        getContext().startActivity(Intent.createChooser(intent, null));
    }

    public String getTxtContentFirstLine(Context context, String str) {
        if (context == null) {
            Logger.INSTANCE.e(TAG, "context is null!!!");
            return "";
        }
        CharSequence charSequenceSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(EditorUtils.normalizeSnippet(str, new ReplaceImageRichParserHandler(context, AttachmentUtils.INSTANCE.getImageInfosByName(context, getNoteId(), EditorUtils.retrieveImages(context, str))), context).toString().toString().trim(), 1);
        Logger.INSTANCE.d(TAG, "sendAsDuplicate title: subTitle" + ((Object) charSequenceSubStringByMaxLine));
        return charSequenceSubStringByMaxLine.toString();
    }

    public static Uri createTempUri(Context context, Bitmap bitmap) {
        File file = new File(NoteApp.getInstance().getExternalCacheDir().getAbsolutePath() + InternalZipConstants.ZIP_FILE_SEPARATOR + ("IMG_SHARE_" + System.currentTimeMillis() + ".png"));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, " createNewFile error: " + e);
                return null;
            }
        }
        Uri compatUri = CompatUtils.getCompatUri(NoteApp.getInstance(), file);
        ContentResolver contentResolver = context.getContentResolver();
        try {
            OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(compatUri);
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStreamOpenOutputStream);
                if (outputStreamOpenOutputStream != null) {
                    outputStreamOpenOutputStream.close();
                }
                return compatUri;
            } catch (Throwable th) {
                if (outputStreamOpenOutputStream != null) {
                    try {
                        outputStreamOpenOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Exception unused) {
            contentResolver.delete(compatUri, null, null);
            return null;
        }
    }

    protected void onAnimThemeBgViewChanged(View view) {
        this.mAnimThemeBgView = (MamlView) view;
    }

    protected void onNoteThemeChangeFinish() {
        this.mIsChangingTheme = false;
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.unlock();
        }
    }

    protected void showLoadingData(boolean z) {
        Handler handler;
        if (z || (handler = this.mHandler) == null || !handler.hasMessages(5)) {
            return;
        }
        this.mHandler.removeMessages(5);
    }

    public void onLoadDataFinish() {
        showLoadingData(false);
    }

    public void onLoadDataFailed() {
        showLoadingData(false);
        Toast.makeText(NoteApp.getInstance(), R.string.error_note_not_exist, 0).show();
    }

    protected void pauseAnimThemeBg() {
        Theme theme;
        if (this.isLite || (theme = this.mCurrentTheme) == null || !(theme instanceof AnimTheme)) {
            return;
        }
        ((AnimTheme) theme).pause();
    }

    protected void resumeAnimThemeBg() {
        Theme theme;
        if (this.isLite || (theme = this.mCurrentTheme) == null || !(theme instanceof AnimTheme)) {
            return;
        }
        ((AnimTheme) theme).resume();
    }

    protected void doneEdit() {
        Logger.INSTANCE.d(TAG, "doneEdit");
        this.mNoteRichEditor.done();
        saveNoteInstantlyIfNeeded();
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.collapsePanel(null);
        }
        Logger.INSTANCE.d(TAG, "doneEdit/updateActionMode-view-true");
        updateActionMode(EditorActionMode.VIEW, true);
    }

    protected void startRecordWithCheckFromEditTask() {
        if (!PreferenceUtils.isCTAAccepted(getContext()) && PermissionUtils.supportNewPermissionStyle()) {
            PermissionNewUtils.showCtaDialog(this.toCtaBeforeAudioWithEditTask);
        } else {
            PermissionNewUtils.requestOnAudioNote(this.requestAudioPermissionWithEditTask);
        }
    }

    protected void updateRecordMode() {
        this.mIsRecording = true;
    }

    protected void updateRecordClickTime() {
        this.mAudioClickTime = System.currentTimeMillis();
    }

    protected void executeCreateNewNoteTask() {
        Runnable runnable = this.mPendingEditIntentTask;
        if (runnable != null) {
            this.mHandler.post(runnable);
            this.mPendingEditIntentTask = null;
        }
    }

    @Deprecated
    protected boolean executePendingEditTask() {
        if (this.mPendingEditIntentTask == null) {
            return false;
        }
        this.mHandler.sendEmptyMessage(4);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeWidgetUpdateTask() {
        if (this.mWorkingNote == null || this.mWorkingNote.getNoteId() <= 0) {
            return;
        }
        new NoteWidgetUpdateTask(this.mBackTaskDisposables, this, this.mWorkingNote.getNoteId()).execute();
    }

    protected void setWorkingNote(WorkingNote workingNote, boolean z) {
        Logger.INSTANCE.d(TAG, "setWorkingNote..forceEdit:" + z);
        Trace.beginSection("setWorkingNote");
        this.mNoteId = workingNote.getNoteId();
        Logger.INSTANCE.d(TAG, "set mNoteId:" + this.mNoteId);
        this.mWorkingNote = workingNote;
        if (this.mWorkingNote.getAlertDate() > 0) {
            this.mHandler.removeMessages(3);
            this.mHandler.sendEmptyMessage(3);
        }
        boolean z2 = (getActivity() == null || getActivity().getIntent() == null || getActivity().getIntent().getExtras() == null || !getActivity().getIntent().getExtras().containsKey("com.miui.notes.widget_type")) ? false : true;
        boolean zIsHidden = this.mWorkingNote.isHidden();
        if (isAdded() && this.mPasswordController != null && ((z2 || zIsHidden) && !this.mIsFromRebuild && !this.mIsRebuildRestoringPrivateNote)) {
            Logger.INSTANCE.d(TAG, "setAWorkingNote: lock and unlock");
            this.mPasswordController.lock();
            this.mPasswordController.unlock();
        }
        this.mIsRebuildRestoringPrivateNote = false;
        FragmentActivity activity = getActivity();
        if (isAdded() && activity != null) {
            if (this.mWorkingNote.isHidden()) {
                activity.getWindow().addFlags(8192);
            } else {
                activity.getWindow().clearFlags(8192);
            }
        }
        if (this.mEditToolbarController != null) {
            Logger.INSTANCE.d(TAG, "setWorkingNote..,collapsePanel");
            this.mEditToolbarController.collapsePanel(null);
            this.mEditToolbarController.setWorkingNote(this.mWorkingNote);
        }
        this.mWorkingNote.addOnSettingStatusChangedListener(this);
        registerNoteContentObserver(true);
        this.mFolderId = this.mWorkingNote.getFolderId();
        if (this.mFolderId == -4097) {
            INoteInfoHeader iNoteInfoHeader = this.mNoteExtraInfoController;
            if (iNoteInfoHeader != null) {
                iNoteInfoHeader.updateFolderName(NoteApp.getInstance().getString(R.string.unclassfied_folder_name));
            }
        } else {
            asyncUpdateFolderName();
        }
        if (isHideFolderChooser()) {
            INoteInfoHeader iNoteInfoHeader2 = this.mNoteExtraInfoController;
            if (iNoteInfoHeader2 != null) {
                iNoteInfoHeader2.setFolderGroupVisibility(8);
            }
        } else {
            INoteInfoHeader iNoteInfoHeader3 = this.mNoteExtraInfoController;
            if (iNoteInfoHeader3 != null) {
                iNoteInfoHeader3.setFolderGroupVisibility(0);
            }
        }
        if (isResumed()) {
            registerTapShareCallBack();
            if (FloatWindowManagerAgent.isContinuousExcerpt()) {
                if (!isThisNoteDoExcerpt()) {
                    FloatWindowManagerAgent.resumeContinuousExcerpt();
                } else if (this.mIsShow) {
                    FloatWindowManagerAgent.pauseContinuousExcerpt();
                }
            }
        }
        updateEditor(z);
        Trace.endSection();
    }

    protected void registerTapShareCallBack() {
        try {
            Bundle bundle = NoteApp.getInstance().getPackageManager().getApplicationInfo("com.miui.mishare.connectivity", 128).metaData;
            int i = bundle != null ? bundle.getInt("support_bylink_send", -1) : -1;
            Logger.INSTANCE.d(TAG, "isSupportTapShare: " + i);
            if (this.mTapShareSendCallback == null || i != 1) {
                return;
            }
            if (this.mWorkingNote != null && this.mWorkingNote.getFolderId() == -4) {
                TapShareManager.getInstance().unregisterSendCallback(requireActivity(), this.mTapShareSendCallback);
                Logger.INSTANCE.d(TAG, "Unregistered TapShare for private note");
            } else if (this.mIsRecording) {
                TapShareManager.getInstance().unregisterSendCallback(requireActivity(), this.mTapShareSendCallback);
                Logger.INSTANCE.d(TAG, "Unregistered TapShare for recording state");
            } else {
                TapShareManager.getInstance().registerSendCallback(requireActivity(), this.mTapShareSendCallback);
                Logger.INSTANCE.d(TAG, "Registered TapShare for common note");
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "get appInfo error: " + e);
        }
    }

    protected void unregisterTapShareCallback() {
        try {
            if (this.mTapShareSendCallback != null) {
                TapShareManager.getInstance().unregisterSendCallback(requireActivity(), this.mTapShareSendCallback);
                Logger.INSTANCE.d(TAG, "Unregistered TapShare callback");
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "unregisterTapShareCallback error: " + e);
        }
    }

    public Uri getDeepLink() {
        if (this.mWorkingNote != null) {
            return HandoffHelper.getNoteDeepLink(this.mWorkingNote.getSyncId(), this.mWorkingNote.getFolderId());
        }
        return null;
    }

    protected void updateEditor(boolean z) {
        this.mNoteRichEditor.setDataId(this.mWorkingNote.getNoteId());
        this.mNoteRichEditor.setFolderId(this.mWorkingNote.getFolderId());
        this.mNoteRichEditor.updateFontSizeId(PreferenceUtils.getFontSize(getActivity(), 1));
        String content = this.mWorkingNote.getContent();
        String title = this.mWorkingNote.getTitle();
        Logger.INSTANCE.d(TAG, "updateEditor " + z + "->setData");
        if (this.mIsFromAiAction) {
            this.mNoteRichEditor.pasteMarkdown(content, title);
        } else {
            this.mNoteRichEditor.setData(content, title, this.mSearchText, Boolean.valueOf(this.mIsNeedSetData), this.mHighLightKeyWords);
        }
    }

    protected void setAlertTime(Long l) {
        if (!isAdded() || this.mWorkingNote == null) {
            return;
        }
        if (!this.mWorkingNote.isEmpty() && this.mWorkingNote.getNoteId() > 0) {
            this.mWorkingNote.setAlertDate(l.longValue());
            if (l.longValue() > 0) {
                Toast.makeText(NoteApp.getInstance(), R.string.date_time_picker_has_set_alarm, 0).show();
                PermissionUtils.showNotificationPermission(getAppCompatActivity());
            }
            this.mHandler.removeMessages(3);
            this.mHandler.sendEmptyMessage(3);
            return;
        }
        this.mWorkingNote.setAlertDate(0L);
        Toast.makeText(NoteApp.getInstance(), R.string.error_note_empty_for_clock, 0).show();
    }

    @Deprecated
    protected void handleAudioCTARequest() {
        if (!CTAManager.getInstance().isAccepted(getContext())) {
            FragmentActivity activity = getActivity();
            if (activity instanceof CTAActivity) {
                ((CTAActivity) activity).showCtaDialog();
                CTAManager.getInstance().addListener(new CTAManager.CTAListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.12
                    @Override // com.miui.notes.cta.CTAManager.CTAListener
                    public void onAccept() {
                        BaseNormalNoteEditFragment.this.updateRecordMode();
                    }

                    @Override // com.miui.notes.cta.CTAManager.CTAListener
                    public void onReject() {
                        Logger.INSTANCE.w(BaseNormalNoteEditFragment.TAG, "user deny to authorization");
                    }
                });
                return;
            } else {
                Logger.INSTANCE.e(TAG, "get Activity is not instanceof CTAActivity");
                return;
            }
        }
        updateRecordMode();
    }

    public void asyncSaveNote() {
        Logger.INSTANCE.d(TAG, "asyncSaveNote ");
        if (isAdded() && this.mWorkingNote != null) {
            updateWorkingTextAndTitle();
            if (this.mHandler.hasMessages(1)) {
                return;
            }
            if (!DualScreenManager.INSTANCE.isPcActivityLiving()) {
                this.mHandler.sendEmptyMessageDelayed(1, getAutoSaveCommonDuration());
            } else {
                this.mHandler.sendEmptyMessageDelayed(1, 1000L);
            }
        }
    }

    public boolean saveNote(boolean z) {
        String string;
        Logger.INSTANCE.d(TAG, "saveNote:" + (this.mWorkingNote == null ? "null" : Integer.valueOf(this.mWorkingNote.hashCode())) + ",force:" + z);
        if (this.mWorkingNote == null) {
            return false;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (z) {
            CharSequence richTextData = this.mNoteRichEditor.getRichTextData();
            if (richTextData != null) {
                string = richTextData.toString();
            } else {
                string = "";
            }
            this.mWorkingNote.setWorkingText(string);
        } else {
            updateWorkingTextAndTitle();
        }
        long noteId = this.mWorkingNote.getNoteId();
        boolean zSaveNote = this.mWorkingNote.saveNote(z);
        if (noteId <= 0 && zSaveNote) {
            this.mNoteRichEditor.setDataId(this.mWorkingNote.getNoteId());
            this.mNoteRichEditor.setFolderId(this.mWorkingNote.getFolderId());
            if (isAdded()) {
                getActivity().setResult(-1);
            }
            registerNoteContentObserver(true);
        }
        Logger.INSTANCE.d(TAG, "saveNote finish,ts cost:" + (System.currentTimeMillis() - jCurrentTimeMillis));
        return zSaveNote;
    }

    protected void asyncSaveNoteInstantlyIfNeeded() {
        Logger.INSTANCE.d(TAG, "asyncSaveNoteInstantlyIfNeeded");
        if (this.mWorkingNote == null || !this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.removeMessages(1);
        Logger.INSTANCE.d(TAG, "asyncSaveNoteInstantlyIfNeeded SYNC_SAVE_NOTE");
        this.mHandler.sendEmptyMessage(1);
    }

    protected void asyncSaveNoteInstantly() {
        Logger.INSTANCE.d(TAG, "asyncSaveNoteInstantlyIfNeeded");
        if (this.mWorkingNote != null) {
            if (this.mHandler.hasMessages(1)) {
                this.mHandler.removeMessages(1);
            }
            Logger.INSTANCE.d(TAG, "asyncSaveNoteInstantlyIfNeeded SYNC_SAVE_NOTE");
            this.mHandler.sendEmptyMessage(1);
        }
    }

    public void saveNoteInstantlyIfNeeded() {
        Logger.INSTANCE.d(TAG, "saveNoteInstantlyIfNeeded,workingNote:,hasSavingMsg:" + this.mHandler.hasMessages(1));
        if (this.mWorkingNote == null || !this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.removeMessages(1);
        saveNote(true);
    }

    protected void killSaveTask() {
        Logger.INSTANCE.d(TAG, "killSaveTask");
        NoteLoadTask noteLoadTask = this.mLoadTask;
        if (noteLoadTask != null) {
            noteLoadTask.cancel();
            this.mLoadTask = null;
        }
        Handler handler = this.mHandler;
        if (handler == null) {
            Logger.INSTANCE.w(TAG, "killSaveTask mHandler is null! ");
            return;
        }
        if (handler.hasMessages(4)) {
            this.mHandler.removeMessages(4);
        }
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessage(2);
        } else if (this.mWorkingNote != null && this.mWorkingNote.isEmpty()) {
            this.mHandler.sendEmptyMessage(2);
        } else {
            this.mExecutorService.shutdown();
        }
    }

    public void cancelSaveTask() {
        if (this.mHandler.hasMessages(4)) {
            this.mHandler.removeMessages(4);
        }
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
        }
        this.mExecutorService.shutdown();
    }

    protected void confirmEditAnyway(DialogInterface.OnClickListener onClickListener) {
        Logger.INSTANCE.d("NotesApp", "confirmEditAnyway: ");
        dismissDialog();
        manageDialog(new AlertDialog.Builder(getActivity()).setTitle(R.string.alert_title_edit_anyway).setMessage(R.string.alert_message_edit_anyway).setPositiveButton(R.string.alert_action_edit_anyway, onClickListener).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show());
    }

    public void delayChangeToMindNote() {
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.hide(false);
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onChangeToMindNote();
            }
        }, 50L);
    }

    public void onChangeToMindNote() {
        if (!isAdded() || this.mWorkingNote == null || isReadOnlyMode()) {
            return;
        }
        cancelSaveTask();
        try {
            NoteStore.delete(NoteApp.getInstance(), new long[]{getNoteId()});
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "onChangeToMindNote delete note error, e: " + e);
        }
        MindNoteStat.isCreateMindNote = true;
        Intent intent = new Intent(getActivity(), (Class<?>) WebViewBrainActivity.class);
        NoteLoadDataEntity noteLoadDataEntityCreateMindNoteFromNormalNote = NoteLoadDataBuilder.createMindNoteFromNormalNote(1, this.mNoteRichEditor.getTitle());
        if (FolderModel.isFakeFolder(this.mFolderId)) {
            noteLoadDataEntityCreateMindNoteFromNormalNote.setFolderId(0L);
        } else {
            noteLoadDataEntityCreateMindNoteFromNormalNote.setFolderId(this.mFolderId);
        }
        noteLoadDataEntityCreateMindNoteFromNormalNote.setNoteId(-2L);
        if (this.mFolderId == -4) {
            PrivateNotesListActivity.ACTION_FROM_PRIVACY = true;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", noteLoadDataEntityCreateMindNoteFromNormalNote);
        intent.putExtras(bundle);
        intent.setAction(NoteIntent.ACTION_MIND);
        getActivity().startActivityForResult(intent, 16, ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.activity_fade_in, R.anim.activity_fade_out).toBundle());
        getActivity().finish();
    }

    private static class NoteContentObserver extends ContentObserver {
        private final WeakReference<BaseNormalNoteEditFragment> refs;

        public NoteContentObserver(BaseNormalNoteEditFragment baseNormalNoteEditFragment, Handler handler) {
            super(handler);
            this.refs = new WeakReference<>(baseNormalNoteEditFragment);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (this.refs.get() != null) {
                this.refs.get().executeWidgetUpdateTask();
            }
        }
    }

    protected void registerNoteContentObserver(boolean z) {
        Context context = getContext();
        if (context == null || this.mNoteContentObserver == null) {
            Logger.INSTANCE.e(TAG, " context or observer is null, " + this.mNoteContentObserver);
            return;
        }
        if (z) {
            if (isResumed() && this.mWorkingNote != null && this.mWorkingNote.getNoteId() > 0) {
                context.getContentResolver().registerContentObserver(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, this.mWorkingNote.getNoteId()), false, this.mNoteContentObserver);
                Logger.INSTANCE.d(TAG, "registerNoteContentObserver: true ,for:" + this.mNoteContentObserver + ",owner:" + this);
                return;
            }
            return;
        }
        context.getContentResolver().unregisterContentObserver(this.mNoteContentObserver);
        Logger.INSTANCE.d(TAG, "registerNoteContentObserver:false,for:" + this.mNoteContentObserver + ",owner:" + this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BaseEditHandler extends Handler {
        private final WeakReference<BaseNormalNoteEditFragment> wkFragment;

        private BaseEditHandler(BaseNormalNoteEditFragment baseNormalNoteEditFragment) {
            super(Looper.getMainLooper());
            this.wkFragment = new WeakReference<>(baseNormalNoteEditFragment);
        }

        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            super.dispatchMessage(message);
            final BaseNormalNoteEditFragment baseNormalNoteEditFragment = this.wkFragment.get();
            if (message.what == 0) {
                Logger.INSTANCE.d("BaseEditHandler", "dispatchMessage msg what " + message.what);
            }
            Logger.INSTANCE.d("BaseEditHandler", "dispatchMessage msg what " + message.what + ",for fragment:" + baseNormalNoteEditFragment);
            if (baseNormalNoteEditFragment == null) {
                return;
            }
            if (message.what == 4) {
                baseNormalNoteEditFragment.executeCreateNewNoteTask();
                NotesTextStat.isCreateCommonNote = true;
            }
            if (baseNormalNoteEditFragment.getWorkingNote() == null || !baseNormalNoteEditFragment.isAdded()) {
                return;
            }
            switch (message.what) {
                case 1:
                    Schedulers.io().scheduleDirect(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$BaseEditHandler$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            baseNormalNoteEditFragment.saveNote(true);
                        }
                    });
                    break;
                case 2:
                    baseNormalNoteEditFragment.saveNote(true);
                    if (baseNormalNoteEditFragment.mWorkingNote.isEmpty()) {
                        Logger.INSTANCE.d("ARTest", "normalEdit scheduleDirect DeleteEmptyRunnable");
                        Schedulers.io().scheduleDirect(new NoteStore.DeleteEmptyRunnable());
                        if (baseNormalNoteEditFragment.mWorkingNote.getNoteId() == FloatWindowManagerAgent.getExcerptId()) {
                            FloatWindowManagerAgent.stopContinuousExcerpt();
                        }
                    }
                    baseNormalNoteEditFragment.mWorkingNote = null;
                    break;
                case 3:
                    baseNormalNoteEditFragment.refreshAlertTime();
                    if (baseNormalNoteEditFragment.mWorkingNote != null && baseNormalNoteEditFragment.mWorkingNote.getAlertDate() > 0) {
                        sendEmptyMessageDelayed(3, 60000 - (System.currentTimeMillis() % 60000));
                        break;
                    }
                    break;
                case 5:
                    baseNormalNoteEditFragment.showLoadingData(true);
                    break;
                case 6:
                    baseNormalNoteEditFragment.showContentOverLength(message.arg1);
                    break;
                case 7:
                    baseNormalNoteEditFragment.showImageAnnotationWarning();
                    break;
                case 8:
                    baseNormalNoteEditFragment.showBrowseModeWarning();
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBrowseModeWarning() {
        Toast toastMakeText = Toast.makeText(NoteApp.getInstance(), R.string.warn_enter_browse_mode, 0);
        mWarnToast = toastMakeText;
        toastMakeText.show();
    }

    protected void asyncUpdateFolderName() {
        Logger.INSTANCE.d(TAG, "asyncUpdateFolderName");
        Disposable disposable = this.mFolderNameGetTask;
        if (disposable != null && !disposable.isDisposed()) {
            this.mFolderNameGetTask.dispose();
            this.mFolderNameGetTask = null;
        }
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda3
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f$0.lambda$asyncUpdateFolderName$18(observableEmitter);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.Observer<Pair<String, Long>>() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.13
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable2) {
                BaseNormalNoteEditFragment.this.mFolderNameGetTask = disposable2;
            }

            @Override // io.reactivex.Observer
            public void onNext(Pair<String, Long> pair) {
                String str = (String) pair.first;
                long jLongValue = ((Long) pair.second).longValue();
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "asyncUpdateFolderName folderIdWhenRequested = " + jLongValue + " currentFolderId = " + BaseNormalNoteEditFragment.this.mFolderId);
                if (BaseNormalNoteEditFragment.this.isAdded() && BaseNormalNoteEditFragment.this.mNoteExtraInfoController != null && jLongValue == BaseNormalNoteEditFragment.this.mFolderId) {
                    BaseNormalNoteEditFragment.this.mNoteExtraInfoController.updateFolderName(str);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$asyncUpdateFolderName$18(ObservableEmitter observableEmitter) throws Exception {
        long j = this.mFolderId;
        observableEmitter.onNext(new Pair(FolderStore.getSubject(j), Long.valueOf(j)));
        observableEmitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract class NoteLoadTask extends Task<WorkingNote> {
        protected ArrayList<String> highLightKeyWords;
        protected Intent intent;
        protected String searchContent;

        protected abstract WorkingNote loadWorkingNote();

        protected abstract WorkingNote parseWorkingNote();

        public NoteLoadTask(Intent intent) {
            this.intent = intent;
            this.searchContent = BaseNormalNoteEditFragment.this.getSearchExtraDataKeyFromIntent(intent);
            this.highLightKeyWords = BaseNormalNoteEditFragment.this.getHighLightKeyWordsFromIntent(intent);
        }

        @Override // com.miui.common.tool.async.Task
        public void onPrepare(TaskManager taskManager) {
            BaseNormalNoteEditFragment.this.onLoadDataStart();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.common.tool.async.Task
        public WorkingNote doLoad() {
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "doLoad");
            WorkingNote workingNoteLoadWorkingNote = loadWorkingNote();
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "workingNote is null? " + (workingNoteLoadWorkingNote == null));
            if (workingNoteLoadWorkingNote == null || BaseNormalNoteEditFragment.this.isWorkingNoteIdInvalid(workingNoteLoadWorkingNote.getNoteId())) {
                WorkingNote workingNote = parseWorkingNote();
                if (workingNote != null) {
                    workingNote.saveNote(true);
                }
                return workingNote;
            }
            if (!RomUtils.isPadDevice() && !UIUtils.isFoldInternalScreen(BaseNormalNoteEditFragment.this.getContext())) {
                handleTemporaryAudioFile(workingNoteLoadWorkingNote);
            }
            return workingNoteLoadWorkingNote;
        }

        private void handleTemporaryAudioFile(WorkingNote workingNote) {
            String temAudioPath = PreferenceUtils.getTemAudioPath(NoteApp.getInstance());
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "handleTemporaryAudioFile tmpAudioPath :" + temAudioPath);
            if (TextUtils.isEmpty(temAudioPath) || workingNote.getContent() == null || !workingNote.getContent().contains(AudioUtils.TEMP_AUDIO_FLAG)) {
                return;
            }
            if (!AudioUtils.isMp3FileNameFormat(temAudioPath)) {
                temAudioPath = AudioUtils.renameMp3FileToSha1(AudioUtils.NOTE_MP3_DIR_NAME, temAudioPath);
            }
            if (!TextUtils.isEmpty(temAudioPath)) {
                workingNote.setWorkingText(workingNote.getContent().replace(AudioUtils.TEMP_AUDIO_FLAG, "<sound fileid=\"" + temAudioPath + "\" />"));
                workingNote.saveNote();
            }
            PreferenceUtils.setTemAudioPath(NoteApp.getInstance(), "");
        }

        @Override // com.miui.common.tool.async.Task
        public void onException(TaskManager taskManager, Exception exc) {
            onResult(taskManager, (WorkingNote) null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, WorkingNote workingNote) {
            if (BaseNormalNoteEditFragment.this.mLoadTask != this) {
                return;
            }
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "onResult,workingNoteId:" + (workingNote == null ? "null" : workingNote.getNoteId() + ""));
            BaseNormalNoteEditFragment.this.mLoadTask = null;
            updateUI(workingNote);
        }

        protected void updateUI(WorkingNote workingNote) {
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "NoteLoadTask updateUI");
            if (workingNote == null || BaseNormalNoteEditFragment.this.isWorkingNoteIdInvalid(workingNote.getNoteId())) {
                if (workingNote != null) {
                    Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "NoteLoadTask updateUI,invalid note data:");
                }
                BaseNormalNoteEditFragment.this.onLoadDataFailed();
                return;
            }
            boolean z = (TextUtils.isEmpty(this.searchContent) || this.searchContent.equals(BaseNormalNoteEditFragment.this.mSearchText) || (RomUtils.isPadMode() && (!RomUtils.isPadMode() || !BaseNormalNoteEditFragment.this.mInSearchMode))) ? false : true;
            if (z) {
                BaseNormalNoteEditFragment.this.mSearchText = this.searchContent;
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "updateUI: mSearchText = ");
            }
            BaseNormalNoteEditFragment.this.mHighLightKeyWords = this.highLightKeyWords;
            BaseNormalNoteEditFragment.this.mIsFromAiAction = NoteIntent.ACTION_CREATE_NOTE.equals(this.intent.getType());
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "updateUI: mIsFromAiAction = " + BaseNormalNoteEditFragment.this.mIsFromAiAction);
            BaseNormalNoteEditFragment.this.setWorkingNote(workingNote, "android.intent.action.INSERT_OR_EDIT".equals(this.intent.getAction()));
            if (TextUtils.isEmpty(workingNote.getContent()) && TextUtils.isEmpty(workingNote.getTitle())) {
                return;
            }
            if (TextUtils.isEmpty(BaseNormalNoteEditFragment.this.mSearchText)) {
                BaseNormalNoteEditFragment.this.updateUserQuery("", false, this.highLightKeyWords);
            } else if (z) {
                BaseNormalNoteEditFragment baseNormalNoteEditFragment = BaseNormalNoteEditFragment.this;
                baseNormalNoteEditFragment.updateUserQuery(baseNormalNoteEditFragment.mSearchText, false, this.highLightKeyWords);
            }
        }

        public void commit() {
            TaskManagerUtils.getTaskManager().add(this);
        }
    }

    public class ViewNoteLoadTask extends NoteLoadTask {
        long noteId;

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote parseWorkingNote() {
            return null;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        public /* bridge */ /* synthetic */ void commit() {
            super.commit();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ WorkingNote doLoad() {
            return super.doLoad();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ void onException(TaskManager taskManager, Exception exc) {
            super.onException(taskManager, exc);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ void onPrepare(TaskManager taskManager) {
            super.onPrepare(taskManager);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        public /* bridge */ /* synthetic */ void onResult(TaskManager taskManager, WorkingNote workingNote) {
            super.onResult(taskManager, workingNote);
        }

        public ViewNoteLoadTask(Intent intent, long j) {
            super(intent);
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "ViewNoteLoadTask");
            BaseNormalNoteEditFragment.this.mHighLightKeyWords = intent.getStringArrayListExtra(NoteIntent.ACTION_WITH_AI_QUERY);
            this.noteId = j;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote loadWorkingNote() {
            return WorkingNote.loadNote("_id=" + this.noteId, null);
        }
    }

    public abstract class EditNoteLoadTask extends NoteLoadTask {
        protected int bgResId;
        protected long callDate;
        protected String contactName;
        protected boolean createNew;
        protected long folderId;
        protected String phoneNumber;
        protected int widgetId;
        protected int widgetType;

        public abstract void onEnterEdit(WorkingNote workingNote);

        public abstract void onInsertAudio();

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        public /* bridge */ /* synthetic */ void commit() {
            super.commit();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ void onException(TaskManager taskManager, Exception exc) {
            super.onException(taskManager, exc);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ void onPrepare(TaskManager taskManager) {
            super.onPrepare(taskManager);
        }

        public EditNoteLoadTask(Intent intent) {
            super(intent);
            initialize();
        }

        private void initialize() {
            String str;
            this.folderId = this.intent.getLongExtra("com.miui.notes.folder_id", 0L);
            this.widgetId = this.intent.getIntExtra("com.miui.notes.widget_id", 0);
            this.widgetType = this.intent.getIntExtra("com.miui.notes.widget_type", -1);
            this.bgResId = this.intent.getIntExtra(Notes.Intents.INTENT_EXTRA_BACKGROUND_ID, ResourceParser.getDefaultBgId(BaseNormalNoteEditFragment.this.getActivity()));
            String stringExtra = this.intent.getStringExtra("android.intent.extra.PHONE_NUMBER");
            this.contactName = this.intent.getStringExtra(Notes.Intents.INTENT_EXTRA_CONTACT_NAME);
            this.phoneNumber = TextProcessUtils.formatPhoneNum(stringExtra);
            long longExtra = this.intent.getLongExtra(Notes.Intents.INTENT_EXTRA_CALL_DATE, 0L);
            this.callDate = longExtra;
            if (longExtra != 0 && (str = this.phoneNumber) != null) {
                if (TextUtils.isEmpty(str)) {
                    Logger.INSTANCE.w(BaseNormalNoteEditFragment.TAG, "The call record number is null");
                    this.callDate = 0L;
                    this.phoneNumber = null;
                    return;
                }
                return;
            }
            this.phoneNumber = null;
        }

        public boolean isCallNote() {
            return !TextUtils.isEmpty(this.phoneNumber);
        }

        public boolean haveContactName() {
            return !TextUtils.isEmpty(this.contactName);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public WorkingNote doLoad() {
            WorkingNote workingNoteLoadWorkingNote = loadWorkingNote();
            if (workingNoteLoadWorkingNote != null && !BaseNormalNoteEditFragment.this.isWorkingNoteIdInvalid(workingNoteLoadWorkingNote.getNoteId()) && (workingNoteLoadWorkingNote.getNoteId() != -2 || this.phoneNumber == null)) {
                return workingNoteLoadWorkingNote;
            }
            WorkingNote workingNote = parseWorkingNote();
            if (workingNote != null && this.phoneNumber != null) {
                workingNote.saveNote(true);
            }
            return workingNote;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote loadWorkingNote() {
            if (this.phoneNumber == null) {
                return null;
            }
            WorkingNote workingNoteLoadNote = WorkingNote.loadNote("_id=(SELECT note_id FROM data WHERE data1=? AND mime_type=? AND PHONE_NUMBERS_EQUAL(data3,?) ORDER BY created_date DESC)", new String[]{String.valueOf(this.callDate), Notes.CallData.CONTENT_ITEM_TYPE, this.phoneNumber});
            if (workingNoteLoadNote == null || workingNoteLoadNote.getFolderId() == -2) {
                return workingNoteLoadNote;
            }
            return null;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote parseWorkingNote() {
            WorkingNote workingNoteCreateEmptyNote = WorkingNote.createEmptyNote(this.folderId, this.widgetId, this.widgetType, this.bgResId);
            String str = this.phoneNumber;
            if (str != null) {
                workingNoteCreateEmptyNote.convertToCallNote(str, this.callDate);
            }
            this.createNew = true;
            return workingNoteCreateEmptyNote;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, WorkingNote workingNote) {
            super.onResult(taskManager, workingNote);
            BaseNormalNoteEditFragment.this.showLoadingData(false);
            if (NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE.equals(this.intent.getType()) && PreferenceUtils.isCTAAccepted(BaseNormalNoteEditFragment.this.getContext())) {
                onInsertAudio();
                return;
            }
            if (haveContactName()) {
                BaseNormalNoteEditFragment.this.setContactName(this.contactName);
                return;
            }
            if (isCallNote()) {
                if (!PreferenceUtils.isFirstHandle(BaseNormalNoteEditFragment.this.getContext()) || RomUtils.isFoldDevice()) {
                    return;
                }
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "isCallNote showCTA");
                PermissionNewUtils.showCtaDialog(BaseNormalNoteEditFragment.this.mToCtaForSetPhoneNumber);
                return;
            }
            onEnterEdit(workingNote);
        }
    }

    protected void setContactName(String str) {
        if (isAdded()) {
            this.mContactName = str;
            if (PreferenceUtils.isFirstHandle(getContext())) {
                PermissionNewUtils.showCtaDialog(this.mToCtaForSetContactName);
            } else {
                PermissionNewUtils.requestOnReadContacts(this.mRequestOnEnterCallNote);
            }
        }
    }

    public class TapShareNoteLoadTask extends NoteLoadTask {
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote loadWorkingNote() {
            return null;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        public /* bridge */ /* synthetic */ void commit() {
            super.commit();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ WorkingNote doLoad() {
            return super.doLoad();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ void onException(TaskManager taskManager, Exception exc) {
            super.onException(taskManager, exc);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ void onPrepare(TaskManager taskManager) {
            super.onPrepare(taskManager);
        }

        public TapShareNoteLoadTask(Intent intent) {
            super(intent);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote parseWorkingNote() {
            WorkingNote workingNoteRestoreSingleNoteFromUri;
            try {
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "parseWorkingNote");
                Bundle extras = this.intent.getExtras();
                if (extras == null) {
                    Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "parseWorkingNote: tapShareBundle is null");
                    return null;
                }
                ArrayList parcelableArrayList = extras.getParcelableArrayList("tap_share_uris");
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "tapshareurisInBundle: " + parcelableArrayList);
                if (parcelableArrayList != null && !parcelableArrayList.isEmpty()) {
                    Uri uri = (Uri) parcelableArrayList.get(0);
                    Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "tapshareDataUris: " + uri);
                    if (extras.getBoolean("isAppleDevice")) {
                        Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "tapShareNoteFromIOS: ");
                        workingNoteRestoreSingleNoteFromUri = TapShareDataPrepareUtils.restoreNoteFromIOS(BaseNormalNoteEditFragment.this.getContext(), uri);
                        Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "IOSworkingNoteFromTapShare: " + workingNoteRestoreSingleNoteFromUri);
                    } else {
                        Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "tapShareNoteFromAndroid: ");
                        workingNoteRestoreSingleNoteFromUri = TapShareDataPrepareUtils.restoreSingleNoteFromUri(BaseNormalNoteEditFragment.this.getContext(), uri);
                    }
                    Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "workingNote: " + workingNoteRestoreSingleNoteFromUri);
                    return workingNoteRestoreSingleNoteFromUri;
                }
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "parseWorkingNote: uris is null or size is 0");
                return null;
            } catch (Exception e) {
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "parseWorkingNote Exception: " + e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, WorkingNote workingNote) {
            super.onResult(taskManager, workingNote);
            BaseNormalNoteEditFragment.this.showLoadingData(true);
            if (workingNote != null && !BaseNormalNoteEditFragment.this.isWorkingNoteIdInvalid(workingNote.getNoteId())) {
                Toast.makeText(BaseNormalNoteEditFragment.this.getActivity(), BaseNormalNoteEditFragment.this.getString(R.string.tapshare_restore_notes_success_toast), 0).show();
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "TapShareNoteLoadTask: Successfully imported note, noteId: " + workingNote.getNoteId());
            }
            updateUI(workingNote);
        }
    }

    public class SendNoteLoadTask extends NoteLoadTask {
        private long folderId;
        protected String sendContent;

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote loadWorkingNote() {
            return null;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        public /* bridge */ /* synthetic */ void commit() {
            super.commit();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ WorkingNote doLoad() {
            return super.doLoad();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ void onException(TaskManager taskManager, Exception exc) {
            super.onException(taskManager, exc);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ void onPrepare(TaskManager taskManager) {
            super.onPrepare(taskManager);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        public /* bridge */ /* synthetic */ void onResult(TaskManager taskManager, WorkingNote workingNote) {
            super.onResult(taskManager, workingNote);
        }

        public SendNoteLoadTask(Intent intent) {
            super(intent);
            this.sendContent = null;
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "SendNoteLoadTask");
            this.folderId = intent.getLongExtra("com.miui.notes.folder_id", 0L);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote parseWorkingNote() throws Throwable {
            WorkingNote workingNoteCreateEmptyNote = WorkingNote.createEmptyNote(this.folderId, 0, -1, ResourceParser.getDefaultBgId(BaseNormalNoteEditFragment.this.getActivity()));
            String sendIntent = BaseNormalNoteEditFragment.this.parseSendIntent(this.intent);
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "text parsed:");
            if (this.intent.hasExtra("com.miui.share.extra.url")) {
                String stringExtra = this.intent.getStringExtra("android.intent.extra.SUBJECT");
                String stringExtra2 = this.intent.getStringExtra("com.miui.share.extra.url");
                if (!TextUtils.isEmpty(stringExtra2)) {
                    if (stringExtra == null) {
                        stringExtra = "";
                    }
                    NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(BaseNormalNoteEditFragment.this.getContext());
                    simpleBuilder.appendLink(stringExtra2, stringExtra);
                    sendIntent = simpleBuilder.toAncient() + StringUtils.LF;
                }
            }
            this.sendContent = sendIntent;
            String stringExtra3 = this.intent.getStringExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE);
            if (!TextUtils.isEmpty(stringExtra3)) {
                workingNoteCreateEmptyNote.setTitle(stringExtra3);
                AiActionUtil.asyncNotifyResult(0);
            }
            workingNoteCreateEmptyNote.setWorkingText(sendIntent);
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "parseWorkingNote:");
            workingNoteCreateEmptyNote.handleSent();
            return workingNoteCreateEmptyNote;
        }

        @Deprecated
        private void setSource(WorkingNote workingNote) {
            String stringExtra = this.intent.getStringExtra(Notes.Intents.INTENT_EXTRA_SOURCE_NAME);
            String callingPackage = BaseNormalNoteEditFragment.this.getActivity().getCallingPackage();
            Intent intent = (Intent) this.intent.getParcelableExtra(Notes.Intents.INTENT_EXTRA_SOURCE_INTENT);
            if (stringExtra == null && callingPackage != null) {
                stringExtra = Utils.getApplicationLabel(BaseNormalNoteEditFragment.this.getActivity(), callingPackage);
            }
            workingNote.setSourceName(stringExtra);
            workingNote.setSourcePackage(callingPackage);
            workingNote.setSourceIntent(intent);
        }
    }

    protected String parseSendIntent(Intent intent) throws Throwable {
        Logger.INSTANCE.d(TAG, "parseSendIntent,type:" + intent.getType());
        String contactTextFromIntent = Contact.parseContactTextFromIntent(getActivity(), intent);
        if (!TextUtils.isEmpty(contactTextFromIntent)) {
            Logger.INSTANCE.d(TAG, "parseSendIntent/text is not empty");
            return contactTextFromIntent;
        }
        NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(getContext());
        String stringExtra = intent.getStringExtra("android.intent.extra.TEXT");
        if (!TextUtils.isEmpty(stringExtra)) {
            Logger.INSTANCE.d(TAG, "parseSendIntent/intent has extra text");
            simpleBuilder.appendText(stringExtra);
            AiActionUtil.asyncNotifyResult(0);
        }
        Uri uriFromFile = (Uri) intent.getParcelableExtra("android.intent.extra.STREAM");
        if (uriFromFile == null) {
            Logger.INSTANCE.d(TAG, "parseSendIntent/intent has no extra stream ");
            String stringExtra2 = intent.getStringExtra(NoteIntent.EXTRA_INSERT_IMAGE_NOTE);
            if (!TextUtils.isEmpty(stringExtra2)) {
                String strSaveImageFile = AttachmentUtils.saveImageFile((Context) getActivity(), Uri.parse(stringExtra2), true);
                if (strSaveImageFile != null) {
                    if (!simpleBuilder.isEmpty()) {
                        simpleBuilder.appendNewLine();
                    }
                    simpleBuilder.appendImage(strSaveImageFile);
                    simpleBuilder.appendNewLine();
                    AiActionUtil.asyncNotifyResult(0);
                } else {
                    AiActionUtil.asyncNotifyResult(-1);
                }
            }
            return simpleBuilder.toAncient();
        }
        if (uriFromFile.getScheme() == null) {
            uriFromFile = Uri.fromFile(new File(uriFromFile.getEncodedPath()));
        }
        if (intent.getType().startsWith("image/")) {
            String strSaveImageFile2 = AttachmentUtils.saveImageFile((Context) getActivity(), uriFromFile, true);
            if (strSaveImageFile2 != null) {
                if (!simpleBuilder.isEmpty()) {
                    simpleBuilder.appendNewLine();
                }
                simpleBuilder.appendImage(strSaveImageFile2);
                simpleBuilder.appendNewLine();
            } else {
                getActivity().runOnUiThread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(NoteApp.getInstance(), R.string.toast_message_add_image_fail, 0).show();
                    }
                });
            }
        } else {
            InputStream inputStreamOpenInputStream = null;
            try {
                try {
                    inputStreamOpenInputStream = getActivity().getContentResolver().openInputStream(uriFromFile);
                    int iAvailable = inputStreamOpenInputStream.available();
                    if (iAvailable > 300000) {
                        iAvailable = 300000;
                    }
                    byte[] bArr = new byte[iAvailable];
                    int i = inputStreamOpenInputStream.read(bArr);
                    if (i != iAvailable) {
                        Logger.INSTANCE.w(TAG, "parseSendIntent/Read bytes is not same as expected, file=" + uriFromFile + ",read=" + i + ",expected=" + iAvailable);
                    }
                    CharsetDetector charsetDetector = new CharsetDetector();
                    charsetDetector.setText(bArr);
                    String string = charsetDetector.detect().getString();
                    Logger.INSTANCE.d(TAG, "parseSendIntent/parse txt from file with text:" + string.length());
                    simpleBuilder.appendText(string);
                } catch (IOException e) {
                    Logger.INSTANCE.e(TAG, "parseSendIntent/Error in handling the send intent" + e);
                }
            } finally {
                IOUtils.closeQuietly(inputStreamOpenInputStream);
            }
        }
        return simpleBuilder.toAncient();
    }

    public class DeleteNoteTask extends NoteLoadTask {
        protected long date;
        protected long folderId;
        protected volatile boolean hasDeleted;
        protected long noteId;

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote parseWorkingNote() {
            return null;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        public /* bridge */ /* synthetic */ void commit() {
            super.commit();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ WorkingNote doLoad() {
            return super.doLoad();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ void onException(TaskManager taskManager, Exception exc) {
            super.onException(taskManager, exc);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public /* bridge */ /* synthetic */ void onPrepare(TaskManager taskManager) {
            super.onPrepare(taskManager);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        public /* bridge */ /* synthetic */ void onResult(TaskManager taskManager, WorkingNote workingNote) {
            super.onResult(taskManager, workingNote);
        }

        public DeleteNoteTask(Intent intent) {
            super(intent);
            this.noteId = intent.getLongExtra("android.intent.extra.UID", 0L);
            this.folderId = intent.getLongExtra(NoteIntent.KEY_FOLDER_ID, 0L);
            this.date = intent.getLongExtra(NoteIntent.KEY_DATE, 0L);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote loadWorkingNote() {
            int iDelete = NoteStore.delete(BaseNormalNoteEditFragment.this.getActivity(), new long[]{this.noteId});
            Logger.INSTANCE.d("DeleteNoteTask", "NoteStore.delete noteId " + this.noteId);
            Logger.INSTANCE.d("DeleteNoteTask", "NoteStore.delete " + iDelete);
            if (iDelete <= 0) {
                return null;
            }
            this.hasDeleted = true;
            return null;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected void updateUI(WorkingNote workingNote) {
            if (this.hasDeleted) {
                BaseNormalNoteEditFragment.this.onNoteDeleted(this.noteId);
            }
        }
    }

    protected static class ReplaceImageRichParserHandler extends EditorUtils.SnippetMediaHandler {
        private final Map<String, AttachmentUtils.ImageInfo> nInfos;

        public ReplaceImageRichParserHandler(Context context, Map<String, AttachmentUtils.ImageInfo> map) {
            super(context);
            this.nInfos = map == null ? new HashMap<>(0) : map;
        }

        @Override // com.miui.richeditor.util.EditorUtils.SnippetMediaHandler
        protected CharSequence getImageText(String str) {
            AttachmentUtils.ImageInfo imageInfo = this.nInfos.get(str);
            if (imageInfo == null) {
                return super.getImageText(str);
            }
            String string = Long.toString(imageInfo.id);
            String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(imageInfo.mimeType);
            if (extensionFromMimeType != null) {
                string = string + "." + extensionFromMimeType;
            }
            return this.nContext.getString(R.string.snippet_image_template, string);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void createFolderAndMoveItems(final long[] jArr) {
        CreateFolderDialog createFolderDialog = new CreateFolderDialog(getActivity());
        createFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda19
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$createFolderAndMoveItems$20(jArr, dialogInterface);
            }
        });
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        createFolderDialog.show();
        manageDialog(createFolderDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createFolderAndMoveItems$20(long[] jArr, DialogInterface dialogInterface) {
        CreateFolderDialog createFolderDialog = (CreateFolderDialog) dialogInterface;
        if (createFolderDialog.getFolderId() > 0) {
            this.mNoteExtraInfoController.updateFolderName(createFolderDialog.getFolderName());
            refreshFolderInfo();
            moveCheckedItems(createFolderDialog.getFolderId(), jArr, createFolderDialog.getFolderName());
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshFolderInfo() {
        FolderStore.INSTANCE.query(getLoaderManager(), 1, 3, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.14
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                FolderStore.updateEnableNewDefaultFolderName(NoteApp.getInstance(), cursor);
                cursor.close();
                BaseNormalNoteEditFragment.this.getLoaderManager().destroyLoader(3);
            }
        });
    }

    public class NoteMenuLoaderCallbacks extends CursorLoaderBuilder.DefaultLoaderCallbacks {
        protected int position;

        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoaderReset(Loader<Cursor> loader) {
        }

        public NoteMenuLoaderCallbacks(int i) {
            this.position = i;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if (BaseNormalNoteEditFragment.this.isAdded() && loader.getId() == 2 && cursor != null) {
                Cursor staticCursor = ChooseFolderAdapter.getStaticCursor(cursor);
                LoaderManager.getInstance(BaseNormalNoteEditFragment.this).destroyLoader(2);
                updateExtraInfo(staticCursor, new long[]{BaseNormalNoteEditFragment.this.getNoteId()});
            }
        }

        protected void updateExtraInfo(Cursor cursor, long[] jArr) {
            BaseNormalNoteEditFragment.this.mNoteExtraInfoController.setHeadMenuInfo(cursor, jArr, BaseNormalNoteEditFragment.this.mFolderId);
            BaseNormalNoteEditFragment.this.mNoteExtraInfoController.onNoteMenuClick(this.position);
        }
    }

    protected void notifyEditUiState(int i) {
        Intent intent = new Intent();
        intent.setAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_CHANGED);
        intent.putExtra(FrameConstant.PARAM_SCREEN_ID, 0);
        intent.putExtra(FrameConstant.PARAM_SCREEN_MODE, i);
        try {
            getActivity().sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void responseEditUiState() {
        if (isInEditAction()) {
            notifyEditUiState(1);
        } else {
            notifyEditUiState(0);
        }
    }

    protected void deleteCurrentNote() {
        if (this.mWorkingNote == null) {
            Logger.INSTANCE.e(TAG, "deleteCurrentNote: mWorkingNote is null");
            return;
        }
        if (this.mWorkingNote.existInDatabase()) {
            HashSet hashSet = new HashSet();
            long noteId = this.mWorkingNote.getNoteId();
            if (noteId != 0) {
                hashSet.add(Long.valueOf(noteId));
            } else {
                Logger.INSTANCE.e(TAG, "Wrong note id, should not happen");
            }
            DataUtils.batchDeleteNotes(getActivity().getContentResolver(), hashSet);
        }
        this.mWorkingNote.markDeleted(true);
        this.mWorkingNote.invalid();
    }

    protected NoteThemeResCache loadNoteThemeResToCache(Theme theme) {
        theme.getEditorStyle(getContext());
        NoteThemeResCache noteThemeResCache = new NoteThemeResCache(theme.getId());
        noteThemeResCache.loadActionBarRes(theme, getContext());
        noteThemeResCache.loadToolBarRes(theme, getContext());
        Logger.INSTANCE.d(TAG, "loadNoteThemeResToCache");
        return noteThemeResCache;
    }

    @Override // com.miui.notes.model.WorkingNote.NoteSettingChangedListener
    public void onWidgetChanged(int i, int i2) {
        UIUtils.updateWidget(NoteApp.getInstance(), i2, i);
    }

    public void onClockAlertChanged(long j, long j2) {
        updateWorkingTextAndTitle();
        if (this.mWorkingNote.getNoteId() > 0) {
            getActionBarController().updateAlarm(this.mWorkingNote);
            if (j > 0) {
                if (!this.mWorkingNote.isEmpty()) {
                    scheduleAlert(j, false);
                } else {
                    scheduleAlert(j, true);
                }
            } else if (j2 > 0) {
                scheduleAlert(j2, true);
            }
        }
        asyncSaveNote();
    }

    private void scheduleAlert(long j, boolean z) {
        AlarmReceiver.scheduleAlert(getActivity().getApplicationContext(), this.mWorkingNote.getNoteId(), j, z);
    }

    @Override // com.miui.notes.ui.BaseActionBarController.EditCallback
    public boolean isNoteHidden() {
        if (this.mWorkingNote == null) {
            return false;
        }
        return this.mWorkingNote.isHidden();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normal_note_edit_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected boolean handleIntentData(Intent intent) {
        if (intent == null) {
            return true;
        }
        String action = intent.getAction();
        this.mFromWidget = intent.getBooleanExtra(FrameConstant.PARAM_FROM_WIDGET, false);
        if (isTapShare(intent)) {
            Logger.INSTANCE.d(TAG, "handleIntentData/intent action tap share ,handled:" + handleTapShareIntent(intent));
        }
        if ("android.intent.action.VIEW".equals(action)) {
            boolean zHandleViewIntent = handleViewIntent(intent);
            Logger.INSTANCE.d(TAG, "handleIntentData/intent action view ,handled:" + zHandleViewIntent);
            return zHandleViewIntent;
        }
        if ("android.intent.action.INSERT_OR_EDIT".equals(action) || "android.intent.action.SEND".equals(action)) {
            Logger.INSTANCE.d(TAG, "handleIntentData action: " + action);
            this.mIsEditNotes = false;
            if ("android.intent.action.INSERT_OR_EDIT".equals(action)) {
                Logger.INSTANCE.d(TAG, "handleIntentData/intent action edit");
                if (NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE.equals(intent.getType())) {
                    this.mIsEditNotes = true;
                    checkPermissionAndStartRecordFromIntent(intent);
                    return true;
                }
                return handleEditIntent(intent);
            }
            Logger.INSTANCE.d(TAG, "handleIntentData/intent action send");
            return handleSendIntent(intent);
        }
        if (NoteIntent.ACTION_DELETE.equals(action)) {
            return handleDeleteIntent(intent);
        }
        if (TextUtils.isEmpty(action)) {
            LogUtils.d(TAG, "Restore edit fragment");
            return false;
        }
        Logger.INSTANCE.w(TAG, "Unknown action: " + action);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: callbackHandleIntent, reason: merged with bridge method [inline-methods] */
    public void lambda$checkPermissionAndStartRecordFromIntent$21(Intent intent) {
        if ("android.intent.action.INSERT_OR_EDIT".equals(intent.getAction())) {
            handleEditIntent(intent);
        } else {
            handleSendIntent(intent);
        }
    }

    protected void viewLinkByBrowser(String str) {
        if (!isAdded() || getActivity() == null) {
            return;
        }
        if (!TextUtils.isEmpty(str) && !str.contains(HyperMenuUtils.GROUP_TITLE_START)) {
            str = URLUtil.guessUrl(str);
        }
        try {
            getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.INSTANCE.v(TAG, "viewLinkByBrowser startActivity failed");
        }
    }

    protected void checkPermissionAndStartRecordFromIntent(final Intent intent) {
        if (isAdded()) {
            this.callbackHandleIntentAction = new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$checkPermissionAndStartRecordFromIntent$21(intent);
                }
            };
            if (!PreferenceUtils.isCTAAccepted(getContext()) && PermissionUtils.supportNewPermissionStyle()) {
                PermissionNewUtils.showCtaDialog(this.toCtaBeforeAudioWithHandIntent);
            } else {
                PermissionNewUtils.requestOnAudioNote(this.requestAudioPermissionWithHandIntent);
            }
        }
    }

    protected static class MyDualScreenEditReceiver extends SafeBroadcastReceiver<BaseNormalNoteEditFragment> {
        public MyDualScreenEditReceiver(BaseNormalNoteEditFragment baseNormalNoteEditFragment) {
            super(baseNormalNoteEditFragment);
        }

        @Override // com.miui.common.base.SafeBroadcastReceiver, android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (hasReference()) {
                BaseNormalNoteEditFragment wkObject = getWkObject();
                int i = intent.getExtras().getInt(FrameConstant.PARAM_SCREEN_ID, 0);
                if (FrameConstant.ACTION_DUAL_SCREEN_STATE_REQUEST.equals(intent.getAction())) {
                    if (i != 0) {
                        wkObject.responseEditUiState();
                    }
                } else {
                    if (FrameConstant.ACTION_DUAL_SCREEN_STATE_CHANGED.equals(intent.getAction())) {
                        wkObject.handleDualScreenStateChanged(intent);
                        return;
                    }
                    if (FrameConstant.ACTION_DUAL_SCREEN_STATE_CONTENT_SYNC.equals(intent.getAction())) {
                        long j = intent.getExtras().getLong("note_id", -1L);
                        if (i == 0 || j != wkObject.getNoteId()) {
                            return;
                        }
                        wkObject.updateNoteFromDualScreen();
                    }
                }
            }
        }
    }

    protected void handleDualScreenStateChanged(Intent intent) {
        int i = intent.getExtras().getInt(FrameConstant.PARAM_SCREEN_ID, 0);
        int i2 = intent.getExtras().getInt(FrameConstant.PARAM_SCREEN_MODE, 0);
        if (i != 0) {
            if (i2 != 0) {
                if (i2 == 1) {
                    showDualScreenEditTipDialog();
                    return;
                } else if (i2 != 2) {
                    if (i2 != 3) {
                        return;
                    }
                    closeEditActivity();
                    return;
                }
            }
            if (this.mDualScreenEditTipDialog != null) {
                if (isAdded()) {
                    this.mDualScreenEditTipDialog.dismiss();
                }
                this.mDualScreenEditTipDialog = null;
            }
        }
    }

    protected void closeEditActivity() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    protected static class MyAudioTaskReceiver extends SafeBroadcastReceiver<RecordTaskWorker> {
        public MyAudioTaskReceiver(RecordTaskWorker recordTaskWorker) {
            super(recordTaskWorker);
        }

        @Override // com.miui.common.base.SafeBroadcastReceiver, android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (hasReference()) {
                getWkObject().onReceiveTaskResult(intent.getStringExtra("mp3_name"));
            }
        }
    }

    protected void handleBrowseMode() {
        FragmentActivity activity;
        Logger.INSTANCE.d(TAG, "handleBrowserMode " + this.mIsInPcNote + "activity:" + getActivity() + ",handler:" + this.mHandler);
        if (this.mIsInPcNote || (activity = getActivity()) == null || this.mHandler == null) {
            return;
        }
        if (UIUtils.isBrowseMode(getActivity()) || this.mInAiMode) {
            if (!this.mHandler.hasMessages(8) && !this.mInAiMode) {
                this.mHandler.removeMessages(8);
                this.mHandler.sendEmptyMessageDelayed(8, 400L);
            }
            setEnableEdit(false);
            this.mIsInBrowseMode = true;
            return;
        }
        this.mHandler.removeMessages(8);
        activity.getWindow().clearFlags(131072);
        if ((getActivity() instanceof PrivateNotesListActivity) && getActivity() != null && ((PrivateNotesListActivity) getActivity()).mPasswordController.mLockState == 1) {
            setEnableEdit(false);
        } else {
            setEnableEdit(true);
        }
        this.mIsInBrowseMode = false;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void initBaseResultLauncher() {
        this.toCtaBeforeAudioWithEditTask = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.15
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "toCtaBeforeAudioWithEditTask result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == 1) {
                    PermissionNewUtils.requestOnAudioNote(BaseNormalNoteEditFragment.this.requestAudioPermissionWithEditTask);
                } else if (activityResult.getResultCode() == 666) {
                    BaseNormalNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                } else if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(BaseNormalNoteEditFragment.this.toCtaBeforeAudioWithEditTask);
                }
            }
        });
        this.requestAudioPermissionWithEditTask = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.16
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(Map<String, Boolean> map) {
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "result:" + map.get("android.permission.RECORD_AUDIO"));
                if (Boolean.TRUE.equals(map.get("android.permission.RECORD_AUDIO"))) {
                    if (!UIUtils.isFoldInternalScreen(BaseNormalNoteEditFragment.this.getContext()) && BaseNormalNoteEditFragment.this.getActivity() != null && CompatUtils.invokeIsInMultiWindowMode(BaseNormalNoteEditFragment.this.getActivity())) {
                        Utils.hideSoftInput(BaseNormalNoteEditFragment.this.getActivity());
                        Toast.makeText(NoteApp.getInstance(), R.string.toast_refuse_to_record_when_in_multi_window_new, 0).show();
                        return;
                    }
                    BaseNormalNoteEditFragment.this.mIsFromAudioNoteShortCut = true;
                    if (!PreferenceUtils.getAllowXiaoai(BaseNormalNoteEditFragment.this.getContext()) && !RomUtils.isInternationalBuild()) {
                        BaseNormalNoteEditFragment baseNormalNoteEditFragment = BaseNormalNoteEditFragment.this;
                        baseNormalNoteEditFragment.manageDialog(PermissionUtils.showXiaoaiAcceptDialog(baseNormalNoteEditFragment.getActivity(), new PermissionUtils.AcceptListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.16.1
                            @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                            public void reject() {
                            }

                            @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                            public void accept() {
                                BaseNormalNoteEditFragment.this.updateRecordMode();
                            }
                        }));
                        return;
                    } else {
                        BaseNormalNoteEditFragment.this.updateRecordMode();
                        return;
                    }
                }
                BaseNormalNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                Toast.makeText(NoteApp.getInstance(), R.string.toast_refuse_to_use_record_audio, 0).show();
            }
        });
        this.toCtaBeforeAudioWithHandIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.17
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "toCtaBeforeAudioWithHandIntent result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == 1) {
                    PermissionNewUtils.requestOnAudioNote(BaseNormalNoteEditFragment.this.requestAudioPermissionWithHandIntent);
                } else if (activityResult.getResultCode() == 666) {
                    BaseNormalNoteEditFragment.this.getActivity().finish();
                } else if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(BaseNormalNoteEditFragment.this.toCtaBeforeAudioWithHandIntent);
                }
            }
        });
        this.requestAudioPermissionWithHandIntent = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new AnonymousClass18());
        this.mGalleryResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.19
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                BaseNormalNoteEditFragment.this.homeViewModel.toDetailResultCallback();
                if (activityResult.getResultCode() == -1) {
                    Intent data = activityResult.getData();
                    ArrayList parcelableArrayListExtra = data.getParcelableArrayListExtra("pick-result-data");
                    Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "onActivityResult add images, count: " + (parcelableArrayListExtra == null ? 0 : parcelableArrayListExtra.size()));
                    if (parcelableArrayListExtra == null) {
                        BaseNormalNoteEditFragment.this.insertImages(false, data.getData());
                        return;
                    }
                    Uri[] uriArr = new Uri[parcelableArrayListExtra.size()];
                    parcelableArrayListExtra.toArray(uriArr);
                    BaseNormalNoteEditFragment.this.insertImages(false, uriArr);
                }
            }
        });
        this.mNewGalleryResult = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(10), new ActivityResultCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda4
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initBaseResultLauncher$22((List) obj);
            }
        });
        this.mTakePhotoResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.20
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                BaseNormalNoteEditFragment.this.homeViewModel.toDetailResultCallback();
                if (activityResult.getResultCode() == -1) {
                    String scrapPath = NotesProvider.getScrapPath(BaseNormalNoteEditFragment.this.getActivity());
                    if (scrapPath == null) {
                        Logger.INSTANCE.e(BaseNormalNoteEditFragment.TAG, "scrapPath error");
                    } else {
                        BaseNormalNoteEditFragment.this.insertImages(false, Uri.fromFile(new File(scrapPath)));
                    }
                }
            }
        });
        this.mDoodleResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.21
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                BaseNormalNoteEditFragment.this.homeViewModel.toDetailResultCallback();
                if (activityResult.getResultCode() == -1) {
                    Intent data = activityResult.getData();
                    if (!BaseNormalNoteEditFragment.this.isAdded() || data == null) {
                        return;
                    }
                    BaseNormalNoteEditFragment.this.saveNewCachePath = data.getStringExtra(DoodleFragment.KEY_IMAGE_NAME);
                    if (TextUtils.isEmpty(BaseNormalNoteEditFragment.this.saveNewCachePath) || !BaseNormalNoteEditFragment.this.mPagePrepare) {
                        return;
                    }
                    BaseNormalNoteEditFragment.this.startInsertDoodle(AttachmentUtils.saveImageFile(NoteApp.getInstance(), new File(BaseNormalNoteEditFragment.this.saveNewCachePath)));
                    BaseNormalNoteEditFragment.this.saveNewCachePath = null;
                }
            }
        });
        this.mEidtDooleResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.22
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                BaseNormalNoteEditFragment.this.homeViewModel.toDetailResultCallback();
                if (activityResult.getResultCode() == -1) {
                    Intent data = activityResult.getData();
                    if (!BaseNormalNoteEditFragment.this.isAdded() || data == null) {
                        return;
                    }
                    BaseNormalNoteEditFragment.this.setmEidtDoodleResult(data);
                }
            }
        });
        this.mToShareResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.23
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                BaseNormalNoteEditFragment.this.homeViewModel.toDetailResultCallback();
            }
        });
        this.toChooseFolderResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.24
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                if (activityResult.getResultCode() == -1) {
                    Intent data = activityResult.getData();
                    if (!BaseNormalNoteEditFragment.this.isAdded() || data == null) {
                        return;
                    }
                    String stringExtra = data.getStringExtra(ChooserFolderDialogActivity.CHOOSER_FOLDER_SELECT_FOLDER);
                    long longExtra = data.getLongExtra(ChooserFolderDialogActivity.CHOOSER_FOLDER_SELECT_FOLDER_ID, 0L);
                    long[] longArrayExtra = data.getLongArrayExtra(ChooserFolderDialogActivity.CHOOSER_MOVE_NOTE_IDS);
                    if (BaseNormalNoteEditFragment.this.mWorkingNote != null && !BaseNormalNoteEditFragment.isNoteInMovingNotes(longArrayExtra, BaseNormalNoteEditFragment.this.mWorkingNote.getNoteId())) {
                        Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "toChooseFolderResult: the working note is not in the moving notes list.");
                        return;
                    }
                    if (!TextUtils.isEmpty(stringExtra) && BaseNormalNoteEditFragment.this.mNoteExtraInfoController != null) {
                        BaseNormalNoteEditFragment.this.mNoteExtraInfoController.updateFolderName(stringExtra);
                    }
                    if (BaseNormalNoteEditFragment.this.mWorkingNote != null) {
                        BaseNormalNoteEditFragment.this.mFolderId = longExtra;
                        BaseNormalNoteEditFragment.this.mWorkingNote.setFolderId(longExtra);
                    }
                }
            }
        });
        this.mSyncAccountResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.25
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                BaseNormalNoteEditFragment.this.setmSyncAccountResult(activityResult);
            }
        });
        this.requestAudioPermission = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new AnonymousClass26());
        this.toCtaBeforeAudio = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.27
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "toCtaBeforeAudio result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == 1) {
                    Utils.isCtaOnWin = true;
                    PermissionNewUtils.requestOnAudioNote(BaseNormalNoteEditFragment.this.requestAudioPermission);
                } else if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(BaseNormalNoteEditFragment.this.toCtaBeforeAudio);
                }
            }
        });
        this.mToCtaForSetContactName = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.28
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                PreferenceUtils.setFirstHandle(false, BaseNormalNoteEditFragment.this.getContext());
                if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(BaseNormalNoteEditFragment.this.mToCtaForSetContactName);
                } else {
                    BaseNormalNoteEditFragment baseNormalNoteEditFragment = BaseNormalNoteEditFragment.this;
                    baseNormalNoteEditFragment.setContactName(baseNormalNoteEditFragment.mContactName);
                }
            }
        });
        this.mToCtaForSetPhoneNumber = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.29
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                PreferenceUtils.setFirstHandle(false, BaseNormalNoteEditFragment.this.getContext());
                if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(BaseNormalNoteEditFragment.this.mToCtaForSetPhoneNumber);
                }
            }
        });
        this.mRequestOnEnterCallNote = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda5
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initBaseResultLauncher$23((Map) obj);
            }
        });
        this.toCtaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.30
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "toCtaLauncher result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(BaseNormalNoteEditFragment.this.toCtaLauncher);
                }
                BaseNormalNoteEditFragment.this.showSoftInput();
            }
        });
        this.toKoreaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda6
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initBaseResultLauncher$24((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: renamed from: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$18, reason: invalid class name */
    class AnonymousClass18 implements ActivityResultCallback<Map<String, Boolean>> {
        AnonymousClass18() {
        }

        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(Map<String, Boolean> map) {
            if (map == null || BaseNormalNoteEditFragment.this.getActivity() == null) {
                return;
            }
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "result:" + map.get("android.permission.RECORD_AUDIO"));
            final Intent intent = BaseNormalNoteEditFragment.this.getActivity().getIntent();
            String type = intent.getType();
            if (Boolean.TRUE.equals(map.get("android.permission.RECORD_AUDIO")) && !Utils.isInvisibleMode()) {
                BaseNormalNoteEditFragment.this.mIsFromAudioNoteShortCut = true;
                if (!BaseNormalNoteEditFragment.this.getActivity().isFinishing()) {
                    if (BaseNormalNoteEditFragment.this.callbackHandleIntentAction == null && "android.intent.action.INSERT_OR_EDIT".equals(intent.getAction()) && NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE.equals(type)) {
                        BaseNormalNoteEditFragment.this.callbackHandleIntentAction = new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$18$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$onActivityResult$0(intent);
                            }
                        };
                    }
                    BaseNormalNoteEditFragment.this.callbackHandleIntentAction.run();
                }
            } else {
                if (NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE.equals(type)) {
                    intent.setType(Notes.TextData.CONTENT_ITEM_TYPE);
                    intent.putExtra(ShortcutsTools.IS_FROM_OUTER_INTENT, true);
                    intent.putExtra("android.intent.extra.TEXT", "");
                }
                BaseNormalNoteEditFragment.this.lambda$checkPermissionAndStartRecordFromIntent$21(intent);
                if (Utils.isInvisibleMode()) {
                    Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.stealth_mode_tip), 0).show();
                } else {
                    Toast.makeText(NoteApp.getInstance(), R.string.toast_refuse_to_use_record_audio, 0).show();
                }
            }
            if (BaseNormalNoteEditFragment.this.mEditToolbarController != null) {
                BaseNormalNoteEditFragment.this.mEditToolbarController.removeShowSoftInputTask();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActivityResult$0(Intent intent) {
            BaseNormalNoteEditFragment.this.lambda$checkPermissionAndStartRecordFromIntent$21(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBaseResultLauncher$22(List list) {
        if (list != null && !list.isEmpty()) {
            Uri[] uriArr = new Uri[list.size()];
            list.toArray(uriArr);
            insertImages(false, uriArr);
            return;
        }
        Logger.INSTANCE.d(TAG, "mNewGalleryResult , images is null ");
    }

    /* JADX INFO: renamed from: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$26, reason: invalid class name */
    class AnonymousClass26 implements ActivityResultCallback<Map<String, Boolean>> {
        AnonymousClass26() {
        }

        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(final Map<String, Boolean> map) {
            Utils.isCtaOnWin = false;
            if (BaseNormalNoteEditFragment.this.mHandler != null) {
                BaseNormalNoteEditFragment.this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$26$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onActivityResult$0(map);
                    }
                }, 100L);
            } else {
                BaseNormalNoteEditFragment.this.onAudioPermissionResult(Boolean.TRUE.equals(map.get("android.permission.RECORD_AUDIO")));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActivityResult$0(Map map) {
            Logger.INSTANCE.d(BaseNormalNoteEditFragment.TAG, "onAudioPermissionResult delayed 100ms");
            BaseNormalNoteEditFragment.this.onAudioPermissionResult(Boolean.TRUE.equals(map.get("android.permission.RECORD_AUDIO")));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBaseResultLauncher$23(Map map) {
        if (this.mWorkingNote == null || !Boolean.TRUE.equals(map.get("android.permission.READ_CONTACTS"))) {
            return;
        }
        if (TextUtils.isEmpty(this.mWorkingNote.getTitle())) {
            this.mWorkingNote.setTitle(this.mContactName);
        }
        setWorkingNote(this.mWorkingNote, true);
        asyncSaveNote();
        if (this.mWorkingNote.getContent() == null || this.mWorkingNote.getContent().isEmpty()) {
            Logger.INSTANCE.d(TAG, "RequestOnEnterCallNote-result setIsEditNotes true");
            this.mIsEditNotes = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBaseResultLauncher$24(ActivityResult activityResult) {
        if (activityResult.getResultCode() == 2308) {
            PreferenceUtils.setKoreaPermissionInfoShowed(NoteApp.getInstance(), true);
            showPostNotificationsPermission();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isNoteInMovingNotes(long[] jArr, long j) {
        if (jArr == null) {
            return false;
        }
        for (long j2 : jArr) {
            if (j2 == j) {
                return true;
            }
        }
        return false;
    }

    private void showPostNotificationsPermission() {
        if (PreferenceUtils.getPostNotificationsPermissionInfoShowed(getContext())) {
            return;
        }
        PermissionUtils.showNotificationPermission(getAppCompatActivity());
        PreferenceUtils.setPostNotificationsPermissionInfoShowed(getContext(), true);
    }

    protected void setmSyncAccountResult(ActivityResult activityResult) {
        this.mLoadTask = null;
        deleteCurrentNote();
    }

    public boolean isSupportAIRecorder() {
        return AudioUtils.isSupportAIRecorder();
    }

    protected void clickAudionBtnAboveOS2() {
        if (!isAdded()) {
            Logger.INSTANCE.e(TAG, "fragment is not added ,abort recording!!");
            return;
        }
        if (isSupportAIRecorder()) {
            if (Utils.isRecordIsInstall()) {
                if (Utils.isNeedUpdateRecord()) {
                    showUpdateRecordDialog();
                    return;
                }
                if (isCallOrVoip()) {
                    Logger.INSTANCE.d(TAG, "clickAudionBtnAboveOS2: MODE_IN_CALL OR MODE_IN_COMMUNICATION");
                    Toast.makeText(NoteApp.getInstance(), getString(R.string.toast_refuse_to_record_when_in_call_new), 0).show();
                    return;
                } else if (isOtherAppRecording()) {
                    Logger.INSTANCE.d(TAG, "clickAudionBtnAboveOS2: MIC_IN_USE");
                    Toast.makeText(NoteApp.getInstance(), getString(R.string.toast_refuse_to_record_when_mic_in_use), 0).show();
                    return;
                } else {
                    onClickAudioBtnAboveOS2();
                    return;
                }
            }
            showInstallRecordDialog();
            return;
        }
        onClickAudioBtnUnderOS2();
    }

    private boolean isCallOrVoip() {
        AudioManager audioManager = (AudioManager) NoteApp.getInstance().getSystemService(AITextWidgetStat.TYPE_AUDIO);
        if (audioManager == null) {
            return false;
        }
        int mode = audioManager.getMode();
        return mode == 2 || mode == 3;
    }

    private boolean isOtherAppRecording() {
        AudioManager audioManager = (AudioManager) NoteApp.getInstance().getSystemService(AITextWidgetStat.TYPE_AUDIO);
        if (audioManager == null) {
            return false;
        }
        return !audioManager.getActiveRecordingConfigurations().isEmpty();
    }

    public void onClickAudioBtnUnderOS2() {
        Logger.INSTANCE.d(TAG, "onClickAudioBtnUnderOS2 ");
    }

    public void onClickAudioBtnAboveOS2() {
        Logger.INSTANCE.d(TAG, "onClickAudioBtnAboveOS2 ");
    }

    public void showInstallRecordDialog() {
        Logger.INSTANCE.d(TAG, "showInstallRecordDialog");
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.audio_record_install_title);
        builder.setMessage(R.string.audio_record_install_content);
        builder.setPositiveButton(R.string.audio_reocrd_install_btn_positive, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showInstallRecordDialog$25(dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.audio_reocrd_install_btn_negative, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda18
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseNormalNoteEditFragment.lambda$showInstallRecordDialog$26(dialogInterface, i);
            }
        });
        this.mUpdateOrInstallDialog = builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showInstallRecordDialog$25(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (RomUtils.isInternationalBuild()) {
            intent.setAction(NotesNormalConstants.GLOBAL_DELETE_VIEW);
        } else {
            intent.setData(Uri.parse(NotesNormalConstants.MI_MARKET_CN));
        }
        startActivity(intent);
    }

    public void showUpdateRecordDialog() {
        Logger.INSTANCE.d(TAG, "showUpdateRecordDialog");
        FragmentActivity activity = getActivity();
        if (activity == null) {
            Logger.INSTANCE.d(TAG, "act null,return");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.audio_record_upadte_title);
        builder.setMessage(R.string.audio_record_upadte_content);
        builder.setPositiveButton(R.string.audio_reocrd_update_btn_positive, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda29
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showUpdateRecordDialog$27(dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.audio_reocrd_update_btn_negative, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda30
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseNormalNoteEditFragment.lambda$showUpdateRecordDialog$28(dialogInterface, i);
            }
        });
        this.mUpdateOrInstallDialog = builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUpdateRecordDialog$27(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (RomUtils.isInternationalBuild()) {
            intent.setPackage("com.xiaomi.discover");
            intent.setClassName("com.xiaomi.discover", NotesNormalConstants.GLOBAL_UPDATE_CLASS);
        } else {
            intent.setData(Uri.parse(NotesNormalConstants.MI_MARKET_CN));
        }
        startActivity(intent);
    }

    protected void onClickAudioBtn() {
        Logger.INSTANCE.d(TAG, "onClickAudioBtn ");
        if (!isAdded()) {
            Logger.INSTANCE.e(TAG, "onClickAudioBtn,fragment not attached!! ");
            return;
        }
        if (!PreferenceUtils.isCTAAccepted(getContext()) && PermissionUtils.supportNewPermissionStyle()) {
            Logger.INSTANCE.d(TAG, "show cta dialog ");
            PermissionNewUtils.showCtaDialog(this.toCtaBeforeAudio);
            return;
        }
        if (PermissionNewUtils.isPermissionGranted(getContext(), "android.permission.RECORD_AUDIO")) {
            onAudioPermissionResult(true);
            return;
        }
        if (isAdded()) {
            if ((this instanceof PhoneHybridNoteEditFragment) && getContext() != null && getContext().getResources() != null && UIUtils.isFreeFormScreen(getContext().getResources().getConfiguration()) && getView() != null) {
                Handler handler = this.mHandler;
                if (handler != null) {
                    handler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onClickAudioBtn$29();
                        }
                    }, 300L);
                    return;
                }
                return;
            }
            Utils.isCtaOnWin = true;
            Logger.INSTANCE.d(TAG, "request requestOnAudioNote");
            PermissionNewUtils.requestOnAudioNote(this.requestAudioPermission);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClickAudioBtn$29() {
        Utils.isCtaOnWin = true;
        Logger.INSTANCE.d(TAG, "request mirco permission ");
        PermissionNewUtils.requestOnAudioNote(this.requestAudioPermission);
    }

    protected void onAudioPermissionResult(boolean z) {
        Logger.INSTANCE.d(TAG, "onAudioPermissionResult " + z);
        if (Utils.isInvisibleMode()) {
            Logger.INSTANCE.e(TAG, "InvisibleMode！！ ");
            Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.stealth_mode_tip), 0).show();
            return;
        }
        if (z) {
            if (!UIUtils.isFoldInternalScreen(getContext()) && getActivity() != null && CompatUtils.invokeIsInMultiWindowMode(getActivity())) {
                Utils.hideSoftInput(getActivity());
                Logger.INSTANCE.e(TAG, "folder device or float window not supported！！ ");
                Toast.makeText(NoteApp.getInstance(), R.string.toast_refuse_to_record_when_in_multi_window_new, 0).show();
                return;
            } else {
                if (RomUtils.isPadDevice() && RomUtils.isInternationalBuild()) {
                    Logger.INSTANCE.d(TAG, "pad showEditToolBar ");
                    this.mEditToolbarController.show(false, false, false);
                }
                requestXiaoAiPermission();
                return;
            }
        }
        Logger.INSTANCE.d(TAG, "no micro permission!! ");
        Toast.makeText(NoteApp.getInstance(), R.string.toast_refuse_to_use_record_audio, 0).show();
    }

    protected void smallWindowAdapter(View view) {
        if (view != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.31
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view2);
                    if (rootWindowInsets != null) {
                        final Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                        BaseNormalNoteEditFragment.this.mInsetBottom = insetsIgnoringVisibility.bottom;
                        final Rect rect = new Rect();
                        if (BaseNormalNoteEditFragment.this.getView() != null) {
                            BaseNormalNoteEditFragment.this.getView().post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.31.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (BaseNormalNoteEditFragment.this.mSharePopupWindow != null) {
                                        BaseNormalNoteEditFragment.this.mSharePopupWindow.updatePaddingBottom(insetsIgnoringVisibility.bottom);
                                        BaseNormalNoteEditFragment.this.getView().getLocalVisibleRect(rect);
                                        BaseNormalNoteEditFragment.this.mSharePopupWindow.refresh(rect);
                                    }
                                }
                            });
                        }
                    }
                    return windowInsetsCompat;
                }
            });
        }
    }

    protected boolean isTapShare(Intent intent) {
        return intent.hasExtra("tap_share_uris");
    }

    protected View getPreviewView() {
        View viewFindViewById;
        if (getView() == null) {
            return null;
        }
        try {
            View rootView = getView().getRootView();
            if (rootView != null && (viewFindViewById = rootView.findViewById(R.id.web_view)) != null && viewFindViewById.getWidth() > 0 && viewFindViewById.getHeight() > 0) {
                return viewFindViewById;
            }
            return null;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error getting preview view: " + e.getMessage());
            return null;
        }
    }
}
