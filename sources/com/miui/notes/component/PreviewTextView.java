package com.miui.notes.component;

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
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.miui.aiengine.common.result.SSEConst;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class PreviewTextView extends AppCompatTextView implements AsyncLayout.LayoutCallBack {
    private static final String ELLIPSIS_NORMAL = "…";
    private static final String EMPTY_TEXT = "";
    public static final char MAX_HIGH_SURROGATE = 56319;
    public static final char MIN_HIGH_SURROGATE = 55296;
    public static final String TAG = "PreviewText";
    private static String sSearchToken = "";
    private AsyncLayout asyncLayout;
    public boolean isContainExtraContent;
    public boolean isContainTitle;
    CharSequence lastText;
    private int mBodyStyle;
    private TextView.BufferType mBufferType;
    private boolean mGridMode;
    private int mLastParentMarginEnd;
    private int mLastParentMarginStart;
    private int mLastViewWidth;
    private CharSequence mRawText;
    private int mStubStyle;
    private boolean mStyleIsDirty;
    private int mTitleStyle;

    private static boolean isEmojiCharacter(char c) {
        return 55296 <= c && 56319 >= c;
    }

    public PreviewTextView(Context context) {
        this(context, null);
    }

    public PreviewTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PreviewTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBufferType = TextView.BufferType.NORMAL;
        this.lastText = "";
    }

    public void setTitleStyle(int i) {
        this.mTitleStyle = i;
    }

    public void setBodyStyle(int i) {
        this.mBodyStyle = i;
        this.mStubStyle = i;
    }

    public void setStubStyle(int i) {
        this.mStubStyle = i;
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
        int width = (getWidth() - getPaddingStart()) - getPaddingEnd();
        layoutText(width);
        if (width <= 0) {
            super.setText("", bufferType);
        }
    }

    public void setSearchToken(String str) {
        sSearchToken = str;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x0086  */
    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        int marginStart;
        int paddingEnd;
        int size;
        int paddingEnd2;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        View view = (View) getParent();
        int size2 = 0;
        try {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (!this.mGridMode) {
                marginStart = marginLayoutParams.getMarginStart();
                try {
                    paddingEnd = marginLayoutParams.getMarginEnd();
                } catch (Exception unused) {
                    paddingEnd = 0;
                }
            } else {
                int paddingStart = view.getPaddingStart();
                try {
                    paddingEnd = view.getPaddingEnd();
                    marginStart = paddingStart;
                } catch (Exception unused2) {
                    marginStart = paddingStart;
                    paddingEnd = 0;
                }
            }
        } catch (Exception unused3) {
            marginStart = 0;
        }
        if (marginStart != this.mLastParentMarginStart || paddingEnd != this.mLastParentMarginEnd) {
            this.mLastParentMarginStart = marginStart;
            this.mLastParentMarginEnd = paddingEnd;
            this.mStyleIsDirty = true;
        }
        boolean z = this.mGridMode;
        if (!z && mode == 0 && mode2 == 0) {
            size = View.MeasureSpec.getSize(i) - this.mLastParentMarginStart;
            paddingEnd2 = this.mLastParentMarginEnd;
        } else {
            if (!z && mode == 1073741824 && mode2 == 0) {
                size2 = View.MeasureSpec.getSize(i);
            } else if (z && mode == 1073741824 && mode2 == 0) {
                size = View.MeasureSpec.getSize(i) - getPaddingStart();
                paddingEnd2 = getPaddingEnd();
            }
            if (!this.mStyleIsDirty && this.mLastViewWidth != size2 && size2 != 0) {
                this.mStyleIsDirty = true;
            }
            if (this.mStyleIsDirty) {
                layoutText(size2);
            }
            super.onMeasure(i, i2);
        }
        size2 = size - paddingEnd2;
        if (!this.mStyleIsDirty) {
            this.mStyleIsDirty = true;
        }
        if (this.mStyleIsDirty) {
            layoutText(size2);
        }
        super.onMeasure(i, i2);
    }

    private void layoutText(int i) {
        int i2 = this.mLastViewWidth;
        this.mLastViewWidth = i;
        if (i > 0) {
            AbstractLayout gridLayout = this.mGridMode ? new GridLayout() : new ListLayout();
            gridLayout.setContainTitle(this.isContainTitle);
            gridLayout.setContainExtraContent(this.isContainExtraContent);
            if (!this.mGridMode) {
                if (!RomUtils.isPadMode() && LiteUtils.isNewLiteOrMiddle()) {
                    if (this.asyncLayout == null) {
                        this.asyncLayout = new AsyncLayout(this);
                    }
                    this.asyncLayout.cancel();
                    try {
                        transitText(i, i2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.asyncLayout.layoutText(gridLayout, this.mRawText, getPaint(), this.mTitleStyle, this.mBodyStyle, this.mStubStyle, i);
                    this.mStyleIsDirty = false;
                    return;
                }
                super.setText(gridLayout.layoutText(getContext(), this.mRawText, getPaint(), this.mTitleStyle, this.mBodyStyle, this.mStubStyle, i), this.mBufferType);
                this.mStyleIsDirty = false;
                return;
            }
            super.setText(gridLayout.layoutText(getContext(), this.mRawText, getPaint(), this.mTitleStyle, this.mBodyStyle, this.mStubStyle, i), this.mBufferType);
            this.mStyleIsDirty = false;
        }
    }

    private void transitText(int i, int i2) {
        if (TextUtils.isEmpty(this.lastText) || !(this.lastText instanceof SpannableStringBuilder) || !TextUtils.isEmpty(sSearchToken) || i == i2) {
            return;
        }
        StringBuilder sb = new StringBuilder(this.lastText);
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) this.lastText;
        int iLastIndexOf = sb.lastIndexOf(SSEConst.MSG_SPLIT);
        if (iLastIndexOf > 8) {
            if (getPaint().measureText(sb.subSequence(0, iLastIndexOf), 0, iLastIndexOf) > i) {
                spannableStringBuilder.delete(iLastIndexOf - 2, iLastIndexOf);
                super.setText(spannableStringBuilder, this.mBufferType);
            }
        }
    }

    @Override // com.miui.notes.component.AsyncLayout.LayoutCallBack
    public void callBack(CharSequence charSequence, CharSequence charSequence2) {
        if (TextUtils.equals(this.mRawText, charSequence2)) {
            this.lastText = charSequence;
            super.setText(charSequence, this.mBufferType);
        }
    }

    public void cancelLayoutText() {
        AsyncLayout asyncLayout = this.asyncLayout;
        if (asyncLayout != null) {
            asyncLayout.cancel();
        }
    }

    public void setContainTitle(boolean z) {
        this.isContainTitle = z;
    }

    public void setContainExtraContent(boolean z) {
        this.isContainExtraContent = z;
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
        private ListLayout() {
        }

        @Override // com.miui.notes.component.PreviewTextView.AbstractLayout
        protected CharSequence doLayoutText(Context context, CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3, int i4) {
            CharSequence[] charSequenceArrSplitText = splitText(charSequence);
            CharSequence charSequence2 = charSequenceArrSplitText[0];
            CharSequence charSequence3 = charSequenceArrSplitText[1];
            int length = charSequence2.length();
            PreviewTextSpan previewTextSpan = new PreviewTextSpan(context, i);
            PreviewTextSpan previewTextSpan2 = new PreviewTextSpan(context, R.style.V12_TextAppearance_Grid_Blank);
            TextPaint textPaint2 = new TextPaint();
            textPaint2.set(textPaint);
            PreviewTextSpan previewTextSpan3 = new PreviewTextSpan(context, i2);
            PreviewTextSpan previewTextSpan4 = new PreviewTextSpan(context, i3);
            previewTextSpan3.updateMeasureState(textPaint2);
            StaticLayout staticLayoutCreateStaticLayout = createStaticLayout(charSequence2, 0, length, textPaint, i4, TextUtils.TruncateAt.END, 1);
            int ellipsisStart = staticLayoutCreateStaticLayout.getEllipsisStart(0);
            int lineEnd = ellipsisStart == 0 ? staticLayoutCreateStaticLayout.getLineEnd(0) : ellipsisStart;
            if (!TextUtils.isEmpty(charSequence3)) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                if (ellipsisStart > 0) {
                    spannableStringBuilder.append(charSequence2, 0, lineEnd);
                    spannableStringBuilder.append((CharSequence) PreviewTextView.ELLIPSIS_NORMAL);
                } else {
                    spannableStringBuilder.append(charSequence2, 0, lineEnd);
                }
                spannableStringBuilder.append('\n');
                int length2 = spannableStringBuilder.length();
                spannableStringBuilder.setSpan(previewTextSpan, 0, length2 - 1, 17);
                spannableStringBuilder.append('\n');
                spannableStringBuilder.setSpan(previewTextSpan2, length2, spannableStringBuilder.length(), 17);
                int length3 = spannableStringBuilder.length();
                spannableStringBuilder.append(charSequence3);
                spannableStringBuilder.setSpan(previewTextSpan3, length3, spannableStringBuilder.length(), 17);
                return ContentUtils.changeHighLight(spannableStringBuilder, PreviewTextView.sSearchToken);
            }
            if (lineEnd == length) {
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
                spannableStringBuilder2.append(charSequence2).append('\n');
                int length4 = spannableStringBuilder2.length();
                spannableStringBuilder2.setSpan(previewTextSpan, 0, length4 - 1, 17);
                spannableStringBuilder2.append('\n');
                spannableStringBuilder2.setSpan(previewTextSpan2, length4, spannableStringBuilder2.length(), 17);
                int length5 = spannableStringBuilder2.length();
                spannableStringBuilder2.append((CharSequence) NoteApp.getInstance().getString(R.string.note_not_text_stub));
                spannableStringBuilder2.setSpan(previewTextSpan4, length5, spannableStringBuilder2.length(), 17);
                return ContentUtils.changeHighLight(spannableStringBuilder2, PreviewTextView.sSearchToken);
            }
            int i5 = lineEnd;
            StaticLayout staticLayoutCreateStaticLayout2 = createStaticLayout(charSequence2, i5, length, textPaint2, i4, null, 3);
            int i6 = staticLayoutCreateStaticLayout2.getLineCount() <= 2 ? 0 : 1;
            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder();
            spannableStringBuilder3.append(charSequence2, 0, Math.max(i5 - 2, 0)).append((CharSequence) PreviewTextView.ELLIPSIS_NORMAL).append('\n');
            int length6 = spannableStringBuilder3.length();
            spannableStringBuilder3.setSpan(previewTextSpan, 0, Math.max(length6 - 1, 0), 17);
            spannableStringBuilder3.append('\n');
            spannableStringBuilder3.setSpan(previewTextSpan2, length6, spannableStringBuilder3.length(), 17);
            int length7 = spannableStringBuilder3.length();
            spannableStringBuilder3.append((CharSequence) PreviewTextView.ELLIPSIS_NORMAL);
            spannableStringBuilder3.append(charSequence2, staticLayoutCreateStaticLayout2.getLineStart(i6), length);
            spannableStringBuilder3.setSpan(previewTextSpan3, length7, spannableStringBuilder3.length(), 17);
            return ContentUtils.changeHighLight(spannableStringBuilder3, PreviewTextView.sSearchToken);
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
    }

    public static boolean containsEmoji(CharSequence charSequence) {
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (isEmojiCharacter(charSequence.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static class GridLayout extends AbstractLayout {
        private GridLayout() {
        }

        @Override // com.miui.notes.component.PreviewTextView.AbstractLayout
        protected CharSequence doLayoutText(Context context, CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3, int i4) {
            CharSequence leftLines;
            int i5;
            CharSequence firstLine = getFirstLine(charSequence);
            int length = firstLine.length();
            PreviewTextSpan previewTextSpan = new PreviewTextSpan(context, i);
            PreviewTextSpan previewTextSpan2 = new PreviewTextSpan(context, R.style.Common_TextAppearance_Grid_Blank);
            PreviewTextSpan previewTextSpan3 = new PreviewTextSpan(context, i2);
            PreviewTextSpan previewTextSpan4 = new PreviewTextSpan(context, i3);
            int lineEnd = createStaticLayout(firstLine, 0, length, textPaint, i4, null, 1).getLineEnd(0);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            if (this.mContainTitle && lineEnd != firstLine.length()) {
                CharSequence charSequenceSubSequence = firstLine.subSequence(0, lineEnd - 1);
                if (lineEnd > 2) {
                    int i6 = lineEnd - 2;
                    if (PreviewTextView.containsEmoji(firstLine.subSequence(i6, lineEnd))) {
                        charSequenceSubSequence = firstLine.subSequence(0, i6);
                    }
                }
                float fMeasureText = textPaint.measureText("...");
                float fMeasureText2 = textPaint.measureText(charSequenceSubSequence.toString());
                float f = i4;
                if (fMeasureText + fMeasureText2 > f && lineEnd > (i5 = (int) ((f - fMeasureText) / (fMeasureText2 / lineEnd)))) {
                    CharSequence charSequenceSubSequence2 = firstLine.subSequence(0, i5 - 1);
                    if (fMeasureText + textPaint.measureText(charSequenceSubSequence2.toString()) > f) {
                        charSequenceSubSequence2 = firstLine.subSequence(0, Math.max(i5 - 2, 0));
                    }
                    charSequenceSubSequence = charSequenceSubSequence2;
                }
                spannableStringBuilder.append(charSequenceSubSequence);
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
            } else if (this.mContainExtraContent) {
                spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
            } else {
                spannableStringBuilder.append('\n');
                int length4 = spannableStringBuilder.length();
                spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
                spannableStringBuilder.append('\n');
                spannableStringBuilder.setSpan(previewTextSpan2, length4, spannableStringBuilder.length(), 17);
                int length5 = spannableStringBuilder.length();
                spannableStringBuilder.append((CharSequence) NoteApp.getInstance().getString(R.string.pad_note_list_not_text_stub));
                spannableStringBuilder.setSpan(previewTextSpan4, length5, spannableStringBuilder.length(), 17);
            }
            return ContentUtils.changeHighLight(spannableStringBuilder, PreviewTextView.sSearchToken);
        }

        private CharSequence getFirstLine(CharSequence charSequence) {
            int iIndexOf = charSequence.toString().indexOf(10);
            return iIndexOf >= 0 ? charSequence.subSequence(0, iIndexOf) : charSequence;
        }

        private CharSequence getLeftLines(CharSequence charSequence, int i) {
            return TextProcessUtils.trimEmptyLineSequence(charSequence.subSequence(i, charSequence.length()));
        }
    }

    public static abstract class AbstractLayout {
        boolean mContainExtraContent;
        boolean mContainTitle;

        protected abstract CharSequence doLayoutText(Context context, CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3, int i4);

        public CharSequence layoutText(Context context, CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3, int i4) {
            if (TextUtils.isEmpty(charSequence) || i4 <= 0) {
                return "";
            }
            return doLayoutText(context, charSequence, textPaint, i, i2, i3, i4);
        }

        protected static StaticLayout createStaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, TextUtils.TruncateAt truncateAt, int i4) {
            return StaticLayout.Builder.obtain(charSequence, i, i2, textPaint, i3).setMaxLines(i4).setEllipsize(truncateAt).build();
        }

        public void setContainTitle(boolean z) {
            this.mContainTitle = z;
        }

        public void setContainExtraContent(boolean z) {
            this.mContainExtraContent = z;
        }
    }
}
