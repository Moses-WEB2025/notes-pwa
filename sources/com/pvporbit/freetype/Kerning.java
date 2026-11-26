package com.pvporbit.freetype;

/* JADX INFO: loaded from: classes4.dex */
public class Kerning {
    private final int x;
    private final int y;

    public Kerning(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public int getHorizontalKerning() {
        return this.x;
    }

    public int getVerticalKerning() {
        return this.y;
    }

    public String toString() {
        return "Kerning(" + this.x + ", " + this.y + ")";
    }
}
