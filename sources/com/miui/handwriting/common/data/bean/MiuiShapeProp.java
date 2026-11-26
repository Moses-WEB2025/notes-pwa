package com.miui.handwriting.common.data.bean;

import android.graphics.Color;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiShapeProp {
    protected int fillAlpha;
    protected int fillColor;
    protected int fillColorWheel = -16777216;
    public int shapeType;
    protected int strokeAlpha;
    protected int strokeColor;
    protected float strokeSize;
    protected int strokeType;

    public MiuiShapeProp(int i, int i2, float f, int i3, int i4, int i5, int i6) {
        this.shapeType = i;
        this.fillColor = i4;
        this.fillAlpha = i5;
        this.strokeColor = i2;
        this.strokeSize = f;
        this.strokeAlpha = i3;
        this.strokeType = i6;
    }

    public int getShapeType() {
        return this.shapeType;
    }

    public void setShapeType(int i) {
        this.shapeType = i;
    }

    public int getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(int i) {
        this.fillColor = i;
        this.fillAlpha = Color.alpha(i);
    }

    public float getFillAlpha() {
        return this.fillAlpha;
    }

    public void setFillAlpha(int i) {
        this.fillAlpha = i;
    }

    public int getFillColorWheel() {
        return this.fillColorWheel;
    }

    public void setFillColorWheel(int i) {
        this.fillColorWheel = i;
    }

    public int getStrokeType() {
        return this.strokeType;
    }

    public void setStrokeType(int i) {
        this.strokeType = i;
    }

    public int getStrokeColor() {
        return this.strokeColor;
    }

    public void setStrokeColor(int i) {
        this.strokeColor = i;
        this.strokeAlpha = Color.alpha(i);
    }

    public float getStrokeSize() {
        return this.strokeSize;
    }

    public void setStrokeSize(float f) {
        this.strokeSize = f;
    }

    public int getStrokeAlpha() {
        return this.strokeAlpha;
    }

    public void setStrokeAlpha(int i) {
        this.strokeAlpha = i;
    }
}
