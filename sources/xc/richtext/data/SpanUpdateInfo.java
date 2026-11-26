package xc.richtext.data;

import android.graphics.Matrix;
import android.graphics.RectF;

/* JADX INFO: loaded from: classes6.dex */
public class SpanUpdateInfo {
    public int id;
    public float newDegrees;
    public RectF newRectF;
    public float oldDegrees;
    public RectF oldRectF;
    public float scale;

    public SpanUpdateInfo(int i, RectF rectF, RectF rectF2, float f, float f2, float f3) {
        this.id = i;
        this.oldRectF = rectF;
        this.newRectF = rectF2;
        this.oldDegrees = f;
        this.newDegrees = f2;
        this.scale = f3;
    }

    public RectF getScaleRectF(float f, boolean z) {
        RectF rectF;
        if (z) {
            rectF = new RectF(this.oldRectF);
        } else {
            rectF = new RectF(this.newRectF);
        }
        Matrix matrix = new Matrix();
        matrix.setScale(f, f);
        matrix.mapRect(rectF);
        return rectF;
    }
}
