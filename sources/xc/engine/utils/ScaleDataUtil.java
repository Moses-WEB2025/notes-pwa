package xc.engine.utils;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import java.util.Iterator;
import java.util.List;
import xc.engine.data.CsPoint;
import xc.engine.operate.canvas.CanvasSizeInfo;
import xc.engine.operate.canvas.draw.helper.BitmapIndex;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes6.dex */
public class ScaleDataUtil {
    private static final String TAG = "ScaleDataUtil";

    public static CsPoint cloneCsPoint(CsPoint csPoint) {
        CsPoint csPoint2 = new CsPoint();
        csPoint2.setX(csPoint.getX());
        csPoint2.setY(csPoint.getY());
        csPoint2.setPressure(csPoint.getPressure());
        csPoint2.setDataState(csPoint.getDataState());
        csPoint2.setOrientation(csPoint.getOrientation());
        csPoint2.setTilt(csPoint.getTilt());
        csPoint2.setTime(csPoint.getTime());
        csPoint2.setVelocity(csPoint.getVelocity());
        csPoint2.setRotateAngle(csPoint.getRotateAngle());
        return csPoint2;
    }

    public static PointF scaleInverseOperation(float f, float f2, ScaleInfo scaleInfo) {
        PointF pointF = new PointF();
        pointF.x = (f - scaleInfo.getTranslate().x) / scaleInfo.getScaleRate();
        pointF.y = (f2 - scaleInfo.getTranslate().y) / scaleInfo.getScaleRate();
        return pointF;
    }

    public static boolean isScaleChange(ScaleInfo scaleInfo, ScaleInfo scaleInfo2) {
        return (scaleInfo.getScaleRate() == scaleInfo2.getScaleRate() && scaleInfo.getTranslate().equals(scaleInfo2.getTranslate())) ? false : true;
    }

    public static RectF getCompatScaleScreenRect(ScaleInfo scaleInfo, CanvasSizeInfo canvasSizeInfo, boolean z) {
        if (z) {
            RectF rectF = new RectF();
            float scaleRate = scaleInfo.getScaleRate();
            float f = scaleInfo.getTranslate().x;
            float f2 = scaleInfo.getTranslate().y;
            rectF.left = (-f) / scaleRate;
            rectF.top = ((-f2) + canvasSizeInfo.getVisibleTop()) / scaleRate;
            rectF.right = rectF.left + (canvasSizeInfo.getVisibleWidth() / scaleRate);
            rectF.bottom = rectF.top + (canvasSizeInfo.getVisibleHeight() / scaleRate);
            return rectF;
        }
        return getScaleScreenRect(scaleInfo, canvasSizeInfo);
    }

    public static RectF getScaleScreenRect(ScaleInfo scaleInfo, CanvasSizeInfo canvasSizeInfo) {
        RectF rectF = new RectF();
        rectF.left = Math.max((-scaleInfo.getTranslate().x) / scaleInfo.getScaleRate(), 0.0f);
        rectF.right = Math.min((canvasSizeInfo.getVisibleWidth() - scaleInfo.getTranslate().x) / scaleInfo.getScaleRate(), canvasSizeInfo.getVisibleWidth());
        float scaleRate = ((-scaleInfo.getTranslate().y) / scaleInfo.getScaleRate()) + (canvasSizeInfo.getVisibleTop() / scaleInfo.getScaleRate());
        rectF.top = scaleRate;
        rectF.bottom = scaleRate + (canvasSizeInfo.getVisibleHeight() / scaleInfo.getScaleRate());
        return rectF;
    }

    public static RectF getCompatVisibleSaveRect(ScaleInfo scaleInfo, CanvasSizeInfo canvasSizeInfo, float f, boolean z) {
        if (z) {
            RectF rectF = new RectF(-scaleInfo.getTranslate().x, (-scaleInfo.getTranslate().y) + canvasSizeInfo.getVisibleTop(), (-scaleInfo.getTranslate().x) + canvasSizeInfo.getVisibleWidth(), (-scaleInfo.getTranslate().y) + canvasSizeInfo.getVisibleTop() + canvasSizeInfo.getVisibleHeight());
            rectF.left /= f;
            rectF.right /= f;
            rectF.top /= f;
            rectF.bottom /= f;
            return rectF;
        }
        return getVisibleDataSaveRect(scaleInfo, canvasSizeInfo, f);
    }

