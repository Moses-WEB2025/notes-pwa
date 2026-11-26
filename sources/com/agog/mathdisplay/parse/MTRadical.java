package com.agog.mathdisplay.parse;

import ch.qos.logback.core.CoreConstants;
import kotlin.Metadata;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\u0000H\u0016J\b\u0010\r\u001a\u00020\u0000H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\u0010"}, d2 = {"Lcom/agog/mathdisplay/parse/MTRadical;", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "()V", "degree", "Lcom/agog/mathdisplay/parse/MTMathList;", "getDegree", "()Lcom/agog/mathdisplay/parse/MTMathList;", "setDegree", "(Lcom/agog/mathdisplay/parse/MTMathList;)V", "radicand", "getRadicand", "setRadicand", "copyDeep", "finalized", "toLatexString", "", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTRadical extends MTMathAtom {
    private MTMathList degree;
    private MTMathList radicand;

    public MTRadical() {
        super(MTMathAtomType.KMTMathAtomRadical, "");
    }

    public final MTMathList getDegree() {
        return this.degree;
    }

    public final void setDegree(MTMathList mTMathList) {
        this.degree = mTMathList;
    }

    public final MTMathList getRadicand() {
        return this.radicand;
    }

    public final void setRadicand(MTMathList mTMathList) {
        this.radicand = mTMathList;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public String toLatexString() {
        String str;
        String latexString;
        MTMathList mTMathList = this.degree;
        if (mTMathList == null) {
            str = "\\sqrt";
        } else {
            str = "\\sqrt[" + MTMathListBuilder.INSTANCE.toLatexString(mTMathList) + ']';
        }
        MTMathList mTMathList2 = this.radicand;
        if (mTMathList2 == null) {
            latexString = "";
        } else {
            latexString = MTMathListBuilder.INSTANCE.toLatexString(mTMathList2);
        }
        return super.toStringSubs(str + CoreConstants.CURLY_LEFT + latexString + CoreConstants.CURLY_RIGHT);
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTRadical copyDeep() {
        MTRadical mTRadical = new MTRadical();
        super.copyDeepContent(mTRadical);
        MTMathList mTMathList = this.radicand;
        mTRadical.radicand = mTMathList == null ? null : mTMathList.copyDeep();
        MTMathList mTMathList2 = this.degree;
        mTRadical.degree = mTMathList2 != null ? mTMathList2.copyDeep() : null;
        return mTRadical;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTRadical finalized() {
        MTRadical mTRadicalCopyDeep = copyDeep();
        super.finalized(mTRadicalCopyDeep);
        MTMathList mTMathList = mTRadicalCopyDeep.radicand;
        mTRadicalCopyDeep.radicand = mTMathList == null ? null : mTMathList.finalized();
        MTMathList mTMathList2 = mTRadicalCopyDeep.degree;
        mTRadicalCopyDeep.degree = mTMathList2 != null ? mTMathList2.finalized() : null;
        return mTRadicalCopyDeep;
    }
}
