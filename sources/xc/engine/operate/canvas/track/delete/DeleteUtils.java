package xc.engine.operate.canvas.track.delete;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import xc.engine.operate.touch.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
class DeleteUtils {
    DeleteUtils() {
    }

    protected static void calDeleteRegion(RectF rectF, PointF pointF, TouchPoint touchPoint, float f, Region region, boolean z, float f2) {
        Path path = new Path();
        Region region2 = new Region();
        rectF.set(Math.min(pointF.x, touchPoint.getX()) - f, Math.min(pointF.y, touchPoint.getY()) - f, Math.max(pointF.x, touchPoint.getX()) + f, Math.max(pointF.y, touchPoint.getY()) + f);
        if (z) {
            f *= f2;
        }
        double d = -Math.atan2(touchPoint.getY() - pointF.y, touchPoint.getX() - pointF.x);
        double d2 = f;
        float fSin = (float) (Math.sin(d) * d2);
        float fCos = (float) (d2 * Math.cos(d));
        path.moveTo(touchPoint.getX() - fSin, touchPoint.getY() - fCos);
        path.lineTo(pointF.x - fSin, pointF.y - fCos);
        path.lineTo(pointF.x + fSin, pointF.y + fCos);
        path.lineTo(touchPoint.getX() + fSin, touchPoint.getY() + fCos);
        path.close();
        path.addCircle(touchPoint.getX(), touchPoint.getY(), f, Path.Direction.CCW);
        path.addCircle(pointF.x, pointF.y, f, Path.Direction.CCW);
        path.offset(-touchPoint.getX(), -touchPoint.getY());
        region2.set(new Rect((int) (rectF.left - touchPoint.getX()), (int) (rectF.top - touchPoint.getY()), (int) (rectF.right - touchPoint.getX()), (int) (rectF.bottom - touchPoint.getY())));
        region.setEmpty();
        region.setPath(path, region2);
    }
}
