package xc.engine.data.curve;

/* JADX INFO: loaded from: classes6.dex */
public class CurvePoint implements ICurvePoint {
    private int index;
    private ICurvePointList list;

    public CurvePoint(ICurvePointList iCurvePointList, int i) {
        this.list = iCurvePointList;
        this.index = i;
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public int getIndex() {
        return this.index;
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public float getX() {
        return this.list.getX(this.index);
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public float getY() {
        return this.list.getY(this.index);
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public float getPressure() {
        return this.list.getPressure(this.index);
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public float getTilt() {
        return this.list.getTilt(this.index);
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public float getOrientation() {
        return this.list.getOrientation(this.index);
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public float getV() {
        return this.list.getVelocity(this.index);
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public int getTime() {
        return this.list.getTime(this.index);
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public short getState() {
        return this.list.getState(this.index);
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public float getRotateAngle() {
        return this.list.getRotateAngle(this.index);
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public void setRotateAngle(float f) {
        this.list.setRotateAngle(this.index, f);
    }

    @Override // xc.engine.data.curve.ICurvePoint
    public void setState(short s) {
        this.list.setState(this.index, s);
    }
}
