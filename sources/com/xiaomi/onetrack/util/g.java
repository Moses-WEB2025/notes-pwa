package com.xiaomi.onetrack.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
class g extends BroadcastReceiver {
    final /* synthetic */ e a;

    g(e eVar) {
        this.a = eVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            String stringExtra = intent.getStringExtra("host");
            String stringExtra2 = intent.getStringExtra("packagename");
            String stringExtra3 = intent.getStringExtra(com.xiaomi.onetrack.b.p.a.e);
            String stringExtra4 = intent.getStringExtra("user");
            boolean booleanExtra = intent.getBooleanExtra("logon", false);
            boolean booleanExtra2 = intent.getBooleanExtra("quickuploadon", false);
            String strE = com.xiaomi.onetrack.f.a.e();
            if (!TextUtils.isEmpty(stringExtra2) && !"".equals(stringExtra2) && strE.equals(stringExtra2)) {
                r.a = booleanExtra;
                r.b = booleanExtra2;
                if (booleanExtra2 && this.a.a(stringExtra)) {
                    this.a.a(stringExtra, stringExtra3, stringExtra4);
                }
            }
        } catch (Exception e) {
            r.b(e.a, e.getMessage());
        }
    }
}
