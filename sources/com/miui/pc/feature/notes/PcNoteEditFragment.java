package com.miui.pc.feature.notes;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.provider.FontsContractCompat;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.constant.FrameConstant;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.common.tool.async.TaskManager;
import com.miui.common.view.IPopMenu;
import com.miui.doodle.DoodleApplication;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.maml.component.MamlView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.basefeature.noteedit.ColumnsNativeNoteEditFragment;
import com.miui.notes.component.noteedit.IBaseNoteEditToolbarController;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.editor.BaseNoteRichEditorController;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.LogUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.BaseActionBarController;
import com.miui.notes.ui.BaseDragListener;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.notes.widget.NoteWidgetProvider;
import com.miui.pc.richeditor.PcNoteRichEditor;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.IEditorPopMenu;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.miui.richeditor.UndoManager;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.theme.Theme;
import com.xiaomi.mirror.synergy.MiuiSynergySdk;
import java.io.File;
import java.util.ArrayList;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.core.widget.NestedScrollView;
import miuix.nestedheader.widget.NestedHeaderLayout;

/* JADX INFO: loaded from: classes4.dex */
public class PcNoteEditFragment extends ColumnsNativeNoteEditFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_QUERY_FOLDER = 2;
    private static final String TAG = "PcNoteEditFragment";
    private View mBlankView;
    private ViewGroup mEditorContainer;

    @Deprecated
    private FolderChoiceMenuController mFolderMenuController;
    private View mRemindContainer;
    private int mRequestFocusChildAdd = 0;
    private PcAnnotationEditController mPcAnnotationController = null;
    private final IEditorPopMenu.PopMenuListener mPopMenuListener = new IEditorPopMenu.PopMenuListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.1
        @Override // com.miui.richeditor.IEditorPopMenu.PopMenuListener
        public void onLinkCardMenuClick(View view, LinkCardSpan linkCardSpan) {
        }

        @Override // com.miui.richeditor.IEditorPopMenu.PopMenuListener
        public void onImageMenuClick(View view, BaseImageSpan baseImageSpan) {
            int id = view.getId();
            if (id == R.id.annotate_image) {
                if (!PcNoteEditFragment.this.isInEditAction()) {
                    PcNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                }
                PcNoteEditFragment.this.lambda$annotateImage$13((NormalImageSpan) baseImageSpan);
            } else if (id == R.id.edit_image) {
                PcNoteEditFragment.this.editImage((NormalImageSpan) baseImageSpan);
            } else {
                if (id != R.id.scale_image) {
                    return;
                }
                PcNoteEditFragment.this.toggleImageShowBig((NormalImageSpan) baseImageSpan);
            }
        }
    };
    private final PcRichEditorDragListener mEditorDragListener = new PcRichEditorDragListener(0);
    private final BlankViewDragListener mBlankViewDragListener = new BlankViewDragListener(0);
    private final BaseDragListener mBlockDragListener = new BaseDragListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.2
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
    private final BroadcastReceiver mPhotoResultReceiver = new BroadcastReceiver() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int i = intent.getExtras().getInt("request_code", 0);
            int i2 = intent.getExtras().getInt(FontsContractCompat.Columns.RESULT_CODE, 0);
            if (PcNoteEditFragment.this.getContext() == null || PcNoteEditFragment.this.getActivity() == null) {
                Logger.INSTANCE.w(PcNoteEditFragment.TAG, "mPhotoResultReceiver getContext is null!!");
                return;
            }
            if (i2 == -1) {
                if (i == 1) {
                    String scrapPath = NotesProvider.getScrapPath(PcNoteEditFragment.this.getActivity());
                    if (scrapPath == null) {
                        return;
                    }
                    PcNoteEditFragment.this.insertImages(Uri.fromFile(new File(scrapPath)));
                } else if (i == 2) {
                    ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("pick-result-data");
                    if (parcelableArrayListExtra == null) {
                        PcNoteEditFragment.this.insertImages(intent.getData());
                    } else {
                        int size = parcelableArrayListExtra.size();
                        Uri[] uriArr = new Uri[size];
                        parcelableArrayListExtra.toArray(uriArr);
                        for (int i3 = 0; i3 < size; i3++) {
                            PcNoteEditFragment.this.getContext().grantUriPermission(PcNoteEditFragment.this.getContext().getPackageName(), uriArr[i3], 1);
                        }
                        PcNoteEditFragment.this.insertImages(uriArr);
                    }
                } else if (i == 32) {
                    String stringExtra = intent.getStringExtra(MainScreenBridgeActivity.KEY_DOODLE_RESULT);
                    if (!TextUtils.isEmpty(stringExtra)) {
                        PcNoteEditFragment.this.insertDoodle(stringExtra);
                    }
                } else if (i == 64) {
                    String stringExtra2 = intent.getStringExtra(MainScreenBridgeActivity.KEY_DOODLE_RESULT);
                    if (TextUtils.isEmpty(stringExtra2)) {
                        if (PcNoteEditFragment.this.mEditingImageSpan != null) {
                            PcNoteEditFragment.this.mEditorView.deleteImageSpan(PcNoteEditFragment.this.mEditingImageSpan, true);
                        }
                    } else if (!DoodleApplication.SaveFlagNoChange.contentEquals(stringExtra2)) {
                        PcNoteEditFragment.this.updateDoodle(stringExtra2);
                    }
                    PcNoteEditFragment.this.mEditingImageSpan = null;
                }
            }
            if (PcNoteEditFragment.this.mShowingLockScreenDialog != null) {
                PcNoteEditFragment.this.mShowingLockScreenDialog.dismiss();
            }
            try {
                PcNoteEditFragment.this.getActivity().unregisterReceiver(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private PcLockScreenDialog mDualScreenEditTipDialog = null;

    private boolean isSupportTheme() {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected BaseActionBarController createHeaderBarController(View view) {
        return null;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getAutoSaveCommonDuration() {
        return 1500;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getLayoutResId() {
        return R.layout.pc_note_edit;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getThemeResId() {
        return R.style.NoteTheme_Light_PcEdit;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initActionBar() {
    }

    @Override // com.miui.notes.ui.BaseActionBarController.EditCallback
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.model.WorkingNote.NoteSettingChangedListener
    public void onClockAlertChanged(long j, long j2) {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.model.WorkingNote.NoteSettingChangedListener
    public void onNoteThemeChanged() {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, androidx.fragment.app.Fragment
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected IBaseNoteEditToolbarController createToolBarController(View view) {
        return new PcActionToolBarController(view.findViewById(R.id.toolbar), (PcNoteRichEditor) this.mEditorView);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected INoteInfoHeader createNoteExtraInfoController(View view, View view2) {
        return new PcNoteHeadInfoController(view);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected BaseNoteRichEditorController createEditorController() {
        return new PcNoteRichEditorController(this, this.mEditorView, this.mTitleEditor);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logger.INSTANCE.d(TAG, "onCreate");
        this.mRequestFocusChildAdd = getContext().getResources().getDimensionPixelSize(R.dimen.notes_edit_request_child_focus_add);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Logger.INSTANCE.d(TAG, "onViewCreated");
        if (getActivity() == null || getView() == null) {
            return;
        }
        initTitleActionBar(getView());
        if (isSupportTheme() && this.mCurrentTheme != null && (this.mCurrentTheme instanceof AnimTheme)) {
            ((AnimTheme) this.mCurrentTheme).resume();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment
    public void onVisibilityChanged(boolean z) {
        super.onVisibilityChanged(z);
        if (z) {
            return;
        }
        this.mEditorController.hideInputMethod();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        this.mHandler.removeMessages(3);
        asyncSaveNoteInstantlyIfNeeded();
        dismissAllDialogOrPanel();
        if (this.mCurrentTheme != null && (this.mCurrentTheme instanceof AnimTheme)) {
            ((AnimTheme) this.mCurrentTheme).pause();
        }
        super.onPause();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        updateActionMode(EditorActionMode.VIEW, false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        ((PcNoteRichEditor) this.mNoteRichEditor).dismissShowingMenu();
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
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
        this.mLocalBroadcastManager = null;
        PcLockScreenDialog pcLockScreenDialog = this.mDualScreenEditTipDialog;
        if (pcLockScreenDialog != null) {
            pcLockScreenDialog.dismiss();
            this.mDualScreenEditTipDialog = null;
        }
        super.onDestroy();
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
                    if (!this.mEditorView.isCursorVisible()) {
                        this.mEditorView.setSelection(this.mEditorView.length());
                        this.mEditorView.setCursorVisible(true);
                        this.mEditorView.requestFocus();
                    }
                    this.mEditorView.getFocusedRect(rect);
                    int paddingBottom = getView().findViewById(R.id.edit_content).getPaddingBottom() + this.mRequestFocusChildAdd;
                    rect.top += paddingBottom;
                    rect.bottom += paddingBottom;
                    this.mEditorScrollView.requestChildRectangleOnScreen(this.mEditorContainer, rect, false);
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initView(View view) {
        view.setOnDragListener(this.mBlockDragListener);
        this.mContentView = view.findViewById(R.id.sv_note_edit);
        this.mNestedHeaderLayout = (NestedHeaderLayout) view.findViewById(R.id.nested_header_layout);
        this.mBlankView = view.findViewById(R.id.blank_container);
        this.mBlankViewDragListener.setEditFragment(this);
        this.mBlankView.setOnDragListener(this.mBlankViewDragListener);
        this.mTitleEditor = (EditText) view.findViewById(R.id.note_title);
        initReminder(view);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initNoteEditor(final View view) {
        this.mEditorContainer = (ViewGroup) view.findViewById(R.id.editor_container);
        Logger.INSTANCE.d(TAG, "initNoteEditor");
        super.initNoteEditor(view);
        this.mEditorView.getUndoManager().setUndoStateChangedListener(new UndoManager.UndoStateChangedListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda8
            @Override // com.miui.richeditor.UndoManager.UndoStateChangedListener
            public final void onUndoStateChanged(UndoManager undoManager) {
                this.f$0.lambda$initNoteEditor$0(undoManager);
            }
        });
        this.mEditorScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda9
            @Override // miuix.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
                this.f$0.lambda$initNoteEditor$1(nestedScrollView, i, i2, i3, i4);
            }
        });
        view.findViewById(R.id.edit_content).setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initNoteEditor$2(view, view2);
            }
        });
        this.mEditorView.setOutKeyListener(new OnKeyDownAndShortCutListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.4
            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
                if (PcNoteEditFragment.this.mOutKeyListenerWrapper != null) {
                    return PcNoteEditFragment.this.mOutKeyListenerWrapper.onKeyShortcut(i, keyEvent);
                }
                return false;
            }

            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyDown(int i, KeyEvent keyEvent) {
                if (PcNoteEditFragment.this.mOutKeyListenerWrapper != null) {
                    return PcNoteEditFragment.this.mOutKeyListenerWrapper.onKeyDown(i, keyEvent);
                }
                return false;
            }

            @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
            public boolean onKeyUp(int i, KeyEvent keyEvent) {
                if (PcNoteEditFragment.this.mOutKeyListenerWrapper != null) {
                    return PcNoteEditFragment.this.mOutKeyListenerWrapper.onKeyUp(i, keyEvent);
                }
                return false;
            }
        });
        ((PcNoteRichEditor) this.mEditorView).setStateListener(new PcNoteRichEditor.StateListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.5
            @Override // com.miui.pc.richeditor.PcNoteRichEditor.StateListener
            public void onImageEditMenu(boolean z) {
                PcNoteEditFragment.this.mEditorScrollView.setForbidScroll(z);
            }

            @Override // com.miui.pc.richeditor.PcNoteRichEditor.StateListener
            public void onStartPlayAudio() {
                if (PcNoteEditFragment.this.isInEditAction()) {
                    return;
                }
                PcNoteEditFragment.this.mEditorScrollView.setForbidScroll(true);
            }

            @Override // com.miui.pc.richeditor.PcNoteRichEditor.StateListener
            public void onStopPlayAudio() {
                if (PcNoteEditFragment.this.isInEditAction()) {
                    return;
                }
                PcNoteEditFragment.this.mEditorScrollView.setForbidScroll(false);
            }

            @Override // com.miui.pc.richeditor.PcNoteRichEditor.StateListener
            public void onMindSpanTextClick() {
                if (PcNoteEditFragment.this.isAdded()) {
                    if (PcNoteEditFragment.this.mActionMode == null || PcNoteEditFragment.this.mActionMode != EditorActionMode.EDIT) {
                        PcNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                    }
                }
            }
        });
        ((PcNoteRichEditor) this.mEditorView).setRootContainer(getView().findViewById(android.R.id.content));
        this.mEditorDragListener.setEditFragment(this);
        this.mEditorView.setOnDragListener(this.mEditorDragListener);
        this.mTitleEditor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda11
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return this.f$0.lambda$initNoteEditor$3(textView, i, keyEvent);
            }
        });
        this.mEditorController.setMenuListener(this.mPopMenuListener);
        this.mEditorView.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initNoteEditor$0(UndoManager undoManager) {
        getPcToolBarController().updateUndoState(undoManager.canUndo(), undoManager.canRedo());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initNoteEditor$1(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
        Logger.INSTANCE.i(TAG, "onScrollChange scrollY = " + i2 + " oldScrollY = " + i4 + " getScrollY() = " + nestedScrollView.getScrollY());
        PcAnnotationEditController pcAnnotationEditController = this.mPcAnnotationController;
        if (pcAnnotationEditController != null) {
            pcAnnotationEditController.onScroll(i4 - i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initNoteEditor$2(View view, View view2) {
        this.mEditorView.setSelection(this.mEditorView.length());
        this.mEditorView.setCursorVisible(true);
        this.mEditorView.requestFocus();
        Rect rect = new Rect();
        this.mEditorView.getFocusedRect(rect);
        int paddingBottom = view.findViewById(R.id.edit_content).getPaddingBottom() + this.mRequestFocusChildAdd;
        rect.top += paddingBottom;
        rect.bottom += paddingBottom;
        this.mEditorScrollView.requestChildRectangleOnScreen(this.mEditorContainer, rect, false);
        updateActionMode(EditorActionMode.EDIT, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initNoteEditor$3(TextView textView, int i, KeyEvent keyEvent) {
        if (keyEvent == null || keyEvent.getKeyCode() != 66) {
            return false;
        }
        doneEdit();
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initToolbarController(View view) {
        this.mEditToolbarController = createToolBarController(view);
        getPcToolBarController().setActionListener(this);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void initNoteTitle(View view) {
        super.initNoteTitle(view);
        this.mTitleEditor.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view2, int i, KeyEvent keyEvent) {
                return this.f$0.lambda$initNoteTitle$4(view2, i, keyEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initNoteTitle$4(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || (i != 66 && i != 160 && i != 23)) {
            return false;
        }
        saveNoteInstantlyIfNeeded();
        this.mEditorView.setSelection(0);
        this.mEditorView.requestFocus();
        updateActionMode(EditorActionMode.EDIT, true);
        return true;
    }

    private void initReminder(View view) {
        View viewFindViewById = view.findViewById(R.id.reminder);
        this.mRemindContainer = viewFindViewById;
        viewFindViewById.findViewById(R.id.restore).setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initReminder$5(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initReminder$5(View view) {
        NoteStore.restore(getContext(), new long[]{this.mWorkingNote.getNoteId()});
    }

    private void initTitleActionBar(View view) {
        View viewFindViewById = view.findViewById(R.id.more);
        Folme.useAt(viewFindViewById).touch().handleTouchOf(viewFindViewById, new AnimConfig[0]);
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                PcNoteEditFragment.this.showMoreMenuDialog(view2);
            }
        });
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateEditor(boolean z) {
        this.mNoteRichEditor.setDataId(this.mWorkingNote.getNoteId());
        this.mNoteRichEditor.setFolderId(this.mWorkingNote.getFolderId());
        this.mEditorView.updateFontSizeId(PreferenceUtils.getFontSize(getActivity(), 1));
        this.mTitleEditor.removeTextChangedListener(this.mTitleEditorTextWatcher);
        if (!TextUtils.isEmpty(this.mWorkingNote.getTitle())) {
            this.mTitleEditor.setText(this.mWorkingNote.getTitle());
        } else {
            this.mTitleEditor.setText("");
        }
        if (this.mTitleEditor.getText() != null && !TextUtils.isEmpty(this.mTitleEditor.getText().toString())) {
            this.mEditorView.setTitle(this.mTitleEditor.getText().toString());
        }
        this.mTitleEditor.addTextChangedListener(this.mTitleEditorTextWatcher);
        String content = this.mWorkingNote.getContent();
        this.mEditorView.updateContent(content, this.mSearchText);
        this.mEditorView.requestFocus();
        if (!PreferenceUtils.isFirstHandle(getContext()) && (z || TextUtils.isEmpty(content))) {
            this.mEditorView.setSelection(this.mEditorView.length());
            if (isInEditAction()) {
                this.mEditorView.setCursorVisible(true);
            }
        }
        if (TextUtils.isEmpty(this.mSearchText)) {
            return;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateEditor$6();
            }
        }, 150L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEditor$6() {
        Rect rect = new Rect();
        this.mEditorView.getFocusedRect(rect);
        int paddingBottom = getView().findViewById(R.id.edit_content).getPaddingBottom() + this.mRequestFocusChildAdd;
        rect.top += paddingBottom;
        rect.bottom += paddingBottom;
        this.mEditorScrollView.requestChildRectangleOnScreen(this.mEditorView, rect, true);
    }

    public void editImage(NormalImageSpan normalImageSpan) {
        this.mEditorView.cacheSelection(true);
        this.mEditingImageSpan = normalImageSpan;
        launchMainScreenAction(3, createEditImageParams(normalImageSpan));
        showDoodleDialog();
    }

    public void toggleImageShowBig(NormalImageSpan normalImageSpan) {
        this.mEditorView.clearFocus();
        normalImageSpan.toggleShowBig();
        this.mEditorView.createNewRichTextData();
        saveNote(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
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
        if (this.mEditorView != null) {
            this.mEditorView.cancelBackgroundTask();
        }
        getActivity().setIntent(intent);
        saveNoteInstantlyIfNeeded();
        handleIntent(intent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(NoteLoadDataEntity noteLoadDataEntity, boolean z) {
        if (noteLoadDataEntity != null) {
            PcAnnotationEditController pcAnnotationEditController = this.mPcAnnotationController;
            if (pcAnnotationEditController != null) {
                try {
                    pcAnnotationEditController.hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.mPcAnnotationController = null;
            }
            try {
                loadNoteFromEntity(noteLoadDataEntity);
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
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
        Logger.INSTANCE.d(TAG, "loadNoteFromEntity for data" + (noteLoadDataEntity != null));
        View view = this.mBlankView;
        if (view != null) {
            view.setAlpha(0.0f);
        }
        if (this.mExecutorService.isShutdown()) {
            this.mExecutorService = Utils.newDiscardPolicySingleThreadExecutor();
        }
        this.mFolderId = noteLoadDataEntity.getFolderId();
        if (this.mFolderId == -3) {
            this.mRemindContainer.setVisibility(0);
        } else {
            this.mRemindContainer.setVisibility(8);
        }
        ((PcNoteRichEditor) this.mNoteRichEditor).dismissShowingMenu();
        setEnableEdit(!isReadOnlyMode());
        final Intent intent = noteLoadDataEntity.toIntent();
        if (noteLoadDataEntity.getNoteId() == -2) {
            this.mSearchText = "";
            this.mActionMode = null;
            resetEditContent();
            this.mEditorView.clearFocus();
            this.mEditToolbarController.resetRichTextStatus();
            intent.setAction("android.intent.action.EDIT");
            processLastWorkingNote(noteLoadDataEntity.getNoteId());
            this.mPendingEditIntentTask = new Runnable() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$loadNoteFromEntity$7(intent);
                }
            };
            this.mHandler.sendEmptyMessage(4);
            return true;
        }
        intent.setAction("android.intent.action.VIEW");
        boolean z = noteLoadDataEntity.getNoteId() > 0;
        boolean z2 = this.mWorkingNote == null || this.mWorkingNote.getNoteId() != noteLoadDataEntity.getNoteId();
        boolean z3 = (this.mWorkingNote == null || this.mWorkingNote.getFolderId() == noteLoadDataEntity.getFolderId() || noteLoadDataEntity.getFolderId() == -4097) ? false : true;
        if (z2 || z3) {
            this.mEditorView.clearPendingTask();
            this.mSearchText = "";
            this.mActionMode = null;
            processLastWorkingNote(noteLoadDataEntity.getNoteId());
            this.mEditToolbarController.resetRichTextStatus();
            if (noteLoadDataEntity.getNoteId() != -1) {
                showBlankView(false);
                if (z2) {
                    resetEditContent();
                    this.mLoadTask = new PcViewNoteLoadTask(intent, noteLoadDataEntity.getNoteId());
                    this.mLoadTask.commit();
                } else if (this.mEditorView.getDataParseTask() == null) {
                    this.mEditorView.updateContent(this.mEditorView.getRichTextData(), this.mSearchText);
                }
                updateActionMode(EditorActionMode.VIEW, false);
                return z;
            }
            onDataEmpty();
            return z;
        }
        if ((this.mLoadTask == null || !(this.mLoadTask instanceof UpdateNoteLoadTask)) && this.mWorkingNote != null) {
            if (this.mLoadTask != null) {
                this.mLoadTask.cancel();
            }
            this.mLoadTask = new UpdateNoteLoadTask(intent, noteLoadDataEntity.getNoteId());
            this.mLoadTask.commit();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadNoteFromEntity$7(Intent intent) {
        this.mLoadTask = new PcEditNoteLoadTask(intent);
        this.mLoadTask.commit();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected boolean handleIntent(Intent intent) {
        this.mFolderId = intent.getLongExtra("com.miui.notes.folder_id", 0L);
        this.mWorkingNote = null;
        this.mSearchText = null;
        this.mEditorView.setVisibility(4);
        if (this.mLoadTask != null) {
            this.mLoadTask.cancel();
            this.mLoadTask = null;
        }
        String action = intent.getAction();
        if ("android.intent.action.VIEW".equals(action)) {
            return handleViewIntent(intent);
        }
        if ("android.intent.action.INSERT_OR_EDIT".equals(action) || "android.intent.action.SEND".equals(action)) {
            if ("android.intent.action.INSERT_OR_EDIT".equals(action)) {
                if (NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE.equals(intent.getType())) {
                    checkPermissionAndStartRecordFromIntent(intent);
                    return true;
                }
                return handleEditIntent(intent);
            }
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleEditIntent(final Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra(NoteIntent.KEY_IS_PENDING, false);
        this.mPendingEditIntentTask = new Runnable() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleEditIntent$8(intent);
            }
        };
        if (booleanExtra) {
            return true;
        }
        this.mHandler.sendEmptyMessage(4);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleEditIntent$8(Intent intent) {
        this.mLoadTask = new PcEditNoteLoadTask(intent);
        this.mEditorController.showLoadingView(true);
        this.mLoadTask.commit();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleSendIntent(Intent intent) {
        this.mLoadTask = new BaseNormalNoteEditFragment.SendNoteLoadTask(intent);
        this.mLoadTask.commit();
        updateActionMode(EditorActionMode.VIEW, true);
        return true;
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public boolean loadNote(Intent intent, Bundle bundle) {
        return handleInstanceState(bundle) || handleIntent(intent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onChangeToMindNote() {
        if (isReadOnlyMode()) {
            return;
        }
        cancelSaveTask();
        if (getContext() != null) {
            NoteStore.delete(getContext(), new long[]{getNoteId()});
        }
        String string = this.mTitleEditor.getText().toString();
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(string)) {
            bundle.putString("note_title", string);
        }
        bundle.putInt("mind_content_type", 0);
        if (this.mWorkingNote != null) {
            bundle.putInt("widget_id", this.mWorkingNote.getWidgetId());
            bundle.putInt("widget_type", this.mWorkingNote.getWidgetType());
        }
        notifyAction(11, bundle);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean isBlankViewShow() {
        return (this.mContentView == null || this.mContentView.getVisibility() == 0) ? false : true;
    }

    @Override // com.miui.notes.basefeature.noteedit.ColumnsNativeNoteEditFragment
    protected void showBlankView(boolean z) {
        Float fValueOf = Float.valueOf(1.0f);
        Float fValueOf2 = Float.valueOf(0.0f);
        if (z) {
            Folme.useAt(this.mBlankView).state().to(ViewProperty.ALPHA, fValueOf);
            Folme.useAt(this.mContentView).state().to(ViewProperty.ALPHA, fValueOf2, new AnimConfig().addListeners(new TransitionListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.7
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    PcNoteEditFragment.this.mContentView.setVisibility(8);
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
            if (this.mEditorView != null) {
                this.mEditorView.setFocusable(false);
                this.mEditorView.setFocusableInTouchMode(false);
                this.mEditorView.clearFocus();
                this.mEditorView.setOnDragListener(null);
            }
            if (this.mEditorScrollView != null) {
                this.mEditorScrollView.setOnDragListener(null);
                return;
            }
            return;
        }
        Folme.useAt(this.mBlankView).state().to(ViewProperty.ALPHA, fValueOf2);
        this.mContentView.setVisibility(0);
        Folme.useAt(this.mContentView).state().to(ViewProperty.ALPHA, fValueOf);
        View view = this.mBlankView;
        if (view != null) {
            view.setOnDragListener(null);
        }
        if (this.mEditorView != null) {
            this.mEditorView.setFocusable(true);
            this.mEditorView.setFocusableInTouchMode(true);
            this.mEditorView.setOnDragListener(this.mEditorDragListener);
        }
        if (this.mEditorScrollView != null) {
            this.mEditorScrollView.setOnDragListener(this.mBlockDragListener);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onShowAddNewNote(boolean z) {
        this.mBlankView.setSelected(z);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void updateActionMode(EditorActionMode editorActionMode, boolean z) {
        if ((isReadOnlyMode() && (editorActionMode == EditorActionMode.EDIT || editorActionMode == EditorActionMode.EDIT_TITLE)) || editorActionMode == EditorActionMode.EDIT_RECODE) {
            return;
        }
        if (editorActionMode == EditorActionMode.EDIT) {
            if (this.mActionMode == null) {
                homeToEdit();
            } else if (this.mActionMode == EditorActionMode.VIEW) {
                viewToEdit();
            } else if (this.mActionMode == EditorActionMode.EDIT_TITLE) {
                titleToEdit();
            } else {
                Logger.INSTANCE.e(TAG, "ActionMode switch error!,from " + this.mActionMode.name() + " to " + editorActionMode.name());
            }
        } else if (editorActionMode == EditorActionMode.VIEW) {
            if (this.mActionMode == null) {
                homeToView();
            } else if (this.mActionMode == EditorActionMode.EDIT) {
                editToView();
            } else if (this.mActionMode == EditorActionMode.EDIT_RECODE) {
                recordToView();
            } else if (this.mActionMode == EditorActionMode.EDIT_TITLE) {
                titleToView();
            } else {
                Logger.INSTANCE.e(TAG, "ActionMode switch error!,from " + this.mActionMode.name() + " to " + editorActionMode.name());
            }
        } else if (editorActionMode == EditorActionMode.EDIT_TITLE) {
            if (this.mActionMode == EditorActionMode.VIEW) {
                viewToTitle();
            } else if (this.mActionMode == EditorActionMode.EDIT) {
                editToTitle();
            } else if (this.mActionMode != null) {
                Logger.INSTANCE.e(TAG, "ActionMode switch error!,from " + this.mActionMode.name() + " to " + editorActionMode.name());
            }
        }
        this.mActionMode = editorActionMode;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void onViewMode() {
        super.onViewMode();
        Logger.INSTANCE.i(TAG, "onViewMode");
        ((PcNoteHeadInfoController) this.mNoteExtraInfoController).show(true);
        getPcToolBarController().hide(false);
        notifyEditUiState(0);
        notifyAction(20, null);
        ((PcNoteRichEditor) this.mEditorView).setIsInEditMode(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void onEditMode() {
        super.onEditMode();
        Logger.INSTANCE.i(TAG, "onEditMode");
        ((PcNoteHeadInfoController) this.mNoteExtraInfoController).hide(true);
        getPcToolBarController().show(false);
        notifyEditUiState(1);
        notifyAction(21, null);
        this.mEditorView.setEnabled(true);
        this.mEditorView.setCursorVisible(true);
        ((PcNoteRichEditor) this.mEditorView).setIsInEditMode(true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void onTitleMode() {
        super.onTitleMode();
        Logger.INSTANCE.i(TAG, "onTitleMode");
        ((PcNoteHeadInfoController) this.mNoteExtraInfoController).show(true);
        getPcToolBarController().hide(false);
        notifyEditUiState(1);
        notifyAction(21, null);
        ((PcNoteRichEditor) this.mEditorView).setIsInEditMode(true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void editToView() {
        if (this.mActionFromToolbarRichText) {
            this.mActionFromToolbarRichText = false;
        }
        super.editToView();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void homeToView() {
        ((PcNoteRichEditor) this.mEditorView).setIsInEditMode(false);
        Logger.INSTANCE.i(TAG, "homeToView");
        super.homeToView();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void recordToView() {
        ((PcNoteRichEditor) this.mEditorView).setIsInEditMode(false);
        super.recordToView();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void viewToEdit() {
        Logger.INSTANCE.i(TAG, "viewToEdit");
        if (this.mActionFromToolbarRichText) {
            this.mActionFromToolbarRichText = false;
        }
        super.viewToEdit();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void homeToEdit() {
        Logger.INSTANCE.i(TAG, "homeToEdit");
        getPcToolBarController().resetRichTextStatus();
        super.homeToEdit();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void titleToEdit() {
        Logger.INSTANCE.d(TAG, "titleToEdit");
        if (this.mActionFromToolbarRichText) {
            this.mActionFromToolbarRichText = false;
        }
        super.titleToEdit();
    }

    protected void editToTitle() {
        Logger.INSTANCE.d(TAG, "editToTitle");
        editToTitle(true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void editToTitle(boolean z) {
        if (this.mActionFromToolbarRichText) {
            this.mActionFromToolbarRichText = false;
        }
        super.editToTitle(z);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void viewToTitle() {
        Logger.INSTANCE.d(TAG, "viewToTitle");
        if (this.mActionFromToolbarRichText) {
            this.mActionFromToolbarRichText = false;
        }
        super.viewToTitle();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void viewToRecord() {
        Logger.INSTANCE.d(TAG, "viewToRecord");
        super.viewToRecord();
        this.mIsRecording = true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void editToRecord() {
        Logger.INSTANCE.d(TAG, "editToRecord");
        super.editToRecord();
        ((PcNoteRichEditor) this.mEditorView).setIsInEditMode(true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void homeToRecord() {
        Logger.INSTANCE.d(TAG, "homeToRecord");
        super.homeToRecord();
        ((PcNoteRichEditor) this.mEditorView).setIsInEditMode(true);
    }

    private void setContentBackground(Drawable drawable) {
        this.mContentView.setBackground(drawable);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setWorkingNote(final WorkingNote workingNote, boolean z) {
        this.mNoteId = workingNote.getNoteId();
        this.mWorkingNote = workingNote;
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
        updateEditor(z);
        if (TextUtils.isEmpty(this.mWorkingNote.getContent()) && isSupportCreateMindNote()) {
            this.mEditorView.setHint(this.mEditorView.createHintEntrySpanText());
        } else {
            this.mEditorView.setHint("");
        }
        registerNoteContentObserver(true);
        updateNoteTheme(ResourceManager.getTheme(isSupportTheme() ? this.mWorkingNote.getThemeId() : 10), false);
        setContentBackground(getContext().getDrawable(R.drawable.pc_right_frame_bg));
        updateEditor(z);
        this.mNoteExtraInfoController.getView().post(new Runnable() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setWorkingNote$9(workingNote);
            }
        });
        showBlankView(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setWorkingNote$9(WorkingNote workingNote) {
        this.mNoteExtraInfoController.update(workingNote, getContentLength());
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
        PcAnnotationEditController pcAnnotationEditController = this.mPcAnnotationController;
        if (pcAnnotationEditController == null || !pcAnnotationEditController.isShowing()) {
            return;
        }
        this.mPcAnnotationController.saveAndHide();
    }

    /* JADX WARN: Code duplicated, block: B:23:0x00b1  */
    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void updateNoteTheme(Theme theme, boolean z) {
        final PcNoteEditFragment pcNoteEditFragment;
        long j;
        final int i;
        Context context = getContext();
        if (context == null) {
            return;
        }
        if (TextUtils.isEmpty(this.mTitleEditor.getText().toString())) {
            this.mTitleEditor.setHint(R.string.edit_note_hint_title);
        }
        Resources resources = context.getResources();
        this.mNoteExtraInfoController.updateStyle(theme, null);
        this.mEditorView.onThemeChanged(theme);
        getPcToolBarController().updateTheme(theme, null);
        Theme.EditorStyle editorStyle = theme.getEditorStyle(context, this.mWorkingNote.getModifiedDate());
        Rect rect = new Rect();
        getPcToolBarController().lock();
        if (theme instanceof AnimTheme) {
            AnimTheme animTheme = (AnimTheme) theme;
            final MamlView backgroundView = animTheme.getBackgroundView(getContext());
            if (backgroundView != null) {
                final ViewGroup viewGroup = (ViewGroup) getView();
                if (this.mCurrentTheme == null || !(this.mCurrentTheme instanceof AnimTheme)) {
                    j = 50;
                    i = 0;
                } else {
                    ((AnimTheme) this.mCurrentTheme).pause();
                    j = 800;
                    i = 1;
                }
                final long j2 = j;
                backgroundView.setAlpha(1.0f);
                animTheme.reload();
                rect.set(theme.getForegroundPadding(getActivity()));
                if (backgroundView.getParent() != null) {
                    ((ViewGroup) backgroundView.getParent()).removeView(backgroundView);
                }
                if (viewGroup != null) {
                    pcNoteEditFragment = this;
                    this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$updateNoteTheme$11(viewGroup, backgroundView, i, j2);
                        }
                    }, 0L);
                } else {
                    pcNoteEditFragment = this;
                }
            } else {
                pcNoteEditFragment = this;
            }
        } else {
            pcNoteEditFragment = this;
            rect.set(theme.getForegroundPadding(pcNoteEditFragment.getActivity()));
            pcNoteEditFragment.getPcToolBarController().unlock();
            pcNoteEditFragment.mIsChangingTheme = false;
        }
        Logger.INSTANCE.d(TAG, "updateNoteTheme theme " + theme.getId());
        pcNoteEditFragment.mEditorView.setTextColor(resources.getColor(editorStyle.mTextColor));
        pcNoteEditFragment.mEditorView.setHintTextColor(resources.getColor(editorStyle.mEditorHintColor));
        pcNoteEditFragment.mTitleEditor.setTextColor(resources.getColor(editorStyle.mTextColor));
        pcNoteEditFragment.mTitleEditor.setHintTextColor(resources.getColor(editorStyle.mEditorHintColor));
        if (z) {
            Rect rect2 = new Rect(rect);
            rect2.left = (int) (rect2.left + resources.getDimension(R.dimen.pc_note_editor_padding));
            rect2.right = (int) (rect2.right - resources.getDimension(R.dimen.pc_note_editor_padding));
            pcNoteEditFragment.changePaddingOnThemeChanged(editorStyle.mForeground, rect2);
        } else {
            pcNoteEditFragment.mEditAreaLayout.setBackground(editorStyle.mForeground);
        }
        pcNoteEditFragment.mEditorView.setGravity(editorStyle.mTextGravity);
        pcNoteEditFragment.mTitleEditor.setGravity(editorStyle.mTextGravity);
        CompatUtils.invokeCursorColor(pcNoteEditFragment.mEditorView, resources.getColor(editorStyle.mCursorColor), resources.getColor(editorStyle.mHandleColor));
        CompatUtils.invokeCursorColor(pcNoteEditFragment.mTitleEditor, resources.getColor(editorStyle.mCursorColor), resources.getColor(editorStyle.mHandleColor));
        CompatUtils.invokeCursorController(pcNoteEditFragment.mEditorView);
        pcNoteEditFragment.mTitleEditor.setHighlightColor(resources.getColor(editorStyle.mHighLightColor));
        pcNoteEditFragment.mEditorView.setHighlightColor(resources.getColor(editorStyle.mHighLightColor));
        pcNoteEditFragment.mEditorView.setLinkTextColor(resources.getColor(editorStyle.mLinkedColor));
        pcNoteEditFragment.mEditorView.setCursorVisible(false);
        if (pcNoteEditFragment.mCurrentTheme == null || !pcNoteEditFragment.mCurrentTheme.equals(theme)) {
            pcNoteEditFragment.mCurrentTheme = theme;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateNoteTheme$11(final ViewGroup viewGroup, View view, final int i, long j) {
        viewGroup.addView(view, i, new ViewGroup.LayoutParams(-1, -1));
        onAnimThemeBgViewChanged(view);
        viewGroup.postDelayed(new Runnable() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateNoteTheme$10(i, viewGroup);
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateNoteTheme$10(int i, ViewGroup viewGroup) {
        if (i > 0) {
            viewGroup.removeViewAt(0);
        }
        getPcToolBarController().unlock();
        this.mIsChangingTheme = false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public void insertImages(Uri... uriArr) {
        this.mEditorView.insertImages(new Runnable() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$insertImages$12();
            }
        }, uriArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$insertImages$12() {
        this.mEditorView.createNewRichTextData();
        saveNoteForce();
        this.mEditorView.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void insertDoodle(String str) {
        this.mEditorView.insertDoodleByName(str, null);
        this.mEditorView.createNewRichTextData();
        saveNoteForce();
        this.mEditorView.cacheSelection(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDoodle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mEditorView.insertDoodleByName(str, this.mEditingImageSpan);
        }
        this.mEditingImageSpan = null;
        this.mEditorView.createNewRichTextData();
        saveNoteForce();
        this.mEditorView.cacheSelection(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    public void annotateImage(BaseImageSpan baseImageSpan) {
        if (baseImageSpan instanceof NormalImageSpan) {
            lambda$annotateImage$13((NormalImageSpan) baseImageSpan);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: annotateImage, reason: merged with bridge method [inline-methods] */
    public void lambda$annotateImage$13(final NormalImageSpan normalImageSpan) {
        this.mEditorScrollView.setForbidScroll(false);
        PcAnnotationEditController pcAnnotationEditController = this.mPcAnnotationController;
        if (pcAnnotationEditController == null) {
            Context context = getContext();
            if (context == null) {
                context = NoteApp.getInstance();
            }
            this.mPcAnnotationController = new PcAnnotationEditController(context, normalImageSpan);
        } else if (pcAnnotationEditController.isShowing()) {
            this.mPcAnnotationController.saveNoForceAndHide(new Runnable() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$annotateImage$13(normalImageSpan);
                }
            });
            return;
        }
        this.mPcAnnotationController.setEditListener(new PcAnnotationEditController.EditListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.8
            @Override // com.miui.pc.feature.notes.PcAnnotationEditController.EditListener
            public void onOverLength() {
                PcNoteEditFragment.this.onImageAnnotationOverLength();
            }

            @Override // com.miui.pc.feature.notes.PcAnnotationEditController.EditListener
            public void onCompete(String str, boolean z) {
                normalImageSpan.setAnnotation(str);
                normalImageSpan.exitEditAnnotation();
                PcNoteEditFragment.this.mEditorView.clearRichTextData();
                PcNoteEditFragment.this.mEditorView.requestLayout();
                PcNoteEditFragment.this.mEditorView.refreshAllImageSpans();
                if (z) {
                    PcNoteEditFragment.this.saveNote(true);
                }
                PcNoteEditFragment.this.mPcAnnotationController = null;
            }
        });
        Point point = new Point();
        point.y = normalImageSpan.getAnnotationEditorY() - this.mEditorScrollView.getScrollY();
        point.x = (int) (this.mEditorScrollView.getX() + this.mEditorContainer.getX() + normalImageSpan.getBounds().left);
        normalImageSpan.setSelected(false);
        normalImageSpan.enterEditAnnotation();
        this.mEditorView.requestLayout();
        this.mEditorView.refreshAllImageSpans();
        this.mPcAnnotationController.show(this.mEditorContainer, point);
        Rect rect = new Rect(normalImageSpan.getBounds());
        rect.top = rect.bottom;
        rect.bottom += this.mRequestFocusChildAdd;
        this.mEditorScrollView.requestChildRectangleOnScreen(this.mEditorContainer, rect, false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateWorkingTextAndTitle() {
        if (!TextUtils.isEmpty(this.mTitleEditor.getText().toString())) {
            this.mWorkingNote.setWorkingTitle(this.mTitleEditor.getText().toString());
        } else if (TextUtils.isEmpty(this.mTitleEditor.getText().toString()) && !TextUtils.isEmpty(this.mWorkingNote.getTitle())) {
            this.mWorkingNote.setWorkingTitle("");
        }
        this.mWorkingNote.setWorkingText(this.mEditorView.getRichTextData().toString());
    }

    protected void showPictureChooserDialog(View view) {
        PcInsertPictureMenu pcInsertPictureMenu = new PcInsertPictureMenu(getContext());
        pcInsertPictureMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.9
            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onDismiss() {
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onShow() {
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onItemSelected(View view2, int i) {
                if (PcNoteEditFragment.this.mEditorView.canAddAttachments()) {
                    PcNoteEditFragment.this.mEditorView.cacheSelection(true);
                    switch (view2.getId()) {
                        case R.id.pick_from_camera /* 2131363192 */:
                            PcNoteEditFragment.this.launchMainScreenAction(1, null);
                            PcNoteEditFragment.this.showTakePhotoDialog();
                            break;
                        case R.id.pick_from_doodle /* 2131363193 */:
                            PcNoteEditFragment.this.mEditorView.cacheSelection(true);
                            PcNoteEditFragment.this.launchMainScreenAction(3, null);
                            PcNoteEditFragment.this.showDoodleDialog();
                            break;
                        case R.id.pick_from_pc /* 2131363194 */:
                            MiuiSynergySdk.getInstance().chooseFileOnSynergy(PcNoteEditFragment.this.getActivity(), new MiuiSynergySdk.ChooseFileCallback() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.9.1
                                @Override // com.xiaomi.mirror.synergy.MiuiSynergySdk.ChooseFileCallback
                                public void onFileChosen(ClipData clipData) {
                                    if (clipData != null) {
                                        Logger.INSTANCE.i(PcNoteEditFragment.TAG, "onFileChosen ");
                                        PcNoteEditFragment.this.insertImageFromClipData(clipData, false);
                                    }
                                }
                            });
                            break;
                        case R.id.pick_from_phone /* 2131363195 */:
                            PcNoteEditFragment.this.launchMainScreenAction(2, null);
                            PcNoteEditFragment.this.showSelectPhotoDialog();
                            break;
                    }
                }
            }
        });
        pcInsertPictureMenu.show(view, null);
    }

    protected void showMoreMenuDialog(View view) {
        PcTitleMoreMenu pcTitleMoreMenu = new PcTitleMoreMenu(getContext());
        pcTitleMoreMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.10
            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onDismiss() {
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onShow() {
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onItemSelected(View view2, int i) {
                int id = view2.getId();
                if (id == R.id.delete) {
                    PcNoteEditFragment.this.requestDeleteNote();
                } else {
                    if (id != R.id.set_alarm) {
                        return;
                    }
                    PcNoteEditFragment.this.showReminderDialog();
                }
            }
        });
        pcTitleMoreMenu.show(view, null);
    }

    private boolean doSaveWorkingNote() {
        if (this.mPcAnnotationController != null) {
            return false;
        }
        boolean zSaveNote = this.mWorkingNote.saveNote(true);
        notifyWorkingNoteContentSync();
        return zSaveNote;
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

    public void saveNoteForce() {
        if (this.mWorkingNote != null) {
            if (this.mHandler.hasMessages(1)) {
                this.mHandler.removeMessages(1);
            }
            saveNote(true);
            doSaveWorkingNote();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void startQueryMenuFolder(int i) {
        FolderStore.INSTANCE.query(getLoaderManager(), 0, 2, new PcNoteMenuLoaderCallbacks(i), "_id <> " + this.mFolderId);
    }

    private class PcNoteMenuLoaderCallbacks extends BaseNormalNoteEditFragment.NoteMenuLoaderCallbacks {
        public PcNoteMenuLoaderCallbacks(int i) {
            super(i);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteMenuLoaderCallbacks
        protected void updateExtraInfo(Cursor cursor, long[] jArr) {
            PcNoteEditFragment.this.mFolderMenuController.setData(cursor, jArr, PcNoteEditFragment.this.mFolderId);
            PcNoteEditFragment.this.mFolderMenuController.choose(this.position);
        }
    }

    class PcViewNoteLoadTask extends BaseNormalNoteEditFragment.ViewNoteLoadTask {
        PcViewNoteLoadTask(Intent intent, long j) {
            super(intent, j);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, WorkingNote workingNote) {
            super.onResult(taskManager, workingNote);
            PcNoteEditFragment pcNoteEditFragment = PcNoteEditFragment.this;
            pcNoteEditFragment.updateTitleUserQuery(pcNoteEditFragment.mTitleEditor.getText(), PcNoteEditFragment.this.mSearchText, null);
            PcNoteEditFragment.this.updateActionMode(EditorActionMode.VIEW, true);
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
            Logger.INSTANCE.i(PcNoteEditFragment.TAG, "onResult UpdateNoteLoadTask");
            if (PcNoteEditFragment.this.mWorkingNote == null || workingNote.getModifiedDate() != PcNoteEditFragment.this.mWorkingNote.getModifiedDate()) {
                Logger.INSTANCE.i(PcNoteEditFragment.TAG, "onResult UpdateNoteLoadTask remote save note");
                super.onResult(taskManager, workingNote);
                return;
            }
            Logger.INSTANCE.i(PcNoteEditFragment.TAG, "onResult UpdateNoteLoadTask same note");
            if (PcNoteEditFragment.this.mLoadTask != this) {
                return;
            }
            PcNoteEditFragment.this.mLoadTask = null;
            PcNoteEditFragment.this.showLoadingData(false);
            if (TextUtils.isEmpty(this.searchContent)) {
                PcNoteEditFragment.this.updateUserQuery("", false, null);
                PcNoteEditFragment.this.mSearchText = this.searchContent;
            } else {
                if (TextUtils.isEmpty(this.searchContent) || this.searchContent.equals(PcNoteEditFragment.this.mSearchText)) {
                    return;
                }
                PcNoteEditFragment.this.mSearchText = this.searchContent;
                PcNoteEditFragment pcNoteEditFragment = PcNoteEditFragment.this;
                pcNoteEditFragment.updateUserQuery(pcNoteEditFragment.mSearchText, false, null);
                PcNoteEditFragment.this.updateActionMode(EditorActionMode.VIEW, false);
            }
        }
    }

    public class PcEditNoteLoadTask extends BaseNormalNoteEditFragment.EditNoteLoadTask {
        public PcEditNoteLoadTask(Intent intent) {
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
                bundle.putLong("new_note_id", workingNoteLoadWorkingNote.getNoteId());
                PcNoteEditFragment.this.notifyAction(10, bundle);
            }
            return workingNoteLoadWorkingNote;
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onInsertAudio() {
            PcNoteEditFragment.this.startRecordWithCheckFromEditTask();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onEnterEdit(WorkingNote workingNote) {
            if (this.intent.getExtras() == null) {
                PcNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                return;
            }
            EditorActionMode editorActionMode = (EditorActionMode) this.intent.getExtras().get(NoteIntent.KEY_ACTION_MODE);
            if (editorActionMode != null && editorActionMode.equals(EditorActionMode.EDIT_RECODE)) {
                PcNoteEditFragment.this.updateRecordMode();
            } else {
                PcNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchMainScreenAction(int i, Parcelable parcelable) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainScreenBridgeActivity.BRIDGE_ACTION_RESULT);
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (Utils.isAboveSDK34()) {
            activity.registerReceiver(this.mPhotoResultReceiver, intentFilter, 2);
        } else {
            activity.registerReceiver(this.mPhotoResultReceiver, intentFilter);
        }
        Intent intent = new Intent(activity, (Class<?>) MainScreenBridgeActivity.class);
        intent.setFlags(67108864);
        intent.putExtra(MainScreenBridgeActivity.EXTRA_METHOD, i);
        if (parcelable != null) {
            intent.putExtra(MainScreenBridgeActivity.EXTRA_PARAMS, parcelable);
        }
        startActivity(intent, ActivityOptions.makeBasic().setLaunchDisplayId(0).toBundle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishMainScreenActivity() {
        Intent intent = new Intent(getActivity(), (Class<?>) MainScreenBridgeActivity.class);
        intent.setFlags(67108864);
        intent.setAction(MainScreenBridgeActivity.BRIDGE_ACTION_FINISH);
        startActivity(intent, ActivityOptions.makeBasic().setLaunchDisplayId(0).toBundle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTakePhotoDialog() {
        if (isAdded()) {
            PcLockScreenDialog pcLockScreenDialog = new PcLockScreenDialog(getContext(), R.layout.pc_note_take_photo_lock_dialog, new PcLockScreenDialog.ActionListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.11
                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onConfirm() {
                }

                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onCancel() {
                    PcNoteEditFragment.this.finishMainScreenActivity();
                }
            });
            pcLockScreenDialog.show(this.mRootView);
            this.mShowingLockScreenDialog = pcLockScreenDialog;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSelectPhotoDialog() {
        if (isAdded()) {
            PcLockScreenDialog pcLockScreenDialog = new PcLockScreenDialog(getContext(), R.layout.pc_note_select_photo_lock_dialog, new PcLockScreenDialog.ActionListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.12
                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onConfirm() {
                }

                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onCancel() {
                    PcNoteEditFragment.this.finishMainScreenActivity();
                }
            });
            pcLockScreenDialog.show(this.mRootView);
            this.mShowingLockScreenDialog = pcLockScreenDialog;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDoodleDialog() {
        if (isAdded()) {
            PcLockScreenDialog pcLockScreenDialog = new PcLockScreenDialog(getContext(), R.layout.pc_note_doodle_lock_dialog, new PcLockScreenDialog.ActionListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.13
                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onConfirm() {
                }

                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onCancel() {
                    PcNoteEditFragment.this.finishMainScreenActivity();
                }
            });
            pcLockScreenDialog.show(this.mRootView);
            this.mShowingLockScreenDialog = pcLockScreenDialog;
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

    protected void notifyWorkingNoteContentSync() {
        if (isAdded()) {
            Intent intent = new Intent();
            intent.setAction(FrameConstant.ACTION_DUAL_SCREEN_STATE_CONTENT_SYNC);
            intent.putExtra(FrameConstant.PARAM_SCREEN_ID, 1);
            intent.putExtra("note_id", getNoteId());
            if (getActivity() != null) {
                getActivity().sendBroadcast(intent);
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showDualScreenEditTipDialog() {
        if (isAdded() && this.mDualScreenEditTipDialog == null) {
            PcLockScreenDialog pcLockScreenDialog = new PcLockScreenDialog(getContext(), R.layout.pc_note_phone_edit_lock_dialog, new PcLockScreenDialog.ActionListener() { // from class: com.miui.pc.feature.notes.PcNoteEditFragment.14
                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onCancel() {
                }

                @Override // com.miui.pc.feature.notes.PcLockScreenDialog.ActionListener
                public void onConfirm() {
                    PcNoteEditFragment.this.notifyEditUiState(3);
                }
            });
            this.mDualScreenEditTipDialog = pcLockScreenDialog;
            pcLockScreenDialog.setCanceledOnTouchOutside(false);
            this.mDualScreenEditTipDialog.show(this.mRootView);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected OnKeyDownAndShortCutListener createKeyListenerWrapper(OnKeyDownAndShortCutListener onKeyDownAndShortCutListener) {
        return new OutKeyListenerWrapper(onKeyDownAndShortCutListener);
    }

    class OutKeyListenerWrapper implements OnKeyDownAndShortCutListener {
        private final OnKeyDownAndShortCutListener mOutKeyListener;

        public OutKeyListenerWrapper(OnKeyDownAndShortCutListener onKeyDownAndShortCutListener) {
            this.mOutKeyListener = onKeyDownAndShortCutListener;
        }

        @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
        public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
            OnKeyDownAndShortCutListener onKeyDownAndShortCutListener = this.mOutKeyListener;
            if (onKeyDownAndShortCutListener != null) {
                return onKeyDownAndShortCutListener.onKeyShortcut(i, keyEvent);
            }
            return false;
        }

        @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            if (i == 19 || i == 20) {
                if (PcNoteEditFragment.this.isInEditAction()) {
                    return false;
                }
            } else if (i != 113 && i != 114) {
                return false;
            }
            OnKeyDownAndShortCutListener onKeyDownAndShortCutListener = this.mOutKeyListener;
            if (onKeyDownAndShortCutListener != null) {
                return onKeyDownAndShortCutListener.onKeyDown(i, keyEvent);
            }
            return false;
        }

        @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
        public boolean onKeyUp(int i, KeyEvent keyEvent) {
            OnKeyDownAndShortCutListener onKeyDownAndShortCutListener = this.mOutKeyListener;
            if (onKeyDownAndShortCutListener != null) {
                return onKeyDownAndShortCutListener.onKeyDown(i, keyEvent);
            }
            return false;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public boolean saveNote(boolean z) {
        String string;
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
}
