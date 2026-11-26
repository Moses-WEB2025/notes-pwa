package xc.engine.core.algorithm.recognize.data;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
class RecognizeCloudData extends RecognizeData {
    private boolean parasResult = false;
    private List<Arc> arcs = new ArrayList();
    private float centerX = 0.0f;
    private float centerY = 0.0f;
    private float degree = 0.0f;

    RecognizeCloudData() {
    }

    public static class Arc {
        private float centerX;
        private float centerY;
        private float radius;
        private float startAngle;
        private float sweepAngle;
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public Path getPathResult() {
        if (!this.parasResult) {
            return null;
        }
        Path path = new Path();
        for (Arc arc : this.arcs) {
            path.arcTo(new RectF(arc.centerX - arc.radius, arc.centerY - arc.radius, arc.centerX + arc.radius, arc.centerY + arc.radius), arc.startAngle, arc.sweepAngle);
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(this.degree, this.centerX, this.centerY);
        path.transform(matrix);
        path.close();
        path.transform(getMatrix());
        return path;
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public boolean parseResult(String[] strArr) {
        int length = strArr.length;
        if (length > 3 && (length - 3) % 5 == 0) {
            this.centerX = Float.parseFloat(strArr[0]);
            this.centerY = Float.parseFloat(strArr[1]);
            this.degree = Float.parseFloat(strArr[2]);
            for (int i = 3; i < strArr.length; i += 5) {
                Arc arc = new Arc();
                arc.centerX = Float.parseFloat(strArr[i]);
                arc.centerY = Float.parseFloat(strArr[i + 1]);
                arc.radius = Float.parseFloat(strArr[i + 2]);
                arc.startAngle = Float.parseFloat(strArr[i + 3]);
                arc.sweepAngle = Float.parseFloat(strArr[i + 4]);
                this.arcs.add(arc);
            }
            this.parasResult = true;
        }
        return this.parasResult;
    }
}
