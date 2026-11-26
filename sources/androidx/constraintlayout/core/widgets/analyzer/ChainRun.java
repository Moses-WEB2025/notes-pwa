package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class ChainRun extends WidgetRun {
    private int mChainStyle;
    ArrayList<WidgetRun> mWidgets;

    public ChainRun(ConstraintWidget constraintWidget, int i) {
        super(constraintWidget);
        this.mWidgets = new ArrayList<>();
        this.orientation = i;
        build();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ChainRun ");
        sb.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        for (WidgetRun widgetRun : this.mWidgets) {
            sb.append("<");
            sb.append(widgetRun);
            sb.append("> ");
        }
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    boolean supportsWrapComputation() {
        int size = this.mWidgets.size();
        for (int i = 0; i < size; i++) {
            if (!this.mWidgets.get(i).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public long getWrapDimension() {
        int size = this.mWidgets.size();
        long wrapDimension = 0;
        for (int i = 0; i < size; i++) {
            WidgetRun widgetRun = this.mWidgets.get(i);
            wrapDimension = wrapDimension + ((long) widgetRun.start.mMargin) + widgetRun.getWrapDimension() + ((long) widgetRun.end.mMargin);
        }
        return wrapDimension;
    }

    private void build() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2 = this.mWidget;
        ConstraintWidget previousChainMember = constraintWidget2.getPreviousChainMember(this.orientation);
        while (true) {
            ConstraintWidget constraintWidget3 = previousChainMember;
            constraintWidget = constraintWidget2;
            constraintWidget2 = constraintWidget3;
            if (constraintWidget2 == null) {
                break;
            } else {
                previousChainMember = constraintWidget2.getPreviousChainMember(this.orientation);
            }
        }
        this.mWidget = constraintWidget;
        this.mWidgets.add(constraintWidget.getRun(this.orientation));
        ConstraintWidget nextChainMember = constraintWidget.getNextChainMember(this.orientation);
        while (nextChainMember != null) {
            this.mWidgets.add(nextChainMember.getRun(this.orientation));
            nextChainMember = nextChainMember.getNextChainMember(this.orientation);
        }
        for (WidgetRun widgetRun : this.mWidgets) {
            if (this.orientation == 0) {
                widgetRun.mWidget.horizontalChainRun = this;
            } else if (this.orientation == 1) {
                widgetRun.mWidget.verticalChainRun = this;
            }
        }
        if (this.orientation == 0 && ((ConstraintWidgetContainer) this.mWidget.getParent()).isRtl() && this.mWidgets.size() > 1) {
            ArrayList<WidgetRun> arrayList = this.mWidgets;
            this.mWidget = arrayList.get(arrayList.size() - 1).mWidget;
        }
        this.mChainStyle = this.orientation == 0 ? this.mWidget.getHorizontalChainStyle() : this.mWidget.getVerticalChainStyle();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void clear() {
        this.mRunGroup = null;
        Iterator<WidgetRun> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void reset() {
        this.start.resolved = false;
        this.end.resolved = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        int i;
        int i2;
        float f;
        float f2;
        int i3;
        int i4;
        int i5;
        boolean z;
        int i6;
        float f3;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        if (this.start.resolved && this.end.resolved) {
            ConstraintWidget parent = this.mWidget.getParent();
            boolean zIsRtl = parent instanceof ConstraintWidgetContainer ? ((ConstraintWidgetContainer) parent).isRtl() : false;
            int i12 = this.end.value - this.start.value;
            int size = this.mWidgets.size();
            int i13 = 0;
            while (true) {
                i = -1;
                i2 = 8;
                if (i13 >= size) {
                    i13 = -1;
                    break;
                } else if (this.mWidgets.get(i13).mWidget.getVisibility() != 8) {
                    break;
                } else {
                    i13++;
                }
            }
            int i14 = size - 1;
            for (int i15 = i14; i15 >= 0; i15--) {
                if (this.mWidgets.get(i15).mWidget.getVisibility() != 8) {
                    i = i15;
                    break;
                }
            }
            int i16 = 0;
            while (true) {
                if (i16 >= 2) {
                    f = 0.0f;
                    f2 = 0.0f;
                    i3 = 0;
                    i4 = 0;
                    i5 = 0;
                    break;
                }
                int i17 = 0;
                i4 = 0;
                i5 = 0;
                int i18 = 0;
                f2 = 0.0f;
                while (i17 < size) {
                    WidgetRun widgetRun = this.mWidgets.get(i17);
                    if (widgetRun.mWidget.getVisibility() != i2) {
                        i18++;
                        if (i17 > 0 && i17 >= i13) {
                            i4 += widgetRun.start.mMargin;
                        }
                        int i19 = widgetRun.mDimension.value;
                        boolean z2 = widgetRun.mDimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                        if (z2) {
                            if (this.orientation == 0 && !widgetRun.mWidget.mHorizontalRun.mDimension.resolved) {
                                return;
                            }
                            if (this.orientation == 1 && !widgetRun.mWidget.mVerticalRun.mDimension.resolved) {
                                return;
                            }
                        } else {
                            if (widgetRun.matchConstraintsType == 1 && i16 == 0) {
                                i19 = widgetRun.mDimension.wrapValue;
                                i5++;
                            } else if (widgetRun.mDimension.resolved) {
                            }
                            z2 = true;
                        }
                        if (z2) {
                            i4 += i19;
                        } else {
                            i5++;
                            float f4 = widgetRun.mWidget.mWeight[this.orientation];
                            if (f4 >= 0.0f) {
                                f2 += f4;
                            }
                        }
                        if (i17 < i14 && i17 < i) {
                            i4 += -widgetRun.end.mMargin;
                        }
                    }
                    i17++;
                    i2 = 8;
                }
                f = 0.0f;
                if (i4 < i12 || i5 == 0) {
                    i3 = i18;
                    break;
                } else {
                    i16++;
                    i2 = 8;
                }
            }
            int i20 = this.start.value;
            if (zIsRtl) {
                i20 = this.end.value;
            }
            float f5 = 0.5f;
            if (i4 > i12) {
                i20 = zIsRtl ? i20 + ((int) (((i4 - i12) / 2.0f) + 0.5f)) : i20 - ((int) (((i4 - i12) / 2.0f) + 0.5f));
            }
            if (i5 > 0) {
                float f6 = i12 - i4;
                int i21 = (int) ((f6 / i5) + 0.5f);
                int i22 = 0;
                int i23 = 0;
                while (i22 < size) {
                    float f7 = f5;
                    WidgetRun widgetRun2 = this.mWidgets.get(i22);
                    boolean z3 = zIsRtl;
                    if (widgetRun2.mWidget.getVisibility() == 8 || widgetRun2.mDimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widgetRun2.mDimension.resolved) {
                        i20 = i20;
                    } else {
                        int i24 = f2 > f ? (int) (((widgetRun2.mWidget.mWeight[this.orientation] * f6) / f2) + f7) : i21;
                        if (this.orientation == 0) {
                            i10 = widgetRun2.mWidget.mMatchConstraintMaxWidth;
                            i11 = widgetRun2.mWidget.mMatchConstraintMinWidth;
                        } else {
                            i10 = widgetRun2.mWidget.mMatchConstraintMaxHeight;
                            i11 = widgetRun2.mWidget.mMatchConstraintMinHeight;
                        }
                        int iMax = Math.max(i11, widgetRun2.matchConstraintsType == 1 ? Math.min(i24, widgetRun2.mDimension.wrapValue) : i24);
                        if (i10 > 0) {
                            iMax = Math.min(i10, iMax);
                        }
                        if (iMax != i24) {
                            i23++;
                            i24 = iMax;
                        }
                        widgetRun2.mDimension.resolve(i24);
                    }
                    i22++;
                    f5 = f7;
                    zIsRtl = z3;
                    i20 = i20;
                    f6 = f6;
                    i21 = i21;
                }
                z = zIsRtl;
                i6 = i20;
                f3 = f5;
                if (i23 > 0) {
                    i5 -= i23;
                    int i25 = 0;
                    for (int i26 = 0; i26 < size; i26++) {
                        WidgetRun widgetRun3 = this.mWidgets.get(i26);
                        if (widgetRun3.mWidget.getVisibility() != 8) {
                            if (i26 > 0 && i26 >= i13) {
                                i25 += widgetRun3.start.mMargin;
                            }
                            i25 += widgetRun3.mDimension.value;
                            if (i26 < i14 && i26 < i) {
                                i25 += -widgetRun3.end.mMargin;
                            }
                        }
                    }
                    i4 = i25;
                }
                i8 = 2;
                if (this.mChainStyle == 2 && i23 == 0) {
                    i7 = 0;
                    this.mChainStyle = 0;
                } else {
                    i7 = 0;
                }
            } else {
                z = zIsRtl;
                i6 = i20;
                f3 = 0.5f;
                i7 = 0;
                i8 = 2;
            }
            if (i4 > i12) {
                this.mChainStyle = i8;
            }
            if (i3 > 0 && i5 == 0 && i13 == i) {
                this.mChainStyle = i8;
            }
            int i27 = this.mChainStyle;
            if (i27 == 1) {
                if (i3 > 1) {
                    i9 = (i12 - i4) / (i3 - 1);
                } else {
                    i9 = i3 == 1 ? (i12 - i4) / 2 : i7;
                }
                if (i5 > 0) {
                    i9 = i7;
                }
                int i28 = i6;
                for (int i29 = i7; i29 < size; i29++) {
                    WidgetRun widgetRun4 = this.mWidgets.get(z ? size - (i29 + 1) : i29);
                    if (widgetRun4.mWidget.getVisibility() == 8) {
                        widgetRun4.start.resolve(i28);
                        widgetRun4.end.resolve(i28);
                    } else {
                        if (i29 > 0) {
                            i28 = z ? i28 - i9 : i28 + i9;
                        }
                        if (i29 > 0 && i29 >= i13) {
                            if (z) {
                                i28 -= widgetRun4.start.mMargin;
                            } else {
                                i28 += widgetRun4.start.mMargin;
                            }
                        }
                        if (z) {
                            widgetRun4.end.resolve(i28);
                        } else {
                            widgetRun4.start.resolve(i28);
                        }
                        int i30 = widgetRun4.mDimension.value;
                        if (widgetRun4.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun4.matchConstraintsType == 1) {
                            i30 = widgetRun4.mDimension.wrapValue;
                        }
                        i28 = z ? i28 - i30 : i28 + i30;
                        if (z) {
                            widgetRun4.start.resolve(i28);
                        } else {
                            widgetRun4.end.resolve(i28);
                        }
                        widgetRun4.mResolved = true;
                        if (i29 < i14 && i29 < i) {
                            if (z) {
                                i28 -= -widgetRun4.end.mMargin;
                            } else {
                                i28 += -widgetRun4.end.mMargin;
                            }
                        }
                    }
                }
                return;
            }
            if (i27 == 0) {
                int i31 = (i12 - i4) / (i3 + 1);
                if (i5 > 0) {
                    i31 = i7;
                }
                int i32 = i6;
                for (int i33 = i7; i33 < size; i33++) {
                    WidgetRun widgetRun5 = this.mWidgets.get(z ? size - (i33 + 1) : i33);
                    if (widgetRun5.mWidget.getVisibility() == 8) {
                        widgetRun5.start.resolve(i32);
                        widgetRun5.end.resolve(i32);
                    } else {
                        int i34 = z ? i32 - i31 : i32 + i31;
                        if (i33 > 0 && i33 >= i13) {
                            if (z) {
                                i34 -= widgetRun5.start.mMargin;
                            } else {
                                i34 += widgetRun5.start.mMargin;
                            }
                        }
                        if (z) {
                            widgetRun5.end.resolve(i34);
                        } else {
                            widgetRun5.start.resolve(i34);
                        }
                        int iMin = widgetRun5.mDimension.value;
                        if (widgetRun5.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun5.matchConstraintsType == 1) {
                            iMin = Math.min(iMin, widgetRun5.mDimension.wrapValue);
                        }
                        i32 = z ? i34 - iMin : i34 + iMin;
                        if (z) {
                            widgetRun5.start.resolve(i32);
                        } else {
                            widgetRun5.end.resolve(i32);
                        }
                        if (i33 < i14 && i33 < i) {
                            if (z) {
                                i32 -= -widgetRun5.end.mMargin;
                            } else {
                                i32 += -widgetRun5.end.mMargin;
                            }
                        }
                    }
                }
                return;
            }
            if (i27 == 2) {
                float horizontalBiasPercent = this.orientation == 0 ? this.mWidget.getHorizontalBiasPercent() : this.mWidget.getVerticalBiasPercent();
                if (z) {
                    horizontalBiasPercent = 1.0f - horizontalBiasPercent;
                }
                int i35 = (int) (((i12 - i4) * horizontalBiasPercent) + f3);
                if (i35 < 0 || i5 > 0) {
                    i35 = i7;
                }
                int i36 = z ? i6 - i35 : i6 + i35;
                for (int i37 = i7; i37 < size; i37++) {
                    WidgetRun widgetRun6 = this.mWidgets.get(z ? size - (i37 + 1) : i37);
                    if (widgetRun6.mWidget.getVisibility() == 8) {
                        widgetRun6.start.resolve(i36);
                        widgetRun6.end.resolve(i36);
                    } else {
                        if (i37 > 0 && i37 >= i13) {
                            if (z) {
                                i36 -= widgetRun6.start.mMargin;
                            } else {
                                i36 += widgetRun6.start.mMargin;
                            }
                        }
                        if (z) {
                            widgetRun6.end.resolve(i36);
                        } else {
                            widgetRun6.start.resolve(i36);
                        }
                        int i38 = widgetRun6.mDimension.value;
                        if (widgetRun6.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun6.matchConstraintsType == 1) {
                            i38 = widgetRun6.mDimension.wrapValue;
                        }
                        i36 = z ? i36 - i38 : i36 + i38;
                        if (z) {
                            widgetRun6.start.resolve(i36);
                        } else {
                            widgetRun6.end.resolve(i36);
                        }
                        if (i37 < i14 && i37 < i) {
                            if (z) {
                                i36 -= -widgetRun6.end.mMargin;
                            } else {
                                i36 += -widgetRun6.end.mMargin;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        for (int i = 0; i < this.mWidgets.size(); i++) {
            this.mWidgets.get(i).applyToWidget();
        }
    }

    private ConstraintWidget getFirstVisibleWidget() {
        for (int i = 0; i < this.mWidgets.size(); i++) {
            WidgetRun widgetRun = this.mWidgets.get(i);
            if (widgetRun.mWidget.getVisibility() != 8) {
                return widgetRun.mWidget;
            }
        }
        return null;
    }

    private ConstraintWidget getLastVisibleWidget() {
        for (int size = this.mWidgets.size() - 1; size >= 0; size--) {
            WidgetRun widgetRun = this.mWidgets.get(size);
            if (widgetRun.mWidget.getVisibility() != 8) {
                return widgetRun.mWidget;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void apply() {
        Iterator<WidgetRun> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            it.next().apply();
        }
        int size = this.mWidgets.size();
        if (size < 1) {
            return;
        }
        ConstraintWidget constraintWidget = this.mWidgets.get(0).mWidget;
        ConstraintWidget constraintWidget2 = this.mWidgets.get(size - 1).mWidget;
        if (this.orientation == 0) {
            ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
            ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
            DependencyNode target = getTarget(constraintAnchor, 0);
            int margin = constraintAnchor.getMargin();
            ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
            if (firstVisibleWidget != null) {
                margin = firstVisibleWidget.mLeft.getMargin();
            }
            if (target != null) {
                addTarget(this.start, target, margin);
            }
            DependencyNode target2 = getTarget(constraintAnchor2, 0);
            int margin2 = constraintAnchor2.getMargin();
            ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
            if (lastVisibleWidget != null) {
                margin2 = lastVisibleWidget.mRight.getMargin();
            }
            if (target2 != null) {
                addTarget(this.end, target2, -margin2);
            }
        } else {
            ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
            ConstraintAnchor constraintAnchor4 = constraintWidget2.mBottom;
            DependencyNode target3 = getTarget(constraintAnchor3, 1);
            int margin3 = constraintAnchor3.getMargin();
            ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
            if (firstVisibleWidget2 != null) {
                margin3 = firstVisibleWidget2.mTop.getMargin();
            }
            if (target3 != null) {
                addTarget(this.start, target3, margin3);
            }
            DependencyNode target4 = getTarget(constraintAnchor4, 1);
            int margin4 = constraintAnchor4.getMargin();
            ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
            if (lastVisibleWidget2 != null) {
                margin4 = lastVisibleWidget2.mBottom.getMargin();
            }
            if (target4 != null) {
                addTarget(this.end, target4, -margin4);
            }
        }
        this.start.updateDelegate = this;
        this.end.updateDelegate = this;
    }
}
