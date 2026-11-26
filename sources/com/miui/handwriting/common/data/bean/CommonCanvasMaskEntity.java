package com.miui.handwriting.common.data.bean;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes3.dex */
public class CommonCanvasMaskEntity {
    private float mAngle;
    private int mBgColor;
    private Bitmap mContentBitmap;
    private int mExpandHeight;
    private int mExpandWidth;
    private Bitmap mMaskBitmap;
    private float mOffsetX;
    private float mOffsetY;
    private int mToolBarHeight;

    public CommonCanvasMaskEntity() {
    }

    public CommonCanvasMaskEntity(Bitmap bitmap, Bitmap bitmap2, float f, int i, float f2, float f3, int i2, int i3) {
        this.mContentBitmap = bitmap;
        this.mMaskBitmap = bitmap2;
        this.mAngle = f;
        this.mBgColor = i;
        this.mOffsetX = f2;
        this.mOffsetY = f3;
        this.mExpandWidth = i2;
        this.mExpandHeight = i3;
    }

    public Bitmap getContentBitmap() {
        return this.mContentBitmap;
    }

    public void setContentBitmap(Bitmap bitmap) {
        this.mContentBitmap = bitmap;
    }

    public Bitmap getMaskBitmap() {
        return this.mMaskBitmap;
    }

    public void setMaskBitmap(Bitmap bitmap) {
        this.mMaskBitmap = bitmap;
    }

    public float getAngle() {
        return this.mAngle;
    }

    public void setAngle(float f) {
        this.mAngle = f;
    }

    public int getBgColor() {
        return this.mBgColor;
    }

    public void setBgColor(int i) {
        this.mBgColor = i;
    }

    public float getOffsetX() {
        return this.mOffsetX;
    }

    public void setOffsetX(float f) {
        this.mOffsetX = f;
    }

    public float getOffsetY() {
        return this.mOffsetY;
    }

    public void setOffsetY(float f) {
        this.mOffsetY = f;
    }

    public int getExpandWidth() {
        return this.mExpandWidth;
    }

    public void setExpandWidth(int i) {
        this.mExpandWidth = i;
    }

    public int getExpandHeight() {
        return this.mExpandHeight;
    }

    public void setExpandHeight(int i) {
        this.mExpandHeight = i;
    }

    public int getToolBarHeight() {
        return this.mToolBarHeight;
    }

    public void setToolBarHeight(int i) {
        this.mToolBarHeight = i;
    }
}
