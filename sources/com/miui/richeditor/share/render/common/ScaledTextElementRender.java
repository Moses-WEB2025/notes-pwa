package com.miui.richeditor.share.render.common;

import android.content.Context;
import android.graphics.Canvas;
import com.miui.richeditor.share.element.ScaledTextElement;
import com.miui.richeditor.share.element.TextElement;

/* JADX INFO: loaded from: classes4.dex */
public class ScaledTextElementRender extends ElementRender<ScaledTextElement> {
    private TextElementRender mTextElementRender;

    public ScaledTextElementRender(Context context, ScaledTextElement scaledTextElement, int i, int i2, long j) {
        super(context, scaledTextElement, i, i2, j);
        layout();
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void draw(Canvas canvas) {
        TextElementRender textElementRender = this.mTextElementRender;
        if (textElementRender != null) {
            textElementRender.draw(canvas);
        }
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void destroy() {
        super.destroy();
        this.mTextElementRender.destroy();
        this.mTextElementRender = null;
    }

    private void layout() {
        TextElementRender textElementRender = new TextElementRender(this.mContext, (TextElement) this.mElement, 0, this.mWidth, this.mThemeId);
        this.mTextElementRender = textElementRender;
        this.mHeight = textElementRender.getHeight();
    }
}
