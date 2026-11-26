package com.miui.pc.feature.notes;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.provider.FontsContractCompat;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.constant.FrameConstant;
import com.miui.common.record.SpeechRecognitionTask;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.async.TaskManager;
import com.miui.common.view.IPopMenu;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.base.DoodleParams;
import com.miui.doodle.document.DocumentReader;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.hybirdeditor.RichEditorWebView;
import com.miui.hybirdeditor.WebRichEditor;
import com.miui.hybirdeditor.jsbridge.EmbedElementInfoBean;
import com.miui.hybirdeditor.jsbridge.HybridEditorScriptInterface;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.basefeature.noteedit.ColumnsHybridNoteEditFragment;
import com.miui.notes.basefeature.noteedit.RichTextFloatMenuHelper;
import com.miui.notes.basefeature.noteedit.RichTextFloatMenuInfo;
import com.miui.notes.component.noteedit.IBaseNoteEditToolbarController;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.store.NoteStore;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.BaseActionBarController;
import com.miui.notes.ui.BaseDragListener;
import com.miui.notes.ui.IFragmentController;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.notes.widget.NoteWidgetProvider;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.xiaomi.mirror.synergy.MiuiSynergySdk;
import java.io.File;
import java.util.ArrayList;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.nestedheader.widget.NestedHeaderLayout;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes4.dex */
public class PcHybridNoteEditFragment extends ColumnsHybridNoteEditFragment implements View.OnClickListener {
    private static final String TAG = "PcHybridNoteEditFragment";
    private IFragmentController iFragmentController;
    private View mBlankView;
    private View mContentView;
    private ViewGroup mEditorContainer;
    protected LinearLayout mEditorMaskView;

