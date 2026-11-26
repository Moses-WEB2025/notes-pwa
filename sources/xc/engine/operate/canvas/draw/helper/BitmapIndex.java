package xc.engine.operate.canvas.draw.helper;

import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.List;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class BitmapIndex {
    public volatile Bitmap bitmap;
    public int id;
    public List<DataCurve> intersectingCurves;
    public boolean isLoaded;
    public Rect mRect = new Rect();
    public Rect bitmapRect = new Rect();
}
