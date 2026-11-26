package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import com.miui.blur.sdk.backdrop.BackdropBlurLinearLayout;
import miuix.popupwidget.R;

/* JADX INFO: loaded from: classes3.dex */
public class ArrowPopupContentWrapper extends BackdropBlurLinearLayout {
    private Bitmap mMask1;
    private Bitmap mMask2;
    private Bitmap mMask3;
    private Bitmap mMask4;
    private Paint mPaint;

    public ArrowPopupContentWrapper(Context context) {
        this(context, null);
    }

    public ArrowPopupContentWrapper(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ArrowPopupContentWrapper(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        this.mPaint.setAntiAlias(true);
        Resources resources = getResources();
        this.mMask1 = BitmapFactory.decodeResource(resources, R.drawable.miuix_appcompat_popup_mask_1);
        this.mMask2 = BitmapFactory.decodeResource(resources, R.drawable.miuix_appcompat_popup_mask_2);
        this.mMask3 = BitmapFactory.decodeResource(resources, R.drawable.miuix_appcompat_popup_mask_3);
        this.mMask4 = BitmapFactory.decodeResource(resources, R.drawable.miuix_appcompat_popup_mask_4);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null, 31);
        super.dispatchDraw(canvas);
        canvas.drawBitmap(this.mMask1, getPaddingLeft(), getPaddingTop(), this.mPaint);
        canvas.drawBitmap(this.mMask2, (getWidth() - this.mMask2.getWidth()) - getPaddingRight(), getPaddingTop(), this.mPaint);
        canvas.drawBitmap(this.mMask3, getPaddingLeft(), (getHeight() - this.mMask3.getHeight()) - getPaddingBottom(), this.mPaint);
        canvas.drawBitmap(this.mMask4, (getWidth() - this.mMask4.getWidth()) - getPaddingRight(), (getHeight() - this.mMask4.getHeight()) - getPaddingBottom(), this.mPaint);
        canvas.restore();
    }
}
