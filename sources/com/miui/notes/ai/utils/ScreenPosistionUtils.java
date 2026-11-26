package com.miui.notes.ai.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import miuix.os.DeviceHelper;

/* JADX INFO: loaded from: classes4.dex */
public class ScreenPosistionUtils {

    public enum Position {
        LEFT,
        RIGHT,
        MIDDLE
    }

    public static Position calWindowPosition(Rect rect, Context context, int i) {
        if (FreeModeUtils.isHostInFreeMode(i) != null) {
            return Position.MIDDLE;
        }
        DisplayMetrics screenMetrics = DisplayUtil.getScreenMetrics(context);
        int i2 = rect.right - rect.left;
        int i3 = rect.bottom;
        int i4 = rect.top;
        if (RomUtils.isPhoneDevice()) {
            return Position.MIDDLE;
        }
        if (i2 != screenMetrics.widthPixels) {
            if (SplitScreenModelUtils.isSplitScreenMode(i) == SplitScreenModelUtils.SplitResult.TRUE || SplitScreenModelUtils.isSplitScreenMode(i) == SplitScreenModelUtils.SplitResult.NOT_FOUND) {
                if (rect.left + (i2 / 2) < screenMetrics.widthPixels / 2) {
                    return Position.LEFT;
                }
                return Position.RIGHT;
            }
            return Position.MIDDLE;
        }
        return Position.MIDDLE;
    }

    public static boolean isInNarrowScreen(Context context) {
        return !DeviceHelper.isWideScreen(context);
    }
}
