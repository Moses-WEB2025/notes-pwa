package com.miui.handwritecommon.api.singlepage;

import kotlin.Metadata;

/* JADX INFO: compiled from: ISinglePageLayoutListener.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\u0003H&¨\u0006\bÀ\u0006\u0003"}, d2 = {"Lcom/miui/handwritecommon/api/singlepage/ISinglePageLayoutListener;", "", "onInsertImageClick", "", "onBtnSave", "onShare", "onContentChange", "onTitleChange", "libHandWriteCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface ISinglePageLayoutListener {
    void onBtnSave();

    void onContentChange();

    void onInsertImageClick();

    void onShare();

    void onTitleChange();
}
