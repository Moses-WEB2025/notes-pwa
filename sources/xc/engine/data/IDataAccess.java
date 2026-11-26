package xc.engine.data;

import android.graphics.Rect;
import android.graphics.RectF;
import java.util.List;
import xc.engine.data.pagedata.ICurve;
import xc.engine.data.pagedata.IDataSet;
import xc.engine.operate.step.type.IStep;

/* JADX INFO: loaded from: classes6.dex */
public interface IDataAccess {
    void clearEngineData();

    boolean deletePageAndHaveShiftIndex(int i);

    RectF getContentRange();

    PathInfo getCurrentPathInfo();

    List<ICurve> getCurveData(Rect rect);

    IDataSet getDataSet();

    List<IStep> getRedoList();

    long getTotalSavePoint(int i);

    List<IStep> getUndoList();

    RectF getVisibleRange();

    boolean hasValidCurVeData(int i);

    void loadEntFile(String str);

    void loadEntFile(String str, int i);

    boolean loadPathInfo(PathInfo pathInfo, List<IStep> list, List<IStep> list2);

    boolean loadPathInfo(PathInfo pathInfo, List<IStep> list, List<IStep> list2, int i);

    boolean saveEntFileSync(String str);

    boolean saveEntFileSync(String str, int i);

    void setDataBufferListener(IDataSwapListener iDataSwapListener);

    void setExtraTotalPoint(long j);

    void setIDataListener(IDataListener iDataListener);

    void setOnExtraDataListener(OnExtraDataListener onExtraDataListener);

    void shiftPage(int i, float f, float f2);

    boolean updateCurveDataPageIndex(int i, int i2, int i3);
}
