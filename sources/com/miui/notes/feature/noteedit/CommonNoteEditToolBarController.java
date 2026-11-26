package com.miui.notes.feature.noteedit;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.miui.common.tool.LiteUtils;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.INoteRichEditor;
import com.miui.notes.component.ThemeChooserGroup;
import com.miui.notes.component.noteedit.BigScreenNoteEditToolbarController;
import com.miui.notes.component.noteedit.IBaseNoteEditToolbarController;
import com.miui.notes.component.noteedit.INoteEditToolBar;
import com.miui.notes.component.noteedit.SmallScreenNoteEditToolbarController;
import com.miui.notes.editor.ReadOnlyActionListener;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class CommonNoteEditToolBarController implements IBaseNoteEditToolbarController {
    private IBaseNoteEditToolbarController mControllerImpl;
    private boolean mIsItalic;

    @Override // com.miui.richeditor.TextStyleStateListener
    public void canIndent(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void canInsert(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void canUndent(boolean z) {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public int getSizeLevel() {
        return -1;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isBgHighLight() {
        return false;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isUnderline() {
        return false;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditContentMode() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onFirstEditContentMode(boolean z, boolean z2, boolean z3) {
    }

    public CommonNoteEditToolBarController(Activity activity, View view, INoteRichEditor iNoteRichEditor) {
        if (view.findViewById(R.id.small_placeholder) != null && !LiteUtils.isSuperLite()) {
            this.mControllerImpl = new SmallScreenNoteEditToolbarController(activity, view, iNoteRichEditor);
        } else {
            this.mControllerImpl = new BigScreenNoteEditToolbarController(activity, view, iNoteRichEditor);
        }
    }

    @Override // com.miui.notes.component.noteedit.IBaseNoteEditToolbarController
    public void showGalleryPicker() {
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mControllerImpl;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.showGalleryPicker();
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onFocusAble(boolean z) {
        this.mControllerImpl.onFocusAble(z);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public Context getContext() {
        return this.mControllerImpl.getContext();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public View.OnClickListener getViewClickListener() {
        return this.mControllerImpl.getViewClickListener();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditTitleMode() {
        this.mControllerImpl.onEditTitleMode();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void dismissPopupWindow() {
        this.mControllerImpl.dismissPopupWindow();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditContentMode(boolean z, boolean z2, boolean z3) {
        this.mControllerImpl.onEditContentMode(z, z2, z3);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onViewMode() {
        this.mControllerImpl.onViewMode();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateTheme(Theme theme, NoteThemeResCache noteThemeResCache) {
        this.mControllerImpl.updateTheme(theme, noteThemeResCache);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onViewMode(boolean z) {
        this.mControllerImpl.onViewMode(z);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setNeedDelayShowInput(boolean z) {
        this.mControllerImpl.setNeedDelayShowInput(z);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void show(boolean z, boolean z2, boolean z3) {
        this.mControllerImpl.show(z, z2, z3);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void hideToolbarAndShowInput() {
        this.mControllerImpl.hideToolbarAndShowInput();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void hide(boolean z) {
        this.mControllerImpl.hide(z);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void hideWithoutAnim() {
        this.mControllerImpl.hideWithoutAnim();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void removeShowSoftInputTask() {
        this.mControllerImpl.removeShowSoftInputTask();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void lock() {
        this.mControllerImpl.lock();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void unlock() {
        this.mControllerImpl.unlock();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void setExternalClickListener(View.OnClickListener onClickListener) {
        this.mControllerImpl.setExternalClickListener(onClickListener);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void setReadOnlyActionListener(ReadOnlyActionListener readOnlyActionListener) {
        this.mControllerImpl.setReadOnlyActionListener(readOnlyActionListener);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleCheckBox() {
        this.mControllerImpl.toggleCheckBox();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertImage() {
        this.mControllerImpl.toggleInsertImage();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertDoodle() {
        this.mControllerImpl.toggleInsertDoodle();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void startAudioInput() {
        this.mControllerImpl.startAudioInput();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void stopAudioInput() {
        this.mControllerImpl.stopAudioInput();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleAudioRecordPanel() {
        this.mControllerImpl.toggleAudioRecordPanel();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void closeAudioRecordPanel() {
        this.mControllerImpl.closeAudioRecordPanel();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void updateAudioVolume(float f) {
        this.mControllerImpl.updateAudioVolume(f);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setTextStylePanelVisible(boolean z, boolean z2) {
        this.mControllerImpl.setTextStylePanelVisible(z, z2);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void collapsePanel(Animator.AnimatorListener animatorListener, boolean z) {
        this.mControllerImpl.collapsePanel(animatorListener, z);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateNavigationHeight(int i) {
        this.mControllerImpl.updateNavigationHeight(i);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateLayout() {
        this.mControllerImpl.updateLayout();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public boolean onBackPressed() {
        return this.mControllerImpl.onBackPressed();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onDestroy() {
        this.mControllerImpl.onDestroy();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setWorkingNote(WorkingNote workingNote) {
        this.mControllerImpl.setWorkingNote(workingNote);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onConfigChanged() {
        this.mControllerImpl.onConfigChanged();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean isTextStylePanelShowed() {
        return this.mControllerImpl.isTextStylePanelShowed();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean ismToolbarIsShow() {
        return this.mControllerImpl.ismToolbarIsShow();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setOnThemeChosenListener(ThemeChooserGroup.OnThemeChosenListener onThemeChosenListener) {
        this.mControllerImpl.setOnThemeChosenListener(onThemeChosenListener);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean shouldHideSoftInput() {
        return this.mControllerImpl.shouldHideSoftInput();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void checkAndCollapseThemePanel() {
        this.mControllerImpl.checkAndCollapseThemePanel();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void collapsePanel(Animator.AnimatorListener animatorListener) {
        this.mControllerImpl.collapsePanel(animatorListener);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleThemeChooserFromHead() {
        this.mControllerImpl.toggleThemeChooserFromHead();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void show(boolean z) {
        this.mControllerImpl.show(z);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void resetEditBarStatus() {
        this.mControllerImpl.resetEditBarStatus();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setMarginStart() {
        this.mControllerImpl.setMarginStart();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setOnVisibleChangedListener(INoteEditToolBar.OnVisibleChangedListener onVisibleChangedListener) {
        this.mControllerImpl.setOnVisibleChangedListener(onVisibleChangedListener);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onH1(boolean z) {
        this.mControllerImpl.onH1(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onH2(boolean z) {
        this.mControllerImpl.onH2(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onH3(boolean z) {
        this.mControllerImpl.onH3(z);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature, com.miui.richeditor.TextStyleStateListener
    public void isNeedLock(boolean z) {
        this.mControllerImpl.isNeedLock(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void isAiCanUse(boolean z) {
        this.mControllerImpl.isAiCanUse(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onBold(boolean z) {
        this.mControllerImpl.onBold(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onItalic(boolean z) {
        this.mIsItalic = z;
        this.mControllerImpl.onItalic(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onLeft(boolean z) {
        this.mControllerImpl.onLeft(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onListBullet(boolean z) {
        this.mControllerImpl.onListBullet(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onStrikeThrough(boolean z) {
        this.mControllerImpl.onStrikeThrough(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onSpliteLine(boolean z) {
        this.mControllerImpl.onSpliteLine(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onGallery(boolean z) {
        this.mControllerImpl.onGallery(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onQuote(boolean z) {
        this.mControllerImpl.onQuote(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onCenter(boolean z) {
        this.mControllerImpl.onCenter(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onRight(boolean z) {
        this.mControllerImpl.onRight(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onCheckbox(boolean z) {
        this.mControllerImpl.onCheckbox(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onFontSize(int i) {
        this.mControllerImpl.onFontSize(i);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onUnderline(boolean z) {
        this.mControllerImpl.onUnderline(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onBgHighLight(boolean z) {
        this.mControllerImpl.onBgHighLight(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onBullet(boolean z) {
        this.mControllerImpl.onBullet(z);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isH1() {
        return this.mControllerImpl.isH1();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isH2() {
        return this.mControllerImpl.isH2();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isH3() {
        return this.mControllerImpl.isH3();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isStrikeThrough() {
        return this.mControllerImpl.isStrikeThrough();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isListBullet() {
        return this.mControllerImpl.isListBullet();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isCanSplitLine() {
        return this.mControllerImpl.isCanSplitLine();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isQuote() {
        return this.mControllerImpl.isQuote();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isIndentRight() {
        return this.mControllerImpl.isIndentRight();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isIndentLeft() {
        return this.mControllerImpl.isLeft();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isBold() {
        return this.mControllerImpl.isBold();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isItalic() {
        return this.mIsItalic;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isLeft() {
        return this.mControllerImpl.isLeft();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isCenter() {
        return this.mControllerImpl.isCenter();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isBullet() {
        return this.mControllerImpl.isBullet();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isCheckbox() {
        return this.mControllerImpl.isCheckbox();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isRight() {
        return this.mControllerImpl.isRight();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void H1() {
        this.mControllerImpl.H1();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void H2() {
        this.mControllerImpl.H2();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void H3() {
        this.mControllerImpl.H3();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void strikeThrough() {
        this.mControllerImpl.strikeThrough();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void quote() {
        this.mControllerImpl.quote();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void splitLine() {
        this.mControllerImpl.splitLine();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void indentRight() {
        this.mControllerImpl.isIndentLeft();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void indentLeft() {
        this.mControllerImpl.isIndentLeft();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void bold() {
        this.mControllerImpl.bold();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void italic() {
        this.mControllerImpl.italic();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void left() {
        this.mControllerImpl.left();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void center() {
        this.mControllerImpl.center();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void right() {
        this.mControllerImpl.right();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void fontSize() {
        this.mControllerImpl.fontSize();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void fontSize(int i) {
        this.mControllerImpl.fontSize(i);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void underline() {
        this.mControllerImpl.underline();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void bgHighLight() {
        this.mControllerImpl.bgHighLight();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean listBullet() {
        return this.mControllerImpl.listBullet();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean bullet() {
        return this.mControllerImpl.bullet();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean checkbox() {
        return this.mControllerImpl.checkbox();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void resetRichTextStatus() {
        this.mControllerImpl.resetRichTextStatus();
        this.mIsItalic = false;
    }

    public static class RichTextChooser extends RelativeLayout {
        static final int[] ICON_IDS = {R.id.bold, R.id.italic, R.id.underline, R.id.bg_highlight, R.id.bullet, R.id.center, R.id.right};
        static final int[] ICON_SIZE_ID = {R.id.increase_font, R.id.reduce_font};

        public RichTextChooser(Context context) {
            super(context);
        }

        public RichTextChooser(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public RichTextChooser(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        public RichTextChooser(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
        }
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean ismIsShowThemeChooser() {
        return this.mControllerImpl.ismIsShowThemeChooser();
    }
}
