package com.xiaomi.onetrack.util;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import com.miui.common.stat.NoteAppStat;
import com.miui.mishare.tapshare.Util.TapShareConstant;
import com.miui.notes.ui.view.SearchCallback;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.onetrack.api.az;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class w {
    private static final String a = "PermissionUtil";
    private static final String b = "android.permission.READ_PRIVILEGED_PHONE_STATE";
    private static Set<String> c;

    static {
        try {
            HashSet hashSet = new HashSet();
            c = hashSet;
            hashSet.add(AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
            c.add(az.a);
            c.add("com.miui.cit");
            c.add(AccountIntent.PACKAGE_NAME_FIND_DEVICE);
            c.add(NoteAppStat.APP_PKG.securitycenter);
            c.add(TapShareConstant.SETTING_PACKAGE_NAME);
            c.add("com.android.vending");
            c.add("com.google.android.gms");
            c.add("com.xiaomi.factory.mmi");
            c.add("com.miui.qr");
            c.add(SearchCallback.TextSearch.PHONE_NUMBER_SEARCH_KEY);
            c.add("com.qualcomm.qti.autoregistration");
            c.add("com.miui.tsmclient");
            c.add("com.miui.sekeytool");
            c.add("com.android.updater");
            if (!"cn_chinamobile".equals(ad.a("ro.miui.cust_variant")) && !"cn_chinatelecom".equals(ad.a("ro.miui.cust_variant"))) {
                return;
            }
            c.add("com.mobiletools.systemhelper");
            c.add("com.miui.dmregservice");
        } catch (Exception e) {
            Log.e(a, "static initializer: " + e.toString());
        }
    }

    public static boolean a(Context context) {
        if (a()) {
            return a(com.xiaomi.onetrack.f.a.e()) && a(context, b);
        }
        return a(context, b);
    }

    public static boolean b(Context context) {
        return a(context, b);
    }

    private static boolean a() {
        try {
            return s.b() && !s.k() && "1".equals(ad.a("ro.miui.restrict_imei"));
        } catch (Exception e) {
            r.b(a, "isRestrictIMEI " + e.toString());
            return false;
        }
    }

    private static boolean a(String str) {
        Set<String> set;
        return (TextUtils.isEmpty(str) || (set = c) == null || !set.contains(str)) ? false : true;
    }

    private static boolean a(Context context, String str) {
        return context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }
}
