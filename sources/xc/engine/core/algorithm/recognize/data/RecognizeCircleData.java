package xc.engine.core.algorithm.recognize.data;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

/* JADX INFO: loaded from: classes6.dex */
class RecognizeCircleData extends RecognizeData {
    private float centerX = 0.0f;
    private float centerY = 0.0f;
    private float height = 0.0f;
    private float width = 0.0f;
    private float degree = 0.0f;

    RecognizeCircleData() {
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public Path getPathResult() {
        if (!(this.width > 0.0f) || !(this.height > 0.0f)) {
            return null;
        }
        Path path = new Path();
        float f = this.centerX;
        float f2 = this.centerY;
        float f3 = this.width / 2.0f;
        float f4 = this.height / 2.0f;
        RectF rectF = new RectF(f - f3, f2 - f4, f + f3, f2 + f4);
        Path path2 = new Path();
        path.rewind();
        path2.addOval(rectF, Path.Direction.CCW);
        Matrix matrix = new Matrix();
        matrix.postRotate(this.degree, this.centerX, this.centerY);
        path.addPath(path2, matrix);
        path.computeBounds(rectF, false);
        path.transform(getMatrix());
        return path;
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public boolean parseResult(String[] strArr) {
        if (strArr.length < 5) {
            return false;
        }
        this.centerX = Float.parseFloat(strArr[0]);
        this.centerY = Float.parseFloat(strArr[1]);
        this.width = Float.parseFloat(strArr[2]);
        this.height = Float.parseFloat(strArr[3]);
        this.degree = Float.parseFloat(strArr[4]);
        return true;
    }
}
