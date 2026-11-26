package com.miui.pc.feature.notes;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.miui.common.view.IPopMenu;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.INoteRichEditor;
import com.miui.notes.component.ThemeChooserGroup;
import com.miui.notes.component.noteedit.IBaseNoteEditToolbarController;
import com.miui.notes.component.noteedit.INoteEditToolBar;
import com.miui.notes.editor.ReadOnlyActionListener;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.richeditor.theme.Theme;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class PcActionToolBarController implements IBaseNoteEditToolbarController {
    private ImageView mBtnAudio;
    private ImageView mBtnBgHighLight;
    private ImageView mBtnBold;
    private ImageView mBtnBullet;
    private ImageView mBtnCenter;
    private ImageView mBtnCheck;
    private ImageView mBtnEditTheme;
    private ImageView mBtnGallery;
    private ImageView mBtnItalic;
    private View mBtnRedo;
    private ImageView mBtnRichText;
    private ImageView mBtnRight;
    private View mBtnSize;
    private ImageView mBtnUnderline;
    private View mBtnUndo;
    private INoteRichEditor mEditor;
    private boolean mIsBgHighLight;
    private boolean mIsBold;
    private boolean mIsBullet;
    private boolean mIsCenter;
    private boolean mIsCheckbox;
    private boolean mIsItalic;
    private boolean mIsLeft;
    private boolean mIsRight;
    private boolean mIsUnderline;
    private RelativeLayout mRootView;
    private int mSizeLevel;
    private PcTextSizeChoiceMenu mTextSizeMenu;
    private boolean mIsLock = false;
    private View.OnClickListener mOutActionListener = null;
    private View.OnClickListener mActionListener = new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcActionToolBarController.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            PcActionToolBarController pcActionToolBarController = PcActionToolBarController.this;
            pcActionToolBarController.mSizeState = pcActionToolBarController.getSizeLevel();
            if (PcActionToolBarController.this.mOutActionListener != null) {
                PcActionToolBarController.this.mOutActionListener.onClick(PcActionToolBarController.this.mRootView);
            }
            switch (view.getId()) {
                case R.id.bg_highlight /* 2131362065 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.bgHighLight();
                        if (PcActionToolBarController.this.mOutActionListener != null) {
                            PcActionToolBarController.this.mOutActionListener.onClick(view);
                        }
                        break;
                    }
                    break;
                case R.id.bold /* 2131362073 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.bold();
                        if (PcActionToolBarController.this.mOutActionListener != null) {
                            PcActionToolBarController.this.mOutActionListener.onClick(view);
                        }
                        break;
                    }
                    break;
                case R.id.bullet /* 2131362117 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.bullet();
                        if (PcActionToolBarController.this.mOutActionListener != null) {
                            PcActionToolBarController.this.mOutActionListener.onClick(view);
                        }
                        break;
                    }
                    break;
                case R.id.center /* 2131362149 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.center();
                        if (PcActionToolBarController.this.mOutActionListener != null) {
                            PcActionToolBarController.this.mOutActionListener.onClick(view);
                        }
                        break;
                    }
                    break;
                case R.id.check /* 2131362157 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.checkbox();
                        if (PcActionToolBarController.this.mOutActionListener != null) {
                            PcActionToolBarController.this.mOutActionListener.onClick(view);
                        }
                        break;
                    }
                    break;
                case R.id.gallery /* 2131362611 */:
                    if (PcActionToolBarController.this.mOutActionListener != null) {
                        PcActionToolBarController.this.mOutActionListener.onClick(view);
                    }
                    break;
                case R.id.italic /* 2131362763 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.italic();
                        if (PcActionToolBarController.this.mOutActionListener != null) {
                            PcActionToolBarController.this.mOutActionListener.onClick(view);
                        }
                        break;
                    }
                    break;
                case R.id.redo /* 2131363278 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.redo();
                        if (PcActionToolBarController.this.mOutActionListener != null) {
                            PcActionToolBarController.this.mOutActionListener.onClick(view);
                        }
                        break;
                    }
                    break;
                case R.id.right /* 2131363305 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.right();
                        if (PcActionToolBarController.this.mOutActionListener != null) {
                            PcActionToolBarController.this.mOutActionListener.onClick(view);
                        }
                        break;
                    }
                    break;
                case R.id.size_group /* 2131363467 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.showTextSizeChoiceMenu(view);
                        break;
                    }
                    break;
                case R.id.underline /* 2131363734 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.underline();
                        if (PcActionToolBarController.this.mOutActionListener != null) {
                            PcActionToolBarController.this.mOutActionListener.onClick(view);
                        }
                        break;
                    }
                    break;
                case R.id.undo /* 2131363735 */:
                    if (!PcActionToolBarController.this.isLock()) {
                        PcActionToolBarController.this.undo();
                        if (PcActionToolBarController.this.mOutActionListener != null) {
                            PcActionToolBarController.this.mOutActionListener.onClick(view);
                        }
                        break;
                    }
                    break;
            }
        }
    };
    private int mTextSizeMenuPos = 0;
    private int mSizeState = 0;
    private TransitionListener mHideListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.PcActionToolBarController.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            PcActionToolBarController.this.mRootView.setVisibility(8);
        }
    };

    @Override // com.miui.richeditor.IRichTextStyleController
    public void H1() {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void H2() {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void H3() {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void canIndent(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void canUndent(boolean z) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void checkAndCollapseThemePanel() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void closeAudioRecordPanel() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void collapsePanel(Animator.AnimatorListener animatorListener) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void collapsePanel(Animator.AnimatorListener animatorListener, boolean z) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void dismissPopupWindow() {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void fontSize() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public View.OnClickListener getViewClickListener() {
        return null;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void hideToolbarAndShowInput() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void hideWithoutAnim() {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void indentLeft() {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void indentRight() {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void isAiCanUse(boolean z) {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isCanSplitLine() {
        return false;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isH1() {
        return false;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isH2() {
        return false;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isH3() {
        return false;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isIndentLeft() {
        return false;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isIndentRight() {
        return false;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isListBullet() {
        return false;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature, com.miui.richeditor.TextStyleStateListener
    public void isNeedLock(boolean z) {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isQuote() {
        return false;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isStrikeThrough() {
        return false;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean isTextStylePanelShowed() {
        return false;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean ismIsShowThemeChooser() {
        return false;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean ismToolbarIsShow() {
        return false;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void left() {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean listBullet() {
        return false;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public boolean onBackPressed() {
        return false;
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onCheckbox(boolean z) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onConfigChanged() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onDestroy() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditContentMode() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditContentMode(boolean z, boolean z2, boolean z3) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onEditTitleMode() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onFirstEditContentMode(boolean z, boolean z2, boolean z3) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onFocusAble(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onGallery(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onH1(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onH2(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onH3(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onLeft(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onListBullet(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onQuote(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onSpliteLine(boolean z) {
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onStrikeThrough(boolean z) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onViewMode() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onViewMode(boolean z) {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void quote() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void removeShowSoftInputTask() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void resetEditBarStatus() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void setExternalClickListener(View.OnClickListener onClickListener) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setMarginStart() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setNeedDelayShowInput(boolean z) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setOnThemeChosenListener(ThemeChooserGroup.OnThemeChosenListener onThemeChosenListener) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setOnVisibleChangedListener(INoteEditToolBar.OnVisibleChangedListener onVisibleChangedListener) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void setReadOnlyActionListener(ReadOnlyActionListener readOnlyActionListener) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setTextStylePanelVisible(boolean z, boolean z2) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void setWorkingNote(WorkingNote workingNote) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public boolean shouldHideSoftInput() {
        return false;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void show(boolean z, boolean z2, boolean z3) {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void splitLine() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void startAudioInput() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void stopAudioInput() {
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void strikeThrough() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleAudioRecordPanel() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleCheckBox() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertDoodle() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleInsertImage() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void toggleThemeChooserFromHead() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditToolBar
    public void updateAudioVolume(float f) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateLayout() {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateNavigationHeight(int i) {
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateTheme(Theme theme, NoteThemeResCache noteThemeResCache) {
    }

    public PcActionToolBarController(View view, INoteRichEditor iNoteRichEditor) {
        this.mRootView = (RelativeLayout) view;
        initView(view);
        this.mEditor = iNoteRichEditor;
        iNoteRichEditor.setTextStyleStateListener(this);
    }

    public View getView() {
        return this.mRootView;
    }

    private void initView(View view) {
        View viewFindViewById = view.findViewById(R.id.undo);
        this.mBtnUndo = viewFindViewById;
        viewFindViewById.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnUndo);
        View viewFindViewById2 = view.findViewById(R.id.redo);
        this.mBtnRedo = viewFindViewById2;
        viewFindViewById2.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnRedo);
        ImageView imageView = (ImageView) view.findViewById(R.id.bold);
        this.mBtnBold = imageView;
        imageView.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnBold);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.italic);
        this.mBtnItalic = imageView2;
        imageView2.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnItalic);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.underline);
        this.mBtnUnderline = imageView3;
        imageView3.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnUnderline);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.bg_highlight);
        this.mBtnBgHighLight = imageView4;
        imageView4.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnBgHighLight);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.check);
        this.mBtnCheck = imageView5;
        imageView5.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnCheck);
        ImageView imageView6 = (ImageView) view.findViewById(R.id.bullet);
        this.mBtnBullet = imageView6;
        imageView6.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnBullet);
        ImageView imageView7 = (ImageView) view.findViewById(R.id.center);
        this.mBtnCenter = imageView7;
        imageView7.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnCenter);
        ImageView imageView8 = (ImageView) view.findViewById(R.id.right);
        this.mBtnRight = imageView8;
        imageView8.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnRight);
        ImageView imageView9 = (ImageView) view.findViewById(R.id.gallery);
        this.mBtnGallery = imageView9;
        imageView9.setOnClickListener(this.mActionListener);
        setViewFolmeTouch(this.mBtnGallery);
        View viewFindViewById3 = view.findViewById(R.id.size_group);
        this.mBtnSize = viewFindViewById3;
        viewFindViewById3.setOnClickListener(this.mActionListener);
        initTextSizeChoiceMenu();
    }

    private void setViewFolmeTouch(View view) {
        Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig[0]);
    }

    public void setActionListener(View.OnClickListener onClickListener) {
        this.mOutActionListener = onClickListener;
    }

    public void undo() {
        this.mEditor.undo();
    }

    public void redo() {
        this.mEditor.redo();
    }

    public void updateUndoState(boolean z, boolean z2) {
        this.mBtnUndo.setEnabled(z);
        this.mBtnRedo.setEnabled(z2);
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public Context getContext() {
        return this.mRootView.getContext();
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void show(boolean z) {
        this.mRootView.setVisibility(0);
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void hide(boolean z) {
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mHideListener));
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void lock() {
        this.mIsLock = true;
    }

    @Override // com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void unlock() {
        this.mIsLock = false;
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void canInsert(boolean z) {
        ImageView imageView = this.mBtnGallery;
        if (imageView != null) {
            imageView.setAlpha(z ? 1.0f : 0.5f);
            this.mBtnGallery.setClickable(z);
        }
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void bold() {
        bold(!isBold());
        this.mEditor.setFontBold();
    }

    public void bold(boolean z) {
        this.mIsBold = z;
        updateState(R.id.bold);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void italic() {
        italic(!isItalic());
        this.mEditor.setFontItalic();
    }

    public void italic(boolean z) {
        this.mIsItalic = z;
        updateState(R.id.italic);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void fontSize(int i) {
        this.mSizeLevel = i;
        updateTextSizeView(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSize(int i) {
        fontSize(i);
        this.mEditor.setFontSizeLevel(i);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void underline() {
        underline(!isUnderline());
        this.mEditor.setUnderline();
    }

    public void underline(boolean z) {
        this.mIsUnderline = z;
        updateState(R.id.underline);
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void center() {
        if (this.mEditor.canAlignCenter()) {
            onCenter(!isCenter());
            this.mEditor.setAlignCenter();
        }
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void right() {
        if (this.mEditor.canAlignRight()) {
            onRight(!isRight());
            this.mEditor.setAlignRight();
        }
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void bgHighLight() {
        onBgHighLight(!isBgHighLight());
        this.mEditor.setBgHighLight();
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean checkbox() {
        if (!this.mEditor.canAddNewElement(40)) {
            Toast.makeText(getView().getContext(), R.string.warn_cannot_add_new_checkbox, 0).show();
            return false;
        }
        updateState(R.id.check);
        this.mEditor.setCheckbox();
        return true;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean bullet() {
        if (!this.mEditor.canAddNewElement(10)) {
            Toast.makeText(getView().getContext(), R.string.warn_cannot_add_new_bullet, 0).show();
            return false;
        }
        this.mEditor.setEditorBullet();
        return true;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public int getSizeLevel() {
        return this.mSizeLevel;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isBold() {
        return this.mIsBold;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isItalic() {
        return this.mIsItalic;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isUnderline() {
        return this.mIsUnderline;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isLeft() {
        return this.mIsLeft;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isCenter() {
        return this.mIsCenter;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isRight() {
        return this.mIsRight;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isBgHighLight() {
        return this.mIsBgHighLight;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isCheckbox() {
        return this.mIsCheckbox;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public boolean isBullet() {
        return this.mIsBullet;
    }

    @Override // com.miui.richeditor.IRichTextStyleController
    public void resetRichTextStatus() {
        this.mIsBold = false;
        this.mIsItalic = false;
        this.mIsLeft = true;
        this.mIsCenter = false;
        this.mSizeLevel = 0;
        updateTextSizeView(0);
        this.mIsUnderline = false;
        this.mIsBgHighLight = false;
        this.mIsBullet = false;
        this.mIsCheckbox = false;
        this.mIsRight = false;
        this.mBtnBold.setSelected(false);
        this.mBtnItalic.setSelected(false);
        this.mBtnCenter.setSelected(false);
        this.mBtnUnderline.setSelected(false);
        this.mBtnRight.setSelected(false);
        this.mBtnBullet.setSelected(false);
        this.mBtnBgHighLight.setSelected(false);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onBold(boolean z) {
        bold(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onItalic(boolean z) {
        italic(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onCenter(boolean z) {
        this.mIsCenter = z;
        if (z) {
            onRight(false);
            onLeft(false);
        }
        updateState(R.id.center);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onRight(boolean z) {
        this.mIsRight = z;
        if (z) {
            onCenter(false);
            onLeft(false);
        }
        updateState(R.id.right);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onUnderline(boolean z) {
        underline(z);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onBgHighLight(boolean z) {
        this.mIsBgHighLight = z;
        updateState(R.id.bg_highlight);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onBullet(boolean z) {
        this.mIsBullet = z;
        updateState(R.id.bullet);
    }

    @Override // com.miui.richeditor.TextStyleStateListener
    public void onFontSize(int i) {
        fontSize(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLock() {
        return this.mIsLock;
    }

    private void initTextSizeChoiceMenu() {
        PcTextSizeChoiceMenu pcTextSizeChoiceMenu = new PcTextSizeChoiceMenu(this.mRootView.getContext());
        this.mTextSizeMenu = pcTextSizeChoiceMenu;
        pcTextSizeChoiceMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pc.feature.notes.PcActionToolBarController.3
            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onDismiss() {
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onShow() {
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onItemSelected(View view, int i) {
                if (PcActionToolBarController.this.mTextSizeMenuPos == i) {
                    return;
                }
                PcActionToolBarController.this.mTextSizeMenuPos = i;
                if (i == 0) {
                    PcActionToolBarController.this.setSize(0);
                } else if (i == 1) {
                    PcActionToolBarController.this.setSize(2);
                } else if (i == 2) {
                    PcActionToolBarController.this.setSize(1);
                }
                if (PcActionToolBarController.this.mOutActionListener != null) {
                    PcActionToolBarController.this.mOutActionListener.onClick(PcActionToolBarController.this.mBtnSize);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTextSizeChoiceMenu(View view) {
        this.mTextSizeMenu.setSelectedItem(this.mTextSizeMenuPos);
        this.mTextSizeMenu.show(view, null);
    }

    private void updateTextSizeView(int i) {
        TextView textView = (TextView) this.mBtnSize.findViewById(R.id.text_size_content);
        if (i == 0) {
            textView.setText(textView.getResources().getString(R.string.pc_note_edit_text_format_body));
            this.mTextSizeMenuPos = 0;
        } else if (i == 1) {
            textView.setText(textView.getResources().getString(R.string.pc_note_edit_text_format_title_level2));
            this.mTextSizeMenuPos = 2;
        } else {
            if (i != 2) {
                return;
            }
            textView.setText(textView.getResources().getString(R.string.pc_note_edit_text_format_title_level1));
            this.mTextSizeMenuPos = 1;
        }
    }

    public void updateState(int i) {
        this.mRootView.findViewById(i).setSelected(getState(i));
    }

    private boolean getState(int i) {
        switch (i) {
            case R.id.bg_highlight /* 2131362065 */:
                return isBgHighLight();
            case R.id.bold /* 2131362073 */:
                return isBold();
            case R.id.bullet /* 2131362117 */:
                return isBullet();
            case R.id.center /* 2131362149 */:
                return isCenter();
            case R.id.checkbox /* 2131362159 */:
                return isCenter();
            case R.id.italic /* 2131362763 */:
                return isItalic();
            case R.id.left /* 2131362802 */:
                return isLeft();
            case R.id.right /* 2131363305 */:
                return isRight();
            case R.id.underline /* 2131363734 */:
                return isUnderline();
            default:
                return false;
        }
    }

    private void toggleButtonOnce(ImageView imageView) {
        ((AnimatedVectorDrawable) imageView.getDrawable()).start();
    }
}
