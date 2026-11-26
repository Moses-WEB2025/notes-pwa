package com.airbnb.lottie.model.content;

import android.graphics.Paint;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.StrokeContent;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ShapeStroke implements ContentModel {
    private final LineCapType capType;
    private final AnimatableColorValue color;
    private final boolean hidden;
    private final LineJoinType joinType;
    private final List<AnimatableFloatValue> lineDashPattern;
    private final float miterLimit;
    private final String name;
    private final AnimatableFloatValue offset;
    private final AnimatableIntegerValue opacity;
    private final AnimatableFloatValue width;

    public enum LineCapType {
        BUTT,
        ROUND,
        UNKNOWN;

        public Paint.Cap toPaintCap() {
            int iOrdinal = ordinal();
            if (iOrdinal == 0) {
                return Paint.Cap.BUTT;
            }
            if (iOrdinal == 1) {
                return Paint.Cap.ROUND;
            }
            return Paint.Cap.SQUARE;
        }
    }

    public enum LineJoinType {
        MITER,
        ROUND,
        BEVEL;

        public Paint.Join toPaintJoin() {
            int iOrdinal = ordinal();
            if (iOrdinal == 0) {
                return Paint.Join.MITER;
            }
            if (iOrdinal == 1) {
                return Paint.Join.ROUND;
            }
            if (iOrdinal != 2) {
                return null;
            }
            return Paint.Join.BEVEL;
        }
    }

    public ShapeStroke(String str, AnimatableFloatValue animatableFloatValue, List<AnimatableFloatValue> list, AnimatableColorValue animatableColorValue, AnimatableIntegerValue animatableIntegerValue, AnimatableFloatValue animatableFloatValue2, LineCapType lineCapType, LineJoinType lineJoinType, float f, boolean z) {
        this.name = str;
        this.offset = animatableFloatValue;
        this.lineDashPattern = list;
        this.color = animatableColorValue;
        this.opacity = animatableIntegerValue;
        this.width = animatableFloatValue2;
        this.capType = lineCapType;
        this.joinType = lineJoinType;
        this.miterLimit = f;
        this.hidden = z;
    }

    @Override // com.airbnb.lottie.model.content.ContentModel
    public Content toContent(LottieDrawable lottieDrawable, LottieComposition lottieComposition, BaseLayer baseLayer) {
        return new StrokeContent(lottieDrawable, baseLayer, this);
    }

    public String getName() {
        return this.name;
    }

    public AnimatableColorValue getColor() {
        return this.color;
    }

    public AnimatableIntegerValue getOpacity() {
        return this.opacity;
    }

    public AnimatableFloatValue getWidth() {
        return this.width;
    }

    public List<AnimatableFloatValue> getLineDashPattern() {
        return this.lineDashPattern;
    }

    public AnimatableFloatValue getDashOffset() {
        return this.offset;
    }

    public LineCapType getCapType() {
        return this.capType;
    }

    public LineJoinType getJoinType() {
        return this.joinType;
    }

    public float getMiterLimit() {
        return this.miterLimit;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}
