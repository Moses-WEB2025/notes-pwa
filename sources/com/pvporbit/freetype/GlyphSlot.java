package com.pvporbit.freetype;

/* JADX INFO: loaded from: classes4.dex */
public class GlyphSlot extends Utils.Pointer {
    public GlyphSlot(long j) {
        super(j);
    }

    public Bitmap getBitmap() {
        long jFT_GlyphSlot_Get_bitmap = FreeType.FT_GlyphSlot_Get_bitmap(this.pointer);
        if (jFT_GlyphSlot_Get_bitmap == 0) {
            return null;
        }
        return new Bitmap(jFT_GlyphSlot_Get_bitmap);
    }

    public long getLinearHoriAdvance() {
        return FreeType.FT_GlyphSlot_Get_linearHoriAdvance(this.pointer);
    }

    public long getLinearVertAdvance() {
        return FreeType.FT_GlyphSlot_Get_linearVertAdvance(this.pointer);
    }

    public Advance getAdvance() {
        return FreeType.FT_GlyphSlot_Get_advance(this.pointer);
    }

    public int getFormat() {
        return FreeType.FT_GlyphSlot_Get_format(this.pointer);
    }

    public int getBitmapLeft() {
        return FreeType.FT_GlyphSlot_Get_bitmap_left(this.pointer);
    }

    public int getBitmapTop() {
        return FreeType.FT_GlyphSlot_Get_bitmap_top(this.pointer);
    }

    public GlyphMetrics getMetrics() {
        long jFT_GlyphSlot_Get_metrics = FreeType.FT_GlyphSlot_Get_metrics(this.pointer);
        if (jFT_GlyphSlot_Get_metrics == 0) {
            return null;
        }
        return new GlyphMetrics(jFT_GlyphSlot_Get_metrics);
    }

    public boolean renderGlyph(FreeTypeConstants.FT_Render_Mode fT_Render_Mode) {
        return FreeType.FT_Render_Glyph(this.pointer, fT_Render_Mode.ordinal());
    }

    public static class Advance {
        private final long x;
        private final long y;

        public Advance(long j, long j2) {
            this.x = j;
            this.y = j2;
        }

        public long getX() {
            return this.x;
        }

        public long getY() {
            return this.y;
        }

        public String toString() {
            return "(" + this.x + "," + this.y + ")";
        }
    }
}
