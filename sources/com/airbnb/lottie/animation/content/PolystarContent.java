package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PolystarContent implements PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    private static final float POLYGON_MAGIC_NUMBER = 0.25f;
    private static final float POLYSTAR_MAGIC_NUMBER = 0.47829f;
    private final boolean hidden;
    private final BaseKeyframeAnimation<?, Float> innerRadiusAnimation;
    private final BaseKeyframeAnimation<?, Float> innerRoundednessAnimation;
    private boolean isPathValid;
    private final boolean isReversed;
    private final LottieDrawable lottieDrawable;
    private final String name;
    private final BaseKeyframeAnimation<?, Float> outerRadiusAnimation;
    private final BaseKeyframeAnimation<?, Float> outerRoundednessAnimation;
    private final BaseKeyframeAnimation<?, Float> pointsAnimation;
    private final BaseKeyframeAnimation<?, PointF> positionAnimation;
    private final BaseKeyframeAnimation<?, Float> rotationAnimation;
    private final PolystarShape.Type type;
    private final Path path = new Path();
    private final Path lastSegmentPath = new Path();
    private final PathMeasure lastSegmentPathMeasure = new PathMeasure();
    private final float[] lastSegmentPosition = new float[2];
    private final CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();

    public PolystarContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, PolystarShape polystarShape) {
        this.lottieDrawable = lottieDrawable;
        this.name = polystarShape.getName();
        PolystarShape.Type type = polystarShape.getType();
        this.type = type;
        this.hidden = polystarShape.isHidden();
        this.isReversed = polystarShape.isReversed();
        FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation = polystarShape.getPoints().createAnimation();
        this.pointsAnimation = floatKeyframeAnimationCreateAnimation;
        BaseKeyframeAnimation<PointF, PointF> baseKeyframeAnimationCreateAnimation = polystarShape.getPosition().createAnimation();
        this.positionAnimation = baseKeyframeAnimationCreateAnimation;
        FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation2 = polystarShape.getRotation().createAnimation();
        this.rotationAnimation = floatKeyframeAnimationCreateAnimation2;
        FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation3 = polystarShape.getOuterRadius().createAnimation();
        this.outerRadiusAnimation = floatKeyframeAnimationCreateAnimation3;
        FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation4 = polystarShape.getOuterRoundedness().createAnimation();
        this.outerRoundednessAnimation = floatKeyframeAnimationCreateAnimation4;
        if (type == PolystarShape.Type.STAR) {
            this.innerRadiusAnimation = polystarShape.getInnerRadius().createAnimation();
            this.innerRoundednessAnimation = polystarShape.getInnerRoundedness().createAnimation();
        } else {
            this.innerRadiusAnimation = null;
            this.innerRoundednessAnimation = null;
        }
        baseLayer.addAnimation(floatKeyframeAnimationCreateAnimation);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation);
        baseLayer.addAnimation(floatKeyframeAnimationCreateAnimation2);
        baseLayer.addAnimation(floatKeyframeAnimationCreateAnimation3);
        baseLayer.addAnimation(floatKeyframeAnimationCreateAnimation4);
        if (type == PolystarShape.Type.STAR) {
            baseLayer.addAnimation(this.innerRadiusAnimation);
            baseLayer.addAnimation(this.innerRoundednessAnimation);
        }
        floatKeyframeAnimationCreateAnimation.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
        floatKeyframeAnimationCreateAnimation2.addUpdateListener(this);
        floatKeyframeAnimationCreateAnimation3.addUpdateListener(this);
        floatKeyframeAnimationCreateAnimation4.addUpdateListener(this);
        if (type == PolystarShape.Type.STAR) {
            this.innerRadiusAnimation.addUpdateListener(this);
            this.innerRoundednessAnimation.addUpdateListener(this);
        }
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        invalidate();
    }

    private void invalidate() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public void setContents(List<Content> list, List<Content> list2) {
        for (int i = 0; i < list.size(); i++) {
            Content content = list.get(i);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent = (TrimPathContent) content;
                if (trimPathContent.getType() == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    this.trimPaths.addTrimPath(trimPathContent);
                    trimPathContent.addListener(this);
                }
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public Path getPath() {
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        int i = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[this.type.ordinal()];
        if (i == 1) {
            createStarPath();
        } else if (i == 2) {
            createPolygonPath();
        }
        this.path.close();
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
    }

    /* JADX INFO: renamed from: com.airbnb.lottie.animation.content.PolystarContent$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type;

        static {
            int[] iArr = new int[PolystarShape.Type.values().length];
            $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type = iArr;
            try {
                iArr[PolystarShape.Type.STAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[PolystarShape.Type.POLYGON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public String getName() {
        return this.name;
    }

    private void createStarPath() {
        float f;
        float f2;
        float fCos;
        float fSin;
        float f3;
        double d;
        float f4;
        float f5;
        float f6;
        float fFloatValue = this.pointsAnimation.getValue().floatValue();
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation = this.rotationAnimation;
        double radians = Math.toRadians((baseKeyframeAnimation == null ? 0.0d : baseKeyframeAnimation.getValue().floatValue()) - 90.0d);
        double d2 = fFloatValue;
        float f7 = (float) (6.283185307179586d / d2);
        if (this.isReversed) {
            f7 *= -1.0f;
        }
        float f8 = f7 / 2.0f;
        float f9 = fFloatValue - ((int) fFloatValue);
        if (f9 != 0.0f) {
            radians += (double) ((1.0f - f9) * f8);
        }
        float fFloatValue2 = this.outerRadiusAnimation.getValue().floatValue();
        float fFloatValue3 = this.innerRadiusAnimation.getValue().floatValue();
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2 = this.innerRoundednessAnimation;
        float fFloatValue4 = baseKeyframeAnimation2 != null ? baseKeyframeAnimation2.getValue().floatValue() / 100.0f : 0.0f;
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation3 = this.outerRoundednessAnimation;
        float fFloatValue5 = baseKeyframeAnimation3 != null ? baseKeyframeAnimation3.getValue().floatValue() / 100.0f : 0.0f;
        if (f9 != 0.0f) {
            f4 = ((fFloatValue2 - fFloatValue3) * f9) + fFloatValue3;
            f2 = 0.0f;
            double d3 = f4;
            f = 2.0f;
            float fCos2 = (float) (d3 * Math.cos(radians));
            fSin = (float) (d3 * Math.sin(radians));
            this.path.moveTo(fCos2, fSin);
            d = radians + ((double) ((f7 * f9) / 2.0f));
            fCos = fCos2;
            f3 = f8;
        } else {
            f = 2.0f;
            f2 = 0.0f;
            double d4 = fFloatValue2;
            fCos = (float) (Math.cos(radians) * d4);
            fSin = (float) (d4 * Math.sin(radians));
            this.path.moveTo(fCos, fSin);
            f3 = f8;
            d = radians + ((double) f3);
            f4 = 0.0f;
        }
        double dCeil = Math.ceil(d2) * 2.0d;
        int i = 0;
        boolean z = false;
        double d5 = d;
        float f10 = fSin;
        float f11 = fCos;
        double d6 = d5;
        while (true) {
            double d7 = i;
            if (d7 < dCeil) {
                float f12 = z ? fFloatValue2 : fFloatValue3;
                float f13 = (f4 == f2 || d7 != dCeil - 2.0d) ? f3 : (f7 * f9) / f;
                double d8 = (f4 == f2 || d7 != dCeil - 1.0d) ? f12 : f4;
                float fCos3 = (float) (d8 * Math.cos(d6));
                float f14 = f7;
                float fSin2 = (float) (d8 * Math.sin(d6));
                if (fFloatValue4 == f2 && fFloatValue5 == f2) {
                    this.path.lineTo(fCos3, fSin2);
                    f6 = fCos3;
                    f5 = fSin2;
                } else {
                    double dAtan2 = (float) (Math.atan2(f10, f11) - 1.5707963267948966d);
                    float fCos4 = (float) Math.cos(dAtan2);
                    float fSin3 = (float) Math.sin(dAtan2);
                    float f15 = f11;
                    float f16 = f10;
                    f5 = fSin2;
                    double dAtan3 = (float) (Math.atan2(fSin2, fCos3) - 1.5707963267948966d);
                    float fCos5 = (float) Math.cos(dAtan3);
                    float fSin4 = (float) Math.sin(dAtan3);
                    float f17 = z ? fFloatValue4 : fFloatValue5;
                    float f18 = z ? fFloatValue5 : fFloatValue4;
                    float f19 = z ? fFloatValue3 : fFloatValue2;
                    float f20 = z ? fFloatValue2 : fFloatValue3;
                    float f21 = f19 * f17 * POLYSTAR_MAGIC_NUMBER;
                    float f22 = fCos4 * f21;
                    float f23 = f21 * fSin3;
                    float f24 = f20 * f18 * POLYSTAR_MAGIC_NUMBER;
                    float f25 = fCos5 * f24;
                    float f26 = f24 * fSin4;
                    if (f9 != 0.0f) {
                        if (i == 0) {
                            f22 *= f9;
                            f23 *= f9;
                        } else if (d7 == dCeil - 1.0d) {
                            f25 *= f9;
                            f26 *= f9;
                        }
                    }
                    f6 = fCos3;
                    this.path.cubicTo(f15 - f22, f16 - f23, fCos3 + f25, f5 + f26, f6, f5);
                }
                d6 += (double) f13;
                z = !z;
                i++;
                f3 = f3;
                f11 = f6;
                f10 = f5;
                f7 = f14;
            } else {
                PointF value = this.positionAnimation.getValue();
                this.path.offset(value.x, value.y);
                this.path.close();
                return;
            }
        }
    }

    private void createPolygonPath() {
        double d;
        float f;
        float f2;
        float f3;
        int iFloor = (int) Math.floor(this.pointsAnimation.getValue().floatValue());
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation = this.rotationAnimation;
        double radians = Math.toRadians((baseKeyframeAnimation == null ? 0.0d : baseKeyframeAnimation.getValue().floatValue()) - 90.0d);
        double d2 = iFloor;
        float fFloatValue = this.outerRoundednessAnimation.getValue().floatValue() / 100.0f;
        float fFloatValue2 = this.outerRadiusAnimation.getValue().floatValue();
        double d3 = fFloatValue2;
        float fCos = (float) (Math.cos(radians) * d3);
        float fSin = (float) (Math.sin(radians) * d3);
        this.path.moveTo(fCos, fSin);
        double d4 = (float) (6.283185307179586d / d2);
        double dCeil = Math.ceil(d2);
        double d5 = radians + d4;
        int i = 0;
        while (true) {
            double d6 = i;
            if (d6 < dCeil) {
                float fCos2 = (float) (d3 * Math.cos(d5));
                float fSin2 = (float) (Math.sin(d5) * d3);
                if (fFloatValue != 0.0f) {
                    d = dCeil;
                    f = fFloatValue;
                    double dAtan2 = (float) (Math.atan2(fSin, fCos) - 1.5707963267948966d);
                    float fCos3 = (float) Math.cos(dAtan2);
                    float fSin3 = (float) Math.sin(dAtan2);
                    double dAtan3 = (float) (Math.atan2(fSin2, fCos2) - 1.5707963267948966d);
                    float fCos4 = (float) Math.cos(dAtan3);
                    float fSin4 = (float) Math.sin(dAtan3);
                    float f4 = fFloatValue2 * f * POLYGON_MAGIC_NUMBER;
                    float f5 = f4 * fCos3;
                    float f6 = f4 * fSin3;
                    float f7 = fCos4 * f4;
                    float f8 = f4 * fSin4;
                    if (d6 == d - 1.0d) {
                        this.lastSegmentPath.reset();
                        this.lastSegmentPath.moveTo(fCos, fSin);
                        float f9 = fCos - f5;
                        float f10 = fSin - f6;
                        float f11 = fCos2 + f7;
                        float f12 = fSin2 + f8;
                        f2 = fCos2;
                        f3 = fSin2;
                        this.lastSegmentPath.cubicTo(f9, f10, f11, f12, f2, f3);
                        this.lastSegmentPathMeasure.setPath(this.lastSegmentPath, false);
                        PathMeasure pathMeasure = this.lastSegmentPathMeasure;
                        pathMeasure.getPosTan(pathMeasure.getLength() * 0.9999f, this.lastSegmentPosition, null);
                        Path path = this.path;
                        float[] fArr = this.lastSegmentPosition;
                        path.cubicTo(f9, f10, f11, f12, fArr[0], fArr[1]);
                    } else {
                        f2 = fCos2;
                        f3 = fSin2;
                        this.path.cubicTo(fCos - f5, fSin - f6, f2 + f7, f3 + f8, f2, f3);
                    }
                    fCos = f2;
                    fSin = f3;
                } else {
                    fCos = fCos2;
                    fSin = fSin2;
                    d = dCeil;
                    f = fFloatValue;
                    if (d6 != d - 1.0d) {
                        this.path.lineTo(fCos, fSin);
                    }
                    i++;
                    dCeil = d;
                    fFloatValue = f;
                }
                d5 += d4;
                i++;
                dCeil = d;
                fFloatValue = f;
            } else {
                PointF value = this.positionAnimation.getValue();
                this.path.offset(value.x, value.y);
                this.path.close();
                return;
            }
        }
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation;
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2;
        if (t == LottieProperty.POLYSTAR_POINTS) {
            this.pointsAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (t == LottieProperty.POLYSTAR_ROTATION) {
            this.rotationAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (t == LottieProperty.POSITION) {
            this.positionAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (t == LottieProperty.POLYSTAR_INNER_RADIUS && (baseKeyframeAnimation2 = this.innerRadiusAnimation) != null) {
            baseKeyframeAnimation2.setValueCallback(lottieValueCallback);
            return;
        }
        if (t == LottieProperty.POLYSTAR_OUTER_RADIUS) {
            this.outerRadiusAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (t == LottieProperty.POLYSTAR_INNER_ROUNDEDNESS && (baseKeyframeAnimation = this.innerRoundednessAnimation) != null) {
            baseKeyframeAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_OUTER_ROUNDEDNESS) {
            this.outerRoundednessAnimation.setValueCallback(lottieValueCallback);
        }
    }
}
