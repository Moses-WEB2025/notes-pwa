package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.TextDelegate;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.model.content.TextRangeUnits;
import com.airbnb.lottie.utils.DropShadow;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import com.miui.maml.elements.MusicLyricParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class TextLayer extends BaseLayer {
    private final LongSparseArray<String> codePointCache;
    private BaseKeyframeAnimation<Integer, Integer> colorAnimation;
    private BaseKeyframeAnimation<Integer, Integer> colorCallbackAnimation;
    private final LottieComposition composition;
    private final Map<FontCharacter, List<ContentGroup>> contentsForCharacter;
    private final Paint fillPaint;
    private final LottieDrawable lottieDrawable;
    private final Matrix matrix;
    private BaseKeyframeAnimation<Integer, Integer> opacityAnimation;
    private final RectF rectF;
    private final StringBuilder stringBuilder;
    private BaseKeyframeAnimation<Integer, Integer> strokeColorAnimation;
    private BaseKeyframeAnimation<Integer, Integer> strokeColorCallbackAnimation;
    private final Paint strokePaint;
    private BaseKeyframeAnimation<Float, Float> strokeWidthAnimation;
    private BaseKeyframeAnimation<Float, Float> strokeWidthCallbackAnimation;
    private final TextKeyframeAnimation textAnimation;
    private BaseKeyframeAnimation<Integer, Integer> textRangeEndAnimation;
    private BaseKeyframeAnimation<Integer, Integer> textRangeOffsetAnimation;
    private BaseKeyframeAnimation<Integer, Integer> textRangeStartAnimation;
    private TextRangeUnits textRangeUnits;
    private BaseKeyframeAnimation<Float, Float> textSizeCallbackAnimation;
    private final List<TextSubLine> textSubLines;
    private BaseKeyframeAnimation<Float, Float> trackingAnimation;
    private BaseKeyframeAnimation<Float, Float> trackingCallbackAnimation;
    private BaseKeyframeAnimation<Typeface, Typeface> typefaceCallbackAnimation;

    TextLayer(LottieDrawable lottieDrawable, Layer layer) {
        super(lottieDrawable, layer);
        this.stringBuilder = new StringBuilder(2);
        this.rectF = new RectF();
        this.matrix = new Matrix();
        int i = 1;
        this.fillPaint = new Paint(i) { // from class: com.airbnb.lottie.model.layer.TextLayer.1
            {
                setStyle(Paint.Style.FILL);
            }
        };
        this.strokePaint = new Paint(i) { // from class: com.airbnb.lottie.model.layer.TextLayer.2
            {
                setStyle(Paint.Style.STROKE);
            }
        };
        this.contentsForCharacter = new HashMap();
        this.codePointCache = new LongSparseArray<>();
        this.textSubLines = new ArrayList();
        this.textRangeUnits = TextRangeUnits.INDEX;
        this.lottieDrawable = lottieDrawable;
        this.composition = layer.getComposition();
        TextKeyframeAnimation textKeyframeAnimationCreateAnimation = layer.getText().createAnimation();
        this.textAnimation = textKeyframeAnimationCreateAnimation;
        textKeyframeAnimationCreateAnimation.addUpdateListener(this);
        addAnimation(textKeyframeAnimationCreateAnimation);
        AnimatableTextProperties textProperties = layer.getTextProperties();
        if (textProperties != null && textProperties.textStyle != null && textProperties.textStyle.color != null) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimationCreateAnimation = textProperties.textStyle.color.createAnimation();
            this.colorAnimation = baseKeyframeAnimationCreateAnimation;
            baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
            addAnimation(this.colorAnimation);
        }
        if (textProperties != null && textProperties.textStyle != null && textProperties.textStyle.stroke != null) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimationCreateAnimation2 = textProperties.textStyle.stroke.createAnimation();
            this.strokeColorAnimation = baseKeyframeAnimationCreateAnimation2;
            baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
            addAnimation(this.strokeColorAnimation);
        }
        if (textProperties != null && textProperties.textStyle != null && textProperties.textStyle.strokeWidth != null) {
            FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation = textProperties.textStyle.strokeWidth.createAnimation();
            this.strokeWidthAnimation = floatKeyframeAnimationCreateAnimation;
            floatKeyframeAnimationCreateAnimation.addUpdateListener(this);
            addAnimation(this.strokeWidthAnimation);
        }
        if (textProperties != null && textProperties.textStyle != null && textProperties.textStyle.tracking != null) {
            FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation2 = textProperties.textStyle.tracking.createAnimation();
            this.trackingAnimation = floatKeyframeAnimationCreateAnimation2;
            floatKeyframeAnimationCreateAnimation2.addUpdateListener(this);
            addAnimation(this.trackingAnimation);
        }
        if (textProperties != null && textProperties.textStyle != null && textProperties.textStyle.opacity != null) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimationCreateAnimation3 = textProperties.textStyle.opacity.createAnimation();
            this.opacityAnimation = baseKeyframeAnimationCreateAnimation3;
            baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
            addAnimation(this.opacityAnimation);
        }
        if (textProperties != null && textProperties.rangeSelector != null && textProperties.rangeSelector.start != null) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimationCreateAnimation4 = textProperties.rangeSelector.start.createAnimation();
            this.textRangeStartAnimation = baseKeyframeAnimationCreateAnimation4;
            baseKeyframeAnimationCreateAnimation4.addUpdateListener(this);
            addAnimation(this.textRangeStartAnimation);
        }
        if (textProperties != null && textProperties.rangeSelector != null && textProperties.rangeSelector.end != null) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimationCreateAnimation5 = textProperties.rangeSelector.end.createAnimation();
            this.textRangeEndAnimation = baseKeyframeAnimationCreateAnimation5;
            baseKeyframeAnimationCreateAnimation5.addUpdateListener(this);
            addAnimation(this.textRangeEndAnimation);
        }
        if (textProperties != null && textProperties.rangeSelector != null && textProperties.rangeSelector.offset != null) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimationCreateAnimation6 = textProperties.rangeSelector.offset.createAnimation();
            this.textRangeOffsetAnimation = baseKeyframeAnimationCreateAnimation6;
            baseKeyframeAnimationCreateAnimation6.addUpdateListener(this);
            addAnimation(this.textRangeOffsetAnimation);
        }
        if (textProperties == null || textProperties.rangeSelector == null) {
            return;
        }
        this.textRangeUnits = textProperties.rangeSelector.units;
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        rectF.set(0.0f, 0.0f, this.composition.getBounds().width(), this.composition.getBounds().height());
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    void drawLayer(Canvas canvas, Matrix matrix, int i, DropShadow dropShadow) {
        Canvas canvas2;
        DocumentData value = this.textAnimation.getValue();
        Font font = this.composition.getFonts().get(value.fontName);
        if (font == null) {
            return;
        }
        canvas.save();
        canvas.concat(matrix);
        configurePaint(value, i, 0);
        if (this.lottieDrawable.useTextGlyphs()) {
            canvas2 = canvas;
            drawTextWithGlyphs(value, matrix, font, canvas2, i);
        } else {
            canvas2 = canvas;
            drawTextWithFont(value, font, canvas2, i);
        }
        canvas2.restore();
    }

    private void configurePaint(DocumentData documentData, int i, int i2) {
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation = this.colorCallbackAnimation;
        if (baseKeyframeAnimation != null) {
            this.fillPaint.setColor(baseKeyframeAnimation.getValue().intValue());
        } else if (this.colorAnimation != null && isIndexInRangeSelection(i2)) {
            this.fillPaint.setColor(this.colorAnimation.getValue().intValue());
        } else {
            this.fillPaint.setColor(documentData.color);
        }
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation2 = this.strokeColorCallbackAnimation;
        if (baseKeyframeAnimation2 != null) {
            this.strokePaint.setColor(baseKeyframeAnimation2.getValue().intValue());
        } else if (this.strokeColorAnimation != null && isIndexInRangeSelection(i2)) {
            this.strokePaint.setColor(this.strokeColorAnimation.getValue().intValue());
        } else {
            this.strokePaint.setColor(documentData.strokeColor);
        }
        int iIntValue = 100;
        int iIntValue2 = this.transform.getOpacity() == null ? 100 : this.transform.getOpacity().getValue().intValue();
        if (this.opacityAnimation != null && isIndexInRangeSelection(i2)) {
            iIntValue = this.opacityAnimation.getValue().intValue();
        }
        int iRound = Math.round(((((iIntValue2 * 255.0f) / 100.0f) * (iIntValue / 100.0f)) * i) / 255.0f);
        this.fillPaint.setAlpha(iRound);
        this.strokePaint.setAlpha(iRound);
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation3 = this.strokeWidthCallbackAnimation;
        if (baseKeyframeAnimation3 != null) {
            this.strokePaint.setStrokeWidth(baseKeyframeAnimation3.getValue().floatValue());
        } else if (this.strokeWidthAnimation != null && isIndexInRangeSelection(i2)) {
            this.strokePaint.setStrokeWidth(this.strokeWidthAnimation.getValue().floatValue());
        } else {
            this.strokePaint.setStrokeWidth(documentData.strokeWidth * Utils.dpScale());
        }
    }

    private boolean isIndexInRangeSelection(int i) {
        int length = this.textAnimation.getValue().text.length();
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation = this.textRangeStartAnimation;
        if (baseKeyframeAnimation == null || this.textRangeEndAnimation == null) {
            return true;
        }
        int iMin = Math.min(baseKeyframeAnimation.getValue().intValue(), this.textRangeEndAnimation.getValue().intValue());
        int iMax = Math.max(this.textRangeStartAnimation.getValue().intValue(), this.textRangeEndAnimation.getValue().intValue());
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation2 = this.textRangeOffsetAnimation;
        if (baseKeyframeAnimation2 != null) {
            int iIntValue = baseKeyframeAnimation2.getValue().intValue();
            iMin += iIntValue;
            iMax += iIntValue;
        }
        if (this.textRangeUnits == TextRangeUnits.INDEX) {
            return i >= iMin && i < iMax;
        }
        float f = (i / length) * 100.0f;
        return f >= ((float) iMin) && f < ((float) iMax);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0053  */
    /* JADX WARN: Code duplicated, block: B:17:0x005d  */
    /* JADX WARN: Code duplicated, block: B:18:0x005f  */
    /* JADX WARN: Code duplicated, block: B:22:0x0071  */
    /* JADX WARN: Code duplicated, block: B:24:0x0088  */
    /* JADX WARN: Code duplicated, block: B:25:0x009a  */
    private void drawTextWithGlyphs(DocumentData documentData, Matrix matrix, Font font, Canvas canvas, int i) {
        float fFloatValue;
        float fFloatValue2;
        float f;
        int i2;
        int i3;
        float f2;
        List<TextSubLine> listSplitGlyphTextIntoLines;
        int i4;
        TextSubLine textSubLine;
        float f3;
        float f4;
        TextLayer textLayer = this;
        DocumentData documentData2 = documentData;
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation = textLayer.textSizeCallbackAnimation;
        if (baseKeyframeAnimation != null) {
            fFloatValue = baseKeyframeAnimation.getValue().floatValue();
        } else {
            fFloatValue = documentData2.size;
        }
        float f5 = fFloatValue / 100.0f;
        float scale = Utils.getScale(matrix);
        List<String> textLines = textLayer.getTextLines(documentData2.text);
        int size = textLines.size();
        float f6 = documentData2.tracking / 10.0f;
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation2 = textLayer.trackingCallbackAnimation;
        if (baseKeyframeAnimation2 != null) {
            fFloatValue2 = baseKeyframeAnimation2.getValue().floatValue();
        } else {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation3 = textLayer.trackingAnimation;
            if (baseKeyframeAnimation3 != null) {
                fFloatValue2 = baseKeyframeAnimation3.getValue().floatValue();
            }
            f = f6;
            i2 = -1;
            i3 = 0;
            while (i3 < size) {
                String str = textLines.get(i3);
                if (documentData2.boxSize == null) {
                    f2 = 0.0f;
                } else {
                    f2 = documentData2.boxSize.x;
                }
                listSplitGlyphTextIntoLines = textLayer.splitGlyphTextIntoLines(str, f2, font, f5, f, true);
                i4 = 0;
                while (i4 < listSplitGlyphTextIntoLines.size()) {
                    textSubLine = listSplitGlyphTextIntoLines.get(i4);
                    i2++;
                    canvas.save();
                    if (textLayer.offsetCanvas(canvas, documentData2, i2, textSubLine.width)) {
                        float f7 = f5;
                        DocumentData documentData3 = documentData2;
                        f3 = f;
                        f4 = scale;
                        textLayer.drawGlyphTextLine(textSubLine.text, documentData3, font, canvas, f4, f7, f3, i);
                        f5 = f7;
                    } else {
                        f3 = f;
                        f4 = scale;
                    }
                    canvas.restore();
                    i4++;
                    textLayer = this;
                    scale = f4;
                    f = f3;
                    documentData2 = documentData;
                }
                i3++;
                textLayer = this;
                f = f;
                documentData2 = documentData;
            }
        }
        f6 += fFloatValue2;
        f = f6;
        i2 = -1;
        i3 = 0;
        while (i3 < size) {
            String str2 = textLines.get(i3);
            if (documentData2.boxSize == null) {
                f2 = 0.0f;
            } else {
                f2 = documentData2.boxSize.x;
            }
            listSplitGlyphTextIntoLines = textLayer.splitGlyphTextIntoLines(str2, f2, font, f5, f, true);
            i4 = 0;
            while (i4 < listSplitGlyphTextIntoLines.size()) {
                textSubLine = listSplitGlyphTextIntoLines.get(i4);
                i2++;
                canvas.save();
                if (textLayer.offsetCanvas(canvas, documentData2, i2, textSubLine.width)) {
                    float f8 = f5;
                    DocumentData documentData4 = documentData2;
                    f3 = f;
                    f4 = scale;
                    textLayer.drawGlyphTextLine(textSubLine.text, documentData4, font, canvas, f4, f8, f3, i);
                    f5 = f8;
                } else {
                    f3 = f;
                    f4 = scale;
                }
                canvas.restore();
                i4++;
                textLayer = this;
                scale = f4;
                f = f3;
                documentData2 = documentData;
            }
            i3++;
            textLayer = this;
            f = f;
            documentData2 = documentData;
        }
    }

    private void drawGlyphTextLine(String str, DocumentData documentData, Font font, Canvas canvas, float f, float f2, float f3, int i) {
        TextLayer textLayer;
        DocumentData documentData2;
        Canvas canvas2;
        float f4;
        int i2;
        int i3 = 0;
        while (i3 < str.length()) {
            FontCharacter fontCharacter = this.composition.getCharacters().get(FontCharacter.hashFor(str.charAt(i3), font.getFamily(), font.getStyle()));
            if (fontCharacter == null) {
                textLayer = this;
                documentData2 = documentData;
                canvas2 = canvas;
                f4 = f2;
                i2 = i;
            } else {
                textLayer = this;
                documentData2 = documentData;
                canvas2 = canvas;
                f4 = f2;
                i2 = i;
                textLayer.drawCharacterAsGlyph(fontCharacter, f4, documentData2, canvas2, i3, i2);
                canvas2.translate((((float) fontCharacter.getWidth()) * f4 * Utils.dpScale()) + f3, 0.0f);
            }
            i3++;
            this = textLayer;
            f2 = f4;
            documentData = documentData2;
            canvas = canvas2;
            i = i2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0093  */
    /* JADX WARN: Code duplicated, block: B:23:0x009d  */
    /* JADX WARN: Code duplicated, block: B:24:0x009f  */
    /* JADX WARN: Code duplicated, block: B:28:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:35:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:38:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:39:0x00ee  */
    private void drawTextWithFont(DocumentData documentData, Font font, Canvas canvas, int i) {
        float fFloatValue;
        float fFloatValue2;
        float fDpScale;
        List<String> textLines;
        int size;
        int i2;
        int i3;
        int length;
        float f;
        float f2;
        List<TextSubLine> listSplitGlyphTextIntoLines;
        int i4;
        TextSubLine textSubLine;
        float fMeasureText;
        float f3;
        int i5;
        TextLayer textLayer = this;
        DocumentData documentData2 = documentData;
        Font font2 = font;
        Typeface typeface = textLayer.getTypeface(font2);
        if (typeface == null) {
            return;
        }
        String textInternal = documentData2.text;
        TextDelegate textDelegate = textLayer.lottieDrawable.getTextDelegate();
        if (textDelegate != null) {
            textInternal = textDelegate.getTextInternal(textLayer.getName(), textInternal);
        }
        textLayer.fillPaint.setTypeface(typeface);
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation = textLayer.textSizeCallbackAnimation;
        if (baseKeyframeAnimation != null) {
            fFloatValue = baseKeyframeAnimation.getValue().floatValue();
        } else {
            fFloatValue = documentData2.size;
        }
        textLayer.fillPaint.setTextSize(Utils.dpScale() * fFloatValue);
        textLayer.strokePaint.setTypeface(textLayer.fillPaint.getTypeface());
        textLayer.strokePaint.setTextSize(textLayer.fillPaint.getTextSize());
        float f4 = documentData2.tracking / 10.0f;
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation2 = textLayer.trackingCallbackAnimation;
        if (baseKeyframeAnimation2 != null) {
            fFloatValue2 = baseKeyframeAnimation2.getValue().floatValue();
        } else {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation3 = textLayer.trackingAnimation;
            if (baseKeyframeAnimation3 != null) {
                fFloatValue2 = baseKeyframeAnimation3.getValue().floatValue();
            }
            fDpScale = ((f4 * Utils.dpScale()) * fFloatValue) / 100.0f;
            textLines = textLayer.getTextLines(textInternal);
            size = textLines.size();
            i2 = -1;
            i3 = 0;
            length = 0;
            while (i3 < size) {
                String str = textLines.get(i3);
                if (documentData2.boxSize == null) {
                    f = 0.0f;
                } else {
                    f = documentData2.boxSize.x;
                }
                f2 = fDpScale;
                listSplitGlyphTextIntoLines = textLayer.splitGlyphTextIntoLines(str, f, font2, 0.0f, f2, false);
                i4 = 0;
                while (i4 < listSplitGlyphTextIntoLines.size()) {
                    textSubLine = listSplitGlyphTextIntoLines.get(i4);
                    i2++;
                    canvas.save();
                    if (textLayer.textAnimation == null || textLayer.textSizeCallbackAnimation != null || textLayer.trackingCallbackAnimation != null) {
                        fMeasureText = textLayer.fillPaint.measureText(textSubLine.text);
                    } else {
                        fMeasureText = textSubLine.width;
                    }
                    if (textLayer.offsetCanvas(canvas, documentData2, i2, fMeasureText)) {
                        f3 = f2;
                        i5 = length;
                        textLayer.drawFontTextLine(textSubLine.text, documentData2, canvas, f3, i5, i);
                    } else {
                        f3 = f2;
                        i5 = length;
                    }
                    length = i5 + textSubLine.text.length();
                    canvas.restore();
                    i4++;
                    textLayer = this;
                    documentData2 = documentData;
                    f2 = f3;
                }
                fDpScale = f2;
                i3++;
                textLayer = this;
                documentData2 = documentData;
                font2 = font;
            }
        }
        f4 += fFloatValue2;
        fDpScale = ((f4 * Utils.dpScale()) * fFloatValue) / 100.0f;
        textLines = textLayer.getTextLines(textInternal);
        size = textLines.size();
        i2 = -1;
        i3 = 0;
        length = 0;
        while (i3 < size) {
            String str2 = textLines.get(i3);
            if (documentData2.boxSize == null) {
                f = 0.0f;
            } else {
                f = documentData2.boxSize.x;
            }
            f2 = fDpScale;
            listSplitGlyphTextIntoLines = textLayer.splitGlyphTextIntoLines(str2, f, font2, 0.0f, f2, false);
            i4 = 0;
            while (i4 < listSplitGlyphTextIntoLines.size()) {
                textSubLine = listSplitGlyphTextIntoLines.get(i4);
                i2++;
                canvas.save();
                if (textLayer.textAnimation == null) {
                    fMeasureText = textLayer.fillPaint.measureText(textSubLine.text);
                } else {
                    fMeasureText = textLayer.fillPaint.measureText(textSubLine.text);
                }
                if (textLayer.offsetCanvas(canvas, documentData2, i2, fMeasureText)) {
                    f3 = f2;
                    i5 = length;
                    textLayer.drawFontTextLine(textSubLine.text, documentData2, canvas, f3, i5, i);
                } else {
                    f3 = f2;
                    i5 = length;
                }
                length = i5 + textSubLine.text.length();
                canvas.restore();
                i4++;
                textLayer = this;
                documentData2 = documentData;
                f2 = f3;
            }
            fDpScale = f2;
            i3++;
            textLayer = this;
            documentData2 = documentData;
            font2 = font;
        }
    }

    private boolean offsetCanvas(Canvas canvas, DocumentData documentData, int i, float f) {
        PointF pointF = documentData.boxPosition;
        PointF pointF2 = documentData.boxSize;
        float fDpScale = Utils.dpScale();
        float f2 = (i * documentData.lineHeight * fDpScale) + (pointF == null ? 0.0f : (documentData.lineHeight * fDpScale) + pointF.y);
        if (this.lottieDrawable.getClipTextToBoundingBox() && pointF2 != null && pointF != null && f2 >= pointF.y + pointF2.y + documentData.size) {
            return false;
        }
        float f3 = pointF == null ? 0.0f : pointF.x;
        float f4 = pointF2 != null ? pointF2.x : 0.0f;
        int i2 = AnonymousClass3.$SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[documentData.justification.ordinal()];
        if (i2 == 1) {
            canvas.translate(f3, f2);
        } else if (i2 == 2) {
            canvas.translate((f3 + f4) - f, f2);
        } else if (i2 == 3) {
            canvas.translate((f3 + (f4 / 2.0f)) - (f / 2.0f), f2);
        }
        return true;
    }

    /* JADX INFO: renamed from: com.airbnb.lottie.model.layer.TextLayer$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification;

        static {
            int[] iArr = new int[DocumentData.Justification.values().length];
            $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification = iArr;
            try {
                iArr[DocumentData.Justification.LEFT_ALIGN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[DocumentData.Justification.RIGHT_ALIGN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[DocumentData.Justification.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private Typeface getTypeface(Font font) {
        Typeface value;
        BaseKeyframeAnimation<Typeface, Typeface> baseKeyframeAnimation = this.typefaceCallbackAnimation;
        if (baseKeyframeAnimation != null && (value = baseKeyframeAnimation.getValue()) != null) {
            return value;
        }
        Typeface typeface = this.lottieDrawable.getTypeface(font);
        return typeface != null ? typeface : font.getTypeface();
    }

    private List<String> getTextLines(String str) {
        return Arrays.asList(str.replaceAll(MusicLyricParser.CRLF, StringUtils.CR).replaceAll("\u0003", StringUtils.CR).replaceAll(StringUtils.LF, StringUtils.CR).split(StringUtils.CR));
    }

    private void drawFontTextLine(String str, DocumentData documentData, Canvas canvas, float f, int i, int i2) {
        int length = 0;
        while (length < str.length()) {
            String strCodePointToString = this.codePointToString(str, length);
            TextLayer textLayer = this;
            DocumentData documentData2 = documentData;
            textLayer.drawCharacterFromFont(strCodePointToString, documentData2, canvas, i + length, i2);
            canvas.translate(textLayer.fillPaint.measureText(strCodePointToString) + f, 0.0f);
            length += strCodePointToString.length();
            this = textLayer;
            documentData = documentData2;
        }
    }

    private List<TextSubLine> splitGlyphTextIntoLines(String str, float f, Font font, float f2, float f3, boolean z) {
        float fMeasureText;
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (int i4 = 0; i4 < str.length(); i4++) {
            char cCharAt = str.charAt(i4);
            if (z) {
                FontCharacter fontCharacter = this.composition.getCharacters().get(FontCharacter.hashFor(cCharAt, font.getFamily(), font.getStyle()));
                if (fontCharacter != null) {
                    fMeasureText = ((float) fontCharacter.getWidth()) * f2 * Utils.dpScale();
                }
            } else {
                fMeasureText = this.fillPaint.measureText(str.substring(i4, i4 + 1));
            }
            float f7 = fMeasureText + f3;
            if (cCharAt == ' ') {
                z2 = true;
                f6 = f7;
            } else if (z2) {
                z2 = false;
                i3 = i4;
                f5 = f7;
            } else {
                f5 += f7;
            }
            f4 += f7;
            if (f > 0.0f && f4 >= f && cCharAt != ' ') {
                i++;
                TextSubLine textSubLineEnsureEnoughSubLines = ensureEnoughSubLines(i);
                if (i3 == i2) {
                    String strSubstring = str.substring(i2, i4);
                    String strTrim = strSubstring.trim();
                    textSubLineEnsureEnoughSubLines.set(strTrim, (f4 - f7) - ((strTrim.length() - strSubstring.length()) * f6));
                    i2 = i4;
                    i3 = i2;
                    f4 = f7;
                    f5 = f4;
                } else {
                    String strSubstring2 = str.substring(i2, i3 - 1);
                    String strTrim2 = strSubstring2.trim();
                    textSubLineEnsureEnoughSubLines.set(strTrim2, ((f4 - f5) - ((strSubstring2.length() - strTrim2.length()) * f6)) - f6);
                    f4 = f5;
                    i2 = i3;
                }
            }
        }
        if (f4 > 0.0f) {
            i++;
            ensureEnoughSubLines(i).set(str.substring(i2), f4);
        }
        return this.textSubLines.subList(0, i);
    }

    private TextSubLine ensureEnoughSubLines(int i) {
        for (int size = this.textSubLines.size(); size < i; size++) {
            this.textSubLines.add(new TextSubLine());
        }
        return this.textSubLines.get(i - 1);
    }

    private void drawCharacterAsGlyph(FontCharacter fontCharacter, float f, DocumentData documentData, Canvas canvas, int i, int i2) {
        configurePaint(documentData, i2, i);
        List<ContentGroup> contentsForCharacter = getContentsForCharacter(fontCharacter);
        for (int i3 = 0; i3 < contentsForCharacter.size(); i3++) {
            Path path = contentsForCharacter.get(i3).getPath();
            path.computeBounds(this.rectF, false);
            this.matrix.reset();
            this.matrix.preTranslate(0.0f, (-documentData.baselineShift) * Utils.dpScale());
            this.matrix.preScale(f, f);
            path.transform(this.matrix);
            if (documentData.strokeOverFill) {
                drawGlyph(path, this.fillPaint, canvas);
                drawGlyph(path, this.strokePaint, canvas);
            } else {
                drawGlyph(path, this.strokePaint, canvas);
                drawGlyph(path, this.fillPaint, canvas);
            }
        }
    }

    private void drawGlyph(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawPath(path, paint);
    }

    private void drawCharacterFromFont(String str, DocumentData documentData, Canvas canvas, int i, int i2) {
        configurePaint(documentData, i2, i);
        if (documentData.strokeOverFill) {
            drawCharacter(str, this.fillPaint, canvas);
            drawCharacter(str, this.strokePaint, canvas);
        } else {
            drawCharacter(str, this.strokePaint, canvas);
            drawCharacter(str, this.fillPaint, canvas);
        }
    }

    private void drawCharacter(String str, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawText(str, 0, str.length(), 0.0f, 0.0f, paint);
    }

    private List<ContentGroup> getContentsForCharacter(FontCharacter fontCharacter) {
        if (this.contentsForCharacter.containsKey(fontCharacter)) {
            return this.contentsForCharacter.get(fontCharacter);
        }
        List<ShapeGroup> shapes = fontCharacter.getShapes();
        int size = shapes.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new ContentGroup(this.lottieDrawable, this, shapes.get(i), this.composition));
        }
        this.contentsForCharacter.put(fontCharacter, arrayList);
        return arrayList;
    }

    private String codePointToString(String str, int i) {
        int iCodePointAt = str.codePointAt(i);
        int iCharCount = Character.charCount(iCodePointAt) + i;
        while (iCharCount < str.length()) {
            int iCodePointAt2 = str.codePointAt(iCharCount);
            if (!isModifier(iCodePointAt2)) {
                break;
            }
            iCharCount += Character.charCount(iCodePointAt2);
            iCodePointAt = (iCodePointAt * 31) + iCodePointAt2;
        }
        long j = iCodePointAt;
        if (this.codePointCache.containsKey(j)) {
            return this.codePointCache.get(j);
        }
        this.stringBuilder.setLength(0);
        while (i < iCharCount) {
            int iCodePointAt3 = str.codePointAt(i);
            this.stringBuilder.appendCodePoint(iCodePointAt3);
            i += Character.charCount(iCodePointAt3);
        }
        String string = this.stringBuilder.toString();
        this.codePointCache.put(j, string);
        return string;
    }

    private boolean isModifier(int i) {
        return Character.getType(i) == 16 || Character.getType(i) == 27 || Character.getType(i) == 6 || Character.getType(i) == 28 || Character.getType(i) == 8 || Character.getType(i) == 19;
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        super.addValueCallback(t, lottieValueCallback);
        if (t == LottieProperty.COLOR) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation = this.colorCallbackAnimation;
            if (baseKeyframeAnimation != null) {
                removeAnimation(baseKeyframeAnimation);
            }
            if (lottieValueCallback == null) {
                this.colorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.colorCallbackAnimation = valueCallbackKeyframeAnimation;
            valueCallbackKeyframeAnimation.addUpdateListener(this);
            addAnimation(this.colorCallbackAnimation);
            return;
        }
        if (t == LottieProperty.STROKE_COLOR) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation2 = this.strokeColorCallbackAnimation;
            if (baseKeyframeAnimation2 != null) {
                removeAnimation(baseKeyframeAnimation2);
            }
            if (lottieValueCallback == null) {
                this.strokeColorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.strokeColorCallbackAnimation = valueCallbackKeyframeAnimation2;
            valueCallbackKeyframeAnimation2.addUpdateListener(this);
            addAnimation(this.strokeColorCallbackAnimation);
            return;
        }
        if (t == LottieProperty.STROKE_WIDTH) {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation3 = this.strokeWidthCallbackAnimation;
            if (baseKeyframeAnimation3 != null) {
                removeAnimation(baseKeyframeAnimation3);
            }
            if (lottieValueCallback == null) {
                this.strokeWidthCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.strokeWidthCallbackAnimation = valueCallbackKeyframeAnimation3;
            valueCallbackKeyframeAnimation3.addUpdateListener(this);
            addAnimation(this.strokeWidthCallbackAnimation);
            return;
        }
        if (t == LottieProperty.TEXT_TRACKING) {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation4 = this.trackingCallbackAnimation;
            if (baseKeyframeAnimation4 != null) {
                removeAnimation(baseKeyframeAnimation4);
            }
            if (lottieValueCallback == null) {
                this.trackingCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.trackingCallbackAnimation = valueCallbackKeyframeAnimation4;
            valueCallbackKeyframeAnimation4.addUpdateListener(this);
            addAnimation(this.trackingCallbackAnimation);
            return;
        }
        if (t == LottieProperty.TEXT_SIZE) {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation5 = this.textSizeCallbackAnimation;
            if (baseKeyframeAnimation5 != null) {
                removeAnimation(baseKeyframeAnimation5);
            }
            if (lottieValueCallback == null) {
                this.textSizeCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation5 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.textSizeCallbackAnimation = valueCallbackKeyframeAnimation5;
            valueCallbackKeyframeAnimation5.addUpdateListener(this);
            addAnimation(this.textSizeCallbackAnimation);
            return;
        }
        if (t == LottieProperty.TYPEFACE) {
            BaseKeyframeAnimation<Typeface, Typeface> baseKeyframeAnimation6 = this.typefaceCallbackAnimation;
            if (baseKeyframeAnimation6 != null) {
                removeAnimation(baseKeyframeAnimation6);
            }
            if (lottieValueCallback == null) {
                this.typefaceCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation6 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.typefaceCallbackAnimation = valueCallbackKeyframeAnimation6;
            valueCallbackKeyframeAnimation6.addUpdateListener(this);
            addAnimation(this.typefaceCallbackAnimation);
            return;
        }
        if (t == LottieProperty.TEXT) {
            this.textAnimation.setStringValueCallback(lottieValueCallback);
        }
    }

    private static class TextSubLine {
        private String text;
        private float width;

        private TextSubLine() {
            this.text = "";
            this.width = 0.0f;
        }

        void set(String str, float f) {
            this.text = str;
            this.width = f;
        }
    }
}
