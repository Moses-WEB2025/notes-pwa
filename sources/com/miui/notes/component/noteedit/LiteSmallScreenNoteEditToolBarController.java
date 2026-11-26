package com.miui.notes.component.noteedit;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.miui.common.tool.BlurHelper;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.INoteRichEditor;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.ui.view.RichTextCheckedTextView;
import com.miui.richeditor.theme.Theme;
import miuix.internal.util.ViewUtils;

/* JADX INFO: loaded from: classes4.dex */
public class LiteSmallScreenNoteEditToolBarController extends NoteEditToolbarController {
    public static final boolean IS_SUPER_LITE = LiteUtils.isSuperLite();
    private ImageView mAudioButton;
    private RichTextCheckedTextView mBgHighLightView;
    private RichTextCheckedTextView mBoldView;
    private int mBottomHeight;
    private RichTextCheckedTextView mBulletView;
    private RichTextCheckedTextView mCenterView;
    private RichTextCheckedTextView mCheckButton;
    private ImageView mDoodleButton;
    private ImageView mGalleryButton;
    private RichTextCheckedTextView mIncreaseFontView;
    private RichTextCheckedTextView mItalicView;
    private RichTextCheckedTextView mReduceFontView;
    private RichTextCheckedTextView mRightView;
    private RichTextCheckedTextView mUnderlineView;

