package xc.engine.operate.rule;

import android.graphics.Canvas;
import android.view.MotionEvent;
import xc.engine.api.PenEngine;

/* JADX INFO: loaded from: classes6.dex */
public class RulerOperator implements IRulerOperator {
    public boolean mEnable = false;
    public PenEngine mPenEngine;
    public Ruler mRuler;

    public RulerOperator(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    @Override // xc.engine.operate.rule.IRulerOperator
    public void enableRuler(boolean z) {
        this.mEnable = z;
        if (z) {
            this.mRuler = new Ruler(this.mPenEngine);
        } else {
            this.mRuler = null;
        }
    }

    @Override // xc.engine.operate.rule.IRulerOperator
    public boolean isEnable() {
        return this.mEnable;
    }

    @Override // xc.engine.operate.rule.IRulerOperator
    public boolean isAdsorbent() {
        Ruler ruler = this.mRuler;
        if (ruler == null) {
            return false;
        }
        return ruler.isAdsorbent();
    }

    @Override // xc.engine.operate.rule.IRulerOperator
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Ruler ruler = this.mRuler;
        if (ruler == null) {
            return false;
        }
        return ruler.onTouchEvent(motionEvent);
    }

    @Override // xc.engine.operate.rule.IRulerOperator
    public void onSizeChanged(float f, float f2) {
        Ruler ruler = this.mRuler;
        if (ruler == null) {
            return;
        }
        ruler.onSizeChanged(f, f2);
    }

    @Override // xc.engine.operate.rule.IRulerOperator
    public void onDraw(Canvas canvas) {
        Ruler ruler;
        if (!this.mEnable || (ruler = this.mRuler) == null) {
            return;
        }
        ruler.onDraw(canvas);
    }
}
