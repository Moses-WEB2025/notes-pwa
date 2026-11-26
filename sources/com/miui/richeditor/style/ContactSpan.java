package com.miui.richeditor.style;

import android.graphics.Bitmap;
import android.graphics.Paint;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class ContactSpan extends BaseImageSpan {
    private final String mContactName;
    private final String mPhoneNumber;

    public ContactSpan(IEditorContext iEditorContext, Bitmap bitmap, HtmlParser.ContactElement contactElement) {
        super(iEditorContext, bitmap, contactElement);
        this.mContactName = contactElement.getName();
        this.mPhoneNumber = contactElement.getPhone();
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        int size = super.getSize(paint, charSequence, i, i2, fontMetricsInt);
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (fontMetricsInt != null && iEditorContext != null) {
            Paint.FontMetricsInt fontMetricsInt2 = iEditorContext.getView().getPaint().getFontMetricsInt();
            fontMetricsInt.top = fontMetricsInt2.top;
            fontMetricsInt.ascent = fontMetricsInt2.ascent;
            fontMetricsInt.bottom = fontMetricsInt2.bottom;
            fontMetricsInt.descent = fontMetricsInt2.descent;
        }
        return size;
    }

    public String getContactName() {
        return this.mContactName;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onClick(int i, int i2) {
        IEditorContext iEditorContext = this.mIEditorContextWeakReference == null ? null : this.mIEditorContextWeakReference.get();
        if (iEditorContext != null) {
            iEditorContext.onContactClick(this, new int[]{i, i2});
        }
    }
}