    public static RectF getVisibleDataSaveRect(ScaleInfo scaleInfo, CanvasSizeInfo canvasSizeInfo, float f) {
        RectF rectF = new RectF(-scaleInfo.getTranslate().x, (-scaleInfo.getTranslate().y) + canvasSizeInfo.getVisibleTop(), (-scaleInfo.getTranslate().x) + canvasSizeInfo.getVisibleWidth(), (-scaleInfo.getTranslate().y) + canvasSizeInfo.getVisibleTop() + canvasSizeInfo.getVisibleHeight());
        rectF.left = Math.max(rectF.left / f, 0.0f);
        rectF.right = Math.min(rectF.right / f, (canvasSizeInfo.getCanvasWidth() * scaleInfo.getScaleRate()) / f);
        rectF.top = Math.max(rectF.top / f, 0.0f);
        rectF.bottom = Math.min(rectF.bottom / f, (canvasSizeInfo.getCanvasHeight() * scaleInfo.getScaleRate()) / f);
        return rectF;
    }

    public static RectF getScaleAndTranslateRect(RectF rectF, ScaleInfo scaleInfo) {
        RectF rectF2 = new RectF();
        if (rectF.left < scaleInfo.getScaleCenter().x) {
            rectF2.left = (scaleInfo.getScaleCenter().x - ((scaleInfo.getScaleCenter().x - rectF.left) * scaleInfo.getScaleRate())) + scaleInfo.getTranslate().x;
        } else if (rectF.left >= scaleInfo.getScaleCenter().x) {
            rectF2.left = scaleInfo.getScaleCenter().x + ((rectF.left - scaleInfo.getScaleCenter().x) * scaleInfo.getScaleRate()) + scaleInfo.getTranslate().x;
        }
        if (rectF.right < scaleInfo.getScaleCenter().x) {
            rectF2.right = (scaleInfo.getScaleCenter().x - ((scaleInfo.getScaleCenter().x - rectF.right) * scaleInfo.getScaleRate())) + scaleInfo.getTranslate().x;
        } else if (rectF.right >= scaleInfo.getScaleCenter().x) {
            rectF2.right = scaleInfo.getScaleCenter().x + ((rectF.right - scaleInfo.getScaleCenter().x) * scaleInfo.getScaleRate()) + scaleInfo.getTranslate().x;
        }
        if (rectF.top < scaleInfo.getScaleCenter().y) {
            rectF2.top = (scaleInfo.getScaleCenter().y - ((scaleInfo.getScaleCenter().y - rectF.top) * scaleInfo.getScaleRate())) + scaleInfo.getTranslate().y;
        } else if (rectF.top >= scaleInfo.getScaleCenter().y) {
            rectF2.top = scaleInfo.getScaleCenter().y + ((rectF.top - scaleInfo.getScaleCenter().y) * scaleInfo.getScaleRate()) + scaleInfo.getTranslate().y;
        }
        if (rectF.bottom < scaleInfo.getScaleCenter().y) {
            rectF2.bottom = (scaleInfo.getScaleCenter().y - ((scaleInfo.getScaleCenter().y - rectF.bottom) * scaleInfo.getScaleRate())) + scaleInfo.getTranslate().y;
            return rectF2;
        }
        if (rectF.bottom >= scaleInfo.getScaleCenter().y) {
            rectF2.bottom = scaleInfo.getScaleCenter().y + ((rectF.bottom - scaleInfo.getScaleCenter().y) * scaleInfo.getScaleRate()) + scaleInfo.getTranslate().y;
        }
        return rectF2;
    }

