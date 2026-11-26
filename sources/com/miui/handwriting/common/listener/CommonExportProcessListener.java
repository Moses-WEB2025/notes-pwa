package com.miui.handwriting.common.listener;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface CommonExportProcessListener<T> {
    void exportFinish(Boolean bool, List<T> list);

    void exportStart();
}
