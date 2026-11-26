package xc.engine.operate.step.type;

import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes6.dex */
public class StepChangePageSize implements IStep {
    private static final String TAG = "StepChangePageSize";
    private int pageIndex;
    public PenEngine penEngine;
    public final RectF rectF;
    public PageSizeConfig redoConfig;
    public PageSizeConfig undoConfig;

    @Override // xc.engine.operate.step.type.IStep
    public void serialize() {
    }

    @Override // xc.engine.operate.step.type.IStep
    public void unserialize(SparseArray<DataCurve> sparseArray) {
    }

    public StepChangePageSize(PenEngine penEngine, PageSizeConfig pageSizeConfig, PageSizeConfig pageSizeConfig2, int i) {
        Log.d(TAG, "StepChangePageSize  init");
        this.penEngine = penEngine;
        this.rectF = new RectF();
        this.undoConfig = new PageSizeConfig(pageSizeConfig2);
        this.redoConfig = new PageSizeConfig(pageSizeConfig);
        this.pageIndex = i;
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        return this.pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
        this.pageIndex = i;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF undo() {
        Log.d(TAG, "undo ");
        return new RectF(this.rectF);
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        Log.d(TAG, "redo ");
        return new RectF(this.rectF);
    }

    @Override // xc.engine.operate.step.type.IStep
    public void resetContext(PenEngine penEngine) {
        this.penEngine = penEngine;
    }
}
