package com.miui.handwriting.common.data.bean;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiSpannedData {
    private String entPath;
    private float height;
    private long id;
    private int pageIndex;
    private String text;
    private int textId;
    private int textIndex;
    public float textSize;
    private float width;
    private float x;
    private float y;

    public MiuiSpannedData(long j, String str, int i, int i2, int i3, String str2, float f, float f2, float f3, float f4, float f5) {
        this.id = j;
        this.entPath = str;
        this.pageIndex = i;
        this.textIndex = i2;
        this.textId = i3;
        this.text = str2;
        this.textSize = f;
        this.x = f2;
        this.y = f3;
        this.width = f4;
        this.height = f5;
    }

    public MiuiSpannedData() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getEntPath() {
        return this.entPath;
    }

    public void setEntPath(String str) {
        this.entPath = str;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setTextIndex(int i) {
        this.textIndex = i;
    }

    public int getTextIndex() {
        return this.textIndex;
    }

    public void setPageIndex(int i) {
        this.pageIndex = i;
    }

    public int getTextId() {
        return this.textId;
    }

    public void setTextId(int i) {
        this.textId = i;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public float getTextSize() {
        return this.textSize;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float f) {
        this.x = f;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float f) {
        this.y = f;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float f) {
        this.height = f;
    }
}
