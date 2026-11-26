package xc.engine.operate.canvas.track;

import android.graphics.Canvas;
import xc.engine.operate.touch.HoverPoint;
import xc.engine.operate.touch.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
public interface IOperator {
    void applyAdsorbent(boolean z);

    void cancel();

    void hoverExit();

    void hoverMove(HoverPoint hoverPoint, boolean z, float f, int i);

    void onCancelII();

    void onDown(TouchPoint touchPoint, TouchPoint touchPoint2);

    void onDraw(Canvas canvas);

    void onMove(TouchPoint[] touchPointArr, TouchPoint[] touchPointArr2, TouchPoint touchPoint);

    void onUp(TouchPoint[] touchPointArr);

    void setEraseTime(long j);
}
