package com.miui.richeditor.style.audio;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import com.miui.common.tool.UIUtils;
import com.miui.richeditor.style.BaseAudioPlayListener;
import miuix.view.animation.SineEaseInOutInterpolator;

/* JADX INFO: loaded from: classes4.dex */
public class NormalAudioSpanDrawable extends WaveBubbleDrawable {
    protected ValueAnimator mValueAnimator;

    public NormalAudioSpanDrawable(int i, int i2) {
        super(i, i2);
    }

    @Override // com.miui.richeditor.style.audio.BubbleDrawable
    public void initStyle(int i) {
        if (UIUtils.isPcDensity(this.mContext)) {
            super.initStyle(10);
        } else {
            super.initStyle(i);
        }
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleAnim
    public void stopAnim() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mValueAnimator.removeAllUpdateListeners();
            this.mValueAnimator.removeAllListeners();
            this.mValueAnimator = null;
        }
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleAnim
    public void startIncreaseAnim(int i, final BaseAudioPlayListener baseAudioPlayListener) {
        this.mIncreaseTime = i;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, this.mIncreaseTime);
        this.mValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(300L);
        this.mValueAnimator.setRepeatCount(0);
        this.mValueAnimator.setInterpolator(new SineEaseInOutInterpolator());
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.audio.NormalAudioSpanDrawable.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                if (iIntValue == NormalAudioSpanDrawable.this.mIncreaseTime) {
                    NormalAudioSpanDrawable.this.onIncreaseAnimEnd();
                } else {
                    NormalAudioSpanDrawable.this.updateDimension(iIntValue);
                    NormalAudioSpanDrawable normalAudioSpanDrawable = NormalAudioSpanDrawable.this;
                    normalAudioSpanDrawable.setBounds(0, 0, normalAudioSpanDrawable.mRectWidth, NormalAudioSpanDrawable.this.mRectHeight);
                }
                baseAudioPlayListener.onAnimationUpdate(valueAnimator);
            }
        });
        this.mValueAnimator.addListener(baseAudioPlayListener);
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.miui.richeditor.style.audio.NormalAudioSpanDrawable.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                NormalAudioSpanDrawable.this.onIncreaseAnimEnd();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                NormalAudioSpanDrawable.this.onIncreaseAnimEnd();
            }
        });
        this.mValueAnimator.start();
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleAnim
    public void startPlayAnim(final BaseAudioPlayListener baseAudioPlayListener) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mValueAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setRepeatCount(-1);
        this.mValueAnimator.setRepeatMode(2);
        this.mValueAnimator.setInterpolator(new SineEaseInOutInterpolator());
        this.mValueAnimator.setDuration(300L);
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.audio.NormalAudioSpanDrawable.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                NormalAudioSpanDrawable.this.updateOffset(((Float) valueAnimator.getAnimatedValue()).floatValue());
                baseAudioPlayListener.onAnimationUpdate(valueAnimator);
            }
        });
        this.mValueAnimator.addListener(baseAudioPlayListener);
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.miui.richeditor.style.audio.NormalAudioSpanDrawable.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                NormalAudioSpanDrawable.this.onPlayAnimEnd();
                baseAudioPlayListener.onAnimationCancel(animator);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                NormalAudioSpanDrawable.this.onPlayAnimEnd();
            }
        });
        this.mValueAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOffset(float f) {
        this.mLineHeight0Offset = (int) ((this.mWaveFrame1LineHeight0 - this.mWaveFrame0LineHeight0) * f);
        this.mLineHeight1Offset = (int) ((this.mWaveFrame1LineHeight1 - this.mWaveFrame0LineHeight1) * f);
        this.mLineHeight2Offset = (int) ((this.mWaveFrame1LineHeight2 - this.mWaveFrame0LineHeight2) * f);
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleAnim
    public void onIncreaseAnimEnd() {
        if (!isDrawText()) {
            this.mDrawType = 0;
        }
        updateDimension(this.mIncreaseTime);
        setBounds(0, 0, this.mRectWidth, this.mRectHeight);
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleAnim
    public void onPlayAnimEnd() {
        updateOffset(0.0f);
    }
}
