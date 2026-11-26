package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.agog.mathdisplay.parse.MathDisplayException;
import com.agog.mathdisplay.parse.NSRange;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\r\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R$\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001b"}, d2 = {"Lcom/agog/mathdisplay/render/MTGlyphDisplay;", "Lcom/agog/mathdisplay/render/MTDisplay;", "glyph", "Lcom/agog/mathdisplay/render/CGGlyph;", "range", "Lcom/agog/mathdisplay/parse/NSRange;", "myfont", "Lcom/agog/mathdisplay/render/MTFont;", "(Lcom/agog/mathdisplay/render/CGGlyph;Lcom/agog/mathdisplay/parse/NSRange;Lcom/agog/mathdisplay/render/MTFont;)V", "value", "", "ascent", "getAscent", "()F", "setAscent", "(F)V", "descent", "getDescent", "setDescent", "getGlyph", "()Lcom/agog/mathdisplay/render/CGGlyph;", "getMyfont", "()Lcom/agog/mathdisplay/render/MTFont;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTGlyphDisplay extends MTDisplay {
    private final CGGlyph glyph;
    private final MTFont myfont;

    public final CGGlyph getGlyph() {
        return this.glyph;
    }

    public final MTFont getMyfont() {
        return this.myfont;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTGlyphDisplay(CGGlyph glyph, NSRange range, MTFont myfont) {
        super(0.0f, 0.0f, 0.0f, range, false, 23, null);
        Intrinsics.checkNotNullParameter(glyph, "glyph");
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(myfont, "myfont");
        this.glyph = glyph;
        this.myfont = myfont;
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void draw(Canvas canvas) throws MathDisplayException {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.draw(canvas);
        Paint paint = new Paint(193);
        paint.setColor(getTextColor());
        MTDrawFreeType mTDrawFreeType = new MTDrawFreeType(this.myfont.getMathTable());
        canvas.save();
        canvas.translate(getPosition().getX(), getPosition().getY() - getShiftDown());
        canvas.scale(1.0f, -1.0f);
        mTDrawFreeType.drawGlyph(canvas, paint, this.glyph.getGid(), 0.0f, 0.0f);
        canvas.restore();
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public float getAscent() {
        return super.getAscent() - getShiftDown();
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void setAscent(float f) {
        super.setAscent(f);
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public float getDescent() {
        return super.getDescent() + getShiftDown();
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void setDescent(float f) {
        super.setDescent(f);
    }
}
