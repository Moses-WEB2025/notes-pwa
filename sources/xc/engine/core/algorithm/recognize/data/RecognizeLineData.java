package xc.engine.core.algorithm.recognize.data;

import android.graphics.Path;
import xc.engine.core.algorithm.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
class RecognizeLineData extends RecognizeWithArrowData {
    private TouchPoint startPoint = null;
    private TouchPoint endPoint = null;

    RecognizeLineData() {
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0031  */
    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public Path getPathResult() {
        boolean z;
        RecognizeLineData recognizeLineData;
        if (this.startPoint == null || this.endPoint == null) {
            return null;
        }
        Path path = new Path();
        boolean z2 = true;
        if (getRecognizeCurveType() == 1) {
            z = false;
            z2 = false;
        } else if (getRecognizeCurveType() == 2) {
            z = false;
        } else if (getRecognizeCurveType() == 3) {
            z = true;
            z2 = false;
        } else if (getRecognizeCurveType() == 4) {
            z = true;
        } else {
            z = false;
            z2 = false;
        }
        if (z2) {
            recognizeLineData = this;
            recognizeLineData.getArrowPath(path, this.endPoint.x, this.endPoint.y, this.startPoint.x, this.startPoint.y, true);
        } else {
            recognizeLineData = this;
            path.moveTo(recognizeLineData.startPoint.x, recognizeLineData.startPoint.y);
        }
        path.lineTo(recognizeLineData.endPoint.x, recognizeLineData.endPoint.y);
        if (z) {
            recognizeLineData.getArrowPath(path, recognizeLineData.startPoint.x, recognizeLineData.startPoint.y, recognizeLineData.endPoint.x, recognizeLineData.endPoint.y, false);
        }
        path.transform(recognizeLineData.getMatrix());
        return path;
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public boolean parseResult(String[] strArr) {
        if (strArr.length / 2 < 2) {
            return false;
        }
        this.startPoint = new TouchPoint(Float.parseFloat(strArr[0]), Float.parseFloat(strArr[1]));
        this.endPoint = new TouchPoint(Float.parseFloat(strArr[2]), Float.parseFloat(strArr[3]));
        return true;
    }
}
