package ho;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes4.dex */
public final class e {
    public static String a(Context context) {
        PackageManager packageManager = ((Context) Objects.requireNonNull(context)).getPackageManager();
        Iterator<ResolveInfo> it = (Build.VERSION.SDK_INT >= 33 ? packageManager.queryIntentServices(new Intent("com.xiaomi.dist.intent.action.APP_HANDOFF"), PackageManager.ResolveInfoFlags.of(0L)) : packageManager.queryIntentServices(new Intent("com.xiaomi.dist.intent.action.APP_HANDOFF"), 0)).iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().serviceInfo;
            if (serviceInfo != null && serviceInfo.isEnabled()) {
                String str = serviceInfo.packageName;
                if (Objects.equals(serviceInfo.name, "com.xiaomi.dist.handoff.AppHandoffService") && "com.milink.service".equals(str) && (serviceInfo.applicationInfo.flags & 1) != 0) {
                    return str;
                }
            }
        }
        return null;
    }

    public static boolean b(Context context) {
        int i;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            i = Integer.parseInt((String) cls.getMethod(CallMethod.METHOD_GET, String.class, String.class).invoke(cls, "ro.miui.ui.version.code", "0"));
        } catch (Throwable th) {
            j.a("HU", "getMiuiVersion error", th);
            i = 0;
        }
        j.a("HU", "getMiuiVersion ,version=%s", Integer.valueOf(i));
        if (i < 15) {
            j.a("HU", "isSupported, miui version not support");
            return false;
        }
        boolean z = !TextUtils.isEmpty(a(context));
        j.a("HU", "isSupported=%s", Boolean.valueOf(z));
        return z;
    }

    public static String a(Supplier<Uri> supplier) {
        Uri uri = supplier != null ? supplier.get() : null;
        String string = uri != null ? uri.toString() : "";
        j.a("HU", "getUriString : uriString.length=", Integer.valueOf(string.length()));
        return string;
    }
}
