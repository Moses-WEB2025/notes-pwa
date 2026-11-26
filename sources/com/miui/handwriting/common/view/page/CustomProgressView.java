package com.miui.handwriting.common.view.page;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.miui.creation.common.R;

/* JADX INFO: loaded from: classes3.dex */
public class CustomProgressView extends View {
    private Paint paint;
    private final Path path;
    private int progress;
    private final RectF rectF;

    public CustomProgressView(Context context) {
        this(context, null);
    }

    public CustomProgressView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.rectF = new RectF();
        this.path = new Path();
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.paint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.paint.setAntiAlias(true);
        this.paint.setColor(ContextCompat.getColor(getContext(), R.color.creation_property_seekbar_blue));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        canvas.drawPath(this.path, this.paint);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(ContextCompat.getColor(getContext(), R.color.creation_property_seekbar_gary));
        this.paint.setStrokeWidth(4.0f);
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, (getWidth() / 2.0f) - 2.0f, this.paint);
        int i = this.progress;
        if (i > 0) {
            float f = (i * 360.0f) / 100.0f;
            float f2 = f <= 360.0f ? f : 360.0f;
            this.paint.setColor(ContextCompat.getColor(getContext(), R.color.creation_property_seekbar_blue));
            this.rectF.set(2.0f, 2.0f, getWidth() - 2, getHeight() - 2);
            canvas.drawArc(this.rectF, 270.0f, -f2, false, this.paint);
        }
    }

    public void reset() {
        this.progress = 0;
        invalidate();
    }

    public void update(int i) {
        this.progress = i;
        invalidate();
    }
}
