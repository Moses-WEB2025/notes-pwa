package com.miui.notes.tool.util;

import android.view.View;
import android.view.Window;

/* JADX INFO: loaded from: classes4.dex */
public class WindowUtil {
    private static final int EXTRA_FLAG_STATUS_BAR_DARK_MODE = 16;
    private static final int EXTRA_FLAG_STATUS_BAR_TRANSPARENT = 1;
    private static final int EXTRA_FLAG_STATUS_BAR_TRANSPARENT_MASK = 17;
    public static final int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = Integer.MIN_VALUE;
    private static final int FLAG_TRANSLUCENT_STATUS = 67108864;
    public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;

    public static boolean setTranslucentStatus(Window window, int i) {
        return setTranslucentStatus23(window, i);
    }

    public static boolean setTranslucentStatus23(Window window, int i) {
        if (i == 0) {
            window.clearFlags(Integer.MIN_VALUE);
        } else {
            window.addFlags(Integer.MIN_VALUE);
            View decorView = window.getDecorView();
            if (i == 1) {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 8192);
            } else {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & (-8193));
            }
        }
        return true;
    }
}
