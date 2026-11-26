package com.miui.libhandwritingpreview.system;

import kotlin.Metadata;

/* JADX INFO: compiled from: PreCamera.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0003H&¨\u0006\bÀ\u0006\u0003"}, d2 = {"Lcom/miui/libhandwritingpreview/system/GLViewportEventListener;", "", "onScrollBack", "", "onTopPageChanged", "pageIndex", "", "onTitleChanged", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface GLViewportEventListener {
    void onScrollBack();

    void onTitleChanged();

    void onTopPageChanged(int pageIndex);
}
