package xc.engine.operate.touch;

/* JADX INFO: loaded from: classes6.dex */
public final class TouchPoint implements Cloneable {
    private float angle;
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

    public TouchPoint(float f, float f2, float f3) {
        this.orientation = 0.0f;
        this.tilt = 0.0f;
        this.isHistory = false;
        this.x = f;
        this.y = f2;
        this.pressure = f3;
    }

    public TouchPoint(float f, float f2, long j) {
        this.orientation = 0.0f;
        this.pressure = 1.0f;
        this.tilt = 0.0f;
        this.isHistory = false;
        this.x = f;
        this.y = f2;
        this.time = j;
    }

    public TouchPoint(float f, float f2, float f3, long j, float f4) {
        this.orientation = 0.0f;
        this.tilt = 0.0f;
        this.isHistory = false;
        this.x = f;
        this.y = f2;
        this.pressure = f3;
        this.time = j;
        this.velocity = f4;
    }

    public TouchPoint(float f, float f2, float f3, long j, float f4, int i) {
        this.orientation = 0.0f;
        this.tilt = 0.0f;
        this.isHistory = false;
        this.x = f;
        this.y = f2;
        this.pressure = f3;
        this.time = j;
        this.velocity = f4;
        this.postureDegree = i;
    }

    public TouchPoint clone() throws CloneNotSupportedException {
        return (TouchPoint) super.clone();
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

    public float getAngle() {
        return this.angle;
    }

    public void setAngle(float f) {
        this.angle = f;
    }

    public int getPostureDegree() {
        return this.postureDegree;
    }

    public void setPostureDegree(int i) {
        this.postureDegree = i;
    }
}
