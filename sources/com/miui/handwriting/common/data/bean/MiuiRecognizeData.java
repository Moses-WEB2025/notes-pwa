package com.miui.handwriting.common.data.bean;

import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiRecognizeData implements Serializable {
    private String entPath;
    private float height;
    private long id;
    private String label;
    private int pageIndex;
    private float width;
    private int wordIndex;
    private float x;
    private float y;

    public MiuiRecognizeData(long j, String str, int i, int i2, String str2, float f, float f2, float f3, float f4) {
        this.id = j;
        this.entPath = str;
        this.pageIndex = i;
        this.wordIndex = i2;
        this.label = str2;
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public MiuiRecognizeData() {
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

    public void setPageIndex(int i) {
        this.pageIndex = i;
    }

    public int getWordIndex() {
        return this.wordIndex;
    }

    public void setWordIndex(int i) {
        this.wordIndex = i;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        this.label = str;
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
