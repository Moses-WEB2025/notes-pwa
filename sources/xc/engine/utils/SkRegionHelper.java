package xc.engine.utils;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

/* JADX INFO: loaded from: classes6.dex */
public class SkRegionHelper {
    public static final int COORDINATE_LIMIT_VALUE = 16383;
    public static final int LIMIT_OFFSET_VALUE = 8191;
    public static final RectF LIMIT_RECT = new RectF(-16383.0f, -16383.0f, 16383.0f, 16383.0f);

    public static PointF calculatePathOffsetForClip(Path path) {
        float f;
        RectF rectF = new RectF();
        path.computeBounds(rectF, false);
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (true) {
            RectF rectF2 = LIMIT_RECT;
            if (!rectF2.contains(rectF)) {
                float f4 = -8191.0f;
                if (rectF.left <= rectF2.left || rectF.right <= rectF2.left) {
                    f2 += 8191.0f;
                    f = 8191.0f;
                } else if (rectF.left >= rectF2.right || rectF.right >= rectF2.right) {
                    f2 -= 8191.0f;
                    f = -8191.0f;
                } else {
                    f = 0.0f;
                }
                if (rectF.top <= rectF2.top || rectF.bottom <= rectF2.top) {
                    f3 += 8191.0f;
                    f4 = 8191.0f;
                } else if (rectF.top >= rectF2.bottom || rectF.bottom >= rectF2.bottom) {
                    f3 -= 8191.0f;
                } else {
                    f4 = 0.0f;
                }
                rectF.offset(f, f4);
            } else {
                return new PointF(f2, f3);
            }
        }
    }
}
