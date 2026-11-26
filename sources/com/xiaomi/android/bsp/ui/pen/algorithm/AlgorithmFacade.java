package com.xiaomi.android.bsp.ui.pen.algorithm;

import android.content.Context;
import com.xiaomi.android.bsp.ui.pen.algorithm.data.MotionPoint;
import com.xiaomi.android.bsp.ui.pen.algorithm.recognizelib.sdk.RecognizeListener;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AlgorithmFacade {
    private static final AlgorithmFacade INSTANCE = new AlgorithmFacade();

    public void awaitIdle(long j) {
    }

    public void cancelContourLine(boolean z) {
    }

    public void clearRecognizeContent(long j) {
    }

    public void closeEngine(long j) {
    }

    public String getContourLine(List<MotionPoint> list, double d) {
        return null;
    }

    public String getHighlightLine(List<MotionPoint> list, double d) {
        return null;
    }

    public int getPredictionPoints(List<double[]> list) {
        return 0;
    }

    public String getRecognizeContent(long j) {
        return null;
    }

    public void init(Context context) {
    }

    public void onApplicationStop() {
    }

    public void openEngine(long j) {
    }

    public void recognizePage(long j) {
    }

    public String recognizeTrajectory(long j, List<MotionPoint> list) {
        return null;
    }

    public void setDrawScale(float f) {
    }

    public void setMaxThreadNum(int i) {
    }

    public void setPredictionPoint(MotionPoint motionPoint) {
    }

    public void setPredictionTime(long j) {
    }

    public void setRecognizeListener(long j, RecognizeListener recognizeListener) {
    }

    public static AlgorithmFacade getInstance() {
        return INSTANCE;
    }

    public int[] addRecognizePoints(long j, List<MotionPoint> list) {
        return new int[0];
    }
}
