package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.IBinder;
import com.xiaomi.onetrack.util.r;
import java.security.MessageDigest;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class k {
    private static final String d = "OppoDeviceIDHelper";
    com.xiaomi.onetrack.util.oaid.a.f a;
    public final LinkedBlockingQueue<IBinder> b = new LinkedBlockingQueue<>(1);
    ServiceConnection c = new ServiceConnection() { // from class: com.xiaomi.onetrack.util.oaid.helpers.OppoDeviceIDHelper$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.a.b.offer(iBinder, 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                r.a("OppoDeviceIDHelper", e.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.a.a = null;
        }
    };
    private String e;

    public String a(Context context) {
        ServiceConnection serviceConnection;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.heytap.openid", "com.heytap.openid.IdentifyService"));
        intent.setAction("action.com.heytap.openid.OPEN_ID_SERVICE");
        String strA = "";
        if (context.bindService(intent, this.c, 1)) {
            try {
                try {
                    try {
                        IBinder iBinderPoll = this.b.poll(1L, TimeUnit.SECONDS);
                        if (iBinderPoll != null) {
                            com.xiaomi.onetrack.util.oaid.a.f fVarA = com.xiaomi.onetrack.util.oaid.a.f.a.a(iBinderPoll);
                            this.a = fVarA;
                            if (fVarA != null) {
                                strA = a("OUID", context);
                            }
                            serviceConnection = this.c;
                        } else {
                            try {
                                context.unbindService(this.c);
                                return "";
                            } catch (Exception e) {
                                r.a(d, e.getMessage());
                                return "";
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            context.unbindService(this.c);
                        } catch (Exception e2) {
                            r.a(d, e2.getMessage());
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    r.a(d, e3.getMessage());
                }
            } catch (Exception e4) {
                r.a(d, e4.getMessage());
                serviceConnection = this.c;
            }
            context.unbindService(serviceConnection);
        }
        return strA;
    }

    private String a(String str, Context context) {
        Signature[] signatureArr;
        String packageName = context.getPackageName();
        if (this.e == null) {
            String string = null;
            try {
                signatureArr = context.getPackageManager().getPackageInfo(packageName, 64).signatures;
            } catch (Exception e) {
                e.printStackTrace();
                signatureArr = null;
            }
            if (signatureArr != null && signatureArr.length > 0) {
                byte[] byteArray = signatureArr[0].toByteArray();
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                    if (messageDigest != null) {
                        byte[] bArrDigest = messageDigest.digest(byteArray);
                        StringBuilder sb = new StringBuilder();
                        for (byte b : bArrDigest) {
                            sb.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
                        }
                        string = sb.toString();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.e = string;
        }
        return ((com.xiaomi.onetrack.util.oaid.a.f.a.C0127a) this.a).a(packageName, this.e, str);
    }

    private boolean b(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.heytap.openid", 0);
            return packageInfo != null && ((long) packageInfo.versionCode) >= 1;
        } catch (Exception e) {
            r.a(d, e.getMessage());
            return true;
        }
    }
}
