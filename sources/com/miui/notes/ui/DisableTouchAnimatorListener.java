package com.miui.notes.ui;

import android.animation.Animator;
import android.app.Activity;

/* JADX INFO: loaded from: classes4.dex */
public class DisableTouchAnimatorListener implements Animator.AnimatorListener {
    private Activity mActivity;

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
    }

    public DisableTouchAnimatorListener(Activity activity) {
        this.mActivity = activity;
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        disableTouchEvent();
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        enableTouchEvent();
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        enableTouchEvent();
    }

    private void disableTouchEvent() {
        this.mActivity.getWindow().setFlags(16, 16);
    }

    private void enableTouchEvent() {
        this.mActivity.getWindow().clearFlags(16);
    }
}