    public LiteSmallScreenNoteEditToolBarController(Activity activity, View view, INoteRichEditor iNoteRichEditor) {
        super(activity, view, iNoteRichEditor);
        initToolbar(view);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController
    protected String getToolbarType() {
        return "HybridSmallScreenLite";
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController
    protected void createViewClickListener() {
        this.mViewClickListener = new BaseNoteEditToolbarController.ButtonClickListener(this);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController
    protected void initToolbar(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.gallery);
        this.mGalleryButton = imageView;
        imageView.setOnClickListener(getViewClickListener());
        ImageView imageView2 = (ImageView) view.findViewById(R.id.edit_image);
        this.mDoodleButton = imageView2;
        imageView2.setOnClickListener(getViewClickListener());
        RichTextCheckedTextView richTextCheckedTextView = (RichTextCheckedTextView) view.findViewById(R.id.check);
        this.mCheckButton = richTextCheckedTextView;
        richTextCheckedTextView.setOnClickListener(getViewClickListener());
        RichTextCheckedTextView richTextCheckedTextView2 = (RichTextCheckedTextView) view.findViewById(R.id.bold);
        this.mBoldView = richTextCheckedTextView2;
        richTextCheckedTextView2.setOnClickListener(getViewClickListener());
        RichTextCheckedTextView richTextCheckedTextView3 = (RichTextCheckedTextView) view.findViewById(R.id.italic);
        this.mItalicView = richTextCheckedTextView3;
        richTextCheckedTextView3.setOnClickListener(getViewClickListener());
        RichTextCheckedTextView richTextCheckedTextView4 = (RichTextCheckedTextView) view.findViewById(R.id.underline);
        this.mUnderlineView = richTextCheckedTextView4;
        richTextCheckedTextView4.setOnClickListener(getViewClickListener());
        RichTextCheckedTextView richTextCheckedTextView5 = (RichTextCheckedTextView) view.findViewById(R.id.increase_font);
        this.mIncreaseFontView = richTextCheckedTextView5;
        richTextCheckedTextView5.setOnClickListener(getViewClickListener());
        RichTextCheckedTextView richTextCheckedTextView6 = (RichTextCheckedTextView) view.findViewById(R.id.center);
        this.mCenterView = richTextCheckedTextView6;
        richTextCheckedTextView6.setOnClickListener(getViewClickListener());
        RichTextCheckedTextView richTextCheckedTextView7 = (RichTextCheckedTextView) view.findViewById(R.id.right);
        this.mRightView = richTextCheckedTextView7;
        richTextCheckedTextView7.setOnClickListener(getViewClickListener());
        RichTextCheckedTextView richTextCheckedTextView8 = (RichTextCheckedTextView) view.findViewById(R.id.bg_highlight);
        this.mBgHighLightView = richTextCheckedTextView8;
        richTextCheckedTextView8.setOnClickListener(getViewClickListener());
        RichTextCheckedTextView richTextCheckedTextView9 = (RichTextCheckedTextView) view.findViewById(R.id.bullet);
        this.mBulletView = richTextCheckedTextView9;
        richTextCheckedTextView9.setOnClickListener(getViewClickListener());
        RichTextCheckedTextView richTextCheckedTextView10 = (RichTextCheckedTextView) view.findViewById(R.id.reduce_font);
        this.mReduceFontView = richTextCheckedTextView10;
        richTextCheckedTextView10.setOnClickListener(getViewClickListener());
        ImageView imageView3 = (ImageView) view.findViewById(R.id.audio);
        this.mAudioButton = imageView3;
        imageView3.setOnClickListener(getViewClickListener());
        if (IS_SUPER_LITE) {
            this.mGalleryButton.setVisibility(8);
            this.mDoodleButton.setVisibility(8);
            this.mAudioButton.setVisibility(8);
        }
        smallWindowAdapter();
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void show(boolean z, boolean z2, boolean z3) {
        super.show(z, z2, z3);
        if (KeyboardHelper.INSTANCE.getShowKeyboard()) {
            return;
        }
        updatePaddingBottom(0);
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void startAudioInput() {
        super.startAudioInput();
        if (!KeyboardHelper.INSTANCE.getShowKeyboard()) {
            updatePaddingBottom(0);
        }
        this.mAudioButton.setSelected(true);
        this.mAudioButton.setContentDescription(getContext().getResources().getString(R.string.toolbar_voice_done_description));
        setIsOnRecord(true);
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void stopAudioInput() {
        super.stopAudioInput();
        this.mAudioButton.setSelected(false);
        this.mAudioButton.setContentDescription(getContext().getResources().getString(R.string.toolbar_voice_description));
        setIsOnRecord(false);
    }

    public void setIsOnRecord(boolean z) {
        View[] viewArr = {this.mGalleryButton, this.mDoodleButton, this.mCheckButton, this.mReduceFontView, this.mBulletView, this.mBgHighLightView, this.mRightView, this.mCenterView, this.mIncreaseFontView, this.mUnderlineView, this.mItalicView, this.mBoldView};
        for (int i = 0; i < 12; i++) {
            viewArr[i].setOnClickListener(!z ? getViewClickListener() : null);
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void updateTheme(Theme theme, NoteThemeResCache noteThemeResCache) {
        super.updateTheme(theme, noteThemeResCache);
        updateBtnStyle(theme, noteThemeResCache);
        Theme.EditorStyle editorStyle = theme.getEditorStyle(this.mToolbarRootView.getContext());
        BlurHelper.setBlurBackground(this.mToolbarRootView, null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor2);
    }

    private void updateBtnStyle(Theme theme, NoteThemeResCache noteThemeResCache) {
        if (!IS_SUPER_LITE) {
            this.mGalleryButton.setImageDrawable(noteThemeResCache.toolBarGalleryIcon);
            this.mDoodleButton.setImageDrawable(noteThemeResCache.toolBarDoodleIcon);
            this.mAudioButton.setImageDrawable(noteThemeResCache.toolBarAudioIcon);
        }
        this.mCheckButton.setDrawable(noteThemeResCache.toolBarCheckIcon);
        this.mBoldView.setDrawable(noteThemeResCache.toolBarBoldIcon);
        this.mItalicView.setDrawable(noteThemeResCache.toolBarItalicIcon);
        this.mUnderlineView.setDrawable(noteThemeResCache.toolBarUnderlineIcon);
        this.mIncreaseFontView.setDrawable(noteThemeResCache.toolBarIncreaseSizeIcon);
        this.mReduceFontView.setDrawable(noteThemeResCache.toolBarDecreaseSizeIcon);
        this.mCenterView.setDrawable(noteThemeResCache.toolBarCenterIcon);
        this.mRightView.setDrawable(noteThemeResCache.toolBarRightIcon);
        this.mBgHighLightView.setDrawable(noteThemeResCache.toolBarBgHighLightIcon);
        this.mBulletView.setDrawable(noteThemeResCache.toolBarBulletIcon);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature, com.miui.richeditor.TextStyleStateListener
    public void isNeedLock(boolean z) {
        super.isNeedLock(z);
        float f = z ? 1.0f : 0.4f;
        this.mGalleryButton.setAlpha(f);
        this.mAudioButton.setAlpha(f);
        this.mDoodleButton.setAlpha(f);
        resetActionListener(z ? getViewClickListener() : null);
    }

    public void resetActionListener(View.OnClickListener onClickListener) {
        View[] viewArr = {this.mGalleryButton, this.mAudioButton, this.mDoodleButton};
        for (int i = 0; i < 3; i++) {
            viewArr[i].setOnClickListener(onClickListener);
        }
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.TextStyleStateListener
    public void onFontSize(int i) {
        super.onFontSize(i);
        updateSizeState(R.id.increase_font);
        updateSizeState(R.id.reduce_font);
    }

    @Override // com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.richeditor.IRichTextStyleController
    public boolean checkbox() {
        return super.checkbox();
    }

    @Override // com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteRichEditToolbarController, com.miui.notes.component.noteedit.INoteEditExtraFeature
    public void onDestroy() {
        super.onDestroy();
        this.mGalleryButton.setOnClickListener(null);
        this.mDoodleButton.setOnClickListener(null);
        this.mCheckButton.setOnClickListener(null);
        this.mBoldView.setOnClickListener(null);
        this.mItalicView.setOnClickListener(null);
        this.mUnderlineView.setOnClickListener(null);
        this.mIncreaseFontView.setOnClickListener(null);
        this.mCenterView.setOnClickListener(null);
        this.mRightView.setOnClickListener(null);
        this.mBgHighLightView.setOnClickListener(null);
        this.mBulletView.setOnClickListener(null);
        this.mReduceFontView.setOnClickListener(null);
        this.mAudioButton.setOnClickListener(null);
        Logger.INSTANCE.d(TAG, "onDestroy,clear listeners");
    }

    @Override // com.miui.notes.component.noteedit.IBaseNoteEditToolbarController
    public void updatePaddingBottom(int i) {
        super.updatePaddingBottom(i);
        if (i > this.mBottomHeight) {
            ViewUtils.resetPaddingBottom(this.mToolbarContainer, 0);
        } else {
            ViewUtils.resetPaddingBottom(this.mToolbarContainer, this.mBottomHeight - i);
        }
    }

    protected void smallWindowAdapter() {
        if (this.mToolbarContainer != null) {
            ViewCompat.setOnApplyWindowInsetsListener(this.mToolbarContainer, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.component.noteedit.LiteSmallScreenNoteEditToolBarController.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view);
                    if (rootWindowInsets == null) {
                        return windowInsetsCompat;
                    }
                    Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                    LiteSmallScreenNoteEditToolBarController.this.mBottomHeight = insetsIgnoringVisibility.bottom;
                    return windowInsetsCompat;
                }
            });
        }
    }
}
