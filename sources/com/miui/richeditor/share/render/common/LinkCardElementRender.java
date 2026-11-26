package com.miui.richeditor.share.render.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import com.miui.notes.R;
import com.miui.richeditor.share.element.LinkCardElement;
import com.miui.richeditor.style.LinkCardSpanHelper;

/* JADX INFO: loaded from: classes4.dex */
public class LinkCardElementRender extends ElementRender<LinkCardElement> {
    private LinkCardSpanHelper mDrawHelper;
    private Rect mDrawRect;
    private StaticLayout mLinkTextLayout;
    private StaticLayout mTitleTextLayout;

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void draw(Canvas canvas) {
    }

    public LinkCardElementRender(Context context, LinkCardElement linkCardElement, int i, int i2, long j) {
        super(context, linkCardElement, i, i2, j);
        this.mDrawHelper = null;
        this.mDrawRect = new Rect();
        this.mTitleTextLayout = null;
        this.mLinkTextLayout = null;
        layout();
    }

    public void setDrawHelper(LinkCardSpanHelper linkCardSpanHelper) {
        this.mDrawHelper = linkCardSpanHelper;
        linkCardSpanHelper.setImageRadius(((LinkCardElement) getElement()).getRadius());
        updateTextContent();
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void render(Canvas canvas) {
        canvas.drawBitmap(createBitmap(), ((LinkCardElement) this.mElement).getMarginLeft(), ((LinkCardElement) this.mElement).getMarginTop(), (Paint) null);
    }

    private void layout() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.link_card_span_height);
        this.mHeight = ((LinkCardElement) this.mElement).getMarginTop() + dimensionPixelSize + ((LinkCardElement) this.mElement).getMarginBottom();
        this.mWidth = (this.mWidth - ((LinkCardElement) this.mElement).getMarginLeft()) - ((LinkCardElement) this.mElement).getMarginRight();
        this.mDrawRect.set(0, 0, getWidth(), dimensionPixelSize);
    }

    private void updateTextContent() {
        if (this.mDrawHelper == null) {
            return;
        }
        this.mTitleTextLayout = createStaticLayout(((LinkCardElement) this.mElement).getTitle(), 0, ((LinkCardElement) this.mElement).getTitle().length(), this.mDrawHelper.getTitlePaint(), this.mDrawHelper.getTextContentWidth(this.mDrawRect), TextUtils.TruncateAt.END, 1);
        this.mLinkTextLayout = createStaticLayout(((LinkCardElement) this.mElement).getLink(), 0, ((LinkCardElement) this.mElement).getLink().length(), this.mDrawHelper.getLinkPaint(), this.mDrawHelper.getTextContentWidth(this.mDrawRect), TextUtils.TruncateAt.END, 1);
    }

    private Bitmap createBitmap() {
        Rect rect = this.mDrawRect;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        this.mDrawHelper.drawBg(rect, canvas, false, 1.0f, 255);
        this.mDrawHelper.drawSpanIcon(rect, canvas, 1.0f, 255);
        this.mDrawHelper.drawWebTitle(this.mTitleTextLayout, rect, canvas, 1.0f, 255);
        this.mDrawHelper.drawWebLink(this.mLinkTextLayout, rect, canvas, 1.0f, 255);
        return bitmapCreateBitmap;
    }

    private StaticLayout createStaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, TextUtils.TruncateAt truncateAt, int i4) {
        return StaticLayout.Builder.obtain(charSequence, i, i2, textPaint, i3).setMaxLines(i4).setEllipsize(truncateAt).setLineSpacing(10.0f, 1.0f).build();
    }
}
