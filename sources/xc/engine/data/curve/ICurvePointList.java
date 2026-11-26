package xc.engine.data.curve;

/* JADX INFO: loaded from: classes6.dex */
public interface ICurvePointList {
    ICurvePoint get(int i);

    float getOrientation(int i);

    float getPressure(int i);

    float getRotateAngle(int i);

    short getState(int i);

    float getTilt(int i);

    int getTime(int i);

    float getVelocity(int i);

    float getX(int i);

    float getY(int i);

    void setNativeX(float[] fArr, int i, int i2);

    void setNativeY(float[] fArr, int i, int i2);

    void setRotateAngle(int i, float f);

    void setState(int i, short s);

    void setX(int i, float f);

    void setY(int i, float f);

    int size();
}
