package xc.engine.core.algorithm.recognize.data;

import android.graphics.Path;
import java.util.ArrayList;
import xc.engine.core.algorithm.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
class RecognizeCurveData extends RecognizePolygonData {
    private ArrayList<TouchPoint> breakPointsList = new ArrayList<>();

    RecognizeCurveData() {
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizePolygonData, xc.engine.core.algorithm.recognize.data.RecognizeData
    public Path getPathResult() {
        int size = this.breakPointsList.size();
        if (size <= 1) {
            return null;
        }
        Path path = new Path();
        float f = 0.0f;
        int i = 0;
        float f2 = 0.0f;
        while (i < size) {
            float f3 = this.breakPointsList.get(i).x;
            float f4 = this.breakPointsList.get(i).y;
            if (i == 0) {
                path.moveTo(f3, f4);
            } else {
                path.quadTo(f, f2, (f + f3) / 2.0f, (f2 + f4) / 2.0f);
            }
            i++;
            f = f3;
            f2 = f4;
        }
        path.transform(getMatrix());
        return path;
    }
}
