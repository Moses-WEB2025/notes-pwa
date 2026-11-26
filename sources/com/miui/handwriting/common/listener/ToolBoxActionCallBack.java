package com.miui.handwriting.common.listener;

import com.miui.handwriting.common.data.bean.BackgroundWrapper;

/* JADX INFO: loaded from: classes3.dex */
public interface ToolBoxActionCallBack {
    void callBackground(BackgroundWrapper backgroundWrapper);

    void clickThumbnail();

    void insertImage();

    void showGuide(Boolean bool);
}
