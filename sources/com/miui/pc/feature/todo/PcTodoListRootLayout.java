package com.miui.pc.feature.todo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoListRootLayout extends FrameLayout {
    private Paint mShaderPaint;
    private int mViewHeight;
    private int mViewWidth;

    public PcTodoListRootLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShaderPaint = new Paint();
        init(context);
    }

    private void init(Context context) {
        this.mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        this.mShaderPaint.setAntiAlias(true);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mViewWidth != getWidth() || this.mViewHeight != getHeight()) {
            this.mViewWidth = getWidth();
            this.mViewHeight = getHeight();
            this.mShaderPaint.setShader(new BitmapShader(drawableToBitmap(getBackground()), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        }
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, this.mViewWidth, this.mViewHeight, null);
        super.dispatchDraw(canvas);
        canvas.drawRect(0.0f, 0.0f, this.mViewWidth, this.mViewHeight, this.mShaderPaint);
        canvas.restoreToCount(iSaveLayer);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    private View getTouchTarget(View view, int i, int i2) {
        for (View view2 : view.getTouchables()) {
            if (isTouchPointInView(view2, i, i2)) {
                return view2;
            }
        }
        return null;
    }

    private boolean isTouchPointInView(View view, int i, int i2) {
        if (view == null) {
            return false;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i3 = iArr[0];
        int i4 = iArr[1];
        return i2 >= i4 && i2 <= view.getMeasuredHeight() + i4 && i >= i3 && i <= view.getMeasuredWidth() + i3;
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mViewWidth, this.mViewHeight, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, this.mViewWidth, this.mViewHeight);
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }
}
