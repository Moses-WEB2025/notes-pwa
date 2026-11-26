package com.miui.notes.ui.widget;

/* JADX INFO: loaded from: classes4.dex */
public interface ViewHolderEditableCallback {
    boolean hasAnimationStarted();

    void onAnimationStart(boolean z);

    void onAnimationStop(boolean z);

    void onAnimationUpdate(boolean z, float f);

    void onClearAnimation();

    boolean onUpdateEditable(boolean z, boolean z2);
}
