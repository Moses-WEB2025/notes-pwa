package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
class RunGroup {
    public static final int BASELINE = 2;
    public static final int END = 1;
    public static final int START = 0;
    public static int index;
    int mDirection;
    WidgetRun mFirstRun;
    int mGroupIndex;
    WidgetRun mLastRun;
    public int position = 0;
    public boolean dual = false;
    ArrayList<WidgetRun> mRuns = new ArrayList<>();

    RunGroup(WidgetRun widgetRun, int i) {
        this.mFirstRun = null;
        this.mLastRun = null;
        int i2 = index;
        this.mGroupIndex = i2;
        index = i2 + 1;
        this.mFirstRun = widgetRun;
        this.mLastRun = widgetRun;
        this.mDirection = i;
    }

    public void add(WidgetRun widgetRun) {
        this.mRuns.add(widgetRun);
        this.mLastRun = widgetRun;
    }

    private long traverseStart(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.mRun;
        if (widgetRun instanceof HelperReferences) {
            return j;
        }
        int size = dependencyNode.mDependencies.size();
        long jMax = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = dependencyNode.mDependencies.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.mRun != widgetRun) {
                    jMax = Math.max(jMax, traverseStart(dependencyNode2, ((long) dependencyNode2.mMargin) + j));
                }
            }
        }
        if (dependencyNode != widgetRun.start) {
            return jMax;
        }
        long wrapDimension = j + widgetRun.getWrapDimension();
        return Math.max(Math.max(jMax, traverseStart(widgetRun.end, wrapDimension)), wrapDimension - ((long) widgetRun.end.mMargin));
    }

    private long traverseEnd(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.mRun;
        if (widgetRun instanceof HelperReferences) {
            return j;
        }
        int size = dependencyNode.mDependencies.size();
        long jMin = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = dependencyNode.mDependencies.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.mRun != widgetRun) {
                    jMin = Math.min(jMin, traverseEnd(dependencyNode2, ((long) dependencyNode2.mMargin) + j));
                }
            }
        }
        if (dependencyNode != widgetRun.end) {
            return jMin;
        }
        long wrapDimension = j - widgetRun.getWrapDimension();
        return Math.min(Math.min(jMin, traverseEnd(widgetRun.start, wrapDimension)), wrapDimension - ((long) widgetRun.start.mMargin));
    }

    public long computeWrapSize(ConstraintWidgetContainer constraintWidgetContainer, int i) {
        long wrapDimension;
        int i2;
        WidgetRun widgetRun = this.mFirstRun;
        if (widgetRun instanceof ChainRun) {
            if (((ChainRun) widgetRun).orientation != i) {
                return 0L;
            }
        } else if (i == 0) {
            if (!(widgetRun instanceof HorizontalWidgetRun)) {
                return 0L;
            }
        } else if (!(widgetRun instanceof VerticalWidgetRun)) {
            return 0L;
        }
        DependencyNode dependencyNode = i == 0 ? constraintWidgetContainer.mHorizontalRun.start : constraintWidgetContainer.mVerticalRun.start;
        DependencyNode dependencyNode2 = i == 0 ? constraintWidgetContainer.mHorizontalRun.end : constraintWidgetContainer.mVerticalRun.end;
        boolean zContains = this.mFirstRun.start.mTargets.contains(dependencyNode);
        boolean zContains2 = this.mFirstRun.end.mTargets.contains(dependencyNode2);
        long wrapDimension2 = this.mFirstRun.getWrapDimension();
        if (zContains && zContains2) {
            long jTraverseStart = traverseStart(this.mFirstRun.start, 0L);
            long jTraverseEnd = traverseEnd(this.mFirstRun.end, 0L);
            long j = jTraverseStart - wrapDimension2;
            if (j >= (-this.mFirstRun.end.mMargin)) {
                j += (long) this.mFirstRun.end.mMargin;
            }
            long j2 = ((-jTraverseEnd) - wrapDimension2) - ((long) this.mFirstRun.start.mMargin);
            if (j2 >= this.mFirstRun.start.mMargin) {
                j2 -= (long) this.mFirstRun.start.mMargin;
            }
            float biasPercent = this.mFirstRun.mWidget.getBiasPercent(i);
            float f = biasPercent > 0.0f ? (long) ((j2 / biasPercent) + (j / (1.0f - biasPercent))) : 0L;
            wrapDimension = ((long) this.mFirstRun.start.mMargin) + ((long) ((f * biasPercent) + 0.5f)) + wrapDimension2 + ((long) ((f * (1.0f - biasPercent)) + 0.5f));
            i2 = this.mFirstRun.end.mMargin;
        } else {
            if (zContains) {
                return Math.max(traverseStart(this.mFirstRun.start, this.mFirstRun.start.mMargin), ((long) this.mFirstRun.start.mMargin) + wrapDimension2);
            }
            if (zContains2) {
                return Math.max(-traverseEnd(this.mFirstRun.end, this.mFirstRun.end.mMargin), ((long) (-this.mFirstRun.end.mMargin)) + wrapDimension2);
            }
            wrapDimension = ((long) this.mFirstRun.start.mMargin) + this.mFirstRun.getWrapDimension();
            i2 = this.mFirstRun.end.mMargin;
        }
        return wrapDimension - ((long) i2);
    }

    private boolean defineTerminalWidget(WidgetRun widgetRun, int i) {
        if (!widgetRun.mWidget.isTerminalWidget[i]) {
            return false;
        }
        for (Dependency dependency : widgetRun.start.mDependencies) {
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode = (DependencyNode) dependency;
                if (dependencyNode.mRun != widgetRun && dependencyNode == dependencyNode.mRun.start) {
                    if (widgetRun instanceof ChainRun) {
                        Iterator<WidgetRun> it = ((ChainRun) widgetRun).mWidgets.iterator();
                        while (it.hasNext()) {
                            defineTerminalWidget(it.next(), i);
                        }
                    } else if (!(widgetRun instanceof HelperReferences)) {
                        widgetRun.mWidget.isTerminalWidget[i] = false;
                    }
                    defineTerminalWidget(dependencyNode.mRun, i);
                }
            }
        }
        for (Dependency dependency2 : widgetRun.end.mDependencies) {
            if (dependency2 instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency2;
                if (dependencyNode2.mRun != widgetRun && dependencyNode2 == dependencyNode2.mRun.start) {
                    if (widgetRun instanceof ChainRun) {
                        Iterator<WidgetRun> it2 = ((ChainRun) widgetRun).mWidgets.iterator();
                        while (it2.hasNext()) {
                            defineTerminalWidget(it2.next(), i);
                        }
                    } else if (!(widgetRun instanceof HelperReferences)) {
                        widgetRun.mWidget.isTerminalWidget[i] = false;
                    }
                    defineTerminalWidget(dependencyNode2.mRun, i);
                }
            }
        }
        return false;
    }

    public void defineTerminalWidgets(boolean z, boolean z2) {
        if (z) {
            WidgetRun widgetRun = this.mFirstRun;
            if (widgetRun instanceof HorizontalWidgetRun) {
                defineTerminalWidget(widgetRun, 0);
            }
        }
        if (z2) {
            WidgetRun widgetRun2 = this.mFirstRun;
            if (widgetRun2 instanceof VerticalWidgetRun) {
                defineTerminalWidget(widgetRun2, 1);
            }
        }
    }
}
