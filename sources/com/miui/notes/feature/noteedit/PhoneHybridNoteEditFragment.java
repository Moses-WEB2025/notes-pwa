package com.miui.notes.feature.noteedit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.record.SpeechRecognitionListener;
import com.miui.common.record.SpeechRecognitionTask;
import com.miui.common.record.SpeechRecognitionTaskImp;
import com.miui.common.stat.EditStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.DualScreenManager;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.Logger;
import com.miui.common.tool.NetworkCallbackImpl;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor;
import com.miui.common.view.IPopMenu;
import com.miui.doodle.DoodleApplication;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.hybirdeditor.TempAudio;
import com.miui.hybirdeditor.jsbridge.HybridEditorScriptInterface;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.component.ThemeChooserGroup;
import com.miui.notes.component.noteedit.NoteEditToolbarController;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.provider.Notes;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.BaseActionBarController;
import com.miui.notes.ui.EventController;
import com.miui.notes.ui.PhoneActionBarController;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.pad.feature.notes.commonedit.ImageChooserMenu;
import com.miui.richeditor.EditorActionMode;
import com.xiaomi.ai.mibrain.MibrainException;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import miuix.appcompat.app.AlertDialog;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneHybridNoteEditFragment extends BaseHybridNoteEditFragment implements SpeechRecognitionListener {
    private static final String TAG = "PhoneHybridNoteEditFragment";
    ConnectivityManager mConnMgr;
    protected TextView mCountDownView;
    protected KeyboardHelper mEditKeyboardHelper;
    protected boolean mIsWarned;
    NetworkCallbackImpl mNetworkCallback;
    private SpeechRecognitionTask mRecordAiTask;
    private static final long MAX_RECORD_TIME = TimeUnit.SECONDS.toMillis(300);
    protected static final long LAST_WARRING_TIME = TimeUnit.SECONDS.toMillis(10);
    private CountDownTimer mAudioRecordTimer = null;
    private boolean mIsFirstInflate = false;
    private boolean mCanUpdateViewBottom = true;
    private int mSavedThemeId = 0;
    private final View.OnClickListener mToolbarClickListener = new View.OnClickListener() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda5
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f$0.lambda$new$0(view);
        }
    };

    static /* synthetic */ void lambda$showDualScreenEditTipDialog$8(DialogInterface dialogInterface, int i) {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getLayoutResId() {
        return R.layout.hybrid_note_edit_layout;
    }

    @Override // com.miui.notes.basefeature.noteedit.RecordTaskWorker
    public boolean isSupportRecordTaskWorker() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (this.mWorkingNote == null || this.mWorkingNote.getIsInvalid()) {
            Logger.INSTANCE.e(TAG, "working note is null or invalid");
            return;
        }
        switch (view.getId()) {
            case R.id.ai /* 2131361915 */:
                Logger.INSTANCE.d(TAG, "toolbar onClick ai");
                showAttachmentAiChooser(view);
                break;
            case R.id.audio /* 2131362036 */:
                Logger.INSTANCE.d(TAG, "toolbar onClick audio");
                if (!Utils.isFastClick(view.getId(), 800L)) {
                    clickAudionBtnAboveOS2();
                    break;
                }
                break;
            case R.id.edit_image /* 2131362464 */:
                Logger.INSTANCE.d(TAG, "toolbar onClick edit_image");
                if (!this.mNoteRichEditor.canAddNewElement(63)) {
                    onContentOverLength(5);
                } else {
                    createNewDoodle();
                }
                break;
            case R.id.gallery /* 2131362611 */:
                Logger.INSTANCE.d(TAG, "toolbar onClick gallery");
                if (!this.mNoteRichEditor.canAddNewElement(63)) {
                    onContentOverLength(4);
                } else {
                    showAttachmentChooserDialog(view);
                }
                break;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onClickAudioBtnUnderOS2() {
        removeAiFloatingWindow();
        updateRecordClickTime();
        if (this.mIsRecording) {
            stopRecord();
        } else {
            if (!this.mNoteRichEditor.canAddNewElement(63)) {
                onContentOverLength(0);
                return;
            }
            this.mActionFromToolbarRichText = false;
            Utils.hideSoftInput(this.mEditorWebView);
            onClickAudioBtn();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showAttachmentChooserDialog(View view) {
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        if (viewGroup != null) {
            viewGroup.findViewById(R.id.content_container).setImportantForAccessibility(4);
        }
        final View.OnFocusChangeListener onFocusChangeListener = null;
        if (this.mImageChooserMenu != null) {
            this.mImageChooserMenu = null;
        }
        if (this.mEditorWebView != null) {
            View.OnFocusChangeListener onFocusChangeListener2 = this.mEditorWebView.getOnFocusChangeListener();
            this.mEditorWebView.setOnFocusChangeListener(null);
            this.mEditorWebView.clearFocus();
            this.mEditorWebView.stopActionMode();
            onFocusChangeListener = onFocusChangeListener2;
        }
        ImageChooserMenu imageChooserMenu = new ImageChooserMenu(getContext(), this.mEditorWebView == null ? 0 : this.mEditorWebView.getHeight(), false);
        imageChooserMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment.1
            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onDismiss() {
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onShow() {
                if (PhoneHybridNoteEditFragment.this.mEditorWebView != null) {
                    PhoneHybridNoteEditFragment.this.mEditorWebView.requestFocus();
                    PhoneHybridNoteEditFragment.this.mEditorWebView.setOnFocusChangeListener(onFocusChangeListener);
                }
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onItemSelected(View view2, int i) {
                if (i == 0) {
                    PhoneHybridNoteEditFragment.this.addAttachment(1);
                    EditStat.trackGalleryChooserClick(EditStat.ELEMENT_CHOOSE_LOCAL_CAMERA);
                } else {
                    if (i != 1) {
                        return;
                    }
                    PhoneHybridNoteEditFragment.this.addAttachment(2);
                    EditStat.trackGalleryChooserClick("gallery");
                }
            }
        });
        imageChooserMenu.show(view, new int[2]);
        this.mImageChooserMenu = imageChooserMenu;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void createNewDoodle() {
        if (getActivity() != null) {
            this.mEditToolbarController.hideWithoutAnim();
            Utils.hideSoftInput(getActivity());
        }
        super.createNewDoodle();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected HybridEditorScriptInterface createJsBridge() {
        return new HybridEditorScriptInterface(this, 0);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected BaseActionBarController createHeaderBarController(View view) {
        return new PhoneActionBarController(this, this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public NoteEditToolbarController createToolBarController(View view) {
        return new MultiThemesSmallScreenNoteEditToolbarController(getActivity(), view.findViewById(R.id.editor_mode_tool_group), this.mEditor);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected INoteInfoHeader createNoteExtraInfoController(View view, View view2) {
        return new NoteFolderHeader(getContext(), view2);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logger.INSTANCE.d(TAG, "onCreate");
        this.mCanUpdateViewBottom = true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        Intent intent;
        Logger.INSTANCE.d(TAG, "onViewInflated");
        super.onViewInflated(view, bundle);
        this.mIsFirstInflate = true;
        FragmentActivity activity = getActivity();
        if (UIUtils.isAboveAndroidR()) {
            activity.getWindow().setSoftInputMode(48);
        } else if (!UIUtils.isInFullWindowGestureMode(activity)) {
            activity.getWindow().setSoftInputMode(16);
        }
        if (!handleInstanceState(bundle)) {
            preHandleIntent(this.mPendingHandleIntent);
        } else if (this.mWorkingNoteFromRestore != null && this.mWorkingNoteFromRestore.getNoteId() == -2) {
            this.mSavedThemeId = this.mWorkingNoteFromRestore.getThemeId();
            Logger.INSTANCE.d(TAG, "Saved themeID from restoredNote: " + this.mSavedThemeId);
        }
        if (getActivity() != null && this.mWorkingNoteFromRestore != null && TextUtils.isEmpty(this.mWorkingNoteFromRestore.getContent()) && TextUtils.isEmpty(this.mWorkingNoteFromRestore.getTitle()) && !this.mNewDoodle && (intent = getActivity().getIntent()) != null && "android.intent.action.INSERT_OR_EDIT".equals(intent.getAction())) {
            this.mIsEditNotes = true;
            this.mPendingHandleIntent = intent;
            preHandleIntent(this.mPendingHandleIntent);
        }
        initKeyboardHelper();
        if (this.mEditorWebView != null) {
            this.mEditorWebView.setIsPcMode(false);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initView(View view) {
        super.initView(view);
        smallWindowAdapter(this.mBgContentContainer);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void sendAsImage() {
        if (isWorkingNoteEmpty()) {
            Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
        } else {
            super.sendAsImage();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void sendAsDuplicate() {
        if (isWorkingNoteEmpty()) {
            Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
        } else {
            super.sendAsDuplicate();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void onEditMode(boolean z) {
        super.onEditMode(z);
        Logger.INSTANCE.e(TAG, "onEditMode,showRichTextTool:" + z);
        notifyEditUiState(1);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (isInEditAction()) {
            notifyEditUiState(1);
        }
        if (shouldTriggerEditMode()) {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onResume$1();
                }
            });
        }
        if (this.mNeedHideEditToolBar) {
            this.mEditToolbarController.hide(false);
            this.mNeedHideEditToolBar = false;
        }
        if (!this.mPagePrepare || this.mEditor == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "onResume isShowSelector");
        this.mEditor.isShowSelector("#fake-root", new Function1<Boolean, Unit>() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment.2
            @Override // kotlin.jvm.functions.Function1
            public Unit invoke(Boolean bool) {
                Logger.INSTANCE.d(PhoneHybridNoteEditFragment.TAG, "onResume isShowSelector-result = " + bool);
                if (!bool.booleanValue()) {
                    return null;
                }
                PhoneHybridNoteEditFragment.this.mIsEditNotes = true;
                if (PhoneHybridNoteEditFragment.this.mWorkingNote == null) {
                    return null;
                }
                PhoneHybridNoteEditFragment.this.updateEditor(true);
                return null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$1() {
        onFirstEditMode(false);
        if (this.mNeedVisible) {
            this.mActionBarController.setEditableQuick();
        }
        this.mNeedVisible = true;
    }

    private boolean shouldTriggerEditMode() {
        boolean z = (getActivity() == null || getActivity().getIntent() == null || !NoteIntent.TYPE_ACTION_SHOW_AI_TEXT_WIDGET.equals(getActivity().getIntent().getType())) ? false : true;
        Logger.INSTANCE.d(TAG, "onResume isShowAiTextWidgetFromMiShow = " + z);
        return this.mEnableEdit && this.mIsEditNotes && getMIsKeyboardVisible() && this.mHandler != null && !z;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mSharePopupWindow != null && this.mSharePopupWindow.isShowing()) {
            this.mSharePopupWindow.dismiss();
        }
        if (this.mIsRecording || !this.mEditToolbarController.ismIsShowThemeChooser()) {
            return;
        }
        this.mEditToolbarController.collapsePanel(null, false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        if (this.mHandler != null) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onMultiWindowModeChanged$2();
                }
            }, 50L);
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onMultiWindowModeChanged$3();
                }
            }, 10L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMultiWindowModeChanged$2() {
        if (this.mEditorWebView == null || !this.mEditToolbarController.ismToolbarIsShow()) {
            return;
        }
        Utils.showSoftInput(this.mEditorWebView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMultiWindowModeChanged$3() {
        updateWindowStatus(this.mIsForceDark);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Logger.INSTANCE.e(TAG, "onPause");
        notifyEditUiState(2);
        if (this.mIsRecording) {
            if (!isSupportAIRecorder()) {
                stopRecord();
            }
        } else if (UIUtils.isAboveAndroidR() && getMIsKeyboardVisible() && this.mCanUpdateViewBottom) {
            Utils.hideSoftInput(this.mEditorWebView);
        }
        this.mIsEditNotes = false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        Logger.INSTANCE.e(TAG, "onStop");
        if (this.mIsRecording) {
            if (isSupportAIRecorder()) {
                stopAIRecord();
            } else {
                stopRecord();
            }
        }
        dismissDialog();
        super.onStop();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        Logger.INSTANCE.i(TAG, "onDestroyView");
        KeyboardHelper keyboardHelper = this.mEditKeyboardHelper;
        if (keyboardHelper != null) {
            keyboardHelper.release();
        }
        super.onDestroyView();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Logger.INSTANCE.e(TAG, "onDestroy");
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
        super.onDestroy();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.ui.BaseActionBarController.EditCallback
    public void onPerformAction(int i, Object... objArr) {
        if (i == 25) {
            this.mIsReport = false;
            NoteContentStat.IS_BTN_BACK = true;
            NoteContentStat.IS_NOTE_BTN_BACK = true;
            removeAiFloatingWindow();
            if (this.mWorkingNote != null && this.mWorkingNote.isEmpty()) {
                this.mCanUpdateViewBottom = false;
                return;
            }
            if (!isAdded() || getActivity() == null) {
                return;
            }
            if (getMIsKeyboardVisible()) {
                Utils.hideSoftInput(getActivity());
            }
            getActivity().getWindow().clearFlags(524288);
            onNavigationUp();
            return;
        }
        super.onPerformAction(i, objArr);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        if (!this.mCanUpdateViewBottom && this.mEditorWebView != null) {
            this.mEditorWebView.clearFocus();
            return false;
        }
        if (dismissAllDialogOrPanel()) {
            return true;
        }
        boolean zOnBackPressed = this.mEditToolbarController.onBackPressed();
        if (this.mActionMode == EditorActionMode.EDIT_RECODE || this.mIsRecording) {
            if (isSupportAIRecorder()) {
                stopAIRecord();
            } else {
                stopRecord();
            }
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
        if (!zOnBackPressed && !FloatWindowManagerAgent.isContinuousExcerpt()) {
            this.mIsKillSaveTaskBeforeDestroy = true;
            killSaveTask();
        }
        return zOnBackPressed;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, com.miui.common.tool.softinputhelper.KeyboardListener
    public void onKeyboardVisibleChanged(boolean z) {
        super.onKeyboardVisibleChanged(z);
        Logger.INSTANCE.d(TAG, "onKeyboardVisibleChanged " + z);
        this.mEditor.onKeyboardVisibilityChanged(z);
        if (z) {
            if (!this.mEditorWebView.isFocused() || this.mIsRecording) {
                return;
            }
            this.mEditToolbarController.collapsePanel(null, false);
            return;
        }
        if (this.mIsRecording) {
            return;
        }
        this.mEditorWebView.clearFocus();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setWorkingNote(WorkingNote workingNote, boolean z) {
        int i;
        Logger.INSTANCE.d(TAG, "setWorkingNote");
        int themeId = workingNote.getThemeId();
        if (themeId == 0 && (i = this.mSavedThemeId) != 0) {
            Logger.INSTANCE.d(TAG, "update saved themeID: " + i);
            workingNote.setThemeId(i);
            themeId = i;
        }
        super.setWorkingNote(workingNote, z);
        updateEditorTheme(ResourceManager.getTheme(themeId), false);
        trackReportOpenContent(this.mPendingHandleIntent, workingNote);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateEditor(boolean z) {
        Logger.INSTANCE.d(TAG, "updateEditor,forceEdit" + z);
        this.mNoteRichEditor.setDataId(this.mWorkingNote.getNoteId());
        this.mNoteRichEditor.setFolderId(this.mWorkingNote.getFolderId());
        this.mNoteRichEditor.updateFontSizeId(PreferenceUtils.getFontSize(getActivity(), 1));
        String content = this.mWorkingNote.getContent();
        String title = this.mWorkingNote.getTitle();
        if (this.mWorkingNote.getThemeId() != 0 && this.mIsFirstInflate && this.mHandler != null) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateEditor$4();
                }
            }, 300L);
            checkEditEnable();
        } else if (this.mIsFromAiAction) {
            this.mNoteRichEditor.pasteMarkdown(content, title);
        } else {
            this.mNoteRichEditor.setData(content, title, this.mSearchText, Boolean.valueOf(this.mIsNeedSetData), this.mHighLightKeyWords);
        }
        this.mIsFirstInflate = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEditor$4() {
        String content = this.mWorkingNote.getContent();
        String title = this.mWorkingNote.getTitle();
        if (this.mIsFromAiAction) {
            this.mNoteRichEditor.pasteMarkdown(content, title);
        } else {
            this.mNoteRichEditor.setData(content, title, this.mSearchText, Boolean.valueOf(this.mIsNeedSetData), this.mHighLightKeyWords);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public void onPagePrepared() {
        Logger.INSTANCE.d(TAG, "onPagePrepared " + this.mIsEditNotes + " mPagePrepare: " + this.mPagePrepare);
        this.mPagePrepare = true;
        getActivity().runOnUiThread(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onPagePrepared$5();
            }
        });
        this.mEditorWebView.setConsumeTouch(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPagePrepared$5() {
        if (this.mIsEditNotes) {
            Logger.INSTANCE.d(TAG, "onPagePrepared,handleNewNotes..");
            setWorkingNote(handleNewNotes(this.mPendingHandleIntent), true);
            onChangeFontStyle();
            if (this.mPendingHandleIntent != null && (NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE.equals(this.mPendingHandleIntent.getType()) || this.mPendingHandleIntent.hasExtra(Notes.Intents.INTENT_EXTRA_CALL_DATE) || NoteIntent.TYPE_ACTION_SHOW_AI_TEXT_WIDGET.equals(this.mPendingHandleIntent.getType()))) {
                handleIntentData(this.mPendingHandleIntent);
            }
            saveDoodle();
            if (this.mPendingHandleIntent != null && NoteIntent.ACTION_CREATE_NOTE.equals(this.mPendingHandleIntent.getType())) {
                this.mEditor.setTitle(this.mPendingHandleIntent.getStringExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE));
                AiActionUtil.asyncNotifyResult(0);
            }
        } else {
            if (this.mWorkingNoteFromRestore != null) {
                if (TempAudio.ID == this.mWorkingNoteFromRestore.getNoteId() && TempAudio.AUDIO_NAME != null && TempAudio.CONTENT != null && TempAudio.CONTENT.replace(AudioUtils.TEMP_AUDIO_FLAG, StringUtils.SPACE).equals(this.mWorkingNoteFromRestore.getContent())) {
                    this.mWorkingNoteFromRestore.setWorkingText(TempAudio.CONTENT.replace("fileid=\"\"", "fileid=\"" + TempAudio.AUDIO_NAME + "\""));
                    this.mWorkingNoteFromRestore.saveNote(true);
                }
                setWorkingNote(this.mWorkingNoteFromRestore, false);
                this.mWorkingNoteFromRestore = null;
            } else if (getActivity() != null && this.mEditorWebView != null) {
                this.mEditorWebView.setFocusable(true);
                this.mPendingHandleIntent = getActivity().getIntent();
                handleIntentData(this.mPendingHandleIntent);
                onChangeFontStyle();
            }
            saveDoodle();
        }
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (UIUtils.isBrowseMode(getActivity())) {
            setEnableEdit(false);
            return;
        }
        activity.getWindow().clearFlags(131072);
        Logger.INSTANCE.d(TAG, "onPagePrepared,setEnableEdit true");
        setEnableEdit(true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initToolbarController(View view) {
        Logger.INSTANCE.d(TAG, "initToolbarController");
        this.mEditToolbarController = createToolBarController(view.findViewById(R.id.editor_mode_tool_group));
        this.mEditToolbarController.setOnThemeChosenListener(new ThemeChooserGroup.OnThemeChosenListener() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda4
            @Override // com.miui.notes.component.ThemeChooserGroup.OnThemeChosenListener
            public final void onThemeChosen(int i) {
                this.f$0.lambda$initToolbarController$6(i);
            }
        });
        this.mEditToolbarController.setExternalClickListener(this.mToolbarClickListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initToolbarController$6(int i) {
        if (this.mWorkingNote != null) {
            this.mWorkingNote.setThemeId(i);
            this.mSavedThemeId = i;
            Logger.INSTANCE.d(TAG, "Theme chosen and saved, themeID: " + i);
        }
    }

    protected void initKeyboardHelper() {
        Logger.INSTANCE.d(TAG, "initKeyboardHelper");
        this.mIsInFullScreenMode = UIUtils.isInFullWindowGestureMode(getActivity());
        this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
        this.mEditToolbarController.updateLayout();
        KeyboardHelper keyboardHelper = new KeyboardHelper(this.mContentView, getActivity().getWindow().getDecorView(), new KeyboardPaddingProcessor() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment.3
            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor, com.miui.common.tool.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChanged(View view, WindowInsets windowInsets, int i, int i2) {
                super.onKeyboardHeightChanged(view, windowInsets, i, i2);
            }

            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor, com.miui.common.tool.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChangedEnd(View view, WindowInsets windowInsets, int i) {
                super.onKeyboardHeightChangedEnd(view, windowInsets, i);
            }

            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor
            protected void updateViewPaddingBottom(View view, WindowInsets windowInsets, int i) {
                boolean showKeyboard;
                if (PhoneHybridNoteEditFragment.this.isAdded()) {
                    FragmentActivity activity = PhoneHybridNoteEditFragment.this.getActivity();
                    if (activity != null && activity.isInMultiWindowMode()) {
                        if (UIUtils.isAboveAndroidR() && PhoneHybridNoteEditFragment.this.getMIsKeyboardVisible() != (showKeyboard = KeyboardHelper.INSTANCE.getShowKeyboard())) {
                            PhoneHybridNoteEditFragment.this.onKeyboardVisibleChanged(showKeyboard);
                        }
                    } else if (UIUtils.isAboveAndroidR()) {
                        boolean showKeyboard2 = KeyboardHelper.INSTANCE.getShowKeyboard();
                        if (PhoneHybridNoteEditFragment.this.getMIsKeyboardVisible() != showKeyboard2) {
                            PhoneHybridNoteEditFragment.this.onKeyboardVisibleChanged(showKeyboard2);
                        }
                    } else if (i <= 0) {
                        if (PhoneHybridNoteEditFragment.this.getMIsKeyboardVisible()) {
                            PhoneHybridNoteEditFragment.this.onKeyboardVisibleChanged(false);
                        }
                    } else if (!PhoneHybridNoteEditFragment.this.getMIsKeyboardVisible()) {
                        PhoneHybridNoteEditFragment.this.onKeyboardVisibleChanged(true);
                    }
                    if (PhoneHybridNoteEditFragment.this.mCanUpdateViewBottom) {
                        super.updateViewPaddingBottom(view, windowInsets, i);
                    }
                }
            }
        }, true);
        this.mEditKeyboardHelper = keyboardHelper;
        keyboardHelper.setForceMode(false);
        Logger.INSTANCE.d(TAG, "initKeyboardHelper,finished");
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void doneEdit() {
        Logger.INSTANCE.i(TAG, "doneEdit, mIsRecording: " + this.mIsRecording);
        if (this.mIsRecording) {
            if (isSupportAIRecorder()) {
                stopAIRecord();
            } else {
                stopRecord();
            }
        }
        super.doneEdit();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public boolean isNoteCreated() {
        return this.mIsEditNotes;
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
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateRecordMode() {
        super.updateRecordMode();
        dismissAllDialogOrPanel();
        startRecord();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void closeEditActivity() {
        updateActionMode(EditorActionMode.VIEW, false);
        super.closeEditActivity();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onLoadDataFailed() {
        super.onLoadDataFailed();
        if (this.mHandler != null) {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onLoadDataFailed$7();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLoadDataFailed$7() {
        if (isAdded()) {
            Logger.INSTANCE.i(TAG, "Fail to load note, Back!");
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.onBackPressed();
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleIntentData(Intent intent) {
        boolean zHandleIntentData = super.handleIntentData(intent);
        this.mEditToolbarController.setNeedDelayShowInput(this.mNeedDelayShowInput);
        this.mNeedDelayShowInput = false;
        return zHandleIntentData;
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

    private void startRecord() {
        Logger.INSTANCE.d(TAG, "startRecord");
        this.mEditToolbarController.onEditContentMode(false, false, false);
        this.mEditToolbarController.toggleAudioRecordPanel();
        startRecordTask();
        this.mIsRecording = true;
        Utils.hideSoftInput(this.mEditorWebView);
    }

    private void stopRecord() {
        Logger.INSTANCE.d(TAG, "stopRecord");
        this.mEditToolbarController.toggleAudioRecordPanel();
        this.mEditToolbarController.onViewMode(true);
        this.mIsRecording = false;
        stopRecordTask();
        this.mEditToolbarController.resetRichTextStatus();
        updateActionMode(EditorActionMode.VIEW, true);
    }

    private void startRecordTask() {
        PhoneHybridNoteEditFragment phoneHybridNoteEditFragment;
        Logger.INSTANCE.d(TAG, "startRecordTask");
        if (DualScreenManager.INSTANCE.isPcActivityLiving() && this.mHandler != null && this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        if (this.mRecordAiTask == null) {
            this.mRecordAiTask = new SpeechRecognitionTaskImp(NoteApp.getInstance(), AudioUtils.NOTE_MP3_DIR_NAME, this);
        }
        this.mEditToolbarController.startAudioInput();
        if (this.mAudioRecordTimer == null) {
            phoneHybridNoteEditFragment = this;
            phoneHybridNoteEditFragment.mAudioRecordTimer = new CountDownTimer(MAX_RECORD_TIME, 1000L) { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment.4
                @Override // android.os.CountDownTimer
                public void onTick(long j) {
                    PhoneHybridNoteEditFragment.this.onAudioTimerTick(j);
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    PhoneHybridNoteEditFragment.this.onAudioTimerFinish();
                }
            };
        } else {
            phoneHybridNoteEditFragment = this;
        }
        phoneHybridNoteEditFragment.mAudioRecordTimer.start();
        if (phoneHybridNoteEditFragment.mNetworkCallback == null) {
            phoneHybridNoteEditFragment.mNetworkCallback = new NetworkCallbackImpl(new NetworkCallbackImpl.NetworkCall() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment.5
                @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
                public void onAvailable() {
                    if (PhoneHybridNoteEditFragment.this.mRecordAiTask != null) {
                        PhoneHybridNoteEditFragment.this.mRecordAiTask.setNetWorkValid(true);
                        PhoneHybridNoteEditFragment.this.mRecordAiTask.startEngine();
                    }
                }

                @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
                public void onLost() {
                    if (PhoneHybridNoteEditFragment.this.mRecordAiTask != null) {
                        PhoneHybridNoteEditFragment.this.mRecordAiTask.setNetWorkValid(false);
                        PhoneHybridNoteEditFragment.this.mRecordAiTask.stopEngine();
                        Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getResources().getString(R.string.audio_toast_network_is_not_available), 1).show();
                    }
                }
            });
        }
        phoneHybridNoteEditFragment.mNetworkCallback.initNetWorkState();
        boolean isNetWorkAvailable = phoneHybridNoteEditFragment.mNetworkCallback.getIsNetWorkAvailable();
        Logger.INSTANCE.d(TAG, "isNetworkAvailable " + isNetWorkAvailable);
        if (!phoneHybridNoteEditFragment.mRecordAiTask.isSkipSpeechRecognition()) {
            phoneHybridNoteEditFragment.registerNetWorkListener();
            if (!isNetWorkAvailable) {
                Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getResources().getString(R.string.audio_toast_network_is_not_available), 1).show();
            }
        }
        phoneHybridNoteEditFragment.getActivity().getWindow().addFlags(128);
        try {
            phoneHybridNoteEditFragment.mRecordAiTask.setNetWorkValid(isNetWorkAvailable);
            phoneHybridNoteEditFragment.mRecordAiTask.start();
            phoneHybridNoteEditFragment.mIsWarned = false;
        } catch (MibrainException unused) {
            Logger.INSTANCE.e(TAG, "Fail to start record task!");
        }
        phoneHybridNoteEditFragment.asyncSaveNote();
        FloatWindowManagerAgent.setRecordState(true);
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

    private void stopRecordTask() {
        Logger.INSTANCE.i(TAG, "stopRecordTask");
        FloatWindowManagerAgent.setRecordState(false);
        SpeechRecognitionTask speechRecognitionTask = this.mRecordAiTask;
        if (speechRecognitionTask != null) {
            speechRecognitionTask.shutdownNow();
            if (!this.mRecordAiTask.isSkipSpeechRecognition()) {
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void checkEditEnable() {
        Logger.INSTANCE.d(TAG, "checkEditEnable");
        if (UIUtils.isBrowseMode(getActivity())) {
            if (this.mHandler != null && !this.mHandler.hasMessages(8)) {
                this.mHandler.removeMessages(8);
                this.mHandler.sendEmptyMessageDelayed(8, 400L);
            }
            Logger.INSTANCE.d(TAG, "checkEditEnable,setEdit false");
            setEnableEdit(false);
            return;
        }
        if (this.mHandler != null) {
            this.mHandler.removeMessages(8);
        }
        if (getActivity() != null) {
            getActivity().getWindow().clearFlags(131072);
        }
        Logger.INSTANCE.d(TAG, "checkEditEnable,setEdit true");
        setEnableEdit(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAudioTimerFinish() {
        if (this.mEditorMaskView != null) {
            this.mEditorMaskView.removeAllViews();
        }
        if (this.mIsRecording) {
            if (isSupportAIRecorder()) {
                stopAIRecord();
            } else {
                stopRecord();
            }
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
        Logger.INSTANCE.i(TAG, "onAsrSaveAudioFileName");
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
        Logger.INSTANCE.i(TAG, "onReceiveTaskResult");
        if (this.mEditor != null && this.mEditor.getMDataId() == TempAudio.ID && this.mEditor.getRichTextData() != null && this.mEditor.getRichTextData().toString().equals(TempAudio.CONTENT)) {
            this.mEditor.insertAudioEnd(str);
            saveNote(true);
            if (this.mWorkingNote != null) {
                this.mWorkingNote.setWorkingText(TempAudio.CONTENT.replace("fileid=\"\"", "fileid=\"" + str + "\""));
                this.mWorkingNote.saveNote(true);
                if (this.mIsStop) {
                    updateEditor(true);
                    return;
                }
                return;
            }
            return;
        }
        TempAudio.AUDIO_NAME = str;
        if (this.mWorkingNote == null || this.mEditor == null || this.mEditor.getMDataId() != TempAudio.ID || this.mEditor.getRichTextData() == null || !TempAudio.CONTENT.replace(AudioUtils.TEMP_AUDIO_FLAG, StringUtils.SPACE).equals(this.mEditor.getRichTextData().toString())) {
            return;
        }
        this.mWorkingNote.setWorkingText(TempAudio.CONTENT.replace("fileid=\"\"", "fileid=\"" + str + "\""));
        this.mWorkingNote.saveNote(true);
        updateEditor(true);
    }

    protected void notifyAudioFileSaved(String str) {
        onReceiveTaskResult(str);
        asyncSaveNote();
    }

    private void registerNetWorkListener() {
        if (this.mConnMgr == null) {
            this.mConnMgr = (ConnectivityManager) NoteApp.getInstance().getSystemService("connectivity");
        }
        ConnectivityManager connectivityManager = this.mConnMgr;
        if (connectivityManager == null || this.mNetworkCallback == null) {
            return;
        }
        connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(), this.mNetworkCallback);
    }

    private void unRegisterNetWorkListener() {
        NetworkCallbackImpl networkCallbackImpl;
        ConnectivityManager connectivityManager = this.mConnMgr;
        if (connectivityManager == null || (networkCallbackImpl = this.mNetworkCallback) == null) {
            return;
        }
        connectivityManager.unregisterNetworkCallback(networkCallbackImpl);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showDualScreenEditTipDialog() {
        if (isAdded()) {
            if (this.mDualScreenEditTipDialog == null || !this.mDualScreenEditTipDialog.isShowing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle(getString(R.string.note_dual_screen_dialog_title));
                builder.setMessage(getString(R.string.note_dual_screen_dialog_content));
                builder.setNegativeButton(getString(R.string.dialog_button_continue_work), new DialogInterface.OnClickListener() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        PhoneHybridNoteEditFragment.lambda$showDualScreenEditTipDialog$8(dialogInterface, i);
                    }
                });
                builder.setPositiveButton(getString(R.string.dialog_button_close), new DialogInterface.OnClickListener() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.lambda$showDualScreenEditTipDialog$9(dialogInterface, i);
                    }
                });
                this.mDualScreenEditTipDialog = builder.create();
                this.mDualScreenEditTipDialog.show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDualScreenEditTipDialog$9(DialogInterface dialogInterface, int i) {
        notifyEditUiState(3);
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

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void setWindowsOnFocus() {
        if (this.mActionMode != EditorActionMode.EDIT || getMIsKeyboardVisible() || this.mIsRecording) {
            return;
        }
        if (this.mEditorWebView != null) {
            this.mEditorWebView.post(new Runnable() { // from class: com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setWindowsOnFocus$10();
                }
            });
        } else {
            Logger.INSTANCE.w(TAG, "mEditorWebView is null, cannot show keyboard");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setWindowsOnFocus$10() {
        Utils.showKeyboardWithRestartInput(this.mEditorWebView);
    }
}
