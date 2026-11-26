package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.agog.mathdisplay.parse.MTMathAtom;
import com.agog.mathdisplay.parse.MathDisplayException;
import com.agog.mathdisplay.parse.NSRange;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u0012\u001a\u00020\u0013J\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0017"}, d2 = {"Lcom/agog/mathdisplay/render/MTCTLineDisplay;", "Lcom/agog/mathdisplay/render/MTDisplay;", "str", "", "range", "Lcom/agog/mathdisplay/parse/NSRange;", "font", "Lcom/agog/mathdisplay/render/MTFont;", "atoms", "", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "(Ljava/lang/String;Lcom/agog/mathdisplay/parse/NSRange;Lcom/agog/mathdisplay/render/MTFont;Ljava/util/List;)V", "getAtoms", "()Ljava/util/List;", "getFont", "()Lcom/agog/mathdisplay/render/MTFont;", "getStr", "()Ljava/lang/String;", "computeDimensions", "", "draw", "canvas", "Landroid/graphics/Canvas;", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTCTLineDisplay extends MTDisplay {
    private final List<MTMathAtom> atoms;
    private final MTFont font;
    private final String str;

    public final List<MTMathAtom> getAtoms() {
        return this.atoms;
    }

    public final MTFont getFont() {
        return this.font;
    }

    public final String getStr() {
        return this.str;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public MTCTLineDisplay(String str, NSRange range, MTFont font, List<? extends MTMathAtom> atoms) {
        super(0.0f, 0.0f, 0.0f, range, false, 23, null);
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(font, "font");
        Intrinsics.checkNotNullParameter(atoms, "atoms");
        this.str = str;
        this.font = font;
        this.atoms = atoms;
        computeDimensions();
    }

    public final void computeDimensions() {
        List<Integer> gidListForString = this.font.getGidListForString(this.str);
        int size = gidListForString.size();
        BoundingBox[] boundingBoxArr = new BoundingBox[size];
        Float[] fArr = new Float[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = Float.valueOf(0.0f);
        }
        List<Integer> list = gidListForString;
        this.font.getMathTable().getBoundingRectsForGlyphs(CollectionsKt.toList(list), boundingBoxArr, size);
        this.font.getMathTable().getAdvancesForGlyphs(CollectionsKt.toList(list), fArr, size);
        setWidth(0.0f);
        int i2 = 0;
        while (i2 < size) {
            int i3 = i2 + 1;
            BoundingBox boundingBox = boundingBoxArr[i2];
            if (boundingBox != null) {
                float fMax = Math.max(0.0f, boundingBox.getUpperRightY() - 0);
                float fMax2 = Math.max(0.0f, 0.0f - boundingBox.getLowerLeftY());
                if (fMax > getAscent()) {
                    setAscent(fMax);
                }
                if (fMax2 > getDescent()) {
                    setDescent(fMax2);
                }
                setWidth(getWidth() + fArr[i2].floatValue());
            }
            i2 = i3;
        }
    }

    @Override // com.agog.mathdisplay.render.MTDisplay
    public void draw(Canvas canvas) throws MathDisplayException {
        float fFloatValue;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.draw(canvas);
        Paint paint = new Paint(193);
        paint.setColor(getTextColor());
        MTDrawFreeType mTDrawFreeType = new MTDrawFreeType(this.font.getMathTable());
        List<Integer> gidListForString = this.font.getGidListForString(this.str);
        int size = gidListForString.size();
        Float[] fArr = new Float[size];
        int i = 0;
        while (true) {
            fFloatValue = 0.0f;
            if (i >= size) {
                break;
            }
            fArr[i] = Float.valueOf(0.0f);
            i++;
        }
        this.font.getMathTable().getAdvancesForGlyphs(gidListForString, fArr, size);
        canvas.save();
        canvas.translate(getPosition().getX(), getPosition().getY());
        canvas.scale(1.0f, -1.0f);
        for (int i2 = 0; i2 < size; i2++) {
            mTDrawFreeType.drawGlyph(canvas, paint, gidListForString.get(i2).intValue(), fFloatValue, 0.0f);
            fFloatValue += fArr[i2].floatValue();
        }
        paint.setColor(-65536);
        canvas.restore();
    }
}
