package com.miui.notes.feature.noteedit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.constant.FrameConstant;
import com.miui.common.record.SpeechRecognitionListener;
import com.miui.common.record.SpeechRecognitionTask;
import com.miui.common.record.SpeechRecognitionTaskImp;
import com.miui.common.stat.EditStat;
import com.miui.common.stat.GlobalStat;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DualScreenManager;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.NetworkCallbackImpl;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor;
import com.miui.common.view.IPopMenu;
import com.miui.doodle.DoodleApplication;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.ai.utils.NoteSchemaUtil;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.component.GestureFrameLayout;
import com.miui.notes.component.datafolder.FolderItemEntity;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.editor.ReadOnlyActionListener;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.EventController;
import com.miui.notes.ui.IFragmentController;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.notes.ui.widget.AnnotationEditController;
import com.miui.pad.feature.notes.commonedit.ImageChooserMenu;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.IEditorPopMenu;
import com.miui.richeditor.IRichEditor;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.xiaomi.ai.mibrain.MibrainException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes4.dex */
public abstract class PhoneNativeNoteEditFragment extends BaseNativeNoteEditFragment implements SpeechRecognitionListener {
    private static final String TAG = "PhoneNativeNoteEditFragment";
    ConnectivityManager mConnMgr;
    protected TextView mCountDownView;
    protected KeyboardHelper mEditKeyboardHelper;
    protected boolean mIsWarned;
    private final NetworkCallbackImpl.NetworkCall mNetworkCall;
    NetworkCallbackImpl mNetworkCallback;
    private final IEditorPopMenu.PopMenuListener mPopMenuListener;
    protected SpeechRecognitionTask mRecordAiTask;
    NetworkRequest mRequest;
    protected final View.OnClickListener mToolbarClickListener;
    protected static final long MAX_RECORD_TIME = TimeUnit.SECONDS.toMillis(300);
    protected static final long LAST_WARRING_TIME = TimeUnit.SECONDS.toMillis(10);
    protected boolean mIsShowThemeFromTool = false;
    protected boolean mActionFromToolbarRichText = false;
    protected CountDownTimer mAudioRecordTimer = null;
    private Configuration mConfiguration = null;
    protected Intent mPendingHandleIntent = null;
    private long mStartTime = 0;
    private boolean mPendingCreateDoodle = false;

    static /* synthetic */ void lambda$showDualScreenEditTipDialog$13(DialogInterface dialogInterface, int i) {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getAutoSaveCommonDuration() {
        return 5000;
    }

    @Override // com.miui.notes.basefeature.noteedit.RecordTaskWorker
    public boolean isSupportRecordTaskWorker() {
        return true;
    }

    public PhoneNativeNoteEditFragment() {
        NetworkCallbackImpl.NetworkCall networkCall = new NetworkCallbackImpl.NetworkCall() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment.1
            @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
            public void onAvailable() {
                if (PhoneNativeNoteEditFragment.this.mRecordAiTask != null) {
                    PhoneNativeNoteEditFragment.this.mRecordAiTask.setNetWorkValid(true);
                    PhoneNativeNoteEditFragment.this.mRecordAiTask.startEngine();
                }
            }

            @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
            public void onLost() {
                if (PhoneNativeNoteEditFragment.this.mRecordAiTask != null) {
                    PhoneNativeNoteEditFragment.this.mRecordAiTask.setNetWorkValid(false);
                    PhoneNativeNoteEditFragment.this.mRecordAiTask.stopEngine();
                    Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getResources().getString(R.string.audio_toast_network_is_not_available), 1).show();
                }
            }
        };
        this.mNetworkCall = networkCall;
        this.mNetworkCallback = new NetworkCallbackImpl(networkCall);
        this.mRequest = new NetworkRequest.Builder().build();
        this.mConnMgr = (ConnectivityManager) NoteApp.getInstance().getSystemService("connectivity");
        this.mPopMenuListener = new IEditorPopMenu.PopMenuListener() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment.2
            @Override // com.miui.richeditor.IEditorPopMenu.PopMenuListener
            public void onImageMenuClick(View view, BaseImageSpan baseImageSpan) {
                switch (view.getId()) {
                    case R.id.annotate_image /* 2131362007 */:
                        PhoneNativeNoteEditFragment.this.annotateImage(baseImageSpan);
                        break;
                    case R.id.delete_image /* 2131362399 */:
                        if (PhoneNativeNoteEditFragment.this.mAnnotationController != null && PhoneNativeNoteEditFragment.this.mAnnotationController.isShowing()) {
                            PhoneNativeNoteEditFragment.this.annotateImage(baseImageSpan);
                            break;
                        }
                        break;
                    case R.id.edit_image /* 2131362464 */:
                        PhoneNativeNoteEditFragment.this.editImage(baseImageSpan);
                        break;
                    case R.id.scale_image /* 2131363347 */:
                        PhoneNativeNoteEditFragment.this.toggleImageShowBig(baseImageSpan);
                        break;
                    case R.id.view_image /* 2131363771 */:
                        PhoneNativeNoteEditFragment.this.viewImage(baseImageSpan);
                        break;
                }
            }

