package xc.engine.data.pagedata;

import xc.engine.core.graphics.pen.PenType;

/* JADX INFO: loaded from: classes6.dex */
public interface ICurve extends IData {
    int getAlpha();

    int getCurveColor();

    int getCurveColorWheel();

    float getCurvePenWidth();

    PenType getPenType();

    float getRotateAngle();

    float getSensitivity();

    short getShapeType();

    float getTipSize();
}
