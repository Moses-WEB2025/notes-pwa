package org.jsoup;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface Progress<ProgressContext> {
    void onProgress(int i, int i2, float f, ProgressContext progresscontext);
}
