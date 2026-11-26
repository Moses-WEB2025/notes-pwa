package xc.engine.core.algorithm.recognize.data;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;

/* JADX INFO: loaded from: classes6.dex */
class RecognizeParabolaData extends RecognizeWithArrowData {
    private boolean parasResult = false;
    private float xCoordinateFirst = 0.0f;
    private float yCoordinateFirst = 0.0f;
    private float xCoordinateCtrlFirst = 0.0f;
    private float yCoordinateCtrlFirst = 0.0f;
    private float xCoordinateCtrlSecond = 0.0f;
    private float yCoordinateCtrlSecond = 0.0f;
    private float xCoordinatePeak = 0.0f;
    private float yCoordinatePeak = 0.0f;
    private float xCoordinateCtrlThird = 0.0f;
    private float yCoordinateCtrlThird = 0.0f;
    private float xCoordinateCtrlFourth = 0.0f;
    private float yCoordinateCtrlFourth = 0.0f;
    private float xCoordinateSecond = 0.0f;
    private float yCoordinateSecond = 0.0f;

    RecognizeParabolaData() {
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0031  */
    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public Path getPathResult() {
        boolean z;
        boolean z2;
        Path path;
        RecognizeParabolaData recognizeParabolaData;
        float f;
        float f2;
        if (!this.parasResult) {
            return null;
        }
        Path path2 = new Path();
        if (getRecognizeCurveType() != 7) {
            z2 = true;
            if (getRecognizeCurveType() == 8) {
                z = false;
            } else if (getRecognizeCurveType() == 9) {
                z = true;
                z2 = false;
            } else if (getRecognizeCurveType() == 10) {
                z = true;
            } else {
                z = false;
                z2 = false;
            }
        } else {
            z = false;
            z2 = false;
        }
        Path path3 = new Path();
        path3.moveTo(this.xCoordinateFirst, this.yCoordinateFirst);
        path3.cubicTo(this.xCoordinateCtrlFirst, this.yCoordinateCtrlFirst, this.xCoordinateCtrlSecond, this.yCoordinateCtrlSecond, this.xCoordinatePeak, this.yCoordinatePeak);
        path3.cubicTo(this.xCoordinateCtrlThird, this.yCoordinateCtrlThird, this.xCoordinateCtrlFourth, this.yCoordinateCtrlFourth, this.xCoordinateSecond, this.yCoordinateSecond);
        float[] fArr = new float[9];
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path3, false);
        Matrix matrix = new Matrix();
        if (z2) {
            pathMeasure.getMatrix(getArrowBottom(), matrix, 3);
            matrix.getValues(fArr);
            getArrowPath(path2, fArr[2], fArr[5], this.xCoordinateFirst, this.yCoordinateFirst, true);
            path2 = path2;
        } else {
            path2.moveTo(this.xCoordinateFirst, this.yCoordinateFirst);
        }
        path2.cubicTo(this.xCoordinateCtrlFirst, this.yCoordinateCtrlFirst, this.xCoordinateCtrlSecond, this.yCoordinateCtrlSecond, this.xCoordinatePeak, this.yCoordinatePeak);
        path2.cubicTo(this.xCoordinateCtrlThird, this.yCoordinateCtrlThird, this.xCoordinateCtrlFourth, this.yCoordinateCtrlFourth, this.xCoordinateSecond, this.yCoordinateSecond);
        if (z) {
            float length = pathMeasure.getLength();
            if (length > getArrowHeight()) {
                pathMeasure.getMatrix(length - getArrowBottom(), matrix, 3);
                matrix.getValues(fArr);
                f = fArr[2];
                f2 = fArr[5];
            } else {
                f = this.xCoordinatePeak;
                f2 = this.yCoordinatePeak;
            }
            path = path2;
            recognizeParabolaData = this;
            recognizeParabolaData.getArrowPath(path, f, f2, this.xCoordinateSecond, this.yCoordinateSecond, false);
        } else {
            path = path2;
            recognizeParabolaData = this;
        }
        path.transform(recognizeParabolaData.getMatrix());
        return path;
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public boolean parseResult(String[] strArr) {
        this.parasResult = false;
        if (strArr.length / 2 >= 7) {
            this.xCoordinateFirst = Float.parseFloat(strArr[0]);
            this.yCoordinateFirst = Float.parseFloat(strArr[1]);
            this.xCoordinateCtrlFirst = Float.parseFloat(strArr[2]);
            this.yCoordinateCtrlFirst = Float.parseFloat(strArr[3]);
            this.xCoordinateCtrlSecond = Float.parseFloat(strArr[4]);
            this.yCoordinateCtrlSecond = Float.parseFloat(strArr[5]);
            this.xCoordinatePeak = Float.parseFloat(strArr[6]);
            this.yCoordinatePeak = Float.parseFloat(strArr[7]);
            this.xCoordinateCtrlThird = Float.parseFloat(strArr[8]);
            this.yCoordinateCtrlThird = Float.parseFloat(strArr[9]);
            this.xCoordinateCtrlFourth = Float.parseFloat(strArr[10]);
            this.yCoordinateCtrlFourth = Float.parseFloat(strArr[11]);
            this.xCoordinateSecond = Float.parseFloat(strArr[12]);
            this.yCoordinateSecond = Float.parseFloat(strArr[13]);
            this.parasResult = true;
        }
        return this.parasResult;
    }
}
