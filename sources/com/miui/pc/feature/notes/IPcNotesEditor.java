package com.miui.pc.feature.notes;

import android.view.View;

/* JADX INFO: loaded from: classes4.dex */
public interface IPcNotesEditor {

    public interface RichText {
        void bgHighLight();

        void bgHighLight(boolean z);

        void bold();

        void bold(boolean z);

        void bullet(boolean z);

        boolean bullet();

        void center();

        void center(boolean z);

        boolean check();

        void clearFormat();

        int getSize();

        boolean isBgHighLight();

        boolean isBold();

        boolean isBullet();

        boolean isCenter();

        boolean isItalic();

        boolean isRight();

        boolean isSizeH1();

        boolean isSizeH2();

        boolean isUnderline();

        void italic();

        void italic(boolean z);

        void resetRichTextStatus();

        void right();

        void right(boolean z);

        void size();

        void size(int i);

        void sizeH1();

        void sizeH2();

        void underline();

        void underline(boolean z);
    }

    public interface ToolBar extends RichText {
        View getView();

        void hide(boolean z);

        void lock();

        void redo();

        void setActionListener(View.OnClickListener onClickListener);

        void show(boolean z);

        void undo();

        void unlock();

        void updateStyle(int i);

        void updateUndoState(boolean z, boolean z2);
    }

    void redo();

    void undo();
}
