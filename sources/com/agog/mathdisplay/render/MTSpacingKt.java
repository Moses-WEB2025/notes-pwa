package com.agog.mathdisplay.render;

import com.agog.mathdisplay.parse.MTMathAtomType;
import com.agog.mathdisplay.parse.MathDisplayException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTSpacing.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\"\u001f\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00010\u0001¢\u0006\n\n\u0002\u0010\u0005\u001a\u0004\b\u0003\u0010\u0004¨\u0006\f"}, d2 = {"interElementSpaceArray", "", "Lcom/agog/mathdisplay/render/MTInterElementSpaceType;", "getInterElementSpaceArray", "()[[Lcom/agog/mathdisplay/render/MTInterElementSpaceType;", "[[Lcom/agog/mathdisplay/render/MTInterElementSpaceType;", "getInterElementSpaceArrayIndexForType", "", "type", "Lcom/agog/mathdisplay/parse/MTMathAtomType;", "row", "", "mathdisplaylib_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
public final class MTSpacingKt {
    private static final MTInterElementSpaceType[][] interElementSpaceArray = {new MTInterElementSpaceType[]{MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceThin, MTInterElementSpaceType.KMTSpaceNSMedium, MTInterElementSpaceType.KMTSpaceNSThick, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNSThin}, new MTInterElementSpaceType[]{MTInterElementSpaceType.KMTSpaceThin, MTInterElementSpaceType.KMTSpaceThin, MTInterElementSpaceType.KMTSpaceInvalid, MTInterElementSpaceType.KMTSpaceNSThick, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNSThin}, new MTInterElementSpaceType[]{MTInterElementSpaceType.KMTSpaceNSMedium, MTInterElementSpaceType.KMTSpaceNSMedium, MTInterElementSpaceType.KMTSpaceInvalid, MTInterElementSpaceType.KMTSpaceInvalid, MTInterElementSpaceType.KMTSpaceNSMedium, MTInterElementSpaceType.KMTSpaceInvalid, MTInterElementSpaceType.KMTSpaceInvalid, MTInterElementSpaceType.KMTSpaceNSMedium}, new MTInterElementSpaceType[]{MTInterElementSpaceType.KMTSpaceNSThick, MTInterElementSpaceType.KMTSpaceNSThick, MTInterElementSpaceType.KMTSpaceInvalid, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNSThick, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNSThick}, new MTInterElementSpaceType[]{MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceInvalid, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone}, new MTInterElementSpaceType[]{MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceThin, MTInterElementSpaceType.KMTSpaceNSMedium, MTInterElementSpaceType.KMTSpaceNSThick, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNSThin}, new MTInterElementSpaceType[]{MTInterElementSpaceType.KMTSpaceNSThin, MTInterElementSpaceType.KMTSpaceNSThin, MTInterElementSpaceType.KMTSpaceInvalid, MTInterElementSpaceType.KMTSpaceNSThin, MTInterElementSpaceType.KMTSpaceNSThin, MTInterElementSpaceType.KMTSpaceNSThin, MTInterElementSpaceType.KMTSpaceNSThin, MTInterElementSpaceType.KMTSpaceNSThin}, new MTInterElementSpaceType[]{MTInterElementSpaceType.KMTSpaceNSThin, MTInterElementSpaceType.KMTSpaceThin, MTInterElementSpaceType.KMTSpaceNSMedium, MTInterElementSpaceType.KMTSpaceNSThick, MTInterElementSpaceType.KMTSpaceNSThin, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNSThin, MTInterElementSpaceType.KMTSpaceNSThin}, new MTInterElementSpaceType[]{MTInterElementSpaceType.KMTSpaceNSMedium, MTInterElementSpaceType.KMTSpaceNSThin, MTInterElementSpaceType.KMTSpaceNSMedium, MTInterElementSpaceType.KMTSpaceNSThick, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNone, MTInterElementSpaceType.KMTSpaceNSThin}};

    /* JADX INFO: compiled from: MTSpacing.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MTMathAtomType.values().length];
            iArr[MTMathAtomType.KMTMathAtomColor.ordinal()] = 1;
            iArr[MTMathAtomType.KMTMathAtomOrdinary.ordinal()] = 2;
            iArr[MTMathAtomType.KMTMathAtomPlaceholder.ordinal()] = 3;
            iArr[MTMathAtomType.KMTMathAtomLargeOperator.ordinal()] = 4;
            iArr[MTMathAtomType.KMTMathAtomBinaryOperator.ordinal()] = 5;
            iArr[MTMathAtomType.KMTMathAtomRelation.ordinal()] = 6;
            iArr[MTMathAtomType.KMTMathAtomOpen.ordinal()] = 7;
            iArr[MTMathAtomType.KMTMathAtomClose.ordinal()] = 8;
            iArr[MTMathAtomType.KMTMathAtomPunctuation.ordinal()] = 9;
            iArr[MTMathAtomType.KMTMathAtomFraction.ordinal()] = 10;
            iArr[MTMathAtomType.KMTMathAtomInner.ordinal()] = 11;
            iArr[MTMathAtomType.KMTMathAtomRadical.ordinal()] = 12;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final MTInterElementSpaceType[][] getInterElementSpaceArray() {
        return interElementSpaceArray;
    }

    public static final int getInterElementSpaceArrayIndexForType(MTMathAtomType type, boolean z) throws MathDisplayException {
        Intrinsics.checkNotNullParameter(type, "type");
        switch (WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
            case 1:
            case 2:
            case 3:
                return 0;
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 4;
            case 8:
                return 5;
            case 9:
                return 6;
            case 10:
            case 11:
                return 7;
            case 12:
                if (z) {
                    return 8;
                }
                throw new MathDisplayException("Interelement space undefined for radical on the right. Treat radical as ordinary.");
            default:
                throw new MathDisplayException(Intrinsics.stringPlus("Interelement space undefined for type ", type));
        }
    }
}
