package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePathValue;
import com.airbnb.lottie.model.animatable.AnimatableScaleValue;
import com.airbnb.lottie.model.animatable.AnimatableSplitDimensionPathValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.ScaleXY;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.IOException;
import miui.cloud.CloudPushConstants;

/* JADX INFO: loaded from: classes.dex */
public class AnimatableTransformParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of(HtmlParser.LinkCardElement.TAG, CloudPushConstants.WATERMARK_TYPE.PERSONAL, CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "rz", Constants.RANDOM_LONG, "o", "so", "eo", "sk", "sa");
    private static final JsonReader.Options ANIMATABLE_NAMES = JsonReader.Options.of("k");

    private AnimatableTransformParser() {
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0066  */
    /* JADX WARN: Code duplicated, block: B:23:0x008c  */
    /* JADX WARN: Code duplicated, block: B:25:0x009b  */
    public static AnimatableTransform parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        AnimatableFloatValue animatableFloatValue;
        LottieComposition lottieComposition2 = lottieComposition;
        boolean z = jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT;
        if (z) {
            jsonReader.beginObject();
        }
        AnimatableFloatValue animatableFloatValue2 = null;
        AnimatablePathValue animatablePathValue = null;
        AnimatableValue<PointF, PointF> splitPath = null;
        AnimatableScaleValue scale = null;
        AnimatableFloatValue animatableFloatValue3 = null;
        AnimatableFloatValue animatableFloatValue4 = null;
        AnimatableIntegerValue integer = null;
        AnimatableFloatValue animatableFloatValue5 = null;
        AnimatableFloatValue animatableFloatValue6 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.selectName(ANIMATABLE_NAMES) == 0) {
                            animatablePathValue = AnimatablePathValueParser.parse(jsonReader, lottieComposition);
                        } else {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();
                    lottieComposition2 = lottieComposition;
                    break;
                case 1:
                    splitPath = AnimatablePathValueParser.parseSplitPath(jsonReader, lottieComposition);
                    lottieComposition2 = lottieComposition;
                    break;
                case 2:
                    scale = AnimatableValueParser.parseScale(jsonReader, lottieComposition);
                    lottieComposition2 = lottieComposition;
                    break;
                case 3:
                    lottieComposition2.addWarning("Lottie doesn't support 3D layers.");
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition2, false);
                    if (animatableFloatValue.getKeyframes().isEmpty()) {
                        animatableFloatValue.getKeyframes().add(new Keyframe(lottieComposition2, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(lottieComposition2.getEndFrame())));
                    } else if (((Keyframe) animatableFloatValue.getKeyframes().get(0)).startValue == 0) {
                        animatableFloatValue.getKeyframes().set(0, new Keyframe(lottieComposition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(lottieComposition.getEndFrame())));
                    }
                    lottieComposition2 = lottieComposition;
                    animatableFloatValue2 = animatableFloatValue;
                    break;
                case 4:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition2, false);
                    if (animatableFloatValue.getKeyframes().isEmpty()) {
                        animatableFloatValue.getKeyframes().add(new Keyframe(lottieComposition2, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(lottieComposition2.getEndFrame())));
                    } else if (((Keyframe) animatableFloatValue.getKeyframes().get(0)).startValue == 0) {
                        animatableFloatValue.getKeyframes().set(0, new Keyframe(lottieComposition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(lottieComposition.getEndFrame())));
                    }
                    lottieComposition2 = lottieComposition;
                    animatableFloatValue2 = animatableFloatValue;
                    break;
                case 5:
                    integer = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                    break;
                case 6:
                    animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition2, false);
                    break;
                case 7:
                    animatableFloatValue6 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition2, false);
                    break;
                case 8:
                    animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition2, false);
                    break;
                case 9:
                    animatableFloatValue4 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition2, false);
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    lottieComposition2 = lottieComposition;
                    break;
            }
        }
        if (z) {
            jsonReader.endObject();
        }
        AnimatablePathValue animatablePathValue2 = isAnchorPointIdentity(animatablePathValue) ? null : animatablePathValue;
        if (isPositionIdentity(splitPath)) {
            splitPath = null;
        }
        return new AnimatableTransform(animatablePathValue2, splitPath, isScaleIdentity(scale) ? null : scale, isRotationIdentity(animatableFloatValue2) ? null : animatableFloatValue2, integer, animatableFloatValue5, animatableFloatValue6, isSkewIdentity(animatableFloatValue3) ? null : animatableFloatValue3, isSkewAngleIdentity(animatableFloatValue4) ? null : animatableFloatValue4);
    }

    private static boolean isAnchorPointIdentity(AnimatablePathValue animatablePathValue) {
        if (animatablePathValue != null) {
            return animatablePathValue.isStatic() && animatablePathValue.getKeyframes().get(0).startValue.equals(0.0f, 0.0f);
        }
        return true;
    }

    private static boolean isPositionIdentity(AnimatableValue<PointF, PointF> animatableValue) {
        if (animatableValue != null) {
            return !(animatableValue instanceof AnimatableSplitDimensionPathValue) && animatableValue.isStatic() && animatableValue.getKeyframes().get(0).startValue.equals(0.0f, 0.0f);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isRotationIdentity(AnimatableFloatValue animatableFloatValue) {
        if (animatableFloatValue != null) {
            return animatableFloatValue.isStatic() && ((Float) ((Keyframe) animatableFloatValue.getKeyframes().get(0)).startValue).floatValue() == 0.0f;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isScaleIdentity(AnimatableScaleValue animatableScaleValue) {
        if (animatableScaleValue != null) {
            return animatableScaleValue.isStatic() && ((ScaleXY) ((Keyframe) animatableScaleValue.getKeyframes().get(0)).startValue).equals(1.0f, 1.0f);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isSkewIdentity(AnimatableFloatValue animatableFloatValue) {
        if (animatableFloatValue != null) {
            return animatableFloatValue.isStatic() && ((Float) ((Keyframe) animatableFloatValue.getKeyframes().get(0)).startValue).floatValue() == 0.0f;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isSkewAngleIdentity(AnimatableFloatValue animatableFloatValue) {
        if (animatableFloatValue != null) {
            return animatableFloatValue.isStatic() && ((Float) ((Keyframe) animatableFloatValue.getKeyframes().get(0)).startValue).floatValue() == 0.0f;
        }
        return true;
    }
}
