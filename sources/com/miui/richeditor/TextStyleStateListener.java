package com.miui.richeditor;

/* JADX INFO: loaded from: classes4.dex */
public interface TextStyleStateListener {
    void canIndent(boolean z);

    void canInsert(boolean z);

    void canUndent(boolean z);

    void isAiCanUse(boolean z);

    void isNeedLock(boolean z);

    void onBgHighLight(boolean z);

    void onBold(boolean z);

    void onBullet(boolean z);

    void onCenter(boolean z);

    void onCheckbox(boolean z);

    void onFontSize(int i);

    void onGallery(boolean z);

    void onH1(boolean z);

    void onH2(boolean z);

    void onH3(boolean z);

    void onItalic(boolean z);

    void onLeft(boolean z);

    void onListBullet(boolean z);

    void onQuote(boolean z);

    void onRight(boolean z);

    void onSpliteLine(boolean z);

    void onStrikeThrough(boolean z);

    void onUnderline(boolean z);
}
