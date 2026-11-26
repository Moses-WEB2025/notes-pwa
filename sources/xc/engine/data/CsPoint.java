package xc.engine.data;

import xc.engine.data.curve.ICurvePoint;

/* JADX INFO: loaded from: classes6.dex */
public class CsPoint implements IPoint, Cloneable {
    private byte dataState;
    private short orientation;
    private byte pressure;
    private float rotateAngle;
    private short tilt;
    private int time;
    private byte velocity;
    public float x;
    public float y;

    public CsPoint() {
    }

    public CsPoint(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public CsPoint(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.pressure = (byte) (f3 * 100.0f);
    }

    public CsPoint(float f, float f2, float f3, float f4, float f5) {
        this.x = f;
        this.y = f2;
        this.pressure = (byte) (f3 * 100.0f);
        this.tilt = (short) (f4 * 100.0f);
        this.orientation = (short) (f5 * 100.0f);
    }

    public CsPoint(float f, float f2, float f3, int i) {
        this.x = f;
        this.y = f2;
        this.pressure = (byte) (f3 * 100.0f);
    }

    public CsPoint(ICurvePoint iCurvePoint) {
        this.x = iCurvePoint.getX();
        this.y = iCurvePoint.getY();
        this.pressure = (byte) (iCurvePoint.getPressure() * 100.0f);
        this.tilt = (short) (iCurvePoint.getTilt() * 100.0f);
        this.time = iCurvePoint.getTime();
        this.orientation = (short) (iCurvePoint.getOrientation() * 100.0f);
        this.velocity = (byte) (iCurvePoint.getV() * 100.0f);
        this.rotateAngle = iCurvePoint.getRotateAngle();
        this.dataState = (byte) iCurvePoint.getState();
    }

    public CsPoint(CsPoint csPoint) {
        this.x = csPoint.x;
        this.y = csPoint.y;
        this.pressure = csPoint.pressure;
        this.tilt = csPoint.tilt;
        this.time = csPoint.time;
        this.orientation = csPoint.orientation;
        this.velocity = csPoint.velocity;
        this.rotateAngle = csPoint.rotateAngle;
        this.dataState = csPoint.dataState;
    }

    public CsPoint(float f, float f2, float f3, long j) {
        this(f, f2, f3, 0.0f, j, 0.0f, 1.0f);
    }

    public CsPoint(float f, float f2, float f3, float f4, long j, float f5, float f6) {
        this.x = f;
        this.y = f2;
        this.pressure = (byte) (f3 * 100.0f);
        this.tilt = (short) (f4 * 100.0f);
        this.time = (int) j;
        this.orientation = (short) (f5 * 100.0f);
        this.velocity = (byte) (f6 * 100.0f);
        this.dataState = (byte) 1;
    }

    public CsPoint(float f, float f2, float f3, float f4, long j, float f5, float f6, float f7) {
        this.x = f;
        this.y = f2;
        this.pressure = (byte) (f3 * 100.0f);
        this.tilt = (short) (f4 * 100.0f);
        this.time = (int) j;
        this.orientation = (short) (f5 * 100.0f);
        this.velocity = (byte) (f6 * 100.0f);
        this.rotateAngle = f7;
        this.dataState = (byte) 1;
    }

    public CsPoint clone() throws CloneNotSupportedException {
        return (CsPoint) super.clone();
    }

    @Override // xc.engine.data.IPoint
    public float getX() {
        return this.x;
    }

    public void setX(float f) {
        this.x = f;
    }

    @Override // xc.engine.data.IPoint
    public float getY() {
        return this.y;
    }

    public void setY(float f) {
        this.y = f;
    }

    @Override // xc.engine.data.IPoint
    public float getPressure() {
        return this.pressure / 100.0f;
    }

    @Override // xc.engine.data.IPoint
    public float getTilt() {
        return this.tilt / 100.0f;
    }

    @Override // xc.engine.data.IPoint
    public int getTime() {
        return this.time;
    }

    public void setTime(int i) {
        this.time = i;
    }

    @Override // xc.engine.data.IPoint
    public float getOrientation() {
        return this.orientation / 100.0f;
    }

    @Override // xc.engine.data.IPoint
    public float getRotateAngle() {
        return this.rotateAngle;
    }

    public void setRotateAngle(float f) {
        this.rotateAngle = f;
    }

    public int getDataState() {
        byte b = this.dataState;
        int i = 2;
        if (b != 2) {
            i = 3;
            if (b != 3) {
                return 1;
            }
        }
        return i;
    }

    public void setDataState(int i) {
        this.dataState = (byte) i;
    }

    public void setPressure(float f) {
        this.pressure = (byte) (f * 100.0f);
    }

    public void setTilt(float f) {
        this.tilt = (short) (f * 100.0f);
    }

    public void setOrientation(float f) {
        this.orientation = (short) (f * 100.0f);
    }

    public float getVelocity() {
        return this.velocity / 100.0f;
    }

    public void setVelocity(float f) {
        this.velocity = (byte) (f * 100.0f);
    }
}
