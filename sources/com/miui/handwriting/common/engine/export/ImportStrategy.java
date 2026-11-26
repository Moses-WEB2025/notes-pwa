package com.miui.handwriting.common.engine.export;

/* JADX INFO: loaded from: classes3.dex */
public interface ImportStrategy {
    public static final int IMAGE = 2;
    public static final int PDF = 1;

    void importFiles();

    void importImage();

    void importPdf();
}
