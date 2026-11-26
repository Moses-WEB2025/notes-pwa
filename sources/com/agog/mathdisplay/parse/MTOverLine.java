package com.agog.mathdisplay.parse;

import ch.qos.logback.core.CoreConstants;
import kotlin.Metadata;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\u0000H\u0016J\b\u0010\n\u001a\u00020\u0000H\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lcom/agog/mathdisplay/parse/MTOverLine;", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "()V", "innerList", "Lcom/agog/mathdisplay/parse/MTMathList;", "getInnerList", "()Lcom/agog/mathdisplay/parse/MTMathList;", "setInnerList", "(Lcom/agog/mathdisplay/parse/MTMathList;)V", "copyDeep", "finalized", "toLatexString", "", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTOverLine extends MTMathAtom {
    private MTMathList innerList;

    public MTOverLine() {
        super(MTMathAtomType.KMTMathAtomOverline, "");
    }

    public final MTMathList getInnerList() {
        return this.innerList;
    }

    public final void setInnerList(MTMathList mTMathList) {
        this.innerList = mTMathList;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public String toLatexString() {
        String latexString;
        MTMathList mTMathList = this.innerList;
        if (mTMathList == null) {
            latexString = "";
        } else {
            latexString = MTMathListBuilder.INSTANCE.toLatexString(mTMathList);
        }
        return "{" + latexString + CoreConstants.CURLY_RIGHT;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTOverLine copyDeep() {
        MTOverLine mTOverLine = new MTOverLine();
        super.copyDeepContent(mTOverLine);
        MTMathList mTMathList = this.innerList;
        mTOverLine.innerList = mTMathList == null ? null : mTMathList.copyDeep();
        return mTOverLine;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTOverLine finalized() {
        MTOverLine mTOverLineCopyDeep = copyDeep();
        super.finalized(mTOverLineCopyDeep);
        MTMathList mTMathList = mTOverLineCopyDeep.innerList;
        mTOverLineCopyDeep.innerList = mTMathList == null ? null : mTMathList.finalized();
        return mTOverLineCopyDeep;
    }
}
