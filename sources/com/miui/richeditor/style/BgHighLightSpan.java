package com.miui.richeditor.style;

import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import com.miui.richeditor.IEditorContext;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class BgHighLightSpan extends BackgroundColorSpan {
    protected WeakReference<IEditorContext> mIEditorContextWeakReference;

    public BgHighLightSpan(IEditorContext iEditorContext) {
        this(iEditorContext, iEditorContext.getBgHighLightColor());
    }

    public BgHighLightSpan(IEditorContext iEditorContext, int i) {
        super(i);
        this.mIEditorContextWeakReference = null;
        this.mIEditorContextWeakReference = new WeakReference<>(iEditorContext);
    }

    @Override // android.text.style.BackgroundColorSpan
    public int getBackgroundColor() {
        if (this.mIEditorContextWeakReference.get() != null) {
            return this.mIEditorContextWeakReference.get().getBgHighLightColor();
        }
        return 0;
    }

    @Override // android.text.style.BackgroundColorSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        if (this.mIEditorContextWeakReference.get() != null) {
            textPaint.bgColor = this.mIEditorContextWeakReference.get().getBgHighLightColor();
        }
    }
}
