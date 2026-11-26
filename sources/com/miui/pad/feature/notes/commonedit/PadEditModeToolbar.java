package com.miui.pad.feature.notes.commonedit;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.notes.R;
import com.miui.notes.theme.util.NoteThemeResCache;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.view.animation.CubicEaseOutInterpolator;

/* JADX INFO: loaded from: classes4.dex */
public class PadEditModeToolbar extends LinearLayout {
    private static final String TAG = "PadEditModeToolbar";
    ToolbarAccessiabilityDelegate accessibilityDelegate;
    private HoverMotionImpl hoverMotionImpl;
    private ImageView mAiButton;
    private ImageView mAudioButton;
    private ImageView mDoodleButton;
    private ImageView mGalleryButton;
    private View.OnClickListener mListener;
    private LayoutTransition mTransition;
    ToolbarunlockAccessiabilityDelegate unlockaccessiabilityDelegate;

    public void onToggleDoodle() {
    }

    public void onToggleGallery() {
    }

    public void resetActiveIcon() {
    }

    public PadEditModeToolbar(Context context) {
        this(context, null);
    }

    public PadEditModeToolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PadEditModeToolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.hoverMotionImpl = new HoverMotionImpl();
        LayoutTransition layoutTransition = new LayoutTransition();
        this.mTransition = layoutTransition;
        layoutTransition.setDuration(300L);
        this.mTransition.setAnimator(2, null);
        this.mTransition.setInterpolator(4, new CubicEaseOutInterpolator());
        setLayoutTransition(this.mTransition);
        this.accessibilityDelegate = new ToolbarAccessiabilityDelegate();
        this.unlockaccessiabilityDelegate = new ToolbarunlockAccessiabilityDelegate();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        Logger.INSTANCE.d(TAG, "onFinishInflate");
        super.onFinishInflate();
        this.mAiButton = (ImageView) findViewById(R.id.ai);
        this.mGalleryButton = (ImageView) findViewById(R.id.gallery);
        this.mDoodleButton = (ImageView) findViewById(R.id.edit_image);
        this.mAudioButton = (ImageView) findViewById(R.id.audio);
        Folme.useAt(this.mAiButton).touch().handleTouchOf(this.mAiButton, new AnimConfig[0]);
        Folme.useAt(this.mGalleryButton).touch().handleTouchOf(this.mGalleryButton, new AnimConfig[0]);
        Folme.useAt(this.mDoodleButton).touch().handleTouchOf(this.mDoodleButton, new AnimConfig[0]);
        Folme.useAt(this.mAudioButton).touch().handleTouchOf(this.mAudioButton, new AnimConfig[0]);
        if (!RomUtils.isSupportAi() && !RomUtils.isShowAiTextIcon(getContext()) && !com.miui.notes.ai.utils.RomUtils.isLLMAIWriting(getContext())) {
            Logger.INSTANCE.d(TAG, "onFinishInflate mAiButton GONE");
            this.mAiButton.setVisibility(8);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mAudioButton.getLayoutParams();
            marginLayoutParams.setMarginStart(0);
            this.mAudioButton.setLayoutParams(marginLayoutParams);
        }
        if (RomUtils.isSupportAi()) {
            Logger.INSTANCE.d(TAG, "onFinishInflate isSupport Ai 1.0");
            this.mAiButton.setImageResource(R.drawable.ic_tool_ai);
        }
        if (!LiteUtils.isLiteOrMiddle() && RomUtils.isNewTablet()) {
            this.hoverMotionImpl.setOnHoverListener(this.mGalleryButton);
            this.hoverMotionImpl.setOnHoverListener(this.mDoodleButton);
            this.hoverMotionImpl.setOnHoverListener(this.mAudioButton);
        }
        updateDrawParams();
    }

    public void showPhotoPicker() {
        ImageView imageView = this.mGalleryButton;
        if (imageView == null) {
            return;
        }
        imageView.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadEditModeToolbar.1
            @Override // java.lang.Runnable
            public void run() {
                PadEditModeToolbar.this.mGalleryButton.setTag(R.id.gallery, "highLight");
                PadEditModeToolbar.this.mGalleryButton.performClick();
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        HoverMotionImpl hoverMotionImpl = this.hoverMotionImpl;
        if (hoverMotionImpl != null) {
            hoverMotionImpl.onDetach();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateDrawParams();
    }

    private void updateDrawParams() {
        if (RomUtils.isNewTablet()) {
            int i = getContext().getResources().getConfiguration().orientation == 2 ? R.dimen.notes_edit_toolbar_margin_to_divider : R.dimen.notes_edit_toolbar_icon_margin_start_big_vertical;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mGalleryButton.getLayoutParams();
            marginLayoutParams.setMarginStart((int) getContext().getResources().getDimension(i));
            this.mGalleryButton.setLayoutParams(marginLayoutParams);
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mDoodleButton.getLayoutParams();
            marginLayoutParams2.setMarginStart((int) getContext().getResources().getDimension(i));
            this.mDoodleButton.setLayoutParams(marginLayoutParams2);
            if (this.mAiButton.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) this.mAudioButton.getLayoutParams();
                marginLayoutParams3.setMarginStart((int) getContext().getResources().getDimension(i));
                this.mAudioButton.setLayoutParams(marginLayoutParams3);
            }
        }
    }

    public void setActionListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            this.mListener = onClickListener;
        }
        View[] viewArr = {this.mGalleryButton, this.mAudioButton, this.mDoodleButton};
        for (int i = 0; i < 3; i++) {
            viewArr[i].setOnClickListener(onClickListener);
        }
        setOnClickListener(null);
    }

    public void updateStyle(NoteThemeResCache noteThemeResCache) {
        this.mAiButton.setImageDrawable(noteThemeResCache.toolBarAiIcon);
        this.mGalleryButton.setImageDrawable(noteThemeResCache.toolBarGalleryIcon);
        this.mAudioButton.setImageDrawable(noteThemeResCache.toolBarAudioIcon);
        this.mDoodleButton.setImageDrawable(noteThemeResCache.toolBarDoodleIcon);
    }

    public void dismissPopupWindow() {
        this.hoverMotionImpl.dismissPopupWindow();
    }

    public void onRecordStart() {
        resetActiveIcon();
        this.mAudioButton.setSelected(true);
        this.mAudioButton.requestLayout();
        dismissPopupWindow();
        this.mAiButton.setSelected(false);
        this.mGalleryButton.setEnabled(false);
        this.mDoodleButton.setEnabled(false);
        this.mAudioButton.setContentDescription(getContext().getResources().getString(R.string.toolbar_voice_done_description));
    }

    public void onRecordEnd() {
        this.mAiButton.setSelected(true);
        this.mAudioButton.setSelected(false);
        this.mGalleryButton.setEnabled(true);
        this.mDoodleButton.setEnabled(true);
        this.mAudioButton.setContentDescription(getContext().getResources().getString(R.string.toolbar_voice_description));
    }

    public void onEditMode() {
        resetActiveIcon();
        this.mAiButton.setSelected(true);
        this.mGalleryButton.setEnabled(true);
        this.mAudioButton.setEnabled(true);
        this.mDoodleButton.setEnabled(true);
    }

    public void onViewMode() {
        resetActiveIcon();
        this.mAiButton.setEnabled(false);
        this.mGalleryButton.setEnabled(false);
        this.mAudioButton.setEnabled(false);
        this.mDoodleButton.setEnabled(false);
    }

    public void isNeedLock(boolean z) {
        float f = z ? 1.0f : 0.4f;
        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = this.mGalleryButton.createAccessibilityNodeInfo();
        if (!z) {
            this.accessibilityDelegate.onInitializeAccessibilityNodeInfo(this.mGalleryButton, accessibilityNodeInfoCreateAccessibilityNodeInfo);
            this.mGalleryButton.setAccessibilityDelegate(this.accessibilityDelegate);
            this.accessibilityDelegate.onInitializeAccessibilityNodeInfo(this.mAudioButton, this.mAudioButton.createAccessibilityNodeInfo());
            this.mAudioButton.setAccessibilityDelegate(this.accessibilityDelegate);
            this.accessibilityDelegate.onInitializeAccessibilityNodeInfo(this.mDoodleButton, this.mDoodleButton.createAccessibilityNodeInfo());
            this.mDoodleButton.setAccessibilityDelegate(this.accessibilityDelegate);
        } else {
            this.unlockaccessiabilityDelegate.onInitializeAccessibilityNodeInfo(this.mGalleryButton, accessibilityNodeInfoCreateAccessibilityNodeInfo);
            this.mGalleryButton.setAccessibilityDelegate(this.unlockaccessiabilityDelegate);
            this.unlockaccessiabilityDelegate.onInitializeAccessibilityNodeInfo(this.mAudioButton, this.mAudioButton.createAccessibilityNodeInfo());
            this.mAudioButton.setAccessibilityDelegate(this.unlockaccessiabilityDelegate);
            this.unlockaccessiabilityDelegate.onInitializeAccessibilityNodeInfo(this.mDoodleButton, this.mDoodleButton.createAccessibilityNodeInfo());
            this.mDoodleButton.setAccessibilityDelegate(this.unlockaccessiabilityDelegate);
        }
        this.mGalleryButton.setAlpha(f);
        this.mAudioButton.setAlpha(f);
        this.mDoodleButton.setAlpha(f);
        setActionListener(z ? this.mListener : null);
    }

    protected boolean updateState(int i, boolean z) {
        CheckedTextView checkedTextView = (CheckedTextView) findViewById(i);
        if (checkedTextView == null) {
            return false;
        }
        checkedTextView.setChecked(z);
        return true;
    }

    public void resetAudioButton() {
        this.mAudioButton.setSelected(false);
    }

    public void onShowThemePanel(boolean z, Animatable2.AnimationCallback animationCallback) {
        resetActiveIcon();
    }

    private void toggleButtonOnce(ImageView imageView) {
        ((AnimatedVectorDrawable) imageView.getDrawable()).start();
    }

    private class ToolbarAccessiabilityDelegate extends View.AccessibilityDelegate {
        public ToolbarAccessiabilityDelegate() {
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setEnabled(false);
            accessibilityNodeInfo.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK.getId());
        }
    }

    private class ToolbarunlockAccessiabilityDelegate extends View.AccessibilityDelegate {
        public ToolbarunlockAccessiabilityDelegate() {
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setClickable(true);
        }
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
}
