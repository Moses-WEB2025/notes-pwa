package xc.engine.operate.edit.select.selectobject;

import android.graphics.RectF;
import java.util.List;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public interface ISelectCurveObject extends ISelectObject {
    boolean doSelectedContour();

    List<DataCurve> getCurveList();

    RectF getCurveListRect();
}
