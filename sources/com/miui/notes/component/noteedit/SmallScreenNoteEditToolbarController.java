package com.miui.notes.component.noteedit;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.miui.common.stat.EditStat;
import com.miui.common.tool.BlurHelper;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.INoteRichEditor;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.ui.view.EditModeToolbar;
import com.miui.notes.ui.view.NoteEditAudioRecordGroup;
import com.miui.notes.ui.view.RichTextIconHorizontalView;
import com.miui.pad.feature.notes.commonedit.HoverMotionImpl;
import com.miui.richeditor.theme.Theme;
import com.xiaomi.e2ee.E2EEException;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes4.dex */
public class SmallScreenNoteEditToolbarController extends NoteEditToolbarController {
    protected static String TAG = "SmallScreenNoteEditToolbarController";
    HoverMotionImpl hoverMotionImpl;
    private View mAudioRecordView;
    private NoteThemeResCache mResCache;
    private Drawable mRichTextIconCache;
    private RichTextIconHorizontalView mRichTextIconHorizontalView;
    private Drawable mRichTextReverseIconCache;
    private ImageView mRichToolDivide;
    private ImageView mRichToolSwitch;
    private SpringBackLayout mSpringBackLayout;
    private Theme mTheme;

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void setMarginStart() {
    }

    public SmallScreenNoteEditToolbarController(Activity activity, View view, INoteRichEditor iNoteRichEditor) {
        super(activity, view, iNoteRichEditor);
        this.mEditor.setEnableToggleAlignment(true);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController
    protected String getToolbarType() {
        return "SmallScreen";
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController
    protected void createViewClickListener() {
        this.mViewClickListener = new BaseNoteEditToolbarController.ButtonClickListener(this) { // from class: com.miui.notes.component.noteedit.SmallScreenNoteEditToolbarController.1
            @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController.ButtonClickListener, android.view.View.OnClickListener
            public void onClick(View view) {
                if (System.currentTimeMillis() - Utils.INSTANCE.getPerformActionClickTime() < 500) {
                    return;
                }
                super.onClick(view);
                if (view.getId() != R.id.rich_text_switch || SmallScreenNoteEditToolbarController.this.mIsLock) {
                    return;
                }
                if (SmallScreenNoteEditToolbarController.this.mExternalClickListener != null) {
                    SmallScreenNoteEditToolbarController.this.mExternalClickListener.onClick(view);
                }
                if (Math.abs(System.currentTimeMillis() - SmallScreenNoteEditToolbarController.this.lastClickTime) < 400) {
                    SmallScreenNoteEditToolbarController.this.isFastClick = true;
                } else {
                    SmallScreenNoteEditToolbarController.this.isFastClick = false;
                    SmallScreenNoteEditToolbarController.this.lastClickTime = System.currentTimeMillis();
                }
                if (!SmallScreenNoteEditToolbarController.this.isFastClick) {
                    if (SmallScreenNoteEditToolbarController.this.mIsShowTextStylePanel) {
                        SmallScreenNoteEditToolbarController.this.setTextStylePanelVisible(false, true);
                        if (SmallScreenNoteEditToolbarController.this.mRichTextIconHorizontalView != null) {
                            SmallScreenNoteEditToolbarController.this.mRichTextIconHorizontalView.resetOffset();
                        }
                    } else {
                        SmallScreenNoteEditToolbarController.this.setTextStylePanelVisible(true, true);
                    }
                    if (SmallScreenNoteEditToolbarController.this.hoverMotionImpl != null) {
                        SmallScreenNoteEditToolbarController.this.hoverMotionImpl.resetPopupWindowDes(SmallScreenNoteEditToolbarController.this.mRichToolSwitch);
                    }
                }
                EditStat.trackRichEditorControllerClick(EditStat.ELEMENT_SWITCH);
            }
        };
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    public void initView(View view) {
        super.initView(view);
        this.hoverMotionImpl = new HoverMotionImpl();
        this.mRichToolDivide = (ImageView) view.findViewById(R.id.rich_text_divide);
        ImageView imageView = (ImageView) view.findViewById(R.id.rich_text_switch);
        this.mRichToolSwitch = imageView;
        imageView.setOnClickListener(getViewClickListener());
        if (!LiteUtils.isLiteOrMiddle() && RomUtils.isNewTablet()) {
            this.hoverMotionImpl.setOnHoverListener(this.mRichToolSwitch);
        }
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.toolbar_relative_layout);
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        View viewFindViewById = view.findViewById(R.id.icon_group);
        View viewFindViewById2 = view.findViewById(R.id.icon_container);
        if (viewFindViewById != null && viewFindViewById2 != null) {
            SpringBackLayout springBackLayout = (SpringBackLayout) viewFindViewById2;
            this.mSpringBackLayout = springBackLayout;
            springBackLayout.setVisibility(8);
            RichTextIconHorizontalView richTextIconHorizontalView = (RichTextIconHorizontalView) viewFindViewById;
            this.mRichTextIconHorizontalView = richTextIconHorizontalView;
            richTextIconHorizontalView.init(this, getViewClickListener());
        } else {
            new AsyncLayoutInflater(view.getContext()).inflate(R.layout.expand_icon_group, null, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.miui.notes.component.noteedit.SmallScreenNoteEditToolbarController$$ExternalSyntheticLambda0
                @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
                public final void onInflateFinished(View view2, int i, ViewGroup viewGroup) {
                    this.f$0.lambda$initView$1(relativeLayout, layoutParams, view2, i, viewGroup);
                }
            });
        }
        ((EditModeToolbar) this.mToolbarView).setActionListener(getViewClickListener());
        this.mToolbarRootView.setOnClickListener(getViewClickListener());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(RelativeLayout relativeLayout, ViewGroup.LayoutParams layoutParams, View view, int i, ViewGroup viewGroup) {
        view.setVisibility(4);
        this.mSpringBackLayout = (SpringBackLayout) view;
        this.mRichTextIconHorizontalView = (RichTextIconHorizontalView) view.findViewById(R.id.icon_group);
        relativeLayout.addView(view, 1, layoutParams);
        this.mRichTextIconHorizontalView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.miui.notes.component.noteedit.SmallScreenNoteEditToolbarController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                this.f$0.lambda$initView$0(view2, i2, i3, i4, i5, i6, i7, i8, i9);
            }
        });
        this.mRichTextIconHorizontalView.init(this, getViewClickListener());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        updateRichTextIcon();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditContentMode() {
        super.onEditContentMode();
        ((EditModeToolbar) this.mToolbarView).onEditMode();
        this.mRichToolSwitch.setEnabled(true);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void dismissPopupWindow() {
        super.dismissPopupWindow();
        if (this.mRichTextIconHorizontalView == null || this.mToolbarView == null) {
            return;
        }
        this.mRichTextIconHorizontalView.dismissPopupWindow();
        ((EditModeToolbar) this.mToolbarView).dismissPopupWindow();
        HoverMotionImpl hoverMotionImpl = this.hoverMotionImpl;
        if (hoverMotionImpl != null) {
            hoverMotionImpl.dismissPopupWindow();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onViewMode() {
        super.onViewMode();
        ((EditModeToolbar) this.mToolbarView).onViewMode();
        this.mRichToolSwitch.setEnabled(false);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature, com.miui.richeditor.TextStyleStateListener
    public void isNeedLock(boolean z) {
        super.isNeedLock(z);
        ((EditModeToolbar) this.mToolbarView).isNeedLock(z);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleCheckBox() {
        ((EditModeToolbar) this.mToolbarView).onToggleCheck();
        super.toggleCheckBox();
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertImage() {
        super.toggleInsertImage();
        ((EditModeToolbar) this.mToolbarView).onToggleGallery();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertDoodle() {
        ((EditModeToolbar) this.mToolbarView).onToggleDoodle();
        super.toggleInsertDoodle();
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void startAudioInput() {
        super.startAudioInput();
        ((EditModeToolbar) this.mToolbarView).onRecordStart();
        this.mRichToolSwitch.setEnabled(false);
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void stopAudioInput() {
        this.mRichToolSwitch.setEnabled(true);
        ((EditModeToolbar) this.mToolbarView).onRecordEnd();
        super.stopAudioInput();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleAudioRecordPanel() {
        if (!this.mIsShowAudioRecord) {
            if (this.mIsShowTextStylePanel) {
                this.mEditor.setViewShowSoftInputOnFocus(true);
            }
            if (this.mToolbarContainer.getVisibility() != 0 || this.mToolbarRootView.getAlpha() != 1.0f) {
                this.mToolbarRootView.setVisibility(0);
                this.mToolbarRootView.setAlpha(1.0f);
            }
            this.mIsShowTextStylePanel = false;
            this.mIsShowThemeChooser = false;
            this.mIsShowAudioRecord = true;
            if (UIUtils.isFreeFormScreen(getContext().getResources().getConfiguration()) && RomUtils.isInternationalBuild() && RomUtils.isPadMode()) {
                return;
            }
            if (this.mAudioRecordView == null) {
                View viewCreateAudioRecordPanel = createAudioRecordPanel();
                this.mAudioRecordView = viewCreateAudioRecordPanel;
                viewCreateAudioRecordPanel.measure(View.MeasureSpec.makeMeasureSpec(0, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(getContext().getResources().getDimensionPixelSize(R.dimen.note_edit_toolbar_content_height), 1073741824));
                this.mAudioRecordGroup = new NoteEditAudioRecordGroup(this.mEditor.getContext(), this.mAudioRecordView);
            }
            showEditContainer(this.mAudioRecordView, new Animator.AnimatorListener() { // from class: com.miui.notes.component.noteedit.SmallScreenNoteEditToolbarController.2
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
                    SmallScreenNoteEditToolbarController.this.mAudioRecordGroup.reset();
                    SmallScreenNoteEditToolbarController.this.mAudioRecordGroup.startWaveAnim();
                    if (RomUtils.isPadMode()) {
                        if ((!LiteUtils.isLiteOrMiddle() && !RomUtils.isInternationalBuild()) || SmallScreenNoteEditToolbarController.this.getContext() == null || SmallScreenNoteEditToolbarController.this.mToolbarContainer == null) {
                            return;
                        }
                        ViewGroup viewGroup = (ViewGroup) SmallScreenNoteEditToolbarController.this.getContext().getWindow().getDecorView().findViewById(R.id.secondary_content_decor);
                        viewGroup.setPadding(viewGroup.getPaddingLeft(), viewGroup.getPaddingTop(), viewGroup.getPaddingRight(), SmallScreenNoteEditToolbarController.this.getContext().getResources().getDimensionPixelSize(R.dimen.note_edit_toolbar_content_height) + SmallScreenNoteEditToolbarController.this.mToolbarContainer.getHeight());
                    }
                }
            });
            return;
        }
        closeAudioRecordPanel();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateTheme(Theme theme, NoteThemeResCache noteThemeResCache) {
        super.updateTheme(theme, noteThemeResCache);
        this.mRichTextIconCache = noteThemeResCache.toolBarRichTextIcon;
        this.mRichTextReverseIconCache = noteThemeResCache.toolBarRichTextReverseIcon;
        if (isTextStylePanelShowed()) {
            this.mRichToolSwitch.setImageDrawable(this.mRichTextReverseIconCache);
            ImageView imageView = this.mRichToolSwitch;
            imageView.setContentDescription(imageView.getResources().getString(R.string.rich_text_expand_description));
        } else {
            this.mRichToolSwitch.setImageDrawable(this.mRichTextIconCache);
            ImageView imageView2 = this.mRichToolSwitch;
            imageView2.setContentDescription(imageView2.getResources().getString(R.string.rich_text_collapse_description));
        }
        ((EditModeToolbar) this.mToolbarView).updateStyle(noteThemeResCache);
        this.mRichToolDivide.setImageDrawable(noteThemeResCache.toolBarDivideIcon);
        this.mTheme = theme;
        this.mResCache = noteThemeResCache;
        updateRichTextIcon();
    }

    private void updateRichTextIcon() {
        NoteThemeResCache noteThemeResCache;
        RichTextIconHorizontalView richTextIconHorizontalView;
        if (this.mTheme == null || (noteThemeResCache = this.mResCache) == null || (richTextIconHorizontalView = this.mRichTextIconHorizontalView) == null) {
            return;
        }
        richTextIconHorizontalView.updateStyle(noteThemeResCache);
        Theme.EditorStyle editorStyle = this.mTheme.getEditorStyle(this.mRichTextIconHorizontalView.getContext());
        BlurHelper.setBlurBackground(this.mRichTextIconHorizontalView, null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor2);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void updateAudioVolume(float f) {
        if (this.mAudioRecordGroup != null) {
            this.mAudioRecordGroup.updateWave(f);
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void setTextStylePanelVisible(boolean z, boolean z2) {
        if (this.mSpringBackLayout == null) {
            return;
        }
        if (this.mToolbarContainer.getVisibility() != 0) {
            super.setTextStylePanelVisible(z, z2);
            return;
        }
        if (z) {
            if (this.mIsShowTextStylePanel && z2) {
                return;
            }
            toggleRichTool(this.mRichToolSwitch, z2);
            this.mRichToolDivide.setVisibility(0);
            if (z2) {
                Folme.use(this.mToolbarView).state().setTo(ViewProperty.TRANSLATION_X, 0).to(ViewProperty.TRANSLATION_X, Integer.valueOf(E2EEException.DECODE_DATA_ERROR));
                this.mSpringBackLayout.setVisibility(0);
                Folme.use((View) this.mSpringBackLayout).state().setTo(ViewProperty.SCROLL_X, Integer.valueOf(E2EEException.DECODE_DATA_ERROR), ViewProperty.ALPHA, Float.valueOf(0.0f)).to(ViewProperty.SCROLL_X, 0, ViewProperty.ALPHA, Float.valueOf(1.0f)).to(new AnimConfig[0]);
                Folme.use(this.mToolbarView).visible().hide(new AnimConfig().setEase(-2, 0.9f, 0.2f));
            } else {
                this.mToolbarView.setTranslationX(-400.0f);
                this.mToolbarView.setVisibility(8);
                this.mSpringBackLayout.setVisibility(0);
                this.mSpringBackLayout.setAlpha(1.0f);
                this.mSpringBackLayout.setScrollX(0);
            }
        } else {
            if (!this.mIsShowTextStylePanel && z2) {
                return;
            }
            toggleRichToolReverse(this.mRichToolSwitch, z2);
            this.mRichToolDivide.setVisibility(4);
            if (z2) {
                Folme.use((View) this.mSpringBackLayout).state().to(ViewProperty.SCROLL_X, Integer.valueOf(E2EEException.DECODE_DATA_ERROR));
                this.mToolbarView.setVisibility(0);
                Folme.use(this.mToolbarView).state().setTo(ViewProperty.TRANSLATION_X, Integer.valueOf(E2EEException.DECODE_DATA_ERROR), ViewProperty.ALPHA, Float.valueOf(0.0f)).to(ViewProperty.TRANSLATION_X, 0, ViewProperty.ALPHA, Float.valueOf(1.0f)).to(new AnimConfig[0]);
                Folme.use((View) this.mSpringBackLayout).visible().hide(new AnimConfig().setEase(-2, 0.9f, 0.2f));
            } else {
                this.mToolbarView.setTranslationX(0.0f);
                this.mToolbarView.setAlpha(1.0f);
                this.mToolbarView.setVisibility(0);
                this.mSpringBackLayout.setVisibility(8);
                this.mSpringBackLayout.setAlpha(0.0f);
                this.mSpringBackLayout.setScrollX(E2EEException.DECODE_DATA_ERROR);
            }
        }
        super.setTextStylePanelVisible(z, z2);
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void collapsePanel(Animator.AnimatorListener animatorListener, boolean z) {
        if (this.mIsShowThemeChooser) {
            ((EditModeToolbar) this.mToolbarView).onShowThemePanel(false, null);
        }
        super.collapsePanel(animatorListener, z);
        this.mIsShowTextStylePanel = false;
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onDestroy() {
        super.onDestroy();
        this.mRichToolSwitch.setOnClickListener(null);
        this.mToolbarRootView.setOnClickListener(null);
        Logger.INSTANCE.d(TAG, "onDestroy,clear listeners");
        if (this.mAudioRecordGroup != null) {
            if (this.mIsShowAudioRecord) {
                this.mIsShowAudioRecord = false;
            }
            this.mAudioRecordGroup.stopWaveAnim();
            this.mAudioRecordGroup.reset();
        }
        HoverMotionImpl hoverMotionImpl = this.hoverMotionImpl;
        if (hoverMotionImpl != null) {
            hoverMotionImpl.onDetach();
        }
        if (this.mExpandContainer != null) {
            this.mExpandContainer.removeAllViews();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController
    protected void hideToolbarWithAnim(Animator.AnimatorListener animatorListener) {
        SpringBackLayout springBackLayout = this.mSpringBackLayout;
        if (springBackLayout != null) {
            springBackLayout.animate().cancel();
        }
        super.hideToolbarWithAnim(animatorListener);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void resetEditBarStatus() {
        super.resetEditBarStatus();
        SpringBackLayout springBackLayout = this.mSpringBackLayout;
        if (springBackLayout != null) {
            Folme.useAt(springBackLayout).state().cancel();
            this.mSpringBackLayout.setTranslationX(0.0f);
            this.mSpringBackLayout.setVisibility(8);
        }
        this.mRichToolDivide.setVisibility(4);
        if (this.mNoteTheme != null) {
            this.mRichToolSwitch.setImageDrawable(this.mNoteTheme.getToolBarStyle(this.mExpandContainer.getContext()).getRichTextStaticIcon());
        }
    }

    protected View createAudioRecordPanel() {
        return UIUtils.inflateView(this.mExpandContainer, R.layout.edit_audio_record_panel);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected boolean updateState(int i) {
        RichTextIconHorizontalView richTextIconHorizontalView = this.mRichTextIconHorizontalView;
        if (richTextIconHorizontalView != null) {
            return richTextIconHorizontalView.updateState(i);
        }
        return false;
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected boolean updateSizeState(int i) {
        RichTextIconHorizontalView richTextIconHorizontalView = this.mRichTextIconHorizontalView;
        if (richTextIconHorizontalView != null) {
            return richTextIconHorizontalView.updateSizeState(i);
        }
        return false;
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.TextStyleStateListener
    public void onFontSize(int i) {
        super.onFontSize(i);
        updateSizeState(R.id.increase_font);
        updateSizeState(R.id.reduce_font);
    }

    private void toggleRichToolReverse(ImageView imageView, boolean z) {
        this.mRichToolSwitch.setContentDescription(imageView.getResources().getString(R.string.rich_text_expand_description));
        this.mRichToolSwitch.setImageDrawable(this.mRichTextReverseIconCache);
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageView.getDrawable();
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.start();
            if (z) {
                return;
            }
            animatedVectorDrawable.stop();
        }
    }

    private void toggleRichTool(ImageView imageView, boolean z) {
        this.mRichToolSwitch.setContentDescription(imageView.getResources().getString(R.string.rich_text_collapse_description));
        this.mRichToolSwitch.setImageDrawable(this.mRichTextIconCache);
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageView.getDrawable();
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.start();
            if (z) {
                return;
            }
            animatedVectorDrawable.stop();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.TextStyleStateListener
    public void isAiCanUse(boolean z) {
        ((EditModeToolbar) this.mToolbarView).setAiState(z);
    }

    @Override // com.miui.notes.component.noteedit.IBaseNoteEditToolbarController
    public void showGalleryPicker() {
        super.showGalleryPicker();
        if (this.mHandler != null) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.component.noteedit.SmallScreenNoteEditToolbarController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showGalleryPicker$2();
                }
            }, !Utils.isExternalKeyboardEnable(NoteApp.getInstance()) ? 800L : 400L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showGalleryPicker$2() {
        if (this.mToolbarView == null || !(this.mToolbarView instanceof EditModeToolbar)) {
            return;
        }
        ((EditModeToolbar) this.mToolbarView).showPhotoPicker();
    }
}
