package com.miui.maml.util;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

/* JADX INFO: loaded from: classes3.dex */
public interface MamlViewManager extends ViewManager {
    void addView(View view, int i, ViewGroup.LayoutParams layoutParams);

    default Context getViewContext() {
        return null;
    }

    default Rect getViewLocationOnScreen() {
        return null;
    }
}
