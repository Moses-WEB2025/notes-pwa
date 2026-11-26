package com.miui.notes.component.noteedit;

import android.animation.Animator;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.miui.common.tool.BlurHelper;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.INoteRichEditor;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.ui.view.NoteEditAudioRecordGroup;
import com.miui.pad.feature.notes.commonedit.PadEditModeToolbar;
import com.miui.pad.feature.notes.commonedit.PadRichTextIconHorizontalView;
import com.miui.richeditor.theme.Theme;
import miuix.animation.Folme;

/* JADX INFO: loaded from: classes4.dex */
public class BigScreenNoteEditToolbarController extends NoteEditToolbarController {
    private static final String TAG = "BigScreenNoteToolBarController";
    private View mAudioRecordView;
    private PadRichTextIconHorizontalView mRichTextIconHorizontalView;
    private ImageView mRichToolDivide;
    private View mToolInnerGroup;
    private RelativeLayout mToolbarRelativeView;
    private PadEditModeToolbar mToolbarView;

    public BigScreenNoteEditToolbarController(Activity activity, View view, INoteRichEditor iNoteRichEditor) {
        super(activity, view, iNoteRichEditor);
        this.mEditor.setEnableToggleAlignment(false);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController
    protected String getToolbarType() {
        return "BigScreen";
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController
    protected void createViewClickListener() {
        this.mViewClickListener = new BaseNoteEditToolbarController.ButtonClickListener(this);
    }

    @Override // com.miui.notes.component.noteedit.IBaseNoteEditToolbarController
    public void showGalleryPicker() {
        super.showGalleryPicker();
        if (this.mHandler != null) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.component.noteedit.BigScreenNoteEditToolbarController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showGalleryPicker$0();
                }
            }, !Utils.isExternalKeyboardEnable(NoteApp.getInstance()) ? 800L : 400L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showGalleryPicker$0() {
        PadEditModeToolbar padEditModeToolbar = this.mToolbarView;
        if (padEditModeToolbar != null) {
            padEditModeToolbar.showPhotoPicker();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    public void initView(View view) {
        super.initView(view);
        this.mToolbarView = (PadEditModeToolbar) view.findViewById(R.id.toolbar);
        this.mToolInnerGroup = view.findViewById(R.id.tool_inner_group);
        this.mRichTextIconHorizontalView = (PadRichTextIconHorizontalView) view.findViewById(R.id.icon_group);
        this.mToolbarRelativeView = (RelativeLayout) view.findViewById(R.id.toolbar_relative_layout);
        this.mToolbarView.setActionListener(getViewClickListener());
        this.mRichTextIconHorizontalView.init(this, getViewClickListener());
        this.mRichToolDivide = (ImageView) view.findViewById(R.id.rich_text_divide);
        if (RomUtils.isFoldDevice()) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mRichTextIconHorizontalView.getLayoutParams();
            marginLayoutParams.setMarginEnd(0);
            this.mRichTextIconHorizontalView.setLayoutParams(marginLayoutParams);
        }
        if (LiteUtils.isSuperLite() && RomUtils.isPadDevice()) {
            this.mToolbarView.setVisibility(8);
            this.mRichToolDivide.setVisibility(8);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mToolbarRelativeView.getLayoutParams();
            layoutParams.setMarginStart(0);
            this.mToolbarRelativeView.setLayoutParams(layoutParams);
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditContentMode() {
        super.onEditContentMode();
        this.mToolbarView.onEditMode();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void dismissPopupWindow() {
        PadEditModeToolbar padEditModeToolbar;
        super.dismissPopupWindow();
        if (this.mRichTextIconHorizontalView == null || (padEditModeToolbar = this.mToolbarView) == null) {
            return;
        }
        padEditModeToolbar.dismissPopupWindow();
        this.mRichTextIconHorizontalView.dismissPopupWindow();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void show(boolean z, boolean z2, boolean z3) {
        showToolbarWithAnim(z, z2, this.mNeedDelayShowInput ? 400L : 0L, null);
        isAiCanUse(z3);
        this.mNeedDelayShowInput = false;
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateTheme(Theme theme, NoteThemeResCache noteThemeResCache) {
        super.updateTheme(theme, noteThemeResCache);
        this.mToolbarView.updateStyle(noteThemeResCache);
        this.mRichToolDivide.setImageDrawable(noteThemeResCache.toolBarDivideIcon);
        this.mRichTextIconHorizontalView.updateStyle(noteThemeResCache);
        Theme.EditorStyle editorStyle = theme.getEditorStyle(this.mToolbarRootView.getContext(), System.currentTimeMillis());
        BlurHelper.setBlurBackground(this.mRichTextIconHorizontalView, null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor2);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void setMarginStart() {
        setMargin();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature, com.miui.richeditor.TextStyleStateListener
    public void isNeedLock(boolean z) {
        super.isNeedLock(z);
        this.mToolbarView.isNeedLock(z);
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertImage() {
        super.toggleInsertImage();
        this.mToolbarView.onToggleGallery();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertDoodle() {
        this.mToolbarView.onToggleDoodle();
        super.toggleInsertDoodle();
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void startAudioInput() {
        super.startAudioInput();
        this.mToolbarView.onRecordStart();
        this.mRichTextIconHorizontalView.setRichViewsEnable(false);
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void stopAudioInput() {
        super.stopAudioInput();
        this.mToolbarView.onRecordEnd();
        this.mRichTextIconHorizontalView.setRichViewsEnable(true);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onFocusAble(boolean z) {
        this.mRichTextIconHorizontalView.setFocusAble(z);
    }

    public void setMargin() {
        int dimensionPixelSize;
        if (RomUtils.isFoldDevice()) {
            dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.note_toolbar_padding_start);
        } else {
            dimensionPixelSize = (getContext().getResources().getConfiguration().orientation == 1 || UIUtils.isFreeFormScreen(getContext().getResources().getConfiguration()) || UIUtils.isInMultiWindowMode(getContext())) ? 0 : getContext().getResources().getDimensionPixelSize(R.dimen.pad_toolbar_margin_land);
        }
        Logger.INSTANCE.d(TAG, "setMargin: " + dimensionPixelSize);
        this.mToolbarView.setPadding(dimensionPixelSize, 0, 0, 0);
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onDestroy() {
        super.onDestroy();
        if (this.mAudioRecordGroup != null) {
            if (this.mIsShowAudioRecord) {
                this.mIsShowAudioRecord = false;
            }
            this.mAudioRecordGroup.stopWaveAnim();
            this.mAudioRecordGroup.reset();
            if (this.mExpandContainer != null) {
                this.mExpandContainer.removeAllViews();
                ViewGroup.LayoutParams layoutParams = this.mExpandContainer.getLayoutParams();
                layoutParams.height = 0;
                this.mExpandContainer.setLayoutParams(layoutParams);
            }
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.TextStyleStateListener
    public void isAiCanUse(boolean z) {
        this.mToolbarView.setAiState(z);
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void collapsePanel(Animator.AnimatorListener animatorListener, boolean z) {
        this.mToolbarView.resetActiveIcon();
        if (this.mIsShowThemeChooser) {
            this.mToolbarView.onShowThemePanel(false, null);
        }
        super.collapsePanel(animatorListener, z);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void updateAudioVolume(float f) {
        if (this.mAudioRecordGroup != null) {
            this.mAudioRecordGroup.updateWave(f);
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleAudioRecordPanel() {
        if (!this.mIsShowAudioRecord) {
            if (this.mIsShowTextStylePanel) {
                this.mEditor.setViewShowSoftInputOnFocus(true);
            }
            this.mIsShowTextStylePanel = false;
            if (this.mAudioRecordView == null) {
                View viewCreateAudioRecordPanel = createAudioRecordPanel();
                this.mAudioRecordView = viewCreateAudioRecordPanel;
                viewCreateAudioRecordPanel.measure(View.MeasureSpec.makeMeasureSpec(0, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(getContext().getResources().getDimensionPixelSize(R.dimen.note_edit_toolbar_content_height), 1073741824));
                this.mAudioRecordGroup = new NoteEditAudioRecordGroup(this.mEditor.getContext(), this.mAudioRecordView);
            }
            this.mIsShowThemeChooser = false;
            this.mIsShowAudioRecord = true;
            showEditContainer(this.mAudioRecordView, new Animator.AnimatorListener() { // from class: com.miui.notes.component.noteedit.BigScreenNoteEditToolbarController.1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    BigScreenNoteEditToolbarController.this.mAudioRecordGroup.reset();
                    BigScreenNoteEditToolbarController.this.mAudioRecordGroup.startWaveAnim();
                    if (RomUtils.isPadMode()) {
                        if ((!LiteUtils.isLiteOrMiddle() && !RomUtils.isInternationalBuild()) || BigScreenNoteEditToolbarController.this.getContext() == null || BigScreenNoteEditToolbarController.this.mToolbarContainer == null) {
                            return;
                        }
                        ViewGroup viewGroup = (ViewGroup) BigScreenNoteEditToolbarController.this.getContext().getWindow().getDecorView().findViewById(R.id.secondary_content_decor);
                        viewGroup.setPadding(viewGroup.getPaddingLeft(), viewGroup.getPaddingTop(), viewGroup.getPaddingRight(), BigScreenNoteEditToolbarController.this.getContext().getResources().getDimensionPixelSize(R.dimen.note_edit_toolbar_content_height));
                    }
                }
            });
            return;
        }
        closeAudioRecordPanel();
    }

    private View createAudioRecordPanel() {
        return UIUtils.inflateView(this.mExpandContainer, R.layout.edit_audio_record_panel);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController
    protected void showToolbarWithAnim(boolean z, boolean z2, long j, Runnable runnable) {
        this.mToolbarView.resetActiveIcon();
        super.showToolbarWithAnim(z, z2, j, runnable);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void resetEditBarStatus() {
        super.resetEditBarStatus();
        Folme.useAt(this.mRichTextIconHorizontalView).state().cancel();
        this.mRichTextIconHorizontalView.setTranslationX(0.0f);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController
    protected void hideToolbarWithAnim(Animator.AnimatorListener animatorListener) {
        this.mRichTextIconHorizontalView.animate().cancel();
        super.hideToolbarWithAnim(animatorListener);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected boolean updateState(int i) {
        return this.mRichTextIconHorizontalView.updateState(i);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected boolean updateSizeState(int i) {
        return this.mRichTextIconHorizontalView.updateSizeState(i);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.TextStyleStateListener
    public void onFontSize(int i) {
        super.onFontSize(i);
        updateSizeState(R.id.increase_font);
        updateSizeState(R.id.reduce_font);
    }
}
