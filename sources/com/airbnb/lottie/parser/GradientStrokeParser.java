package com.airbnb.lottie.parser;

import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.GradientStroke;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.value.Keyframe;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.onetrack.b.m;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import miui.cloud.CloudPushConstants;

/* JADX INFO: loaded from: classes.dex */
class GradientStrokeParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of("nm", CloudPushConstants.WATERMARK_TYPE.GLOBAL, "o", Constants.TIMESTAMP, CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "e", AnimatedProperty.PROPERTY_NAME_W, "lc", "lj", m.g, "hd", DateTokenConverter.CONVERTER_KEY);
    private static final JsonReader.Options GRADIENT_NAMES = JsonReader.Options.of(CloudPushConstants.WATERMARK_TYPE.PERSONAL, "k");
    private static final JsonReader.Options DASH_PATTERN_NAMES = JsonReader.Options.of("n", "v");

    private GradientStrokeParser() {
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:6:0x0027. Please report as an issue. */
    static GradientStroke parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        AnimatableIntegerValue animatableIntegerValue;
        ArrayList arrayList = new ArrayList();
        GradientType gradientType = null;
        String strNextString = null;
        AnimatableGradientColorValue gradientColor = null;
        AnimatablePointValue point = null;
        AnimatablePointValue point2 = null;
        AnimatableFloatValue animatableFloatValue = null;
        ShapeStroke.LineCapType lineCapType = null;
        ShapeStroke.LineJoinType lineJoinType = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        float fNextDouble = 0.0f;
        boolean zNextBoolean = false;
        AnimatableIntegerValue integer = null;
        while (jsonReader.hasNext()) {
            GradientType gradientType2 = gradientType;
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    strNextString = jsonReader.nextString();
                    gradientType = gradientType2;
                    break;
                case 1:
                    animatableIntegerValue = integer;
                    jsonReader.beginObject();
                    int iNextInt = -1;
                    while (jsonReader.hasNext()) {
                        int iSelectName = jsonReader.selectName(GRADIENT_NAMES);
                        if (iSelectName == 0) {
                            iNextInt = jsonReader.nextInt();
                        } else if (iSelectName == 1) {
                            gradientColor = AnimatableValueParser.parseGradientColor(jsonReader, lottieComposition, iNextInt);
                        } else {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();
                    gradientType = gradientType2;
                    integer = animatableIntegerValue;
                    break;
                case 2:
                    integer = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                    gradientType = gradientType2;
                    break;
                case 3:
                    animatableIntegerValue = integer;
                    gradientType = jsonReader.nextInt() == 1 ? GradientType.LINEAR : GradientType.RADIAL;
                    integer = animatableIntegerValue;
                    break;
                case 4:
                    point = AnimatableValueParser.parsePoint(jsonReader, lottieComposition);
                    gradientType = gradientType2;
                    break;
                case 5:
                    point2 = AnimatableValueParser.parsePoint(jsonReader, lottieComposition);
                    gradientType = gradientType2;
                    break;
                case 6:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
                    gradientType = gradientType2;
                    break;
                case 7:
                    animatableIntegerValue = integer;
                    lineCapType = ShapeStroke.LineCapType.values()[jsonReader.nextInt() - 1];
                    gradientType = gradientType2;
                    integer = animatableIntegerValue;
                    break;
                case 8:
                    animatableIntegerValue = integer;
                    lineJoinType = ShapeStroke.LineJoinType.values()[jsonReader.nextInt() - 1];
                    gradientType = gradientType2;
                    integer = animatableIntegerValue;
                    break;
                case 9:
                    animatableIntegerValue = integer;
                    fNextDouble = (float) jsonReader.nextDouble();
                    gradientType = gradientType2;
                    integer = animatableIntegerValue;
                    break;
                case 10:
                    zNextBoolean = jsonReader.nextBoolean();
                    gradientType = gradientType2;
                    break;
                case 11:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        String strNextString2 = null;
                        AnimatableFloatValue animatableFloatValue3 = null;
                        while (jsonReader.hasNext()) {
                            int iSelectName2 = jsonReader.selectName(DASH_PATTERN_NAMES);
                            if (iSelectName2 != 0) {
                                AnimatableIntegerValue animatableIntegerValue2 = integer;
                                if (iSelectName2 == 1) {
                                    animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
                                } else {
                                    jsonReader.skipName();
                                    jsonReader.skipValue();
                                }
                                integer = animatableIntegerValue2;
                            } else {
                                strNextString2 = jsonReader.nextString();
                            }
                        }
                        AnimatableIntegerValue animatableIntegerValue3 = integer;
                        jsonReader.endObject();
                        if (strNextString2.equals("o")) {
                            animatableFloatValue2 = animatableFloatValue3;
                        } else {
                            if (strNextString2.equals(DateTokenConverter.CONVERTER_KEY) || strNextString2.equals(CloudPushConstants.WATERMARK_TYPE.GLOBAL)) {
                                lottieComposition.setHasDashPattern(true);
                                arrayList.add(animatableFloatValue3);
                            }
                            integer = animatableIntegerValue3;
                        }
                        integer = animatableIntegerValue3;
                    }
                    animatableIntegerValue = integer;
                    jsonReader.endArray();
                    if (arrayList.size() == 1) {
                        arrayList.add((AnimatableFloatValue) arrayList.get(0));
                    }
                    gradientType = gradientType2;
                    integer = animatableIntegerValue;
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    gradientType = gradientType2;
                    break;
            }
        }
        AnimatableIntegerValue animatableIntegerValue4 = integer;
        return new GradientStroke(strNextString, gradientType, gradientColor, animatableIntegerValue4 == null ? new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100))) : animatableIntegerValue4, point, point2, animatableFloatValue, lineCapType, lineJoinType, fNextDouble, arrayList, animatableFloatValue2, zNextBoolean);
    }
}
