package com.miui.handwriting.common.listener;

/* JADX INFO: loaded from: classes3.dex */
public interface CommonPageStateListener {
    void dataLoadFinish();

    void onContentChanged(int i);

    void onDoubleUndo();

    void onPageLoadingFinish();

    void onPageScale(float f);

    void onStepChanged(int i, int i2, boolean z, boolean z2, boolean z3);
}
