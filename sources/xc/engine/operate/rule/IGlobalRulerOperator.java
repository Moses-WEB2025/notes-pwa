package xc.engine.operate.rule;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import xc.engine.operate.touch.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
public interface IGlobalRulerOperator {
    boolean convertTouchPoint(TouchPoint touchPoint, MotionEvent motionEvent);

    void enableRuler(boolean z);

    boolean isAdsorbent();

    boolean isEnable();

    void onDraw(Canvas canvas);

    void onSizeChanged(float f, float f2);

    boolean onTouchEvent(MotionEvent motionEvent);

    void setGlobalOffset(RectF rectF);

    void setGlobalRulerListener(IGlobalRulerListener iGlobalRulerListener);
}
