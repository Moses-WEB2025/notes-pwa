package xc.engine.core.algorithm;

import android.content.Context;
import com.miui.handwriting.common.tools.constants.Constants;
import com.xiaomi.android.bsp.ui.pen.algorithm.AlgorithmFacade;
import com.xiaomi.android.bsp.ui.pen.algorithm.data.MotionPoint;
import com.xiaomi.android.bsp.ui.pen.algorithm.recognizelib.sdk.RecognizeListener;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class AlgorithmNative {
    private final int RECOGNIZE_MAX_THREAD_NUM;
    private final String TAG;
    AlgorithmFacade algorithmFacade;
    float lastScaleRate;
    public Context mContext;

    private AlgorithmNative() {
        this.TAG = getClass().getSimpleName();
        this.RECOGNIZE_MAX_THREAD_NUM = 4;
        this.lastScaleRate = -1.0f;
    }

    private static class EditorInstance {
        private static final AlgorithmNative singleton = new AlgorithmNative();

        private EditorInstance() {
        }
    }

    public static AlgorithmNative getInstance() {
        return EditorInstance.singleton;
    }

    public void setContent(Context context) {
        if (this.mContext != null) {
            return;
        }
        this.mContext = context;
        long j = context.getSharedPreferences(Constants.CREATION_SHPREF, 0).getLong("estimate_time", 37L);
        AlgorithmFacade algorithmFacade = AlgorithmFacade.getInstance();
        this.algorithmFacade = algorithmFacade;
        algorithmFacade.init(this.mContext);
        this.algorithmFacade.setPredictionTime(j);
        this.algorithmFacade.setMaxThreadNum(4);
    }

    public boolean openEngine(long j) {
        this.algorithmFacade.openEngine(j);
        return true;
    }

    public void clearRecognizeContent(long j) {
        this.algorithmFacade.clearRecognizeContent(j);
    }

    public int[] addRecognizePoints(long j, List<MotionPoint> list) {
        return this.algorithmFacade.addRecognizePoints(j, list);
    }

    public void recognizePage(long j) {
        this.algorithmFacade.recognizePage(j);
    }

    public String recognizeTrajectory(long j, List<MotionPoint> list) {
        return this.algorithmFacade.recognizeTrajectory(j, list);
    }

    public void awaitIdle(long j) {
        this.algorithmFacade.awaitIdle(j);
    }

    public String getRecognizeContent(long j) {
        return this.algorithmFacade.getRecognizeContent(j);
    }

    public void setScaleRate(float f) {
        if (this.lastScaleRate != f) {
            this.algorithmFacade.setDrawScale(f);
        }
        this.lastScaleRate = f;
    }

    public boolean closeEngine(long j) {
        AlgorithmFacade algorithmFacade = this.algorithmFacade;
        if (algorithmFacade == null) {
            return true;
        }
        algorithmFacade.closeEngine(j);
        return true;
    }

    public void setTouchDownPredictionPoint(MotionPoint motionPoint) {
        this.algorithmFacade.setPredictionPoint(motionPoint);
    }

    public void setTouchMovePredictionPoint(MotionPoint motionPoint) {
        this.algorithmFacade.setPredictionPoint(motionPoint);
    }

    public int getPredictionPoints(List<double[]> list) {
        return this.algorithmFacade.getPredictionPoints(list);
    }

    public String getContourLine(List<MotionPoint> list, double d) {
        return this.algorithmFacade.getContourLine(list, d);
    }

    public String getHighlightLine(List<MotionPoint> list, double d) {
        return this.algorithmFacade.getHighlightLine(list, d);
    }

    public void cancelContourLine(boolean z) {
        this.algorithmFacade.cancelContourLine(z);
    }

    public void setRecognizeEngineListener(long j, RecognizeListener recognizeListener) {
        this.algorithmFacade.setRecognizeListener(j, recognizeListener);
    }

    public void onStop() {
        AlgorithmFacade algorithmFacade = this.algorithmFacade;
        if (algorithmFacade != null) {
            algorithmFacade.onApplicationStop();
        }
    }
}
