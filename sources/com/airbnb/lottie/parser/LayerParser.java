package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.Rect;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.BlurEffect;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.LBlendMode;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.micloudsdk.micloudrichmedia.RequestParameters;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public class LayerParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", Constants.TIMESTAMP, "ef", "sr", RequestParameters.ST, AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "ip", "op", "tm", "cl", "hd", "ao", "bm");
    private static final JsonReader.Options TEXT_NAMES = JsonReader.Options.of(DateTokenConverter.CONVERTER_KEY, HtmlParser.LinkCardElement.TAG);
    private static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.of("ty", "nm");

    private LayerParser() {
    }

    public static Layer parse(LottieComposition lottieComposition) {
        Rect bounds = lottieComposition.getBounds();
        return new Layer(Collections.emptyList(), lottieComposition, "__container", -1L, Layer.LayerType.PRE_COMP, -1L, null, Collections.emptyList(), new AnimatableTransform(), 0, 0, 0, 0.0f, 0.0f, bounds.width(), bounds.height(), null, null, Collections.emptyList(), Layer.MatteType.NONE, null, false, null, null, LBlendMode.NORMAL);
    }

    public static Layer parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        boolean z;
        float f;
        float f2;
        Layer.MatteType matteType = Layer.MatteType.NONE;
        LBlendMode lBlendMode = LBlendMode.NORMAL;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        jsonReader.beginObject();
        boolean z2 = false;
        float f3 = 0.0f;
        Float fValueOf = Float.valueOf(0.0f);
        float fNextDouble = 1.0f;
        Float fValueOf2 = Float.valueOf(1.0f);
        LBlendMode lBlendMode2 = lBlendMode;
        AnimatableTextFrame documentData = null;
        AnimatableTextProperties animatableTextProperties = null;
        AnimatableFloatValue animatableFloatValue = null;
        BlurEffect blurEffect = null;
        DropShadowEffect dropShadowEffect = null;
        boolean z3 = false;
        int iNextInt = 0;
        int iNextInt2 = 0;
        int color = 0;
        boolean zNextBoolean = false;
        long jNextInt = 0;
        float fNextDouble2 = 0.0f;
        float endFrame = 0.0f;
        float fNextDouble3 = 0.0f;
        float fNextDouble4 = 0.0f;
        Layer.MatteType matteType2 = matteType;
        long jNextInt2 = -1;
        String strNextString = "UNSET";
        String strNextString2 = null;
        AnimatableTransform animatableTransform = null;
        Layer.LayerType layerType = null;
        String strNextString3 = null;
        float fNextDouble5 = 0.0f;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    strNextString = jsonReader.nextString();
                    z2 = false;
                    break;
                case 1:
                    jNextInt = jsonReader.nextInt();
                    z2 = false;
                    break;
                case 2:
                    strNextString3 = jsonReader.nextString();
                    z2 = false;
                    break;
                case 3:
                    f = f3;
                    int iNextInt3 = jsonReader.nextInt();
                    if (iNextInt3 < Layer.LayerType.UNKNOWN.ordinal()) {
                        layerType = Layer.LayerType.values()[iNextInt3];
                    } else {
                        layerType = Layer.LayerType.UNKNOWN;
                    }
                    f3 = f;
                    z2 = false;
                    break;
                case 4:
                    jNextInt2 = jsonReader.nextInt();
                    z2 = false;
                    break;
                case 5:
                    iNextInt = (int) (jsonReader.nextInt() * Utils.dpScale());
                    z2 = false;
                    break;
                case 6:
                    iNextInt2 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    z2 = false;
                    break;
                case 7:
                    color = Color.parseColor(jsonReader.nextString());
                    z2 = false;
                    break;
                case 8:
                    animatableTransform = AnimatableTransformParser.parse(jsonReader, lottieComposition);
                    z2 = false;
                    break;
                case 9:
                    f = f3;
                    int iNextInt4 = jsonReader.nextInt();
                    if (iNextInt4 >= Layer.MatteType.values().length) {
                        lottieComposition.addWarning("Unsupported matte type: " + iNextInt4);
                    } else {
                        matteType2 = Layer.MatteType.values()[iNextInt4];
                        int i = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[matteType2.ordinal()];
                        if (i == 1) {
                            lottieComposition.addWarning("Unsupported matte type: Luma");
                        } else if (i == 2) {
                            lottieComposition.addWarning("Unsupported matte type: Luma Inverted");
                        }
                        lottieComposition.incrementMatteOrMaskCount(1);
                    }
                    f3 = f;
                    z2 = false;
                    break;
                case 10:
                    f = f3;
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        arrayList.add(MaskParser.parse(jsonReader, lottieComposition));
                    }
                    lottieComposition.incrementMatteOrMaskCount(arrayList.size());
                    jsonReader.endArray();
                    f3 = f;
                    z2 = false;
                    break;
                case 11:
                    ArrayList arrayList3 = arrayList2;
                    f = f3;
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        ContentModel contentModel = ContentModelParser.parse(jsonReader, lottieComposition);
                        ArrayList arrayList4 = arrayList3;
                        if (contentModel != null) {
                            arrayList4.add(contentModel);
                        }
                        arrayList3 = arrayList4;
                    }
                    arrayList2 = arrayList3;
                    jsonReader.endArray();
                    f3 = f;
                    z2 = false;
                    break;
                case 12:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        int iSelectName = jsonReader.selectName(TEXT_NAMES);
                        if (iSelectName == 0) {
                            documentData = AnimatableValueParser.parseDocumentData(jsonReader, lottieComposition);
                        } else if (iSelectName == 1) {
                            jsonReader.beginArray();
                            if (jsonReader.hasNext()) {
                                animatableTextProperties = AnimatableTextPropertiesParser.parse(jsonReader, lottieComposition);
                            }
                            while (jsonReader.hasNext()) {
                                jsonReader.skipValue();
                            }
                            jsonReader.endArray();
                        } else {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();
                    f3 = f3;
                    arrayList2 = arrayList2;
                    z2 = false;
                    break;
                case 13:
                    jsonReader.beginArray();
                    ArrayList arrayList5 = new ArrayList();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            int iSelectName2 = jsonReader.selectName(EFFECTS_NAMES);
                            if (iSelectName2 == 0) {
                                int iNextInt5 = jsonReader.nextInt();
                                if (iNextInt5 == 29) {
                                    blurEffect = BlurEffectParser.parse(jsonReader, lottieComposition);
                                } else if (iNextInt5 == 25) {
                                    dropShadowEffect = new DropShadowEffectParser().parse(jsonReader, lottieComposition);
                                }
                            } else if (iSelectName2 == 1) {
                                arrayList5.add(jsonReader.nextString());
                            } else {
                                jsonReader.skipName();
                                jsonReader.skipValue();
                            }
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                    lottieComposition.addWarning("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList5);
                    f3 = f3;
                    arrayList2 = arrayList2;
                    z2 = false;
                    break;
                case 14:
                    fNextDouble = (float) jsonReader.nextDouble();
                    z2 = false;
                    break;
                case 15:
                    fNextDouble5 = (float) jsonReader.nextDouble();
                    z2 = false;
                    break;
                case 16:
                    arrayList2 = arrayList2;
                    fNextDouble3 = (float) (jsonReader.nextDouble() * ((double) Utils.dpScale()));
                    arrayList2 = arrayList2;
                    z2 = false;
                    break;
                case 17:
                    fNextDouble4 = (float) (jsonReader.nextDouble() * ((double) Utils.dpScale()));
                    f3 = f3;
                    arrayList2 = arrayList2;
                    z2 = false;
                    break;
                case 18:
                    fNextDouble2 = (float) jsonReader.nextDouble();
                    break;
                case 19:
                    endFrame = (float) jsonReader.nextDouble();
                    break;
                case 20:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, z2);
                    break;
                case 21:
                    strNextString2 = jsonReader.nextString();
                    break;
                case 22:
                    zNextBoolean = jsonReader.nextBoolean();
                    break;
                case 23:
                    f2 = f3;
                    z3 = jsonReader.nextInt() == 1 ? true : z2;
                    f3 = f2;
                    break;
                case 24:
                    int iNextInt6 = jsonReader.nextInt();
                    if (iNextInt6 >= LBlendMode.values().length) {
                        f2 = f3;
                        lottieComposition.addWarning("Unsupported Blend Mode: " + iNextInt6);
                        lBlendMode2 = LBlendMode.NORMAL;
                        f3 = f2;
                    } else {
                        lBlendMode2 = LBlendMode.values()[iNextInt6];
                    }
                    break;
                default:
                    f = f3;
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    f3 = f;
                    z2 = false;
                    break;
            }
        }
        float f4 = f3;
        jsonReader.endObject();
        ArrayList arrayList6 = new ArrayList();
        if (fNextDouble2 > f4) {
            z = z3;
            arrayList6.add(new Keyframe(lottieComposition, fValueOf, fValueOf, null, 0.0f, Float.valueOf(fNextDouble2)));
        } else {
            z = z3;
        }
        if (endFrame <= f4) {
            endFrame = lottieComposition.getEndFrame();
        }
        arrayList6.add(new Keyframe(lottieComposition, fValueOf2, fValueOf2, null, fNextDouble2, Float.valueOf(endFrame)));
        arrayList6.add(new Keyframe(lottieComposition, fValueOf, fValueOf, null, endFrame, Float.valueOf(Float.MAX_VALUE)));
        if (strNextString.endsWith(".ai") || "ai".equals(strNextString2)) {
            lottieComposition.addWarning("Convert your Illustrator layers to shape layers.");
        }
        if (z) {
            if (animatableTransform == null) {
                animatableTransform = new AnimatableTransform();
            }
            animatableTransform.setAutoOrient(z);
        }
        return new Layer(arrayList2, lottieComposition, strNextString, jNextInt, layerType, jNextInt2, strNextString3, arrayList, animatableTransform, iNextInt, iNextInt2, color, fNextDouble, fNextDouble5, fNextDouble3, fNextDouble4, documentData, animatableTextProperties, arrayList6, matteType2, animatableFloatValue, zNextBoolean, blurEffect, dropShadowEffect, lBlendMode2);
    }

    /* JADX INFO: renamed from: com.airbnb.lottie.parser.LayerParser$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType;

        static {
            int[] iArr = new int[Layer.MatteType.values().length];
            $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType = iArr;
            try {
                iArr[Layer.MatteType.LUMA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[Layer.MatteType.LUMA_INVERTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
