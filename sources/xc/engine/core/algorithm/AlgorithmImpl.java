package xc.engine.core.algorithm;

import android.graphics.Matrix;
import android.graphics.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.core.algorithm.estimate.EstimatedGenerator;
import xc.engine.core.algorithm.estimate.ParamsEstimate;
import xc.engine.core.algorithm.outline.OutlineHelper;
import xc.engine.core.algorithm.recognize.ParamsRecognizeShape;
import xc.engine.core.algorithm.recognize.RecognizeEngine;
import xc.engine.data.CsPoint;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.ICurve;

/* JADX INFO: loaded from: classes6.dex */
public class AlgorithmImpl implements IAlgorithmInterface {
    private final PenEngine mPenEngine;
    private final RecognizeEngine mRecognizeEngine;
    private final ParamsEstimate mParamsEstimate = new ParamsEstimate();
    private final ParamsRecognizeShape mParamsRecognizeShape = new ParamsRecognizeShape();
    private boolean isShapeRecognizeEnable = false;
    private boolean isEnableEstimate = true;
    private final EstimatedGenerator mEstimatedGenerator = new EstimatedGenerator();
    private final OutlineHelper mOutlineHelper = new OutlineHelper();

    public AlgorithmImpl(PenEngine penEngine) {
        this.mPenEngine = penEngine;
        this.mRecognizeEngine = penEngine.getRecognizeEngine();
    }

    public EstimatedGenerator getEstimatedGenerator() {
        return this.mEstimatedGenerator;
    }

    public OutlineHelper getOutlineHelper() {
        return this.mOutlineHelper;
    }

    public RecognizeEngine getRecognizeEngine() {
        return this.mRecognizeEngine;
    }

    public boolean isEnableEstimate() {
        return this.isEnableEstimate;
    }

    @Override // xc.engine.core.algorithm.IAlgorithmInterface
    public void enableEstimate(boolean z) {
        this.isEnableEstimate = z;
    }

    @Override // xc.engine.core.algorithm.IAlgorithmInterface
    public void setEstimateParams(ParamsEstimate paramsEstimate) {
        this.mParamsEstimate.set(paramsEstimate);
    }

    public boolean isShapeRecognizeEnable() {
        return this.isShapeRecognizeEnable;
    }

    @Override // xc.engine.core.algorithm.IAlgorithmInterface
    public void enableShapeRecognize(boolean z) {
        this.isShapeRecognizeEnable = z;
    }

    public ParamsRecognizeShape getShapeRecognizeParams() {
        return this.mParamsRecognizeShape;
    }

    @Override // xc.engine.core.algorithm.IAlgorithmInterface
    public void setShapeRecognizeParams(ParamsRecognizeShape paramsRecognizeShape) {
        this.mParamsRecognizeShape.set(paramsRecognizeShape);
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
