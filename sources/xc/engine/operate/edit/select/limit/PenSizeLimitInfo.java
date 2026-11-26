package xc.engine.operate.edit.select.limit;

import android.graphics.RectF;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class PenSizeLimitInfo implements ILimitInfo {
    public List<DataCurve> mDataCurveList;
    public float mScale;
    public SelectLimit mSelectLimit;

    public PenSizeLimitInfo(PenEngine penEngine, RectF rectF) {
        this.mSelectLimit = penEngine.getEditOperator().getSelectLimit();
    }

    @Override // xc.engine.operate.edit.select.limit.ILimitInfo
    public boolean a(List<DataCurve> list, float f, int i) {
        SelectLimit.PenSize penSizeLimit;
        this.mDataCurveList = list;
        this.mScale = f;
        for (DataCurve dataCurve : list) {
            if (dataCurve.getType() == 28) {
                penSizeLimit = this.mSelectLimit.getPenSizeLimit(6);
            } else {
                penSizeLimit = this.mSelectLimit.getPenSizeLimit(dataCurve.getPenType().getValue());
            }
            if (penSizeLimit != null) {
                float penSize = dataCurve.getCurveProp().getPenSize() * f;
                if (penSize > penSizeLimit.getMaxSize() || penSize < penSizeLimit.getMinSize()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // xc.engine.operate.edit.select.limit.ILimitInfo
    public int getLimitType() {
        return this.mSelectLimit.getLimitMode();
    }

    @Override // xc.engine.operate.edit.select.limit.ILimitInfo
    public float getScaleValue() {
        SelectLimit.PenSize penSizeLimit;
        SelectLimit.PenSize penSizeLimit2;
        float fMin = this.mScale;
        int i = 0;
        if (fMin < 1.0f) {
            while (i < this.mDataCurveList.size()) {
                DataCurve dataCurve = this.mDataCurveList.get(i);
                if (dataCurve.getType() == 28) {
                    penSizeLimit2 = this.mSelectLimit.getPenSizeLimit(6);
                } else {
                    penSizeLimit2 = this.mSelectLimit.getPenSizeLimit(dataCurve.getPenType().getValue());
                }
                if (penSizeLimit2 != null && dataCurve.getCurvePenWidth() * this.mScale < penSizeLimit2.getMinSize()) {
                    fMin = Math.max(this.mScale, penSizeLimit2.getMinSize() / dataCurve.getCurvePenWidth());
                }
                i++;
            }
            return fMin;
        }
        if (fMin <= 1.0f) {
            return 1.0f;
        }
        while (i < this.mDataCurveList.size()) {
            DataCurve dataCurve2 = this.mDataCurveList.get(i);
            if (dataCurve2.getType() == 28) {
                penSizeLimit = this.mSelectLimit.getPenSizeLimit(6);
            } else {
                penSizeLimit = this.mSelectLimit.getPenSizeLimit(dataCurve2.getPenType().getValue());
            }
            if (penSizeLimit != null && dataCurve2.getCurvePenWidth() * this.mScale > penSizeLimit.getMaxSize()) {
                fMin = Math.min(this.mScale, penSizeLimit.getMaxSize() / dataCurve2.getCurvePenWidth());
            }
            i++;
        }
        return fMin;
    }
}
