package com.miui.pad.feature.notes.commonedit;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.INoteRichEditor;
import com.miui.notes.ui.view.NoteEditAudioTransGroup;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes4.dex */
public class CommonPadNoteAudioController {
    public NoteEditAudioTransGroup mAudioTransGroup;
    public View mAudioTransView;
    ValueAnimator mDismissAnimator;
    public INoteRichEditor mEditor;
    public OnPanelListener mPanelListener;
    public ViewGroup mParentView;
    public boolean mIsDismissing = false;
    public boolean mIsSingleMode = true;
    public boolean mIsLanTrans = false;
    public TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.pad.feature.notes.commonedit.CommonPadNoteAudioController.1
        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj, Collection<UpdateInfo> collection) {
            super.onBegin(obj, collection);
            if (CommonPadNoteAudioController.this.mAudioTransGroup != null) {
                CommonPadNoteAudioController.this.mAudioTransGroup.startWaveAnim();
            }
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (CommonPadNoteAudioController.this.mPanelListener != null) {
                CommonPadNoteAudioController.this.mPanelListener.onShow(CommonPadNoteAudioController.this.mParentView);
            }
        }
    };
    public NoteEditAudioTransGroup.OnTransClickListener OnTransClickListener = new NoteEditAudioTransGroup.OnTransClickListener() { // from class: com.miui.pad.feature.notes.commonedit.CommonPadNoteAudioController.2
        @Override // com.miui.notes.ui.view.NoteEditAudioTransGroup.OnTransClickListener
        public void onViewClick(View view) {
            if (CommonPadNoteAudioController.this.mPanelListener != null) {
                CommonPadNoteAudioController.this.mPanelListener.onViewClick(view);
            }
        }
    };

    interface OnPanelListener extends NoteEditAudioTransGroup.OnTransClickListener {
        void onDismiss();

        void onShow(View view);
    }

    public CommonPadNoteAudioController(INoteRichEditor iNoteRichEditor, ViewGroup viewGroup, OnPanelListener onPanelListener) {
        this.mEditor = iNoteRichEditor;
        this.mParentView = viewGroup;
        this.mPanelListener = onPanelListener;
    }

    public void show(ScreenSpec screenSpec) {
        this.mAudioTransView = createAudioTransPanel();
        this.mAudioTransGroup = new NoteEditAudioTransGroup(this.mEditor.getContext(), this.mAudioTransView, screenSpec, this.mIsSingleMode, this.mIsLanTrans);
        int dimensionPixelSize = this.mParentView.getContext().getResources().getDimensionPixelSize(R.dimen.pad_note_audio_panel_height);
        this.mAudioTransGroup.updateMode(this.mIsSingleMode, this.mIsLanTrans, null);
        this.mAudioTransGroup.setOnTransListener(this.OnTransClickListener);
        NoteEditAudioTransGroup noteEditAudioTransGroup = this.mAudioTransGroup;
        if (noteEditAudioTransGroup != null) {
            noteEditAudioTransGroup.updateScreenStatus(screenSpec);
        }
        this.mAudioTransView.setAlpha(0.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.mParentView.getLayoutParams());
        layoutParams.height = -2;
        layoutParams.width = -1;
        layoutParams.bottomMargin = 0;
        this.mParentView.addView(this.mAudioTransView, layoutParams);
        Folme.useAt(this.mAudioTransView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), ViewProperty.HEIGHT, Integer.valueOf(dimensionPixelSize), new AnimConfig().addListeners(this.mShowListener));
    }

    public View getAudioTransView() {
        return this.mAudioTransView;
    }

    public void dismiss() {
        if (this.mIsDismissing) {
            return;
        }
        this.mIsDismissing = true;
        ValueAnimator valueAnimator = this.mDismissAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mDismissAnimator = null;
        }
        if (this.mAudioTransView != null) {
            startDismissAnim();
        }
    }

    private void startDismissAnim() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mDismissAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(300L);
        final int i = this.mAudioTransView.getLayoutParams().height;
        final float alpha = this.mAudioTransView.getAlpha();
        this.mDismissAnimator.setInterpolator(new EaseManager.SpringInterpolator().setResponse(0.85f).setDamping(0.9f));
        this.mDismissAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.pad.feature.notes.commonedit.CommonPadNoteAudioController.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ViewGroup.LayoutParams layoutParams = CommonPadNoteAudioController.this.mAudioTransView.getLayoutParams();
                layoutParams.height = (int) (i * fFloatValue);
                CommonPadNoteAudioController.this.mAudioTransView.setLayoutParams(layoutParams);
                CommonPadNoteAudioController.this.mAudioTransView.setAlpha(alpha * fFloatValue);
                CommonPadNoteAudioController.this.mAudioTransView.requestLayout();
            }
        });
        this.mDismissAnimator.addListener(new Animator.AnimatorListener() { // from class: com.miui.pad.feature.notes.commonedit.CommonPadNoteAudioController.4
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                CommonPadNoteAudioController.this.onDismissEnd();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                CommonPadNoteAudioController.this.onDismissEnd();
            }
        });
        this.mDismissAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDismissEnd() {
        View view = this.mAudioTransView;
        if (view != null) {
            ((ViewGroup) view.getParent()).removeView(this.mAudioTransView);
        }
        NoteEditAudioTransGroup noteEditAudioTransGroup = this.mAudioTransGroup;
        if (noteEditAudioTransGroup != null) {
            noteEditAudioTransGroup.stopWaveAnim();
        }
        this.mIsDismissing = false;
        this.mAudioTransView = null;
        this.mAudioTransGroup = null;
        OnPanelListener onPanelListener = this.mPanelListener;
        if (onPanelListener != null) {
            onPanelListener.onDismiss();
        }
    }

    public void updateView(ScreenSpec screenSpec) {
        NoteEditAudioTransGroup noteEditAudioTransGroup = this.mAudioTransGroup;
        if (noteEditAudioTransGroup != null) {
            noteEditAudioTransGroup.updateMode(this.mIsSingleMode, this.mIsLanTrans, screenSpec);
        }
    }

    public void updateScreenStatus(ScreenSpec screenSpec) {
        NoteEditAudioTransGroup noteEditAudioTransGroup = this.mAudioTransGroup;
        if (noteEditAudioTransGroup != null) {
            noteEditAudioTransGroup.updateScreenStatus(screenSpec);
        }
    }

    public View getModeMenuAnchorView() {
        NoteEditAudioTransGroup noteEditAudioTransGroup = this.mAudioTransGroup;
        if (noteEditAudioTransGroup != null) {
            return noteEditAudioTransGroup.getModeMenuAnchorView();
        }
        return null;
    }

    public void updateAudioVolume(double d) {
        NoteEditAudioTransGroup noteEditAudioTransGroup = this.mAudioTransGroup;
        if (noteEditAudioTransGroup != null) {
            noteEditAudioTransGroup.updateWave(d);
        }
    }

    public View createAudioTransPanel() {
        return UIUtils.inflateView(this.mParentView, R.layout.pad_edit_audio_record_panel);
    }

    public void setMode(boolean z) {
        if (this.mIsSingleMode != z) {
            this.mIsSingleMode = z;
            NoteEditAudioTransGroup noteEditAudioTransGroup = this.mAudioTransGroup;
            if (noteEditAudioTransGroup != null) {
                noteEditAudioTransGroup.updateModeText(z);
            }
        }
    }

    public void setLanTransEnable(boolean z) {
        this.mIsLanTrans = z;
        NoteEditAudioTransGroup noteEditAudioTransGroup = this.mAudioTransGroup;
        if (noteEditAudioTransGroup != null) {
            noteEditAudioTransGroup.updateTrans(z);
        }
    }

    public boolean isSingleMode() {
        return this.mIsSingleMode;
    }

    public boolean isLanTrans() {
        return this.mIsLanTrans;
    }
}
