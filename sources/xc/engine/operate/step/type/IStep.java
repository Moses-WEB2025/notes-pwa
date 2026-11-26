package xc.engine.operate.step.type;

import android.graphics.RectF;
import android.util.SparseArray;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public interface IStep {
    int getPageIndex();

    RectF redo();

    void resetContext(PenEngine penEngine);

    void serialize();

    void setPageIndex(int i);

    RectF undo();

    void unserialize(SparseArray<DataCurve> sparseArray);
}
