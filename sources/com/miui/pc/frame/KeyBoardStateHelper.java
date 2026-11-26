package com.miui.pc.frame;

/* JADX INFO: loaded from: classes4.dex */
public class KeyBoardStateHelper {
    private static boolean isCtrlPress = false;

    public static void setCtrlPress(boolean z) {
        isCtrlPress = z;
    }

    public static boolean isCtrlPress() {
        return isCtrlPress;
    }
}
