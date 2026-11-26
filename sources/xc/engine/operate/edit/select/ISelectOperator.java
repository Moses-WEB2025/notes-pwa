package xc.engine.operate.edit.select;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.List;
import xc.engine.core.graphics.engine.systems.SelectSystem;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.ICurve;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.touch.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
public interface ISelectOperator {
    void cancelSelect(boolean z);

    void cancelSelectAction(float f, float f2);

    boolean checkSelectLimit(float f);

    void copyTempSelectData();

    void doCopy(boolean z);

    void doCopyAndPaste(boolean z);

    void doDelete(boolean z);

    void doDelete(boolean z, boolean z2);

    void doDeleteJustCurve();

    void doRectSelect(RectF rectF);

    void doRectSelect(RectF rectF, List<DataCurve> list, boolean z);

    void doRectSelect(RectF rectF, boolean z);

    void forceRedraw();

    float getCurScaleValue();

    List<ICurve> getCurrentAllSelectCurves();

    List<ICurve> getCurrentSelectCurves();

    float[] getEditPoint();

    Path getSelectPath();

    RectF getSelectRect();

    Bitmap getSelectScreenshot();

    Bitmap getSelectScreenshotBySize(int i);

    ShapeProp getSelectShapeProp();

    boolean isCloseShape();

    boolean isPointSelected();

    boolean isSelectReady();

    boolean isSelectedContour();

    boolean isSelectedObjectValid();

    boolean isShapeEditMode();

    boolean isShowEditPoint();

    boolean isSingleCurves();

    void onDraw(Canvas canvas);

    void setEnablePointSelect(boolean z);

    void setEnableSelect(boolean z);

    void setExtraSelectPoint(List<TouchPoint> list, List<DataCurve> list2, boolean z, boolean z2);

    void setExtraSelectPoint(List<TouchPoint> list, boolean z);

    void setExtraSelectPoint(List<TouchPoint> list, boolean z, boolean z2);

    void setMultiSelectRectF(SRectF sRectF);

    void setOnSelectStepChangedListener(onSelectStepChangedListener onselectstepchangedlistener);

    void setPaste(byte[] bArr, float f, float f2);

    void setRenderTaskListener(SelectSystem.RenderTaskListener renderTaskListener);

    void setSelectAction(int i);

    void setSelectLimit(boolean z, float f);

    void setSelectListenerI(OnSelectModelListener onSelectModelListener);

    void setSelectShapeAttr(ShapeEditAttr shapeEditAttr, boolean z);

    void setShapeEditType(int i, PointF pointF, PointF pointF2);
}
