package com.agog.mathdisplay.parse;

import ch.qos.logback.core.CoreConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u001c\u001a\u00020\u0000H\u0016J\b\u0010\u001d\u001a\u00020\u0000H\u0016J\b\u0010\u001e\u001a\u00020\u0011H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0004R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\t\"\u0004\b\u0018\u0010\u000bR\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0013\"\u0004\b\u001b\u0010\u0015¨\u0006\u001f"}, d2 = {"Lcom/agog/mathdisplay/parse/MTFraction;", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "rule", "", "(Z)V", "()V", "denominator", "Lcom/agog/mathdisplay/parse/MTMathList;", "getDenominator", "()Lcom/agog/mathdisplay/parse/MTMathList;", "setDenominator", "(Lcom/agog/mathdisplay/parse/MTMathList;)V", "hasRule", "getHasRule", "()Z", "setHasRule", "leftDelimiter", "", "getLeftDelimiter", "()Ljava/lang/String;", "setLeftDelimiter", "(Ljava/lang/String;)V", "numerator", "getNumerator", "setNumerator", "rightDelimiter", "getRightDelimiter", "setRightDelimiter", "copyDeep", "finalized", "toLatexString", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTFraction extends MTMathAtom {
    private MTMathList denominator;
    private boolean hasRule;
    private String leftDelimiter;
    private MTMathList numerator;
    private String rightDelimiter;

    public MTFraction() {
        super(MTMathAtomType.KMTMathAtomFraction, "");
        this.hasRule = true;
    }

    public final MTMathList getNumerator() {
        return this.numerator;
    }

    public final void setNumerator(MTMathList mTMathList) {
        this.numerator = mTMathList;
    }

    public final MTMathList getDenominator() {
        return this.denominator;
    }

    public final void setDenominator(MTMathList mTMathList) {
        this.denominator = mTMathList;
    }

    public final boolean getHasRule() {
        return this.hasRule;
    }

    public final void setHasRule(boolean z) {
        this.hasRule = z;
    }

    public final String getLeftDelimiter() {
        return this.leftDelimiter;
    }

    public final void setLeftDelimiter(String str) {
        this.leftDelimiter = str;
    }

    public final String getRightDelimiter() {
        return this.rightDelimiter;
    }

    public final void setRightDelimiter(String str) {
        this.rightDelimiter = str;
    }

    public MTFraction(boolean z) {
        this();
        this.hasRule = z;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public String toLatexString() {
        String str;
        String latexString;
        if (this.hasRule) {
            str = "\\frac";
        } else {
            str = "\\atop";
        }
        String latexString2 = "";
        String strStringPlus = Intrinsics.stringPlus("", str);
        if (this.leftDelimiter != null || this.rightDelimiter != null) {
            strStringPlus = strStringPlus + '[' + this + ".leftDelimiter][" + this + ".rightDelimiter]";
        }
        MTMathList mTMathList = this.numerator;
        if (mTMathList == null) {
            latexString = "";
        } else {
            latexString = MTMathListBuilder.INSTANCE.toLatexString(mTMathList);
        }
        MTMathList mTMathList2 = this.denominator;
        if (mTMathList2 != null) {
            latexString2 = MTMathListBuilder.INSTANCE.toLatexString(mTMathList2);
        }
        return super.toStringSubs(strStringPlus + CoreConstants.CURLY_LEFT + latexString + "}{" + latexString2 + CoreConstants.CURLY_RIGHT);
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTFraction copyDeep() {
        MTFraction mTFraction = new MTFraction(this.hasRule);
        super.copyDeepContent(mTFraction);
        mTFraction.hasRule = this.hasRule;
        MTMathList mTMathList = this.numerator;
        mTFraction.numerator = mTMathList == null ? null : mTMathList.copyDeep();
        MTMathList mTMathList2 = this.denominator;
        mTFraction.denominator = mTMathList2 != null ? mTMathList2.copyDeep() : null;
        mTFraction.leftDelimiter = this.leftDelimiter;
        mTFraction.rightDelimiter = this.rightDelimiter;
        return mTFraction;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTFraction finalized() {
        MTFraction mTFractionCopyDeep = copyDeep();
        super.finalized(mTFractionCopyDeep);
        MTMathList mTMathList = mTFractionCopyDeep.numerator;
        mTFractionCopyDeep.numerator = mTMathList == null ? null : mTMathList.finalized();
        MTMathList mTMathList2 = mTFractionCopyDeep.denominator;
        mTFractionCopyDeep.denominator = mTMathList2 != null ? mTMathList2.finalized() : null;
        return mTFractionCopyDeep;
    }
}
