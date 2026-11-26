package xc.engine.core.algorithm.estimate;

import com.xiaomi.android.bsp.ui.pen.algorithm.data.MotionPoint;
import java.util.ArrayList;
import xc.engine.core.algorithm.AlgorithmNative;
import xc.engine.core.algorithm.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
public class EstimatedGenerator {
    private String TAG = getClass().getSimpleName();
    public ArrayList<TouchPoint> touchPoints = new ArrayList<>();

    public void onTouchDownEvent(float f, float f2, float f3, float f4, float f5, long j) {
        this.touchPoints.clear();
        this.touchPoints.add(new TouchPoint(f, f2, f3, f4, f5, j, 0, false));
        AlgorithmNative.getInstance().setTouchDownPredictionPoint(new MotionPoint(f, f2, j, f3, f5, f4, false, 0));
    }

    public boolean onTouchMoveEvent(float f, float f2, float f3, float f4, float f5, long j, boolean z) {
        MotionPoint motionPoint;
        this.touchPoints.add(new TouchPoint(f, f2, f3, f4, f5, j, 2, false));
        if (z) {
            motionPoint = new MotionPoint(f, f2, j, f3, f5, f4, true, 2);
        } else {
            motionPoint = new MotionPoint(f, f2, j, f3, f5, f4, false, 2);
        }
        AlgorithmNative.getInstance().setTouchMovePredictionPoint(motionPoint);
        return true;
    }

    public TouchPoint[] getPredictionPoints() {
        ArrayList arrayList = new ArrayList();
        if (AlgorithmNative.getInstance().getPredictionPoints(arrayList) == 0) {
            return null;
        }
        int size = arrayList.size();
        TouchPoint[] touchPointArr = new TouchPoint[size];
        for (int i = 0; i < size; i++) {
            touchPointArr[i] = new TouchPoint((float) ((double[]) arrayList.get(i))[0], (float) ((double[]) arrayList.get(i))[1], (long) ((double[]) arrayList.get(i))[2]);
        }
        return touchPointArr;
    }

    public TouchPoint[] getEstimatedPoints() {
        TouchPoint[] predictionPoints;
        if (this.touchPoints.size() <= 0 || (predictionPoints = getPredictionPoints()) == null) {
            return null;
        }
        TouchPoint[] touchPointArr = new TouchPoint[predictionPoints.length];
        for (int i = 0; i < predictionPoints.length; i++) {
            TouchPoint touchPoint = new TouchPoint(predictionPoints[i].x, predictionPoints[i].y);
            touchPointArr[i] = touchPoint;
            touchPoint.downTime = predictionPoints[i].downTime;
        }
        return touchPointArr;
    }

    public ArrayList<TouchPoint> getTouchPoint() {
        return this.touchPoints;
    }
}
