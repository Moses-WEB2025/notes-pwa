package com.miui.handwritecommon.api.bean;

/* JADX INFO: loaded from: classes2.dex */
public class WebImage {
    int height;
    String image;
    int width;

    public WebImage(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public WebImage(String str, int i, int i2) {
        this.image = str;
        this.width = i;
        this.height = i2;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }
}
