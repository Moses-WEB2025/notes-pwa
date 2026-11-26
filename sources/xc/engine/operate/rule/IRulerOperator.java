package xc.engine.operate.rule;

import android.graphics.Canvas;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes6.dex */
public interface IRulerOperator {
    void enableRuler(boolean z);

    boolean isAdsorbent();

    boolean isEnable();

    void onDraw(Canvas canvas);

    void onSizeChanged(float f, float f2);

    boolean onTouchEvent(MotionEvent motionEvent);
}
