package com.miui.notes.basefeature.mindnote;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.webkit.ConsoleMessage;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.gson.Gson;
import com.miui.common.base.BaseActivity;
import com.miui.common.base.NoteLoadDataBuilder;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.base.SafeBroadcastReceiver;
import com.miui.common.constant.FrameConstant;
import com.miui.common.stat.MindNoteStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DateFormatUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.async.Task;
import com.miui.common.tool.async.TaskManager;
import com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.AigcPicMetaData;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.detail.BaseNoteDetailFragment;
import com.miui.notes.feature.folder.ChooserFolderDialogActivity;
import com.miui.notes.home.PrivateNotesListActivity;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.model.MindDataHelper;
import com.miui.notes.model.MindEntity;
import com.miui.notes.model.NoteModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.page.viewmodel.NoteOperationViewModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.DataUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.TaskManagerUtils;
import com.miui.notes.tool.TempFileManager;
import com.miui.notes.ui.EventController;
import com.miui.notes.ui.SnsEditActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.notes.ui.fragment.BaseFolderChooser;
import com.miui.notes.widget.NoteWidgetProvider;
import com.miui.notes.widget.NoteWidgetProvider_2x2;
import com.miui.notes.widget.NoteWidgetProvider_4x2;
import com.miui.notes.widget.NoteWidgetProvider_4x4;
import com.miui.notes.widget.NoteWidgetProvider_8x4;
import com.miui.richeditor.share.render.Render;
import com.miui.richeditor.util.ResourceParser;
import com.miui.todo.data.Todo;
import com.xiaomi.verificationsdk.internal.Constants;
import defpackage.R2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import miuix.android.content.SystemIntent;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;
import miuix.internal.util.ViewUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseWebMindNoteFragment extends BaseNoteDetailFragment implements WorkingNote.NoteSettingChangedListener {
    private static final String ACTION_CLOSE_AI_Floating_WINDOW = "com.miui.note.action.close_ai_floating_window";
    private static final int FIX_SAVE_DURATION = 5000;
    protected static final int MSG_CODE_CREATE_NEW_NOTE = 0;
    protected static final int MSG_CODE_REFRESH_ALARM_TIME = 3;
    protected static final int MSG_CODE_SHARE_MIND = 4;
    protected static final int MSG_CODE_SYNC_SAVE_NOTE = 1;
    protected static final int MSG_CODE_SYNC_SAVE_NOTE_AND_SHUTDOWN = 2;
    private static final String TAG = "BaseWebMindNoteFragment";
    protected AccessibilityManager accessibilityManager;
    protected BaseMindNoteBridgeInterface mBridgeInterface;
    private ProgressDialog mDialog;
    protected BaseFolderChooser mDialogFolderChooser;
    private KeyboardHelper mEditKeyboardHelper;
    public String mFirstThumbnailBase64;
    protected Handler mHandler;
    protected boolean mInFreeForm;
    protected boolean mIsExited;
    protected boolean mIsFromPrivate;
    protected NoteLoadTask mLoadTask;
    protected PasswordController mPasswordController;
    protected Intent mPendingHandleIntent;
    private Render mRender;
    private ViewUtils.RelativePadding mRootViewInitPadding;
    private SaveToGalleryTask mSaveToGalleryTask;
    private LaunchSendActivityTask mShareGenerateTask;
    public String mSharedBase64;
    public String mThumbnailBase64;
    protected String mTitle;
    protected ActivityResultLauncher<Intent> mToShareResult;
    protected AccessibilityManager.TouchExplorationStateChangeListener mTouchExplorationStateChangeListener;
    protected WebView mWebView;
    protected NoteOperationViewModel noteOperationViewModel;
    protected final String KEY_EDIT_MODE = "key_edit_mode";
    protected boolean mIsPagePrepare = false;
    protected boolean mIsDataPrepare = false;
    protected boolean mWorkingNoteIsEmpty = true;
    protected boolean mIsFromPc = false;
    protected NoteContentObserver mObserver = new NoteContentObserver(this);
    protected boolean mIsKeyboardVisible = false;
    protected int mLastViewPaddingBottom = 0;
    private final Gson mGson = new Gson();
    private boolean mIsRebuildRestoringPrivateNote = false;
    protected Runnable mPendingEditIntentTask = null;
    private ExecutorService mExecutorService = Utils.newDiscardPolicySingleThreadExecutor();
    protected String mLastThumbnailSha1Digest = null;
    protected int mEditMode = 1;
    protected int mMindContentType = 0;
    protected int mCurPageContentType = 0;
    public String mMindNoteDataJson = "";
    protected LocalBroadcastManager mLocalBroadcastManager = null;
    protected int mScreenType = 0;
    private int mUiMode = 16;
    public boolean mIsFirstLoad = true;
    private String mLastCurrentPage = "";
    protected int mNavigationBarHeight = 0;
    protected boolean mIsSendingToDesktop = false;
    private AlertDialog mDualScreenEditTipDialog = null;
    private boolean onEditMode = false;
    protected boolean mIsInSearchMode = false;
    boolean mNeedConfirmPass = false;
    private int mWindowInitFlags = 0;
    private boolean isTouchExplorationStateChanged = false;
    protected PasswordController.OnSetPasswordListener mOnSetPasswordListener = new PasswordController.SimpleOnSetPasswordListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.1
        @Override // com.miui.notes.controller.PasswordController.SimpleOnSetPasswordListener, com.miui.notes.controller.PasswordController.OnSetPasswordListener
        public void onSuccess(int i) {
            if (i == 5102) {
                BaseWebMindNoteFragment.this.hideNote();
            }
        }
    };
    protected PasswordController.LockStateListener mLockStateListener = new PasswordController.SimpleLockStateListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.2
        @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
        public void onLockStateChanged(PasswordController passwordController, int i) {
            super.onLockStateChanged(passwordController, i);
            if (BaseWebMindNoteFragment.this.mWebView != null) {
                if (i == 0) {
                    BaseWebMindNoteFragment.this.mWebView.setVisibility(4);
                } else if (i == 2) {
                    BaseWebMindNoteFragment.this.mWebView.setVisibility(0);
                }
            }
        }

        @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
        public boolean shouldAutoLock() {
            return BaseWebMindNoteFragment.this.shouldConfirmPassword();
        }
    };
    public MyDualScreenEditReceiver mDualScreenEditReceiver = new MyDualScreenEditReceiver(this);

    protected abstract BaseMindNoteBridgeInterface createBridgeInterFace();

    protected abstract int getLayoutId();

    protected long getSaveDataDuration() {
        return 5000L;
    }

    public int getUiMode() {
        return 0;
    }

    protected abstract void initPasswordController();

    protected abstract void initView(View view);

    protected boolean isEnableSaveEmptyData() {
        return false;
    }

    public boolean isMobile() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isWorkingNoteIdInvalid(long j) {
        return j <= 0 && j != -2;
    }

    @Override // com.miui.notes.model.WorkingNote.NoteSettingChangedListener
    public void onClockAlertChanged(long j, long j2) {
    }

    @Override // com.miui.notes.model.WorkingNote.NoteSettingChangedListener
    public void onNoteThemeChanged() {
    }

    public void onUpdateViewState(boolean z, boolean z2, boolean z3, boolean z4, String str, String str2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldConfirmPassword() {
        if (this.mWorkingNote == null || !this.mWorkingNote.isHidden()) {
            return false;
        }
        return !this.mIsFromPrivate || this.mNeedConfirmPass;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(getLayoutId(), viewGroup, false);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mHandler = new WebViewBrainHandler();
    }

    public void onNoteMovedSuccess(long j) {
        Logger.INSTANCE.d(TAG, "onNoteMovedSuccess/current working note " + this.mWorkingNote + " has been moved to folder:" + j);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void initBaseResultLauncher() {
        this.toChooseFolderResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda5
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initBaseResultLauncher$0((ActivityResult) obj);
            }
        });
        this.mToShareResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda6
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initBaseResultLauncher$1((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBaseResultLauncher$0(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            Intent data = activityResult.getData();
            if (!isAdded() || data == null) {
                return;
            }
            onNoteMovedSuccess(data.getLongExtra(ChooserFolderDialogActivity.CHOOSER_FOLDER_SELECT_FOLDER_ID, 0L));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBaseResultLauncher$1(ActivityResult activityResult) {
        ((HomeViewModel) Objects.requireNonNull(this.homeViewModel)).toDetailResultCallback();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        Logger.INSTANCE.d(TAG, "onStop");
        super.onStop();
        if (this.mPasswordController != null && !requireActivity().isChangingConfigurations()) {
            this.mPasswordController.lockDelay();
        }
        saveNoteInstantlyIfNeeded();
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
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        this.mRootViewInitPadding = new ViewUtils.RelativePadding(ViewCompat.getPaddingStart(this.rootView), this.rootView.getPaddingTop(), ViewCompat.getPaddingEnd(this.rootView), this.rootView.getPaddingBottom());
        this.mInFreeForm = UIUtils.isFreeFormScreen(getResources().getConfiguration());
        this.noteOperationViewModel = (NoteOperationViewModel) new ViewModelProvider(requireActivity()).get(NoteOperationViewModel.class);
        this.mWindowInitFlags = requireActivity().getWindow().getAttributes().flags;
        initView(getView());
        initWebView();
        initPasswordController();
        initKeyboardDetector();
        initDualScreenModule();
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        if (this.mWorkingNote != null) {
            if (this.mWorkingNote.getNoteId() == -2 && !this.mWorkingNote.isEmpty()) {
                saveNoteInstantlyIfNeeded();
            }
            this.mWorkingNote.onSaveInstanceState(bundle);
            bundle.putString("user_query", this.mSearchText);
            if (this.mHighLightKeyWords != null) {
                bundle.putStringArrayList("user_ai_query", this.mHighLightKeyWords);
            }
            bundle.putInt("key_edit_mode", this.mEditMode);
        }
    }

    protected boolean handleInstanceState(Bundle bundle) {
        Logger.INSTANCE.d(TAG, "handleInstanceState");
        WorkingNote workingNoteLoadNote = WorkingNote.loadNote(bundle);
        if (workingNoteLoadNote == null) {
            return false;
        }
        this.mSearchText = bundle.getString("user_query");
        this.mHighLightKeyWords = bundle.getStringArrayList("user_ai_query");
        this.mEditMode = bundle.getInt("key_edit_mode");
        NoteLoadDataEntity noteLoadDataEntityCreate = NoteLoadDataBuilder.create(workingNoteLoadNote);
        if (TextUtils.isEmpty(workingNoteLoadNote.getWorkingMindContent())) {
            this.mMindContentType = 1;
            noteLoadDataEntityCreate.setMindContentType(1);
        } else {
            this.mMindContentType = noteLoadDataEntityCreate.getMindContentType();
        }
        noteLoadDataEntityCreate.setMindEditMode(this.mEditMode);
        if (!TextUtils.isEmpty(this.mSearchText) || (this.mHighLightKeyWords != null && !this.mHighLightKeyWords.isEmpty())) {
            noteLoadDataEntityCreate.setSearchText(this.mSearchText);
            noteLoadDataEntityCreate.setHighlightKeywords(this.mHighLightKeyWords);
        }
        refreshNote(noteLoadDataEntityCreate, false);
        return true;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mPasswordController != null && !this.mIsFromRebuild) {
            this.mPasswordController.unlock();
        }
        if (this.mIsFromRebuild) {
            this.mIsRebuildRestoringPrivateNote = true;
        }
        this.mIsFromRebuild = false;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        closeAiFloatingWindow();
        if (this.accessibilityManager != null && this.mTouchExplorationStateChangeListener != null) {
            Logger.INSTANCE.d(TAG, "onDestroy remove TouchExplorationStateChangeListener");
            try {
                this.accessibilityManager.removeTouchExplorationStateChangeListener(this.mTouchExplorationStateChangeListener);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "Failed to remove touch exploration listener" + e);
            }
            this.mTouchExplorationStateChangeListener = null;
        }
        KeyboardHelper keyboardHelper = this.mEditKeyboardHelper;
        if (keyboardHelper != null) {
            keyboardHelper.release();
        }
        WebView webView = this.mWebView;
        if (webView != null) {
            webView.stopLoading();
            this.mWebView.clearHistory();
            this.mWebView.getSettings().setJavaScriptEnabled(false);
            this.mWebView.removeAllViewsInLayout();
            this.mWebView.removeAllViews();
            this.mWebView.setWebChromeClient(null);
            this.mWebView.setWebViewClient(null);
            this.mWebView.clearCache(true);
            this.mWebView.destroy();
        }
        if (this.mWorkingNote != null) {
            this.mWorkingNote.removeOnSettingStatusChangedListener(this);
        }
        unregisterObserver();
        try {
            if (getActivity() != null) {
                getActivity().unregisterReceiver(this.mDualScreenEditReceiver);
            }
            if (MindNoteStat.isCreateMindNote && this.mWorkingNote != null && !this.mWorkingNote.isEmpty()) {
                MindNoteStat.reportCreateEvent(MindNoteStat.MIND_NOTE_CREATE_EFFECTIVE_CLICK, null);
            }
            MindNoteStat.isCreateMindNote = false;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void closeAiFloatingWindow() {
        try {
            Intent intent = new Intent(ACTION_CLOSE_AI_Floating_WINDOW);
            if (getContext() != null) {
                getContext().sendBroadcast(intent);
                Logger.INSTANCE.d(TAG, "closeAiTextWindow: broadcast sent");
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "closeAiTextWindow error: " + e);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mIsPagePrepare = false;
        this.mIsDataPrepare = false;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        BaseFolderChooser baseFolderChooser = this.mDialogFolderChooser;
        if (baseFolderChooser != null) {
            baseFolderChooser.cancel();
        }
        this.mInFreeForm = UIUtils.isFreeFormScreen(getResources().getConfiguration());
        if (this.mUiMode != configuration.uiMode) {
            this.mUiMode = configuration.uiMode;
            String str = DisplayUtils.isNightMode() ? "dark" : Constants.LIGHT;
            WebView webView = this.mWebView;
            if (webView != null) {
                webView.loadUrl("javascript:getThemeModeFromAndroid('" + str + "')");
            }
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void updateWorkingNoteFromOuter() {
        if (this.mWorkingNote != null) {
            this.mWorkingNote = WorkingNote.loadNote("_id=" + this.mWorkingNote.getNoteId(), null);
            Logger.INSTANCE.d(TAG, "updateWorkingNoteFromOuter/ set workingNote= " + this.mWorkingNote.getNoteId());
        } else {
            Logger.INSTANCE.d(TAG, "updateWorkingNoteFromOuter/working note null");
        }
    }

    protected void initWebView() {
        Logger.INSTANCE.d(TAG, "initWebView");
        WebView webView = this.mWebView;
        if (webView == null) {
            throw new NullPointerException("mWebView shouldn't be null! please check layout and view Id");
        }
        webView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setTextZoom(100);
        this.mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebView.getSettings().setAllowContentAccess(false);
        WebView.setWebContentsDebuggingEnabled(false);
        this.mWebView.setWebChromeClient(new WebChromeClient() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.3
            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Logger.INSTANCE.i(BaseWebMindNoteFragment.TAG, "MindOnConsoleMessage: " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.4
            @Override // android.webkit.WebViewClient
            public boolean onRenderProcessGone(WebView webView2, RenderProcessGoneDetail renderProcessGoneDetail) {
                Logger.INSTANCE.e(BaseWebMindNoteFragment.TAG, "onRenderProcessGone: " + renderProcessGoneDetail.didCrash());
                if (webView2 != BaseWebMindNoteFragment.this.mWebView || BaseWebMindNoteFragment.this.getActivity() == null) {
                    return true;
                }
                BaseWebMindNoteFragment.this.getActivity().finish();
                return true;
            }
        });
        BaseMindNoteBridgeInterface baseMindNoteBridgeInterfaceCreateBridgeInterFace = createBridgeInterFace();
        this.mBridgeInterface = baseMindNoteBridgeInterfaceCreateBridgeInterFace;
        this.mWebView.addJavascriptInterface(baseMindNoteBridgeInterfaceCreateBridgeInterFace, "note_interface");
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mWebView.getContext().getSystemService("accessibility");
        this.accessibilityManager = accessibilityManager;
        if (accessibilityManager != null) {
            Logger.INSTANCE.d(TAG, "init onTouchExplorationStateChanged");
            AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda8
                @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                public final void onTouchExplorationStateChanged(boolean z) {
                    this.f$0.lambda$initWebView$2(z);
                }
            };
            this.mTouchExplorationStateChangeListener = touchExplorationStateChangeListener;
            try {
                this.accessibilityManager.addTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "Failed to add touch exploration listener" + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initWebView$2(boolean z) {
        Logger.INSTANCE.d(TAG, "onTouchExplorationStateChanged reload mind");
        if (this.mWebView != null) {
            saveNoteInstantlyIfNeeded();
            Intent intent = this.mPendingHandleIntent;
            if (intent != null && (("android.intent.action.EDIT".equals(intent.getAction()) || NoteIntent.ACTION_MIND.equals(this.mPendingHandleIntent.getAction())) && this.mWorkingNote != null && this.mWorkingNote.getNoteId() > 0)) {
                Logger.INSTANCE.d(TAG, "onTouchExplorationStateChanged update pendingHandleIntent action");
                this.mPendingHandleIntent.setAction("android.intent.action.VIEW");
                this.mPendingHandleIntent.putExtra("android.intent.extra.UID", this.mWorkingNote.getNoteId());
            }
            updateWebContentType(this.mMindContentType);
            this.isTouchExplorationStateChanged = true;
        }
    }

    protected void initKeyboardDetector() {
        KeyboardHelper keyboardHelper = new KeyboardHelper(this.rootView, getActivity().getWindow().getDecorView(), new KeyboardPaddingProcessor() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.5
            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor, com.miui.common.tool.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChanged(View view, WindowInsets windowInsets, int i, int i2) {
                super.onKeyboardHeightChanged(view, windowInsets, i, i2);
            }

            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor, com.miui.common.tool.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChangedEnd(View view, WindowInsets windowInsets, int i) {
                super.onKeyboardHeightChangedEnd(view, windowInsets, i);
                if (BaseWebMindNoteFragment.this.getMIsKeyboardVisible()) {
                    return;
                }
                super.updateViewPaddingBottom(view, windowInsets, 0);
            }

            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor
            protected void updateViewPaddingBottom(View view, WindowInsets windowInsets, int i) {
                if (BaseWebMindNoteFragment.this.isAdded()) {
                    FragmentActivity activity = BaseWebMindNoteFragment.this.getActivity();
                    BaseWebMindNoteFragment.this.onKeyboardVisibleChanged(KeyboardHelper.INSTANCE.getShowKeyboard());
                    if (activity != null && BaseWebMindNoteFragment.this.mInFreeForm) {
                        i = 0;
                    }
                    BaseWebMindNoteFragment.this.updatePaddingBottom(i);
                    super.updateViewPaddingBottom(view, windowInsets, i);
                }
            }
        });
        this.mEditKeyboardHelper = keyboardHelper;
        keyboardHelper.setForceMode(false);
    }

    protected void updateWebContentType(int i) {
        if (isAdded()) {
            this.mCurPageContentType = i;
            Logger.INSTANCE.i(TAG, "updateWebContentType:" + i);
            Context context = getContext();
            int statusBarHeight = (int) (UIUtils.getStatusBarHeight(context) / context.getResources().getDisplayMetrics().density);
            String str = "values-" + Locale.getDefault().getLanguage() + "-r" + Locale.getDefault().getCountry();
            AccessibilityManager accessibilityManager = this.accessibilityManager;
            boolean zIsTouchExplorationEnabled = accessibilityManager != null ? accessibilityManager.isTouchExplorationEnabled() : false;
            Logger.INSTANCE.d(TAG, "updateWebContentType touchExplorationEnabled = " + zIsTouchExplorationEnabled);
            if (i == 0) {
                this.mWebView.loadUrl("file:///android_asset/mindbuild/index.html" + String.format("?statusBarHeight=%s&language=%s&touchExplorationEnabled=%s", this.mGson.toJson(Integer.valueOf(statusBarHeight)), str, Boolean.valueOf(zIsTouchExplorationEnabled)));
                return;
            }
            String str2 = String.format("?statusBarHeight=%s&language=%s&timestamp=%s&touchExplorationEnabled=%s", this.mGson.toJson(Integer.valueOf(statusBarHeight)), str, Long.valueOf(System.currentTimeMillis()), Boolean.valueOf(zIsTouchExplorationEnabled));
            this.mWebView.loadUrl("javascript:setDataInvisible()");
            this.mWebView.loadUrl("file:///android_asset/mindbuild/index.html" + str2 + "#/overview");
        }
    }

    protected void initDualScreenModule() {
        this.mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_REQUEST);
        intentFilter.addAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_CHANGED);
        intentFilter.addAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_CONTENT_SYNC);
        if (Utils.isAboveSDK34()) {
            getActivity().registerReceiver(this.mDualScreenEditReceiver, intentFilter, 2);
        } else {
            getActivity().registerReceiver(this.mDualScreenEditReceiver, intentFilter);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(NoteLoadDataEntity noteLoadDataEntity, boolean z) {
        Logger.INSTANCE.d(TAG, "refreshNote ,isFromPc:" + z + ",mIsDataPrepare:" + this.mIsDataPrepare);
        if (noteLoadDataEntity == null) {
            Logger.INSTANCE.i(TAG, "refreshNote/noteEntity is null!!!");
            return;
        }
        if (noteLoadDataEntity.getSearchText() == null && !TextUtils.isEmpty(this.mSearchText)) {
            noteLoadDataEntity.setSearchText(this.mSearchText);
        }
        if (this.mHighLightKeyWords != null) {
            noteLoadDataEntity.setHighlightKeywords(this.mHighLightKeyWords);
        }
        this.mIsFromPc = z;
        try {
            setArguments(noteLoadDataEntity.toBundle());
            if (loadNoteFromArguments()) {
                if (this.mIsDataPrepare) {
                    onDataPrepare();
                } else {
                    Logger.INSTANCE.e(TAG, "refreshNote/mIsDataPrepare, not prepared");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean loadNoteFromArguments() {
        if (this.mExecutorService.isShutdown()) {
            this.mExecutorService = Utils.newDiscardPolicySingleThreadExecutor();
        }
        long j = getArguments().getLong("data_id", -1L);
        this.mNoteId = j;
        Logger.INSTANCE.d(TAG, "loadNoteFromArguments,set mNoteId:" + this.mNoteId);
        this.mFolderId = getArguments().getLong("folder_id", 0L);
        boolean z = true;
        this.mEditMode = getArguments().getInt(NoteLoadDataEntity.MIND_EDIT_MODE, 1);
        this.mMindContentType = getArguments().getInt("mind_content_type", 1);
        this.mTitle = getArguments().getString("note_title");
        this.mSearchText = getArguments().getString(NoteLoadDataEntity.SEARCH_TEXT);
        this.mHighLightKeyWords = getArguments().getStringArrayList(NoteLoadDataEntity.HIGHLIGHT_KEYWORDS);
        Logger.INSTANCE.d(TAG, "mNoteId:" + this.mNoteId + " mEditMode = " + this.mEditMode + " mMindContentType = " + this.mMindContentType);
        Intent intent = new Intent();
        intent.putExtra(NoteIntent.KEY_EXTRA_LOAD_DATA, getArguments());
        if (j == -2) {
            intent.setAction("android.intent.action.EDIT");
            intent.putExtra("android.intent.extra.UID", j);
            intent.putExtra("com.miui.notes.folder_id", this.mFolderId);
        } else {
            intent.setAction("android.intent.action.VIEW");
            intent.putExtra("android.intent.extra.UID", j);
            intent.putExtra("intent_extra_data_key", this.mSearchText);
            if (j <= 0) {
                z = false;
            }
        }
        this.mPendingHandleIntent = intent;
        preHandleIntent(intent);
        return z;
    }

    private void processLastWorkingNote(long j) {
        if (this.mWorkingNote == null || this.mWorkingNote.getNoteId() == j) {
            return;
        }
        Logger.INSTANCE.d(TAG, "processLastWorkingNote " + j);
        if (this.mWorkingNote.isEmpty()) {
            NoteStore.delete(getContext(), new long[]{this.mWorkingNote.getNoteId()});
            return;
        }
        saveNoteInstantlyIfNeeded();
        if (j == -1 || !MindNoteStat.isCreateMindNote) {
            return;
        }
        MindNoteStat.reportCreateEvent(MindNoteStat.MIND_NOTE_CREATE_EFFECTIVE_CLICK, null);
        MindNoteStat.isCreateMindNote = false;
    }

    protected void hideNote() {
        this.mWorkingNote.setFolderId(-4L);
        if (RomUtils.isPadMode()) {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_hidden_pad, 0).show();
        } else {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_hidden_edit_new, 0).show();
        }
        Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_hidden_edit_new, 0).show();
        asyncSaveNote(this.mMindNoteDataJson);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNoteFromDualScreen() {
        long noteId = this.mWorkingNote.getNoteId();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.putExtra("android.intent.extra.UID", noteId);
        EventController.getInstance().sendEvent(4, Long.valueOf(noteId));
        ViewNoteLoadTask viewNoteLoadTask = new ViewNoteLoadTask(intent, this.mWorkingNote.getNoteId());
        this.mLoadTask = viewNoteLoadTask;
        viewNoteLoadTask.commit();
    }

    protected void notifyEditUiState(int i) {
        if (this.mLocalBroadcastManager == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_CHANGED);
        intent.putExtra(FrameConstant.PARAM_SCREEN_ID, 0);
        intent.putExtra(FrameConstant.PARAM_SCREEN_MODE, i);
        if (getActivity() != null) {
            getActivity().sendBroadcast(intent);
        }
    }

    private boolean isInEditAction() {
        return this.onEditMode;
    }

    protected void responseEditUiState() {
        if (isInEditAction()) {
            notifyEditUiState(1);
        } else {
            notifyEditUiState(0);
        }
    }

    protected void showDualScreenEditTipDialog() {
        if (isAdded()) {
            AlertDialog alertDialog = this.mDualScreenEditTipDialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle(getString(R.string.note_dual_screen_dialog_title));
                builder.setMessage(getString(R.string.note_dual_screen_dialog_content));
                builder.setNegativeButton(getString(R.string.dialog_button_continue_work), new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.6
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setPositiveButton(getString(R.string.dialog_button_close), new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.7
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BaseWebMindNoteFragment.this.getActivity().finish();
                    }
                });
                AlertDialog alertDialogCreate = builder.create();
                this.mDualScreenEditTipDialog = alertDialogCreate;
                alertDialogCreate.show();
            }
        }
    }

    public boolean isLandScape() {
        return this.mScreenType == 2;
    }

    public void preHandleIntent(Intent intent) {
        Logger.INSTANCE.d(TAG, "preHandleIntent");
        if (intent == null) {
            Logger.INSTANCE.e(TAG, "preHandleIntent,intent null abort!!!");
            return;
        }
        NoteLoadTask noteLoadTask = this.mLoadTask;
        if (noteLoadTask != null) {
            noteLoadTask.cancel();
            this.mLoadTask = null;
        }
        Bundle bundle = (Bundle) intent.getParcelableExtra(NoteIntent.KEY_EXTRA_LOAD_DATA);
        if (bundle != null) {
            this.mEditMode = bundle.getInt(NoteLoadDataEntity.MIND_EDIT_MODE);
            this.mMindContentType = bundle.getInt("mind_content_type");
            this.mTitle = bundle.getString("note_title");
            long j = bundle.getLong("data_id");
            this.mWebView.loadUrl("javascript:clearModifiedFlag()");
            processLastWorkingNote(j);
            if (!this.mIsDataPrepare || !this.mIsPagePrepare || this.mCurPageContentType != this.mMindContentType) {
                Logger.INSTANCE.i(TAG, "reload mind");
                updateWebContentType(this.mMindContentType);
                this.mIsDataPrepare = false;
                this.mIsPagePrepare = false;
            } else {
                Logger.INSTANCE.i(TAG, "don't reload mind");
            }
            if (getFragmentManager() == null || !isStateSaved()) {
                setArguments(bundle);
            }
        }
        this.mWorkingNote = null;
    }

    public void handleIntentData(Intent intent) {
        Logger.INSTANCE.d(TAG, "handleIntentData");
        String action = intent.getAction();
        Logger.INSTANCE.d(TAG, "handleIntentData action = " + action);
        if ("android.intent.action.VIEW".equals(action)) {
            handleViewIntent(intent);
            return;
        }
        if ("android.intent.action.EDIT".equals(action) || NoteIntent.ACTION_MIND.equals(action)) {
            handleEditIntent(intent);
        } else if (NoteIntent.ACTION_DELETE.equals(action)) {
            handleDeleteIntent(intent, getActivity());
        }
    }

    protected boolean handleViewIntent(Intent intent) {
        Logger.INSTANCE.d(TAG, "handleViewIntent");
        long extraNoteId = getExtraNoteId(intent);
        if (extraNoteId <= 0) {
            Logger.INSTANCE.w(TAG, "Miss note id when parse intent");
            return false;
        }
        ViewNoteLoadTask viewNoteLoadTask = new ViewNoteLoadTask(intent, extraNoteId);
        this.mLoadTask = viewNoteLoadTask;
        viewNoteLoadTask.commit();
        return true;
    }

    protected void handleEditIntent(final Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra(NoteIntent.KEY_IS_PENDING, false);
        Runnable runnable = new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.8
            @Override // java.lang.Runnable
            public void run() {
                BaseWebMindNoteFragment baseWebMindNoteFragment = BaseWebMindNoteFragment.this;
                BaseWebMindNoteFragment.this.mLoadTask = baseWebMindNoteFragment.new EditNoteLoadTask(intent, baseWebMindNoteFragment.getActivity());
                BaseWebMindNoteFragment.this.mLoadTask.commit();
            }
        };
        this.mPendingEditIntentTask = runnable;
        if (booleanExtra) {
            return;
        }
        runnable.run();
        this.mPendingEditIntentTask = null;
    }

    protected boolean handleDeleteIntent(Intent intent, Context context) {
        NoteDeleteTask noteDeleteTask = new NoteDeleteTask(intent, context);
        this.mLoadTask = noteDeleteTask;
        noteDeleteTask.commit();
        return true;
    }

    private long getExtraNoteId(Intent intent) {
        long longExtra = intent.getLongExtra("android.intent.extra.UID", -1L);
        if (longExtra <= 0) {
            Uri data = intent.getData();
            if (data == null || !"notes".equals(data.getAuthority())) {
                Logger.INSTANCE.e(TAG, "Invalid data uri: " + (data != null ? data.toString() : null));
            } else {
                try {
                    return ContentUris.parseId(data);
                } catch (NumberFormatException e) {
                    Logger.INSTANCE.e(TAG, "Fail to parse note id from uri: " + data + ",excep:" + e);
                } catch (UnsupportedOperationException e2) {
                    Logger.INSTANCE.e(TAG, "Fail to parse note id from uri: " + data + ",excep:" + e2);
                }
            }
        }
        return longExtra;
    }

    public void shareMind(String str) {
        this.mSharedBase64 = str;
        this.mHandler.sendEmptyMessageDelayed(4, 300L);
    }

    public boolean isFirstLoad() {
        return this.mIsFirstLoad;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: doSaveToGallery, reason: merged with bridge method [inline-methods] */
    public void lambda$checkPermissionAndSaveToGallery$9(File file) {
        SaveToGalleryTask saveToGalleryTask = this.mSaveToGalleryTask;
        if (saveToGalleryTask != null) {
            saveToGalleryTask.cancel(true);
            this.mSaveToGalleryTask = null;
        }
        SaveToGalleryTask saveToGalleryTask2 = new SaveToGalleryTask(this);
        this.mSaveToGalleryTask = saveToGalleryTask2;
        saveToGalleryTask2.execute(file);
    }

    public void setThumbnail(String str) {
        Logger.INSTANCE.d(TAG, "  set thumbnail ");
        this.mThumbnailBase64 = str;
        updateWorkingText(this.mMindNoteDataJson);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        Logger.INSTANCE.d(TAG, "onBackPressed");
        BaseFolderChooser baseFolderChooser = this.mDialogFolderChooser;
        if (baseFolderChooser == null) {
            return false;
        }
        baseFolderChooser.cancel();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getExtraUserQuery(Intent intent) {
        if (intent.hasExtra("intent_extra_data_key")) {
            return intent.getStringExtra("intent_extra_data_key");
        }
        return null;
    }

    protected void setWorkingNote(WorkingNote workingNote, boolean z) {
        Logger.INSTANCE.d(TAG, "setWorkingNote mWorkingNote: " + workingNote + ",forceEdit:" + z);
        if (workingNote.getNoteId() == -2 && !workingNote.isEmpty()) {
            this.mHandler.sendEmptyMessage(1);
        }
        this.mWorkingNote = workingNote;
        this.mWorkingNote.addOnSettingStatusChangedListener(this);
        this.mWorkingNote.setWorkingNoteType(NoteModel.NoteType.TYPE_MIND);
        if (isAdded() && this.mPasswordController != null && !this.mIsFromRebuild && !this.mIsRebuildRestoringPrivateNote) {
            this.mPasswordController.lock();
            this.mPasswordController.unlock();
        }
        this.mIsRebuildRestoringPrivateNote = false;
        if (getActivity() != null && isAdded()) {
            if (this.mWorkingNote.isHidden()) {
                getActivity().getWindow().addFlags(8192);
            } else {
                getActivity().getWindow().clearFlags(8192);
            }
        }
    }

    public void onPagePrepare() {
        this.mIsPagePrepare = true;
        Logger.INSTANCE.d(TAG, "onPagePrepare");
    }

    public void onDataPrepare() {
        this.mIsDataPrepare = true;
        Logger.INSTANCE.d(TAG, "onDataPrepare");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onDataPrepare$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDataPrepare$3() {
        handleIntentData(this.mPendingHandleIntent);
    }

    protected void setDataToWebView() {
        String string;
        if (this.mWorkingNote == null) {
            return;
        }
        String workingMindContent = this.mWorkingNote.getWorkingMindContent();
        String str = this.mSearchText;
        if (!TextUtils.isEmpty(workingMindContent)) {
            boolean zIsCorrectFormatContent = MindDataHelper.isCorrectFormatContent(workingMindContent);
            MindEntity mindEntity = (MindEntity) new Gson().fromJson(workingMindContent.substring(15), MindEntity.class);
            String strConvertToCorrectFormatContent = mindEntity.content;
            this.mLastThumbnailSha1Digest = mindEntity.thumbnail;
            if (!zIsCorrectFormatContent) {
                strConvertToCorrectFormatContent = MindDataHelper.convertToCorrectFormatContent(strConvertToCorrectFormatContent);
            }
            this.mMindNoteDataJson = strConvertToCorrectFormatContent;
            try {
                string = new JSONObject(strConvertToCorrectFormatContent).getString("currentPage");
            } catch (JSONException e) {
                e.printStackTrace();
                string = "";
            }
            if (TextUtils.isEmpty(this.mSearchText) && (this.mHighLightKeyWords == null || this.mHighLightKeyWords.isEmpty())) {
                str = "";
            }
            if (!TextUtils.isEmpty(strConvertToCorrectFormatContent)) {
                if (this.mMindContentType == 0) {
                    int[] screenSizeDp = DisplayUtils.getScreenSizeDp(getContext());
                    if (screenSizeDp[0] > screenSizeDp[1]) {
                        setScreenOrientationToWebView(2);
                    } else {
                        setScreenOrientationToWebView(1);
                    }
                } else if (!RomUtils.isPadMode() && getThisActivity() != null && DisplayUtils.isInMultiWindowMode(getThisActivity()) && !UIUtils.isFreeFormScreen(getResources().getConfiguration())) {
                    int[] screenSizeDp2 = DisplayUtils.getScreenSizeDp(getContext());
                    if (screenSizeDp2[0] > screenSizeDp2[1]) {
                        setScreenOrientationToWebView(2);
                    } else {
                        setScreenOrientationToWebView(1);
                    }
                } else {
                    setScreenOrientationToWebView(1);
                }
                setMindContentToWebView(strConvertToCorrectFormatContent, str, this.mMindContentType, this.mHighLightKeyWords);
            } else {
                String strCreateNewMindContentByTitle = MindEntity.createNewMindContentByTitle("");
                this.mMindContentType = 0;
                int[] screenSizeDp3 = DisplayUtils.getScreenSizeDp(getContext());
                if (screenSizeDp3[0] > screenSizeDp3[1]) {
                    setScreenOrientationToWebView(2);
                } else {
                    setScreenOrientationToWebView(1);
                }
                setMindContentToWebView(strCreateNewMindContentByTitle, str, this.mMindContentType, this.mHighLightKeyWords);
            }
            if (!TextUtils.isEmpty(string)) {
                disableLandscape(string);
            }
        } else {
            if (this.mMindContentType != 1 && !this.isTouchExplorationStateChanged) {
                disableLandscape("/overview");
            }
            String title = this.mWorkingNote.getTitle();
            if (title == null) {
                title = "";
            }
            String strCreateNewMindContentByTitle2 = MindEntity.createNewMindContentByTitle(title);
            if (RomUtils.isPadMode() && !this.mIsInSearchMode) {
                str = "";
            }
            setMindContentToWebView(strCreateNewMindContentByTitle2, str, this.mMindContentType, this.mHighLightKeyWords);
        }
        this.isTouchExplorationStateChanged = false;
        updateLanguageToWebView();
        this.mWebView.setAlpha(1.0f);
    }

    public void updateWorkingText(String str) {
        String strOptString;
        String string;
        Logger.INSTANCE.d(TAG, "updateWorkingText");
        String strConvertToOldFormatContent = MindDataHelper.convertToOldFormatContent(str);
        this.mMindNoteDataJson = strConvertToOldFormatContent;
        if (TextUtils.isEmpty(strConvertToOldFormatContent)) {
            return;
        }
        String strReplace = null;
        boolean z = true;
        try {
            JSONObject jSONObject = new JSONObject(str);
            string = jSONObject.getString("currentPage");
            try {
                strOptString = jSONObject.optString("userInput");
                try {
                    JSONObject jSONObject2 = new JSONObject(jSONObject.getString("data"));
                    strReplace = jSONObject2.getString(Todo.LABEL).replace(StringUtils.LF, "");
                    strOptString = strOptString.replace(StringUtils.LF, "");
                    JSONArray jSONArray = jSONObject2.getJSONArray("children");
                    if (!isEnableSaveEmptyData() && jSONArray.length() == 0 && TextUtils.isEmpty(strReplace)) {
                        Logger.INSTANCE.i(TAG, "内容为空不保存");
                        if (this.mWorkingNote != null) {
                            MindEntity mindEntity = new MindEntity(TextUtils.isEmpty(string) || string.equals("/mind"), TextUtils.isEmpty(strReplace) ? "" : strReplace, this.mMindNoteDataJson, "");
                            this.mWorkingNote.setWorkingTitle("");
                            this.mWorkingNote.setWorkingText("");
                            this.mWorkingNote.setWorkingMindContent(mindEntity.toNoteContent());
                            this.mWorkingNote.setWorkingMindPlainText("");
                            this.mWorkingNoteIsEmpty = true;
                            setSelectedMindContentType(mindEntity.toNoteContent());
                            return;
                        }
                    }
                } catch (JSONException e) {
                    e = e;
                    e.printStackTrace();
                }
            } catch (JSONException e2) {
                e = e2;
                strOptString = "";
            }
        } catch (JSONException e3) {
            e = e3;
            strOptString = "";
            string = null;
        }
        Logger.INSTANCE.i(TAG, "(mWorkingNote != null)" + (this.mWorkingNote != null));
        if (this.mWorkingNote != null) {
            if (!TextUtils.isEmpty(string) && !string.equals("/mind")) {
                z = false;
            }
            MindEntity mindEntity2 = new MindEntity(z, TextUtils.isEmpty(strReplace) ? "" : strReplace, this.mMindNoteDataJson, "");
            this.mWorkingNote.setWorkingTitle(strReplace);
            this.mWorkingNote.setWorkingMindPlainText(strOptString);
            this.mWorkingNote.setWorkingMindContent(mindEntity2.toNoteContent());
            setSelectedMindContentType(mindEntity2.toNoteContent());
            this.mWorkingNoteIsEmpty = false;
        }
    }

    private void setSelectedMindContentType(String str) {
        NoteLoadDataEntity selectedNote = this.homeViewModel == null ? null : this.homeViewModel.getSelectedNote();
        if (selectedNote == null || this.mWorkingNote == null || selectedNote.getNoteId() != this.mWorkingNote.getNoteId()) {
            return;
        }
        int contentType = !TextUtils.isEmpty(str) ? ((MindEntity) new Gson().fromJson(str.substring(15), MindEntity.class)).getContentType() : 0;
        this.mCurPageContentType = contentType;
        selectedNote.setMindContentType(contentType);
    }

    public void disableLandscape(String str) {
        if (isAdded()) {
            Logger.INSTANCE.d(TAG, "disableLandscape currentPage = " + str + " mLastCurrentPage = " + this.mLastCurrentPage + " mMindContentType = " + this.mMindContentType);
            if (this.mLastCurrentPage.equals(str)) {
                return;
            }
            if (str.equals("/overview")) {
                getActivity().setRequestedOrientation(1);
                this.mMindContentType = 1;
            } else {
                getActivity().setRequestedOrientation(-1);
                this.mMindContentType = 0;
            }
            this.mLastCurrentPage = str;
        }
    }

    protected void onNoteDeleted(long j) {
        Toast.makeText(getActivity().getApplicationContext(), R.string.toast_notes_delete_success, 0).show();
        getActivity().onBackPressed();
    }

    public abstract class NoteLoadTask extends Task<WorkingNote> {
        Intent intent;
        protected String userQuery;

        protected abstract WorkingNote loadWorkingNote();

        @Override // com.miui.common.tool.async.Task
        public void onPrepare(TaskManager taskManager) {
        }

        protected abstract WorkingNote parseWorkingNote();

        public NoteLoadTask(Intent intent) {
            this.intent = intent;
            this.userQuery = BaseWebMindNoteFragment.this.getExtraUserQuery(intent);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.common.tool.async.Task
        public WorkingNote doLoad() {
            WorkingNote workingNoteLoadWorkingNote = loadWorkingNote();
            if (workingNoteLoadWorkingNote != null && !BaseWebMindNoteFragment.this.isWorkingNoteIdInvalid(workingNoteLoadWorkingNote.getNoteId())) {
                return workingNoteLoadWorkingNote;
            }
            WorkingNote workingNote = parseWorkingNote();
            if (workingNote != null) {
                workingNote.saveNote(true);
            }
            return workingNote;
        }

        @Override // com.miui.common.tool.async.Task
        public void onException(TaskManager taskManager, Exception exc) {
            onResult(taskManager, (WorkingNote) null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, WorkingNote workingNote) {
            if (BaseWebMindNoteFragment.this.mLoadTask != this) {
                return;
            }
            BaseWebMindNoteFragment.this.mLoadTask = null;
            updateUI(workingNote);
        }

        protected void updateUI(WorkingNote workingNote) {
            if (workingNote == null || BaseWebMindNoteFragment.this.isWorkingNoteIdInvalid(workingNote.getNoteId()) || (workingNote.getFolderId() == -3 && !BaseWebMindNoteFragment.this.mIsFromPc)) {
                Logger.INSTANCE.w(BaseWebMindNoteFragment.TAG, "Fail to load note");
                return;
            }
            if (BaseWebMindNoteFragment.this.isAdded()) {
                if (!TextUtils.isEmpty(this.userQuery) && !this.userQuery.equals(BaseWebMindNoteFragment.this.mSearchText)) {
                    BaseWebMindNoteFragment.this.mSearchText = this.userQuery;
                }
                BaseWebMindNoteFragment.this.setWorkingNote(workingNote, NoteIntent.ACTION_MIND.equals(this.intent.getAction()) || "android.intent.action.EDIT".equals(this.intent.getAction()));
                BaseWebMindNoteFragment.this.setDataToWebView();
            }
        }

        public void commit() {
            TaskManagerUtils.getTaskManager().add(this);
        }
    }

    public class NoteDeleteTask extends NoteLoadTask {
        long date;
        long folderId;
        volatile boolean hasDeleted;
        Context mContext;
        long noteId;
        boolean touchDelete;

        @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.NoteLoadTask
        protected WorkingNote parseWorkingNote() {
            return null;
        }

        public NoteDeleteTask(Intent intent, Context context) {
            super(intent);
            this.touchDelete = intent.getBooleanExtra(NoteIntent.KEY_TOUCH_DELETE, false);
            Bundle bundle = (Bundle) intent.getParcelableExtra(NoteIntent.KEY_EXTRA_LOAD_DATA);
            if (bundle != null) {
                this.noteId = bundle.getLong("data_id", 0L);
                this.folderId = bundle.getLong("folder_id", 0L);
            }
            this.date = intent.getLongExtra(NoteIntent.KEY_DATE, 0L);
            this.mContext = context;
        }

        @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.NoteLoadTask
        protected WorkingNote loadWorkingNote() {
            if (NoteStore.delete(this.mContext, new long[]{this.noteId}) <= 0) {
                return null;
            }
            this.hasDeleted = true;
            return null;
        }

        @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.NoteLoadTask
        protected void updateUI(WorkingNote workingNote) {
            if (this.hasDeleted) {
                BaseWebMindNoteFragment.this.onNoteDeleted(this.noteId);
            }
        }
    }

    public class ViewNoteLoadTask extends NoteLoadTask {
        long noteId;

        @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.NoteLoadTask
        protected WorkingNote parseWorkingNote() {
            return null;
        }

        public ViewNoteLoadTask(Intent intent, long j) {
            super(intent);
            Logger.INSTANCE.i(BaseWebMindNoteFragment.TAG, "ViewNoteLoadTask start");
            this.noteId = j;
        }

        @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.NoteLoadTask
        protected WorkingNote loadWorkingNote() {
            return WorkingNote.loadNote("_id=" + this.noteId, null);
        }
    }

    public class EditNoteLoadTask extends NoteLoadTask {
        int bgResId;
        long callDate;
        boolean createNew;
        long folderId;
        Context mContext;
        String phoneNumber;
        String title;
        int widgetId;
        int widgetType;

        public EditNoteLoadTask(Intent intent, Context context) {
            super(intent);
            this.mContext = context;
            initialize();
        }

        private void initialize() {
            String str;
            Bundle bundle = (Bundle) this.intent.getParcelableExtra(NoteIntent.KEY_EXTRA_LOAD_DATA);
            this.bgResId = this.intent.getIntExtra(Notes.Intents.INTENT_EXTRA_BACKGROUND_ID, ResourceParser.getDefaultBgId(this.mContext));
            if (bundle != null) {
                this.folderId = bundle.getLong("folder_id");
                this.widgetId = (int) bundle.getLong("widget_id");
                this.widgetType = bundle.getInt("widget_type");
                this.title = bundle.getString("note_title");
            } else {
                this.folderId = this.intent.getLongExtra("com.miui.notes.folder_id", 0L);
            }
            this.phoneNumber = TextProcessUtils.formatPhoneNum(this.intent.getStringExtra("android.intent.extra.PHONE_NUMBER"));
            long longExtra = this.intent.getLongExtra(Notes.Intents.INTENT_EXTRA_CALL_DATE, 0L);
            this.callDate = longExtra;
            if (longExtra == 0 || (str = this.phoneNumber) == null || !TextUtils.isEmpty(str)) {
                return;
            }
            Logger.INSTANCE.w(BaseWebMindNoteFragment.TAG, "The call record number is null");
            this.callDate = 0L;
            this.phoneNumber = null;
        }

        public boolean isCallNote() {
            return !TextUtils.isEmpty(this.phoneNumber);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public WorkingNote doLoad() {
            WorkingNote workingNoteLoadWorkingNote = loadWorkingNote();
            return (workingNoteLoadWorkingNote == null || BaseWebMindNoteFragment.this.isWorkingNoteIdInvalid(workingNoteLoadWorkingNote.getNoteId())) ? parseWorkingNote() : workingNoteLoadWorkingNote;
        }

        @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.NoteLoadTask
        protected WorkingNote loadWorkingNote() {
            if (this.phoneNumber != null) {
                return WorkingNote.loadNote("_id=(SELECT note_id FROM data WHERE data1=? AND mime_type=? AND PHONE_NUMBERS_EQUAL(data3,?))", new String[]{String.valueOf(this.callDate), Notes.CallData.CONTENT_ITEM_TYPE, this.phoneNumber});
            }
            return null;
        }

        @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.NoteLoadTask
        protected WorkingNote parseWorkingNote() {
            WorkingNote workingNoteCreateEmptyMindNote = WorkingNote.createEmptyMindNote(this.folderId, this.widgetId, this.widgetType, this.bgResId);
            workingNoteCreateEmptyMindNote.setWorkingTitle(this.title);
            String str = this.phoneNumber;
            if (str != null) {
                workingNoteCreateEmptyMindNote.convertToCallNote(str, this.callDate);
            }
            this.createNew = true;
            return workingNoteCreateEmptyMindNote;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, WorkingNote workingNote) {
            super.onResult(taskManager, workingNote);
            if (this.createNew) {
                BaseWebMindNoteFragment.this.mWebView.post(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$EditNoteLoadTask$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResult$0();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0() {
            BaseWebMindNoteFragment.this.mWebView.requestFocus();
        }
    }

    public void showSaveToGalleryDialog() {
        if (!isAdded() || getActivity() == null) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showSaveToGalleryDialog$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSaveToGalleryDialog$4() {
        if (getContext() == null || getContext().getResources() == null) {
            return;
        }
        showProgressDialog(getContext().getResources().getString(R.string.dlg_info_save_to_local_message));
    }

    public void showShareDialog() {
        if (!isAdded() || getContext() == null) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showShareDialog$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showShareDialog$5() {
        showProgressDialog(getContext().getResources().getString(R.string.dlg_info_creating_message));
    }

    public void checkPermissionSavePicToGallery(String str) {
        savePicToGallery(str);
    }

    public void savePicToGallery(final String str) {
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$savePicToGallery$6(str);
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$savePicToGallery$6(String str) {
        Logger.INSTANCE.i(TAG, "savePicToGallery base64 is empty:" + TextUtils.isEmpty(str));
        Bitmap bitmapBase64ToBitmap2 = ImageUtils.INSTANCE.base64ToBitmap2(str);
        String strInsertImage = MediaStore.Images.Media.insertImage(NoteApp.getInstance().getContentResolver(), bitmapBase64ToBitmap2, "IMG_" + System.currentTimeMillis(), (String) null);
        if (TextUtils.isEmpty(strInsertImage)) {
            Logger.INSTANCE.i(TAG, "savePicToGallery error insertImage is empty");
            Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.save_to_local_failure_message), 0).show();
        } else if (Uri.parse(strInsertImage) != null) {
            Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.save_to_local_success_message), 0).show();
        } else {
            Logger.INSTANCE.i(TAG, "savePicToGallery error base64Uri is empty");
            Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.save_to_local_failure_message), 0).show();
        }
        dismissProgressDialog();
        if (bitmapBase64ToBitmap2 == null || bitmapBase64ToBitmap2.isRecycled()) {
            return;
        }
        bitmapBase64ToBitmap2.recycle();
    }

    static class LaunchSendActivityTask extends AsyncTask<Void, Void, Void> {
        private final String TAG = "LaunchSendActivityTask";
        private final WeakReference<BaseWebMindNoteFragment> iFragmentRef;
        private Uri iPreviewUri;
        private File iShareFile;

        public LaunchSendActivityTask(BaseWebMindNoteFragment baseWebMindNoteFragment) {
            this.iFragmentRef = new WeakReference<>(baseWebMindNoteFragment);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            Thread.currentThread().setName("LaunchSendActivityTask");
            if (isCancelled()) {
                Logger.INSTANCE.i("LaunchSendActivityTask", "isCancelled, return.");
                return null;
            }
            File shareFile = getShareFile();
            this.iShareFile = shareFile;
            if (shareFile != null) {
                this.iPreviewUri = Uri.fromFile(shareFile);
            } else {
                Logger.INSTANCE.e("LaunchSendActivityTask", "Fail to get preview file");
            }
            return null;
        }

        private File getShareFile() {
            File fileCreateFile = TempFileManager.getInstance().createFile("share", Utils.JPG_SUFFIX);
            if (fileCreateFile == null) {
                Logger.INSTANCE.e("LaunchSendActivityTask", "Fail to getShareFile, shareFile is null");
                return null;
            }
            if (this.iFragmentRef.get() == null) {
                Logger.INSTANCE.e("LaunchSendActivityTask", "Fail to getShareFile, wk is null failed");
                return null;
            }
            if (this.iFragmentRef.get().mRender == null || !this.iFragmentRef.get().mRender.saveToFile(fileCreateFile)) {
                Logger.INSTANCE.e("LaunchSendActivityTask", "Fail to getShareFile, saveToFile failed");
                return null;
            }
            fileCreateFile.setReadable(true, false);
            fileCreateFile.setWritable(true, false);
            return fileCreateFile;
        }

        @Override // android.os.AsyncTask
        protected void onCancelled() {
            BaseWebMindNoteFragment baseWebMindNoteFragment = this.iFragmentRef.get();
            if (baseWebMindNoteFragment != null && baseWebMindNoteFragment.mShareGenerateTask == this) {
                baseWebMindNoteFragment.mShareGenerateTask = null;
                baseWebMindNoteFragment.dismissProgressDialog();
            }
            super.onCancelled();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r3) {
            BaseWebMindNoteFragment baseWebMindNoteFragment = this.iFragmentRef.get();
            if (baseWebMindNoteFragment != null) {
                if (baseWebMindNoteFragment.mShareGenerateTask != this) {
                    Logger.INSTANCE.w("LaunchSendActivityTask", "task has changed, don't apply result");
                    return;
                }
                baseWebMindNoteFragment.mShareGenerateTask = null;
                baseWebMindNoteFragment.dismissProgressDialog();
                baseWebMindNoteFragment.checkPermissionAndSaveToGallery(this.iShareFile);
                if (this.iPreviewUri == null) {
                    Logger.INSTANCE.e("LaunchSendActivityTask", "Fail to get preview file");
                }
            }
        }
    }

    private void showProgressDialog(String str) {
        ProgressDialog progressDialog = this.mDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            ProgressDialog progressDialog2 = new ProgressDialog(getActivity());
            this.mDialog = progressDialog2;
            progressDialog2.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda14
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    this.f$0.lambda$showProgressDialog$7(dialogInterface);
                }
            });
            this.mDialog.setMessage(str);
            this.mDialog.setIndeterminate(true);
            this.mDialog.setCanceledOnTouchOutside(false);
            this.mDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showProgressDialog$7(DialogInterface dialogInterface) {
        LaunchSendActivityTask launchSendActivityTask = this.mShareGenerateTask;
        if (launchSendActivityTask != null) {
            launchSendActivityTask.cancel(true);
        }
        getActivity().finish();
    }

    public void dismissProgressDialog() {
        if (!isAdded() || getActivity() == null) {
            if (this.mDialog != null) {
                this.mDialog = null;
                return;
            }
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$dismissProgressDialog$8();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dismissProgressDialog$8() {
        ProgressDialog progressDialog = this.mDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPermissionAndSaveToGallery(final File file) {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkPermissionAndSaveToGallery$9(file);
            }
        });
    }

    public void requestDeleteNote() {
        saveNow();
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestDeleteNote$11();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestDeleteNote$11() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 2131951644);
        builder.setTitle(R.string.new_alert_title_delete);
        builder.setMessage(R.string.alert_message_delete_note);
        builder.setPositiveButton(R.string.menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$requestDeleteNote$10(dialogInterface, i);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestDeleteNote$10(DialogInterface dialogInterface, int i) {
        doDeleteAndLoad();
    }

    protected void doDeleteAndLoad() {
        deleteAndLoad();
    }

    protected void deleteAndLoad() {
        AudioUtils.playDeleteRingtone();
        long noteId = getNoteId();
        if (noteId <= 0) {
            Logger.INSTANCE.e(TAG, "deleteAndLoad: invalid note id " + noteId);
            return;
        }
        Intent intent = new Intent(NoteIntent.ACTION_DELETE);
        intent.putExtra(NoteIntent.KEY_TOUCH_DELETE, true);
        NoteLoadDataEntity noteLoadDataEntity = new NoteLoadDataEntity();
        noteLoadDataEntity.setNoteId(noteId);
        noteLoadDataEntity.setFolderId(this.mWorkingNote.getFolderId());
        intent.putExtra(NoteIntent.KEY_EXTRA_LOAD_DATA, noteLoadDataEntity.toBundle());
        if (PreferenceUtils.getNoteSortWay(getActivity(), 1) == 1) {
            intent.putExtra(NoteIntent.KEY_DATE, this.mWorkingNote.getModifiedDate());
        } else {
            intent.putExtra(NoteIntent.KEY_DATE, this.mWorkingNote.getCreatedDate());
        }
        onNewWebIntent(intent);
    }

    public void onNewWebIntent(Intent intent) {
        if (getActivity().isFinishing()) {
            return;
        }
        boolean booleanExtra = intent.getBooleanExtra(NoteWidgetProvider.INTENT_EXTRA_IS_HIDDEN, false);
        if ("android.intent.action.VIEW".equals(intent.getAction()) && this.mWorkingNote != null && !booleanExtra) {
            long extraNoteId = getExtraNoteId(intent);
            String extraUserQuery = getExtraUserQuery(intent);
            if (extraNoteId > 0 && extraNoteId == getNoteId() && TextUtils.equals(extraUserQuery, this.mSearchText)) {
                return;
            }
        }
        getActivity().setIntent(intent);
        saveNoteInstantlyIfNeeded();
        this.mPendingHandleIntent = intent;
        Bundle bundle = (Bundle) intent.getParcelableExtra(NoteIntent.KEY_EXTRA_LOAD_DATA);
        if (bundle != null) {
            this.mEditMode = bundle.getInt(NoteLoadDataEntity.MIND_EDIT_MODE);
            this.mMindContentType = bundle.getInt("mind_content_type");
            this.mTitle = bundle.getString("note_title");
        }
        preHandleIntent(this.mPendingHandleIntent);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public long getNoteId() {
        if (this.mWorkingNote == null || this.mWorkingNote.getIsInvalid()) {
            return this.mLoadTask != null ? 0L : -1L;
        }
        return this.mWorkingNote.getNoteId();
    }

    protected boolean isWorkingNoteEmpty() {
        if (this.mWorkingNote == null || isWorkingNoteIdInvalid(this.mWorkingNote.getNoteId())) {
            return true;
        }
        return TextUtils.isEmpty(this.mWorkingNote.getContent()) && TextUtils.isEmpty(this.mWorkingNote.getTitle()) && TextUtils.isEmpty(this.mWorkingNote.getWorkingMindContent());
    }

    public void asyncSaveNote(String str) {
        if (this.mWorkingNote == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "mMindNoteDataJson.contentEquals(dataJson) " + this.mMindNoteDataJson.contentEquals(str));
        if (this.mMindNoteDataJson.contentEquals(str)) {
            return;
        }
        updateWorkingText(str);
        if (this.mHandler.hasMessages(1) || this.mWorkingNote.isEmpty()) {
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(1, getSaveDataDuration());
    }

    public String getNightMode() {
        return DisplayUtils.isNightMode() ? "dark" : Constants.LIGHT;
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
            getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
            getActivity().getWindow().getAttributes().flags = this.mWindowInitFlags;
            return;
        }
        if (zIsInMultiWindowMode) {
            return;
        }
        getActivity().getWindow().getDecorView().setSystemUiVisibility(R2.dimen.miuix_appcompat_dialog_button_vertical_margin);
        getActivity().getWindow().getAttributes().flags = this.mWindowInitFlags;
    }

    public void onViewMode() {
        this.onEditMode = false;
        notifyEditUiState(0);
    }

    public void onEditMode() {
        this.onEditMode = true;
        notifyEditUiState(1);
    }

    public void onBackToNormalNote() {
        if (isAdded() && !this.mIsExited) {
            Intent intent = new Intent(getActivity(), (Class<?>) EditActivity.class);
            ActivityOptionsCompat activityOptionsCompatMakeCustomAnimation = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.activity_fade_in, R.anim.activity_fade_out);
            intent.putExtra("com.miui.notes.folder_id", this.mFolderId);
            intent.putExtra(NoteIntent.KEY_IS_PENDING, false);
            PrivateNotesListActivity.ACTION_FROM_PRIVACY = this.mIsFromPrivate;
            intent.setAction("android.intent.action.INSERT_OR_EDIT");
            if (!RomUtils.isPadMode() && getActivity().getResources().getConfiguration().orientation == 2) {
                intent.putExtra(NoteIntent.KEY_NEED_DELAY_SOFTINPUT, true);
            }
            getActivity().startActivity(intent, activityOptionsCompatMakeCustomAnimation.toBundle());
            getActivity().finish();
            this.mIsExited = true;
        }
    }

    public boolean isFirstEnterMind() {
        return PreferenceUtils.getFirstEnterMindNote(getContext());
    }

    public void setHasEnterMind() {
        PreferenceUtils.setFirstEnterMindNote(getContext(), false);
    }

    public void onLog(String str) {
        Logger.INSTANCE.i("MindNote", str);
    }

    public boolean showSendToDesktop() {
        if ((this.mWorkingNote == null || !this.mWorkingNote.isHidden()) && !this.mIsFromPrivate && isAdded()) {
            return UIUtils.isMiuiWidgetSupported(getContext());
        }
        return false;
    }

    public void sendToDesktop() {
        String[] strArr;
        boolean zIsElderlyManEnable = UIUtils.isElderlyManEnable(getContext());
        Logger.INSTANCE.d(TAG, "sendToDesktop mIsSendingToDesktop: " + this.mIsSendingToDesktop + " elderlyManEnable = " + zIsElderlyManEnable);
        if (zIsElderlyManEnable) {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_minimal_mode_widget_add_unsupported, 0).show();
            return;
        }
        if (this.mIsSendingToDesktop) {
            return;
        }
        saveNoteInstantlyIfNeeded();
        if (isWorkingNoteEmpty()) {
            Logger.INSTANCE.w(TAG, "Send to desktop error");
            Toast.makeText(NoteApp.getInstance(), R.string.error_note_empty_for_send_to_desktop, 0).show();
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
        registerObserver();
        if (RomUtils.isPadDevice()) {
            strArr = new String[]{getResources().getString(R.string.widget_size_item_small), getResources().getString(R.string.widget_size_item_medium), getResources().getString(R.string.widget_size_item_big), getResources().getString(R.string.widget_size_item_super_big)};
        } else {
            strArr = new String[]{getResources().getString(R.string.widget_size_item_small), getResources().getString(R.string.widget_size_item_medium), getResources().getString(R.string.widget_size_item_big)};
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setActionSheetEnabled(true);
        builder.setHapticFeedbackEnabled(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda12
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                this.f$0.lambda$sendToDesktop$12(dialogInterface);
            }
        });
        builder.setItems(strArr, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.9
            private Long lastClickTime = null;

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Class cls;
                if (this.lastClickTime != null && System.currentTimeMillis() - this.lastClickTime.longValue() <= 400) {
                    this.lastClickTime = Long.valueOf(System.currentTimeMillis());
                    return;
                }
                this.lastClickTime = Long.valueOf(System.currentTimeMillis());
                if (i == 0) {
                    cls = NoteWidgetProvider_2x2.class;
                } else if (i == 1) {
                    cls = NoteWidgetProvider_4x2.class;
                } else if (i == 2) {
                    cls = NoteWidgetProvider_4x4.class;
                } else {
                    cls = i != 3 ? null : NoteWidgetProvider_8x4.class;
                }
                if (BaseWebMindNoteFragment.this.isAdded()) {
                    Intent intent = new Intent();
                    intent.putExtra("android.intent.extra.UID", BaseWebMindNoteFragment.this.mWorkingNote.getNoteId());
                    intent.putExtra(SystemIntent.EXTRA_RESULT_RECEIVER_COMPONENT_NAME, new ComponentName(BaseWebMindNoteFragment.this.getActivity(), (Class<?>) cls));
                    intent.putExtra(SystemIntent.EXTRA_PROVIDER_COMPONENT_NAME, new ComponentName(BaseWebMindNoteFragment.this.getActivity(), (Class<?>) cls));
                    intent.setAction(SystemIntent.ACTION_INSTALL_WIDGET);
                    CompatUtils.sendImplicitBroadcast(BaseWebMindNoteFragment.this.getActivity(), intent);
                    Logger.INSTANCE.i(BaseWebMindNoteFragment.TAG, "send broadcast to desktop success,id:" + BaseWebMindNoteFragment.this.mWorkingNote.getNoteId());
                }
            }
        });
        builder.setNeutralButton(getResources().getString(android.R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda13
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$sendToDesktop$13(dialogInterface, i);
            }
        });
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendToDesktop$12(DialogInterface dialogInterface) {
        this.mIsSendingToDesktop = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendToDesktop$13(DialogInterface dialogInterface, int i) {
        this.mIsSendingToDesktop = false;
    }

    protected void killSaveTask() {
        NoteLoadTask noteLoadTask = this.mLoadTask;
        if (noteLoadTask != null) {
            noteLoadTask.cancel();
            this.mLoadTask = null;
        }
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        if (handler.hasMessages(0)) {
            this.mHandler.removeMessages(0);
        }
        if (this.mHandler.hasMessages(4)) {
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

    protected void cancelSaveTask() {
        NoteLoadTask noteLoadTask = this.mLoadTask;
        if (noteLoadTask != null) {
            noteLoadTask.cancel();
            this.mLoadTask = null;
        }
        if (this.mHandler.hasMessages(0)) {
            this.mHandler.removeMessages(0);
        }
        if (this.mHandler.hasMessages(4)) {
            this.mHandler.removeMessages(4);
        }
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Logger.INSTANCE.e(TAG, "onPause");
        this.mNeedConfirmPass = true;
        this.mWebView.loadUrl("javascript:saveDataOnPause()");
        if (!this.mIsInSearchMode) {
            Utils.hideSoftInput(this.mWebView);
        }
        dismissProgressDialog();
        notifyEditUiState(2);
    }

    public void exitWithoutSave() {
        if (isAdded()) {
            Logger.INSTANCE.d(TAG, "exitWithoutSave");
            getActivity().setResult(-1);
            getActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$14() {
        this.mWebView.loadUrl("javascript:saveData()");
    }

    public void saveData() {
        this.mWebView.post(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$saveData$14();
            }
        });
    }

    public void asyncSaveNoteInstantlyIfNeeded(String str) {
        if (isAdded() && !this.mMindNoteDataJson.contentEquals(str)) {
            updateWorkingText(str);
            saveAndExit();
        }
    }

    public void asyncSaveNoteInstantlyOnPause(String str) {
        Logger.INSTANCE.d(TAG, "asyncSaveNoteInstantlyOnPause " + this.mMindNoteDataJson.contentEquals(str));
        if (this.mMindNoteDataJson.contentEquals(str)) {
            return;
        }
        updateWorkingText(str);
        saveNow();
    }

    public String getTitle() {
        return this.mTitle;
    }

    public int getEditMode() {
        return this.mEditMode;
    }

    public int getEditMarginTopInOverViewMode() {
        if (isAdded()) {
            return getContext().getResources().getDimensionPixelSize(R.dimen.mind_note_overview_mode_edit_margin_top);
        }
        return 0;
    }

    public void saveNoteInstantlyIfNeeded() {
        Logger.INSTANCE.d(TAG, "saveNoteInstantlyIfNeeded");
        if (this.mWorkingNote == null || !this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.removeMessages(1);
        this.mWorkingNote.saveNote(true);
    }

    public void updateWorkingEmptyData(String str) {
        String str2;
        String string;
        String strSaveImageFile;
        this.mMindNoteDataJson = str;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String string2 = null;
        try {
            JSONObject jSONObject = new JSONObject(TextProcessUtils.getStandardJsonString(str));
            str2 = this.mThumbnailBase64;
            try {
                string = jSONObject.getString("currentPage");
                try {
                    JSONObject jSONObject2 = new JSONObject(jSONObject.getString("data"));
                    string2 = jSONObject2.getString(Todo.LABEL);
                    jSONObject2.getJSONArray("children");
                } catch (JSONException e) {
                    e = e;
                    e.printStackTrace();
                }
            } catch (JSONException e2) {
                e = e2;
                string = null;
            }
        } catch (JSONException e3) {
            e = e3;
            str2 = null;
            string = null;
        }
        if (this.mWorkingNote != null) {
            if (!TextUtils.isEmpty(str2)) {
                strSaveImageFile = AttachmentUtils.saveImageFile(getActivity(), ImageUtils.INSTANCE.base64PngToBitmap(str2), this.mLastThumbnailSha1Digest);
            } else {
                strSaveImageFile = AttachmentUtils.saveImageFile(NoteApp.getInstance(), BitmapFactory.decodeResource(getResources(), R.drawable.pad_mind_thumbnail_holderplace_pic), this.mLastThumbnailSha1Digest);
            }
            this.mLastThumbnailSha1Digest = strSaveImageFile;
            if (!TextUtils.isEmpty(this.mFirstThumbnailBase64)) {
                strSaveImageFile = this.mFirstThumbnailBase64;
            }
            boolean z = TextUtils.isEmpty(string) || string.equals("/mind");
            String str3 = TextUtils.isEmpty(string2) ? "" : string2;
            if (strSaveImageFile == null) {
                strSaveImageFile = "";
            }
            MindEntity mindEntity = new MindEntity(z, str3, str, strSaveImageFile);
            this.mWorkingNote.setWorkingTitle(string2);
            this.mWorkingNote.setWorkingText("");
            this.mWorkingNote.setWorkingMindContent(mindEntity.toNoteContent());
        }
        saveNow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeCreateNewNoteTask() {
        Runnable runnable = this.mPendingEditIntentTask;
        if (runnable != null) {
            runnable.run();
            this.mPendingEditIntentTask = null;
        }
    }

    protected void saveNow() {
        Logger.INSTANCE.d(TAG, "saveNow");
        if (this.mWorkingNote == null || this.mWorkingNote.isEmpty()) {
            return;
        }
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessage(1);
    }

    protected void saveAndExit() {
        saveNow();
        getActivity().setResult(-1);
        this.mBridgeInterface.releaseReference();
        getActivity().finish();
    }

    protected void startToShareActivity() {
        if (TextUtils.isEmpty(this.mSharedBase64)) {
            return;
        }
        SnsEditActivity.launchActivity(this.mToShareResult, (BaseActivity) getActivity(), "asd", "", 2, 0, 0L, 0.0f, 16, this.mSharedBase64, this.mWorkingNote, (AigcPicMetaData) null);
    }

    @Deprecated
    protected void notifyWebViewSizeChange(int i, int i2) {
        this.mWebView.loadUrl("javascript:changeViewSize(" + i + ", + " + i2 + " + )");
    }

    protected void setScreenOrientationToWebView(int i) {
        if (this.mWebView != null) {
            Logger.INSTANCE.d(TAG, "setScreenOrientationToWebView orientation = " + i);
            this.mWebView.loadUrl("javascript:forNoteSetOrientation('" + i + "')");
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    protected void setMindContentToWebView(String str, String str2, int i, ArrayList<String> arrayList) {
        boolean z = (arrayList == null || arrayList.isEmpty()) ? false : true;
        Logger.INSTANCE.d(TAG, "setMindContentToWebView mSearchText :" + this.mSearchText + " useHighLightKeyWords :" + z + "  mGson.toJson(highLightKeyWords) :" + this.mGson.toJson(arrayList));
        if (i == 0) {
            WebView webView = this.mWebView;
            StringBuilder sbAppend = new StringBuilder("javascript:getDataInMind(").append(str).append(", ");
            Gson gson = this.mGson;
            Object obj = str2;
            if (z) {
                obj = arrayList;
            }
            webView.evaluateJavascript(sbAppend.append(gson.toJson(obj)).append(")").toString(), null);
            return;
        }
        WebView webView2 = this.mWebView;
        StringBuilder sbAppend2 = new StringBuilder("javascript:getDataInOverview(").append(str).append(", ");
        Gson gson2 = this.mGson;
        Object obj2 = str2;
        if (z) {
            obj2 = arrayList;
        }
        webView2.evaluateJavascript(sbAppend2.append(gson2.toJson(obj2)).append(")").toString(), null);
    }

    protected void updateLanguageToWebView() {
        this.mWebView.loadUrl("javascript:changeLanguage('" + ("values-" + Locale.getDefault().getLanguage() + "-r" + Locale.getDefault().getCountry()) + "')");
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, com.miui.common.tool.softinputhelper.KeyboardListener
    public void onKeyboardVisibleChanged(boolean z) {
        super.onKeyboardVisibleChanged(z);
        this.mIsKeyboardVisible = z;
    }

    protected void updatePaddingBottom(int i) {
        this.mLastViewPaddingBottom = i;
        this.mWebView.loadUrl("javascript:keyBoardShowStatusChange(" + this.mIsKeyboardVisible + ")");
    }

    protected boolean isAboveAndroidR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class WebViewBrainHandler extends Handler {
        private final WeakReference<BaseWebMindNoteFragment> nFragment;

        private WebViewBrainHandler(BaseWebMindNoteFragment baseWebMindNoteFragment) {
            this.nFragment = new WeakReference<>(baseWebMindNoteFragment);
        }

        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            super.dispatchMessage(message);
            final BaseWebMindNoteFragment baseWebMindNoteFragment = this.nFragment.get();
            if (baseWebMindNoteFragment == null) {
                return;
            }
            Logger.INSTANCE.d(BaseWebMindNoteFragment.TAG, "dispatchMessage msg = " + message.what);
            if (message.what == 0) {
                baseWebMindNoteFragment.executeCreateNewNoteTask();
            }
            if (baseWebMindNoteFragment.mWorkingNote == null || baseWebMindNoteFragment.mExecutorService.isShutdown()) {
                return;
            }
            int i = message.what;
            if (i == 1) {
                baseWebMindNoteFragment.mExecutorService.execute(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$WebViewBrainHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseWebMindNoteFragment.WebViewBrainHandler.lambda$dispatchMessage$0(baseWebMindNoteFragment);
                    }
                });
                return;
            }
            if (i != 2) {
                if (i != 4) {
                    return;
                }
                Logger.INSTANCE.i(BaseWebMindNoteFragment.TAG, "intent share pic");
                baseWebMindNoteFragment.startToShareActivity();
                return;
            }
            if (baseWebMindNoteFragment.mWorkingNote != null) {
                baseWebMindNoteFragment.mWorkingNote.saveNote(true);
                if (baseWebMindNoteFragment.mWorkingNote.isEmpty()) {
                    NoteStore.delete(baseWebMindNoteFragment.mWorkingNote.getContext(), new long[]{baseWebMindNoteFragment.mWorkingNote.getNoteId()});
                }
                baseWebMindNoteFragment.mWorkingNote = null;
            }
            baseWebMindNoteFragment.mExecutorService.shutdown();
        }

        static /* synthetic */ void lambda$dispatchMessage$0(BaseWebMindNoteFragment baseWebMindNoteFragment) {
            if (baseWebMindNoteFragment.mWorkingNote != null) {
                baseWebMindNoteFragment.mWorkingNote.saveNote(true);
            }
        }
    }

    static class SaveToGalleryTask extends AsyncTask<File, Void, String> {
        private final Context iContext;
        private final WeakReference<BaseWebMindNoteFragment> iFragmentRef;

        public SaveToGalleryTask(BaseWebMindNoteFragment baseWebMindNoteFragment) {
            WeakReference<BaseWebMindNoteFragment> weakReference = new WeakReference<>(baseWebMindNoteFragment);
            this.iFragmentRef = weakReference;
            this.iContext = weakReference.get().getActivity().getApplicationContext();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Code duplicated, block: B:64:0x009c A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:65:0x009e A[Catch: IOException -> 0x009a, TRY_LEAVE, TryCatch #2 {IOException -> 0x009a, blocks: (B:61:0x0096, B:65:0x009e), top: B:70:0x0096 }] */
        /* JADX WARN: Code duplicated, block: B:70:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v17 */
        /* JADX WARN: Type inference failed for: r9v3, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r9v7 */
        @Override // android.os.AsyncTask
        public String doInBackground(File... fileArr) throws Throwable {
            File file;
            ?? r9;
            FileOutputStream fileOutputStream;
            FileInputStream fileInputStream;
            Thread.currentThread().setName("save2GalleryTsk");
            FileInputStream fileInputStream2 = null;
            if (fileArr == null || fileArr.length < 1 || (file = fileArr[0]) == null || !file.exists()) {
                return null;
            }
            File fileObtainJpgFile = Utils.obtainJpgFile(this.iContext);
            if (file != null) {
                try {
                    try {
                        if (fileObtainJpgFile != null) {
                            try {
                                fileInputStream = new FileInputStream(file);
                                try {
                                    fileOutputStream = new FileOutputStream(fileObtainJpgFile);
                                    try {
                                        byte[] bArr = new byte[1024];
                                        while (true) {
                                            int i = fileInputStream.read(bArr);
                                            if (i == -1) {
                                                break;
                                            }
                                            fileOutputStream.write(bArr, 0, i);
                                        }
                                        fileInputStream.close();
                                    } catch (FileNotFoundException e) {
                                        e = e;
                                        e.printStackTrace();
                                        if (fileInputStream != null) {
                                            fileInputStream.close();
                                        }
                                        if (fileOutputStream != null) {
                                        }
                                        MediaScannerConnection.scanFile(this.iContext, new String[]{fileObtainJpgFile.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$SaveToGalleryTask$$ExternalSyntheticLambda0
                                            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                                            public final void onScanCompleted(String str, Uri uri) {
                                                Logger.INSTANCE.i(BaseWebMindNoteFragment.TAG, "Scanned " + str + " completely.");
                                            }
                                        });
                                        return fileObtainJpgFile.getAbsolutePath();
                                    } catch (IOException e2) {
                                        e = e2;
                                        e.printStackTrace();
                                        if (fileInputStream != null) {
                                            fileInputStream.close();
                                        }
                                        if (fileOutputStream != null) {
                                        }
                                        MediaScannerConnection.scanFile(this.iContext, new String[]{fileObtainJpgFile.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$SaveToGalleryTask$$ExternalSyntheticLambda0
                                            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                                            public final void onScanCompleted(String str, Uri uri) {
                                                Logger.INSTANCE.i(BaseWebMindNoteFragment.TAG, "Scanned " + str + " completely.");
                                            }
                                        });
                                        return fileObtainJpgFile.getAbsolutePath();
                                    }
                                } catch (FileNotFoundException e3) {
                                    e = e3;
                                    fileOutputStream = null;
                                } catch (IOException e4) {
                                    e = e4;
                                    fileOutputStream = null;
                                } catch (Throwable th) {
                                    th = th;
                                    file = null;
                                    fileInputStream2 = fileInputStream;
                                    r9 = file;
                                    if (fileInputStream2 != null) {
                                        try {
                                            fileInputStream2.close();
                                            if (r9 != 0) {
                                                r9.close();
                                            }
                                        } catch (IOException e5) {
                                            e5.printStackTrace();
                                            throw th;
                                        }
                                    } else if (r9 != 0) {
                                        r9.close();
                                    }
                                    throw th;
                                }
                            } catch (FileNotFoundException e6) {
                                e = e6;
                                fileOutputStream = null;
                                fileInputStream = null;
                            } catch (IOException e7) {
                                e = e7;
                                fileOutputStream = null;
                                fileInputStream = null;
                            } catch (Throwable th2) {
                                th = th2;
                                r9 = 0;
                                if (fileInputStream2 != null) {
                                    fileInputStream2.close();
                                    if (r9 != 0) {
                                        r9.close();
                                    }
                                } else if (r9 != 0) {
                                    r9.close();
                                }
                                throw th;
                            }
                            fileOutputStream.close();
                            MediaScannerConnection.scanFile(this.iContext, new String[]{fileObtainJpgFile.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$SaveToGalleryTask$$ExternalSyntheticLambda0
                                @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                                public final void onScanCompleted(String str, Uri uri) {
                                    Logger.INSTANCE.i(BaseWebMindNoteFragment.TAG, "Scanned " + str + " completely.");
                                }
                            });
                            return fileObtainJpgFile.getAbsolutePath();
                        }
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
            return null;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            BaseWebMindNoteFragment baseWebMindNoteFragment = this.iFragmentRef.get();
            if (baseWebMindNoteFragment != null) {
                baseWebMindNoteFragment.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$SaveToGalleryTask$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onPreExecute$1();
                    }
                }, 500L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreExecute$1() {
            BaseWebMindNoteFragment baseWebMindNoteFragment = this.iFragmentRef.get();
            if (baseWebMindNoteFragment == null || baseWebMindNoteFragment.mSaveToGalleryTask != this || baseWebMindNoteFragment.getActivity().isFinishing()) {
                return;
            }
            Toast.makeText(baseWebMindNoteFragment.getActivity(), baseWebMindNoteFragment.getResources().getString(R.string.dlg_info_save_to_local_message), 0).show();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            final BaseWebMindNoteFragment baseWebMindNoteFragment = this.iFragmentRef.get();
            if (baseWebMindNoteFragment == null) {
                Logger.INSTANCE.w(BaseWebMindNoteFragment.TAG, "fragment is null");
                return;
            }
            if (baseWebMindNoteFragment.mSaveToGalleryTask != this) {
                Logger.INSTANCE.w(BaseWebMindNoteFragment.TAG, "Task has changed, don't apply result");
                return;
            }
            baseWebMindNoteFragment.mSaveToGalleryTask = null;
            if (str == null) {
                Toast.makeText(baseWebMindNoteFragment.getActivity(), baseWebMindNoteFragment.getResources().getString(R.string.save_to_local_failure_message), 0).show();
                return;
            }
            Toast.makeText(baseWebMindNoteFragment.getActivity(), baseWebMindNoteFragment.getResources().getString(R.string.save_to_local_success_message), 0).show();
            if (baseWebMindNoteFragment.getView() != null) {
                baseWebMindNoteFragment.getView().post(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$SaveToGalleryTask$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseWebMindNoteFragment.SaveToGalleryTask.lambda$onPostExecute$2(baseWebMindNoteFragment);
                    }
                });
            }
        }

        static /* synthetic */ void lambda$onPostExecute$2(BaseWebMindNoteFragment baseWebMindNoteFragment) {
            baseWebMindNoteFragment.dismissProgressDialog();
            ExternalAppUtils.openGallery(baseWebMindNoteFragment.getActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    protected static class MyDualScreenEditReceiver extends SafeBroadcastReceiver<BaseWebMindNoteFragment> {
        public MyDualScreenEditReceiver(BaseWebMindNoteFragment baseWebMindNoteFragment) {
            super(baseWebMindNoteFragment);
        }

        @Override // com.miui.common.base.SafeBroadcastReceiver, android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (hasReference()) {
                int i = intent.getExtras().getInt(FrameConstant.PARAM_SCREEN_ID, 0);
                if (FrameConstant.ACTION_DUAL_SCREEN_STATE_REQUEST.equals(intent.getAction())) {
                    if (i != 0) {
                        getWkObject().responseEditUiState();
                        return;
                    }
                    return;
                }
                if (FrameConstant.ACTION_DUAL_SCREEN_STATE_CHANGED.equals(intent.getAction())) {
                    int i2 = intent.getExtras().getInt(FrameConstant.PARAM_SCREEN_MODE, 0);
                    if (i != 0) {
                        if (i2 != 0) {
                            if (i2 == 1) {
                                getWkObject().showDualScreenEditTipDialog();
                                return;
                            } else if (i2 != 2) {
                                return;
                            }
                        }
                        if (getWkObject().mDualScreenEditTipDialog != null) {
                            if (getWkObject().isAdded()) {
                                getWkObject().mDualScreenEditTipDialog.dismiss();
                            }
                            getWkObject().mDualScreenEditTipDialog = null;
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (FrameConstant.ACTION_DUAL_SCREEN_STATE_CONTENT_SYNC.equals(intent.getAction())) {
                    long j = intent.getExtras().getLong("note_id", -1L);
                    if (i == 0 || j != getWkObject().getNoteId()) {
                        return;
                    }
                    getWkObject().updateNoteFromDualScreen();
                }
            }
        }
    }

    protected static class NoteContentObserver extends ContentObserver {
        private final WeakReference<BaseWebMindNoteFragment> mFragment;

        public NoteContentObserver(BaseWebMindNoteFragment baseWebMindNoteFragment) {
            super(new Handler());
            this.mFragment = new WeakReference<>(baseWebMindNoteFragment);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            Logger.INSTANCE.d(BaseWebMindNoteFragment.TAG, "widget observer onChanged");
            WeakReference<BaseWebMindNoteFragment> weakReference = this.mFragment;
            BaseWebMindNoteFragment baseWebMindNoteFragment = weakReference == null ? null : weakReference.get();
            if (baseWebMindNoteFragment != null) {
                baseWebMindNoteFragment.onContentChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onContentChanged() {
        Logger.INSTANCE.d(TAG, "widget observer onChanged execute");
        if (this.mWorkingNote == null || this.mWorkingNote.getNoteId() <= 0) {
            return;
        }
        new WidgetUpdateTask(this, this.mWorkingNote.getNoteId()).execute(new Void[0]);
    }

    protected void registerObserver() {
        FragmentActivity activity;
        if (!isResumed() || (activity = getActivity()) == null || this.mWorkingNote == null || this.mWorkingNote.getNoteId() <= 0 || activity.getContentResolver() == null) {
            return;
        }
        activity.getContentResolver().registerContentObserver(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, this.mWorkingNote.getNoteId()), false, this.mObserver);
    }

    private void unregisterObserver() {
        FragmentActivity activity = getActivity();
        if (activity == null || this.mObserver == null || activity.getContentResolver() == null) {
            return;
        }
        activity.getContentResolver().unregisterContentObserver(this.mObserver);
    }

    private static class WidgetUpdateTask extends AsyncTask<Void, Void, int[]> {
        private final WeakReference<BaseWebMindNoteFragment> fragmentRef;
        private final Context iContext = NoteApp.getInstance();
        private final long noteId;

        public WidgetUpdateTask(BaseWebMindNoteFragment baseWebMindNoteFragment, long j) {
            this.fragmentRef = new WeakReference<>(baseWebMindNoteFragment);
            this.noteId = j;
            baseWebMindNoteFragment.mIsSendingToDesktop = true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onCancelled(int[] iArr) {
            super.onCancelled(iArr);
            BaseWebMindNoteFragment baseWebMindNoteFragment = this.fragmentRef.get();
            if (baseWebMindNoteFragment != null) {
                baseWebMindNoteFragment.mIsSendingToDesktop = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public int[] doInBackground(Void... voidArr) {
            Thread.currentThread().setName("widgetUpdateTsk");
            Logger.INSTANCE.d(BaseWebMindNoteFragment.TAG, "doInBackground...WidgetUpdateTask run");
            return DataUtils.getWidgetInfoByNote(this.iContext, this.noteId);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(int[] iArr) {
            BaseWebMindNoteFragment baseWebMindNoteFragment = this.fragmentRef.get();
            if (baseWebMindNoteFragment != null) {
                baseWebMindNoteFragment.applyWidgetResult(this.noteId, iArr);
                baseWebMindNoteFragment.mIsSendingToDesktop = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyWidgetResult(long j, int[] iArr) {
        unregisterObserver();
        if (this.mWorkingNote == null || this.mWorkingNote.getNoteId() != j || iArr == null) {
            return;
        }
        int i = iArr[0];
        int i2 = iArr[1];
        if (this.mWorkingNote.getWidgetId() != i) {
            this.mWorkingNote.applyWidgetId(i);
            this.mWorkingNote.applyWidgetType(i2);
            if (i > 0) {
                Toast.makeText(NoteApp.getInstance(), R.string.info_note_enter_desktop, 0).show();
            }
        }
    }

    @Override // com.miui.notes.model.WorkingNote.NoteSettingChangedListener
    public void onWidgetChanged(int i, int i2) {
        UIUtils.updateWidget(NoteApp.getInstance(), i2, i);
    }

    public String getCreateTime() {
        if (this.mWorkingNote != null) {
            long sortDate = this.mWorkingNote.getSortDate();
            return ((Object) DateFormatUtils.formatDateText(getContext(), sortDate)) + StringUtils.SPACE + ((Object) DateFormatUtils.formatTimeText(getContext(), sortDate));
        }
        return "";
    }

    public String getModifiedTime(String str) {
        long jCurrentTimeMillis = this.mWorkingNote == null ? System.currentTimeMillis() : this.mWorkingNote.getSortDate();
        return ((Object) DateFormatUtils.formatDateText(getContext(), jCurrentTimeMillis)) + StringUtils.SPACE + ((Object) DateFormatUtils.formatTimeText(getContext(), jCurrentTimeMillis));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startQueryFolder$15() {
        super.startQueryFolder();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void startQueryFolder() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$startQueryFolder$15();
            }
        });
    }
}
