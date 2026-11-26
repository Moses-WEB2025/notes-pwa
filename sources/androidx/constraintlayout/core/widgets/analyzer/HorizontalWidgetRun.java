package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;

/* JADX INFO: loaded from: classes.dex */
public class HorizontalWidgetRun extends WidgetRun {
    private static int[] sTempDimensions = new int[2];

    public HorizontalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.mType = DependencyNode.Type.LEFT;
        this.end.mType = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    public String toString() {
        return "HorizontalRun " + this.mWidget.getDebugName();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void clear() {
        this.mRunGroup = null;
        this.start.clear();
        this.end.clear();
        this.mDimension.clear();
        this.mResolved = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void reset() {
        this.mResolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.mDimension.resolved = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    boolean supportsWrapComputation() {
        return this.mDimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.mWidget.mMatchConstraintDefaultWidth == 0;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void apply() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        if (this.mWidget.measured) {
            this.mDimension.resolve(this.mWidget.getWidth());
        }
        if (!this.mDimension.resolved) {
            this.mDimensionBehavior = this.mWidget.getHorizontalDimensionBehaviour();
            if (this.mDimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent2 = this.mWidget.getParent()) != null && (parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
                    int width = (parent2.getWidth() - this.mWidget.mLeft.getMargin()) - this.mWidget.mRight.getMargin();
                    addTarget(this.start, parent2.mHorizontalRun.start, this.mWidget.mLeft.getMargin());
                    addTarget(this.end, parent2.mHorizontalRun.end, -this.mWidget.mRight.getMargin());
                    this.mDimension.resolve(width);
                    return;
                }
                if (this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.mDimension.resolve(this.mWidget.getWidth());
                }
            }
        } else if (this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent = this.mWidget.getParent()) != null && (parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
            addTarget(this.start, parent.mHorizontalRun.start, this.mWidget.mLeft.getMargin());
            addTarget(this.end, parent.mHorizontalRun.end, -this.mWidget.mRight.getMargin());
            return;
        }
        if (this.mDimension.resolved && this.mWidget.measured) {
            if (this.mWidget.mListAnchors[0].mTarget != null && this.mWidget.mListAnchors[1].mTarget != null) {
                if (this.mWidget.isInHorizontalChain()) {
                    this.start.mMargin = this.mWidget.mListAnchors[0].getMargin();
                    this.end.mMargin = -this.mWidget.mListAnchors[1].getMargin();
                    return;
                }
                DependencyNode target = getTarget(this.mWidget.mListAnchors[0]);
                if (target != null) {
                    addTarget(this.start, target, this.mWidget.mListAnchors[0].getMargin());
                }
                DependencyNode target2 = getTarget(this.mWidget.mListAnchors[1]);
                if (target2 != null) {
                    addTarget(this.end, target2, -this.mWidget.mListAnchors[1].getMargin());
                }
                this.start.delegateToWidgetRun = true;
                this.end.delegateToWidgetRun = true;
                return;
            }
            if (this.mWidget.mListAnchors[0].mTarget != null) {
                DependencyNode target3 = getTarget(this.mWidget.mListAnchors[0]);
                if (target3 != null) {
                    addTarget(this.start, target3, this.mWidget.mListAnchors[0].getMargin());
                    addTarget(this.end, this.start, this.mDimension.value);
                    return;
                }
                return;
            }
            if (this.mWidget.mListAnchors[1].mTarget != null) {
                DependencyNode target4 = getTarget(this.mWidget.mListAnchors[1]);
                if (target4 != null) {
                    addTarget(this.end, target4, -this.mWidget.mListAnchors[1].getMargin());
                    addTarget(this.start, this.end, -this.mDimension.value);
                    return;
                }
                return;
            }
            if ((this.mWidget instanceof Helper) || this.mWidget.getParent() == null || this.mWidget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                return;
            }
            addTarget(this.start, this.mWidget.getParent().mHorizontalRun.start, this.mWidget.getX());
            addTarget(this.end, this.start, this.mDimension.value);
            return;
        }
        if (this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i = this.mWidget.mMatchConstraintDefaultWidth;
            if (i == 2) {
                ConstraintWidget parent3 = this.mWidget.getParent();
                if (parent3 != null) {
                    DimensionDependency dimensionDependency = parent3.mVerticalRun.mDimension;
                    this.mDimension.mTargets.add(dimensionDependency);
                    dimensionDependency.mDependencies.add(this.mDimension);
                    this.mDimension.delegateToWidgetRun = true;
                    this.mDimension.mDependencies.add(this.start);
                    this.mDimension.mDependencies.add(this.end);
                }
            } else if (i == 3) {
                if (this.mWidget.mMatchConstraintDefaultHeight == 3) {
                    this.start.updateDelegate = this;
                    this.end.updateDelegate = this;
                    this.mWidget.mVerticalRun.start.updateDelegate = this;
                    this.mWidget.mVerticalRun.end.updateDelegate = this;
                    this.mDimension.updateDelegate = this;
                    if (this.mWidget.isInVerticalChain()) {
                        this.mDimension.mTargets.add(this.mWidget.mVerticalRun.mDimension);
                        this.mWidget.mVerticalRun.mDimension.mDependencies.add(this.mDimension);
                        this.mWidget.mVerticalRun.mDimension.updateDelegate = this;
                        this.mDimension.mTargets.add(this.mWidget.mVerticalRun.start);
                        this.mDimension.mTargets.add(this.mWidget.mVerticalRun.end);
                        this.mWidget.mVerticalRun.start.mDependencies.add(this.mDimension);
                        this.mWidget.mVerticalRun.end.mDependencies.add(this.mDimension);
                    } else if (this.mWidget.isInHorizontalChain()) {
                        this.mWidget.mVerticalRun.mDimension.mTargets.add(this.mDimension);
                        this.mDimension.mDependencies.add(this.mWidget.mVerticalRun.mDimension);
                    } else {
                        this.mWidget.mVerticalRun.mDimension.mTargets.add(this.mDimension);
                    }
                } else {
                    DimensionDependency dimensionDependency2 = this.mWidget.mVerticalRun.mDimension;
                    this.mDimension.mTargets.add(dimensionDependency2);
                    dimensionDependency2.mDependencies.add(this.mDimension);
                    this.mWidget.mVerticalRun.start.mDependencies.add(this.mDimension);
                    this.mWidget.mVerticalRun.end.mDependencies.add(this.mDimension);
                    this.mDimension.delegateToWidgetRun = true;
                    this.mDimension.mDependencies.add(this.start);
                    this.mDimension.mDependencies.add(this.end);
                    this.start.mTargets.add(this.mDimension);
                    this.end.mTargets.add(this.mDimension);
                }
            }
        }
        if (this.mWidget.mListAnchors[0].mTarget != null && this.mWidget.mListAnchors[1].mTarget != null) {
            if (this.mWidget.isInHorizontalChain()) {
                this.start.mMargin = this.mWidget.mListAnchors[0].getMargin();
                this.end.mMargin = -this.mWidget.mListAnchors[1].getMargin();
                return;
            }
            DependencyNode target5 = getTarget(this.mWidget.mListAnchors[0]);
            DependencyNode target6 = getTarget(this.mWidget.mListAnchors[1]);
            if (target5 != null) {
                target5.addDependency(this);
            }
            if (target6 != null) {
                target6.addDependency(this);
            }
            this.mRunType = WidgetRun.RunType.CENTER;
            return;
        }
        if (this.mWidget.mListAnchors[0].mTarget != null) {
            DependencyNode target7 = getTarget(this.mWidget.mListAnchors[0]);
            if (target7 != null) {
                addTarget(this.start, target7, this.mWidget.mListAnchors[0].getMargin());
                addTarget(this.end, this.start, 1, this.mDimension);
                return;
            }
            return;
        }
        if (this.mWidget.mListAnchors[1].mTarget != null) {
            DependencyNode target8 = getTarget(this.mWidget.mListAnchors[1]);
            if (target8 != null) {
                addTarget(this.end, target8, -this.mWidget.mListAnchors[1].getMargin());
                addTarget(this.start, this.end, -1, this.mDimension);
                return;
            }
            return;
        }
        if ((this.mWidget instanceof Helper) || this.mWidget.getParent() == null) {
            return;
        }
        addTarget(this.start, this.mWidget.getParent().mHorizontalRun.start, this.mWidget.getX());
        addTarget(this.end, this.start, 1, this.mDimension);
    }

    private void computeInsetRatio(int[] iArr, int i, int i2, int i3, int i4, float f, int i5) {
        int i6 = i2 - i;
        int i7 = i4 - i3;
        if (i5 != -1) {
            if (i5 == 0) {
                iArr[0] = (int) ((i7 * f) + 0.5f);
                iArr[1] = i7;
                return;
            } else {
                if (i5 != 1) {
                    return;
                }
                iArr[0] = i6;
                iArr[1] = (int) ((i6 * f) + 0.5f);
                return;
            }
        }
        int i8 = (int) ((i7 * f) + 0.5f);
        int i9 = (int) ((i6 / f) + 0.5f);
        if (i8 <= i6) {
            iArr[0] = i8;
            iArr[1] = i7;
        } else if (i9 <= i7) {
            iArr[0] = i6;
            iArr[1] = i9;
        }
    }

    /* JADX INFO: renamed from: androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType;

        static {
            int[] iArr = new int[WidgetRun.RunType.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType = iArr;
            try {
                iArr[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:123:0x02f4  */
    /* JADX WARN: Code duplicated, block: B:125:0x0303  */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        int limitedDimension;
        int i;
        int limitedDimension2;
        float f;
        float dimensionRatio;
        float dimensionRatio2;
        int i2;
        int i3 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()];
        if (i3 == 1) {
            updateRunStart(dependency);
        } else if (i3 == 2) {
            updateRunEnd(dependency);
        } else if (i3 == 3) {
            updateRunCenter(dependency, this.mWidget.mLeft, this.mWidget.mRight, 0);
            return;
        }
        if (!this.mDimension.resolved && this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i4 = this.mWidget.mMatchConstraintDefaultWidth;
            if (i4 == 2) {
                ConstraintWidget parent = this.mWidget.getParent();
                if (parent != null && parent.mHorizontalRun.mDimension.resolved) {
                    this.mDimension.resolve((int) ((parent.mHorizontalRun.mDimension.value * this.mWidget.mMatchConstraintPercentWidth) + 0.5f));
                }
            } else if (i4 == 3) {
                if (this.mWidget.mMatchConstraintDefaultHeight == 0 || this.mWidget.mMatchConstraintDefaultHeight == 3) {
                    DependencyNode dependencyNode = this.mWidget.mVerticalRun.start;
                    DependencyNode dependencyNode2 = this.mWidget.mVerticalRun.end;
                    boolean z = this.mWidget.mLeft.mTarget != null;
                    boolean z2 = this.mWidget.mTop.mTarget != null;
                    boolean z3 = this.mWidget.mRight.mTarget != null;
                    boolean z4 = this.mWidget.mBottom.mTarget != null;
                    int dimensionRatioSide = this.mWidget.getDimensionRatioSide();
                    if (z && z2 && z3 && z4) {
                        float dimensionRatio3 = this.mWidget.getDimensionRatio();
                        if (dependencyNode.resolved && dependencyNode2.resolved) {
                            if (this.start.readyToSolve && this.end.readyToSolve) {
                                computeInsetRatio(sTempDimensions, this.start.mMargin + this.start.mTargets.get(0).value, this.end.mTargets.get(0).value - this.end.mMargin, dependencyNode.mMargin + dependencyNode.value, dependencyNode2.value - dependencyNode2.mMargin, dimensionRatio3, dimensionRatioSide);
                                this.mDimension.resolve(sTempDimensions[0]);
                                this.mWidget.mVerticalRun.mDimension.resolve(sTempDimensions[1]);
                                return;
                            }
                            return;
                        }
                        if (this.start.resolved && this.end.resolved) {
                            if (!dependencyNode.readyToSolve || !dependencyNode2.readyToSolve) {
                                return;
                            }
                            computeInsetRatio(sTempDimensions, this.start.mMargin + this.start.value, this.end.value - this.end.mMargin, dependencyNode.mMargin + dependencyNode.mTargets.get(0).value, dependencyNode2.mTargets.get(0).value - dependencyNode2.mMargin, dimensionRatio3, dimensionRatioSide);
                            this.mDimension.resolve(sTempDimensions[0]);
                            this.mWidget.mVerticalRun.mDimension.resolve(sTempDimensions[1]);
                        }
                        if (!this.start.readyToSolve || !this.end.readyToSolve || !dependencyNode.readyToSolve || !dependencyNode2.readyToSolve) {
                            return;
                        }
                        computeInsetRatio(sTempDimensions, this.start.mMargin + this.start.mTargets.get(0).value, this.end.mTargets.get(0).value - this.end.mMargin, dependencyNode.mMargin + dependencyNode.mTargets.get(0).value, dependencyNode2.mTargets.get(0).value - dependencyNode2.mMargin, dimensionRatio3, dimensionRatioSide);
                        this.mDimension.resolve(sTempDimensions[0]);
                        this.mWidget.mVerticalRun.mDimension.resolve(sTempDimensions[1]);
                    } else if (z && z3) {
                        if (!this.start.readyToSolve || !this.end.readyToSolve) {
                            return;
                        }
                        float dimensionRatio4 = this.mWidget.getDimensionRatio();
                        int i5 = this.start.mTargets.get(0).value + this.start.mMargin;
                        int i6 = this.end.mTargets.get(0).value - this.end.mMargin;
                        if (dimensionRatioSide == -1 || dimensionRatioSide == 0) {
                            int limitedDimension3 = getLimitedDimension(i6 - i5, 0);
                            int i7 = (int) ((limitedDimension3 * dimensionRatio4) + 0.5f);
                            int limitedDimension4 = getLimitedDimension(i7, 1);
                            if (i7 != limitedDimension4) {
                                limitedDimension3 = (int) ((limitedDimension4 / dimensionRatio4) + 0.5f);
                            }
                            this.mDimension.resolve(limitedDimension3);
                            this.mWidget.mVerticalRun.mDimension.resolve(limitedDimension4);
                        } else if (dimensionRatioSide == 1) {
                            int limitedDimension5 = getLimitedDimension(i6 - i5, 0);
                            int i8 = (int) ((limitedDimension5 / dimensionRatio4) + 0.5f);
                            int limitedDimension6 = getLimitedDimension(i8, 1);
                            if (i8 != limitedDimension6) {
                                limitedDimension5 = (int) ((limitedDimension6 * dimensionRatio4) + 0.5f);
                            }
                            this.mDimension.resolve(limitedDimension5);
                            this.mWidget.mVerticalRun.mDimension.resolve(limitedDimension6);
                        }
                    } else if (z2 && z4) {
                        if (!dependencyNode.readyToSolve || !dependencyNode2.readyToSolve) {
                            return;
                        }
                        float dimensionRatio5 = this.mWidget.getDimensionRatio();
                        int i9 = dependencyNode.mTargets.get(0).value + dependencyNode.mMargin;
                        int i10 = dependencyNode2.mTargets.get(0).value - dependencyNode2.mMargin;
                        if (dimensionRatioSide == -1) {
                            limitedDimension = getLimitedDimension(i10 - i9, 1);
                            i = (int) ((limitedDimension / dimensionRatio5) + 0.5f);
                            limitedDimension2 = getLimitedDimension(i, 0);
                            if (i != limitedDimension2) {
                                limitedDimension = (int) ((limitedDimension2 * dimensionRatio5) + 0.5f);
                            }
                            this.mDimension.resolve(limitedDimension2);
                            this.mWidget.mVerticalRun.mDimension.resolve(limitedDimension);
                        } else if (dimensionRatioSide == 0) {
                            int limitedDimension7 = getLimitedDimension(i10 - i9, 1);
                            int i11 = (int) ((limitedDimension7 * dimensionRatio5) + 0.5f);
                            int limitedDimension8 = getLimitedDimension(i11, 0);
                            if (i11 != limitedDimension8) {
                                limitedDimension7 = (int) ((limitedDimension8 / dimensionRatio5) + 0.5f);
                            }
                            this.mDimension.resolve(limitedDimension8);
                            this.mWidget.mVerticalRun.mDimension.resolve(limitedDimension7);
                        } else if (dimensionRatioSide == 1) {
                            limitedDimension = getLimitedDimension(i10 - i9, 1);
                            i = (int) ((limitedDimension / dimensionRatio5) + 0.5f);
                            limitedDimension2 = getLimitedDimension(i, 0);
                            if (i != limitedDimension2) {
                                limitedDimension = (int) ((limitedDimension2 * dimensionRatio5) + 0.5f);
                            }
                            this.mDimension.resolve(limitedDimension2);
                            this.mWidget.mVerticalRun.mDimension.resolve(limitedDimension);
                        }
                    }
                } else {
                    int dimensionRatioSide2 = this.mWidget.getDimensionRatioSide();
                    if (dimensionRatioSide2 == -1) {
                        f = this.mWidget.mVerticalRun.mDimension.value;
                        dimensionRatio = this.mWidget.getDimensionRatio();
                    } else {
                        if (dimensionRatioSide2 == 0) {
                            dimensionRatio2 = this.mWidget.mVerticalRun.mDimension.value / this.mWidget.getDimensionRatio();
                            i2 = (int) (dimensionRatio2 + 0.5f);
                        } else if (dimensionRatioSide2 != 1) {
                            i2 = 0;
                        } else {
                            f = this.mWidget.mVerticalRun.mDimension.value;
                            dimensionRatio = this.mWidget.getDimensionRatio();
                        }
                        this.mDimension.resolve(i2);
                    }
                    dimensionRatio2 = f * dimensionRatio;
                    i2 = (int) (dimensionRatio2 + 0.5f);
                    this.mDimension.resolve(i2);
                }
            }
        }
        if (this.start.readyToSolve && this.end.readyToSolve) {
            if (this.start.resolved && this.end.resolved && this.mDimension.resolved) {
                return;
            }
            if (!this.mDimension.resolved && this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.mWidget.mMatchConstraintDefaultWidth == 0 && !this.mWidget.isInHorizontalChain()) {
                DependencyNode dependencyNode3 = this.start.mTargets.get(0);
                DependencyNode dependencyNode4 = this.end.mTargets.get(0);
                int i12 = dependencyNode3.value + this.start.mMargin;
                int i13 = dependencyNode4.value + this.end.mMargin;
                this.start.resolve(i12);
                this.end.resolve(i13);
                this.mDimension.resolve(i13 - i12);
                return;
            }
            if (!this.mDimension.resolved && this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.mTargets.size() > 0 && this.end.mTargets.size() > 0) {
                int iMin = Math.min((this.end.mTargets.get(0).value + this.end.mMargin) - (this.start.mTargets.get(0).value + this.start.mMargin), this.mDimension.wrapValue);
                int i14 = this.mWidget.mMatchConstraintMaxWidth;
                int iMax = Math.max(this.mWidget.mMatchConstraintMinWidth, iMin);
                if (i14 > 0) {
                    iMax = Math.min(i14, iMax);
                }
                this.mDimension.resolve(iMax);
            }
            if (this.mDimension.resolved) {
                DependencyNode dependencyNode5 = this.start.mTargets.get(0);
                DependencyNode dependencyNode6 = this.end.mTargets.get(0);
                int i15 = dependencyNode5.value + this.start.mMargin;
                int i16 = dependencyNode6.value + this.end.mMargin;
                float horizontalBiasPercent = this.mWidget.getHorizontalBiasPercent();
                if (dependencyNode5 == dependencyNode6) {
                    i15 = dependencyNode5.value;
                    i16 = dependencyNode6.value;
                    horizontalBiasPercent = 0.5f;
                }
                this.start.resolve((int) (i15 + 0.5f + (((i16 - i15) - this.mDimension.value) * horizontalBiasPercent)));
                this.end.resolve(this.start.value + this.mDimension.value);
            }
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        if (this.start.resolved) {
            this.mWidget.setX(this.start.value);
        }
    }
}
