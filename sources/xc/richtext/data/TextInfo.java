package xc.richtext.data;

import android.graphics.RectF;

/* JADX INFO: loaded from: classes6.dex */
public class TextInfo {
    private RectF mRectF;
    private final String mText;
    private final int mTextColor;
    private final float mTextSize;

    public TextInfo(RectF rectF, int i, float f, String str) {
        this.mRectF = rectF;
        this.mTextColor = i;
        this.mTextSize = f;
        this.mText = str;
    }

    public RectF getRectF() {
        return this.mRectF;
    }

    public void setRectF(RectF rectF) {
        this.mRectF = rectF;
    }

    public float getTextSize() {
        return this.mTextSize;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public String getText() {
        return this.mText;
    }
}
