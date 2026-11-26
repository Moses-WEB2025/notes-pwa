package com.agog.mathdisplay.render;

import com.agog.mathdisplay.parse.MathDisplayException;
import com.pvporbit.freetype.Face;
import com.pvporbit.freetype.FreeType;
import com.pvporbit.freetype.GlyphMetrics;
import com.pvporbit.freetype.GlyphSlot;
import com.pvporbit.freetype.Library;
import com.pvporbit.freetype.MTFreeTypeMathTable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTFontMathTable.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b'\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b]\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0007\u0010\u009d\u0001\u001a\u000206J\u0011\u0010\u009e\u0001\u001a\u00020\b2\b\u0010\u009f\u0001\u001a\u00030 \u0001J\u0010\u0010¡\u0001\u001a\u00020\u00002\u0007\u0010¢\u0001\u001a\u00020\bJ\u0012\u0010£\u0001\u001a\u00030¤\u00012\b\u0010¥\u0001\u001a\u00030¤\u0001J\u0013\u0010¦\u0001\u001a\u00020\b2\b\u0010§\u0001\u001a\u00030\u0094\u0001H\u0002J\u0013\u0010¦\u0001\u001a\u00020\b2\b\u0010§\u0001\u001a\u00030¨\u0001H\u0002J9\u0010©\u0001\u001a\u00030ª\u00012\u000f\u0010«\u0001\u001a\n\u0012\u0005\u0012\u00030\u0094\u00010¬\u00012\u000e\u0010\u00ad\u0001\u001a\t\u0012\u0004\u0012\u00020\b0®\u00012\b\u0010¯\u0001\u001a\u00030\u0094\u0001¢\u0006\u0003\u0010°\u0001J>\u0010±\u0001\u001a\u00030¤\u00012\u000f\u0010«\u0001\u001a\n\u0012\u0005\u0012\u00030\u0094\u00010¬\u00012\u0013\u0010²\u0001\u001a\u000e\u0012\u0007\u0012\u0005\u0018\u00010¤\u0001\u0018\u00010®\u00012\b\u0010¯\u0001\u001a\u00030\u0094\u0001¢\u0006\u0003\u0010³\u0001J\u0012\u0010´\u0001\u001a\u00030\u0094\u00012\b\u0010µ\u0001\u001a\u00030\u0094\u0001J\u0012\u0010¶\u0001\u001a\u00030 \u00012\b\u0010·\u0001\u001a\u00030\u0094\u0001J\u0012\u0010¸\u0001\u001a\u00030\u0094\u00012\b\u0010¹\u0001\u001a\u00030 \u0001J\u0019\u0010º\u0001\u001a\n\u0012\u0005\u0012\u00030\u0094\u00010¬\u00012\b\u0010»\u0001\u001a\u00030¼\u0001J\u0011\u0010½\u0001\u001a\u00020\b2\b\u0010·\u0001\u001a\u00030\u0094\u0001J\u0012\u0010¾\u0001\u001a\u00030\u0094\u00012\b\u0010»\u0001\u001a\u00030\u0094\u0001J\u0011\u0010¿\u0001\u001a\u00020\b2\b\u0010»\u0001\u001a\u00030\u0094\u0001J\u001b\u0010À\u0001\u001a\f\u0012\u0005\u0012\u00030Á\u0001\u0018\u00010¬\u00012\b\u0010»\u0001\u001a\u00030\u0094\u0001J\u0019\u0010Â\u0001\u001a\n\u0012\u0005\u0012\u00030\u0094\u00010¬\u00012\b\u0010»\u0001\u001a\u00030¼\u0001J\u0007\u0010Ã\u0001\u001a\u00020\bJ\u0011\u0010Ä\u0001\u001a\u00020\b2\b\u0010Å\u0001\u001a\u00030 \u0001J\u001c\u0010Æ\u0001\u001a\u00030ª\u00012\b\u0010Ç\u0001\u001a\u00030¤\u00012\b\u0010¥\u0001\u001a\u00030¤\u0001R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u000f\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\nR\u0011\u0010\u0011\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\n\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\nR\u0011\u0010\u001b\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\nR\u0011\u0010\u001d\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\nR\u0011\u0010\u001f\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b \u0010\nR\u0011\u0010!\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\"\u0010\nR\u0011\u0010#\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b$\u0010\nR\u0011\u0010%\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b&\u0010\nR\u0011\u0010'\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b(\u0010\nR\u0011\u0010)\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b*\u0010\nR\u0011\u0010+\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b,\u0010\nR\u0011\u0010-\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b.\u0010\nR\u001a\u0010/\u001a\u000200X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001a\u00105\u001a\u000206X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u0011\u0010?\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b@\u0010\nR\u0011\u0010A\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bB\u0010\nR\u0011\u0010C\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bD\u0010\nR\u0011\u0010E\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bF\u0010\nR\u0011\u0010G\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bH\u0010\nR\u0011\u0010I\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bJ\u0010\nR\u0011\u0010K\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bL\u0010\nR\u0011\u0010M\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bN\u0010\nR\u0011\u0010O\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bP\u0010\nR\u0011\u0010Q\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bR\u0010\nR\u0011\u0010S\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bT\u0010\nR\u0011\u0010U\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bV\u0010\nR\u0011\u0010W\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bX\u0010\nR\u0011\u0010Y\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bZ\u0010\nR\u0011\u0010[\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\\\u0010\nR\u0011\u0010]\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b^\u0010\nR\u0011\u0010_\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b`\u0010\nR\u0011\u0010a\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bb\u0010\nR\u0011\u0010c\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bd\u0010\nR\u0011\u0010e\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bf\u0010\nR\u0011\u0010g\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bh\u0010\nR\u0011\u0010i\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bj\u0010\nR\u0011\u0010k\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bl\u0010\nR\u0011\u0010m\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bn\u0010\nR\u0011\u0010o\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bp\u0010\nR\u0011\u0010q\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\br\u0010\nR\u0011\u0010s\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bt\u0010\nR\u0011\u0010u\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bv\u0010\nR\u0011\u0010w\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bx\u0010\nR\u0011\u0010y\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bz\u0010\nR\u0011\u0010{\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b|\u0010\nR\u0011\u0010}\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b~\u0010\nR\u0012\u0010\u007f\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0080\u0001\u0010\nR\u0013\u0010\u0081\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0082\u0001\u0010\nR\u0013\u0010\u0083\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0084\u0001\u0010\nR\u0013\u0010\u0085\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0086\u0001\u0010\nR\u0013\u0010\u0087\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0088\u0001\u0010\nR\u0013\u0010\u0089\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u008a\u0001\u0010\nR\u0013\u0010\u008b\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u008c\u0001\u0010\nR\u0013\u0010\u008d\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u008e\u0001\u0010\nR\u0013\u0010\u008f\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0090\u0001\u0010\nR\u0013\u0010\u0091\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0092\u0001\u0010\nR \u0010\u0093\u0001\u001a\u00030\u0094\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0095\u0001\u0010\u0096\u0001\"\u0006\b\u0097\u0001\u0010\u0098\u0001R\u0013\u0010\u0099\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u009a\u0001\u0010\nR\u0013\u0010\u009b\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u009c\u0001\u0010\n¨\u0006È\u0001"}, d2 = {"Lcom/agog/mathdisplay/render/MTFontMathTable;", "", "font", "Lcom/agog/mathdisplay/render/MTFont;", "istreamotf", "Ljava/io/InputStream;", "(Lcom/agog/mathdisplay/render/MTFont;Ljava/io/InputStream;)V", "accentBaseHeight", "", "getAccentBaseHeight", "()F", "axisHeight", "getAxisHeight", "delimitedSubFormulaMinHeight", "getDelimitedSubFormulaMinHeight", "displayOperatorMinHeight", "getDisplayOperatorMinHeight", "flattenedAccentBaseHeight", "getFlattenedAccentBaseHeight", "getFont", "()Lcom/agog/mathdisplay/render/MTFont;", "fontSize", "getFontSize", "setFontSize", "(F)V", "fractionDelimiterDisplayStyleSize", "getFractionDelimiterDisplayStyleSize", "fractionDelimiterSize", "getFractionDelimiterSize", "fractionDenominatorDisplayStyleGapMin", "getFractionDenominatorDisplayStyleGapMin", "fractionDenominatorDisplayStyleShiftDown", "getFractionDenominatorDisplayStyleShiftDown", "fractionDenominatorGapMin", "getFractionDenominatorGapMin", "fractionDenominatorShiftDown", "getFractionDenominatorShiftDown", "fractionNumeratorDisplayStyleGapMin", "getFractionNumeratorDisplayStyleGapMin", "fractionNumeratorDisplayStyleShiftUp", "getFractionNumeratorDisplayStyleShiftUp", "fractionNumeratorGapMin", "getFractionNumeratorGapMin", "fractionNumeratorShiftUp", "getFractionNumeratorShiftUp", "fractionRuleThickness", "getFractionRuleThickness", "freeTypeMathTable", "Lcom/pvporbit/freetype/MTFreeTypeMathTable;", "getFreeTypeMathTable", "()Lcom/pvporbit/freetype/MTFreeTypeMathTable;", "setFreeTypeMathTable", "(Lcom/pvporbit/freetype/MTFreeTypeMathTable;)V", "freeface", "Lcom/pvporbit/freetype/Face;", "getFreeface", "()Lcom/pvporbit/freetype/Face;", "setFreeface", "(Lcom/pvporbit/freetype/Face;)V", "getIstreamotf", "()Ljava/io/InputStream;", "setIstreamotf", "(Ljava/io/InputStream;)V", "limitExtraAscenderDescender", "getLimitExtraAscenderDescender", "lowerLimitBaselineDropMin", "getLowerLimitBaselineDropMin", "lowerLimitGapMin", "getLowerLimitGapMin", "mathLeading", "getMathLeading", "minConnectorOverlap", "getMinConnectorOverlap", "overbarExtraAscender", "getOverbarExtraAscender", "overbarRuleThickness", "getOverbarRuleThickness", "overbarVerticalGap", "getOverbarVerticalGap", "radicalDegreeBottomRaisePercent", "getRadicalDegreeBottomRaisePercent", "radicalDisplayStyleVerticalGap", "getRadicalDisplayStyleVerticalGap", "radicalExtraAscender", "getRadicalExtraAscender", "radicalKernAfterDegree", "getRadicalKernAfterDegree", "radicalKernBeforeDegree", "getRadicalKernBeforeDegree", "radicalRuleThickness", "getRadicalRuleThickness", "radicalVerticalGap", "getRadicalVerticalGap", "scriptScaleDown", "getScriptScaleDown", "scriptScriptScaleDown", "getScriptScriptScaleDown", "skewedFractionHorizontalGap", "getSkewedFractionHorizontalGap", "skewedFractionVerticalGap", "getSkewedFractionVerticalGap", "spaceAfterScript", "getSpaceAfterScript", "stackBottomDisplayStyleShiftDown", "getStackBottomDisplayStyleShiftDown", "stackBottomShiftDown", "getStackBottomShiftDown", "stackDisplayStyleGapMin", "getStackDisplayStyleGapMin", "stackGapMin", "getStackGapMin", "stackTopDisplayStyleShiftUp", "getStackTopDisplayStyleShiftUp", "stackTopShiftUp", "getStackTopShiftUp", "stretchStackBottomShiftDown", "getStretchStackBottomShiftDown", "stretchStackGapAboveMin", "getStretchStackGapAboveMin", "stretchStackGapBelowMin", "getStretchStackGapBelowMin", "stretchStackTopShiftUp", "getStretchStackTopShiftUp", "subSuperscriptGapMin", "getSubSuperscriptGapMin", "subscriptBaselineDropMin", "getSubscriptBaselineDropMin", "subscriptShiftDown", "getSubscriptShiftDown", "subscriptTopMax", "getSubscriptTopMax", "superscriptBaselineDropMax", "getSuperscriptBaselineDropMax", "superscriptBottomMaxWithSubscript", "getSuperscriptBottomMaxWithSubscript", "superscriptBottomMin", "getSuperscriptBottomMin", "superscriptShiftUp", "getSuperscriptShiftUp", "superscriptShiftUpCramped", "getSuperscriptShiftUpCramped", "underbarExtraDescender", "getUnderbarExtraDescender", "underbarRuleThickness", "getUnderbarRuleThickness", "underbarVerticalGap", "getUnderbarVerticalGap", "unitsPerEm", "", "getUnitsPerEm", "()I", "setUnitsPerEm", "(I)V", "upperLimitBaselineRiseMin", "getUpperLimitBaselineRiseMin", "upperLimitGapMin", "getUpperLimitGapMin", "checkFontSize", "constantFromTable", "constName", "", "copyFontTableWithSize", "size", "fontUnitsBox", "Lcom/agog/mathdisplay/render/BoundingBox;", "b", "fontUnitsToPt", "fontUnits", "", "getAdvancesForGlyphs", "", "glyphs", "", "advances", "", "count", "(Ljava/util/List;[Ljava/lang/Float;I)V", "getBoundingRectsForGlyphs", "boundingRects", "(Ljava/util/List;[Lcom/agog/mathdisplay/render/BoundingBox;I)Lcom/agog/mathdisplay/render/BoundingBox;", "getGlyphForCodepoint", "codepoint", "getGlyphName", "gid", "getGlyphWithName", "glyphName", "getHorizontalVariantsForGlyph", "glyph", "Lcom/agog/mathdisplay/render/CGGlyph;", "getItalicCorrection", "getLargerGlyph", "getTopAccentAdjustment", "getVerticalGlyphAssemblyForGlyph", "Lcom/agog/mathdisplay/render/MTGlyphPart;", "getVerticalVariantsForGlyph", "muUnit", "percentFromTable", "percentName", "unionBounds", "u", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTFontMathTable {
    private final MTFont font;
    private float fontSize;
    public MTFreeTypeMathTable freeTypeMathTable;
    public Face freeface;
    private InputStream istreamotf;
    private int unitsPerEm;

    public final float getLimitExtraAscenderDescender() {
        return 0.0f;
    }

    public MTFontMathTable(MTFont font, InputStream inputStream) throws MathDisplayException {
        byte[] bytes;
        Intrinsics.checkNotNullParameter(font, "font");
        this.font = font;
        this.istreamotf = inputStream;
        this.unitsPerEm = 1;
        this.fontSize = font.getFontSize();
        InputStream inputStream2 = this.istreamotf;
        if (inputStream2 != null) {
            InputStream inputStream3 = inputStream2;
            try {
                InputStream inputStream4 = inputStream3;
                try {
                    try {
                        Intrinsics.checkNotNull(inputStream4);
                        bytes = ByteStreamsKt.readBytes(inputStream4);
                        try {
                            inputStream4.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Throwable th) {
                        if (inputStream4 != null) {
                            try {
                                inputStream4.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    if (inputStream4 != null) {
                        try {
                            inputStream4.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    bytes = null;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(inputStream3, null);
                Library libraryNewLibrary = FreeType.newLibrary();
                if (libraryNewLibrary == null) {
                    throw new MathDisplayException("Error initializing FreeType.");
                }
                Face faceNewFace = libraryNewLibrary.newFace(bytes, 0);
                Intrinsics.checkNotNullExpressionValue(faceNewFace, "library.newFace(barray, 0)");
                setFreeface(faceNewFace);
                checkFontSize();
                this.unitsPerEm = getFreeface().getUnitsPerEM();
                MTFreeTypeMathTable mTFreeTypeMathTableLoadMathTable = getFreeface().loadMathTable();
                Intrinsics.checkNotNullExpressionValue(mTFreeTypeMathTableLoadMathTable, "freeface.loadMathTable()");
                setFreeTypeMathTable(mTFreeTypeMathTableLoadMathTable);
            } catch (Throwable th2) {
                try {
                    throw th2;
                } catch (Throwable th3) {
                    CloseableKt.closeFinally(inputStream3, th2);
                    throw th3;
                }
            }
        }
    }

    public final MTFont getFont() {
        return this.font;
    }

    public final InputStream getIstreamotf() {
        return this.istreamotf;
    }

    public final void setIstreamotf(InputStream inputStream) {
        this.istreamotf = inputStream;
    }

    public final int getUnitsPerEm() {
        return this.unitsPerEm;
    }

    public final void setUnitsPerEm(int i) {
        this.unitsPerEm = i;
    }

    public final float getFontSize() {
        return this.fontSize;
    }

    public final void setFontSize(float f) {
        this.fontSize = f;
    }

    public final Face getFreeface() {
        Face face = this.freeface;
        if (face != null) {
            return face;
        }
        Intrinsics.throwUninitializedPropertyAccessException("freeface");
        return null;
    }

    public final void setFreeface(Face face) {
        Intrinsics.checkNotNullParameter(face, "<set-?>");
        this.freeface = face;
    }

    public final MTFreeTypeMathTable getFreeTypeMathTable() {
        MTFreeTypeMathTable mTFreeTypeMathTable = this.freeTypeMathTable;
        if (mTFreeTypeMathTable != null) {
            return mTFreeTypeMathTable;
        }
        Intrinsics.throwUninitializedPropertyAccessException("freeTypeMathTable");
        return null;
    }

    public final void setFreeTypeMathTable(MTFreeTypeMathTable mTFreeTypeMathTable) {
        Intrinsics.checkNotNullParameter(mTFreeTypeMathTable, "<set-?>");
        this.freeTypeMathTable = mTFreeTypeMathTable;
    }

    public final Face checkFontSize() {
        getFreeface().setCharSize(0, (int) (this.fontSize * 64), 0, 0);
        return getFreeface();
    }

    public final MTFontMathTable copyFontTableWithSize(float size) {
        MTFontMathTable mTFontMathTable = new MTFontMathTable(this.font, null);
        mTFontMathTable.fontSize = size;
        mTFontMathTable.unitsPerEm = this.unitsPerEm;
        mTFontMathTable.setFreeface(getFreeface());
        mTFontMathTable.setFreeTypeMathTable(getFreeTypeMathTable());
        return mTFontMathTable;
    }

    public final String getGlyphName(int gid) {
        String g = getFreeface().getGlyphName(gid);
        Intrinsics.checkNotNullExpressionValue(g, "g");
        return g;
    }

    public final int getGlyphWithName(String glyphName) {
        Intrinsics.checkNotNullParameter(glyphName, "glyphName");
        return getFreeface().getGlyphIndexByName(glyphName);
    }

    public final int getGlyphForCodepoint(int codepoint) {
        return getFreeface().getCharIndex(codepoint);
    }

    public final void getAdvancesForGlyphs(List<Integer> glyphs, Float[] advances, int count) {
        GlyphSlot.Advance advance;
        Intrinsics.checkNotNullParameter(glyphs, "glyphs");
        Intrinsics.checkNotNullParameter(advances, "advances");
        int i = 0;
        while (i < count) {
            int i2 = i + 1;
            if (!getFreeface().loadGlyph(glyphs.get(i).intValue(), 1) && (advance = getFreeface().getGlyphSlot().getAdvance()) != null) {
                advances[i] = Float.valueOf(fontUnitsToPt(advance.getX()));
            }
            i = i2;
        }
    }

    public final void unionBounds(BoundingBox u, BoundingBox b) {
        Intrinsics.checkNotNullParameter(u, "u");
        Intrinsics.checkNotNullParameter(b, "b");
        u.setLowerLeftX(Math.min(u.getLowerLeftX(), b.getLowerLeftX()));
        u.setLowerLeftY(Math.min(u.getLowerLeftY(), b.getLowerLeftY()));
        u.setUpperRightX(Math.max(u.getUpperRightX(), b.getUpperRightX()));
        u.setUpperRightY(Math.max(u.getUpperRightY(), b.getUpperRightY()));
    }

    public final BoundingBox getBoundingRectsForGlyphs(List<Integer> glyphs, BoundingBox[] boundingRects, int count) {
        Intrinsics.checkNotNullParameter(glyphs, "glyphs");
        BoundingBox boundingBox = new BoundingBox();
        int i = 0;
        while (i < count) {
            int i2 = i + 1;
            if (!getFreeface().loadGlyph(glyphs.get(i).intValue(), 1)) {
                BoundingBox boundingBox2 = new BoundingBox();
                GlyphMetrics metrics = getFreeface().getGlyphSlot().getMetrics();
                float fFontUnitsToPt = fontUnitsToPt(metrics.getWidth());
                float fFontUnitsToPt2 = fontUnitsToPt(metrics.getHeight());
                float fFontUnitsToPt3 = fontUnitsToPt(metrics.getHoriBearingX());
                float fFontUnitsToPt4 = fontUnitsToPt(metrics.getHoriBearingY());
                boundingBox2.setLowerLeftX(fFontUnitsToPt3);
                boundingBox2.setLowerLeftY(fFontUnitsToPt4 - fFontUnitsToPt2);
                boundingBox2.setUpperRightX(fFontUnitsToPt3 + fFontUnitsToPt);
                boundingBox2.setUpperRightY(fFontUnitsToPt4);
                unionBounds(boundingBox, boundingBox2);
                if (boundingRects != null) {
                    boundingRects[i] = boundingBox2;
                }
            }
            i = i2;
        }
        return boundingBox;
    }

    private final float fontUnitsToPt(long fontUnits) {
        return (fontUnits * this.fontSize) / this.unitsPerEm;
    }

    private final float fontUnitsToPt(int fontUnits) {
        return (fontUnits * this.fontSize) / this.unitsPerEm;
    }

    public final BoundingBox fontUnitsBox(BoundingBox b) {
        Intrinsics.checkNotNullParameter(b, "b");
        BoundingBox boundingBox = new BoundingBox();
        boundingBox.setLowerLeftX(fontUnitsToPt((int) b.getLowerLeftX()));
        boundingBox.setLowerLeftY(fontUnitsToPt((int) b.getLowerLeftY()));
        boundingBox.setUpperRightX(fontUnitsToPt((int) b.getUpperRightX()));
        boundingBox.setUpperRightY(fontUnitsToPt((int) b.getUpperRightY()));
        return boundingBox;
    }

    public final float muUnit() {
        return this.fontSize / 18;
    }

    public final float constantFromTable(String constName) {
        Intrinsics.checkNotNullParameter(constName, "constName");
        return fontUnitsToPt(getFreeTypeMathTable().getConstant(constName));
    }

    public final float percentFromTable(String percentName) {
        Intrinsics.checkNotNullParameter(percentName, "percentName");
        return getFreeTypeMathTable().getConstant(percentName) / 100.0f;
    }

    public final float getFractionNumeratorDisplayStyleShiftUp() {
        return constantFromTable("FractionNumeratorDisplayStyleShiftUp");
    }

    public final float getFractionNumeratorShiftUp() {
        return constantFromTable("FractionNumeratorShiftUp");
    }

    public final float getFractionDenominatorDisplayStyleShiftDown() {
        return constantFromTable("FractionDenominatorDisplayStyleShiftDown");
    }

    public final float getFractionDenominatorShiftDown() {
        return constantFromTable("FractionDenominatorShiftDown");
    }

    public final float getFractionNumeratorDisplayStyleGapMin() {
        return constantFromTable("FractionNumDisplayStyleGapMin");
    }

    public final float getFractionNumeratorGapMin() {
        return constantFromTable("FractionNumeratorGapMin");
    }

    public final float getFractionDenominatorDisplayStyleGapMin() {
        return constantFromTable("FractionDenomDisplayStyleGapMin");
    }

    public final float getFractionDenominatorGapMin() {
        return constantFromTable("FractionDenominatorGapMin");
    }

    public final float getFractionRuleThickness() {
        return constantFromTable("FractionRuleThickness");
    }

    public final float getSkewedFractionHorizontalGap() {
        return constantFromTable("SkewedFractionHorizontalGap");
    }

    public final float getSkewedFractionVerticalGap() {
        return constantFromTable("SkewedFractionVerticalGap");
    }

    public final float getFractionDelimiterSize() {
        return this.fontSize * 1.01f;
    }

    public final float getFractionDelimiterDisplayStyleSize() {
        return this.fontSize * 2.39f;
    }

    public final float getSuperscriptShiftUp() {
        return constantFromTable("SuperscriptShiftUp");
    }

    public final float getSuperscriptShiftUpCramped() {
        return constantFromTable("SuperscriptShiftUpCramped");
    }

    public final float getSubscriptShiftDown() {
        return constantFromTable("SubscriptShiftDown");
    }

    public final float getSuperscriptBaselineDropMax() {
        return constantFromTable("SuperscriptBaselineDropMax");
    }

    public final float getSubscriptBaselineDropMin() {
        return constantFromTable("SubscriptBaselineDropMin");
    }

    public final float getSuperscriptBottomMin() {
        return constantFromTable("SuperscriptBottomMin");
    }

    public final float getSubscriptTopMax() {
        return constantFromTable("SubscriptTopMax");
    }

    public final float getSubSuperscriptGapMin() {
        return constantFromTable("SubSuperscriptGapMin");
    }

    public final float getSuperscriptBottomMaxWithSubscript() {
        return constantFromTable("SuperscriptBottomMaxWithSubscript");
    }

    public final float getSpaceAfterScript() {
        return constantFromTable("SpaceAfterScript");
    }

    public final float getRadicalRuleThickness() {
        return constantFromTable("RadicalRuleThickness");
    }

    public final float getRadicalExtraAscender() {
        return constantFromTable("RadicalExtraAscender");
    }

    public final float getRadicalVerticalGap() {
        return constantFromTable("RadicalVerticalGap");
    }

    public final float getRadicalDisplayStyleVerticalGap() {
        return constantFromTable("RadicalDisplayStyleVerticalGap");
    }

    public final float getRadicalKernBeforeDegree() {
        return constantFromTable("RadicalKernBeforeDegree");
    }

    public final float getRadicalKernAfterDegree() {
        return constantFromTable("RadicalKernAfterDegree");
    }

    public final float getRadicalDegreeBottomRaisePercent() {
        return percentFromTable("RadicalDegreeBottomRaisePercent");
    }

    public final float getUpperLimitGapMin() {
        return constantFromTable("UpperLimitGapMin");
    }

    public final float getUpperLimitBaselineRiseMin() {
        return constantFromTable("UpperLimitBaselineRiseMin");
    }

    public final float getLowerLimitGapMin() {
        return constantFromTable("LowerLimitGapMin");
    }

    public final float getLowerLimitBaselineDropMin() {
        return constantFromTable("LowerLimitBaselineDropMin");
    }

    public final float getAxisHeight() {
        return constantFromTable("AxisHeight");
    }

    public final float getScriptScaleDown() {
        return percentFromTable("ScriptPercentScaleDown");
    }

    public final float getScriptScriptScaleDown() {
        return percentFromTable("ScriptScriptPercentScaleDown");
    }

    public final float getMathLeading() {
        return constantFromTable("MathLeading");
    }

    public final float getDelimitedSubFormulaMinHeight() {
        return constantFromTable("DelimitedSubFormulaMinHeight");
    }

    public final float getAccentBaseHeight() {
        return constantFromTable("AccentBaseHeight");
    }

    public final float getFlattenedAccentBaseHeight() {
        return constantFromTable("FlattenedAccentBaseHeight");
    }

    public final float getDisplayOperatorMinHeight() {
        return constantFromTable("DisplayOperatorMinHeight");
    }

    public final float getOverbarExtraAscender() {
        return constantFromTable("OverbarExtraAscender");
    }

    public final float getOverbarRuleThickness() {
        return constantFromTable("OverbarRuleThickness");
    }

    public final float getOverbarVerticalGap() {
        return constantFromTable("OverbarVerticalGap");
    }

    public final float getUnderbarExtraDescender() {
        return constantFromTable("UnderbarExtraDescender");
    }

    public final float getUnderbarRuleThickness() {
        return constantFromTable("UnderbarRuleThickness");
    }

    public final float getUnderbarVerticalGap() {
        return constantFromTable("UnderbarVerticalGap");
    }

    public final float getStackBottomDisplayStyleShiftDown() {
        return constantFromTable("StackBottomDisplayStyleShiftDown");
    }

    public final float getStackBottomShiftDown() {
        return constantFromTable("StackBottomShiftDown");
    }

    public final float getStackDisplayStyleGapMin() {
        return constantFromTable("StackDisplayStyleGapMin");
    }

    public final float getStackGapMin() {
        return constantFromTable("StackGapMin");
    }

    public final float getStackTopDisplayStyleShiftUp() {
        return constantFromTable("StackTopDisplayStyleShiftUp");
    }

    public final float getStackTopShiftUp() {
        return constantFromTable("StackTopShiftUp");
    }

    public final float getStretchStackBottomShiftDown() {
        return constantFromTable("StretchStackBottomShiftDown");
    }

    public final float getStretchStackGapAboveMin() {
        return constantFromTable("StretchStackGapAboveMin");
    }

    public final float getStretchStackGapBelowMin() {
        return constantFromTable("StretchStackGapBelowMin");
    }

    public final float getStretchStackTopShiftUp() {
        return constantFromTable("StretchStackTopShiftUp");
    }

    public final List<Integer> getVerticalVariantsForGlyph(CGGlyph glyph) {
        Intrinsics.checkNotNullParameter(glyph, "glyph");
        return getFreeTypeMathTable().getVerticalVariantsForGlyph(glyph.getGid());
    }

    public final List<Integer> getHorizontalVariantsForGlyph(CGGlyph glyph) {
        Intrinsics.checkNotNullParameter(glyph, "glyph");
        return getFreeTypeMathTable().getHorizontalVariantsForGlyph(glyph.getGid());
    }

    public final int getLargerGlyph(int glyph) {
        String glyphName = this.font.getGlyphName(glyph);
        Iterator<Integer> it = getFreeTypeMathTable().getVerticalVariantsForGlyph(glyph).iterator();
        while (it.hasNext()) {
            String glyphName2 = this.font.getGlyphName(it.next().intValue());
            if (!Intrinsics.areEqual(glyphName2, glyphName)) {
                return this.font.getGlyphWithName(glyphName2);
            }
        }
        return glyph;
    }

    public final float getItalicCorrection(int gid) {
        return fontUnitsToPt(getFreeTypeMathTable().getitalicCorrection(gid));
    }

    public final float getTopAccentAdjustment(int glyph) {
        Integer num = getFreeTypeMathTable().gettopAccentAttachment(glyph);
        if (num != null) {
            return fontUnitsToPt(num.intValue());
        }
        Integer[] numArr = {Integer.valueOf(glyph)};
        Float[] fArr = {Float.valueOf(0.0f)};
        getAdvancesForGlyphs(ArraysKt.toList(numArr), fArr, 1);
        return fArr[0].floatValue() / 2;
    }

    public final float getMinConnectorOverlap() {
        return fontUnitsToPt(getFreeTypeMathTable().getMinConnectorOverlap());
    }

    public final List<MTGlyphPart> getVerticalGlyphAssemblyForGlyph(int glyph) {
        MTFreeTypeMathTable.GlyphPartRecord[] verticalGlyphAssemblyForGlyph = getFreeTypeMathTable().getVerticalGlyphAssemblyForGlyph(glyph);
        if (verticalGlyphAssemblyForGlyph == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = ArrayIteratorKt.iterator(verticalGlyphAssemblyForGlyph);
        while (it.hasNext()) {
            MTFreeTypeMathTable.GlyphPartRecord glyphPartRecord = (MTFreeTypeMathTable.GlyphPartRecord) it.next();
            MTGlyphPart mTGlyphPart = new MTGlyphPart(0, 0.0f, 0.0f, 0.0f, false, 31, null);
            mTGlyphPart.setFullAdvance(fontUnitsToPt(glyphPartRecord.getFullAdvance()));
            mTGlyphPart.setEndConnectorLength(fontUnitsToPt(glyphPartRecord.getEndConnectorLength()));
            mTGlyphPart.setStartConnectorLength(fontUnitsToPt(glyphPartRecord.getStartConnectorLength()));
            boolean z = true;
            if (glyphPartRecord.getPartFlags() != 1) {
                z = false;
            }
            mTGlyphPart.setExtender(z);
            mTGlyphPart.setGlyph(glyphPartRecord.getGlyph());
            arrayList.add(mTGlyphPart);
        }
        return arrayList;
    }
}
