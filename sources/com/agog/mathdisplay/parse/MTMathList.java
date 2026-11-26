package com.agog.mathdisplay.parse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathList.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\b\u0016\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0015\b\u0016\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004J\u000e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0000J\u0006\u0010\u0011\u001a\u00020\u0000J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0000J\u0016\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\u0004H\u0002J\b\u0010\u001a\u001a\u00020\u0013H\u0016R \u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\u0007¨\u0006\u001b"}, d2 = {"Lcom/agog/mathdisplay/parse/MTMathList;", "", "alist", "", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "([Lcom/agog/mathdisplay/parse/MTMathAtom;)V", "", "(Ljava/util/List;)V", "atoms", "getAtoms", "()Ljava/util/List;", "setAtoms", "addAtom", "", "atom", "append", "list", "copyDeep", "description", "", "finalized", "insertAtom", "index", "", "isAtomAllowed", "", "toString", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTMathList {
    private List<MTMathAtom> atoms;

    /* JADX INFO: compiled from: MTMathList.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MTMathAtomType.values().length];
            iArr[MTMathAtomType.KMTMathAtomBinaryOperator.ordinal()] = 1;
            iArr[MTMathAtomType.KMTMathAtomRelation.ordinal()] = 2;
            iArr[MTMathAtomType.KMTMathAtomPunctuation.ordinal()] = 3;
            iArr[MTMathAtomType.KMTMathAtomClose.ordinal()] = 4;
            iArr[MTMathAtomType.KMTMathAtomNumber.ordinal()] = 5;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final List<MTMathAtom> getAtoms() {
        return this.atoms;
    }

    public final void setAtoms(List<MTMathAtom> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.atoms = list;
    }

    public MTMathList(MTMathAtom... alist) {
        Intrinsics.checkNotNullParameter(alist, "alist");
        this.atoms = new ArrayList();
        int length = alist.length;
        int i = 0;
        while (i < length) {
            MTMathAtom mTMathAtom = alist[i];
            i++;
            this.atoms.add(mTMathAtom);
        }
    }

    public MTMathList(List<MTMathAtom> alist) {
        Intrinsics.checkNotNullParameter(alist, "alist");
        ArrayList arrayList = new ArrayList();
        this.atoms = arrayList;
        arrayList.addAll(alist);
    }

    private final boolean isAtomAllowed(MTMathAtom atom) {
        return atom.getType() != MTMathAtomType.KMTMathAtomBoundary;
    }

    public final void addAtom(MTMathAtom atom) throws MathDisplayException {
        Intrinsics.checkNotNullParameter(atom, "atom");
        if (!isAtomAllowed(atom)) {
            throw new MathDisplayException("Cannot add atom of type " + MTMathAtom.INSTANCE.typeToText(atom.getType()) + " in a mathlist ");
        }
        this.atoms.add(atom);
    }

    public final void insertAtom(MTMathAtom atom, int index) throws MathDisplayException {
        Intrinsics.checkNotNullParameter(atom, "atom");
        if (!isAtomAllowed(atom)) {
            throw new MathDisplayException("Cannot add atom of type " + MTMathAtom.INSTANCE.typeToText(atom.getType()) + " in a mathlist ");
        }
        this.atoms.add(index, atom);
    }

    public final void append(MTMathList list) {
        Intrinsics.checkNotNullParameter(list, "list");
        this.atoms.addAll(list.atoms);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<MTMathAtom> it = this.atoms.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "str.toString()");
        return string;
    }

    public final String description() {
        return toString();
    }

    public final MTMathList finalized() throws MathDisplayException {
        MTMathList mTMathList = new MTMathList(new MTMathAtom[0]);
        NSRange nSRange = new NSRange(0, 0);
        MTMathAtom mTMathAtom = null;
        for (MTMathAtom mTMathAtom2 : this.atoms) {
            MTMathAtom mTMathAtomFinalized = mTMathAtom2.finalized();
            if (nSRange.equal(mTMathAtom2.getIndexRange())) {
                mTMathAtomFinalized.setIndexRange(new NSRange(mTMathAtom == null ? 0 : mTMathAtom.getIndexRange().getLocation() + mTMathAtom.getIndexRange().getLength(), 1));
            }
            int i = WhenMappings.$EnumSwitchMapping$0[mTMathAtomFinalized.getType().ordinal()];
            if (i != 1) {
                if (i == 2 || i == 3 || i == 4) {
                    if (mTMathAtom != null && mTMathAtom.getType() == MTMathAtomType.KMTMathAtomBinaryOperator) {
                        mTMathAtom.setType(MTMathAtomType.KMTMathAtomUnaryOperator);
                    }
                } else if (i == 5 && mTMathAtom != null && mTMathAtom.getType() == MTMathAtomType.KMTMathAtomNumber && mTMathAtom.getSubScript() == null && mTMathAtom.getSuperScript() == null) {
                    mTMathAtom.fuse(mTMathAtomFinalized);
                }
            } else if (MTMathAtom.INSTANCE.isNotBinaryOperator(mTMathAtom)) {
                mTMathAtomFinalized.setType(MTMathAtomType.KMTMathAtomUnaryOperator);
            }
            mTMathList.addAtom(mTMathAtomFinalized);
            mTMathAtom = mTMathAtomFinalized;
        }
        if (mTMathAtom != null && mTMathAtom.getType() == MTMathAtomType.KMTMathAtomBinaryOperator) {
            mTMathAtom.setType(MTMathAtomType.KMTMathAtomUnaryOperator);
        }
        return mTMathList;
    }

    public final MTMathList copyDeep() throws MathDisplayException {
        MTMathList mTMathList = new MTMathList(new MTMathAtom[0]);
        Iterator<MTMathAtom> it = this.atoms.iterator();
        while (it.hasNext()) {
            mTMathList.addAtom(it.next().copyDeep());
        }
        return mTMathList;
    }
}
