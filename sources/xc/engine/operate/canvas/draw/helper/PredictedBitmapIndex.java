package xc.engine.operate.canvas.draw.helper;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

/* JADX INFO: loaded from: classes6.dex */
public class PredictedBitmapIndex {
    public Bitmap bitmap;
    public int[] pixels;
    public boolean isLoaded = false;
    public Rect rect = new Rect();
    public Point size = new Point();
    public PointF offset = new PointF();

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setIsLoaded(boolean z) {
        this.isLoaded = z;
    }
}
