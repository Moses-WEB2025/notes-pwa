package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.agog.mathdisplay.parse.NSRange;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\u001dH\u0016J\u0016\u0010\u000b\u001a\u00020\u001d2\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020#J\u0006\u0010$\u001a\u00020\u001dR\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0010\"\u0004\b\u0017\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\nR\u001a\u0010\u0019\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0010\"\u0004\b\u001b\u0010\u0012¨\u0006%"}, d2 = {"Lcom/agog/mathdisplay/render/MTRadicalDisplay;", "Lcom/agog/mathdisplay/render/MTDisplay;", "radicand", "Lcom/agog/mathdisplay/render/MTMathListDisplay;", "radicalGlyph", "range", "Lcom/agog/mathdisplay/parse/NSRange;", "(Lcom/agog/mathdisplay/render/MTMathListDisplay;Lcom/agog/mathdisplay/render/MTDisplay;Lcom/agog/mathdisplay/parse/NSRange;)V", "degree", "getDegree", "()Lcom/agog/mathdisplay/render/MTMathListDisplay;", "setDegree", "(Lcom/agog/mathdisplay/render/MTMathListDisplay;)V", "lineThickness", "", "getLineThickness", "()F", "setLineThickness", "(F)V", "getRadicalGlyph", "()Lcom/agog/mathdisplay/render/MTDisplay;", "radicalShift", "getRadicalShift", "setRadicalShift", "getRadicand", "topKern", "getTopKern", "setTopKern", "colorChanged", "", "draw", "canvas", "Landroid/graphics/Canvas;", "positionChanged", "fontMetrics", "Lcom/agog/mathdisplay/render/MTFontMathTable;", "updateRadicandPosition", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTRadicalDisplay extends MTDisplay {
    private MTMathListDisplay degree;
    private float lineThickness;
    private final MTDisplay radicalGlyph;
    private float radicalShift;
    private final MTMathListDisplay radicand;
    private float topKern;

    public final MTDisplay getRadicalGlyph() {
        return this.radicalGlyph;
    }

    public final MTMathListDisplay getRadicand() {
        return this.radicand;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTRadicalDisplay(MTMathListDisplay radicand, MTDisplay radicalGlyph, NSRange range) {
        super(0.0f, 0.0f, 0.0f, range, false, 23, null);
        Intrinsics.checkNotNullParameter(radicand, "radicand");
        Intrinsics.checkNotNullParameter(radicalGlyph, "radicalGlyph");
        Intrinsics.checkNotNullParameter(range, "range");
        this.radicand = radicand;
        this.radicalGlyph = radicalGlyph;
        updateRadicandPosition();
    }

    public final float getRadicalShift() {
        return this.radicalShift;
    }

    public final void setRadicalShift(float f) {
        this.radicalShift = f;
    }

    public final MTMathListDisplay getDegree() {
        return this.degree;
    }

    public final void setDegree(MTMathListDisplay mTMathListDisplay) {
        this.degree = mTMathListDisplay;
    }

    public final float getTopKern() {
        return this.topKern;
    }

    public final void setTopKern(float f) {
        this.topKern = f;
    }

    public final float getLineThickness() {
        return this.lineThickness;
    }

    public final void setLineThickness(float f) {
        this.lineThickness = f;
    }

    public final void setDegree(MTMathListDisplay degree, MTFontMathTable fontMetrics) {
        Intrinsics.checkNotNullParameter(degree, "degree");
        Intrinsics.checkNotNullParameter(fontMetrics, "fontMetrics");
        float radicalKernBeforeDegree = fontMetrics.getRadicalKernBeforeDegree();
        float radicalKernAfterDegree = fontMetrics.getRadicalKernAfterDegree();
        float radicalDegreeBottomRaisePercent = fontMetrics.getRadicalDegreeBottomRaisePercent() * (getAscent() - getDescent());
        this.degree = degree;
        float width = degree.getWidth() + radicalKernBeforeDegree + radicalKernAfterDegree;
        this.radicalShift = width;
        if (width < 0.0f) {
            radicalKernBeforeDegree -= width;
            this.radicalShift = 0.0f;
        }
        MTMathListDisplay mTMathListDisplay = this.degree;
        if (mTMathListDisplay != null) {
            mTMathListDisplay.setPosition(new CGPoint(getPosition().getX() + radicalKernBeforeDegree, getPosition().getY() + radicalDegreeBottomRaisePercent));
            setWidth(this.radicalShift + this.radicalGlyph.getWidth() + this.radicand.getWidth());
        }
        updateRadicandPosition();
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void positionChanged() {
        updateRadicandPosition();
    }

    public final void updateRadicandPosition() {
        this.radicand.setPosition(new CGPoint(getPosition().getX() + this.radicalShift + this.radicalGlyph.getWidth(), getPosition().getY()));
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void colorChanged() {
        this.radicand.setTextColor(getTextColor());
        this.radicalGlyph.setTextColor(getTextColor());
        MTMathListDisplay mTMathListDisplay = this.degree;
        if (mTMathListDisplay != null) {
            mTMathListDisplay.setTextColor(getTextColor());
        }
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        this.radicand.draw(canvas);
        MTMathListDisplay mTMathListDisplay = this.degree;
        if (mTMathListDisplay != null) {
            mTMathListDisplay.draw(canvas);
        }
        canvas.save();
        canvas.translate(getPosition().getX() + this.radicalShift, getPosition().getY());
        this.radicalGlyph.draw(canvas);
        float f = this.topKern;
        Paint paint = new Paint(193);
        paint.setColor(getTextColor());
        paint.setStrokeWidth(this.lineThickness);
        paint.setStrokeCap(Paint.Cap.ROUND);
        float width = this.radicalGlyph.getWidth();
        float ascent = (getAscent() - f) - (this.lineThickness / 2);
        canvas.drawLine(width, ascent, width + this.radicand.getWidth(), ascent, paint);
        canvas.restore();
    }
}
