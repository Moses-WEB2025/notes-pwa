package com.xiaomi.accounts;

import android.accounts.AuthenticatorDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: classes4.dex */
public class AccountAuthenticatorCache {
    private static final String MI_ACCOUNT_TYPE = "com.xiaomi";
    private static final String TAG = "Account";
    private Context mContext;
    private final String mInterfaceName = AccountManager.ACTION_AUTHENTICATOR_INTENT;
    private ServiceInfo<AuthenticatorDescription> mServiceInfo;

    public AccountAuthenticatorCache(Context context) {
        this.mContext = context;
        generateServicesMap();
    }

    void generateServicesMap() {
        Intent intent = new Intent(this.mInterfaceName);
        intent.setPackage(this.mContext.getPackageName());
        this.mServiceInfo = parseServiceInfo(this.mContext.getPackageManager().resolveService(intent, 0));
    }

    private ServiceInfo<AuthenticatorDescription> parseServiceInfo(ResolveInfo resolveInfo) {
        String packageName;
        ApplicationInfo applicationInfo;
        String str;
        if (resolveInfo != null) {
            android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            packageName = serviceInfo.packageName;
            str = serviceInfo.name;
            applicationInfo = serviceInfo.applicationInfo;
        } else {
            packageName = this.mContext.getPackageName();
            applicationInfo = this.mContext.getApplicationInfo();
            str = "com.xiaomi.passport.accountmanager.MiAuthenticatorService";
        }
        String str2 = packageName;
        return new ServiceInfo<>(new AuthenticatorDescription("com.xiaomi", str2, applicationInfo.labelRes, applicationInfo.icon, applicationInfo.icon, -1), new ComponentName(str2, str), applicationInfo.uid);
    }

    public ServiceInfo<AuthenticatorDescription> getServiceInfo(AuthenticatorDescription authenticatorDescription) {
        if (authenticatorDescription == null || !TextUtils.equals("com.xiaomi", authenticatorDescription.type)) {
            AccountLogger.log(TAG, "no xiaomi account type");
            return null;
        }
        return this.mServiceInfo;
    }

    public Collection<ServiceInfo<AuthenticatorDescription>> getAllServices() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mServiceInfo);
        return Collections.unmodifiableCollection(arrayList);
    }

    public static class ServiceInfo<V> {
        public final ComponentName componentName;
        public final V type;
        public final int uid;

        public ServiceInfo(V v, ComponentName componentName, int i) {
            this.type = v;
            this.componentName = componentName;
            this.uid = i;
        }

        public String toString() {
            return "ServiceInfo: " + this.type + ", " + this.componentName + ", uid " + this.uid;
        }
    }
}
