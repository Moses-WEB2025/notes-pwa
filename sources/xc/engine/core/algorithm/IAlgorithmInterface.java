package xc.engine.core.algorithm;

import android.graphics.Path;
import java.util.List;
import xc.engine.core.algorithm.estimate.ParamsEstimate;
import xc.engine.core.algorithm.recognize.ParamsRecognizeShape;
import xc.engine.data.pagedata.ICurve;

/* JADX INFO: loaded from: classes6.dex */
public interface IAlgorithmInterface {
    Path countDataPath(List<? extends ICurve> list);

    Path countDataPaths(List<? extends ICurve> list, float f);

    void enableEstimate(boolean z);

    void enableShapeRecognize(boolean z);

    void setEstimateParams(ParamsEstimate paramsEstimate);

    void setShapeRecognizeParams(ParamsRecognizeShape paramsRecognizeShape);
}
