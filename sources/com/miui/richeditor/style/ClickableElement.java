package com.miui.richeditor.style;

import android.graphics.Rect;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes4.dex */
public interface ClickableElement {
    Rect getBoundsInParent();

    CharSequence getContentDescription();

    boolean isTouchIn(int i, int i2);

    void onClick(int i, int i2);

    void onLongClick(int i, int i2);

    void onRightClick(int i, int i2);

    void onSelected(boolean z);

    void onTouchPoint(int i, int i2, MotionEvent motionEvent);

    void setPressed(boolean z);

    void setSelected(boolean z);
}