    public static Rect getToScaleRect(Rect rect, ScaleInfo scaleInfo) {
        Rect rect2 = new Rect();
        if (rect.left < scaleInfo.getScaleCenter().x) {
            rect2.left = (int) ((scaleInfo.getScaleCenter().x - ((scaleInfo.getScaleCenter().x - rect.left) * scaleInfo.getScaleRate())) + scaleInfo.getTranslate().x);
        } else if (rect.left >= scaleInfo.getScaleCenter().x) {
            rect2.left = (int) (scaleInfo.getScaleCenter().x + ((rect.left - scaleInfo.getScaleCenter().x) * scaleInfo.getScaleRate()) + scaleInfo.getTranslate().x);
        }
        if (rect.right < scaleInfo.getScaleCenter().x) {
            rect2.right = (int) ((scaleInfo.getScaleCenter().x - ((scaleInfo.getScaleCenter().x - rect.right) * scaleInfo.getScaleRate())) + scaleInfo.getTranslate().x);
        } else if (rect.right >= scaleInfo.getScaleCenter().x) {
            rect2.right = (int) (scaleInfo.getScaleCenter().x + ((rect.right - scaleInfo.getScaleCenter().x) * scaleInfo.getScaleRate()) + scaleInfo.getTranslate().x);
        }
        if (rect.top < scaleInfo.getScaleCenter().y) {
            rect2.top = (int) ((scaleInfo.getScaleCenter().y - ((scaleInfo.getScaleCenter().y - rect.top) * scaleInfo.getScaleRate())) + scaleInfo.getTranslate().y);
        } else if (rect.top >= scaleInfo.getScaleCenter().y) {
            rect2.top = (int) (scaleInfo.getScaleCenter().y + ((rect.top - scaleInfo.getScaleCenter().y) * scaleInfo.getScaleRate()) + scaleInfo.getTranslate().y);
        }
        if (rect.bottom < scaleInfo.getScaleCenter().y) {
            rect2.bottom = (int) ((scaleInfo.getScaleCenter().y - ((scaleInfo.getScaleCenter().y - rect.bottom) * scaleInfo.getScaleRate())) + scaleInfo.getTranslate().y);
            return rect2;
        }
        if (rect.bottom >= scaleInfo.getScaleCenter().y) {
            rect2.bottom = (int) (scaleInfo.getScaleCenter().y + ((rect.bottom - scaleInfo.getScaleCenter().y) * scaleInfo.getScaleRate()) + scaleInfo.getTranslate().y);
        }
        return rect2;
    }

    public static RectF toScaleNotOffsetRectF(RectF rectF, ScaleInfo scaleInfo) {
        return new RectF(rectF.left * scaleInfo.getScaleRate(), rectF.top * scaleInfo.getScaleRate(), rectF.right * scaleInfo.getScaleRate(), rectF.bottom * scaleInfo.getScaleRate());
    }

    public static Rect toScaleNotOffsetRect(Rect rect, ScaleInfo scaleInfo) {
        return new Rect((int) Math.floor(rect.left * scaleInfo.getScaleRate()), (int) Math.floor(rect.top * scaleInfo.getScaleRate()), (int) Math.ceil(rect.right * scaleInfo.getScaleRate()), (int) Math.ceil(rect.bottom * scaleInfo.getScaleRate()));
    }

    public static Rect toScaleNotOffsetRect(Rect rect, float f) {
        return new Rect((int) Math.floor(rect.left * f), (int) Math.floor(rect.top * f), (int) Math.ceil(rect.right * f), (int) Math.ceil(rect.bottom * f));
    }

    public static Rect getScreenInCanvasRect(ScaleInfo scaleInfo, CanvasSizeInfo canvasSizeInfo) {
        Rect rect = new Rect();
        rect.left = (int) Math.max(0.0f, -scaleInfo.getTranslate().x);
        rect.right = (int) Math.min(canvasSizeInfo.getVisibleWidth() * scaleInfo.getScaleRate(), canvasSizeInfo.getVisibleWidth() - scaleInfo.getTranslate().x);
        rect.top = (int) Math.max(0.0f, canvasSizeInfo.getVisibleTop() - scaleInfo.getTranslate().y);
        rect.bottom = (int) Math.min(canvasSizeInfo.getCanvasHeight() * scaleInfo.getScaleRate(), (canvasSizeInfo.getVisibleTop() + canvasSizeInfo.getVisibleHeight()) - scaleInfo.getTranslate().y);
        return rect;
    }

    public static Rect getCompatScreenInCanvasRect(ScaleInfo scaleInfo, CanvasSizeInfo canvasSizeInfo, boolean z) {
        if (z) {
            return getScreenVisibleRect(scaleInfo, canvasSizeInfo);
        }
        return getScreenInCanvasRect(scaleInfo, canvasSizeInfo);
    }

