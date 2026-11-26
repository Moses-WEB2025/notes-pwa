package com.miui.libhandwritingpage.listener;

/* JADX INFO: loaded from: classes3.dex */
public interface PageStateListener {
    void dataLoadFinish();

    void onContentChanged(int i);

    void onDoubleUndo();

    void onPageLoadingFinish();

    void onPageScale(float f);

    void onStepChanged(int i, int i2, boolean z, boolean z2, boolean z3);
}
