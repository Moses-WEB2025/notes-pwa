package xc.richtext.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.LocaleList;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes2.dex */
public class ScreenShortUtil {
    public Paint mBitmapPaint;
    public String mHightText = null;
    public int mId = -1;
    public Paint mPathPaint;
    public Paint mRectPaint;
    public TextPaint mTextPaint;

    public ScreenShortUtil() {
        Paint paint = new Paint();
        this.mBitmapPaint = paint;
        paint.setAntiAlias(true);
        this.mBitmapPaint.setDither(true);
        Paint paint2 = new Paint();
        this.mRectPaint = paint2;
        paint2.setAntiAlias(true);
        this.mRectPaint.setDither(true);
        this.mRectPaint.setColor(-16776961);
        Paint paint3 = new Paint();
        this.mPathPaint = paint3;
        paint3.setStyle(Paint.Style.FILL);
        TextPaint textPaint = new TextPaint();
        this.mTextPaint = textPaint;
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mTextPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextLocales(LocaleList.getDefault());
    }

    public void screenToBitmap(Bitmap bitmap, RectF rectF, List<SpannedData> list, boolean z, int i, ScreenRenderProvider screenRenderProvider) {
        screenToBitmap(bitmap, rectF, list, z, i, false, screenRenderProvider);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0158  */
    public void screenToBitmap(Bitmap bitmap, RectF rectF, List<SpannedData> list, boolean z, int i, boolean z2, ScreenRenderProvider screenRenderProvider) {
        float f;
        float f2;
        Canvas canvas = new Canvas(bitmap);
        for (SpannedData spannedData : list) {
            float f3 = 0.0f;
            if (i == 2 || spannedData.getType() != 11 || TextUtils.isEmpty(spannedData.getText())) {
                f3 = 0.0f;
            } else {
                RectF drawRectF = spannedData.getDrawRectF(screenRenderProvider);
                this.mTextPaint.setTextSize(spannedData.getRealTextSize());
                this.mTextPaint.setColor(CsUtil.getRealTextColor(spannedData.mTextColor, z));
                StaticLayout.Builder builderObtainBuilder = new TextLayoutUtil().obtainBuilder(spannedData, z);
                if (builderObtainBuilder != null) {
                    StaticLayout staticLayoutBuild = builderObtainBuilder.build();
                    RectF drawRectF2 = spannedData.getDrawRectF(screenRenderProvider);
                    float f4 = spannedData.mDegrees;
                    if (f4 != 0.0f) {
                        RectF rectF2 = new RectF(drawRectF);
                        Matrix matrix = new Matrix();
                        matrix.setRotate(f4, drawRectF2.centerX(), drawRectF2.centerY());
                        matrix.mapRect(rectF2);
                        matrix.reset();
                        RectF rectF3 = new RectF(drawRectF);
                        Matrix matrix2 = new Matrix();
                        matrix2.setRotate(f4, drawRectF2.width() / 2.0f, drawRectF2.height() / 2.0f);
                        matrix2.mapRect(rectF3);
                        matrix2.reset();
                        f = (-rectF.left) + (rectF3.left - rectF2.left);
                        f2 = (-rectF.top) + (rectF3.top - rectF2.top);
                    } else {
                        f = -rectF.left;
                        f2 = -rectF.top;
                    }
                    if (spannedData.mType == 11) {
                        canvas.save();
                        Path rotatedRectPath = SpannedUtils.getRotatedRectPath(drawRectF2, spannedData.mDegrees, drawRectF2.centerX(), drawRectF2.centerY());
                        rotatedRectPath.offset(-rectF.left, -rectF.top);
                        this.mPathPaint.setColor(spannedData.mBgColor);
                        this.mPathPaint.setTypeface(Typeface.DEFAULT_BOLD);
                        canvas.drawPath(rotatedRectPath, this.mPathPaint);
                        canvas.restore();
                    }
                    float fWidth = spannedData.getDrawRectF(screenRenderProvider).width() / staticLayoutBuild.getWidth();
                    ArrayList arrayList = new ArrayList();
                    int i2 = this.mId;
                    if (i2 == -1 || i2 == spannedData.mId) {
                        arrayList.addAll(TextLayoutUtil.genSearchTextPath(staticLayoutBuild, this.mHightText));
                    }
                    canvas.save();
                    canvas.translate((int) (drawRectF2.left + f), (int) (drawRectF2.top + f2));
                    canvas.rotate(spannedData.mDegrees, drawRectF2.centerX(), drawRectF2.centerY());
                    canvas.scale(fWidth, fWidth);
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        canvas.drawRect((RectF) it.next(), this.mRectPaint);
                    }
                    staticLayoutBuild.draw(canvas);
                    canvas.restore();
                } else {
                    f3 = 0.0f;
                }
            }
            if (i != 1 && spannedData.getType() == 7) {
                RectF drawRectF3 = spannedData.getDrawRectF(screenRenderProvider);
                Matrix matrix3 = new Matrix();
                float f5 = !z2 ? spannedData.mDegrees : f3;
                matrix3.postRotate(f5, drawRectF3.centerX(), drawRectF3.centerY());
                matrix3.mapRect(drawRectF3);
                drawRectF3.offset(-rectF.left, -rectF.top);
                Bitmap drawBitmap = SpannedUtils.getDrawBitmap(spannedData.mBitmapPath, drawRectF3.width());
                if (drawBitmap != null) {
                    canvas.save();
                    drawRectF3.set((int) spannedData.getDrawRectF(screenRenderProvider).left, (int) spannedData.getDrawRectF(screenRenderProvider).top, (int) spannedData.getDrawRectF(screenRenderProvider).right, (int) spannedData.getDrawRectF(screenRenderProvider).bottom);
                    drawRectF3.offset(-((int) rectF.left), -((int) rectF.top));
                    if (f5 != f3) {
                        canvas.rotate(f5, drawRectF3.centerX(), drawRectF3.centerY());
                    }
                    canvas.drawBitmap(drawBitmap, new Rect(0, 0, drawBitmap.getWidth(), drawBitmap.getHeight()), drawRectF3, this.mBitmapPaint);
                    canvas.restore();
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x015f  */
    /* JADX WARN: Code duplicated, block: B:45:0x023e  */
    public void screenToViewAndMaskBitmap(Bitmap bitmap, Bitmap bitmap2, RectF rectF, List<SpannedData> list, boolean z, int i, int i2, boolean z2, ScreenRenderProvider screenRenderProvider) {
        float f;
        float f2;
        Canvas canvas = new Canvas(bitmap);
        Canvas canvas2 = new Canvas(bitmap2);
        for (SpannedData spannedData : list) {
            float f3 = 0.0f;
            if (i == 2 || spannedData.getType() != 11 || TextUtils.isEmpty(spannedData.getText())) {
                f3 = 0.0f;
            } else {
                RectF drawRectF = spannedData.getDrawRectF(screenRenderProvider);
                this.mTextPaint.setTextSize(spannedData.getRealTextSize());
                this.mTextPaint.setColor(CsUtil.getRealTextColor(spannedData.mTextColor, z));
                StaticLayout.Builder builderObtainBuilder = new TextLayoutUtil().obtainBuilder(spannedData, z);
                if (builderObtainBuilder != null) {
                    StaticLayout staticLayoutBuild = builderObtainBuilder.build();
                    RectF drawRectF2 = spannedData.getDrawRectF(screenRenderProvider);
                    float f4 = spannedData.mDegrees;
                    if (f4 != 0.0f) {
                        RectF rectF2 = new RectF(drawRectF);
                        Matrix matrix = new Matrix();
                        matrix.setRotate(f4, drawRectF2.centerX(), drawRectF2.centerY());
                        matrix.mapRect(rectF2);
                        matrix.reset();
                        RectF rectF3 = new RectF(drawRectF);
                        Matrix matrix2 = new Matrix();
                        matrix2.setRotate(f4, drawRectF2.width() / 2.0f, drawRectF2.height() / 2.0f);
                        matrix2.mapRect(rectF3);
                        matrix2.reset();
                        f = (-rectF.left) + (rectF3.left - rectF2.left);
                        f2 = (-rectF.top) + (rectF3.top - rectF2.top);
                    } else {
                        f = -rectF.left;
                        f2 = -rectF.top;
                    }
                    if (spannedData.mType == 11) {
                        canvas.save();
                        Path rotatedRectPath = SpannedUtils.getRotatedRectPath(drawRectF2, spannedData.mDegrees, drawRectF2.centerX(), drawRectF2.centerY());
                        rotatedRectPath.offset(-rectF.left, -rectF.top);
                        this.mPathPaint.setColor(spannedData.mBgColor);
                        this.mPathPaint.setTypeface(Typeface.DEFAULT_BOLD);
                        canvas.drawPath(rotatedRectPath, this.mPathPaint);
                        canvas.restore();
                    }
                    float fWidth = spannedData.getDrawRectF(screenRenderProvider).width() / staticLayoutBuild.getWidth();
                    ArrayList arrayList = new ArrayList();
                    int i3 = this.mId;
                    if (i3 == -1 || i3 == spannedData.mId) {
                        arrayList.addAll(TextLayoutUtil.genSearchTextPath(staticLayoutBuild, this.mHightText));
                    }
                    canvas.save();
                    canvas.translate((int) (drawRectF2.left + f), (int) (drawRectF2.top + f2));
                    canvas.rotate(spannedData.mDegrees, drawRectF2.centerX(), drawRectF2.centerY());
                    canvas.scale(fWidth, fWidth);
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        canvas.drawRect((RectF) it.next(), this.mRectPaint);
                    }
                    staticLayoutBuild.draw(canvas);
                    canvas.restore();
                } else {
                    f3 = 0.0f;
                }
            }
            if (i != 1 && spannedData.getType() == 7) {
                RectF drawRectF3 = spannedData.getDrawRectF(screenRenderProvider);
                Matrix matrix3 = new Matrix();
                float f5 = !z2 ? spannedData.mDegrees : f3;
                matrix3.postRotate(f5, drawRectF3.centerX(), drawRectF3.centerY());
                matrix3.mapRect(drawRectF3);
                drawRectF3.offset(-rectF.left, -rectF.top);
                Bitmap drawBitmap = SpannedUtils.getDrawBitmap(spannedData.mBitmapPath, drawRectF3.width());
                if (drawBitmap != null) {
                    Bitmap bitmapCreateSolidColorBitmap = createSolidColorBitmap(drawBitmap.getWidth() + 6, drawBitmap.getHeight() + 6, i2);
                    canvas.save();
                    canvas2.save();
                    drawRectF3.set((int) spannedData.getDrawRectF(screenRenderProvider).left, (int) spannedData.getDrawRectF(screenRenderProvider).top, (int) spannedData.getDrawRectF(screenRenderProvider).right, (int) spannedData.getDrawRectF(screenRenderProvider).bottom);
                    drawRectF3.offset(-((int) rectF.left), -((int) rectF.top));
                    if (f5 != f3) {
                        canvas.rotate(f5, drawRectF3.centerX(), drawRectF3.centerY());
                        canvas2.rotate(f5, drawRectF3.centerX(), drawRectF3.centerY());
                    }
                    canvas.drawBitmap(drawBitmap, new Rect(0, 0, drawBitmap.getWidth(), drawBitmap.getHeight()), drawRectF3, this.mBitmapPaint);
                    float f6 = 3;
                    canvas2.drawBitmap(bitmapCreateSolidColorBitmap, new Rect(0, 0, bitmapCreateSolidColorBitmap.getWidth(), bitmapCreateSolidColorBitmap.getHeight()), new RectF(drawRectF3.left - f6, drawRectF3.top - f6, drawRectF3.right + f6, drawRectF3.bottom + f6), (Paint) null);
                    canvas.restore();
                    canvas2.restore();
                }
            }
        }
    }

    public Bitmap createSolidColorBitmap(int i, int i2, int i3) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.eraseColor(i3);
        return bitmapCreateBitmap;
    }
}
