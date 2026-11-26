package com.miui.libhandwritingpage.listener;

import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes3.dex */
public interface StepChangedListener {
    void onPageSizeChanged(PageSizeConfig pageSizeConfig);

    void onSpanStepChanged(int i, int i2, boolean z);

    void onStepChanged(int i, int i2, boolean z);
}
