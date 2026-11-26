package com.agog.mathdisplay.parse;

import ch.qos.logback.core.CoreConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0000H\u0016J\b\u0010\u0013\u001a\u00020\u0000H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR(\u0010\n\u001a\u0004\u0018\u00010\u00012\b\u0010\t\u001a\u0004\u0018\u00010\u0001@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR(\u0010\u000f\u001a\u0004\u0018\u00010\u00012\b\u0010\t\u001a\u0004\u0018\u00010\u0001@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e¨\u0006\u0016"}, d2 = {"Lcom/agog/mathdisplay/parse/MTInner;", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "()V", "innerList", "Lcom/agog/mathdisplay/parse/MTMathList;", "getInnerList", "()Lcom/agog/mathdisplay/parse/MTMathList;", "setInnerList", "(Lcom/agog/mathdisplay/parse/MTMathList;)V", "value", "leftBoundary", "getLeftBoundary", "()Lcom/agog/mathdisplay/parse/MTMathAtom;", "setLeftBoundary", "(Lcom/agog/mathdisplay/parse/MTMathAtom;)V", "rightBoundary", "getRightBoundary", "setRightBoundary", "copyDeep", "finalized", "toLatexString", "", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTInner extends MTMathAtom {
    private MTMathList innerList;
    private MTMathAtom leftBoundary;
    private MTMathAtom rightBoundary;

    public MTInner() {
        super(MTMathAtomType.KMTMathAtomInner, "");
    }

    public final MTMathList getInnerList() {
        return this.innerList;
    }

    public final void setInnerList(MTMathList mTMathList) {
        this.innerList = mTMathList;
    }

    public final MTMathAtom getLeftBoundary() {
        return this.leftBoundary;
    }

    public final void setLeftBoundary(MTMathAtom mTMathAtom) {
        if (mTMathAtom != null && mTMathAtom.getType() != MTMathAtomType.KMTMathAtomBoundary) {
            throw new MathDisplayException(Intrinsics.stringPlus("Left boundary must be of type KMTMathAtomBoundary ", mTMathAtom));
        }
        this.leftBoundary = mTMathAtom;
    }

    public final MTMathAtom getRightBoundary() {
        return this.rightBoundary;
    }

    public final void setRightBoundary(MTMathAtom mTMathAtom) {
        if (mTMathAtom != null && mTMathAtom.getType() != MTMathAtomType.KMTMathAtomBoundary) {
            throw new MathDisplayException(Intrinsics.stringPlus("Right boundary must be of type KMTMathAtomBoundary ", mTMathAtom));
        }
        this.rightBoundary = mTMathAtom;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public String toLatexString() {
        String str;
        String latexString;
        MTMathAtom mTMathAtom = this.leftBoundary;
        if (mTMathAtom == null) {
            str = "\\inner";
        } else {
            str = "\\inner[" + mTMathAtom.getNucleus() + ']';
        }
        MTMathList mTMathList = this.innerList;
        if (mTMathList == null) {
            latexString = "";
        } else {
            latexString = MTMathListBuilder.INSTANCE.toLatexString(mTMathList);
        }
        String str2 = str + CoreConstants.CURLY_LEFT + latexString + CoreConstants.CURLY_RIGHT;
        MTMathAtom mTMathAtom2 = this.rightBoundary;
        if (mTMathAtom2 != null) {
            str2 = str2 + '[' + mTMathAtom2.getNucleus() + ']';
        }
        return super.toStringSubs(str2);
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTInner copyDeep() {
        MTInner mTInner = new MTInner();
        super.copyDeepContent(mTInner);
        MTMathList mTMathList = this.innerList;
        mTInner.innerList = mTMathList == null ? null : mTMathList.copyDeep();
        MTMathAtom mTMathAtom = this.leftBoundary;
        mTInner.setLeftBoundary(mTMathAtom == null ? null : mTMathAtom.copyDeep());
        MTMathAtom mTMathAtom2 = this.rightBoundary;
        mTInner.setRightBoundary(mTMathAtom2 != null ? mTMathAtom2.copyDeep() : null);
        return mTInner;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTInner finalized() {
        MTInner mTInnerCopyDeep = copyDeep();
        super.finalized(mTInnerCopyDeep);
        MTMathList mTMathList = mTInnerCopyDeep.innerList;
        mTInnerCopyDeep.innerList = mTMathList == null ? null : mTMathList.finalized();
        MTMathAtom mTMathAtom = mTInnerCopyDeep.leftBoundary;
        mTInnerCopyDeep.setLeftBoundary(mTMathAtom == null ? null : mTMathAtom.finalized());
        MTMathAtom mTMathAtom2 = mTInnerCopyDeep.rightBoundary;
        mTInnerCopyDeep.setRightBoundary(mTMathAtom2 != null ? mTMathAtom2.finalized() : null);
        return mTInnerCopyDeep;
    }
}
