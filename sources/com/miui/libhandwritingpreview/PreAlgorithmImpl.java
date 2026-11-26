package com.miui.libhandwritingpreview;

import android.graphics.Matrix;
import android.graphics.Path;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.engine.core.algorithm.IAlgorithmInterface;
import xc.engine.core.algorithm.TouchPoint;
import xc.engine.core.algorithm.estimate.ParamsEstimate;
import xc.engine.core.algorithm.outline.OutlineHelper;
import xc.engine.core.algorithm.recognize.ParamsRecognizeShape;
import xc.engine.data.CsPoint;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.ICurve;

/* JADX INFO: loaded from: classes3.dex */
public class PreAlgorithmImpl implements IAlgorithmInterface {
    private final OutlineHelper mOutlineHelper = new OutlineHelper();
    private final PenPreEngine mPenEngine;

    @Override // xc.engine.core.algorithm.IAlgorithmInterface
    public void enableEstimate(boolean z) {
    }

    @Override // xc.engine.core.algorithm.IAlgorithmInterface
    public void enableShapeRecognize(boolean z) {
    }

    @Override // xc.engine.core.algorithm.IAlgorithmInterface
    public void setEstimateParams(ParamsEstimate paramsEstimate) {
    }

    @Override // xc.engine.core.algorithm.IAlgorithmInterface
    public void setShapeRecognizeParams(ParamsRecognizeShape paramsRecognizeShape) {
    }

    public PreAlgorithmImpl(PenPreEngine penPreEngine) {
        this.mPenEngine = penPreEngine;
    }

    public OutlineHelper getOutlineHelper() {
        return this.mOutlineHelper;
    }

    @Override // xc.engine.core.algorithm.IAlgorithmInterface
    public Path countDataPath(List<? extends ICurve> list) {
        if (this.mPenEngine.isRelease()) {
            return null;
        }
        ArrayList<DataCurve> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<? extends ICurve> it = list.iterator();
        while (it.hasNext()) {
            try {
                DataCurve dataCurveClone = ((DataCurve) it.next()).clone();
                arrayList.add(dataCurveClone);
                if (dataCurveClone.size(dataCurveClone.mOriginPoints) == 0) {
                    arrayList2.add(dataCurveClone);
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        this.mPenEngine.mCurveDataManager.batchExecCurveGenTask(arrayList2);
        ArrayList<TouchPoint> arrayList3 = new ArrayList<>();
        float fMax = 0.0f;
        for (DataCurve dataCurve : arrayList) {
            fMax = Math.max(fMax, dataCurve.getCurvePenWidth());
            ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mOriginPoints);
            int size = curvePointList.size();
            for (int i = 0; i < size; i++) {
                CsPoint csPoint = new CsPoint(curvePointList.get(i));
                if (csPoint.getDataState() == 1) {
                    arrayList3.add(new TouchPoint(csPoint.getX(), csPoint.getY()));
                }
            }
        }
        return this.mOutlineHelper.extractHighlight(arrayList3, fMax * 1.5f);
    }

    @Override // xc.engine.core.algorithm.IAlgorithmInterface
    public Path countDataPaths(List<? extends ICurve> list, float f) {
        if (this.mPenEngine.isRelease()) {
            return null;
        }
        ArrayList<TouchPoint> arrayList = new ArrayList<>();
        Iterator<? extends ICurve> it = list.iterator();
        float fMax = 0.0f;
        while (it.hasNext()) {
            DataCurve dataCurve = (DataCurve) it.next();
            fMax = Math.max(fMax, dataCurve.getCurvePenWidth());
            ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mOriginPoints);
            int size = curvePointList.size();
            for (int i = 0; i < size; i++) {
                CsPoint csPoint = new CsPoint(curvePointList.get(i));
                if (csPoint.getDataState() == 1) {
                    arrayList.add(new TouchPoint(csPoint.getX() * f, csPoint.getY() * f));
                }
            }
        }
        Path pathExtractHighlight = this.mOutlineHelper.extractHighlight(arrayList, fMax);
        Matrix matrix = new Matrix();
        float f2 = 1.0f / f;
        matrix.setScale(f2, f2);
        pathExtractHighlight.transform(matrix);
        return pathExtractHighlight;
    }
}
