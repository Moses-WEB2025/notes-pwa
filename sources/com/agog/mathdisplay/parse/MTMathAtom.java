package com.agog.mathdisplay.parse;

import ch.qos.logback.core.CoreConstants;
import com.miui.maml.StylesManager;
import com.miui.maml.data.VariableBinder;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import miui.cloud.sync.FamilyInfo;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0016\u0018\u0000 92\u00020\u0001:\u00019B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010+\u001a\u00020\u0000H\u0016J\u000e\u0010,\u001a\u00020\u00002\u0006\u0010-\u001a\u00020\u0000J\u0006\u0010.\u001a\u00020\u0005J\u0010\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u0005H\u0002J\b\u00102\u001a\u00020\u0000H\u0016J\u000e\u00102\u001a\u00020\u00002\u0006\u00103\u001a\u00020\u0000J\u000e\u00104\u001a\u0002002\u0006\u0010-\u001a\u00020\u0000J\u0006\u00105\u001a\u000206J\b\u00107\u001a\u00020\u0005H\u0016J\u000e\u00108\u001a\u00020\u00052\u0006\u00101\u001a\u00020\u0005R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR \u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00000\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR(\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R(\u0010$\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010!\"\u0004\b&\u0010#R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*¨\u0006:"}, d2 = {"Lcom/agog/mathdisplay/parse/MTMathAtom;", "", "type", "Lcom/agog/mathdisplay/parse/MTMathAtomType;", "nucleus", "", "(Lcom/agog/mathdisplay/parse/MTMathAtomType;Ljava/lang/String;)V", "fontStyle", "Lcom/agog/mathdisplay/parse/MTFontStyle;", "getFontStyle", "()Lcom/agog/mathdisplay/parse/MTFontStyle;", "setFontStyle", "(Lcom/agog/mathdisplay/parse/MTFontStyle;)V", "fusedAtoms", "", "getFusedAtoms", "()Ljava/util/List;", "setFusedAtoms", "(Ljava/util/List;)V", "indexRange", "Lcom/agog/mathdisplay/parse/NSRange;", "getIndexRange", "()Lcom/agog/mathdisplay/parse/NSRange;", "setIndexRange", "(Lcom/agog/mathdisplay/parse/NSRange;)V", "getNucleus", "()Ljava/lang/String;", "setNucleus", "(Ljava/lang/String;)V", "value", "Lcom/agog/mathdisplay/parse/MTMathList;", "subScript", "getSubScript", "()Lcom/agog/mathdisplay/parse/MTMathList;", "setSubScript", "(Lcom/agog/mathdisplay/parse/MTMathList;)V", "superScript", "getSuperScript", "setSuperScript", "getType", "()Lcom/agog/mathdisplay/parse/MTMathAtomType;", "setType", "(Lcom/agog/mathdisplay/parse/MTMathAtomType;)V", "copyDeep", "copyDeepContent", "atom", "description", "dumpstr", "", CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "finalized", "newNode", "fuse", "scriptsAllowed", "", "toLatexString", "toStringSubs", "Factory", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public class MTMathAtom {

    /* JADX INFO: renamed from: Factory, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private MTFontStyle fontStyle;
    private List<MTMathAtom> fusedAtoms;
    private NSRange indexRange;
    private String nucleus;
    private MTMathList subScript;
    private MTMathList superScript;
    private MTMathAtomType type;

    public MTMathAtom(MTMathAtomType type, String nucleus) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(nucleus, "nucleus");
        this.type = type;
        this.nucleus = nucleus;
        this.fontStyle = MTFontStyle.KMTFontStyleDefault;
        this.fusedAtoms = new ArrayList();
        this.indexRange = new NSRange(0, 0);
    }

    public final String getNucleus() {
        return this.nucleus;
    }

    public final MTMathAtomType getType() {
        return this.type;
    }

    public final void setNucleus(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.nucleus = str;
    }

    public final void setType(MTMathAtomType mTMathAtomType) {
        Intrinsics.checkNotNullParameter(mTMathAtomType, "<set-?>");
        this.type = mTMathAtomType;
    }

    public final MTMathList getSuperScript() {
        return this.superScript;
    }

    public final void setSuperScript(MTMathList mTMathList) {
        if (!scriptsAllowed()) {
            throw new MathDisplayException(Intrinsics.stringPlus("Superscripts not allowed for atom ", this));
        }
        this.superScript = mTMathList;
    }

    public final MTMathList getSubScript() {
        return this.subScript;
    }

    public final void setSubScript(MTMathList mTMathList) {
        if (!scriptsAllowed()) {
            throw new MathDisplayException(Intrinsics.stringPlus("Subscripts not allowed for atom ", this));
        }
        this.subScript = mTMathList;
    }

    public final MTFontStyle getFontStyle() {
        return this.fontStyle;
    }

    public final void setFontStyle(MTFontStyle mTFontStyle) {
        Intrinsics.checkNotNullParameter(mTFontStyle, "<set-?>");
        this.fontStyle = mTFontStyle;
    }

    public final List<MTMathAtom> getFusedAtoms() {
        return this.fusedAtoms;
    }

    public final void setFusedAtoms(List<MTMathAtom> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.fusedAtoms = list;
    }

    public final NSRange getIndexRange() {
        return this.indexRange;
    }

    public final void setIndexRange(NSRange nSRange) {
        Intrinsics.checkNotNullParameter(nSRange, "<set-?>");
        this.indexRange = nSRange;
    }

    private final void dumpstr(String s) {
        char[] charArray = s.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        int i = 0;
        System.out.println((Object) ("str " + s + " codepoint " + Character.codePointAt(charArray, 0)));
        int length = charArray.length;
        while (i < length) {
            char c = charArray[i];
            i++;
            System.out.println((Object) Intrinsics.stringPlus("c ", Character.valueOf(c)));
        }
    }

    /* JADX INFO: renamed from: com.agog.mathdisplay.parse.MTMathAtom$Factory, reason: from kotlin metadata */
    /* JADX INFO: compiled from: MTMathAtom.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t¨\u0006\u0010"}, d2 = {"Lcom/agog/mathdisplay/parse/MTMathAtom$Factory;", "Lcom/agog/mathdisplay/parse/MTMathAtomFactory;", "()V", "atomForCharacter", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "ch", "", "atomWithType", "type", "Lcom/agog/mathdisplay/parse/MTMathAtomType;", "value", "", "isNotBinaryOperator", "", "prevNode", "typeToText", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion extends MTMathAtomFactory {

        /* JADX INFO: renamed from: com.agog.mathdisplay.parse.MTMathAtom$Factory$WhenMappings */
        /* JADX INFO: compiled from: MTMathAtom.kt */
        @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[MTMathAtomType.values().length];
                iArr[MTMathAtomType.KMTMathAtomNone.ordinal()] = 1;
                iArr[MTMathAtomType.KMTMathAtomOrdinary.ordinal()] = 2;
                iArr[MTMathAtomType.KMTMathAtomNumber.ordinal()] = 3;
                iArr[MTMathAtomType.KMTMathAtomVariable.ordinal()] = 4;
                iArr[MTMathAtomType.KMTMathAtomBinaryOperator.ordinal()] = 5;
                iArr[MTMathAtomType.KMTMathAtomUnaryOperator.ordinal()] = 6;
                iArr[MTMathAtomType.KMTMathAtomRelation.ordinal()] = 7;
                iArr[MTMathAtomType.KMTMathAtomOpen.ordinal()] = 8;
                iArr[MTMathAtomType.KMTMathAtomClose.ordinal()] = 9;
                iArr[MTMathAtomType.KMTMathAtomFraction.ordinal()] = 10;
                iArr[MTMathAtomType.KMTMathAtomRadical.ordinal()] = 11;
                iArr[MTMathAtomType.KMTMathAtomPunctuation.ordinal()] = 12;
                iArr[MTMathAtomType.KMTMathAtomPlaceholder.ordinal()] = 13;
                iArr[MTMathAtomType.KMTMathAtomLargeOperator.ordinal()] = 14;
                iArr[MTMathAtomType.KMTMathAtomInner.ordinal()] = 15;
                iArr[MTMathAtomType.KMTMathAtomUnderline.ordinal()] = 16;
                iArr[MTMathAtomType.KMTMathAtomOverline.ordinal()] = 17;
                iArr[MTMathAtomType.KMTMathAtomAccent.ordinal()] = 18;
                iArr[MTMathAtomType.KMTMathAtomBoundary.ordinal()] = 19;
                iArr[MTMathAtomType.KMTMathAtomSpace.ordinal()] = 20;
                iArr[MTMathAtomType.KMTMathAtomStyle.ordinal()] = 21;
                iArr[MTMathAtomType.KMTMathAtomColor.ordinal()] = 22;
                iArr[MTMathAtomType.KMTMathAtomTable.ordinal()] = 23;
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isNotBinaryOperator(MTMathAtom prevNode) {
            return prevNode == null || prevNode.getType() == MTMathAtomType.KMTMathAtomBinaryOperator || prevNode.getType() == MTMathAtomType.KMTMathAtomRelation || prevNode.getType() == MTMathAtomType.KMTMathAtomOpen || prevNode.getType() == MTMathAtomType.KMTMathAtomPunctuation || prevNode.getType() == MTMathAtomType.KMTMathAtomLargeOperator;
        }

        public final String typeToText(MTMathAtomType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            switch (WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
                case 1:
                    return FamilyInfo.ROLE_NONE;
                case 2:
                    return "Ordinary";
                case 3:
                    return "Number";
                case 4:
                    return VariableBinder.Variable.TAG_NAME;
                case 5:
                    return "Binary Operator";
                case 6:
                    return "Unary Operator";
                case 7:
                    return "Relation";
                case 8:
                    return "Open";
                case 9:
                    return "Close";
                case 10:
                    return "Fraction";
                case 11:
                    return "Radical";
                case 12:
                    return "Punctuation";
                case 13:
                    return "Placeholder";
                case 14:
                    return "Large Operator";
                case 15:
                    return "Inner";
                case 16:
                    return "Underline";
                case 17:
                    return "Overline";
                case 18:
                    return "Accent";
                case 19:
                    return "Boundary";
                case 20:
                    return "Space";
                case 21:
                    return StylesManager.Style.TAG;
                case 22:
                    return "Color";
                case 23:
                    return "Table";
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        public final MTMathAtom atomWithType(MTMathAtomType type, String value) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(value, "value");
            switch (WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
                case 10:
                    return new MTFraction(true);
                case 11:
                    return new MTRadical();
                case 12:
                case 19:
                case 21:
                default:
                    return new MTMathAtom(type, value);
                case 13:
                    return new MTMathAtom(MTMathAtomType.KMTMathAtomPlaceholder, "□");
                case 14:
                    return new MTLargeOperator(value, true);
                case 15:
                    return new MTInner();
                case 16:
                    return new MTUnderLine();
                case 17:
                    return new MTOverLine();
                case 18:
                    return new MTAccent(value);
                case 20:
                    return new MTMathSpace(0.0f);
                case 22:
                    return new MTMathColor();
            }
        }

        public final MTMathAtom atomForCharacter(char ch2) {
            String chStr = Character.toString(ch2);
            if (ch2 < '!' || ch2 > '~' || ch2 == '$' || ch2 == '%' || ch2 == '#' || ch2 == '&' || ch2 == '~' || ch2 == '\'' || ch2 == '^' || ch2 == '_' || ch2 == '{' || ch2 == '}' || ch2 == '\\') {
                return null;
            }
            if (ch2 == '(' || ch2 == '[') {
                MTMathAtomType mTMathAtomType = MTMathAtomType.KMTMathAtomOpen;
                Intrinsics.checkNotNullExpressionValue(chStr, "chStr");
                return atomWithType(mTMathAtomType, chStr);
            }
            if (ch2 == ')' || ch2 == ']' || ch2 == '!' || ch2 == '?') {
                MTMathAtomType mTMathAtomType2 = MTMathAtomType.KMTMathAtomClose;
                Intrinsics.checkNotNullExpressionValue(chStr, "chStr");
                return atomWithType(mTMathAtomType2, chStr);
            }
            if (ch2 == ',' || ch2 == ';') {
                MTMathAtomType mTMathAtomType3 = MTMathAtomType.KMTMathAtomPunctuation;
                Intrinsics.checkNotNullExpressionValue(chStr, "chStr");
                return atomWithType(mTMathAtomType3, chStr);
            }
            if (ch2 == '=' || ch2 == '>' || ch2 == '<') {
                MTMathAtomType mTMathAtomType4 = MTMathAtomType.KMTMathAtomRelation;
                Intrinsics.checkNotNullExpressionValue(chStr, "chStr");
                return atomWithType(mTMathAtomType4, chStr);
            }
            if (ch2 == ':') {
                return atomWithType(MTMathAtomType.KMTMathAtomRelation, "∶");
            }
            if (ch2 == '-') {
                return atomWithType(MTMathAtomType.KMTMathAtomBinaryOperator, "−");
            }
            if (ch2 == '+' || ch2 == '*') {
                MTMathAtomType mTMathAtomType5 = MTMathAtomType.KMTMathAtomBinaryOperator;
                Intrinsics.checkNotNullExpressionValue(chStr, "chStr");
                return atomWithType(mTMathAtomType5, chStr);
            }
            if (ch2 == '.' || ('0' <= ch2 && ch2 < ':')) {
                MTMathAtomType mTMathAtomType6 = MTMathAtomType.KMTMathAtomNumber;
                Intrinsics.checkNotNullExpressionValue(chStr, "chStr");
                return atomWithType(mTMathAtomType6, chStr);
            }
            if (('a' <= ch2 && ch2 < '{') || ('A' <= ch2 && ch2 < '[')) {
                MTMathAtomType mTMathAtomType7 = MTMathAtomType.KMTMathAtomVariable;
                Intrinsics.checkNotNullExpressionValue(chStr, "chStr");
                return atomWithType(mTMathAtomType7, chStr);
            }
            if (ch2 == '\"' || ch2 == '/' || ch2 == '@' || ch2 == '`' || ch2 == '|') {
                MTMathAtomType mTMathAtomType8 = MTMathAtomType.KMTMathAtomOrdinary;
                Intrinsics.checkNotNullExpressionValue(chStr, "chStr");
                return atomWithType(mTMathAtomType8, chStr);
            }
            throw new MathDisplayException("Unknown ascii character " + ch2 + ". Should have been accounted for.");
        }
    }

    public String toLatexString() {
        return toStringSubs(this.nucleus);
    }

    public final String toStringSubs(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        MTMathList mTMathList = this.superScript;
        if (mTMathList != null) {
            s = s + "^{" + MTMathListBuilder.INSTANCE.toLatexString(mTMathList) + CoreConstants.CURLY_RIGHT;
        }
        MTMathList mTMathList2 = this.subScript;
        return mTMathList2 != null ? s + "_{" + MTMathListBuilder.INSTANCE.toLatexString(mTMathList2) + CoreConstants.CURLY_RIGHT : s;
    }

    public final MTMathAtom copyDeepContent(MTMathAtom atom) {
        Intrinsics.checkNotNullParameter(atom, "atom");
        MTMathList mTMathList = this.subScript;
        if (mTMathList != null) {
            atom.setSubScript(mTMathList == null ? null : mTMathList.copyDeep());
        }
        MTMathList mTMathList2 = this.superScript;
        if (mTMathList2 != null) {
            atom.setSuperScript(mTMathList2 == null ? null : mTMathList2.copyDeep());
        }
        atom.fusedAtoms.isEmpty();
        atom.fontStyle = this.fontStyle;
        atom.indexRange = NSRange.copy$default(this.indexRange, 0, 0, 3, null);
        return atom;
    }

    public MTMathAtom copyDeep() {
        MTMathAtom mTMathAtom = new MTMathAtom(this.type, this.nucleus);
        copyDeepContent(mTMathAtom);
        return mTMathAtom;
    }

    public final MTMathAtom finalized(MTMathAtom newNode) {
        Intrinsics.checkNotNullParameter(newNode, "newNode");
        if (this.superScript != null) {
            MTMathList mTMathList = newNode.superScript;
            newNode.setSuperScript(mTMathList == null ? null : mTMathList.finalized());
        }
        if (this.subScript != null) {
            MTMathList mTMathList2 = newNode.subScript;
            newNode.setSubScript(mTMathList2 == null ? null : mTMathList2.finalized());
        }
        newNode.fontStyle = this.fontStyle;
        newNode.indexRange = NSRange.copy$default(this.indexRange, 0, 0, 3, null);
        return newNode;
    }

    public MTMathAtom finalized() {
        return finalized(copyDeep());
    }

    public final boolean scriptsAllowed() {
        return this.type.compareTo(MTMathAtomType.KMTMathAtomBoundary) < 0;
    }

    public final String description() {
        return INSTANCE.typeToText(this.type) + ' ' + this;
    }

    public final void fuse(MTMathAtom atom) {
        Intrinsics.checkNotNullParameter(atom, "atom");
        if (this.subScript != null) {
            throw new MathDisplayException(Intrinsics.stringPlus("Cannot fuse into an atom which has a subscript: ", this));
        }
        if (this.superScript != null) {
            throw new MathDisplayException(Intrinsics.stringPlus("Cannot fuse into an atom which has a superscript: ", this));
        }
        if (this.type != atom.type) {
            throw new MathDisplayException("Only atoms of the same type can be fused: " + this + ' ' + atom);
        }
        if (this.fusedAtoms.size() == 0) {
            this.fusedAtoms.add(copyDeep());
        }
        if (atom.fusedAtoms.size() != 0) {
            List<MTMathAtom> list = this.fusedAtoms;
            Object[] array = atom.fusedAtoms.toArray(new MTMathAtom[0]);
            if (array != null) {
                CollectionsKt.addAll(list, array);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            }
        } else {
            this.fusedAtoms.add(atom);
        }
        this.nucleus = Intrinsics.stringPlus(this.nucleus, atom.nucleus);
        NSRange nSRange = this.indexRange;
        nSRange.setLength(nSRange.getLength() + atom.indexRange.getLength());
        setSubScript(atom.subScript);
        setSuperScript(atom.superScript);
    }
}
