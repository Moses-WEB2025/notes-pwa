package com.miui.richeditor.style;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spanned;
import android.text.style.LineHeightSpan;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.IEditorContext;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class EditLineHeightSpan implements LineHeightSpan {
    public static float FONT_BASE_LINE_RATIO = 0.3f;
    public static float FONT_LARGE_LINE_HEIGHT_RATIO = 1.2f;
    public static float FONT_MIDDLE_LINE_HEIGHT_RATIO = 1.067f;
    protected WeakReference<IEditorContext> editorContextWeakReference;
    protected int mFontSizeLevel;
    protected int mStandLineHeight;
    protected int mStandParagraphMargin;
    protected float mFontScale = 1.0f;
    protected float mFontInnerScale = 1.0f;
    protected int mFontDescent = 0;
    protected int mFontAscent = 0;
    protected int mFontDescentAdd = 0;
    private Context mContext = NoteApp.getInstance();

    public EditLineHeightSpan(IEditorContext iEditorContext, int i) {
        this.mFontSizeLevel = 0;
        this.mStandLineHeight = 0;
        this.mStandParagraphMargin = 0;
        this.editorContextWeakReference = new WeakReference<>(iEditorContext);
        this.mFontSizeLevel = i;
        this.mStandLineHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_stand_line_height);
        this.mStandParagraphMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_stand_paragraph_margin);
        updateFontParams();
    }

    public void updateFontParams() {
        updateFontScaleParams();
        updateFontMetrics();
    }

    protected void init() {
        updateFontScaleParams();
    }

    @Override // android.text.style.LineHeightSpan
    public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
        if (fontMetricsInt.descent - fontMetricsInt.ascent <= 0) {
            return;
        }
        updateFontScaleParams();
        for (int i5 = i; i5 < i2; i5++) {
            if (charSequence.charAt(i5) == 65532) {
                Logger.INSTANCE.d("EditLineHeightSpan", "lineHeight chooseHeight lineHeight text.charAt(i) == HtmlParser.MEDIA_DUMMY_CHAR return");
                return;
            }
        }
        Spanned spanned = (Spanned) charSequence;
        int spanStart = spanned.getSpanStart(this);
        int spanEnd = spanned.getSpanEnd(this);
        int i6 = 0;
        if (i >= spanStart || i2 <= spanEnd) {
            FontLargeSizeSpan[] fontLargeSizeSpanArr = (FontLargeSizeSpan[]) spanned.getSpans(i, i2, FontLargeSizeSpan.class);
            FontMidSizeSpan[] fontMidSizeSpanArr = (FontMidSizeSpan[]) spanned.getSpans(i, i2, FontMidSizeSpan.class);
            if (fontLargeSizeSpanArr.length > 0) {
                i6 = 2;
            } else if (fontMidSizeSpanArr.length > 0) {
                i6 = 1;
            }
        }
        if (i6 != this.mFontSizeLevel) {
            this.mFontSizeLevel = i6;
            updateFontMetrics();
        }
        fontMetricsInt.descent = this.mFontDescent;
        fontMetricsInt.ascent = this.mFontAscent;
        if (charSequence.charAt(i2 - 1) == '\n' || charSequence.length() == i2) {
            fontMetricsInt.descent += this.mFontDescentAdd;
        }
        if (RomUtils.isPadMode()) {
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = fontMetricsInt.descent;
        }
    }

    public int getFontSizeLevel() {
        return this.mFontSizeLevel;
    }

    protected void updateFontScaleParams() {
        IEditorContext iEditorContext = this.editorContextWeakReference.get();
        Context context = this.mContext;
        if (context != null) {
            this.mFontScale = context.getResources().getDisplayMetrics().scaledDensity;
        } else if (iEditorContext != null) {
            this.mFontScale = iEditorContext.getFontScale();
        }
        if (iEditorContext != null) {
            this.mFontInnerScale = iEditorContext.getFontInnerScale();
        }
        if (RomUtils.isPadMode()) {
            this.mFontDescentAdd = this.mStandParagraphMargin;
        } else {
            this.mFontDescentAdd = (int) Math.round(((double) this.mFontScale) * 7.28d * ((double) this.mFontInnerScale));
        }
    }

    protected void updateFontMetrics() {
        float f;
        float f2;
        int i = this.mStandLineHeight;
        int i2 = this.mFontSizeLevel;
        if (i2 == 1) {
            f = i * FONT_MIDDLE_LINE_HEIGHT_RATIO * this.mFontScale;
            f2 = this.mFontInnerScale;
        } else if (i2 == 2) {
            f = i * FONT_LARGE_LINE_HEIGHT_RATIO * this.mFontScale;
            f2 = this.mFontInnerScale;
        } else {
            f = i * this.mFontScale;
            f2 = this.mFontInnerScale;
        }
        int i3 = (int) (f * f2);
        Logger.INSTANCE.d("LhTest", "lineHeight " + i3);
        int iFloor = (int) Math.floor(i3 * FONT_BASE_LINE_RATIO);
        this.mFontDescent = iFloor;
        this.mFontAscent = iFloor - i3;
    }
}
