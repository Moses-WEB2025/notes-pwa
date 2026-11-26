package xc.engine.core.algorithm.recognize.data;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
class RecognizeHeartData extends RecognizeData {
    private boolean parasResult = false;
    private float centerX = 0.0f;
    private float centerY = 0.0f;
    private float degree = 0.0f;
    private PointF leftCircle = new PointF();
    private float leftRadius = 0.0f;
    private PointF leftCirclePara = new PointF();
    private PointF rightCircle = new PointF();
    private float rightRadius = 0.0f;
    private PointF rightCirclePara = new PointF();
    private List<PointF> pointFList = new ArrayList();

    RecognizeHeartData() {
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public Path getPathResult() {
        PointF pointF = null;
        if (!this.parasResult) {
            return null;
        }
        Path path = new Path();
        float f = this.rightCircle.x - this.rightRadius;
        float f2 = this.rightCircle.y - this.rightRadius;
        float f3 = this.rightCircle.x;
        float f4 = this.rightRadius;
        path.addArc(f, f2, f3 + f4, this.rightCircle.y + f4, this.rightCirclePara.x, this.rightCirclePara.y);
        if (!this.pointFList.isEmpty()) {
            for (PointF pointF2 : this.pointFList) {
                if (pointF == null) {
                    path.lineTo(pointF2.x, pointF2.y);
                } else {
                    path.quadTo(pointF.x, pointF.y, (pointF2.x + pointF.x) / 2.0f, (pointF2.y + pointF.y) / 2.0f);
                }
                pointF = new PointF(pointF2.x, pointF2.y);
            }
        }
        float f5 = this.leftCircle.x - this.leftRadius;
        float f6 = this.leftCircle.y - this.leftRadius;
        float f7 = this.leftCircle.x;
        float f8 = this.leftRadius;
        path.arcTo(f5, f6, f7 + f8, f8 + this.leftCircle.y, this.leftCirclePara.x, this.leftCirclePara.y, false);
        path.close();
        Matrix matrix = new Matrix();
        matrix.setRotate(this.degree, this.centerX, this.centerY);
        path.transform(matrix);
        path.transform(getMatrix());
        return path;
    }

    @Override // xc.engine.core.algorithm.recognize.data.RecognizeData
    public boolean parseResult(String[] strArr) {
        if (strArr.length >= 13) {
            this.centerX = Float.parseFloat(strArr[0]);
            this.centerY = Float.parseFloat(strArr[1]);
            this.degree = Float.parseFloat(strArr[2]);
            this.leftCircle.x = Float.parseFloat(strArr[3]);
            this.leftCircle.y = Float.parseFloat(strArr[4]);
            this.leftRadius = Float.parseFloat(strArr[5]);
            this.leftCirclePara.x = Float.parseFloat(strArr[6]);
            this.leftCirclePara.y = Float.parseFloat(strArr[7]);
            this.rightCircle.x = Float.parseFloat(strArr[8]);
            this.rightCircle.y = Float.parseFloat(strArr[9]);
            this.rightRadius = Float.parseFloat(strArr[10]);
            this.rightCirclePara.x = Float.parseFloat(strArr[11]);
            this.rightCirclePara.y = Float.parseFloat(strArr[12]);
            if ((strArr.length - 12) % 2 != 0) {
                for (int i = 13; i < strArr.length; i += 2) {
                    this.pointFList.add(new PointF(Float.parseFloat(strArr[i]), Float.parseFloat(strArr[i + 1])));
                }
                this.parasResult = true;
            } else {
                this.parasResult = false;
            }
        }
        return this.parasResult;
    }
}
