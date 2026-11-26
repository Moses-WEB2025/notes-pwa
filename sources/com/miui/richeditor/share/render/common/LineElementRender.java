package com.miui.richeditor.share.render.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.miui.richeditor.share.element.LineElement;

/* JADX INFO: loaded from: classes4.dex */
public class LineElementRender extends ElementRender<LineElement> {
    private Paint mPaint;

    public LineElementRender(Context context, LineElement lineElement, int i, int i2, long j) {
        super(context, lineElement, i, i2, j);
        layout();
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void draw(Canvas canvas) {
        canvas.drawLine(((LineElement) this.mElement).getMarginLeft(), ((LineElement) this.mElement).getMarginTop(), this.mWidth - ((LineElement) this.mElement).getMarginRight(), ((LineElement) this.mElement).getMarginTop() + ((LineElement) this.mElement).getStrokeWidth(), this.mPaint);
    }

    private void layout() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(((LineElement) this.mElement).getColor());
        this.mPaint.setStrokeWidth(((LineElement) this.mElement).getStrokeWidth());
        this.mHeight = ((LineElement) this.mElement).getStrokeWidth() + ((LineElement) this.mElement).getMarginTop() + ((LineElement) this.mElement).getMarginBottom();
    }
}
