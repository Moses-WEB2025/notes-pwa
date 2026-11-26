package com.miui.pad.feature.notes.commonedit;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentActivity;
import androidx.hardware.DataSpace;
import androidx.lifecycle.Observer;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.constant.FrameConstant;
import com.miui.common.record.SpeechRecognitionListener;
import com.miui.common.record.SpeechRecognitionTask;
import com.miui.common.record.SpeechRecognitionTaskImp;
import com.miui.common.stat.EditStat;
import com.miui.common.stat.MindNoteStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.DualScreenManager;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.NetworkCallbackImpl;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.async.TaskManager;
import com.miui.common.view.IPopMenu;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.base.DoodleParams;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.adapter.ResolverAdapter;
import com.miui.notes.ai.AigcPicMetaData;
import com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.basefeature.noteedit.ColumnsNativeNoteEditFragment;
import com.miui.notes.component.noteedit.IBaseNoteEditToolbarController;
import com.miui.notes.component.noteedit.INoteEditToolBar;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.doodle.feature.DoodleActivity;
import com.miui.notes.doodle.feature.DoodleFragment;
import com.miui.notes.doodle.feature.PadDoodleActivity;
import com.miui.notes.doodle.feature.PadFullDoodleActivity;
import com.miui.notes.editor.BaseNoteRichEditorController;
import com.miui.notes.editor.NoteRichEditor;
import com.miui.notes.editor.ReadOnlyActionListener;
import com.miui.notes.feature.noteedit.CommonNoteEditToolBarController;
import com.miui.notes.model.WorkingNote;
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
import com.miui.notes.ui.widget.AnnotationEditController;
import com.miui.notes.ui.widget.SharePopupGroup;
import com.miui.pad.feature.notes.meeting.MeetingRecognitionListener;
import com.miui.pad.feature.notes.meeting.MeetingRecognitionTask;
import com.miui.pad.feature.notes.meeting.MeetingRecognitionTaskImp;
import com.miui.pad.feature.notes.share.PadSnsEditActivity;
import com.miui.pad.richeditor.PadNoteRichEditor;
import com.miui.pad.richeditor.style.PadNoteAudioSpan;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.IEditorPopMenu;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.theme.Theme;
import com.xiaomi.ai.mibrain.MibrainException;
import com.xiaomi.mirror.synergy.MiuiSynergySdk;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AlertDialog;
import miuix.miuixbasewidget.widget.EmptyStateView;
import miuix.navigator.Navigator;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes4.dex */
public class PadNoteEditFragment extends ColumnsNativeNoteEditFragment {
    private static final String TAG = "PadNoteEditFragment";
    private static boolean USE_MEETING_AUDIO = true;
    private GlobalFocusChangeListenerIml globalFocusChangeListener;
    private MeetingRecognitionTask mAudioMeetingTask;
    private IBaseNoteEditToolbarController mBarController;
    private EmptyStateView mBlankView;
    ConnectivityManager mConnMgr;
    private LinearLayout mContentView;
    private TextView mCountDownView;
    protected CommonPadNoteAudioController mEditAudioController;
    private boolean mIsWarned;
    private final NetworkCallbackImpl.NetworkCall mNetworkCall;
    NetworkCallbackImpl mNetworkCallback;
    private SpeechRecognitionTask mRecordAiTask;
    protected RecordModeChooserMenu mRecordModeChooserMenu;
    NetworkRequest mRequest;
    private MeetingRecognitionImpl meetingRecognitionListener;
    private SpeechRecognitionImpl speechRecognitionListener;
    private static final long MAX_RECORD_TIME = TimeUnit.SECONDS.toMillis(300);
    private static final long MAX_MEETING_RECORD_TIME = TimeUnit.SECONDS.toMillis(3600);
    private static final long LAST_WARRING_TIME = TimeUnit.SECONDS.toMillis(10);
    private final boolean mIsShowThemeFromTool = false;
    private boolean mActionFromToolbarRichText = false;
    private boolean mWillConvertToMindNote = false;
    private boolean mWillBackFromInsertImage = false;
    private boolean mIsNaviShow = true;
    private boolean mIsShowBlank = false;
    protected Runnable mPendingDataLoadTask = null;
    protected Runnable mPendingRefreshNoteTask = null;
    protected Runnable mPendingDisableListenKeyboardTask = null;
    private CountDownTimer mAudioRecordTimer = null;
    private boolean mSearchIsOnFocus = false;
    private final View.OnClickListener mToolbarClickListener = new View.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda5
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f$0.lambda$new$0(view);
        }
    };
    private final BroadcastReceiver mDataUpdateTaskReceiver = new BroadcastReceiver() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            PadNoteEditFragment.this.updateWorkingNoteFromOuter();
        }
    };
    private AlertDialog mDualScreenEditTipDialog = null;
    private final IEditorPopMenu.PopMenuListener mPopMenuListener = new IEditorPopMenu.PopMenuListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.2
        @Override // com.miui.richeditor.IEditorPopMenu.PopMenuListener
        public void onImageMenuClick(View view, BaseImageSpan baseImageSpan) {
            switch (view.getId()) {
                case R.id.annotate_image /* 2131362007 */:
                    PadNoteEditFragment.this.annotateImage(baseImageSpan);
                    break;
                case R.id.delete_image /* 2131362399 */:
                    if (PadNoteEditFragment.this.mAnnotationController != null && PadNoteEditFragment.this.mAnnotationController.isShowing()) {
                        PadNoteEditFragment.this.annotateImage(baseImageSpan);
                        break;
                    }
                    break;
                case R.id.edit_image /* 2131362464 */:
                    PadNoteEditFragment.this.editImage(baseImageSpan);
                    break;
                case R.id.scale_image /* 2131363347 */:
                    PadNoteEditFragment.this.toggleImageShowBig(baseImageSpan);
                    break;
                case R.id.view_image /* 2131363771 */:
                    PadNoteEditFragment.this.viewImage(baseImageSpan);
                    break;
            }
        }

        @Override // com.miui.richeditor.IEditorPopMenu.PopMenuListener
        public void onLinkCardMenuClick(View view, LinkCardSpan linkCardSpan) {
            if (view.getId() == R.id.view_link) {
                PadNoteEditFragment.this.viewLinkByBrowser(linkCardSpan);
            }
        }
    };
    private final PadRichEditorDragListener mEditorDragListener = new PadRichEditorDragListener(0);
    private final BlankViewDragListener mBlankViewDragListener = new BlankViewDragListener(0);
    private final BaseDragListener mBlockDragListener = new BaseDragListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.3
        @Override // com.miui.notes.ui.BaseDragListener
        protected boolean handleDragDrop(View view, DragEvent dragEvent) {
            return true;
        }

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
    };

    static /* synthetic */ void lambda$showDualScreenEditTipDialog$23(DialogInterface dialogInterface, int i) {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getAutoSaveCommonDuration() {
        return 1500;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getLayoutResId() {
        return R.layout.pad_note_edit;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getThemeResId() {
        return R.style.NavigatorSecondaryContentTheme;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleEditIntent(Intent intent) {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleSendIntent(Intent intent) {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initPasswordController() {
    }

    @Override // com.miui.notes.ui.BaseActionBarController.EditCallback
    public boolean isOnSaveInstanceCalled() {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.RecordTaskWorker
    public boolean isSupportRecordTaskWorker() {
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateWindowStatus(boolean z) {
    }

    public PadNoteEditFragment() {
        NetworkCallbackImpl.NetworkCall networkCall = new NetworkCallbackImpl.NetworkCall() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.4
            @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
            public void onAvailable() {
                PadNoteEditFragment.this.onNetworkStateChanged(true);
            }

            @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
            public void onLost() {
                PadNoteEditFragment.this.onNetworkStateChanged(false);
            }
        };
        this.mNetworkCall = networkCall;
        this.mNetworkCallback = new NetworkCallbackImpl(networkCall);
        this.mRequest = new NetworkRequest.Builder().build();
        this.mConnMgr = (ConnectivityManager) NoteApp.getInstance().getSystemService("connectivity");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (this.mWorkingNote == null || this.mWorkingNote.getIsInvalid()) {
            return;
        }
        switch (view.getId()) {
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
                    createNewDoodle();
                }
                break;
            case R.id.edit_info /* 2131362465 */:
                ExternalAppUtils.launchSystemUpdater(getActivity());
                break;
            case R.id.gallery /* 2131362611 */:
                if (!this.mNoteRichEditor.canAddNewElement(63)) {
                    onContentOverLength(4);
                } else {
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
        if (!this.mNoteRichEditor.canAddNewElement(63)) {
            onContentOverLength(0);
        } else {
            onAudioButtonClick();
        }
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
        CommonNoteEditToolBarController commonNoteEditToolBarController = new CommonNoteEditToolBarController(getActivity(), view, this.mEditorView);
        this.mBarController = commonNoteEditToolBarController;
        commonNoteEditToolBarController.setOnVisibleChangedListener(new INoteEditToolBar.OnVisibleChangedListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda13
            @Override // com.miui.notes.component.noteedit.INoteEditToolBar.OnVisibleChangedListener
            public final void onVisibilityChanged(boolean z) {
                this.f$0.lambda$createToolBarController$1(z);
            }
        });
        return this.mBarController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createToolBarController$1(boolean z) {
        LinearLayout linearLayout = this.mContentView;
        if (linearLayout != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
            if (UIUtils.isInFullWindowGestureMode(getActivity())) {
                layoutParams.bottomMargin = z ? NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_height) + this.mNavigationBarHeight : 0;
            } else {
                layoutParams.bottomMargin = z ? NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.notes_edit_toolbar_height) : 0;
            }
            this.mContentView.setLayoutParams(layoutParams);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected INoteInfoHeader createNoteExtraInfoController(View view, View view2) {
        return new PadNoteInfoController(getContext(), view, view2);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(final NoteLoadDataEntity noteLoadDataEntity, boolean z) {
        dismissImmersionMenu(false);
        if (this.mNoteExtraInfoController != null) {
            this.mNoteExtraInfoController.dismissMenu();
        }
        if (!isAdded()) {
            this.mPendingRefreshNoteTask = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$refreshNote$2(noteLoadDataEntity);
                }
            };
            return;
        }
        if (noteLoadDataEntity != null && this.mWorkingNote != null && noteLoadDataEntity.getNoteId() != this.mWorkingNote.getNoteId() && this.mEditorView != null) {
            this.mEditorView.stopMediaPlayerIfNeed();
        }
        if (this.mIsRecording) {
            if (noteLoadDataEntity == null || this.mWorkingNote == null || noteLoadDataEntity.getNoteId() != this.mWorkingNote.getNoteId() || noteLoadDataEntity.getModifiedTime() > this.mWorkingNote.getModifiedDate()) {
                this.mPendingDataLoadTask = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$refreshNote$3(noteLoadDataEntity);
                    }
                };
                onAudioButtonClick();
                return;
            }
            return;
        }
        if (this.mEditorController != null) {
            this.mEditorController.dismissShowingPopupWindow();
        }
        if (noteLoadDataEntity != null) {
            hideAnnotationController();
            try {
                loadNoteFromEntity(noteLoadDataEntity);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshNote$2(NoteLoadDataEntity noteLoadDataEntity) {
        refreshNote(noteLoadDataEntity, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshNote$3(NoteLoadDataEntity noteLoadDataEntity) {
        refreshNote(noteLoadDataEntity, false);
    }

    private void hideAnnotationController() {
        if (this.mAnnotationController != null) {
            try {
                if (this.mAnnotationController.isShowing()) {
                    this.mAnnotationController.hide();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        Logger.INSTANCE.d(TAG, "onCreate");
        this.mWorkingNote = null;
        com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "onCreate/setWorkNote mWorkingNote null");
        super.onCreate(bundle);
        USE_MEETING_AUDIO = (RomUtils.isInternationalBuild() || LiteUtils.isLiteOrMiddle()) ? false : true;
        if (this.mIsFromRebuild) {
            this.mWillBackFromInsertImage = false;
        }
    }

    @Override // miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle bundle) {
        super.onUpdateArguments(bundle);
        Logger.INSTANCE.d(TAG, "Detail newArguments:" + (bundle != null));
        if (bundle != null) {
            refreshNote((NoteLoadDataEntity) bundle.getSerializable("entity"), false);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        updateHomeButtonState(this.mScreenSpec);
        ((ActionBarWithNaviController) this.mActionBarController).setNaviState(this.mIsNaviShow);
        if (getActivity() != null) {
            updateSoftInputMode(getActivity());
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FrameConstant.ACTION_NOTE_DATA_UPDATE);
        this.mLocalBroadcastManager.registerReceiver(this.mDataUpdateTaskReceiver, intentFilter);
        Runnable runnable = this.mPendingRefreshNoteTask;
        if (runnable != null) {
            runnable.run();
            this.mPendingRefreshNoteTask = null;
        }
        if (getArguments() != null) {
            NoteLoadDataEntity noteLoadDataEntity = (NoteLoadDataEntity) getArguments().getSerializable("entity");
            if (this.mWorkingNote == null || noteLoadDataEntity == null || this.mWorkingNote.getNoteId() == noteLoadDataEntity.getNoteId()) {
                refreshNote(noteLoadDataEntity, false);
            }
        }
        this.homeViewModel.getKeyBoardShowStatus().observe(this, new Observer() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.keyBoardShowStatusChange(((Boolean) obj).booleanValue());
            }
        });
        this.homeViewModel.getToEditModeLiveData().observe(this, new Observer() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$4((Boolean) obj);
            }
        });
        this.homeViewModel.getSearchOnFocusLiveData().observe(this, new Observer() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$5((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$4(Boolean bool) {
        enterEditMode(0L, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$5(Boolean bool) {
        this.mSearchIsOnFocus = bool.booleanValue();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        this.mPendingDisableListenKeyboardTask = null;
        super.onResume();
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
            this.mEditToolbarController.updateLayout();
        }
        RecordModeChooserMenu recordModeChooserMenu = this.mRecordModeChooserMenu;
        if (recordModeChooserMenu != null) {
            recordModeChooserMenu.dismiss();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (FloatWindowManagerAgent.clickExcerptNote()) {
            FloatWindowManagerAgent.resetClickExcerptNote();
        } else if (this.mIsRecording) {
            onAudioButtonClick();
        }
        if (isInEditAction()) {
            this.homeViewModel.setEditStatus(false);
            updateActionMode(EditorActionMode.VIEW, true);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (FloatWindowManagerAgent.isContinuousExcerpt() && isThisNoteDoExcerpt()) {
            FloatWindowManagerAgent.resumeContinuousExcerpt();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        deleteEmptyNote();
        if (isInEditAction()) {
            updateActionMode(EditorActionMode.VIEW, true);
        }
        FragmentActivity activity = getActivity();
        if (getMIsKeyboardVisible() && activity != null) {
            Utils.hideSoftInput(activity);
        }
        if (this.mEditAreaLayout != null) {
            this.mEditAreaLayout.getViewTreeObserver().removeOnGlobalFocusChangeListener(this.globalFocusChangeListener);
        }
        hideAnnotationController();
        LinearLayout linearLayout = this.mContentView;
        if (linearLayout != null) {
            Folme.clean(linearLayout);
        }
    }

    private void deleteEmptyNote() {
        if (isDataEmpty()) {
            NoteStore.delete(getContext(), new long[]{this.mWorkingNote.getNoteId()});
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        if (!this.mWillConvertToMindNote && !this.mWillBackFromInsertImage) {
            killSaveTask();
        } else {
            this.mWillConvertToMindNote = false;
            this.mWillBackFromInsertImage = false;
        }
        DoodleApplication.cancelAndDeletePreloadData();
        if (this.mIsRecording && isUseMeetingAudio()) {
            this.mIsRecording = false;
            MeetingRecognitionTask meetingRecognitionTask = this.mAudioMeetingTask;
            if (meetingRecognitionTask != null) {
                meetingRecognitionTask.stopEngine();
            }
        }
        if (this.mLocalBroadcastManager != null) {
            this.mLocalBroadcastManager.unregisterReceiver(this.mDataUpdateTaskReceiver);
        }
        LinearLayout linearLayout = this.mContentView;
        if (linearLayout != null) {
            Folme.clean(linearLayout);
        }
        if (this.mEditAreaLayout != null) {
            Folme.clean(this.mEditAreaLayout);
        }
        if (getActionBarController() != null && getActionBarController().getHeadInfo() != null) {
            Folme.clean(getActionBarController().getHeadInfo());
        }
        if (this.mNoteExtraInfoController != null) {
            this.mNoteExtraInfoController.onDestroy();
        }
        if (this.mAnnotationController != null) {
            this.mAnnotationController.cancel();
            this.mAnnotationController = null;
        }
        super.onDestroy();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        boolean zCommitComposingContent = false;
        if (dismissAllDialogOrPanel()) {
            return false;
        }
        boolean zOnBackPressed = this.mEditToolbarController.onBackPressed();
        if (this.mActionMode == EditorActionMode.EDIT_RECODE) {
            updateActionMode(EditorActionMode.VIEW, true);
            return true;
        }
        if (zOnBackPressed && isInEditAction()) {
            updateActionMode(EditorActionMode.VIEW, true);
        }
        if (!zOnBackPressed) {
            if (this.mEditorView != null) {
                this.mEditorView.stopMediaPlayerIfNeed();
                zCommitComposingContent = this.mEditorView.commitComposingContent();
            }
            if (zCommitComposingContent) {
                asyncSaveNote();
            }
            this.mIsKillSaveTaskBeforeDestroy = true;
            killSaveTask();
        }
        return zOnBackPressed;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        if (this.mEditorView.isFocused()) {
            return this.mEditorView.onKeyShortcut(i, keyEvent);
        }
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initView(View view) {
        super.initView(view);
        view.setOnDragListener(this.mBlockDragListener);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.sv_note_edit);
        this.mContentView = linearLayout;
        linearLayout.setAlpha(0.0f);
        this.mContentView.setVisibility(8);
        this.mBlankView = (EmptyStateView) view.findViewById(R.id.pad_edit_blank_view);
        this.mBlankViewDragListener.setEditFragment(this);
        this.mBlankView.setOnDragListener(this.mBlankViewDragListener);
        this.mTitleEditor = (EditText) view.findViewById(R.id.note_title);
        this.mTitleEditor.removeTextChangedListener(this.mTitleEditorTextWatcher);
        this.mTitleEditor.setText("");
        smallWindowAdapter(this.mContentView);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onNetworkStateChanged(boolean z) {
        if (z) {
            if (isUseMeetingAudio()) {
                this.mAudioMeetingTask.setNetWorkValid(true);
                this.mAudioMeetingTask.startEngine();
                return;
            } else {
                this.mRecordAiTask.setNetWorkValid(true);
                this.mRecordAiTask.startEngine();
                return;
            }
        }
        if (isUseMeetingAudio()) {
            this.mAudioMeetingTask.setNetWorkValid(false);
            this.mAudioMeetingTask.stopEngine();
        } else {
            this.mRecordAiTask.setNetWorkValid(false);
            this.mRecordAiTask.stopEngine();
        }
        if (getActivity() != null) {
            Toast.makeText(getActivity(), R.string.audio_toast_network_is_not_available, 0).show();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void onNoteDeleted(long j) {
        if (isAdded()) {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_delete_success, 0).show();
            if (this.mActionListener != null) {
                Bundle bundle = new Bundle();
                bundle.putLong("note_id", j);
                this.mActionListener.onAction(27, bundle);
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initNoteEditor(View view) {
        super.initNoteEditor(view);
        this.mEditorController.setMenuListener(this.mPopMenuListener);
        this.mEditorView.setOutKeyListener(new OnKeyDownAndShortCutListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.5
            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyDown(int i, KeyEvent keyEvent) {
                return false;
            }

            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyUp(int i, KeyEvent keyEvent) {
                return false;
            }

            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
                if (keyEvent.hasModifiers(4096) && keyEvent.getRepeatCount() == 0) {
                    if (i == 41) {
                        if (!Utils.isFastClick(0, 500L)) {
                            PadNoteEditFragment.this.homeViewModel.createMindNote();
                        }
                        return true;
                    }
                    if (i == 42) {
                        if (!Utils.isFastClick(0, 500L)) {
                            PadNoteEditFragment.this.homeViewModel.createNormalNote();
                        }
                        return true;
                    }
                }
                if (keyEvent.hasModifiers(4098)) {
                    if (i == 31) {
                        PadNoteEditFragment.this.addAttachment(2);
                        return true;
                    }
                    if (i == 32) {
                        PadNoteEditFragment.this.createNewDoodle();
                        return true;
                    }
                    if (i == 44) {
                        PadNoteEditFragment.this.addAttachment(1);
                        return true;
                    }
                    if (i == 50) {
                        PadNoteEditFragment.this.clickAudionBtnAboveOS2();
                        return true;
                    }
                }
                return false;
            }
        });
        this.mEditAudioController = new CommonPadNoteAudioController(this.mEditorView, this.mContentView, new CommonPadNoteAudioController.OnPanelListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.6
            @Override // com.miui.pad.feature.notes.commonedit.CommonPadNoteAudioController.OnPanelListener
            public void onShow(View view2) {
                if (PadNoteEditFragment.this.isAdded()) {
                    PadNoteEditFragment.this.startRecordTask();
                }
            }

            @Override // com.miui.pad.feature.notes.commonedit.CommonPadNoteAudioController.OnPanelListener
            public void onDismiss() {
                if (PadNoteEditFragment.this.isAdded()) {
                    PadNoteEditFragment.this.setEditContentMarginBottom(0);
                }
            }

            @Override // com.miui.notes.ui.view.NoteEditAudioTransGroup.OnTransClickListener
            public void onViewClick(View view2) {
                if (PadNoteEditFragment.this.isAdded()) {
                    switch (view2.getId()) {
                        case R.id.flag /* 2131362550 */:
                            if (PadNoteEditFragment.this.mAudioMeetingTask != null) {
                                PadNoteEditFragment.this.mAudioMeetingTask.addEnterFlag();
                            }
                            break;
                        case R.id.mode /* 2131362969 */:
                            PadNoteEditFragment.this.mRecordModeChooserMenu = new RecordModeChooserMenu(PadNoteEditFragment.this.getContext());
                            PadNoteEditFragment.this.mRecordModeChooserMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.6.1
                                @Override // com.miui.common.view.IPopMenu.OnMenuListener
                                public void onDismiss() {
                                }

                                @Override // com.miui.common.view.IPopMenu.OnMenuListener
                                public void onShow() {
                                }

                                @Override // com.miui.common.view.IPopMenu.OnMenuListener
                                public void onItemSelected(View view3, int i) {
                                    int id = view3.getId();
                                    if (id == R.id.mode_meeting) {
                                        if (PadNoteEditFragment.this.mEditAudioController.isSingleMode()) {
                                            PadNoteEditFragment.this.showAudioMeetingDialog();
                                        }
                                    } else {
                                        if (id != R.id.mode_single) {
                                            return;
                                        }
                                        PadNoteEditFragment.this.mEditAudioController.setMode(true);
                                        PadNoteEditFragment.this.updateMeetingAudioTaskInfo();
                                    }
                                }
                            });
                            PadNoteEditFragment.this.mRecordModeChooserMenu.show(PadNoteEditFragment.this.mEditAudioController.getModeMenuAnchorView(), null);
                            break;
                        case R.id.mode_meeting /* 2131362974 */:
                            if (PadNoteEditFragment.this.mEditAudioController.isSingleMode()) {
                                PadNoteEditFragment.this.showAudioMeetingDialog();
                            }
                            break;
                        case R.id.mode_single /* 2131362976 */:
                            PadNoteEditFragment.this.mEditAudioController.setMode(true);
                            PadNoteEditFragment.this.updateMeetingAudioTaskInfo();
                            break;
                        case R.id.stop /* 2131363538 */:
                            if (PadNoteEditFragment.this.mIsRecording) {
                                PadNoteEditFragment.this.mIsRecording = false;
                                PadNoteEditFragment.this.mEditAudioController.dismiss();
                                PadNoteEditFragment.this.updateActionMode(EditorActionMode.VIEW, true);
                                if (PadNoteEditFragment.this.mAudioMeetingTask != null) {
                                    PadNoteEditFragment.this.mAudioMeetingTask.stopEngine();
                                }
                            }
                            break;
                        case R.id.translation /* 2131363690 */:
                            PadNoteEditFragment.this.mEditAudioController.setLanTransEnable(true ^ PadNoteEditFragment.this.mEditAudioController.isLanTrans());
                            PadNoteEditFragment.this.updateMeetingAudioTaskInfo();
                            break;
                    }
                }
            }
        });
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (this.mEditToolbarController != null) {
            this.mScreenSpec.type = getResponsiveState().getType();
            updateToolBarLayout();
        } else {
            initToolbarController(view);
        }
        this.mAnnotationController = new AnnotationEditController(activity.findViewById(R.id.annotate_group), activity.findViewById(R.id.annotate_placeholder));
        if (this.mIsFromRebuild) {
            if (getEditToolBarController() != null) {
                getEditToolBarController().hide(false);
            }
            this.mAnnotationController.hide();
        }
        this.globalFocusChangeListener = new GlobalFocusChangeListenerIml(this);
        this.mEditAreaLayout.getViewTreeObserver().addOnGlobalFocusChangeListener(this.globalFocusChangeListener);
    }

    private static class GlobalFocusChangeListenerIml implements ViewTreeObserver.OnGlobalFocusChangeListener {
        private final WeakReference<PadNoteEditFragment> reference;

        public GlobalFocusChangeListenerIml(PadNoteEditFragment padNoteEditFragment) {
            this.reference = new WeakReference<>(padNoteEditFragment);
        }

        @Override // android.view.ViewTreeObserver.OnGlobalFocusChangeListener
        public void onGlobalFocusChanged(View view, View view2) {
            PadNoteEditFragment padNoteEditFragment = this.reference.get();
            if (view == null || view2 == null || padNoteEditFragment == null || padNoteEditFragment.getContext() == null || !padNoteEditFragment.isAdded() || !KeyboardHelper.isHardwareKeyboard(padNoteEditFragment.getContext())) {
                return;
            }
            if (view.getId() == R.id.note_title && view2.getId() == R.id.rich_editor) {
                padNoteEditFragment.updateActionMode(EditorActionMode.EDIT, false);
                return;
            }
            if (view2.getId() == R.id.note_title && view.getId() == R.id.rich_editor) {
                padNoteEditFragment.updateActionMode(EditorActionMode.EDIT_TITLE, false);
                return;
            }
            if (view.getId() == R.id.note_title || view.getId() == R.id.rich_editor) {
                if (view2.getId() == R.id.recycler_view || view2.getId() == R.id.folder_rv) {
                    padNoteEditFragment.updateActionMode(EditorActionMode.VIEW, false);
                }
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initToolbarController(View view) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
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
        this.mEditToolbarController = createToolBarController(activity.findViewById(R.id.editor_mode_tool_group));
        this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
        this.mEditToolbarController.setReadOnlyActionListener(new ReadOnlyActionListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda27
            @Override // com.miui.notes.editor.ReadOnlyActionListener
            public final void onConfirmEdit(Runnable runnable) {
                this.f$0.lambda$initToolbarController$7(runnable);
            }
        });
        this.mEditToolbarController.setExternalClickListener(this.mToolbarClickListener);
        this.mEditToolbarController.setOnThemeChosenListener(this.mThemeChosenListener);
        this.mTitleEditor.clearFocus();
        this.mEditorView.clearFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initToolbarController$7(final Runnable runnable) {
        confirmEditAnyway(new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda26
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$initToolbarController$6(runnable, dialogInterface, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initToolbarController$6(Runnable runnable, DialogInterface dialogInterface, int i) {
        setReadonly(false);
        runnable.run();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected BaseNoteRichEditorController createEditorController() {
        return new PadNoteRichEditorController(this, this.mEditorView, this.mTitleEditor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public void editImage(BaseImageSpan baseImageSpan) {
        if (baseImageSpan instanceof NormalImageSpan) {
            forbidListenKeyboardOnLeave();
            this.mEditorView.cacheSelection(true);
            this.mWillBackFromInsertImage = true;
            DoodleParams doodleParamsCreateEditImageParams = createEditImageParams(baseImageSpan);
            if (doodleParamsCreateEditImageParams != null) {
                this.mEditingImageSpan = (NormalImageSpan) baseImageSpan;
                this.mEditorController.hideInputMethod();
                notifyAction(3, null);
                startToDoodle(doodleParamsCreateEditImageParams, this.mEidtDooleResult);
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void startToDoodle(DoodleParams doodleParams, ActivityResultLauncher<Intent> activityResultLauncher) {
        Intent intent;
        if (isAdded()) {
            if (RomUtils.isFoldDevice()) {
                intent = new Intent(getContext(), (Class<?>) PadFullDoodleActivity.class);
            } else {
                intent = new Intent(getContext(), (Class<?>) PadDoodleActivity.class);
            }
            intent.putExtra(DoodleFragment.KEY_PARAMS, doodleParams);
            intent.putExtra("screen_status", this.mScreenStatus);
            if (this.mWorkingNote != null) {
                intent.putExtra(DoodleActivity.EXTRA_KEY_IS_HIDDEN, this.mWorkingNote.isHidden());
            }
            activityResultLauncher.launch(intent);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public void annotateImage(final BaseImageSpan baseImageSpan) {
        final FragmentActivity activity = getActivity();
        if (activity == null || this.mAnnotationController == null) {
            return;
        }
        if (!this.mAnnotationController.isShowing()) {
            this.mRootView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$annotateImage$8();
                }
            }, 500L);
            this.mEditToolbarController.hide(false);
            this.mAnnotationController.setEditListener(new AnnotationEditController.EditListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.7
                @Override // com.miui.notes.ui.widget.AnnotationEditController.EditListener
                public void onOverLength() {
                    PadNoteEditFragment.this.onImageAnnotationOverLength();
                }

                @Override // com.miui.notes.ui.widget.AnnotationEditController.EditListener
                public void onCompete(String str) {
                    baseImageSpan.setAnnotation(str);
                    PadNoteEditFragment.this.mEditorView.refreshAllImageSpans();
                    PadNoteEditFragment.this.mEditorView.createNewRichTextData();
                    PadNoteEditFragment.this.saveNote(true);
                    PadNoteEditFragment.this.mEditorView.requestLayout();
                    PadNoteEditFragment.this.annotateImage(baseImageSpan);
                    PadNoteEditFragment.this.mEditorView.refreshAllImageSpans();
                    PadNoteEditFragment.this.mEditorView.setSelection(PadNoteEditFragment.this.mEditorView.getText().getSpanEnd(baseImageSpan));
                    PadNoteEditFragment.this.mEditorScrollView.setForbidScroll(false);
                }
            });
            this.mEditorView.clearTextWatcher();
            if (this.mEditorView.getText() != null) {
                this.mEditorView.setSelection(this.mEditorView.getText().getSpanEnd(baseImageSpan));
            }
            this.mEditorView.requestFocus();
            final int selectionEnd = this.mEditorView.getSelectionEnd();
            if (getMIsKeyboardVisible()) {
                this.mEditorView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda21
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$annotateImage$9(selectionEnd);
                    }
                }, 310L);
            } else if (DisplayUtils.getRealScreenOrientation(getContext()) != 2) {
                this.mEditorView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda22
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$annotateImage$10(baseImageSpan, activity);
                    }
                }, 310L);
            }
            this.mAnnotationController.setAnnotation(baseImageSpan.getAnnotation());
            this.mAnnotationController.show();
            return;
        }
        this.mAnnotationController.hide();
        this.mEditorView.restoreTextWatcher();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$annotateImage$8() {
        this.mEditorScrollView.setForbidScroll(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$annotateImage$9(int i) {
        this.mEditorScrollView.setForbidScroll(false);
        this.mEditorView.bringPointIntoView(Math.min(i + 1, this.mEditorView.getText() != null ? this.mEditorView.getText().length() : 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$annotateImage$10(BaseImageSpan baseImageSpan, Activity activity) {
        if (isAdded()) {
            this.mEditorScrollView.setForbidScroll(false);
            Rect rect = new Rect(baseImageSpan.getBounds());
            rect.offset(0, activity.getResources().getDimensionPixelSize(R.dimen.image_annotation_panel_height) + UIUtils.dip2px(NoteApp.getInstance(), 40.0f));
            rect.top = rect.bottom;
            this.mEditorScrollView.requestChildRectangleOnScreen(this.mEditAreaLayout, rect, true);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void updateNoteTheme(Theme theme, boolean z) {
        if (this.mCurrentTheme == null) {
            this.mCurrentTheme = ResourceManager.getTheme(0);
        }
        if (TextUtils.isEmpty(this.mTitleEditor.getText().toString())) {
            this.mTitleEditor.setHint(R.string.edit_note_hint_title);
        }
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.lock();
        }
        onPostUpdateNoteTheme(this.mCurrentTheme, loadNoteThemeResToCache(this.mCurrentTheme), false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected NoteThemeResCache loadNoteThemeResToCache(Theme theme) {
        return super.loadNoteThemeResToCache(ResourceManager.getTheme(0));
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void onPostUpdateNoteTheme(Theme theme, NoteThemeResCache noteThemeResCache, boolean z) {
        Theme theme2 = ResourceManager.getTheme(0);
        if (this.mAnnotationController != null) {
            this.mAnnotationController.updateStyle(theme2);
        }
        if (this.mSharePopupWindow != null) {
            this.mSharePopupWindow.updateStyle(theme2);
        }
        this.mEditToolbarController.updateTheme(theme2, noteThemeResCache);
        super.onPostUpdateNoteTheme(theme2, noteThemeResCache, false);
        updateEditContentMargin(Navigator.get(this).getNavigationMode() != Navigator.Mode.C);
        this.mEditToolbarController.unlock();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void updateEditorTheme(Theme theme, Boolean bool) {
        if (!isAdded() || getActivity() == null) {
            return;
        }
        if (theme.getId() != 0) {
            theme = ResourceManager.getTheme(0);
        }
        Resources resources = getActivity().getResources();
        Theme.EditorStyle editorStyle = theme.getEditorStyle(getActivity(), this.mWorkingNote != null ? this.mWorkingNote.getModifiedDate() : System.currentTimeMillis());
        Rect rect = new Rect();
        rect.set(theme.getForegroundPadding(getActivity()));
        int color = resources.getColor(editorStyle.mTextColor);
        if (!UIUtils.isNightMode(getContext())) {
            color = (color & 16777215) - 570425344;
        }
        this.mEditorView.setTextColor(color);
        this.mTitleEditor.setTextColor(color);
        this.mEditorView.setHintTextColor(resources.getColor(editorStyle.mEditorHintColor));
        this.mTitleEditor.setHintTextColor(resources.getColor(editorStyle.mEditorHintColor));
        if (bool.booleanValue()) {
            Rect rect2 = new Rect(rect);
            rect2.left += (int) resources.getDimension(R.dimen.note_editor_padding);
            rect2.right -= (int) resources.getDimension(R.dimen.note_editor_padding);
            changePaddingOnThemeChanged(editorStyle.mForeground, rect2);
        } else {
            Rect rect3 = new Rect(rect);
            rect3.left += (int) resources.getDimension(R.dimen.note_editor_padding);
            rect3.right -= (int) resources.getDimension(R.dimen.note_editor_padding);
            getActionBarController().updateHeadInfoMargin(rect3);
        }
        this.mEditorView.setGravity(editorStyle.mTextGravity);
        int color2 = resources.getColor(editorStyle.mCursorColor);
        int color3 = resources.getColor(editorStyle.mHandleColor);
        CompatUtils.invokeCursorColor(this.mEditorView, color2, color3);
        CompatUtils.invokeCursorColor(this.mTitleEditor, color2, color3);
        CompatUtils.invokeCursorController(this.mEditorView);
        this.mTitleEditor.setHighlightColor(resources.getColor(editorStyle.mHighLightColor));
        this.mEditorView.setHighlightColor(resources.getColor(editorStyle.mHighLightColor));
        this.mEditorView.setLinkTextColor(resources.getColor(editorStyle.mLinkedColor));
    }

    protected void updateEditContentMargin(boolean z) {
        int dimensionPixelSize;
        if (!isAdded() || getActivity() == null) {
            return;
        }
        Resources resources = getActivity().getResources();
        int i = resources.getConfiguration().orientation;
        Rect rect = new Rect();
        if (getResponsiveState().getType() == 1 || z) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.pad_note_edit_content_margin_common);
        } else if (!RomUtils.isFoldDevice() && i == 2) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.pad_note_edit_content_margin_land_no_navi);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.pad_note_edit_content_margin_port_no_navi);
        }
        rect.set(dimensionPixelSize, 0, dimensionPixelSize, 0);
        Rect rect2 = new Rect(rect);
        if (this.mEditAreaLayout == null) {
            return;
        }
        if (this.mEditAreaLayout.getPaddingLeft() <= 0) {
            getActionBarController().updateHeadInfoMargin(rect2);
            this.mEditAreaLayout.setPadding(rect.left, 0, rect.right, 0);
            return;
        }
        int dimension = (int) resources.getDimension(R.dimen.pad_note_editor_padding);
        if (RomUtils.isPadDevice() && getResponsiveState().getType() == 3 && i == 2) {
            rect2 = new Rect(rect.left + dimension, 0, rect.right + dimension, 0);
        } else {
            rect.left -= dimension;
            rect.right -= dimension;
        }
        getActionBarController().updateHeadInfoMargin(rect2);
        this.mEditAreaLayout.setPadding(rect.left, 0, rect.right, 0);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public void onEditorClick(boolean z) {
        if (isAdded()) {
            this.mNestedHeaderLayout.setAutoTriggerClose(true);
            this.mEditorView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onEditorClick$11();
                }
            }, getMIsKeyboardVisible() ? 0L : 300L);
            if (this.mEditToolbarController != null && !this.mEditToolbarController.isTextStylePanelShowed() && this.mActionMode == EditorActionMode.EDIT && !z && annotationHide()) {
                this.mEditToolbarController.show(false, false, false);
            } else {
                updateActionMode(EditorActionMode.EDIT, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEditorClick$11() {
        if (!this.mEditorView.hasFocus()) {
            this.mEditorView.requestFocus();
        }
        this.mEditorView.bringPointIntoView(this.mEditorView.getSelectionEnd());
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public boolean onEditorLongClick() {
        if (isAdded()) {
            this.mNestedHeaderLayout.setAutoTriggerClose(true);
            this.mEditorView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onEditorLongClick$12();
                }
            }, getMIsKeyboardVisible() ? 0L : 400L);
            if (this.mEditToolbarController != null && !this.mEditToolbarController.isTextStylePanelShowed() && this.mActionMode == EditorActionMode.EDIT) {
                this.mEditToolbarController.show(false, true, false);
            } else {
                updateActionMode(EditorActionMode.EDIT, true);
            }
            if (CompatUtils.invokeIsInMultiWindowMode(getActivity())) {
                this.mEditorController.forceShowInputMethod();
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEditorLongClick$12() {
        this.mEditorView.bringPointIntoView(this.mEditorView.getSelectionEnd());
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void updateActionMode(EditorActionMode editorActionMode, boolean z) {
        if (editorActionMode == null || editorActionMode == this.mActionMode) {
            if (editorActionMode == EditorActionMode.EDIT) {
                if (this.mEditToolbarController.shouldHideSoftInput()) {
                    this.mEditorController.hideInputMethod();
                    this.mEditToolbarController.checkAndCollapseThemePanel();
                    return;
                } else {
                    if (getMIsKeyboardVisible()) {
                        return;
                    }
                    this.mEditorController.showInputMethod();
                    if (this.mInSearchMode) {
                        this.mEditToolbarController.show(true, false, false);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        super.updateActionMode(editorActionMode, z);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void onEnterActionMode() {
        super.onEnterActionMode();
        if (this.mIsRecording) {
            onAudioButtonClick();
        }
        if ((this.mEditorController == null || this.mEditorView == null || !this.mEditorView.hasFocus()) && (this.mTitleEditor == null || !this.mTitleEditor.hasFocus())) {
            return;
        }
        updateActionMode(EditorActionMode.VIEW, true);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void onExitActionMode() {
        super.onExitActionMode();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void enterEditMode(long j, final boolean z) {
        if (this.mEditorView == null) {
            return;
        }
        Editable text = this.mEditorView.getText();
        if (text != null) {
            this.mEditorView.setSelection(text.length());
        }
        this.mEditorView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$enterEditMode$13(z);
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enterEditMode$13(boolean z) {
        if (this.mShowingPopupWindow == null || !this.mShowingPopupWindow.isShowing()) {
            onEditorClick(z);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void editToView(boolean z) {
        Logger.INSTANCE.d(TAG, "editToView hideToolBar = " + z);
        this.mEditToolbarController.onViewMode(z);
        super.editToView(z);
        if (this.mActionFromToolbarRichText) {
            this.mActionFromToolbarRichText = false;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void homeToView() {
        super.homeToView();
        this.mEditToolbarController.onViewMode(true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void titleToView() {
        super.titleToView();
        this.mEditToolbarController.onViewMode(true);
        Utils.hideSoftInput(this.mTitleEditor);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void recordToView() {
        super.recordToView();
        recordNotifyNavigation(false);
        this.mEditToolbarController.onViewMode(true);
        if (this.mIsRecording) {
            this.mIsRecording = false;
            if (isUseMeetingAudio()) {
                this.mEditAudioController.dismiss();
            } else {
                this.mEditToolbarController.closeAudioRecordPanel();
            }
        }
        this.mEditorView.clearFocus();
        stopRecordTask();
        this.mEditToolbarController.resetRichTextStatus();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void viewToEdit() {
        Logger.INSTANCE.d(TAG, "viewToEdit");
        if (!this.mActionFromToolbarRichText) {
            if (this.mEditorView.getText() != null) {
                this.mEditorView.setSelection(this.mEditorView.getText().length());
            }
            this.mEditToolbarController.collapsePanel(null, false);
        } else {
            this.mEditorView.requestFocus();
            this.mEditorView.setSelection(this.mEditorView.getCacheSelectionStart());
            this.mEditorView.cacheSelection(false);
        }
        annotationHide();
        this.mEditToolbarController.isNeedLock(true);
        this.mEditToolbarController.onEditContentMode(true ^ this.mActionFromToolbarRichText, false, false);
        onEditMode();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void homeToEdit() {
        Logger.INSTANCE.d(TAG, "homeToEdit");
        super.homeToEdit();
        this.mEditToolbarController.resetRichTextStatus();
        this.mEditToolbarController.onEditContentMode(true, false, false);
        if (this.mHandler != null) {
            this.mHandler.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$homeToEdit$14();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$homeToEdit$14() {
        this.mEditorView.requestFocus();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void titleToEdit() {
        super.titleToEdit();
        this.mEditToolbarController.resetRichTextStatus();
        this.mEditToolbarController.collapsePanel(null, false);
        this.mEditToolbarController.onEditContentMode(true, false, false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void editToTitle(boolean z) {
        super.editToTitle(z);
        this.mEditToolbarController.onEditTitleMode();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void viewToTitle() {
        Logger.INSTANCE.d(TAG, "viewToTitle");
        super.viewToTitle();
        this.mEditToolbarController.onEditTitleMode();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void viewToRecord() {
        if (isAdded()) {
            super.viewToRecord();
            recordNotifyNavigation(true);
            this.mEditToolbarController.onEditContentMode(false, false, false);
            preStartRecordTask();
            this.mIsRecording = true;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void editToRecord() {
        if (isAdded()) {
            super.editToRecord();
            recordNotifyNavigation(true);
            preStartRecordTask();
            this.mIsRecording = true;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void homeToRecord() {
        if (isAdded()) {
            super.homeToRecord();
            recordNotifyNavigation(true);
            this.mEditToolbarController.show(false, false, false);
            preStartRecordTask();
            this.mIsRecording = true;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void recordNotifyNavigation(boolean z) {
        Navigator.get(this).immerseSecondaryContent(z);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected boolean handleInstanceState(Bundle bundle) {
        boolean zHandleInstanceState = super.handleInstanceState(bundle);
        if (zHandleInstanceState) {
            showBlankView(false);
        }
        return zHandleInstanceState;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.ui.BaseActionBarController.EditCallback
    public void onPerformAction(int i, Object... objArr) {
        if (i == 25) {
            handleActionHome();
            return;
        }
        if (i == 30) {
            handleActionOpenNavi();
        } else if (i == 31) {
            handleActionCloseNavi();
        } else {
            super.onPerformAction(i, objArr);
        }
    }

    private void handleActionHome() {
        if (!isAdded() || getActivity() == null) {
            return;
        }
        this.mEditorController.hideInputMethod();
        onBackPressed();
        handleFragmentBack();
        shutDownRecording();
    }

    private void handleFragmentBack() {
        NotesListActivity notesListActivity = (NotesListActivity) requireActivity();
        if (notesListActivity.getNavigator().getNavigationMode() == Navigator.Mode.C && notesListActivity.getNavigator().isContentOpen()) {
            handleBack();
        } else {
            Logger.INSTANCE.i(TAG, "handleBackPress, content  not open,mode:" + notesListActivity.getNavigator().getNavigationMode());
        }
    }

    private void shutDownRecording() {
        if (this.mIsRecording) {
            this.mIsRecording = false;
            if (isUseMeetingAudio()) {
                this.mEditAudioController.dismiss();
            } else {
                this.mEditToolbarController.closeAudioRecordPanel();
            }
            updateActionMode(EditorActionMode.VIEW, true);
        }
    }

    private void handleActionOpenNavi() {
        preBeforeOpenOrCloseNavigation();
        if (Navigator.get(this).isContentOpen()) {
            return;
        }
        setNaviState(true);
        Navigator.get(this).openContent();
    }

    private void handleActionCloseNavi() {
        preBeforeOpenOrCloseNavigation();
        if (Navigator.get(this).isContentOpen()) {
            setNaviState(false);
            Navigator.get(this).closeContent();
        }
    }

    private void preBeforeOpenOrCloseNavigation() {
        if (RomUtils.isPadDevice()) {
            updateActionMode(EditorActionMode.VIEW, true);
        } else {
            this.mEditorView.clearFocus();
            forceHideSoftInput();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void doDeleteNote() {
        if (this.mHandler != null && this.mActionBarController != null && ((ActionBarWithNaviController) this.mActionBarController).getNaviState()) {
            ((ActionBarWithNaviController) this.mActionBarController).showOrHideNaviState(true);
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.8
                @Override // java.lang.Runnable
                public void run() {
                    PadNoteEditFragment.this.deleteNote();
                }
            }, 200L);
        } else {
            deleteNote();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void tryHideNote() {
        super.tryHideNote();
        if (this.mHandler != null && this.mActionBarController != null && ((ActionBarWithNaviController) this.mActionBarController).getNaviState()) {
            ((ActionBarWithNaviController) this.mActionBarController).showOrHideNaviState(true);
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.9
                @Override // java.lang.Runnable
                public void run() {
                    if (PadNoteEditFragment.this.isWorkingNoteEmpty()) {
                        return;
                    }
                    PadNoteEditFragment.this.homeViewModel.hideNote(PadNoteEditFragment.this.mWorkingNote.getNoteId());
                }
            }, 200L);
        } else {
            if (isWorkingNoteEmpty()) {
                return;
            }
            this.homeViewModel.hideNote(this.mWorkingNote.getNoteId());
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void unhideNote() {
        if (this.mHandler != null && this.mActionBarController != null && ((ActionBarWithNaviController) this.mActionBarController).getNaviState()) {
            ((ActionBarWithNaviController) this.mActionBarController).showOrHideNaviState(true);
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.10
                @Override // java.lang.Runnable
                public void run() {
                    if (PadNoteEditFragment.this.isWorkingNoteEmpty()) {
                        return;
                    }
                    PadNoteEditFragment.this.homeViewModel.unHideNote(PadNoteEditFragment.this.mWorkingNote.getNoteId());
                }
            }, 200L);
        } else {
            if (isWorkingNoteEmpty()) {
                return;
            }
            this.homeViewModel.unHideNote(this.mWorkingNote.getNoteId());
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public void insertImages(Uri... uriArr) {
        Runnable runnable = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$insertImages$15();
            }
        };
        forceHideSoftInput();
        this.mEditorView.insertImages(runnable, uriArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$insertImages$15() {
        this.mEditorView.createNewRichTextData();
        asyncSaveNote();
    }

    private void updateToolBarLayout() {
        FragmentActivity activity = getActivity();
        if (activity == null || LiteUtils.isSuperLite()) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.edit_note_tool_layout);
        if (Navigator.get(this).getNavigationMode() != Navigator.Mode.C) {
            if (viewGroup.findViewById(R.id.big_placeholder) != null) {
                return;
            }
        } else if (viewGroup.findViewById(R.id.small_placeholder) != null) {
            return;
        }
        viewGroup.removeAllViews();
        int i = Navigator.get(this).getNavigationMode() != Navigator.Mode.C ? R.layout.big_screen_mode_tool_group : R.layout.small_screen_mode_tool_group;
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
        super.onResponsiveLayout(configuration, screenSpec, z);
        if (RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(getActivity()) && screenSpec.screenMode != 4103) {
            stopRecording();
        }
        if (!USE_MEETING_AUDIO && this.mScreenSpec.type != screenSpec.type && this.mActionMode == EditorActionMode.EDIT_RECODE) {
            updateActionMode(EditorActionMode.VIEW, false);
        }
        if (this.mShowingPopupWindow != null && this.mScreenSpec.type != screenSpec.type) {
            dismissMindHintGuideWindow();
        }
        this.mScreenSpec = screenSpec;
        CommonPadNoteAudioController commonPadNoteAudioController = this.mEditAudioController;
        if (commonPadNoteAudioController != null) {
            commonPadNoteAudioController.updateView(this.mScreenSpec);
        }
        updateToolBarLayout();
        this.mScreenStatus = UIUtils.getScreenStatus(getActivity(), this.mScreenSpec);
        updateHomeButtonState(this.mScreenSpec);
        setNaviState(Navigator.get(this).isContentOpen());
        if (configuration != null) {
            updateEditContentMargin(Navigator.get(this).isContentOpen());
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void updateWorkingNoteFromOuter() {
        if (this.mWorkingNote != null) {
            com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "updateWorkingNote/setWorkNote mWorkingNote " + this.mWorkingNote);
            this.mWorkingNote = WorkingNote.loadNote("_id=" + this.mWorkingNote.getNoteId(), null);
            this.mWorkingNote.setInPadMode(true);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onLoadDataFailed() {
        super.onLoadDataFailed();
        onDataEmpty();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void onViewMode() {
        Logger.INSTANCE.i(TAG, "onViewMode");
        super.onViewMode();
        notifyEditUiState(0);
        if (this.mSearchIsOnFocus && this.mInSearchMode) {
            return;
        }
        Utils.hideSoftInput(this.mEditorView);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void onEditMode() {
        Logger.INSTANCE.i(TAG, "onEditMode");
        notifyEditUiState(1);
        if (this.mActionFromToolbarRichText) {
            this.mActionFromToolbarRichText = false;
        }
        super.onEditMode();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void onTitleMode() {
        Logger.INSTANCE.i(TAG, "onTitleMode");
        notifyEditUiState(1);
        if (this.mActionFromToolbarRichText) {
            this.mActionFromToolbarRichText = false;
        }
        super.onTitleMode();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void notifyAction(int i, Bundle bundle) {
        super.notifyAction(i, bundle);
        if (i == 11) {
            this.mWillConvertToMindNote = true;
        }
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

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void clearFocus() {
        super.clearFocus();
        if (this.mActionMode != EditorActionMode.EDIT || getEditToolBarController() == null) {
            return;
        }
        getEditToolBarController().hide(false);
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
        this.mFolderId = noteLoadDataEntity.getFolderId();
        boolean z = true;
        setEnableEdit(!isReadOnlyMode());
        final Intent intent = noteLoadDataEntity.toIntent();
        if (noteLoadDataEntity.getNoteId() == -2) {
            this.mSearchText = "";
            this.mActionMode = null;
            getActionBarController().resetButtonAnimState();
            if (getActivity() == null || getActivity().getIntent() == null) {
                zEquals = false;
            } else {
                zEquals = "android.intent.action.SEND".equals(getActivity().getIntent().getAction());
                if (NoteIntent.ACTION_PAD_INSERT_OR_EDIT.equals(getActivity().getIntent().getAction())) {
                    if (NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE.equals(getActivity().getIntent().getType())) {
                        intent.setType(NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE);
                    }
                    getActivity().getIntent().setAction("android.intent.action.MAIN");
                }
            }
            if (!zEquals) {
                intent.setAction("android.intent.action.EDIT");
                getEditToolBarController().setNeedDelayShowInput(noteLoadDataEntity.isFromFloatWindow());
                processLastWorkingNote(noteLoadDataEntity.getNoteId());
                resetEditContent();
                this.mEditorView.clearFocus();
                this.mPendingEditIntentTask = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$loadNoteFromEntity$16(intent);
                    }
                };
                this.mHandler.sendEmptyMessage(4);
            } else {
                showBlankView(false);
                processLastWorkingNote(noteLoadDataEntity.getNoteId());
                resetEditContent();
                this.mEditorView.clearFocus();
                Intent intent2 = new Intent(getActivity().getIntent());
                intent2.putExtra("com.miui.notes.folder_id", noteLoadDataEntity.getFolderId());
                this.mLoadTask = new BaseNativeNoteEditFragment.NativeSendNoteLoadTask(intent2);
                this.mLoadTask.commit();
                getActivity().getIntent().setAction("android.intent.action.MAIN");
                updateActionMode(EditorActionMode.VIEW, false);
                z = false;
            }
        } else {
            intent.setAction("android.intent.action.VIEW");
            if (noteLoadDataEntity.getNoteId() > 0) {
                boolean z2 = this.mWorkingNote == null || this.mWorkingNote.getNoteId() != noteLoadDataEntity.getNoteId();
                boolean z3 = (this.mWorkingNote == null || this.mWorkingNote.getFolderId() == noteLoadDataEntity.getFolderId() || noteLoadDataEntity.getFolderId() == -4097) ? false : true;
                if (z2 || z3) {
                    if (getEditToolBarController() != null) {
                        getEditToolBarController().hide(false);
                    }
                    if (this.mAnnotationController != null && this.mAnnotationController.isShowing()) {
                        this.mAnnotationController.hide();
                    }
                    this.mEditorView.clearPendingTask();
                    this.mSearchText = "";
                    this.mActionMode = null;
                    getActionBarController().resetButtonAnimState();
                    processLastWorkingNote(noteLoadDataEntity.getNoteId());
                    showBlankView(false);
                    if (z2) {
                        resetEditContent();
                        this.mLoadTask = new BaseNormalNoteEditFragment.ViewNoteLoadTask(intent, noteLoadDataEntity.getNoteId());
                        this.mLoadTask.commit();
                    } else if (this.mEditorView.getDataParseTask() == null) {
                        this.mEditorView.updateContent(this.mEditorView.getRichTextData(), this.mSearchText);
                    }
                    updateActionMode(EditorActionMode.VIEW, false);
                } else if ((this.mLoadTask == null || !(this.mLoadTask instanceof UpdateNoteLoadTask)) && this.mWorkingNote != null) {
                    if (this.mWorkingNote.getModifiedDate() < noteLoadDataEntity.getModifiedTime() || !TextUtils.equals(this.mSearchText, noteLoadDataEntity.getSearchText())) {
                        if (this.mLoadTask != null) {
                            this.mLoadTask.cancel();
                        }
                        this.mLoadTask = new UpdateNoteLoadTask(intent, noteLoadDataEntity.getNoteId());
                        this.mLoadTask.commit();
                    }
                }
            } else {
                onDataEmpty();
                z = false;
            }
        }
        if (this.mLoadTask == null && this.mContentView.getAlpha() == 0.0f) {
            if (this.mCurrentTheme == null) {
                updateNoteTheme(null, false);
            }
            this.mContentView.setAlpha(1.0f);
            this.mContentView.setVisibility(0);
            this.mContentView.requestLayout();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadNoteFromEntity$16(Intent intent) {
        showBlankView(false);
        this.mLoadTask = new PadEditNoteLoadTask(intent);
        this.mLoadTask.commit();
    }

    @Override // com.miui.notes.basefeature.noteedit.ColumnsNativeNoteEditFragment
    protected void showBlankView(boolean z) {
        if (isAdded()) {
            if (z) {
                ActionBar actionBar = getActionBar();
                if (actionBar != null) {
                    actionBar.getCustomView().setVisibility(4);
                }
                EmptyStateView emptyStateView = this.mBlankView;
                if (emptyStateView != null && this.mContentView != null) {
                    Folme.useAt(emptyStateView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
                    Folme.useAt(this.mContentView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(new TransitionListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.11
                        @Override // miuix.animation.listener.TransitionListener
                        public void onCancel(Object obj) {
                            super.onCancel(obj);
                            PadNoteEditFragment.this.mBlankView.setAlpha(0.0f);
                            PadNoteEditFragment.this.mContentView.setVisibility(0);
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            PadNoteEditFragment.this.mBlankView.setAlpha(1.0f);
                            PadNoteEditFragment.this.mContentView.setVisibility(8);
                        }
                    }));
                    String str = this.mSearchText;
                    if (this.mFolderId == -3 || this.mFolderId == -2 || this.mFolderId == -5 || !TextUtils.isEmpty(str)) {
                        this.mBlankView.setOnDragListener(this.mBlockDragListener);
                    } else {
                        this.mBlankView.setOnDragListener(this.mBlankViewDragListener);
                    }
                    this.mBlankView.setTitle(getString(R.string.data_empty_note));
                    this.mBlankView.setIcon(R.drawable.note_blank_icon);
                }
                if (this.mEditorView != null) {
                    this.mEditorView.setFocusable(false);
                    this.mEditorView.setFocusableInTouchMode(false);
                    this.mEditorView.clearFocus();
                    this.mEditorView.setOnDragListener(null);
                }
                if (this.mEditorScrollView != null) {
                    this.mEditorScrollView.setOnDragListener(null);
                }
            } else {
                ActionBar actionBar2 = getActionBar();
                if (actionBar2 != null) {
                    actionBar2.getCustomView().setVisibility(0);
                }
                EmptyStateView emptyStateView2 = this.mBlankView;
                if (emptyStateView2 != null) {
                    Folme.useAt(emptyStateView2).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f));
                    this.mBlankView.setOnDragListener(null);
                }
                LinearLayout linearLayout = this.mContentView;
                if (linearLayout != null) {
                    Folme.useAt(linearLayout).state().cancel();
                    this.mContentView.setVisibility(0);
                    Folme.useAt(this.mContentView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
                }
                if (this.mEditorView != null) {
                    this.mEditorView.setFocusable(true);
                    this.mEditorView.setFocusableInTouchMode(true);
                    this.mEditorView.setOnDragListener(this.mEditorDragListener);
                    this.mEditorDragListener.setEditFragment(this);
                }
                if (this.mEditorScrollView != null) {
                    this.mEditorScrollView.setOnDragListener(this.mBlockDragListener);
                }
            }
            this.mIsShowBlank = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEditContentMarginBottom(int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mNestedHeaderLayout.getLayoutParams();
        marginLayoutParams.bottomMargin = i;
        this.mNestedHeaderLayout.setLayoutParams(marginLayoutParams);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onShowAddNewNote(boolean z) {
        this.mBlankView.setSelected(z);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onChangeToMindNote() {
        View view = getView();
        if (isReadOnlyMode() || view == null) {
            return;
        }
        Utils.hideSoftInput(view);
        view.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onChangeToMindNote$17();
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onChangeToMindNote$17() {
        MindNoteStat.isCreateMindNote = true;
        cancelSaveTask();
        if (getContext() != null) {
            NoteStore.delete(getContext(), new long[]{getNoteId()});
        }
        String string = this.mTitleEditor.getText().toString();
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(string)) {
            bundle.putString("note_title", string);
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
        this.homeViewModel.createMindNote(bundle);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void doneEdit() {
        if (this.mEditorView.commitComposingContent()) {
            asyncSaveNote();
        }
        if (this.mWorkingNote != null) {
            if (this.mHandler.hasMessages(1)) {
                this.mHandler.removeMessages(1);
            }
            saveNote(true);
        }
        this.mTitleEditor.clearFocus();
        this.mEditToolbarController.collapsePanel(null);
        updateActionMode(EditorActionMode.VIEW, true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void responseEditUiState() {
        if (isInEditAction()) {
            notifyEditUiState(1);
        } else {
            notifyEditUiState(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRecordTask() {
        PadNoteEditFragment padNoteEditFragment;
        if (!isAdded() || getActivity() == null) {
            return;
        }
        if (DualScreenManager.INSTANCE.isPcActivityLiving() && this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        this.mEditorView.stopMediaPlayerIfNeed();
        if (isUseMeetingAudio()) {
            if (this.mAudioMeetingTask == null) {
                this.meetingRecognitionListener = new MeetingRecognitionImpl();
                this.mAudioMeetingTask = new MeetingRecognitionTaskImp(NoteApp.getInstance(), AudioUtils.NOTE_MP3_DIR_NAME, this.meetingRecognitionListener);
            }
        } else if (this.mRecordAiTask == null) {
            this.speechRecognitionListener = new SpeechRecognitionImpl(this);
            this.mRecordAiTask = new SpeechRecognitionTaskImp(NoteApp.getInstance(), AudioUtils.NOTE_MP3_DIR_NAME, this.speechRecognitionListener);
        }
        this.mEditToolbarController.startAudioInput();
        if (this.mAudioRecordTimer == null) {
            padNoteEditFragment = this;
            padNoteEditFragment.mAudioRecordTimer = new CountDownTimer(isUseMeetingAudio() ? MAX_MEETING_RECORD_TIME : MAX_RECORD_TIME, 1000L) { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.12
                @Override // android.os.CountDownTimer
                public void onTick(long j) {
                    PadNoteEditFragment.this.onAudioTimerTick(j);
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    PadNoteEditFragment.this.onAudioTimerFinish();
                }
            };
        } else {
            padNoteEditFragment = this;
        }
        padNoteEditFragment.mAudioRecordTimer.start();
        padNoteEditFragment.mNetworkCallback.initNetWorkState();
        boolean isNetWorkAvailable = padNoteEditFragment.mNetworkCallback.getIsNetWorkAvailable();
        Logger.INSTANCE.d(TAG, "isNetworkAvailable " + isNetWorkAvailable);
        if (padNoteEditFragment.isUseMeetingAudio()) {
            if (!padNoteEditFragment.mAudioMeetingTask.isSkipSpeechRecognition()) {
                padNoteEditFragment.registerNetWorkListener(isNetWorkAvailable);
            }
        } else if (!padNoteEditFragment.mRecordAiTask.isSkipSpeechRecognition()) {
            padNoteEditFragment.registerNetWorkListener(isNetWorkAvailable);
        }
        padNoteEditFragment.getActivity().getWindow().addFlags(128);
        if (padNoteEditFragment.isUseMeetingAudio()) {
            boolean z = RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(padNoteEditFragment.getContext());
            padNoteEditFragment.mAudioMeetingTask.setNetWorkValid(isNetWorkAvailable);
            padNoteEditFragment.mAudioMeetingTask.start(padNoteEditFragment.mEditAudioController.isSingleMode(), padNoteEditFragment.mEditAudioController.isLanTrans(), z);
            padNoteEditFragment.mIsWarned = false;
            return;
        }
        try {
            padNoteEditFragment.mRecordAiTask.setNetWorkValid(isNetWorkAvailable);
            padNoteEditFragment.mRecordAiTask.start();
            padNoteEditFragment.mIsWarned = false;
        } catch (MibrainException unused) {
            Logger.INSTANCE.e(TAG, "Fail to start record task!");
        }
    }

    private void stopRecordTask() {
        if (getActivity() == null) {
            return;
        }
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
    }

    private View getAudioCountDownView() {
        if (getActivity() == null) {
            return null;
        }
        this.mCountDownView = new TextView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        Rect rect = new Rect();
        this.mEditorView.getLocalVisibleRect(rect);
        layoutParams.topMargin = (rect.bottom - rect.top) / 2;
        this.mCountDownView.setLayoutParams(layoutParams);
        this.mCountDownView.setBackground(getActivity().getResources().getDrawable(R.drawable.countdown_view_backgound));
        this.mCountDownView.setTextColor(getActivity().getResources().getColor(R.color.countdown_view_text_color));
        this.mCountDownView.setTextSize(0, getActivity().getResources().getDimension(R.dimen.bubble_text_size));
        return this.mCountDownView;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void requestXiaoAiPermission() {
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (!PreferenceUtils.getAllowXiaoai(activity) && !RomUtils.isInternationalBuild()) {
            manageDialog(PermissionUtils.showXiaoaiAcceptDialog(activity, new PermissionUtils.AcceptListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.13
                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void reject() {
                }

                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void accept() {
                    PadNoteEditFragment.this.updateRecordMode();
                }
            }));
        } else {
            updateRecordMode();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void launchSnsShareActivity(int i, int i2, AigcPicMetaData aigcPicMetaData) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            PadSnsEditActivity.INSTANCE.launchActivity(this.mToShareResult, activity, this.mWorkingNote.getContent(), this.mWorkingNote.getTitle(), i, this.mWorkingNote.getThemeId(), this.mWorkingNote.getModifiedDate(), this.mEditorView.getTextSize(), "", this.mWorkingNote, aigcPicMetaData);
        }
    }

    private void dismissShowingMenu() {
        if (this.mImageChooserMenu != null) {
            this.mImageChooserMenu.dismiss();
            this.mImageChooserMenu = null;
        }
    }

    private void forbidListenKeyboardOnLeave() {
        if (getMIsKeyboardVisible()) {
            this.mPendingDisableListenKeyboardTask = new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda23
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

    /* JADX INFO: Access modifiers changed from: private */
    public void keyBoardShowStatusChange(boolean z) {
        if (isAdded()) {
            Logger.INSTANCE.d(TAG, " onKeyboardVisibleChanged visible = " + z + " isAdded() " + isAdded());
            if (isAdded()) {
                if (!z) {
                    dismissShowingMenu();
                }
                if (this.mAnnotationController != null && this.mAnnotationController.isShowing()) {
                    if (z) {
                        return;
                    } else {
                        this.mAnnotationController.cancel();
                    }
                }
                if (this.mEditorView == null) {
                    return;
                }
                if (this.mEditorView.getShowSoftInputOnFocus() && this.mEditorView.hasFocus()) {
                    if (!z && (this.mActionMode == EditorActionMode.EDIT || this.mActionMode == EditorActionMode.EDIT_TITLE || this.mActionMode == null)) {
                        updateActionMode(EditorActionMode.VIEW, true);
                    } else if (z && (this.mActionMode == EditorActionMode.VIEW || this.mActionMode == null)) {
                        updateActionMode(EditorActionMode.EDIT, true);
                    }
                } else if (!z && (this.mActionMode == EditorActionMode.EDIT || this.mActionMode == null)) {
                    Logger.INSTANCE.d(TAG, " mEditor.getShowSoftInputOnFocus() state exception");
                    updateActionMode(EditorActionMode.VIEW, true);
                }
                if (z) {
                    this.mEditorView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda24
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$keyBoardShowStatusChange$19();
                        }
                    }, 350L);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$keyBoardShowStatusChange$19() {
        if (this.mEditorView.hasFocus()) {
            this.mEditorView.bringPointIntoView(Math.min(this.mEditorView.getText() != null ? this.mEditorView.getText().length() : 0, this.mEditorView.getSelectionStart()));
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, com.miui.common.tool.softinputhelper.KeyboardListener
    public void onKeyboardChangeEnd() {
        Runnable runnable;
        super.onKeyboardChangeEnd();
        if (getMIsKeyboardVisible() || (runnable = this.mPendingDisableListenKeyboardTask) == null) {
            return;
        }
        runnable.run();
        this.mPendingDisableListenKeyboardTask = null;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void onSplitModeChanged(int i) {
        super.onSplitModeChanged(i);
        if (this.mEditorController != null) {
            this.mEditorController.dismissShowingPopupWindow();
        }
        updateHomeButtonState(this.mScreenSpec);
        if (i == 0) {
            this.mIsShow = true;
            setNaviState(true);
            if (this.mEditorView != null) {
                this.mEditorView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSplitModeChanged$20();
                    }
                }, 100L);
                return;
            }
            return;
        }
        if (i != 1) {
            if (i != 2) {
                return;
            }
            this.mIsShow = true;
            setNaviState(false);
            if (this.mEditorView != null) {
                this.mEditorView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSplitModeChanged$21();
                    }
                }, 100L);
                return;
            }
            return;
        }
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSplitModeChanged$20() {
        this.mEditorView.requestLayout();
        this.mEditorView.invalidateDeeply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSplitModeChanged$21() {
        this.mEditorView.requestLayout();
        this.mEditorView.invalidateDeeply();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected boolean isContentShow() {
        if (this.mSplitMode == 0) {
            return false;
        }
        return super.isContentShow();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void setNaviState(boolean z) {
        Logger.INSTANCE.d(TAG, "setNaviState " + z);
        this.mIsNaviShow = z;
        if (this.mActionBarController != null) {
            ((ActionBarWithNaviController) this.mActionBarController).setNaviState(this.mIsNaviShow);
            updateEditContentMargin(z);
        }
    }

    protected void onClickAudioBtnOld() {
        ArrayList arrayList = new ArrayList();
        String str = new String[]{"android.permission.RECORD_AUDIO"}[0];
        if (PermissionUtils.shouldOnceRequestPermission(getActivity(), str)) {
            arrayList.add(str);
        }
        if (arrayList.size() == 0) {
            if (isAdded()) {
                if (Utils.isInCommunication(getActivity())) {
                    Toast.makeText(getActivity(), R.string.toast_refuse_to_record_when_in_call, 0).show();
                    return;
                } else if (PermissionUtils.isPermissionNotGranted(getContext(), "android.permission.RECORD_AUDIO")) {
                    Toast.makeText(getActivity(), R.string.toast_refuse_to_use_record_audio, 0).show();
                    return;
                } else {
                    handleAudioCTARequest();
                    return;
                }
            }
            return;
        }
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        requestPermissions(strArr, 2);
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showSharePopupWindow(Intent intent) {
        if (this.mSharePopupWindow != null && this.mSharePopupWindow.isShowing()) {
            this.mSharePopupWindow.dismiss();
        }
        if (RomUtils.isPadMode()) {
            this.mCurrentTheme = ResourceManager.getTheme(0);
        }
        if (getActivity() == null || getView() == null) {
            return;
        }
        this.mSharePopupWindow = new SharePopupGroup(getActivity(), UIUtils.inflateView((ViewGroup) getView(), R.layout.share_layout), this.mCurrentTheme);
        Rect rect = new Rect();
        getView().getLocalVisibleRect(rect);
        this.mSharePopupWindow.queryInfo(intent);
        this.mSharePopupWindow.setOnIntentSelectedListener(new ResolverAdapter.OnIntentSelectedListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda20
            @Override // com.miui.notes.adapter.ResolverAdapter.OnIntentSelectedListener
            public final void onIntentSelected(Intent intent2) {
                this.f$0.lambda$showSharePopupWindow$22(intent2);
            }
        });
        this.mSharePopupWindow.updatePaddingBottom(this.mInsetBottom);
        this.mSharePopupWindow.show(rect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSharePopupWindow$22(Intent intent) {
        if (this.mSharePopupWindow != null && this.mSharePopupWindow.isShowing()) {
            this.mSharePopupWindow.dismiss();
        }
        if (isIgnoreNewTask(intent.getComponent().getPackageName())) {
            intent.removeFlags(DataSpace.RANGE_LIMITED);
        }
        startActivity(intent);
    }

    private boolean isIgnoreNewTask(String str) {
        return PackageNameUtils.isWechatPackage(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public void viewImage(BaseImageSpan baseImageSpan) {
        super.viewImage(baseImageSpan);
        notifyAction(3, null);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void createNewDoodle() {
        forbidListenKeyboardOnLeave();
        Utils.hideSoftInput(this.mEditorView);
        notifyAction(3, null);
        this.mEditorView.cacheSelection(true);
        this.mEditorController.hideInputMethod();
        this.mWillBackFromInsertImage = true;
        super.createNewDoodle();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showAttachmentChooserDialog(View view) {
        if (this.mImageChooserMenu != null) {
            this.mImageChooserMenu = null;
        }
        ImageChooserMenu imageChooserMenu = new ImageChooserMenu(getContext());
        imageChooserMenu.setOnMenuListener(new AnonymousClass14());
        imageChooserMenu.show(view, new int[2]);
        this.mImageChooserMenu = imageChooserMenu;
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$14, reason: invalid class name */
    class AnonymousClass14 implements IPopMenu.OnMenuListener {
        @Override // com.miui.common.view.IPopMenu.OnMenuListener
        public void onDismiss() {
        }

        @Override // com.miui.common.view.IPopMenu.OnMenuListener
        public void onShow() {
        }

        AnonymousClass14() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemSelected$0(DialogInterface dialogInterface, int i) {
            MiuiSynergySdk.getInstance().takePhotoCancel(PadNoteEditFragment.this.getActivity());
            PadNoteEditFragment.this.dismissDialog();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemSelected$2(final ClipData clipData) {
            if (!PadNoteEditFragment.this.isAdded() || PadNoteEditFragment.this.mHandler == null) {
                return;
            }
            PadNoteEditFragment.this.mHandler.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$14$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onItemSelected$1(clipData);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemSelected$1(ClipData clipData) {
            if (clipData != null && clipData.getItemAt(0) != null) {
                PadNoteEditFragment.this.insertImageFromClipData(clipData, false);
            }
            PadNoteEditFragment.this.dismissDialog();
        }

        @Override // com.miui.common.view.IPopMenu.OnMenuListener
        public void onItemSelected(View view, int i) {
            if (i == 0) {
                PadNoteEditFragment.this.addAttachment(1);
                EditStat.trackGalleryChooserClick("gallery");
                return;
            }
            if (i == 1) {
                PadNoteEditFragment.this.addAttachment(2);
                EditStat.trackGalleryChooserClick(EditStat.ELEMENT_CHOOSE_LOCAL_CAMERA);
                return;
            }
            if (i == 2) {
                if (PadNoteEditFragment.this.getContext() == null) {
                    return;
                }
                AlertDialog alertDialogCreate = new AlertDialog.Builder(PadNoteEditFragment.this.getContext()).setTitle(R.string.dialog_tip_title).setMessage(R.string.pad_dialog_tip_content_take_photo_by_phone).setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$14$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        this.f$0.lambda$onItemSelected$0(dialogInterface, i2);
                    }
                }).create();
                alertDialogCreate.setCancelable(false);
                alertDialogCreate.setCanceledOnTouchOutside(false);
                alertDialogCreate.show();
                if (PadNoteEditFragment.this.getActivity() != null) {
                    Utils.hideSoftInput(PadNoteEditFragment.this.getActivity());
                }
                PadNoteEditFragment.this.manageDialog(alertDialogCreate);
                PadNoteEditFragment.this.mEditorView.cacheSelection(true);
                MiuiSynergySdk.getInstance().takePhotoOnSynergy(PadNoteEditFragment.this.getAppCompatActivity(), new MiuiSynergySdk.ChooseFileCallback() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$14$$ExternalSyntheticLambda3
                    @Override // com.xiaomi.mirror.synergy.MiuiSynergySdk.ChooseFileCallback
                    public final void onFileChosen(ClipData clipData) {
                        this.f$0.lambda$onItemSelected$2(clipData);
                    }
                });
                EditStat.trackGalleryChooserClick(EditStat.ELEMENT_CHOOSE_REMOTE_CAMERA);
                return;
            }
            if (i == 3 && PadNoteEditFragment.this.getContext() != null) {
                final String str = (String) view.getTag(R.id.device_id);
                String str2 = (String) view.getTag(R.id.device_name);
                Logger.INSTANCE.d(PadNoteEditFragment.TAG, "onItemSelected deviceId: " + str);
                AlertDialog alertDialogCreate2 = new AlertDialog.Builder(PadNoteEditFragment.this.getContext()).setTitle(R.string.dialog_tip_title).setMessage(String.format(NoteApp.getInstance().getString(R.string.pad_dialog_take_photo_by_device), str2)).setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.14.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        if (PadNoteEditFragment.this.getActivity() != null) {
                            MiuiSynergySdk.getInstance().takePhotoCancel(PadNoteEditFragment.this.getActivity(), str);
                        }
                        PadNoteEditFragment.this.dismissDialog();
                    }
                }).create();
                alertDialogCreate2.setCancelable(false);
                alertDialogCreate2.setCanceledOnTouchOutside(false);
                alertDialogCreate2.show();
                if (PadNoteEditFragment.this.getActivity() != null) {
                    Utils.hideSoftInput(PadNoteEditFragment.this.getActivity());
                }
                PadNoteEditFragment.this.manageDialog(alertDialogCreate2);
                PadNoteEditFragment.this.mEditorView.cacheSelection(true);
                MiuiSynergySdk.getInstance().takePhotoOnSynergy(PadNoteEditFragment.this.getAppCompatActivity(), str, new MiuiSynergySdk.ChooseFileCallback() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$14$$ExternalSyntheticLambda4
                    @Override // com.xiaomi.mirror.synergy.MiuiSynergySdk.ChooseFileCallback
                    public final void onFileChosen(ClipData clipData) {
                        this.f$0.lambda$onItemSelected$4(clipData);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemSelected$4(final ClipData clipData) {
            if (!PadNoteEditFragment.this.isAdded() || PadNoteEditFragment.this.mHandler == null) {
                return;
            }
            PadNoteEditFragment.this.mHandler.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$14$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onItemSelected$3(clipData);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemSelected$3(ClipData clipData) {
            if (clipData != null && clipData.getItemAt(0) != null) {
                PadNoteEditFragment.this.insertImageFromClipData(clipData, false);
            }
            PadNoteEditFragment.this.dismissDialog();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void addAttachment(int i) {
        if (getActivity() == null || !isAdded()) {
            return;
        }
        this.mEditorView.cacheSelection(true);
        updateActionMode(EditorActionMode.VIEW, true);
        if (i == 1) {
            notifyAction(3, null);
            if (getMIsKeyboardVisible()) {
                this.mEditorController.hideInputMethod();
            }
            forbidListenKeyboardOnLeave();
            ExternalAppUtils.selectImage(this, this.mGalleryResult, this.mNewGalleryResult);
        } else if (i == 2) {
            notifyAction(3, null);
            if (getMIsKeyboardVisible()) {
                this.mEditorController.hideInputMethod();
            }
            forbidListenKeyboardOnLeave();
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.15
                @Override // java.lang.Runnable
                public void run() {
                    if (PadNoteEditFragment.this.isAdded()) {
                        PadNoteEditFragment padNoteEditFragment = PadNoteEditFragment.this;
                        ExternalAppUtils.takePhoto(padNoteEditFragment, padNoteEditFragment.mTakePhotoResult);
                    }
                }
            }, 150L);
        }
        super.addAttachment(i);
    }

    private void registerNetWorkListener(boolean z) {
        ConnectivityManager connectivityManager = this.mConnMgr;
        if (connectivityManager != null) {
            connectivityManager.registerNetworkCallback(this.mRequest, this.mNetworkCallback);
        }
        if (z) {
            return;
        }
        Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getResources().getString(R.string.audio_toast_network_is_not_available), 1).show();
    }

    private void unRegisterNetWorkListener() {
        ConnectivityManager connectivityManager = this.mConnMgr;
        if (connectivityManager != null) {
            connectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAudioTimerTick(long j) {
        if (isAdded()) {
            if (!this.mIsWarned && j <= LAST_WARRING_TIME) {
                this.mIsWarned = true;
                View audioCountDownView = getAudioCountDownView();
                if (audioCountDownView == null) {
                    return;
                } else {
                    this.mEditorMaskView.addView(audioCountDownView);
                }
            }
            PadNoteAudioSpan padNoteAudioSpan = (PadNoteAudioSpan) this.mEditorView.getPlayingAudioSpan();
            if (padNoteAudioSpan != null) {
                if (isUseMeetingAudio()) {
                    padNoteAudioSpan.updateRecordingTime((int) ((MAX_MEETING_RECORD_TIME - j) / 1000));
                } else {
                    padNoteAudioSpan.updateRecordingTime((int) ((MAX_RECORD_TIME - j) / 1000));
                }
            }
            if (this.mIsWarned) {
                this.mCountDownView.setText(Utils.obtainLocalInteger(((int) Math.floor(j / 1000)) + 1, R.plurals.record_last_ten_seconds_reminder));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAudioTimerFinish() {
        this.mEditorMaskView.removeAllViews();
        updateActionMode(EditorActionMode.VIEW, true);
    }

    @Override // com.miui.notes.basefeature.noteedit.RecordTaskWorker
    public void onReceiveTaskResult(String str) {
        Logger.INSTANCE.d("notifyAudioFileSaved", "onReceiveTaskResult audioFileName:" + str);
        if (this.mEditorView != null) {
            this.mEditorView.onAudioEndAndFileSaved(str);
        }
        saveNote(true);
    }

    public void notifyAudioFileSaved(String str) {
        if (isAdded()) {
            this.mEditorView.onAudioEndAndFileSaved(str);
            saveNote(true);
            saveNoteInstantlyIfNeeded();
            return;
        }
        if (this.mWorkingNote != null && this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        try {
            this.mEditorView.onAudioEndAndFileSaved(str);
            saveNote(true);
            Intent intent = new Intent();
            intent.setAction(FrameConstant.BROADCAST_ACTION_NOTE_EDITOR_AUDIO);
            intent.putExtra("mp3_name", str);
            if (this.mLocalBroadcastManager != null) {
                this.mLocalBroadcastManager.sendBroadcast(intent);
            }
            Logger.INSTANCE.d("notifyAudioFileSaved", "mLocalBroadcastManager:" + this.mLocalBroadcastManager);
        } catch (Exception e) {
            e.printStackTrace();
            PreferenceUtils.saveTempAudioFile(str);
        }
    }

    protected void refreshEditorFocus() {
        if (isAdded() && this.mEditorView.isCursorVisible()) {
            Rect rect = new Rect();
            this.mEditorView.getFocusedRect(rect);
            View view = getView();
            int paddingBottom = view != null ? view.findViewById(R.id.edit_content).getPaddingBottom() + UIUtils.dip2px(NoteApp.getInstance(), 10.0f) : 0;
            rect.top += paddingBottom;
            rect.bottom += paddingBottom;
            this.mEditorScrollView.requestChildRectangleOnScreen(this.mEditAreaLayout, rect, false);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showDualScreenEditTipDialog() {
        FragmentActivity activity = getActivity();
        AlertDialog alertDialog = this.mDualScreenEditTipDialog;
        if ((alertDialog == null || !alertDialog.isShowing()) && activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setCancelable(false);
            builder.setTitle(getString(R.string.note_dual_screen_dialog_title));
            builder.setMessage(getString(R.string.note_dual_screen_dialog_content));
            builder.setNegativeButton(getString(R.string.dialog_button_continue_work), new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda15
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    PadNoteEditFragment.lambda$showDualScreenEditTipDialog$23(dialogInterface, i);
                }
            });
            builder.setPositiveButton(getString(R.string.dialog_button_close), new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda16
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$showDualScreenEditTipDialog$24(dialogInterface, i);
                }
            });
            AlertDialog alertDialogCreate = builder.create();
            this.mDualScreenEditTipDialog = alertDialogCreate;
            alertDialogCreate.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDualScreenEditTipDialog$24(DialogInterface dialogInterface, int i) {
        notifyEditUiState(3);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateNoteFromDualScreen() {
        long noteId = this.mWorkingNote.getNoteId();
        if (this.mHandler.hasMessages(1)) {
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
            Logger.INSTANCE.i(PadNoteEditFragment.TAG, "onResult UpdateNoteLoadTask");
            if (PadNoteEditFragment.this.mWorkingNote == null || workingNote.getModifiedDate() != PadNoteEditFragment.this.mWorkingNote.getModifiedDate()) {
                Logger.INSTANCE.i(PadNoteEditFragment.TAG, "onResult UpdateNoteLoadTask remote save note");
                super.onResult(taskManager, workingNote);
                return;
            }
            Logger.INSTANCE.i(PadNoteEditFragment.TAG, "onResult UpdateNoteLoadTask same note");
            if (PadNoteEditFragment.this.mLoadTask != this) {
                return;
            }
            PadNoteEditFragment.this.mLoadTask = null;
            PadNoteEditFragment.this.showLoadingData(false);
            if (!PadNoteEditFragment.this.mInSearchMode) {
                this.searchContent = "";
            }
            if (TextUtils.isEmpty(this.searchContent)) {
                PadNoteEditFragment.this.updateUserQuery("", false, null);
                PadNoteEditFragment.this.mSearchText = "";
            } else if (!TextUtils.isEmpty(this.searchContent) && !this.searchContent.equals(PadNoteEditFragment.this.mSearchText)) {
                PadNoteEditFragment.this.mSearchText = this.searchContent;
                PadNoteEditFragment padNoteEditFragment = PadNoteEditFragment.this;
                padNoteEditFragment.updateUserQuery(padNoteEditFragment.mSearchText, false, null);
                PadNoteEditFragment.this.updateActionMode(EditorActionMode.VIEW, false);
            }
            if (PadNoteEditFragment.this.isInEditAction()) {
                PadNoteEditFragment.this.mEditorView.requestFocus();
            }
            PadNoteEditFragment.this.mHandler.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$UpdateNoteLoadTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onResult$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0() {
            PadNoteEditFragment.this.mEditorView.requestLayout();
            PadNoteEditFragment.this.mEditorView.invalidateDeeply();
        }
    }

    public static class SpeechRecognitionImpl implements SpeechRecognitionListener {
        private final WeakReference<PadNoteEditFragment> iFragmentRef;

        public SpeechRecognitionImpl(PadNoteEditFragment padNoteEditFragment) {
            this.iFragmentRef = new WeakReference<>(padNoteEditFragment);
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrAppendTextResult(String str, boolean z) {
            PadNoteEditFragment padNoteEditFragment = this.iFragmentRef.get();
            if (padNoteEditFragment == null || padNoteEditFragment.mEditorView == null) {
                return;
            }
            padNoteEditFragment.mEditorView.onAudioTextAppend(str, z);
            padNoteEditFragment.refreshEditorFocus();
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrSaveAudioFileName(String str) {
            PadNoteEditFragment padNoteEditFragment = this.iFragmentRef.get();
            if (padNoteEditFragment != null) {
                padNoteEditFragment.notifyAudioFileSaved(str);
            }
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrMaxAmplitudeUpdate(short s) {
            PadNoteEditFragment padNoteEditFragment = this.iFragmentRef.get();
            if (padNoteEditFragment != null) {
                padNoteEditFragment.getEditToolBarController().updateAudioVolume(s);
            }
        }
    }

    private static class MeetingRecognitionImpl implements MeetingRecognitionListener {
        private final WeakReference<PadNoteEditFragment> iFragmentRef;

        private MeetingRecognitionImpl(PadNoteEditFragment padNoteEditFragment) {
            this.iFragmentRef = new WeakReference<>(padNoteEditFragment);
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onMeetingTextResult(String str, boolean z) {
            PadNoteEditFragment padNoteEditFragment = this.iFragmentRef.get();
            if (padNoteEditFragment != null) {
                if (padNoteEditFragment.mEditorView.getText() != null && padNoteEditFragment.mEditorView.getText().length() >= 20000 && padNoteEditFragment.mActionMode == EditorActionMode.EDIT_RECODE) {
                    padNoteEditFragment.updateActionMode(EditorActionMode.VIEW, true);
                    return;
                }
                try {
                    ((PadNoteRichEditor) padNoteEditFragment.mEditorView).onMeetingTextResult(str, z);
                    padNoteEditFragment.refreshEditorFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onMeetingTextFull(String str) {
            PadNoteEditFragment padNoteEditFragment = this.iFragmentRef.get();
            if (padNoteEditFragment != null) {
                if (padNoteEditFragment.mEditorView.getText() != null && padNoteEditFragment.mEditorView.getText().length() >= 20000 && padNoteEditFragment.mActionMode == EditorActionMode.EDIT_RECODE) {
                    padNoteEditFragment.updateActionMode(EditorActionMode.VIEW, true);
                } else {
                    ((PadNoteRichEditor) padNoteEditFragment.mEditorView).onMeetingTextFull(str);
                    padNoteEditFragment.refreshEditorFocus();
                }
            }
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onMeetingMp3FileName(String str) {
            PadNoteEditFragment padNoteEditFragment = this.iFragmentRef.get();
            if (padNoteEditFragment != null) {
                padNoteEditFragment.notifyAudioFileSaved(str);
            }
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onMeetingMaxAmplitudeUpdate(short s) {
            PadNoteEditFragment padNoteEditFragment = this.iFragmentRef.get();
            if (padNoteEditFragment != null) {
                padNoteEditFragment.mEditAudioController.updateAudioVolume(s);
            }
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onSocketFailure() {
            PadNoteEditFragment padNoteEditFragment = this.iFragmentRef.get();
            if (padNoteEditFragment == null || padNoteEditFragment.getActivity() == null || !padNoteEditFragment.mIsRecording) {
                return;
            }
            Toast.makeText(padNoteEditFragment.getActivity(), R.string.audio_dialog_mode_meeting_recognize_fail, 0).show();
        }

        @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionListener
        public void onSocketClosed() {
            PadNoteEditFragment padNoteEditFragment = this.iFragmentRef.get();
            if (padNoteEditFragment == null || padNoteEditFragment.getContext() == null || !padNoteEditFragment.mIsRecording) {
                return;
            }
            Toast.makeText(padNoteEditFragment.getContext(), R.string.audio_dialog_mode_meeting_recognize_close, 0).show();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateEditContentMargin(Navigator.get(this).isContentOpen());
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
            this.mEditToolbarController.updateLayout();
        }
        if (this.mSharePopupWindow != null && getView() != null) {
            getView().postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onConfigurationChanged$25();
                }
            }, 50L);
        }
        RecordModeChooserMenu recordModeChooserMenu = this.mRecordModeChooserMenu;
        if (recordModeChooserMenu != null) {
            recordModeChooserMenu.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConfigurationChanged$25() {
        Rect rect = new Rect();
        if (getView() != null) {
            getView().getLocalVisibleRect(rect);
            this.mSharePopupWindow.refresh(rect);
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
        if (this.mNoteExtraInfoController instanceof PadNoteInfoController) {
            this.mNoteExtraInfoController.dismissMenu();
        }
        RecordModeChooserMenu recordModeChooserMenu = this.mRecordModeChooserMenu;
        if (recordModeChooserMenu != null) {
            recordModeChooserMenu.dismiss();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
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

    private void updateHomeButtonState(ScreenSpec screenSpec) {
        if (this.mActionBarController == null || Navigator.get(this) == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "updateHomeButtonState screenSpec.type " + screenSpec.type + " screenMode " + screenSpec.screenMode);
        if ((RomUtils.isFoldDevice() && PreferenceUtils.getNoteHomePageArrayWay(getActivity(), 0) == 1) || Navigator.get(this).getNavigationMode() == Navigator.Mode.C) {
            ((ActionBarWithNaviController) this.mActionBarController).setHomeButtonState(true);
        } else {
            ((ActionBarWithNaviController) this.mActionBarController).setHomeButtonState(false);
        }
        CommonPadNoteAudioController commonPadNoteAudioController = this.mEditAudioController;
        if (commonPadNoteAudioController != null) {
            commonPadNoteAudioController.updateScreenStatus(screenSpec);
        }
    }

    private void onAudioButtonClick() {
        if (this.mIsRecording) {
            this.mIsRecording = false;
            if (isUseMeetingAudio()) {
                this.mEditAudioController.dismiss();
            } else {
                this.mEditToolbarController.closeAudioRecordPanel();
            }
            updateActionMode(EditorActionMode.VIEW, true);
            return;
        }
        this.mActionFromToolbarRichText = false;
        if (!this.mEditorView.isCursorVisible()) {
            this.mEditorView.setSelection(this.mEditorView.length());
            this.mEditorView.requestFocus();
        }
        Rect rect = new Rect();
        this.mEditorView.getFocusedRect(rect);
        View view = getView();
        int paddingBottom = view != null ? view.findViewById(R.id.edit_content).getPaddingBottom() + UIUtils.dip2px(NoteApp.getInstance(), 60.0f) : 0;
        rect.top += paddingBottom;
        rect.bottom += paddingBottom;
        this.mEditorScrollView.requestChildRectangleOnScreen(this.mEditAreaLayout, rect, false);
        if (PermissionUtils.supportNewPermissionStyle()) {
            onClickAudioBtn();
        } else {
            onClickAudioBtnOld();
        }
    }

    public void stopRecording() {
        if (this.mIsRecording) {
            this.mIsRecording = false;
            if (isUseMeetingAudio()) {
                this.mEditAudioController.dismiss();
            } else {
                this.mEditToolbarController.closeAudioRecordPanel();
            }
            updateActionMode(EditorActionMode.VIEW, true);
        }
    }

    protected void preStartRecordTask() {
        if (isUseMeetingAudio()) {
            this.mEditToolbarController.hide(false);
            Utils.hideSoftInput(this.mEditorView);
            this.mEditAudioController.show(this.mScreenSpec);
        } else {
            getView().postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment.16
                @Override // java.lang.Runnable
                public void run() {
                    PadNoteEditFragment.this.mEditToolbarController.toggleAudioRecordPanel();
                    PadNoteEditFragment.this.startRecordTask();
                }
            }, 200L);
            Utils.hideSoftInput(this.mEditorView);
        }
    }

    public boolean isUseMeetingAudio() {
        return USE_MEETING_AUDIO;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAudioMeetingDialog() {
        FragmentActivity activity = getActivity();
        Logger.INSTANCE.d(TAG, "showAudioMeetingDialog");
        if (activity == null || activity.isFinishing()) {
            return;
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(activity).setTitle(R.string.audio_dialog_mode_meeting).setMessage(String.format(activity.getString(R.string.audio_dialog_mode_meeting_sum), 2, 4)).setPositiveButton(R.string.audio_dialog_mode_meeting_use, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showAudioMeetingDialog$26(dialogInterface, i);
            }
        }).setNegativeButton(R.string.audio_dialog_mode_meeting_exit, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showAudioMeetingDialog$27(dialogInterface, i);
            }
        }).create();
        alertDialogCreate.show();
        manageDialog(alertDialogCreate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAudioMeetingDialog$26(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        this.mEditAudioController.setMode(false);
        updateMeetingAudioTaskInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAudioMeetingDialog$27(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        this.mEditAudioController.setMode(true);
        updateMeetingAudioTaskInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMeetingAudioTaskInfo() {
        boolean z = RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(getContext());
        MeetingRecognitionTask meetingRecognitionTask = this.mAudioMeetingTask;
        if (meetingRecognitionTask != null) {
            meetingRecognitionTask.updateAsrInfo(this.mEditAudioController.isSingleMode(), this.mEditAudioController.isLanTrans(), z);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean isDataEmpty() {
        return this.mWorkingNote != null && this.mWorkingNote.isEmpty();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean isBlankViewShow() {
        return this.mIsShowBlank || this.mWorkingNote == null;
    }

    public class PadEditNoteLoadTask extends BaseNormalNoteEditFragment.EditNoteLoadTask {
        public PadEditNoteLoadTask(Intent intent) {
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
                PadNoteEditFragment.this.notifyAction(10, bundle);
            }
            return workingNoteLoadWorkingNote;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, WorkingNote workingNote) {
            super.onResult(taskManager, workingNote);
            if (workingNote == null) {
                return;
            }
            NoteLoadDataEntity noteLoadDataEntity = new NoteLoadDataEntity(workingNote.getNoteId(), workingNote.getFolderId(), "", 0, workingNote.getModifiedDate(), false, null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("entity", noteLoadDataEntity);
            if (PadNoteEditFragment.this.isAdded()) {
                PadNoteEditFragment.this.setArguments(bundle);
            }
            PadNoteEditFragment.this.homeViewModel.postSelectNote(noteLoadDataEntity, false);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onInsertAudio() {
            NoteRichEditor noteRichEditor = PadNoteEditFragment.this.mEditorView;
            final PadNoteEditFragment padNoteEditFragment = PadNoteEditFragment.this;
            noteRichEditor.addPendingTask(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteEditFragment$PadEditNoteLoadTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    padNoteEditFragment.startRecordWithCheckFromEditTask();
                }
            });
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onEnterEdit(WorkingNote workingNote) {
            if (this.intent.getExtras() == null) {
                PadNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                return;
            }
            EditorActionMode editorActionMode = (EditorActionMode) this.intent.getExtras().get(NoteIntent.KEY_ACTION_MODE);
            if (editorActionMode != null && editorActionMode.equals(EditorActionMode.EDIT_RECODE)) {
                PadNoteEditFragment.this.startRecordWithCheckFromEditTask();
            } else {
                PadNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, false);
            }
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void setForAccessibility(boolean z) {
        if (this.mRootView != null) {
            this.mRootView.setImportantForAccessibility(z ? 4 : 1);
        }
    }
}
