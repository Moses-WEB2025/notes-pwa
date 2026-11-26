package com.pvporbit.freetype;

/* JADX INFO: loaded from: classes4.dex */
public class CharMap extends Utils.Pointer {
    public CharMap(long j) {
        super(j);
    }

    public static int getCharmapIndex(CharMap charMap) {
        return FreeType.FT_Get_Charmap_Index(charMap.getPointer());
    }
}
