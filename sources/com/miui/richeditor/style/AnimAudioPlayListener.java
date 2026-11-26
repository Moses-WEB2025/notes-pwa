package com.miui.richeditor.style;

import android.animation.Animator;
import android.animation.ValueAnimator;
import com.miui.richeditor.IAudioEditorContext;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class AnimAudioPlayListener extends BaseAudioPlayListener {
    private WeakReference<AudioSpan> mAudioSpanRef;
    private WeakReference<IAudioEditorContext> mEditorContainerRef;

    public AnimAudioPlayListener(IAudioEditorContext iAudioEditorContext, AudioSpan audioSpan) {
        this.mEditorContainerRef = new WeakReference<>(iAudioEditorContext);
        this.mAudioSpanRef = new WeakReference<>(audioSpan);
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (this.mAudioSpanRef.get() != null) {
            this.mAudioSpanRef.get().clearDrawable();
        }
        if (this.mEditorContainerRef.get() != null) {
            this.mEditorContainerRef.get().refreshAllImageSpans();
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        super.onAnimationCancel(animator);
        if (this.mAudioSpanRef.get() != null) {
            this.mAudioSpanRef.get().clearDrawable();
        }
        if (this.mEditorContainerRef.get() != null) {
            this.mEditorContainerRef.get().refreshAllImageSpans();
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        super.onAnimationEnd(animator);
        if (this.mAudioSpanRef.get() != null) {
            this.mAudioSpanRef.get().clearDrawable();
        }
        if (this.mEditorContainerRef.get() != null) {
            this.mEditorContainerRef.get().refreshAllImageSpans();
        }
    }

    @Override // com.miui.richeditor.style.BaseAudioPlayListener
    public void onAnimStateChanged(boolean z) {
        if (this.mAudioSpanRef.get() != null) {
            this.mAudioSpanRef.get().setAnimRunningState(z);
        }
    }
}
