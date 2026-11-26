package com.xiaomi.onetrack.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DeviceUtil {
    static final int a = 15;
    static final int b = 6;
    private static final String c = "DeviceUtil";
    private static final String d = "MI_BOX";
    private static final String e = "MI_TVBOX";
    private static final String f = "MI_PROJECTOR";
    private static final String g = "MI_TV";
    private static final String h = "MI_PAD";
    private static final String i = "MI_WIFI_SPEAKER";
    private static final String j = "MI_PHONE";
    private static final String k = "OTHER";
    private static final String l = "";
    private static final int m = 15;
    private static Method n = null;
    private static String o = null;
    private static String p = null;
    private static volatile boolean q = false;
    private static final String r = "0000000000000000";
    private static String s;
    private static Boolean t;

    public static List<String> c(Context context) {
        return null;
    }

    static {
        try {
            n = Class.forName("android.os.SystemProperties").getMethod(CallMethod.METHOD_GET, String.class);
        } catch (Throwable th) {
            r.b(c, "sGetProp init failed ex: " + th.getMessage());
        }
        t = null;
    }

    public static String a(Context context) {
        return "";
    }

    public static String b(Context context) {
        if (!TextUtils.isEmpty(o)) {
            return o;
        }
        if (GAIDClient.b(context)) {
            return "";
        }
        String strA = GAIDClient.a(context);
        if (TextUtils.isEmpty(strA)) {
            return "";
        }
        o = strA;
        return strA;
    }

    public static void a() {
        o = null;
    }

    private static String a(String str) {
        try {
            Method method = n;
            if (method != null) {
                return String.valueOf(method.invoke(null, str));
            }
        } catch (Throwable th) {
            r.a(c, "getProp failed ex: " + th.getMessage());
        }
        return null;
    }

    private static class GAIDClient {
        private static final String a = "GAIDClient";

        private GAIDClient() {
        }

        static String a(Context context) {
            if (!c(context)) {
                r.a(a, "Google play service is not available");
                return "";
            }
            AdvertisingConnection advertisingConnection = new AdvertisingConnection();
            try {
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage("com.google.android.gms");
                if (context.bindService(intent, advertisingConnection, 1)) {
                    String strA = new a(advertisingConnection.a()).a();
                    context.unbindService(advertisingConnection);
                    return strA;
                }
            } catch (Throwable th) {
                try {
                    r.a(a, "Query Google ADID failed: " + th.getMessage());
                } finally {
                    context.unbindService(advertisingConnection);
                }
            }
            return "";
        }

        static boolean b(Context context) {
            if (!c(context)) {
                r.a(a, "Google play service is not available");
                return false;
            }
            AdvertisingConnection advertisingConnection = new AdvertisingConnection();
            try {
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage("com.google.android.gms");
                if (context.bindService(intent, advertisingConnection, 1)) {
                    boolean zA = new a(advertisingConnection.a()).a(true);
                    context.unbindService(advertisingConnection);
                    return zA;
                }
            } catch (Throwable th) {
                try {
                    r.a(a, "Query Google isLimitAdTrackingEnabled failed :" + th.getMessage());
                } finally {
                    context.unbindService(advertisingConnection);
                }
            }
            return false;
        }

        private static boolean c(Context context) {
            try {
                context.getPackageManager().getPackageInfo("com.android.vending", 16384);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        private static final class AdvertisingConnection implements ServiceConnection {
            private static final int a = 30000;
            private boolean b;
            private IBinder c;

            private AdvertisingConnection() {
                this.b = false;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                synchronized (this) {
                    this.c = iBinder;
                    notifyAll();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.b = true;
                this.c = null;
            }

            public IBinder a() throws InterruptedException {
                IBinder iBinder = this.c;
                if (iBinder != null) {
                    return iBinder;
                }
                if (iBinder == null && !this.b) {
                    synchronized (this) {
                        wait(30000L);
                        if (this.c == null) {
                            throw new InterruptedException("Not connect or connect timeout to google play service");
                        }
                    }
                }
                return this.c;
            }
        }

        private static final class a implements IInterface {
            private IBinder a;

            public a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            public String a() throws RemoteException {
                if (this.a == null) {
                    return "";
                }
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public boolean a(boolean z) throws RemoteException {
                if (this.a == null) {
                    return false;
                }
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static String d(Context context) {
        return "";
    }

    public static String b() {
        return Build.MODEL;
    }

    public static String c() {
        return a("ro.product.marketname");
    }

    public static String d() {
        return Build.MANUFACTURER;
    }

    public static String e(Context context) {
        return com.xiaomi.onetrack.util.oaid.a.a().a(context);
    }

    public static void e() {
        com.xiaomi.onetrack.util.oaid.a.a().b();
    }

    public static boolean f() {
        return com.xiaomi.onetrack.util.oaid.a.a().c();
    }

    public static String f(Context context) {
        try {
            if (ab.b(p)) {
                return p;
            }
            String string = Settings.System.getString(context.getContentResolver(), "android_id");
            if (ab.b(string) && !r.equals(string)) {
                p = string;
                return string;
            }
            return "";
        } catch (Throwable th) {
            r.b(c, "getandroid d throwable:" + th.getMessage());
            return "";
        }
    }

    public static boolean g() {
        if (t == null) {
            String strB = b();
            if (ab.b(strB)) {
                String upperCase = strB.toUpperCase();
                if (upperCase.contains("MITV") || upperCase.contains("MIBOX") || upperCase.contains("PROJECTOR")) {
                    Boolean bool = true;
                    t = bool;
                    return bool.booleanValue();
                }
            }
            t = false;
        }
        return t.booleanValue();
    }

    public static String h() {
        if (!TextUtils.isEmpty(s)) {
            return s;
        }
        String strB = a.b();
        s = strB;
        if (!TextUtils.isEmpty(strB)) {
            return s;
        }
        if (i()) {
            s = h;
            return h;
        }
        if (j()) {
            s = i;
            return i;
        }
        if (s.b()) {
            s = j;
            return j;
        }
        s = "OTHER";
        return "OTHER";
    }

    private static boolean i() {
        try {
            Class<?> cls = Class.forName("miui.os.Build");
            if (cls != null) {
                return ((Boolean) cls.getField("IS_TABLET").get(null)).booleanValue();
            }
        } catch (Throwable th) {
            r.c(c, "get IS_TABLET from miui.os.Build exception: " + th.getMessage());
        }
        try {
            return l.a("is_pad", false);
        } catch (Throwable th2) {
            r.c(c, "get is_pad from FeatureParser exception: " + th2.getMessage());
            return false;
        }
    }

    private static class a {
        private a() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String b() {
            try {
                Class<?> cls = Class.forName("mitv.common.ConfigurationManager");
                int i = Integer.parseInt(String.valueOf(cls.getMethod("getProductCategory", new Class[0]).invoke(cls.getMethod("getInstance", new Class[0]).invoke(cls, new Object[0]), new Object[0])));
                Class<?> cls2 = Class.forName("mitv.tv.TvContext");
                if (i == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MITV")))) {
                    return DeviceUtil.g;
                }
                if (i == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MIBOX")))) {
                    return DeviceUtil.d;
                }
                if (i == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MITVBOX")))) {
                    return DeviceUtil.e;
                }
                if (i != Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MIPROJECTOR")))) {
                    return "";
                }
                return DeviceUtil.f;
            } catch (Throwable th) {
                r.c(DeviceUtil.c, "getMiTvProductCategory exception: " + th.getMessage());
                return "";
            }
        }

        private static <T> T a(Class<?> cls, String str) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return (T) declaredField.get(null);
            } catch (Throwable th) {
                r.c(DeviceUtil.c, "getStaticVariableValue exception: " + th.getMessage());
                return null;
            }
        }
    }

    private static boolean j() {
        String strA = ad.a("ro.product.brand");
        return !TextUtils.isEmpty(strA) && strA.contains("XiaoAiTongXue");
    }
}
