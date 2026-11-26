package com.miui.libhandwritingpage.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageData;

/* JADX INFO: loaded from: classes3.dex */
public class BackgroundRender {
    private static final String TAG = "BackgroundRender";

    public static void drawBackground(Context context, Canvas canvas, PageData pageData, RectF rectF, Bitmap bitmap) {
        try {
            if (TextUtils.isEmpty(pageData.pdfId)) {
                canvas.drawColor(pageData.bgColor);
                canvasDrawLine(context, canvas, pageData.bgConfig, pageData.width, pageData.height, pageData.bgGrid, rectF);
                canvasDrawImage(canvas, bitmap, rectF);
            }
        } catch (Exception unused) {
        }
    }

    public static void drawDashHorizontal(BgGridConfiguration bgGridConfiguration, float f, RectF rectF, RectF rectF2, RectF rectF3, Paint paint, Canvas canvas) {
        float f2 = bgGridConfiguration.pointInterval * f;
        float f3 = bgGridConfiguration.pointRadius * f;
        float fWidth = (((rectF.width() - rectF3.left) + rectF3.right) / f2) + 1.0f;
        float fHeight = (((rectF.height() - rectF3.top) + rectF3.bottom) / f2) + 1.0f;
        rectF2.set(rectF2.left + rectF3.left, rectF2.top + rectF3.top, rectF2.right - rectF3.right, rectF2.bottom - rectF3.bottom);
        paint.setColor(bgGridConfiguration.pointColor);
        paint.setStrokeWidth(f3);
        paint.setPathEffect(null);
        int i = 0;
        while (true) {
            float f4 = i;
            if (f4 > fWidth) {
                return;
            }
            int i2 = 0;
            while (true) {
                float f5 = i2;
                if (f5 <= fHeight) {
                    canvas.drawCircle(rectF2.left + (f2 * f4), rectF2.top + (f5 * f2), f3, paint);
                    i2++;
                }
            }
            i++;
        }
    }

    public static void drawLineVertical(BgGridConfiguration bgGridConfiguration, float f, float f2, float f3, float f4, RectF rectF, Paint paint, Canvas canvas) {
        float f5 = bgGridConfiguration.dottedLine[0] * f;
        float f6 = bgGridConfiguration.dottedLine[1] * f;
        if (bgGridConfiguration.isDottedLine) {
            Path path = new Path();
            RectF rectF2 = new RectF(0.0f, 0.0f, f5, f2);
            float f7 = f2 * 2.0f;
            path.addRoundRect(rectF2, f7, f7, Path.Direction.CW);
            paint.setPathEffect(new PathDashPathEffect(path, f5 + f6, 0.0f, PathDashPathEffect.Style.TRANSLATE));
        } else {
            paint.setPathEffect(null);
        }
        if (bgGridConfiguration.isShieldLine) {
            int i = 1;
            while (true) {
                float f8 = i;
                if (f8 >= f4) {
                    return;
                }
                float f9 = f8 * f3;
                canvas.drawLine(rectF.left, rectF.top + f9, rectF.right, rectF.top + f9, paint);
                i++;
            }
        } else {
            int i2 = 0;
            while (true) {
                float f10 = i2;
                if (f10 > f4) {
                    return;
                }
                float f11 = f10 * f3;
                canvas.drawLine(rectF.left, rectF.top + f11, rectF.right, rectF.top + f11, paint);
                i2++;
            }
        }
    }

