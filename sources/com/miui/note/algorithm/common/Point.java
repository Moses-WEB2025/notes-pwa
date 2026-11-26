package com.miui.note.algorithm.common;

import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes3.dex */
public class Point {
    float x;
    float y;

    public Point(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float f) {
        this.x = f;
    }

    public void setY(float f) {
        this.y = f;
    }

    public String toString() {
        return "Point{x=" + this.x + ", y=" + this.y + CoreConstants.CURLY_RIGHT;
    }
}
