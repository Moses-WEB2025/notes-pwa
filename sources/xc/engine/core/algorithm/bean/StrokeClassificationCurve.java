package xc.engine.core.algorithm.bean;

import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class StrokeClassificationCurve {
    public List<StrokeClassificationPoint> StrokeClassificationPoints;
    public int index;
    public boolean isInCircle;

    public StrokeClassificationCurve(List<StrokeClassificationPoint> list, int i, boolean z) {
        this.StrokeClassificationPoints = list;
        this.index = i;
        this.isInCircle = z;
    }
}
