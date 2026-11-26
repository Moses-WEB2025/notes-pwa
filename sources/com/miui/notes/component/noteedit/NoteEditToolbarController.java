package com.miui.notes.component.noteedit;

import android.animation.Animator;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.hybirdeditor.WebRichEditor;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.INoteRichEditor;
import com.miui.notes.ui.view.NoteEditAudioRecordGroup;
import com.miui.richeditor.RichEditTextView;

/* JADX INFO: loaded from: classes4.dex */
public abstract class NoteEditToolbarController extends BaseNoteEditToolbarController {
    protected static String TAG = "NoteEditToolbarController";
    public NoteEditAudioRecordGroup mAudioRecordGroup;
    protected INoteRichEditor mEditor;
    protected boolean mIsNativeEditor;

    public NoteEditToolbarController(Activity activity, View view, INoteRichEditor iNoteRichEditor) {
        super(activity, view);
        this.mEditor = iNoteRichEditor;
        iNoteRichEditor.setTextStyleStateListener(this);
        this.mIsNativeEditor = !(this.mEditor instanceof WebRichEditor);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController
    protected void requestEditorFocusAndShowSoftInput() {
        if (this.mIsShowAudioRecord) {
            Utils.hideSoftInput(this.mEditor.getView());
        } else {
            this.mEditor.requestViewFocus();
            Utils.showSoftInput(this.mEditor.getView(), true);
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void collapsePanel(Animator.AnimatorListener animatorListener, boolean z) {
        if (this.mIsShowTextStylePanel) {
            this.mEditor.setViewShowSoftInputOnFocus(true);
        }
        super.collapsePanel(animatorListener, z);
        hideEditContainer(this.mExpandContainer.getHeight(), 0, animatorListener);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertImage() {
        if (this.mEditor.isCursorVisible()) {
            return;
        }
        this.mEditor.setSelectionToEnd();
        this.mEditor.setCursorVisible(true);
        this.mEditor.requestViewFocus(130);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void startAudioInput() {
        this.mEditor.startAudioInput();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void stopAudioInput() {
        this.mEditor.stopAudioInput();
        if (this.mIsShowAudioRecord) {
            toggleAudioRecordPanel();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleThemeChooserFromHead() {
        if (!this.mIsShowThemeChooser && this.mIsShowTextStylePanel) {
            this.mEditor.setViewShowSoftInputOnFocus(true);
        }
        super.toggleThemeChooserFromHead();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void closeAudioRecordPanel() {
        if (RomUtils.isPadMode() && ((LiteUtils.isLiteOrMiddle() || RomUtils.isInternationalBuild()) && getContext() != null)) {
            ViewGroup viewGroup = (ViewGroup) getContext().getWindow().getDecorView().findViewById(R.id.secondary_content_decor);
            viewGroup.setPadding(viewGroup.getPaddingLeft(), viewGroup.getPaddingTop(), viewGroup.getPaddingRight(), 0);
        }
        if (this.mIsShowAudioRecord) {
            this.mIsShowAudioRecord = false;
            hideEditContainer(this.mExpandContainer.getHeight(), 0, new Animator.AnimatorListener() { // from class: com.miui.notes.component.noteedit.NoteEditToolbarController.1
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
                    NoteEditToolbarController.this.mAudioRecordGroup.stopWaveAnim();
                    NoteEditToolbarController.this.mAudioRecordGroup.reset();
                }
            });
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void H1() {
        if (isH1() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.H1();
            this.mEditor.setH1();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void H2() {
        if (isH2() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.H2();
            this.mEditor.setH2();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void H3() {
        if (isH3() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.H3();
            this.mEditor.setH3();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void bold() {
        if (isBold() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.bold();
            if (isBold()) {
                Object obj = this.mEditor;
                if (obj instanceof RichEditTextView) {
                    ((RichEditTextView) obj).setStates(1, true);
                }
            }
            this.mEditor.setFontBold();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void italic() {
        if (isItalic() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.italic();
            if (isItalic()) {
                Object obj = this.mEditor;
                if (obj instanceof RichEditTextView) {
                    ((RichEditTextView) obj).setStates(2, true);
                }
            }
            this.mEditor.setFontItalic();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void fontSize() {
        super.fontSize();
        this.mEditor.setFontSizeLevel(this.mSizeLevelNow);
        this.mEditor.notifySaveData();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void fontSize(int i) {
        if (i <= 0 || this.mSizeLevelNow != 0 || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.fontSize(i);
            if (this.mIsNativeEditor && !UIUtils.isAboveAndroidS()) {
                this.mEditor.clearFocus();
            }
            this.mEditor.setFontSizeLevel(i);
            this.mEditor.notifySaveData();
            if (this.mIsNativeEditor) {
                this.mEditor.requestViewFocus();
            }
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void underline() {
        if (isUnderline() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.underline();
            if (isUnderline()) {
                Object obj = this.mEditor;
                if (obj instanceof RichEditTextView) {
                    ((RichEditTextView) obj).setStates(5, true);
                }
            }
            this.mEditor.setUnderline();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void quote() {
        if (isQuote() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.quote();
            this.mEditor.setQuote();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void indentRight() {
        if (canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.indentRight();
            this.mEditor.setIndent();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void indentLeft() {
        if (canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.indentLeft();
            this.mEditor.setUndent();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void splitLine() {
        if (canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.splitLine();
            this.mEditor.setHr();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void strikeThrough() {
        if (isStrikeThrough() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.strikeThrough();
            this.mEditor.setDeline();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.TextStyleStateListener
    public void onGallery(boolean z) {
        super.onGallery(z);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public void bgHighLight() {
        if (isBgHighLight() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.bgHighLight();
            if (isBgHighLight()) {
                Object obj = this.mEditor;
                if (obj instanceof RichEditTextView) {
                    ((RichEditTextView) obj).setStates(11, true);
                }
            }
            this.mEditor.setBgHighLight();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected boolean canAlignNormal() {
        return this.mEditor.canAlignNormal();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected boolean canAlignCenter() {
        return this.mEditor.canAlignCenter();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected boolean canAlignRight() {
        return this.mEditor.canAlignRight();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected boolean canAddNewElement(int i, int i2) {
        if (this.mWorkingNote == null) {
            return false;
        }
        if (this.mEditor.canAddNewElement(i)) {
            return true;
        }
        Toast.makeText(getContext(), i2, 0).show();
        return false;
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected void doSetLeft() {
        if (isLeft() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.doSetLeft();
            this.mEditor.setAlignNormal();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected void doSetCenter() {
        if (isCenter() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.doSetCenter();
            this.mEditor.setAlignCenter();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected void doSetRight() {
        if (isRight() || canAddNewElement(1, R.string.warn_cannot_add_new_style)) {
            super.doSetRight();
            this.mEditor.setAlignRight();
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected void doSetCheckbox() {
        this.mEditor.setCheckbox();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected void doSetBullet() {
        this.mEditor.setEditorBullet();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected void doSetListBullet() {
        this.mEditor.setOrderList();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onDestroy() {
        super.onDestroy();
        Logger.INSTANCE.d(getClass().getSimpleName(), "onDestroy");
        this.mEditor.setTextStyleStateListener(null);
        setOnVisibleChangedListener(null);
    }
}
