package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.HelperWidget;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DependencyGraph {
    private static final boolean DEBUG = false;
    private static final boolean USE_GROUPS = true;
    private ConstraintWidgetContainer mContainer;
    private ConstraintWidgetContainer mWidgetcontainer;
    private boolean mNeedBuildGraph = true;
    private boolean mNeedRedoMeasures = true;
    private ArrayList<WidgetRun> mRuns = new ArrayList<>();
    private ArrayList<RunGroup> mRunGroups = new ArrayList<>();
    private BasicMeasure.Measurer mMeasurer = null;
    private BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
    ArrayList<RunGroup> mGroups = new ArrayList<>();

    public DependencyGraph(ConstraintWidgetContainer constraintWidgetContainer) {
        this.mWidgetcontainer = constraintWidgetContainer;
        this.mContainer = constraintWidgetContainer;
    }

    public void setMeasurer(BasicMeasure.Measurer measurer) {
        this.mMeasurer = measurer;
    }

    private int computeWrap(ConstraintWidgetContainer constraintWidgetContainer, int i) {
        int size = this.mGroups.size();
        long jMax = 0;
        for (int i2 = 0; i2 < size; i2++) {
            jMax = Math.max(jMax, this.mGroups.get(i2).computeWrapSize(constraintWidgetContainer, i));
        }
        return (int) jMax;
    }

    public void defineTerminalWidgets(ConstraintWidget.DimensionBehaviour dimensionBehaviour, ConstraintWidget.DimensionBehaviour dimensionBehaviour2) {
        if (this.mNeedBuildGraph) {
            buildGraph();
            boolean z = false;
            for (ConstraintWidget constraintWidget : this.mWidgetcontainer.mChildren) {
                constraintWidget.isTerminalWidget[0] = true;
                constraintWidget.isTerminalWidget[1] = true;
                if (constraintWidget instanceof Barrier) {
                    z = true;
                }
            }
            if (z) {
                return;
            }
            Iterator<RunGroup> it = this.mGroups.iterator();
            while (it.hasNext()) {
                it.next().defineTerminalWidgets(dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
        }
    }

    public boolean directMeasure(boolean z) {
        boolean z2;
        boolean z3 = false;
        if (this.mNeedBuildGraph || this.mNeedRedoMeasures) {
            for (ConstraintWidget constraintWidget : this.mWidgetcontainer.mChildren) {
                constraintWidget.ensureWidgetRuns();
                constraintWidget.measured = false;
                constraintWidget.mHorizontalRun.reset();
                constraintWidget.mVerticalRun.reset();
            }
            this.mWidgetcontainer.ensureWidgetRuns();
            this.mWidgetcontainer.measured = false;
            this.mWidgetcontainer.mHorizontalRun.reset();
            this.mWidgetcontainer.mVerticalRun.reset();
            this.mNeedRedoMeasures = false;
        }
        if (basicMeasureWidgets(this.mContainer)) {
            return false;
        }
        this.mWidgetcontainer.setX(0);
        this.mWidgetcontainer.setY(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.mWidgetcontainer.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.mWidgetcontainer.getDimensionBehaviour(1);
        if (this.mNeedBuildGraph) {
            buildGraph();
        }
        int x = this.mWidgetcontainer.getX();
        int y = this.mWidgetcontainer.getY();
        this.mWidgetcontainer.mHorizontalRun.start.resolve(x);
        this.mWidgetcontainer.mVerticalRun.start.resolve(y);
        measureWidgets();
        if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            if (z) {
                Iterator<WidgetRun> it = this.mRuns.iterator();
                while (it.hasNext()) {
                    if (!it.next().supportsWrapComputation()) {
                        z = false;
                        break;
                    }
                }
            }
            if (z && dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.mWidgetcontainer.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer = this.mWidgetcontainer;
                constraintWidgetContainer.setWidth(computeWrap(constraintWidgetContainer, 0));
                this.mWidgetcontainer.mHorizontalRun.mDimension.resolve(this.mWidgetcontainer.getWidth());
            }
            if (z && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.mWidgetcontainer.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer2 = this.mWidgetcontainer;
                constraintWidgetContainer2.setHeight(computeWrap(constraintWidgetContainer2, 1));
                this.mWidgetcontainer.mVerticalRun.mDimension.resolve(this.mWidgetcontainer.getHeight());
            }
        }
        if (this.mWidgetcontainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || this.mWidgetcontainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int width = this.mWidgetcontainer.getWidth() + x;
            this.mWidgetcontainer.mHorizontalRun.end.resolve(width);
            this.mWidgetcontainer.mHorizontalRun.mDimension.resolve(width - x);
            measureWidgets();
            if (this.mWidgetcontainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || this.mWidgetcontainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int height = this.mWidgetcontainer.getHeight() + y;
                this.mWidgetcontainer.mVerticalRun.end.resolve(height);
                this.mWidgetcontainer.mVerticalRun.mDimension.resolve(height - y);
            }
            measureWidgets();
            z2 = true;
        } else {
            z2 = false;
        }
        for (WidgetRun widgetRun : this.mRuns) {
            if (widgetRun.mWidget != this.mWidgetcontainer || widgetRun.mResolved) {
                widgetRun.applyToWidget();
            }
        }
        for (WidgetRun widgetRun2 : this.mRuns) {
            if (z2 || widgetRun2.mWidget != this.mWidgetcontainer) {
                if (!widgetRun2.start.resolved || ((!widgetRun2.end.resolved && !(widgetRun2 instanceof GuidelineReference)) || (!widgetRun2.mDimension.resolved && !(widgetRun2 instanceof ChainRun) && !(widgetRun2 instanceof GuidelineReference)))) {
                    this.mWidgetcontainer.setHorizontalDimensionBehaviour(dimensionBehaviour);
                    this.mWidgetcontainer.setVerticalDimensionBehaviour(dimensionBehaviour2);
                    return z3;
                }
            }
        }
        z3 = true;
        this.mWidgetcontainer.setHorizontalDimensionBehaviour(dimensionBehaviour);
        this.mWidgetcontainer.setVerticalDimensionBehaviour(dimensionBehaviour2);
        return z3;
    }

    public boolean directMeasureSetup(boolean z) {
        if (this.mNeedBuildGraph) {
            for (ConstraintWidget constraintWidget : this.mWidgetcontainer.mChildren) {
                constraintWidget.ensureWidgetRuns();
                constraintWidget.measured = false;
                constraintWidget.mHorizontalRun.mDimension.resolved = false;
                constraintWidget.mHorizontalRun.mResolved = false;
                constraintWidget.mHorizontalRun.reset();
                constraintWidget.mVerticalRun.mDimension.resolved = false;
                constraintWidget.mVerticalRun.mResolved = false;
                constraintWidget.mVerticalRun.reset();
            }
            this.mWidgetcontainer.ensureWidgetRuns();
            this.mWidgetcontainer.measured = false;
            this.mWidgetcontainer.mHorizontalRun.mDimension.resolved = false;
            this.mWidgetcontainer.mHorizontalRun.mResolved = false;
            this.mWidgetcontainer.mHorizontalRun.reset();
            this.mWidgetcontainer.mVerticalRun.mDimension.resolved = false;
            this.mWidgetcontainer.mVerticalRun.mResolved = false;
            this.mWidgetcontainer.mVerticalRun.reset();
            buildGraph();
        }
        if (basicMeasureWidgets(this.mContainer)) {
            return false;
        }
        this.mWidgetcontainer.setX(0);
        this.mWidgetcontainer.setY(0);
        this.mWidgetcontainer.mHorizontalRun.start.resolve(0);
        this.mWidgetcontainer.mVerticalRun.start.resolve(0);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00d4  */
    public boolean directMeasureWithOrientation(boolean z, int i) {
        boolean z2;
        boolean z3 = false;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.mWidgetcontainer.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.mWidgetcontainer.getDimensionBehaviour(1);
        int x = this.mWidgetcontainer.getX();
        int y = this.mWidgetcontainer.getY();
        if (z && (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
            for (WidgetRun widgetRun : this.mRuns) {
                if (widgetRun.orientation == i && !widgetRun.supportsWrapComputation()) {
                    z = false;
                    break;
                }
            }
            if (i == 0) {
                if (z && dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.mWidgetcontainer.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    ConstraintWidgetContainer constraintWidgetContainer = this.mWidgetcontainer;
                    constraintWidgetContainer.setWidth(computeWrap(constraintWidgetContainer, 0));
                    this.mWidgetcontainer.mHorizontalRun.mDimension.resolve(this.mWidgetcontainer.getWidth());
                }
            } else if (z && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.mWidgetcontainer.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer2 = this.mWidgetcontainer;
                constraintWidgetContainer2.setHeight(computeWrap(constraintWidgetContainer2, 1));
                this.mWidgetcontainer.mVerticalRun.mDimension.resolve(this.mWidgetcontainer.getHeight());
            }
        }
        if (i == 0) {
            if (this.mWidgetcontainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || this.mWidgetcontainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int width = this.mWidgetcontainer.getWidth() + x;
                this.mWidgetcontainer.mHorizontalRun.end.resolve(width);
                this.mWidgetcontainer.mHorizontalRun.mDimension.resolve(width - x);
                z2 = true;
            } else {
                z2 = false;
            }
        } else if (this.mWidgetcontainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || this.mWidgetcontainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int height = this.mWidgetcontainer.getHeight() + y;
            this.mWidgetcontainer.mVerticalRun.end.resolve(height);
            this.mWidgetcontainer.mVerticalRun.mDimension.resolve(height - y);
            z2 = true;
        } else {
            z2 = false;
        }
        measureWidgets();
        for (WidgetRun widgetRun2 : this.mRuns) {
            if (widgetRun2.orientation == i && (widgetRun2.mWidget != this.mWidgetcontainer || widgetRun2.mResolved)) {
                widgetRun2.applyToWidget();
            }
        }
        for (WidgetRun widgetRun3 : this.mRuns) {
            if (widgetRun3.orientation == i && (z2 || widgetRun3.mWidget != this.mWidgetcontainer)) {
                if (!widgetRun3.start.resolved || !widgetRun3.end.resolved || (!(widgetRun3 instanceof ChainRun) && !widgetRun3.mDimension.resolved)) {
                    this.mWidgetcontainer.setHorizontalDimensionBehaviour(dimensionBehaviour);
                    this.mWidgetcontainer.setVerticalDimensionBehaviour(dimensionBehaviour2);
                    return z3;
                }
            }
        }
        z3 = true;
        this.mWidgetcontainer.setHorizontalDimensionBehaviour(dimensionBehaviour);
        this.mWidgetcontainer.setVerticalDimensionBehaviour(dimensionBehaviour2);
        return z3;
    }

    private void measure(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int i, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int i2) {
        this.mMeasure.horizontalBehavior = dimensionBehaviour;
        this.mMeasure.verticalBehavior = dimensionBehaviour2;
        this.mMeasure.horizontalDimension = i;
        this.mMeasure.verticalDimension = i2;
        this.mMeasurer.measure(constraintWidget, this.mMeasure);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        constraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
    }

    /* JADX WARN: Code duplicated, block: B:104:0x01bb A[PHI: r3 r7 r13
  0x01bb: PHI (r3v5 androidx.constraintlayout.core.widgets.analyzer.DependencyGraph) = 
  (r3v6 androidx.constraintlayout.core.widgets.analyzer.DependencyGraph)
  (r3v10 androidx.constraintlayout.core.widgets.analyzer.DependencyGraph)
  (r3v10 androidx.constraintlayout.core.widgets.analyzer.DependencyGraph)
 binds: [B:103:0x01b7, B:101:0x0194, B:96:0x0157] A[DONT_GENERATE, DONT_INLINE]
  0x01bb: PHI (r7v5 androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour) = 
  (r7v12 androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour)
  (r7v15 androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour)
  (r7v15 androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour)
 binds: [B:103:0x01b7, B:101:0x0194, B:96:0x0157] A[DONT_GENERATE, DONT_INLINE]
  0x01bb: PHI (r13v18 int) = (r13v69 int), (r13v74 int), (r13v74 int) binds: [B:103:0x01b7, B:101:0x0194, B:96:0x0157] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:106:0x01bf  */
    /* JADX WARN: Code duplicated, block: B:134:0x0295  */
    /* JADX WARN: Code duplicated, block: B:136:0x0299  */
    /* JADX WARN: Code duplicated, block: B:140:0x02a1  */
    /* JADX WARN: Code duplicated, block: B:152:0x02f2  */
    private boolean basicMeasureWidgets(ConstraintWidgetContainer constraintWidgetContainer) {
        DependencyGraph dependencyGraph;
        int i;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        DependencyGraph dependencyGraph2;
        for (ConstraintWidget constraintWidget : constraintWidgetContainer.mChildren) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.mListDimensionBehaviors[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidget.mListDimensionBehaviors[1];
            if (constraintWidget.getVisibility() == 8) {
                constraintWidget.measured = true;
            } else {
                if (constraintWidget.mMatchConstraintPercentWidth < 1.0f && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintWidget.mMatchConstraintDefaultWidth = 2;
                }
                if (constraintWidget.mMatchConstraintPercentHeight < 1.0f && dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintWidget.mMatchConstraintDefaultHeight = 2;
                }
                if (constraintWidget.getDimensionRatio() > 0.0f) {
                    if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        constraintWidget.mMatchConstraintDefaultWidth = 3;
                    } else if (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        constraintWidget.mMatchConstraintDefaultHeight = 3;
                    } else if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        if (constraintWidget.mMatchConstraintDefaultWidth == 0) {
                            constraintWidget.mMatchConstraintDefaultWidth = 3;
                        }
                        if (constraintWidget.mMatchConstraintDefaultHeight == 0) {
                            constraintWidget.mMatchConstraintDefaultHeight = 3;
                        }
                    }
                }
                if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 1 && (constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget == null)) {
                    dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = dimensionBehaviour2;
                if (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 1 && (constraintWidget.mTop.mTarget == null || constraintWidget.mBottom.mTarget == null)) {
                    dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                }
                constraintWidget.mHorizontalRun.mDimensionBehavior = dimensionBehaviour4;
                constraintWidget.mHorizontalRun.matchConstraintsType = constraintWidget.mMatchConstraintDefaultWidth;
                constraintWidget.mVerticalRun.mDimensionBehavior = dimensionBehaviour3;
                constraintWidget.mVerticalRun.matchConstraintsType = constraintWidget.mMatchConstraintDefaultHeight;
                if ((dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = dimensionBehaviour3;
                    dependencyGraph = this;
                    int width = constraintWidget.getWidth();
                    if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                        width = (constraintWidgetContainer.getWidth() - constraintWidget.mLeft.mMargin) - constraintWidget.mRight.mMargin;
                        dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.FIXED;
                    }
                    int i2 = width;
                    int height = constraintWidget.getHeight();
                    if (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                        height = (constraintWidgetContainer.getHeight() - constraintWidget.mTop.mMargin) - constraintWidget.mBottom.mMargin;
                        dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.FIXED;
                    }
                    dependencyGraph.measure(constraintWidget, dimensionBehaviour4, i2, dimensionBehaviour5, height);
                    constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                    constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                    constraintWidget.measured = true;
                    this = dependencyGraph;
                } else if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                    if (constraintWidget.mMatchConstraintDefaultWidth == 3) {
                        if (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                            dependencyGraph2 = this;
                            dependencyGraph2.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                        } else {
                            dependencyGraph2 = this;
                        }
                        int height2 = constraintWidget.getHeight();
                        dependencyGraph2.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, (int) ((height2 * constraintWidget.mDimensionRatio) + 0.5f), ConstraintWidget.DimensionBehaviour.FIXED, height2);
                        this = dependencyGraph2;
                        constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                        constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                        constraintWidget.measured = true;
                    } else {
                        if (constraintWidget.mMatchConstraintDefaultWidth == 1) {
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = dimensionBehaviour3;
                            dependencyGraph = this;
                            dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, dimensionBehaviour6, 0);
                            constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                        } else {
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = dimensionBehaviour3;
                            dependencyGraph = this;
                            i = 3;
                            dimensionBehaviour = dimensionBehaviour7;
                            if (constraintWidget.mMatchConstraintDefaultWidth == 2) {
                                if (constraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || constraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                                    dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, (int) ((constraintWidget.mMatchConstraintPercentWidth * constraintWidgetContainer.getWidth()) + 0.5f), dimensionBehaviour, constraintWidget.getHeight());
                                    constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                                    constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                                    constraintWidget.measured = true;
                                } else if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                        if (constraintWidget.mMatchConstraintDefaultWidth != 1) {
                                            dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                            constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                            constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                                        } else {
                                            dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                            constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                            constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                                        }
                                    }
                                } else if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    if (constraintWidget.mMatchConstraintDefaultWidth != 1) {
                                        dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                        constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                        constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                                    } else {
                                        dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                        constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                        constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                                    }
                                }
                            } else if (constraintWidget.mListAnchors[0].mTarget == null || constraintWidget.mListAnchors[1].mTarget == null) {
                                dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, dimensionBehaviour, 0);
                                constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                                constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                                constraintWidget.measured = true;
                            } else if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    if (constraintWidget.mMatchConstraintDefaultWidth != 1) {
                                        dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                        constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                        constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                                    } else {
                                        dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                        constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                        constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                                    }
                                }
                            } else if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                if (constraintWidget.mMatchConstraintDefaultWidth != 1) {
                                    dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                    constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                    constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                                } else {
                                    dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                    constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                    constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                                }
                            }
                        }
                        this = dependencyGraph;
                    }
                } else {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour8 = dimensionBehaviour3;
                    dependencyGraph = this;
                    i = 3;
                    dimensionBehaviour = dimensionBehaviour8;
                    if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        if (constraintWidget.mMatchConstraintDefaultHeight == i) {
                            if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                                dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                            }
                            int width2 = constraintWidget.getWidth();
                            float f = constraintWidget.mDimensionRatio;
                            if (constraintWidget.getDimensionRatioSide() == -1) {
                                f = 1.0f / f;
                            }
                            dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, width2, ConstraintWidget.DimensionBehaviour.FIXED, (int) ((width2 * f) + 0.5f));
                            constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                            constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                        } else if (constraintWidget.mMatchConstraintDefaultHeight == 1) {
                            dependencyGraph.measure(constraintWidget, dimensionBehaviour4, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                            constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                        } else if (constraintWidget.mMatchConstraintDefaultHeight == 2) {
                            if (constraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || constraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                                dependencyGraph.measure(constraintWidget, dimensionBehaviour4, constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, (int) ((constraintWidget.mMatchConstraintPercentHeight * constraintWidgetContainer.getHeight()) + 0.5f));
                                constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                                constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                                constraintWidget.measured = true;
                            } else if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                if (constraintWidget.mMatchConstraintDefaultWidth != 1) {
                                    dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                    constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                    constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                                } else {
                                    dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                    constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                    constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                                }
                            }
                        } else if (constraintWidget.mListAnchors[2].mTarget == null || constraintWidget.mListAnchors[i].mTarget == null) {
                            dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, dimensionBehaviour, 0);
                            constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                            constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                        } else if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            if (constraintWidget.mMatchConstraintDefaultWidth != 1) {
                                dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                            } else {
                                dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                                constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                                constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                            }
                        }
                    } else if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        if (constraintWidget.mMatchConstraintDefaultWidth != 1 || constraintWidget.mMatchConstraintDefaultHeight == 1) {
                            dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                            constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                            constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                        } else if (constraintWidget.mMatchConstraintDefaultHeight == 2 && constraintWidget.mMatchConstraintDefaultWidth == 2 && constraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED && constraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) {
                            dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, (int) ((constraintWidget.mMatchConstraintPercentWidth * constraintWidgetContainer.getWidth()) + 0.5f), ConstraintWidget.DimensionBehaviour.FIXED, (int) ((constraintWidget.mMatchConstraintPercentHeight * constraintWidgetContainer.getHeight()) + 0.5f));
                            constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                            constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                        }
                    }
                    this = dependencyGraph;
                }
            }
        }
        return false;
    }

    public void measureWidgets() {
        DependencyGraph dependencyGraph;
        for (ConstraintWidget constraintWidget : this.mWidgetcontainer.mChildren) {
            if (!constraintWidget.measured) {
                boolean z = false;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.mListDimensionBehaviors[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.mListDimensionBehaviors[1];
                int i = constraintWidget.mMatchConstraintDefaultWidth;
                int i2 = constraintWidget.mMatchConstraintDefaultHeight;
                boolean z2 = dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && i == 1);
                if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && i2 == 1)) {
                    z = true;
                }
                boolean z3 = constraintWidget.mHorizontalRun.mDimension.resolved;
                boolean z4 = constraintWidget.mVerticalRun.mDimension.resolved;
                if (z3 && z4) {
                    dependencyGraph = this;
                    dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.mHorizontalRun.mDimension.value, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.mVerticalRun.mDimension.value);
                    constraintWidget.measured = true;
                } else if (z3 && z) {
                    dependencyGraph = this;
                    dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.mHorizontalRun.mDimension.value, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, constraintWidget.mVerticalRun.mDimension.value);
                    if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                    } else {
                        constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                        constraintWidget.measured = true;
                    }
                } else {
                    dependencyGraph = this;
                    if (z4 && z2) {
                        dependencyGraph.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, constraintWidget.mHorizontalRun.mDimension.value, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.mVerticalRun.mDimension.value);
                        if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                        } else {
                            constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                            constraintWidget.measured = true;
                        }
                    }
                }
                if (constraintWidget.measured && constraintWidget.mVerticalRun.mBaselineDimension != null) {
                    constraintWidget.mVerticalRun.mBaselineDimension.resolve(constraintWidget.getBaselineDistance());
                }
                this = dependencyGraph;
            }
        }
    }

    public void invalidateGraph() {
        this.mNeedBuildGraph = true;
    }

    public void invalidateMeasures() {
        this.mNeedRedoMeasures = true;
    }

    public void buildGraph() {
        buildGraph(this.mRuns);
        this.mGroups.clear();
        RunGroup.index = 0;
        findGroup(this.mWidgetcontainer.mHorizontalRun, 0, this.mGroups);
        findGroup(this.mWidgetcontainer.mVerticalRun, 1, this.mGroups);
        this.mNeedBuildGraph = false;
    }

    public void buildGraph(ArrayList<WidgetRun> arrayList) {
        arrayList.clear();
        this.mContainer.mHorizontalRun.clear();
        this.mContainer.mVerticalRun.clear();
        arrayList.add(this.mContainer.mHorizontalRun);
        arrayList.add(this.mContainer.mVerticalRun);
        HashSet hashSet = null;
        for (ConstraintWidget constraintWidget : this.mContainer.mChildren) {
            if (constraintWidget instanceof Guideline) {
                arrayList.add(new GuidelineReference(constraintWidget));
            } else {
                if (constraintWidget.isInHorizontalChain()) {
                    if (constraintWidget.horizontalChainRun == null) {
                        constraintWidget.horizontalChainRun = new ChainRun(constraintWidget, 0);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(constraintWidget.horizontalChainRun);
                } else {
                    arrayList.add(constraintWidget.mHorizontalRun);
                }
                if (constraintWidget.isInVerticalChain()) {
                    if (constraintWidget.verticalChainRun == null) {
                        constraintWidget.verticalChainRun = new ChainRun(constraintWidget, 1);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(constraintWidget.verticalChainRun);
                } else {
                    arrayList.add(constraintWidget.mVerticalRun);
                }
                if (constraintWidget instanceof HelperWidget) {
                    arrayList.add(new HelperReferences(constraintWidget));
                }
            }
        }
        if (hashSet != null) {
            arrayList.addAll(hashSet);
        }
        Iterator<WidgetRun> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        for (WidgetRun widgetRun : arrayList) {
            if (widgetRun.mWidget != this.mContainer) {
                widgetRun.apply();
            }
        }
    }

    private void displayGraph() {
        Iterator<WidgetRun> it = this.mRuns.iterator();
        String strGenerateDisplayGraph = "digraph {\n";
        while (it.hasNext()) {
            strGenerateDisplayGraph = generateDisplayGraph(it.next(), strGenerateDisplayGraph);
        }
        System.out.println("content:<<\n" + (strGenerateDisplayGraph + "\n}\n") + "\n>>");
    }

    private void applyGroup(DependencyNode dependencyNode, int i, int i2, DependencyNode dependencyNode2, ArrayList<RunGroup> arrayList, RunGroup runGroup) {
        DependencyGraph dependencyGraph;
        int i3;
        DependencyNode dependencyNode3;
        ArrayList<RunGroup> arrayList2;
        WidgetRun widgetRun = dependencyNode.mRun;
        if (widgetRun.mRunGroup != null || widgetRun == this.mWidgetcontainer.mHorizontalRun || widgetRun == this.mWidgetcontainer.mVerticalRun) {
            return;
        }
        if (runGroup == null) {
            runGroup = new RunGroup(widgetRun, i2);
            arrayList.add(runGroup);
        }
        RunGroup runGroup2 = runGroup;
        widgetRun.mRunGroup = runGroup2;
        runGroup2.add(widgetRun);
        for (Dependency dependency : widgetRun.start.mDependencies) {
            if (dependency instanceof DependencyNode) {
                dependencyGraph = this;
                i3 = i;
                dependencyNode3 = dependencyNode2;
                arrayList2 = arrayList;
                dependencyGraph.applyGroup((DependencyNode) dependency, i3, 0, dependencyNode3, arrayList2, runGroup2);
            } else {
                dependencyGraph = this;
                i3 = i;
                dependencyNode3 = dependencyNode2;
                arrayList2 = arrayList;
            }
            this = dependencyGraph;
            i = i3;
            dependencyNode2 = dependencyNode3;
            arrayList = arrayList2;
        }
        DependencyGraph dependencyGraph2 = this;
        int i4 = i;
        DependencyNode dependencyNode4 = dependencyNode2;
        ArrayList<RunGroup> arrayList3 = arrayList;
        for (Dependency dependency2 : widgetRun.end.mDependencies) {
            if (dependency2 instanceof DependencyNode) {
                dependencyGraph2.applyGroup((DependencyNode) dependency2, i4, 1, dependencyNode4, arrayList3, runGroup2);
            }
        }
        if (i4 == 1 && (widgetRun instanceof VerticalWidgetRun)) {
            for (Dependency dependency3 : ((VerticalWidgetRun) widgetRun).baseline.mDependencies) {
                if (dependency3 instanceof DependencyNode) {
                    dependencyGraph2.applyGroup((DependencyNode) dependency3, i4, 2, dependencyNode4, arrayList3, runGroup2);
                }
            }
        }
        for (DependencyNode dependencyNode5 : widgetRun.start.mTargets) {
            if (dependencyNode5 == dependencyNode4) {
                runGroup2.dual = true;
            }
            dependencyGraph2.applyGroup(dependencyNode5, i4, 0, dependencyNode4, arrayList3, runGroup2);
        }
        for (DependencyNode dependencyNode6 : widgetRun.end.mTargets) {
            if (dependencyNode6 == dependencyNode4) {
                runGroup2.dual = true;
            }
            dependencyGraph2.applyGroup(dependencyNode6, i4, 1, dependencyNode4, arrayList3, runGroup2);
        }
        if (i4 == 1 && (widgetRun instanceof VerticalWidgetRun)) {
            Iterator<DependencyNode> it = ((VerticalWidgetRun) widgetRun).baseline.mTargets.iterator();
            while (it.hasNext()) {
                dependencyGraph2.applyGroup(it.next(), i4, 2, dependencyNode4, arrayList3, runGroup2);
            }
        }
    }

    private void findGroup(WidgetRun widgetRun, int i, ArrayList<RunGroup> arrayList) {
        for (Dependency dependency : widgetRun.start.mDependencies) {
            if (dependency instanceof DependencyNode) {
                applyGroup((DependencyNode) dependency, i, 0, widgetRun.end, arrayList, null);
            } else if (dependency instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependency).start, i, 0, widgetRun.end, arrayList, null);
            }
        }
        for (Dependency dependency2 : widgetRun.end.mDependencies) {
            if (dependency2 instanceof DependencyNode) {
                applyGroup((DependencyNode) dependency2, i, 1, widgetRun.start, arrayList, null);
            } else if (dependency2 instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependency2).end, i, 1, widgetRun.start, arrayList, null);
            }
        }
        int i2 = i;
        if (i2 == 1) {
            for (Dependency dependency3 : ((VerticalWidgetRun) widgetRun).baseline.mDependencies) {
                if (dependency3 instanceof DependencyNode) {
                    applyGroup((DependencyNode) dependency3, i2, 2, null, arrayList, null);
                }
                i2 = i;
            }
        }
    }

    private String generateDisplayNode(DependencyNode dependencyNode, boolean z, String str) {
        StringBuilder sb = new StringBuilder(str);
        Iterator<DependencyNode> it = dependencyNode.mTargets.iterator();
        while (it.hasNext()) {
            String str2 = (StringUtils.LF + dependencyNode.name()) + " -> " + it.next().name();
            if (dependencyNode.mMargin > 0 || z || (dependencyNode.mRun instanceof HelperReferences)) {
                String str3 = str2 + "[";
                if (dependencyNode.mMargin > 0) {
                    str3 = str3 + "label=\"" + dependencyNode.mMargin + "\"";
                    if (z) {
                        str3 = str3 + ",";
                    }
                }
                if (z) {
                    str3 = str3 + " style=dashed ";
                }
                if (dependencyNode.mRun instanceof HelperReferences) {
                    str3 = str3 + " style=bold,color=gray ";
                }
                str2 = str3 + "]";
            }
            sb.append(str2 + StringUtils.LF);
        }
        return sb.toString();
    }

    private String nodeDefinition(WidgetRun widgetRun) {
        ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour;
        boolean z = widgetRun instanceof VerticalWidgetRun;
        String debugName = widgetRun.mWidget.getDebugName();
        StringBuilder sb = new StringBuilder(debugName);
        if (!z) {
            verticalDimensionBehaviour = widgetRun.mWidget.getHorizontalDimensionBehaviour();
        } else {
            verticalDimensionBehaviour = widgetRun.mWidget.getVerticalDimensionBehaviour();
        }
        RunGroup runGroup = widgetRun.mRunGroup;
        if (!z) {
            sb.append("_HORIZONTAL");
        } else {
            sb.append("_VERTICAL");
        }
        sb.append(" [shape=none, label=<<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\">  <TR>");
        if (!z) {
            sb.append("    <TD ");
            if (widgetRun.start.resolved) {
                sb.append(" BGCOLOR=\"green\"");
            }
            sb.append(" PORT=\"LEFT\" BORDER=\"1\">L</TD>");
        } else {
            sb.append("    <TD ");
            if (widgetRun.start.resolved) {
                sb.append(" BGCOLOR=\"green\"");
            }
            sb.append(" PORT=\"TOP\" BORDER=\"1\">T</TD>");
        }
        sb.append("    <TD BORDER=\"1\" ");
        if (widgetRun.mDimension.resolved && !widgetRun.mWidget.measured) {
            sb.append(" BGCOLOR=\"green\" ");
        } else if (widgetRun.mDimension.resolved) {
            sb.append(" BGCOLOR=\"lightgray\" ");
        } else if (widgetRun.mWidget.measured) {
            sb.append(" BGCOLOR=\"yellow\" ");
        }
        if (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            sb.append("style=\"dashed\"");
        }
        sb.append(">");
        sb.append(debugName);
        if (runGroup != null) {
            sb.append(" [");
            sb.append(runGroup.mGroupIndex + 1);
            sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
            sb.append(RunGroup.index);
            sb.append("]");
        }
        sb.append(" </TD>");
        if (!z) {
            sb.append("    <TD ");
            if (widgetRun.end.resolved) {
                sb.append(" BGCOLOR=\"green\"");
            }
            sb.append(" PORT=\"RIGHT\" BORDER=\"1\">R</TD>");
        } else {
            sb.append("    <TD ");
            if (((VerticalWidgetRun) widgetRun).baseline.resolved) {
                sb.append(" BGCOLOR=\"green\"");
            }
            sb.append(" PORT=\"BASELINE\" BORDER=\"1\">b</TD>    <TD ");
            if (widgetRun.end.resolved) {
                sb.append(" BGCOLOR=\"green\"");
            }
            sb.append(" PORT=\"BOTTOM\" BORDER=\"1\">B</TD>");
        }
        sb.append("  </TR></TABLE>>];\n");
        return sb.toString();
    }

    private String generateChainDisplayGraph(ChainRun chainRun, String str) {
        int i = chainRun.orientation;
        StringBuilder sb = new StringBuilder("subgraph cluster_");
        sb.append(chainRun.mWidget.getDebugName());
        if (i == 0) {
            sb.append("_h");
        } else {
            sb.append("_v");
        }
        sb.append(" {\n");
        String strGenerateDisplayGraph = "";
        for (WidgetRun widgetRun : chainRun.mWidgets) {
            sb.append(widgetRun.mWidget.getDebugName());
            if (i == 0) {
                sb.append("_HORIZONTAL");
            } else {
                sb.append("_VERTICAL");
            }
            sb.append(";\n");
            strGenerateDisplayGraph = generateDisplayGraph(widgetRun, strGenerateDisplayGraph);
        }
        sb.append("}\n");
        return str + strGenerateDisplayGraph + ((Object) sb);
    }

    private boolean isCenteredConnection(DependencyNode dependencyNode, DependencyNode dependencyNode2) {
        Iterator<DependencyNode> it = dependencyNode.mTargets.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next() != dependencyNode2) {
                i++;
            }
        }
        Iterator<DependencyNode> it2 = dependencyNode2.mTargets.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            if (it2.next() != dependencyNode) {
                i2++;
            }
        }
        return i > 0 && i2 > 0;
    }

    private String generateDisplayGraph(WidgetRun widgetRun, String str) {
        boolean z;
        DependencyNode dependencyNode = widgetRun.start;
        DependencyNode dependencyNode2 = widgetRun.end;
        StringBuilder sb = new StringBuilder(str);
        if (!(widgetRun instanceof HelperReferences) && dependencyNode.mDependencies.isEmpty() && dependencyNode2.mDependencies.isEmpty() && dependencyNode.mTargets.isEmpty() && dependencyNode2.mTargets.isEmpty()) {
            return str;
        }
        sb.append(nodeDefinition(widgetRun));
        boolean zIsCenteredConnection = isCenteredConnection(dependencyNode, dependencyNode2);
        String strGenerateDisplayNode = generateDisplayNode(dependencyNode2, zIsCenteredConnection, generateDisplayNode(dependencyNode, zIsCenteredConnection, str));
        boolean z2 = widgetRun instanceof VerticalWidgetRun;
        if (z2) {
            strGenerateDisplayNode = generateDisplayNode(((VerticalWidgetRun) widgetRun).baseline, zIsCenteredConnection, strGenerateDisplayNode);
        }
        if ((widgetRun instanceof HorizontalWidgetRun) || (((z = widgetRun instanceof ChainRun)) && ((ChainRun) widgetRun).orientation == 0)) {
            ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = widgetRun.mWidget.getHorizontalDimensionBehaviour();
            if (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                if (!dependencyNode.mTargets.isEmpty() && dependencyNode2.mTargets.isEmpty()) {
                    sb.append(StringUtils.LF);
                    sb.append(dependencyNode2.name());
                    sb.append(" -> ");
                    sb.append(dependencyNode.name());
                    sb.append(StringUtils.LF);
                } else if (dependencyNode.mTargets.isEmpty() && !dependencyNode2.mTargets.isEmpty()) {
                    sb.append(StringUtils.LF);
                    sb.append(dependencyNode.name());
                    sb.append(" -> ");
                    sb.append(dependencyNode2.name());
                    sb.append(StringUtils.LF);
                }
            } else if (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun.mWidget.getDimensionRatio() > 0.0f) {
                sb.append(StringUtils.LF);
                sb.append(widgetRun.mWidget.getDebugName());
                sb.append("_HORIZONTAL -> ");
                sb.append(widgetRun.mWidget.getDebugName());
                sb.append("_VERTICAL;\n");
            }
        } else if (z2 || (z && ((ChainRun) widgetRun).orientation == 1)) {
            ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour = widgetRun.mWidget.getVerticalDimensionBehaviour();
            if (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                if (!dependencyNode.mTargets.isEmpty() && dependencyNode2.mTargets.isEmpty()) {
                    sb.append(StringUtils.LF);
                    sb.append(dependencyNode2.name());
                    sb.append(" -> ");
                    sb.append(dependencyNode.name());
                    sb.append(StringUtils.LF);
                } else if (dependencyNode.mTargets.isEmpty() && !dependencyNode2.mTargets.isEmpty()) {
                    sb.append(StringUtils.LF);
                    sb.append(dependencyNode.name());
                    sb.append(" -> ");
                    sb.append(dependencyNode2.name());
                    sb.append(StringUtils.LF);
                }
            } else if (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun.mWidget.getDimensionRatio() > 0.0f) {
                sb.append(StringUtils.LF);
                sb.append(widgetRun.mWidget.getDebugName());
                sb.append("_VERTICAL -> ");
                sb.append(widgetRun.mWidget.getDebugName());
                sb.append("_HORIZONTAL;\n");
            }
        }
        if (widgetRun instanceof ChainRun) {
            return generateChainDisplayGraph((ChainRun) widgetRun, strGenerateDisplayNode);
        }
        return sb.toString();
    }
}
