package com.miui.notes.ai.data.input;

/* JADX INFO: loaded from: classes4.dex */
public enum PolishStyle {
    TYPE_COMMON(0),
    TYPE_PROFESSIONAL(1),
    TYPE_EASY(2),
    TYPE_FRIENDLY(3),
    TYPE_UNKNOWN(-1);

    private int mValue;

    PolishStyle(int i) {
        this.mValue = i;
    }

    public int getValue() {
        return this.mValue;
    }
}
