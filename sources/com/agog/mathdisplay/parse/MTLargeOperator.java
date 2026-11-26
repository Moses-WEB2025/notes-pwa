package com.agog.mathdisplay.parse;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u0000H\u0016R\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u000e"}, d2 = {"Lcom/agog/mathdisplay/parse/MTLargeOperator;", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "nucleus", "", "limits", "", "(Ljava/lang/String;Z)V", "(Ljava/lang/String;)V", "hasLimits", "getHasLimits", "()Z", "setHasLimits", "(Z)V", "copyDeep", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTLargeOperator extends MTMathAtom {
    private boolean hasLimits;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTLargeOperator(String nucleus) {
        super(MTMathAtomType.KMTMathAtomLargeOperator, nucleus);
        Intrinsics.checkNotNullParameter(nucleus, "nucleus");
    }

    public final boolean getHasLimits() {
        return this.hasLimits;
    }

    public final void setHasLimits(boolean z) {
        this.hasLimits = z;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MTLargeOperator(String nucleus, boolean z) {
        this(nucleus);
        Intrinsics.checkNotNullParameter(nucleus, "nucleus");
        this.hasLimits = z;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTLargeOperator copyDeep() {
        MTLargeOperator mTLargeOperator = new MTLargeOperator(getNucleus(), this.hasLimits);
        super.copyDeepContent(mTLargeOperator);
        return mTLargeOperator;
    }
}
