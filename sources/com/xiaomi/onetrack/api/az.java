package com.xiaomi.onetrack.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import com.miui.analytics.ITrack;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes4.dex */
public class az {
    public static final String a = "com.miui.analytics";
    public static final String b = "com.miui.analytics.onetrack.OneTrackService";
    private static final String c = "ServiceConnectManager";
    private static final int h = 1;
    private static final int i = 2;
    private static volatile az k = null;
    private static String l = "onetrack_service_connect";
    private volatile ITrack d;
    private b j;
    private AtomicBoolean e = new AtomicBoolean(false);
    private AtomicBoolean f = new AtomicBoolean(false);
    private AtomicBoolean m = new AtomicBoolean(false);
    private ServiceConnection n = new ServiceConnection() { // from class: com.xiaomi.onetrack.api.ServiceConnectionManager$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.a.d = ITrack.Stub.asInterface(iBinder);
                this.a.f.set(true);
                this.a.e.set(false);
                this.a.g();
                com.xiaomi.onetrack.util.r.a("ServiceConnectManager", "onServiceConnected  mConnecting " + this.a.e + " mBindResult:" + this.a.f + " mIOneTrackService " + (this.a.d == null ? 0 : 1) + " pid:" + Process.myPid() + " tid:" + Process.myTid());
            } catch (Throwable th) {
                this.a.c();
                com.xiaomi.onetrack.util.r.a("ServiceConnectManager", "onServiceConnected throwable:" + th.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            try {
                this.a.c();
                com.xiaomi.onetrack.util.r.a("ServiceConnectManager", "onServiceDisconnected:  mConnecting " + this.a.e + " mIOneTrackService " + (this.a.d == null ? 0 : 1));
            } catch (Throwable th) {
                com.xiaomi.onetrack.util.r.a("ServiceConnectManager", "onServiceDisconnected throwable:" + th.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            try {
                com.xiaomi.onetrack.util.r.a("ServiceConnectManager", "onBindingDied");
                try {
                    this.a.g.unbindService(this.a.n);
                } catch (Exception e) {
                    com.xiaomi.onetrack.util.r.a("ServiceConnectManager", "onBindingDied: " + e.toString());
                }
                this.a.c();
            } catch (Throwable th) {
                com.xiaomi.onetrack.util.r.a("ServiceConnectManager", "onBindingDied throwable:" + th.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            try {
                com.xiaomi.onetrack.util.r.a("ServiceConnectManager", "onNullBinding");
                this.a.c();
            } catch (Throwable th) {
                com.xiaomi.onetrack.util.r.a("ServiceConnectManager", "onNullBinding throwable:" + th.getMessage());
            }
        }
    };
    private CopyOnWriteArrayList<a> o = new CopyOnWriteArrayList<>();
    private Context g = com.xiaomi.onetrack.f.a.b();

    public interface a {
        void a();
    }

    public static az a() {
        if (k == null) {
            b();
        }
        return k;
    }

    public static void b() {
        if (k == null) {
            synchronized (az.class) {
                if (k == null) {
                    k = new az();
                }
            }
        }
    }

    private az() {
        HandlerThread handlerThread = new HandlerThread(l);
        handlerThread.start();
        b bVar = new b(handlerThread.getLooper());
        this.j = bVar;
        bVar.sendEmptyMessage(2);
    }

    public boolean a(String str, String str2, String str3) {
        if (!this.e.get()) {
            if (this.d != null) {
                try {
                    this.d.trackEvent(str3, com.xiaomi.onetrack.f.a.e(), str, str2);
                    return true;
                } catch (Throwable th) {
                    f();
                    com.xiaomi.onetrack.util.r.a(c, "track throwable: " + th.getMessage());
                    return false;
                }
            }
            this.j.sendEmptyMessage(2);
        }
        return false;
    }

    public boolean a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        if (!this.e.get()) {
            if (this.d != null) {
                try {
                    this.d.trackPSEvent(str, str2, str3, str4, com.xiaomi.onetrack.f.a.e(), str5, str6, str7);
                    return true;
                } catch (Throwable th) {
                    f();
                    com.xiaomi.onetrack.util.r.a(c, "track throwable: " + th.getMessage());
                    return false;
                }
            }
            this.j.sendEmptyMessage(2);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        this.d = null;
        this.f.set(false);
        this.e.set(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        if (this.e.get() || this.f.get() || this.d != null) {
            com.xiaomi.onetrack.util.r.a(c, "ensureService mConnecting: " + this.e.get() + " mIsBindSuccess:" + this.f.get() + " mAnalytics: " + (this.d == null ? 0 : 1));
        } else {
            e();
        }
    }

    private void e() {
        this.e.set(true);
        try {
            Intent intent = new Intent();
            intent.setClassName(a, b);
            boolean zBindService = this.g.bindService(intent, this.n, 1);
            if (zBindService) {
                this.f.set(true);
            } else {
                this.f.set(false);
                try {
                    this.g.unbindService(this.n);
                } catch (Throwable th) {
                    Log.d(c, "unbindService e1: " + th.getMessage());
                }
            }
            com.xiaomi.onetrack.util.r.a(c, "bindService:  mConnecting: " + this.e + " bindResult:" + zBindService);
        } catch (Throwable th2) {
            try {
                this.f.set(false);
                this.e.set(false);
                this.g.unbindService(this.n);
            } catch (Throwable th3) {
                Log.d(c, "bindService e1: " + th3.getMessage());
            }
            com.xiaomi.onetrack.util.r.b(c, "bindService e: " + th2.getMessage());
        }
        this.e.set(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        try {
            if (this.f.get()) {
                this.g.unbindService(this.n);
            }
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.r.a(c, "unBindService Throwable: " + th.getMessage());
        }
        c();
        com.xiaomi.onetrack.util.r.a(c, "unBindService  mIsBindSuccess:" + this.f.get());
    }

    public void a(a aVar) {
        if (this.o.contains(aVar)) {
            return;
        }
        this.o.add(aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        Iterator<a> it = this.o.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    public void a(int i2) {
        if (i2 == 2) {
            this.j.sendEmptyMessageDelayed(1, 5000L);
        } else if (this.j.hasMessages(1)) {
            this.j.removeMessages(1);
        }
    }

    private class b extends Handler {
        public b(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                az.this.f();
            } else if (message.what == 2) {
                az.this.d();
            }
        }
    }
}
