package xc.engine.core.algorithm;

/* JADX INFO: loaded from: classes6.dex */
public class TouchPoint {
    public int action;
    public long downTime;
    public boolean isHistory;
    public float orientation;
    public float pressure;
    public float tilt;
    public float x;
    public float y;

    public TouchPoint(TouchPoint touchPoint) {
        this.x = 0.0f;
        this.y = 0.0f;
        this.action = 0;
        this.downTime = 0L;
        this.tilt = 1.0f;
        this.pressure = 1.0f;
        this.orientation = 0.0f;
        this.isHistory = false;
        this.x = touchPoint.x;
        this.y = touchPoint.y;
        this.action = touchPoint.action;
        this.tilt = touchPoint.tilt;
        this.downTime = touchPoint.downTime;
        this.pressure = touchPoint.pressure;
        this.orientation = touchPoint.orientation;
        this.isHistory = touchPoint.isHistory;
    }

    public TouchPoint(float f, float f2) {
        this.action = 0;
        this.downTime = 0L;
        this.tilt = 1.0f;
        this.pressure = 1.0f;
        this.orientation = 0.0f;
        this.isHistory = false;
        this.x = f;
        this.y = f2;
    }

    public TouchPoint(float f, float f2, float f3) {
        this(f, f2);
        this.pressure = f3;
    }

    public TouchPoint(float f, float f2, long j) {
        this(f, f2);
        this.downTime = j;
    }

    public TouchPoint(float f, float f2, float f3, float f4, float f5, long j, int i, boolean z) {
        this(f, f2, f3);
        this.tilt = f4;
        this.orientation = f5;
        this.downTime = j;
        this.action = i;
        this.isHistory = z;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TouchPoint)) {
            return false;
        }
        TouchPoint touchPoint = (TouchPoint) obj;
        return Math.abs(touchPoint.x - this.x) <= 1.0E-5f && Math.abs(touchPoint.y - this.y) <= 1.0E-5f;
    }
}
