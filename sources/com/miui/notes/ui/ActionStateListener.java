package com.miui.notes.ui;

/* JADX INFO: loaded from: classes4.dex */
public interface ActionStateListener {
    void onAction(int i);

    void onChangeViewPagerScroll(boolean z);

    void onDragEnd();

    void onDragStart();

    void onEnterMultiMode();

    void onEnterSearch();

    void onExitMultiMode();

    void onExitSearch();

    void onRestoreContentPaddingBottom();
}
