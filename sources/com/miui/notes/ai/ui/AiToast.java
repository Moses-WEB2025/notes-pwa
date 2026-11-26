package com.miui.notes.ai.ui;

import android.animation.Animator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.ai.text.widget.R;
import defpackage.R2;

/* JADX INFO: loaded from: classes4.dex */
public class AiToast {
    public static final long LONG_DURATION = 7000;
    public static final long SHORT_DURATION = 2000;
    private Context mContext;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private RelativeLayout mRootView;
    private View mToastView;

    public AiToast(Context context, RelativeLayout relativeLayout) {
        this.mContext = context;
        this.mRootView = relativeLayout;
    }

    public void showToast(String str, long j, int i) {
        if (this.mToastView == null) {
            this.mToastView = LayoutInflater.from(this.mContext).inflate(R.layout.toast_text, (ViewGroup) null);
            Context context = this.mContext;
            Drawable appIconByPackageName = getAppIconByPackageName(context, context.getPackageName());
            if (appIconByPackageName != null) {
                ((ImageView) this.mToastView.findViewById(R.id.icon)).setImageDrawable(appIconByPackageName);
            } else {
                this.mToastView.findViewById(R.id.icon).setVisibility(8);
            }
        }
        ((TextView) this.mToastView.findViewById(R.id.text)).setText(str);
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mToastView.getParent() == null) {
            addToView(j, i, str);
            return;
        }
        this.mToastView.sendAccessibilityEvent(8);
        this.mToastView.announceForAccessibility(str);
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.ui.AiToast.1
            @Override // java.lang.Runnable
            public void run() {
                AiToast.this.cancelToast();
            }
        }, j);
    }

    private void addToView(final long j, int i, final String str) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        layoutParams.addRule(12);
        layoutParams.bottomMargin = i + R2.array.ai_edit_text_blend_colors;
        this.mToastView.setAlpha(0.0f);
        this.mRootView.addView(this.mToastView, layoutParams);
        this.mToastView.animate().alpha(1.0f).setDuration(200L).setListener(new Animator.AnimatorListener() { // from class: com.miui.notes.ai.ui.AiToast.2
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
                AiToast.this.mHandler.removeCallbacksAndMessages(null);
                AiToast.this.mToastView.sendAccessibilityEvent(8);
                AiToast.this.mToastView.announceForAccessibility(str);
                AiToast.this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.ui.AiToast.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AiToast.this.cancelToast();
                    }
                }, j);
            }
        }).start();
    }

    public void cancelToast() {
        this.mToastView.animate().alpha(0.0f).setDuration(200L).setListener(new Animator.AnimatorListener() { // from class: com.miui.notes.ai.ui.AiToast.3
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
                if (AiToast.this.mRootView == null || AiToast.this.mToastView == null || AiToast.this.mToastView.getParent() != AiToast.this.mRootView) {
                    return;
                }
                AiToast.this.mRootView.removeView(AiToast.this.mToastView);
            }
        });
    }

    public void release() {
        View view;
        View view2 = this.mToastView;
        if (view2 != null) {
            view2.animate().cancel();
        }
        if (this.mRootView != null && (view = this.mToastView) != null) {
            ViewParent parent = view.getParent();
            RelativeLayout relativeLayout = this.mRootView;
            if (parent == relativeLayout) {
                relativeLayout.removeView(this.mToastView);
            }
        }
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler = null;
        this.mContext = null;
        this.mRootView = null;
    }

    public static Drawable getAppIconByPackageName(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationIcon(str);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }
}
