package xc.engine.math.iink.callback;

import android.graphics.RectF;
import java.util.List;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public interface MathSelectCallback {
    void onExitSelect();

    void onMathSelectRequired(RectF rectF, List<DataCurve> list);
}
