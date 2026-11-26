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
public class a {
    private static final String c = "ASUSDeviceIDHelper";
    public final LinkedBlockingQueue<IBinder> a = new LinkedBlockingQueue<>(1);
    ServiceConnection b = new ServiceConnection() { // from class: com.xiaomi.onetrack.util.oaid.helpers.ASUSDeviceIDHelper$1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.a.a.offer(iBinder, 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                r.a("ASUSDeviceIDHelper", e.getMessage());
            }
        }
    };

    public String a(Context context) {
        ServiceConnection serviceConnection;
        Intent intent = new Intent();
        intent.setAction("com.asus.msa.action.ACCESS_DID");
        intent.setComponent(new ComponentName("com.asus.msa.SupplementaryDID", "com.asus.msa.SupplementaryDID.SupplementaryDIDService"));
        String strA = "";
        try {
            if (context.bindService(intent, this.b, 1)) {
                try {
                    try {
                        IBinder iBinderPoll = this.a.poll(1L, TimeUnit.SECONDS);
                        if (iBinderPoll != null) {
                            strA = new com.xiaomi.onetrack.util.oaid.a.a.C0123a(iBinderPoll).a();
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
                    serviceConnection = this.b;
                }
                context.unbindService(serviceConnection);
            }
        } catch (Exception e4) {
            r.a(c, e4.getMessage());
        }
        return strA;
    }
}
