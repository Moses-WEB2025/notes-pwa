package com.miui.notes.audio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class ShadowBubbleDrawable extends WaveBubbleDrawable {
    public ShadowBubbleDrawable(Context context, int i, int i2) {
        super(context, i, i2);
    }

    public ShadowBubbleDrawable(Context context, int i, int i2, int i3) {
        super(context, i, i2, i3);
    }

    public ShadowBubbleDrawable(Context context) {
        super(context);
    }

    @Override // com.miui.notes.audio.WaveBubbleDrawable, com.miui.notes.audio.BubbleDrawable
    protected void initBubblePadding(Context context) {
        super.initBubblePadding(context);
        this.mBubblePaddingTop = (int) context.getResources().getDimension(R.dimen.bubble_to_top);
        this.mBubblePaddingBottom = (int) context.getResources().getDimension(R.dimen.bubble_to_bottom);
        this.mBubblePaddingStart = (int) context.getResources().getDimension(R.dimen.bubble_to_start);
        this.mBubblePaddingEnd = (int) context.getResources().getDimension(R.dimen.bubble_to_end);
    }

    @Override // com.miui.notes.audio.BubbleDrawable
    protected void drawShadow(Canvas canvas) {
        super.drawShadow(canvas);
        Drawable drawable = this.mContext.getDrawable(R.drawable.ic_bubble_shadow_start);
        int intrinsicWidth = (int) (drawable.getIntrinsicWidth() / (drawable.getIntrinsicHeight() / this.mRectHeight));
        drawable.setBounds(this.mBubblePaddingStart, 0, intrinsicWidth, this.mRectHeight);
        drawable.draw(canvas);
        Drawable drawable2 = this.mContext.getDrawable(R.drawable.ic_bubble_shadow_end);
        int intrinsicWidth2 = (int) (drawable2.getIntrinsicWidth() / (drawable2.getIntrinsicHeight() / this.mRectHeight));
        drawable2.setBounds(this.mRectWidth - intrinsicWidth2, 0, this.mRectWidth, this.mRectHeight);
        drawable2.draw(canvas);
        Drawable drawable3 = this.mContext.getDrawable(R.drawable.ic_bubble_shadow_middle);
        drawable3.setBounds(intrinsicWidth, 0, this.mRectWidth - intrinsicWidth2, this.mRectHeight);
        drawable3.draw(canvas);
    }
}
