package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ArrayList<ConstraintWidget> arrayList, int i) {
        int i2;
        ChainHead[] chainHeadArr;
        int i3;
        if (i == 0) {
            i2 = constraintWidgetContainer.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer.mHorizontalChainsArray;
            i3 = 0;
        } else {
            i2 = constraintWidgetContainer.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer.mVerticalChainsArray;
            i3 = 2;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            ChainHead chainHead = chainHeadArr[i4];
            chainHead.define();
            if (arrayList == null || arrayList.contains(chainHead.mFirst)) {
                applyChainConstraints(constraintWidgetContainer, linearSystem, i, i3, chainHead);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:198:0x036b  */
    /* JADX WARN: Code duplicated, block: B:216:0x03c0  */
    /* JADX WARN: Code duplicated, block: B:217:0x03c2  */
    /* JADX WARN: Code duplicated, block: B:289:0x04f7  */
    /* JADX WARN: Code duplicated, block: B:292:0x0502  */
    /* JADX WARN: Code duplicated, block: B:293:0x0507  */
    /* JADX WARN: Code duplicated, block: B:296:0x050d  */
    /* JADX WARN: Code duplicated, block: B:297:0x0512  */
    /* JADX WARN: Code duplicated, block: B:299:0x0516  */
    /* JADX WARN: Code duplicated, block: B:29:0x004c A[PHI: r14 r15
  0x004c: PHI (r14v3 boolean) = (r14v1 boolean), (r14v33 boolean) binds: [B:28:0x004a, B:17:0x0037] A[DONT_GENERATE, DONT_INLINE]
  0x004c: PHI (r15v3 boolean) = (r15v1 boolean), (r15v8 boolean) binds: [B:28:0x004a, B:17:0x0037] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:301:0x051e  */
    /* JADX WARN: Code duplicated, block: B:304:0x0528  */
    /* JADX WARN: Code duplicated, block: B:30:0x004e A[PHI: r14 r15
  0x004e: PHI (r14v30 boolean) = (r14v1 boolean), (r14v33 boolean) binds: [B:28:0x004a, B:17:0x0037] A[DONT_GENERATE, DONT_INLINE]
  0x004e: PHI (r15v5 boolean) = (r15v1 boolean), (r15v8 boolean) binds: [B:28:0x004a, B:17:0x0037] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v20, types: [androidx.constraintlayout.core.LinearSystem] */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v5, types: [androidx.constraintlayout.core.LinearSystem] */
    /* JADX WARN: Type inference failed for: r5v103 */
    /* JADX WARN: Type inference failed for: r5v31 */
    /* JADX WARN: Type inference failed for: r5v32 */
    /* JADX WARN: Type inference failed for: r5v33, types: [androidx.constraintlayout.core.SolverVariable] */
    /* JADX WARN: Type inference failed for: r5v36 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2, types: [androidx.constraintlayout.core.widgets.ConstraintWidget] */
    /* JADX WARN: Type inference failed for: r7v27 */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v29 */
    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i, int i2, ChainHead chainHead) {
        boolean z;
        boolean z2;
        boolean z3;
        ?? r0;
        ConstraintAnchor constraintAnchor;
        SolverVariable solverVariable;
        SolverVariable solverVariable2;
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        int i3;
        ConstraintAnchor constraintAnchor2;
        SolverVariable solverVariable3;
        int i4;
        ConstraintAnchor constraintAnchor3;
        int i5;
        ConstraintAnchor constraintAnchor4;
        SolverVariable solverVariable4;
        ?? r5;
        float f;
        int size;
        int i6;
        int i7 = i;
        ConstraintWidget constraintWidget3 = chainHead.mFirst;
        ConstraintWidget constraintWidget4 = chainHead.mLast;
        ConstraintWidget constraintWidget5 = chainHead.mFirstVisibleWidget;
        ConstraintWidget constraintWidget6 = chainHead.mLastVisibleWidget;
        ConstraintWidget constraintWidget7 = chainHead.mHead;
        float f2 = chainHead.mTotalWeight;
        ConstraintWidget constraintWidget8 = chainHead.mFirstMatchConstraintWidget;
        ConstraintWidget constraintWidget9 = chainHead.mLastMatchConstraintWidget;
        boolean z4 = constraintWidgetContainer.mListDimensionBehaviors[i7] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (i7 == 0) {
            z = constraintWidget7.mHorizontalChainStyle == 0;
            z2 = constraintWidget7.mHorizontalChainStyle == 1;
            if (constraintWidget7.mHorizontalChainStyle == 2) {
                z3 = true;
            } else {
                z3 = false;
            }
        } else {
            z = constraintWidget7.mVerticalChainStyle == 0;
            z2 = constraintWidget7.mVerticalChainStyle == 1;
            if (constraintWidget7.mVerticalChainStyle == 2) {
                z3 = true;
            } else {
                z3 = false;
            }
        }
        ?? r7 = constraintWidget3;
        boolean z5 = false;
        while (true) {
            Object obj = null;
            if (z5) {
                break;
            }
            ConstraintAnchor constraintAnchor5 = r7.mListAnchors[i2];
            int i8 = z3 ? 1 : 4;
            int margin = constraintAnchor5.getMargin();
            boolean z6 = z4;
            boolean z7 = z3;
            boolean z8 = r7.mListDimensionBehaviors[i7] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && r7.mResolvedMatchConstraintDefault[i7] == 0;
            if (constraintAnchor5.mTarget != null && r7 != constraintWidget3) {
                margin += constraintAnchor5.mTarget.getMargin();
            }
            int i9 = margin;
            if (z7 && r7 != constraintWidget3 && r7 != constraintWidget5) {
                i8 = 8;
            }
            boolean z9 = z8;
            if (constraintAnchor5.mTarget != null) {
                if (r7 == constraintWidget5) {
                    linearSystem.addGreaterThan(constraintAnchor5.mSolverVariable, constraintAnchor5.mTarget.mSolverVariable, i9, 6);
                } else {
                    linearSystem.addGreaterThan(constraintAnchor5.mSolverVariable, constraintAnchor5.mTarget.mSolverVariable, i9, 8);
                }
                if (z9 && !z7) {
                    i8 = 5;
                }
                linearSystem.addEquality(constraintAnchor5.mSolverVariable, constraintAnchor5.mTarget.mSolverVariable, i9, (r7 == constraintWidget5 && z7 && r7.isInBarrier(i7)) ? 5 : i8);
            } else {
                z5 = z5;
                z = z;
            }
            if (z6) {
                if (r7.getVisibility() == 8 || r7.mListDimensionBehaviors[i7] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    i6 = 0;
                } else {
                    i6 = 0;
                    linearSystem.addGreaterThan(r7.mListAnchors[i2 + 1].mSolverVariable, r7.mListAnchors[i2].mSolverVariable, 0, 5);
                }
                linearSystem.addGreaterThan(r7.mListAnchors[i2].mSolverVariable, constraintWidgetContainer.mListAnchors[i2].mSolverVariable, i6, 8);
            }
            ConstraintAnchor constraintAnchor6 = r7.mListAnchors[i2 + 1].mTarget;
            if (constraintAnchor6 != null) {
                ConstraintWidget constraintWidget10 = constraintAnchor6.mOwner;
                if (constraintWidget10.mListAnchors[i2].mTarget != null && constraintWidget10.mListAnchors[i2].mTarget.mOwner == r7) {
                    obj = constraintWidget10;
                }
            }
            if (obj != null) {
                r7 = obj;
                z5 = z5;
            } else {
                z5 = true;
            }
            z4 = z6;
            z3 = z7;
            z = z;
            r7 = r7;
        }
        boolean z10 = z4;
        boolean z11 = z3;
        boolean z12 = z;
        if (constraintWidget6 != null) {
            int i10 = i2 + 1;
            if (constraintWidget4.mListAnchors[i10].mTarget != null) {
                ConstraintAnchor constraintAnchor7 = constraintWidget6.mListAnchors[i10];
                if (constraintWidget6.mListDimensionBehaviors[i7] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget6.mResolvedMatchConstraintDefault[i7] == 0 && !z11 && constraintAnchor7.mTarget.mOwner == constraintWidgetContainer) {
                    linearSystem.addEquality(constraintAnchor7.mSolverVariable, constraintAnchor7.mTarget.mSolverVariable, -constraintAnchor7.getMargin(), 5);
                } else if (z11 && constraintAnchor7.mTarget.mOwner == constraintWidgetContainer) {
                    linearSystem.addEquality(constraintAnchor7.mSolverVariable, constraintAnchor7.mTarget.mSolverVariable, -constraintAnchor7.getMargin(), 4);
                }
                linearSystem.addLowerThan(constraintAnchor7.mSolverVariable, constraintWidget4.mListAnchors[i10].mTarget.mSolverVariable, -constraintAnchor7.getMargin(), 6);
            }
        }
        if (z10) {
            int i11 = i2 + 1;
            linearSystem.addGreaterThan(constraintWidgetContainer.mListAnchors[i11].mSolverVariable, constraintWidget4.mListAnchors[i11].mSolverVariable, constraintWidget4.mListAnchors[i11].getMargin(), 8);
        }
        ArrayList<ConstraintWidget> arrayList = chainHead.mWeightedMatchConstraintsWidgets;
        if (arrayList != null && (size = arrayList.size()) > 1) {
            float f3 = (!chainHead.mHasUndefinedWeights || chainHead.mHasComplexMatchWeights) ? f2 : chainHead.mWidgetsMatchCount;
            float f4 = 0.0f;
            float f5 = 0.0f;
            ConstraintWidget constraintWidget11 = null;
            int i12 = 0;
            while (i12 < size) {
                ConstraintWidget constraintWidget12 = arrayList.get(i12);
                float f6 = constraintWidget12.mWeight[i7];
                if (f6 < f4) {
                    if (chainHead.mHasComplexMatchWeights) {
                        arrayList = arrayList;
                        f4 = f4;
                        linearSystem.addEquality(constraintWidget12.mListAnchors[i2 + 1].mSolverVariable, constraintWidget12.mListAnchors[i2].mSolverVariable, 0, 4);
                    } else {
                        f6 = 1.0f;
                    }
                    i12++;
                    f4 = f4;
                    arrayList = arrayList;
                }
                float f7 = f6;
                if (f7 == f4) {
                    linearSystem.addEquality(constraintWidget12.mListAnchors[i2 + 1].mSolverVariable, constraintWidget12.mListAnchors[i2].mSolverVariable, 0, 8);
                } else {
                    if (constraintWidget11 != null) {
                        SolverVariable solverVariable5 = constraintWidget11.mListAnchors[i2].mSolverVariable;
                        ConstraintAnchor[] constraintAnchorArr = constraintWidget11.mListAnchors;
                        int i13 = i2 + 1;
                        SolverVariable solverVariable6 = constraintAnchorArr[i13].mSolverVariable;
                        SolverVariable solverVariable7 = constraintWidget12.mListAnchors[i2].mSolverVariable;
                        SolverVariable solverVariable8 = constraintWidget12.mListAnchors[i13].mSolverVariable;
                        ArrayRow arrayRowCreateRow = linearSystem.createRow();
                        arrayRowCreateRow.createRowEqualMatchDimensions(f5, f3, f7, solverVariable5, solverVariable6, solverVariable7, solverVariable8);
                        linearSystem.addConstraint(arrayRowCreateRow);
                    }
                    constraintWidget11 = constraintWidget12;
                    f5 = f7;
                }
                i12++;
                f4 = f4;
                arrayList = arrayList;
            }
        }
        if (constraintWidget5 != null && (constraintWidget5 == constraintWidget6 || z11)) {
            ConstraintAnchor constraintAnchor8 = constraintWidget3.mListAnchors[i2];
            int i14 = i2 + 1;
            ConstraintAnchor constraintAnchor9 = constraintWidget4.mListAnchors[i14];
            SolverVariable solverVariable9 = constraintAnchor8.mTarget != null ? constraintAnchor8.mTarget.mSolverVariable : null;
            SolverVariable solverVariable10 = constraintAnchor9.mTarget != null ? constraintAnchor9.mTarget.mSolverVariable : null;
            ConstraintAnchor constraintAnchor10 = constraintWidget5.mListAnchors[i2];
            if (constraintWidget6 != null) {
                constraintAnchor9 = constraintWidget6.mListAnchors[i14];
            }
            if (solverVariable9 != null && solverVariable10 != null) {
                if (i7 == 0) {
                    f = constraintWidget7.mHorizontalBiasPercent;
                } else {
                    f = constraintWidget7.mVerticalBiasPercent;
                }
                linearSystem.addCentering(constraintAnchor10.mSolverVariable, solverVariable9, constraintAnchor10.getMargin(), f, solverVariable10, constraintAnchor9.mSolverVariable, constraintAnchor9.getMargin(), 7);
            }
        } else {
            if (!z12 || constraintWidget5 == null) {
                int i15 = 8;
                if (z2 && constraintWidget5 != null) {
                    boolean z13 = chainHead.mWidgetsMatchCount > 0 && chainHead.mWidgetsCount == chainHead.mWidgetsMatchCount;
                    ConstraintWidget constraintWidget13 = constraintWidget5;
                    ConstraintWidget constraintWidget14 = constraintWidget13;
                    while (constraintWidget14 != null) {
                        ConstraintWidget constraintWidget15 = constraintWidget14.mNextChainWidget[i];
                        while (constraintWidget15 != null && constraintWidget15.getVisibility() == i15) {
                            constraintWidget15 = constraintWidget15.mNextChainWidget[i];
                        }
                        if (constraintWidget14 == constraintWidget5 || constraintWidget14 == constraintWidget6 || constraintWidget15 == null) {
                            constraintWidget13 = constraintWidget13;
                        } else {
                            if (constraintWidget15 == constraintWidget6) {
                                constraintWidget15 = null;
                            }
                            ConstraintAnchor constraintAnchor11 = constraintWidget14.mListAnchors[i2];
                            SolverVariable solverVariable11 = constraintAnchor11.mSolverVariable;
                            if (constraintAnchor11.mTarget != null) {
                                SolverVariable solverVariable12 = constraintAnchor11.mTarget.mSolverVariable;
                            }
                            int i16 = i2 + 1;
                            SolverVariable solverVariable13 = constraintWidget13.mListAnchors[i16].mSolverVariable;
                            int margin2 = constraintAnchor11.getMargin();
                            int margin3 = constraintWidget14.mListAnchors[i16].getMargin();
                            if (constraintWidget15 != null) {
                                constraintAnchor = constraintWidget15.mListAnchors[i2];
                                solverVariable = constraintAnchor.mSolverVariable;
                                solverVariable2 = constraintAnchor.mTarget != null ? constraintAnchor.mTarget.mSolverVariable : null;
                            } else {
                                constraintAnchor = constraintWidget6.mListAnchors[i2];
                                solverVariable = constraintAnchor != null ? constraintAnchor.mSolverVariable : null;
                                solverVariable2 = constraintWidget14.mListAnchors[i16].mSolverVariable;
                            }
                            if (constraintAnchor != null) {
                                margin3 += constraintAnchor.getMargin();
                            }
                            int i17 = margin3;
                            int margin4 = margin2 + constraintWidget13.mListAnchors[i16].getMargin();
                            SolverVariable solverVariable14 = solverVariable;
                            int i18 = z13 ? 8 : 4;
                            if (solverVariable11 == null || solverVariable13 == null || solverVariable14 == null || solverVariable2 == null) {
                                constraintWidget = constraintWidget15;
                            } else {
                                constraintWidget = constraintWidget15;
                                linearSystem.addCentering(solverVariable11, solverVariable13, margin4, 0.5f, solverVariable14, solverVariable2, i17, i18);
                            }
                            constraintWidget15 = constraintWidget;
                        }
                        if (constraintWidget14.getVisibility() == 8) {
                            constraintWidget14 = constraintWidget13;
                        }
                        i15 = 8;
                        constraintWidget13 = constraintWidget14;
                        constraintWidget14 = constraintWidget15;
                    }
                    r0 = linearSystem;
                    ConstraintAnchor constraintAnchor12 = constraintWidget5.mListAnchors[i2];
                    ConstraintAnchor constraintAnchor13 = constraintWidget3.mListAnchors[i2].mTarget;
                    int i19 = i2 + 1;
                    ConstraintAnchor constraintAnchor14 = constraintWidget6.mListAnchors[i19];
                    ConstraintAnchor constraintAnchor15 = constraintWidget4.mListAnchors[i19].mTarget;
                    if (constraintAnchor13 != null) {
                        if (constraintWidget5 != constraintWidget6) {
                            r0.addEquality(constraintAnchor12.mSolverVariable, constraintAnchor13.mSolverVariable, constraintAnchor12.getMargin(), 5);
                        } else if (constraintAnchor15 != null) {
                            r0.addCentering(constraintAnchor12.mSolverVariable, constraintAnchor13.mSolverVariable, constraintAnchor12.getMargin(), 0.5f, constraintAnchor14.mSolverVariable, constraintAnchor15.mSolverVariable, constraintAnchor14.getMargin(), 5);
                        }
                    }
                    if (constraintAnchor15 != null && constraintWidget5 != constraintWidget6) {
                        r0.addEquality(constraintAnchor14.mSolverVariable, constraintAnchor15.mSolverVariable, -constraintAnchor14.getMargin(), 5);
                    }
                }
                if ((z12 && !z2) || constraintWidget5 == null || constraintWidget5 == constraintWidget6) {
                    return;
                }
                constraintAnchor3 = constraintWidget5.mListAnchors[i2];
                if (constraintWidget6 == null) {
                    constraintWidget6 = constraintWidget5;
                }
                i5 = i2 + 1;
                constraintAnchor4 = constraintWidget6.mListAnchors[i5];
                if (constraintAnchor3.mTarget != null) {
                    solverVariable4 = constraintAnchor3.mTarget.mSolverVariable;
                } else {
                    solverVariable4 = null;
                }
                if (constraintAnchor4.mTarget != null) {
                    r5 = constraintAnchor4.mTarget.mSolverVariable;
                } else {
                    r5 = 0;
                }
                if (constraintWidget4 != constraintWidget6) {
                    ConstraintAnchor constraintAnchor16 = constraintWidget4.mListAnchors[i5];
                    r5 = constraintAnchor16.mTarget != null ? constraintAnchor16.mTarget.mSolverVariable : null;
                }
                if (constraintWidget5 == constraintWidget6) {
                    constraintAnchor3 = constraintWidget5.mListAnchors[i2];
                    constraintAnchor4 = constraintWidget5.mListAnchors[i5];
                }
                if (solverVariable4 != null || r5 == 0) {
                }
                r0.addCentering(constraintAnchor3.mSolverVariable, solverVariable4, constraintAnchor3.getMargin(), 0.5f, r5, constraintAnchor4.mSolverVariable, constraintWidget6.mListAnchors[i5].getMargin(), 5);
                return;
            }
            boolean z14 = chainHead.mWidgetsMatchCount > 0 && chainHead.mWidgetsCount == chainHead.mWidgetsMatchCount;
            ConstraintWidget constraintWidget16 = constraintWidget5;
            for (ConstraintWidget constraintWidget17 = constraintWidget16; constraintWidget17 != null; constraintWidget17 = constraintWidget2) {
                constraintWidget2 = constraintWidget17.mNextChainWidget[i7];
                while (true) {
                    if (constraintWidget2 == null) {
                        i3 = 8;
                        break;
                    }
                    i3 = 8;
                    if (constraintWidget2.getVisibility() != 8) {
                        break;
                    } else {
                        constraintWidget2 = constraintWidget2.mNextChainWidget[i7];
                    }
                }
                if (constraintWidget2 != null || constraintWidget17 == constraintWidget6) {
                    ConstraintAnchor constraintAnchor17 = constraintWidget17.mListAnchors[i2];
                    SolverVariable solverVariable15 = constraintAnchor17.mSolverVariable;
                    SolverVariable solverVariable16 = constraintAnchor17.mTarget != null ? constraintAnchor17.mTarget.mSolverVariable : null;
                    if (constraintWidget16 != constraintWidget17) {
                        solverVariable16 = constraintWidget16.mListAnchors[i2 + 1].mSolverVariable;
                    } else if (constraintWidget17 == constraintWidget5) {
                        solverVariable16 = constraintWidget3.mListAnchors[i2].mTarget != null ? constraintWidget3.mListAnchors[i2].mTarget.mSolverVariable : null;
                    }
                    int margin5 = constraintAnchor17.getMargin();
                    int i20 = i2 + 1;
                    int margin6 = constraintWidget17.mListAnchors[i20].getMargin();
                    if (constraintWidget2 != null) {
                        constraintAnchor2 = constraintWidget2.mListAnchors[i2];
                        solverVariable3 = constraintAnchor2.mSolverVariable;
                    } else {
                        constraintAnchor2 = constraintWidget4.mListAnchors[i20].mTarget;
                        if (constraintAnchor2 != null) {
                            solverVariable3 = constraintAnchor2.mSolverVariable;
                        } else {
                            solverVariable3 = null;
                        }
                        SolverVariable solverVariable17 = constraintWidget17.mListAnchors[i20].mSolverVariable;
                        if (constraintAnchor2 != null) {
                            margin6 += constraintAnchor2.getMargin();
                        }
                        int margin7 = margin5 + constraintWidget16.mListAnchors[i20].getMargin();
                        if (solverVariable15 != null || solverVariable16 == null || solverVariable3 == null || solverVariable17 == null) {
                            i4 = 8;
                        } else {
                            if (constraintWidget17 == constraintWidget5) {
                                margin7 = constraintWidget5.mListAnchors[i2].getMargin();
                            }
                            if (constraintWidget17 == constraintWidget6) {
                                margin6 = constraintWidget6.mListAnchors[i20].getMargin();
                            }
                            constraintWidget2 = constraintWidget2;
                            constraintWidget16 = constraintWidget16;
                            i4 = 8;
                            linearSystem.addCentering(solverVariable15, solverVariable16, margin7, 0.5f, solverVariable3, solverVariable17, margin6, z14 ? 8 : 5);
                        }
                        if (constraintWidget17.getVisibility() != i4) {
                            constraintWidget16 = constraintWidget17;
                        } else {
                            constraintWidget16 = constraintWidget16;
                        }
                        i7 = i;
                    }
                    SolverVariable solverVariable18 = constraintWidget17.mListAnchors[i20].mSolverVariable;
                    if (constraintAnchor2 != null) {
                        margin6 += constraintAnchor2.getMargin();
                    }
                    int margin8 = margin5 + constraintWidget16.mListAnchors[i20].getMargin();
                    if (solverVariable15 != null) {
                    }
                    i4 = 8;
                } else {
                    i4 = i3;
                }
                if (constraintWidget17.getVisibility() != i4) {
                    constraintWidget16 = constraintWidget17;
                } else {
                    constraintWidget16 = constraintWidget16;
                }
                i7 = i;
            }
        }
        r0 = linearSystem;
        if (z12) {
        }
        constraintAnchor3 = constraintWidget5.mListAnchors[i2];
        if (constraintWidget6 == null) {
            constraintWidget6 = constraintWidget5;
        }
        i5 = i2 + 1;
        constraintAnchor4 = constraintWidget6.mListAnchors[i5];
        if (constraintAnchor3.mTarget != null) {
            solverVariable4 = constraintAnchor3.mTarget.mSolverVariable;
        } else {
            solverVariable4 = null;
        }
        if (constraintAnchor4.mTarget != null) {
            r5 = constraintAnchor4.mTarget.mSolverVariable;
        } else {
            r5 = 0;
        }
        if (constraintWidget4 != constraintWidget6) {
            ConstraintAnchor constraintAnchor18 = constraintWidget4.mListAnchors[i5];
            r5 = constraintAnchor18.mTarget != null ? constraintAnchor18.mTarget.mSolverVariable : null;
        }
        if (constraintWidget5 == constraintWidget6) {
            constraintAnchor3 = constraintWidget5.mListAnchors[i2];
            constraintAnchor4 = constraintWidget5.mListAnchors[i5];
        }
        if (solverVariable4 != null) {
        }
    }
}
