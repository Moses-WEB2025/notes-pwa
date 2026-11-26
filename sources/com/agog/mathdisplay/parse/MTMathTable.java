package com.agog.mathdisplay.parse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathTable.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u001c\u001a\u00020\u0000H\u0016J\b\u0010\u001d\u001a\u00020\u0000H\u0016J\u000e\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020 J\u0006\u0010!\u001a\u00020 J\u0006\u0010\"\u001a\u00020 J\u0016\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020 J\u001e\u0010&\u001a\u00020$2\u0006\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020 2\u0006\u0010\u001f\u001a\u00020 R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0004R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0016\"\u0004\b\u001b\u0010\u0018¨\u0006)"}, d2 = {"Lcom/agog/mathdisplay/parse/MTMathTable;", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "env", "", "(Ljava/lang/String;)V", "()V", "alignments", "", "Lcom/agog/mathdisplay/parse/MTColumnAlignment;", "cells", "Lcom/agog/mathdisplay/parse/MTMathList;", "getCells", "()Ljava/util/List;", "setCells", "(Ljava/util/List;)V", "environment", "getEnvironment", "()Ljava/lang/String;", "setEnvironment", "interColumnSpacing", "", "getInterColumnSpacing", "()F", "setInterColumnSpacing", "(F)V", "interRowAdditionalSpacing", "getInterRowAdditionalSpacing", "setInterRowAdditionalSpacing", "copyDeep", "finalized", "getAlignmentForColumn", "column", "", "numColumns", "numRows", "setAlignment", "", "alignment", "setCell", "list", "row", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTMathTable extends MTMathAtom {
    private List<MTColumnAlignment> alignments;
    private List<List<MTMathList>> cells;
    private String environment;
    private float interColumnSpacing;
    private float interRowAdditionalSpacing;

    public MTMathTable() {
        super(MTMathAtomType.KMTMathAtomTable, "");
        this.alignments = new ArrayList();
        this.cells = new ArrayList();
    }

    public final List<List<MTMathList>> getCells() {
        return this.cells;
    }

    public final void setCells(List<List<MTMathList>> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.cells = list;
    }

    public final String getEnvironment() {
        return this.environment;
    }

    public final void setEnvironment(String str) {
        this.environment = str;
    }

    public final float getInterColumnSpacing() {
        return this.interColumnSpacing;
    }

    public final void setInterColumnSpacing(float f) {
        this.interColumnSpacing = f;
    }

    public final float getInterRowAdditionalSpacing() {
        return this.interRowAdditionalSpacing;
    }

    public final void setInterRowAdditionalSpacing(float f) {
        this.interRowAdditionalSpacing = f;
    }

    public MTMathTable(String str) {
        this();
        this.environment = str;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTMathTable copyDeep() {
        MTMathTable mTMathTable = new MTMathTable(this.environment);
        super.copyDeepContent(mTMathTable);
        ArrayList arrayList = new ArrayList();
        mTMathTable.alignments = arrayList;
        arrayList.addAll(CollectionsKt.toSet(this.alignments));
        mTMathTable.cells = new ArrayList();
        for (List<MTMathList> list : this.cells) {
            ArrayList arrayList2 = new ArrayList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(list.get(i).copyDeep());
            }
            mTMathTable.cells.add(arrayList2);
        }
        mTMathTable.interColumnSpacing = this.interColumnSpacing;
        mTMathTable.interRowAdditionalSpacing = this.interRowAdditionalSpacing;
        return mTMathTable;
    }

    @Override // com.agog.mathdisplay.parse.MTMathAtom
    public MTMathTable finalized() {
        MTMathTable mTMathTableCopyDeep = copyDeep();
        super.finalized(mTMathTableCopyDeep);
        for (List<MTMathList> list : mTMathTableCopyDeep.cells) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                list.set(i, list.get(i).finalized());
            }
        }
        return mTMathTableCopyDeep;
    }

    public final void setCell(MTMathList list, int row, int column) {
        Intrinsics.checkNotNullParameter(list, "list");
        if (this.cells.size() <= row) {
            for (int size = this.cells.size(); size <= row; size++) {
                this.cells.add(size, new ArrayList());
            }
        }
        List<MTMathList> list2 = this.cells.get(row);
        if (list2.size() <= column) {
            for (int size2 = list2.size(); size2 <= column; size2++) {
                list2.add(size2, new MTMathList(new MTMathAtom[0]));
            }
        }
        list2.set(column, list);
    }

    public final void setAlignment(MTColumnAlignment alignment, int column) {
        Intrinsics.checkNotNullParameter(alignment, "alignment");
        if (this.alignments.size() <= column) {
            for (int size = this.alignments.size(); size <= column; size++) {
                this.alignments.add(size, MTColumnAlignment.KMTColumnAlignmentCenter);
            }
        }
        this.alignments.set(column, alignment);
    }

    public final MTColumnAlignment getAlignmentForColumn(int column) {
        if (this.alignments.size() <= column) {
            return MTColumnAlignment.KMTColumnAlignmentCenter;
        }
        return this.alignments.get(column);
    }

    public final int numColumns() {
        Iterator<List<MTMathList>> it = this.cells.iterator();
        int iMax = 0;
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().size());
        }
        return iMax;
    }

    public final int numRows() {
        return this.cells.size();
    }
}
