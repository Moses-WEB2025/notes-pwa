package com.agog.mathdisplay.parse;

import kotlin.Metadata;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u0000H\u0016R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0004¨\u0006\u000b"}, d2 = {"Lcom/agog/mathdisplay/parse/MTMathSpace;", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "sp", "", "(F)V", "()V", "space", "getSpace", "()F", "setSpace", "copyDeep", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTMathSpace extends MTMathAtom {
    private float space;

    public MTMathSpace() {
        super(MTMathAtomType.KMTMathAtomSpace, "");
    }

    public final float getSpace() {
        return this.space;
    }

    public final void setSpace(float f) {
        this.space = f;
    }

    public MTMathSpace(float f) {
        this();
        this.space = f;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTMathSpace copyDeep() {
        MTMathSpace mTMathSpace = new MTMathSpace(this.space);
        super.copyDeepContent(mTMathSpace);
        return mTMathSpace;
    }
}
