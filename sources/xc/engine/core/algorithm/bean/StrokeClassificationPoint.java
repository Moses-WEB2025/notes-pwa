package xc.engine.core.algorithm.bean;

/* JADX INFO: loaded from: classes6.dex */
public class StrokeClassificationPoint {
    public int action;
    public float pressure;
    public long time;
    public float x;
    public float y;

    public StrokeClassificationPoint(float f, float f2, long j, float f3, int i) {
        this.x = f;
        this.y = f2;
        this.time = j;
        this.pressure = f3;
        this.action = i;
    }
}
