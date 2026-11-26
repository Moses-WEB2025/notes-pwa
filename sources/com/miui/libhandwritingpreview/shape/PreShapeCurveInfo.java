package com.miui.libhandwritingpreview.shape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Pair;
import com.miui.libhandwritingpreview.PreCurveInfo;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import xc.engine.data.pagedata.DataShape;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PreShapeCurveInfo extends PreCurveInfo {
    private static final float starMinScale = 0.03f;
    private static final float starRoundScale = 1.03f;
    private static final float starValueCos1 = 0.809017f;
    private static final float starValueCos2 = 0.309017f;
    private static final float starValueSin1 = 0.58778524f;
    private static final float starValueTan1 = 0.72654253f;
    private PointF mBasePoint;
    public final DataShape mDataCurveShape;
    private final float mDrawRatio;
    private float mRotateAngle;
    private final PointF mRoundRectValue;
    private final Path mShapePath;
    private float startValue = 1.0514622f;
    public final RectF mShapeRect = new RectF();
    private final Matrix mPathMatrix = new Matrix();
    private RectF mUpdatedShapeRect = new RectF();
    private final float[] mStarFixedPoint = new float[10];
    private final float[] mStarActivePoint = new float[10];
    private final float[] mStarCentroidPoint = new float[2];
    private final float oneRadians = (float) Math.toRadians(1.0d);

    public PreShapeCurveInfo(PenPreEngine penPreEngine, DataShape dataShape, boolean z, boolean z2) {
        enableScale(z);
        this.mDrawRatio = getDrawRatio(penPreEngine.getCanvasOperator());
        this.mShapePath = getShapePath(dataShape);
        this.mRoundRectValue = dataShape.getFilletPointF();
        this.mBasePoint = getCurvePoint(penPreEngine.getCanvasOperator());
        this.mDataCurveShape = dataShape;
        this.mRotateAngle = dataShape.getRotateAngle();
    }

    public void optimizeAndTransformShapePath() {
        synchronized (this.mDataCurveShape) {
            initShapeRect(this.mDataCurveShape.mAnchorPoint4);
            prepareShapePath(false);
            this.mPathMatrix.reset();
            this.mPathMatrix.setRotate(this.mRotateAngle, this.mShapeRect.centerX(), this.mShapeRect.centerY());
            this.mShapePath.transform(this.mPathMatrix);
            if (!this.mEnableScale) {
                this.mDataCurveShape.mDrawRatio = this.mDrawRatio;
            }
        }
    }

    public void a(float[] fArr) {
        initShapeRect(fArr);
        prepareShapePath(true);
        this.mDataCurveShape.calculateBoundingRect();
    }

    private static RectF adjustShapeRect(RectF rectF) {
        RectF rectF2 = new RectF(rectF);
        rectF2.sort();
        return rectF2;
    }

    private void prepareShapePath(boolean z) {
        Pair pair;
        float f;
        float f2;
        Pair pair2;
        Pair pair3;
        if (((float) Math.sqrt(Math.pow(this.mShapeRect.right - this.mShapeRect.left, 2.0d) + Math.pow(this.mShapeRect.bottom - this.mShapeRect.top, 2.0d))) < 12.0f) {
            return;
        }
        switch (this.mDataCurveShape.mShapeProp.getShapeType()) {
            case 1:
                this.mShapePath.reset();
                this.mShapePath.moveTo(this.mShapeRect.left, this.mShapeRect.top);
                this.mShapePath.lineTo(this.mShapeRect.right, this.mShapeRect.bottom);
                break;
            case 2:
                this.mShapePath.reset();
                float penSize = this.mDataCurveShape.mShapeProp.getPenSize() * this.mDrawRatio * 1.6f;
                float penSize2 = (this.mDataCurveShape.mShapeProp.getPenSize() - 1.0f) * this.mDrawRatio;
                float f3 = this.mShapeRect.right - (1.2f * penSize);
                this.mShapePath.moveTo(f3, this.mShapeRect.bottom - penSize);
                this.mShapePath.lineTo(this.mShapeRect.right, this.mShapeRect.bottom);
                this.mShapePath.lineTo(f3, this.mShapeRect.bottom + penSize);
                this.mShapePath.lineTo(f3, this.mShapeRect.bottom + penSize);
                this.mShapePath.lineTo(f3, this.mShapeRect.bottom + penSize2);
                this.mShapePath.lineTo(this.mShapeRect.left, this.mShapeRect.top + penSize2);
                this.mShapePath.lineTo(this.mShapeRect.left, this.mShapeRect.top - penSize2);
                this.mShapePath.lineTo(f3, this.mShapeRect.bottom - penSize2);
                this.mShapePath.close();
                break;
            case 3:
                this.mShapePath.reset();
                RectF rectFAdjustShapeRect = adjustShapeRect(this.mShapeRect);
                this.mUpdatedShapeRect = rectFAdjustShapeRect;
                this.mShapePath.addRoundRect(rectFAdjustShapeRect, this.mRoundRectValue.x * this.mDrawRatio, this.mRoundRectValue.y * this.mDrawRatio, Path.Direction.CW);
                this.mShapePath.close();
                break;
            case 4:
                if (z) {
                    this.mRoundRectValue.set((Math.abs(this.mShapeRect.width()) / 2.0f) / this.mDrawRatio, this.mShapeRect.height() > 0.0f ? 0.0f : Math.abs(this.mShapeRect.height()) / this.mDrawRatio);
                }
                this.mShapePath.reset();
                if (this.mShapeRect.left < this.mShapeRect.right) {
                    pair = new Pair(Float.valueOf(this.mShapeRect.left), Float.valueOf(this.mShapeRect.right));
                } else {
                    pair = new Pair(Float.valueOf(this.mShapeRect.right), Float.valueOf(this.mShapeRect.left));
                }
                PointF pointF = new PointF();
                if (this.mShapeRect.top > this.mShapeRect.bottom) {
                    PointF pointF2 = new PointF(this.mShapeRect.centerX(), this.mShapeRect.bottom + (this.mRoundRectValue.y * this.mDrawRatio));
                    PointF pointF3 = new PointF(this.mShapeRect.centerX(), this.mShapeRect.top);
                    PointF pointF4 = new PointF(((Float) pair.second).floatValue(), this.mShapeRect.bottom);
                    PenEngineUtils.calculateRoundedCornerPoint(pointF3, pointF4, pointF2, pointF);
                    float fSqrt = (float) Math.sqrt(Math.pow(pointF2.x - pointF.x, 2.0d) + Math.pow(pointF2.y - pointF.y, 2.0d));
                    double dFloatValue = (((Float) pair.second).floatValue() - ((Float) pair.first).floatValue()) / 2.0f;
                    float degrees = (float) Math.toDegrees(Math.atan2(this.mShapeRect.top - this.mShapeRect.bottom, dFloatValue));
                    this.mShapePath.moveTo(pointF.x, pointF.y);
                    this.mShapePath.arcTo(pointF2.x - fSqrt, pointF2.y - fSqrt, pointF2.x + fSqrt, pointF2.y + fSqrt, 90.0f - degrees, degrees * 2.0f, false);
                    float fFloatValue = (float) (((double) (this.mShapeRect.top - (this.mShapeRect.bottom * (((Float) pair.second).floatValue() - ((Float) pair.first).floatValue())))) / (((Math.sqrt(Math.pow(dFloatValue / 2.0d, 2.0d) + Math.pow(this.mShapeRect.bottom - this.mShapeRect.top, 2.0d)) * 2.0d) + ((double) ((Float) pair.second).floatValue())) - ((double) ((Float) pair.first).floatValue())));
                    float f4 = ((this.mShapeRect.top - this.mShapeRect.bottom) - (this.mRoundRectValue.y * this.mDrawRatio)) / ((this.mShapeRect.top - this.mShapeRect.bottom) - fFloatValue);
                    float fFloatValue2 = ((((Float) pair.second).floatValue() - ((Float) pair.first).floatValue()) * f4) / 2.0f;
                    float f5 = f4 * fFloatValue;
                    pointF2.set(((Float) pair.first).floatValue() + fFloatValue2, this.mShapeRect.bottom + f5);
                    pointF4.set(((Float) pair.first).floatValue(), this.mShapeRect.bottom);
                    PenEngineUtils.calculateRoundedCornerPoint(pointF3, pointF4, pointF2, pointF);
                    float fSqrt2 = (float) Math.sqrt(Math.pow(pointF2.x - pointF.x, 2.0d) + Math.pow(pointF2.y - pointF.y, 2.0d));
                    this.mShapePath.lineTo(pointF.x, pointF.y);
                    float f6 = 180.0f - degrees;
                    this.mShapePath.arcTo(pointF2.x - fSqrt2, pointF2.y - fSqrt2, pointF2.x + fSqrt2, pointF2.y + fSqrt2, degrees + 90.0f, f6, false);
                    pointF2.set(((Float) pair.second).floatValue() - fFloatValue2, this.mShapeRect.bottom + f5);
                    pointF3.set(((Float) pair.second).floatValue(), this.mShapeRect.bottom);
                    PenEngineUtils.calculateRoundedCornerPoint(pointF3, pointF4, pointF2, pointF);
                    float fSqrt3 = (float) Math.sqrt(Math.pow(pointF2.x - pointF.x, 2.0d) + Math.pow(pointF2.y - pointF.y, 2.0d));
                    this.mShapePath.lineTo(pointF.x, pointF.y);
                    this.mShapePath.arcTo(pointF2.x - fSqrt3, pointF2.y - fSqrt3, pointF2.x + fSqrt3, pointF2.y + fSqrt3, -90.0f, f6, false);
                } else {
                    PointF pointF5 = new PointF(this.mShapeRect.centerX(), this.mShapeRect.top + (this.mRoundRectValue.y * this.mDrawRatio));
                    PointF pointF6 = new PointF(this.mShapeRect.centerX(), this.mShapeRect.top);
                    PointF pointF7 = new PointF(((Float) pair.first).floatValue(), this.mShapeRect.bottom);
                    PenEngineUtils.calculateRoundedCornerPoint(pointF6, pointF7, pointF5, pointF);
                    float fSqrt4 = (float) Math.sqrt(Math.pow(pointF5.x - pointF.x, 2.0d) + Math.pow(pointF5.y - pointF.y, 2.0d));
                    double dFloatValue2 = (((Float) pair.second).floatValue() - ((Float) pair.first).floatValue()) / 2.0f;
                    float degrees2 = (float) Math.toDegrees(Math.atan2(this.mShapeRect.bottom - this.mShapeRect.top, dFloatValue2));
                    this.mShapePath.moveTo(pointF.x, pointF.y);
                    this.mShapePath.arcTo(pointF5.x - fSqrt4, pointF5.y - fSqrt4, pointF5.x + fSqrt4, pointF5.y + fSqrt4, -(degrees2 + 90.0f), degrees2 * 2.0f, false);
                    float fFloatValue3 = (float) (((double) ((this.mShapeRect.bottom - this.mShapeRect.top) * (((Float) pair.second).floatValue() - ((Float) pair.first).floatValue()))) / (((Math.sqrt(Math.pow(dFloatValue2, 2.0d) + Math.pow(this.mShapeRect.bottom - this.mShapeRect.top, 2.0d)) * 2.0d) + ((double) ((Float) pair.second).floatValue())) - ((double) ((Float) pair.first).floatValue())));
                    float f7 = (this.mRoundRectValue.y * this.mDrawRatio) / ((this.mShapeRect.bottom - this.mShapeRect.top) - fFloatValue3);
                    float fFloatValue4 = ((((Float) pair.second).floatValue() - ((Float) pair.first).floatValue()) * f7) / 2.0f;
                    float f8 = f7 * fFloatValue3;
                    pointF5.set(((Float) pair.second).floatValue() - fFloatValue4, this.mShapeRect.bottom - f8);
                    pointF7.set(((Float) pair.second).floatValue(), this.mShapeRect.bottom);
                    PenEngineUtils.calculateRoundedCornerPoint(pointF6, pointF7, pointF5, pointF);
                    float fSqrt5 = (float) Math.sqrt(Math.pow(pointF5.x - pointF.x, 2.0d) + Math.pow(pointF5.y - pointF.y, 2.0d));
                    this.mShapePath.lineTo(pointF.x, pointF.y);
                    float f9 = 180.0f - degrees2;
                    this.mShapePath.arcTo(pointF5.x - fSqrt5, pointF5.y - fSqrt5, pointF5.x + fSqrt5, pointF5.y + fSqrt5, degrees2 - 90.0f, f9, false);
                    pointF5.set(((Float) pair.first).floatValue() + fFloatValue4, this.mShapeRect.bottom - f8);
                    pointF6.set(((Float) pair.first).floatValue(), this.mShapeRect.bottom);
                    PenEngineUtils.calculateRoundedCornerPoint(pointF6, pointF7, pointF5, pointF);
                    float fSqrt6 = (float) Math.sqrt(Math.pow(pointF5.x - pointF.x, 2.0d) + Math.pow(pointF5.y - pointF.y, 2.0d));
                    this.mShapePath.lineTo(pointF.x, pointF.y);
                    this.mShapePath.arcTo(pointF5.x - fSqrt6, pointF5.y - fSqrt6, pointF5.x + fSqrt6, pointF5.y + fSqrt6, 90.0f, f9, false);
                }
                this.mShapePath.close();
                break;
            case 5:
                this.mShapePath.reset();
                RectF rectFAdjustShapeRect2 = adjustShapeRect(this.mShapeRect);
                this.mUpdatedShapeRect = rectFAdjustShapeRect2;
                this.mShapePath.addOval(rectFAdjustShapeRect2, Path.Direction.CW);
                break;
            case 6:
                this.mShapePath.reset();
                RectF rectFAdjustShapeRect3 = adjustShapeRect(this.mShapeRect);
                this.mUpdatedShapeRect = rectFAdjustShapeRect3;
                float f10 = rectFAdjustShapeRect3.right - this.mUpdatedShapeRect.left;
                float f11 = this.mUpdatedShapeRect.bottom - this.mUpdatedShapeRect.top;
                float f12 = this.mRoundRectValue.x * this.mDrawRatio;
                float f13 = this.mRoundRectValue.y * this.mDrawRatio;
                float f14 = this.startValue;
                if (f10 > f14 * f11) {
                    RectF rectF = this.mUpdatedShapeRect;
                    rectF.right = (f11 * f14) + rectF.left;
                    float f15 = this.mUpdatedShapeRect.right - this.mUpdatedShapeRect.left;
                    float f16 = f15 / f10;
                    f12 *= f16;
                    f2 = f16;
                    f10 = f15;
                    f = 1.0f;
                } else {
                    RectF rectF2 = this.mUpdatedShapeRect;
                    rectF2.bottom = (f10 / f14) + rectF2.top;
                    f = (this.mUpdatedShapeRect.bottom - this.mUpdatedShapeRect.top) / f11;
                    f13 *= f;
                    f2 = 1.0f;
                }
                this.mStarFixedPoint[0] = (this.mUpdatedShapeRect.left + this.mUpdatedShapeRect.right) / 2.0f;
                this.mStarFixedPoint[1] = this.mUpdatedShapeRect.top;
                this.mStarFixedPoint[2] = this.mUpdatedShapeRect.left;
                float[] fArr = this.mStarFixedPoint;
                float f17 = this.mUpdatedShapeRect.top;
                float f18 = 0.0f;
                double d = f10 * starValueTan1;
                int i = 0;
                float f19 = (float) (d / 2.0d);
                fArr[3] = f17 + f19;
                float[] fArr2 = this.mStarFixedPoint;
                float f20 = this.mUpdatedShapeRect.left;
                float f21 = (float) (((double) (((f10 * starValueCos2) * starValueTan1) / starValueSin1)) / 2.0d);
                char c = 4;
                fArr2[4] = f20 + f21;
                this.mStarFixedPoint[5] = this.mUpdatedShapeRect.bottom;
                this.mStarFixedPoint[6] = this.mUpdatedShapeRect.right - f21;
                this.mStarFixedPoint[7] = this.mUpdatedShapeRect.bottom;
                this.mStarFixedPoint[8] = this.mUpdatedShapeRect.right;
                this.mStarFixedPoint[9] = this.mUpdatedShapeRect.top + f19;
                int i2 = 0;
                float f22 = 0.0f;
                while (true) {
                    float[] fArr3 = this.mStarFixedPoint;
                    int i3 = i;
                    if (i2 < fArr3.length) {
                        if (i2 % 2 == 0) {
                            f18 += fArr3[i2];
                        } else {
                            f22 += fArr3[i2];
                        }
                        i2++;
                        i = i3;
                    } else {
                        float length = f18 / (fArr3.length / 2.0f);
                        float length2 = f22 / (fArr3.length / 2.0f);
                        float[] fArr4 = this.mStarCentroidPoint;
                        fArr4[i3] = length;
                        fArr4[1] = length2;
                        if (z) {
                            this.mStarActivePoint[i3] = this.mUpdatedShapeRect.left + ((float) (d / 1.9021130250138185d));
                            this.mStarActivePoint[1] = this.mUpdatedShapeRect.top + f19;
                        } else {
                            if (Math.abs((length - this.mUpdatedShapeRect.left) - f12) <= f12 * starMinScale) {
                                f12 = (this.mStarCentroidPoint[i3] - this.mUpdatedShapeRect.left) * starRoundScale;
                            }
                            if (Math.abs((this.mStarCentroidPoint[1] - this.mUpdatedShapeRect.top) - f13) <= starMinScale * f13) {
                                f13 = (this.mStarCentroidPoint[1] - this.mUpdatedShapeRect.top) * starRoundScale;
                            }
                            this.mStarActivePoint[i3] = this.mUpdatedShapeRect.left + f12;
                            this.mStarActivePoint[1] = this.mUpdatedShapeRect.top + f13;
                        }
                        float[] fArr5 = this.mStarActivePoint;
                        float f23 = fArr5[i3];
                        float[] fArr6 = this.mStarCentroidPoint;
                        float f24 = fArr6[i3];
                        float f25 = (f23 - f24) * (f23 - f24);
                        float f26 = fArr5[1];
                        float f27 = fArr6[1];
                        float fSqrt7 = (float) Math.sqrt(f25 + ((f26 - f27) * (f26 - f27)));
                        int i4 = i3;
                        while (true) {
                            float f28 = i4;
                            float[] fArr7 = this.mStarFixedPoint;
                            if (f28 < fArr7.length / 2.0f) {
                                float length3 = (f28 + 1.75f) * (360.0f / (fArr7.length / 2.0f));
                                int i5 = i4 * 2;
                                char c2 = c;
                                double d2 = fSqrt7;
                                this.mStarActivePoint[i5] = this.mStarCentroidPoint[i3] - ((float) (Math.cos(this.oneRadians * length3) * d2));
                                this.mStarActivePoint[i5 + 1] = this.mStarCentroidPoint[1] - ((float) (d2 * Math.sin(this.oneRadians * length3)));
                                i4++;
                                c = c2;
                                f = f;
                            } else {
                                char c3 = c;
                                float f29 = f;
                                this.mPathMatrix.reset();
                                if (f2 != 1.0f) {
                                    this.mPathMatrix.postScale(1.0f / f2, 1.0f / f29, this.mUpdatedShapeRect.left, (this.mUpdatedShapeRect.top + this.mUpdatedShapeRect.bottom) / 2.0f);
                                    this.mPathMatrix.mapPoints(this.mStarFixedPoint);
                                    this.mPathMatrix.mapPoints(this.mStarActivePoint);
                                } else if (f29 != 1.0f) {
                                    this.mPathMatrix.postScale(1.0f / f2, 1.0f / f29, (this.mUpdatedShapeRect.left + this.mUpdatedShapeRect.right) / 2.0f, this.mUpdatedShapeRect.top);
                                    this.mPathMatrix.mapPoints(this.mStarFixedPoint);
                                    this.mPathMatrix.mapPoints(this.mStarActivePoint);
                                }
                                if (z) {
                                    this.mRoundRectValue.set((this.mStarActivePoint[i3] - this.mUpdatedShapeRect.left) / this.mDrawRatio, (this.mStarActivePoint[1] - this.mUpdatedShapeRect.top) / this.mDrawRatio);
                                }
                                Path path = this.mShapePath;
                                float[] fArr8 = this.mStarFixedPoint;
                                path.moveTo(fArr8[i3], fArr8[1]);
                                Path path2 = this.mShapePath;
                                float[] fArr9 = this.mStarActivePoint;
                                path2.lineTo(fArr9[8], fArr9[9]);
                                Path path3 = this.mShapePath;
                                float[] fArr10 = this.mStarFixedPoint;
                                path3.lineTo(fArr10[2], fArr10[3]);
                                Path path4 = this.mShapePath;
                                float[] fArr11 = this.mStarActivePoint;
                                path4.lineTo(fArr11[6], fArr11[7]);
                                Path path5 = this.mShapePath;
                                float[] fArr12 = this.mStarFixedPoint;
                                path5.lineTo(fArr12[c3], fArr12[5]);
                                Path path6 = this.mShapePath;
                                float[] fArr13 = this.mStarActivePoint;
                                path6.lineTo(fArr13[c3], fArr13[5]);
                                Path path7 = this.mShapePath;
                                float[] fArr14 = this.mStarFixedPoint;
                                path7.lineTo(fArr14[6], fArr14[7]);
                                Path path8 = this.mShapePath;
                                float[] fArr15 = this.mStarActivePoint;
                                path8.lineTo(fArr15[2], fArr15[3]);
                                Path path9 = this.mShapePath;
                                float[] fArr16 = this.mStarFixedPoint;
                                path9.lineTo(fArr16[8], fArr16[9]);
                                Path path10 = this.mShapePath;
                                float[] fArr17 = this.mStarActivePoint;
                                path10.lineTo(fArr17[i3], fArr17[1]);
                                this.mShapePath.close();
                            }
                        }
                    }
                    break;
                }
                break;
            case 7:
                if (z) {
                    this.mRoundRectValue.set(((Math.abs(this.mShapeRect.width()) / 2.0f) / this.mDrawRatio) * 1.1f, (Math.abs(this.mShapeRect.height()) / 4.0f) / this.mDrawRatio);
                }
                this.mShapePath.reset();
                PointF pointF8 = new PointF(this.mRoundRectValue.x * this.mDrawRatio, this.mRoundRectValue.y * this.mDrawRatio);
                if (this.mShapeRect.top < this.mShapeRect.bottom) {
                    pair2 = new Pair(Float.valueOf(this.mShapeRect.top), Float.valueOf(this.mShapeRect.bottom));
                } else {
                    pair2 = new Pair(Float.valueOf(this.mShapeRect.bottom), Float.valueOf(this.mShapeRect.top));
                }
                if (this.mShapeRect.left < this.mShapeRect.right) {
                    pair3 = new Pair(Float.valueOf(this.mShapeRect.left), Float.valueOf(this.mShapeRect.right));
                } else {
                    pair3 = new Pair(Float.valueOf(this.mShapeRect.right), Float.valueOf(this.mShapeRect.left));
                }
                this.mShapePath.moveTo(this.mShapeRect.right, this.mShapeRect.centerY());
                this.mShapePath.lineTo(((Float) pair3.first).floatValue() + pointF8.x, ((Float) pair2.first).floatValue());
                this.mShapePath.lineTo(((Float) pair3.first).floatValue() + pointF8.x, ((Float) pair2.first).floatValue() + pointF8.y);
                this.mShapePath.lineTo(this.mShapeRect.left, ((Float) pair2.first).floatValue() + pointF8.y);
                this.mShapePath.lineTo(this.mShapeRect.left, ((Float) pair2.second).floatValue() - pointF8.y);
                this.mShapePath.lineTo(((Float) pair3.first).floatValue() + pointF8.x, ((Float) pair2.second).floatValue() - pointF8.y);
                this.mShapePath.lineTo(((Float) pair3.first).floatValue() + pointF8.x, ((Float) pair2.second).floatValue());
                this.mShapePath.close();
                break;
        }
    }

    public void initShapeRect(float[] fArr) {
        this.mRotateAngle = this.mDataCurveShape.mRotateDegrees;
        this.mShapePath.reset();
        if (fArr.length == 4) {
            RectF rectF = this.mShapeRect;
            float f = fArr[0];
            float f2 = this.mDrawRatio;
            rectF.set(f * f2, fArr[1] * f2, fArr[2] * f2, fArr[3] * f2);
        }
    }
}
