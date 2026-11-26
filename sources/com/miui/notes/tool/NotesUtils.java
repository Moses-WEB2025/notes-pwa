package com.miui.notes.tool;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import miuix.android.content.MiuiIntent;

/* JADX INFO: loaded from: classes4.dex */
public class NotesUtils {
    public static boolean isInLockState(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        if (keyguardManager != null) {
            return keyguardManager.isKeyguardLocked();
        }
        return false;
    }

    public static boolean isAddLockFlag(Context context, Intent intent) {
        return isInLockState(context) && intent.getBooleanExtra(MiuiIntent.EXTRA_START_ACTIVITY_WHEN_LOCKED, false);
    }
}
