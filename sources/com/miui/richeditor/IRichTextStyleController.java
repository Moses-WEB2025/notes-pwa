package com.miui.richeditor;

/* JADX INFO: loaded from: classes4.dex */
public interface IRichTextStyleController extends TextStyleStateListener {
    void H1();

    void H2();

    void H3();

    void bgHighLight();

    void bold();

    boolean bullet();

    void center();

    boolean checkbox();

    void fontSize();

    void fontSize(int i);

    int getSizeLevel();

    void indentLeft();

    void indentRight();

    boolean isBgHighLight();

    boolean isBold();

    boolean isBullet();

    boolean isCanSplitLine();

    boolean isCenter();

    boolean isCheckbox();

    boolean isH1();

    boolean isH2();

    boolean isH3();

    boolean isIndentLeft();

    boolean isIndentRight();

    boolean isItalic();

    boolean isLeft();

    boolean isListBullet();

    boolean isQuote();

    boolean isRight();

    boolean isStrikeThrough();

    boolean isUnderline();

    void italic();

    void left();

    boolean listBullet();

    void quote();

    void resetRichTextStatus();

    void right();

    void splitLine();

    void strikeThrough();

    void underline();
}
