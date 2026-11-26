package com.miui.richeditor.share.render.common;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class JustifyTextView extends AppCompatTextView {
    private static final String TAG = "JustifyTextView";

    protected boolean canDrawLastLineDirectly(CharSequence charSequence, int i, int i2, boolean z) {
        return true;
    }

    protected float lastLineX(CharSequence charSequence, int i, int i2, boolean z, float f, float f2) {
        return f;
    }

    public JustifyTextView(Context context) {
        super(context);
    }

    public JustifyTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public JustifyTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        float fLastLineX;
        int i;
        int i2;
        float f;
        boolean zCanDrawLastLineDirectly;
        Layout layout;
        int i3;
        int i4;
        JustifyTextView justifyTextView = this;
        CharSequence text = justifyTextView.getText();
        TextPaint paint = justifyTextView.getPaint();
        paint.setColor(justifyTextView.getCurrentTextColor());
        Layout layout2 = justifyTextView.getLayout();
        int lineCount = layout2.getLineCount();
        int width = (justifyTextView.getWidth() - justifyTextView.getCompoundPaddingStart()) - justifyTextView.getCompoundPaddingRight();
        int i5 = 0;
        while (i5 < lineCount) {
            boolean z = layout2.getParagraphDirection(i5) == -1;
            float width2 = z ? justifyTextView.getWidth() - justifyTextView.getCompoundPaddingRight() : justifyTextView.getCompoundPaddingLeft();
            float lineBaseline = layout2.getLineBaseline(i5);
            int lineStart = layout2.getLineStart(i5);
            int lineEnd = layout2.getLineEnd(i5);
            boolean zIsLastLineOfParagraph = justifyTextView.isLastLineOfParagraph(text, lineStart, lineEnd);
            if (zIsLastLineOfParagraph) {
                i = lineStart;
                i2 = lineEnd;
                fLastLineX = justifyTextView.lastLineX(text, i, i2, z, layout2.getLineLeft(i5), layout2.getLineRight(i5));
                zCanDrawLastLineDirectly = justifyTextView.canDrawLastLineDirectly(text, i, i2, z);
                f = lineBaseline;
                if (zCanDrawLastLineDirectly) {
                    boolean z2 = z;
                    CharSequence charSequence = text;
                    justifyTextView.drawLastLine(canvas, charSequence, i, i2, fLastLineX, f, paint, z2);
                    text = charSequence;
                    i = i;
                    i2 = i2;
                    z = z2;
                }
            } else {
                fLastLineX = width2;
                i = lineStart;
                i2 = lineEnd;
                f = lineBaseline;
                zCanDrawLastLineDirectly = false;
            }
            if (zCanDrawLastLineDirectly) {
                layout = layout2;
                i3 = width;
                i4 = i5;
            } else {
                i3 = width;
                TextPaint textPaint = paint;
                float f2 = f;
                float f3 = fLastLineX;
                int i6 = i2;
                i4 = i5;
                boolean z3 = z;
                int i7 = i;
                layout = layout2;
                drawTextLine(text, layout, i4, i7, i6, f3, f2, i3, canvas, textPaint, z3, zIsLastLineOfParagraph);
                paint = textPaint;
            }
            i5 = i4 + 1;
            justifyTextView = this;
            layout2 = layout;
            width = i3;
        }
    }

    protected void drawLastLine(Canvas canvas, CharSequence charSequence, int i, int i2, float f, float f2, TextPaint textPaint, boolean z) {
        canvas.drawText(charSequence, i, i2, f, f2, textPaint);
    }

    private boolean isLastLineOfParagraph(CharSequence charSequence, int i, int i2) {
        return (i2 > i && charSequence.charAt(i2 + (-1)) == '\n') || i2 == charSequence.length();
    }

    private void drawTextLine(CharSequence charSequence, Layout layout, int i, int i2, int i3, float f, float f2, int i4, Canvas canvas, TextPaint textPaint, boolean z, boolean z2) {
        float fDrawOffset;
        ArrayList arrayList = new ArrayList();
        List<Integer> lineOffsets = getLineOffsets(charSequence, layout, i, i2, i3);
        int size = lineOffsets.size();
        float lineWidth = (i4 - layout.getLineWidth(i)) / size;
        if (z2) {
            lineWidth = 0.0f;
        }
        float f3 = lineWidth;
        float f4 = f;
        int i5 = 0;
        while (i5 < size) {
            int iIntValue = lineOffsets.get(i5).intValue();
            int iIntValue2 = i5 < size + (-1) ? lineOffsets.get(i5 + 1).intValue() : i3;
            if (z) {
                if (layout.isRtlCharAt(iIntValue)) {
                    float fDrawGroup = drawGroup(charSequence, lineOffsets, arrayList, i2, i3, f4, f2, f3, canvas, textPaint);
                    arrayList.clear();
                    fDrawOffset = drawOffset(charSequence, iIntValue, iIntValue2, fDrawGroup, f2, f3, canvas, textPaint, z);
                } else {
                    i5 = i5;
                    arrayList.add(Integer.valueOf(i5));
                }
                i5++;
            } else {
                fDrawOffset = drawOffset(charSequence, iIntValue, iIntValue2, f4, f2, f3, canvas, textPaint, z);
            }
            f4 = fDrawOffset;
            i5++;
        }
        if (arrayList.isEmpty()) {
            return;
        }
        drawGroup(charSequence, lineOffsets, arrayList, i2, i3, f4, f2, f3, canvas, textPaint);
    }

    protected float drawOffset(CharSequence charSequence, int i, int i2, float f, float f2, float f3, Canvas canvas, TextPaint textPaint, boolean z) {
        if (i < 0 || i2 <= i) {
            return f;
        }
        float desiredWidth = StaticLayout.getDesiredWidth(charSequence, i, i2, textPaint);
        canvas.drawText(charSequence, i, i2, z ? f - desiredWidth : f, f2, textPaint);
        return z ? f - (desiredWidth + f3) : f + desiredWidth + f3;
    }

    protected float drawGroup(CharSequence charSequence, List<Integer> list, List<Integer> list2, int i, int i2, float f, float f2, float f3, Canvas canvas, TextPaint textPaint) {
        if (list2.isEmpty()) {
            return f;
        }
        int size = list2.size();
        int size2 = list.size();
        float f4 = f;
        for (int i3 = size - 1; i3 >= 0; i3--) {
            int iIntValue = list2.get(i3).intValue();
            int iIntValue2 = list.get(iIntValue).intValue();
            int iIntValue3 = iIntValue < size2 + (-1) ? list.get(iIntValue + 1).intValue() : i2;
            float desiredWidth = StaticLayout.getDesiredWidth(charSequence, iIntValue2, iIntValue3, textPaint);
            canvas.drawText(charSequence, iIntValue2, iIntValue3, f4 - desiredWidth, f2, textPaint);
            f4 -= desiredWidth + f3;
        }
        return f4;
    }

    private List<Integer> getLineOffsets(CharSequence charSequence, Layout layout, int i, int i2, int i3) {
        String string = charSequence.toString();
        ArrayList arrayList = new ArrayList();
        int paragraphDirection = layout.getParagraphDirection(i);
        int iIndexOf = i2;
        while (iIndexOf >= i2 && iIndexOf < i3) {
            arrayList.add(Integer.valueOf(iIndexOf));
            if (layout.isRtlCharAt(iIndexOf)) {
                if (charSequence.charAt(iIndexOf) == ' ') {
                    iIndexOf++;
                } else {
                    iIndexOf = string.indexOf(32, iIndexOf + 1);
                    if (iIndexOf < 0) {
                        iIndexOf = i3;
                    }
                }
            } else if (paragraphDirection == 1) {
                int offsetToRightOf = layout.getOffsetToRightOf(iIndexOf);
                boolean zIsAsciiTextOnly = isAsciiTextOnly(charSequence, iIndexOf, offsetToRightOf);
                int i4 = iIndexOf;
                while (offsetToRightOf < i3 && zIsAsciiTextOnly) {
                    int offsetToRightOf2 = layout.getOffsetToRightOf(offsetToRightOf);
                    boolean zIsAsciiTextOnly2 = isAsciiTextOnly(charSequence, offsetToRightOf, offsetToRightOf2);
                    i4 = offsetToRightOf;
                    offsetToRightOf = offsetToRightOf2;
                    zIsAsciiTextOnly = zIsAsciiTextOnly2;
                }
                iIndexOf = (i4 == iIndexOf || zIsAsciiTextOnly) ? offsetToRightOf : i4;
            } else {
                int offsetToLeftOf = layout.getOffsetToLeftOf(iIndexOf);
                boolean z = false;
                while (offsetToLeftOf < iIndexOf) {
                    offsetToLeftOf = layout.getOffsetToLeftOf(offsetToLeftOf);
                    z = true;
                }
                if (z && arrayList.size() > 1) {
                    arrayList.remove(arrayList.size() - 1);
                }
                iIndexOf = offsetToLeftOf;
            }
        }
        return arrayList;
    }

    private boolean isAsciiTextOnly(CharSequence charSequence, int i, int i2) {
        while (i < i2) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt <= ' ' || cCharAt >= 127) {
                return false;
            }
            i++;
        }
        return true;
    }
}
