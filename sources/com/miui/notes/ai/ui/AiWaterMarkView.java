package com.miui.notes.ai.ui;

import android.accounts.Account;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.View;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.account.ExtraAccountManager;
import com.miui.notes.ai.utils.CryptoUtils;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class AiWaterMarkView extends View {
    private Paint mPaint;
    private String mText;
    private String mUserId;
    private int spaceX;
    private int spaceY;

    public AiWaterMarkView(Context context) {
        super(context);
        this.mText = "";
        this.mUserId = "";
        this.spaceX = 0;
        this.spaceY = 0;
        init();
    }

    public AiWaterMarkView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mText = "";
        this.mUserId = "";
        this.spaceX = 0;
        this.spaceY = 0;
        init();
    }

    public AiWaterMarkView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mText = "";
        this.mUserId = "";
        this.spaceX = 0;
        this.spaceY = 0;
        init();
    }

    public AiWaterMarkView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mText = "";
        this.mUserId = "";
        this.spaceX = 0;
        this.spaceY = 0;
        init();
    }

    private void init() {
        this.spaceX = (int) getContext().getResources().getDimension(R.dimen.ai_water_mark_space_x);
        this.spaceY = (int) getContext().getResources().getDimension(R.dimen.ai_water_mark_space_y);
        this.mText = getContext().getString(R.string.ai_text_watermark);
        this.mUserId = getAccountUserId();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(getResources().getColor(R.color.ai_water_mark_text_color, null));
        this.mPaint.setTextSize(getResources().getDimension(R.dimen.ai_water_mark_text_size));
        this.mPaint.setTextAlign(Paint.Align.CENTER);
    }

    private String getAccountUserId() {
        Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(getContext());
        if (xiaomiAccount != null) {
            String str = xiaomiAccount.name == null ? "" : xiaomiAccount.name;
            this.mUserId = str;
            try {
                this.mUserId = CryptoUtils.encrypt(str);
            } catch (Exception e) {
                e.printStackTrace();
                this.mUserId = new String(Base64.encode(this.mUserId.getBytes(), 0));
            }
        } else {
            Logger.INSTANCE.e("AiWaterMarkView", "showMarkView xiaomiAccount is null!!!");
        }
        return this.mUserId;
    }

    public void setUserId(String str) {
        this.mUserId = str;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int iMeasureText = (int) this.mPaint.measureText(this.mText);
        Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        int i = (int) (fontMetrics.bottom - fontMetrics.top);
        canvas.save();
        canvas.rotate(45.0f);
        int i2 = this.spaceX;
        double d = ((double) (iMeasureText + i2)) * 0.5d;
        double d2 = width;
        double d3 = -((-((d2 * 0.71d) / ((double) (iMeasureText + i2)))) * ((double) (i2 + iMeasureText)));
        while (d3 < (((double) (width + height)) * 0.71d) + d) {
            int i3 = ((int) ((-0.71d) * d2)) + i;
            boolean z = true;
            while (i3 < height) {
                boolean z2 = !z;
                double d4 = 0.0d;
                int i4 = width;
                int i5 = height;
                int i6 = i;
                canvas.drawText(this.mText, (float) (d3 - (!z ? 0.0d : d)), i3, this.mPaint);
                String str = this.mUserId;
                if (z) {
                    d4 = d;
                }
                int i7 = i3;
                canvas.drawText(str, (float) (d3 - d4), i7 + i6, this.mPaint);
                i3 = i7 + (i6 * 2) + this.spaceY;
                z = z2;
                i = i6;
                width = i4;
                height = i5;
            }
            d3 = d3 + ((double) iMeasureText) + ((double) this.spaceX);
            width = width;
            height = height;
        }
        canvas.restore();
    }
}
