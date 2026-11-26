package xc.engine.operate.rule;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import xc.engine.operate.touch.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
public class GlobalRulerOperator implements IGlobalRulerOperator {
    public boolean mEnable = false;
    public IGlobalRulerListener mGlobalRulerListener;
    public RulerOperator mRulerOperator;

    @Override // xc.engine.operate.rule.IGlobalRulerOperator
    public void setGlobalRulerListener(IGlobalRulerListener iGlobalRulerListener) {
        this.mGlobalRulerListener = iGlobalRulerListener;
        RulerOperator rulerOperator = this.mRulerOperator;
        if (rulerOperator == null || rulerOperator.mRuler == null) {
            return;
        }
        this.mRulerOperator.mRuler.mGlobalRulerListener = iGlobalRulerListener;
    }

    @Override // xc.engine.operate.rule.IGlobalRulerOperator
    public void enableRuler(boolean z) {
        this.mEnable = z;
        RulerOperator rulerOperator = this.mRulerOperator;
        if (rulerOperator != null) {
            rulerOperator.enableRuler(z);
        }
    }

    @Override // xc.engine.operate.rule.IGlobalRulerOperator
    public boolean isEnable() {
        RulerOperator rulerOperator = this.mRulerOperator;
        if (rulerOperator != null) {
            return rulerOperator.mEnable;
        }
        return false;
    }

    @Override // xc.engine.operate.rule.IGlobalRulerOperator
    public boolean isAdsorbent() {
        RulerOperator rulerOperator = this.mRulerOperator;
        if (rulerOperator != null) {
            return rulerOperator.isAdsorbent();
        }
        return false;
    }

    @Override // xc.engine.operate.rule.IGlobalRulerOperator
    public boolean onTouchEvent(MotionEvent motionEvent) {
        RulerOperator rulerOperator = this.mRulerOperator;
        return (rulerOperator == null || rulerOperator.mRuler == null || !this.mRulerOperator.mRuler.onTouchEvent(motionEvent)) ? false : true;
    }

    @Override // xc.engine.operate.rule.IGlobalRulerOperator
    public boolean convertTouchPoint(TouchPoint touchPoint, MotionEvent motionEvent) {
        RulerOperator rulerOperator = this.mRulerOperator;
        if (rulerOperator == null || rulerOperator.mRuler == null) {
            return false;
        }
        return this.mRulerOperator.mRuler.convertTouchPoint(touchPoint);
    }

    @Override // xc.engine.operate.rule.IGlobalRulerOperator
    public void onSizeChanged(float f, float f2) {
        RulerOperator rulerOperator = this.mRulerOperator;
        if (rulerOperator == null || rulerOperator.mRuler == null) {
            return;
        }
        this.mRulerOperator.mRuler.onSizeChanged(f, f2);
    }

    @Override // xc.engine.operate.rule.IGlobalRulerOperator
    public void onDraw(Canvas canvas) {
        RulerOperator rulerOperator = this.mRulerOperator;
        if (rulerOperator == null || !rulerOperator.mEnable || this.mRulerOperator.mRuler == null) {
            return;
        }
        this.mRulerOperator.mRuler.onDraw(canvas);
    }

    @Override // xc.engine.operate.rule.IGlobalRulerOperator
    public void setGlobalOffset(RectF rectF) {
        RulerOperator rulerOperator = this.mRulerOperator;
        if (rulerOperator == null || rulerOperator.mRuler == null) {
            return;
        }
        this.mRulerOperator.mRuler.setCurrentPageOffset(rectF);
    }
}
