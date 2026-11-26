package xc.engine.data.curve;

/* JADX INFO: loaded from: classes6.dex */
public class CurvePointList implements ICurvePointList {
    private short[] dataState;
    private long id;
    private short[] orientation;
    private short[] pressure;
    private float[] rotateAngle;
    private int size;
    private short[] tilt;
    private int[] time;
    private short[] velocity;
    private float[] x;
    private float[] y;

    public CurvePointList(long j) {
        this.id = j;
        int size = CurvePointListOperator.size(j);
        this.size = size;
        if (size != 0) {
            float[] fArr = new float[size];
            this.x = fArr;
            float[] fArr2 = new float[size];
            this.y = fArr2;
            short[] sArr = new short[size];
            this.pressure = sArr;
            short[] sArr2 = new short[size];
            this.tilt = sArr2;
            int[] iArr = new int[size];
            this.time = iArr;
            short[] sArr3 = new short[size];
            this.orientation = sArr3;
            short[] sArr4 = new short[size];
            this.velocity = sArr4;
            float[] fArr3 = new float[size];
            this.rotateAngle = fArr3;
            short[] sArr5 = new short[size];
            this.dataState = sArr5;
            CurvePointListOperator.getLine(this.id, fArr, fArr2, sArr, sArr2, iArr, sArr3, sArr4, fArr3, sArr5);
        }
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public int size() {
        return this.size;
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public ICurvePoint get(int i) {
        int i2 = this.size;
        if (i2 == 0 || i < 0 || i >= i2) {
            return null;
        }
        return new CurvePoint(this, i);
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public float getX(int i) {
        float[] fArr = this.x;
        if (fArr == null || i < 0 || i >= fArr.length) {
            return 0.0f;
        }
        return fArr[i];
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public void setX(int i, float f) {
        float[] fArr = this.x;
        if (fArr == null || i < 0 || i >= fArr.length) {
            return;
        }
        fArr[i] = f;
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public void setNativeX(float[] fArr, int i, int i2) {
        float[] fArr2;
        if (fArr == null || fArr.length == 0 || (fArr2 = this.x) == null || i < 0 || i2 > fArr2.length) {
            return;
        }
        CurvePointListOperator.setPointsX(this.id, fArr, fArr.length, i, i2);
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public float getY(int i) {
        float[] fArr = this.y;
        if (fArr == null || i < 0 || i >= fArr.length) {
            return 0.0f;
        }
        return fArr[i];
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public void setY(int i, float f) {
        float[] fArr = this.y;
        if (fArr == null || i < 0 || i >= fArr.length) {
            return;
        }
        fArr[i] = f;
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public void setNativeY(float[] fArr, int i, int i2) {
        float[] fArr2;
        if (fArr == null || fArr.length == 0 || (fArr2 = this.y) == null || i < 0 || i2 > fArr2.length) {
            return;
        }
        CurvePointListOperator.setPointsY(this.id, fArr, fArr.length, i, i2);
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public float getPressure(int i) {
        short[] sArr = this.pressure;
        if (sArr == null || i < 0 || i >= sArr.length) {
            return 0.0f;
        }
        return sArr[i] / 100.0f;
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public float getTilt(int i) {
        short[] sArr = this.tilt;
        if (sArr == null || i < 0 || i >= sArr.length) {
            return 0.0f;
        }
        return sArr[i] / 100.0f;
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public float getOrientation(int i) {
        short[] sArr = this.orientation;
        if (sArr == null || i < 0 || i >= sArr.length) {
            return 0.0f;
        }
        return sArr[i] / 100.0f;
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public float getVelocity(int i) {
        short[] sArr = this.velocity;
        if (sArr == null || i < 0 || i >= sArr.length) {
            return 0.0f;
        }
        return sArr[i] / 100.0f;
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public int getTime(int i) {
        int[] iArr = this.time;
        if (iArr == null || i < 0 || i >= iArr.length) {
            return 0;
        }
        return iArr[i];
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public float getRotateAngle(int i) {
        float[] fArr = this.rotateAngle;
        if (fArr == null || i < 0 || i >= fArr.length) {
            return 0.0f;
        }
        return fArr[i];
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public short getState(int i) {
        short[] sArr = this.dataState;
        if (sArr == null || i < 0 || i >= sArr.length) {
            return (short) 0;
        }
        return sArr[i];
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public void setState(int i, short s) {
        short[] sArr = this.dataState;
        if (sArr == null || i < 0 || i >= sArr.length) {
            return;
        }
        sArr[i] = s;
        CurvePointListOperator.setPointState(this.id, i, s);
    }

    @Override // xc.engine.data.curve.ICurvePointList
    public void setRotateAngle(int i, float f) {
        float[] fArr = this.rotateAngle;
        if (fArr == null || i < 0 || i >= fArr.length) {
            return;
        }
        fArr[i] = f;
        CurvePointListOperator.setPointsRotateAngle(this.id, fArr, i, i + 1);
    }
}
