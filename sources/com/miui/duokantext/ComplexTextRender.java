package com.miui.duokantext;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import com.miui.notes.base.utils.Logger;
import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
public class ComplexTextRender extends BaseTextRender {
    private static final String DOT = ".";
    private static final String LOG_TAG = "ComplexTextRender";
    private long mNativeHandle;
    private RichText mText;

    private native void nativeDestroy();

    private native void nativeDrawTextByJString(Canvas canvas, String str);

    private native int nativeGetFirstLineBaseLine();

    private native int nativeGetMaxLines();

    private native int nativeGetPaddingBottom();

    private native int nativeGetPaddingLeft();

    private native int nativeGetPaddingRight();

    private native int nativeGetPaddingTop();

    private native void nativeInit();

    private native boolean nativeIsTruncate();

    private native void nativeMeasure(int i, int i2);

    private native void nativeRenderText(Bitmap bitmap, Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6);

    private native void nativeRequestLayout();

    private native void nativeReset();

    private native void nativeSetBoundingBox(int i, int i2, int i3, int i4);

    private native void nativeSetEllipsize(int i);

    private native void nativeSetMaxLines(int i);

    private native void nativeSetPadding(int i, int i2, int i3, int i4);

    private native void nativeSetWidth(int i);

    public ComplexTextRender() {
        nativeInit();
        this.mTextWidth = 1000;
        this.mTextHeight = 800;
    }

    @Override // com.miui.duokantext.BaseTextRender, com.miui.duokantext.TextRender
    public void drawText(Canvas canvas) {
        nativeDrawTextByJString(canvas, this.mText.getText());
    }

    public void setRichText(RichText richText) {
        this.mText = richText;
        nativeRequestLayout();
    }

    public RichText getRichText() {
        if (this.mText == null) {
            this.mText = new RichText();
        }
        return this.mText;
    }

    public void setTextWidth(int i) {
        nativeSetWidth(i);
    }

    @Override // com.miui.duokantext.BaseTextRender
    public void measure(int i, int i2) {
        nativeMeasure(i, i2);
    }

    public void saveToJPEGFile(String str, int i, int i2) throws IOException {
        setTextWidth(i2);
        int measuredTextWidth = getMeasuredTextWidth();
        int measuredTextHeight = getMeasuredTextHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(measuredTextWidth, measuredTextHeight < 800 ? measuredTextHeight : 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        int i3 = 0;
        Encoder encoderCreate = EncoderFactory.getInstance().create(false);
        encoderCreate.createEncoder(str, i, measuredTextWidth, measuredTextHeight);
        Paint paint = new Paint();
        paint.setColor(-1);
        encoderCreate.startCompress();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        int i4 = 0;
        while (i3 < measuredTextHeight) {
            canvas.save();
            Logger.INSTANCE.i(LOG_TAG, "Save to JPEG offset=" + i4 + ", cur_y=" + i3);
            canvas.translate(0.0f, -i3);
            Paint paint2 = paint;
            canvas.drawRect(0.0f, i3, measuredTextWidth, bitmapCreateBitmap.getHeight() + i3, paint2);
            drawText(canvas);
            int i5 = measuredTextHeight - i3;
            if (i5 > 800) {
                i5 = 800;
            }
            int i6 = i3 + i5;
            Logger.INSTANCE.i(LOG_TAG, "Save To JPEG height=" + i5);
            int[] iArr = new int[measuredTextWidth * i5];
            int i7 = i5;
            Encoder encoder = encoderCreate;
            bitmapCreateBitmap.getPixels(iArr, 0, measuredTextWidth, 0, 0, measuredTextWidth, i7);
            encoder.savePixels(iArr, i7);
            canvas.restore();
            i4 = i7;
            i3 = i6;
            encoderCreate = encoder;
            paint = paint2;
        }
        encoderCreate.finishCompress();
        if (bitmapCreateBitmap == null || bitmapCreateBitmap.isRecycled()) {
            return;
        }
        bitmapCreateBitmap.recycle();
    }

    @Override // com.miui.duokantext.BaseTextRender
    public void destroy() {
        nativeDestroy();
        this.mNativeHandle = 0L;
    }

    public void renderText(Bitmap bitmap, Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6) {
        nativeRenderText(bitmap, canvas, i, i2, i3, i4, i5, i6);
    }

    @Override // com.miui.duokantext.BaseTextRender
    public int getFirstLineBaseLine() {
        return nativeGetFirstLineBaseLine();
    }

    public void reset() {
        nativeReset();
    }

    public void setBoundintBox(int i, int i2, int i3, int i4) {
        nativeSetBoundingBox(i, i2, i3, i4);
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        nativeSetPadding(i, i2, i3, i4);
    }

    public int getPaddingLeft() {
        return nativeGetPaddingLeft();
    }

    public int getPaddingRight() {
        return nativeGetPaddingRight();
    }

    public void setMaxLines(int i) {
        nativeSetMaxLines(i);
    }

    public int getMaxLines() {
        return nativeGetMaxLines();
    }

    public boolean isTruncate() {
        return nativeIsTruncate();
    }

    public void setEllipsize(int i) {
        nativeSetEllipsize(i);
    }
}
