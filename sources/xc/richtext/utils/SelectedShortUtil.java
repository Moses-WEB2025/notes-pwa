package xc.richtext.utils;

import android.graphics.Paint;
import android.os.LocaleList;
import android.text.TextPaint;

/* JADX INFO: loaded from: classes2.dex */
public class SelectedShortUtil {
    public Paint dPaint;
    public float dx;
    public float dy;
    public Paint fPaint;
    public boolean mIsDark;
    public TextPaint mTextPaint;

    public SelectedShortUtil(boolean z) {
        this.mIsDark = z;
        Paint paint = new Paint();
        this.dPaint = paint;
        paint.setAntiAlias(true);
        this.dPaint.setDither(true);
        TextPaint textPaint = new TextPaint();
        this.mTextPaint = textPaint;
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mTextPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextLocales(LocaleList.getDefault());
        Paint paint2 = new Paint();
        this.fPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
    }
}
