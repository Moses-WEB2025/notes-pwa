package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.agog.mathdisplay.parse.MathDisplayException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B)\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R$\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014¨\u0006\u001c"}, d2 = {"Lcom/agog/mathdisplay/render/MTGlyphConstructionDisplay;", "Lcom/agog/mathdisplay/render/MTDisplay;", "glyphs", "", "", "offsets", "", "myfont", "Lcom/agog/mathdisplay/render/MTFont;", "(Ljava/util/List;Ljava/util/List;Lcom/agog/mathdisplay/render/MTFont;)V", "value", "ascent", "getAscent", "()F", "setAscent", "(F)V", "descent", "getDescent", "setDescent", "getGlyphs", "()Ljava/util/List;", "getMyfont", "()Lcom/agog/mathdisplay/render/MTFont;", "getOffsets", "draw", "", "canvas", "Landroid/graphics/Canvas;", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTGlyphConstructionDisplay extends MTDisplay {
    private final List<Integer> glyphs;
    private final MTFont myfont;
    private final List<Float> offsets;

    public final List<Integer> getGlyphs() {
        return this.glyphs;
    }

    public final MTFont getMyfont() {
        return this.myfont;
    }

    public final List<Float> getOffsets() {
        return this.offsets;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTGlyphConstructionDisplay(List<Integer> glyphs, List<Float> offsets, MTFont myfont) {
        super(0.0f, 0.0f, 0.0f, null, false, 31, null);
        Intrinsics.checkNotNullParameter(glyphs, "glyphs");
        Intrinsics.checkNotNullParameter(offsets, "offsets");
        Intrinsics.checkNotNullParameter(myfont, "myfont");
        this.glyphs = glyphs;
        this.offsets = offsets;
        this.myfont = myfont;
        glyphs.size();
        offsets.size();
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void draw(Canvas canvas) throws MathDisplayException {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        MTDrawFreeType mTDrawFreeType = new MTDrawFreeType(this.myfont.getMathTable());
        canvas.save();
        canvas.translate(getPosition().getX(), getPosition().getY() - getShiftDown());
        Paint paint = new Paint(193);
        paint.setColor(getTextColor());
        int size = this.glyphs.size();
        for (int i = 0; i < size; i++) {
            canvas.save();
            canvas.translate(0.0f, this.offsets.get(i).floatValue());
            canvas.scale(1.0f, -1.0f);
            mTDrawFreeType.drawGlyph(canvas, paint, this.glyphs.get(i).intValue(), 0.0f, 0.0f);
            canvas.restore();
        }
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
