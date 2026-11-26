package com.miui.handwriting.common.data.bean;

import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiCurveProp {
    protected int penAlpha;
    protected int penColor;
    protected int penColorWheel;
    protected float penSize;
    protected PenType penType;
    protected float sensitivity;
    protected float tipSize;

    public MiuiCurveProp(PenType penType, int i, float f, int i2) {
        this.penColorWheel = -16777216;
        this.penType = penType;
        this.penColor = i;
        this.penSize = f;
        this.penAlpha = i2;
        this.sensitivity = 0.5f;
        this.tipSize = 0.0f;
    }

    public MiuiCurveProp(MiuiCurveProp miuiCurveProp) {
        this.penColorWheel = -16777216;
        this.penType = miuiCurveProp.penType;
        this.penColor = miuiCurveProp.penColor;
        this.penSize = miuiCurveProp.penSize;
        this.penAlpha = miuiCurveProp.penAlpha;
        this.penColorWheel = miuiCurveProp.penColorWheel;
        this.sensitivity = miuiCurveProp.sensitivity;
        this.tipSize = miuiCurveProp.tipSize;
    }

    public CurveProp clone() throws CloneNotSupportedException {
        return (CurveProp) super.clone();
    }

    public PenType getPenType() {
        return this.penType;
    }

    public PenType getConvertPenType() {
        return this.penType;
    }

    public void setPenType(PenType penType) {
        this.penType = penType;
    }

    public int getPenColor() {
        return this.penColor;
    }

    public void setPenColor(int i) {
        this.penColor = i;
    }

    public int getPenColorWheel() {
        return this.penColorWheel;
    }

    public void setPenColorWheel(int i) {
        this.penColorWheel = i;
    }

    public float getPenSize() {
        return this.penSize;
    }

    public void setPenSize(float f) {
        this.penSize = f;
    }

    public int getPenAlpha() {
        return this.penAlpha;
    }

    public void setPenAlpha(int i) {
        this.penAlpha = i;
    }

    public float getSensitivity() {
        return this.sensitivity;
    }

    public void setSensitivity(float f) {
        this.sensitivity = f;
    }

    public float getTipSize() {
        return this.tipSize;
    }

    public void setTipSize(float f) {
        if (f > 4.0f) {
            f = 4.0f;
        }
        this.tipSize = f;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            MiuiCurveProp miuiCurveProp = (MiuiCurveProp) obj;
            if (this.penColor == miuiCurveProp.penColor && Float.compare(miuiCurveProp.penSize, this.penSize) == 0 && this.penAlpha == miuiCurveProp.penAlpha && this.penType == miuiCurveProp.penType && miuiCurveProp.penColorWheel == this.penColorWheel && Float.compare(miuiCurveProp.sensitivity, this.sensitivity) == 0 && Float.compare(miuiCurveProp.tipSize, this.tipSize) == 0) {
                return true;
            }
        }
        return false;
    }
}
