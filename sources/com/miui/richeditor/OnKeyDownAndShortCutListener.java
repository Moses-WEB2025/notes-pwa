package com.miui.richeditor;

import android.view.KeyEvent;

/* JADX INFO: loaded from: classes4.dex */
public interface OnKeyDownAndShortCutListener {
    boolean onKeyDown(int i, KeyEvent keyEvent);

    boolean onKeyShortcut(int i, KeyEvent keyEvent);

    boolean onKeyUp(int i, KeyEvent keyEvent);
}
