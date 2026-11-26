package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.Optimizer;
import androidx.constraintlayout.core.widgets.VirtualLayout;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BasicMeasure {
    public static final int AT_MOST = Integer.MIN_VALUE;
    private static final boolean DEBUG = false;
    private static final boolean DO_NOT_USE = false;
    public static final int EXACTLY = 1073741824;
    public static final int FIXED = -3;
    public static final int MATCH_PARENT = -1;
    private static final int MODE_SHIFT = 30;
    public static final int UNSPECIFIED = 0;
    public static final int WRAP_CONTENT = -2;
    private ConstraintWidgetContainer mConstraintWidgetContainer;
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList<>();
    private Measure mMeasure = new Measure();

    public static class Measure {
        public static int SELF_DIMENSIONS = 0;
        public static int TRY_GIVEN_DIMENSIONS = 1;
        public static int USE_GIVEN_DIMENSIONS = 2;
        public ConstraintWidget.DimensionBehaviour horizontalBehavior;
        public int horizontalDimension;
        public int measureStrategy;
        public int measuredBaseline;
        public boolean measuredHasBaseline;
        public int measuredHeight;
        public boolean measuredNeedsSolverPass;
        public int measuredWidth;
        public ConstraintWidget.DimensionBehaviour verticalBehavior;
        public int verticalDimension;
    }

    public interface Measurer {
        void didMeasures();

        void measure(ConstraintWidget constraintWidget, Measure measure);
    }

    public void updateHierarchy(ConstraintWidgetContainer constraintWidgetContainer) {
        this.mVariableDimensionsWidgets.clear();
        int size = constraintWidgetContainer.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = constraintWidgetContainer.mChildren.get(i);
            if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                this.mVariableDimensionsWidgets.add(constraintWidget);
            }
        }
        constraintWidgetContainer.invalidateGraph();
    }

    public BasicMeasure(ConstraintWidgetContainer constraintWidgetContainer) {
        this.mConstraintWidgetContainer = constraintWidgetContainer;
    }

    /* JADX WARN: Code duplicated, block: B:63:0x00aa A[PHI: r9
  0x00aa: PHI (r9v3 boolean) = (r9v2 boolean), (r9v2 boolean), (r9v2 boolean), (r9v5 boolean), (r9v5 boolean) binds: [B:33:0x0068, B:35:0x006e, B:37:0x0072, B:61:0x00a7, B:59:0x00a0] A[DONT_GENERATE, DONT_INLINE]] */
    private void measureChildren(ConstraintWidgetContainer constraintWidgetContainer) {
        boolean z;
        int size = constraintWidgetContainer.mChildren.size();
        boolean zOptimizeFor = constraintWidgetContainer.optimizeFor(64);
        Measurer measurer = constraintWidgetContainer.getMeasurer();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = constraintWidgetContainer.mChildren.get(i);
            if (!(constraintWidget instanceof Guideline) && !(constraintWidget instanceof Barrier) && !constraintWidget.isInVirtualLayout() && (!zOptimizeFor || constraintWidget.mHorizontalRun == null || constraintWidget.mVerticalRun == null || !constraintWidget.mHorizontalRun.mDimension.resolved || !constraintWidget.mVerticalRun.mDimension.resolved)) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.getDimensionBehaviour(0);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.getDimensionBehaviour(1);
                boolean z2 = dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth != 1 && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight != 1;
                if (!z2 && constraintWidgetContainer.optimizeFor(1) && !(constraintWidget instanceof VirtualLayout)) {
                    if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0 && dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !constraintWidget.isInHorizontalChain()) {
                        z2 = true;
                    }
                    if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0 && dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !constraintWidget.isInHorizontalChain()) {
                        z2 = true;
                    }
                    z = (!(dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || constraintWidget.mDimensionRatio <= 0.0f) ? z2 : true;
                }
                if (!z) {
                    measure(measurer, constraintWidget, Measure.SELF_DIMENSIONS);
                    if (constraintWidgetContainer.mMetrics != null) {
                        constraintWidgetContainer.mMetrics.measuredWidgets++;
                    }
                }
            }
        }
        measurer.didMeasures();
    }

    private void solveLinearSystem(ConstraintWidgetContainer constraintWidgetContainer, String str, int i, int i2, int i3) {
        long jNanoTime = constraintWidgetContainer.mMetrics != null ? System.nanoTime() : 0L;
        int minWidth = constraintWidgetContainer.getMinWidth();
        int minHeight = constraintWidgetContainer.getMinHeight();
        constraintWidgetContainer.setMinWidth(0);
        constraintWidgetContainer.setMinHeight(0);
        constraintWidgetContainer.setWidth(i2);
        constraintWidgetContainer.setHeight(i3);
        constraintWidgetContainer.setMinWidth(minWidth);
        constraintWidgetContainer.setMinHeight(minHeight);
        this.mConstraintWidgetContainer.setPass(i);
        this.mConstraintWidgetContainer.layout();
        if (constraintWidgetContainer.mMetrics != null) {
            long jNanoTime2 = System.nanoTime();
            constraintWidgetContainer.mMetrics.mSolverPasses++;
            constraintWidgetContainer.mMetrics.measuresLayoutDuration += jNanoTime2 - jNanoTime;
        }
    }

    public long solverMeasure(ConstraintWidgetContainer constraintWidgetContainer, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        long j;
        boolean zDirectMeasureWithOrientation;
        int i10;
        int i11;
        int i12;
        long j2;
        boolean z;
        BasicMeasure basicMeasure = this;
        ConstraintWidgetContainer constraintWidgetContainer2 = constraintWidgetContainer;
        Measurer measurer = constraintWidgetContainer2.getMeasurer();
        int size = constraintWidgetContainer2.mChildren.size();
        int width = constraintWidgetContainer2.getWidth();
        int height = constraintWidgetContainer2.getHeight();
        boolean zEnabled = Optimizer.enabled(i, 128);
        boolean z2 = zEnabled || Optimizer.enabled(i, 64);
        if (z2) {
            for (int i13 = 0; i13 < size; i13++) {
                ConstraintWidget constraintWidget = constraintWidgetContainer2.mChildren.get(i13);
                boolean z3 = (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && constraintWidget.getDimensionRatio() > 0.0f;
                if ((constraintWidget.isInHorizontalChain() && z3) || ((constraintWidget.isInVerticalChain() && z3) || (constraintWidget instanceof VirtualLayout) || constraintWidget.isInHorizontalChain() || constraintWidget.isInVerticalChain())) {
                    z2 = false;
                    break;
                }
            }
        }
        if (z2 && LinearSystem.sMetrics != null) {
            LinearSystem.sMetrics.measures++;
        }
        boolean z4 = z2 & ((i4 == 1073741824 && i6 == 1073741824) || zEnabled);
        int i14 = 2;
        if (z4) {
            j = 1;
            int iMin = Math.min(constraintWidgetContainer2.getMaxWidth(), i5);
            int iMin2 = Math.min(constraintWidgetContainer2.getMaxHeight(), i7);
            if (i4 == 1073741824 && constraintWidgetContainer2.getWidth() != iMin) {
                constraintWidgetContainer2.setWidth(iMin);
                constraintWidgetContainer2.invalidateGraph();
            }
            if (i6 == 1073741824 && constraintWidgetContainer2.getHeight() != iMin2) {
                constraintWidgetContainer2.setHeight(iMin2);
                constraintWidgetContainer2.invalidateGraph();
            }
            if (i4 == 1073741824 && i6 == 1073741824) {
                zDirectMeasureWithOrientation = constraintWidgetContainer2.directMeasure(zEnabled);
                i10 = 2;
            } else {
                boolean zDirectMeasureSetup = constraintWidgetContainer2.directMeasureSetup(zEnabled);
                if (i4 == 1073741824) {
                    zDirectMeasureSetup &= constraintWidgetContainer2.directMeasureWithOrientation(zEnabled, 0);
                    i10 = 1;
                } else {
                    i10 = 0;
                }
                if (i6 == 1073741824) {
                    zDirectMeasureWithOrientation = constraintWidgetContainer2.directMeasureWithOrientation(zEnabled, 1) & zDirectMeasureSetup;
                    i10++;
                } else {
                    zDirectMeasureWithOrientation = zDirectMeasureSetup;
                }
            }
            if (zDirectMeasureWithOrientation) {
                constraintWidgetContainer2.updateFromRuns(i4 == 1073741824, i6 == 1073741824);
            }
        } else {
            j = 1;
            zDirectMeasureWithOrientation = false;
            i10 = 0;
        }
        long jNanoTime = 0;
        if (!zDirectMeasureWithOrientation || i10 != 2) {
            int optimizationLevel = constraintWidgetContainer2.getOptimizationLevel();
            if (size > 0) {
                measureChildren(constraintWidgetContainer);
            }
            jNanoTime = constraintWidgetContainer2.mMetrics != null ? System.nanoTime() : 0L;
            updateHierarchy(constraintWidgetContainer);
            int size2 = basicMeasure.mVariableDimensionsWidgets.size();
            if (size > 0) {
                basicMeasure.solveLinearSystem(constraintWidgetContainer2, "First pass", 0, width, height);
                i11 = width;
                i12 = height;
            } else {
                i11 = width;
                i12 = height;
            }
            if (size2 > 0) {
                boolean z5 = constraintWidgetContainer2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                boolean z6 = constraintWidgetContainer2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                int iMax = Math.max(constraintWidgetContainer2.getWidth(), basicMeasure.mConstraintWidgetContainer.getMinWidth());
                int iMax2 = Math.max(constraintWidgetContainer2.getHeight(), basicMeasure.mConstraintWidgetContainer.getMinHeight());
                int i15 = 0;
                boolean zNeedSolverPass = false;
                while (i15 < size2) {
                    ConstraintWidget constraintWidget2 = basicMeasure.mVariableDimensionsWidgets.get(i15);
                    boolean z7 = z4;
                    if (constraintWidget2 instanceof VirtualLayout) {
                        int width2 = constraintWidget2.getWidth();
                        int height2 = constraintWidget2.getHeight();
                        boolean zMeasure = zNeedSolverPass | basicMeasure.measure(measurer, constraintWidget2, Measure.TRY_GIVEN_DIMENSIONS);
                        if (constraintWidgetContainer2.mMetrics != null) {
                            constraintWidgetContainer2.mMetrics.measuredMatchWidgets += j;
                        }
                        int width3 = constraintWidget2.getWidth();
                        int height3 = constraintWidget2.getHeight();
                        if (width3 != width2) {
                            constraintWidget2.setWidth(width3);
                            if (z5 && constraintWidget2.getRight() > iMax) {
                                iMax = Math.max(iMax, constraintWidget2.getRight() + constraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                            }
                            z = true;
                        } else {
                            z = zMeasure;
                        }
                        if (height3 != height2) {
                            constraintWidget2.setHeight(height3);
                            if (z6 && constraintWidget2.getBottom() > iMax2) {
                                iMax2 = Math.max(iMax2, constraintWidget2.getBottom() + constraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                            }
                            z = true;
                        }
                        zNeedSolverPass = z | ((VirtualLayout) constraintWidget2).needSolverPass();
                    } else {
                        i11 = i11;
                        i12 = i12;
                    }
                    i15++;
                    i11 = i11;
                    i12 = i12;
                    z4 = z7;
                    jNanoTime = jNanoTime;
                    i14 = 2;
                }
                boolean z8 = z4;
                j2 = jNanoTime;
                int i16 = i11;
                int i17 = i12;
                int i18 = i14;
                int i19 = 0;
                while (i19 < i18) {
                    int i20 = 0;
                    while (i20 < size2) {
                        ConstraintWidget constraintWidget3 = basicMeasure.mVariableDimensionsWidgets.get(i20);
                        if (((constraintWidget3 instanceof Helper) && !(constraintWidget3 instanceof VirtualLayout)) || (constraintWidget3 instanceof Guideline) || constraintWidget3.getVisibility() == 8 || ((z8 && constraintWidget3.mHorizontalRun.mDimension.resolved && constraintWidget3.mVerticalRun.mDimension.resolved) || (constraintWidget3 instanceof VirtualLayout))) {
                            i19 = i19;
                        } else {
                            int width4 = constraintWidget3.getWidth();
                            int height4 = constraintWidget3.getHeight();
                            int baselineDistance = constraintWidget3.getBaselineDistance();
                            int i21 = Measure.TRY_GIVEN_DIMENSIONS;
                            if (i19 == 1) {
                                i21 = Measure.USE_GIVEN_DIMENSIONS;
                            }
                            boolean zMeasure2 = zNeedSolverPass | basicMeasure.measure(measurer, constraintWidget3, i21);
                            if (constraintWidgetContainer2.mMetrics != null) {
                                constraintWidgetContainer2.mMetrics.measuredMatchWidgets += j;
                            }
                            int width5 = constraintWidget3.getWidth();
                            int height5 = constraintWidget3.getHeight();
                            if (width5 != width4) {
                                constraintWidget3.setWidth(width5);
                                if (z5 && constraintWidget3.getRight() > iMax) {
                                    iMax = Math.max(iMax, constraintWidget3.getRight() + constraintWidget3.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                                }
                                zMeasure2 = true;
                            }
                            if (height5 != height4) {
                                constraintWidget3.setHeight(height5);
                                if (z6 && constraintWidget3.getBottom() > iMax2) {
                                    iMax2 = Math.max(iMax2, constraintWidget3.getBottom() + constraintWidget3.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                                }
                                zMeasure2 = true;
                            }
                            zNeedSolverPass = (!constraintWidget3.hasBaseline() || baselineDistance == constraintWidget3.getBaselineDistance()) ? zMeasure2 : true;
                        }
                        i20++;
                        basicMeasure = this;
                        constraintWidgetContainer2 = constraintWidgetContainer;
                        i19 = i19;
                    }
                    int i22 = i19;
                    if (!zNeedSolverPass) {
                        constraintWidgetContainer2 = constraintWidgetContainer;
                        break;
                    }
                    i19 = i22 + 1;
                    solveLinearSystem(constraintWidgetContainer, "intermediate pass", i19, i16, i17);
                    basicMeasure = this;
                    constraintWidgetContainer2 = constraintWidgetContainer;
                    i18 = 2;
                    zNeedSolverPass = false;
                }
            } else {
                j2 = jNanoTime;
            }
            constraintWidgetContainer2.setOptimizationLevel(optimizationLevel);
            jNanoTime = j2;
        }
        return constraintWidgetContainer2.mMetrics != null ? System.nanoTime() - jNanoTime : jNanoTime;
    }

    private boolean measure(Measurer measurer, ConstraintWidget constraintWidget, int i) {
        this.mMeasure.horizontalBehavior = constraintWidget.getHorizontalDimensionBehaviour();
        this.mMeasure.verticalBehavior = constraintWidget.getVerticalDimensionBehaviour();
        this.mMeasure.horizontalDimension = constraintWidget.getWidth();
        this.mMeasure.verticalDimension = constraintWidget.getHeight();
        this.mMeasure.measuredNeedsSolverPass = false;
        this.mMeasure.measureStrategy = i;
        boolean z = this.mMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z2 = this.mMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z3 = z && constraintWidget.mDimensionRatio > 0.0f;
        boolean z4 = z2 && constraintWidget.mDimensionRatio > 0.0f;
        if (z3 && constraintWidget.mResolvedMatchConstraintDefault[0] == 4) {
            this.mMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        if (z4 && constraintWidget.mResolvedMatchConstraintDefault[1] == 4) {
            this.mMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        measurer.measure(constraintWidget, this.mMeasure);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        constraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
        this.mMeasure.measureStrategy = Measure.SELF_DIMENSIONS;
        return this.mMeasure.measuredNeedsSolverPass;
    }
}