    public static void drawLineHorizontal(BgGridConfiguration bgGridConfiguration, float f, float f2, float f3, float f4, RectF rectF, Paint paint, Canvas canvas) {
        float f5 = bgGridConfiguration.dottedLine[0] * f;
        float f6 = bgGridConfiguration.dottedLine[1] * f;
        if (bgGridConfiguration.isDottedLine) {
            Path path = new Path();
            RectF rectF2 = new RectF(0.0f, 0.0f, f2, f5);
            float f7 = f2 * 2.0f;
            path.addRoundRect(rectF2, f7, f7, Path.Direction.CW);
            paint.setPathEffect(new PathDashPathEffect(path, f5 + f6, 0.0f, PathDashPathEffect.Style.TRANSLATE));
        } else {
            paint.setPathEffect(null);
        }
        if (bgGridConfiguration.isShieldLine) {
            int i = 1;
            while (true) {
                float f8 = i;
                if (f8 >= f4) {
                    return;
                }
                float f9 = f8 * f3;
                canvas.drawLine(rectF.left + f9, rectF.top, rectF.left + f9, rectF.bottom, paint);
                i++;
            }
        } else {
            int i2 = 0;
            while (true) {
                float f10 = i2;
                if (f10 > f4) {
                    return;
                }
                float f11 = f10 * f3;
                canvas.drawLine(rectF.left + f11, rectF.top, rectF.left + f11, rectF.bottom, paint);
                i2++;
            }
        }
    }

    public static void canvasDrawLine(Context context, Canvas canvas, BgGridConfiguration bgGridConfiguration, int i, int i2, int i3, RectF rectF) {
        float f;
        float f2;
        if (bgGridConfiguration == null) {
            bgGridConfiguration = new BgGridConfiguration();
        }
        BgGridConfiguration bgGridConfiguration2 = bgGridConfiguration;
        if (i3 > 0) {
            if (context != null) {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
                i = displayMetrics.widthPixels;
                i2 = displayMetrics.heightPixels;
            }
            float fWidth = rectF.width() / Math.min(i, i2);
            float f3 = bgGridConfiguration2.lineInterval * fWidth;
            float f4 = bgGridConfiguration2.lineWidth * fWidth;
            Matrix matrix = new Matrix();
            matrix.setScale(fWidth, fWidth);
            RectF rectF2 = new RectF();
            matrix.mapRect(rectF2, bgGridConfiguration2.lineMargin);
            RectF rectF3 = new RectF();
            matrix.mapRect(rectF3, bgGridConfiguration2.pointMargin);
            float fWidth2 = (((rectF.width() - rectF2.left) + rectF2.right) / f3) + 1.0f;
            float fHeight = (((rectF.height() - rectF2.top) + rectF2.bottom) / f3) + 1.0f;
            RectF rectF4 = new RectF(0.0f, 0.0f, rectF.width(), rectF.height());
            Paint paint = new Paint();
            if (i3 == 4) {
                drawDashHorizontal(bgGridConfiguration2, fWidth, rectF, rectF4, rectF3, paint, canvas);
                return;
            }
            Canvas canvas2 = canvas;
            Paint paint2 = paint;
            rectF4.set(rectF4.left + rectF2.left, rectF4.top + rectF2.top, rectF4.right - rectF2.right, rectF4.bottom - rectF2.bottom);
            paint2.setColor(bgGridConfiguration2.lineColor);
            paint2.setStrokeWidth(f4);
            if (i3 == 1 || i3 == 3) {
                f = f3;
                f2 = f4;
                drawLineVertical(bgGridConfiguration2, fWidth, f2, f, fHeight, rectF4, paint2, canvas2);
                paint2 = paint2;
                canvas2 = canvas2;
            } else {
                f = f3;
                f2 = f4;
            }
            if (i3 == 2 || i3 == 3) {
                drawLineHorizontal(bgGridConfiguration2, fWidth, f2, f, fWidth2, rectF4, paint2, canvas2);
            }
        }
    }

    public static void canvasDrawImage(Canvas canvas, Bitmap bitmap, RectF rectF) {
        Paint paint = new Paint();
        if (bitmap != null) {
            Matrix matrix = new Matrix();
            float fWidth = rectF.width() / bitmap.getWidth();
            matrix.setScale(fWidth, fWidth);
            canvas.drawBitmap(bitmap, matrix, paint);
        }
    }
}
