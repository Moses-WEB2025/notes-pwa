package com.xiaomi.onetrack.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class e {
    private static final String a = "e";
    private static final String b = "com.xiaomi.onetrack.DEBUG";
    private static final String c = "com.xiaomi.onetrack.permissions.DEBUG_MODE";
    private static final String d = "/api/open/device/writeBack";
    private static final String e = "http://";
    private static final String f = "https://";
    private static final String g = ".mi.com";
    private static volatile e h = null;
    private static final int i = 100;
    private final Context j;
    private Handler k = new f(this, Looper.getMainLooper());
    private BroadcastReceiver l = new g(this);

    public static e a() {
        if (h == null) {
            synchronized (e.class) {
                if (h == null) {
                    h = new e();
                }
            }
        }
        return h;
    }

    private e() {
        Context contextB = com.xiaomi.onetrack.f.a.b();
        this.j = contextB;
        a(contextB);
    }

    private void a(Context context) {
        if (context == null) {
            return;
        }
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(b);
            if (Build.VERSION.SDK_INT >= 33) {
                context.registerReceiver(this.l, intentFilter, c, null, 2);
            } else {
                context.registerReceiver(this.l, intentFilter, c, null);
            }
        } catch (Exception e2) {
            r.a(a, "registerDebugModeReceiver: " + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str) {
        return !TextUtils.isEmpty(str) && str.endsWith(g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, String str3) {
        j.a(new h(this, str, str2, str3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int iOptInt = jSONObject.optInt("code");
            String strOptString = jSONObject.optString("message");
            String strOptString2 = jSONObject.optString("result");
            boolean zOptBoolean = jSONObject.optBoolean("success");
            Message messageObtain = Message.obtain();
            messageObtain.what = 100;
            Bundle bundle = new Bundle();
            if (iOptInt == 0 && zOptBoolean) {
                bundle.putString("hint", strOptString2);
            } else {
                bundle.putString("hint", strOptString);
            }
            messageObtain.setData(bundle);
            this.k.sendMessage(messageObtain);
        } catch (JSONException e2) {
            r.b(a, e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str) {
        Toast.makeText(this.j, str, 1).show();
    }
}
