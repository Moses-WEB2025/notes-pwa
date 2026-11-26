package xc.engine.data.curve;

/* JADX INFO: loaded from: classes6.dex */
public interface ICurvePoint {
    int getIndex();

    float getOrientation();

    float getPressure();

    float getRotateAngle();

    short getState();

    float getTilt();

    int getTime();

    float getV();

    float getX();

    float getY();

    void setRotateAngle(float f);

    void setState(short s);
}
