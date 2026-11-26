package com.agog.mathdisplay.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.agog.mathdisplay.parse.MathDisplayException;
import com.miui.maml.folme.AnimatedProperty;
import com.pvporbit.freetype.Bitmap;
import com.pvporbit.freetype.Face;
import com.pvporbit.freetype.GlyphMetrics;
import com.pvporbit.freetype.GlyphSlot;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: MTDrawFreeType.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J.\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lcom/agog/mathdisplay/render/MTDrawFreeType;", "", "mathfont", "Lcom/agog/mathdisplay/render/MTFontMathTable;", "(Lcom/agog/mathdisplay/render/MTFontMathTable;)V", "getMathfont", "()Lcom/agog/mathdisplay/render/MTFontMathTable;", "drawGlyph", "", "canvas", "Landroid/graphics/Canvas;", CloudPushConstants.WATERMARK_TYPE.PERSONAL, "Landroid/graphics/Paint;", "gid", "", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTDrawFreeType {
    private final MTFontMathTable mathfont;

    public MTDrawFreeType(MTFontMathTable mathfont) {
        Intrinsics.checkNotNullParameter(mathfont, "mathfont");
        this.mathfont = mathfont;
    }

    public final MTFontMathTable getMathfont() {
        return this.mathfont;
    }

    public final void drawGlyph(Canvas canvas, Paint p, int gid, float x, float y) throws MathDisplayException {
        GlyphSlot glyphSlot;
        Bitmap bitmap;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(p, "p");
        Face faceCheckFontSize = this.mathfont.checkFontSize();
        if (gid == 0 || faceCheckFontSize.loadGlyph(gid, 4) || (bitmap = (glyphSlot = faceCheckFontSize.getGlyphSlot()).getBitmap()) == null) {
            return;
        }
        if (bitmap.getWidth() == 0 || bitmap.getRows() == 0) {
            if (gid != 1 && gid != 33) {
                throw new MathDisplayException("missing glyph slot " + gid + '.');
            }
        } else {
            android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(bitmap.getWidth(), bitmap.getRows(), android.graphics.Bitmap.Config.ALPHA_8);
            bitmapCreateBitmap.copyPixelsFromBuffer(bitmap.getBuffer());
            GlyphMetrics metrics = glyphSlot.getMetrics();
            canvas.drawBitmap(bitmapCreateBitmap, x + (metrics.getHoriBearingX() / 64.0f), y - (metrics.getHoriBearingY() / 64.0f), p);
        }
    }
}
