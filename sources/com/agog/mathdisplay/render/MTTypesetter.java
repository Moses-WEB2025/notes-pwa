package com.agog.mathdisplay.render;

import android.graphics.Color;
import com.agog.mathdisplay.parse.MTAccent;
import com.agog.mathdisplay.parse.MTColumnAlignment;
import com.agog.mathdisplay.parse.MTFraction;
import com.agog.mathdisplay.parse.MTInner;
import com.agog.mathdisplay.parse.MTLargeOperator;
import com.agog.mathdisplay.parse.MTLineStyle;
import com.agog.mathdisplay.parse.MTMathAtom;
import com.agog.mathdisplay.parse.MTMathAtomType;
import com.agog.mathdisplay.parse.MTMathColor;
import com.agog.mathdisplay.parse.MTMathList;
import com.agog.mathdisplay.parse.MTMathSpace;
import com.agog.mathdisplay.parse.MTMathStyle;
import com.agog.mathdisplay.parse.MTMathTable;
import com.agog.mathdisplay.parse.MTOverLine;
import com.agog.mathdisplay.parse.MTRadical;
import com.agog.mathdisplay.parse.MTUnderLine;
import com.agog.mathdisplay.parse.MathDisplayException;
import com.agog.mathdisplay.parse.NSRange;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.onetrack.b.m;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTTypesetter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000è\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 \u009d\u00012\u00020\u0001:\u0002\u009d\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u0018\u00107\u001a\u00020&2\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;H\u0002J\b\u0010<\u001a\u00020=H\u0002J\u001a\u0010>\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010\u00102\u0006\u0010A\u001a\u00020BH\u0002J \u0010C\u001a\u00020&2\u0006\u00108\u001a\u00020&2\u0006\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020GH\u0002J\u001a\u0010H\u001a\u0004\u0018\u00010I2\u0006\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020GH\u0002J:\u0010M\u001a\u00020G2\f\u0010N\u001a\b\u0012\u0004\u0012\u00020P0O2\u0006\u0010L\u001a\u00020G2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020R0\u000f2\f\u0010S\u001a\b\u0012\u0004\u0012\u00020G0\u000fH\u0002J\u0016\u0010T\u001a\u00020?2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00100OH\u0002J\u0006\u0010V\u001a\u00020GJ\u000e\u0010W\u001a\u00020G2\u0006\u0010X\u001a\u00020\u0007J\u0018\u0010Y\u001a\u00020K2\u0006\u0010J\u001a\u00020K2\u0006\u0010Z\u001a\u00020GH\u0002J\u0018\u0010[\u001a\u00020&2\u0006\u0010\\\u001a\u00020\u00162\u0006\u0010L\u001a\u00020GH\u0002J\u0018\u0010]\u001a\u00020K2\u0006\u0010J\u001a\u00020K2\u0006\u0010^\u001a\u00020GH\u0002J\u0006\u0010_\u001a\u00020GJ\b\u0010`\u001a\u00020\u0005H\u0002J\u0012\u0010a\u001a\u00020G2\b\u0010b\u001a\u0004\u0018\u00010cH\u0002J\u0012\u0010d\u001a\u00020G2\b\u0010b\u001a\u0004\u0018\u00010cH\u0002J\u0018\u0010e\u001a\u00020G2\u0006\u0010f\u001a\u00020B2\u0006\u0010g\u001a\u00020BH\u0002J\u0010\u0010h\u001a\u00020&2\u0006\u0010i\u001a\u00020GH\u0002J \u0010j\u001a\u00020G2\u0006\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020G2\u0006\u0010n\u001a\u00020KH\u0002J\u0010\u0010o\u001a\u00020R2\u0006\u0010p\u001a\u00020qH\u0002J\u0018\u0010r\u001a\u00020G2\u0006\u0010.\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010s\u001a\u00020\u00072\u0006\u0010k\u001a\u00020lH\u0002J\u0012\u0010t\u001a\u0004\u0018\u00010&2\u0006\u0010k\u001a\u00020lH\u0002J\u0010\u0010u\u001a\u00020&2\u0006\u0010:\u001a\u00020;H\u0002J\u0010\u0010v\u001a\u00020&2\u0006\u0010D\u001a\u00020EH\u0002J\u0010\u0010w\u001a\u00020&2\u0006\u0010x\u001a\u00020yH\u0002J\u0012\u0010z\u001a\u0004\u0018\u00010&2\u0006\u0010{\u001a\u00020|H\u0002J\u001a\u0010}\u001a\u00020~2\u0007\u0010\u007f\u001a\u00030\u0080\u00012\u0007\u0010\u0081\u0001\u001a\u00020\u001cH\u0002J:\u0010\u0082\u0001\u001a\u00030\u0083\u00012\u000e\u0010\u0084\u0001\u001a\t\u0012\u0004\u0012\u00020&0\u0085\u00012\b\u0010\u0086\u0001\u001a\u00030\u0087\u00012\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u00020G0\u0085\u0001H\u0002¢\u0006\u0003\u0010\u0089\u0001J+\u0010\u008a\u0001\u001a\u00020?2\u0007\u0010\u008b\u0001\u001a\u00020\u00102\u0006\u00108\u001a\u00020&2\u0007\u0010\u008c\u0001\u001a\u00020R2\u0006\u0010F\u001a\u00020GH\u0002J\u0013\u0010\u008d\u0001\u001a\u00020&2\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001H\u0002J\u0015\u0010\u008e\u0001\u001a\u0004\u0018\u00010&2\b\u0010\u008f\u0001\u001a\u00030\u0090\u0001H\u0002J\u0007\u0010\u0091\u0001\u001a\u00020GJ\u000f\u0010\u0092\u0001\u001a\u00020G2\u0006\u0010X\u001a\u00020\u0007J\"\u0010\u0093\u0001\u001a\u00020?2\r\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u00020&0\u000f2\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001H\u0002J\t\u0010\u0095\u0001\u001a\u00020GH\u0002J\t\u0010\u0096\u0001\u001a\u00020\u0005H\u0002J\u0007\u0010\u0097\u0001\u001a\u00020GJ\t\u0010\u0098\u0001\u001a\u00020\u0007H\u0002J\t\u0010\u0099\u0001\u001a\u00020\u0007H\u0002J\t\u0010\u009a\u0001\u001a\u00020GH\u0002J7\u0010\u009b\u0001\u001a\u0010\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020&0\u0085\u00010\u0085\u00012\b\u0010\u0086\u0001\u001a\u00030\u0087\u00012\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u00020G0\u0085\u0001H\u0002¢\u0006\u0003\u0010\u009c\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R \u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0012\"\u0004\b(\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u000b\"\u0004\b,\u0010\rR$\u0010.\u001a\u00020\u00052\u0006\u0010-\u001a\u00020\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001a\u00103\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010*\"\u0004\b5\u00106¨\u0006\u009e\u0001"}, d2 = {"Lcom/agog/mathdisplay/render/MTTypesetter;", "", "font", "Lcom/agog/mathdisplay/render/MTFont;", "linestyle", "Lcom/agog/mathdisplay/parse/MTLineStyle;", "cramped", "", "spaced", "(Lcom/agog/mathdisplay/render/MTFont;Lcom/agog/mathdisplay/parse/MTLineStyle;ZZ)V", "getCramped", "()Z", "setCramped", "(Z)V", "currentAtoms", "", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "getCurrentAtoms", "()Ljava/util/List;", "setCurrentAtoms", "(Ljava/util/List;)V", "currentLine", "", "getCurrentLine", "()Ljava/lang/String;", "setCurrentLine", "(Ljava/lang/String;)V", "currentLineIndexRange", "Lcom/agog/mathdisplay/parse/NSRange;", "getCurrentLineIndexRange", "()Lcom/agog/mathdisplay/parse/NSRange;", "setCurrentLineIndexRange", "(Lcom/agog/mathdisplay/parse/NSRange;)V", "currentPosition", "Lcom/agog/mathdisplay/render/CGPoint;", "getCurrentPosition", "()Lcom/agog/mathdisplay/render/CGPoint;", "displayAtoms", "Lcom/agog/mathdisplay/render/MTDisplay;", "getDisplayAtoms", "setDisplayAtoms", "getFont", "()Lcom/agog/mathdisplay/render/MTFont;", "getSpaced", "setSpaced", "value", "style", "getStyle", "()Lcom/agog/mathdisplay/parse/MTLineStyle;", "setStyle", "(Lcom/agog/mathdisplay/parse/MTLineStyle;)V", "styleFont", "getStyleFont", "setStyleFont", "(Lcom/agog/mathdisplay/render/MTFont;)V", "addDelimitersToFractionDisplay", "display", "Lcom/agog/mathdisplay/render/MTFractionDisplay;", "frac", "Lcom/agog/mathdisplay/parse/MTFraction;", "addDisplayLine", "Lcom/agog/mathdisplay/render/MTCTLineDisplay;", "addInterElementSpace", "", "prevNode", "currentType", "Lcom/agog/mathdisplay/parse/MTMathAtomType;", "addLimitsToDisplay", "op", "Lcom/agog/mathdisplay/parse/MTLargeOperator;", "delta", "", "constructGlyph", "Lcom/agog/mathdisplay/render/MTGlyphConstructionDisplay;", "glyph", "Lcom/agog/mathdisplay/render/CGGlyph;", "glyphHeight", "constructGlyphWithParts", "parts", "", "Lcom/agog/mathdisplay/render/MTGlyphPart;", "glyphs", "", "offsets", "createDisplayAtoms", "preprocessed", "denominatorGapMin", "denominatorShiftDown", "hasRule", "findGlyph", Layer.KEY_HEIGHT, "findGlyphForBoundary", "delimiter", "findVariantGlyph", "maxWidth", "fractionDelimiterHeight", "fractionStyle", "getBboxDetailsAscent", "bbox", "Lcom/agog/mathdisplay/render/BoundingBox;", "getBboxDetailsDescent", "getInterElementSpace", "left", EditStat.ELEMENT_RIGHT, "getRadicalGlyphWithHeight", "radicalHeight", "getSkew", "accent", "Lcom/agog/mathdisplay/parse/MTAccent;", "accenteeWidth", "accentGlyph", "getSpacingInMu", "type", "Lcom/agog/mathdisplay/render/MTInterElementSpaceType;", "getStyleSize", "isSingleCharAccentee", "makeAccent", "makeFraction", "makeLargeOp", "makeLeftRight", "inner", "Lcom/agog/mathdisplay/parse/MTInner;", "makeOverline", "over", "Lcom/agog/mathdisplay/parse/MTOverLine;", "makeRadical", "Lcom/agog/mathdisplay/render/MTRadicalDisplay;", "radicand", "Lcom/agog/mathdisplay/parse/MTMathList;", "range", "makeRowWithColumns", "Lcom/agog/mathdisplay/render/MTMathListDisplay;", "cols", "", HtmlParser.TableElement.TAG, "Lcom/agog/mathdisplay/parse/MTMathTable;", "columnWidths", "([Lcom/agog/mathdisplay/render/MTDisplay;Lcom/agog/mathdisplay/parse/MTMathTable;[Ljava/lang/Float;)Lcom/agog/mathdisplay/render/MTMathListDisplay;", "makeScripts", "atom", "index", "makeTable", "makeUnderline", "under", "Lcom/agog/mathdisplay/parse/MTUnderLine;", "numeratorGapMin", "numeratorShiftUp", "positionRows", "rows", "radicalVerticalGap", "scriptStyle", "stackGapMin", "subScriptCramped", "superScriptCramped", "superScriptShiftUp", "typesetCells", "(Lcom/agog/mathdisplay/parse/MTMathTable;[Ljava/lang/Float;)[[Lcom/agog/mathdisplay/render/MTDisplay;", "Companion", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTTypesetter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private boolean cramped;
    private List<MTMathAtom> currentAtoms;
    private String currentLine;
    private NSRange currentLineIndexRange;
    private final CGPoint currentPosition;
    private List<MTDisplay> displayAtoms;
    private final MTFont font;
    private boolean spaced;
    private MTLineStyle style;
    private MTFont styleFont;

    /* JADX INFO: compiled from: MTTypesetter.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;

        static {
            int[] iArr = new int[MTLineStyle.values().length];
            iArr[MTLineStyle.KMTLineStyleDisplay.ordinal()] = 1;
            iArr[MTLineStyle.KMTLineStyleText.ordinal()] = 2;
            iArr[MTLineStyle.KMTLineStyleScript.ordinal()] = 3;
            iArr[MTLineStyle.KMTLineStyleScriptScript.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[MTMathAtomType.values().length];
            iArr2[MTMathAtomType.KMTMathAtomNone.ordinal()] = 1;
            iArr2[MTMathAtomType.KMTMathAtomNumber.ordinal()] = 2;
            iArr2[MTMathAtomType.KMTMathAtomVariable.ordinal()] = 3;
            iArr2[MTMathAtomType.KMTMathAtomUnaryOperator.ordinal()] = 4;
            iArr2[MTMathAtomType.KMTMathAtomBoundary.ordinal()] = 5;
            iArr2[MTMathAtomType.KMTMathAtomSpace.ordinal()] = 6;
            iArr2[MTMathAtomType.KMTMathAtomStyle.ordinal()] = 7;
            iArr2[MTMathAtomType.KMTMathAtomColor.ordinal()] = 8;
            iArr2[MTMathAtomType.KMTMathAtomRadical.ordinal()] = 9;
            iArr2[MTMathAtomType.KMTMathAtomFraction.ordinal()] = 10;
            iArr2[MTMathAtomType.KMTMathAtomLargeOperator.ordinal()] = 11;
            iArr2[MTMathAtomType.KMTMathAtomInner.ordinal()] = 12;
            iArr2[MTMathAtomType.KMTMathAtomUnderline.ordinal()] = 13;
            iArr2[MTMathAtomType.KMTMathAtomOverline.ordinal()] = 14;
            iArr2[MTMathAtomType.KMTMathAtomAccent.ordinal()] = 15;
            iArr2[MTMathAtomType.KMTMathAtomTable.ordinal()] = 16;
            iArr2[MTMathAtomType.KMTMathAtomOrdinary.ordinal()] = 17;
            iArr2[MTMathAtomType.KMTMathAtomBinaryOperator.ordinal()] = 18;
            iArr2[MTMathAtomType.KMTMathAtomRelation.ordinal()] = 19;
            iArr2[MTMathAtomType.KMTMathAtomOpen.ordinal()] = 20;
            iArr2[MTMathAtomType.KMTMathAtomClose.ordinal()] = 21;
            iArr2[MTMathAtomType.KMTMathAtomPlaceholder.ordinal()] = 22;
            iArr2[MTMathAtomType.KMTMathAtomPunctuation.ordinal()] = 23;
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[MTInterElementSpaceType.values().length];
            iArr3[MTInterElementSpaceType.KMTSpaceInvalid.ordinal()] = 1;
            iArr3[MTInterElementSpaceType.KMTSpaceNone.ordinal()] = 2;
            iArr3[MTInterElementSpaceType.KMTSpaceThin.ordinal()] = 3;
            iArr3[MTInterElementSpaceType.KMTSpaceNSThin.ordinal()] = 4;
            iArr3[MTInterElementSpaceType.KMTSpaceNSMedium.ordinal()] = 5;
            iArr3[MTInterElementSpaceType.KMTSpaceNSThick.ordinal()] = 6;
            $EnumSwitchMapping$2 = iArr3;
            int[] iArr4 = new int[MTColumnAlignment.values().length];
            iArr4[MTColumnAlignment.KMTColumnAlignmentRight.ordinal()] = 1;
            iArr4[MTColumnAlignment.KMTColumnAlignmentCenter.ordinal()] = 2;
            iArr4[MTColumnAlignment.KMTColumnAlignmentLeft.ordinal()] = 3;
            $EnumSwitchMapping$3 = iArr4;
        }
    }

    private final boolean subScriptCramped() {
        return true;
    }

    public MTTypesetter(MTFont font, MTLineStyle linestyle, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(font, "font");
        Intrinsics.checkNotNullParameter(linestyle, "linestyle");
        this.font = font;
        this.cramped = z;
        this.spaced = z2;
        this.displayAtoms = new ArrayList();
        this.currentPosition = new CGPoint(0.0f, 0.0f);
        this.currentLine = "";
        this.currentAtoms = new ArrayList();
        this.currentLineIndexRange = new NSRange(0, 0, 3, null);
        this.styleFont = font;
        this.style = MTLineStyle.KMTLineStyleDisplay;
        setStyle(linestyle);
    }

    public /* synthetic */ MTTypesetter(MTFont mTFont, MTLineStyle mTLineStyle, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(mTFont, mTLineStyle, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2);
    }

    public final boolean getCramped() {
        return this.cramped;
    }

    public final MTFont getFont() {
        return this.font;
    }

    public final boolean getSpaced() {
        return this.spaced;
    }

    public final void setCramped(boolean z) {
        this.cramped = z;
    }

    public final void setSpaced(boolean z) {
        this.spaced = z;
    }

    public final List<MTDisplay> getDisplayAtoms() {
        return this.displayAtoms;
    }

    public final void setDisplayAtoms(List<MTDisplay> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.displayAtoms = list;
    }

    public final CGPoint getCurrentPosition() {
        return this.currentPosition;
    }

    public final String getCurrentLine() {
        return this.currentLine;
    }

    public final void setCurrentLine(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.currentLine = str;
    }

    public final List<MTMathAtom> getCurrentAtoms() {
        return this.currentAtoms;
    }

    public final void setCurrentAtoms(List<MTMathAtom> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.currentAtoms = list;
    }

    public final NSRange getCurrentLineIndexRange() {
        return this.currentLineIndexRange;
    }

    public final void setCurrentLineIndexRange(NSRange nSRange) {
        Intrinsics.checkNotNullParameter(nSRange, "<set-?>");
        this.currentLineIndexRange = nSRange;
    }

    public final MTFont getStyleFont() {
        return this.styleFont;
    }

    public final void setStyleFont(MTFont mTFont) {
        Intrinsics.checkNotNullParameter(mTFont, "<set-?>");
        this.styleFont = mTFont;
    }

    public final MTLineStyle getStyle() {
        return this.style;
    }

    public final void setStyle(MTLineStyle value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.style = value;
        MTFont mTFont = this.font;
        this.styleFont = mTFont.copyFontWithSize(getStyleSize(value, mTFont));
    }

    /* JADX INFO: compiled from: MTTypesetter.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0006¨\u0006\u0012"}, d2 = {"Lcom/agog/mathdisplay/render/MTTypesetter$Companion;", "", "()V", "createLineForMathList", "Lcom/agog/mathdisplay/render/MTMathListDisplay;", "mathList", "Lcom/agog/mathdisplay/parse/MTMathList;", "font", "Lcom/agog/mathdisplay/render/MTFont;", "style", "Lcom/agog/mathdisplay/parse/MTLineStyle;", "cramped", "", "spaced", "preprocessMathList", "", "Lcom/agog/mathdisplay/parse/MTMathAtom;", m.g, "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MTMathListDisplay createLineForMathList(MTMathList mathList, MTFont font, MTLineStyle style) {
            Intrinsics.checkNotNullParameter(mathList, "mathList");
            Intrinsics.checkNotNullParameter(font, "font");
            Intrinsics.checkNotNullParameter(style, "style");
            return createLineForMathList(mathList.finalized(), font, style, false);
        }

        public final MTMathListDisplay createLineForMathList(MTMathList mathList, MTFont font, MTLineStyle style, boolean cramped) {
            Intrinsics.checkNotNullParameter(mathList, "mathList");
            Intrinsics.checkNotNullParameter(font, "font");
            Intrinsics.checkNotNullParameter(style, "style");
            return createLineForMathList(mathList, font, style, cramped, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final MTMathListDisplay createLineForMathList(MTMathList mathList, MTFont font, MTLineStyle style, boolean cramped, boolean spaced) throws MathDisplayException {
            NSRange indexRange;
            List<MTMathAtom> listPreprocessMathList = preprocessMathList(mathList);
            MTTypesetter mTTypesetter = new MTTypesetter(font, style, cramped, spaced);
            mTTypesetter.createDisplayAtoms(listPreprocessMathList);
            MTMathAtom mTMathAtom = (MTMathAtom) CollectionsKt.lastOrNull((List) mathList.getAtoms());
            return new MTMathListDisplay(mTTypesetter.getDisplayAtoms(), new NSRange(0, (mTMathAtom == null || (indexRange = mTMathAtom.getIndexRange()) == null) ? 0 : indexRange.getMaxrange()));
        }

        public final List<MTMathAtom> preprocessMathList(MTMathList ml) {
            Intrinsics.checkNotNullParameter(ml, "ml");
            ArrayList arrayList = new ArrayList();
            MTMathAtom mTMathAtom = null;
            for (MTMathAtom mTMathAtom2 : ml.getAtoms()) {
                if (mTMathAtom2.getType() == MTMathAtomType.KMTMathAtomVariable || mTMathAtom2.getType() == MTMathAtomType.KMTMathAtomNumber) {
                    String strChangeFont = MTCharsetKt.changeFont(mTMathAtom2.getNucleus(), mTMathAtom2.getFontStyle());
                    mTMathAtom2.setType(MTMathAtomType.KMTMathAtomOrdinary);
                    mTMathAtom2.setNucleus(strChangeFont);
                } else if (mTMathAtom2.getType() == MTMathAtomType.KMTMathAtomUnaryOperator) {
                    mTMathAtom2.setType(MTMathAtomType.KMTMathAtomOrdinary);
                }
                if (mTMathAtom2.getType() == MTMathAtomType.KMTMathAtomOrdinary && mTMathAtom != null && mTMathAtom.getType() == MTMathAtomType.KMTMathAtomOrdinary && mTMathAtom.getSubScript() == null && mTMathAtom.getSuperScript() == null) {
                    mTMathAtom.fuse(mTMathAtom2);
                } else {
                    arrayList.add(mTMathAtom2);
                    mTMathAtom = mTMathAtom2;
                }
            }
            return arrayList;
        }
    }

    private final float getStyleSize(MTLineStyle style, MTFont font) {
        float scriptScaleDown;
        float fontSize = font.getFontSize();
        int i = WhenMappings.$EnumSwitchMapping$0[style.ordinal()];
        if (i == 1 || i == 2) {
            return fontSize;
        }
        if (i == 3) {
            scriptScaleDown = font.getMathTable().getScriptScaleDown();
        } else {
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            scriptScaleDown = font.getMathTable().getScriptScriptScaleDown();
        }
        return fontSize * scriptScaleDown;
    }

    private final void addInterElementSpace(MTMathAtom prevNode, MTMathAtomType currentType) throws MathDisplayException {
        float interElementSpace;
        if (prevNode != null) {
            interElementSpace = getInterElementSpace(prevNode.getType(), currentType);
        } else {
            interElementSpace = this.spaced ? getInterElementSpace(MTMathAtomType.KMTMathAtomOpen, currentType) : 0.0f;
        }
        CGPoint cGPoint = this.currentPosition;
        cGPoint.setX(cGPoint.getX() + interElementSpace);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void createDisplayAtoms(List<? extends MTMathAtom> preprocessed) throws MathDisplayException {
        MTMathListDisplay mTMathListDisplayMakeLeftRight;
        MTMathAtomType type = MTMathAtomType.KMTMathAtomNone;
        MTMathAtom mTMathAtom = null;
        for (MTMathAtom mTMathAtom2 : preprocessed) {
            switch (WhenMappings.$EnumSwitchMapping$1[mTMathAtom2.getType().ordinal()]) {
                case 2:
                case 3:
                case 4:
                    throw new MathDisplayException("These types should never show here as they are removed by preprocessing");
                case 5:
                    throw new MathDisplayException("A boundary atom should never be inside a mathlist.");
                case 6:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    CGPoint cGPoint = this.currentPosition;
                    cGPoint.setX(cGPoint.getX() + (((MTMathSpace) mTMathAtom2).getSpace() * this.styleFont.getMathTable().muUnit()));
                    continue;
                case 7:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    setStyle(((MTMathStyle) mTMathAtom2).getStyle());
                    continue;
                case 8:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    MTMathColor mTMathColor = (MTMathColor) mTMathAtom2;
                    if (mTMathColor.getInnerList() != null) {
                        Companion companion = INSTANCE;
                        MTMathList innerList = mTMathColor.getInnerList();
                        Intrinsics.checkNotNull(innerList);
                        MTMathListDisplay mTMathListDisplayCreateLineForMathList = companion.createLineForMathList(innerList, this.font, this.style);
                        mTMathListDisplayCreateLineForMathList.setLocalTextColor(Color.parseColor(mTMathColor.getColorString()));
                        mTMathListDisplayCreateLineForMathList.setPosition(this.currentPosition);
                        CGPoint cGPoint2 = this.currentPosition;
                        cGPoint2.setX(cGPoint2.getX() + mTMathListDisplayCreateLineForMathList.getWidth());
                        this.displayAtoms.add(mTMathListDisplayCreateLineForMathList);
                    }
                    break;
                case 9:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    MTRadical mTRadical = (MTRadical) mTMathAtom2;
                    addInterElementSpace(mTMathAtom, MTMathAtomType.KMTMathAtomOrdinary);
                    MTMathList radicand = mTRadical.getRadicand();
                    Intrinsics.checkNotNull(radicand);
                    MTRadicalDisplay mTRadicalDisplayMakeRadical = makeRadical(radicand, mTRadical.getIndexRange());
                    if (mTRadical.getDegree() != null) {
                        Companion companion2 = INSTANCE;
                        MTMathList degree = mTRadical.getDegree();
                        Intrinsics.checkNotNull(degree);
                        mTRadicalDisplayMakeRadical.setDegree(companion2.createLineForMathList(degree, this.font, MTLineStyle.KMTLineStyleScriptScript), this.styleFont.getMathTable());
                    }
                    this.displayAtoms.add(mTRadicalDisplayMakeRadical);
                    CGPoint cGPoint3 = this.currentPosition;
                    cGPoint3.setX(cGPoint3.getX() + mTRadicalDisplayMakeRadical.getWidth());
                    if (mTMathAtom2.getSubScript() != null || mTMathAtom2.getSuperScript() != null) {
                        makeScripts(mTMathAtom2, mTRadicalDisplayMakeRadical, mTRadical.getIndexRange().getLocation(), 0.0f);
                    }
                    break;
                case 10:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    MTFraction mTFraction = (MTFraction) mTMathAtom2;
                    addInterElementSpace(mTMathAtom, mTMathAtom2.getType());
                    MTDisplay mTDisplayMakeFraction = makeFraction(mTFraction);
                    this.displayAtoms.add(mTDisplayMakeFraction);
                    CGPoint cGPoint4 = this.currentPosition;
                    cGPoint4.setX(cGPoint4.getX() + mTDisplayMakeFraction.getWidth());
                    if (mTMathAtom2.getSubScript() != null || mTMathAtom2.getSuperScript() != null) {
                        makeScripts(mTMathAtom2, mTDisplayMakeFraction, mTFraction.getIndexRange().getLocation(), 0.0f);
                    }
                    break;
                case 11:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    addInterElementSpace(mTMathAtom, mTMathAtom2.getType());
                    this.displayAtoms.add(makeLargeOp((MTLargeOperator) mTMathAtom2));
                    break;
                case 12:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    addInterElementSpace(mTMathAtom, mTMathAtom2.getType());
                    MTInner mTInner = (MTInner) mTMathAtom2;
                    if (mTInner.getLeftBoundary() != null || mTInner.getRightBoundary() != null) {
                        mTMathListDisplayMakeLeftRight = makeLeftRight(mTInner);
                    } else if (mTInner.getInnerList() != null) {
                        Companion companion3 = INSTANCE;
                        MTMathList innerList2 = mTInner.getInnerList();
                        Intrinsics.checkNotNull(innerList2);
                        mTMathListDisplayMakeLeftRight = companion3.createLineForMathList(innerList2, this.font, this.style, this.cramped);
                    } else {
                        mTMathListDisplayMakeLeftRight = null;
                    }
                    if (mTMathListDisplayMakeLeftRight != null) {
                        mTMathListDisplayMakeLeftRight.setPosition(this.currentPosition);
                        CGPoint cGPoint5 = this.currentPosition;
                        cGPoint5.setX(cGPoint5.getX() + mTMathListDisplayMakeLeftRight.getWidth());
                        this.displayAtoms.add(mTMathListDisplayMakeLeftRight);
                        if (mTMathAtom2.getSubScript() != null || mTMathAtom2.getSuperScript() != null) {
                            makeScripts(mTMathAtom2, mTMathListDisplayMakeLeftRight, mTInner.getIndexRange().getLocation(), 0.0f);
                        }
                    }
                    break;
                case 13:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    addInterElementSpace(mTMathAtom, MTMathAtomType.KMTMathAtomOrdinary);
                    mTMathAtom2.setType(MTMathAtomType.KMTMathAtomOrdinary);
                    MTUnderLine mTUnderLine = (MTUnderLine) mTMathAtom2;
                    MTDisplay mTDisplayMakeUnderline = makeUnderline(mTUnderLine);
                    if (mTDisplayMakeUnderline != null) {
                        this.displayAtoms.add(mTDisplayMakeUnderline);
                        CGPoint cGPoint6 = this.currentPosition;
                        cGPoint6.setX(cGPoint6.getX() + mTDisplayMakeUnderline.getWidth());
                        if (mTMathAtom2.getSubScript() != null || mTMathAtom2.getSuperScript() != null) {
                            makeScripts(mTMathAtom2, mTDisplayMakeUnderline, mTUnderLine.getIndexRange().getLocation(), 0.0f);
                        }
                    }
                    break;
                case 14:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    addInterElementSpace(mTMathAtom, MTMathAtomType.KMTMathAtomOrdinary);
                    mTMathAtom2.setType(MTMathAtomType.KMTMathAtomOrdinary);
                    MTOverLine mTOverLine = (MTOverLine) mTMathAtom2;
                    MTDisplay mTDisplayMakeOverline = makeOverline(mTOverLine);
                    if (mTDisplayMakeOverline != null) {
                        this.displayAtoms.add(mTDisplayMakeOverline);
                        CGPoint cGPoint7 = this.currentPosition;
                        cGPoint7.setX(cGPoint7.getX() + mTDisplayMakeOverline.getWidth());
                        if (mTMathAtom2.getSubScript() != null || mTMathAtom2.getSuperScript() != null) {
                            makeScripts(mTMathAtom2, mTDisplayMakeOverline, mTOverLine.getIndexRange().getLocation(), 0.0f);
                        }
                    }
                    break;
                case 15:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    addInterElementSpace(mTMathAtom, MTMathAtomType.KMTMathAtomOrdinary);
                    mTMathAtom2.setType(MTMathAtomType.KMTMathAtomOrdinary);
                    MTAccent mTAccent = (MTAccent) mTMathAtom2;
                    MTDisplay mTDisplayMakeAccent = makeAccent(mTAccent);
                    if (mTDisplayMakeAccent != null) {
                        this.displayAtoms.add(mTDisplayMakeAccent);
                        CGPoint cGPoint8 = this.currentPosition;
                        cGPoint8.setX(cGPoint8.getX() + mTDisplayMakeAccent.getWidth());
                        if (mTMathAtom2.getSubScript() != null || mTMathAtom2.getSuperScript() != null) {
                            makeScripts(mTMathAtom2, mTDisplayMakeAccent, mTAccent.getIndexRange().getLocation(), 0.0f);
                        }
                    }
                    break;
                case 16:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    addInterElementSpace(mTMathAtom, MTMathAtomType.KMTMathAtomInner);
                    mTMathAtom2.setType(MTMathAtomType.KMTMathAtomInner);
                    MTDisplay mTDisplayMakeTable = makeTable((MTMathTable) mTMathAtom2);
                    this.displayAtoms.add(mTDisplayMakeTable);
                    CGPoint cGPoint9 = this.currentPosition;
                    cGPoint9.setX(cGPoint9.getX() + mTDisplayMakeTable.getWidth());
                    break;
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    if (this.currentLine.length() > 0) {
                        addDisplayLine();
                    }
                    if (mTMathAtom != null) {
                        float interElementSpace = getInterElementSpace(mTMathAtom.getType(), mTMathAtom2.getType());
                        if (this.currentLine.length() <= 0) {
                            CGPoint cGPoint10 = this.currentPosition;
                            cGPoint10.setX(cGPoint10.getX() + interElementSpace);
                        } else if (interElementSpace > 0.0f) {
                            CGPoint cGPoint11 = this.currentPosition;
                            cGPoint11.setX(cGPoint11.getX() + interElementSpace);
                        }
                    }
                    this.currentLine = Intrinsics.stringPlus(this.currentLine, mTMathAtom2.getNucleus());
                    if (this.currentLineIndexRange.getLocation() == -1) {
                        this.currentLineIndexRange.setLocation(mTMathAtom2.getIndexRange().getLocation());
                        this.currentLineIndexRange.setLength(mTMathAtom2.getIndexRange().getLength());
                    } else {
                        NSRange nSRange = this.currentLineIndexRange;
                        nSRange.setLength(nSRange.getLength() + mTMathAtom2.getIndexRange().getLength());
                    }
                    if (mTMathAtom2.getFusedAtoms().size() > 0) {
                        this.currentAtoms.addAll(mTMathAtom2.getFusedAtoms());
                    } else {
                        this.currentAtoms.add(mTMathAtom2);
                    }
                    if (mTMathAtom2.getSubScript() != null || mTMathAtom2.getSuperScript() != null) {
                        MTCTLineDisplay mTCTLineDisplayAddDisplayLine = addDisplayLine();
                        float italicCorrection = mTMathAtom2.getNucleus().length() > 0 ? this.styleFont.getMathTable().getItalicCorrection(this.styleFont.findGlyphForCharacterAtIndex(0, mTMathAtom2.getNucleus()).getGid()) : 0.0f;
                        if (italicCorrection > 0.0f && mTMathAtom2.getSubScript() == null) {
                            CGPoint cGPoint12 = this.currentPosition;
                            cGPoint12.setX(cGPoint12.getX() + italicCorrection);
                        }
                        makeScripts(mTMathAtom2, mTCTLineDisplayAddDisplayLine, mTMathAtom2.getIndexRange().getMaxrange() - 1, italicCorrection);
                    }
                    break;
            }
            type = mTMathAtom2.getType();
            mTMathAtom = mTMathAtom2;
        }
        if (this.currentLine.length() > 0) {
            addDisplayLine();
        }
        if (!this.spaced || type == MTMathAtomType.KMTMathAtomNone) {
            return;
        }
        MTDisplay mTDisplay = (MTDisplay) CollectionsKt.last((List) this.displayAtoms);
        mTDisplay.setWidth(mTDisplay.getWidth() + getInterElementSpace(type, MTMathAtomType.KMTMathAtomClose));
    }

    private final MTCTLineDisplay addDisplayLine() {
        MTCTLineDisplay mTCTLineDisplay = new MTCTLineDisplay(this.currentLine, this.currentLineIndexRange, this.styleFont, this.currentAtoms);
        mTCTLineDisplay.setPosition(this.currentPosition);
        this.displayAtoms.add(mTCTLineDisplay);
        CGPoint cGPoint = this.currentPosition;
        cGPoint.setX(cGPoint.getX() + mTCTLineDisplay.getWidth());
        this.currentLine = "";
        this.currentAtoms = new ArrayList();
        this.currentLineIndexRange = new NSRange(0, 0, 3, null);
        return mTCTLineDisplay;
    }

    private final int getSpacingInMu(MTInterElementSpaceType type) {
        switch (WhenMappings.$EnumSwitchMapping$2[type.ordinal()]) {
            case 1:
                return -1;
            case 2:
                return 0;
            case 3:
                return 3;
            case 4:
                return this.style.compareTo(MTLineStyle.KMTLineStyleScript) < 0 ? 3 : 0;
            case 5:
                return this.style.compareTo(MTLineStyle.KMTLineStyleScript) < 0 ? 4 : 0;
            case 6:
                return this.style.compareTo(MTLineStyle.KMTLineStyleScript) < 0 ? 5 : 0;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final float getInterElementSpace(MTMathAtomType left, MTMathAtomType right) throws MathDisplayException {
        MTInterElementSpaceType mTInterElementSpaceType = MTSpacingKt.getInterElementSpaceArray()[MTSpacingKt.getInterElementSpaceArrayIndexForType(left, true)][MTSpacingKt.getInterElementSpaceArrayIndexForType(right, false)];
        if (mTInterElementSpaceType == MTInterElementSpaceType.KMTSpaceInvalid) {
            throw new MathDisplayException("Invalid space between " + left + " and " + right);
        }
        int spacingInMu = getSpacingInMu(mTInterElementSpaceType);
        if (spacingInMu > 0) {
            return spacingInMu * this.styleFont.getMathTable().muUnit();
        }
        return 0.0f;
    }

    private final MTLineStyle scriptStyle() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.style.ordinal()];
        if (i == 1 || i == 2) {
            return MTLineStyle.KMTLineStyleScript;
        }
        if (i == 3) {
            return MTLineStyle.KMTLineStyleScriptScript;
        }
        if (i == 4) {
            return MTLineStyle.KMTLineStyleScriptScript;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: renamed from: superScriptCramped, reason: from getter */
    private final boolean getCramped() {
        return this.cramped;
    }

    private final float superScriptShiftUp() {
        if (this.cramped) {
            return this.styleFont.getMathTable().getSuperscriptShiftUpCramped();
        }
        return this.styleFont.getMathTable().getSuperscriptShiftUp();
    }

    private final void makeScripts(MTMathAtom atom, MTDisplay display, int index, float delta) {
        MTMathList subScript = atom.getSubScript();
        MTMathList superScript = atom.getSuperScript();
        display.setHasScript(true);
        MTFontMathTable mathTable = this.font.copyFontWithSize(getStyleSize(scriptStyle(), this.font)).getMathTable();
        float ascent = display.getAscent() - mathTable.getSuperscriptBaselineDropMax();
        float descent = display.getDescent() + mathTable.getSubscriptBaselineDropMin();
        if (superScript == null && subScript != null) {
            MTMathListDisplay mTMathListDisplayCreateLineForMathList = INSTANCE.createLineForMathList(subScript, this.font, scriptStyle(), subScriptCramped());
            mTMathListDisplayCreateLineForMathList.setType(MTLinePosition.KMTLinePositionSubscript);
            mTMathListDisplayCreateLineForMathList.setIndex(index);
            mTMathListDisplayCreateLineForMathList.setPosition(new CGPoint(this.currentPosition.getX(), this.currentPosition.getY() - Math.max(Math.max(descent, this.styleFont.getMathTable().getSubscriptShiftDown()), mTMathListDisplayCreateLineForMathList.getAscent() - this.styleFont.getMathTable().getSubscriptTopMax())));
            this.displayAtoms.add(mTMathListDisplayCreateLineForMathList);
            CGPoint cGPoint = this.currentPosition;
            cGPoint.setX(cGPoint.getX() + mTMathListDisplayCreateLineForMathList.getWidth() + this.styleFont.getMathTable().getSpaceAfterScript());
            return;
        }
        Companion companion = INSTANCE;
        Intrinsics.checkNotNull(superScript);
        MTMathListDisplay mTMathListDisplayCreateLineForMathList2 = companion.createLineForMathList(superScript, this.font, scriptStyle(), getCramped());
        mTMathListDisplayCreateLineForMathList2.setType(MTLinePosition.KMTLinePositionSuperscript);
        mTMathListDisplayCreateLineForMathList2.setIndex(index);
        float fMax = Math.max(Math.max(ascent, superScriptShiftUp()), mTMathListDisplayCreateLineForMathList2.getDescent() + this.styleFont.getMathTable().getSuperscriptBottomMin());
        if (subScript == null) {
            mTMathListDisplayCreateLineForMathList2.setPosition(new CGPoint(this.currentPosition.getX(), this.currentPosition.getY() + fMax));
            this.displayAtoms.add(mTMathListDisplayCreateLineForMathList2);
            CGPoint cGPoint2 = this.currentPosition;
            cGPoint2.setX(cGPoint2.getX() + mTMathListDisplayCreateLineForMathList2.getWidth() + this.styleFont.getMathTable().getSpaceAfterScript());
            return;
        }
        MTMathListDisplay mTMathListDisplayCreateLineForMathList3 = companion.createLineForMathList(subScript, this.font, scriptStyle(), subScriptCramped());
        mTMathListDisplayCreateLineForMathList3.setType(MTLinePosition.KMTLinePositionSubscript);
        mTMathListDisplayCreateLineForMathList3.setIndex(index);
        float fMax2 = Math.max(descent, this.styleFont.getMathTable().getSubscriptShiftDown());
        float descent2 = (fMax - mTMathListDisplayCreateLineForMathList2.getDescent()) + (fMax2 - mTMathListDisplayCreateLineForMathList3.getAscent());
        if (descent2 < this.styleFont.getMathTable().getSubSuperscriptGapMin()) {
            fMax2 += this.styleFont.getMathTable().getSubSuperscriptGapMin() - descent2;
            float superscriptBottomMaxWithSubscript = this.styleFont.getMathTable().getSuperscriptBottomMaxWithSubscript() - (fMax - mTMathListDisplayCreateLineForMathList2.getDescent());
            if (superscriptBottomMaxWithSubscript > 0.0f) {
                fMax += superscriptBottomMaxWithSubscript;
                fMax2 -= superscriptBottomMaxWithSubscript;
            }
        }
        mTMathListDisplayCreateLineForMathList2.setPosition(new CGPoint(this.currentPosition.getX() + delta, this.currentPosition.getY() + fMax));
        this.displayAtoms.add(mTMathListDisplayCreateLineForMathList2);
        mTMathListDisplayCreateLineForMathList3.setPosition(new CGPoint(this.currentPosition.getX(), this.currentPosition.getY() - fMax2));
        this.displayAtoms.add(mTMathListDisplayCreateLineForMathList3);
        CGPoint cGPoint3 = this.currentPosition;
        cGPoint3.setX(cGPoint3.getX() + Math.max(mTMathListDisplayCreateLineForMathList2.getWidth() + delta, mTMathListDisplayCreateLineForMathList3.getWidth()) + this.styleFont.getMathTable().getSpaceAfterScript());
    }

    public final float numeratorShiftUp(boolean hasRule) {
        if (hasRule) {
            if (this.style == MTLineStyle.KMTLineStyleDisplay) {
                return this.styleFont.getMathTable().getFractionNumeratorDisplayStyleShiftUp();
            }
            return this.styleFont.getMathTable().getFractionNumeratorShiftUp();
        }
        if (this.style == MTLineStyle.KMTLineStyleDisplay) {
            return this.styleFont.getMathTable().getStackTopDisplayStyleShiftUp();
        }
        return this.styleFont.getMathTable().getStackTopShiftUp();
    }

    public final float numeratorGapMin() {
        if (this.style == MTLineStyle.KMTLineStyleDisplay) {
            return this.styleFont.getMathTable().getFractionNumeratorDisplayStyleGapMin();
        }
        return this.styleFont.getMathTable().getFractionNumeratorGapMin();
    }

    public final float denominatorShiftDown(boolean hasRule) {
        if (hasRule) {
            if (this.style == MTLineStyle.KMTLineStyleDisplay) {
                return this.styleFont.getMathTable().getFractionDenominatorDisplayStyleShiftDown();
            }
            return this.styleFont.getMathTable().getFractionDenominatorShiftDown();
        }
        if (this.style == MTLineStyle.KMTLineStyleDisplay) {
            return this.styleFont.getMathTable().getStackBottomDisplayStyleShiftDown();
        }
        return this.styleFont.getMathTable().getStackBottomShiftDown();
    }

    public final float denominatorGapMin() {
        if (this.style == MTLineStyle.KMTLineStyleDisplay) {
            return this.styleFont.getMathTable().getFractionDenominatorDisplayStyleGapMin();
        }
        return this.styleFont.getMathTable().getFractionDenominatorGapMin();
    }

    public final float stackGapMin() {
        if (this.style == MTLineStyle.KMTLineStyleDisplay) {
            return this.styleFont.getMathTable().getStackDisplayStyleGapMin();
        }
        return this.styleFont.getMathTable().getStackGapMin();
    }

    public final float fractionDelimiterHeight() {
        if (this.style == MTLineStyle.KMTLineStyleDisplay) {
            return this.styleFont.getMathTable().getFractionDelimiterDisplayStyleSize();
        }
        return this.styleFont.getMathTable().getFractionDelimiterSize();
    }

    private final MTLineStyle fractionStyle() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.style.ordinal()];
        if (i == 1) {
            return MTLineStyle.KMTLineStyleText;
        }
        if (i == 2) {
            return MTLineStyle.KMTLineStyleScript;
        }
        if (i == 3 || i == 4) {
            return MTLineStyle.KMTLineStyleScriptScript;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final MTDisplay makeFraction(MTFraction frac) {
        MTLineStyle mTLineStyleFractionStyle = fractionStyle();
        Companion companion = INSTANCE;
        MTMathList numerator = frac.getNumerator();
        Intrinsics.checkNotNull(numerator);
        MTMathListDisplay mTMathListDisplayCreateLineForMathList = companion.createLineForMathList(numerator, this.font, mTLineStyleFractionStyle, false);
        MTMathList denominator = frac.getDenominator();
        Intrinsics.checkNotNull(denominator);
        MTMathListDisplay mTMathListDisplayCreateLineForMathList2 = companion.createLineForMathList(denominator, this.font, mTLineStyleFractionStyle, true);
        float fNumeratorShiftUp = numeratorShiftUp(frac.getHasRule());
        float fDenominatorShiftDown = denominatorShiftDown(frac.getHasRule());
        float axisHeight = this.styleFont.getMathTable().getAxisHeight();
        float fractionRuleThickness = frac.getHasRule() ? this.styleFont.getMathTable().getFractionRuleThickness() : 0.0f;
        if (frac.getHasRule()) {
            float f = fractionRuleThickness / 2;
            float descent = (fNumeratorShiftUp - mTMathListDisplayCreateLineForMathList.getDescent()) - (axisHeight + f);
            float fNumeratorGapMin = numeratorGapMin();
            if (descent < fNumeratorGapMin) {
                fNumeratorShiftUp += fNumeratorGapMin - descent;
            }
            float ascent = (axisHeight - f) - (mTMathListDisplayCreateLineForMathList2.getAscent() - fDenominatorShiftDown);
            float fDenominatorGapMin = denominatorGapMin();
            if (ascent < fDenominatorGapMin) {
                fDenominatorShiftDown += fDenominatorGapMin - ascent;
            }
        } else {
            float descent2 = (fNumeratorShiftUp - mTMathListDisplayCreateLineForMathList.getDescent()) - (mTMathListDisplayCreateLineForMathList2.getAscent() - fDenominatorShiftDown);
            float fStackGapMin = stackGapMin();
            if (descent2 < fStackGapMin) {
                float f2 = (fStackGapMin - descent2) / 2;
                fNumeratorShiftUp += f2;
                fDenominatorShiftDown += f2;
            }
        }
        MTFractionDisplay mTFractionDisplay = new MTFractionDisplay(mTMathListDisplayCreateLineForMathList, mTMathListDisplayCreateLineForMathList2, frac.getIndexRange());
        mTFractionDisplay.setPosition(this.currentPosition);
        mTFractionDisplay.setNumeratorUp(fNumeratorShiftUp);
        mTFractionDisplay.setDenominatorDown(fDenominatorShiftDown);
        mTFractionDisplay.setLineThickness(fractionRuleThickness);
        mTFractionDisplay.setLinePosition(axisHeight);
        if (frac.getLeftDelimiter() == null && frac.getRightDelimiter() == null) {
            return mTFractionDisplay;
        }
        return addDelimitersToFractionDisplay(mTFractionDisplay, frac);
    }

    private final MTDisplay addDelimitersToFractionDisplay(MTFractionDisplay display, MTFraction frac) {
        if (frac.getLeftDelimiter() == null) {
            frac.getRightDelimiter();
        }
        ArrayList arrayList = new ArrayList(0);
        float fFractionDelimiterHeight = fractionDelimiterHeight();
        CGPoint cGPoint = new CGPoint(0.0f, 0.0f, 3, null);
        String leftDelimiter = frac.getLeftDelimiter();
        if (leftDelimiter != null && leftDelimiter.length() > 0) {
            MTDisplay mTDisplayFindGlyphForBoundary = findGlyphForBoundary(leftDelimiter, fFractionDelimiterHeight);
            mTDisplayFindGlyphForBoundary.setPosition(CGPoint.copy$default(cGPoint, 0.0f, 0.0f, 3, null));
            cGPoint.setX(cGPoint.getX() + mTDisplayFindGlyphForBoundary.getWidth());
            arrayList.add(mTDisplayFindGlyphForBoundary);
        }
        display.setPosition(CGPoint.copy$default(cGPoint, 0.0f, 0.0f, 3, null));
        cGPoint.setX(cGPoint.getX() + display.getWidth());
        arrayList.add(display);
        String rightDelimiter = frac.getRightDelimiter();
        if (rightDelimiter != null && rightDelimiter.length() > 0) {
            MTDisplay mTDisplayFindGlyphForBoundary2 = findGlyphForBoundary(rightDelimiter, fFractionDelimiterHeight);
            mTDisplayFindGlyphForBoundary2.setPosition(CGPoint.copy$default(cGPoint, 0.0f, 0.0f, 3, null));
            cGPoint.setX(cGPoint.getX() + mTDisplayFindGlyphForBoundary2.getWidth());
            arrayList.add(mTDisplayFindGlyphForBoundary2);
        }
        MTMathListDisplay mTMathListDisplay = new MTMathListDisplay(arrayList, frac.getIndexRange());
        mTMathListDisplay.setPosition(this.currentPosition);
        return mTMathListDisplay;
    }

    private final float radicalVerticalGap() {
        if (this.style == MTLineStyle.KMTLineStyleDisplay) {
            return this.styleFont.getMathTable().getRadicalDisplayStyleVerticalGap();
        }
        return this.styleFont.getMathTable().getRadicalVerticalGap();
    }

    private final MTDisplay getRadicalGlyphWithHeight(float radicalHeight) {
        CGGlyph cGGlyphFindGlyphForCharacterAtIndex = this.styleFont.findGlyphForCharacterAtIndex(0, "√");
        CGGlyph cGGlyphFindGlyph = findGlyph(cGGlyphFindGlyphForCharacterAtIndex, radicalHeight);
        MTGlyphConstructionDisplay mTGlyphConstructionDisplayConstructGlyph = cGGlyphFindGlyph.getGlyphAscent() + cGGlyphFindGlyph.getGlyphDescent() < radicalHeight ? constructGlyph(cGGlyphFindGlyphForCharacterAtIndex, radicalHeight) : null;
        if (mTGlyphConstructionDisplayConstructGlyph != null) {
            return mTGlyphConstructionDisplayConstructGlyph;
        }
        MTGlyphDisplay mTGlyphDisplay = new MTGlyphDisplay(cGGlyphFindGlyph, new NSRange(-1, 0), this.styleFont);
        MTGlyphDisplay mTGlyphDisplay2 = mTGlyphDisplay;
        mTGlyphDisplay2.setAscent(cGGlyphFindGlyph.getGlyphAscent());
        mTGlyphDisplay2.setDescent(cGGlyphFindGlyph.getGlyphDescent());
        mTGlyphDisplay2.setWidth(cGGlyphFindGlyph.getGlyphWidth());
        return mTGlyphDisplay;
    }

    private final MTRadicalDisplay makeRadical(MTMathList radicand, NSRange range) {
        MTMathListDisplay mTMathListDisplayCreateLineForMathList = INSTANCE.createLineForMathList(radicand, this.font, this.style, true);
        float fRadicalVerticalGap = radicalVerticalGap();
        float radicalRuleThickness = this.styleFont.getMathTable().getRadicalRuleThickness();
        MTDisplay radicalGlyphWithHeight = getRadicalGlyphWithHeight(mTMathListDisplayCreateLineForMathList.getAscent() + mTMathListDisplayCreateLineForMathList.getDescent() + fRadicalVerticalGap + radicalRuleThickness);
        float descent = (radicalGlyphWithHeight.getDescent() + radicalGlyphWithHeight.getAscent()) - (((mTMathListDisplayCreateLineForMathList.getAscent() + mTMathListDisplayCreateLineForMathList.getDescent()) + fRadicalVerticalGap) + radicalRuleThickness);
        if (descent > 0.0f) {
            fRadicalVerticalGap += descent / 2;
        }
        float ascent = fRadicalVerticalGap + radicalRuleThickness + mTMathListDisplayCreateLineForMathList.getAscent();
        radicalGlyphWithHeight.setShiftDown(-(ascent - radicalGlyphWithHeight.getAscent()));
        MTRadicalDisplay mTRadicalDisplay = new MTRadicalDisplay(mTMathListDisplayCreateLineForMathList, radicalGlyphWithHeight, range);
        mTRadicalDisplay.setPosition(this.currentPosition);
        mTRadicalDisplay.setAscent(this.styleFont.getMathTable().getRadicalExtraAscender() + ascent);
        mTRadicalDisplay.setTopKern(this.styleFont.getMathTable().getRadicalExtraAscender());
        mTRadicalDisplay.setLineThickness(radicalRuleThickness);
        mTRadicalDisplay.setDescent(Math.max((radicalGlyphWithHeight.getAscent() + radicalGlyphWithHeight.getDescent()) - ascent, mTMathListDisplayCreateLineForMathList.getDescent()));
        mTRadicalDisplay.setWidth(radicalGlyphWithHeight.getWidth() + mTMathListDisplayCreateLineForMathList.getWidth());
        return mTRadicalDisplay;
    }

    private final CGGlyph findGlyph(CGGlyph glyph, float height) {
        float f;
        List<Integer> verticalVariantsForGlyph = this.styleFont.getMathTable().getVerticalVariantsForGlyph(glyph);
        int size = verticalVariantsForGlyph.size();
        BoundingBox[] boundingBoxArr = new BoundingBox[size];
        Float[] fArr = new Float[size];
        int i = 0;
        int i2 = 0;
        while (true) {
            f = 0.0f;
            if (i2 >= size) {
                break;
            }
            fArr[i2] = Float.valueOf(0.0f);
            i2++;
        }
        this.styleFont.getMathTable().getBoundingRectsForGlyphs(verticalVariantsForGlyph, boundingBoxArr, size);
        this.styleFont.getMathTable().getAdvancesForGlyphs(verticalVariantsForGlyph, fArr, size);
        float f2 = 0.0f;
        float fFloatValue = 0.0f;
        while (i < size) {
            int i3 = i + 1;
            BoundingBox boundingBox = boundingBoxArr[i];
            fFloatValue = fArr[i].floatValue();
            float bboxDetailsAscent = getBboxDetailsAscent(boundingBox);
            float bboxDetailsDescent = getBboxDetailsDescent(boundingBox);
            if (bboxDetailsAscent + bboxDetailsDescent >= height) {
                return new CGGlyph(verticalVariantsForGlyph.get(i).intValue(), bboxDetailsAscent, bboxDetailsDescent, fFloatValue);
            }
            i = i3;
            f2 = bboxDetailsDescent;
            f = bboxDetailsAscent;
        }
        return new CGGlyph(verticalVariantsForGlyph.get(size - 1).intValue(), f, f2, fFloatValue);
    }

    private final MTGlyphConstructionDisplay constructGlyph(CGGlyph glyph, float glyphHeight) {
        List<MTGlyphPart> verticalGlyphAssemblyForGlyph = this.styleFont.getMathTable().getVerticalGlyphAssemblyForGlyph(glyph.getGid());
        if (verticalGlyphAssemblyForGlyph == null || verticalGlyphAssemblyForGlyph.size() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(0);
        ArrayList arrayList2 = new ArrayList(0);
        float fConstructGlyphWithParts = constructGlyphWithParts(verticalGlyphAssemblyForGlyph, glyphHeight, arrayList, arrayList2);
        Float[] fArr = {Float.valueOf(0.0f)};
        this.styleFont.getMathTable().getAdvancesForGlyphs(CollectionsKt.toList(arrayList), fArr, 1);
        MTGlyphConstructionDisplay mTGlyphConstructionDisplay = new MTGlyphConstructionDisplay(arrayList, arrayList2, this.styleFont);
        mTGlyphConstructionDisplay.setWidth(fArr[0].floatValue());
        mTGlyphConstructionDisplay.setAscent(fConstructGlyphWithParts);
        mTGlyphConstructionDisplay.setDescent(0.0f);
        return mTGlyphConstructionDisplay;
    }

    private final float constructGlyphWithParts(List<MTGlyphPart> parts, float glyphHeight, List<Integer> glyphs, List<Float> offsets) {
        int i = 0;
        while (true) {
            float minConnectorOverlap = this.styleFont.getMathTable().getMinConnectorOverlap();
            glyphs.clear();
            offsets.clear();
            Iterator<MTGlyphPart> it = parts.iterator();
            float fFloatValue = 0.0f;
            MTGlyphPart mTGlyphPart = null;
            float fMin = 1000000.0f;
            float f = 0.0f;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MTGlyphPart next = it.next();
                int i2 = next.isExtender() ? i : 1;
                int i3 = 0;
                while (i3 < i2) {
                    i3++;
                    glyphs.add(Integer.valueOf(next.getGlyph()));
                    if (mTGlyphPart != null) {
                        float fullAdvance = mTGlyphPart.getFullAdvance() - Math.min(mTGlyphPart.getEndConnectorLength(), next.getStartConnectorLength());
                        fMin = Math.min(fMin, (mTGlyphPart.getFullAdvance() - minConnectorOverlap) - fullAdvance);
                        f += fullAdvance;
                    }
                    offsets.add(Float.valueOf(f));
                    mTGlyphPart = next;
                }
            }
            if (mTGlyphPart != null) {
                float fullAdvance2 = f + mTGlyphPart.getFullAdvance();
                List<Integer> list = glyphs;
                float size = (fMin * (list.size() - 1)) + fullAdvance2;
                if (fullAdvance2 >= glyphHeight) {
                    return fullAdvance2;
                }
                if (glyphHeight <= size) {
                    float size2 = (glyphHeight - fullAdvance2) / (list.size() - 1);
                    int size3 = offsets.size();
                    for (int i4 = 0; i4 < size3; i4++) {
                        fFloatValue = offsets.get(i4).floatValue() + (i4 * size2);
                        offsets.set(i4, Float.valueOf(fFloatValue));
                    }
                    return fFloatValue + mTGlyphPart.getFullAdvance();
                }
            }
            i++;
        }
    }

    private final MTDisplay makeLargeOp(MTLargeOperator op) {
        boolean z = op.getHasLimits() && this.style == MTLineStyle.KMTLineStyleDisplay;
        if (op.getNucleus().length() == 1) {
            CGGlyph cGGlyphFindGlyphForCharacterAtIndex = this.styleFont.findGlyphForCharacterAtIndex(0, op.getNucleus());
            if (this.style == MTLineStyle.KMTLineStyleDisplay && cGGlyphFindGlyphForCharacterAtIndex.isValid()) {
                cGGlyphFindGlyphForCharacterAtIndex = new CGGlyph(this.styleFont.getMathTable().getLargerGlyph(cGGlyphFindGlyphForCharacterAtIndex.getGid()), 0.0f, 0.0f, 0.0f, 14, null);
            }
            float italicCorrection = this.styleFont.getMathTable().getItalicCorrection(cGGlyphFindGlyphForCharacterAtIndex.getGid());
            BoundingBox[] boundingBoxArr = new BoundingBox[1];
            Float[] fArr = {Float.valueOf(0.0f)};
            List<Integer> listListOf = CollectionsKt.listOf(Integer.valueOf(cGGlyphFindGlyphForCharacterAtIndex.getGid()));
            List<Integer> list = listListOf;
            this.styleFont.getMathTable().getBoundingRectsForGlyphs(listListOf, boundingBoxArr, list.size());
            this.styleFont.getMathTable().getAdvancesForGlyphs(listListOf, fArr, list.size());
            float bboxDetailsAscent = getBboxDetailsAscent(boundingBoxArr[0]);
            float bboxDetailsDescent = getBboxDetailsDescent(boundingBoxArr[0]);
            float axisHeight = ((bboxDetailsAscent - bboxDetailsDescent) * 0.5f) - this.styleFont.getMathTable().getAxisHeight();
            MTGlyphDisplay mTGlyphDisplay = new MTGlyphDisplay(cGGlyphFindGlyphForCharacterAtIndex, op.getIndexRange(), this.styleFont);
            mTGlyphDisplay.setAscent(bboxDetailsAscent);
            mTGlyphDisplay.setDescent(bboxDetailsDescent);
            mTGlyphDisplay.setWidth(fArr[0].floatValue());
            if (op.getSubScript() != null && !z) {
                mTGlyphDisplay.setWidth(mTGlyphDisplay.getWidth() - italicCorrection);
            }
            mTGlyphDisplay.setShiftDown(axisHeight);
            mTGlyphDisplay.setPosition(this.currentPosition);
            return addLimitsToDisplay(mTGlyphDisplay, op, italicCorrection);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(op);
        MTCTLineDisplay mTCTLineDisplay = new MTCTLineDisplay(op.getNucleus(), op.getIndexRange(), this.styleFont, arrayList);
        mTCTLineDisplay.setPosition(this.currentPosition);
        return addLimitsToDisplay(mTCTLineDisplay, op, 0.0f);
    }

    private final MTDisplay addLimitsToDisplay(MTDisplay display, MTLargeOperator op, float delta) {
        MTMathListDisplay mTMathListDisplayCreateLineForMathList;
        MTMathListDisplay mTMathListDisplayCreateLineForMathList2;
        if (op.getSubScript() == null && op.getSuperScript() == null) {
            CGPoint cGPoint = this.currentPosition;
            cGPoint.setX(cGPoint.getX() + display.getWidth());
            return display;
        }
        if (op.getHasLimits() && this.style == MTLineStyle.KMTLineStyleDisplay) {
            if (op.getSuperScript() != null) {
                Companion companion = INSTANCE;
                MTMathList superScript = op.getSuperScript();
                Intrinsics.checkNotNull(superScript);
                mTMathListDisplayCreateLineForMathList = companion.createLineForMathList(superScript, this.font, scriptStyle(), getCramped());
            } else {
                mTMathListDisplayCreateLineForMathList = null;
            }
            if (op.getSubScript() != null) {
                Companion companion2 = INSTANCE;
                MTMathList subScript = op.getSubScript();
                Intrinsics.checkNotNull(subScript);
                mTMathListDisplayCreateLineForMathList2 = companion2.createLineForMathList(subScript, this.font, scriptStyle(), subScriptCramped());
            } else {
                mTMathListDisplayCreateLineForMathList2 = null;
            }
            MTLargeOpLimitsDisplay mTLargeOpLimitsDisplay = new MTLargeOpLimitsDisplay(display, mTMathListDisplayCreateLineForMathList, mTMathListDisplayCreateLineForMathList2, delta / 2, 0.0f);
            if (mTMathListDisplayCreateLineForMathList != null) {
                mTLargeOpLimitsDisplay.setUpperLimitGap(Math.max(this.styleFont.getMathTable().getUpperLimitGapMin(), this.styleFont.getMathTable().getUpperLimitBaselineRiseMin() - mTMathListDisplayCreateLineForMathList.getDescent()));
            }
            if (mTMathListDisplayCreateLineForMathList2 != null) {
                mTLargeOpLimitsDisplay.setLowerLimitGap(Math.max(this.styleFont.getMathTable().getLowerLimitGapMin(), this.styleFont.getMathTable().getLowerLimitBaselineDropMin() - mTMathListDisplayCreateLineForMathList2.getAscent()));
            }
            mTLargeOpLimitsDisplay.setPosition(this.currentPosition);
            mTLargeOpLimitsDisplay.setRange(NSRange.copy$default(op.getIndexRange(), 0, 0, 3, null));
            CGPoint cGPoint2 = this.currentPosition;
            cGPoint2.setX(cGPoint2.getX() + mTLargeOpLimitsDisplay.getWidth());
            return mTLargeOpLimitsDisplay;
        }
        CGPoint cGPoint3 = this.currentPosition;
        cGPoint3.setX(cGPoint3.getX() + display.getWidth());
        makeScripts(op, display, op.getIndexRange().getLocation(), delta);
        return display;
    }

    private final MTDisplay makeLeftRight(MTInner inner) {
        if (inner.getLeftBoundary() == null) {
            inner.getRightBoundary();
        }
        Companion companion = INSTANCE;
        MTMathList innerList = inner.getInnerList();
        Intrinsics.checkNotNull(innerList);
        MTMathListDisplay mTMathListDisplayCreateLineForMathList = companion.createLineForMathList(innerList, this.font, this.style, this.cramped, true);
        float axisHeight = this.styleFont.getMathTable().getAxisHeight();
        float fMax = Math.max(mTMathListDisplayCreateLineForMathList.getAscent() - axisHeight, mTMathListDisplayCreateLineForMathList.getDescent() + axisHeight);
        float fMax2 = Math.max((fMax / 500.0f) * 901, (fMax * 2.0f) - 5);
        ArrayList arrayList = new ArrayList();
        CGPoint cGPoint = new CGPoint(0.0f, 0.0f, 3, null);
        MTMathAtom leftBoundary = inner.getLeftBoundary();
        if (leftBoundary != null && leftBoundary.getNucleus().length() > 0) {
            MTDisplay mTDisplayFindGlyphForBoundary = findGlyphForBoundary(leftBoundary.getNucleus(), fMax2);
            mTDisplayFindGlyphForBoundary.setPosition(CGPoint.copy$default(cGPoint, 0.0f, 0.0f, 3, null));
            cGPoint.setX(cGPoint.getX() + mTDisplayFindGlyphForBoundary.getWidth());
            arrayList.add(mTDisplayFindGlyphForBoundary);
        }
        mTMathListDisplayCreateLineForMathList.setPosition(CGPoint.copy$default(cGPoint, 0.0f, 0.0f, 3, null));
        cGPoint.setX(cGPoint.getX() + mTMathListDisplayCreateLineForMathList.getWidth());
        arrayList.add(mTMathListDisplayCreateLineForMathList);
        MTMathAtom rightBoundary = inner.getRightBoundary();
        if (rightBoundary != null && rightBoundary.getNucleus().length() > 0) {
            MTDisplay mTDisplayFindGlyphForBoundary2 = findGlyphForBoundary(rightBoundary.getNucleus(), fMax2);
            mTDisplayFindGlyphForBoundary2.setPosition(CGPoint.copy$default(cGPoint, 0.0f, 0.0f, 3, null));
            cGPoint.setX(cGPoint.getX() + mTDisplayFindGlyphForBoundary2.getWidth());
            arrayList.add(mTDisplayFindGlyphForBoundary2);
        }
        return new MTMathListDisplay(arrayList, inner.getIndexRange());
    }

    private final MTDisplay findGlyphForBoundary(String delimiter, float glyphHeight) {
        CGGlyph cGGlyphFindGlyphForCharacterAtIndex = this.styleFont.findGlyphForCharacterAtIndex(0, delimiter);
        CGGlyph cGGlyphFindGlyph = findGlyph(cGGlyphFindGlyphForCharacterAtIndex, glyphHeight);
        MTGlyphDisplay mTGlyphDisplayConstructGlyph = cGGlyphFindGlyph.getGlyphAscent() + cGGlyphFindGlyph.getGlyphDescent() < glyphHeight ? constructGlyph(cGGlyphFindGlyphForCharacterAtIndex, glyphHeight) : null;
        if (mTGlyphDisplayConstructGlyph == null) {
            mTGlyphDisplayConstructGlyph = new MTGlyphDisplay(cGGlyphFindGlyph, new NSRange(-1, 0), this.styleFont);
            MTGlyphDisplay mTGlyphDisplay = mTGlyphDisplayConstructGlyph;
            mTGlyphDisplay.setAscent(cGGlyphFindGlyph.getGlyphAscent());
            mTGlyphDisplay.setDescent(cGGlyphFindGlyph.getGlyphDescent());
            mTGlyphDisplay.setWidth(cGGlyphFindGlyph.getGlyphWidth());
        }
        mTGlyphDisplayConstructGlyph.setShiftDown(((mTGlyphDisplayConstructGlyph.getAscent() - mTGlyphDisplayConstructGlyph.getDescent()) * 0.5f) - this.styleFont.getMathTable().getAxisHeight());
        return mTGlyphDisplayConstructGlyph;
    }

    private final MTDisplay makeUnderline(MTUnderLine under) {
        if (under.getInnerList() == null) {
            return null;
        }
        Companion companion = INSTANCE;
        MTMathList innerList = under.getInnerList();
        Intrinsics.checkNotNull(innerList);
        MTMathListDisplay mTMathListDisplayCreateLineForMathList = companion.createLineForMathList(innerList, this.font, this.style, this.cramped);
        MTLineDisplay mTLineDisplay = new MTLineDisplay(mTMathListDisplayCreateLineForMathList, under.getIndexRange());
        mTLineDisplay.setLineShiftUp(-(mTMathListDisplayCreateLineForMathList.getDescent() + this.styleFont.getMathTable().getUnderbarVerticalGap()));
        mTLineDisplay.setLineThickness(this.styleFont.getMathTable().getUnderbarRuleThickness());
        mTLineDisplay.setAscent(mTMathListDisplayCreateLineForMathList.getAscent());
        mTLineDisplay.setDescent(mTMathListDisplayCreateLineForMathList.getDescent() + this.styleFont.getMathTable().getUnderbarVerticalGap() + this.styleFont.getMathTable().getUnderbarRuleThickness() + this.styleFont.getMathTable().getUnderbarExtraDescender());
        mTLineDisplay.setWidth(mTMathListDisplayCreateLineForMathList.getWidth());
        mTLineDisplay.setPosition(this.currentPosition);
        return mTLineDisplay;
    }

    private final MTDisplay makeOverline(MTOverLine over) {
        if (over.getInnerList() == null) {
            return null;
        }
        Companion companion = INSTANCE;
        MTMathList innerList = over.getInnerList();
        Intrinsics.checkNotNull(innerList);
        MTMathListDisplay mTMathListDisplayCreateLineForMathList = companion.createLineForMathList(innerList, this.font, this.style, this.cramped);
        MTLineDisplay mTLineDisplay = new MTLineDisplay(mTMathListDisplayCreateLineForMathList, over.getIndexRange());
        mTLineDisplay.setLineShiftUp(mTMathListDisplayCreateLineForMathList.getAscent() + this.styleFont.getMathTable().getOverbarVerticalGap());
        mTLineDisplay.setLineThickness(this.styleFont.getMathTable().getUnderbarRuleThickness());
        mTLineDisplay.setAscent(mTMathListDisplayCreateLineForMathList.getAscent() + this.styleFont.getMathTable().getOverbarVerticalGap() + this.styleFont.getMathTable().getOverbarRuleThickness() + this.styleFont.getMathTable().getOverbarExtraAscender());
        mTLineDisplay.setDescent(mTMathListDisplayCreateLineForMathList.getDescent());
        mTLineDisplay.setWidth(mTMathListDisplayCreateLineForMathList.getWidth());
        mTLineDisplay.setPosition(this.currentPosition);
        return mTLineDisplay;
    }

    private final boolean isSingleCharAccentee(MTAccent accent) {
        if (accent.getInnerList() != null) {
            MTMathList innerList = accent.getInnerList();
            Intrinsics.checkNotNull(innerList);
            if (innerList.getAtoms().size() != 1) {
                return false;
            }
            MTMathList innerList2 = accent.getInnerList();
            Intrinsics.checkNotNull(innerList2);
            MTMathAtom mTMathAtom = innerList2.getAtoms().get(0);
            if (MTCharsetKt.numberOfGlyphs(mTMathAtom.getNucleus()) == 1 && mTMathAtom.getSubScript() == null && mTMathAtom.getSuperScript() == null) {
                return true;
            }
        }
        return false;
    }

    private final float getSkew(MTAccent accent, float accenteeWidth, CGGlyph accentGlyph) {
        float topAccentAdjustment = 0.0f;
        if (accent.getNucleus().length() == 0) {
            return 0.0f;
        }
        float topAccentAdjustment2 = this.styleFont.getMathTable().getTopAccentAdjustment(accentGlyph.getGid());
        if (!isSingleCharAccentee(accent)) {
            topAccentAdjustment = accenteeWidth / 2;
        } else if (accent.getInnerList() != null) {
            MTMathList innerList = accent.getInnerList();
            Intrinsics.checkNotNull(innerList);
            topAccentAdjustment = this.styleFont.getMathTable().getTopAccentAdjustment(this.styleFont.findGlyphForCharacterAtIndex(0, innerList.getAtoms().get(0).getNucleus()).getGid());
        }
        return topAccentAdjustment - topAccentAdjustment2;
    }

    private final CGGlyph findVariantGlyph(CGGlyph glyph, float maxWidth) {
        List<Integer> horizontalVariantsForGlyph = this.styleFont.getMathTable().getHorizontalVariantsForGlyph(glyph);
        int size = horizontalVariantsForGlyph.size();
        BoundingBox[] boundingBoxArr = new BoundingBox[size];
        Float[] fArr = new Float[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            fArr[i2] = Float.valueOf(0.0f);
        }
        this.styleFont.getMathTable().getBoundingRectsForGlyphs(horizontalVariantsForGlyph, boundingBoxArr, size);
        this.styleFont.getMathTable().getAdvancesForGlyphs(horizontalVariantsForGlyph, fArr, size);
        CGGlyph cGGlyph = new CGGlyph(0, 0.0f, 0.0f, 0.0f, 15, null);
        while (i < size) {
            int i3 = i + 1;
            BoundingBox boundingBox = boundingBoxArr[i];
            if (boundingBox != null) {
                float bboxDetailsAscent = getBboxDetailsAscent(boundingBox);
                float bboxDetailsDescent = getBboxDetailsDescent(boundingBox);
                if (Math.max(boundingBox.getLowerLeftX(), boundingBox.getUpperRightX()) > maxWidth) {
                    if (i != 0) {
                        break;
                    }
                    cGGlyph.setGlyphWidth(fArr[i].floatValue());
                    cGGlyph.setGlyphAscent(bboxDetailsAscent);
                    cGGlyph.setGlyphDescent(bboxDetailsDescent);
                    return cGGlyph;
                }
                cGGlyph.setGid(horizontalVariantsForGlyph.get(i).intValue());
                cGGlyph.setGlyphWidth(fArr[i].floatValue());
                cGGlyph.setGlyphAscent(bboxDetailsAscent);
                cGGlyph.setGlyphDescent(bboxDetailsDescent);
            }
            i = i3;
        }
        return cGGlyph;
    }

    private final MTDisplay makeAccent(MTAccent accent) {
        if (accent.getInnerList() == null) {
            return null;
        }
        Companion companion = INSTANCE;
        MTMathList innerList = accent.getInnerList();
        Intrinsics.checkNotNull(innerList);
        MTMathListDisplay mTMathListDisplayCreateLineForMathList = companion.createLineForMathList(innerList, this.font, this.style, true);
        if (accent.getNucleus().length() == 0) {
            return mTMathListDisplayCreateLineForMathList;
        }
        CGGlyph cGGlyphFindGlyphForCharacterAtIndex = this.styleFont.findGlyphForCharacterAtIndex(0, accent.getNucleus());
        float width = mTMathListDisplayCreateLineForMathList.getWidth();
        CGGlyph cGGlyphFindVariantGlyph = findVariantGlyph(cGGlyphFindGlyphForCharacterAtIndex, width);
        float fMin = Math.min(mTMathListDisplayCreateLineForMathList.getAscent(), this.styleFont.getMathTable().getAccentBaseHeight());
        CGPoint cGPoint = new CGPoint(getSkew(accent, width, cGGlyphFindVariantGlyph), mTMathListDisplayCreateLineForMathList.getAscent() - fMin);
        MTGlyphDisplay mTGlyphDisplay = new MTGlyphDisplay(cGGlyphFindVariantGlyph, accent.getIndexRange(), this.styleFont);
        mTGlyphDisplay.setAscent(cGGlyphFindVariantGlyph.getGlyphAscent());
        mTGlyphDisplay.setDescent(cGGlyphFindVariantGlyph.getGlyphDescent());
        mTGlyphDisplay.setWidth(cGGlyphFindVariantGlyph.getGlyphWidth());
        mTGlyphDisplay.setPosition(cGPoint);
        if (isSingleCharAccentee(accent) && (accent.getSubScript() != null || accent.getSuperScript() != null)) {
            MTMathList innerList2 = accent.getInnerList();
            Intrinsics.checkNotNull(innerList2);
            MTMathAtom mTMathAtom = innerList2.getAtoms().get(0);
            mTMathAtom.setSuperScript(accent.getSuperScript());
            mTMathAtom.setSubScript(accent.getSubScript());
            accent.setSuperScript(null);
            accent.setSubScript(null);
            MTMathList innerList3 = accent.getInnerList();
            Intrinsics.checkNotNull(innerList3);
            mTMathListDisplayCreateLineForMathList = companion.createLineForMathList(innerList3, this.font, this.style, this.cramped);
        }
        MTAccentDisplay mTAccentDisplay = new MTAccentDisplay(mTGlyphDisplay, mTMathListDisplayCreateLineForMathList, accent.getIndexRange());
        mTAccentDisplay.setWidth(mTMathListDisplayCreateLineForMathList.getWidth());
        mTAccentDisplay.setDescent(mTMathListDisplayCreateLineForMathList.getDescent());
        mTAccentDisplay.setAscent(Math.max(mTMathListDisplayCreateLineForMathList.getAscent(), (mTMathListDisplayCreateLineForMathList.getAscent() - fMin) + cGGlyphFindVariantGlyph.getGlyphAscent()));
        mTAccentDisplay.setPosition(this.currentPosition);
        return mTAccentDisplay;
    }

    private final MTDisplay makeTable(MTMathTable table) {
        int iNumColumns = table.numColumns();
        int i = 0;
        if (iNumColumns == 0 || table.numRows() == 0) {
            return new MTMathListDisplay(new ArrayList(0), table.getIndexRange());
        }
        Float[] fArr = new Float[iNumColumns];
        for (int i2 = 0; i2 < iNumColumns; i2++) {
            fArr[i2] = Float.valueOf(0.0f);
        }
        MTDisplay[][] mTDisplayArrTypesetCells = typesetCells(table, fArr);
        ArrayList arrayList = new ArrayList(0);
        int length = mTDisplayArrTypesetCells.length;
        while (i < length) {
            MTDisplay[] mTDisplayArr = mTDisplayArrTypesetCells[i];
            i++;
            arrayList.add(makeRowWithColumns(mTDisplayArr, table, fArr));
        }
        positionRows(arrayList, table);
        MTMathListDisplay mTMathListDisplay = new MTMathListDisplay(arrayList, table.getIndexRange());
        mTMathListDisplay.setPosition(this.currentPosition);
        return mTMathListDisplay;
    }

    private final MTDisplay[][] typesetCells(MTMathTable table, Float[] columnWidths) {
        int iNumRows = table.numRows();
        MTDisplay[][] mTDisplayArr = new MTDisplay[iNumRows][];
        for (int i = 0; i < iNumRows; i++) {
            mTDisplayArr[i] = new MTDisplay[0];
        }
        int iNumRows2 = table.numRows();
        int i2 = 0;
        while (i2 < iNumRows2) {
            int i3 = i2 + 1;
            List<MTMathList> list = table.getCells().get(i2);
            List<MTMathList> list2 = list;
            int size = list2.size();
            MTDisplay[] mTDisplayArr2 = new MTDisplay[size];
            for (int i4 = 0; i4 < size; i4++) {
                mTDisplayArr2[i4] = new MTDisplay(0.0f, 0.0f, 0.0f, null, false, 31, null);
            }
            mTDisplayArr[i2] = mTDisplayArr2;
            int size2 = list2.size();
            for (int i5 = 0; i5 < size2; i5++) {
                MTMathListDisplay mTMathListDisplayCreateLineForMathList = INSTANCE.createLineForMathList(list.get(i5), this.font, this.style, false);
                Intrinsics.checkNotNull(mTMathListDisplayCreateLineForMathList);
                columnWidths[i5] = Float.valueOf(Math.max(mTMathListDisplayCreateLineForMathList.getWidth(), columnWidths[i5].floatValue()));
                mTDisplayArr2[i5] = mTMathListDisplayCreateLineForMathList;
            }
            i2 = i3;
        }
        return mTDisplayArr;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0047  */
    /* JADX WARN: Code duplicated, block: B:15:0x0050  */
    private final MTMathListDisplay makeRowWithColumns(MTDisplay[] cols, MTMathTable table, Float[] columnWidths) {
        float width;
        float f;
        NSRange nSRange = new NSRange(0, 0, 3, null);
        int length = cols.length;
        int i = 0;
        float interColumnSpacing = 0.0f;
        while (i < length) {
            int i2 = i + 1;
            MTDisplay mTDisplay = cols[i];
            float fFloatValue = columnWidths[i].floatValue();
            int i3 = WhenMappings.$EnumSwitchMapping$3[table.getAlignmentForColumn(i).ordinal()];
            if (i3 == 1) {
                width = fFloatValue - mTDisplay.getWidth();
            } else {
                if (i3 != 2) {
                    f = interColumnSpacing;
                } else {
                    width = (fFloatValue - mTDisplay.getWidth()) / 2;
                }
                if (nSRange.getLocation() != -1) {
                    nSRange = nSRange.union(mTDisplay.getRange());
                } else {
                    nSRange = NSRange.copy$default(mTDisplay.getRange(), 0, 0, 3, null);
                }
                mTDisplay.setPosition(new CGPoint(f, 0.0f));
                interColumnSpacing += fFloatValue + (table.getInterColumnSpacing() * this.styleFont.getMathTable().muUnit());
                i = i2;
            }
            f = width + interColumnSpacing;
            if (nSRange.getLocation() != -1) {
                nSRange = nSRange.union(mTDisplay.getRange());
            } else {
                nSRange = NSRange.copy$default(mTDisplay.getRange(), 0, 0, 3, null);
            }
            mTDisplay.setPosition(new CGPoint(f, 0.0f));
            interColumnSpacing += fFloatValue + (table.getInterColumnSpacing() * this.styleFont.getMathTable().muUnit());
            i = i2;
        }
        return new MTMathListDisplay(ArraysKt.toList(cols), nSRange);
    }

    private final void positionRows(List<MTDisplay> rows, MTMathTable table) {
        float interRowAdditionalSpacing = table.getInterRowAdditionalSpacing() * 0.3f * this.styleFont.getFontSize();
        float fontSize = (this.styleFont.getFontSize() * 1.2f) + interRowAdditionalSpacing;
        float fontSize2 = (this.styleFont.getFontSize() * 0.1f) + interRowAdditionalSpacing;
        float fontSize3 = interRowAdditionalSpacing + (this.styleFont.getFontSize() * 0.0f);
        boolean z = true;
        float ascent = 0.0f;
        float descent = 0.0f;
        float ascent2 = 0.0f;
        for (MTDisplay mTDisplay : rows) {
            if (z) {
                mTDisplay.setPosition(new CGPoint(0.0f, 0.0f, 3, null));
                ascent2 += mTDisplay.getAscent();
                z = false;
            } else {
                ascent -= fontSize - (mTDisplay.getAscent() + descent) < fontSize3 ? (descent + mTDisplay.getAscent()) + fontSize2 : fontSize;
                mTDisplay.setPosition(new CGPoint(0.0f, ascent));
            }
            descent = mTDisplay.getDescent();
        }
        float axisHeight = ((ascent2 - ((-ascent) + descent)) * 0.5f) - this.styleFont.getMathTable().getAxisHeight();
        for (MTDisplay mTDisplay2 : rows) {
            mTDisplay2.setPosition(new CGPoint(mTDisplay2.getPosition().getX(), mTDisplay2.getPosition().getY() - axisHeight));
        }
    }

    private final float getBboxDetailsDescent(BoundingBox bbox) {
        if (bbox == null) {
            return 0.0f;
        }
        return Math.max(0.0f, 0.0f - Math.min(bbox.getUpperRightY(), bbox.getLowerLeftY()));
    }

    private final float getBboxDetailsAscent(BoundingBox bbox) {
        if (bbox == null) {
            return 0.0f;
        }
        return Math.max(0.0f, Math.max(bbox.getUpperRightY(), bbox.getLowerLeftY()));
    }
}
