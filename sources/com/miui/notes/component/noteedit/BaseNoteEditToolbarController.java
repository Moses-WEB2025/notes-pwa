package com.miui.notes.component.noteedit;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.CheckedTextView;
import com.miui.common.stat.EditStat;
import com.miui.common.tool.BlurHelper;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.component.ThemeChooserGroup;
import com.miui.notes.editor.ReadOnlyActionListener;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.theme.SimpleTheme;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.richeditor.theme.Theme;
import defpackage.R2;
import java.lang.ref.WeakReference;
import miuix.animation.Folme;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseNoteEditToolbarController extends BaseNoteRichEditToolbarController {
    protected static String TAG = "BaseNoteEditToolbarController";
    public boolean isFastClick;
    public long lastClickTime;
    protected ValueAnimator mContainerCollapseAnimator;
    protected ValueAnimator mContainerExpandAnimator;
    protected ViewGroup mExpandContainer;
    protected View.OnClickListener mExternalClickListener;
    protected boolean mIsExpandEditContainer;
    protected boolean mIsLock;
    protected boolean mIsShowAudioRecord;
    protected boolean mIsShowTextStylePanel;
    protected boolean mIsShowThemeChooser;
    protected boolean mIsUpdateLayoutFromController;
    protected View mNaviPlaceHolder;
    protected int mNavigationBarHeight;
    protected boolean mNeedDelayShowInput;
    protected ThemeChooserGroup.OnThemeChosenListener mOnThemeChosenListener;
    protected INoteEditToolBar.OnVisibleChangedListener mOnVisibleChangedListener;
    protected ThemeChooserGroup.OnThemeChosenListener mOutOnThemeChosenListener;
    protected ReadOnlyActionListener mReadOnlyActionListener;
    protected Runnable mShowSoftInputTask;
    protected ThemeChooserGroup mThemeChooserGroup;
    protected View mThemeChooserView;
    protected View mToolbarContainer;
    public boolean mToolbarIsShow;
    protected View mToolbarView;
    protected View.OnClickListener mViewClickListener;
    protected WorkingNote mWorkingNote;
    private ViewPropertyAnimator toolBarAnimator;
    private ViewPropertyAnimator toolBarContainerAnimator;

    protected abstract void createViewClickListener();

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void dismissPopupWindow() {
    }

    protected abstract String getToolbarType();

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature, com.miui.richeditor.TextStyleStateListener
    public void isNeedLock(boolean z) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onConfigChanged() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditContentMode() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onFocusAble(boolean z) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onViewMode() {
    }

    protected abstract void requestEditorFocusAndShowSoftInput();

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setMarginStart() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void startAudioInput() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void stopAudioInput() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleAudioRecordPanel() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertImage() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void updateAudioVolume(float f) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateLayout() {
    }

    public BaseNoteEditToolbarController(Activity activity, View view) {
        super(activity, view);
        this.mIsLock = false;
        this.isFastClick = false;
        this.mNeedDelayShowInput = false;
        this.mReadOnlyActionListener = null;
        this.mIsUpdateLayoutFromController = false;
        this.mToolbarIsShow = false;
        this.mOutOnThemeChosenListener = null;
        this.mOnThemeChosenListener = new ThemeChooserGroup.OnThemeChosenListener() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController.1
            @Override // com.miui.notes.component.ThemeChooserGroup.OnThemeChosenListener
            public void onThemeChosen(int i) {
                if (BaseNoteEditToolbarController.this.mIsLock) {
                    return;
                }
                BaseNoteEditToolbarController.this.mThemeChooserGroup.updateColor(i);
                if (BaseNoteEditToolbarController.this.mOutOnThemeChosenListener != null) {
                    BaseNoteEditToolbarController.this.mOutOnThemeChosenListener.onThemeChosen(i);
                }
            }
        };
        this.mShowSoftInputTask = new Runnable() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.requestEditorFocusAndShowSoftInput();
            }
        };
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    public void initView(View view) {
        super.initView(view);
        createViewClickListener();
        if (view != null) {
            this.mToolbarContainer = view.findViewById(R.id.toolbar_container);
            this.mToolbarView = view.findViewById(R.id.toolbar);
            this.mExpandContainer = (ViewGroup) view.findViewById(R.id.expand_container);
            this.mNaviPlaceHolder = view.findViewById(R.id.navi_placeholder);
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public View.OnClickListener getViewClickListener() {
        return this.mViewClickListener;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void show(boolean z) {
        show(true, false, z);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void show(boolean z, boolean z2, boolean z3) {
        int i = LiteUtils.isNewLiteOrMiddle() ? R2.array.ai_tag_top_item_gradient_border_colors : 0;
        if (this.mNeedDelayShowInput) {
            i = 400;
        }
        showToolbarWithAnim(z, z2, i, null);
        isAiCanUse(z3);
        this.mNeedDelayShowInput = false;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void hide(boolean z) {
        Logger.INSTANCE.d(TAG, "hide(isStartAiTextWindow:" + z + ")");
        if (z) {
            this.mToolbarIsShow = false;
            this.mIsShowTextStylePanel = false;
            if (this.mHandler != null) {
                this.mHandler.removeCallbacks(this.mShowSoftInputTask);
            }
            ViewPropertyAnimator viewPropertyAnimatorAnimate = this.mToolbarContainer.animate();
            this.toolBarContainerAnimator = viewPropertyAnimatorAnimate;
            viewPropertyAnimatorAnimate.cancel();
            ViewPropertyAnimator viewPropertyAnimatorAnimate2 = this.mToolbarRootView.animate();
            this.toolBarAnimator = viewPropertyAnimatorAnimate2;
            viewPropertyAnimatorAnimate2.cancel();
            if (BlurHelper.isEnableBlur() && this.mToolbarRootView.getBackground() != null) {
                this.mToolbarRootView.setBackground(null);
            }
            this.mToolbarRootView.setVisibility(8);
            if (this.mIsShowThemeChooser) {
                this.mIsShowThemeChooser = false;
                hideEditContainerDirectly();
                return;
            }
            return;
        }
        hideToolbarWithAnim(null);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void hideWithoutAnim() {
        Logger.INSTANCE.d(TAG, "hideWithoutAnim");
        hideToolbarWithoutAnim();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void hideToolbarAndShowInput() {
        hide(false);
        if (this.mHandler == null) {
            return;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$hideToolbarAndShowInput$0();
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideToolbarAndShowInput$0() {
        if (getContext() != null) {
            Utils.showSoftInput(this.mToolbarContainer, false);
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void setExternalClickListener(View.OnClickListener onClickListener) {
        this.mExternalClickListener = onClickListener;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void setReadOnlyActionListener(ReadOnlyActionListener readOnlyActionListener) {
        this.mReadOnlyActionListener = readOnlyActionListener;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void lock() {
        this.mIsLock = true;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void unlock() {
        this.mIsLock = false;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onViewMode(boolean z) {
        if (z) {
            hide(false);
            onViewMode();
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditTitleMode() {
        hideToolbarAndShowInput();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditContentMode(boolean z, boolean z2, boolean z3) {
        Logger.INSTANCE.d(TAG, "onEditContentMode(showToolBar:" + z + ",showRichTextTool:" + z2 + ",canUseAi:" + z3 + ")");
        show(z, z2, z3);
        onEditContentMode();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onFirstEditContentMode(boolean z, boolean z2, boolean z3) {
        showToolbarWithoutAnim(z, z2, 0L, null);
        isAiCanUse(z3);
        onEditContentMode();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateTheme(Theme theme, NoteThemeResCache noteThemeResCache) {
        this.mNoteTheme = theme;
        Theme.EditorStyle editorStyle = theme.getEditorStyle(this.mToolbarRootView.getContext());
        BlurHelper.setBlurBackground(this.mToolbarRootView, null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor2);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateNavigationHeight(int i) {
        Logger.INSTANCE.d(TAG, "updateNavigationHeight,barHeight:" + i);
        this.mNavigationBarHeight = i;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public boolean onBackPressed() {
        Logger.INSTANCE.d(TAG, "onBackPressed");
        if (!this.mIsExpandEditContainer) {
            return false;
        }
        collapsePanel(null);
        return true;
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onDestroy() {
        Logger.INSTANCE.d(TAG, "onDestroy");
        super.onDestroy();
        if (this.mIsExpandEditContainer) {
            collapsePanel(null);
        }
        ValueAnimator valueAnimator = this.mContainerExpandAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mContainerExpandAnimator.removeAllUpdateListeners();
            this.mContainerExpandAnimator.removeAllListeners();
            this.mContainerExpandAnimator = null;
        }
        ValueAnimator valueAnimator2 = this.mContainerCollapseAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
            this.mContainerCollapseAnimator.removeAllUpdateListeners();
            this.mContainerCollapseAnimator.removeAllListeners();
            this.mContainerCollapseAnimator = null;
        }
        if (this.toolBarAnimator != null) {
            if (this.mToolbarRootView != null) {
                this.mToolbarRootView.setAlpha(0.0f);
            }
            this.toolBarAnimator.cancel();
            this.toolBarAnimator.setListener(null);
        }
        ViewPropertyAnimator viewPropertyAnimator = this.toolBarContainerAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            this.toolBarContainerAnimator.setListener(null);
        }
        this.mOutOnThemeChosenListener = null;
        setOnVisibleChangedListener(null);
        setExternalClickListener(null);
        this.mViewClickListener = null;
        setExternalClickListener(null);
        setReadOnlyActionListener(null);
        setOnThemeChosenListener(null);
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleCheckBox() {
        checkbox();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertDoodle() {
        if (this.mIsShowThemeChooser) {
            toggleThemeChooserFromHead();
            hide(false);
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setTextStylePanelVisible(boolean z, boolean z2) {
        this.mIsShowTextStylePanel = z;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void collapsePanel(Animator.AnimatorListener animatorListener, boolean z) {
        Logger.INSTANCE.d(TAG, "collapsePanel,hideToolbar:" + z);
        this.mIsShowTextStylePanel = false;
        this.mIsShowThemeChooser = false;
        if (!z && this.mIsExpandEditContainer && KeyboardHelper.INSTANCE.getShowKeyboard() && this.mExpandContainer != null) {
            collapseExpandContainerImmediately();
            if (animatorListener != null) {
                animatorListener.onAnimationEnd(null);
                return;
            }
            return;
        }
        if (animatorListener == null && z) {
            animatorListener = new Animator.AnimatorListener() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController.2
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    BaseNoteEditToolbarController.this.hide(false);
                }
            };
        }
        hideEditContainer(this.mExpandContainer.getHeight(), 0, animatorListener);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void collapsePanel(Animator.AnimatorListener animatorListener) {
        collapsePanel(null, true);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setWorkingNote(WorkingNote workingNote) {
        this.mWorkingNote = workingNote;
        ThemeChooserGroup themeChooserGroup = this.mThemeChooserGroup;
        if (themeChooserGroup != null) {
            themeChooserGroup.updateColor(workingNote.getThemeId());
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setNeedDelayShowInput(boolean z) {
        this.mNeedDelayShowInput = z;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean isTextStylePanelShowed() {
        return this.mIsShowTextStylePanel;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean ismToolbarIsShow() {
        return this.mToolbarIsShow;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setOnThemeChosenListener(ThemeChooserGroup.OnThemeChosenListener onThemeChosenListener) {
        this.mOutOnThemeChosenListener = onThemeChosenListener;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean shouldHideSoftInput() {
        return this.mIsExpandEditContainer;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void checkAndCollapseThemePanel() {
        Logger.INSTANCE.d(TAG, "checkAndCollapseThemePanel");
        if (this.mIsShowThemeChooser) {
            collapsePanel(new Animator.AnimatorListener() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController.3
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    Utils.hideSoftInput(BaseNoteEditToolbarController.this.mToolbarView);
                }
            });
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setOnVisibleChangedListener(INoteEditToolBar.OnVisibleChangedListener onVisibleChangedListener) {
        this.mOnVisibleChangedListener = onVisibleChangedListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVisibleChanged(boolean z) {
        INoteEditToolBar.OnVisibleChangedListener onVisibleChangedListener = this.mOnVisibleChangedListener;
        if (onVisibleChangedListener != null) {
            onVisibleChangedListener.onVisibilityChanged(z);
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleThemeChooserFromHead() {
        int id;
        Logger.INSTANCE.d(TAG, "toggleThemeChooserFromHead");
        updateLayout();
        if (!this.mIsShowThemeChooser) {
            this.mToolbarView.setVisibility(8);
            this.mToolbarContainer.setVisibility(8);
            this.mToolbarRootView.setVisibility(0);
            onVisibleChanged(true);
            if (BlurHelper.isEnableBlur()) {
                this.mToolbarRootView.setAlpha(0.0f);
                this.mToolbarRootView.setBackground(null);
            }
            WorkingNote workingNote = this.mWorkingNote;
            if (workingNote != null) {
                id = workingNote.getThemeId();
            } else {
                id = this.mNoteTheme != null ? this.mNoteTheme.getId() : 0;
            }
            if (this.mThemeChooserView == null) {
                View viewCreateThemeChooser = createThemeChooser();
                this.mThemeChooserView = viewCreateThemeChooser;
                viewCreateThemeChooser.measure(View.MeasureSpec.makeMeasureSpec(0, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(getContext().getResources().getDimensionPixelSize(R.dimen.note_edit_toolbar_content_height), 1073741824));
                ThemeChooserGroup themeChooserGroup = new ThemeChooserGroup(getContext(), this.mThemeChooserView, id);
                this.mThemeChooserGroup = themeChooserGroup;
                themeChooserGroup.setOnThemeChosenListener(this.mOnThemeChosenListener);
            }
            this.mIsShowThemeChooser = true;
            this.mIsShowAudioRecord = false;
            this.mIsShowTextStylePanel = false;
            showEditContainer(this.mThemeChooserView, new Animator.AnimatorListener() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController.4
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    BaseNoteEditToolbarController.this.mToolbarRootView.setAlpha(1.0f);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (BlurHelper.isEnableBlur() && BaseNoteEditToolbarController.this.mToolbarRootView.getBackground() == null) {
                        Theme.EditorStyle editorStyle = BaseNoteEditToolbarController.this.mNoteTheme.getEditorStyle(BaseNoteEditToolbarController.this.mToolbarRootView.getContext());
                        BlurHelper.setBlurBackground(BaseNoteEditToolbarController.this.mToolbarRootView, null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor2);
                    }
                }
            });
            return;
        }
        this.mIsShowThemeChooser = false;
        hideEditContainer(this.mExpandContainer.getHeight(), 0, new Animator.AnimatorListener() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController.5
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BaseNoteEditToolbarController.this.mToolbarRootView.setAlpha(0.0f);
                BaseNoteEditToolbarController.this.mToolbarRootView.setVisibility(8);
                BaseNoteEditToolbarController.this.onVisibleChanged(false);
            }
        });
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void resetEditBarStatus() {
        Folme.useAt(this.mToolbarView).state().cancel();
        this.mToolbarView.setTranslationX(0.0f);
    }

    protected boolean isReadOnly() {
        WorkingNote workingNote = this.mWorkingNote;
        if (workingNote == null) {
            return true;
        }
        return workingNote.getReadonly();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public boolean checkbox() {
        if (!isCheckbox() && !canAddNewElement(40, R.string.warn_cannot_add_new_checkbox)) {
            return false;
        }
        if (isReadOnly()) {
            ReadOnlyActionListener readOnlyActionListener = this.mReadOnlyActionListener;
            if (readOnlyActionListener == null) {
                return true;
            }
            readOnlyActionListener.onConfirmEdit(new Runnable() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.doSetCheckbox();
                }
            });
            return true;
        }
        doSetCheckbox();
        return true;
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public boolean listBullet() {
        if (!isListBullet() && !canAddNewElement(10, R.string.warn_cannot_add_new_bullet)) {
            return false;
        }
        doSetListBullet();
        return true;
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public boolean bullet() {
        if (!isBullet() && !canAddNewElement(10, R.string.warn_cannot_add_new_bullet)) {
            return false;
        }
        if (isReadOnly()) {
            ReadOnlyActionListener readOnlyActionListener = this.mReadOnlyActionListener;
            if (readOnlyActionListener == null) {
                return true;
            }
            readOnlyActionListener.onConfirmEdit(new Runnable() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$bullet$1();
                }
            });
            return true;
        }
        doSetBullet();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bullet$1() {
        doSetBullet();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected boolean updateState(int i) {
        CheckedTextView checkedTextView = (CheckedTextView) this.mToolbarView.findViewById(i);
        if (checkedTextView == null) {
            return false;
        }
        checkedTextView.setChecked(getRichStyleState(i));
        return true;
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected boolean updateSizeState(int i) {
        CheckedTextView checkedTextView = (CheckedTextView) this.mToolbarView.findViewById(i);
        if (checkedTextView == null) {
            return false;
        }
        checkedTextView.setChecked(getFontSizeType(i));
        return true;
    }

    protected boolean isLock() {
        return this.mIsLock;
    }

    protected void performExternalClick(View view) {
        Logger.INSTANCE.d(TAG, "performExternalClick " + view);
        View.OnClickListener onClickListener = this.mExternalClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    protected void showToolbarWithoutAnim(boolean z, boolean z2, long j, Runnable runnable) {
        Logger.INSTANCE.d(TAG, "showToolbarWithoutAnim(showSoftInput:" + z + ",showRichTextTool:" + z2 + ")");
        this.mToolbarIsShow = true;
        this.mIsUpdateLayoutFromController = true;
        updateLayout();
        this.mToolbarContainer.setAlpha(1.0f);
        this.mToolbarContainer.setVisibility(0);
        this.mToolbarRootView.setAlpha(1.0f);
        this.mToolbarRootView.setVisibility(0);
        onVisibleChanged(true);
        setTextStylePanelVisible(z2, false);
        if (runnable != null) {
            runnable.run();
        }
    }

    protected void showToolbarWithAnim(boolean z, final boolean z2, long j, final Runnable runnable) {
        Logger.INSTANCE.d(TAG, "showToolbarWithAnim(showSoftInput:" + z + ",showRichTextTool:" + z2 + ")");
        this.mIsUpdateLayoutFromController = true;
        this.mToolbarIsShow = true;
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mShowSoftInputTask);
        }
        updateLayout();
        resetEditBarStatus();
        ViewPropertyAnimator viewPropertyAnimatorAnimate = this.mToolbarRootView.animate();
        this.toolBarAnimator = viewPropertyAnimatorAnimate;
        viewPropertyAnimatorAnimate.cancel();
        this.mToolbarRootView.setAlpha(0.0f);
        this.toolBarAnimator.alpha(1.0f).setDuration(200L).setListener(new Animator.AnimatorListener() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController.6
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "showToolbarWithAnim Start");
                BaseNoteEditToolbarController.this.mToolbarContainer.setAlpha(1.0f);
                BaseNoteEditToolbarController.this.mToolbarContainer.setVisibility(0);
                BaseNoteEditToolbarController.this.mToolbarRootView.setVisibility(0);
                BaseNoteEditToolbarController.this.onVisibleChanged(true);
                BaseNoteEditToolbarController.this.setTextStylePanelVisible(z2, false);
                if (BlurHelper.isEnableBlur()) {
                    SimpleTheme simpleTheme = (SimpleTheme) BaseNoteEditToolbarController.this.mNoteTheme;
                    BlurHelper.setBlurBackground(BaseNoteEditToolbarController.this.mToolbarRootView, null, simpleTheme.getBlurMixColor(), simpleTheme.getBlurMixColor2());
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "showToolbarWithAnim End");
                BaseNoteEditToolbarController.this.mToolbarRootView.setVisibility(0);
                BaseNoteEditToolbarController.this.mToolbarRootView.setAlpha(1.0f);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                BaseNoteEditToolbarController.this.setTextStylePanelVisible(z2, false);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "showToolbarWithAnim cancel");
            }
        }).start();
        if (this.mToolbarContainer.getVisibility() != 0) {
            resetEditBarStatus();
            ViewPropertyAnimator viewPropertyAnimatorAnimate2 = this.mToolbarContainer.animate();
            this.toolBarContainerAnimator = viewPropertyAnimatorAnimate2;
            viewPropertyAnimatorAnimate2.cancel();
            this.mToolbarContainer.setVisibility(0);
            this.mToolbarContainer.setAlpha(0.0f);
            this.toolBarContainerAnimator.alpha(1.0f).setListener(new Animator.AnimatorListener() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController.7
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (BlurHelper.isEnableBlur()) {
                        Theme.EditorStyle editorStyle = BaseNoteEditToolbarController.this.mNoteTheme.getEditorStyle(BaseNoteEditToolbarController.this.mToolbarRootView.getContext());
                        BlurHelper.setBlurBackground(BaseNoteEditToolbarController.this.mToolbarRootView, null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor2);
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    BaseNoteEditToolbarController.this.setTextStylePanelVisible(z2, false);
                    BaseNoteEditToolbarController.this.mToolbarContainer.setAlpha(1.0f);
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            }).setDuration(200L).start();
        }
        if (!z || this.mHandler == null) {
            return;
        }
        this.mHandler.postDelayed(this.mShowSoftInputTask, j + 200);
    }

    public void hideToolbarWithoutAnim() {
        this.mToolbarIsShow = false;
        this.mIsShowTextStylePanel = false;
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mShowSoftInputTask);
        }
        ViewPropertyAnimator viewPropertyAnimatorAnimate = this.mToolbarContainer.animate();
        this.toolBarContainerAnimator = viewPropertyAnimatorAnimate;
        viewPropertyAnimatorAnimate.cancel();
        ViewPropertyAnimator viewPropertyAnimatorAnimate2 = this.mToolbarRootView.animate();
        this.toolBarAnimator = viewPropertyAnimatorAnimate2;
        viewPropertyAnimatorAnimate2.cancel();
        if (BlurHelper.isEnableBlur() && this.mToolbarRootView.getBackground() != null) {
            this.mToolbarRootView.setBackground(null);
        }
        this.mToolbarRootView.setVisibility(8);
        onVisibleChanged(false);
        if (this.mIsShowThemeChooser) {
            this.mIsShowThemeChooser = false;
            hideEditContainerDirectly();
        }
        Logger.INSTANCE.e(TAG, "hideToolbarWithoutAnim");
    }

    protected void hideToolbarWithAnim(final Animator.AnimatorListener animatorListener) {
        Logger.INSTANCE.d(TAG, "hideToolbarWithAnim with listener:" + animatorListener);
        this.mToolbarIsShow = false;
        this.mIsShowTextStylePanel = false;
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mShowSoftInputTask);
        }
        ViewPropertyAnimator viewPropertyAnimatorAnimate = this.mToolbarContainer.animate();
        this.toolBarContainerAnimator = viewPropertyAnimatorAnimate;
        viewPropertyAnimatorAnimate.cancel();
        ViewPropertyAnimator viewPropertyAnimatorAnimate2 = this.mToolbarRootView.animate();
        this.toolBarAnimator = viewPropertyAnimatorAnimate2;
        viewPropertyAnimatorAnimate2.cancel();
        this.toolBarAnimator.alpha(0.0f).setDuration(200L).setListener(new Animator.AnimatorListener() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController.8
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "onAnimationStart ");
                if (BlurHelper.isEnableBlur() && BaseNoteEditToolbarController.this.mToolbarRootView.getBackground() != null) {
                    BaseNoteEditToolbarController.this.mToolbarRootView.setBackground(null);
                }
                Animator.AnimatorListener animatorListener2 = animatorListener;
                if (animatorListener2 != null) {
                    animatorListener2.onAnimationStart(animator);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "onAnimationEnd ");
                BaseNoteEditToolbarController.this.mToolbarRootView.setVisibility(8);
                BaseNoteEditToolbarController.this.onVisibleChanged(false);
                Animator.AnimatorListener animatorListener2 = animatorListener;
                if (animatorListener2 != null) {
                    animatorListener2.onAnimationEnd(animator);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "onAnimationCancel ");
                BaseNoteEditToolbarController.this.mToolbarRootView.setVisibility(8);
                BaseNoteEditToolbarController.this.onVisibleChanged(false);
                Animator.AnimatorListener animatorListener2 = animatorListener;
                if (animatorListener2 != null) {
                    animatorListener2.onAnimationCancel(animator);
                }
            }
        }).start();
        if (this.mIsShowThemeChooser) {
            this.mIsShowThemeChooser = false;
            hideEditContainerDirectly();
        }
    }

    protected void showEditContainer(View view, Animator.AnimatorListener animatorListener) {
        if (view == null) {
            return;
        }
        if (this.mExpandContainer.findViewById(view.getId()) == null) {
            this.mExpandContainer.removeAllViews();
            updateLayout();
            this.mExpandContainer.addView(view);
            this.mExpandContainer.requestLayout();
            showEditContainer(0, view.getMeasuredHeight() + this.mNavigationBarHeight, animatorListener);
            return;
        }
        if (this.mIsExpandEditContainer) {
            return;
        }
        showEditContainer(0, view.getMeasuredHeight() + this.mNavigationBarHeight, animatorListener);
    }

    protected void showEditContainer(int i, int i2, Animator.AnimatorListener animatorListener) {
        if (this.mExpandContainer == null) {
            return;
        }
        this.mIsExpandEditContainer = true;
        ValueAnimator valueAnimator = this.mContainerCollapseAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mContainerCollapseAnimator.cancel();
        }
        if (this.mContainerExpandAnimator == null) {
            this.mContainerExpandAnimator = createEditContainerAnimator();
        }
        if (this.mContainerExpandAnimator.isRunning()) {
            return;
        }
        this.mContainerExpandAnimator.removeAllListeners();
        if (animatorListener != null) {
            this.mContainerExpandAnimator.addListener(animatorListener);
        }
        this.mContainerExpandAnimator.setIntValues(i, i2);
        this.mContainerExpandAnimator.start();
    }

    protected void hideEditContainer(int i, int i2, Animator.AnimatorListener animatorListener) {
        if (this.mExpandContainer != null && this.mIsExpandEditContainer) {
            this.mIsExpandEditContainer = false;
            ValueAnimator valueAnimator = this.mContainerExpandAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mContainerExpandAnimator.cancel();
            }
            if (this.mContainerCollapseAnimator == null) {
                this.mContainerCollapseAnimator = createEditContainerAnimator();
            }
            if (this.mContainerCollapseAnimator.isRunning()) {
                return;
            }
            this.mContainerCollapseAnimator.removeAllListeners();
            if (animatorListener != null) {
                this.mContainerCollapseAnimator.addListener(animatorListener);
            }
            this.mContainerCollapseAnimator.setIntValues(i, i2);
            this.mContainerCollapseAnimator.start();
        }
    }

    protected void hideEditContainerDirectly() {
        if (this.mExpandContainer != null && this.mIsExpandEditContainer) {
            this.mIsExpandEditContainer = false;
            ValueAnimator valueAnimator = this.mContainerExpandAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mContainerExpandAnimator.cancel();
            }
            ViewGroup.LayoutParams layoutParams = this.mExpandContainer.getLayoutParams();
            layoutParams.height = 0;
            this.mExpandContainer.setLayoutParams(layoutParams);
            this.mToolbarRootView.requestLayout();
            this.mToolbarRootView.setAlpha(0.0f);
            this.mToolbarRootView.setVisibility(8);
            onVisibleChanged(false);
            Logger.INSTANCE.e(TAG, "hideEditContainerDirectly");
        }
    }

    private void collapseExpandContainerImmediately() {
        if (this.mExpandContainer == null) {
            return;
        }
        this.mIsExpandEditContainer = false;
        ValueAnimator valueAnimator = this.mContainerExpandAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mContainerExpandAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.mContainerCollapseAnimator;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            this.mContainerCollapseAnimator.cancel();
        }
        ViewGroup.LayoutParams layoutParams = this.mExpandContainer.getLayoutParams();
        layoutParams.height = 0;
        this.mExpandContainer.setLayoutParams(layoutParams);
        if (this.mToolbarRootView != null) {
            this.mToolbarRootView.requestLayout();
        }
        Logger.INSTANCE.d(TAG, "collapseExpandContainerImmediately");
    }

    private ValueAnimator createEditContainerAnimator() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(300L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.notes.component.noteedit.BaseNoteEditToolbarController.9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                int iIntValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                ViewGroup.LayoutParams layoutParams = BaseNoteEditToolbarController.this.mExpandContainer.getLayoutParams();
                layoutParams.height = iIntValue;
                BaseNoteEditToolbarController.this.mExpandContainer.setLayoutParams(layoutParams);
                Logger.INSTANCE.e(BaseNoteEditToolbarController.TAG, "onAnimationUpdate:" + iIntValue + ",toolbar/requestLayout");
                BaseNoteEditToolbarController.this.mToolbarRootView.requestLayout();
            }
        });
        return valueAnimator;
    }

    protected View createThemeChooser() {
        return UIUtils.inflateView(this.mExpandContainer, R.layout.v12_theme_chooser_panel);
    }

    protected static class ButtonClickListener implements View.OnClickListener {
        private final WeakReference<BaseNoteEditToolbarController> barController;
        private int mSizeLevelState = 0;

        public ButtonClickListener(BaseNoteEditToolbarController baseNoteEditToolbarController) {
            this.barController = new WeakReference<>(baseNoteEditToolbarController);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BaseNoteEditToolbarController baseNoteEditToolbarController = this.barController.get();
            if (baseNoteEditToolbarController == null) {
                return;
            }
            this.mSizeLevelState = baseNoteEditToolbarController.getSizeLevel();
            switch (view.getId()) {
                case R.id.ai /* 2131361915 */:
                case R.id.edit_info /* 2131362465 */:
                    Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "on click toot bar ai/edit_info,isLocked: " + baseNoteEditToolbarController.isLock());
                    if (!baseNoteEditToolbarController.isLock()) {
                        baseNoteEditToolbarController.performExternalClick(view);
                        break;
                    }
                    break;
                case R.id.audio /* 2131362036 */:
                    Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "on click toot bar audio,isLocked: " + baseNoteEditToolbarController.isLock());
                    if (baseNoteEditToolbarController.isLock()) {
                        Logger.INSTANCE.e(getClass().getSimpleName(), "on click toot bar audio,abandon!!");
                    } else {
                        baseNoteEditToolbarController.performExternalClick(view);
                    }
                    break;
                case R.id.bg_highlight /* 2131362065 */:
                    baseNoteEditToolbarController.bgHighLight();
                    EditStat.trackRichEditorControllerClick(EditStat.ELEMENT_HIGHLIGHT);
                    break;
                case R.id.bold /* 2131362073 */:
                    baseNoteEditToolbarController.bold();
                    EditStat.trackRichEditorControllerClick(EditStat.ELEMENT_BOLD);
                    break;
                case R.id.bullet /* 2131362117 */:
                    baseNoteEditToolbarController.bullet();
                    EditStat.trackRichEditorControllerClick("bullet");
                    break;
                case R.id.center /* 2131362149 */:
                    baseNoteEditToolbarController.center();
                    EditStat.trackRichEditorControllerClick(EditStat.ELEMENT_CENTER);
                    break;
                case R.id.check /* 2131362157 */:
                    Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "on click toot bar check,isLocked: " + baseNoteEditToolbarController.isLock());
                    if (!baseNoteEditToolbarController.isLock()) {
                        baseNoteEditToolbarController.toggleCheckBox();
                        baseNoteEditToolbarController.performExternalClick(view);
                        EditStat.trackRichEditorControllerClick("checkbox");
                        break;
                    }
                    break;
                case R.id.edit_image /* 2131362464 */:
                    Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "on click toot bar edit_img,isLocked: " + baseNoteEditToolbarController.isLock());
                    if (!baseNoteEditToolbarController.isLock()) {
                        baseNoteEditToolbarController.toggleInsertDoodle();
                        baseNoteEditToolbarController.performExternalClick(view);
                        EditStat.trackRichEditorControllerClick(EditStat.ELEMENT_DOODLE);
                        break;
                    }
                    break;
                case R.id.first_level /* 2131362538 */:
                    baseNoteEditToolbarController.H1();
                    break;
                case R.id.gallery /* 2131362611 */:
                    Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "on click toot bar gallery,isLocked: " + baseNoteEditToolbarController.isLock());
                    if (!baseNoteEditToolbarController.isLock()) {
                        baseNoteEditToolbarController.toggleInsertImage();
                        baseNoteEditToolbarController.performExternalClick(view);
                        break;
                    }
                    break;
                case R.id.increase_font /* 2131362743 */:
                    int i = this.mSizeLevelState + 1;
                    this.mSizeLevelState = i;
                    if (i > 2) {
                        this.mSizeLevelState = 2;
                    } else {
                        baseNoteEditToolbarController.fontSize(i);
                        EditStat.trackRichEditorControllerClick(EditStat.ELEMENT_FONT_INCREASE);
                    }
                    break;
                case R.id.indentleft /* 2131362744 */:
                    if (!baseNoteEditToolbarController.isIndentLeft()) {
                        Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "on click toot bar indentLeft, but it cannot be used");
                    } else {
                        baseNoteEditToolbarController.indentLeft();
                    }
                    break;
                case R.id.indentright /* 2131362745 */:
                    if (!baseNoteEditToolbarController.isIndentRight()) {
                        Logger.INSTANCE.d(BaseNoteEditToolbarController.TAG, "on click toot bar indentRight, but it cannot be used");
                    } else {
                        baseNoteEditToolbarController.indentRight();
                    }
                    break;
                case R.id.italic /* 2131362763 */:
                    baseNoteEditToolbarController.italic();
                    EditStat.trackRichEditorControllerClick(EditStat.ELEMENT_ITALIC);
                    break;
                case R.id.left /* 2131362802 */:
                    baseNoteEditToolbarController.left();
                    EditStat.trackRichEditorControllerClick("left");
                    break;
                case R.id.listbullet /* 2131362821 */:
                    baseNoteEditToolbarController.listBullet();
                    break;
                case R.id.quote /* 2131363263 */:
                    baseNoteEditToolbarController.quote();
                    break;
                case R.id.reduce_font /* 2131363279 */:
                    int i2 = this.mSizeLevelState - 1;
                    this.mSizeLevelState = i2;
                    if (i2 < 0) {
                        this.mSizeLevelState = 0;
                    } else {
                        baseNoteEditToolbarController.fontSize(i2);
                        EditStat.trackRichEditorControllerClick(EditStat.ELEMENT_FONT_REDUCE);
                    }
                    break;
                case R.id.right /* 2131363305 */:
                    baseNoteEditToolbarController.right();
                    EditStat.trackRichEditorControllerClick(EditStat.ELEMENT_RIGHT);
                    break;
                case R.id.second_level /* 2131363384 */:
                    baseNoteEditToolbarController.H2();
                    break;
                case R.id.split_line /* 2131363510 */:
                    baseNoteEditToolbarController.splitLine();
                    break;
                case R.id.strikethrough /* 2131363540 */:
                    baseNoteEditToolbarController.strikeThrough();
                    break;
                case R.id.thrid_level /* 2131363616 */:
                    baseNoteEditToolbarController.H3();
                    break;
                case R.id.underline /* 2131363734 */:
                    baseNoteEditToolbarController.underline();
                    EditStat.trackRichEditorControllerClick(EditStat.ELEMENT_UNDERLINE);
                    break;
            }
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void removeShowSoftInputTask() {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mShowSoftInputTask);
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean ismIsShowThemeChooser() {
        return this.mIsShowThemeChooser;
    }
}