    public static Rect getScreenVisibleRect(ScaleInfo scaleInfo, CanvasSizeInfo canvasSizeInfo) {
        Rect rect = new Rect();
        rect.left = (int) (-scaleInfo.getTranslate().x);
        rect.right = (int) (canvasSizeInfo.getVisibleWidth() - scaleInfo.getTranslate().x);
        rect.top = (int) (canvasSizeInfo.getVisibleTop() - scaleInfo.getTranslate().y);
        rect.bottom = (int) ((canvasSizeInfo.getVisibleTop() + canvasSizeInfo.getVisibleHeight()) - scaleInfo.getTranslate().y);
        return rect;
    }

    public static Rect toCanvasInRect(RectF rectF, Rect rect) {
        Log.d(TAG, "toCanvasInRect, draw rect: " + rectF + ", canvas rect: " + rect);
        if (rectF.left > rect.right || rectF.right < rect.left || rectF.top > rect.bottom || rectF.bottom < rect.top) {
            return null;
        }
        Rect rect2 = new Rect();
        rect2.left = (int) Math.max(rectF.left, rect.left);
        rect2.right = Math.min((int) Math.ceil(rectF.right), rect.right);
        rect2.top = (int) Math.max(rectF.top, rect.top);
        rect2.bottom = Math.min((int) Math.ceil(rectF.bottom), rect.bottom);
        return rect2;
    }

    public static boolean validCanvasRect(RectF rectF, ScaleInfo scaleInfo, CanvasSizeInfo canvasSizeInfo) {
        Rect screenInCanvasRect = getScreenInCanvasRect(scaleInfo, canvasSizeInfo);
        screenInCanvasRect.offset((int) scaleInfo.getTranslate().x, (int) scaleInfo.getTranslate().y);
        return rectF.left <= ((float) screenInCanvasRect.right) && rectF.right >= ((float) screenInCanvasRect.left) && rectF.top <= ((float) screenInCanvasRect.bottom) && rectF.bottom >= ((float) screenInCanvasRect.top);
    }

    public static boolean validCanvasRect(RectF rectF, ScaleInfo scaleInfo, CanvasSizeInfo canvasSizeInfo, boolean z) {
        Rect compatScreenInCanvasRect = getCompatScreenInCanvasRect(scaleInfo, canvasSizeInfo, z);
        compatScreenInCanvasRect.offset((int) scaleInfo.getTranslate().x, (int) scaleInfo.getTranslate().y);
        return rectF.left <= ((float) compatScreenInCanvasRect.right) && rectF.right >= ((float) compatScreenInCanvasRect.left) && rectF.top <= ((float) compatScreenInCanvasRect.bottom) && rectF.bottom >= ((float) compatScreenInCanvasRect.top);
    }

    public static void clearBitmapList(List<BitmapIndex> list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).bitmap != null && !list.get(i).bitmap.isRecycled()) {
                list.get(i).bitmap.recycle();
                list.get(i).bitmap = null;
            }
        }
        list.clear();
    }

    public static Rect getAllBitmapRect(List<BitmapIndex> list) {
        Rect rect = new Rect();
        if (list != null && list.size() > 0) {
            rect.set(list.get(0).bitmapRect);
            Iterator<BitmapIndex> it = list.iterator();
            while (it.hasNext()) {
                rect.union(it.next().bitmapRect);
            }
        }
        return rect;
    }

    public static Rect getAllBitmapIndexRect(List<BitmapIndex> list) {
        Rect rect = new Rect();
        if (list != null && list.size() > 0) {
            rect.set(list.get(0).mRect);
            for (int i = 1; i < list.size(); i++) {
                rect.union(list.get(i).mRect);
            }
        }
        return rect;
    }

    public static ScaleInfo cloneScaleInfo(ScaleInfo scaleInfo) {
        ScaleInfo scaleInfo2 = new ScaleInfo();
        scaleInfo2.setScaleRate(scaleInfo.getScaleRate());
        scaleInfo2.getTranslate().set(scaleInfo.getTranslate());
        scaleInfo2.getScaleCenter().set(scaleInfo.getScaleCenter());
        return scaleInfo2;
    }
}
