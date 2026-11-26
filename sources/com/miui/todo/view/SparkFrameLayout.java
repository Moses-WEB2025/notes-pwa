package com.miui.todo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import com.miui.common.tool.Logger;
import com.miui.common.view.DoneSparkAnim;

/* JADX INFO: loaded from: classes4.dex */
public class SparkFrameLayout extends FrameLayout {
    public DoneSparkAnim doneSparkAnim;
    private boolean mChangeDrawLevel;

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void focusableViewAvailable(View view) {
        super.focusableViewAvailable(view);
    }

    public SparkFrameLayout(Context context) {
        super(context);
        this.doneSparkAnim = null;
        this.mChangeDrawLevel = false;
    }

    public SparkFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.doneSparkAnim = null;
        this.mChangeDrawLevel = false;
    }

    public SparkFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.doneSparkAnim = null;
        this.mChangeDrawLevel = false;
    }

    public SparkFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.doneSparkAnim = null;
        this.mChangeDrawLevel = false;
    }

    public void startSparkAnim(Animator.AnimatorListener animatorListener, final Runnable runnable) {
        this.mChangeDrawLevel = false;
        Logger.INSTANCE.d("SparkFrameLayout", "startSparkAnim");
        DoneSparkAnim doneSparkAnim = this.doneSparkAnim;
        if (doneSparkAnim == null || !doneSparkAnim.isRunning()) {
            if (this.doneSparkAnim == null) {
                DoneSparkAnim doneSparkAnim2 = new DoneSparkAnim(this, new Rect(0, 0, getWidth(), getHeight()));
                this.doneSparkAnim = doneSparkAnim2;
                doneSparkAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.todo.view.SparkFrameLayout.1
                    boolean shouldFinish = true;

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        if (valueAnimator.getCurrentPlayTime() > 1000) {
                            SparkFrameLayout.this.mChangeDrawLevel = true;
                        }
                        if (this.shouldFinish && valueAnimator.getAnimatedFraction() > 0.7f) {
                            this.shouldFinish = false;
                            Runnable runnable2 = runnable;
                            if (runnable2 != null) {
                                runnable2.run();
                            }
                        }
                        SparkFrameLayout.this.invalidate();
                    }
                });
                this.doneSparkAnim.addListener(animatorListener);
                this.doneSparkAnim.addListener(new Animator.AnimatorListener() { // from class: com.miui.todo.view.SparkFrameLayout.2
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        Logger.INSTANCE.d("SparkFrameLayout", "onAnimationStart");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Logger.INSTANCE.d("SparkFrameLayout", "onAnimationEnd");
                        SparkFrameLayout.this.mChangeDrawLevel = false;
                        SparkFrameLayout.this.doneSparkAnim = null;
                    }
                });
                this.doneSparkAnim.setDuration(1300L);
                this.doneSparkAnim.setInterpolator(new LinearInterpolator());
            }
            this.doneSparkAnim.start();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mChangeDrawLevel) {
            super.dispatchDraw(canvas);
            DoneSparkAnim doneSparkAnim = this.doneSparkAnim;
            if (doneSparkAnim != null) {
                doneSparkAnim.draw(canvas);
                return;
            }
            return;
        }
        DoneSparkAnim doneSparkAnim2 = this.doneSparkAnim;
        if (doneSparkAnim2 != null) {
            doneSparkAnim2.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }
}