    @Deprecated
    private FolderChoiceMenuController mFolderMenuController;
    private SpeechRecognitionTask mRecordTask;
    private View mRemindContainer;
    protected boolean mIsNoteCreated = false;
    private int mRequestFocusChildAdd = 0;
    private boolean isImageClicked = false;
    private String clickedImagePath = null;
    private BlankViewDragListener mBlankViewDragListener = new BlankViewDragListener(0);
    private BaseDragListener mBlockDragListener = new BaseDragListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.1
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
    private PcLockScreenDialog mShowingLockScreenDialog = null;
    private BroadcastReceiver mPhotoResultReceiver = new BroadcastReceiver() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int i = intent.getExtras().getInt("request_code", 0);
            int i2 = intent.getExtras().getInt(FontsContractCompat.Columns.RESULT_CODE, 0);
            if (PcHybridNoteEditFragment.this.getContext() == null) {
                Logger.INSTANCE.w(PcHybridNoteEditFragment.TAG, "mPhotoResultReceiver getContext is null!!");
                return;
            }
            if (i2 == -1) {
                if (i == 1) {
                    String scrapPath = NotesProvider.getScrapPath(PcHybridNoteEditFragment.this.getContext());
                    if (scrapPath == null) {
                        return;
                    }
                    PcHybridNoteEditFragment.this.insertImages(false, Uri.fromFile(new File(scrapPath)));
                } else if (i == 2) {
                    ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("pick-result-data");
                    if (parcelableArrayListExtra == null) {
                        PcHybridNoteEditFragment.this.insertImages(false, intent.getData());
                    } else {
                        int size = parcelableArrayListExtra.size();
                        Uri[] uriArr = new Uri[size];
                        parcelableArrayListExtra.toArray(uriArr);
                        for (int i3 = 0; i3 < size; i3++) {
                            PcHybridNoteEditFragment.this.getContext().grantUriPermission(PcHybridNoteEditFragment.this.getContext().getPackageName(), uriArr[i3], 1);
                        }
                        PcHybridNoteEditFragment.this.insertImages(false, uriArr);
                    }
                } else if (i == 32) {
                    String stringExtra = intent.getStringExtra(MainScreenBridgeActivity.KEY_DOODLE_RESULT);
                    if (!TextUtils.isEmpty(stringExtra)) {
                        PcHybridNoteEditFragment.this.insertDoodle(stringExtra);
                    }
                } else if (i == 64) {
                    String stringExtra2 = intent.getStringExtra(MainScreenBridgeActivity.KEY_DOODLE_RESULT);
                    if (!TextUtils.isEmpty(stringExtra2)) {
                        if (!DoodleApplication.SaveFlagNoChange.contentEquals(stringExtra2)) {
                            PcHybridNoteEditFragment pcHybridNoteEditFragment = PcHybridNoteEditFragment.this;
                            pcHybridNoteEditFragment.updateDoodle(stringExtra2, pcHybridNoteEditFragment.mDoodleFileId);
                        }
                    } else {
                        PcHybridNoteEditFragment pcHybridNoteEditFragment2 = PcHybridNoteEditFragment.this;
                        pcHybridNoteEditFragment2.updateDoodle(null, pcHybridNoteEditFragment2.mDoodleFileId);
                    }
                }
            }
            if (PcHybridNoteEditFragment.this.mShowingLockScreenDialog != null) {
                PcHybridNoteEditFragment.this.mShowingLockScreenDialog.dismiss();
            }
            if (PcHybridNoteEditFragment.this.mLocalBroadcastManager != null) {
                PcHybridNoteEditFragment.this.mLocalBroadcastManager.unregisterReceiver(this);
            }
        }
    };
    private PcLockScreenDialog mDualScreenEditTipDialog = null;

    private boolean isSupportTheme() {
        return false;
    }

    private void pasteAsPlainText() {
    }

    private void selectAll() {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void checkEditEnable() {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected BaseActionBarController createHeaderBarController(View view) {
        return null;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getAutoSaveCommonDuration() {
        return 1500;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public int getDeviceFlag() {
        return 2;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getLayoutResId() {
        return R.layout.hybrid_pc_note_edit;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getThemeResId() {
        return R.style.NoteTheme_Edit_PcHybrid;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initActionBar() {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public boolean isDarkMode() {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.ui.BaseActionBarController.EditCallback
    public boolean isOnSaveInstanceCalled() {
        return false;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    /* JADX INFO: renamed from: isSupportNavigator */
    protected boolean getIsSupportNavigator() {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.RecordTaskWorker
    public boolean isSupportRecordTaskWorker() {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
    }

    @Override // com.miui.notes.basefeature.noteedit.RecordTaskWorker
    public void onReceiveTaskResult(String str) {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.model.WorkingNote.NoteSettingChangedListener
    public void onWidgetChanged(int i, int i2) {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void handleDualScreenStateChanged(Intent intent) {
        int i = intent.getExtras().getInt(FrameConstant.PARAM_SCREEN_ID, 0);
        int i2 = intent.getExtras().getInt(FrameConstant.PARAM_SCREEN_MODE, 0);
        if (i != 1) {
            if (i2 != 0) {
                if (i2 == 1) {
                    showDualScreenEditTipDialog();
                    return;
                } else if (i2 != 2) {
                    if (i2 == 3 && getActivity() != null) {
                        getActivity().finish();
                        return;
                    }
                    return;
                }
            }
            PcLockScreenDialog pcLockScreenDialog = this.mDualScreenEditTipDialog;
            if (pcLockScreenDialog != null) {
                pcLockScreenDialog.dismiss();
                this.mDualScreenEditTipDialog = null;
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.mWorkingNote == null || this.mWorkingNote.getIsInvalid()) {
            return;
        }
        switch (view.getId()) {
            case R.id.audio /* 2131362036 */:
                if (!this.mIsRecording) {
                    Rect rect = new Rect();
                    if (!this.mEditor.isCursorVisible()) {
                        this.mEditor.setCursorVisible(true);
                        this.mEditor.requestViewFocus();
                    }
                    int paddingBottom = getView().findViewById(R.id.edit_content).getPaddingBottom() + this.mRequestFocusChildAdd;
                    rect.top += paddingBottom;
                    rect.bottom += paddingBottom;
                    this.mEditorWebView.requestChildRectangleOnScreen(this.mEditorContainer, rect, false);
                }
                break;
            case R.id.bg_highlight /* 2131362065 */:
            case R.id.bold /* 2131362073 */:
            case R.id.bullet /* 2131362117 */:
            case R.id.center /* 2131362149 */:
            case R.id.check /* 2131362157 */:
            case R.id.italic /* 2131362763 */:
            case R.id.right /* 2131363305 */:
            case R.id.size_group /* 2131363467 */:
            case R.id.underline /* 2131363734 */:
                asyncSaveNote();
                break;
            case R.id.gallery /* 2131362611 */:
                showPictureChooserDialog(view);
                break;
            case R.id.toolbar /* 2131363663 */:
                dismissAllPopMenu();
                break;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected HybridEditorScriptInterface createJsBridge() {
        return new HybridEditorScriptInterface(this, 0);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected IBaseNoteEditToolbarController createToolBarController(View view) {
        return new PcActionToolBarController(view.findViewById(R.id.toolbar), this.mEditor);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected INoteInfoHeader createNoteExtraInfoController(View view, View view2) {
        return new PcNoteHeadInfoController(view);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initPasswordController() {
        this.mPasswordController = new PasswordController(this, true);
        this.mPasswordController.setLockSateListener(this.mLockStateListener);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logger.INSTANCE.d(TAG, "onCreate");
        this.mIsInPcNote = true;
        this.mRequestFocusChildAdd = getContext().getResources().getDimensionPixelSize(R.dimen.notes_edit_request_child_focus_add);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Logger.INSTANCE.d(TAG, "onViewCreated");
        if (isSupportTheme() && this.mCurrentTheme != null && (this.mCurrentTheme instanceof AnimTheme)) {
            ((AnimTheme) this.mCurrentTheme).resume();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public boolean isNoteCreated() {
        return this.mIsNoteCreated;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public void onPagePrepared() {
        super.onPagePrepared();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        this.mHandler.removeMessages(3);
        asyncSaveNoteInstantlyIfNeeded();
        dismissAllDialogOrPanel();
        if (this.mCurrentTheme != null && (this.mCurrentTheme instanceof AnimTheme)) {
            ((AnimTheme) this.mCurrentTheme).pause();
        }
        super.onPause();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mPasswordController.lockDelay();
        updateActionMode(EditorActionMode.VIEW, false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        ((WebRichEditor) this.mNoteRichEditor).hidePopMenu();
        dismissAllPopMenu();
        PermissionUtils.releaseListeners();
        super.onDestroyView();
        ResourceManager.clearThemes();
        if (this.mLoadTask != null) {
            this.mLoadTask.cancel();
            this.mLoadTask = null;
        }
        if (this.mCurrentTheme instanceof AnimTheme) {
            onAnimThemeBgViewChanged(null);
        }
        this.mWorkingNote = null;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Intent intent;
        FragmentActivity activity = getActivity();
        if (activity != null && (intent = activity.getIntent()) != null) {
            intent.setAction("android.intent.action.MAIN");
            activity.setIntent(intent);
        }
        if (this.mWorkingNote != null) {
            this.mWorkingNote.removeOnSettingStatusChangedListener(this);
        }
        onBackPressed();
        if (!this.mIsKillSaveTaskBeforeDestroy) {
            killSaveTask();
        }
        this.mIsKillSaveTaskBeforeDestroy = false;
        IFragmentController iFragmentController = this.iFragmentController;
        if (iFragmentController != null) {
            iFragmentController.onDestroyEditFragment();
        }
        this.mLocalBroadcastManager = null;
        PcLockScreenDialog pcLockScreenDialog = this.mDualScreenEditTipDialog;
        if (pcLockScreenDialog != null) {
            pcLockScreenDialog.dismiss();
            this.mDualScreenEditTipDialog = null;
        }
        this.mPagePrepare = false;
        super.onDestroy();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initView(View view) {
        super.initView(view);
        view.setOnDragListener(this.mBlockDragListener);
        View viewFindViewById = view.findViewById(R.id.sv_note_edit);
        this.mContentView = viewFindViewById;
        viewFindViewById.setAlpha(0.0f);
        this.mContentView.setVisibility(8);
        this.mBgContentContainer = (SpringBackLayout) view.findViewById(R.id.content_container);
        this.mNestedHeaderLayout = (NestedHeaderLayout) view.findViewById(R.id.nested_header_layout);
        this.mBlankView = view.findViewById(R.id.blank_container);
        this.mBlankViewDragListener.setEditFragment(this);
        this.mBlankView.setOnDragListener(this.mBlankViewDragListener);
        initReminder(view);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void updateNoteThemeOnNative(int i, boolean z, boolean z2) {
        super.updateNoteThemeOnNative(0, z, z2);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void initNoteEditor(View view, int i) {
        Logger.INSTANCE.d(TAG, "pc initNoteEditor: " + i);
        this.mIsPcMode = true;
        super.initNoteEditor(view, i);
        this.mEditor.setButtonStateListener(new WebRichEditor.ButtonStateListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.3
            @Override // com.miui.hybirdeditor.WebRichEditor.ButtonStateListener
            public void onUndoRedoStateChanged(boolean z, boolean z2) {
                if (PcHybridNoteEditFragment.this.getPcToolBarController() != null) {
                    PcHybridNoteEditFragment.this.getPcToolBarController().updateUndoState(z, z2);
                }
            }
        });
        this.mEditorWebView.setIsPcMode(true);
        this.mEditorWebView.setKeyShortCutListener(new OnKeyDownAndShortCutListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.4
            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyShortcut(int i2, KeyEvent keyEvent) {
                return false;
            }

            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyUp(int i2, KeyEvent keyEvent) {
                return false;
            }

            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyDown(int i2, KeyEvent keyEvent) {
                Logger.INSTANCE.d(PcHybridNoteEditFragment.TAG, "onKeyDown " + keyEvent);
                if (keyEvent.hasModifiers(4096)) {
                    if (i2 == 31) {
                        return PcHybridNoteEditFragment.this.mEditorWebView.doShortcutCopy();
                    }
                    if (i2 == 33) {
                        PcHybridNoteEditFragment.this.mEditor.setAlignCenter();
                        return true;
                    }
                    if (i2 == 46) {
                        PcHybridNoteEditFragment.this.mEditor.setAlignRight();
                        return true;
                    }
                    if (i2 == 50) {
                        if (PcHybridNoteEditFragment.this.mActionMode == EditorActionMode.EDIT_TITLE) {
                            return false;
                        }
                        return PcHybridNoteEditFragment.this.mEditorWebView.doPaste();
                    }
                    if (i2 == 35) {
                        PcHybridNoteEditFragment.this.mEditor.setEditorBullet();
                        return true;
                    }
                    if (i2 == 36) {
                        PcHybridNoteEditFragment.this.mEditor.setBgHighLight();
                        return true;
                    }
                    if (i2 == 39) {
                        PcHybridNoteEditFragment.this.mEditor.setCheckbox();
                        return true;
                    }
                    if (i2 == 40) {
                        PcHybridNoteEditFragment.this.mEditor.setAlignNormal();
                        return true;
                    }
                    if (i2 == 52) {
                        return PcHybridNoteEditFragment.this.mEditorWebView.doShortcutCut();
                    }
                    if (i2 == 53) {
                        PcHybridNoteEditFragment.this.mEditor.redo();
                        return true;
                    }
                    if (i2 == 71) {
                        PcHybridNoteEditFragment.this.mEditor.reduceFontSize();
                        return true;
                    }
                    if (i2 == 72) {
                        PcHybridNoteEditFragment.this.mEditor.increaseFontSize();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onContextItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.selectAll:
                selectAll();
                break;
            case android.R.id.cut:
                this.mEditorWebView.doCut();
                break;
            case android.R.id.copy:
                this.mEditorWebView.doCopy();
                break;
            case android.R.id.paste:
                if (this.mActionMode == EditorActionMode.EDIT_TITLE) {
                    return false;
                }
                this.mEditorWebView.doPaste();
                break;
            case android.R.id.pasteAsPlainText:
                pasteAsPlainText();
                break;
            case android.R.id.shareText:
                shareSelectedText();
                break;
            case android.R.id.autofill:
                autofill();
                break;
            case R.id.redo /* 2131363278 */:
                this.mEditor.redo();
                break;
            case R.id.undo /* 2131363735 */:
                this.mEditor.undo();
                break;
        }
        return super.onContextItemSelected(menuItem);
    }

    private void shareSelectedText() {
        String mSelectData = this.mEditorWebView.getMSelectData();
        if (mSelectData == null || mSelectData.isEmpty()) {
            return;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.removeExtra("android.intent.extra.TEXT");
        intent.putExtra("android.intent.extra.TEXT", mSelectData);
        getContext().startActivity(Intent.createChooser(intent, null));
    }

    private void autofill() {
        AutofillManager autofillManager = (AutofillManager) getContext().getSystemService(AutofillManager.class);
        if (autofillManager != null) {
            autofillManager.requestAutofill(this.mEditorWebView);
        }
        this.mEditorWebView.stopActionMode();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initNoteEditor(View view) {
        this.mEditorContainer = (ViewGroup) view.findViewById(R.id.editor_container);
        Logger.INSTANCE.d(TAG, "initNoteEditor");
        super.initNoteEditor(view);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initToolbarController(View view) {
        this.mEditToolbarController = createToolBarController(view);
        getPcToolBarController().setActionListener(this);
        this.mEditToolbarController.updateNavigationHeight(this.mNavigationBarHeight);
        this.mEditToolbarController.setOnThemeChosenListener(this.mThemeChosenListener);
        this.mEditToolbarController.setExternalClickListener(this);
        this.mEditorWebView.clearFocus();
    }

    private void initReminder(View view) {
        View viewFindViewById = view.findViewById(R.id.reminder);
        this.mRemindContainer = viewFindViewById;
        viewFindViewById.findViewById(R.id.restore).setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                NoteStore.restore(PcHybridNoteEditFragment.this.getContext(), new long[]{PcHybridNoteEditFragment.this.mWorkingNote.getNoteId()});
            }
        });
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public void onTextCountChanged(int i) {
        if (this.mWorkingNote != null) {
            this.mWorkingNote.getExtraInfo().textCount = i;
            this.mEditor.updateExtraInto(this.mWorkingNote.getExtraInfo());
            this.mNoteExtraInfoController.update(this.mWorkingNote, i + this.mWorkingNote.getTitle().length());
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        dismissAllDialogOrPanel();
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
        handleIntentData(intent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(NoteLoadDataEntity noteLoadDataEntity, boolean z) {
        Logger.INSTANCE.d(TAG, "refreshNote");
        if (noteLoadDataEntity != null) {
            try {
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
        if (this.mLoadTask != null) {
            this.mLoadTask.cancel();
            this.mLoadTask = null;
        }
        if (FloatWindowManagerAgent.isContinuousExcerpt() && isThisNoteDoExcerpt()) {
            FloatWindowManagerAgent.resumeContinuousExcerpt();
        }
        resetEditContent();
        onDataEmpty();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean loadNoteFromEntity(NoteLoadDataEntity noteLoadDataEntity) {
        Logger.INSTANCE.d(TAG, "loadNoteFromEntity");
        View view = this.mBlankView;
        if (view != null) {
            view.setAlpha(0.0f);
        }
        if (this.mExecutorService.isShutdown()) {
            this.mExecutorService = Utils.newDiscardPolicySingleThreadExecutor();
        }
        setEnableEdit(true);
        Intent intent = noteLoadDataEntity.toIntent();
        this.mFolderId = noteLoadDataEntity.getFolderId();
        if (this.mFolderId == -3) {
            this.mRemindContainer.setVisibility(0);
        } else {
            this.mRemindContainer.setVisibility(8);
        }
        this.mEditor.hidePopMenu();
        if (noteLoadDataEntity.getNoteId() == -2) {
            this.mSearchText = "";
            this.mActionMode = null;
            resetEditContent();
            this.mEditor.clearFocus();
            this.mEditToolbarController.resetRichTextStatus();
            intent.setAction("android.intent.action.EDIT");
            this.mPendingHandleIntent = intent;
            if (this.mLoadTask == null && this.mContentView.getAlpha() == 0.0f) {
                this.mContentView.setAlpha(1.0f);
                this.mContentView.setVisibility(0);
                this.mContentView.requestLayout();
            }
            return false;
        }
        intent.setAction("android.intent.action.VIEW");
        this.mPendingHandleIntent = intent;
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleIntentData(Intent intent) {
        if (intent == null) {
            Logger.INSTANCE.e(TAG, "handleIntentData intent is null");
            return true;
        }
        Logger.INSTANCE.d(TAG, "handleIntentData " + intent.getAction());
        String action = intent.getAction();
        if ("android.intent.action.VIEW".equals(action)) {
            this.mIsNoteCreated = false;
            long extraNoteIdFromIntent = getExtraNoteIdFromIntent(intent);
            long longExtra = intent.getLongExtra("com.miui.notes.folder_id", 0L);
            long longExtra2 = intent.getLongExtra("modified_time", Long.MIN_VALUE);
            String searchExtraDataKeyFromIntent = getSearchExtraDataKeyFromIntent(intent);
            this.mFolderId = longExtra;
            if (extraNoteIdFromIntent > 0) {
                boolean z = this.mWorkingNote == null || this.mWorkingNote.getNoteId() != extraNoteIdFromIntent;
                boolean z2 = (this.mWorkingNote == null || this.mWorkingNote.getFolderId() == longExtra || longExtra == -4097) ? false : true;
                if (z || z2) {
                    if (getEditToolBarController() != null) {
                        getEditToolBarController().hide(false);
                    }
                    if (getPcToolBarController() != null) {
                        getPcToolBarController().updateUndoState(false, false);
                    }
                    this.mSearchText = "";
                    this.mActionMode = null;
                    processLastWorkingNote(extraNoteIdFromIntent);
                    showBlankView(false);
                    if (z) {
                        resetEditContent();
                        Logger.INSTANCE.d(TAG, "ViewNote");
                        this.mLoadTask = new BaseNormalNoteEditFragment.ViewNoteLoadTask(intent, extraNoteIdFromIntent);
                        this.mLoadTask.commit();
                    }
                } else if ((this.mLoadTask == null || !(this.mLoadTask instanceof UpdateNoteLoadTask)) && this.mWorkingNote != null && (this.mWorkingNote.getModifiedDate() < longExtra2 || !TextUtils.equals(this.mSearchText, searchExtraDataKeyFromIntent))) {
                    if (this.mLoadTask != null) {
                        this.mLoadTask.cancel();
                    }
                    Logger.INSTANCE.d(TAG, "UpdateNote");
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
            this.mIsNoteCreated = true;
        }
        return super.handleIntentData(intent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleEditIntent(final Intent intent) {
        processLastWorkingNote(getExtraNoteIdFromIntent(intent));
        boolean booleanExtra = intent.getBooleanExtra(NoteIntent.KEY_IS_PENDING, false);
        this.mIsFromFloatWindow = intent.getBooleanExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, false);
        if (this.mIsFromFloatWindow) {
            this.mNeedDelayShowInput = true;
        }
        this.mPendingEditIntentTask = new Runnable() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.6
            @Override // java.lang.Runnable
            public void run() {
                PcHybridNoteEditFragment.this.showBlankView(false);
                PcHybridNoteEditFragment.this.mLoadTask = PcHybridNoteEditFragment.this.new PcHybridEditNoteLoadTask(intent);
                PcHybridNoteEditFragment.this.mLoadTask.commit();
            }
        };
        if (!booleanExtra) {
            this.mHandler.sendEmptyMessage(4);
            this.mEditorWebView.performClick();
        }
        this.mEditToolbarController.setNeedDelayShowInput(this.mNeedDelayShowInput);
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleSendIntent(Intent intent) {
        this.mLoadTask = new BaseNormalNoteEditFragment.SendNoteLoadTask(intent);
        this.mLoadTask.commit();
        updateActionMode(EditorActionMode.VIEW, true);
        return true;
    }

    private void callbackHandleIntent(Intent intent) {
        if ("android.intent.action.INSERT_OR_EDIT".equals(intent.getAction())) {
            handleEditIntent(intent);
        } else {
            handleSendIntent(intent);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onNetworkStateChanged(boolean z) {
        if (z) {
            SpeechRecognitionTask speechRecognitionTask = this.mRecordTask;
            if (speechRecognitionTask != null) {
                speechRecognitionTask.setNetWorkValid(true);
                this.mRecordTask.startEngine();
                return;
            }
            return;
        }
        SpeechRecognitionTask speechRecognitionTask2 = this.mRecordTask;
        if (speechRecognitionTask2 != null) {
            speechRecognitionTask2.setNetWorkValid(false);
            this.mRecordTask.stopEngine();
        }
        if (getActivity() != null) {
            Toast.makeText(getActivity(), R.string.audio_toast_network_is_not_available, 0).show();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void onNoteDeleted(long j) {
        if (isAdded()) {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_delete_success, 0).show();
        }
    }

    public PcActionToolBarController getPcToolBarController() {
        return (PcActionToolBarController) this.mEditToolbarController;
    }

    @Override // com.miui.notes.basefeature.noteedit.ColumnsHybridNoteEditFragment
    protected void showBlankView(boolean z) {
        if (isAdded()) {
            if (z) {
                Folme.useAt(this.mBlankView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
                Folme.useAt(this.mContentView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(new TransitionListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.7
                    @Override // miuix.animation.listener.TransitionListener
                    public void onCancel(Object obj) {
                        super.onCancel(obj);
                        PcHybridNoteEditFragment.this.mBlankView.setAlpha(0.0f);
                        PcHybridNoteEditFragment.this.mContentView.setVisibility(0);
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        PcHybridNoteEditFragment.this.mBlankView.setAlpha(1.0f);
                        PcHybridNoteEditFragment.this.mContentView.setVisibility(8);
                    }
                }));
                if (this.mBlankView != null) {
                    String string = getArguments().getString(NoteLoadDataEntity.SEARCH_TEXT, "");
                    if (this.mFolderId == -3 || this.mFolderId == -2 || this.mFolderId == -5 || !TextUtils.isEmpty(string)) {
                        this.mBlankView.setOnDragListener(this.mBlockDragListener);
                        ((TextView) this.mBlankView.findViewById(R.id.content)).setText("");
                    } else {
                        this.mBlankView.setOnDragListener(this.mBlankViewDragListener);
                        ((TextView) this.mBlankView.findViewById(R.id.content)).setText(getString(R.string.pc_note_edit_blank_view_tips));
                    }
                }
                if (this.mEditor != null) {
                    this.mEditor.clearFocus();
                }
                if (this.mEditorWebView != null) {
                    this.mEditorWebView.setFocusable(false);
                    this.mEditorWebView.setFocusableInTouchMode(false);
                    this.mEditorWebView.clearFocus();
                }
            } else {
                Folme.useAt(this.mBlankView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f));
                Folme.useAt(this.mContentView).state().cancel();
                this.mContentView.setVisibility(0);
                Folme.useAt(this.mContentView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
                View view = this.mBlankView;
                if (view != null) {
                    view.setOnDragListener(null);
                }
                if (this.mEditorWebView != null) {
                    this.mEditorWebView.setFocusable(true);
                    this.mEditorWebView.setFocusableInTouchMode(true);
                }
            }
            this.mIsShowBlank = z;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onShowAddNewNote(boolean z) {
        if (z) {
            this.mBlankView.setSelected(true);
        } else {
            this.mBlankView.setSelected(false);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void updateActionMode(EditorActionMode editorActionMode, boolean z) {
        Logger.INSTANCE.d(TAG, "updateActionMode" + editorActionMode);
        updateActionMode(editorActionMode, false, z);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void updateActionMode(EditorActionMode editorActionMode, boolean z, boolean z2) {
        if (this.mActionMode == editorActionMode) {
            return;
        }
        if (editorActionMode == EditorActionMode.VIEW) {
            onViewMode();
            if (this.mActionMode == EditorActionMode.EDIT_RECODE) {
                recordToView();
            }
        } else if (editorActionMode == EditorActionMode.EDIT) {
            onEditMode(z);
        } else if (editorActionMode == EditorActionMode.EDIT_RECODE) {
            onAudioMode();
        } else if (editorActionMode == EditorActionMode.EDIT_TITLE) {
            onTitleMode();
        }
        this.mActionMode = editorActionMode;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void onViewMode() {
        super.onViewMode();
        Logger.INSTANCE.i(TAG, "onViewMode");
        ((PcNoteHeadInfoController) this.mNoteExtraInfoController).show(true);
        if (getPcToolBarController() != null) {
            getPcToolBarController().hide(false);
            getPcToolBarController().lock();
        }
        notifyEditUiState(0);
        notifyAction(20, null);
    }

    private boolean inTrashFolder() {
        if (this.mWorkingNote != null && this.mWorkingNote.getFolderId() == -3) {
            if (this.mEditor != null) {
                this.mEditor.setEnableEdit(false);
            }
            return true;
        }
        if (this.mEditor != null) {
            this.mEditor.setEnableEdit(true);
        }
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void onTitleMode() {
        if (inTrashFolder()) {
            return;
        }
        super.onTitleMode();
        Logger.INSTANCE.i(TAG, "onTitleMode");
        ((PcNoteHeadInfoController) this.mNoteExtraInfoController).show(true);
        if (getPcToolBarController() != null) {
            getPcToolBarController().hide(false);
            getPcToolBarController().lock();
        }
        notifyEditUiState(1);
        notifyAction(21, null);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void onEditMode(boolean z) {
        if (inTrashFolder()) {
            return;
        }
        super.onEditMode(z);
        ((PcNoteHeadInfoController) this.mNoteExtraInfoController).hide(true);
        if (getPcToolBarController() != null) {
            getPcToolBarController().show(false);
            getPcToolBarController().unlock();
        }
        this.mEditorWebView.requestFocus();
        this.mEditor.setEnableEdit(true);
        notifyEditUiState(1);
        notifyAction(21, null);
    }

    private void setContentBackground(Drawable drawable) {
        this.mContentView.setBackground(drawable);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setWorkingNote(final WorkingNote workingNote, boolean z) {
        Logger.INSTANCE.d(TAG, "setWorkingNote");
        this.mNoteId = workingNote.getNoteId();
        this.mWorkingNote = workingNote;
        if (isAdded() && this.mPasswordController != null) {
            this.mPasswordController.lock();
            this.mPasswordController.unlock();
        }
        inTrashFolder();
        FragmentActivity activity = getActivity();
        if (isAdded() && activity != null) {
            if (this.mWorkingNote.isHidden()) {
                activity.getWindow().addFlags(8192);
            } else {
                activity.getWindow().clearFlags(8192);
            }
        }
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.collapsePanel(null);
            this.mEditToolbarController.setWorkingNote(this.mWorkingNote);
        }
        this.mWorkingNote.addOnSettingStatusChangedListener(this);
        registerNoteContentObserver(true);
        this.mFolderId = this.mWorkingNote.getFolderId();
        if (this.mFolderId == -4097) {
            if (this.mNoteExtraInfoController != null) {
                this.mNoteExtraInfoController.updateFolderName(NoteApp.getInstance().getString(R.string.unclassfied_folder_name));
            }
        } else {
            asyncUpdateFolderName();
        }
        if (isHideFolderChooser() && this.mNoteExtraInfoController != null) {
            this.mNoteExtraInfoController.setFolderGroupVisibility(8);
        }
        if (isResumed() && FloatWindowManagerAgent.isContinuousExcerpt() && !isThisNoteDoExcerpt()) {
            if (!isThisNoteDoExcerpt()) {
                FloatWindowManagerAgent.resumeContinuousExcerpt();
            } else if (this.mIsShow) {
                FloatWindowManagerAgent.pauseContinuousExcerpt();
            }
        }
        registerNoteContentObserver(true);
        setContentBackground(getContext().getDrawable(R.drawable.pc_right_frame_bg));
        updateEditor(z);
        this.mHandler.post(new Runnable() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setWorkingNote$0(workingNote);
            }
        });
        showBlankView(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setWorkingNote$0(WorkingNote workingNote) {
        if (this.mNoteExtraInfoController != null) {
            this.mNoteExtraInfoController.update(workingNote, workingNote.getContent().length() + workingNote.getTitle().length());
        }
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

    private void dismissAllPopMenu() {
        this.mEditor.hidePopMenu();
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
            Logger.INSTANCE.i(PcHybridNoteEditFragment.TAG, "onResult UpdateNoteLoadTask");
            if (PcHybridNoteEditFragment.this.mWorkingNote == null || workingNote.getModifiedDate() != PcHybridNoteEditFragment.this.mWorkingNote.getModifiedDate()) {
                Logger.INSTANCE.i(PcHybridNoteEditFragment.TAG, "onResult UpdateNoteLoadTask remote save note");
                super.onResult(taskManager, workingNote);
                return;
            }
            Logger.INSTANCE.i(PcHybridNoteEditFragment.TAG, "onResult UpdateNoteLoadTask same note");
            if (PcHybridNoteEditFragment.this.mLoadTask != this) {
                return;
            }
            PcHybridNoteEditFragment.this.mLoadTask = null;
            PcHybridNoteEditFragment.this.showLoadingData(false);
            if (TextUtils.isEmpty(this.searchContent)) {
                PcHybridNoteEditFragment.this.updateUserQuery("", false, null);
                PcHybridNoteEditFragment.this.mSearchText = "";
            } else if (!TextUtils.isEmpty(this.searchContent) && !this.searchContent.equals(PcHybridNoteEditFragment.this.mSearchText)) {
                PcHybridNoteEditFragment.this.mSearchText = this.searchContent;
                PcHybridNoteEditFragment.this.updateActionMode(EditorActionMode.VIEW, false);
            }
            PcHybridNoteEditFragment pcHybridNoteEditFragment = PcHybridNoteEditFragment.this;
            pcHybridNoteEditFragment.updateUserQuery(pcHybridNoteEditFragment.mSearchText, false, null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateUserQuery(String str, boolean z, ArrayList<String> arrayList) {
        super.updateUserQuery(str, z, arrayList);
        if (this.mEditor == null) {
            return;
        }
        WebRichEditor webRichEditor = this.mEditor;
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        webRichEditor.updateQueryContent(str, null);
    }

    public class PcHybridEditNoteLoadTask extends BaseNormalNoteEditFragment.EditNoteLoadTask {
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onInsertAudio() {
        }

        public PcHybridEditNoteLoadTask(Intent intent) {
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
                PcHybridNoteEditFragment.this.notifyAction(10, bundle);
            }
            return workingNoteLoadWorkingNote;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onEnterEdit(WorkingNote workingNote) {
            if (this.intent.getExtras() == null) {
                PcHybridNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                return;
            }
            EditorActionMode editorActionMode = (EditorActionMode) this.intent.getExtras().get(NoteIntent.KEY_ACTION_MODE);
            if (editorActionMode != null && editorActionMode.equals(EditorActionMode.EDIT_RECODE)) {
                PcHybridNoteEditFragment.this.startRecordWithCheckFromEditTask();
            } else {
                PcHybridNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, false);
            }
        }
    }

    protected void showPictureChooserDialog(View view) {
        PcInsertPictureMenu pcInsertPictureMenu = new PcInsertPictureMenu(getContext());
        pcInsertPictureMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.8
            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onDismiss() {
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onShow() {
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onItemSelected(View view2, int i) {
                switch (view2.getId()) {
                    case R.id.pick_from_camera /* 2131363192 */:
                        PcHybridNoteEditFragment.this.launchMainScreenAction(1, null);
                        PcHybridNoteEditFragment.this.showTakePhotoDialog();
                        break;
                    case R.id.pick_from_doodle /* 2131363193 */:
                        PcHybridNoteEditFragment.this.launchMainScreenAction(3, null);
                        PcHybridNoteEditFragment.this.showDoodleDialog();
                        break;
                    case R.id.pick_from_pc /* 2131363194 */:
                        MiuiSynergySdk.getInstance().chooseFileOnSynergy(PcHybridNoteEditFragment.this.getActivity(), new MiuiSynergySdk.ChooseFileCallback() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.8.1
                            @Override // com.xiaomi.mirror.synergy.MiuiSynergySdk.ChooseFileCallback
                            public void onFileChosen(ClipData clipData) {
                                if (clipData != null) {
                                    Logger.INSTANCE.i(PcHybridNoteEditFragment.TAG, "onFileChosen ");
                                    PcHybridNoteEditFragment.this.insertImageFromClipData(clipData, false);
                                }
                            }
                        });
                        break;
                    case R.id.pick_from_phone /* 2131363195 */:
                        PcHybridNoteEditFragment.this.launchMainScreenAction(2, null);
                        PcHybridNoteEditFragment.this.showSelectPhotoDialog();
                        break;
                }
            }
        });
        pcInsertPictureMenu.show(view, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchMainScreenAction(int i, Parcelable parcelable) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainScreenBridgeActivity.BRIDGE_ACTION_RESULT);
        if (Utils.isAboveSDK34()) {
            getActivity().registerReceiver(this.mPhotoResultReceiver, intentFilter, 2);
        } else {
            getActivity().registerReceiver(this.mPhotoResultReceiver, intentFilter);
        }
        Intent intent = new Intent(getActivity(), (Class<?>) MainScreenBridgeActivity.class);
        intent.setFlags(67108864);
        intent.putExtra(MainScreenBridgeActivity.EXTRA_METHOD, i);
        if (parcelable != null) {
            intent.putExtra(MainScreenBridgeActivity.EXTRA_PARAMS, parcelable);
        }
        startActivity(intent, ActivityOptions.makeBasic().setLaunchDisplayId(0).toBundle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSelectPhotoDialog() {
        if (isAdded()) {
            PcLockScreenDialog pcLockScreenDialog = new PcLockScreenDialog(getContext(), R.layout.pc_note_select_photo_lock_dialog, new PcLockScreenDialog.ActionListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.9
                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onConfirm() {
                }

                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onCancel() {
                    PcHybridNoteEditFragment.this.finishMainScreenActivity();
                }
            });
            pcLockScreenDialog.show(this.mRootView);
            this.mShowingLockScreenDialog = pcLockScreenDialog;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDoodleDialog() {
        if (isAdded()) {
            PcLockScreenDialog pcLockScreenDialog = new PcLockScreenDialog(getContext(), R.layout.pc_note_doodle_lock_dialog, new PcLockScreenDialog.ActionListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.10
                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onConfirm() {
                }

                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onCancel() {
                    PcHybridNoteEditFragment.this.finishMainScreenActivity();
                }
            });
            pcLockScreenDialog.show(this.mRootView);
            this.mShowingLockScreenDialog = pcLockScreenDialog;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTakePhotoDialog() {
        if (isAdded()) {
            PcLockScreenDialog pcLockScreenDialog = new PcLockScreenDialog(getContext(), R.layout.pc_note_take_photo_lock_dialog, new PcLockScreenDialog.ActionListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.11
                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onConfirm() {
                }

                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onCancel() {
                    PcHybridNoteEditFragment.this.finishMainScreenActivity();
                }
            });
            pcLockScreenDialog.show(this.mRootView);
            this.mShowingLockScreenDialog = pcLockScreenDialog;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishMainScreenActivity() {
        Intent intent = new Intent(getActivity(), (Class<?>) MainScreenBridgeActivity.class);
        intent.setFlags(67108864);
        intent.setAction(MainScreenBridgeActivity.BRIDGE_ACTION_FINISH);
        startActivity(intent, ActivityOptions.makeBasic().setLaunchDisplayId(0).toBundle());
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onChangeToMindNote() {
        String title;
        cancelSaveTask();
        if (getContext() != null) {
            NoteStore.delete(getContext(), new long[]{getNoteId()});
        }
        if (this.mWorkingNote == null) {
            title = "";
        } else {
            title = this.mWorkingNote.getTitle();
        }
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(title)) {
            bundle.putString("note_title", title);
        }
        bundle.putInt("mind_content_type", 0);
        if (this.mWorkingNote != null) {
            bundle.putInt("widget_id", this.mWorkingNote.getWidgetId());
            bundle.putInt("widget_type", this.mWorkingNote.getWidgetType());
        }
        notifyAction(11, bundle);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    public void onImageElementClicked(final EmbedElementInfoBean embedElementInfoBean) {
        this.isImageClicked = true;
        this.mEditorWebView.isImageClick = true;
        this.mFormattedDoodleFileId = embedElementInfoBean.fileName;
        embedElementInfoBean.fileName = getOriginalFileId(this.mFormattedDoodleFileId);
        final String attachmentPath = AttachmentUtils.getAttachmentPath(getContext(), embedElementInfoBean.fileName);
        this.clickedImagePath = attachmentPath;
        Logger.INSTANCE.d(TAG, "image right click");
        if (isAdded() && getContext() != null) {
            if (this.mShowingPopupWindow != null) {
                if (this.mShowingPopupWindow.isShowing()) {
                    this.mShowingPopupWindow.dismiss();
                }
                this.mShowingPopupWindow = null;
                this.mEditor.hidePopMenu();
                return;
            }
            if (attachmentPath != null && DocumentReader.INSTANCE.isDoodleFile(attachmentPath)) {
                Logger.INSTANCE.d(TAG, "onImageElementClicked imagePath " + attachmentPath);
                DoodleApplication.preloadData(attachmentPath, getContext());
            }
            final RichEditorWebView richEditorWebView = this.mEditorWebView;
            Rect rect = new Rect();
            richEditorWebView.getGlobalVisibleRect(rect);
            Rect rect2 = new Rect(embedElementInfoBean.eLeft, embedElementInfoBean.eTop, embedElementInfoBean.eRight, embedElementInfoBean.eBottom);
            rect2.offset(rect.left, rect.top);
            final RichTextFloatMenuInfo richTextFloatMenuInfoCreatePcImageActionMenu = RichTextFloatMenuHelper.createPcImageActionMenu(getContext(), rect2, rect, embedElementInfoBean.imgSizeLevel != 0, new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onImageElementClicked$1(embedElementInfoBean, attachmentPath, view);
                }
            });
            richTextFloatMenuInfoCreatePcImageActionMenu.menu.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment$$ExternalSyntheticLambda2
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    this.f$0.lambda$onImageElementClicked$2();
                }
            });
            this.mShowingPopupWindow = richTextFloatMenuInfoCreatePcImageActionMenu.menu;
            this.mHandler.post(new Runnable() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onImageElementClicked$3(richEditorWebView, richTextFloatMenuInfoCreatePcImageActionMenu);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onImageElementClicked$1(EmbedElementInfoBean embedElementInfoBean, String str, View view) {
        switch (view.getId()) {
            case android.R.id.cut:
                cutImage(str);
                break;
            case android.R.id.copy:
                copyImage(str);
                break;
            case R.id.annotate_image /* 2131362007 */:
                this.mEditor.annotateImageStart();
                break;
            case R.id.delete /* 2131362396 */:
                this.mEditor.deleteEmbedElement();
                break;
            case R.id.edit_image /* 2131362464 */:
                editImage(embedElementInfoBean);
                break;
            case R.id.save_as /* 2131363336 */:
                saveToSynergy(str);
                break;
            case R.id.scale_image /* 2131363347 */:
                this.mEditor.changeShowBigImg();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onImageElementClicked$2() {
        this.mShowingPopupWindow = null;
        this.mEditor.hidePopMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onImageElementClicked$3(View view, RichTextFloatMenuInfo richTextFloatMenuInfo) {
        if (isAdded()) {
            this.mShowingPopupWindow.showAtLocation(view, richTextFloatMenuInfo.gravity, richTextFloatMenuInfo.showX, richTextFloatMenuInfo.showY);
            view.requestFocus();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void asyncSaveNote() {
        if (this.mWorkingNote == null) {
            return;
        }
        updateWorkingTextAndTitle();
        if (this.mHandler.hasMessages(1) || this.mWorkingNote.isEmpty()) {
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(1, getAutoSaveCommonDuration());
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void editImage(EmbedElementInfoBean embedElementInfoBean) {
        if (embedElementInfoBean.type == 0) {
            this.mEditor.editImageStart();
            DoodleParams doodleParamsCreateEditImageParams = createEditImageParams(embedElementInfoBean);
            if (doodleParamsCreateEditImageParams != null) {
                this.mDoodleFileId = embedElementInfoBean.fileName;
                launchMainScreenAction(3, doodleParamsCreateEditImageParams);
                showDoodleDialog();
            }
        }
    }

    private void saveToSynergy(String str) {
        if (isAdded()) {
            File file = new File(str);
            if (file.exists()) {
                MiuiSynergySdk.getInstance().saveToSynergy(getActivity(), new ClipData(new ClipDescription(BaseDragListener.LABEL_NOTE_EDITOR, new String[]{ShareAppsAdapter.TYPE_IMAGE}), new ClipData.Item(ImageUtils.INSTANCE.getImageContentUri(getContext(), file))), "custom extra");
            }
        }
    }

    private void cutImage(String str) {
        copyImage(str);
        this.mEditor.deleteEmbedElement();
    }

    private void copyImage(String str) {
        File file = new File(str);
        if (file.exists()) {
            ((ClipboardManager) getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newUri(getContext().getContentResolver(), "URI", ImageUtils.INSTANCE.getImageContentUri(getContext(), file)));
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showDualScreenEditTipDialog() {
        if (isAdded() && this.mDualScreenEditTipDialog == null) {
            PcLockScreenDialog pcLockScreenDialog = new PcLockScreenDialog(getContext(), R.layout.pc_note_phone_edit_lock_dialog, new PcLockScreenDialog.ActionListener() { // from class: com.miui.pc.feature.notes.PcHybridNoteEditFragment.12
                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onCancel() {
                }

                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onConfirm() {
                    PcHybridNoteEditFragment.this.notifyEditUiState(3);
                }
            });
            this.mDualScreenEditTipDialog = pcLockScreenDialog;
            pcLockScreenDialog.setCanceledOnTouchOutside(false);
            this.mDualScreenEditTipDialog.show(this.mRootView);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void notifyEditUiState(int i) {
        if (isAdded()) {
            Intent intent = new Intent();
            intent.setAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_CHANGED);
            intent.putExtra(FrameConstant.PARAM_SCREEN_ID, 1);
            intent.putExtra(FrameConstant.PARAM_SCREEN_MODE, i);
            if (getActivity() != null) {
                getActivity().sendBroadcast(intent);
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public boolean saveNote(boolean z) {
        String string;
        Logger.INSTANCE.d(TAG, "saveNote");
        if (this.mWorkingNote == null) {
            return false;
        }
        if (z) {
            CharSequence richTextData = this.mNoteRichEditor.getRichTextData();
            if (richTextData != null) {
                string = richTextData.toString();
            } else {
                string = "";
            }
            Logger.INSTANCE.d(TAG, "saveNote: content = ");
            this.mWorkingNote.setWorkingText(string);
        } else {
            updateWorkingTextAndTitle();
        }
        return doSaveWorkingNote();
    }

    public void saveNoteForce() {
        if (this.mWorkingNote != null) {
            if (this.mHandler.hasMessages(1)) {
                this.mHandler.removeMessages(1);
            }
            saveNote(true);
            doSaveWorkingNote();
        }
    }

    private boolean doSaveWorkingNote() {
        Logger.INSTANCE.d(TAG, "doSaveWorkingNote");
        if (this.isImageClicked) {
            this.isImageClicked = false;
            this.mEditorWebView.isImageClick = false;
            return false;
        }
        boolean zSaveNote = this.mWorkingNote.saveNote(true);
        notifyWorkingNoteContentSync();
        return zSaveNote;
    }

    protected void notifyWorkingNoteContentSync() {
        if (isAdded()) {
            Intent intent = new Intent();
            intent.setAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_CONTENT_SYNC);
            intent.putExtra(FrameConstant.PARAM_SCREEN_ID, 1);
            intent.putExtra("note_id", getNoteId());
            getActivity().sendBroadcast(intent);
        }
    }
}
