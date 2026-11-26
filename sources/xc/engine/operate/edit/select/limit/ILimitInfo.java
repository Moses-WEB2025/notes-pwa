package xc.engine.operate.edit.select.limit;

import java.util.List;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public interface ILimitInfo {
    boolean a(List<DataCurve> list, float f, int i);

    int getLimitType();

    float getScaleValue();
}
