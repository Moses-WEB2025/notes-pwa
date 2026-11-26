package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;

/* JADX INFO: loaded from: classes.dex */
public abstract class WidgetRun implements Dependency {
    protected ConstraintWidget.DimensionBehaviour mDimensionBehavior;
    RunGroup mRunGroup;
    ConstraintWidget mWidget;
    public int matchConstraintsType;
    DimensionDependency mDimension = new DimensionDependency(this);
    public int orientation = 0;
    boolean mResolved = false;
    public DependencyNode start = new DependencyNode(this);
    public DependencyNode end = new DependencyNode(this);
    protected RunType mRunType = RunType.NONE;

    enum RunType {
        NONE,
        START,
        END,
        CENTER
    }

    abstract void apply();

    abstract void applyToWidget();

    abstract void clear();

    abstract void reset();

    abstract boolean supportsWrapComputation();

    @Override // androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
    }

    protected void updateRunEnd(Dependency dependency) {
    }

    protected void updateRunStart(Dependency dependency) {
    }

    public WidgetRun(ConstraintWidget constraintWidget) {
        this.mWidget = constraintWidget;
    }

    public boolean isDimensionResolved() {
        return this.mDimension.resolved;
    }

    public boolean isCenterConnection() {
        int size = this.start.mTargets.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (this.start.mTargets.get(i2).mRun != this) {
                i++;
            }
        }
        int size2 = this.end.mTargets.size();
        for (int i3 = 0; i3 < size2; i3++) {
            if (this.end.mTargets.get(i3).mRun != this) {
                i++;
            }
        }
        return i >= 2;
    }

    public long wrapSize(int i) {
        if (!this.mDimension.resolved) {
            return 0L;
        }
        long j = this.mDimension.value;
        if (isCenterConnection()) {
            return j + ((long) (this.start.mMargin - this.end.mMargin));
        }
        if (i == 0) {
            return j + ((long) this.start.mMargin);
        }
        return j - ((long) this.end.mMargin);
    }

    protected final DependencyNode getTarget(ConstraintAnchor constraintAnchor) {
        if (constraintAnchor.mTarget == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor.mTarget.mOwner;
        int i = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[constraintAnchor.mTarget.mType.ordinal()];
        if (i == 1) {
            return constraintWidget.mHorizontalRun.start;
        }
        if (i == 2) {
            return constraintWidget.mHorizontalRun.end;
        }
        if (i == 3) {
            return constraintWidget.mVerticalRun.start;
        }
        if (i == 4) {
            return constraintWidget.mVerticalRun.baseline;
        }
        if (i != 5) {
            return null;
        }
        return constraintWidget.mVerticalRun.end;
    }

    /* JADX INFO: renamed from: androidx.constraintlayout.core.widgets.analyzer.WidgetRun$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BASELINE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BOTTOM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    protected void updateRunCenter(Dependency dependency, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i) {
        float verticalBiasPercent;
        DependencyNode target = getTarget(constraintAnchor);
        DependencyNode target2 = getTarget(constraintAnchor2);
        if (target.resolved && target2.resolved) {
            int margin = target.value + constraintAnchor.getMargin();
            int margin2 = target2.value - constraintAnchor2.getMargin();
            int i2 = margin2 - margin;
            if (!this.mDimension.resolved && this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                resolveDimension(i, i2);
            }
            if (this.mDimension.resolved) {
                if (this.mDimension.value == i2) {
                    this.start.resolve(margin);
                    this.end.resolve(margin2);
                    return;
                }
                if (i == 0) {
                    verticalBiasPercent = this.mWidget.getHorizontalBiasPercent();
                } else {
                    verticalBiasPercent = this.mWidget.getVerticalBiasPercent();
                }
                if (target == target2) {
                    margin = target.value;
                    margin2 = target2.value;
                    verticalBiasPercent = 0.5f;
                }
                this.start.resolve((int) (margin + 0.5f + (((margin2 - margin) - this.mDimension.value) * verticalBiasPercent)));
                this.end.resolve(this.start.value + this.mDimension.value);
            }
        }
    }

    private void resolveDimension(int i, int i2) {
        WidgetRun widgetRun;
        float f;
        int i3;
        int i4 = this.matchConstraintsType;
        if (i4 == 0) {
            this.mDimension.resolve(getLimitedDimension(i2, i));
            return;
        }
        if (i4 == 1) {
            this.mDimension.resolve(Math.min(getLimitedDimension(this.mDimension.wrapValue, i), i2));
            return;
        }
        if (i4 == 2) {
            ConstraintWidget parent = this.mWidget.getParent();
            if (parent != null) {
                if (i == 0) {
                    widgetRun = parent.mHorizontalRun;
                } else {
                    widgetRun = parent.mVerticalRun;
                }
                if (widgetRun.mDimension.resolved) {
                    if (i == 0) {
                        f = this.mWidget.mMatchConstraintPercentWidth;
                    } else {
                        f = this.mWidget.mMatchConstraintPercentHeight;
                    }
                    this.mDimension.resolve(getLimitedDimension((int) ((widgetRun.mDimension.value * f) + 0.5f), i));
                    return;
                }
                return;
            }
            return;
        }
        if (i4 != 3) {
            return;
        }
        if (this.mWidget.mHorizontalRun.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.mWidget.mHorizontalRun.matchConstraintsType == 3 && this.mWidget.mVerticalRun.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.mWidget.mVerticalRun.matchConstraintsType == 3) {
            return;
        }
        WidgetRun widgetRun2 = i == 0 ? this.mWidget.mVerticalRun : this.mWidget.mHorizontalRun;
        if (widgetRun2.mDimension.resolved) {
            float dimensionRatio = this.mWidget.getDimensionRatio();
            if (i == 1) {
                i3 = (int) ((widgetRun2.mDimension.value / dimensionRatio) + 0.5f);
            } else {
                i3 = (int) ((dimensionRatio * widgetRun2.mDimension.value) + 0.5f);
            }
            this.mDimension.resolve(i3);
        }
    }

    protected final int getLimitedDimension(int i, int i2) {
        if (i2 == 0) {
            int i3 = this.mWidget.mMatchConstraintMaxWidth;
            int iMax = Math.max(this.mWidget.mMatchConstraintMinWidth, i);
            if (i3 > 0) {
                iMax = Math.min(i3, i);
            }
            if (iMax != i) {
                return iMax;
            }
        } else {
            int i4 = this.mWidget.mMatchConstraintMaxHeight;
            int iMax2 = Math.max(this.mWidget.mMatchConstraintMinHeight, i);
            if (i4 > 0) {
                iMax2 = Math.min(i4, i);
            }
            if (iMax2 != i) {
                return iMax2;
            }
        }
        return i;
    }

    protected final DependencyNode getTarget(ConstraintAnchor constraintAnchor, int i) {
        if (constraintAnchor.mTarget == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor.mTarget.mOwner;
        WidgetRun widgetRun = i == 0 ? constraintWidget.mHorizontalRun : constraintWidget.mVerticalRun;
        int i2 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[constraintAnchor.mTarget.mType.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 != 5) {
                        return null;
                    }
                }
            }
            return widgetRun.end;
        }
        return widgetRun.start;
    }

    protected final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i) {
        dependencyNode.mTargets.add(dependencyNode2);
        dependencyNode.mMargin = i;
        dependencyNode2.mDependencies.add(dependencyNode);
    }

    protected final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i, DimensionDependency dimensionDependency) {
        dependencyNode.mTargets.add(dependencyNode2);
        dependencyNode.mTargets.add(this.mDimension);
        dependencyNode.mMarginFactor = i;
        dependencyNode.mMarginDependency = dimensionDependency;
        dependencyNode2.mDependencies.add(dependencyNode);
        dimensionDependency.mDependencies.add(dependencyNode);
    }

    public long getWrapDimension() {
        if (this.mDimension.resolved) {
            return this.mDimension.value;
        }
        return 0L;
    }

    public boolean isResolved() {
        return this.mResolved;
    }
}
