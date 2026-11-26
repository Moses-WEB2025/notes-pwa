package com.pvporbit.freetype;

/* JADX INFO: loaded from: classes4.dex */
public class SizeRequest {
    private int height;
    private int horiResolution;
    private int type;
    private int vertResolution;
    private int width;

    public SizeRequest(FreeTypeConstants.FT_Size_Request_Type fT_Size_Request_Type, int i, int i2, int i3, int i4) {
        this.type = fT_Size_Request_Type.ordinal();
        this.width = i;
        this.height = i2;
        this.horiResolution = i3;
        this.vertResolution = i4;
    }

    public FreeTypeConstants.FT_Size_Request_Type getType() {
        return FreeTypeConstants.FT_Size_Request_Type.values()[this.type];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getVertResolution() {
        return this.vertResolution;
    }

    public int getHoriResolution() {
        return this.horiResolution;
    }

    public void setType(FreeTypeConstants.FT_Size_Request_Type fT_Size_Request_Type) {
        this.type = fT_Size_Request_Type.ordinal();
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public void setHoriResolution(int i) {
        this.horiResolution = i;
    }

    public void setVertResolution(int i) {
        this.vertResolution = i;
    }
}
