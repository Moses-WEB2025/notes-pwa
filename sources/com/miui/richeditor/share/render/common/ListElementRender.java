package com.miui.richeditor.share.render.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.notes.R;
import com.miui.richeditor.constant.MiuiFontName;
import com.miui.richeditor.share.element.ListElement;
import com.miui.richeditor.share.utils.TextUtils;
import com.miui.richeditor.style.lite.EditorQuoteLiteSpan;
import com.miui.richeditor.style.lite.FontH3SizeLiteSpan;
import com.miui.richeditor.style.lite.FontLargeSizeLiteSpan;
import com.miui.richeditor.style.lite.FontMidSizeLiteSpan;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ListElementRender extends ElementRender<ListElement> {
    private float mDensity;
    private float mFontScale;
    private Drawable mListIcon;
    private int mMarginEnd;
    private TextView mOrderTextView;
    private TextElementRender mTextElementRender;
    private int mTextRelativeLeft;
    private int mTextRelativeTop;

    public ListElementRender(Context context, ListElement listElement, int i, int i2, long j) {
        super(context, listElement, i, i2, j);
        this.mMarginEnd = 0;
        this.mFontScale = context.getResources().getDisplayMetrics().scaledDensity;
        this.mDensity = context.getResources().getDisplayMetrics().density;
        if (listElement.isBullet() || listElement.isOrder()) {
            this.mMarginEnd = context.getResources().getDimensionPixelSize(R.dimen.note_edit_bullet_margin_end);
        } else {
            this.mMarginEnd = context.getResources().getDimensionPixelSize(R.dimen.note_edit_checkbox_margin_end);
        }
        layout(context);
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void draw(Canvas canvas) {
        if (((ListElement) this.mElement).isOrder()) {
            canvas.save();
            canvas.translate(this.mOrderTextView.getLeft(), this.mOrderTextView.getTop());
            this.mOrderTextView.draw(canvas);
            canvas.restore();
        } else {
            this.mListIcon.draw(canvas);
        }
        canvas.save();
        canvas.translate(this.mTextRelativeLeft, this.mTextRelativeTop);
        TextElementRender textElementRender = this.mTextElementRender;
        if (textElementRender != null) {
            textElementRender.draw(canvas);
        }
        canvas.restore();
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void destroy() {
        super.destroy();
        TextElementRender textElementRender = this.mTextElementRender;
        if (textElementRender != null) {
            textElementRender.destroy();
            this.mTextElementRender = null;
        }
        this.mOrderTextView = null;
    }

    /* JADX WARN: Code duplicated, block: B:44:0x0243  */
    /* JADX WARN: Code duplicated, block: B:47:0x0259  */
    /* JADX WARN: Code duplicated, block: B:48:0x025f  */
    /* JADX WARN: Code duplicated, block: B:51:0x026e  */
    /* JADX WARN: Code duplicated, block: B:53:0x0282  */
    private void layout(Context context) {
        int scale;
        int scale2;
        int marginLeft;
        int marginLeft2;
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) ((ListElement) this.mElement).getTextElement().getText();
        if (((ListElement) this.mElement).isOrder()) {
            TextView textView = new TextView(this.mContext);
            this.mOrderTextView = textView;
            textView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            this.mOrderTextView.setText(getOrderString(((ListElement) this.mElement).getIndent(), ((ListElement) this.mElement).getOrderNum(), spannableStringBuilder));
            this.mOrderTextView.setTextSize(0, ((ListElement) this.mElement).getTextElement().getFontSize());
            if (((EditorQuoteLiteSpan[]) spannableStringBuilder.getSpans(0, ((ListElement) this.mElement).getTextElement().getText().length(), EditorQuoteLiteSpan.class)).length > 0 || ((ListElement) this.mElement).isInQuote()) {
                this.mOrderTextView.setTextColor(TextUtils.getColorWithAlpha(0.5f, ((ListElement) this.mElement).getTextElement().getFontColor()));
            } else {
                this.mOrderTextView.setTextColor(((ListElement) this.mElement).getTextElement().getFontColor());
            }
            this.mOrderTextView.measure(View.MeasureSpec.makeMeasureSpec(-2, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(-2, Integer.MIN_VALUE));
            this.mOrderTextView.setScaleX(this.mFontScale / this.mDensity);
            scale = this.mOrderTextView.getMeasuredWidth();
            scale2 = this.mOrderTextView.getMeasuredHeight();
        } else {
            this.mListIcon = ((ListElement) this.mElement).getListIcon();
            if (((EditorQuoteLiteSpan[]) spannableStringBuilder.getSpans(0, ((ListElement) this.mElement).getTextElement().getText().length(), EditorQuoteLiteSpan.class)).length > 0 || ((ListElement) this.mElement).isInQuote()) {
                this.mListIcon.setAlpha(128);
            }
            scale = (int) getScale(this.mListIcon.getIntrinsicWidth(), spannableStringBuilder);
            scale2 = (int) getScale(this.mListIcon.getIntrinsicHeight(), spannableStringBuilder);
        }
        if (((ListElement) this.mElement).isSplitLine()) {
            marginLeft = ((ListElement) this.mElement).getMarginLeft();
            marginLeft2 = this.mWidth - ((ListElement) this.mElement).getMarginLeft();
        } else {
            if (((ListElement) this.mElement).isQuote()) {
                marginLeft = ((ListElement) this.mElement).getMarginLeft() - context.getResources().getDimensionPixelSize(R.dimen.share_quote_margin_right);
            } else {
                marginLeft = ((ListElement) this.mElement).getMarginLeft() + (getIndentMargin(context) * (((ListElement) this.mElement).getIndent() - 1));
            }
            marginLeft2 = marginLeft + scale;
        }
        if (((EditorQuoteLiteSpan[]) spannableStringBuilder.getSpans(0, ((ListElement) this.mElement).getTextElement().getText().length(), EditorQuoteLiteSpan.class)).length > 0 || ((ListElement) this.mElement).isInQuote()) {
            marginLeft += context.getResources().getDimensionPixelSize(R.dimen.share_quote_margin_right);
            marginLeft2 = marginLeft + scale;
        }
        this.mTextRelativeLeft = this.mMarginEnd + marginLeft2;
        this.mTextRelativeTop = ((ListElement) this.mElement).getMarginTop();
        TextElementRender textElementRender = new TextElementRender(this.mContext, ((ListElement) this.mElement).getTextElement(), 0, (this.mWidth - ((ListElement) this.mElement).getMarginRight()) - this.mTextRelativeLeft, this.mThemeId);
        this.mTextElementRender = textElementRender;
        int height = textElementRender.getHeight();
        Layout layout = this.mTextElementRender.getTextView().getLayout();
        Rect rect = new Rect();
        layout.getLineBounds(0, rect);
        rect.offset(this.mTextRelativeLeft + this.mTextElementRender.getTextView().getLeft(), this.mTextRelativeTop + this.mTextElementRender.getTextView().getTop());
        int lineBaseline = (int) (rect.top + layout.getLineBaseline(0) + (scale2 * (((ListElement) this.mElement).isOrder() ? 0.22f : 0.15f)));
        int i = lineBaseline - scale2;
        if (!((ListElement) this.mElement).isSplitLine()) {
            if (((ListElement) this.mElement).isQuote()) {
                lineBaseline += 10;
            }
            if (isRtl(layout)) {
                marginLeft2 = rect.right;
                marginLeft = marginLeft2 - scale;
                this.mTextRelativeLeft = (this.mTextRelativeLeft - scale) - this.mMarginEnd;
            }
            if (((ListElement) this.mElement).isOrder()) {
                this.mOrderTextView.layout(marginLeft, i, marginLeft2, lineBaseline);
            } else {
                this.mListIcon.setBounds(marginLeft, i, marginLeft2, lineBaseline);
            }
            if (((ListElement) this.mElement).isQuote()) {
                this.mHeight = ((ListElement) this.mElement).getMarginTop() + ((ListElement) this.mElement).getMarginBottom();
            } else {
                this.mHeight = ((ListElement) this.mElement).getMarginTop() + height + ((ListElement) this.mElement).getMarginBottom();
            }
        }
        lineBaseline -= 25;
        i = lineBaseline - scale2;
        if (isRtl(layout)) {
            marginLeft2 = rect.right;
            marginLeft = marginLeft2 - scale;
            this.mTextRelativeLeft = (this.mTextRelativeLeft - scale) - this.mMarginEnd;
        }
        if (((ListElement) this.mElement).isOrder()) {
            this.mOrderTextView.layout(marginLeft, i, marginLeft2, lineBaseline);
        } else {
            this.mListIcon.setBounds(marginLeft, i, marginLeft2, lineBaseline);
        }
        if (((ListElement) this.mElement).isQuote()) {
            this.mHeight = ((ListElement) this.mElement).getMarginTop() + ((ListElement) this.mElement).getMarginBottom();
        } else {
            this.mHeight = ((ListElement) this.mElement).getMarginTop() + height + ((ListElement) this.mElement).getMarginBottom();
        }
    }

    private int getIndentMargin(Context context) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.indent_share_margin);
        return ((ListElement) this.mElement).getMarginRight() > 250 ? dimensionPixelSize / 2 : dimensionPixelSize;
    }

    private float getScale(int i, SpannableStringBuilder spannableStringBuilder) {
        double d;
        double d2;
        float f = (i * this.mFontScale) / this.mDensity;
        if (((FontLargeSizeLiteSpan[]) spannableStringBuilder.getSpans(0, ((ListElement) this.mElement).getTextElement().getText().length(), FontLargeSizeLiteSpan.class)).length > 0) {
            d = f;
            d2 = 1.5d;
        } else if (((FontMidSizeLiteSpan[]) spannableStringBuilder.getSpans(0, ((ListElement) this.mElement).getTextElement().getText().length(), FontMidSizeLiteSpan.class)).length > 0) {
            d = f;
            d2 = 1.25d;
        } else {
            if (((FontH3SizeLiteSpan[]) spannableStringBuilder.getSpans(0, ((ListElement) this.mElement).getTextElement().getText().length(), FontH3SizeLiteSpan.class)).length <= 0) {
                return f;
            }
            d = f;
            d2 = 1.125d;
        }
        return (float) (d * d2);
    }

    private SpannableStringBuilder getOrderString(int i, int i2, SpannableStringBuilder spannableStringBuilder) {
        String strValueOf;
        if (i == 1 || i == 4) {
            strValueOf = String.valueOf(i2);
        } else if (i == 2 || i == 5) {
            strValueOf = TextUtils.getArb2Abc(i2 - 1);
        } else if (i != 3 && i != 6) {
            strValueOf = StringUtils.SPACE;
        } else {
            strValueOf = TextUtils.getArb2Roma(i2);
        }
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(strValueOf + ".");
        if (((FontLargeSizeLiteSpan[]) spannableStringBuilder.getSpans(0, ((ListElement) this.mElement).getTextElement().getText().length(), FontLargeSizeLiteSpan.class)).length > 0) {
            spannableStringBuilder2.setSpan(new AbsoluteSizeSpan((int) (((double) ((ListElement) this.mElement).getTextElement().getFontSize()) * 1.5d)), 0, spannableStringBuilder2.length(), 33);
            spannableStringBuilder2.setSpan(new TypefaceSpan(MiuiFontName.REGULAR), 0, spannableStringBuilder2.length(), 33);
            return spannableStringBuilder2;
        }
        if (((FontMidSizeLiteSpan[]) spannableStringBuilder.getSpans(0, ((ListElement) this.mElement).getTextElement().getText().length(), FontMidSizeLiteSpan.class)).length > 0) {
            spannableStringBuilder2.setSpan(new AbsoluteSizeSpan((int) (((double) ((ListElement) this.mElement).getTextElement().getFontSize()) * 1.25d)), 0, spannableStringBuilder2.length(), 33);
            spannableStringBuilder2.setSpan(new TypefaceSpan(MiuiFontName.REGULAR), 0, spannableStringBuilder2.length(), 33);
            return spannableStringBuilder2;
        }
        if (((FontH3SizeLiteSpan[]) spannableStringBuilder.getSpans(0, ((ListElement) this.mElement).getTextElement().getText().length(), FontH3SizeLiteSpan.class)).length > 0) {
            spannableStringBuilder2.setSpan(new AbsoluteSizeSpan((int) (((double) ((ListElement) this.mElement).getTextElement().getFontSize()) * 1.125d)), 0, spannableStringBuilder2.length(), 33);
            spannableStringBuilder2.setSpan(new TypefaceSpan(MiuiFontName.REGULAR), 0, spannableStringBuilder2.length(), 33);
        }
        return spannableStringBuilder2;
    }

    private boolean isRtl(Layout layout) {
        return layout.getParagraphDirection(0) == -1;
    }
}
