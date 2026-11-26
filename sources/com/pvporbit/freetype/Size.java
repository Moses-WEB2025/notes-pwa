package com.pvporbit.freetype;

/* JADX INFO: loaded from: classes4.dex */
public class Size extends Utils.Pointer {
    public Size(long j) {
        super(j);
    }

    public SizeMetrics getMetrics() {
        long jFT_Size_Get_metrics = FreeType.FT_Size_Get_metrics(this.pointer);
        if (jFT_Size_Get_metrics <= 0) {
            return null;
        }
        return new SizeMetrics(jFT_Size_Get_metrics);
    }
}
