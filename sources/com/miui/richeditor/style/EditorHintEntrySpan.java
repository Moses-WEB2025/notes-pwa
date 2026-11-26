package com.miui.richeditor.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.IEditorContext;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class EditorHintEntrySpan extends ImageSpan implements ClickableElement {
    protected WeakReference<IEditorContext> editorContextWeakReference;
    protected final Rect mBoundsInParent;
    protected Drawable mDrawable;
    protected boolean mIsTouchIn;
    protected int mSpanLeft;
    protected int mSpanTop;

    public boolean isTouchIn(int i, int i2) {
        return false;
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onLongClick(int i, int i2) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onRightClick(int i, int i2) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onSelected(boolean z) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onTouchPoint(int i, int i2, MotionEvent motionEvent) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void setPressed(boolean z) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void setSelected(boolean z) {
    }

    public EditorHintEntrySpan(IEditorContext iEditorContext, Drawable drawable, int i, int i2, int i3) {
        super(drawable, i);
        Rect rect = new Rect();
        this.mBoundsInParent = rect;
        this.editorContextWeakReference = new WeakReference<>(iEditorContext);
        this.mDrawable = drawable;
        this.mSpanLeft = i2;
        this.mSpanTop = i3;
        rect.set(i2, i3, drawable.getIntrinsicWidth() + i2, drawable.getIntrinsicHeight() + i3);
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        super.draw(canvas, charSequence, i, i2, f, i3 - 50, i4, i5, paint);
        Drawable drawable = getDrawable();
        if (drawable == null) {
            Logger.INSTANCE.e("NoteApp", "EditorHintEntrySpan [draw] getDrawable is null");
            return;
        }
        Rect bounds = drawable.getBounds();
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        if (fontMetricsInt != null) {
            int i6 = fontMetricsInt.bottom - fontMetricsInt.top;
            int iHeight = bounds.height();
            int i7 = -((iHeight / 2) + (i6 / 4));
            fontMetricsInt.top = i7;
            fontMetricsInt.ascent = i7;
            int i8 = iHeight + fontMetricsInt.top;
            fontMetricsInt.bottom = i8;
            fontMetricsInt.descent = i8;
            this.mBoundsInParent.set(0, 0, bounds.width(), bounds.height());
        }
        if (this.editorContextWeakReference.get() != null) {
            this.mBoundsInParent.offsetTo(((int) f) + getDrawable().getBounds().left + this.editorContextWeakReference.get().getView().getPaddingLeft(), i3);
        }
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            Logger.INSTANCE.e("NoteApp", "EditorHintEntrySpan [getSize] getDrawable is null");
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
            int i5 = iHeight + fontMetricsInt.top;
            fontMetricsInt.bottom = i5;
            fontMetricsInt.descent = i5;
            Rect rect = this.mBoundsInParent;
            rect.bottom = rect.top + i3;
            if (this.editorContextWeakReference.get() != null) {
                this.mBoundsInParent.offsetTo(this.mSpanLeft + this.editorContextWeakReference.get().getView().getPaddingLeft(), 0);
            }
        }
        return bounds.right;
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onClick(int i, int i2) {
        if (this.editorContextWeakReference.get() != null) {
            this.editorContextWeakReference.get().onHintSpanClick(this, this.mIsTouchIn, new int[]{i, i2});
        }
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public Rect getBoundsInParent() {
        Logger.INSTANCE.d("MindEntrySpan", "getBoundsInParent " + this.mBoundsInParent.toString());
        return this.mBoundsInParent;
    }

    @Override // android.text.style.ReplacementSpan, com.miui.richeditor.style.ClickableElement
    public CharSequence getContentDescription() {
        if (this.editorContextWeakReference.get() != null) {
            return this.editorContextWeakReference.get().getView().getHint();
        }
        return "";
    }

    protected boolean isInRtl() {
        return this.editorContextWeakReference.get() != null && this.editorContextWeakReference.get().getView().getLayoutDirection() == 1;
    }
}
