package com.agog.mathdisplay.parse;

import kotlin.Metadata;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0000H\u0016J\b\u0010\u0010\u001a\u00020\u0000H\u0016J\b\u0010\u0011\u001a\u00020\u0004H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0012"}, d2 = {"Lcom/agog/mathdisplay/parse/MTMathColor;", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "()V", "colorString", "", "getColorString", "()Ljava/lang/String;", "setColorString", "(Ljava/lang/String;)V", "innerList", "Lcom/agog/mathdisplay/parse/MTMathList;", "getInnerList", "()Lcom/agog/mathdisplay/parse/MTMathList;", "setInnerList", "(Lcom/agog/mathdisplay/parse/MTMathList;)V", "copyDeep", "finalized", "toLatexString", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTMathColor extends MTMathAtom {
    private String colorString;
    private MTMathList innerList;

    public MTMathColor() {
        super(MTMathAtomType.KMTMathAtomColor, "");
    }

    public final MTMathList getInnerList() {
        return this.innerList;
    }

    public final void setInnerList(MTMathList mTMathList) {
        this.innerList = mTMathList;
    }

    public final String getColorString() {
        return this.colorString;
    }

    public final void setColorString(String str) {
        this.colorString = str;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public String toLatexString() {
        return super.toStringSubs("\\color{" + this + ".colorString}{" + this + ".innerList}");
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTMathColor copyDeep() {
        MTMathColor mTMathColor = new MTMathColor();
        super.copyDeepContent(mTMathColor);
        MTMathList mTMathList = this.innerList;
        mTMathColor.innerList = mTMathList == null ? null : mTMathList.copyDeep();
        mTMathColor.colorString = this.colorString;
        return mTMathColor;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTMathColor finalized() {
        MTMathColor mTMathColorCopyDeep = copyDeep();
        super.finalized(mTMathColorCopyDeep);
        MTMathList mTMathList = mTMathColorCopyDeep.innerList;
        mTMathColorCopyDeep.innerList = mTMathList == null ? null : mTMathList.finalized();
        return mTMathColorCopyDeep;
    }
}
