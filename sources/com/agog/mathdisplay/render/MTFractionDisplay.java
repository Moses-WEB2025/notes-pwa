package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.agog.mathdisplay.parse.NSRange;
import com.miui.doodle.document.Layer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u001e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020(2\u0006\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020(H\u0016J\u0006\u0010-\u001a\u00020(J\u0006\u0010.\u001a\u00020(R$\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R$\u0010\u0013\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\f\"\u0004\b\u0015\u0010\u000eR$\u0010\u0016\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0017\u0010\f\"\u0004\b\u0018\u0010\u000eR\u001a\u0010\u0019\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\f\"\u0004\b\u001b\u0010\u000eR\u001a\u0010\u001c\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\f\"\u0004\b\u001e\u0010\u000eR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0010\"\u0004\b \u0010\u0012R$\u0010!\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\f\"\u0004\b#\u0010\u000eR$\u0010$\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b%\u0010\f\"\u0004\b&\u0010\u000e¨\u0006/"}, d2 = {"Lcom/agog/mathdisplay/render/MTFractionDisplay;", "Lcom/agog/mathdisplay/render/MTDisplay;", "numerator", "Lcom/agog/mathdisplay/render/MTMathListDisplay;", "denominator", "range", "Lcom/agog/mathdisplay/parse/NSRange;", "(Lcom/agog/mathdisplay/render/MTMathListDisplay;Lcom/agog/mathdisplay/render/MTMathListDisplay;Lcom/agog/mathdisplay/parse/NSRange;)V", "value", "", "ascent", "getAscent", "()F", "setAscent", "(F)V", "getDenominator", "()Lcom/agog/mathdisplay/render/MTMathListDisplay;", "setDenominator", "(Lcom/agog/mathdisplay/render/MTMathListDisplay;)V", "denominatorDown", "getDenominatorDown", "setDenominatorDown", "descent", "getDescent", "setDescent", "linePosition", "getLinePosition", "setLinePosition", "lineThickness", "getLineThickness", "setLineThickness", "getNumerator", "setNumerator", "numeratorUp", "getNumeratorUp", "setNumeratorUp", Layer.KEY_WIDTH, "getWidth", "setWidth", "colorChanged", "", "draw", "canvas", "Landroid/graphics/Canvas;", "positionChanged", "updateDenominatorPosition", "updateNumeratorPosition", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTFractionDisplay extends MTDisplay {
    private MTMathListDisplay denominator;
    private float denominatorDown;
    private float linePosition;
    private float lineThickness;
    private MTMathListDisplay numerator;
    private float numeratorUp;

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void setAscent(float f) {
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void setDescent(float f) {
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void setWidth(float f) {
    }

    public final MTMathListDisplay getDenominator() {
        return this.denominator;
    }

    public final MTMathListDisplay getNumerator() {
        return this.numerator;
    }

    public final void setDenominator(MTMathListDisplay mTMathListDisplay) {
        Intrinsics.checkNotNullParameter(mTMathListDisplay, "<set-?>");
        this.denominator = mTMathListDisplay;
    }

    public final void setNumerator(MTMathListDisplay mTMathListDisplay) {
        Intrinsics.checkNotNullParameter(mTMathListDisplay, "<set-?>");
        this.numerator = mTMathListDisplay;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTFractionDisplay(MTMathListDisplay numerator, MTMathListDisplay denominator, NSRange range) {
        super(0.0f, 0.0f, 0.0f, range, false, 23, null);
        Intrinsics.checkNotNullParameter(numerator, "numerator");
        Intrinsics.checkNotNullParameter(denominator, "denominator");
        Intrinsics.checkNotNullParameter(range, "range");
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public final float getLinePosition() {
        return this.linePosition;
    }

    public final void setLinePosition(float f) {
        this.linePosition = f;
    }

    public final float getLineThickness() {
        return this.lineThickness;
    }

    public final void setLineThickness(float f) {
        this.lineThickness = f;
    }

    public final float getNumeratorUp() {
        return this.numeratorUp;
    }

    public final void setNumeratorUp(float f) {
        this.numeratorUp = f;
        updateNumeratorPosition();
    }

    public final float getDenominatorDown() {
        return this.denominatorDown;
    }

    public final void setDenominatorDown(float f) {
        this.denominatorDown = f;
        updateDenominatorPosition();
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public float getAscent() {
        return this.numerator.getAscent() + this.numeratorUp;
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public float getDescent() {
        return this.denominator.getDescent() + this.denominatorDown;
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public float getWidth() {
        return Math.max(this.numerator.getWidth(), this.denominator.getWidth());
    }

    public final void updateDenominatorPosition() {
        this.denominator.setPosition(new CGPoint(getPosition().getX() + ((getWidth() - this.denominator.getWidth()) / 2), getPosition().getY() - this.denominatorDown));
    }

    public final void updateNumeratorPosition() {
        this.numerator.setPosition(new CGPoint(getPosition().getX() + ((getWidth() - this.numerator.getWidth()) / 2), getPosition().getY() + this.numeratorUp));
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void positionChanged() {
        updateDenominatorPosition();
        updateNumeratorPosition();
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void colorChanged() {
        this.numerator.setTextColor(getTextColor());
        this.denominator.setTextColor(getTextColor());
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        this.numerator.draw(canvas);
        this.denominator.draw(canvas);
        if (this.lineThickness == 0.0f) {
            return;
        }
        Paint paint = new Paint(193);
        paint.setColor(getTextColor());
        paint.setStrokeWidth(this.lineThickness);
        canvas.drawLine(getPosition().getX(), getPosition().getY() + this.linePosition, getPosition().getX() + getWidth(), getPosition().getY() + this.linePosition, paint);
    }
}
