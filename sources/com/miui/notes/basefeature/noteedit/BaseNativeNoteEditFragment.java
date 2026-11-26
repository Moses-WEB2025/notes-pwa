package com.miui.notes.basefeature.noteedit;

import android.animation.Animator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.util.Pair;
import android.view.ContentInfo;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.OnReceiveContentListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.base.BaseActivity;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.async.TaskManager;
import com.miui.common.view.richtext.HighLightSpan;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.base.DoodleParams;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.maml.component.MamlView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.ai.AigcPicMetaData;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.cta.CTAActivity;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.doodle.feature.DoodleFragment;
import com.miui.notes.editor.BaseNoteRichEditorController;
import com.miui.notes.editor.NoteRichEditor;
import com.miui.notes.editor.NoteRichEditorController;
import com.miui.notes.editor.RichEditorDragListener;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.provider.Notes;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.ClipDataHelper;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.BaseDragListener;
import com.miui.notes.ui.LoadNoteThemeResTask;
import com.miui.notes.ui.SnsEditActivity;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.notes.ui.widget.AnnotationEditController;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.ExternalTextWatcher;
import com.miui.richeditor.RichEditScrollView;
import com.miui.richeditor.UndoManager;
import com.miui.richeditor.constant.MiuiFontName;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.share.RenderBuilder;
import com.miui.richeditor.share.render.Render;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.theme.Theme;
import com.miui.richeditor.util.NoteClipManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import miuix.animation.utils.EaseManager;
import miuix.appcompat.app.ProgressDialog;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseNativeNoteEditFragment extends BaseNormalNoteEditFragment {
    protected static final String BUNDLE_KEY_EDIT_CACHE_SELECTION = "edit_cache_selection";
    protected static final String BUNDLE_KEY_EDIT_IMAGE_SPAN_RANGE = "edit_image_span_range";
    private static final String TAG = "Notes:BaseNativeNoteEditFragment";
    private View editContentFrame;
    protected LinearLayout mEditAreaLayout;
    protected BaseNoteRichEditorController mEditorController;
    protected LinearLayout mEditorMaskView;
    protected RichEditScrollView mEditorScrollView;
    public NoteRichEditor mEditorView;
    protected EditText mTitleEditor;
    protected boolean mEnableEdit = true;
    public AnnotationEditController mAnnotationController = null;
    private boolean mIsNeedScrollToTop = false;
    protected NormalImageSpan mEditingImageSpan = null;
    protected boolean mIsNeedDelay = false;
    boolean isOnPause = false;
    private long mShowTime = 0;
    private ProgressDialog mDialog = null;
    private Render mRender = null;
    private Runnable renderGenerateTask = null;
    protected TextWatcher mTitleEditorTextWatcher = new TextWatcher() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.1
        boolean iReplacing = false;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if ((BaseNativeNoteEditFragment.this.mWorkingNote == null || !TextUtils.equals(editable, BaseNativeNoteEditFragment.this.mWorkingNote.getTitle())) && !this.iReplacing) {
                BaseNativeNoteEditFragment.this.mTitleEditor.removeTextChangedListener(this);
                for (int i = 0; i < editable.length(); i++) {
                    if (editable.charAt(i) == '\n') {
                        editable.delete(i, i + 1);
                    }
                }
                BaseNativeNoteEditFragment.this.mTitleEditor.addTextChangedListener(this);
                this.iReplacing = true;
                deleteSpans(editable, 0, editable.length(), CharacterStyle.class);
                deleteSpans(editable, 0, editable.length(), BulletSpan.class);
                BaseNativeNoteEditFragment baseNativeNoteEditFragment = BaseNativeNoteEditFragment.this;
                baseNativeNoteEditFragment.appendAndMarkQueryTitle(editable, baseNativeNoteEditFragment.mSearchText);
                String string = editable.toString();
                if (string.contains(String.valueOf(HtmlParser.MEDIA_DUMMY_CHAR))) {
                    BaseNativeNoteEditFragment.this.mTitleEditor.removeTextChangedListener(this);
                    BaseNativeNoteEditFragment.this.mTitleEditor.setText(string.replace(String.valueOf(HtmlParser.MEDIA_DUMMY_CHAR), ""));
                    BaseNativeNoteEditFragment.this.mTitleEditor.addTextChangedListener(this);
                }
                this.iReplacing = false;
                BaseNativeNoteEditFragment baseNativeNoteEditFragment2 = BaseNativeNoteEditFragment.this;
                baseNativeNoteEditFragment2.updateTextCount(baseNativeNoteEditFragment2.getContentLength());
                boolean z = BaseNativeNoteEditFragment.this.mEditorView.getText().length() == 0 && BaseNativeNoteEditFragment.this.mTitleEditor.getText().length() == 0;
                BaseNativeNoteEditFragment.this.asyncSaveNote();
                if (BaseNativeNoteEditFragment.this.isInEditAction() && BaseNativeNoteEditFragment.this.getActionBarController() != null) {
                    BaseNativeNoteEditFragment.this.getActionBarController().updateActionMode(BaseNativeNoteEditFragment.this.mActionMode, z, false);
                }
                if (BaseNativeNoteEditFragment.this.mTitleEditor.getText() != null && !TextUtils.isEmpty(BaseNativeNoteEditFragment.this.mTitleEditor.getText().toString())) {
                    BaseNativeNoteEditFragment.this.mEditorView.setTitle(BaseNativeNoteEditFragment.this.mTitleEditor.getText().toString());
                } else if (TextUtils.isEmpty(BaseNativeNoteEditFragment.this.mTitleEditor.getText().toString())) {
                    BaseNativeNoteEditFragment.this.mEditorView.setTitle("");
                }
            }
        }

        private <T> boolean deleteSpans(Editable editable, int i, int i2, Class<T> cls) {
            Object[] spans = editable.getSpans(i, i2, cls);
            for (Object obj : spans) {
                editable.removeSpan(obj);
            }
            return spans.length != 0;
        }
    };
    protected BroadcastReceiver mLongScreenshotReceiver = new LongScreenshotReceiver(this);
    Runnable mShowCreatingProgressDialogTask = new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.26
        @Override // java.lang.Runnable
        public void run() {
            BaseNativeNoteEditFragment.this.mDialog = new ProgressDialog(BaseNativeNoteEditFragment.this.getContext());
            BaseNativeNoteEditFragment.this.mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.26.1
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialogInterface) {
                    if (BaseNativeNoteEditFragment.this.mRender != null) {
                        BaseNativeNoteEditFragment.this.mRender.shouldBlock = true;
                    }
                    if (BaseNativeNoteEditFragment.this.renderGenerateTask != null) {
                        BaseNativeNoteEditFragment.this.mHandler.removeCallbacks(BaseNativeNoteEditFragment.this.renderGenerateTask);
                        BaseNativeNoteEditFragment.this.renderGenerateTask = null;
                    }
                }
            });
            BaseNativeNoteEditFragment.this.mDialog.setMessage(BaseNativeNoteEditFragment.this.getResources().getString(R.string.dlg_info_creating_message));
            BaseNativeNoteEditFragment.this.mDialog.setIndeterminate(true);
            BaseNativeNoteEditFragment.this.mDialog.setCanceledOnTouchOutside(false);
            BaseNativeNoteEditFragment.this.mShowTime = System.currentTimeMillis();
            BaseNativeNoteEditFragment.this.mDialog.show();
        }
    };

    public abstract void annotateImage(BaseImageSpan baseImageSpan);

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void checkEditEnable() {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected abstract int getAutoSaveCommonDuration();

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getThemeResId() {
        return R.style.NoteTheme_Edit;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(NoteLoadDataEntity noteLoadDataEntity, boolean z) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScreenshotAction() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jCurrentTimeMillis, jCurrentTimeMillis, 11, 0.0f, 0.0f, 0);
        try {
            this.mEditorView.getScrollView().onTouchEvent(motionEventObtain);
        } finally {
            motionEventObtain.recycle();
        }
    }

    static class LongScreenshotReceiver extends BroadcastReceiver {
        WeakReference<BaseNativeNoteEditFragment> weakReference;

        public LongScreenshotReceiver(BaseNativeNoteEditFragment baseNativeNoteEditFragment) {
            this.weakReference = new WeakReference<>(baseNativeNoteEditFragment);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            WeakReference<BaseNativeNoteEditFragment> weakReference = this.weakReference;
            BaseNativeNoteEditFragment baseNativeNoteEditFragment = weakReference == null ? null : weakReference.get();
            if (baseNativeNoteEditFragment != null) {
                baseNativeNoteEditFragment.handleScreenshotAction();
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean isLoadingData() {
        NoteRichEditor noteRichEditor = this.mEditorView;
        if (noteRichEditor != null) {
            return noteRichEditor.isLoadingViewVisible();
        }
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, final Bundle bundle) {
        super.onViewInflated(view, bundle);
        initNoteEditor(view);
        Log.d(TAG, "onViewInflated mPagePrepare: " + this.mPagePrepare);
        this.mPagePrepare = true;
        this.mWindowInitFlags = getActivity().getWindow().getAttributes().flags;
        if (bundle != null) {
            this.mIsFromFloatWindow = bundle.getBoolean(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, false);
            if (this.mIsFromFloatWindow) {
                this.mNeedDelayShowInput = true;
            }
            this.mEditorView.addPendingTask(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onViewInflated$0(bundle);
                }
            });
        }
        if (LiteUtils.isLiteOrMiddle() && !LiteUtils.isNewLiteOrMiddle()) {
            ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.content).getParent().getParent();
            this.mAnimThemeLiteBgView = new ImageView(getContext());
            viewGroup.addView(this.mAnimThemeLiteBgView, 0, new ViewGroup.LayoutParams(-1, -1));
        }
        registerLongScreenShot(true);
        Theme theme = ResourceManager.getTheme(0);
        Intent intent = getActivity().getIntent();
        if (intent != null && bundle == null) {
            theme = ResourceManager.getTheme(!LiteUtils.isNewLiteOrMiddle() ? intent.getIntExtra(NoteIntent.KEY_NOTE_THEME_ID, 0) : 0);
        } else if (bundle != null) {
            theme = ResourceManager.getTheme(bundle.getInt("last_theme_id"));
        }
        if (RomUtils.isPadMode()) {
            theme = ResourceManager.getTheme(0);
        }
        updateNoteTheme(theme, false);
        Logger.INSTANCE.d(TAG, "onActivityCreated loadNote(getActivity().getIntent(), savedInstanceState) success = " + loadNote(getActivity().getIntent(), bundle));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$0(Bundle bundle) {
        if (this.mEditorView.getText() != null && this.mEditorView.getText().length() == 0 && bundle.getBoolean("is_edit_text_enable") && isContentShow()) {
            updateActionMode(EditorActionMode.EDIT, true);
        } else {
            updateActionMode(EditorActionMode.VIEW, true);
        }
        if (PreferenceUtils.hasTempAudioFile()) {
            this.mEditorView.pTempSaveAudioFile = PreferenceUtils.getTempAudioFile();
            PreferenceUtils.removeTempAudioFile();
        }
        this.mEditorView.restoreInstanceState(bundle);
        if (bundle.containsKey(BUNDLE_KEY_EDIT_IMAGE_SPAN_RANGE)) {
            int[] intArray = bundle.getIntArray(BUNDLE_KEY_EDIT_IMAGE_SPAN_RANGE);
            try {
                this.mEditingImageSpan = ((NormalImageSpan[]) this.mEditorView.getText().getSpans(intArray[0], intArray[1], NormalImageSpan.class))[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        NoteRichEditor noteRichEditor = this.mEditorView;
        if (noteRichEditor == null) {
            return;
        }
        bundle.putIntArray(BUNDLE_KEY_EDIT_CACHE_SELECTION, noteRichEditor.getCacheEditSelection());
        this.mEditorView.saveInstanceState(bundle);
        if (this.mEditingImageSpan != null) {
            bundle.putIntArray(BUNDLE_KEY_EDIT_IMAGE_SPAN_RANGE, new int[]{this.mEditorView.getText().getSpanStart(this.mEditingImageSpan), this.mEditorView.getText().getSpanEnd(this.mEditingImageSpan)});
        }
        super.onSaveInstanceState(bundle);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment
    public void onVisibilityChanged(boolean z) {
        super.onVisibilityChanged(z);
        if (z) {
            return;
        }
        this.mEditorController.hideInputMethod();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        this.isOnPause = false;
        super.onResume();
        if (!getIsNeedListenKeyboard()) {
            setNeedListenKeyboard(true);
        }
        if (this.mWorkingNote != null) {
            this.mNoteExtraInfoController.update(this.mWorkingNote, getContentLength());
        }
        if (FloatWindowManagerAgent.isContinuousExcerpt() && isThisNoteDoExcerpt()) {
            FloatWindowManagerAgent.pauseContinuousExcerpt();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.isOnPause = false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.isOnPause = true;
        if (FloatWindowManagerAgent.isContinuousExcerpt() && isThisNoteDoExcerpt()) {
            FloatWindowManagerAgent.resumeContinuousExcerpt();
        }
        NoteRichEditor noteRichEditor = this.mEditorView;
        if (noteRichEditor != null) {
            noteRichEditor.stopMediaPlayerIfNeed();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Intent intent;
        registerLongScreenShot(false);
        FragmentActivity activity = getActivity();
        AnnotationEditController annotationEditController = this.mAnnotationController;
        if (annotationEditController != null) {
            annotationEditController.cancel();
            this.mAnnotationController = null;
        }
        if (activity != null) {
            KeyguardManager keyguardManager = (KeyguardManager) getContext().getSystemService("keyguard");
            boolean z = PermissionNewUtils.isFirstShowCTADialog;
            if (keyguardManager != null && !keyguardManager.isKeyguardLocked() && !z && (intent = activity.getIntent()) != null) {
                intent.setAction("android.intent.action.MAIN");
                activity.setIntent(intent);
            }
        }
        PermissionNewUtils.isFirstShowCTADialog = false;
        super.onDestroy();
    }

    public boolean annotationHide() {
        AnnotationEditController annotationEditController = this.mAnnotationController;
        if (annotationEditController == null || !annotationEditController.isShowing()) {
            return false;
        }
        this.mAnnotationController.hideWithSoftInput();
        this.mEditorView.restoreTextWatcher();
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        Logger.INSTANCE.d(TAG, this + " onDestroyView");
        super.onDestroyView();
        EditText editText = this.mTitleEditor;
        if (editText != null) {
            editText.removeTextChangedListener(this.mTitleEditorTextWatcher);
        }
        NoteRichEditor noteRichEditor = this.mEditorView;
        if (noteRichEditor != null) {
            noteRichEditor.clearTextWatcher();
            this.mEditorView.setExternalTextWatcher(null);
            this.mEditorView.pauseAudio();
            this.mEditorView.clearMediaPlayer();
        }
        ResourceManager.clearThemes();
        if (this.mCurrentTheme instanceof AnimTheme) {
            onAnimThemeBgViewChanged(null);
        }
        if (Build.VERSION.SDK_INT >= 30) {
            getActivity().getWindow().setSoftInputMode(0);
        } else {
            if (UIUtils.isInFullWindowGestureMode(getActivity())) {
                return;
            }
            getActivity().getWindow().setSoftInputMode(0);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        BaseNoteRichEditorController baseNoteRichEditorController = this.mEditorController;
        if (baseNoteRichEditorController != null) {
            baseNoteRichEditorController.dismissShowingPopupWindow();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        updateWindowStatus(this.mIsForceDark);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setmEidtDoodleResult(Intent intent) {
        String stringExtra = intent.getStringExtra(DoodleFragment.KEY_IMAGE_NAME);
        final String stringExtra2 = intent.hasExtra(DoodleFragment.KEY_EDIT_IMAGE_ID) ? intent.getStringExtra(DoodleFragment.KEY_EDIT_IMAGE_ID) : "";
        if (!TextUtils.isEmpty(stringExtra)) {
            if (!DoodleApplication.SaveFlagNoChange.contentEquals(stringExtra)) {
                final String strSaveImageFile = AttachmentUtils.saveImageFile(NoteApp.getInstance(), new File(stringExtra));
                if (!TextUtils.isEmpty(strSaveImageFile)) {
                    if (isNeedPendingInsertContent()) {
                        this.mEditorController.setPendingTaskOnDataLoaded(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.2
                            @Override // java.lang.Runnable
                            public void run() {
                                BaseNativeNoteEditFragment.this.updateDoodle(strSaveImageFile, stringExtra2);
                            }
                        });
                    } else {
                        updateDoodle(strSaveImageFile, stringExtra2);
                    }
                } else {
                    Logger.INSTANCE.d(TAG, "REQUEST_CODE_DOODLE_EDIT saveUpdatePath is null");
                }
            }
            this.mEditingImageSpan = null;
            return;
        }
        NormalImageSpan normalImageSpan = this.mEditingImageSpan;
        if (normalImageSpan != null) {
            this.mEditorView.deleteImageSpan(normalImageSpan, true);
        }
        this.mEditingImageSpan = null;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setmSyncAccountResult(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1 && this.mActionMode == EditorActionMode.EDIT) {
            this.mEditorController.showInputMethod();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        getActionBarController().updateMenu(menu);
        if (FolderModel.isCannotMoveFolder(this.mFolderId)) {
            menu.findItem(R.id.move_to).setVisible(false);
        }
        if (LiteUtils.isSuperLite()) {
            menu.findItem(R.id.set_alarm).setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.ui.BaseActionBarController.EditCallback
    public void onNavigationUp() {
        if (this.mEditorView.getShowSoftInputOnFocus()) {
            forceHideSoftInput();
        }
        super.onNavigationUp();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initNoteInfoHeader() {
        this.mNoteExtraInfoController = createNoteExtraInfoController(this.mRootView.findViewById(R.id.note_extra_info), this.mRootView.findViewById(R.id.note_folder_info));
        this.mNoteExtraInfoController.bindFragment(this);
        this.mNoteExtraInfoController.setNoteMenuClickListener(new INoteInfoHeader.NoteMenuClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.3
            @Override // com.miui.notes.ui.view.INoteInfoHeader.NoteMenuClickListener
            public boolean onMenuClick() {
                if (BaseNativeNoteEditFragment.this.getContext() == null || !BaseNativeNoteEditFragment.this.isThisNoteDoExcerpt()) {
                    return true;
                }
                Toast.makeText(BaseNativeNoteEditFragment.this.getContext().getApplicationContext(), R.string.excerpting, 0).show();
                return false;
            }

            @Override // com.miui.notes.ui.view.INoteInfoHeader.NoteMenuClickListener
            public void onItemChecked(long j, long[] jArr, String str) {
                BaseNativeNoteEditFragment.this.moveCheckedItems(j, jArr, str);
            }

            @Override // com.miui.notes.ui.view.INoteInfoHeader.NoteMenuClickListener
            public void onItemPerformClick(int i) {
                BaseNativeNoteEditFragment.this.startQueryMenuFolder(i);
            }
        });
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        Logger.INSTANCE.d(TAG, "onBackPressed");
        if (dismissAllDialogOrPanel()) {
            return true;
        }
        this.mIsKillSaveTaskBeforeDestroy = true;
        killSaveTask();
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected BaseDragListener getActionBarBaseDragListener() {
        return new BaseDragListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.4
            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragStarted(View view, DragEvent dragEvent) {
                if (dragEvent.getLocalState() instanceof BaseImageSpan) {
                    return true;
                }
                return BaseNativeNoteEditFragment.this.mEditorView.isValidDragEventData(dragEvent);
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragEntered(View view, DragEvent dragEvent) {
                if (dragEvent.getLocalState() instanceof BaseImageSpan) {
                    BaseNativeNoteEditFragment.this.mEditorView.selectLineInDragMode(-1);
                    return true;
                }
                return view.onDragEvent(dragEvent);
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragDrop(View view, DragEvent dragEvent) {
                if (dragEvent.getLocalState() instanceof BaseImageSpan) {
                    return false;
                }
                return BaseNativeNoteEditFragment.this.insertImageFromDragEvent(dragEvent, true);
            }
        };
    }

    protected void initNoteTitle(View view) {
        EditText editText = (EditText) view.findViewById(R.id.note_title);
        this.mTitleEditor = editText;
        editText.setText("");
        this.mTitleEditor.setTypeface(Typeface.create(MiuiFontName.SEMIBOLD, 0));
        this.mTitleEditor.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        this.mTitleEditor.setOnDragListener(getDragListener());
        this.mTitleEditor.setLineSpacing(0.0f, 1.5f);
        this.mTitleEditor.setLineHeight(NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.edit_title_line_height));
        this.mEditAreaLayout = (LinearLayout) view.findViewById(R.id.ll_note_edit);
        this.mTitleEditor.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (BaseNativeNoteEditFragment.this.mEnableEdit) {
                    BaseNativeNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT_TITLE, true);
                }
            }
        });
        this.mTitleEditor.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.6
            private float downX;
            private float downY;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (BaseNativeNoteEditFragment.this.isReadOnlyMode()) {
                        view2.clearFocus();
                        return false;
                    }
                    this.downX = motionEvent.getX();
                    this.downY = motionEvent.getY();
                } else if (action == 1 && Math.abs(motionEvent.getX() - this.downX) <= 30.0f && Math.abs(motionEvent.getY() - this.downY) <= 30.0f && BaseNativeNoteEditFragment.this.mEnableEdit) {
                    BaseNativeNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT_TITLE, true);
                }
                return false;
            }
        });
        this.mTitleEditor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.7
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent == null || keyEvent.getKeyCode() != 66) {
                    return false;
                }
                if (BaseNativeNoteEditFragment.this.mEnableEdit) {
                    BaseNativeNoteEditFragment.this.mEditorView.setSelection(0);
                    BaseNativeNoteEditFragment.this.mEditorView.requestFocus();
                    BaseNativeNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                }
                return true;
            }
        });
        this.mTitleEditor.addTextChangedListener(this.mTitleEditorTextWatcher);
        if (TextUtils.isEmpty(this.mTitleEditor.getText().toString())) {
            this.mTitleEditor.setHint(R.string.edit_note_hint_title);
        }
        this.mTitleEditor.clearFocus();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initNoteEditor(View view) {
        Logger.INSTANCE.d(TAG, "initNoteEditor");
        initNoteTitle(view);
        RichEditScrollView richEditScrollView = (RichEditScrollView) view.findViewById(R.id.editor_scroll_view);
        this.mEditorScrollView = richEditScrollView;
        richEditScrollView.setOnDragListener(new BaseDragListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.8
            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragEntered(View view2, DragEvent dragEvent) {
                return true;
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragStarted(View view2, DragEvent dragEvent) {
                if (dragEvent.getLocalState() instanceof BaseImageSpan) {
                    return true;
                }
                return BaseNativeNoteEditFragment.this.mEditorView.isValidDragEventData(dragEvent);
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected void handleDragLocation(View view2, DragEvent dragEvent) {
                if (dragEvent.getLocalState() instanceof BaseImageSpan) {
                    if (dragEvent.getY() < BaseNativeNoteEditFragment.this.mEditorView.getY()) {
                        BaseNativeNoteEditFragment.this.mEditorView.selectLineInDragMode(-1);
                    } else {
                        BaseNativeNoteEditFragment.this.mEditorView.selectLineInDragMode(BaseNativeNoteEditFragment.this.mEditorView.getLineCount() - 1);
                    }
                }
                super.handleDragLocation(view2, dragEvent);
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragDrop(View view2, DragEvent dragEvent) {
                if (dragEvent.getLocalState() instanceof BaseImageSpan) {
                    BaseNativeNoteEditFragment.this.getDragEventMimeType(dragEvent);
                    return false;
                }
                return BaseNativeNoteEditFragment.this.insertImageFromDragEvent(dragEvent, true);
            }
        });
        if (view.findViewById(R.id.edit_content) != null) {
            view.findViewById(R.id.edit_content).setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.9
                Rect editorRect = new Rect();
                Rect contentRect = new Rect();

                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view2, MotionEvent motionEvent) {
                    if (motionEvent.getAction() != 0 && motionEvent.getAction() != 1) {
                        return false;
                    }
                    BaseNativeNoteEditFragment.this.mEditorView.getGlobalVisibleRect(this.editorRect);
                    view2.getGlobalVisibleRect(this.contentRect);
                    return motionEvent.getY() <= ((float) (this.editorRect.bottom - this.contentRect.top)) || motionEvent.getX() < ((float) (this.editorRect.left - this.contentRect.left)) || motionEvent.getX() > ((float) (this.editorRect.right - this.contentRect.left));
                }
            });
        }
        View viewFindViewById = view.findViewById(R.id.edit_content);
        this.editContentFrame = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (BaseNativeNoteEditFragment.this.mEnableEdit) {
                    BaseNativeNoteEditFragment.this.mEditorView.setSelection(BaseNativeNoteEditFragment.this.mEditorView.length());
                    BaseNativeNoteEditFragment.this.mEditorView.requestFocus();
                    BaseNativeNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                }
            }
        });
        this.mEditorView = (NoteRichEditor) view.findViewById(R.id.rich_editor);
        handleReceiveContent();
        this.mNoteRichEditor = this.mEditorView;
        this.mEditorView.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.11
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view2, boolean z) {
                if (!z && !BaseNativeNoteEditFragment.this.mEditorView.ismIsRemoveFocus()) {
                    BaseNativeNoteEditFragment.this.mEditToolbarController.hide(false);
                }
                BaseNativeNoteEditFragment.this.mEditorView.setmIsRemoveFocus(false);
            }
        });
        this.mNoteRichEditor.setActivity(getActivity());
        this.mEditorView.setScrollView(this.mEditorScrollView);
        this.mEditorView.setEditAreaView(this.mEditAreaLayout);
        this.mEditorView.setMinimumHeight((int) (((double) DisplayUtils.getRealHeight(getActivity())) * 0.3d));
        this.mEditorView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.12
            float downX = 0.0f;
            float downY = 0.0f;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.downX = motionEvent.getX();
                    this.downY = motionEvent.getY();
                } else if (action == 1) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    if (Math.abs(x - this.downX) < 20.0f && Math.abs(y - this.downY) < 20.0f) {
                        BaseNativeNoteEditFragment.this.onEditorClick(false);
                    }
                } else if (action == 3) {
                    this.downX = 0.0f;
                    this.downY = 0.0f;
                }
                return BaseNativeNoteEditFragment.this.mIsInBrowseMode;
            }
        });
        this.mEditorView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                BaseNativeNoteEditFragment.this.onEditorClick(false);
            }
        });
        this.mEditorView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.14
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                BaseNativeNoteEditFragment.this.onEditorLongClick();
                return false;
            }
        });
        this.mEditorView.setExternalTextWatcher(new ExternalTextWatcher() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.15
            @Override // com.miui.richeditor.ExternalTextWatcher, android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                BaseNativeNoteEditFragment baseNativeNoteEditFragment = BaseNativeNoteEditFragment.this;
                baseNativeNoteEditFragment.updateTextCount(baseNativeNoteEditFragment.getContentLength());
                if (BaseNativeNoteEditFragment.this.mNestedHeaderLayout != null) {
                    BaseNativeNoteEditFragment.this.mNestedHeaderLayout.setAutoTriggerClose(true);
                }
            }

            @Override // com.miui.richeditor.ExternalTextWatcher
            public void afterTextActuallyChanged(Editable editable) {
                BaseNativeNoteEditFragment.this.mIsEditorTextChanged = true;
                BaseNativeNoteEditFragment.this.asyncSaveNote();
                boolean z = editable.length() == 0;
                if (BaseNativeNoteEditFragment.this.isInEditAction() && BaseNativeNoteEditFragment.this.getActionBarController() != null) {
                    BaseNativeNoteEditFragment.this.getActionBarController().updateActionMode(BaseNativeNoteEditFragment.this.mActionMode, z, false);
                }
                if (z && BaseNativeNoteEditFragment.this.isSupportCreateMindNote() && !BaseNativeNoteEditFragment.this.isReadOnlyMode()) {
                    BaseNativeNoteEditFragment.this.mEditorView.setAlignNormal();
                    BaseNativeNoteEditFragment.this.mEditorView.setHint(BaseNativeNoteEditFragment.this.mEditorView.createHintEntrySpanText());
                } else {
                    if ("".contentEquals(BaseNativeNoteEditFragment.this.mEditorView.getHint())) {
                        return;
                    }
                    BaseNativeNoteEditFragment.this.mEditorView.setHint("");
                }
            }

            @Override // com.miui.richeditor.ExternalTextWatcher
            public void afterCheckBoxChanged() {
                BaseNativeNoteEditFragment.this.asyncSaveNote();
            }
        });
        this.mEditorView.getUndoManager().setUndoStateChangedListener(new UndoManager.UndoStateChangedListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.16
            @Override // com.miui.richeditor.UndoManager.UndoStateChangedListener
            public void onUndoStateChanged(UndoManager<?> undoManager) {
                BaseNativeNoteEditFragment.this.getActionBarController().updateUndoState(undoManager.canUndo(), undoManager.canRedo());
            }
        });
        RichEditorDragListener richEditorDragListener = new RichEditorDragListener(0);
        richEditorDragListener.setEditFragment(this);
        this.mEditorView.setOnDragListener(richEditorDragListener);
        this.mEditorView.setLoadingContainer(view.findViewById(R.id.loading_container));
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.rich_editor_mask);
        this.mEditorMaskView = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
            }
        });
        BaseNoteRichEditorController baseNoteRichEditorControllerCreateEditorController = createEditorController();
        this.mEditorController = baseNoteRichEditorControllerCreateEditorController;
        baseNoteRichEditorControllerCreateEditorController.setDataLoadedCallback(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.18
            @Override // java.lang.Runnable
            public void run() {
                if (!BaseNativeNoteEditFragment.this.isAdded() || BaseNativeNoteEditFragment.this.getActionBarController() == null || BaseNativeNoteEditFragment.this.mWorkingNote == null) {
                    return;
                }
                BaseNativeNoteEditFragment.this.getActionBarController().update(BaseNativeNoteEditFragment.this.mWorkingNote, BaseNativeNoteEditFragment.this.getContentLength());
            }
        });
        SpringBackLayout springBackLayout = (SpringBackLayout) view.findViewById(R.id.sbl_editor);
        springBackLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.19
            @Override // android.view.View.OnScrollChangeListener
            public void onScrollChange(View view2, int i, int i2, int i3, int i4) {
                BaseNativeNoteEditFragment.this.mEditorScrollView.invalidate();
            }
        });
        springBackLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.20
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 3 && BaseNativeNoteEditFragment.this.mAnimThemeBgView != null) {
                    BaseNativeNoteEditFragment.this.mAnimThemeBgView.onTouchEvent(motionEvent);
                }
                return view2.onTouchEvent(motionEvent);
            }
        });
        initToolbarController(view);
    }

    protected BaseNoteRichEditorController createEditorController() {
        return new NoteRichEditorController(this, this.mEditorView, this.mTitleEditor);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void resetEditContent() {
        EditText editText = this.mTitleEditor;
        if (editText == null || this.mEditorView == null) {
            return;
        }
        editText.removeTextChangedListener(this.mTitleEditorTextWatcher);
        this.mTitleEditor.getText().clear();
        this.mTitleEditor.addTextChangedListener(this.mTitleEditorTextWatcher);
        this.mEditorView.clearTextWatcher();
        this.mEditorView.clearRichTextData();
        this.mEditorView.clearText();
        this.mEditorView.restoreTextWatcher();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setEnableEdit(boolean z) {
        if (this.mEnableEdit != z) {
            this.mEnableEdit = z;
            this.mEditorView.setEnableEdit(z);
            this.mTitleEditor.setEnabled(z);
            this.mTitleEditor.setFocusable(z);
            this.mTitleEditor.setFocusableInTouchMode(z);
            this.editContentFrame.setEnabled(z);
        }
    }

    public boolean loadNote(Intent intent, Bundle bundle) {
        return handleInstanceState(bundle) || handleIntent(intent);
    }

    protected boolean handleInstanceState(Bundle bundle) {
        WorkingNote workingNoteLoadNote = WorkingNote.loadNote(bundle);
        if (workingNoteLoadNote == null) {
            return false;
        }
        this.mSearchText = bundle.getString("user_query");
        this.mHighLightKeyWords = bundle.getStringArrayList("user_ai_query");
        setWorkingNote(workingNoteLoadNote, false);
        int themeId = workingNoteLoadNote.getThemeId();
        try {
            updateEditorTheme(ResourceManager.getTheme(themeId), false);
            return true;
        } catch (Resources.NotFoundException unused) {
            ResourceManager.clearThemes();
            updateEditorTheme(ResourceManager.getTheme(themeId), false);
            return true;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.ui.BaseActionBarController.EditCallback
    public void onPerformAction(int i, Object... objArr) {
        if (i == 19) {
            this.mEditorController.hideInputMethod();
        } else {
            super.onPerformAction(i, objArr);
        }
    }

    protected boolean handleIntent(Intent intent) {
        Logger.INSTANCE.d(TAG, "handleIntent:" + intent);
        if (intent == null) {
            return true;
        }
        this.mNeedDelayShowInput = intent.getBooleanExtra(NoteIntent.KEY_NEED_DELAY_SOFTINPUT, false);
        this.mFolderId = intent.getLongExtra("com.miui.notes.folder_id", 0L);
        this.mWorkingNote = null;
        this.mSearchText = null;
        this.mEditorView.setVisibility(4);
        int intExtra = intent.getIntExtra(NoteIntent.KEY_NOTE_THEME_ID, 0);
        try {
            updateEditorTheme(ResourceManager.getTheme(intExtra), false);
        } catch (Resources.NotFoundException unused) {
            ResourceManager.clearThemes();
            updateEditorTheme(ResourceManager.getTheme(intExtra), false);
        }
        if (this.mLoadTask != null) {
            this.mLoadTask.cancel();
            this.mLoadTask = null;
        }
        if (!TextUtils.isEmpty(intent.getType()) && intent.getType().equals(Notes.CallData.CONTENT_ITEM_TYPE)) {
            this.mIsFromCallNote = true;
        }
        return super.handleIntentData(intent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    BaseNormalNoteEditFragment.EditNoteLoadTask getEditNoteLoadTask(Intent intent) {
        return new NativeEditNoteLoadTask(intent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleSendIntent(Intent intent) {
        this.mLoadTask = new NativeSendNoteLoadTask(intent);
        this.mIsNeedScrollToTop = true;
        this.mLoadTask.commit();
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.INSTANCE.d(TAG, "onNewIntent");
        if (this.mEditorView != null) {
            Logger.INSTANCE.d(TAG, "onNewIntent,cancelBackgroundTask");
            this.mEditorView.cancelBackgroundTask();
        }
        handleIntent(intent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateRecordMode() {
        super.updateRecordMode();
        updateActionMode(EditorActionMode.EDIT_RECODE, true);
        unregisterTapShareCallback();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setReadonly(boolean z) {
        super.setReadonly(z);
        this.mEditorView.setReadOnly(z || UIUtils.isBrowseMode(getActivity()));
    }

    protected int getContentLength() {
        return this.mEditorView.getReadableTextCount();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setWorkingNote(WorkingNote workingNote, boolean z) {
        RichEditScrollView richEditScrollView;
        if (!isAdded() || getActivity() == null) {
            return;
        }
        super.setWorkingNote(workingNote, false);
        if (TextUtils.isEmpty(this.mWorkingNote.getContent()) && isSupportCreateMindNote()) {
            NoteRichEditor noteRichEditor = this.mEditorView;
            noteRichEditor.setHint(noteRichEditor.createHintEntrySpanText());
        } else {
            this.mEditorView.setHint("");
        }
        if (!TextUtils.isEmpty(this.mWorkingNote.getTitle())) {
            this.mEditorView.setTitle(this.mWorkingNote.getTitle());
        }
        updateNoteTheme(ResourceManager.getTheme(this.mWorkingNote.getThemeId()), false);
        if (this.mNoteExtraInfoController != null) {
            this.mNoteExtraInfoController.update(workingNote, getContentLength());
        }
        if (!this.mIsNeedScrollToTop || (richEditScrollView = this.mEditorScrollView) == null) {
            return;
        }
        richEditScrollView.mIgnoreForbid = true;
        this.mEditorScrollView.scrollTo(0, 0);
        this.mIsNeedScrollToTop = false;
        this.mEditorScrollView.mIgnoreForbid = false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateEditor(boolean z) {
        super.updateEditor(z);
        this.mTitleEditor.removeTextChangedListener(this.mTitleEditorTextWatcher);
        String title = this.mWorkingNote.getTitle();
        if (!TextUtils.isEmpty(title)) {
            this.mTitleEditor.setText(title);
            EditText editText = this.mTitleEditor;
            editText.setSelection(editText.getText().length());
            this.mTitleEditor.clearFocus();
        } else if (title != null && this.mWorkingNote.getTitle().equals("")) {
            try {
                this.mTitleEditor.setSelection(0);
                this.mTitleEditor.setText("");
                this.mTitleEditor.clearFocus();
            } catch (Exception e) {
                Logger.INSTANCE.d(TAG, "updateEditor error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        this.mTitleEditor.addTextChangedListener(this.mTitleEditorTextWatcher);
        setReadonly(this.mEditorView.hasUnknownTag());
        if (!PreferenceUtils.isFirstHandle(getContext()) && (z || TextUtils.isEmpty(this.mWorkingNote.getContent()))) {
            this.mNoteRichEditor.setSelection(this.mEditorView.length());
        } else if (!this.mInSearchMode) {
            this.mEditorController.hideInputMethod();
        }
        if (TextUtils.isEmpty(this.mSearchText)) {
            return;
        }
        this.mEditorScrollView.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.21
            @Override // java.lang.Runnable
            public void run() {
                if (BaseNativeNoteEditFragment.this.isAdded()) {
                    Rect rect = new Rect();
                    BaseNativeNoteEditFragment.this.mEditorView.getFocusedRect(rect);
                    View view = BaseNativeNoteEditFragment.this.getView();
                    if (view == null) {
                        return;
                    }
                    int paddingBottom = view.findViewById(R.id.edit_content).getPaddingBottom() + UIUtils.dip2px(NoteApp.getInstance(), 20.0f);
                    rect.top += paddingBottom;
                    rect.bottom += paddingBottom;
                    BaseNativeNoteEditFragment.this.mEditorScrollView.requestChildRectangleOnScreen(BaseNativeNoteEditFragment.this.mEditAreaLayout, rect, true);
                }
            }
        }, 150L);
    }

    @Override // com.miui.notes.model.WorkingNote.NoteSettingChangedListener
    public void onNoteThemeChanged() {
        if (isDetached() || this.mWorkingNote == null) {
            return;
        }
        updateNoteTheme(ResourceManager.getTheme(this.mWorkingNote.getThemeId()), true);
        asyncSaveNote();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateUserQuery(String str, boolean z, ArrayList<String> arrayList) {
        if (this.mEditorView == null) {
            return;
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            this.mEditorView.setHighLightKeyWords(arrayList);
            if (!isInEditAction() || z) {
                this.mEditorView.updateQueryInContent();
                updateTitleUserQuery(this.mTitleEditor.getText(), str, arrayList);
                return;
            }
            return;
        }
        if (TextUtils.isEmpty(str)) {
            this.mEditorView.setQueryContent(null);
            this.mEditorView.clearQueryInContent();
            clearTitleUserQuery();
        } else {
            this.mEditorView.setQueryContent(str);
            if (!isInEditAction() || z) {
                this.mEditorView.updateQueryInContent();
                updateTitleUserQuery(this.mTitleEditor.getText(), str, arrayList);
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateTitleUserQuery(Editable editable, CharSequence charSequence, ArrayList<String> arrayList) {
        if (TextUtils.isEmpty(charSequence)) {
            clearTitleUserQuery();
            return;
        }
        clearTitleHighLightSpan(this.mTitleEditor.getText(), 0, this.mTitleEditor.getText().length());
        if (arrayList != null && !arrayList.isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                changeHighLight(arrayList.get(i), editable);
            }
            return;
        }
        changeHighLight(charSequence, editable);
    }

    protected void changeHighLight(CharSequence charSequence, Editable editable) {
        if (TextUtils.isEmpty(charSequence)) {
            clearTitleUserQuery();
            return;
        }
        int length = editable.toString().length();
        String lowerCase = editable.toString().toLowerCase();
        String lowerCase2 = String.valueOf(charSequence).toLowerCase();
        int length2 = lowerCase2.length();
        int iIndexOf = 0;
        while (iIndexOf >= 0 && iIndexOf < length) {
            iIndexOf = lowerCase.indexOf(lowerCase2, iIndexOf);
            if (iIndexOf >= 0) {
                int i = iIndexOf + length2;
                editable.setSpan(new HighLightSpan(this.mTitleEditor.getContext(), R.style.TextAppearance_HighLight), iIndexOf, i, 33);
                iIndexOf = i;
            }
        }
    }

    protected void clearTitleUserQuery() {
        clearTitleHighLightSpan(this.mTitleEditor.getText(), 0, this.mTitleEditor.getText().length());
    }

    protected void clearTitleHighLightSpan(Editable editable, int i, int i2) {
        for (HighLightSpan highLightSpan : (HighLightSpan[]) editable.getSpans(i, i2, HighLightSpan.class)) {
            int spanStart = editable.getSpanStart(highLightSpan);
            int spanEnd = editable.getSpanEnd(highLightSpan);
            if (Math.max(spanStart, spanEnd) <= i2 && Math.min(spanStart, spanEnd) >= i) {
                editable.removeSpan(highLightSpan);
            }
        }
    }

    public void onEditorClick(boolean z) {
        if (isAdded() && this.mEnableEdit) {
            if (this.mActionMode == null || !(this.mActionMode == EditorActionMode.EDIT || this.mActionMode == EditorActionMode.EDIT_RECODE)) {
                updateActionMode(EditorActionMode.EDIT, true);
            }
        }
    }

    public boolean onEditorLongClick() {
        if (!isAdded() || !this.mEnableEdit) {
            return false;
        }
        if (this.mActionMode != null && (this.mActionMode == EditorActionMode.EDIT || this.mActionMode == EditorActionMode.EDIT_RECODE)) {
            return false;
        }
        updateActionMode(EditorActionMode.EDIT, true);
        return false;
    }

    protected void updateNoteTheme(final Theme theme, final boolean z) {
        if (this.mCurrentTheme != null && this.mCurrentTheme.getId() == theme.getId()) {
            onNoteThemeChangeFinish();
            return;
        }
        if (this.mIsChangingTheme) {
            return;
        }
        this.mIsChangingTheme = true;
        this.mEditorView.stopMediaPlayerIfNeed();
        this.mResLoadDisposables.clear();
        if (this.mEditorView.getEditorTheme() != null && theme.getId() == this.mEditorView.getEditorTheme().getId()) {
            this.mCurrentTheme = theme;
            onNoteThemeChangeFinish();
        } else if (this.mCurrentTheme == null && ResourceManager.pPreLoadResCache != null && ResourceManager.pPreLoadResCache.themeId == theme.getId()) {
            onPostUpdateNoteTheme(theme, ResourceManager.pPreLoadResCache, z);
            ResourceManager.clearPreloadResCache();
        } else {
            new LoadNoteThemeResTask(this.mResLoadDisposables) { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.22
                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.miui.common.tool.RxStandardAsyncTask
                public NoteThemeResCache doInBackground() {
                    Logger.INSTANCE.d(BaseNativeNoteEditFragment.TAG, "LoadNoteThemeTesTask/doingBackground");
                    try {
                        return BaseNativeNoteEditFragment.this.loadNoteThemeResToCache(theme);
                    } catch (Exception e) {
                        Logger.INSTANCE.d(BaseNativeNoteEditFragment.TAG, "error in LoadNoteThemeResTask " + e);
                        return null;
                    }
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.miui.common.tool.RxStandardAsyncTask
                public void onPost(NoteThemeResCache noteThemeResCache) {
                    BaseNativeNoteEditFragment.this.onPostUpdateNoteTheme(theme, noteThemeResCache, z);
                }
            }.execute();
        }
        if (TextUtils.isEmpty(this.mTitleEditor.getText().toString())) {
            this.mTitleEditor.setHint(R.string.edit_note_hint_title);
        }
        this.mTitleEditor.clearFocus();
    }

    protected void onPostUpdateNoteTheme(Theme theme, NoteThemeResCache noteThemeResCache, boolean z) {
        this.mEditorView.onThemeChanged(theme);
        this.mEditorView.invalidate();
        this.mIsForceDark = theme.getStatusBarStyle(getActivity()).mStatusBar == 2;
        updateWindowStatus(this.mIsForceDark);
        getActionBarController().updateStyle(theme, noteThemeResCache);
        if (this.isLite) {
            updateEditContentBgStyleLite(theme, false);
        } else {
            updateEditContentBgStyle(theme, z);
        }
        if (!z) {
            updateEditorTheme(theme, false);
        } else {
            updateEditorTheme(theme, Boolean.valueOf(!this.isLite));
        }
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.updateTheme(theme, noteThemeResCache);
        }
        if (this.mSharePopupWindow != null) {
            this.mSharePopupWindow.updateStyle(theme);
        }
        if (this.mCurrentTheme == null || !this.mCurrentTheme.equals(theme)) {
            this.mCurrentTheme = theme;
        }
    }

    protected void updateEditorTheme(Theme theme, Boolean bool) {
        boolean z;
        if (!isAdded() || getActivity() == null) {
            return;
        }
        Resources resources = getActivity().getResources();
        Theme.EditorStyle editorStyle = theme.getEditorStyle(getActivity(), this.mWorkingNote != null ? this.mWorkingNote.getModifiedDate() : System.currentTimeMillis());
        if (editorStyle.mTextColor == 0 || editorStyle.mEditorHintColor == 0) {
            return;
        }
        Rect rect = new Rect();
        rect.set(theme.getForegroundPadding(getActivity()));
        this.mEditorView.setTextColor(resources.getColor(editorStyle.mTextColor, null));
        this.mEditorView.setHintTextColor(resources.getColor(editorStyle.mEditorHintColor, null));
        this.mTitleEditor.setTextColor(resources.getColor(editorStyle.mTextColor, null));
        this.mTitleEditor.setHintTextColor(resources.getColor(editorStyle.mEditorHintColor, null));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTitleEditor.getLayoutParams();
        layoutParams.topMargin = theme.getTitleMarginTop(NoteApp.getInstance());
        this.mTitleEditor.setLayoutParams(layoutParams);
        if (theme.getId() == 7 || theme.getId() == 8 || theme.getId() == 6) {
            if (theme.getId() == 7) {
                int dimension = (int) getContext().getResources().getDimension(R.dimen.theme_antique_header_padding_horizontal);
                rect.left = dimension;
                rect.right = dimension;
            }
            z = false;
        } else {
            z = true;
        }
        Rect rect2 = new Rect(rect);
        if (z) {
            rect2.left = (int) (rect2.left + getContext().getResources().getDimension(R.dimen.note_editor_padding));
            rect2.right = (int) (rect2.right + getContext().getResources().getDimension(R.dimen.note_editor_padding));
        }
        if (bool.booleanValue()) {
            changePaddingOnThemeChanged(editorStyle.mForeground, rect2);
        } else {
            this.mEditAreaLayout.setBackground(editorStyle.mForeground);
            getActionBarController().updateHeadInfoMargin(rect2);
        }
        this.mEditorView.setGravity(editorStyle.mTextGravity);
        int color = resources.getColor(editorStyle.mCursorColor);
        int color2 = resources.getColor(editorStyle.mHandleColor);
        CompatUtils.invokeCursorColor(this.mEditorView, color, color2);
        CompatUtils.invokeCursorColor(this.mTitleEditor, color, color2);
        CompatUtils.invokeCursorController(this.mEditorView);
        this.mTitleEditor.setHighlightColor(resources.getColor(editorStyle.mHighLightColor));
        this.mEditorView.setHighlightColor(resources.getColor(editorStyle.mHighLightColor));
        this.mEditorView.setLinkTextColor(resources.getColor(editorStyle.mLinkedColor));
    }

    protected void changePaddingOnThemeChanged(Drawable drawable, Rect rect) {
        if (this.mEditUiPaddingAnimator != null && this.mEditUiPaddingAnimator.isRunning()) {
            this.mEditUiPaddingAnimator.cancel();
        }
        LinearLayout linearLayout = this.mEditAreaLayout;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        Rect rect2 = new Rect();
        Rect rect3 = new Rect();
        Rect rect4 = new Rect();
        rect2.left = linearLayout.getPaddingLeft();
        rect2.right = linearLayout.getPaddingRight();
        rect2.top = linearLayout.getPaddingTop();
        rect2.bottom = linearLayout.getPaddingBottom();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mNoteExtraInfoController.getView().getLayoutParams();
        rect3.left = marginLayoutParams.leftMargin;
        rect3.right = marginLayoutParams.rightMargin;
        drawable.getPadding(rect4);
        valueAnimatorOfFloat.addUpdateListener(new ContentPaddingAnimatorUpdateListener(this, rect2, rect4, rect3, rect));
        valueAnimatorOfFloat.addListener(new ThemeChangeAnimListener(this, drawable));
        valueAnimatorOfFloat.setInterpolator(new EaseManager.SpringInterpolator());
        valueAnimatorOfFloat.start();
        this.mEditUiPaddingAnimator = valueAnimatorOfFloat;
    }

    protected void updateEditContentBgStyleLite(Theme theme, boolean z) {
        boolean z2;
        if (isAdded()) {
            Theme.EditorStyle editorStyle = theme.getEditorStyle(getActivity());
            Rect rect = new Rect();
            View view = getView();
            if (theme instanceof AnimTheme) {
                if (this.mAnimThemeLiteBgView != null) {
                    this.mAnimThemeLiteBgView.setBackground(editorStyle.mBackground);
                }
                if (view != null) {
                    view.setBackground(null);
                }
            } else {
                if (this.mAnimThemeLiteBgView != null) {
                    this.mAnimThemeLiteBgView.setBackground(null);
                }
                if (view != null) {
                    view.setBackground(editorStyle.mBackground);
                }
            }
            rect.set(theme.getForegroundPadding(getActivity()));
            if (theme.getId() == 7 || theme.getId() == 8 || theme.getId() == 6) {
                if (theme.getId() == 7) {
                    int dimension = (int) getContext().getResources().getDimension(R.dimen.theme_antique_header_padding_horizontal);
                    rect.left = dimension;
                    rect.right = dimension;
                }
                z2 = false;
            } else {
                z2 = true;
            }
            Rect rect2 = new Rect(rect);
            if (z2) {
                rect2.left = (int) (rect2.left + getContext().getResources().getDimension(R.dimen.note_editor_padding));
                rect2.right = (int) (rect2.right + getContext().getResources().getDimension(R.dimen.note_editor_padding));
            }
            if (z) {
                changePaddingOnThemeChanged(editorStyle.mForeground, rect2);
            } else {
                this.mEditAreaLayout.setBackground(editorStyle.mForeground);
                getActionBarController().updateHeadInfoMargin(rect2);
            }
            onNoteThemeChangeFinish();
        }
    }

    protected void updateEditContentBgStyle(Theme theme, boolean z) {
        int i;
        long j;
        if (isAdded()) {
            final Theme.EditorStyle editorStyle = theme.getEditorStyle(getContext());
            if (theme instanceof AnimTheme) {
                AnimTheme animTheme = (AnimTheme) theme;
                final MamlView backgroundView = animTheme.getBackgroundView(getContext());
                if (backgroundView != null) {
                    final ViewGroup viewGroup = (ViewGroup) getView();
                    if (this.mCurrentTheme == null) {
                        try {
                            getView().setBackground(new ColorDrawable(getContext().getColor(editorStyle.mBlurMixColor3)));
                        } catch (Exception e) {
                            Logger.INSTANCE.e(TAG, "error in setBackground " + e);
                        }
                    }
                    if (this.mCurrentTheme == null || !(this.mCurrentTheme instanceof AnimTheme) || this.mAnimThemeBgView == null) {
                        i = 0;
                        j = 50;
                    } else {
                        ((AnimTheme) this.mCurrentTheme).pause();
                        i = 1;
                        j = 800;
                    }
                    final int i2 = i;
                    final long j2 = j;
                    backgroundView.setAlpha(1.0f);
                    animTheme.reload();
                    if (this.mHandler != null) {
                        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$updateEditContentBgStyle$2(viewGroup, backgroundView, i2, editorStyle, j2);
                            }
                        });
                        return;
                    }
                    return;
                }
                return;
            }
            final ViewGroup viewGroup2 = (ViewGroup) getView();
            getView().setBackground(editorStyle.mBackground);
            if (this.mAnimThemeBgView != null) {
                if (this.mCurrentTheme == null || !(this.mCurrentTheme instanceof AnimTheme)) {
                    return;
                }
                final AnimTheme animTheme2 = (AnimTheme) this.mCurrentTheme;
                this.mThemeBgViewAnimator = this.mAnimThemeBgView.animate();
                this.mThemeBgViewAnimator.alpha(0.0f).withEndAction(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.23
                    @Override // java.lang.Runnable
                    public void run() {
                        if (BaseNativeNoteEditFragment.this.isAdded()) {
                            animTheme2.pause();
                            viewGroup2.removeViewAt(0);
                            BaseNativeNoteEditFragment.this.onAnimThemeBgViewChanged(null);
                            BaseNativeNoteEditFragment.this.onNoteThemeChangeFinish();
                        }
                    }
                }).setDuration(300L).start();
                return;
            }
            onNoteThemeChangeFinish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEditContentBgStyle$2(final ViewGroup viewGroup, View view, final int i, final Theme.EditorStyle editorStyle, long j) {
        if (!isAdded() || viewGroup == null) {
            return;
        }
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        viewGroup.addView(view, i, new ViewGroup.LayoutParams(-1, -1));
        onAnimThemeBgViewChanged(view);
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateEditContentBgStyle$1(viewGroup, editorStyle, i);
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEditContentBgStyle$1(ViewGroup viewGroup, Theme.EditorStyle editorStyle, int i) {
        if (isAdded()) {
            viewGroup.setBackground(new ColorDrawable(getContext().getColor(editorStyle.mBlurMixColor3)));
            if (i > 0) {
                viewGroup.removeViewAt(0);
            }
            onNoteThemeChangeFinish();
        }
    }

    protected void updateContentPadding(Rect rect, Rect rect2) {
        this.mEditAreaLayout.setPadding(rect.left, rect.top, rect.right, rect.bottom);
        this.mEditorView.invalidateDeeply();
        this.mTitleEditor.clearFocus();
        getActionBarController().updateHeadInfoMargin(rect2);
    }

    protected void onThemeAnimChanged(Drawable drawable, boolean z) {
        if (z) {
            this.mEditAreaLayout.setBackground(drawable);
        }
    }

    protected void dismissMindHintGuideWindow() {
        if (this.mShowingPopupWindow != null) {
            this.mShowingPopupWindow.dismiss();
        }
    }

    protected void forceHideSoftInput() {
        this.mEditorController.hideInputMethod();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateWorkingTextAndTitle() {
        if (this.mWorkingNote == null) {
            return;
        }
        String string = this.mTitleEditor.getText().toString();
        if (!TextUtils.isEmpty(string)) {
            this.mWorkingNote.setWorkingTitle(string);
        } else if (TextUtils.isEmpty(string) && !TextUtils.isEmpty(this.mWorkingNote.getTitle())) {
            this.mWorkingNote.setWorkingTitle("");
        }
        this.mWorkingNote.setWorkingText(this.mEditorView.getRichTextData().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendAndMarkQueryTitle(Editable editable, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence) || isInEditAction()) {
            return;
        }
        int length = editable.toString().length();
        int length2 = charSequence.length();
        int iIndexOf = 0;
        while (iIndexOf >= 0 && iIndexOf < length) {
            iIndexOf = editable.toString().indexOf(String.valueOf(charSequence), iIndexOf);
            if (iIndexOf >= 0) {
                int i = iIndexOf + length2;
                editable.setSpan(this.mEditorView.getStyleFactory().createKeywordsSpan(), iIndexOf, i, 33);
                iIndexOf = i;
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void updateActionMode(EditorActionMode editorActionMode, boolean z) {
        boolean z2;
        Logger.INSTANCE.d(TAG, "updateActionMode from  to " + editorActionMode.name());
        boolean z3 = true;
        if (this.homeViewModel != null) {
            this.homeViewModel.setEditStatus(editorActionMode == EditorActionMode.EDIT || editorActionMode == EditorActionMode.EDIT_TITLE);
        }
        if (editorActionMode == EditorActionMode.EDIT) {
            if (this.mActionMode == null) {
                if (!UIUtils.isBrowseMode(getActivity())) {
                    homeToEdit();
                    z2 = true;
                }
                if (!z2 && (this.mEditorView.getText().length() != 0 || this.mTitleEditor.getText().length() != 0)) {
                    z3 = false;
                }
                getActionBarController().updateActionMode(editorActionMode, z3, z);
                this.mActionMode = editorActionMode;
            }
            if (this.mActionMode == EditorActionMode.VIEW) {
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
            if (this.mWorkingNote != null) {
                if (!TextUtils.isEmpty(this.mWorkingNote.getContent()) && NotesTextStat.isCreateCommonNote) {
                    NotesTextStat.reportCreateEvent(NotesTextStat.NOTE_EFFECTIVE_CREATE, null);
                }
                NotesTextStat.isCreateCommonNote = false;
            }
        } else if (editorActionMode == EditorActionMode.EDIT_RECODE) {
            if (PermissionUtils.isPermissionNotGranted(getContext(), "android.permission.RECORD_AUDIO")) {
                editorActionMode = EditorActionMode.EDIT;
                homeToEdit();
            } else if (this.mActionMode == null) {
                homeToRecord();
            } else if (this.mActionMode == EditorActionMode.VIEW) {
                viewToRecord();
            } else if (this.mActionMode == EditorActionMode.EDIT) {
                editToRecord();
            } else {
                Logger.INSTANCE.e(TAG, "ActionMode switch error!,from " + this.mActionMode.name() + " to " + editorActionMode.name());
            }
        } else if (editorActionMode == EditorActionMode.EDIT_TITLE) {
            if (this.mActionMode == EditorActionMode.VIEW) {
                viewToTitle();
            } else if (this.mActionMode == EditorActionMode.EDIT) {
                editToTitle(true);
            } else if (this.mActionMode != null) {
                Logger.INSTANCE.e(TAG, "ActionMode switch error!,from " + this.mActionMode.name() + " to " + editorActionMode.name());
            } else {
                Logger.INSTANCE.e(TAG, "ActionMode switch error!,mActionMode is null ");
            }
        }
        z2 = false;
        if (!z2) {
            z3 = false;
        }
        getActionBarController().updateActionMode(editorActionMode, z3, z);
        this.mActionMode = editorActionMode;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void sendAsImage() {
        if (this.mEditorView.isLoadingViewVisible()) {
            Toast.makeText(NoteApp.getInstance(), R.string.refuse_to_open_as_pic_because_of_loading, 0).show();
            return;
        }
        if (isWorkingNoteEmpty()) {
            Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
            return;
        }
        if (this.mEditorView.getText().length() > 20000) {
            Toast.makeText(NoteApp.getInstance(), R.string.refuse_to_share_as_pic_because_of_content, 0).show();
            return;
        }
        if (this.mWorkingNote != null && this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        saveNote(true);
        Dialog managedDialog = getMManagedDialog();
        Logger.INSTANCE.d(TAG, "sendAsImage isDialogShowing = " + isDialogShowing());
        if (isDialogShowing() && managedDialog != null) {
            managedDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.lambda$sendAsImage$3(dialogInterface);
                }
            });
        } else {
            send();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendAsImage$3(DialogInterface dialogInterface) {
        Logger.INSTANCE.d(TAG, "onDismiss");
        send();
    }

    public void send() {
        if (!RomUtils.isOS31Support()) {
            launchSnsShareActivity(2, 16, null);
        } else {
            this.mHandler.post(this.mShowCreatingProgressDialogTask);
            generateRender(new BaseNormalNoteEditFragment.RenderGenerateCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.24
                @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.RenderGenerateCallback
                public void onComplete(Uri uri, Uri uri2) {
                    BaseNativeNoteEditFragment.this.startSharePageByRenderUri(uri, uri2);
                    BaseNativeNoteEditFragment.this.dismissProgressDialogWithJudge();
                }
            }, ResourceManager.getTheme(this.mWorkingNote.getThemeId()));
        }
    }

    private void generateRender(BaseNormalNoteEditFragment.RenderGenerateCallback renderGenerateCallback, Theme theme) {
        if (this.mWorkingNote != null) {
            this.mRender = new RenderBuilder(getContext()).setContent(this.mWorkingNote != null ? this.mWorkingNote.getContent() : null).setTitle(TextUtils.isEmpty(this.mWorkingNote != null ? this.mWorkingNote.getTitle() : null) ? "" : this.mWorkingNote.getTitle()).setThemeId(this.mWorkingNote.getThemeId()).setStyle(theme.getShareStyle(getContext(), this.mWorkingNote.getModifiedDate())).setCheckRes(R.drawable.ic_richtext_share_checkbox_c).setUnCheckRes((theme.getId() == 0 && UIUtils.isNightMode(getContext())) ? R.drawable.ic_richtext_share_checkbox_u_light : R.drawable.ic_richtext_share_checkbox_u).build();
            Observable.create(new ObservableOnSubscribe() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment$$ExternalSyntheticLambda5
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    this.f$0.lambda$generateRender$4(observableEmitter);
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Pair<Uri, Uri>>() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.25
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    BaseNativeNoteEditFragment.this.mFolderNameGetTask = disposable;
                }

                @Override // io.reactivex.Observer
                public void onNext(Pair<Uri, Uri> pair) {
                    Uri uri = (Uri) pair.first;
                    Uri uri2 = (Uri) pair.second;
                    Logger.INSTANCE.d(BaseNativeNoteEditFragment.TAG, "generateAnimRender uri: $uri previewUri $previewUri");
                    if (uri == null || uri2 == null) {
                        return;
                    }
                    BaseNativeNoteEditFragment.this.startSharePageByRenderUri(uri, uri2);
                    BaseNativeNoteEditFragment.this.lambda$dismissProgressDialogWithJudge$5();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$generateRender$4(ObservableEmitter observableEmitter) throws Exception {
        this.mRender.shouldBlock = false;
        Uri uriCreateRenderTempUri = createRenderTempUri(getContext(), this.mRender);
        int height = this.mRender.getHeight();
        if (this.mRender.getHeight() > this.SHARE_PREVIEW_IMAGE_MAX_HEIGHT) {
            height = this.SHARE_PREVIEW_IMAGE_MAX_HEIGHT;
        } else if (this.mRender.getHeight() < this.SHARE_PREVIEW_IMAGE_MIN_HEIGHT) {
            this.mRender.setHeight(this.SHARE_PREVIEW_IMAGE_MIN_HEIGHT);
            height = this.SHARE_PREVIEW_IMAGE_MIN_HEIGHT;
        }
        observableEmitter.onNext(new Pair(uriCreateRenderTempUri, createRenderTempUri(getContext(), this.mRender, height)));
        observableEmitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialogWithJudge() {
        if (Math.abs(System.currentTimeMillis() - this.mShowTime) < 100) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.27
                @Override // java.lang.Runnable
                public void run() {
                    BaseNativeNoteEditFragment.this.lambda$dismissProgressDialogWithJudge$5();
                }
            }, 100L);
        } else {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$dismissProgressDialogWithJudge$5();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: dismissProgressDialog, reason: merged with bridge method [inline-methods] */
    public void lambda$dismissProgressDialogWithJudge$5() {
        ProgressDialog progressDialog = this.mDialog;
        if (progressDialog != null) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                Logger.INSTANCE.d(TAG, "dismissProgressDialog dismiss exception ");
                e.printStackTrace();
            }
            this.mDialog = null;
        }
    }

    public static Uri createRenderTempUri(Context context, Render render) {
        if (context.getExternalCacheDir() != null) {
            File file = new File(context.getExternalCacheDir().getAbsolutePath() + "/IMG_SHARE_$now.png");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Logger.INSTANCE.e(TAG, " createNewFile error: " + e);
                    return null;
                }
            }
            if (render.saveToFile(file)) {
                return CompatUtils.getCompatUri(context, file);
            }
            if (file.exists()) {
                Logger.INSTANCE.d(TAG, "Render save to file failed delete " + file.delete());
            }
        }
        return null;
    }

    public static Uri createRenderTempUri(Context context, Render render, int i) {
        if (context.getExternalCacheDir() != null) {
            File file = new File(context.getExternalCacheDir().getAbsolutePath() + "/IMG_SHARE_$now.png");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Logger.INSTANCE.e(TAG, " createNewFile error: " + e);
                    return null;
                }
            }
            if (render.saveToFile(file, i)) {
                return CompatUtils.getCompatUri(context, file);
            }
            if (file.exists()) {
                Logger.INSTANCE.d(TAG, "Render save to file failed delete " + file.delete());
            }
        }
        return null;
    }

    protected void launchSnsShareActivity(int i, int i2, AigcPicMetaData aigcPicMetaData) {
        SnsEditActivity.launchActivity(this.mToShareResult, (BaseActivity) getActivity(), this.mWorkingNote.getContent(), this.mWorkingNote.getTitle(), i, this.mWorkingNote.getThemeId(), this.mWorkingNote.getModifiedDate(), this.mEditorView.getTextSize(), i2, "", this.mWorkingNote, aigcPicMetaData);
    }

    protected void onViewMode() {
        NoteRichEditor noteRichEditor;
        updateUserQuery(this.mSearchText, true, null);
        if ((!this.isOnPause || !this.mEditorView.hasSelection()) && (noteRichEditor = this.mEditorView) != null) {
            noteRichEditor.clearFocus();
        }
        if (this.mImageChooserMenu != null) {
            this.mImageChooserMenu.dismiss();
        }
    }

    protected void onEditMode() {
        clearTitleUserQuery();
        this.mEditorView.clearQueryInContent();
    }

    protected void onTitleMode() {
        clearTitleUserQuery();
        this.mEditorView.clearQueryInContent();
    }

    protected void editToView() {
        editToView(true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void dragToEditMode() {
        this.mActionMode = EditorActionMode.EDIT;
        getActionBarController().updateActionMode(EditorActionMode.EDIT, false, false);
        this.mEditToolbarController.onEditContentMode(true, false, false);
    }

    protected void editToView(boolean z) {
        if (!this.isOnPause || !this.mEditorView.hasSelection()) {
            this.mEditorView.hideInsertionPointCursorController();
        }
        if (!this.mInSearchMode && getMIsKeyboardVisible()) {
            this.mEditorController.hideInputMethod();
        }
        onViewMode();
    }

    protected void homeToView() {
        if (!this.mInSearchMode && getMIsKeyboardVisible()) {
            this.mEditorController.hideInputMethod();
        }
        onViewMode();
    }

    protected void recordToView() {
        this.mEditorMaskView.setVisibility(8);
        this.mEditorMaskView.removeAllViews();
        this.mEditorController.hideInputMethod();
        onViewMode();
        registerTapShareCallBack();
    }

    protected void titleToView() {
        if (!this.isOnPause || !this.mTitleEditor.hasSelection()) {
            this.mTitleEditor.clearFocus();
        }
        onViewMode();
    }

    protected void viewToEdit() {
        this.mEditorView.requestFocus();
        NoteRichEditor noteRichEditor = this.mEditorView;
        noteRichEditor.setSelection(noteRichEditor.getCacheSelectionStart());
        this.mEditorView.cacheSelection(false);
        onEditMode();
    }

    protected void homeToEdit() {
        onEditMode();
    }

    protected void titleToEdit() {
        this.mTitleEditor.clearFocus();
        onEditMode();
    }

    protected void editToTitle(boolean z) {
        onTitleMode();
    }

    protected void viewToTitle() {
        onTitleMode();
    }

    protected void viewToRecord() {
        Logger.INSTANCE.d(TAG, "viewToRecord");
        this.mEditorMaskView.setVisibility(0);
        Editable text = this.mEditorView.getText();
        if (TextUtils.isEmpty(text)) {
            this.mEditorView.setSelection(0);
        } else {
            int cacheSelectionStart = this.mEditorView.getCacheSelectionStart();
            if (cacheSelectionStart == -1) {
                this.mEditorView.setSelection(text.length());
            } else {
                this.mEditorView.setSelection(cacheSelectionStart);
            }
        }
        onEditMode();
    }

    protected void editToRecord() {
        Logger.INSTANCE.d(TAG, "editToRecord");
        this.mEditorMaskView.setVisibility(0);
    }

    protected void homeToRecord() {
        Logger.INSTANCE.d(TAG, "homeToRecord");
        this.mEditorMaskView.setVisibility(0);
        onEditMode();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void insertImages(boolean z, Uri... uriArr) {
        insertImages(uriArr);
    }

    public void insertImages(Uri... uriArr) {
        Runnable runnable = new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.28
            @Override // java.lang.Runnable
            public void run() {
                BaseNativeNoteEditFragment.this.mEditorView.createNewRichTextData();
                BaseNativeNoteEditFragment.this.saveNote(true);
                AiActionUtil.asyncNotifyResult(0);
            }
        };
        forceHideSoftInput();
        this.mEditorView.insertImages(runnable, uriArr);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void startInsertDoodle(final String str) {
        if (!TextUtils.isEmpty(str)) {
            if (isNeedPendingInsertContent()) {
                this.mEditorController.setPendingTaskOnDataLoaded(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.29
                    @Override // java.lang.Runnable
                    public void run() {
                        BaseNativeNoteEditFragment.this.insertDoodle(str);
                    }
                });
                return;
            } else {
                insertDoodle(str);
                return;
            }
        }
        Logger.INSTANCE.d(TAG, "REQUEST_CODE_DOODLE saveNewCachePath is null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void insertDoodle(String str) {
        this.mEditorView.insertDoodleByName(str, null);
        this.mEditorView.createNewRichTextData();
        saveNote(true);
        this.mEditorView.cacheSelection(false);
        this.mEditorView.clearFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDoodle(String str, String str2) {
        if (this.mEditingImageSpan == null && !TextUtils.isEmpty(str2)) {
            for (NormalImageSpan normalImageSpan : (NormalImageSpan[]) this.mEditorView.getText().getSpans(0, this.mEditorView.getText().length(), NormalImageSpan.class)) {
                if (normalImageSpan.getSrcFileId().equals(str2)) {
                    this.mEditingImageSpan = normalImageSpan;
                    Logger.INSTANCE.d(TAG, "restore mEditingImageSpan");
                    break;
                }
            }
        }
        if (!TextUtils.isEmpty(str)) {
            this.mEditorView.insertDoodleByName(str, this.mEditingImageSpan);
        }
        this.mEditingImageSpan = null;
        this.mEditorView.createNewRichTextData();
        asyncSaveNote();
        this.mEditorView.cacheSelection(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public boolean insertImageFromClipData(ClipData clipData, final boolean z) {
        return ClipDataHelper.getImageUrisFromClipDataInFragment(this, clipData, new ClipDataHelper.UriGetCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.30
            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onException(String str) {
            }

            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onGetUri(ArrayList<Uri> arrayList) {
                if (arrayList == null || arrayList.size() <= 0) {
                    return;
                }
                if (z && BaseNativeNoteEditFragment.this.mEditorView.getText() != null) {
                    BaseNativeNoteEditFragment.this.mEditorView.setSelection(BaseNativeNoteEditFragment.this.mEditorView.getText().length());
                }
                Uri[] uriArr = new Uri[arrayList.size()];
                arrayList.toArray(uriArr);
                BaseNativeNoteEditFragment.this.insertImages(uriArr);
            }
        });
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public boolean insertImageFromDragEvent(DragEvent dragEvent, final boolean z) {
        return ClipDataHelper.getImageUrisFromDragEventInFragment(this, dragEvent, new ClipDataHelper.UriGetCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.31
            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onException(String str) {
            }

            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onGetUri(ArrayList<Uri> arrayList) {
                if (arrayList == null || arrayList.size() <= 0) {
                    return;
                }
                if (z && BaseNativeNoteEditFragment.this.mEditorView.getText() != null) {
                    BaseNativeNoteEditFragment.this.mEditorView.setSelection(BaseNativeNoteEditFragment.this.mEditorView.getText().length());
                }
                Uri[] uriArr = new Uri[arrayList.size()];
                arrayList.toArray(uriArr);
                BaseNativeNoteEditFragment.this.insertImages(uriArr);
            }
        });
    }

    protected void viewImage(BaseImageSpan baseImageSpan) {
        if (isAdded()) {
            if (!baseImageSpan.isMissed()) {
                viewImage(baseImageSpan.getSrcFileId());
                return;
            }
            HtmlParser.GeneralElement element = baseImageSpan.getElement();
            if (element instanceof HtmlParser.VideoElement) {
                ExternalAppUtils.viewVideo(getContext(), ((HtmlParser.VideoElement) element).getFileId());
            }
        }
    }

    protected void editImage(BaseImageSpan baseImageSpan) {
        if (isAdded() && (baseImageSpan instanceof NormalImageSpan)) {
            this.mEditorView.cacheSelection(true);
            DoodleParams doodleParamsCreateEditImageParams = createEditImageParams(baseImageSpan);
            if (doodleParamsCreateEditImageParams != null) {
                this.mEditingImageSpan = (NormalImageSpan) baseImageSpan;
                this.mEditorController.hideInputMethod();
                startToDoodle(doodleParamsCreateEditImageParams, this.mEidtDooleResult);
            }
        }
    }

    public DoodleParams createEditImageParams(BaseImageSpan baseImageSpan) {
        if (baseImageSpan.isMissed()) {
            return null;
        }
        return createEditImageParams(baseImageSpan.getSrcFileId());
    }

    protected void toggleImageShowBig(BaseImageSpan baseImageSpan) {
        if (baseImageSpan instanceof NormalImageSpan) {
            this.mEditorView.clearFocus();
            ((NormalImageSpan) baseImageSpan).toggleShowBig();
            this.mEditorView.createNewRichTextData();
            asyncSaveNote();
        }
    }

    protected void viewLinkByBrowser(LinkCardSpan linkCardSpan) {
        viewLinkByBrowser(linkCardSpan.getLink());
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void refreshAlertTime() {
        if (this.mWorkingNote == null || this.mWorkingNote.getNoteId() <= 0) {
            return;
        }
        this.mWorkingNote.updateAlarmInfo();
        this.mNoteExtraInfoController.updateAlarm(this.mWorkingNote);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onShareOptions(Context context) {
        if (this.mEditorView.commitComposingContent()) {
            asyncSaveNote();
        }
        saveNoteInstantlyIfNeeded();
        super.onShareOptions(context);
    }

    protected boolean isNeedPendingInsertContent() {
        NoteRichEditor noteRichEditor = this.mEditorView;
        return noteRichEditor == null || noteRichEditor.isFirstLoad() || this.mEditorView.getDataParseTask() != null || this.mIsFromRebuild;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void doneEdit() {
        if (this.mEditorView.commitComposingContent()) {
            asyncSaveNote();
        }
        super.doneEdit();
        this.mTitleEditor.clearFocus();
        updateActionMode(EditorActionMode.VIEW, true);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public boolean saveNote(boolean z) {
        NoteRichEditor noteRichEditor;
        boolean zSaveNote = super.saveNote(z);
        if (zSaveNote && this.mWorkingNote != null && !TextUtils.isEmpty(this.mWorkingNote.getTitle()) && (noteRichEditor = this.mEditorView) != null) {
            noteRichEditor.setTitle(this.mWorkingNote.getTitle());
        }
        return zSaveNote;
    }

    protected void registerLongScreenShot(boolean z) {
        if (isAdded()) {
            if (z) {
                if (Utils.isAboveSDK34()) {
                    getContext().getApplicationContext().registerReceiver(this.mLongScreenshotReceiver, new IntentFilter("com.miui.util.LongScreenshotUtils.LongScreenshot"), 2);
                    return;
                } else {
                    getContext().getApplicationContext().registerReceiver(this.mLongScreenshotReceiver, new IntentFilter("com.miui.util.LongScreenshotUtils.LongScreenshot"));
                    return;
                }
            }
            try {
                getContext().getApplicationContext().unregisterReceiver(this.mLongScreenshotReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mLongScreenshotReceiver = null;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    @Deprecated
    protected void handleAudioCTARequest() {
        if (!CTAManager.getInstance().isAccepted(getActivity())) {
            FragmentActivity activity = getActivity();
            if (activity instanceof CTAActivity) {
                ((CTAActivity) activity).showCtaDialog();
                CTAManager.getInstance().addListener(new CTAManager.CTAListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.32
                    @Override // com.miui.notes.cta.CTAManager.CTAListener
                    public void onAccept() {
                        BaseNativeNoteEditFragment.this.updateRecordMode();
                    }

                    @Override // com.miui.notes.cta.CTAManager.CTAListener
                    public void onReject() {
                        Logger.INSTANCE.w(BaseNativeNoteEditFragment.TAG, "user deny to authorization");
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

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showLoadingData(boolean z) {
        super.showLoadingData(z);
        this.mEditorController.showLoadingView(z);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onLoadDataStart() {
        super.onLoadDataStart();
        if (this.mHandler.hasMessages(5)) {
            this.mHandler.removeMessages(5);
        }
        this.mHandler.sendEmptyMessageDelayed(5, 500L);
    }

    public class NativeEditNoteLoadTask extends BaseNormalNoteEditFragment.EditNoteLoadTask {
        public NativeEditNoteLoadTask(Intent intent) {
            super(BaseNativeNoteEditFragment.this, intent);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onInsertAudio() {
            BaseNativeNoteEditFragment.this.mActionMode = null;
            BaseNativeNoteEditFragment.this.mEditorView.addPendingTask(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.NativeEditNoteLoadTask.1
                @Override // java.lang.Runnable
                public void run() {
                    BaseNativeNoteEditFragment.this.startRecordWithCheckFromEditTask();
                }
            });
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onEnterEdit(WorkingNote workingNote) {
            BaseNativeNoteEditFragment.this.mActionMode = null;
            if (this.intent.getExtras() == null) {
                BaseNativeNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
                return;
            }
            EditorActionMode editorActionMode = (EditorActionMode) this.intent.getExtras().get(NoteIntent.KEY_ACTION_MODE);
            if (editorActionMode != null && editorActionMode.equals(EditorActionMode.EDIT_RECODE)) {
                BaseNativeNoteEditFragment.this.updateRecordMode();
                return;
            }
            if (UIUtils.isMiniSmallWindowMode(BaseNativeNoteEditFragment.this.getActivity()) || UIUtils.isBrowseMode(BaseNativeNoteEditFragment.this.getActivity()) || PreferenceUtils.isFirstHandle(BaseNativeNoteEditFragment.this.getContext())) {
                BaseNativeNoteEditFragment.this.updateActionMode(EditorActionMode.VIEW, false);
                if (BaseNativeNoteEditFragment.this.mHandler != null) {
                    BaseNativeNoteEditFragment.this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment$NativeEditNoteLoadTask$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onEnterEdit$0();
                        }
                    });
                    return;
                }
                return;
            }
            BaseNativeNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEnterEdit$0() {
            if (BaseNativeNoteEditFragment.this.mEditorView != null) {
                BaseNativeNoteEditFragment.this.mEditorView.requestLayout();
                BaseNativeNoteEditFragment.this.mEditorView.invalidateDeeply();
            }
        }
    }

    public class NativeSendNoteLoadTask extends BaseNormalNoteEditFragment.SendNoteLoadTask {
        public NativeSendNoteLoadTask(Intent intent) {
            super(BaseNativeNoteEditFragment.this, intent);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, WorkingNote workingNote) {
            super.onResult(taskManager, workingNote);
            BaseNativeNoteEditFragment.this.homeViewModel.postSelectNote(new NoteLoadDataEntity(workingNote.getNoteId(), workingNote.getFolderId(), "", 0, workingNote.getModifiedDate(), false, null), false);
            if (!TextUtils.isEmpty(this.sendContent)) {
                BaseNativeNoteEditFragment.this.updateActionMode(EditorActionMode.VIEW, true);
            } else {
                BaseNativeNoteEditFragment.this.mNeedDelayShowInput = true;
                BaseNativeNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT, true);
            }
            NotesTextStat.isCreateCommonNote = true;
        }
    }

    private static class ContentPaddingAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        Rect endHeadPadding;
        Rect endPadding;
        float fraction;
        private WeakReference<BaseNativeNoteEditFragment> mWkContainer;
        Rect startHeadPadding;
        Rect startPadding;
        final IntEvaluator evaluator = new IntEvaluator();
        Rect infoHeadMargin = new Rect();
        Rect editContentPadding = new Rect();

        public ContentPaddingAnimatorUpdateListener(BaseNativeNoteEditFragment baseNativeNoteEditFragment, Rect rect, Rect rect2, Rect rect3, Rect rect4) {
            this.mWkContainer = new WeakReference<>(baseNativeNoteEditFragment);
            this.startPadding = rect;
            this.endPadding = rect2;
            this.startHeadPadding = rect3;
            this.endHeadPadding = rect4;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.fraction = valueAnimator.getAnimatedFraction();
            BaseNativeNoteEditFragment baseNativeNoteEditFragment = this.mWkContainer.get();
            if (baseNativeNoteEditFragment != null) {
                this.editContentPadding.set(this.evaluator.evaluate(this.fraction, Integer.valueOf(this.startPadding.left), Integer.valueOf(this.endPadding.left)).intValue(), this.evaluator.evaluate(this.fraction, Integer.valueOf(this.startPadding.top), Integer.valueOf(this.endPadding.top)).intValue(), this.evaluator.evaluate(this.fraction, Integer.valueOf(this.startPadding.right), Integer.valueOf(this.endPadding.right)).intValue(), this.evaluator.evaluate(this.fraction, Integer.valueOf(this.startPadding.bottom), Integer.valueOf(this.endPadding.bottom)).intValue());
                this.infoHeadMargin.left = this.evaluator.evaluate(this.fraction, Integer.valueOf(this.startHeadPadding.left), Integer.valueOf(this.endHeadPadding.left)).intValue();
                this.infoHeadMargin.right = this.evaluator.evaluate(this.fraction, Integer.valueOf(this.startHeadPadding.right), Integer.valueOf(this.endHeadPadding.right)).intValue();
                baseNativeNoteEditFragment.updateContentPadding(this.editContentPadding, this.infoHeadMargin);
            }
        }
    }

    private static class ThemeChangeAnimListener implements Animator.AnimatorListener {
        private Drawable editAreaBackground;
        private WeakReference<BaseNativeNoteEditFragment> mWkContainer;

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        public ThemeChangeAnimListener(BaseNativeNoteEditFragment baseNativeNoteEditFragment, Drawable drawable) {
            this.mWkContainer = new WeakReference<>(baseNativeNoteEditFragment);
            this.editAreaBackground = drawable;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            BaseNativeNoteEditFragment baseNativeNoteEditFragment = this.mWkContainer.get();
            if (baseNativeNoteEditFragment != null) {
                baseNativeNoteEditFragment.onThemeAnimChanged(this.editAreaBackground, true);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            BaseNativeNoteEditFragment baseNativeNoteEditFragment = this.mWkContainer.get();
            if (baseNativeNoteEditFragment != null) {
                baseNativeNoteEditFragment.onThemeAnimChanged(null, false);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            BaseNativeNoteEditFragment baseNativeNoteEditFragment = this.mWkContainer.get();
            if (baseNativeNoteEditFragment != null) {
                baseNativeNoteEditFragment.onThemeAnimChanged(null, false);
            }
        }
    }

    public void getDragEventMimeType(DragEvent dragEvent) {
        ClipDescription clipDescription = dragEvent.getClipDescription();
        if (clipDescription != null) {
            if (clipDescription.hasMimeType(ShareAppsAdapter.TYPE_IMAGE)) {
                NotesTextStat.reportNoteDragEvent(AITextWidgetStat.TYPE_IMAGE);
            } else if (clipDescription.hasMimeType("audio/*")) {
                NotesTextStat.reportNoteDragEvent(AITextWidgetStat.TYPE_AUDIO);
            } else if (clipDescription.hasMimeType("linkcard/*")) {
                NotesTextStat.reportNoteDragEvent("linkcard");
            }
        }
    }

    private void handleReceiveContent() {
        String[] strArr = {"text/uri-list", ShareAppsAdapter.TYPE_IMAGE};
        if (Build.VERSION.SDK_INT >= 31) {
            this.mEditorView.setOnReceiveContentListener(strArr, new OnReceiveContentListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.33
                @Override // android.view.OnReceiveContentListener
                public ContentInfo onReceiveContent(View view, ContentInfo contentInfo) {
                    if (contentInfo.getSource() != 2) {
                        return contentInfo;
                    }
                    NoteClipManager.INSTANCE.showClipData(contentInfo.getClip(), BaseNativeNoteEditFragment.this.mEditorView);
                    return null;
                }
            });
        }
    }

    protected BaseDragListener getDragListener() {
        return new BaseDragListener() { // from class: com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment.34
            boolean mIsDrop = false;

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragStarted(View view, DragEvent dragEvent) {
                return view.onDragEvent(dragEvent);
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragEntered(View view, DragEvent dragEvent) {
                return view.onDragEvent(dragEvent);
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected void handleDragLocation(View view, DragEvent dragEvent) {
                this.mIsDrop = false;
                view.onDragEvent(dragEvent);
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragDrop(View view, DragEvent dragEvent) {
                this.mIsDrop = true;
                return view.onDragEvent(dragEvent);
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected void handleDragExit(View view, DragEvent dragEvent) {
                view.onDragEvent(dragEvent);
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragEnd(View view, DragEvent dragEvent) {
                if (this.mIsDrop) {
                    BaseNativeNoteEditFragment.this.mActionMode = EditorActionMode.EDIT_TITLE;
                    BaseNativeNoteEditFragment.this.getActionBarController().updateActionMode(EditorActionMode.EDIT_TITLE, false, false);
                    BaseNativeNoteEditFragment.this.mEditToolbarController.onEditTitleMode();
                }
                this.mIsDrop = false;
                return view.onDragEvent(dragEvent);
            }
        };
    }
}
