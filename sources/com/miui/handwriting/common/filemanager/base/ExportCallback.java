package com.miui.handwriting.common.filemanager.base;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface ExportCallback {
    void call(int i, boolean z, List<String> list);

    void onProgress(int i);
}
