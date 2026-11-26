package xc.engine.core.graphics.curve;

import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class CurveDataDiff {
    public final boolean isSame(DataCurve dataCurve, DataCurve dataCurve2) {
        if (dataCurve == dataCurve2) {
            return true;
        }
        if (dataCurve2 == null) {
            return false;
        }
        return (dataCurve.mId == dataCurve2.mId || dataCurve.getPenType() == PenType.PENCIL || ((dataCurve.getPenType() == PenType.MARK && dataCurve.getAlpha() >= 255) || ((dataCurve.getPenType() == PenType.INK && dataCurve.getAlpha() >= 255) || (dataCurve.getPenType() == PenType.BALL && dataCurve.getAlpha() >= 255)))) && dataCurve.getCurveColor() == dataCurve2.getCurveColor() && dataCurve.getCurveColorWheel() == dataCurve2.getCurveColorWheel() && dataCurve.getAlpha() == dataCurve2.getAlpha() && dataCurve.getPenType() == dataCurve2.getPenType() && dataCurve.r == dataCurve2.r;
    }
}
