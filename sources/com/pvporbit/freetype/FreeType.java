package com.pvporbit.freetype;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes4.dex */
public class FreeType {
    public static native ByteBuffer FT_Bitmap_Get_buffer(long j);

    public static native short FT_Bitmap_Get_num_grays(long j);

    public static native char FT_Bitmap_Get_palette_mode(long j);

    public static native int FT_Bitmap_Get_pitch(long j);

    public static native char FT_Bitmap_Get_pixel_mode(long j);

    public static native int FT_Bitmap_Get_rows(long j);

    public static native int FT_Bitmap_Get_width(long j);

    public static native boolean FT_Done_Face(long j);

    public static native boolean FT_Done_FreeType(long j);

    public static native boolean FT_Face_CheckTrueTypePatents(long j);

    public static native Kerning FT_Face_Get_Kerning(long j, char c, char c2, int i);

    public static native int FT_Face_Get_ascender(long j);

    public static native int FT_Face_Get_descender(long j);

    public static native long FT_Face_Get_face_flags(long j);

    public static native int FT_Face_Get_face_index(long j);

    public static native String FT_Face_Get_family_name(long j);

    public static native long FT_Face_Get_glyph(long j);

    public static native int FT_Face_Get_heigth(long j);

    public static native int FT_Face_Get_max_advance_height(long j);

    public static native int FT_Face_Get_max_advance_width(long j);

    public static native int FT_Face_Get_num_faces(long j);

    public static native int FT_Face_Get_num_glyphs(long j);

    public static native long FT_Face_Get_size(long j);

    public static native long FT_Face_Get_style_flags(long j);

    public static native String FT_Face_Get_style_name(long j);

    public static native int FT_Face_Get_underline_position(long j);

    public static native int FT_Face_Get_underline_thickness(long j);

    public static native int FT_Face_Get_units_per_EM(long j);

    public static native boolean FT_Face_SetUnpatentedHinting(long j, boolean z);

    public static native int FT_Get_Char_Index(long j, int i);

    public static native int FT_Get_Charmap_Index(long j);

    public static native short FT_Get_FSType_Flags(long j);

    public static native int[] FT_Get_First_Char(long j);

    public static native String FT_Get_Glyph_Name(long j, int i);

    public static native int FT_Get_Name_Index(long j, String str);

    public static native int FT_Get_Next_Char(long j, long j2);

    public static native String FT_Get_Postscript_Name(long j);

    public static native long FT_Get_Track_Kerning(long j, long j2, int i);

    public static native GlyphSlot.Advance FT_GlyphSlot_Get_advance(long j);

    public static native long FT_GlyphSlot_Get_bitmap(long j);

    public static native int FT_GlyphSlot_Get_bitmap_left(long j);

    public static native int FT_GlyphSlot_Get_bitmap_top(long j);

    public static native int FT_GlyphSlot_Get_format(long j);

    public static native long FT_GlyphSlot_Get_linearHoriAdvance(long j);

    public static native long FT_GlyphSlot_Get_linearVertAdvance(long j);

    public static native long FT_GlyphSlot_Get_metrics(long j);

    public static native int FT_Glyph_Metrics_Get_height(long j);

    public static native int FT_Glyph_Metrics_Get_horiAdvance(long j);

    public static native int FT_Glyph_Metrics_Get_horiBearingX(long j);

    public static native int FT_Glyph_Metrics_Get_horiBearingY(long j);

    public static native int FT_Glyph_Metrics_Get_vertAdvance(long j);

    public static native int FT_Glyph_Metrics_Get_vertBearingX(long j);

    public static native int FT_Glyph_Metrics_Get_vertBearingY(long j);

    public static native int FT_Glyph_Metrics_Get_width(long j);

    public static native boolean FT_HAS_KERNING(long j);

    public static native long FT_Init_FreeType();

    public static native LibraryVersion FT_Library_Version(long j);

    public static native boolean FT_Load_Char(long j, char c, int i);

    public static native boolean FT_Load_Glyph(long j, int i, int i2);

    public static native boolean FT_Load_Math_Table(long j, ByteBuffer byteBuffer, int i);

    public static native long FT_New_Memory_Face(long j, ByteBuffer byteBuffer, int i, long j2);

    public static native boolean FT_Reference_Face(long j);

    public static native boolean FT_Render_Glyph(long j, int i);

    public static native boolean FT_Request_Size(long j, SizeRequest sizeRequest);

    public static native boolean FT_Select_Charmap(long j, int i);

    public static native boolean FT_Select_Size(long j, int i);

    public static native boolean FT_Set_Char_Size(long j, int i, int i2, int i3, int i4);

    public static native boolean FT_Set_Charmap(long j, long j2);

    public static native boolean FT_Set_Pixel_Sizes(long j, float f, float f2);

    public static native long FT_Size_Get_metrics(long j);

    public static native int FT_Size_Metrics_Get_ascender(long j);

    public static native int FT_Size_Metrics_Get_descender(long j);

    public static native int FT_Size_Metrics_Get_height(long j);

    public static native int FT_Size_Metrics_Get_max_advance(long j);

    public static native int FT_Size_Metrics_Get_x_ppem(long j);

    public static native int FT_Size_Metrics_Get_x_scale(long j);

    public static native int FT_Size_Metrics_Get_y_ppem(long j);

    public static native int FT_Size_Metrics_Get_y_scale(long j);

    public static Library newLibrary() {
        long jFT_Init_FreeType = FT_Init_FreeType();
        if (jFT_Init_FreeType == 0) {
            return null;
        }
        return new Library(jFT_Init_FreeType);
    }

    static {
        try {
            System.loadLibrary("main");
        } catch (Exception e) {
            e.printStackTrace();
        } catch (UnsatisfiedLinkError e2) {
            System.err.println("Can't find the native file for FreeType-jni.");
            throw e2;
        }
    }
}
