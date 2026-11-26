package com.miui.handwriting.common.data.bean;

import ch.qos.logback.core.CoreConstants;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class BoundingBox implements Serializable {
    private static final long serialVersionUID = -5468799758138031232L;
    private float height;
    private float width;
    private float x;
    private float y;

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

    public String toString() {
        return "BoundingBox{x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + CoreConstants.CURLY_RIGHT;
    }
}
