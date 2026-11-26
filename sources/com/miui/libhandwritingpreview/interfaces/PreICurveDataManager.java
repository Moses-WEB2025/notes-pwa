package com.miui.libhandwritingpreview.interfaces;

import android.graphics.Rect;
import android.graphics.RectF;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import com.miui.libhandwritingpreview.manager.PreCurveDataManager;
import java.util.List;
import java.util.function.Predicate;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.ICurveChangeListener;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PreICurveDataManager {
    public abstract void addCurveChangeListener(ICurveChangeListener iCurveChangeListener);

    public abstract RectF addDataAndGetRectF(List<DataCurve> list);

    public abstract RectF addDataToPool(DataCurve dataCurve);

    public abstract void batchExecCurveGenTask(List<DataCurve> list);

    public abstract void batchExecScalableCurveGenTask(List<DataCurve> list);

    public abstract RectF calculateDrawRect();

    public abstract void clear();

    public abstract boolean doesAreaHasCurve(RectF rectF, Predicate<DataCurve> predicate);

    public abstract void executeCurveGenTask(DataCurve dataCurve);

    public abstract void executeScalableCurveGenTask(DataCurve dataCurve);

    public abstract List<DataCurve> getCachedCurveList();

    public abstract List<DataCurve> getCurvesInArea(RectF rectF);

    public abstract List<DataCurve> getDesiredCurvesInArea(RectF rectF, Predicate<DataCurve> predicate);

    public abstract List<List<DataCurve>> getIntersectingCurves(RectF rectF);

    public abstract Rect limitRect(RectF rectF);

    public abstract void removeCurveChangeListener(ICurveChangeListener iCurveChangeListener);

    public abstract RectF removeDataAndGetRectF(List<DataCurve> list);

    public abstract RectF removeDataFromPool(DataCurve dataCurve);

    public static PreCurveDataManager createInstance(PenPreEngine penPreEngine) {
        return new PreCurveDataManager(penPreEngine);
    }
}
