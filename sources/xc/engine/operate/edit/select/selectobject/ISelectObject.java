package xc.engine.operate.edit.select.selectobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import java.util.List;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public interface ISelectObject {
    void cancelAction();

    boolean checkCurveSelectLimit(float f);

    void doCopy();

    void doCopyAndPaste();

    void doDelete(boolean z);

    void doDeleteJustCurve();

    void doMove(float f, float f2);

    void doRotate(float f, float f2, float f3);

    void doScale(float f, float f2, float f3);

    void finishSelect();

    void finishSelect(boolean z);

    void forceRedraw();

    float getCurScaleValue();

    short getDataType();

    Point getSelectBitmapSize();

    List<DataCurve> getSelectData();

    int getSelectedHashCode();

    boolean isReady();

    void onDraw(Canvas canvas);

    boolean onScreenshots(Bitmap bitmap);

    void startAction(int i);

    void stopAction();
}
