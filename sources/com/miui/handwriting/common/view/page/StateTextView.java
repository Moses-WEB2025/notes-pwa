package com.miui.handwriting.common.view.page;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.miui.creation.common.R;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes3.dex */
public class StateTextView extends View {
    private Paint mPaint;
    private String mText;

    public StateTextView(Context context) {
        super(context);
        this.mText = StringUtils.SPACE;
        init();
    }

    public StateTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mText = StringUtils.SPACE;
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(ContextCompat.getColor(getContext(), R.color.creation_page_index_color));
        this.mPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.creation_page_index_text_size));
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode == Integer.MIN_VALUE) {
            Rect rect = new Rect();
            Paint paint = this.mPaint;
            String str = this.mText;
            paint.getTextBounds(str, 0, str.length(), rect);
            size = rect.width() + getPaddingLeft() + getPaddingRight() + 100;
        }
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i);
        if (mode2 == Integer.MIN_VALUE) {
            Rect rect2 = new Rect();
            Paint paint2 = this.mPaint;
            String str2 = this.mText;
            paint2.getTextBounds(str2, 0, str2.length(), rect2);
            size2 = rect2.height() + getPaddingTop() + getPaddingBottom() + 10;
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
        canvas.drawText(this.mText, paddingLeft, (getHeight() / 2) + (((fontMetricsInt.bottom - fontMetricsInt.top) / 2) - fontMetricsInt.bottom), this.mPaint);
    }

    public void setText(String str) {
        this.mText = str;
        requestLayout();
        invalidate();
    }
}
