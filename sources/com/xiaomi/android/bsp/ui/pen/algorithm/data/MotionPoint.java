package com.xiaomi.android.bsp.ui.pen.algorithm.data;

/* JADX INFO: loaded from: classes4.dex */
public class MotionPoint {
    public int action;
    public boolean isHistory;
    public float orientation;
    public float pressure;
    public long t;
    public float tilt;
    public double x;
    public double y;

    public MotionPoint(double d, double d2, long j, int i) {
        this.x = d;
        this.y = d2;
        this.t = j;
        this.action = i;
    }

    public MotionPoint(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public MotionPoint(double d, double d2, long j, float f, float f2, float f3, boolean z, int i) {
        this.x = d;
        this.y = d2;
        this.t = j;
        this.pressure = f;
        this.orientation = f2;
        this.tilt = f3;
        this.isHistory = z;
        this.action = i;
    }
}
