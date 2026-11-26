package micloud.compat.independent.utils;

import android.content.Context;

/* JADX INFO: loaded from: classes5.dex */
class RelocationCacheCompat_V18 extends RelocationCacheCompat_Base {
    private static final String MICLOUD_ACCOUNTNAME_V2 = "pref_micloud_accountname_v2";
    private static final String MICLOUD_HOSTS_V2 = "pref_micloud_hosts_v2";
    private static final String MICLOUD_SP_NAME = "pref_relocation_cache";

    RelocationCacheCompat_V18() {
    }

    @Override // micloud.compat.independent.utils.RelocationCacheCompat_Base, micloud.compat.independent.utils.IRelocationCacheCompat
    public void cacheXiaomiAccountName(Context context, String str) {
        context.getSharedPreferences(MICLOUD_SP_NAME, 0).edit().putString(MICLOUD_ACCOUNTNAME_V2, str).commit();
    }

    @Override // micloud.compat.independent.utils.RelocationCacheCompat_Base, micloud.compat.independent.utils.IRelocationCacheCompat
    public String getCachedXiaomiAccountName(Context context) {
        return context.getSharedPreferences(MICLOUD_SP_NAME, 0).getString(MICLOUD_ACCOUNTNAME_V2, "");
    }

    @Override // micloud.compat.independent.utils.RelocationCacheCompat_Base, micloud.compat.independent.utils.IRelocationCacheCompat
    public void cacheHostList(Context context, String str) {
        context.getSharedPreferences(MICLOUD_SP_NAME, 0).edit().putString(MICLOUD_HOSTS_V2, str).commit();
    }

    @Override // micloud.compat.independent.utils.RelocationCacheCompat_Base, micloud.compat.independent.utils.IRelocationCacheCompat
    public String getCachedHostList(Context context) {
        return context.getSharedPreferences(MICLOUD_SP_NAME, 0).getString(MICLOUD_HOSTS_V2, "");
    }
}
