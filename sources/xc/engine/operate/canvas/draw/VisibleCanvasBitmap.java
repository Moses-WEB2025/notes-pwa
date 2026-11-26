package xc.engine.operate.canvas.draw;

import android.graphics.Bitmap;
import android.graphics.Point;

/* JADX INFO: loaded from: classes6.dex */
public class VisibleCanvasBitmap {
    private Bitmap bitmap;
    private int x;
    private int y;

    public VisibleCanvasBitmap(int i, int i2, Bitmap bitmap) {
        this.x = i;
        this.y = i2;
        this.bitmap = bitmap;
    }

    public Point getLocation() {
        return new Point(this.x, this.y);
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }
}
