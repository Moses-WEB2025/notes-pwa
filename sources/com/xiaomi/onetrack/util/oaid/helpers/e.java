package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class e {
    private static final String d = "HonorDeviceIDHelper";
    public final LinkedBlockingQueue<IBinder> a = new LinkedBlockingQueue<>(1);
    public final LinkedBlockingQueue<String> b = new LinkedBlockingQueue<>(1);
    ServiceConnection c = new ServiceConnection() { // from class: com.xiaomi.onetrack.util.oaid.helpers.HonorDeviceIDHelper$1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder == null) {
                return;
            }
            try {
                this.a.a.offer(iBinder, 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                Log.d("HonorDeviceIDHelper", "onServiceConnected get exception: " + e.getMessage());
            }
        }
    };
    private a e;

    public String a(Context context) throws InterruptedException {
        IBinder iBinderPoll;
        String strPoll = "";
        try {
            try {
                Intent intent = new Intent("com.hihonor.id.HnOaIdService");
                intent.setPackage("com.hihonor.id");
                if (context.bindService(intent, this.c, 1) && (iBinderPoll = this.a.poll(1L, TimeUnit.SECONDS)) != null) {
                    com.xiaomi.onetrack.util.oaid.a.c.b bVar = new com.xiaomi.onetrack.util.oaid.a.c.b(iBinderPoll);
                    a aVar = new a();
                    this.e = aVar;
                    bVar.a(aVar);
                    strPoll = this.b.poll(1L, TimeUnit.SECONDS);
                }
            } catch (Exception e) {
                Log.e(d, "aidl getOaid error: " + e.getMessage());
            }
            return strPoll;
        } finally {
            context.unbindService(this.c);
        }
    }

    public class a extends com.xiaomi.onetrack.util.oaid.a.c.a.AbstractBinderC0124a {
        @Override // com.xiaomi.onetrack.util.oaid.a.c.a
        public void a(int i, long j, boolean z, float f, double d, String str) {
        }

        public a() {
        }

        @Override // com.xiaomi.onetrack.util.oaid.a.c.a
        public void a(int i, Bundle bundle) {
            if (bundle == null || !bundle.containsKey("oa_id_flag")) {
                return;
            }
            try {
                e.this.b.offer(bundle.getString("oa_id_flag"), 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                Log.d(e.d, "callback exception: " + e.getMessage());
            }
        }
    }
}
