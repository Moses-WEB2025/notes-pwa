package com.miui.common.view;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class ItemAnimHelper {
    private static final long DEFAULT_ANIM_DURATION = 300;
    private static final float DURATION_SCALE = 3.0f;

    public static long getAnimDuration() {
        return 900L;
    }

    public static void transformToTargetViewNoScale(final View view, AnchorInfo anchorInfo, final Runnable runnable, float f) {
        view.getWidth();
        int width = (int) ((anchorInfo.getWidth() * 1.0f) / view.getScaleX());
        view.getHeight();
        int height = (int) ((anchorInfo.getHeight() * 1.0f) / view.getScaleY());
        view.setPivotX(0.0f);
        view.setPivotY(0.0f);
        IFolme iFolmeUseAt = Folme.useAt(view);
        AnimState animState = new AnimState("transNoScale");
        animState.add(ViewProperty.X, anchorInfo.getX());
        animState.add(ViewProperty.Y, anchorInfo.getY());
        animState.add(ViewProperty.WIDTH, width);
        animState.add(ViewProperty.HEIGHT, height);
        animState.add(ViewProperty.ROTATION, anchorInfo.getRotate());
        animState.add(ViewProperty.ALPHA, 1.0d);
        AnimConfig animConfig = new AnimConfig();
        AnimSpecialConfig animSpecialConfig = new AnimSpecialConfig();
        AnimSpecialConfig animSpecialConfig2 = new AnimSpecialConfig();
        animConfig.setSpecial(ViewProperty.X, animSpecialConfig);
        animConfig.setSpecial(ViewProperty.Y, animSpecialConfig2);
        if (anchorInfo.getY() < view.getTranslationY()) {
            animSpecialConfig.setEase(EaseManager.getStyle(-2, 0.9f, f));
            animSpecialConfig2.setEase(EaseManager.getStyle(-2, 0.85f, f + 0.15f));
        } else {
            animSpecialConfig.setEase(EaseManager.getStyle(-2, 0.85f, 0.15f + f));
            animSpecialConfig2.setEase(EaseManager.getStyle(-2, 0.9f, f));
        }
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.common.view.ItemAnimHelper.1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    view.postDelayed(runnable2, 1000L);
                }
            }
        });
        iFolmeUseAt.state().to(animState, animConfig);
    }

    public static void moveIntoAnotherItem(View view, Point point, View view2, AnchorInfo anchorInfo, float f) {
        moveIntoAnotherItemWithCallBack(view, point, view2, anchorInfo, null, f);
    }

    public static void moveIntoAnotherItemWithCallBack(View view, Point point, View view2, AnchorInfo anchorInfo, Runnable runnable, float f) {
        float x = point.x - view2.getX();
        float y = point.y - view2.getY();
        ((ViewGroup) view.getParent()).removeView(view);
        ((ViewGroup) view2).addView(view);
        view.setTranslationX(x);
        view.setTranslationY(y);
        transformToTargetViewNoScale(view, anchorInfo, runnable, f);
    }

    public static class AnchorInfo {
        private PointF mBasePoint;
        private float mRotate;
        private Size mSize;

        public AnchorInfo(float f, float f2, int i, int i2) {
            this(f, f2, i, i2, 0.0f);
        }

        public AnchorInfo(float f, float f2, int i, int i2, float f3) {
            this.mBasePoint = new PointF(f, f2);
            this.mSize = new Size(i, i2);
            this.mRotate = f3;
        }

        public float getX() {
            return this.mBasePoint.x;
        }

        public float getY() {
            return this.mBasePoint.y;
        }

        public void setX(float f) {
            this.mBasePoint.x = f;
        }

        public void setY(float f) {
            this.mBasePoint.y = f;
        }

        public int getWidth() {
            return this.mSize.getWidth();
        }

        public int getHeight() {
            return this.mSize.getHeight();
        }

        public PointF getBasePoint() {
            return this.mBasePoint;
        }

        public Size getSize() {
            return this.mSize;
        }

        public float getRotate() {
            return this.mRotate;
        }
    }
}
