package xc.engine.core.algorithm.recognize.data;

import android.graphics.Path;
import java.util.ArrayList;
import xc.engine.core.algorithm.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
class RecognizePolygonData extends RecognizeData {
    private ArrayList<TouchPoint> breakPointsList = new ArrayList<>();

    RecognizePolygonData() {
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public Path getPathResult() {
        int size = this.breakPointsList.size();
        if (size < 1) {
            return null;
        }
        Path path = new Path();
        for (int i = 0; i < size; i++) {
            TouchPoint touchPoint = this.breakPointsList.get(i);
            if (i == 0) {
                path.moveTo(touchPoint.x, touchPoint.y);
            } else {
                path.lineTo(touchPoint.x, touchPoint.y);
            }
        }
        path.transform(getMatrix());
        return path;
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public boolean parseResult(String[] strArr) {
        int length = strArr.length / 2;
        if (length == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            this.breakPointsList.add(new TouchPoint(Float.parseFloat(strArr[i2]), Float.parseFloat(strArr[i2 + 1]), 1.0f));
        }
        return true;
    }
}
