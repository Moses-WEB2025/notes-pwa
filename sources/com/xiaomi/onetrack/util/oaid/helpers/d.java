package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.onetrack.util.r;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class d {
    private static final String c = "HWDeviceIDHelper";
    public final LinkedBlockingQueue<IBinder> a = new LinkedBlockingQueue<>(1);
    ServiceConnection b = new ServiceConnection() { // from class: com.xiaomi.onetrack.util.oaid.helpers.HWDeviceIDHelper$1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.a.a.offer(iBinder, 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                r.a("HWDeviceIDHelper", e.getMessage());
            }
        }
    };

    public String a(Context context) {
        ServiceConnection serviceConnection;
        Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
        intent.setPackage("com.huawei.hwid");
        String strA = "";
        if (context.bindService(intent, this.b, 1)) {
            try {
                try {
                    try {
                        IBinder iBinderPoll = this.a.poll(1L, TimeUnit.SECONDS);
                        if (iBinderPoll != null) {
                            strA = new com.xiaomi.onetrack.util.oaid.a.b.a(iBinderPoll).a();
                            serviceConnection = this.b;
                        } else {
                            try {
                                context.unbindService(this.b);
                                return "";
                            } catch (Exception e) {
                                r.a(c, e.getMessage());
                                return "";
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            context.unbindService(this.b);
                        } catch (Exception e2) {
                            r.a(c, e2.getMessage());
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    r.a(c, e3.getMessage());
                }
            } catch (Exception e4) {
                r.a(c, e4.getMessage());
                serviceConnection = this.b;
            }
            context.unbindService(serviceConnection);
        }
        return strA;
    }
}
