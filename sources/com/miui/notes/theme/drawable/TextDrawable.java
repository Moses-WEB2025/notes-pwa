package com.miui.notes.theme.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* JADX INFO: loaded from: classes4.dex */
public class TextDrawable extends Drawable {
    private static final String TAG = "TextDrawable";
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private Rect mTextBounds;
    private TextView mTextView;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    private TextDrawable(Context context) {
        TextView textView = new TextView(context);
        this.mTextView = textView;
        textView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
    }

    public static class Builder {
        private TextDrawable mTextDrawable;
        private float mTextSizeForAllText;
        private float[] mTextSizesForEachText;
        private String[] mTexts;

        public Builder(Context context) {
            this.mTextDrawable = new TextDrawable(context);
        }

        public Builder setTexts(String... strArr) {
            this.mTexts = strArr;
            return this;
        }

        public Builder setTextColor(int i) {
            this.mTextDrawable.mTextView.setTextColor(i);
            return this;
        }

        public Builder setTextSizes(float... fArr) {
            this.mTextSizesForEachText = fArr;
            return this;
        }

        public Builder setTextSize(float f) {
            this.mTextSizeForAllText = f;
            return this;
        }

        public Builder setTypeface(Typeface typeface) {
            this.mTextDrawable.mTextView.setTypeface(typeface);
            return this;
        }

        public Builder setTextGravity(int i) {
            this.mTextDrawable.mTextView.setGravity(i);
            return this;
        }

        public Builder setPadding(int i, int i2, int i3, int i4) {
            this.mTextDrawable.mPaddingLeft = i;
            this.mTextDrawable.mPaddingTop = i2;
            this.mTextDrawable.mPaddingRight = i3;
            this.mTextDrawable.mPaddingBottom = i4;
            return this;
        }

        public TextDrawable build() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            int i = 0;
            while (true) {
                String[] strArr = this.mTexts;
                if (i >= strArr.length) {
                    break;
                }
                String str = strArr[i];
                int length = spannableStringBuilder.length();
                spannableStringBuilder.append((CharSequence) str);
                if (this.mTextSizesForEachText != null) {
                    spannableStringBuilder.setSpan(new AbsoluteSizeSpan((int) this.mTextSizesForEachText[i]), length, spannableStringBuilder.length(), 17);
                }
                if (i != this.mTexts.length - 1) {
                    spannableStringBuilder.append('\n');
                }
                i++;
            }
            if (this.mTextSizesForEachText == null) {
                this.mTextDrawable.mTextView.setTextSize(0, this.mTextSizeForAllText);
            }
            this.mTextDrawable.mTextView.setText(spannableStringBuilder);
            return this.mTextDrawable;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        resolveTextBounds();
        return this.mTextBounds.width();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        resolveTextBounds();
        return this.mTextBounds.height();
    }

    private void resolveTextBounds() {
        if (this.mTextBounds == null) {
            this.mTextBounds = new Rect();
            this.mTextView.measure(0, 0);
            this.mTextBounds.set(0, 0, this.mTextView.getMeasuredWidth(), this.mTextView.getMeasuredHeight());
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        this.mTextView.measure(View.MeasureSpec.makeMeasureSpec(rect.width(), 1073741824), View.MeasureSpec.makeMeasureSpec(rect.height(), 1073741824));
        this.mTextView.layout(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        rect.set(this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int iSave = canvas.save();
        canvas.translate(this.mTextView.getLeft(), this.mTextView.getTop());
        this.mTextView.draw(canvas);
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mTextView.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mTextView.getPaint().setColorFilter(colorFilter);
    }
}
