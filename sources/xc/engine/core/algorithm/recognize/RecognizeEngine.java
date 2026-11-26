package xc.engine.core.algorithm.recognize;

import android.content.Context;
import com.xiaomi.android.bsp.ui.pen.algorithm.data.MotionPoint;
import java.util.ArrayList;
import java.util.List;
import xc.engine.core.algorithm.AlgorithmNative;
import xc.engine.core.algorithm.recognize.data.RecognizeData;

/* JADX INFO: loaded from: classes6.dex */
public class RecognizeEngine {
    private List<RecognizePoint> mRecognizePoints = new ArrayList();

    public RecognizeEngine(Context context) {
        AlgorithmNative.getInstance().setContent(context);
    }

    public void release() {
        this.mRecognizePoints.clear();
    }

    public void addMove(float f, float f2, long j, int i) {
        this.mRecognizePoints.add(new RecognizePoint(f, f2, j, i));
    }

    public void clearContent() {
        List<RecognizePoint> list = this.mRecognizePoints;
        if (list != null) {
            list.clear();
        }
    }

    public RecognizeData getTrajectoryRecognizeResult() {
        int size;
        List<RecognizePoint> list = this.mRecognizePoints;
        if (list == null || (size = list.size()) <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            RecognizePoint recognizePoint = this.mRecognizePoints.get(i);
            if (recognizePoint != null) {
                arrayList.add(new MotionPoint(recognizePoint.x, recognizePoint.y, recognizePoint.t, recognizePoint.action));
            }
        }
        String strRecognizeTrajectory = AlgorithmNative.getInstance().recognizeTrajectory(Thread.currentThread().getId(), arrayList);
        if (strRecognizeTrajectory == null) {
            return null;
        }
        return RecognizeData.parseShape(strRecognizeTrajectory);
    }
}
