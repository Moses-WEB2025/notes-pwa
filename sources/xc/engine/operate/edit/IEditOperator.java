package xc.engine.operate.edit;

import android.graphics.PointF;
import java.util.List;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.edit.select.limit.SelectLimit;
import xc.engine.operate.touch.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
public interface IEditOperator {
    void clearI();

    void clearI(int i);

    void clearRunnable();

    void deletePage(int i);

    void doAutoPathSelect(DataCurve dataCurve);

    void doInsertShape(int i, float f, float f2, float f3, float f4, ShapeProp shapeProp);

    void doPaste(byte[] bArr, PointF pointF, boolean z);

    void doPathSelect(List<TouchPoint> list, boolean z, boolean z2);

    void doPointSelect(TouchPoint touchPoint);

    void enableSelectedContour(boolean z);

    boolean isChanged();

    boolean isClickOnShape(float f, float f2);

    boolean isPointLimit(int i, int i2);

    void setEditListener(IEditListener iEditListener);

    void setSelectScaleLimit(SelectLimit selectLimit);
}
