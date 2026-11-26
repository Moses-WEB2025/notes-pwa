package com.miui.pad.feature.notes.commonedit;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentActivity;
import androidx.hardware.DataSpace;
import androidx.lifecycle.Observer;
import com.google.gson.Gson;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.constant.FrameConstant;
import com.miui.common.record.SpeechRecognitionListener;
import com.miui.common.record.SpeechRecognitionTask;
import com.miui.common.record.SpeechRecognitionTaskImp;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.DualScreenManager;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.NetworkCallbackImpl;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.async.TaskManager;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.common.view.IPopMenu;
import com.miui.doodle.DoodleApplication;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.hybirdeditor.TempAudio;
import com.miui.hybirdeditor.WebRichEditor;
import com.miui.hybirdeditor.jsbridge.EmbedElementInfoBean;
import com.miui.hybirdeditor.jsbridge.HybridEditorScriptInterface;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.adapter.ResolverAdapter;
import com.miui.notes.ai.AigcPicMetaData;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.basefeature.noteedit.ColumnsHybridNoteEditFragment;
import com.miui.notes.component.noteedit.IBaseNoteEditToolbarController;
import com.miui.notes.component.noteedit.INoteEditToolBar;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.editor.ReadOnlyActionListener;
import com.miui.notes.feature.noteedit.CommonNoteEditToolBarController;
import com.miui.notes.feature.noteedit.NoteFolderHeader;
import com.miui.notes.home.PrivateNotesListActivity;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.NoteStore;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.PackageNameUtils;
import com.miui.notes.ui.BaseActionBarController;
import com.miui.notes.ui.BaseDragListener;
import com.miui.notes.ui.EventController;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.notes.ui.widget.SharePopupGroup;
import com.miui.pad.feature.notes.meeting.MeetingRecognitionListener;
import com.miui.pad.feature.notes.meeting.MeetingRecognitionTask;
import com.miui.pad.feature.notes.meeting.MeetingRecognitionTaskImp;
import com.miui.pad.feature.notes.share.PadSnsEditActivity;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.miui.richeditor.theme.Theme;
import com.miui.todo.constant.TodoIntent;
import com.xiaomi.ai.mibrain.MibrainException;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.mirror.synergy.MiuiSynergySdk;
import defpackage.R2;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.app.AlertDialog;
import miuix.miuixbasewidget.widget.EmptyStateView;
import miuix.navigator.Navigator;
import miuix.os.Build;
import miuix.responsive.map.ScreenSpec;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PadHybridNoteEditFragment extends ColumnsHybridNoteEditFragment implements SpeechRecognitionListener {
    protected static final String TAG = "PadHybridNoteEditFragment";
    private InputMethodSubtype currentInputMethodSubtype;
    boolean isRegistered;
    private boolean isShowImageChooser;
    private MeetingRecognitionTask mAudioMeetingTask;
    private CountDownTimer mAudioRecordTimer;
    private IBaseNoteEditToolbarController mBarController;
    private EmptyStateView mBlankView;
    private final BaseDragListener mBlockDragListener;
    ConnectivityManager mConnMgr;
    private View mContentView;
    protected TextView mCountDownView;
    private long mCurrentNoteId;
    private WorkingNote mCurrentWorkingNote;
    private final BroadcastReceiver mDataUpdateTaskReceiver;
    protected CommonPadNoteAudioController mEditAudioController;
    Runnable mFocusChangeRunnable;
    Runnable mFocusInSearchRunnable;
    private InputMethodManager mInputMethodManager;
    private boolean mIsWarned;
    private Navigator.NavigatorStateListener mNavigatorStateListener;
    private final boolean mNeedHandleInput;
    private final NetworkCallbackImpl.NetworkCall mNetworkCall;
    NetworkCallbackImpl mNetworkCallback;
    private WorkingNote mOldworkingNote;
    private SpeechRecognitionTask mRecordAiTask;
    protected RecordModeChooserMenu mRecordModeChooserMenu;
    NetworkRequest mRequest;
    private final View.OnClickListener mToolbarClickListener;
    private MeetingRecognitionImpl meetingRecognitionListener;
    private OnBackPressedCallback onBackPressedCallback;
    private SpeechRecognitionImpl speechRecognitionListener;
    private static final long MAX_RECORD_TIME = TimeUnit.SECONDS.toMillis(300);
    private static final long LAST_WARRING_TIME = TimeUnit.SECONDS.toMillis(10);
    private static final long MAX_MEETING_RECORD_TIME = TimeUnit.SECONDS.toMillis(3600);
    private static boolean USE_MEETING_AUDIO = true;
    private final BlankViewDragListener mBlankViewDragListener = new BlankViewDragListener(0);
    protected boolean mIsNoteCreated = false;
    protected Runnable mPendingRefreshNoteTask = null;
    protected Runnable mPendingDataLoadTask = null;
    private boolean mWillBackFromInsertImage = false;
    private boolean mWillConvertToMindNote = false;
    protected Runnable mPendingDisableListenKeyboardTask = null;
    boolean viewCreate = false;
    private boolean mHasInBg = false;
    boolean isInputMethodChanged = false;
    private NoteLoadDataEntity mNoteLoadDataEntity = null;
    boolean mShowGalleryPicker = false;
    private long mOldNoteId = -1;
    private Intent mOldIntent = null;
    private final BroadcastReceiver mInputMethodChangeReceiver = new BroadcastReceiver() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.INPUT_METHOD_CHANGED")) {
                Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, "input method changed");
                PadHybridNoteEditFragment.this.isInputMethodChanged = true;
            }
        }
    };

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void checkEditEnable() {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getAutoSaveCommonDuration() {
        return 1500;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getLayoutResId() {
        return R.layout.pad_hybrid_note_edit_layout;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initPasswordController() {
    }

    @Override // miuix.appcompat.app.Fragment
    protected boolean isResponsiveEnabled() {
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.RecordTaskWorker
    public boolean isSupportRecordTaskWorker() {
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateWindowStatus(boolean z) {
    }

    public PadHybridNoteEditFragment() {
        NetworkCallbackImpl.NetworkCall networkCall = new NetworkCallbackImpl.NetworkCall() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.2
            @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
            public void onAvailable() {
                PadHybridNoteEditFragment.this.onNetworkStateChanged(true);
            }

            @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
            public void onLost() {
                PadHybridNoteEditFragment.this.onNetworkStateChanged(false);
            }
        };
        this.mNetworkCall = networkCall;
        this.mNetworkCallback = new NetworkCallbackImpl(networkCall);
        this.mRequest = new NetworkRequest.Builder().build();
        this.mConnMgr = (ConnectivityManager) NoteApp.getInstance().getSystemService("connectivity");
        this.isRegistered = false;
        this.mNeedHandleInput = true;
        this.mBlockDragListener = new BaseDragListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.3
            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragEntered(View view, DragEvent dragEvent) {
                return true;
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected void handleDragLocation(View view, DragEvent dragEvent) {
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragStarted(View view, DragEvent dragEvent) {
                return true;
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragDrop(View view, DragEvent dragEvent) {
                return PadHybridNoteEditFragment.this.mEditor.isValidDragEventData(dragEvent);
            }
        };
        this.mFocusChangeRunnable = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.doneEdit();
            }
        };
        this.mFocusInSearchRunnable = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        };
        this.mAudioRecordTimer = null;
        this.mToolbarClickListener = new View.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$1(view);
            }
        };
        this.mDataUpdateTaskReceiver = new BroadcastReceiver() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                PadHybridNoteEditFragment.this.updateWorkingNoteFromOuter();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (this.mActionMode != EditorActionMode.EDIT || getEditToolBarController() == null) {
            return;
        }
        getEditToolBarController().show(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        if (this.mWorkingNote == null || this.mWorkingNote.getIsInvalid()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ai /* 2131361915 */:
                if (RomUtils.isSupportAiText(getContext())) {
                    getEditToolBarController().hide(true);
                }
                showAttachmentAiChooser(view);
                break;
            case R.id.audio /* 2131362036 */:
                if (!Utils.isFastClick(view.getId(), 400L)) {
                    clickAudionBtnAboveOS2();
                    break;
                }
                break;
            case R.id.edit_image /* 2131362464 */:
                if (!this.mNoteRichEditor.canAddNewElement(63)) {
                    onContentOverLength(5);
                } else {
                    if (this.mEditorWebView != null) {
                        this.mEditorWebView.clearFocus();
                    }
                    createNewDoodle();
                }
                break;
            case R.id.gallery /* 2131362611 */:
                if (!this.mNoteRichEditor.canAddNewElement(63)) {
                    onContentOverLength(4);
                } else {
                    if (this.mEditorWebView != null) {
                        this.mEditorWebView.stopActionMode();
                    }
                    showAttachmentChooserDialog(view);
                    if (this.mIsRecording) {
                        this.mIsRecording = false;
                        updateActionMode(EditorActionMode.VIEW, true);
                    }
                }
                break;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onClickAudioBtnUnderOS2() {
        super.onClickAudioBtnUnderOS2();
        Logger.INSTANCE.d(TAG, "onClickAudioBtnUnderOS2-removeAiFloatingWindow,isRecording:" + this.mIsRecording);
        removeAiFloatingWindow();
        if (this.mIsRecording) {
            stopRecord();
            return;
        }
        if (!this.mNoteRichEditor.canAddNewElement(63)) {
            onContentOverLength(0);
            Logger.INSTANCE.e(TAG, "abort task!!!");
            return;
        }
        this.mActionFromToolbarRichText = false;
        if (PermissionUtils.supportNewPermissionStyle()) {
            onClickAudioBtn();
        } else {
            onClickAudioBtnOld();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected HybridEditorScriptInterface createJsBridge() {
        return new HybridEditorScriptInterface(this, 0);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected BaseActionBarController createHeaderBarController(View view) {
        if (RomUtils.isFoldDevice()) {
            return new FoldActionBarController(this, this);
        }
        return new PadActionBarController(this, this);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected IBaseNoteEditToolbarController createToolBarController(View view) {
        CommonNoteEditToolBarController commonNoteEditToolBarController = new CommonNoteEditToolBarController(getActivity(), view, this.mEditor);
        this.mBarController = commonNoteEditToolBarController;
        commonNoteEditToolBarController.setOnVisibleChangedListener(new INoteEditToolBar.OnVisibleChangedListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda6
            @Override // com.miui.notes.component.noteedit.INoteEditToolBar.OnVisibleChangedListener
            public final void onVisibilityChanged(boolean z) {
                this.f$0.lambda$createToolBarController$2(z);
            }
        });
        return this.mBarController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createToolBarController$2(boolean z) {
        View view = this.mContentView;
        if (view != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            layoutParams.bottomMargin = z ? NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_height) : 0;
            this.mContentView.setLayoutParams(layoutParams);
        }
    }

    public void showGalleryPicker() {
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController;
        if (!this.mShowGalleryPicker || (iBaseNoteEditToolbarController = this.mBarController) == null) {
            return;
        }
        iBaseNoteEditToolbarController.showGalleryPicker();
        this.mShowGalleryPicker = false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setWorkingNote(WorkingNote workingNote, boolean z) {
        super.setWorkingNote(workingNote, z);
        showGalleryPicker();
        long noteId = workingNote.getNoteId();
        this.mCurrentNoteId = noteId;
        this.mCurrentWorkingNote = workingNote;
        long j = this.mOldNoteId;
        if (j == -1) {
            this.mOldNoteId = noteId;
            this.mOldworkingNote = workingNote;
            trackReportOpenContent(this.mPendingHandleIntent, this.mCurrentWorkingNote);
            this.mOldIntent = this.mPendingHandleIntent;
            return;
        }
        if (j != noteId) {
            trackReportQuitContent(this.mOldIntent, this.mOldworkingNote);
            NoteContentStat.cancel();
            this.mStartTime = 0L;
            trackReportOpenContent(this.mPendingHandleIntent, this.mCurrentWorkingNote);
            this.mOldNoteId = this.mCurrentNoteId;
            this.mOldworkingNote = this.mCurrentWorkingNote;
            this.mOldIntent = this.mPendingHandleIntent;
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean isBlankViewShow() {
        return this.mIsShowBlank || this.mWorkingNote == null;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected INoteInfoHeader createNoteExtraInfoController(View view, View view2) {
        return new NoteFolderHeader(getContext(), view2);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        Logger.INSTANCE.d(TAG, "onCreate mPagePrepare: " + this.mPagePrepare + "," + this);
        this.mPagePrepare = false;
        this.mWorkingNote = null;
        super.onCreate(bundle);
        boolean z = true;
        USE_MEETING_AUDIO = !Build.IS_INTERNATIONAL_BUILD;
        if (this.mIsFromRebuild) {
            this.mWillBackFromInsertImage = false;
        }
        LiveDataEventBus.with("folderClick").observe(this, new Observer() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda21
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onCreate$3(obj);
            }
        });
        this.onBackPressedCallback = new OnBackPressedCallback(z) { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.5
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, "onBackPressedDispatcher,handleOnBackPressed");
                if (PadHybridNoteEditFragment.this.handleBackEvent() || PadHybridNoteEditFragment.this.dismissAiView()) {
                    return;
                }
                Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, "handleOnBackPressed,activity consume");
                NotesListActivity notesListActivity = (NotesListActivity) PadHybridNoteEditFragment.this.requireActivity();
                if (notesListActivity.getNavigator() != null && notesListActivity.getNavigator().getNavigationMode() == Navigator.Mode.C && notesListActivity.getNavigator().isContentOpen()) {
                    Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, "handleOnBackPressed,popBackStack");
                    PadHybridNoteEditFragment.this.handleBack();
                } else {
                    Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, "handleOnBackPressed,dispatcher onBackPressed");
                    setEnabled(false);
                    PadHybridNoteEditFragment.this.requireActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(Object obj) {
        if (isAdded() && this.mIsRecording) {
            if (isSupportAIRecorder()) {
                stopAIRecord();
            } else {
                stopRecord();
            }
        }
        if (RomUtils.isFoldDevice() && getContext() != null && UIUtils.isBrowseMode(getContext()) && this.mEditorWebView != null && this.mEditorWebView.isFocused()) {
            Logger.INSTANCE.d(TAG, "folderClick clear editorWebView focus");
            this.mEditorWebView.clearFocus();
        }
    }

    @Override // miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle bundle) {
        super.onUpdateArguments(bundle);
        if (Navigator.get(this) != null) {
            Navigator.get(this).requestFocus(true);
        }
        Logger.INSTANCE.d(TAG, "onUpdateArguments: ");
        if (bundle != null) {
            NoteLoadDataEntity noteLoadDataEntity = (NoteLoadDataEntity) bundle.getSerializable("entity");
            if ((!this.mInSearchMode || (this.mHandler != null && this.mHandler.hasMessages(1))) && this.mWorkingNote != null && noteLoadDataEntity != null && noteLoadDataEntity.getNoteId() == this.mWorkingNote.getNoteId()) {
                Logger.INSTANCE.i(TAG, "PadDetailFragment onUpdateArguments: no need reload notes. ");
                return;
            }
            if (this.mInAiMode && this.mEditor != null) {
                removeAiFloatingWindow();
                if (mNoteAiFloatingWindow != null && mNoteAiFloatingWindow.isLoading()) {
                    this.mNoteLoadDataEntity = noteLoadDataEntity;
                } else {
                    this.mNoteLoadDataEntity = null;
                    refreshNote(noteLoadDataEntity, false);
                }
            } else if (this.mInRefreshAnim) {
                this.mNoteLoadDataEntity = noteLoadDataEntity;
            } else {
                Logger.INSTANCE.d(TAG, "onUpdateArguments refresh: ");
                refreshNote(noteLoadDataEntity, false);
                this.mNoteLoadDataEntity = null;
            }
            Logger.INSTANCE.i(TAG, "onUpdateArguments refreshNote, mInAnim: " + this.mInRefreshAnim);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public void onStopDone() {
        super.onStopDone();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public void onRefreshDone() {
        super.onRefreshDone();
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onRefreshDone$4();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRefreshDone$4() {
        Logger.INSTANCE.i(TAG, "onRefreshDone refreshNote:" + (this.mNoteLoadDataEntity != null));
        NoteLoadDataEntity noteLoadDataEntity = this.mNoteLoadDataEntity;
        if (noteLoadDataEntity != null) {
            refreshNote(noteLoadDataEntity, false);
        }
        this.mNoteLoadDataEntity = null;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        setExtraHorizontalPaddingEnable(true);
        this.viewCreate = true;
        updateHomeButtonState(this.mScreenSpec);
        if (Navigator.get(this) != null) {
            ((ActionBarWithNaviController) this.mActionBarController).setNaviState(Navigator.get(this).isContentOpen());
        } else {
            ((ActionBarWithNaviController) this.mActionBarController).setNaviState(true);
        }
        this.mCurrentTheme = null;
        if (getActivity() != null) {
            updateSoftInputMode(getActivity());
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FrameConstant.ACTION_NOTE_DATA_UPDATE);
        this.mLocalBroadcastManager.registerReceiver(this.mDataUpdateTaskReceiver, intentFilter);
        boolean zHandleInstanceState = handleInstanceState(bundle);
        Logger.INSTANCE.d(TAG, "onViewInflated handleInstanceResult: " + zHandleInstanceState);
        if (!zHandleInstanceState) {
            preHandleIntent(this.mPendingHandleIntent);
        }
        Runnable runnable = this.mPendingRefreshNoteTask;
        if (runnable != null) {
            runnable.run();
            this.mPendingRefreshNoteTask = null;
        }
        initKeyboardHelper();
        if (!zHandleInstanceState && getArguments() != null && ((getActivity() != null && getActivity().getIntent() != null && !Objects.equals(getActivity().getIntent().getAction(), "android.intent.action.SEND")) || getActivity().getIntent() == null)) {
            NoteLoadDataEntity noteLoadDataEntity = (NoteLoadDataEntity) getArguments().getSerializable("entity");
            refreshNote(noteLoadDataEntity, false);
            if (noteLoadDataEntity != null && this.homeViewModel != null && this.homeViewModel.getSelectedNote() != null && noteLoadDataEntity.getNoteId() != this.homeViewModel.getSelectedNote().getNoteId()) {
                Logger.INSTANCE.d(TAG, "onViewInflated entityId = " + noteLoadDataEntity.getNoteId() + " selectedNoteId = " + this.homeViewModel.getSelectedNote().getNoteId());
                this.homeViewModel.postSelectNote(noteLoadDataEntity, false);
            }
        }
        if (this.mWorkingNoteFromRestore != null && TextUtils.isEmpty(this.mWorkingNoteFromRestore.getContent()) && TextUtils.isEmpty(this.mWorkingNoteFromRestore.getTitle()) && Navigator.get(this).getNavigationMode() == Navigator.Mode.C) {
            this.mIsNoteCreated = true;
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                this.mPendingHandleIntent = intent;
                preHandleIntent(this.mPendingHandleIntent);
            }
            if (getArguments() != null) {
                refreshNote((NoteLoadDataEntity) getArguments().getSerializable("entity"), false);
            }
        }
        Logger.INSTANCE.d(TAG, "mNeedHandleInput: true");
        this.homeViewModel.getKeyBoardShowStatus().observe(this, new Observer() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.keyBoardShowStatusChange(((Boolean) obj).booleanValue());
            }
        });
        this.homeViewModel.getToEditModeLiveData().observe(this, new Observer() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, "toEditModeLiveData: " + ((Boolean) obj));
            }
        });
        this.homeViewModel.getSearchOnFocusLiveData().observe(this, new Observer() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$6((Boolean) obj);
            }
        });
        this.homeViewModel.getWindowsFocus().observe(this, new Observer() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$8((Boolean) obj);
            }
        });
        IntentFilter intentFilter2 = new IntentFilter("android.intent.action.INPUT_METHOD_CHANGED");
        if (Utils.isAboveSDK34()) {
            getActivity().registerReceiver(this.mInputMethodChangeReceiver, intentFilter2, 2);
        } else {
            getActivity().registerReceiver(this.mInputMethodChangeReceiver, intentFilter2);
        }
        InputMethodManager inputMethodManager = (InputMethodManager) NoteApp.getInstance().getSystemService("input_method");
        this.mInputMethodManager = inputMethodManager;
        this.currentInputMethodSubtype = inputMethodManager.getCurrentInputMethodSubtype();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$6(Boolean bool) {
        if (bool.booleanValue()) {
            removeWebFocusChangeCallbacks();
            if (this.mActionMode != EditorActionMode.EDIT || getEditToolBarController() == null) {
                return;
            }
            getEditToolBarController().hide(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$8(Boolean bool) {
        if (this.mActionMode == EditorActionMode.EDIT && !getMIsKeyboardVisible() && bool.booleanValue() && !this.mIsRecording && this.mHandler != null) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onViewInflated$7();
                }
            }, 0L);
            return;
        }
        if (this.mActionMode == EditorActionMode.EDIT && !bool.booleanValue() && !getMIsKeyboardVisible()) {
            if (isSupportAIRecorder() && this.mIsRecording) {
                return;
            }
            Logger.INSTANCE.d(TAG, "doneEdit for focus change");
            doneEdit();
            return;
        }
        if (this.mActionMode == EditorActionMode.EDIT && bool.booleanValue() && !this.mIsRecording) {
            Logger.INSTANCE.d(TAG, "showToolBar on focus change");
            this.mEditToolbarController.show(!this.mEditor.getEditorTextIsEmpty());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$7() {
        if (this.mActionMode == EditorActionMode.EDIT) {
            Logger.INSTANCE.d(TAG, "show soft keyboard for focus change");
            Utils.showSoftInput(this.mEditorWebView);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Logger.INSTANCE.d(TAG, "onSaveInstanceState workingNote is null ? " + (this.mWorkingNote == null));
        if (this.mWorkingNote != null) {
            this.mIsNoteCreated = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void keyBoardShowStatusChange(boolean z) {
        if (isAdded()) {
            InputMethodSubtype currentInputMethodSubtype = this.mInputMethodManager.getCurrentInputMethodSubtype();
            boolean z2 = false;
            if (!z) {
                if (this.isInputMethodChanged) {
                    this.isInputMethodChanged = false;
                    this.currentInputMethodSubtype = currentInputMethodSubtype;
                    return;
                } else {
                    if (currentInputMethodSubtype == null) {
                        return;
                    }
                    if (!Objects.equals(currentInputMethodSubtype, this.currentInputMethodSubtype)) {
                        this.currentInputMethodSubtype = currentInputMethodSubtype;
                        Logger.INSTANCE.d(TAG, "input method subtype changed");
                        return;
                    }
                }
            } else {
                this.currentInputMethodSubtype = currentInputMethodSubtype;
            }
            setMIsKeyboardVisible(z);
            if (this.mEditor != null) {
                WebRichEditor webRichEditor = this.mEditor;
                if (z && Utils.isExternalKeyboardEnable(getContext()) && !UIUtils.isFreeFormScreen(getContext().getResources().getConfiguration())) {
                    z2 = true;
                }
                webRichEditor.onExternalKeyboardEnable(z2);
                this.mEditor.onKeyboardVisibilityChanged(z);
            }
            if (!z && this.mEditorWebView != null && this.mEditorWebView.isFocused()) {
                dismissAllDialogOrPanel();
            }
            if (!z || this.mNestedHeaderLayout == null) {
                return;
            }
            this.mNestedHeaderLayout.setAutoTriggerClose(true);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger.INSTANCE.d(TAG, "onresume");
        if (getActivity() != null && getActivity().getIntent() != null && TodoIntent.ACTION_VIEW.equals(getActivity().getIntent().getAction())) {
            NotesListActivity notesListActivity = (NotesListActivity) requireActivity();
            if (notesListActivity.getNavigator() != null && notesListActivity.getNavigator().getNavigationMode() == Navigator.Mode.C && notesListActivity.getNavigator().isContentOpen()) {
                onPerformAction(25, new Object[0]);
            } else {
                onPerformAction(30, new Object[0]);
            }
        }
        this.onBackPressedCallback.setEnabled(true);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        if (isInEditAction()) {
            notifyEditUiState(1);
        }
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
            this.mEditToolbarController.updateLayout();
        }
        RecordModeChooserMenu recordModeChooserMenu = this.mRecordModeChooserMenu;
        if (recordModeChooserMenu != null) {
            recordModeChooserMenu.dismiss();
        }
        this.homeViewModel.setToHomeFromOutMode(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (this.mWorkingNote != null && this.mHasInBg && isThisNoteDoExcerpt()) {
            WorkingNote workingNoteLoadNote = WorkingNote.loadNote("_id=" + this.mWorkingNote.getNoteId(), null);
            if (workingNoteLoadNote.getContent() != null && !workingNoteLoadNote.getContent().equals(this.mWorkingNote.getContent())) {
                Logger.INSTANCE.d(TAG, "onStart/setWorkingNote mWorkingNote:" + workingNoteLoadNote);
                this.mWorkingNote = workingNoteLoadNote;
                setWorkingNote(this.mWorkingNote, true);
            }
        }
        this.mHasInBg = false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        FragmentActivity activity;
        super.onPause();
        this.onBackPressedCallback.remove();
        boolean zIsSupportAIRecorder = isSupportAIRecorder();
        if (this.mIsRecording && !zIsSupportAIRecorder) {
            stopRecord();
        }
        notifyEditUiState(2);
        if (isInEditAction() && (!zIsSupportAIRecorder || !this.mIsRecording)) {
            updateActionMode(EditorActionMode.VIEW, false);
            this.mEditor.onKeyboardVisibilityChanged(false);
        }
        if (!RomUtils.isFoldDevice() || UIUtils.isFoldInternalScreen(NoteApp.getInstance()) || (activity = getActivity()) == null || activity.getWindow() == null || !UIUtils.isBrowseMode(activity)) {
            return;
        }
        activity.getWindow().clearFlags(131072);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public void onPagePrepared() {
        super.onPagePrepared();
        if (this.mHandler != null) {
            this.mHandler.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onPagePrepared$9();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPagePrepared$9() {
        if (this.mEditor != null) {
            this.mEditor.resetScale();
            saveDoodle();
            if (getActivity() != null) {
                if (UIUtils.isBrowseMode(getActivity())) {
                    Logger.INSTANCE.d(TAG, "onPagePrepared,setEnableEdit false");
                    setEnableEdit(false);
                } else {
                    Logger.INSTANCE.d(TAG, "onPagePrepared,setEnableEdit true");
                    setEnableEdit(true);
                }
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public boolean isNoteCreated() {
        return this.mIsNoteCreated;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mHasInBg = true;
        if (FloatWindowManagerAgent.isContinuousExcerpt() && isThisNoteDoExcerpt()) {
            FloatWindowManagerAgent.resumeContinuousExcerpt();
        }
        saveNoteInstantlyIfNeeded();
        this.mBarController.dismissPopupWindow();
        if (this.mActionMode == EditorActionMode.VIEW && this.mEditorWebView != null && !this.mIsRecording) {
            this.mEditorWebView.clearFocus();
        }
        if (this.mIsRecording && isSupportAIRecorder()) {
            stopAIRecord();
        } else if (this.mIsRecording) {
            stopRecord();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Logger.INSTANCE.e(TAG, "onDestroyView");
        if (isInEditAction()) {
            updateActionMode(EditorActionMode.VIEW, true);
        }
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mBarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.setOnVisibleChangedListener(null);
            this.mBarController.onDestroy();
        }
        deleteEmptyNote();
        boolean z = this.homeViewModel != null && this.homeViewModel.isSearchMode();
        FragmentActivity activity = getActivity();
        if (getMIsKeyboardVisible() && activity != null && !z) {
            Utils.hideSoftInput(activity);
        }
        this.mIsNoteCreated = false;
        if (getActivity() != null) {
            try {
                getActivity().unregisterReceiver(this.mInputMethodChangeReceiver);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "onDestroyView, unregisterReceiver error: " + e);
            }
        }
        if (this.mNavigatorStateListener != null) {
            Navigator.get(this).removeNavigatorStateListener(this.mNavigatorStateListener);
        }
    }

    private void deleteEmptyNote() {
        if (isDataEmpty()) {
            NoteStore.delete(NoteApp.getInstance(), new long[]{this.mWorkingNote.getNoteId()});
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Intent intent;
        if (this.mEditorWebView != null) {
            this.mEditorWebView.removeCallbacks(this.mFocusChangeRunnable);
            this.mEditorWebView.setKeyShortCutListener(null);
            this.mEditorWebView.setOnFocusChangeListener(null);
            this.mEditorWebView.setOnDragListener(null);
        }
        if (!this.mWillConvertToMindNote && !this.mWillBackFromInsertImage) {
            killSaveTask();
        } else {
            this.mWillConvertToMindNote = false;
            this.mWillBackFromInsertImage = false;
        }
        DoodleApplication.cancelAndDeletePreloadData();
        SpeechRecognitionTask speechRecognitionTask = this.mRecordAiTask;
        if (speechRecognitionTask != null) {
            speechRecognitionTask.destroy();
            CountDownTimer countDownTimer = this.mAudioRecordTimer;
            if (countDownTimer != null) {
                try {
                    countDownTimer.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            this.mLocalBroadcastManager.unregisterReceiver(this.mDataUpdateTaskReceiver);
        } catch (Exception e2) {
            Logger.INSTANCE.e(TAG, "error in onDestroy " + e2);
        }
        FragmentActivity activity = getActivity();
        if (activity != null && (intent = activity.getIntent()) != null) {
            intent.setAction("android.intent.action.MAIN");
            activity.setIntent(intent);
        }
        if (this.mPendingHandleIntent != null) {
            this.mPendingHandleIntent.setAction("android.intent.action.MAIN");
        }
        View view = this.mContentView;
        if (view != null) {
            Folme.clean(view);
        }
        EmptyStateView emptyStateView = this.mBlankView;
        if (emptyStateView != null) {
            Folme.clean(emptyStateView);
        }
        if (getActionBarController() != null) {
            getActionBarController().resetButtonAnimState();
            if (getActionBarController().getHeadInfo() != null) {
                Folme.clean(getActionBarController().getHeadInfo());
            }
        }
        if (this.mEditorWebView != null) {
            Folme.clean(this.mEditorWebView);
        }
        super.onDestroy();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        if (dismissAllDialogOrPanel()) {
            return false;
        }
        boolean zOnBackPressed = this.mEditToolbarController.onBackPressed();
        if (this.mActionMode == EditorActionMode.EDIT_RECODE) {
            updateActionMode(EditorActionMode.VIEW, true);
            zOnBackPressed = true;
        }
        if (zOnBackPressed && isInEditAction()) {
            updateActionMode(EditorActionMode.VIEW, true);
        }
        if (!zOnBackPressed) {
            if (this.mEditor != null) {
                this.mEditor.stopPlayingMedia();
                this.mEditor.pauseVoice();
            }
            this.mIsKillSaveTaskBeforeDestroy = true;
            killSaveTask();
        }
        return zOnBackPressed;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void updateWorkingNoteFromOuter() {
        if (this.mWorkingNote != null) {
            Logger.INSTANCE.d(TAG, "updateWorkingNoteFromOuter/set working note = " + this.mWorkingNote.getNoteId());
            this.mWorkingNote = WorkingNote.loadNote("_id=" + this.mWorkingNote.getNoteId(), null);
            this.mWorkingNote.setInPadMode(true);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleIntentData(Intent intent) {
        if (intent == null) {
            return true;
        }
        String action = intent.getAction();
        boolean z = false;
        if ("android.intent.action.VIEW".equals(action)) {
            this.mIsNoteCreated = false;
            long extraNoteIdFromIntent = getExtraNoteIdFromIntent(intent);
            long longExtra = intent.getLongExtra("com.miui.notes.folder_id", 0L);
            long longExtra2 = intent.getLongExtra("modified_time", Long.MIN_VALUE);
            String searchExtraDataKeyFromIntent = getSearchExtraDataKeyFromIntent(intent);
            this.mFolderId = longExtra;
            if (extraNoteIdFromIntent > 0) {
                boolean z2 = this.mWorkingNote == null || this.mWorkingNote.getNoteId() != extraNoteIdFromIntent;
                boolean z3 = (this.mWorkingNote == null || this.mWorkingNote.getFolderId() == longExtra || longExtra == -4097) ? false : true;
                if (z2 || z3 || this.viewCreate) {
                    if (getEditToolBarController() != null) {
                        getEditToolBarController().hide(false);
                    }
                    this.mSearchText = "";
                    this.mActionMode = null;
                    getActionBarController().resetButtonAnimState();
                    getActionBarController().resumeHoverEffect();
                    processLastWorkingNote(extraNoteIdFromIntent);
                    showBlankView(false);
                    if (z2 || this.viewCreate) {
                        resetEditContent();
                        this.mLoadTask = new BaseNormalNoteEditFragment.ViewNoteLoadTask(intent, extraNoteIdFromIntent);
                        this.mLoadTask.commit();
                    }
                    updateActionMode(EditorActionMode.VIEW, false);
                } else if ((this.mLoadTask == null || !(this.mLoadTask instanceof UpdateNoteLoadTask)) && this.mWorkingNote != null && (this.mWorkingNote.getModifiedDate() < longExtra2 || !TextUtils.equals(this.mSearchText, searchExtraDataKeyFromIntent))) {
                    if (this.mLoadTask != null) {
                        this.mLoadTask.cancel();
                    }
                    this.mLoadTask = new UpdateNoteLoadTask(intent, extraNoteIdFromIntent);
                    this.mLoadTask.commit();
                }
            } else {
                onDataEmpty();
            }
            return true;
        }
        if ("android.intent.action.EDIT".equals(action)) {
            handleEditIntent(intent);
            long longExtra3 = intent.getLongExtra(Notes.Intents.INTENT_EXTRA_CALL_DATE, 0L);
            if (longExtra3 != 0 && longExtra3 == PreferenceUtils.getLastCallNoteData()) {
                z = true;
            }
            this.mIsNoteCreated = !z;
        } else {
            super.handleIntentData(intent);
        }
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleSendIntent(Intent intent) {
        Logger.INSTANCE.d(TAG, "handleSendIntent: ");
        processLastWorkingNote(-2L);
        this.mIsNoteCreated = false;
        this.mLoadTask = new HybridSendNoteLoadTask(intent);
        Intent intent2 = requireActivity().getIntent();
        if (intent2 != null && "android.intent.action.SEND".equals(intent2.getAction())) {
            intent2.setAction("android.intent.action.MAIN");
            requireActivity().setIntent(intent2);
            Logger.INSTANCE.d(TAG, "handleSendIntent: set activity action main");
        }
        this.mLoadTask.commit();
        return true;
    }

    public class HybridSendNoteLoadTask extends BaseNormalNoteEditFragment.SendNoteLoadTask {
        public HybridSendNoteLoadTask(Intent intent) {
            super(intent);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public WorkingNote doLoad() {
            WorkingNote workingNoteLoadWorkingNote = loadWorkingNote();
            if (workingNoteLoadWorkingNote != null && !PadHybridNoteEditFragment.this.isWorkingNoteIdInvalid(workingNoteLoadWorkingNote.getNoteId())) {
                return workingNoteLoadWorkingNote;
            }
            WorkingNote workingNote = parseWorkingNote();
            if (workingNote != null && !workingNote.saveNote(true)) {
                workingNote.setInPadMode(true);
                workingNote.createNoteByPad(true);
            }
            return workingNote;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, WorkingNote workingNote) {
            if (workingNote == null) {
                return;
            }
            super.onResult(taskManager, workingNote);
            Bundle bundle = new Bundle();
            bundle.putLong("note_id", workingNote.getNoteId());
            PadHybridNoteEditFragment.this.notifyAction(10, bundle);
            PadHybridNoteEditFragment.this.homeViewModel.postSelectNote(new NoteLoadDataEntity(workingNote.getNoteId(), workingNote.getFolderId(), "", 0, workingNote.getModifiedDate(), false, null), false);
            if (TextUtils.isEmpty(this.sendContent)) {
                if (PadHybridNoteEditFragment.this.mEditor != null && PadHybridNoteEditFragment.this.mEditor.getView() != null && PadHybridNoteEditFragment.this.mEditor.getView().hasFocus()) {
                    PadHybridNoteEditFragment.this.mNeedDelayShowInput = true;
                    PadHybridNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                }
            } else {
                PadHybridNoteEditFragment.this.updateActionMode(EditorActionMode.VIEW, true);
            }
            NotesTextStat.isCreateCommonNote = true;
            if (PadHybridNoteEditFragment.this.mBlankView == null || PadHybridNoteEditFragment.this.mBlankView.getAlpha() == 0.0f) {
                return;
            }
            PadHybridNoteEditFragment.this.showBlankView(false);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleEditIntent(final Intent intent) {
        processLastWorkingNote(getExtraNoteIdFromIntent(intent));
        boolean booleanExtra = intent.getBooleanExtra(NoteIntent.KEY_IS_PENDING, false);
        this.mIsFromPrivate = getActivity() instanceof PrivateNotesListActivity;
        this.mIsFromFloatWindow = intent.getBooleanExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, false);
        if (this.mIsFromFloatWindow) {
            this.mNeedDelayShowInput = true;
        }
        this.mPendingEditIntentTask = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleEditIntent$10(intent);
            }
        };
        if (!booleanExtra) {
            if (this.mHandler != null) {
                this.mHandler.sendEmptyMessage(4);
            }
            if (this.mEditorWebView != null) {
                this.mEditorWebView.performClick();
            }
        }
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.setNeedDelayShowInput(this.mNeedDelayShowInput);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleEditIntent$10(Intent intent) {
        showBlankView(false);
        this.mLoadTask = new PadHybridEditNoteLoadTask(intent);
        this.mLoadTask.commit();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showSharePopupWindow(Intent intent) {
        if (this.mSharePopupWindow != null && this.mSharePopupWindow.isShowing()) {
            this.mSharePopupWindow.dismiss();
        }
        if (getActivity() == null || getView() == null) {
            return;
        }
        this.mSharePopupWindow = new SharePopupGroup(getActivity(), UIUtils.inflateView((ViewGroup) getView(), R.layout.share_layout), ResourceManager.getTheme(0));
        Rect rect = new Rect();
        getView().getLocalVisibleRect(rect);
        getView().getRootView().findViewById(R.id.navigation_decor_wrapper).setImportantForAccessibility(4);
        getView().getRootView().findViewById(R.id.header).setImportantForAccessibility(4);
        getView().getRootView().findViewById(R.id.fragment_content).setImportantForAccessibility(4);
        getView().getRootView().findViewById(R.id.action_bar_container).setImportantForAccessibility(4);
        getView().getRootView().findViewById(R.id.root).setImportantForAccessibility(4);
        this.mSharePopupWindow.queryInfo(intent);
        this.mSharePopupWindow.setOnIntentSelectedListener(new ResolverAdapter.OnIntentSelectedListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda22
            @Override // com.miui.notes.adapter.ResolverAdapter.OnIntentSelectedListener
            public final void onIntentSelected(Intent intent2) {
                this.f$0.lambda$showSharePopupWindow$11(intent2);
            }
        });
        this.mSharePopupWindow.updatePaddingBottom(this.mInsetBottom);
        this.mSharePopupWindow.show(rect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSharePopupWindow$11(Intent intent) {
        if (this.mSharePopupWindow != null && this.mSharePopupWindow.isShowing()) {
            this.mSharePopupWindow.dismiss();
        }
        if (isIgnoreNewTask(intent.getComponent().getPackageName())) {
            intent.removeFlags(DataSpace.RANGE_LIMITED);
        }
        if (getActivity() == null || getView() == null) {
            return;
        }
        startActivity(intent);
    }

    private boolean isIgnoreNewTask(String str) {
        return PackageNameUtils.isWechatPackage(str);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(final NoteLoadDataEntity noteLoadDataEntity, boolean z) {
        Logger.INSTANCE.d(TAG, "refreshNote " + z);
        if (this.mEditor != null) {
            this.mEditor.removeSelectionColorBg();
        }
        if (!isAdded()) {
            this.mPendingRefreshNoteTask = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.6
                @Override // java.lang.Runnable
                public void run() {
                    PadHybridNoteEditFragment.this.refreshNote(noteLoadDataEntity, false);
                }
            };
            return;
        }
        if (this.mIsRecording) {
            if (noteLoadDataEntity == null || this.mWorkingNote == null || noteLoadDataEntity.getNoteId() != this.mWorkingNote.getNoteId() || noteLoadDataEntity.getModifiedTime() > this.mWorkingNote.getModifiedDate()) {
                this.mPendingDataLoadTask = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.7
                    @Override // java.lang.Runnable
                    public void run() {
                        PadHybridNoteEditFragment.this.refreshNote(noteLoadDataEntity, false);
                    }
                };
                onAudioButtonClick();
            }
            if (!isSupportAIRecorder()) {
                return;
            }
        }
        if (this.mNoteId != FloatWindowManagerAgent.getExcerptId()) {
            dismissAllDialogOrPanel();
        }
        if (noteLoadDataEntity != null) {
            try {
                if (noteLoadDataEntity.getShowGalleryPicker()) {
                    this.mShowGalleryPicker = true;
                    noteLoadDataEntity.setShowGalleryPicker(false);
                }
                loadNoteFromEntity(noteLoadDataEntity);
                if (this.mPagePrepare) {
                    handleIntentData(this.mPendingHandleIntent);
                    return;
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.mPendingHandleIntent = null;
        if (this.mLoadTask != null) {
            this.mLoadTask.cancel();
            this.mLoadTask = null;
        }
        if (FloatWindowManagerAgent.isContinuousExcerpt() && isThisNoteDoExcerpt()) {
            FloatWindowManagerAgent.resumeContinuousExcerpt();
        }
        resetEditContent();
        if (!Navigator.get(this).isContentOpen()) {
            View view = new View(getContext());
            view.setId(R.id.home);
            this.mActionBarController.onClick(view);
        }
        onDataEmpty();
    }

    @Override // com.miui.notes.basefeature.noteedit.ColumnsHybridNoteEditFragment
    protected void onDataEmpty() {
        super.onDataEmpty();
        clearData();
        Logger.INSTANCE.d(TAG, "onDataEmpty mode: " + Navigator.get(this).getNavigationMode() + " isFocused: " + Navigator.get(this).isFocused());
        if (Navigator.get(this).getNavigationMode().equals(Navigator.Mode.C) && Navigator.get(this).isFocused()) {
            handleBack();
        }
    }

    public void clearData() {
        if (this.mNoteRichEditor != null) {
            this.mNoteRichEditor.setData("", "", "", false, null);
        }
    }

    public class PadHybridEditNoteLoadTask extends BaseNormalNoteEditFragment.EditNoteLoadTask {
        public PadHybridEditNoteLoadTask(Intent intent) {
            super(intent);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public WorkingNote doLoad() {
            WorkingNote workingNoteLoadWorkingNote = loadWorkingNote();
            if (workingNoteLoadWorkingNote == null && (workingNoteLoadWorkingNote = parseWorkingNote()) != null) {
                workingNoteLoadWorkingNote.setInPadMode(true);
                workingNoteLoadWorkingNote.createNoteByPad(true);
                Bundle bundle = new Bundle();
                bundle.putLong("note_id", workingNoteLoadWorkingNote.getNoteId());
                PadHybridNoteEditFragment.this.notifyAction(10, bundle);
                PadHybridNoteEditFragment.this.homeViewModel.postSelectNote(new NoteLoadDataEntity(workingNoteLoadWorkingNote.getNoteId(), workingNoteLoadWorkingNote.getFolderId(), "", 0, workingNoteLoadWorkingNote.getModifiedDate(), false, null), false);
            }
            return workingNoteLoadWorkingNote;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onInsertAudio() {
            FragmentActivity activity = PadHybridNoteEditFragment.this.getActivity();
            if (activity == null || !activity.isInMultiWindowMode()) {
                PadHybridNoteEditFragment.this.startRecordWithCheckFromEditTask();
            } else {
                Toast.makeText(NoteApp.getInstance(), R.string.toast_refuse_to_record_when_in_multi_window_new, 0).show();
            }
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onEnterEdit(WorkingNote workingNote) {
            if (this.intent.getExtras() == null) {
                PadHybridNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                return;
            }
            EditorActionMode editorActionMode = (EditorActionMode) this.intent.getExtras().get(NoteIntent.KEY_ACTION_MODE);
            if (editorActionMode != null && editorActionMode.equals(EditorActionMode.EDIT_RECODE)) {
                PadHybridNoteEditFragment.this.startRecordWithCheckFromEditTask();
                return;
            }
            PadHybridNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, false);
            String stringExtra = this.intent.getStringExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE);
            if (!TextUtils.isEmpty(stringExtra)) {
                PadHybridNoteEditFragment.this.mEditor.setTitle(stringExtra);
            }
            AiActionUtil.asyncNotifyResult(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleBackEvent() {
        Logger.INSTANCE.d(TAG, "handleBackEvent");
        this.mIsReport = false;
        NoteContentStat.QUIT_WAY = NoteContentStat.QUIT_WAY_BTN_BACK;
        NoteContentStat.IS_BTN_BACK = true;
        removeAiFloatingWindow();
        if (!this.mIsRecording) {
            doneEdit();
            if (this.mEditToolbarController != null) {
                this.mEditToolbarController.setTextStylePanelVisible(false, false);
                this.mEditToolbarController.hideWithoutAnim();
            }
            return false;
        }
        if (isSupportAIRecorder()) {
            stopAIRecord();
        } else {
            stopRecord();
        }
        this.mIsRecording = false;
        if (!RomUtils.isInternationalBuild()) {
            if (isUseMeetingAudio()) {
                this.mEditAudioController.dismiss();
            } else if (this.mEditToolbarController != null) {
                this.mEditToolbarController.toggleAudioRecordPanel();
            }
        }
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.ui.BaseActionBarController.EditCallback
    public void onPerformAction(int i, Object... objArr) {
        if (i == 25) {
            if (Utils.isFastClick(i, 200L)) {
                Logger.INSTANCE.i(TAG, "isFastClick return");
                return;
            }
            Logger.INSTANCE.i(TAG, "onPerformAction ACTION_HOME");
            handleBackEvent();
            NotesListActivity notesListActivity = (NotesListActivity) requireActivity();
            if (notesListActivity.getNavigator().getNavigationMode() == Navigator.Mode.C && notesListActivity.getNavigator().isContentOpen()) {
                handleBack();
                return;
            } else {
                Logger.INSTANCE.i(TAG, "handleBackPress, content  not open,mode:" + notesListActivity.getNavigator().getNavigationMode());
                return;
            }
        }
        if (i == 30) {
            preBeforeOpenOrCloseNavigation();
            if (Navigator.get(this).isContentOpen()) {
                return;
            }
            setNaviState(true);
            Navigator.get(this).openContent();
            return;
        }
        if (i == 31) {
            preBeforeOpenOrCloseNavigation();
            if (Navigator.get(this).isContentOpen()) {
                setNaviState(false);
                Navigator.get(this).closeContent();
                return;
            }
            return;
        }
        super.onPerformAction(i, objArr);
    }

    private void preBeforeOpenOrCloseNavigation() {
        if (this.mIsRecording) {
            if (isSupportAIRecorder()) {
                stopAIRecord();
            } else {
                stopRecord();
            }
        }
        this.mEditorWebView.clearFocus();
        Utils.hideSoftInput(this.mEditorWebView);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void doDeleteNote() {
        Logger.INSTANCE.i(TAG, "doDeleteNote");
        if (this.mHandler != null && this.mActionBarController != null && ((ActionBarWithNaviController) this.mActionBarController).getNaviState()) {
            ((ActionBarWithNaviController) this.mActionBarController).showOrHideNaviState(true);
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.deleteNote();
                }
            }, 200L);
        } else {
            deleteNote();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean loadNoteFromEntity(NoteLoadDataEntity noteLoadDataEntity) {
        boolean zEquals;
        EmptyStateView emptyStateView = this.mBlankView;
        if (emptyStateView != null) {
            emptyStateView.setAlpha(0.0f);
        }
        if (this.mExecutorService.isShutdown()) {
            this.mExecutorService = Utils.newDiscardPolicySingleThreadExecutor();
        }
        Intent intent = noteLoadDataEntity.toIntent();
        Intent intent2 = getActivity() != null ? getActivity().getIntent() : null;
        String action = "";
        if (noteLoadDataEntity.getNoteId() == -2) {
            this.mSearchText = "";
            this.mActionMode = null;
            getActionBarController().resetButtonAnimState();
            getActionBarController().resumeHoverEffect();
            resetEditContent();
            this.mEditor.clearFocus();
            if (intent2 != null) {
                zEquals = "android.intent.action.SEND".equals(intent2.getAction());
                if (NoteIntent.ACTION_PAD_INSERT_OR_EDIT.equals(intent2.getAction())) {
                    if (NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE.equals(intent2.getType())) {
                        intent.setType(NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE);
                    }
                    if (NoteIntent.ACTION_CREATE_NOTE.equals(intent2.getType())) {
                        intent.putExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE, getActivity().getIntent().getStringExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE));
                        getActivity().getIntent().putExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE, "");
                    }
                    intent2.setAction("android.intent.action.MAIN");
                }
            } else {
                zEquals = false;
            }
            long longExtra = intent.getLongExtra(Notes.Intents.INTENT_EXTRA_CALL_DATE, 0L);
            boolean z = longExtra != 0 && longExtra == PreferenceUtils.getLastCallNoteData();
            if (!zEquals) {
                intent.setAction("android.intent.action.EDIT");
                this.mPendingHandleIntent = intent;
                boolean z2 = (this.mPagePrepare || this.mWorkingNoteFromRestore == null || TextUtils.isEmpty(this.mWorkingNoteFromRestore.getContent())) ? false : true;
                Logger.INSTANCE.d(TAG, "loadNoteFromEntity isNoteFromRestore = " + z2);
                if (!z && this.mHandler != null && !z2) {
                    this.mHandler.sendEmptyMessage(4);
                    this.mIsNoteCreated = true;
                } else {
                    this.mIsNoteCreated = false;
                }
            } else {
                this.mPendingHandleIntent = new Intent(getActivity().getIntent());
                this.mPendingHandleIntent.putExtra("com.miui.notes.folder_id", noteLoadDataEntity.getFolderId());
                if ("android.intent.action.SEND".equals(getActivity().getIntent().getAction())) {
                    getActivity().getIntent().setAction("android.intent.action.MAIN");
                } else {
                    getActivity().setIntent(null);
                }
            }
        } else {
            if (getActivity() != null && getActivity().getIntent() != null) {
                action = getActivity().getIntent().getAction();
            }
            boolean zLastWorkingNoteIsEmpty = lastWorkingNoteIsEmpty();
            if (!"android.intent.action.SEND".equals(action)) {
                intent.setAction("android.intent.action.VIEW");
                this.mPendingHandleIntent = intent;
                if (this.mNoteId != FloatWindowManagerAgent.getExcerptId() && !zLastWorkingNoteIsEmpty) {
                    updateActionMode(EditorActionMode.VIEW, false);
                }
            }
        }
        if (this.mLoadTask == null && this.mContentView.getAlpha() == 0.0f) {
            this.mContentView.setAlpha(1.0f);
            this.mContentView.setVisibility(0);
            this.mContentView.requestLayout();
        }
        return false;
    }

    private void onAudioButtonClick() {
        if (this.mIsRecording) {
            if (isUseMeetingAudio()) {
                this.mEditAudioController.dismiss();
            } else {
                this.mEditToolbarController.toggleAudioRecordPanel();
            }
            updateActionMode(EditorActionMode.VIEW, true);
            return;
        }
        if (!this.mNoteRichEditor.canAddNewElement(63)) {
            onContentOverLength(0);
            return;
        }
        this.mActionFromToolbarRichText = false;
        if (PermissionUtils.supportNewPermissionStyle()) {
            onClickAudioBtn();
        } else {
            onClickAudioBtnOld();
        }
    }

    protected void onClickAudioBtnOld() {
        Logger.INSTANCE.d(TAG, "onClickAudioBtnOld");
        String[] strArr = {"android.permission.RECORD_AUDIO"};
        ArrayList arrayList = new ArrayList();
        if (PermissionUtils.shouldOnceRequestPermission(getActivity(), strArr[0])) {
            arrayList.add(strArr[0]);
        }
        if (arrayList.size() == 0) {
            if (isAdded()) {
                if (Utils.isInCommunication(getActivity())) {
                    Toast.makeText(getActivity(), R.string.toast_refuse_to_record_when_in_call, 0).show();
                    return;
                } else if (PermissionUtils.isPermissionNotGranted(NoteApp.getInstance(), "android.permission.RECORD_AUDIO")) {
                    Toast.makeText(getActivity(), R.string.toast_refuse_to_use_record_audio, 0).show();
                    return;
                } else {
                    handleAudioCTARequest();
                    return;
                }
            }
            return;
        }
        String[] strArr2 = new String[arrayList.size()];
        arrayList.toArray(strArr2);
        requestPermissions(strArr2, 2);
    }

    public boolean isUseMeetingAudio() {
        return USE_MEETING_AUDIO;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initView(View view) {
        super.initView(view);
        view.setOnDragListener(this.mBlockDragListener);
        View viewFindViewById = view.findViewById(R.id.sv_note_edit);
        this.mContentView = viewFindViewById;
        viewFindViewById.setAlpha(0.0f);
        this.mContentView.setVisibility(8);
        this.mBlankView = (EmptyStateView) view.findViewById(R.id.pad_edit_blank_view);
        this.mBlankViewDragListener.setEditFragment(this);
        this.mBlankView.setOnDragListener(this.mBlankViewDragListener);
        smallWindowAdapter(this.mContentView);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void initNoteEditor(View view, int i) {
        Logger.INSTANCE.d(TAG, "pad initNoteEditor: " + i);
        super.initNoteEditor(view, i);
        this.mEditorWebView.setIsPcMode(false);
        this.mEditorWebView.setKeyShortCutListener(new OnKeyDownAndShortCutListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.8
            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyShortcut(int i2, KeyEvent keyEvent) {
                return false;
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Code duplicated, block: B:148:0x0308  */
            /* JADX WARN: Code duplicated, block: B:150:0x0312  */
            /* JADX WARN: Code duplicated, block: B:152:0x031c  */
            /* JADX WARN: Code duplicated, block: B:45:0x00ee  */
            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyDown(int i2, KeyEvent keyEvent) {
                boolean zDoShortcutCopy;
                Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, "shortcut key event,keycode: " + i2 + ",isAdd()" + PadHybridNoteEditFragment.this.isAdded() + ",isRecording:" + PadHybridNoteEditFragment.this.mIsRecording);
                if (PadHybridNoteEditFragment.this.isAdded() && (!PadHybridNoteEditFragment.this.mIsRecording || (keyEvent.hasModifiers(4098) && i2 == 50))) {
                    if (PadHybridNoteEditFragment.this.mEditorWebView.isFocused()) {
                        PadHybridNoteEditFragment.this.mEditToolbarController.onFocusAble(false);
                        if (keyEvent.hasModifiers(4096) && keyEvent.getRepeatCount() == 0) {
                            if (i2 == 41) {
                                if (!Utils.isFastClick(0, 500L)) {
                                    PadHybridNoteEditFragment.this.homeViewModel.createMindNote();
                                }
                                return true;
                            }
                            if (i2 == 42) {
                                if (!Utils.isFastClick(0, 500L)) {
                                    PadHybridNoteEditFragment.this.homeViewModel.createNormalNote();
                                }
                                return true;
                            }
                        }
                        boolean zCanInsert = PadHybridNoteEditFragment.this.mEditor != null ? PadHybridNoteEditFragment.this.mEditor.canInsert() : true;
                        if (keyEvent.hasModifiers(1)) {
                            if (i2 != 61) {
                                zDoShortcutCopy = false;
                            } else {
                                PadHybridNoteEditFragment.this.mEditor.setUndent();
                                zDoShortcutCopy = true;
                            }
                        } else if (i2 != 61 || PadHybridNoteEditFragment.this.mActionMode == EditorActionMode.EDIT_TITLE) {
                            zDoShortcutCopy = false;
                        } else {
                            PadHybridNoteEditFragment.this.mEditor.setIndent();
                            zDoShortcutCopy = true;
                        }
                        if (keyEvent.hasModifiers(4096)) {
                            if (i2 == 39) {
                                PadHybridNoteEditFragment.this.mEditor.setCheckbox();
                            } else if (i2 == 40) {
                                PadHybridNoteEditFragment.this.mEditor.setAlignNormal();
                            } else if (i2 != 46) {
                                if (i2 == 49) {
                                    PadHybridNoteEditFragment.this.mBarController.underline();
                                } else if (i2 == 50) {
                                    if (PadHybridNoteEditFragment.this.mActionMode == EditorActionMode.EDIT_TITLE) {
                                        return false;
                                    }
                                    if (PadHybridNoteEditFragment.this.mActionMode == EditorActionMode.VIEW) {
                                        return true;
                                    }
                                    PadHybridNoteEditFragment.this.mEditorWebView.doPaste();
                                } else if (i2 != 71) {
                                    if (i2 == 72) {
                                        PadHybridNoteEditFragment.this.mEditor.increaseFontSize();
                                    } else {
                                        switch (i2) {
                                            case 30:
                                                PadHybridNoteEditFragment.this.mBarController.bold();
                                                break;
                                            case 31:
                                                if (PadHybridNoteEditFragment.this.mShowingPopupWindow == null || !PadHybridNoteEditFragment.this.mShowingPopupWindow.isShowing() || PadHybridNoteEditFragment.this.mShowingPopupWindow.getContentView().findViewById(R.id.copy_image) == null) {
                                                    zDoShortcutCopy = PadHybridNoteEditFragment.this.mEditorWebView.doShortcutCopy();
                                                } else {
                                                    PadHybridNoteEditFragment.this.mShowingPopupWindow.getContentView().findViewById(R.id.copy_image).performClick();
                                                    return true;
                                                }
                                                break;
                                            case 32:
                                                PadHybridNoteEditFragment.this.mBarController.strikeThrough();
                                                break;
                                            case 33:
                                                if (!PadHybridNoteEditFragment.this.mEditToolbarController.isCenter()) {
                                                    PadHybridNoteEditFragment.this.mEditor.setAlignCenter();
                                                }
                                                break;
                                            default:
                                                switch (i2) {
                                                    case 35:
                                                        PadHybridNoteEditFragment.this.mEditor.setEditorBullet();
                                                        break;
                                                    case 36:
                                                        PadHybridNoteEditFragment.this.mBarController.bgHighLight();
                                                        break;
                                                    case 37:
                                                        PadHybridNoteEditFragment.this.mBarController.italic();
                                                        break;
                                                    default:
                                                        switch (i2) {
                                                            case 52:
                                                                if (PadHybridNoteEditFragment.this.mShowingPopupWindow == null || !PadHybridNoteEditFragment.this.mShowingPopupWindow.isShowing() || PadHybridNoteEditFragment.this.mShowingPopupWindow.getContentView().findViewById(R.id.copy_image) == null) {
                                                                    zDoShortcutCopy = PadHybridNoteEditFragment.this.mEditorWebView.doShortcutCut();
                                                                } else {
                                                                    PadHybridNoteEditFragment.this.mShowingPopupWindow.getContentView().findViewById(R.id.copy_image).performClick();
                                                                    PadHybridNoteEditFragment.this.mEditorWebView.evaluateJavascript("javascript:pastePlainText(" + new Gson().toJson("") + ")", null);
                                                                    return true;
                                                                }
                                                                break;
                                                            case 53:
                                                                PadHybridNoteEditFragment.this.mEditor.redo();
                                                                break;
                                                            case 54:
                                                                PadHybridNoteEditFragment.this.mEditor.undo();
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                    }
                                } else {
                                    PadHybridNoteEditFragment.this.mEditor.reduceFontSize();
                                }
                            } else if (!PadHybridNoteEditFragment.this.mEditToolbarController.isRight()) {
                                PadHybridNoteEditFragment.this.mEditor.setAlignRight();
                            }
                            zDoShortcutCopy = true;
                        }
                        if (keyEvent.hasModifiers(4097)) {
                            if (i2 == 52) {
                                PadHybridNoteEditFragment.this.mBarController.strikeThrough();
                            } else if (i2 == 54) {
                                PadHybridNoteEditFragment.this.mEditor.redo();
                            }
                            zDoShortcutCopy = true;
                        }
                        if (keyEvent.hasModifiers(4098)) {
                            Logger.INSTANCE.i(PadHybridNoteEditFragment.TAG, "onKeyDown,key event has modifier meta_ctrl_on/meta_alt_on");
                            if (PadHybridNoteEditFragment.this.mActionMode != EditorActionMode.VIEW) {
                                if (i2 != 14) {
                                    if (i2 != 15) {
                                        if (i2 == 31) {
                                            if (zCanInsert) {
                                                PadHybridNoteEditFragment.this.addAttachment(2);
                                            }
                                            return true;
                                        }
                                        if (i2 == 32) {
                                            if (zCanInsert) {
                                                PadHybridNoteEditFragment.this.createNewDoodle();
                                            }
                                            return true;
                                        }
                                        if (i2 == 44) {
                                            if (zCanInsert) {
                                                PadHybridNoteEditFragment.this.addAttachment(1);
                                            }
                                            return true;
                                        }
                                        if (i2 == 47) {
                                            PadHybridNoteEditFragment.this.mEditor.setHr();
                                            return true;
                                        }
                                        if (i2 == 50) {
                                            Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, "keycode v,canUseKeyShort:" + zCanInsert + ",isRecording:" + PadHybridNoteEditFragment.this.mIsRecording + ",isSupportAiRecorder:" + PadHybridNoteEditFragment.this.isSupportAIRecorder());
                                            if ((zCanInsert || (PadHybridNoteEditFragment.this.mIsRecording && PadHybridNoteEditFragment.this.isSupportAIRecorder())) && !Utils.isFastClick(0, 400L)) {
                                                PadHybridNoteEditFragment.this.clickAudionBtnAboveOS2();
                                            }
                                            return true;
                                        }
                                        if (i2 == 56) {
                                            PadHybridNoteEditFragment.this.mEditor.setQuote();
                                            return true;
                                        }
                                        if (i2 != 151) {
                                            if (i2 != 152) {
                                                switch (i2) {
                                                    case 8:
                                                        PadHybridNoteEditFragment.this.mEditor.setH1();
                                                        return true;
                                                    case 9:
                                                        PadHybridNoteEditFragment.this.mEditor.setH2();
                                                        return true;
                                                    case 10:
                                                        PadHybridNoteEditFragment.this.mEditor.setH3();
                                                        return true;
                                                    default:
                                                        switch (i2) {
                                                            case R2.array.ai_input_state_blend_modes /* 145 */:
                                                                PadHybridNoteEditFragment.this.mEditor.setH1();
                                                                return true;
                                                            case R2.array.ai_material_default_blend_modes /* 146 */:
                                                                PadHybridNoteEditFragment.this.mEditor.setH2();
                                                                return true;
                                                            case R2.array.ai_material_default_blend_pop_modes /* 147 */:
                                                                PadHybridNoteEditFragment.this.mEditor.setH3();
                                                                return true;
                                                        }
                                                }
                                            }
                                        }
                                    }
                                    PadHybridNoteEditFragment.this.mEditor.setEditorBullet();
                                    return true;
                                }
                                PadHybridNoteEditFragment.this.mEditor.setOrderList();
                                return true;
                            }
                            Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, "view mode,return!!");
                            return false;
                        }
                        return zDoShortcutCopy;
                    }
                    Logger.INSTANCE.i(PadHybridNoteEditFragment.TAG, "onKeyDown,has no focus!!");
                    return false;
                }
                Logger.INSTANCE.i(PadHybridNoteEditFragment.TAG, "onKeyDown,has no specify modifiers");
                return false;
            }

            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyUp(int i2, KeyEvent keyEvent) {
                PadHybridNoteEditFragment.this.mEditToolbarController.onFocusAble(true);
                return false;
            }
        });
        this.mEditorWebView.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda18
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view2, boolean z) {
                this.f$0.lambda$initNoteEditor$13(view2, z);
            }
        });
        this.mEditAudioController = new CommonPadNoteAudioController(this.mEditor, (ViewGroup) this.mContentView, new CommonPadNoteAudioController.OnPanelListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.9
            @Override // com.miui.pad.feature.notes.commonedit.CommonPadNoteAudioController.OnPanelListener
            public void onShow(View view2) {
                if (PadHybridNoteEditFragment.this.isAdded()) {
                    if (PadHybridNoteEditFragment.this.mActionMode != EditorActionMode.EDIT_RECODE) {
                        PadHybridNoteEditFragment.this.mEditor.insertAudioEnd("");
                        return;
                    }
                    PadHybridNoteEditFragment.this.startRecordTask();
                    if (UIUtils.isFreeFormScreen(PadHybridNoteEditFragment.this.getActivity().getResources().getConfiguration())) {
                        PadHybridNoteEditFragment.this.mEditorWebView.setScrollY(PadHybridNoteEditFragment.this.mEditorWebView.getScrollY() + R2.array.ai_tag_top_item_gradient_border_colors);
                    }
                }
            }

            @Override // com.miui.pad.feature.notes.commonedit.CommonPadNoteAudioController.OnPanelListener
            public void onDismiss() {
                if (PadHybridNoteEditFragment.this.isAdded()) {
                    PadHybridNoteEditFragment.this.setEditContentMarginBottom(0);
                }
            }

            @Override // com.miui.notes.ui.view.NoteEditAudioTransGroup.OnTransClickListener
            public void onViewClick(View view2) {
                switch (view2.getId()) {
                    case R.id.flag /* 2131362550 */:
                        if (PadHybridNoteEditFragment.this.mAudioMeetingTask != null) {
                            PadHybridNoteEditFragment.this.mAudioMeetingTask.addEnterFlag();
                        }
                        break;
                    case R.id.mode /* 2131362969 */:
                        PadHybridNoteEditFragment.this.mRecordModeChooserMenu = new RecordModeChooserMenu(PadHybridNoteEditFragment.this.getContext());
                        PadHybridNoteEditFragment.this.mRecordModeChooserMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.9.1
                            @Override // com.miui.common.view.IPopMenu.OnMenuListener
                            public void onDismiss() {
                            }

                            @Override // com.miui.common.view.IPopMenu.OnMenuListener
                            public void onShow() {
                            }

                            @Override // com.miui.common.view.IPopMenu.OnMenuListener
                            public void onItemSelected(View view3, int i2) {
                                int id = view3.getId();
                                if (id == R.id.mode_meeting) {
                                    if (PadHybridNoteEditFragment.this.mEditAudioController.isSingleMode()) {
                                        PadHybridNoteEditFragment.this.showAudioMeetingDialog();
                                    }
                                } else {
                                    if (id != R.id.mode_single) {
                                        return;
                                    }
                                    PadHybridNoteEditFragment.this.mEditAudioController.setMode(true);
                                    PadHybridNoteEditFragment.this.updateMeetingAudioTaskInfo();
                                }
                            }
                        });
                        PadHybridNoteEditFragment.this.mRecordModeChooserMenu.show(PadHybridNoteEditFragment.this.mEditAudioController.getModeMenuAnchorView(), null);
                        break;
                    case R.id.mode_meeting /* 2131362974 */:
                        if (PadHybridNoteEditFragment.this.mEditAudioController.isSingleMode()) {
                            PadHybridNoteEditFragment.this.showAudioMeetingDialog();
                        }
                        break;
                    case R.id.mode_single /* 2131362976 */:
                        PadHybridNoteEditFragment.this.mEditAudioController.setMode(true);
                        PadHybridNoteEditFragment.this.updateMeetingAudioTaskInfo();
                        break;
                    case R.id.stop /* 2131363538 */:
                        if (PadHybridNoteEditFragment.this.mIsRecording) {
                            PadHybridNoteEditFragment.this.mEditAudioController.dismiss();
                            if (PadHybridNoteEditFragment.this.isSupportAIRecorder()) {
                                PadHybridNoteEditFragment.this.stopAIRecord();
                            } else {
                                PadHybridNoteEditFragment.this.stopRecord();
                            }
                            if (PadHybridNoteEditFragment.this.mAudioMeetingTask != null) {
                                PadHybridNoteEditFragment.this.mAudioMeetingTask.stopEngine();
                            }
                        }
                        break;
                    case R.id.translation /* 2131363690 */:
                        PadHybridNoteEditFragment.this.mEditAudioController.setLanTransEnable(true ^ PadHybridNoteEditFragment.this.mEditAudioController.isLanTrans());
                        PadHybridNoteEditFragment.this.updateMeetingAudioTaskInfo();
                        break;
                }
            }
        });
        if (this.mEditToolbarController != null) {
            this.mScreenSpec.type = getResponsiveState().getType();
            if (getActivity() != null) {
                this.mScreenSpec.width = UIUtils.getWindowWidth(getActivity());
            }
            updateToolBarLayout();
        } else {
            initToolbarController(view);
        }
        this.mEditorMaskView = (FrameLayout) view.findViewById(R.id.content_mask);
        if (!this.mIsFromRebuild || getEditToolBarController() == null) {
            return;
        }
        getEditToolBarController().hide(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initNoteEditor$13(View view, boolean z) {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mFocusChangeRunnable);
            if (z && this.mInSearchMode) {
                this.mHandler.postDelayed(this.mFocusInSearchRunnable, 100L);
            }
            if (z || this.mIsRecording || this.isShowImageChooser) {
                return;
            }
            this.mHandler.postDelayed(this.mFocusChangeRunnable, 200L);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void sendAsImage() {
        if (isWorkingNoteEmpty()) {
            Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
        } else {
            super.sendAsImage();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEditContentMarginBottom(int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mNestedHeaderLayout.getLayoutParams();
        marginLayoutParams.bottomMargin = i;
        this.mNestedHeaderLayout.setLayoutParams(marginLayoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMeetingAudioTaskInfo() {
        boolean z = RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(getContext());
        MeetingRecognitionTask meetingRecognitionTask = this.mAudioMeetingTask;
        if (meetingRecognitionTask != null) {
            meetingRecognitionTask.updateAsrInfo(this.mEditAudioController.isSingleMode(), this.mEditAudioController.isLanTrans(), z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAudioMeetingDialog() {
        FragmentActivity activity = getActivity();
        Logger.INSTANCE.d(TAG, "showAudioMeetingDialog");
        if (activity == null || activity.isFinishing()) {
            return;
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(activity).setTitle(R.string.audio_dialog_mode_meeting).setMessage(String.format(activity.getString(R.string.audio_dialog_mode_meeting_sum), 2, 4)).setPositiveButton(R.string.audio_dialog_mode_meeting_use, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.11
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                PadHybridNoteEditFragment.this.mEditAudioController.setMode(false);
                PadHybridNoteEditFragment.this.updateMeetingAudioTaskInfo();
            }
        }).setNegativeButton(R.string.audio_dialog_mode_meeting_exit, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.10
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                PadHybridNoteEditFragment.this.mEditAudioController.setMode(true);
                PadHybridNoteEditFragment.this.updateMeetingAudioTaskInfo();
            }
        }).create();
        alertDialogCreate.show();
        manageDialog(alertDialogCreate);
    }

    private void updateToolBarLayout() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.edit_note_tool_layout);
        boolean z = false;
        int dimension = (getContext() == null || getContext().getResources() == null) ? 0 : (int) getContext().getResources().getDimension(R.dimen.compact_mode_max_width);
        if (getResponsiveState().getType() != 1 || this.mScreenSpec.width > dimension ? viewGroup.findViewById(R.id.big_placeholder) == null : viewGroup.findViewById(R.id.small_placeholder) == null) {
            z = true;
        }
        Logger.INSTANCE.d(TAG, "updateToolBarLayout needUpdate = " + z + " type = " + getResponsiveState().getType() + " mScreenSpec.width = " + this.mScreenSpec.width + " maxCompactWidth = " + dimension);
        if (!z) {
            if (this.mEditToolbarController != null) {
                this.mEditToolbarController.setMarginStart();
                return;
            }
            return;
        }
        viewGroup.removeAllViews();
        int i = (getResponsiveState().getType() != 1 || this.mScreenSpec.width > dimension) ? R.layout.big_screen_mode_tool_group : R.layout.small_screen_mode_tool_group;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        View viewInflate = LayoutInflater.from(activity).inflate(i, (ViewGroup) null);
        viewInflate.setLayoutParams(layoutParams);
        viewGroup.addView(viewInflate);
        initToolbarController(this.mRootView);
        this.mEditToolbarController.setWorkingNote(this.mWorkingNote);
        if (this.mCurrentTheme != null) {
            this.mEditToolbarController.updateTheme(this.mCurrentTheme, loadNoteThemeResToCache(this.mCurrentTheme));
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void onFoldChangeScreen() {
        super.onFoldChangeScreen();
        dismiss();
    }

    public void dismiss() {
        dismissDialog();
        dismissImmersionMenu(false);
        RecordModeChooserMenu recordModeChooserMenu = this.mRecordModeChooserMenu;
        if (recordModeChooserMenu != null) {
            recordModeChooserMenu.dismiss();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
        if (this.mPagePrepare) {
            super.onResponsiveLayout(configuration, screenSpec, z);
            Logger.INSTANCE.d(TAG, "onResponsiveLayout");
            if (RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(getActivity()) && screenSpec.screenMode != 4103 && this.mIsRecording) {
                if (isSupportAIRecorder()) {
                    stopAIRecord();
                } else {
                    stopRecord();
                }
            }
            boolean z2 = false;
            if (!USE_MEETING_AUDIO && this.mScreenSpec.type != screenSpec.type) {
                dismissImmersionMenu(false);
                if (this.mActionMode == EditorActionMode.EDIT_RECODE) {
                    updateActionMode(EditorActionMode.VIEW, false);
                }
            }
            this.mScreenSpec = screenSpec;
            CommonPadNoteAudioController commonPadNoteAudioController = this.mEditAudioController;
            if (commonPadNoteAudioController != null) {
                commonPadNoteAudioController.updateView(screenSpec);
                this.mEditAudioController.updateScreenStatus(screenSpec);
            }
            updateToolBarLayout();
            if (this.mEditToolbarController != null && (this.mActionMode == EditorActionMode.EDIT || this.mActionMode == EditorActionMode.EDIT_TITLE)) {
                Logger.INSTANCE.d(TAG, "onResponsiveLayout toolbarIsShow = " + this.mEditToolbarController.ismToolbarIsShow());
                if ((!isSupportAIRecorder() || !this.mIsRecording) && !this.mEditToolbarController.ismToolbarIsShow()) {
                    IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
                    if (this.mEditor != null && !this.mEditor.getEditorTextIsEmpty()) {
                        z2 = true;
                    }
                    iBaseNoteEditToolbarController.show(z2);
                }
            }
            this.mScreenStatus = UIUtils.getScreenStatus(getActivity(), this.mScreenSpec);
            updateHomeButtonState(this.mScreenSpec);
            setNaviState(Navigator.get(this).isContentOpen());
            if (this.mEditorWebView != null && this.mEditor != null) {
                this.mEditorWebView.update();
            }
            if (this.mImageChooserMenu != null) {
                this.mImageChooserMenu.dismiss();
                this.mImageChooserMenu = null;
            }
            if (this.mAiChooserMenu != null) {
                this.mAiChooserMenu.dismiss();
                this.mAiChooserMenu = null;
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initToolbarController(View view) {
        if (LiteUtils.isSuperLite() && RomUtils.isPadDevice()) {
            ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.edit_note_tool_layout);
            if (viewGroup.findViewById(R.id.big_placeholder) == null) {
                viewGroup.removeAllViews();
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
                View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.big_screen_mode_tool_group, (ViewGroup) null);
                viewInflate.setLayoutParams(layoutParams);
                viewGroup.addView(viewInflate);
            }
        }
        this.mEditToolbarController = createToolBarController(getActivity().findViewById(R.id.editor_mode_tool_group));
        this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
        this.mEditToolbarController.setReadOnlyActionListener(new ReadOnlyActionListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.12
            @Override // com.miui.notes.editor.ReadOnlyActionListener
            public void onConfirmEdit(final Runnable runnable) {
                PadHybridNoteEditFragment.this.confirmEditAnyway(new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.12.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PadHybridNoteEditFragment.this.setReadonly(false);
                        runnable.run();
                    }
                });
            }
        });
        this.mEditToolbarController.setOnThemeChosenListener(this.mThemeChosenListener);
        this.mEditToolbarController.setExternalClickListener(this.mToolbarClickListener);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void doneEdit() {
        super.doneEdit();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, com.miui.common.tool.softinputhelper.KeyboardListener
    public void onKeyboardVisibleChanged(boolean z) {
        super.onKeyboardVisibleChanged(z);
        if (this.mEditor != null) {
            this.mEditor.onKeyboardVisibilityChanged(z);
        }
        if (this.mEditorWebView != null) {
            if (z && this.mEditorWebView.isFocused() && !this.mIsRecording) {
                this.mEditToolbarController.collapsePanel(null, false);
            } else {
                if (z || this.mIsRecording) {
                    return;
                }
                this.mEditorWebView.clearFocus();
            }
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void clearFocus() {
        super.clearFocus();
        if (this.mEditorWebView == null || this.mActionMode == null || this.mNoteRichEditor == null) {
            return;
        }
        this.mEditorWebView.clearFocus();
        if (this.mActionMode == EditorActionMode.EDIT && getEditToolBarController() != null) {
            getEditToolBarController().hide(false);
        }
        this.mNoteRichEditor.done();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void setInSearchMode(boolean z) {
        super.setInSearchMode(z);
        if (this.mInSearchMode) {
            if (this.mActionMode != EditorActionMode.EDIT || getEditToolBarController() == null) {
                return;
            }
            getEditToolBarController().hide(false);
            return;
        }
        this.mSearchText = "";
        updateUserQuery(this.mSearchText, false, null);
    }

    @Override // miuix.navigator.NavigatorFragmentListener
    public void onNavigatorModeChanged(Navigator.Mode mode, Navigator.Mode mode2) {
        super.onNavigatorModeChanged(mode, mode2);
        if (this.mWorkingNote == null) {
            Logger.INSTANCE.d(TAG, "Detail ModeChanged mWorkingNote is null");
        } else {
            Logger.INSTANCE.d(TAG, "Detail ModeChanged:" + mode2 + " mWorkingNote.isEmpty(): " + this.mWorkingNote.isEmpty());
        }
        if (mode2 == Navigator.Mode.C && ((this.mWorkingNote == null || this.mWorkingNote.isEmpty()) && getView() != null)) {
            getView().post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onNavigatorModeChanged$14();
                }
            });
        }
        Logger.INSTANCE.d(TAG, "Detail ModeChanged:" + mode2);
        updateHomeButtonState(this.mScreenSpec);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNavigatorModeChanged$14() {
        try {
            Navigator.get(this).getFragmentManager().popBackStack();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "onNavigatorModeChanged popBackStack failed,e:" + e);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void onSplitModeChanged(int i) {
        super.onSplitModeChanged(i);
        updateHomeButtonState(this.mScreenSpec);
        if (i != 0) {
            if (i == 1) {
                if (isAdded() && getEditToolBarController() != null) {
                    updateActionMode(EditorActionMode.VIEW, false);
                    getEditToolBarController().hide(false);
                }
                if (FloatWindowManagerAgent.isContinuousExcerpt() && isThisNoteDoExcerpt()) {
                    FloatWindowManagerAgent.resumeContinuousExcerpt();
                }
                onKeyboardVisibleChanged(false);
                this.mIsShow = false;
                setNaviState(true);
                return;
            }
            if (i != 2) {
                return;
            }
        }
        this.mIsShow = true;
        setNaviState(i == 0);
        if (this.mHandler != null) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSplitModeChanged$15();
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSplitModeChanged$15() {
        this.mEditorWebView.requestLayout();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected boolean handleInstanceState(Bundle bundle) {
        boolean zHandleInstanceState = super.handleInstanceState(bundle);
        if (zHandleInstanceState) {
            showBlankView(false);
        }
        return zHandleInstanceState;
    }

    @Override // com.miui.notes.basefeature.noteedit.ColumnsHybridNoteEditFragment
    protected void showBlankView(boolean z) {
        if (!isAdded() || getActionBar() == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "showBlankView show = " + z);
        if (z) {
            getActionBar().getCustomView().setVisibility(4);
            Folme.useAt(this.mBlankView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
            Folme.useAt(this.mContentView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(new TransitionListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.13
                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    super.onCancel(obj);
                    PadHybridNoteEditFragment.this.mBlankView.setAlpha(0.0f);
                    PadHybridNoteEditFragment.this.mContentView.setVisibility(0);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    PadHybridNoteEditFragment.this.mBlankView.setAlpha(1.0f);
                    PadHybridNoteEditFragment.this.mContentView.setVisibility(8);
                }
            }));
            if (this.mBlankView != null) {
                String str = this.mSearchText;
                if (this.mFolderId == -3 || this.mFolderId == -2 || this.mFolderId == -5 || !TextUtils.isEmpty(str)) {
                    this.mBlankView.setOnDragListener(this.mBlockDragListener);
                } else {
                    this.mBlankView.setOnDragListener(this.mBlankViewDragListener);
                }
                this.mBlankView.setTitle(getString(R.string.data_empty_note));
            }
            if (this.mEditorWebView != null) {
                this.mEditorWebView.setFocusable(false);
                this.mEditorWebView.setFocusableInTouchMode(false);
                this.mEditorWebView.clearFocus();
                this.mEditorWebView.setOnDragListener(null);
            }
        } else {
            getActionBar().getCustomView().setVisibility(0);
            Folme.useAt(this.mBlankView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f));
            Folme.useAt(this.mContentView).state().cancel();
            this.mContentView.setVisibility(0);
            Folme.useAt(this.mContentView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
            EmptyStateView emptyStateView = this.mBlankView;
            if (emptyStateView != null) {
                emptyStateView.setOnDragListener(null);
            }
            if (this.mEditorWebView != null) {
                this.mEditorWebView.setFocusable(true);
                this.mEditorWebView.setFocusableInTouchMode(true);
                this.mEditorWebView.setOnDragListener(this.mDragListener);
            }
        }
        this.mIsShowBlank = z;
        if (z) {
            updateNoteThemeOnNative(0, true, false);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void updateNoteThemeOnNative(int i, boolean z, boolean z2) {
        super.updateNoteThemeOnNative(0, z, z2);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void onTitleMode() {
        if (this.mIsRecording) {
            if (isSupportAIRecorder()) {
                stopAIRecord();
            } else {
                stopRecord();
            }
        }
        super.onTitleMode();
        recordNotifyNavigation(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onLoadDataFailed() {
        super.onLoadDataFailed();
        onDataEmpty();
        if (this.mHandler != null) {
            this.mHandler.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onLoadDataFailed$16();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLoadDataFailed$16() {
        if (isAdded()) {
            Logger.INSTANCE.i(TAG, "Fail to load note, Back!");
            getActivity().onBackPressed();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onNetworkStateChanged(boolean z) {
        super.onNetworkStateChanged(z);
        if (z) {
            if (isUseMeetingAudio()) {
                MeetingRecognitionTask meetingRecognitionTask = this.mAudioMeetingTask;
                if (meetingRecognitionTask != null) {
                    meetingRecognitionTask.setNetWorkValid(true);
                    this.mAudioMeetingTask.startEngine();
                    return;
                }
                return;
            }
            SpeechRecognitionTask speechRecognitionTask = this.mRecordAiTask;
            if (speechRecognitionTask != null) {
                speechRecognitionTask.setNetWorkValid(true);
                this.mRecordAiTask.startEngine();
                return;
            }
            return;
        }
        if (isUseMeetingAudio()) {
            MeetingRecognitionTask meetingRecognitionTask2 = this.mAudioMeetingTask;
            if (meetingRecognitionTask2 != null) {
                meetingRecognitionTask2.setNetWorkValid(false);
                this.mAudioMeetingTask.stopEngine();
            }
        } else {
            SpeechRecognitionTask speechRecognitionTask2 = this.mRecordAiTask;
            if (speechRecognitionTask2 != null) {
                speechRecognitionTask2.setNetWorkValid(false);
                this.mRecordAiTask.stopEngine();
            }
        }
        if (getActivity() == null || !isAdded()) {
            return;
        }
        Toast.makeText(NoteApp.getInstance(), R.string.audio_toast_network_is_not_available, 0).show();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void deleteAndLoad() {
        AudioUtils.playDeleteRingtone();
        long noteId = getNoteId();
        if (noteId <= 0) {
            Logger.INSTANCE.e(TAG, "deleteAndLoad: invalid note id " + noteId);
            return;
        }
        Intent intent = new Intent(NoteIntent.ACTION_DELETE);
        intent.putExtra("android.intent.extra.UID", noteId);
        intent.putExtra(NoteIntent.KEY_TOUCH_DELETE, true);
        intent.putExtra(NoteIntent.KEY_FOLDER_ID, this.mWorkingNote.getFolderId());
        if (PreferenceUtils.getNoteSortWay(getActivity(), 1) == 1) {
            intent.putExtra(NoteIntent.KEY_DATE, this.mWorkingNote.getModifiedDate());
        } else {
            intent.putExtra(NoteIntent.KEY_DATE, this.mWorkingNote.getCreatedDate());
        }
        onNewIntent(intent);
    }

    public void stopRecord() {
        Logger.INSTANCE.d(TAG, "stop record");
        if (this.mEditorMaskView != null) {
            this.mEditorMaskView.setVisibility(8);
            this.mEditorMaskView.removeAllViews();
        }
        this.mEditToolbarController.toggleAudioRecordPanel();
        CommonPadNoteAudioController commonPadNoteAudioController = this.mEditAudioController;
        if (commonPadNoteAudioController != null) {
            commonPadNoteAudioController.dismiss();
        }
        this.mEditToolbarController.onViewMode(true);
        this.mIsRecording = false;
        stopRecordTask();
        recordNotifyNavigation(false);
        this.mEditToolbarController.resetRichTextStatus();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void onViewMode() {
        super.onViewMode();
        if (this.mIsRecording) {
            this.mEditAudioController.dismiss();
            if (!isSupportAIRecorder()) {
                stopRecord();
            } else {
                stopAIRecord();
            }
        }
        getEditToolBarController().hide(false);
        recordNotifyNavigation(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void onEditMode(boolean z) {
        super.onEditMode(z);
        this.mEditorWebView.requestFocus();
        if (this.mEditor.isNeedTextStyleChanged) {
            this.mEditor.onTextStyleNeedChanged();
        }
        recordNotifyNavigation(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onShowAddNewNote(boolean z) {
        if (z) {
            this.mBlankView.setSelected(true);
        } else {
            this.mBlankView.setSelected(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRecordTask() {
        PadHybridNoteEditFragment padHybridNoteEditFragment;
        if (isAdded()) {
            if (DualScreenManager.INSTANCE.isPcActivityLiving() && this.mHandler != null && this.mHandler.hasMessages(1)) {
                this.mHandler.removeMessages(1);
            }
            this.mEditor.stopPlayingMedia();
            this.mEditor.pauseVoice();
            if (isUseMeetingAudio()) {
                if (this.mAudioMeetingTask == null) {
                    this.meetingRecognitionListener = new MeetingRecognitionImpl();
                    this.mAudioMeetingTask = new MeetingRecognitionTaskImp(NoteApp.getInstance(), AudioUtils.NOTE_MP3_DIR_NAME, this.meetingRecognitionListener);
                }
            } else {
                SpeechRecognitionTask speechRecognitionTask = this.mRecordAiTask;
                if (speechRecognitionTask != null) {
                    speechRecognitionTask.destroy();
                    this.mRecordAiTask = null;
                }
                if (this.mRecordAiTask == null) {
                    this.speechRecognitionListener = new SpeechRecognitionImpl(this);
                    this.mRecordAiTask = new SpeechRecognitionTaskImp(NoteApp.getInstance(), AudioUtils.NOTE_MP3_DIR_NAME, this.speechRecognitionListener);
                }
            }
            this.mEditToolbarController.startAudioInput();
            if (this.mAudioRecordTimer == null) {
                padHybridNoteEditFragment = this;
                padHybridNoteEditFragment.mAudioRecordTimer = new CountDownTimer(isUseMeetingAudio() ? MAX_MEETING_RECORD_TIME : MAX_RECORD_TIME, 1000L) { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.14
                    @Override // android.os.CountDownTimer
                    public void onTick(long j) {
                        PadHybridNoteEditFragment.this.onAudioTimerTick(j);
                    }

                    @Override // android.os.CountDownTimer
                    public void onFinish() {
                        PadHybridNoteEditFragment.this.onAudioTimerFinish();
                    }
                };
            } else {
                padHybridNoteEditFragment = this;
            }
            padHybridNoteEditFragment.mAudioRecordTimer.start();
            padHybridNoteEditFragment.mNetworkCallback.initNetWorkState();
            boolean isNetWorkAvailable = padHybridNoteEditFragment.mNetworkCallback.getIsNetWorkAvailable();
            if (padHybridNoteEditFragment.isUseMeetingAudio()) {
                if (!padHybridNoteEditFragment.mAudioMeetingTask.isSkipSpeechRecognition()) {
                    padHybridNoteEditFragment.registerNetWorkListener();
                }
            } else if (!padHybridNoteEditFragment.mRecordAiTask.isSkipSpeechRecognition()) {
                padHybridNoteEditFragment.registerNetWorkListener();
            }
            padHybridNoteEditFragment.getActivity().getWindow().addFlags(128);
            if (padHybridNoteEditFragment.isUseMeetingAudio()) {
                boolean z = RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(padHybridNoteEditFragment.getContext());
                padHybridNoteEditFragment.mAudioMeetingTask.setNetWorkValid(isNetWorkAvailable);
                padHybridNoteEditFragment.mAudioMeetingTask.start(padHybridNoteEditFragment.mEditAudioController.isSingleMode(), padHybridNoteEditFragment.mEditAudioController.isLanTrans(), z);
                padHybridNoteEditFragment.mIsWarned = false;
            } else {
                try {
                    padHybridNoteEditFragment.mRecordAiTask.setNetWorkValid(isNetWorkAvailable);
                    padHybridNoteEditFragment.mRecordAiTask.start();
                    padHybridNoteEditFragment.mIsWarned = false;
                } catch (MibrainException unused) {
                    Logger.INSTANCE.e(TAG, "Fail to start record task!");
                }
            }
            FloatWindowManagerAgent.setRecordState(true);
        }
    }

    private void stopRecordTask() {
        Logger.INSTANCE.i(TAG, "stopRecordTask");
        FloatWindowManagerAgent.setRecordState(false);
        dismissDialog();
        if (isUseMeetingAudio()) {
            MeetingRecognitionTask meetingRecognitionTask = this.mAudioMeetingTask;
            if (meetingRecognitionTask != null) {
                meetingRecognitionTask.shutdownNow();
                if (!this.mAudioMeetingTask.isSkipSpeechRecognition()) {
                    unRegisterNetWorkListener();
                }
                getActivity().getWindow().clearFlags(128);
                this.mIsWarned = false;
                CountDownTimer countDownTimer = this.mAudioRecordTimer;
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    this.mAudioRecordTimer = null;
                }
            }
        } else {
            SpeechRecognitionTask speechRecognitionTask = this.mRecordAiTask;
            if (speechRecognitionTask != null) {
                speechRecognitionTask.shutdownNow();
                if (!this.mRecordAiTask.isSkipSpeechRecognition()) {
                    unRegisterNetWorkListener();
                }
                getActivity().getWindow().clearFlags(128);
                this.mIsWarned = false;
                CountDownTimer countDownTimer2 = this.mAudioRecordTimer;
                if (countDownTimer2 != null) {
                    countDownTimer2.cancel();
                    this.mAudioRecordTimer = null;
                }
            }
        }
        this.mEditToolbarController.stopAudioInput();
        saveNote(true);
        if (this.mWorkingNote == null || this.mNoteRichEditor == null) {
            return;
        }
        TempAudio.ID = this.mWorkingNote.getNoteId();
        CharSequence richTextData = this.mNoteRichEditor.getRichTextData();
        if (richTextData != null) {
            TempAudio.CONTENT = richTextData.toString();
        } else {
            TempAudio.CONTENT = "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAudioTimerTick(long j) {
        if (isAdded()) {
            if (!this.mIsWarned && j <= LAST_WARRING_TIME) {
                this.mIsWarned = true;
                if (this.mEditorMaskView != null) {
                    this.mEditorMaskView.addView(getAudioCountDownView());
                }
            }
            if (this.mIsWarned) {
                int iFloor = ((int) Math.floor(j / 1000)) + 1;
                TextView textView = this.mCountDownView;
                if (textView != null) {
                    textView.setText(Utils.obtainLocalInteger(iFloor, R.plurals.record_last_ten_seconds_reminder));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAudioTimerFinish() {
        if (this.mEditorMaskView != null) {
            this.mEditorMaskView.removeAllViews();
        }
        updateActionMode(EditorActionMode.VIEW, true);
    }

    protected View getAudioCountDownView() {
        if (getActivity() == null) {
            return null;
        }
        this.mCountDownView = new TextView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        Rect rect = new Rect();
        layoutParams.topMargin = (rect.bottom - rect.top) / 2;
        this.mCountDownView.setLayoutParams(layoutParams);
        this.mCountDownView.setBackground(getActivity().getResources().getDrawable(R.drawable.countdown_view_backgound));
        this.mCountDownView.setTextColor(getActivity().getResources().getColor(R.color.countdown_view_text_color));
        this.mCountDownView.setTextSize(0, getActivity().getResources().getDimension(R.dimen.bubble_text_size));
        return this.mCountDownView;
    }

    @Override // com.miui.common.record.SpeechRecognitionListener
    public void onAsrAppendTextResult(String str, boolean z) {
        if (this.mIsRecording) {
            this.mEditor.appendAudioText(str, z);
        }
    }

    @Override // com.miui.common.record.SpeechRecognitionListener
    public void onAsrSaveAudioFileName(String str) {
        notifyAudioFileSaved(str);
    }

    @Override // com.miui.common.record.SpeechRecognitionListener
    public void onAsrMaxAmplitudeUpdate(short s) {
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.updateAudioVolume(s);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.RecordTaskWorker
    public void onReceiveTaskResult(String str) {
        this.mEditor.insertAudioEnd(str);
        saveNote(true);
        if (this.mEditor.getMDataId() == TempAudio.ID && this.mEditor.getRichTextData() != null && this.mEditor.getRichTextData().equals(TempAudio.CONTENT)) {
            saveNote(true);
            if (this.mWorkingNote != null) {
                this.mWorkingNote.setWorkingText(TempAudio.CONTENT.replace("fileid=\"\"", "fileid=\"" + str + "\""));
                this.mWorkingNote.saveNote(true);
                if (this.mHasInBg) {
                    updateEditor(true);
                    return;
                }
                return;
            }
            return;
        }
        TempAudio.AUDIO_NAME = str;
        if (this.mEditor.getMDataId() == TempAudio.ID && this.mEditor.getRichTextData() != null && TempAudio.CONTENT.replace(AudioUtils.TEMP_AUDIO_FLAG, StringUtils.SPACE).equals(this.mEditor.getRichTextData().toString())) {
            this.mWorkingNote.setWorkingText(TempAudio.CONTENT.replace("fileid=\"\"", "fileid=\"" + str + "\""));
            this.mWorkingNote.saveNote(true);
            updateEditor(true);
        }
        if (this.mEditor.getMDataId() != TempAudio.ID) {
            WorkingNote workingNoteLoadNote = WorkingNote.loadNote("_id=" + TempAudio.ID, null);
            if (TempAudio.CONTENT == null || !TempAudio.CONTENT.replace(AudioUtils.TEMP_AUDIO_FLAG, StringUtils.SPACE).equals(workingNoteLoadNote.getContent())) {
                return;
            }
            workingNoteLoadNote.setWorkingText(TempAudio.CONTENT.replace("fileid=\"\"", "fileid=\"" + str + "\""));
            workingNoteLoadNote.saveNote(true);
        }
    }

    protected void notifyAudioFileSaved(String str) {
        onReceiveTaskResult(str);
        asyncSaveNote();
    }

    private void registerNetWorkListener() {
        ConnectivityManager connectivityManager = this.mConnMgr;
        if (connectivityManager == null || this.isRegistered) {
            return;
        }
        connectivityManager.registerNetworkCallback(this.mRequest, this.mNetworkCallback);
        this.isRegistered = true;
    }

    private void unRegisterNetWorkListener() {
        ConnectivityManager connectivityManager = this.mConnMgr;
        if (connectivityManager == null || !this.isRegistered) {
            return;
        }
        connectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        this.isRegistered = false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showDualScreenEditTipDialog() {
        if (isAdded()) {
            if (this.mDualScreenEditTipDialog == null || !this.mDualScreenEditTipDialog.isShowing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle(getString(R.string.note_dual_screen_dialog_title));
                builder.setMessage(getString(R.string.note_dual_screen_dialog_content));
                builder.setNegativeButton(getString(R.string.dialog_button_continue_work), new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.15
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setPositiveButton(getString(R.string.dialog_button_close), new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.16
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PadHybridNoteEditFragment.this.notifyEditUiState(3);
                    }
                });
                this.mDualScreenEditTipDialog = builder.create();
                this.mDualScreenEditTipDialog.show();
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateNoteFromDualScreen() {
        long noteId = this.mWorkingNote.getNoteId();
        if (this.mHandler != null && this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.putExtra("android.intent.extra.UID", noteId);
        EventController.getInstance().sendEvent(4, Long.valueOf(noteId));
        this.mLoadTask = new BaseNormalNoteEditFragment.ViewNoteLoadTask(intent, this.mWorkingNote.getNoteId());
        this.mLoadTask.commit();
        updateActionMode(EditorActionMode.VIEW, false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void hideNote() {
        this.mWorkingNote.setFolderId(-4L);
        getActionBarController().updateNoteHiddenState(this.mWorkingNote);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getWindow().addFlags(8192);
        }
        Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_hidden_pad, 0).show();
        asyncSaveNote();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void setNaviState(boolean z) {
        if (this.mActionBarController != null) {
            ((ActionBarWithNaviController) this.mActionBarController).setNaviState(z);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean isDataEmpty() {
        return this.mWorkingNote != null && this.mWorkingNote.isEmpty();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Logger.INSTANCE.d(TAG, "onConfigurationChanged");
        if (this.mEditor == null) {
            return;
        }
        this.mEditor.onExternalKeyboardEnable(Utils.isExternalKeyboardEnable(getContext()) && !UIUtils.isFreeFormScreen(configuration));
        this.mEditor.isNeedTextStyleChanged = true;
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
            this.mEditToolbarController.updateLayout();
        }
        if (this.mSharePopupWindow != null && this.mHandler != null) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onConfigurationChanged$17();
                }
            }, 50L);
        }
        RecordModeChooserMenu recordModeChooserMenu = this.mRecordModeChooserMenu;
        if (recordModeChooserMenu != null) {
            recordModeChooserMenu.dismiss();
        }
        if (getMIsKeyboardVisible() || !isInEditAction() || getActivity() == null || getActivity().isInMultiWindowMode()) {
            return;
        }
        doneEdit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConfigurationChanged$17() {
        Rect rect = new Rect();
        if (getView() != null) {
            getView().getLocalVisibleRect(rect);
            this.mSharePopupWindow.refresh(rect);
        }
    }

    private void updateHomeButtonState(ScreenSpec screenSpec) {
        if (this.mActionBarController == null || Navigator.get(this) == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "updateHomeButtonState screenSpec.type " + screenSpec.type + " screenMode " + screenSpec.screenMode);
        if (RomUtils.isFoldDevice() && PreferenceUtils.getNoteHomePageArrayWay(getActivity(), 0) == 1) {
            ((ActionBarWithNaviController) this.mActionBarController).setHomeButtonState(true);
        } else {
            ((ActionBarWithNaviController) this.mActionBarController).setHomeButtonState(Navigator.get(this).getNavigationMode() == Navigator.Mode.C);
        }
        CommonPadNoteAudioController commonPadNoteAudioController = this.mEditAudioController;
        if (commonPadNoteAudioController != null) {
            commonPadNoteAudioController.updateScreenStatus(screenSpec);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        updateWindowStatus(this.mIsForceDark);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            updateSoftInputMode(activity);
        }
    }

    private void updateSoftInputMode(Activity activity) {
        if (UIUtils.isAboveAndroidR()) {
            if (activity.isInMultiWindowMode() && UIUtils.isSmallWindowMode(activity)) {
                activity.getWindow().setSoftInputMode(0);
                return;
            } else {
                activity.getWindow().setSoftInputMode(48);
                return;
            }
        }
        if (UIUtils.isInFullWindowGestureMode(activity)) {
            return;
        }
        activity.getWindow().setSoftInputMode(16);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void onAudioMode() {
        super.onAudioMode();
        preStartRecordTask();
        this.mIsRecording = true;
        recordNotifyNavigation(true);
    }

    protected void preStartRecordTask() {
        if (isUseMeetingAudio()) {
            getEditToolBarController().hideWithoutAnim();
            Utils.hideSoftInput(this.mEditorWebView);
            this.mEditAudioController.show(this.mScreenSpec);
        } else {
            this.mEditToolbarController.toggleAudioRecordPanel();
            Utils.hideSoftInput(this.mEditorWebView);
            startRecordTask();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void recordNotifyNavigation(boolean z) {
        Navigator navigator = Navigator.get(this);
        if (navigator != null) {
            navigator.immerseSecondaryContent(z);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected float getNoteEditorPadding() {
        return NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.pad_note_edit_web_mind_content_horizontal_padding);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.container.ExtraPaddingObserver
    public void onExtraPaddingChanged(int i) {
        super.onExtraPaddingChanged(i);
        this.mEditContainerLayout.setPadding(i, 0, i, 0);
        int noteEditorPadding = i + ((int) getNoteEditorPadding());
        getActionBarController().updateHeadInfoMarginInAnim(noteEditorPadding, noteEditorPadding);
    }

    private static void setNoHideDescendantsForRootViews(ViewGroup viewGroup) {
        if (viewGroup == null) {
            return;
        }
        int[] iArr = {R.id.navigation_decor_wrapper, R.id.header, R.id.fragment_content, R.id.action_bar_container, R.id.root};
        for (int i = 0; i < 5; i++) {
            View viewFindViewById = viewGroup.findViewById(iArr[i]);
            if (viewFindViewById != null) {
                viewFindViewById.setImportantForAccessibility(4);
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showAttachmentChooserDialog(View view) {
        boolean z;
        setNoHideDescendantsForRootViews((ViewGroup) view.getRootView());
        if (this.mImageChooserMenu != null) {
            this.mImageChooserMenu = null;
        }
        if (Objects.equals(view.getTag(R.id.gallery), "highLight")) {
            view.setTag(R.id.gallery, null);
            z = true;
        } else {
            z = false;
        }
        ImageChooserMenu imageChooserMenu = new ImageChooserMenu(getContext(), this.mEditorWebView != null ? this.mEditorWebView.getHeight() : 0, z);
        imageChooserMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.17
            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onShow() {
                PadHybridNoteEditFragment.this.isShowImageChooser = true;
                PadHybridNoteEditFragment.this.mNoteRichEditor.clearFocus();
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onDismiss() {
                PadHybridNoteEditFragment.this.isShowImageChooser = false;
                PadHybridNoteEditFragment.this.mNoteRichEditor.requestViewFocus();
            }

            /* JADX INFO: renamed from: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$17$3, reason: invalid class name */
            class AnonymousClass3 implements MiuiSynergySdk.ChooseFileCallback {
                AnonymousClass3() {
                }

                @Override // com.xiaomi.mirror.synergy.MiuiSynergySdk.ChooseFileCallback
                public void onFileChosen(final ClipData clipData) {
                    if (!PadHybridNoteEditFragment.this.isAdded() || PadHybridNoteEditFragment.this.mHandler == null) {
                        return;
                    }
                    PadHybridNoteEditFragment.this.mHandler.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$17$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onFileChosen$0(clipData);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onFileChosen$0(ClipData clipData) {
                    if (clipData != null && clipData.getItemAt(0) != null) {
                        PadHybridNoteEditFragment.this.insertImageFromClipData(clipData, false);
                    }
                    PadHybridNoteEditFragment.this.dismissDialog();
                }
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onItemSelected(View view2, int i) {
                if (i == 0) {
                    PadHybridNoteEditFragment.this.addAttachment(1);
                    return;
                }
                if (i == 1) {
                    PadHybridNoteEditFragment.this.addAttachment(2);
                    return;
                }
                if (i == 2) {
                    PadHybridNoteEditFragment.this.doneEdit();
                    if (PadHybridNoteEditFragment.this.isAdded()) {
                        AlertDialog alertDialogCreate = new AlertDialog.Builder(PadHybridNoteEditFragment.this.getContext()).setTitle(R.string.dialog_tip_title).setMessage(R.string.pad_dialog_tip_content_take_photo_by_phone).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.17.2
                            @Override // android.content.DialogInterface.OnDismissListener
                            public void onDismiss(DialogInterface dialogInterface) {
                                if (PadHybridNoteEditFragment.this.getActivity() != null) {
                                    Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, CallMethod.METHOD_TAKE_PHOTO_CANCEL);
                                    MiuiSynergySdk.getInstance().takePhotoCancel(PadHybridNoteEditFragment.this.getActivity());
                                }
                            }
                        }).setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.17.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i2) {
                                PadHybridNoteEditFragment.this.dismissDialog();
                            }
                        }).create();
                        alertDialogCreate.setCancelable(false);
                        alertDialogCreate.setCanceledOnTouchOutside(false);
                        alertDialogCreate.show();
                        PadHybridNoteEditFragment.this.manageDialog(alertDialogCreate);
                        MiuiSynergySdk.getInstance().takePhotoOnSynergy(PadHybridNoteEditFragment.this.getAppCompatActivity(), new AnonymousClass3());
                        return;
                    }
                    return;
                }
                if (i != 3) {
                    return;
                }
                final String str = (String) view2.getTag(R.id.device_id);
                String str2 = (String) view2.getTag(R.id.device_name);
                Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, "onItemSelected deviceId: " + str);
                PadHybridNoteEditFragment.this.doneEdit();
                if (PadHybridNoteEditFragment.this.isAdded()) {
                    AlertDialog alertDialogCreate2 = new AlertDialog.Builder(PadHybridNoteEditFragment.this.getContext()).setTitle(R.string.dialog_tip_title).setMessage(String.format(NoteApp.getInstance().getString(R.string.pad_dialog_take_photo_by_device), str2)).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.17.5
                        @Override // android.content.DialogInterface.OnDismissListener
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (PadHybridNoteEditFragment.this.getActivity() != null) {
                                Logger.INSTANCE.d(PadHybridNoteEditFragment.TAG, CallMethod.METHOD_TAKE_PHOTO_CANCEL);
                                MiuiSynergySdk.getInstance().takePhotoCancel(PadHybridNoteEditFragment.this.getActivity(), str);
                            }
                        }
                    }).setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.17.4
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            PadHybridNoteEditFragment.this.dismissDialog();
                        }
                    }).create();
                    alertDialogCreate2.setCancelable(false);
                    alertDialogCreate2.setCanceledOnTouchOutside(false);
                    alertDialogCreate2.show();
                    MiuiSynergySdk.getInstance().takePhotoOnSynergy(PadHybridNoteEditFragment.this.getAppCompatActivity(), str, new AnonymousClass6(alertDialogCreate2));
                }
            }

            /* JADX INFO: renamed from: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$17$6, reason: invalid class name */
            class AnonymousClass6 implements MiuiSynergySdk.ChooseFileCallback {
                final /* synthetic */ AlertDialog val$tipDialog1;

                AnonymousClass6(AlertDialog alertDialog) {
                    this.val$tipDialog1 = alertDialog;
                }

                @Override // com.xiaomi.mirror.synergy.MiuiSynergySdk.ChooseFileCallback
                public void onFileChosen(final ClipData clipData) {
                    if (!PadHybridNoteEditFragment.this.isAdded() || PadHybridNoteEditFragment.this.mHandler == null) {
                        return;
                    }
                    Handler handler = PadHybridNoteEditFragment.this.mHandler;
                    final AlertDialog alertDialog = this.val$tipDialog1;
                    handler.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$17$6$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onFileChosen$0(clipData, alertDialog);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onFileChosen$0(ClipData clipData, AlertDialog alertDialog) {
                    if (clipData != null && clipData.getItemAt(0) != null) {
                        PadHybridNoteEditFragment.this.insertImageFromClipData(clipData, false);
                    }
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    PadHybridNoteEditFragment.this.dismissDialog();
                }
            }
        });
        imageChooserMenu.show(view, new int[2]);
        this.mImageChooserMenu = imageChooserMenu;
    }

    private void forbidListenKeyboardOnLeave() {
        if (getMIsKeyboardVisible()) {
            this.mPendingDisableListenKeyboardTask = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$forbidListenKeyboardOnLeave$18();
                }
            };
        } else {
            setNeedListenKeyboard(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forbidListenKeyboardOnLeave$18() {
        setNeedListenKeyboard(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onChangeToMindNote() {
        if (isReadOnlyMode()) {
            return;
        }
        this.mEditorWebView.clearFocus();
        cancelSaveTask();
        NoteStore.delete(NoteApp.getInstance(), new long[]{getNoteId()});
        String title = this.mEditor.getTitle();
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(title)) {
            bundle.putString("note_title", title);
        }
        if (RomUtils.isFoldDevice()) {
            bundle.putInt("mind_content_type", 1);
        } else {
            bundle.putInt("mind_content_type", 0);
        }
        if (this.mWorkingNote != null) {
            bundle.putInt("widget_id", this.mWorkingNote.getWidgetId());
            bundle.putInt("widget_type", this.mWorkingNote.getWidgetType());
        }
        try {
            this.homeViewModel.createMindNote(bundle);
        } catch (Exception e) {
            Logger.INSTANCE.d("onChangeToMindNote", "error ::" + e);
        }
    }

    private static class MeetingRecognitionImpl implements MeetingRecognitionListener {
        private WeakReference<PadHybridNoteEditFragment> iFragmentRef;

        private MeetingRecognitionImpl(PadHybridNoteEditFragment padHybridNoteEditFragment) {
            this.iFragmentRef = new WeakReference<>(padHybridNoteEditFragment);
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onMeetingTextResult(String str, boolean z) {
            PadHybridNoteEditFragment padHybridNoteEditFragment = this.iFragmentRef.get();
            if (padHybridNoteEditFragment != null) {
                if (padHybridNoteEditFragment.mEditorWebView.toString().length() < 20000 || padHybridNoteEditFragment.mActionMode != EditorActionMode.EDIT_RECODE) {
                    try {
                        if (padHybridNoteEditFragment.mIsRecording) {
                            padHybridNoteEditFragment.mEditor.onMeetingTextResult(str, z);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                padHybridNoteEditFragment.updateActionMode(EditorActionMode.VIEW, true);
            }
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onMeetingTextFull(String str) {
            PadHybridNoteEditFragment padHybridNoteEditFragment = this.iFragmentRef.get();
            if (padHybridNoteEditFragment != null) {
                if (padHybridNoteEditFragment.mEditor.getContentLength() < 20000 || padHybridNoteEditFragment.mActionMode != EditorActionMode.EDIT_RECODE) {
                    padHybridNoteEditFragment.mEditor.onMeetingTextFull(str);
                } else {
                    padHybridNoteEditFragment.updateActionMode(EditorActionMode.VIEW, true);
                }
            }
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onMeetingMp3FileName(String str) {
            PadHybridNoteEditFragment padHybridNoteEditFragment = this.iFragmentRef.get();
            if (padHybridNoteEditFragment != null) {
                padHybridNoteEditFragment.notifyAudioFileSaved(str);
            }
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onMeetingMaxAmplitudeUpdate(short s) {
            PadHybridNoteEditFragment padHybridNoteEditFragment = this.iFragmentRef.get();
            if (padHybridNoteEditFragment != null) {
                padHybridNoteEditFragment.mEditAudioController.updateAudioVolume(s);
            }
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onSocketFailure() {
            PadHybridNoteEditFragment padHybridNoteEditFragment = this.iFragmentRef.get();
            if (padHybridNoteEditFragment == null || padHybridNoteEditFragment.getActivity() == null || !padHybridNoteEditFragment.mIsRecording) {
                return;
            }
            Toast.makeText(padHybridNoteEditFragment.getActivity(), R.string.audio_dialog_mode_meeting_recognize_fail, 0).show();
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onSocketClosed() {
            PadHybridNoteEditFragment padHybridNoteEditFragment = this.iFragmentRef.get();
            if (padHybridNoteEditFragment == null || !padHybridNoteEditFragment.mIsRecording) {
                return;
            }
            Toast.makeText(NoteApp.getInstance(), R.string.audio_dialog_mode_meeting_recognize_close, 0).show();
        }
    }

    public static class SpeechRecognitionImpl implements SpeechRecognitionListener {
        private WeakReference<PadHybridNoteEditFragment> iFragmentRef;

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrAppendTextResult(String str, boolean z) {
        }

        public SpeechRecognitionImpl(PadHybridNoteEditFragment padHybridNoteEditFragment) {
            this.iFragmentRef = new WeakReference<>(padHybridNoteEditFragment);
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrSaveAudioFileName(String str) {
            PadHybridNoteEditFragment padHybridNoteEditFragment = this.iFragmentRef.get();
            if (padHybridNoteEditFragment != null) {
                padHybridNoteEditFragment.notifyAudioFileSaved(str);
            }
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrMaxAmplitudeUpdate(short s) {
            PadHybridNoteEditFragment padHybridNoteEditFragment = this.iFragmentRef.get();
            if (padHybridNoteEditFragment != null) {
                padHybridNoteEditFragment.getEditToolBarController().updateAudioVolume(s);
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void createNewDoodle() {
        forbidListenKeyboardOnLeave();
        Utils.hideSoftInput(this.mEditorWebView);
        notifyAction(3, null);
        this.mWillBackFromInsertImage = true;
        super.createNewDoodle();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void notifyAction(int i, Bundle bundle) {
        super.notifyAction(i, bundle);
        if (i == 11) {
            this.mWillConvertToMindNote = true;
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void setForAccessibility(boolean z) {
        if (this.mRootView != null) {
            this.mRootView.setImportantForAccessibility(z ? 4 : 1);
        }
    }

    class UpdateNoteLoadTask extends BaseNormalNoteEditFragment.NoteLoadTask {
        long noteId;

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote parseWorkingNote() {
            return null;
        }

        UpdateNoteLoadTask(Intent intent, long j) {
            super(intent);
            this.noteId = j;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask
        protected WorkingNote loadWorkingNote() {
            return WorkingNote.loadNote("_id=" + this.noteId, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, WorkingNote workingNote) {
            Logger.INSTANCE.i(PadHybridNoteEditFragment.TAG, "onResult UpdateNoteLoadTask");
            if (PadHybridNoteEditFragment.this.mWorkingNote == null || workingNote.getModifiedDate() != PadHybridNoteEditFragment.this.mWorkingNote.getModifiedDate()) {
                Logger.INSTANCE.i(PadHybridNoteEditFragment.TAG, "onResult UpdateNoteLoadTask remote save note");
                return;
            }
            Logger.INSTANCE.i(PadHybridNoteEditFragment.TAG, "onResult UpdateNoteLoadTask same note");
            if (PadHybridNoteEditFragment.this.mLoadTask != this) {
                return;
            }
            PadHybridNoteEditFragment.this.mLoadTask = null;
            PadHybridNoteEditFragment.this.showLoadingData(false);
            if (TextUtils.isEmpty(this.searchContent)) {
                PadHybridNoteEditFragment padHybridNoteEditFragment = PadHybridNoteEditFragment.this;
                padHybridNoteEditFragment.updateUserQuery("", false, padHybridNoteEditFragment.mHighLightKeyWords);
                PadHybridNoteEditFragment.this.mSearchText = "";
            } else {
                if (TextUtils.isEmpty(this.searchContent) || this.searchContent.equals(PadHybridNoteEditFragment.this.mSearchText)) {
                    return;
                }
                PadHybridNoteEditFragment.this.mSearchText = this.searchContent;
                PadHybridNoteEditFragment padHybridNoteEditFragment2 = PadHybridNoteEditFragment.this;
                padHybridNoteEditFragment2.updateUserQuery(padHybridNoteEditFragment2.mSearchText, false, PadHybridNoteEditFragment.this.mHighLightKeyWords);
                PadHybridNoteEditFragment.this.updateActionMode(EditorActionMode.VIEW, false);
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateUserQuery(String str, boolean z, ArrayList<String> arrayList) {
        super.updateUserQuery(str, z, arrayList);
        if (this.mEditor == null) {
            return;
        }
        WebRichEditor webRichEditor = this.mEditor;
        if (TextUtils.isEmpty(str) || !this.mInSearchMode) {
            str = "";
        }
        if (!this.mInSearchMode) {
            arrayList = null;
        }
        webRichEditor.updateQueryContent(str, arrayList);
    }

    protected void initKeyboardHelper() {
        FragmentActivity activity;
        this.mIsInFullScreenMode = UIUtils.isInFullWindowGestureMode(getActivity());
        this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
        this.mEditToolbarController.updateLayout();
        if (UIUtils.isAboveAndroidR() && getView() != null && (activity = getActivity()) != null && !activity.isInMultiWindowMode()) {
            getView().setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback(0) { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment.18
                @Override // android.view.WindowInsetsAnimation.Callback
                public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
                    return windowInsets;
                }
            });
        }
        Logger.INSTANCE.d(TAG, "initKeyboardHelper,finished");
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void tryHideNote() {
        super.tryHideNote();
        if (this.mHandler != null && this.mActionBarController != null && ((ActionBarWithNaviController) this.mActionBarController).getNaviState()) {
            if (isWorkingNoteEmpty()) {
                return;
            }
            ((ActionBarWithNaviController) this.mActionBarController).showOrHideNaviState(true);
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$tryHideNote$19();
                }
            }, 200L);
            return;
        }
        if (isWorkingNoteEmpty()) {
            return;
        }
        this.homeViewModel.hideNote(this.mWorkingNote.getNoteId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryHideNote$19() {
        this.homeViewModel.hideNote(this.mWorkingNote.getNoteId());
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void unhideNote() {
        if (this.mHandler != null && this.mActionBarController != null && ((ActionBarWithNaviController) this.mActionBarController).getNaviState()) {
            ((ActionBarWithNaviController) this.mActionBarController).showOrHideNaviState(true);
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$unhideNote$20();
                }
            }, 200L);
        } else {
            if (isWorkingNoteEmpty()) {
                return;
            }
            this.homeViewModel.unHideNote(this.mWorkingNote.getNoteId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unhideNote$20() {
        if (isWorkingNoteEmpty()) {
            return;
        }
        this.homeViewModel.unHideNote(this.mWorkingNote.getNoteId());
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public void onImageElementClicked(EmbedElementInfoBean embedElementInfoBean) {
        this.mClickCount++;
        if (this.mClickCount > 1) {
            this.mClickCount = 0;
        } else {
            super.onImageElementClicked(embedElementInfoBean);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public void onLinkElementClicked(EmbedElementInfoBean embedElementInfoBean) {
        this.mClickCount++;
        if (this.mClickCount > 1) {
            this.mClickCount = 0;
        } else {
            super.onLinkElementClicked(embedElementInfoBean);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void launchSnsShareActivity(int i, int i2, String str, AigcPicMetaData aigcPicMetaData) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            PadSnsEditActivity.INSTANCE.launchActivity(this.mToShareResult, activity, this.mWorkingNote.getContent(), this.mWorkingNote.getTitle(), i, this.mWorkingNote.getThemeId(), this.mWorkingNote.getModifiedDate(), this.mEditor.getMFontTextSize(), str, this.mWorkingNote, this.mWorkingNote.getAiTextUsed() == 1 ? new AigcPicMetaData(this.mWorkingNote.getAigcDataId(), this.mWorkingNote.getAigcDataId()) : null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void onNoteDeleted(long j) {
        if (isAdded()) {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_delete_success, 0).show();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleDeleteIntent(Intent intent) {
        if (this.mActionListener != null) {
            Bundle bundle = new Bundle();
            bundle.putLong("note_id", intent.getLongExtra("android.intent.extra.UID", 0L));
            this.mActionListener.onAction(27, bundle);
        }
        return super.handleDeleteIntent(intent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected NoteThemeResCache loadNoteThemeResToCache(Theme theme) {
        return super.loadNoteThemeResToCache(ResourceManager.getTheme(0));
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void onPostUpdateNoteTheme(Theme theme, NoteThemeResCache noteThemeResCache, boolean z) {
        Theme theme2 = ResourceManager.getTheme(0);
        if (this.mSharePopupWindow != null) {
            this.mSharePopupWindow.updateStyle(theme2);
        }
        this.mEditToolbarController.updateTheme(theme2, noteThemeResCache);
        super.onPostUpdateNoteTheme(theme2, noteThemeResCache, false);
        this.mEditToolbarController.unlock();
    }

    public void removeWebFocusChangeCallbacks() {
        if (this.mEditorWebView != null) {
            this.mEditorWebView.removeCallbacks(this.mFocusChangeRunnable);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void initFloatingWindow() {
        super.initFloatingWindow();
        if (this.mNavigatorStateListener == null) {
            this.mNavigatorStateListener = new AiTextNavigatorStateListener();
            Navigator.get(this).addNavigatorStateListener(this.mNavigatorStateListener);
        }
    }

    private class AiTextNavigatorStateListener implements Navigator.NavigatorStateListener {
        private AiTextNavigatorStateListener() {
        }

        @Override // miuix.navigator.Navigator.NavigatorStateListener
        public void onNavigationCloseBegin() {
            super.onNavigationCloseBegin();
            BaseHybridNoteEditFragment.removeAiFloatingWindow();
        }

        @Override // miuix.navigator.Navigator.NavigatorStateListener
        public void onNavigationOpenBegin() {
            super.onNavigationOpenBegin();
            BaseHybridNoteEditFragment.removeAiFloatingWindow();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onShareOptions(Context context) {
        super.onShareOptions(context);
        if (getActivity() != null && UIUtils.isInMultiWindowMode(getActivity()) && isDialogShowing() && this.mEditorWebView != null && this.mEditorWebView.isFocused()) {
            Logger.INSTANCE.d(TAG, "onShareOptions show share dialog, clear webView focus");
            this.mEditorWebView.clearFocus();
        }
    }
}
