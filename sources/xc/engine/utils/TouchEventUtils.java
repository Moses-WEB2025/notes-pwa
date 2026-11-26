package xc.engine.utils;

import android.graphics.PointF;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes6.dex */
public class TouchEventUtils {
    public static boolean isClickEvent(PointF pointF, MotionEvent motionEvent) {
        return ((float) Math.sqrt(Math.pow((double) (motionEvent.getX() - pointF.x), 2.0d) + Math.pow((double) (motionEvent.getY() - pointF.y), 2.0d))) < 30.0f && motionEvent.getEventTime() - motionEvent.getDownTime() <= 150;
    }

    public static boolean isClickEventWithIgnoreTimeInterval(PointF pointF, PointF pointF2) {
        return ((float) Math.sqrt(Math.pow((double) (pointF2.x - pointF.x), 2.0d) + Math.pow((double) (pointF2.y - pointF.y), 2.0d))) < 30.0f;
    }
}
