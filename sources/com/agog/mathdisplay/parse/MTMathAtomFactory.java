package com.agog.mathdisplay.parse;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.utils.Language;
import com.miui.doodle.document.Layer;
import com.miui.handwriting.common.ai.NetConfig;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.view.menu.HyperMenuUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: compiled from: MTMathAtomFactory.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0013\u001a\u00020\u0005J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0011J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u001c\u001a\u00020\u0005J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u001e\u001a\u00020\u0005J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u00052\u0006\u0010 \u001a\u00020\u0011J\b\u0010!\u001a\u00020\u0011H\u0002J\u000e\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u000eJ\u0018\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'H\u0002J\u0016\u0010$\u001a\u00020%2\u0006\u0010)\u001a\u00020\u00052\u0006\u0010*\u001a\u00020\u0005J\u0010\u0010+\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001a\u001a\u00020\u0011J\u000e\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020\u0005J\u001d\u0010.\u001a\u00020/2\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u00100\u001a\u000201H\u0000¢\u0006\u0002\b2J\b\u00103\u001a\u00020\u0011H\u0002J\b\u00104\u001a\u00020%H\u0002J\b\u00105\u001a\u00020'H\u0002J\b\u00106\u001a\u000207H\u0002J\b\u00108\u001a\u000207H\u0002J\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00050:J.\u0010;\u001a\u0004\u0018\u00010\u00112\b\u0010<\u001a\u0004\u0018\u00010\u00052\u0012\u0010=\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020'0>0>2\u0006\u0010?\u001a\u00020@J\u0006\u0010A\u001a\u00020\u0011R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R-\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R-\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\nR*\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00110\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0011`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0012\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lcom/agog/mathdisplay/parse/MTMathAtomFactory;", "", "()V", "accentToCommands", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "accents", "aliases", "getAliases", "()Ljava/util/HashMap;", "delimValueToName", "delimiters", "fontStyleWithName", "Lcom/agog/mathdisplay/parse/MTFontStyle;", "getFontStyleWithName", "supportedLatexSymbols", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "textToLatexSymbolNames", "accentName", "accent", "Lcom/agog/mathdisplay/parse/MTAccent;", "accentWithName", "addLatexSymbol", "", "name", "atom", "atomForLatexSymbolName", "symbolName", "boundaryAtomForDelimiterName", "delimName", "delimiterNameForBoundaryAtom", "boundary", "divide", "fontNameForStyle", "fontStyle", "fractionWithNumerator", "Lcom/agog/mathdisplay/parse/MTFraction;", NetConfig.PARAM_CREATION_NUM, "Lcom/agog/mathdisplay/parse/MTMathList;", "denom", "numStr", "denominatorStr", "latexSymbolNameForAtom", "mathListForCharacters", "chars", "operatorWithName", "Lcom/agog/mathdisplay/parse/MTLargeOperator;", "limits", "", "operatorWithName$mathdisplaylib_release", "placeholder", "placeholderFraction", "placeholderList", "placeholderRadical", "Lcom/agog/mathdisplay/parse/MTRadical;", "placeholderSquareRoot", "supportedLatexSymbolNames", "", "tableWithEnvironment", "env", "cells", "", SNSAuthProvider.VALUE_SNS_ERROR, "Lcom/agog/mathdisplay/parse/MTParseError;", "times", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public class MTMathAtomFactory {
    private final HashMap<String, String> accentToCommands;
    private final HashMap<String, String> accents;
    private final HashMap<String, String> aliases;
    private final HashMap<String, String> delimValueToName;
    private final HashMap<String, String> delimiters;
    private final HashMap<String, MTFontStyle> fontStyleWithName;
    private final HashMap<String, MTMathAtom> supportedLatexSymbols;
    private final HashMap<String, String> textToLatexSymbolNames;

    /* JADX INFO: compiled from: MTMathAtomFactory.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MTFontStyle.values().length];
            iArr[MTFontStyle.KMTFontStyleDefault.ordinal()] = 1;
            iArr[MTFontStyle.KMTFontStyleRoman.ordinal()] = 2;
            iArr[MTFontStyle.KMTFontStyleBold.ordinal()] = 3;
            iArr[MTFontStyle.KMTFontStyleFraktur.ordinal()] = 4;
            iArr[MTFontStyle.KMTFontStyleCaligraphic.ordinal()] = 5;
            iArr[MTFontStyle.KMTFontStyleItalic.ordinal()] = 6;
            iArr[MTFontStyle.KMTFontStyleSansSerif.ordinal()] = 7;
            iArr[MTFontStyle.KMTFontStyleBlackboard.ordinal()] = 8;
            iArr[MTFontStyle.KMTFontStyleTypewriter.ordinal()] = 9;
            iArr[MTFontStyle.KMTFontStyleBoldItalic.ordinal()] = 10;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public MTMathAtomFactory() {
        char c;
        Pair pair = TuplesKt.to("mathcal", MTFontStyle.KMTFontStyleCaligraphic);
        char c2 = 7;
        Pair pair2 = TuplesKt.to("mit", MTFontStyle.KMTFontStyleItalic);
        char c3 = CharUtils.CR;
        this.fontStyleWithName = MapsKt.hashMapOf(TuplesKt.to("mathnormal", MTFontStyle.KMTFontStyleDefault), TuplesKt.to("mathrm", MTFontStyle.KMTFontStyleRoman), TuplesKt.to("textrm", MTFontStyle.KMTFontStyleRoman), TuplesKt.to("rm", MTFontStyle.KMTFontStyleRoman), TuplesKt.to("mathbf", MTFontStyle.KMTFontStyleBold), TuplesKt.to("bf", MTFontStyle.KMTFontStyleBold), TuplesKt.to("textbf", MTFontStyle.KMTFontStyleBold), pair, TuplesKt.to("cal", MTFontStyle.KMTFontStyleCaligraphic), TuplesKt.to("mathtt", MTFontStyle.KMTFontStyleTypewriter), TuplesKt.to("texttt", MTFontStyle.KMTFontStyleTypewriter), TuplesKt.to("mathit", MTFontStyle.KMTFontStyleItalic), TuplesKt.to("textit", MTFontStyle.KMTFontStyleItalic), pair2, TuplesKt.to("mathsf", MTFontStyle.KMTFontStyleSansSerif), TuplesKt.to("textsf", MTFontStyle.KMTFontStyleSansSerif), TuplesKt.to("mathfrak", MTFontStyle.KMTFontStyleFraktur), TuplesKt.to("frak", MTFontStyle.KMTFontStyleFraktur), TuplesKt.to("mathbb", MTFontStyle.KMTFontStyleBlackboard), TuplesKt.to("mathbfit", MTFontStyle.KMTFontStyleBoldItalic), TuplesKt.to("bm", MTFontStyle.KMTFontStyleBoldItalic), TuplesKt.to("text", MTFontStyle.KMTFontStyleRoman));
        HashMap<String, MTMathAtom> mapHashMapOf = MapsKt.hashMapOf(TuplesKt.to("square", placeholder()), TuplesKt.to("alpha", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "α")), TuplesKt.to("beta", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "β")), TuplesKt.to("gamma", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "γ")), TuplesKt.to("delta", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "δ")), TuplesKt.to("varepsilon", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ε")), TuplesKt.to("zeta", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ζ")), TuplesKt.to("eta", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "η")), TuplesKt.to("theta", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "θ")), TuplesKt.to("iota", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ι")), TuplesKt.to("kappa", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "κ")), TuplesKt.to("lambda", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "λ")), TuplesKt.to("mu", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "μ")), TuplesKt.to("nu", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ν")), TuplesKt.to("xi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ξ")), TuplesKt.to("omicron", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ο")), TuplesKt.to("pi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "π")), TuplesKt.to("rho", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ρ")), TuplesKt.to("varsigma", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ς")), TuplesKt.to("sigma", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "σ")), TuplesKt.to("tau", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "τ")), TuplesKt.to("upsilon", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "υ")), TuplesKt.to("varphi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "φ")), TuplesKt.to("chi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "χ")), TuplesKt.to("psi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ψ")), TuplesKt.to("omega", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ω")), TuplesKt.to("vartheta", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ϑ")), TuplesKt.to("phi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ϕ")), TuplesKt.to("varpi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ϖ")), TuplesKt.to("varkappa", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ϰ")), TuplesKt.to("varrho", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ϱ")), TuplesKt.to("epsilon", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "ϵ")), TuplesKt.to(ExifInterface.TAG_GAMMA, new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Γ")), TuplesKt.to("Delta", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Δ")), TuplesKt.to("Theta", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Θ")), TuplesKt.to("Lambda", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Λ")), TuplesKt.to("Xi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Ξ")), TuplesKt.to("Pi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Π")), TuplesKt.to("Sigma", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Σ")), TuplesKt.to("Upsilon", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Υ")), TuplesKt.to("Phi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Φ")), TuplesKt.to("Psi", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Ψ")), TuplesKt.to("Omega", new MTMathAtom(MTMathAtomType.KMTMathAtomVariable, "Ω")), TuplesKt.to("lceil", new MTMathAtom(MTMathAtomType.KMTMathAtomOpen, "⌈")), TuplesKt.to("lfloor", new MTMathAtom(MTMathAtomType.KMTMathAtomOpen, "⌊")), TuplesKt.to("langle", new MTMathAtom(MTMathAtomType.KMTMathAtomOpen, "⟨")), TuplesKt.to("lgroup", new MTMathAtom(MTMathAtomType.KMTMathAtomOpen, "⟮")), TuplesKt.to("rceil", new MTMathAtom(MTMathAtomType.KMTMathAtomClose, "⌉")), TuplesKt.to("rfloor", new MTMathAtom(MTMathAtomType.KMTMathAtomClose, "⌋")), TuplesKt.to("rangle", new MTMathAtom(MTMathAtomType.KMTMathAtomClose, "⟩")), TuplesKt.to("rgroup", new MTMathAtom(MTMathAtomType.KMTMathAtomClose, "⟯")), TuplesKt.to("leftarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "←")), TuplesKt.to("uparrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "↑")), TuplesKt.to("rightarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "→")), TuplesKt.to("downarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "↓")), TuplesKt.to("leftrightarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "↔")), TuplesKt.to("updownarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "↕")), TuplesKt.to("nwarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "↖")), TuplesKt.to("nearrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "↗")), TuplesKt.to("searrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "↘")), TuplesKt.to("swarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "↙")), TuplesKt.to("mapsto", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "↦")), TuplesKt.to("Leftarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⇐")), TuplesKt.to("Uparrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⇑")), TuplesKt.to("Rightarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⇒")), TuplesKt.to("Downarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⇓")), TuplesKt.to("Leftrightarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⇔")), TuplesKt.to("Updownarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⇕")), TuplesKt.to("longleftarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⟵")), TuplesKt.to("longrightarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⟶")), TuplesKt.to("longleftrightarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⟷")), TuplesKt.to("Longleftarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⟸")), TuplesKt.to("Longrightarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⟹")), TuplesKt.to("Longleftrightarrow", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⟺")), TuplesKt.to("leq", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≤")), TuplesKt.to("geq", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≥")), TuplesKt.to("neq", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≠")), TuplesKt.to(Language.LA_IN, new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "∈")), TuplesKt.to("notin", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "∉")), TuplesKt.to("ni", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "∋")), TuplesKt.to("propto", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "∝")), TuplesKt.to("mid", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "∣")), TuplesKt.to("parallel", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "∥")), TuplesKt.to("sim", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "∼")), TuplesKt.to("simeq", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≃")), TuplesKt.to("cong", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≅")), TuplesKt.to("approx", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≈")), TuplesKt.to("asymp", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≍")), TuplesKt.to("doteq", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≐")), TuplesKt.to("equiv", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≡")), TuplesKt.to("gg", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≪")), TuplesKt.to("ll", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≫")), TuplesKt.to("prec", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≺")), TuplesKt.to("succ", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "≻")), TuplesKt.to("subset", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⊂")), TuplesKt.to("supset", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⊃")), TuplesKt.to("subseteq", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⊆")), TuplesKt.to("supseteq", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⊇")), TuplesKt.to("sqsubset", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⊏")), TuplesKt.to("sqsupset", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⊐")), TuplesKt.to("sqsubseteq", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⊑")), TuplesKt.to("sqsupseteq", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⊒")), TuplesKt.to("models", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⊧")), TuplesKt.to("perp", new MTMathAtom(MTMathAtomType.KMTMathAtomRelation, "⟂")), TuplesKt.to("times", times()), TuplesKt.to("div", divide()), TuplesKt.to("pm", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "±")), TuplesKt.to("dagger", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "†")), TuplesKt.to("ddagger", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "‡")), TuplesKt.to("mp", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "∓")), TuplesKt.to("setminus", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "∖")), TuplesKt.to("ast", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "∗")), TuplesKt.to("circ", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "∘")), TuplesKt.to("bullet", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "∙")), TuplesKt.to("wedge", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "∧")), TuplesKt.to("vee", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "∨")), TuplesKt.to("cap", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "∩")), TuplesKt.to("cup", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "∪")), TuplesKt.to("wr", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "≀")), TuplesKt.to("uplus", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⊎")), TuplesKt.to("sqcap", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⊓")), TuplesKt.to("sqcup", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⊔")), TuplesKt.to("oplus", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⊕")), TuplesKt.to("ominus", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⊖")), TuplesKt.to("otimes", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⊗")), TuplesKt.to("oslash", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⊘")), TuplesKt.to("odot", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⊙")), TuplesKt.to("star", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⋆")), TuplesKt.to("cdot", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⋅")), TuplesKt.to("amalg", new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "⨿")), TuplesKt.to("log", operatorWithName$mathdisplaylib_release("log", false)), TuplesKt.to("lg", operatorWithName$mathdisplaylib_release("lg", false)), TuplesKt.to("ln", operatorWithName$mathdisplaylib_release("ln", false)), TuplesKt.to("sin", operatorWithName$mathdisplaylib_release("sin", false)), TuplesKt.to("arcsin", operatorWithName$mathdisplaylib_release("arcsin", false)), TuplesKt.to("sinh", operatorWithName$mathdisplaylib_release("sinh", false)), TuplesKt.to("cos", operatorWithName$mathdisplaylib_release("cos", false)), TuplesKt.to("arccos", operatorWithName$mathdisplaylib_release("arccos", false)), TuplesKt.to("cosh", operatorWithName$mathdisplaylib_release("cosh", false)), TuplesKt.to("tan", operatorWithName$mathdisplaylib_release("tan", false)), TuplesKt.to("arctan", operatorWithName$mathdisplaylib_release("arctan", false)), TuplesKt.to("tanh", operatorWithName$mathdisplaylib_release("tanh", false)), TuplesKt.to("cot", operatorWithName$mathdisplaylib_release("cot", false)), TuplesKt.to("coth", operatorWithName$mathdisplaylib_release("coth", false)), TuplesKt.to("sec", operatorWithName$mathdisplaylib_release("sec", false)), TuplesKt.to("csc", operatorWithName$mathdisplaylib_release("csc", false)), TuplesKt.to("arg", operatorWithName$mathdisplaylib_release("arg", false)), TuplesKt.to("ker", operatorWithName$mathdisplaylib_release("ker", false)), TuplesKt.to("dim", operatorWithName$mathdisplaylib_release("dim", false)), TuplesKt.to("hom", operatorWithName$mathdisplaylib_release("hom", false)), TuplesKt.to("exp", operatorWithName$mathdisplaylib_release("exp", false)), TuplesKt.to("deg", operatorWithName$mathdisplaylib_release("deg", false)), TuplesKt.to("lim", operatorWithName$mathdisplaylib_release("lim", true)), TuplesKt.to("limsup", operatorWithName$mathdisplaylib_release("lim sup", true)), TuplesKt.to("liminf", operatorWithName$mathdisplaylib_release("lim inf", true)), TuplesKt.to("max", operatorWithName$mathdisplaylib_release("max", true)), TuplesKt.to("min", operatorWithName$mathdisplaylib_release("min", true)), TuplesKt.to("sup", operatorWithName$mathdisplaylib_release("sup", true)), TuplesKt.to("inf", operatorWithName$mathdisplaylib_release("inf", true)), TuplesKt.to("det", operatorWithName$mathdisplaylib_release("det", true)), TuplesKt.to("Pr", operatorWithName$mathdisplaylib_release("Pr", true)), TuplesKt.to("gcd", operatorWithName$mathdisplaylib_release("gcd", true)), TuplesKt.to("prod", operatorWithName$mathdisplaylib_release("∏", true)), TuplesKt.to("coprod", operatorWithName$mathdisplaylib_release("∐", true)), TuplesKt.to("sum", operatorWithName$mathdisplaylib_release("∑", true)), TuplesKt.to("int", operatorWithName$mathdisplaylib_release("∫", false)), TuplesKt.to("oint", operatorWithName$mathdisplaylib_release("∮", false)), TuplesKt.to("bigwedge", operatorWithName$mathdisplaylib_release("⋀", true)), TuplesKt.to("bigvee", operatorWithName$mathdisplaylib_release("⋁", true)), TuplesKt.to("bigcap", operatorWithName$mathdisplaylib_release("⋂", true)), TuplesKt.to("bigcup", operatorWithName$mathdisplaylib_release("⋃", true)), TuplesKt.to("bigodot", operatorWithName$mathdisplaylib_release("⨀", true)), TuplesKt.to("bigoplus", operatorWithName$mathdisplaylib_release("⨁", true)), TuplesKt.to("bigotimes", operatorWithName$mathdisplaylib_release("⨂", true)), TuplesKt.to("biguplus", operatorWithName$mathdisplaylib_release("⨄", true)), TuplesKt.to("bigsqcup", operatorWithName$mathdisplaylib_release("⨆", true)), TuplesKt.to("{", new MTMathAtom(MTMathAtomType.KMTMathAtomOpen, "{")), TuplesKt.to(StringSubstitutor.DEFAULT_VAR_END, new MTMathAtom(MTMathAtomType.KMTMathAtomClose, StringSubstitutor.DEFAULT_VAR_END)), TuplesKt.to("$", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "$")), TuplesKt.to("&", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "&")), TuplesKt.to(HyperMenuUtils.GROUP_TITLE_START, new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, HyperMenuUtils.GROUP_TITLE_START)), TuplesKt.to("%", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "%")), TuplesKt.to("_", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "_")), TuplesKt.to(StringUtils.SPACE, new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, StringUtils.SPACE)), TuplesKt.to("backslash", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "\\")), TuplesKt.to("colon", new MTMathAtom(MTMathAtomType.KMTMathAtomPunctuation, ":")), TuplesKt.to("cdotp", new MTMathAtom(MTMathAtomType.KMTMathAtomPunctuation, "·")), TuplesKt.to("degree", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "°")), TuplesKt.to("neg", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "¬")), TuplesKt.to("angstrom", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "Å")), TuplesKt.to("|", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "‖")), TuplesKt.to("vert", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "|")), TuplesKt.to("ldots", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "…")), TuplesKt.to("prime", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "′")), TuplesKt.to("hbar", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "ℏ")), TuplesKt.to("Im", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "ℑ")), TuplesKt.to("ell", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "ℓ")), TuplesKt.to("wp", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "℘")), TuplesKt.to("Re", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "ℜ")), TuplesKt.to("mho", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "℧")), TuplesKt.to("aleph", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "ℵ")), TuplesKt.to("forall", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "∀")), TuplesKt.to("exists", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "∃")), TuplesKt.to("emptyset", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "∅")), TuplesKt.to("nabla", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "∇")), TuplesKt.to("infty", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "∞")), TuplesKt.to("angle", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "∠")), TuplesKt.to(Layer.KEY_TOP, new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "⊤")), TuplesKt.to("bot", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "⊥")), TuplesKt.to("vdots", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "⋮")), TuplesKt.to("cdots", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "⋯")), TuplesKt.to("ddots", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "⋱")), TuplesKt.to("triangle", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "△")), TuplesKt.to("imath", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "𝚤")), TuplesKt.to("jmath", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "𝚥")), TuplesKt.to("partial", new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "𝜕")), TuplesKt.to(",", new MTMathSpace(3.0f)), TuplesKt.to(">", new MTMathSpace(4.0f)), TuplesKt.to(";", new MTMathSpace(5.0f)), TuplesKt.to("!", new MTMathSpace(-3.0f)), TuplesKt.to("quad", new MTMathSpace(18.0f)), TuplesKt.to("qquad", new MTMathSpace(36.0f)), TuplesKt.to("displaystyle", new MTMathStyle(MTLineStyle.KMTLineStyleDisplay)), TuplesKt.to("textstyle", new MTMathStyle(MTLineStyle.KMTLineStyleText)), TuplesKt.to("scriptstyle", new MTMathStyle(MTLineStyle.KMTLineStyleScript)), TuplesKt.to("scriptscriptstyle", new MTMathStyle(MTLineStyle.KMTLineStyleScriptScript)));
        this.supportedLatexSymbols = mapHashMapOf;
        this.aliases = MapsKt.hashMapOf(TuplesKt.to("lnot", "neg"), TuplesKt.to("land", "wedge"), TuplesKt.to("lor", "vee"), TuplesKt.to("ne", "neq"), TuplesKt.to("le", "leq"), TuplesKt.to("ge", "geq"), TuplesKt.to("lbrace", "{"), TuplesKt.to("rbrace", StringSubstitutor.DEFAULT_VAR_END), TuplesKt.to("Vert", "|"), TuplesKt.to("gets", "leftarrow"), TuplesKt.to("to", "rightarrow"), TuplesKt.to("iff", "Longleftrightarrow"), TuplesKt.to("AA", "angstrom"));
        this.textToLatexSymbolNames = new HashMap<>();
        for (Map.Entry<String, MTMathAtom> entry : mapHashMapOf.entrySet()) {
            String key = entry.getKey();
            MTMathAtom value = entry.getValue();
            if (value.getNucleus().length() != 0) {
                char c4 = c3;
                String str = this.textToLatexSymbolNames.get(value.getNucleus());
                if (str != null) {
                    c = c2;
                    if (key.length() <= str.length() && (key.length() != str.length() || key.compareTo(str) <= 0)) {
                    }
                    c3 = c4;
                    c2 = c;
                } else {
                    c = c2;
                }
                this.textToLatexSymbolNames.put(value.getNucleus(), key);
                c3 = c4;
                c2 = c;
            }
        }
        char c5 = c3;
        char c6 = c2;
        Pair[] pairArr = new Pair[12];
        pairArr[0] = TuplesKt.to("grave", "̀");
        pairArr[1] = TuplesKt.to("acute", "́");
        pairArr[2] = TuplesKt.to("hat", "̂");
        pairArr[3] = TuplesKt.to("tilde", "̃");
        pairArr[4] = TuplesKt.to("bar", "̄");
        pairArr[5] = TuplesKt.to("breve", "̆");
        pairArr[6] = TuplesKt.to("dot", "̇");
        pairArr[c6] = TuplesKt.to("ddot", "̈");
        pairArr[8] = TuplesKt.to("check", "̌");
        pairArr[9] = TuplesKt.to("vec", "⃗");
        pairArr[10] = TuplesKt.to("widehat", "̂");
        pairArr[11] = TuplesKt.to("widetilde", "̃");
        HashMap<String, String> mapHashMapOf2 = MapsKt.hashMapOf(pairArr);
        this.accents = mapHashMapOf2;
        this.accentToCommands = new HashMap<>();
        for (Map.Entry<String, String> entry2 : mapHashMapOf2.entrySet()) {
            String key2 = entry2.getKey();
            String value2 = entry2.getValue();
            String str2 = this.accentToCommands.get(value2);
            if (str2 == null || (key2.length() <= str2.length() && (key2.length() != str2.length() || key2.compareTo(str2) <= 0))) {
                this.accentToCommands.put(value2, key2);
            }
        }
        Pair[] pairArr2 = new Pair[32];
        pairArr2[0] = TuplesKt.to(".", "");
        pairArr2[1] = TuplesKt.to("(", "(");
        pairArr2[2] = TuplesKt.to(")", ")");
        pairArr2[3] = TuplesKt.to("[", "[");
        pairArr2[4] = TuplesKt.to("]", "]");
        pairArr2[5] = TuplesKt.to("<", "〈");
        pairArr2[6] = TuplesKt.to(">", "〉");
        pairArr2[c6] = TuplesKt.to(InternalZipConstants.ZIP_FILE_SEPARATOR, InternalZipConstants.ZIP_FILE_SEPARATOR);
        pairArr2[8] = TuplesKt.to("\\", "\\");
        pairArr2[9] = TuplesKt.to("|", "|");
        pairArr2[10] = TuplesKt.to("lgroup", "⟮");
        pairArr2[11] = TuplesKt.to("rgroup", "⟯");
        pairArr2[12] = TuplesKt.to("||", "‖");
        pairArr2[c5] = TuplesKt.to("Vert", "‖");
        pairArr2[14] = TuplesKt.to("vert", "|");
        pairArr2[15] = TuplesKt.to("uparrow", "↑");
        pairArr2[16] = TuplesKt.to("downarrow", "↓");
        pairArr2[17] = TuplesKt.to("updownarrow", "↕");
        pairArr2[18] = TuplesKt.to("Uparrow", "21D1");
        pairArr2[19] = TuplesKt.to("Downarrow", "21D3");
        pairArr2[20] = TuplesKt.to("Updownarrow", "21D5");
        pairArr2[21] = TuplesKt.to("backslash", "\\");
        pairArr2[22] = TuplesKt.to("rangle", "〉");
        pairArr2[23] = TuplesKt.to("langle", "〈");
        pairArr2[24] = TuplesKt.to("rbrace", StringSubstitutor.DEFAULT_VAR_END);
        pairArr2[25] = TuplesKt.to(StringSubstitutor.DEFAULT_VAR_END, StringSubstitutor.DEFAULT_VAR_END);
        pairArr2[26] = TuplesKt.to("{", "{");
        pairArr2[27] = TuplesKt.to("lbrace", "{");
        pairArr2[28] = TuplesKt.to("lceil", "⌈");
        pairArr2[29] = TuplesKt.to("rceil", "⌉");
        pairArr2[30] = TuplesKt.to("lfloor", "⌊");
        pairArr2[31] = TuplesKt.to("rfloor", "⌋");
        HashMap<String, String> mapHashMapOf3 = MapsKt.hashMapOf(pairArr2);
        this.delimiters = mapHashMapOf3;
        this.delimValueToName = new HashMap<>();
        for (Map.Entry<String, String> entry3 : mapHashMapOf3.entrySet()) {
            String key3 = entry3.getKey();
            String value3 = entry3.getValue();
            String str3 = this.delimValueToName.get(value3);
            if (str3 == null || (key3.length() <= str3.length() && (key3.length() != str3.length() || key3.compareTo(str3) <= 0))) {
                this.delimValueToName.put(value3, key3);
            }
        }
    }

    public final MTMathAtom times() {
        return new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "×");
    }

    private final MTMathAtom divide() {
        return new MTMathAtom(MTMathAtomType.KMTMathAtomBinaryOperator, "÷");
    }

    private final MTMathAtom placeholder() {
        return new MTMathAtom(MTMathAtomType.KMTMathAtomPlaceholder, "□");
    }

    private final MTMathList placeholderList() throws MathDisplayException {
        MTMathList mTMathList = new MTMathList(new MTMathAtom[0]);
        mTMathList.addAtom(placeholder());
        return mTMathList;
    }

    private final MTFraction placeholderFraction() {
        MTFraction mTFraction = new MTFraction();
        mTFraction.setNumerator(placeholderList());
        mTFraction.setDenominator(placeholderList());
        return mTFraction;
    }

    private final MTRadical placeholderRadical() {
        MTRadical mTRadical = new MTRadical();
        mTRadical.setDegree(placeholderList());
        mTRadical.setRadicand(placeholderList());
        return mTRadical;
    }

    private final MTRadical placeholderSquareRoot() {
        MTRadical mTRadical = new MTRadical();
        mTRadical.setRadicand(placeholderList());
        return mTRadical;
    }

    public final MTLargeOperator operatorWithName$mathdisplaylib_release(String name, boolean limits) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new MTLargeOperator(name, limits);
    }

    public final MTMathList mathListForCharacters(String chars) throws MathDisplayException {
        Intrinsics.checkNotNullParameter(chars, "chars");
        int i = 0;
        MTMathList mTMathList = new MTMathList(new MTMathAtom[0]);
        int length = chars.length();
        while (i < length) {
            char cCharAt = chars.charAt(i);
            i++;
            MTMathAtom mTMathAtomAtomForCharacter = MTMathAtom.INSTANCE.atomForCharacter(cCharAt);
            if (mTMathAtomAtomForCharacter != null) {
                mTMathList.addAtom(mTMathAtomAtomForCharacter);
            }
        }
        return mTMathList;
    }

    public final String latexSymbolNameForAtom(MTMathAtom atom) {
        Intrinsics.checkNotNullParameter(atom, "atom");
        if (atom.getNucleus().length() == 0) {
            return null;
        }
        return this.textToLatexSymbolNames.get(atom.getNucleus());
    }

    public final void addLatexSymbol(String name, MTMathAtom atom) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(atom, "atom");
        this.supportedLatexSymbols.put(name, atom);
        if (atom.getNucleus().length() > 0) {
            this.textToLatexSymbolNames.put(atom.getNucleus(), name);
        }
    }

    public final List<String> supportedLatexSymbolNames() {
        Set<String> setKeySet = this.supportedLatexSymbols.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "supportedLatexSymbols.keys");
        return CollectionsKt.sorted(setKeySet);
    }

    public final MTAccent accentWithName(String accentName) {
        Intrinsics.checkNotNullParameter(accentName, "accentName");
        String str = this.accents.get(accentName);
        if (str != null) {
            return new MTAccent(str);
        }
        return null;
    }

    public final String accentName(MTAccent accent) {
        Intrinsics.checkNotNullParameter(accent, "accent");
        return this.accentToCommands.get(accent.getNucleus());
    }

    public final MTMathAtom boundaryAtomForDelimiterName(String delimName) {
        Intrinsics.checkNotNullParameter(delimName, "delimName");
        String str = this.delimiters.get(delimName);
        if (str == null) {
            return null;
        }
        return new MTMathAtom(MTMathAtomType.KMTMathAtomBoundary, str);
    }

    public final String delimiterNameForBoundaryAtom(MTMathAtom boundary) {
        Intrinsics.checkNotNullParameter(boundary, "boundary");
        if (boundary.getType() != MTMathAtomType.KMTMathAtomBoundary) {
            return null;
        }
        return this.delimValueToName.get(boundary.getNucleus());
    }

    public final HashMap<String, MTFontStyle> getFontStyleWithName() {
        return this.fontStyleWithName;
    }

    public final String fontNameForStyle(MTFontStyle fontStyle) {
        Intrinsics.checkNotNullParameter(fontStyle, "fontStyle");
        switch (WhenMappings.$EnumSwitchMapping$0[fontStyle.ordinal()]) {
            case 1:
                return "mathnormal";
            case 2:
                return "mathrm";
            case 3:
                return "mathbf";
            case 4:
                return "mathfrak";
            case 5:
                return "mathcal";
            case 6:
                return "mathit";
            case 7:
                return "mathsf";
            case 8:
                return "mathbb";
            case 9:
                return "mathtt";
            case 10:
                return "bm";
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final MTFraction fractionWithNumerator(MTMathList num, MTMathList denom) {
        MTFraction mTFraction = new MTFraction();
        mTFraction.setNumerator(num);
        mTFraction.setDenominator(denom);
        return mTFraction;
    }

    public final MTFraction fractionWithNumerator(String numStr, String denominatorStr) {
        Intrinsics.checkNotNullParameter(numStr, "numStr");
        Intrinsics.checkNotNullParameter(denominatorStr, "denominatorStr");
        return fractionWithNumerator(mathListForCharacters(numStr), mathListForCharacters(denominatorStr));
    }

    public final MTMathAtom tableWithEnvironment(String env, List<List<MTMathList>> cells, MTParseError error) throws MathDisplayException {
        Intrinsics.checkNotNullParameter(cells, "cells");
        Intrinsics.checkNotNullParameter(error, "error");
        MTMathTable mTMathTable = new MTMathTable(env);
        mTMathTable.setCells(cells);
        HashMap mapHashMapOf = MapsKt.hashMapOf(TuplesKt.to("matrix", new String[]{""}), TuplesKt.to("pmatrix", new String[]{"(", ")"}), TuplesKt.to("bmatrix", new String[]{"[", "]"}), TuplesKt.to("Bmatrix", new String[]{"{", StringSubstitutor.DEFAULT_VAR_END}), TuplesKt.to("vmatrix", new String[]{"vert", "vert"}), TuplesKt.to("Vmatrix", new String[]{"Vert", "Vert"}));
        if (mapHashMapOf.containsKey(env)) {
            mTMathTable.setEnvironment("matrix");
            mTMathTable.setInterRowAdditionalSpacing(0.0f);
            mTMathTable.setInterColumnSpacing(18.0f);
            MTMathStyle mTMathStyle = new MTMathStyle(MTLineStyle.KMTLineStyleText);
            int size = mTMathTable.getCells().size();
            int i = 0;
            while (i < size) {
                int i2 = i + 1;
                List<MTMathList> list = mTMathTable.getCells().get(i);
                int size2 = list.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    list.get(i3).insertAtom(mTMathStyle, 0);
                }
                i = i2;
            }
            String[] strArr = (String[]) mapHashMapOf.get(env);
            if (strArr != null && strArr.length == 2) {
                MTInner mTInner = new MTInner();
                mTInner.setLeftBoundary(boundaryAtomForDelimiterName(strArr[0]));
                mTInner.setRightBoundary(boundaryAtomForDelimiterName(strArr[1]));
                mTInner.setInnerList(new MTMathList(mTMathTable));
                return mTInner;
            }
            return mTMathTable;
        }
        if (env == null) {
            mTMathTable.setInterRowAdditionalSpacing(1.0f);
            mTMathTable.setInterColumnSpacing(0.0f);
            int iNumColumns = mTMathTable.numColumns();
            for (int i4 = 0; i4 < iNumColumns; i4++) {
                mTMathTable.setAlignment(MTColumnAlignment.KMTColumnAlignmentLeft, i4);
            }
            return mTMathTable;
        }
        if (Intrinsics.areEqual(env, "eqalign") || Intrinsics.areEqual(env, "split") || Intrinsics.areEqual(env, "aligned")) {
            if (mTMathTable.numColumns() != 2) {
                error.copyFrom(new MTParseError(MTParseErrors.InvalidNumColumns, Intrinsics.stringPlus(env, " environment can only have 2 columns")));
                return null;
            }
            MTMathAtom mTMathAtom = new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "");
            int size3 = mTMathTable.getCells().size();
            int i5 = 0;
            while (i5 < size3) {
                int i6 = i5 + 1;
                List<MTMathList> list2 = mTMathTable.getCells().get(i5);
                if (list2.size() > 1) {
                    list2.get(1).insertAtom(mTMathAtom, 0);
                }
                i5 = i6;
            }
            mTMathTable.setInterRowAdditionalSpacing(1.0f);
            mTMathTable.setInterColumnSpacing(0.0f);
            mTMathTable.setAlignment(MTColumnAlignment.KMTColumnAlignmentRight, 0);
            mTMathTable.setAlignment(MTColumnAlignment.KMTColumnAlignmentLeft, 1);
            return mTMathTable;
        }
        if (Intrinsics.areEqual(env, "displaylines") || Intrinsics.areEqual(env, "gather")) {
            if (mTMathTable.numColumns() != 1) {
                error.copyFrom(new MTParseError(MTParseErrors.InvalidNumColumns, Intrinsics.stringPlus(env, " environment can only have 1 column")));
                return null;
            }
            mTMathTable.setInterRowAdditionalSpacing(1.0f);
            mTMathTable.setInterColumnSpacing(0.0f);
            mTMathTable.setAlignment(MTColumnAlignment.KMTColumnAlignmentCenter, 0);
            return mTMathTable;
        }
        if (Intrinsics.areEqual(env, "eqnarray")) {
            if (mTMathTable.numColumns() != 3) {
                error.copyFrom(new MTParseError(MTParseErrors.InvalidNumColumns, "eqnarray environment can only have 3 columns"));
                return null;
            }
            mTMathTable.setInterRowAdditionalSpacing(1.0f);
            mTMathTable.setInterColumnSpacing(18.0f);
            mTMathTable.setAlignment(MTColumnAlignment.KMTColumnAlignmentRight, 0);
            mTMathTable.setAlignment(MTColumnAlignment.KMTColumnAlignmentCenter, 1);
            mTMathTable.setAlignment(MTColumnAlignment.KMTColumnAlignmentLeft, 2);
            return mTMathTable;
        }
        if (Intrinsics.areEqual(env, "cases")) {
            if (mTMathTable.numColumns() != 2) {
                error.copyFrom(new MTParseError(MTParseErrors.InvalidNumColumns, "cases environment can only have 2 columns"));
                return null;
            }
            mTMathTable.setInterRowAdditionalSpacing(0.0f);
            mTMathTable.setInterColumnSpacing(18.0f);
            mTMathTable.setAlignment(MTColumnAlignment.KMTColumnAlignmentLeft, 0);
            mTMathTable.setAlignment(MTColumnAlignment.KMTColumnAlignmentLeft, 1);
            MTMathStyle mTMathStyle2 = new MTMathStyle(MTLineStyle.KMTLineStyleText);
            int size4 = mTMathTable.getCells().size();
            int i7 = 0;
            while (i7 < size4) {
                int i8 = i7 + 1;
                List<MTMathList> list3 = mTMathTable.getCells().get(i7);
                int size5 = list3.size();
                for (int i9 = 0; i9 < size5; i9++) {
                    list3.get(i9).insertAtom(mTMathStyle2, 0);
                }
                i7 = i8;
            }
            MTInner mTInner2 = new MTInner();
            mTInner2.setLeftBoundary(boundaryAtomForDelimiterName("{"));
            mTInner2.setRightBoundary(boundaryAtomForDelimiterName("."));
            MTMathAtom mTMathAtomAtomForLatexSymbolName = atomForLatexSymbolName(",");
            if (mTMathAtomAtomForLatexSymbolName != null) {
                mTInner2.setInnerList(new MTMathList(mTMathAtomAtomForLatexSymbolName, mTMathTable));
            }
            return mTInner2;
        }
        error.copyFrom(new MTParseError(MTParseErrors.InvalidEnv, Intrinsics.stringPlus("Unknown environment: ", env)));
        return null;
    }

    public final HashMap<String, String> getAliases() {
        return this.aliases;
    }

    public final MTMathAtom atomForLatexSymbolName(String symbolName) {
        Intrinsics.checkNotNullParameter(symbolName, "symbolName");
        String str = this.aliases.get(symbolName);
        if (str != null) {
            symbolName = str;
        }
        MTMathAtom mTMathAtom = this.supportedLatexSymbols.get(symbolName);
        if (mTMathAtom != null) {
            return mTMathAtom.copyDeep();
        }
        return null;
    }
}
