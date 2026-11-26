package com.miui.notes.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.miui.common.tool.ShortcutsTools;

/* JADX INFO: loaded from: classes4.dex */
public class LanguageReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        ShortcutsTools.initShortcuts();
    }
}
