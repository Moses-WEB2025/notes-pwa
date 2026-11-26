package com.miui.notes.ui.view;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.notes.R;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.pad.feature.notes.commonedit.HoverMotionImpl;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.view.animation.CubicEaseOutInterpolator;

/* JADX INFO: loaded from: classes4.dex */
public class EditModeToolbar extends LinearLayout {
    private static final String TAG = "EditModeToolbar";
    HoverMotionImpl hoverMotionImpl;
    private ImageView mAiButton;
    private ImageView mAudioButton;
    private ImageView mCheckButton;
    private ImageView mDoodleButton;
    private ImageView mGalleryButton;
    private View.OnClickListener mListener;
    private LayoutTransition mTransition;

    public void onShowThemePanel(boolean z, Animatable2.AnimationCallback animationCallback) {
    }

    @Deprecated
    public void onToggleCheck() {
    }

    @Deprecated
    public void onToggleDoodle() {
    }

    @Deprecated
    public void onToggleGallery() {
    }

    @Deprecated
    public void resetActiveIcon() {
    }

    public EditModeToolbar(Context context) {
        this(context, null);
    }

    public EditModeToolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EditModeToolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.hoverMotionImpl = new HoverMotionImpl();
        LayoutTransition layoutTransition = new LayoutTransition();
        this.mTransition = layoutTransition;
        layoutTransition.setDuration(300L);
        this.mTransition.setAnimator(2, null);
        this.mTransition.setInterpolator(4, new CubicEaseOutInterpolator());
        setLayoutTransition(this.mTransition);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        Logger.INSTANCE.d(TAG, "onFinishInflate");
        super.onFinishInflate();
        this.mAiButton = (ImageView) findViewById(R.id.ai);
        this.mGalleryButton = (ImageView) findViewById(R.id.gallery);
        this.mCheckButton = (ImageView) findViewById(R.id.check);
        this.mAudioButton = (ImageView) findViewById(R.id.audio);
        this.mDoodleButton = (ImageView) findViewById(R.id.edit_image);
        if (!RomUtils.isSupportAi() && !RomUtils.isShowAiTextIcon(getContext()) && !com.miui.notes.ai.utils.RomUtils.isLLMAIWriting(getContext())) {
            Logger.INSTANCE.d(TAG, "onFinishInflate mAiButton GONE");
            this.mAiButton.setVisibility(8);
        }
        if (RomUtils.isSupportAi()) {
            Logger.INSTANCE.d(TAG, "onFinishInflate isSupport Ai 1.0");
            this.mAiButton.setImageResource(R.drawable.ic_tool_ai);
        }
        Folme.useAt(this.mAiButton).touch().handleTouchOf(this.mAiButton, new AnimConfig[0]);
        Folme.useAt(this.mGalleryButton).touch().handleTouchOf(this.mGalleryButton, new AnimConfig[0]);
        Folme.useAt(this.mCheckButton).touch().handleTouchOf(this.mCheckButton, new AnimConfig[0]);
        Folme.useAt(this.mDoodleButton).touch().handleTouchOf(this.mDoodleButton, new AnimConfig[0]);
    }

    public void setActionListener(View.OnClickListener onClickListener) {
        this.mListener = onClickListener;
        View[] viewArr = {this.mAiButton, this.mGalleryButton, this.mCheckButton, this.mAudioButton, this.mDoodleButton};
        for (int i = 0; i < 5; i++) {
            View view = viewArr[i];
            view.setOnClickListener(onClickListener);
            if (!LiteUtils.isLiteOrMiddle() && RomUtils.isNewTablet()) {
                this.hoverMotionImpl.setOnHoverListener(view);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        HoverMotionImpl hoverMotionImpl = this.hoverMotionImpl;
        if (hoverMotionImpl != null) {
            hoverMotionImpl.onDetach();
        }
    }

    public void dismissPopupWindow() {
        this.hoverMotionImpl.dismissPopupWindow();
    }

    public void updateStyle(NoteThemeResCache noteThemeResCache) {
        this.mAiButton.setImageDrawable(noteThemeResCache.toolBarAiIcon);
        this.mCheckButton.setImageDrawable(noteThemeResCache.toolBarCheckIcon);
        this.mGalleryButton.setImageDrawable(noteThemeResCache.toolBarGalleryIcon);
        this.mDoodleButton.setImageDrawable(noteThemeResCache.toolBarDoodleIcon);
        this.mAudioButton.setImageDrawable(noteThemeResCache.toolBarAudioIcon);
    }

    public void onRecordStart() {
        this.mCheckButton.setEnabled(false);
        this.mAudioButton.setSelected(true);
        this.mAudioButton.setContentDescription(getContext().getResources().getString(R.string.toolbar_voice_done_description));
        dismissPopupWindow();
        this.mAiButton.setEnabled(false);
        this.mGalleryButton.setEnabled(false);
        this.mDoodleButton.setEnabled(false);
    }

    public void onRecordEnd() {
        this.mCheckButton.setEnabled(true);
        this.mAudioButton.setSelected(false);
        this.mAudioButton.setContentDescription(getContext().getResources().getString(R.string.toolbar_voice_description));
        this.mAiButton.setEnabled(true);
        this.mGalleryButton.setEnabled(true);
        this.mDoodleButton.setEnabled(true);
    }

    public void onEditMode() {
        this.mCheckButton.setEnabled(true);
        this.mAiButton.setEnabled(true);
        this.mGalleryButton.setEnabled(true);
        this.mAudioButton.setEnabled(true);
        this.mDoodleButton.setEnabled(true);
    }

    public void onViewMode() {
        this.mAiButton.setEnabled(false);
        this.mCheckButton.setEnabled(false);
        this.mGalleryButton.setEnabled(false);
        this.mAudioButton.setEnabled(false);
        this.mDoodleButton.setEnabled(false);
    }

    public void isNeedLock(boolean z) {
        float f = z ? 1.0f : 0.4f;
        this.mGalleryButton.setAlpha(f);
        this.mAudioButton.setAlpha(f);
        this.mDoodleButton.setAlpha(f);
        resetActionListener(z ? this.mListener : null);
    }

    public void resetActionListener(View.OnClickListener onClickListener) {
        View[] viewArr = {this.mGalleryButton, this.mAudioButton, this.mDoodleButton};
        for (int i = 0; i < 3; i++) {
            viewArr[i].setOnClickListener(onClickListener);
        }
    }

    private void toggleButtonOnce(ImageView imageView) {
        ((AnimatedVectorDrawable) imageView.getDrawable()).start();
    }

    public void setAiState(boolean z) {
        Logger.INSTANCE.d(TAG, "setAiState canUse:" + z);
        if (this.mAiButton != null) {
            if (!RomUtils.isSupportAi() && !RomUtils.isShowAiTextIcon(getContext()) && !com.miui.notes.ai.utils.RomUtils.isLLMAIWriting(getContext())) {
                Logger.INSTANCE.d(TAG, "setAiState mAiButton gone");
                this.mAiButton.setVisibility(8);
            } else {
                Logger.INSTANCE.d(TAG, "setAiState mAiButton visible");
                this.mAiButton.setVisibility(0);
            }
            if (RomUtils.isSupportAi()) {
                this.mAiButton.setAlpha(z ? 1.0f : 0.4f);
            }
            this.mAiButton.setOnClickListener(this.mListener);
        }
    }

    public void showPhotoPicker() {
        ImageView imageView = this.mGalleryButton;
        if (imageView == null) {
            return;
        }
        imageView.post(new Runnable() { // from class: com.miui.notes.ui.view.EditModeToolbar.1
            @Override // java.lang.Runnable
            public void run() {
                EditModeToolbar.this.mGalleryButton.setTag(R.id.gallery, "highLight");
                EditModeToolbar.this.mGalleryButton.performClick();
            }
        });
    }
}
