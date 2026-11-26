package com.miui.pc.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes4.dex */
public class SharpView extends View {
    private Context mContext;
    private int mHeight;
    private int mWidth;

    public SharpView(Context context) {
        super(context);
        this.mWidth = 0;
        this.mHeight = 0;
        this.mContext = context;
        initView();
    }

    public SharpView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWidth = 0;
        this.mHeight = 0;
        this.mContext = context;
        initView();
    }

    private void initView() {
        this.mWidth = 15;
        this.mHeight = 8;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.moveTo(0.0f, this.mHeight);
        path.lineTo(this.mWidth, this.mHeight);
        path.lineTo(this.mWidth / 2, 0.0f);
        path.close();
        canvas.drawPath(path, paint);
    }
}
