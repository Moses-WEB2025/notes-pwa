package com.miui.pc.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class PadMindPreviewTextView extends AppCompatTextView {
    private static final String ELLIPSIS_NORMAL = "…";
    private static final String EMPTY_TEXT = "";
    private static String sSearchToken = "";
    public boolean isContainTitle;
    private int mBodyStyle;
    private TextView.BufferType mBufferType;
    private boolean mGridMode;
    private int mLastParentMarginEnd;
    private int mLastParentMarginStart;
    private CharSequence mRawText;
    private boolean mStyleIsDirty;
    private int mTitleStyle;

    public PadMindPreviewTextView(Context context) {
        this(context, null);
    }

    public PadMindPreviewTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PadMindPreviewTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBufferType = TextView.BufferType.NORMAL;
        setLineSpacing(0.0f, 1.2f);
    }

    public void setTitleStyle(int i) {
        this.mTitleStyle = i;
    }

    public void setBodyStyle(int i) {
        this.mBodyStyle = i;
    }

    public void setGridMode(boolean z) {
        if (this.mGridMode != z) {
            this.mGridMode = z;
            this.mStyleIsDirty = true;
        }
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        this.mRawText = charSequence;
        this.mBufferType = bufferType;
        this.mStyleIsDirty = true;
        layoutText((getWidth() - getPaddingStart()) - getPaddingEnd());
        if (this.mStyleIsDirty) {
            super.setText("", bufferType);
        }
    }

    public void setSearchToken(String str) {
        sSearchToken = str;
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        int marginStart;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        View view = (View) getParent();
        int paddingEnd = 0;
        try {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (!this.mGridMode) {
                marginStart = ((LinearLayout.LayoutParams) layoutParams).getMarginStart();
                try {
                    paddingEnd = ((LinearLayout.LayoutParams) layoutParams).getMarginEnd();
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                }
            } else {
                int paddingStart = view.getPaddingStart();
                try {
                    paddingEnd = view.getPaddingEnd();
                    marginStart = paddingStart;
                } catch (Exception e2) {
                    e = e2;
                    marginStart = paddingStart;
                    e.printStackTrace();
                }
            }
        } catch (Exception e3) {
            e = e3;
            marginStart = paddingEnd;
        }
        if (marginStart != this.mLastParentMarginStart || paddingEnd != this.mLastParentMarginEnd) {
            this.mLastParentMarginStart = marginStart;
            this.mLastParentMarginEnd = paddingEnd;
            this.mStyleIsDirty = true;
        }
        if (this.mStyleIsDirty) {
            boolean z = this.mGridMode;
            if (!z && mode == 0 && mode2 == 0) {
                layoutText((View.MeasureSpec.getSize(i) - this.mLastParentMarginStart) - this.mLastParentMarginEnd);
            } else if (!z && mode == 1073741824 && mode2 == 0) {
                layoutText(View.MeasureSpec.getSize(i));
            } else if (z && mode == 1073741824 && mode2 == 0) {
                layoutText((View.MeasureSpec.getSize(i) - getPaddingStart()) - getPaddingEnd());
            }
        }
        super.onMeasure(i, i2);
    }

    private void layoutText(int i) {
        if (i > 0) {
            AbstractLayout gridLayout = this.mGridMode ? new GridLayout() : new ListLayout();
            gridLayout.isContainTitle(this.isContainTitle);
            super.setText(gridLayout.layoutText(getContext(), this.mRawText, getPaint(), this.mTitleStyle, this.mBodyStyle, i), this.mBufferType);
            this.mStyleIsDirty = false;
        }
    }

    public void setContainTitle(boolean z) {
        this.isContainTitle = z;
    }

    public static class PreviewTextSpan extends TextAppearanceSpan {
        public PreviewTextSpan(Context context, int i) {
            super(context, i);
        }

        public PreviewTextSpan(Parcel parcel) {
            super(parcel);
        }

        @Override // android.text.style.TextAppearanceSpan, android.text.style.MetricAffectingSpan
        public void updateMeasureState(TextPaint textPaint) {
            Typeface typefaceCreate;
            Typeface typeface = textPaint.getTypeface();
            int textStyle = getTextStyle();
            if (getFamily() != null) {
                typefaceCreate = Typeface.create(getFamily(), textStyle);
            } else if (typeface == null) {
                typefaceCreate = Typeface.defaultFromStyle(textStyle);
            } else {
                typefaceCreate = Typeface.create(typeface, textStyle);
            }
            int i = textStyle & (~typefaceCreate.getStyle());
            if ((i & 1) != 0) {
                textPaint.setFakeBoldText(true);
            }
            if ((i & 2) != 0) {
                textPaint.setTextSkewX(-0.25f);
            }
            textPaint.setTypeface(typefaceCreate);
            if (getTextSize() > 0) {
                textPaint.setTextSize(getTextSize());
            }
        }
    }

    private static class ListLayout extends AbstractLayout {
        boolean mContainTitle;

        private ListLayout() {
            super();
        }

        @Override // com.miui.pc.view.PadMindPreviewTextView.AbstractLayout
        protected CharSequence doLayoutText(Context context, CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3) {
            CharSequence[] charSequenceArrSplitText = splitText(charSequence);
            CharSequence charSequence2 = charSequenceArrSplitText[0];
            CharSequence charSequence3 = charSequenceArrSplitText[1];
            int length = charSequence2.length();
            PreviewTextSpan previewTextSpan = new PreviewTextSpan(context, i);
            TextPaint textPaint2 = new TextPaint();
            textPaint2.set(textPaint);
            PreviewTextSpan previewTextSpan2 = new PreviewTextSpan(context, i2);
            previewTextSpan2.updateMeasureState(textPaint2);
            previewTextSpan.updateMeasureState(textPaint);
            StaticLayout staticLayoutCreateStaticLayout = createStaticLayout(charSequence2, 0, length, textPaint, i3, TextUtils.TruncateAt.END, 1);
            int ellipsisStart = staticLayoutCreateStaticLayout.getEllipsisStart(0);
            if (ellipsisStart == 0) {
                ellipsisStart = staticLayoutCreateStaticLayout.getLineEnd(0);
            }
            int i4 = ellipsisStart;
            if (!TextUtils.isEmpty(charSequence2)) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                spannableStringBuilder.append(charSequence2, 0, charSequence2.length());
                spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
                return ContentUtils.changeHighLight(spannableStringBuilder, PadMindPreviewTextView.sSearchToken);
            }
            if (i4 == length) {
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
                spannableStringBuilder2.append(charSequence2);
                spannableStringBuilder2.setSpan(previewTextSpan, 0, spannableStringBuilder2.length(), 17);
                return ContentUtils.changeHighLight(spannableStringBuilder2, PadMindPreviewTextView.sSearchToken);
            }
            StaticLayout staticLayoutCreateStaticLayout2 = createStaticLayout(charSequence2, i4, length, textPaint2, i3, null, 3);
            int lineCount = staticLayoutCreateStaticLayout2.getLineCount();
            if (lineCount == 1) {
                SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder();
                spannableStringBuilder3.append(charSequence2.subSequence(0, Math.max(i4 - 2, 0))).append((CharSequence) PadMindPreviewTextView.ELLIPSIS_NORMAL);
                spannableStringBuilder3.setSpan(previewTextSpan, 0, spannableStringBuilder3.length(), 17);
                return ContentUtils.changeHighLight(spannableStringBuilder3, PadMindPreviewTextView.sSearchToken);
            }
            int i5 = lineCount == 2 ? 0 : 1;
            SpannableStringBuilder spannableStringBuilder4 = new SpannableStringBuilder();
            spannableStringBuilder4.append(charSequence2, 0, Math.max(i4 - 2, 0)).append((CharSequence) PadMindPreviewTextView.ELLIPSIS_NORMAL).append('\n');
            spannableStringBuilder4.setSpan(previewTextSpan, 0, Math.max(i4 - 1, 0), 17);
            int length2 = spannableStringBuilder4.length();
            spannableStringBuilder4.append((CharSequence) PadMindPreviewTextView.ELLIPSIS_NORMAL);
            spannableStringBuilder4.append(charSequence2, staticLayoutCreateStaticLayout2.getLineStart(i5), length);
            spannableStringBuilder4.setSpan(previewTextSpan2, length2, spannableStringBuilder4.length(), 17);
            return ContentUtils.changeHighLight(spannableStringBuilder4, PadMindPreviewTextView.sSearchToken);
        }

        protected CharSequence[] splitText(CharSequence charSequence) {
            CharSequence[] charSequenceArr = new CharSequence[2];
            int iIndexOf = charSequence.toString().indexOf(10);
            if (iIndexOf >= 0) {
                charSequenceArr[0] = charSequence.subSequence(0, iIndexOf);
                CharSequence charSequenceTrimEmptyLineSequence = TextProcessUtils.trimEmptyLineSequence(charSequence.subSequence(iIndexOf + 1, charSequence.length()));
                int iIndexOf2 = charSequenceTrimEmptyLineSequence.toString().indexOf(10);
                if (iIndexOf2 >= 0) {
                    charSequenceArr[1] = charSequenceTrimEmptyLineSequence.subSequence(0, iIndexOf2);
                    return charSequenceArr;
                }
                charSequenceArr[1] = charSequenceTrimEmptyLineSequence;
                return charSequenceArr;
            }
            charSequenceArr[0] = charSequence;
            charSequenceArr[1] = "";
            return charSequenceArr;
        }

        @Override // com.miui.pc.view.PadMindPreviewTextView.AbstractLayout
        public void isContainTitle(boolean z) {
            this.mContainTitle = z;
        }
    }

    private static class GridLayout extends AbstractLayout {
        boolean mContainTitle;

        private GridLayout() {
            super();
        }

        @Override // com.miui.pc.view.PadMindPreviewTextView.AbstractLayout
        protected CharSequence doLayoutText(Context context, CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3) {
            CharSequence leftLines;
            CharSequence firstLine = getFirstLine(charSequence);
            int length = firstLine.length();
            PreviewTextSpan previewTextSpan = new PreviewTextSpan(context, i);
            PreviewTextSpan previewTextSpan2 = new PreviewTextSpan(context, R.style.V12_TextAppearance_Grid_Blank);
            PreviewTextSpan previewTextSpan3 = new PreviewTextSpan(context, i2);
            int lineEnd = createStaticLayout(firstLine, 0, length, textPaint, i3, null, 1).getLineEnd(0);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            if (this.mContainTitle && lineEnd != firstLine.length()) {
                spannableStringBuilder.append(firstLine.subSequence(0, lineEnd - 1));
                spannableStringBuilder.append((CharSequence) "...");
                leftLines = getLeftLines(charSequence, firstLine.length());
            } else {
                spannableStringBuilder.append(firstLine.subSequence(0, lineEnd));
                leftLines = getLeftLines(charSequence, lineEnd);
            }
            if (!TextUtils.isEmpty(leftLines)) {
                spannableStringBuilder.append('\n');
                int length2 = spannableStringBuilder.length();
                spannableStringBuilder.setSpan(previewTextSpan, 0, length2 - 1, 17);
                spannableStringBuilder.append('\n');
                spannableStringBuilder.setSpan(previewTextSpan2, length2, spannableStringBuilder.length(), 17);
                int length3 = spannableStringBuilder.length();
                spannableStringBuilder.append(leftLines);
                spannableStringBuilder.setSpan(previewTextSpan3, length3, spannableStringBuilder.length(), 17);
            } else {
                spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
            }
            return ContentUtils.changeHighLight(spannableStringBuilder, PadMindPreviewTextView.sSearchToken);
        }

        private CharSequence getFirstLine(CharSequence charSequence) {
            int iIndexOf = charSequence.toString().indexOf(10);
            return iIndexOf >= 0 ? charSequence.subSequence(0, iIndexOf) : charSequence;
        }

        private CharSequence getLeftLines(CharSequence charSequence, int i) {
            return TextProcessUtils.trimEmptyLineSequence(charSequence.subSequence(i, charSequence.length()));
        }

        @Override // com.miui.pc.view.PadMindPreviewTextView.AbstractLayout
        public void isContainTitle(boolean z) {
            this.mContainTitle = z;
        }
    }

    private static abstract class AbstractLayout {
        protected abstract CharSequence doLayoutText(Context context, CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3);

        public abstract void isContainTitle(boolean z);

        private AbstractLayout() {
        }

        public CharSequence layoutText(Context context, CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3) {
            if (TextUtils.isEmpty(charSequence) || i3 <= 0) {
                return "";
            }
            return doLayoutText(context, charSequence, textPaint, i, i2, i3);
        }

        protected static StaticLayout createStaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, TextUtils.TruncateAt truncateAt, int i4) {
            return StaticLayout.Builder.obtain(charSequence, i, i2, textPaint, i3).setMaxLines(i4).setEllipsize(truncateAt).build();
        }
    }
}
