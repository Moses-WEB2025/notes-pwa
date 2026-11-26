package com.miui.richeditor.share.render.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannedString;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseIntArray;
import com.miui.richeditor.constant.MiuiFontName;
import com.miui.richeditor.style.lite.EditorQuoteLiteSpan;
import com.miui.richeditor.style.lite.FontH3SizeLiteSpan;
import com.miui.richeditor.style.lite.FontLargeSizeLiteSpan;
import com.miui.richeditor.style.lite.FontMidSizeLiteSpan;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class JustifyRichTextView extends JustifyTextView {
    private static final int BOLD_FLAG = 1;
    private static final int DEFAULT_FLAG = 0;
    private static final int FOREGROUND_COLOR_FLAG = 512;
    private static final int H3_SIZE_FLAG = 16;
    private static final int ITALIC_FLAG = 2;
    private static final int LARGE_SIZE_FLAG = 4;
    private static final int MIDDLE_SIZE_FLAG = 8;
    private static final int QUOTE_FLAG = 128;
    private static final int RELATIVE_SIZE_FLAG = 256;
    private static final int STRIKETHROUGH_FLAG = 64;
    private static final String TAG = "JustifyRichTextView";
    private static final int UNDERLINE_FLAG = 32;
    private TextPaint mPaint;

    public JustifyRichTextView(Context context) {
        super(context);
    }

    @Override // com.miui.richeditor.share.render.common.JustifyTextView, android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        this.mPaint = getPaint();
        super.onDraw(canvas);
    }

    @Override // com.miui.richeditor.share.render.common.JustifyTextView
    protected float drawOffset(CharSequence charSequence, int i, int i2, float f, float f2, float f3, Canvas canvas, TextPaint textPaint, boolean z) {
        if (!(charSequence instanceof SpannedString)) {
            return super.drawOffset(charSequence, i, i2, f, f2, f3, canvas, textPaint, z);
        }
        if (i < 0 || i2 <= i) {
            return f;
        }
        if (z) {
            return drawRTLRange(canvas, charSequence, i, i2, f, f2, textPaint) - f3;
        }
        return drawLTRRange(canvas, charSequence, i, i2, f, f2, textPaint) + f3;
    }

    @Override // com.miui.richeditor.share.render.common.JustifyTextView
    protected void drawLastLine(Canvas canvas, CharSequence charSequence, int i, int i2, float f, float f2, TextPaint textPaint, boolean z) {
        if (!z && isContainRichText(charSequence, i, i2)) {
            drawLTRRange(canvas, charSequence, i, i2, f, f2, textPaint);
        } else {
            super.drawLastLine(canvas, charSequence, i, i2, f, f2, textPaint, z);
        }
    }

    private TextPaint getRichTextPaint(int i) {
        TextPaint textPaint = new TextPaint(this.mPaint);
        boolean z = (i & 1) == 1;
        boolean z2 = (i & 2) == 2;
        boolean z3 = (i & 4) == 4;
        boolean z4 = (i & 8) == 8;
        boolean z5 = (i & 16) == 16;
        boolean z6 = (i & 32) == 32;
        boolean z7 = (i & 64) == 64;
        boolean z8 = (i & 128) == 128;
        boolean z9 = (i & 256) == 256;
        boolean z10 = (i & 512) == 512;
        if (z3) {
            textPaint.setTextSize(this.mPaint.getTextSize() * 1.5f);
        } else if (z4) {
            textPaint.setTextSize(this.mPaint.getTextSize() * 1.25f);
        } else if (z5) {
            textPaint.setTextSize(this.mPaint.getTextSize() * 1.125f);
        } else if (z9) {
            textPaint.setTextSize(this.mPaint.getTextSize() * 0.8f);
        } else {
            textPaint.setTextSize(this.mPaint.getTextSize());
        }
        if (z && z2) {
            textPaint.setTypeface(Typeface.create(MiuiFontName.REGULAR, 3));
        } else if (z) {
            textPaint.setTypeface(Typeface.create(MiuiFontName.REGULAR, 1));
        } else if (z2) {
            textPaint.setTypeface(Typeface.create(MiuiFontName.REGULAR, 2));
        } else {
            textPaint.setTypeface(Typeface.create(MiuiFontName.REGULAR, 0));
        }
        if (z8) {
            textPaint.setAlpha(128);
        }
        if (z10) {
            textPaint.setColor(Color.argb(102, 0, 0, 0));
        }
        if (!textPaint.getTypeface().isItalic() && z2) {
            textPaint.setTextSkewX(-0.25f);
        }
        textPaint.setUnderlineText(z6);
        textPaint.setStrikeThruText(z7);
        return textPaint;
    }

    private float drawLTRRange(Canvas canvas, CharSequence charSequence, int i, int i2, float f, float f2, TextPaint textPaint) {
        SparseIntArray rangeFlags = getRangeFlags(charSequence, i, i2);
        int i3 = i;
        float f3 = f;
        while (i3 < i2) {
            int i4 = rangeFlags.get(i3);
            int i5 = i3 + 1;
            while (i5 < i2 && i4 == rangeFlags.get(i5)) {
                i5++;
            }
            float desiredWidth = StaticLayout.getDesiredWidth(charSequence, i3, i5, textPaint);
            canvas.drawText(charSequence, i3, i5, f3, f2, getRichTextPaint(i4));
            f3 += desiredWidth;
            i3 = i5;
        }
        return f3;
    }

    private float drawRTLRange(Canvas canvas, CharSequence charSequence, int i, int i2, float f, float f2, TextPaint textPaint) {
        SparseIntArray rangeFlags = getRangeFlags(charSequence, i, i2);
        int i3 = i;
        while (i3 < i2) {
            int i4 = rangeFlags.get(i3);
            int i5 = i3 + 1;
            while (i5 < i2 && i4 == rangeFlags.get(i5)) {
                i5++;
            }
            float desiredWidth = f - StaticLayout.getDesiredWidth(charSequence, i3, i5, textPaint);
            canvas.drawText(charSequence, i3, i5, desiredWidth, f2, getRichTextPaint(i4));
            i3 = i5;
            f = desiredWidth;
        }
        return f;
    }

    private SparseIntArray getRangeFlags(CharSequence charSequence, int i, int i2) {
        int i3;
        SparseIntArray sparseIntArray = new SparseIntArray();
        int i4 = i;
        while (true) {
            if (i4 >= i2) {
                break;
            }
            sparseIntArray.put(i4, 0);
            i4++;
        }
        SpannedString spannedString = (SpannedString) charSequence;
        for (FontLargeSizeLiteSpan fontLargeSizeLiteSpan : (FontLargeSizeLiteSpan[]) spannedString.getSpans(i, i2, FontLargeSizeLiteSpan.class)) {
            int spanEnd = spannedString.getSpanEnd(fontLargeSizeLiteSpan);
            for (int spanStart = spannedString.getSpanStart(fontLargeSizeLiteSpan); spanStart < spanEnd; spanStart++) {
                sparseIntArray.put(spanStart, sparseIntArray.get(spanStart) | 4);
            }
        }
        for (FontMidSizeLiteSpan fontMidSizeLiteSpan : (FontMidSizeLiteSpan[]) spannedString.getSpans(i, i2, FontMidSizeLiteSpan.class)) {
            int spanEnd2 = spannedString.getSpanEnd(fontMidSizeLiteSpan);
            for (int spanStart2 = spannedString.getSpanStart(fontMidSizeLiteSpan); spanStart2 < spanEnd2; spanStart2++) {
                sparseIntArray.put(spanStart2, sparseIntArray.get(spanStart2) | 8);
            }
        }
        for (FontH3SizeLiteSpan fontH3SizeLiteSpan : (FontH3SizeLiteSpan[]) spannedString.getSpans(i, i2, FontH3SizeLiteSpan.class)) {
            int spanEnd3 = spannedString.getSpanEnd(fontH3SizeLiteSpan);
            for (int spanStart3 = spannedString.getSpanStart(fontH3SizeLiteSpan); spanStart3 < spanEnd3; spanStart3++) {
                sparseIntArray.put(spanStart3, sparseIntArray.get(spanStart3) | 16);
            }
        }
        for (EditorQuoteLiteSpan editorQuoteLiteSpan : (EditorQuoteLiteSpan[]) spannedString.getSpans(i, i2, EditorQuoteLiteSpan.class)) {
            int spanEnd4 = spannedString.getSpanEnd(editorQuoteLiteSpan);
            for (int spanStart4 = spannedString.getSpanStart(editorQuoteLiteSpan); spanStart4 < spanEnd4; spanStart4++) {
                sparseIntArray.put(spanStart4, sparseIntArray.get(spanStart4) | 128);
            }
        }
        for (StyleSpan styleSpan : (StyleSpan[]) spannedString.getSpans(i, i2, StyleSpan.class)) {
            int spanStart5 = spannedString.getSpanStart(styleSpan);
            int spanEnd5 = spannedString.getSpanEnd(styleSpan);
            if (styleSpan.getStyle() == 1) {
                while (spanStart5 < spanEnd5) {
                    sparseIntArray.put(spanStart5, sparseIntArray.get(spanStart5) | 1);
                    spanStart5++;
                }
            } else if (styleSpan.getStyle() == 2) {
                while (spanStart5 < spanEnd5) {
                    sparseIntArray.put(spanStart5, sparseIntArray.get(spanStart5) | 2);
                    spanStart5++;
                }
            }
        }
        for (UnderlineSpan underlineSpan : (UnderlineSpan[]) spannedString.getSpans(i, i2, UnderlineSpan.class)) {
            int spanEnd6 = spannedString.getSpanEnd(underlineSpan);
            for (int spanStart6 = spannedString.getSpanStart(underlineSpan); spanStart6 < spanEnd6; spanStart6++) {
                sparseIntArray.put(spanStart6, sparseIntArray.get(spanStart6) | 32);
            }
        }
        for (StrikethroughSpan strikethroughSpan : (StrikethroughSpan[]) spannedString.getSpans(i, i2, StrikethroughSpan.class)) {
            int spanEnd7 = spannedString.getSpanEnd(strikethroughSpan);
            for (int spanStart7 = spannedString.getSpanStart(strikethroughSpan); spanStart7 < spanEnd7; spanStart7++) {
                sparseIntArray.put(spanStart7, sparseIntArray.get(spanStart7) | 64);
            }
        }
        for (RelativeSizeSpan relativeSizeSpan : (RelativeSizeSpan[]) spannedString.getSpans(i, i2, RelativeSizeSpan.class)) {
            int spanEnd8 = spannedString.getSpanEnd(relativeSizeSpan);
            for (int spanStart8 = spannedString.getSpanStart(relativeSizeSpan); spanStart8 < spanEnd8; spanStart8++) {
                sparseIntArray.put(spanStart8, sparseIntArray.get(spanStart8) | 256);
            }
        }
        for (ForegroundColorSpan foregroundColorSpan : (ForegroundColorSpan[]) spannedString.getSpans(i, i2, ForegroundColorSpan.class)) {
            int spanEnd9 = spannedString.getSpanEnd(foregroundColorSpan);
            for (int spanStart9 = spannedString.getSpanStart(foregroundColorSpan); spanStart9 < spanEnd9; spanStart9++) {
                sparseIntArray.put(spanStart9, sparseIntArray.get(spanStart9) | 512);
            }
        }
        return sparseIntArray;
    }

    @Override // com.miui.richeditor.share.render.common.JustifyTextView
    protected float drawGroup(CharSequence charSequence, List<Integer> list, List<Integer> list2, int i, int i2, float f, float f2, float f3, Canvas canvas, TextPaint textPaint) {
        if (charSequence instanceof SpannedString) {
            if (list2.isEmpty()) {
                return f;
            }
            int size = list2.size();
            int size2 = list.size();
            float fDrawGroupRTLRange = f;
            for (int i3 = size - 1; i3 >= 0; i3--) {
                int iIntValue = list2.get(i3).intValue();
                fDrawGroupRTLRange = drawGroupRTLRange(canvas, charSequence, list.get(iIntValue).intValue(), iIntValue < size2 + (-1) ? list.get(iIntValue + 1).intValue() : i2, fDrawGroupRTLRange, f2, textPaint) - f3;
            }
            return fDrawGroupRTLRange;
        }
        return super.drawGroup(charSequence, list, list2, i, i2, f, f2, f3, canvas, textPaint);
    }

    private float drawGroupRTLRange(Canvas canvas, CharSequence charSequence, int i, int i2, float f, float f2, TextPaint textPaint) {
        SparseIntArray rangeFlags = getRangeFlags(charSequence, i, i2);
        SparseIntArray sparseIntArray = new SparseIntArray();
        while (i < i2) {
            int i3 = rangeFlags.get(i);
            int i4 = i + 1;
            while (i4 < i2 && i3 == rangeFlags.get(i4)) {
                i4++;
            }
            sparseIntArray.put(i, i4);
            i = i4;
        }
        int size = sparseIntArray.size() - 1;
        float f3 = f;
        while (size >= 0) {
            int iKeyAt = sparseIntArray.keyAt(size);
            int iValueAt = sparseIntArray.valueAt(size);
            float desiredWidth = f3 - StaticLayout.getDesiredWidth(charSequence, iKeyAt, iValueAt, textPaint);
            canvas.drawText(charSequence, iKeyAt, iValueAt, desiredWidth, f2, getRichTextPaint(rangeFlags.get(iKeyAt)));
            size--;
            f3 = desiredWidth;
        }
        return f3;
    }

    @Override // com.miui.richeditor.share.render.common.JustifyTextView
    protected float lastLineX(CharSequence charSequence, int i, int i2, boolean z, float f, float f2) {
        return (z && isContainRichText(charSequence, i, i2)) ? f2 : super.lastLineX(charSequence, i, i2, z, f, f2);
    }

    @Override // com.miui.richeditor.share.render.common.JustifyTextView
    protected boolean canDrawLastLineDirectly(CharSequence charSequence, int i, int i2, boolean z) {
        if (z && isContainRichText(charSequence, i, i2)) {
            return false;
        }
        return super.canDrawLastLineDirectly(charSequence, i, i2, z);
    }

    private boolean isContainRichText(CharSequence charSequence, int i, int i2) {
        if (!(charSequence instanceof SpannedString)) {
            return false;
        }
        SpannedString spannedString = (SpannedString) charSequence;
        return ((FontLargeSizeLiteSpan[]) spannedString.getSpans(i, i2, FontLargeSizeLiteSpan.class)).length > 0 || ((FontMidSizeLiteSpan[]) spannedString.getSpans(i, i2, FontMidSizeLiteSpan.class)).length > 0 || ((FontH3SizeLiteSpan[]) spannedString.getSpans(i, i2, FontH3SizeLiteSpan.class)).length > 0 || ((StyleSpan[]) spannedString.getSpans(i, i2, StyleSpan.class)).length > 0 || ((UnderlineSpan[]) spannedString.getSpans(i, i2, UnderlineSpan.class)).length > 0 || ((StrikethroughSpan[]) spannedString.getSpans(i, i2, StrikethroughSpan.class)).length > 0 || ((EditorQuoteLiteSpan[]) spannedString.getSpans(i, i2, EditorQuoteLiteSpan.class)).length > 0 || ((RelativeSizeSpan[]) spannedString.getSpans(i, i2, RelativeSizeSpan.class)).length > 0 || ((ForegroundColorSpan[]) spannedString.getSpans(i, i2, ForegroundColorSpan.class)).length > 0;
    }
}