            @Override // com.miui.richeditor.IEditorPopMenu.PopMenuListener
            public void onLinkCardMenuClick(View view, LinkCardSpan linkCardSpan) {
                if (view.getId() == R.id.view_link) {
                    PhoneNativeNoteEditFragment.this.viewLinkByBrowser(linkCardSpan);
                }
            }
        };
        this.mToolbarClickListener = new View.OnClickListener() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (this.mWorkingNote == null || this.mWorkingNote.getIsInvalid()) {
            return;
        }
        int id = view.getId();
        if (id == R.id.audio) {
            if (Utils.isFastClick(view.getId(), 400L)) {
                return;
            }
            clickAudionBtnAboveOS2();
        } else {
            if (id == R.id.edit_image) {
                if (!this.mNoteRichEditor.canAddNewElement(63)) {
                    onContentOverLength(5);
                    return;
                } else {
                    createNewDoodle();
                    return;
                }
            }
            if (id != R.id.gallery) {
                return;
            }
            if (!this.mNoteRichEditor.canAddNewElement(63)) {
                onContentOverLength(4);
            } else {
                showAttachmentChooserDialog(view);
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onClickAudioBtnUnderOS2() {
        if (this.mIsRecording) {
            this.mIsRecording = false;
            this.mEditToolbarController.toggleAudioRecordPanel();
            updateActionMode(EditorActionMode.VIEW, true);
        } else {
            if (!this.mNoteRichEditor.canAddNewElement(63)) {
                onContentOverLength(0);
                return;
            }
            this.mActionFromToolbarRichText = false;
            Rect rect = new Rect();
            if (!this.mEditorView.isCursorVisible()) {
                this.mEditorView.setSelection(this.mEditorView.length());
                this.mEditorView.requestFocus();
            }
            this.mEditorView.getFocusedRect(rect);
            int paddingBottom = getView().findViewById(R.id.edit_content).getPaddingBottom() + UIUtils.dip2px(NoteApp.getInstance(), 20.0f);
            rect.top += paddingBottom;
            rect.bottom += paddingBottom;
            this.mEditorScrollView.requestChildRectangleOnScreen(this.mEditAreaLayout, rect, false);
            onClickAudioBtn();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected INoteInfoHeader createNoteExtraInfoController(View view, View view2) {
        return new NoteInfoController(getContext(), view, view2);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mStartTime = System.currentTimeMillis();
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (UIUtils.isAboveAndroidR()) {
            activity.getWindow().setSoftInputMode(48);
        } else if (!UIUtils.isInFullWindowGestureMode(activity)) {
            activity.getWindow().setSoftInputMode(16);
        }
        if (this.mHandler != null) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onViewCreated$1();
                }
            }, 200L);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        if (configuration == null) {
            return;
        }
        Configuration configuration2 = this.mConfiguration;
        if (configuration2 != null && (configuration2.keyboardHidden != configuration.keyboardHidden || this.mConfiguration.screenWidthDp != configuration.screenWidthDp || this.mConfiguration.screenHeightDp != configuration.screenHeightDp)) {
            super.onConfigurationChanged(configuration);
            if (isAdded() && this.mWorkingNote != null) {
                this.mEditorView.updateContent("", "");
                updateEditor(false);
            }
        }
        this.mConfiguration = configuration;
        handleBrowseMode();
        if (this.mSharePopupWindow == null || this.mHandler == null) {
            return;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onConfigurationChanged$2();
            }
        }, 50L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConfigurationChanged$2() {
        Rect rect = new Rect();
        if (getView() != null) {
            getView().getLocalVisibleRect(rect);
            this.mSharePopupWindow.refresh(rect);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        if (!z && isAdded() && this.mEditorView != null) {
            this.mEditorView.stopMediaPlayerIfNeed();
        }
        if (z || getMIsKeyboardVisible() || this.mEditToolbarController == null) {
            return;
        }
        this.mEditToolbarController.hide(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (isInEditAction()) {
            notifyEditUiState(1);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void closeEditActivity() {
        Utils.hideSoftInput(this.mEditorView);
        super.closeEditActivity();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        notifyEditUiState(2);
        if (this.mActionMode == EditorActionMode.EDIT_RECODE) {
            updateActionMode(EditorActionMode.VIEW, true);
        } else if (UIUtils.isAboveAndroidR() && getMIsKeyboardVisible()) {
            this.mIsNeedDelay = true;
            updateActionMode(EditorActionMode.VIEW, false);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        if (this.mActionMode == EditorActionMode.EDIT_RECODE) {
            updateActionMode(EditorActionMode.VIEW, true);
        }
        super.onStop();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        KeyboardHelper keyboardHelper = this.mEditKeyboardHelper;
        if (keyboardHelper != null) {
            keyboardHelper.release();
        }
        super.onDestroyView();
        if (this.mCurrentTheme == null || RomUtils.isPadMode()) {
            return;
        }
        trackReportQuitContent(this.mPendingHandleIntent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Editable text;
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
        DoodleApplication.cancelAndDeletePreloadData();
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        View viewFindViewById = activity.findViewById(R.id.content);
        if (viewFindViewById instanceof GestureFrameLayout) {
            ((GestureFrameLayout) viewFindViewById).setOperation(null);
        }
        SpeechRecognitionTask speechRecognitionTask = this.mRecordAiTask;
        if (speechRecognitionTask != null) {
            speechRecognitionTask.destroy();
            CountDownTimer countDownTimer = this.mAudioRecordTimer;
            if (countDownTimer != null) {
                try {
                    countDownTimer.cancel();
                } catch (Exception unused) {
                    Logger.INSTANCE.e(TAG, "cancel countdown timer failed");
                }
            }
        }
        this.mNoteExtraInfoController.onDestroy();
        if (this.mAnnotationController != null) {
            this.mAnnotationController.cancel();
            this.mAnnotationController = null;
        }
        if (this.mEditorView != null && (text = this.mEditorView.getText()) != null) {
            text.clearSpans();
        }
        super.onDestroy();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        if (dismissAllDialogOrPanel()) {
            return true;
        }
        boolean zOnBackPressed = this.mEditToolbarController.onBackPressed();
        if (this.mActionMode == EditorActionMode.EDIT_RECODE) {
            updateActionMode(EditorActionMode.VIEW, true);
            zOnBackPressed = true;
        }
        if (zOnBackPressed && isInEditAction()) {
            updateActionMode(EditorActionMode.VIEW, true);
        }
        if (this.mDialogFolderChooser != null) {
            this.mDialogFolderChooser.cancel();
            return true;
        }
        if (!zOnBackPressed) {
            if (this.mEditorView.commitComposingContent()) {
                asyncSaveNote();
            }
            this.mIsKillSaveTaskBeforeDestroy = true;
            killSaveTask();
        }
        return zOnBackPressed;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initView(View view) {
        super.initView(view);
        this.mContentView = view;
        smallWindowAdapter(this.mContentView);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initNoteEditor(View view) {
        super.initNoteEditor(view);
        this.mEditorController.setMenuListener(this.mPopMenuListener);
        this.mEditorView.setCursorClickListener(new IRichEditor.CursorClickListener() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda6
            @Override // com.miui.richeditor.IRichEditor.CursorClickListener
            public final void onLongClick() {
                this.f$0.lambda$initNoteEditor$4();
            }
        });
        final ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.sv_note_edit);
        final ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(-1, (int) view.getResources().getDimension(R.dimen.image_annotation_panel_height));
        layoutParams.startToStart = 0;
        layoutParams.endToEnd = 0;
        layoutParams.bottomToBottom = 0;
        final View viewFindViewById = view.findViewById(R.id.annotate_placeholder);
        new AsyncLayoutInflater(view.getContext()).inflate(R.layout.image_annotate_layout, null, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda7
            @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
            public final void onInflateFinished(View view2, int i, ViewGroup viewGroup) {
                this.f$0.lambda$initNoteEditor$5(constraintLayout, layoutParams, viewFindViewById, view2, i, viewGroup);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initNoteEditor$4() {
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initNoteEditor$3();
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initNoteEditor$3() {
        if (this.mEditToolbarController == null || this.mEditToolbarController.isTextStylePanelShowed() || this.mActionMode != EditorActionMode.EDIT) {
            return;
        }
        this.mEditToolbarController.setTextStylePanelVisible(true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initNoteEditor$5(ConstraintLayout constraintLayout, ConstraintLayout.LayoutParams layoutParams, View view, View view2, int i, ViewGroup viewGroup) {
        constraintLayout.addView(view2, layoutParams);
        this.mAnnotationController = new AnnotationEditController(view2, view);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initToolbarController(View view) {
        this.mEditToolbarController = createToolBarController(view.findViewById(R.id.editor_mode_tool_group));
        this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
        this.mEditToolbarController.setReadOnlyActionListener(new AnonymousClass3());
        this.mEditToolbarController.setExternalClickListener(this.mToolbarClickListener);
        this.mEditToolbarController.setOnThemeChosenListener(this.mThemeChosenListener);
        this.mTitleEditor.clearFocus();
        this.mEditorView.clearFocus();
    }

    /* JADX INFO: renamed from: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$3, reason: invalid class name */
    class AnonymousClass3 implements ReadOnlyActionListener {
        AnonymousClass3() {
        }

        @Override // com.miui.notes.editor.ReadOnlyActionListener
        public void onConfirmEdit(final Runnable runnable) {
            PhoneNativeNoteEditFragment.this.confirmEditAnyway(new DialogInterface.OnClickListener() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$3$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$onConfirmEdit$0(runnable, dialogInterface, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfirmEdit$0(Runnable runnable, DialogInterface dialogInterface, int i) {
            PhoneNativeNoteEditFragment.this.setReadonly(false);
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: initKeyboardHelper, reason: merged with bridge method [inline-methods] */
    public void lambda$onViewCreated$1() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        this.mIsInFullScreenMode = UIUtils.isInFullWindowGestureMode(activity);
        this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
        this.mEditToolbarController.updateLayout();
        KeyboardHelper keyboardHelper = new KeyboardHelper(this.mContentView, activity.getWindow().getDecorView(), new KeyboardPaddingProcessor() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment.4
            private Rect windowVisiableFrame = new Rect();

            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor, com.miui.common.tool.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChanged(View view, WindowInsets windowInsets, int i, int i2) {
                super.onKeyboardHeightChanged(view, windowInsets, i, i2);
            }

            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor, com.miui.common.tool.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChangedEnd(View view, WindowInsets windowInsets, int i) {
                super.onKeyboardHeightChangedEnd(view, windowInsets, i);
                if (PhoneNativeNoteEditFragment.this.getMIsKeyboardVisible()) {
                    return;
                }
                super.updateViewPaddingBottom(view, windowInsets, 0);
            }

            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor
            protected void updateViewPaddingBottom(View view, WindowInsets windowInsets, int i) {
                if (PhoneNativeNoteEditFragment.this.isAdded()) {
                    FragmentActivity activity2 = PhoneNativeNoteEditFragment.this.getActivity();
                    if (activity2 != null && activity2.isInMultiWindowMode()) {
                        if (UIUtils.isAboveAndroidR()) {
                            boolean showKeyboard = KeyboardHelper.INSTANCE.getShowKeyboard();
                            if (PhoneNativeNoteEditFragment.this.getMIsKeyboardVisible() != showKeyboard) {
                                PhoneNativeNoteEditFragment.this.onKeyboardVisibleChanged(showKeyboard);
                            }
                        } else if (!UIUtils.isSmallWindowMode(activity2)) {
                            activity2.getWindow().getDecorView().getWindowVisibleDisplayFrame(this.windowVisiableFrame);
                        }
                    } else if (UIUtils.isAboveAndroidR()) {
                        boolean showKeyboard2 = KeyboardHelper.INSTANCE.getShowKeyboard();
                        if (PhoneNativeNoteEditFragment.this.getMIsKeyboardVisible() != showKeyboard2) {
                            PhoneNativeNoteEditFragment.this.onKeyboardVisibleChanged(showKeyboard2);
                        }
                    } else if (i <= 0) {
                        if (PhoneNativeNoteEditFragment.this.getMIsKeyboardVisible()) {
                            PhoneNativeNoteEditFragment.this.onKeyboardVisibleChanged(false);
                        }
                    } else if (!PhoneNativeNoteEditFragment.this.getMIsKeyboardVisible()) {
                        PhoneNativeNoteEditFragment.this.onKeyboardVisibleChanged(true);
                    }
                    PhoneNativeNoteEditFragment.this.mEditToolbarController.updatePaddingBottom(i);
                    super.updateViewPaddingBottom(view, windowInsets, i);
                }
            }
        });
        this.mEditKeyboardHelper = keyboardHelper;
        keyboardHelper.setForceMode(false);
    }

    public void setController(IFragmentController iFragmentController) {
        this.mFragmentController = iFragmentController;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void checkEditEnable() {
        if (UIUtils.isBrowseMode(getActivity())) {
            if (!this.mHandler.hasMessages(8)) {
                this.mHandler.removeMessages(8);
                this.mHandler.sendEmptyMessageDelayed(8, 400L);
            }
            setEnableEdit(false);
            return;
        }
        this.mHandler.removeMessages(8);
        if (getActivity() != null) {
            getActivity().getWindow().clearFlags(131072);
        }
        setEnableEdit(true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void updateActionMode(EditorActionMode editorActionMode, boolean z) {
        Logger.INSTANCE.d(TAG, "updateActionMode mode = " + editorActionMode);
        if (editorActionMode == null || editorActionMode == this.mActionMode) {
            if (editorActionMode == EditorActionMode.EDIT) {
                if (this.mEditToolbarController.shouldHideSoftInput()) {
                    if (this.mIsShowThemeFromTool) {
                        this.mEditToolbarController.collapsePanel(null, true);
                        return;
                    } else {
                        this.mEditorController.hideInputMethod();
                        this.mEditToolbarController.checkAndCollapseThemePanel();
                        return;
                    }
                }
                if (getMIsKeyboardVisible()) {
                    this.mEditorController.showInputMethod();
                    if (this.mIsShowThemeFromTool || annotationHide()) {
                        this.mEditToolbarController.show(true, false, false);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        super.updateActionMode(editorActionMode, z);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected boolean handleIntent(Intent intent) {
        boolean zHandleIntent = super.handleIntent(intent);
        this.mEditToolbarController.setNeedDelayShowInput(this.mNeedDelayShowInput);
        this.mNeedDelayShowInput = false;
        this.mPendingHandleIntent = intent;
        return zHandleIntent;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void editToView(boolean z) {
        this.mEditToolbarController.onViewMode(z);
        dismissDialog();
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
        this.mEditToolbarController.onViewMode(true);
        if (this.mIsRecording) {
            this.mIsRecording = false;
        }
        stopRecordTask();
        this.mEditToolbarController.resetRichTextStatus();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void viewToEdit() {
        if (!this.mActionFromToolbarRichText) {
            this.mEditToolbarController.collapsePanel(null, false);
        } else {
            this.mEditorView.requestFocus();
            this.mEditorView.setSelection(this.mEditorView.getCacheSelectionStart());
            this.mEditorView.cacheSelection(false);
        }
        annotationHide();
        this.mEditToolbarController.onEditContentMode(!this.mActionFromToolbarRichText, false, false);
        onEditMode();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void homeToEdit() {
        super.homeToEdit();
        this.mEditToolbarController.resetRichTextStatus();
        this.mEditToolbarController.onEditContentMode(true, false, false);
        this.mEditorView.onSelectionChanged(this.mEditorView.getSelectionStart(), this.mEditorView.getSelectionEnd());
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
        super.viewToTitle();
        this.mEditToolbarController.onEditTitleMode();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void viewToRecord() {
        if (isAdded()) {
            super.viewToRecord();
            this.mEditorController.hideInputMethod();
            if (this.mEditToolbarController != null) {
                this.mEditToolbarController.onEditContentMode(false, false, false);
                this.mEditToolbarController.toggleAudioRecordPanel();
            }
            startRecordTask();
            this.mIsRecording = true;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void editToRecord() {
        if (isAdded()) {
            super.editToRecord();
            this.mEditorController.hideInputMethod();
            this.mEditToolbarController.toggleAudioRecordPanel();
            startRecordTask();
            this.mIsRecording = true;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void homeToRecord() {
        if (isAdded()) {
            super.homeToRecord();
            this.mEditorController.hideInputMethod();
            this.mEditToolbarController.show(false, false, false);
            this.mEditToolbarController.toggleAudioRecordPanel();
            startRecordTask();
            this.mIsRecording = true;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void onViewMode() {
        Logger.INSTANCE.i(TAG, "onViewMode");
        super.onViewMode();
        notifyEditUiState(0);
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showAttachmentChooserDialog(View view) {
        if (this.mEditorView != null) {
            this.mEditorView.cacheSelection(true);
        }
        final View.OnFocusChangeListener onFocusChangeListener = null;
        if (this.mImageChooserMenu != null) {
            this.mImageChooserMenu = null;
        }
        if (this.mEditorView != null) {
            View.OnFocusChangeListener onFocusChangeListener2 = this.mEditorView.getOnFocusChangeListener();
            this.mEditorView.setOnFocusChangeListener(null);
            this.mEditorView.clearFocus();
            onFocusChangeListener = onFocusChangeListener2;
        }
        ImageChooserMenu imageChooserMenu = new ImageChooserMenu(getContext());
        imageChooserMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment.5
            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onDismiss() {
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onShow() {
                if (PhoneNativeNoteEditFragment.this.mEditorView != null) {
                    PhoneNativeNoteEditFragment.this.mEditorView.requestFocus();
                    PhoneNativeNoteEditFragment.this.mEditorView.setOnFocusChangeListener(onFocusChangeListener);
                }
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onItemSelected(View view2, int i) {
                if (i == 0) {
                    PhoneNativeNoteEditFragment.this.addAttachment(1);
                    EditStat.trackGalleryChooserClick(EditStat.ELEMENT_CHOOSE_LOCAL_CAMERA);
                } else {
                    if (i != 1) {
                        return;
                    }
                    PhoneNativeNoteEditFragment.this.addAttachment(2);
                    EditStat.trackGalleryChooserClick("gallery");
                }
            }
        });
        imageChooserMenu.show(view, new int[2]);
        this.mImageChooserMenu = imageChooserMenu;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void createNewDoodle() {
        this.mEditorView.cacheSelection(true);
        Logger.INSTANCE.d(TAG, "MIsKeyboardVisible: " + getMIsKeyboardVisible());
        if (getMIsKeyboardVisible()) {
            this.mPendingCreateDoodle = true;
            this.mEditorController.hideInputMethod();
        } else {
            updateActionMode(EditorActionMode.VIEW, true);
            this.mEditorController.hideInputMethod();
            super.createNewDoodle();
        }
    }

    @Override // com.miui.notes.ui.BaseActionBarController.EditCallback
    public boolean isOnSaveInstanceCalled() {
        return this.mIsOnSaveInstanceCalled;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public void onEditorClick(boolean z) {
        if (isAdded() && this.mEnableEdit) {
            this.mNestedHeaderLayout.setAutoTriggerClose(true);
            if (Build.VERSION.SDK_INT >= 30) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onEditorClick$6();
                    }
                }, 400L);
            }
            if (this.mIsNeedDelay && this.mHandler != null && this.isLite) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onEditorClick$7();
                    }
                }, 250L);
            } else {
                updateActionMode(EditorActionMode.EDIT, true);
            }
            if (CompatUtils.invokeIsInMultiWindowMode(getActivity())) {
                this.mEditorController.forceShowInputMethod();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEditorClick$6() {
        this.mEditorView.bringPointIntoView(this.mEditorView.getSelectionEnd());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEditorClick$7() {
        updateActionMode(EditorActionMode.EDIT, true);
        this.mIsNeedDelay = false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public boolean onEditorLongClick() {
        if (!isAdded() || !this.mEnableEdit) {
            return false;
        }
        this.mNestedHeaderLayout.setAutoTriggerClose(true);
        if (Build.VERSION.SDK_INT >= 30) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onEditorLongClick$8();
                }
            }, 400L);
        }
        if (this.mEditToolbarController != null && !this.mEditToolbarController.isTextStylePanelShowed() && this.mActionMode == EditorActionMode.EDIT) {
            this.mEditToolbarController.setTextStylePanelVisible(true, true);
        }
        updateActionMode(EditorActionMode.EDIT, true);
        if (!CompatUtils.invokeIsInMultiWindowMode(getActivity())) {
            return false;
        }
        this.mEditorController.forceShowInputMethod();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEditorLongClick$8() {
        this.mEditorView.bringPointIntoView(this.mEditorView.getSelectionEnd());
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onNetworkStateChanged(boolean z) {
        super.onNetworkStateChanged(z);
        SpeechRecognitionTask speechRecognitionTask = this.mRecordAiTask;
        if (speechRecognitionTask != null) {
            speechRecognitionTask.setNetWorkValid(z);
            if (z) {
                this.mRecordAiTask.startEngine();
            } else {
                this.mRecordAiTask.stopEngine();
            }
        }
        if (z || !isAdded()) {
            return;
        }
        Toast.makeText(NoteApp.getInstance(), R.string.audio_toast_network_is_not_available, 0).show();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, com.miui.common.tool.softinputhelper.KeyboardListener
    public void onKeyboardVisibleChanged(boolean z) {
        super.onKeyboardVisibleChanged(z);
        if (this.mAnnotationController != null && this.mAnnotationController.isShowing()) {
            if (z) {
                return;
            } else {
                this.mAnnotationController.cancel();
            }
        }
        if (z || !this.mPendingCreateDoodle) {
            Logger.INSTANCE.d(TAG, "onKeyboardVisibleChanged visible = " + z + " hasFocus = " + this.mEditorView.hasFocus() + " mAction = " + this.mActionMode);
            if (this.mEditorView.getShowSoftInputOnFocus() && this.mEditorView.hasFocus()) {
                if (!z && this.mActionMode == EditorActionMode.EDIT) {
                    updateActionMode(EditorActionMode.VIEW, true);
                } else if (z && this.mActionMode == EditorActionMode.VIEW) {
                    updateActionMode(EditorActionMode.EDIT, true);
                } else if (this.mIsShowThemeFromTool && z && this.mActionMode == EditorActionMode.EDIT) {
                    updateActionMode(EditorActionMode.EDIT, true);
                }
            } else if (!this.mActionFromToolbarRichText && !z && this.mActionMode == EditorActionMode.EDIT) {
                updateActionMode(EditorActionMode.VIEW, true);
            } else if (this.mIsShowThemeFromTool && !z && this.mActionMode == EditorActionMode.EDIT) {
                updateActionMode(EditorActionMode.VIEW, true);
            } else if (z && this.mActionMode == EditorActionMode.VIEW && !isDialogShowing()) {
                updateActionMode(EditorActionMode.EDIT, true);
            }
            if (z && !this.mEditorView.hasFocus() && this.mActionMode == EditorActionMode.VIEW) {
                Utils.hideSoftInput(this.mEditorView);
            }
            if (z) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onKeyboardVisibleChanged$9();
                    }
                }, 400L);
                return;
            }
            return;
        }
        Logger.INSTANCE.d(TAG, "onKeyboardVisibleChanged createNewDoodle: ");
        this.mPendingCreateDoodle = false;
        updateActionMode(EditorActionMode.VIEW, true);
        super.createNewDoodle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onKeyboardVisibleChanged$9() {
        this.mEditorView.bringPointIntoView(Math.min(this.mEditorView.getText() != null ? this.mEditorView.getText().length() : 0, this.mEditorView.getSelectionStart()));
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    @Deprecated
    protected void forceHideSoftInput() {
        this.mEditToolbarController.hide(false);
        super.forceHideSoftInput();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void onAnimThemeBgViewChanged(View view) {
        super.onAnimThemeBgViewChanged(view);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onLoadDataFailed() {
        super.onLoadDataFailed();
        if (this.mHandler != null) {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onLoadDataFailed$10();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLoadDataFailed$10() {
        if (isAdded()) {
            Logger.INSTANCE.i(TAG, "Fail to load note, Back!");
            getActivity().onBackPressed();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void asyncSaveNote() {
        if (DualScreenManager.INSTANCE.isPcActivityLiving() && this.mIsRecording) {
            return;
        }
        super.asyncSaveNote();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void asyncSaveNoteInstantlyIfNeeded() {
        if (DualScreenManager.INSTANCE.isPcActivityLiving() && this.mIsRecording) {
            return;
        }
        super.asyncSaveNoteInstantlyIfNeeded();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void deleteCurrentNote() {
        super.deleteCurrentNote();
        Utils.hideSoftInput(this.mEditorView);
        this.mActionBarController.onNavigationUp();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void addAttachment(int i) {
        updateActionMode(EditorActionMode.VIEW, true);
        this.mEditorController.hideInputMethod();
        super.addAttachment(i);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public void annotateImage(final BaseImageSpan baseImageSpan) {
        if (!isAdded() || getActivity() == null) {
            return;
        }
        if ((RomUtils.isPadMode() || getActivity().getResources().getConfiguration().orientation != 2) && this.mAnnotationController != null) {
            if (!this.mAnnotationController.isShowing()) {
                this.mEditorScrollView.setForbidScroll(true);
                this.mEditToolbarController.hide(false);
                this.mAnnotationController.setEditListener(new AnnotationEditController.EditListener() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment.6
                    @Override // com.miui.notes.ui.widget.AnnotationEditController.EditListener
                    public void onOverLength() {
                        PhoneNativeNoteEditFragment.this.onImageAnnotationOverLength();
                    }

                    @Override // com.miui.notes.ui.widget.AnnotationEditController.EditListener
                    public void onCompete(String str) {
                        Logger.INSTANCE.d(PhoneNativeNoteEditFragment.TAG, "mAnnotationController-onCompete");
                        baseImageSpan.setAnnotation(str);
                        PhoneNativeNoteEditFragment.this.mEditorView.createNewRichTextData();
                        PhoneNativeNoteEditFragment.this.saveNote(true);
                        PhoneNativeNoteEditFragment.this.annotateImage(baseImageSpan);
                        PhoneNativeNoteEditFragment.this.mEditorView.refreshAllImageSpans();
                        PhoneNativeNoteEditFragment.this.mEditorView.setSelection(PhoneNativeNoteEditFragment.this.mEditorView.getText().getSpanEnd(baseImageSpan));
                        PhoneNativeNoteEditFragment.this.mEditorScrollView.setForbidScroll(false);
                    }
                });
                this.mEditorView.clearTextWatcher();
                if (this.mEditorView.getText() != null) {
                    this.mEditorView.setSelection(this.mEditorView.getText().getSpanEnd(baseImageSpan));
                }
                this.mEditorView.requestFocus();
                final int selectionEnd = this.mEditorView.getSelectionEnd();
                if (getMIsKeyboardVisible()) {
                    this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$annotateImage$11(selectionEnd);
                        }
                    }, 400L);
                } else {
                    this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda12
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$annotateImage$12(baseImageSpan);
                        }
                    }, 400L);
                }
                if (baseImageSpan.getAnnotation().isEmpty()) {
                    this.mAnnotationController.setAnnotation("");
                } else {
                    this.mAnnotationController.setAnnotation(baseImageSpan.getAnnotation());
                }
                this.mAnnotationController.show();
                return;
            }
            this.mAnnotationController.hide();
            this.mEditorView.restoreTextWatcher();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$annotateImage$11(int i) {
        this.mEditorScrollView.setForbidScroll(false);
        this.mEditorView.bringPointIntoView(Math.min(i + 1, this.mEditorView.getText() != null ? this.mEditorView.getText().length() : 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$annotateImage$12(BaseImageSpan baseImageSpan) {
        this.mEditorScrollView.setForbidScroll(false);
        Rect rect = new Rect(baseImageSpan.getBounds());
        rect.offset(0, UIUtils.dip2px(NoteApp.getInstance(), 60.0f));
        rect.top = rect.bottom;
        this.mEditorScrollView.requestChildRectangleOnScreen(this.mEditAreaLayout, rect, true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void requestXiaoAiPermission() {
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (!PreferenceUtils.getAllowXiaoai(activity) && !RomUtils.isInternationalBuild()) {
            manageDialog(PermissionUtils.showXiaoaiAcceptDialog(activity, new PermissionUtils.AcceptListener() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment.7
                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void reject() {
                }

                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void accept() {
                    PhoneNativeNoteEditFragment.this.updateRecordMode();
                }
            }));
        } else {
            updateRecordMode();
        }
    }

    protected void startRecordTask() {
        PhoneNativeNoteEditFragment phoneNativeNoteEditFragment;
        if (DualScreenManager.INSTANCE.isPcActivityLiving() && this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        this.mEditorView.stopMediaPlayerIfNeed();
        if (this.mRecordAiTask == null) {
            this.mRecordAiTask = new SpeechRecognitionTaskImp(NoteApp.getInstance(), AudioUtils.NOTE_MP3_DIR_NAME, this);
        }
        this.mEditToolbarController.startAudioInput();
        if (this.mAudioRecordTimer == null) {
            phoneNativeNoteEditFragment = this;
            phoneNativeNoteEditFragment.mAudioRecordTimer = new CountDownTimer(MAX_RECORD_TIME, 1000L) { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment.8
                @Override // android.os.CountDownTimer
                public void onTick(long j) {
                    PhoneNativeNoteEditFragment.this.onAudioTimerTick(j);
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    PhoneNativeNoteEditFragment.this.onAudioTimerFinish();
                }
            };
        } else {
            phoneNativeNoteEditFragment = this;
        }
        phoneNativeNoteEditFragment.mAudioRecordTimer.start();
        phoneNativeNoteEditFragment.mNetworkCallback.initNetWorkState();
        boolean isNetWorkAvailable = phoneNativeNoteEditFragment.mNetworkCallback.getIsNetWorkAvailable();
        Logger.INSTANCE.d(TAG, "isNetworkAvailable " + isNetWorkAvailable);
        if (!phoneNativeNoteEditFragment.mRecordAiTask.isSkipSpeechRecognition()) {
            phoneNativeNoteEditFragment.registerNetWorkListener();
            if (!isNetWorkAvailable) {
                Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getResources().getString(R.string.audio_toast_network_is_not_available), 1).show();
            }
        }
        phoneNativeNoteEditFragment.getActivity().getWindow().addFlags(128);
        try {
            phoneNativeNoteEditFragment.mRecordAiTask.setNetWorkValid(isNetWorkAvailable);
            phoneNativeNoteEditFragment.mRecordAiTask.start();
            phoneNativeNoteEditFragment.mIsWarned = false;
        } catch (MibrainException unused) {
            Logger.INSTANCE.e(TAG, "Fail to start record task!");
        }
        phoneNativeNoteEditFragment.asyncSaveNote();
    }

    protected void stopRecordTask() {
        SpeechRecognitionTask speechRecognitionTask = this.mRecordAiTask;
        if (speechRecognitionTask != null) {
            speechRecognitionTask.shutdownNow();
            if (!this.mRecordAiTask.isSkipSpeechRecognition()) {
                unRegisterNetWorkListener();
            }
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.getWindow().clearFlags(128);
            }
            this.mIsWarned = false;
            CountDownTimer countDownTimer = this.mAudioRecordTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                this.mAudioRecordTimer = null;
            }
        }
        this.mEditToolbarController.stopAudioInput();
    }

    protected void onAudioTimerTick(long j) {
        if (!this.mIsWarned && j <= LAST_WARRING_TIME) {
            this.mIsWarned = true;
            this.mEditorMaskView.addView(getAudioCountDownView());
        }
        if (this.mIsWarned) {
            this.mCountDownView.setText(Utils.obtainLocalInteger(((int) Math.floor(j / 1000)) + 1, R.plurals.record_last_ten_seconds_reminder));
        }
    }

    protected void onAudioTimerFinish() {
        this.mEditorMaskView.removeAllViews();
        updateActionMode(EditorActionMode.VIEW, true);
    }

    protected View getAudioCountDownView() {
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

    @Override // com.miui.common.record.SpeechRecognitionListener
    public void onAsrAppendTextResult(String str, boolean z) {
        this.mEditorView.onAudioTextAppend(str, z);
        refreshEditorFocus();
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
        this.mEditorView.onAudioEndAndFileSaved(str);
        saveNote(true);
    }

    protected void notifyAudioFileSaved(String str) {
        Intent intent = new Intent();
        intent.setAction(FrameConstant.BROADCAST_ACTION_NOTE_EDITOR_AUDIO);
        intent.putExtra("mp3_name", str);
        if (this.mLocalBroadcastManager != null) {
            this.mLocalBroadcastManager.sendBroadcast(intent);
        }
        asyncSaveNote();
    }

    protected void registerNetWorkListener() {
        ConnectivityManager connectivityManager = this.mConnMgr;
        if (connectivityManager != null) {
            connectivityManager.registerNetworkCallback(this.mRequest, this.mNetworkCallback);
        }
    }

    protected void unRegisterNetWorkListener() {
        ConnectivityManager connectivityManager = this.mConnMgr;
        if (connectivityManager != null) {
            connectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        }
    }

    protected void refreshEditorFocus() {
        if (!this.mEditorView.isCursorVisible() || getView() == null) {
            return;
        }
        Rect rect = new Rect();
        this.mEditorView.getFocusedRect(rect);
        int paddingBottom = getView().findViewById(R.id.edit_content).getPaddingBottom() + UIUtils.dip2px(NoteApp.getInstance(), 20.0f);
        rect.top += paddingBottom;
        rect.bottom += paddingBottom;
        this.mEditorScrollView.requestChildRectangleOnScreen(this.mEditAreaLayout, rect, false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void responseEditUiState() {
        if (isInEditAction()) {
            notifyEditUiState(1);
        } else {
            notifyEditUiState(0);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showDualScreenEditTipDialog() {
        if (isAdded()) {
            if (this.mDualScreenEditTipDialog == null || !this.mDualScreenEditTipDialog.isShowing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle(getString(R.string.note_dual_screen_dialog_title));
                builder.setMessage(getString(R.string.note_dual_screen_dialog_content));
                builder.setNegativeButton(getString(R.string.dialog_button_continue_work), new DialogInterface.OnClickListener() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda4
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        PhoneNativeNoteEditFragment.lambda$showDualScreenEditTipDialog$13(dialogInterface, i);
                    }
                });
                builder.setPositiveButton(getString(R.string.dialog_button_close), new DialogInterface.OnClickListener() { // from class: com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment$$ExternalSyntheticLambda5
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.lambda$showDualScreenEditTipDialog$14(dialogInterface, i);
                    }
                });
                this.mDualScreenEditTipDialog = builder.create();
                this.mDualScreenEditTipDialog.show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDualScreenEditTipDialog$14(DialogInterface dialogInterface, int i) {
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

    public void trackReportQuitContent(Intent intent) {
        String str;
        long j;
        String str2;
        String contentType;
        String noteContentStatus;
        long j2;
        String str3;
        String str4;
        int i;
        List<FolderItemEntity> list = GlobalStat.Status.folderList;
        if (intent == null || intent.getExtras() == null) {
            str = "";
            j = 0;
        } else {
            Bundle extras = intent.getExtras();
            j = extras.getLong("com.miui.notes.folder_id");
            boolean zIsPhoneDevice = com.miui.notes.ai.utils.RomUtils.isPhoneDevice();
            String str5 = NoteContentStat.TYPE_CARD_WIDGET;
            if (zIsPhoneDevice) {
                if (extras.containsKey("android.intent.extra.REFERRER") && extras.containsKey("intent_extra_data_key")) {
                    str5 = NoteContentStat.TYPE_GLOBAL_SEARCH;
                } else if (extras.containsKey("intent_extra_data_key")) {
                    str5 = NoteContentStat.TYPE_NOTES_SEARCH;
                } else if (!extras.containsKey("com.miui.notes.widget_type")) {
                    if (extras.containsKey(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW)) {
                        str5 = extras.getBoolean(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW) ? "全局速记" : "";
                    } else if (extras.containsKey("android.intent.extra.PHONE_NUMBER")) {
                        str5 = "通话笔记";
                    } else {
                        str5 = "笔记首页";
                    }
                }
            } else if (this.mIsFromFloatWindow) {
                str = "全局速记";
            } else if (!extras.containsKey("com.miui.notes.widget_type") || extras.getInt("com.miui.notes.widget_type") == -1) {
                str5 = "unknown";
            }
            str = str5;
        }
        if (list == null || list.isEmpty()) {
            str2 = "";
        } else {
            String folderName = "";
            for (FolderItemEntity folderItemEntity : GlobalStat.Status.folderList) {
                if (folderItemEntity.getFolderId() == j) {
                    folderName = folderItemEntity.getFolderName();
                }
            }
            str2 = folderName;
        }
        int themeId = 0;
        if (this.mWorkingNote == null) {
            contentType = "";
            noteContentStatus = contentType;
            j2 = 0;
            str3 = "否";
            str4 = str3;
            i = 0;
        } else {
            long createdDate = this.mWorkingNote.getCreatedDate();
            String str6 = this.mWorkingNote.getAlertDate() > 0 ? "是" : "否";
            String str7 = this.mWorkingNote.getFolderId() == -4 ? "是" : "否";
            int plainTextCount = NoteSchemaUtil.getPlainTextCount(NoteSchema.convertToWeb(this.mWorkingNote.getContent()));
            contentType = NoteSchemaUtil.getContentType(NoteSchema.convertToWeb(this.mWorkingNote.getContent()));
            noteContentStatus = getNoteContentStatus();
            themeId = this.mWorkingNote != null ? this.mWorkingNote.getThemeId() : 0;
            j2 = createdDate;
            str3 = str6;
            str4 = str7;
            i = plainTextCount;
        }
        String str8 = NoteContentStat.IS_STICK ? "是" : "否";
        if (NoteContentStat.IS_NOTE_BTN_BACK) {
            NoteContentStat.QUIT_WAY = NoteContentStat.QUIT_WAY_BTN_BACK;
        }
        NotesTextStat.reportQuitEditEvent(str, str2, str2, contentType, noteContentStatus, i, j2, String.valueOf(themeId), System.currentTimeMillis() - this.mStartTime, NoteContentStat.QUIT_WAY, str3, str8, str4);
    }

    private String getNoteContentStatus() {
        if (this.mWorkingNote.getTitle().isEmpty() && this.mWorkingNote.getContent().isEmpty()) {
            return AITextWidgetStat.EMPTY_STATUS;
        }
        if (this.mWorkingNote.getTitle().isEmpty()) {
            return AITextWidgetStat.NO_TITLE_CONTENT;
        }
        if (this.mWorkingNote.getContent().isEmpty()) {
            return AITextWidgetStat.TITLE_NO_CONTENT;
        }
        return AITextWidgetStat.TITLE_AND_CONTENT;
    }
}
