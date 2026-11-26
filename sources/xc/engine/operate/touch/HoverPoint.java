package xc.engine.operate.touch;

import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes6.dex */
public class HoverPoint {
    private boolean isHistory;
    private boolean isPredictPoint;
    private float orientation;
    private int postureDegree;
    private float pressure;
    private float tilt;
    private long time;
    private float velocity;
    private float x;
    private float y;

    public HoverPoint(float f, float f2) {
        this.orientation = 0.0f;
        this.pressure = 1.0f;
        this.tilt = 0.0f;
        this.isHistory = false;
        this.x = f;
        this.y = f2;
    }

    public HoverPoint(float f, float f2, float f3) {
        this.orientation = 0.0f;
        this.tilt = 0.0f;
        this.isHistory = false;
        this.x = f;
        this.y = f2;
        this.pressure = f3;
    }

    public HoverPoint(float f, float f2, float f3, int i) {
        this.orientation = 0.0f;
        this.tilt = 0.0f;
        this.isHistory = false;
        this.x = f;
        this.y = f2;
        this.pressure = f3;
        this.postureDegree = i;
    }

    public HoverPoint(float f, float f2, float f3, long j, float f4) {
        this.orientation = 0.0f;
        this.tilt = 0.0f;
        this.isHistory = false;
        this.x = f;
        this.y = f2;
        this.pressure = f3;
        this.time = j;
        this.velocity = f4;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float f) {
        this.x = f;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float f) {
        this.y = f;
    }

    public float getPressure() {
        return this.pressure;
    }

    public void setPressure(float f) {
        this.pressure = f;
    }

    public float getOrientation() {
        return this.orientation;
    }

    public void setOrientation(float f) {
        this.orientation = f;
    }

    public float getTilt() {
        return this.tilt;
    }

    public void setTilt(float f) {
        this.tilt = f;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long j) {
        this.time = j;
    }

    public boolean isHistory() {
        return this.isHistory;
    }

    public void setHistory(boolean z) {
        this.isHistory = z;
    }

    public float getVelocity() {
        return this.velocity;
    }

    public boolean isPredictPoint() {
        return this.isPredictPoint;
    }

    public void setPredictPoint(boolean z) {
        this.isPredictPoint = z;
    }

    public int getPostureDegree() {
        return this.postureDegree;
    }

    public void setPostureDegree(int i) {
        this.postureDegree = i;
    }

    public String toString() {
        return "TouchPoint{x=" + this.x + ", y=" + this.y + CoreConstants.CURLY_RIGHT;
    }
}
