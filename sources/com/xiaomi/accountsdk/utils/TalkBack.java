package com.xiaomi.accountsdk.utils;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;

/* JADX INFO: loaded from: classes4.dex */
public class TalkBack {
    public static boolean isActive(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        return accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();
    }
}
