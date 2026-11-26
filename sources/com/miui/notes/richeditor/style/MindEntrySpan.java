package com.miui.notes.richeditor.style;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.miui.common.tool.Logger;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.style.EditorHintEntrySpan;

/* JADX INFO: loaded from: classes4.dex */
public class MindEntrySpan extends EditorHintEntrySpan {
    public MindEntrySpan(IEditorContext iEditorContext, Drawable drawable, int i, int i2, int i3) {
        super(iEditorContext, drawable, i, i2, i3);
    }

    @Override // com.miui.richeditor.style.EditorHintEntrySpan, com.miui.richeditor.style.ClickableElement
    public boolean isTouchIn(int i, int i2) {
        boolean z = i2 >= this.mBoundsInParent.top && i2 <= this.mBoundsInParent.bottom && i <= this.mBoundsInParent.right && i >= this.mBoundsInParent.left;
        this.mIsTouchIn = z;
        return z;
    }

    public boolean getIsTouchIn() {
        return this.mIsTouchIn;
    }

    @Override // com.miui.richeditor.style.EditorHintEntrySpan, android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            Logger.INSTANCE.e("MindEntrySpan", "getDrawable is null");
            return 0;
        }
        Rect bounds = drawable.getBounds();
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fontMetricsInt2 = paint.getFontMetricsInt();
            int i3 = fontMetricsInt2.bottom - fontMetricsInt2.top;
            int iHeight = bounds.height();
            int i4 = -((iHeight / 2) + (i3 / 4));
            fontMetricsInt.top = i4;
            fontMetricsInt.ascent = i4;
            int i5 = fontMetricsInt.top + iHeight;
            fontMetricsInt.bottom = i5;
            fontMetricsInt.descent = i5;
            this.mBoundsInParent.bottom = this.mBoundsInParent.top + iHeight;
            if (this.editorContextWeakReference.get() != null) {
                this.mBoundsInParent.offsetTo(this.mSpanLeft + this.editorContextWeakReference.get().getView().getPaddingLeft(), 0);
            }
        }
        return bounds.right;
    }

    @Override // com.miui.richeditor.style.EditorHintEntrySpan, android.text.style.ReplacementSpan, com.miui.richeditor.style.ClickableElement
    public CharSequence getContentDescription() {
        return NoteApp.getInstance().getString(R.string.note_mindnote_entrance_icon_text_newversion);
    }
}
