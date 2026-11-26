package androidx.constraintlayout.core.widgets.analyzer;

/* JADX INFO: loaded from: classes.dex */
class BaselineDimensionDependency extends DimensionDependency {
    BaselineDimensionDependency(WidgetRun widgetRun) {
        super(widgetRun);
    }

    public void update(DependencyNode dependencyNode) {
        ((VerticalWidgetRun) this.mRun).baseline.mMargin = this.mRun.mWidget.getBaselineDistance();
        this.resolved = true;
    }
}
