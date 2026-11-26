package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.agog.mathdisplay.parse.NSRange;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0016J\u0006\u0010\u0018\u001a\u00020\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e¨\u0006\u0019"}, d2 = {"Lcom/agog/mathdisplay/render/MTLineDisplay;", "Lcom/agog/mathdisplay/render/MTDisplay;", "inner", "Lcom/agog/mathdisplay/render/MTMathListDisplay;", "range", "Lcom/agog/mathdisplay/parse/NSRange;", "(Lcom/agog/mathdisplay/render/MTMathListDisplay;Lcom/agog/mathdisplay/parse/NSRange;)V", "getInner", "()Lcom/agog/mathdisplay/render/MTMathListDisplay;", "lineShiftUp", "", "getLineShiftUp", "()F", "setLineShiftUp", "(F)V", "lineThickness", "getLineThickness", "setLineThickness", "colorChanged", "", "draw", "canvas", "Landroid/graphics/Canvas;", "positionChanged", "updateInnerPosition", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTLineDisplay extends MTDisplay {
    private final MTMathListDisplay inner;
    private float lineShiftUp;
    private float lineThickness;

    public final MTMathListDisplay getInner() {
        return this.inner;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTLineDisplay(MTMathListDisplay inner, NSRange range) {
        super(0.0f, 0.0f, 0.0f, range, false, 23, null);
        Intrinsics.checkNotNullParameter(inner, "inner");
        Intrinsics.checkNotNullParameter(range, "range");
        this.inner = inner;
    }

    public final float getLineShiftUp() {
        return this.lineShiftUp;
    }

    public final void setLineShiftUp(float f) {
        this.lineShiftUp = f;
    }

    public final float getLineThickness() {
        return this.lineThickness;
    }

    public final void setLineThickness(float f) {
        this.lineThickness = f;
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void colorChanged() {
        this.inner.setTextColor(getTextColor());
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        this.inner.draw(canvas);
        if (this.lineThickness == 0.0f) {
            return;
        }
        Paint paint = new Paint(193);
        paint.setColor(getTextColor());
        paint.setStrokeWidth(this.lineThickness);
        canvas.drawLine(getPosition().getX(), getPosition().getY() + this.lineShiftUp, getPosition().getX() + getWidth(), getPosition().getY() + this.lineShiftUp, paint);
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void positionChanged() {
        updateInnerPosition();
    }

    public final void updateInnerPosition() {
        this.inner.setPosition(new CGPoint(getPosition().getX(), getPosition().getY()));
    }
}
