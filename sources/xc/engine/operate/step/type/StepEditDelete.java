package xc.engine.operate.step.type;

import android.graphics.RectF;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class StepEditDelete implements IStep {
    private List<DataCurve> dataCurveList = new ArrayList();
    private PenEngine engine;
    private int pageIndex;

    @Override // xc.engine.operate.step.type.IStep
    public void serialize() {
    }

    public void setDataCurveList(List<DataCurve> list) {
    }

    @Override // xc.engine.operate.step.type.IStep
    public void unserialize(SparseArray<DataCurve> sparseArray) {
    }

    public StepEditDelete(PenEngine penEngine, int i) {
        this.engine = penEngine;
        this.pageIndex = i;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF undo() {
        return new RectF();
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        return new RectF();
    }

    @Override // xc.engine.operate.step.type.IStep
    public void resetContext(PenEngine penEngine) {
        this.engine = penEngine;
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        return this.pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
        this.pageIndex = i;
    }
}
