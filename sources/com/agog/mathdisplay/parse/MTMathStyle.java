package com.agog.mathdisplay.parse;

import com.xiaomi.micloudsdk.micloudrichmedia.RequestParameters;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u0000H\u0016R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0004¨\u0006\u000b"}, d2 = {"Lcom/agog/mathdisplay/parse/MTMathStyle;", "Lcom/agog/mathdisplay/parse/MTMathAtom;", RequestParameters.ST, "Lcom/agog/mathdisplay/parse/MTLineStyle;", "(Lcom/agog/mathdisplay/parse/MTLineStyle;)V", "()V", "style", "getStyle", "()Lcom/agog/mathdisplay/parse/MTLineStyle;", "setStyle", "copyDeep", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTMathStyle extends MTMathAtom {
    private MTLineStyle style;

    public MTMathStyle() {
        super(MTMathAtomType.KMTMathAtomStyle, "");
        this.style = MTLineStyle.KMTLineStyleDisplay;
    }

    public final MTLineStyle getStyle() {
        return this.style;
    }

    public final void setStyle(MTLineStyle mTLineStyle) {
        Intrinsics.checkNotNullParameter(mTLineStyle, "<set-?>");
        this.style = mTLineStyle;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MTMathStyle(MTLineStyle st) {
        this();
        Intrinsics.checkNotNullParameter(st, "st");
        this.style = st;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTMathStyle copyDeep() {
        MTMathStyle mTMathStyle = new MTMathStyle(this.style);
        super.copyDeepContent(mTMathStyle);
        return mTMathStyle;
    }
}
