package com.agog.mathdisplay.parse;

import ch.qos.logback.core.CoreConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\u0000H\u0016J\b\u0010\f\u001a\u00020\u0000H\u0016J\b\u0010\r\u001a\u00020\u0003H\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000e"}, d2 = {"Lcom/agog/mathdisplay/parse/MTAccent;", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "nucleus", "", "(Ljava/lang/String;)V", "innerList", "Lcom/agog/mathdisplay/parse/MTMathList;", "getInnerList", "()Lcom/agog/mathdisplay/parse/MTMathList;", "setInnerList", "(Lcom/agog/mathdisplay/parse/MTMathList;)V", "copyDeep", "finalized", "toLatexString", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTAccent extends MTMathAtom {
    private MTMathList innerList;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTAccent(String nucleus) {
        super(MTMathAtomType.KMTMathAtomAccent, nucleus);
        Intrinsics.checkNotNullParameter(nucleus, "nucleus");
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
    public MTAccent copyDeep() {
        MTAccent mTAccent = new MTAccent(getNucleus());
        super.copyDeepContent(mTAccent);
        MTMathList mTMathList = this.innerList;
        mTAccent.innerList = mTMathList == null ? null : mTMathList.copyDeep();
        return mTAccent;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTAccent finalized() {
        MTAccent mTAccentCopyDeep = copyDeep();
        super.finalized(mTAccentCopyDeep);
        MTMathList mTMathList = mTAccentCopyDeep.innerList;
        mTAccentCopyDeep.innerList = mTMathList == null ? null : mTMathList.finalized();
        return mTAccentCopyDeep;
    }
}
