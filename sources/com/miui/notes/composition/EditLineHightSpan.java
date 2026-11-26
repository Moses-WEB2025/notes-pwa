package com.miui.notes.composition;

import android.content.Context;
import android.graphics.Paint;
import android.text.style.LineHeightSpan;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.richeditor.util.ResourceParser;

/* JADX INFO: loaded from: classes4.dex */
public class EditLineHightSpan implements LineHeightSpan {
    private float mFontScale;
    private int mGear;
    private float mInnerScale;

    public EditLineHightSpan(Context context, int i) {
        this.mGear = i;
        if (UIUtils.isPcDensity(context)) {
            this.mFontScale = 1.0f;
            this.mInnerScale = 1.0f;
        } else {
            this.mFontScale = context.getResources().getDisplayMetrics().scaledDensity;
            this.mInnerScale = ResourceParser.TextAppearanceResources.FONT_SCALE[PreferenceUtils.getFontSize(NoteApp.getInstance(), 1)];
        }
    }

    @Override // android.text.style.LineHeightSpan
    public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
        if (fontMetricsInt.descent - fontMetricsInt.ascent <= 0) {
            return;
        }
        while (i < i2) {
            if (charSequence.charAt(i) == 65532) {
                Logger.INSTANCE.d("RichEditView", "SmartImageSpan.draw0: lineHight chooseHeight lineHeight text.charAt(i) == HtmlParser.MEDIA_DUMMY_CHAR return");
                return;
            }
            i++;
        }
        int i5 = this.mGear;
        if (i5 == 0) {
            fontMetricsInt.descent = (int) Math.round(((double) this.mFontScale) * 8.36d * ((double) this.mInnerScale));
            fontMetricsInt.ascent = -((int) Math.round(((double) this.mFontScale) * 20.72d * ((double) this.mInnerScale)));
        } else if (i5 == 1) {
            fontMetricsInt.descent = (int) Math.round(((double) this.mFontScale) * 8.54d * ((double) this.mInnerScale));
            fontMetricsInt.ascent = -Math.round(this.mFontScale * 22.0f * this.mInnerScale);
        } else if (i5 == 2) {
            fontMetricsInt.descent = (int) Math.round(((double) this.mFontScale) * 8.91d * ((double) this.mInnerScale));
            fontMetricsInt.ascent = -((int) Math.round(((double) this.mFontScale) * 24.55d * ((double) this.mInnerScale)));
        } else if (i5 == 3) {
            fontMetricsInt.descent = (int) Math.round(((double) this.mFontScale) * 9.09d * ((double) this.mInnerScale));
            fontMetricsInt.ascent = -((int) Math.round(((double) this.mFontScale) * 25.22d * ((double) this.mInnerScale)));
        }
        if (charSequence.charAt(i2 - 1) == '\n') {
            fontMetricsInt.descent += (int) Math.round(((double) this.mFontScale) * 7.28d * ((double) this.mInnerScale));
        }
    }

    public int getTextType() {
        return this.mGear;
    }
}
