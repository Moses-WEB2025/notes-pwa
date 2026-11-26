package miui.cloud;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncAdapterType;
import android.text.TextUtils;
import com.miui.notes.ui.view.SearchCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import miui.cloud.os.MultiuserUtils;
import miui.cloud.sync.providers.GlobalBrowserSyncInfoProvider;
import miui.cloud.util.BuildUtil;
import miui.cloud.util.DeviceFeatureUtils;
import miui.cloud.util.PkgInfoUtil;
import miui.cloud.util.SysHelper;
import miui.os.Build;

/* JADX INFO: loaded from: classes5.dex */
public class AuthoritiesModel {
    private static final HashMap<String, String> AUTHORITY_TO_MIUI_APP_PKG_MAP;
    private static final HashMap<String, String[]> AUTHORITY_TO_PKG_MAP;
    private static final String DEFAULT_TYPE_XIAOMI_ACCOUNT = "com.xiaomi";
    private static final String FEATURE_SUPPORT_GOOGLE_CSP_SYNC = "support_google_csp_sync";
    public static final IFilter UNAVAILABLE_AUTHORITIES_FILTER;
    private final Account mAccount;
    private final String mAccountType;
    private List<String> mAuthorities;
    private final Context mContext;

    public interface IFilter {
        void filter(Context context, List<String> list);
    }

    public interface ISorter {
        void sort(Context context, List<String> list);
    }

    static {
        HashMap<String, String> map = new HashMap<>();
        AUTHORITY_TO_MIUI_APP_PKG_MAP = map;
        HashMap<String, String[]> map2 = new HashMap<>();
        AUTHORITY_TO_PKG_MAP = map2;
        map.put("sms", "com.android.mms");
        map.put(SearchCallback.TextSearch.PHONE_NUMBER_SEARCH_KEY, SearchCallback.TextSearch.PHONE_NUMBER_SEARCH_KEY);
        map.put("call_log", SearchCallback.TextSearch.PHONE_NUMBER_SEARCH_KEY);
        map2.put("com.android.calendar", new String[]{"com.android.calendar", "com.xiaomi.calendar"});
        UNAVAILABLE_AUTHORITIES_FILTER = new UnAvailableAuthoritiesFilter();
    }

    public AuthoritiesModel(Context context, Account account) {
        this.mAuthorities = new ArrayList();
        this.mContext = context;
        this.mAccount = account;
        this.mAccountType = account.type;
    }

    public AuthoritiesModel(Context context, Account account, List<String> list) {
        this.mAuthorities = new ArrayList();
        this.mContext = context;
        this.mAccount = account;
        this.mAccountType = account.type;
        this.mAuthorities.addAll(list);
    }

    public AuthoritiesModel(Context context) {
        this.mAuthorities = new ArrayList();
        this.mContext = context;
        this.mAccount = null;
        this.mAccountType = "com.xiaomi";
    }

    public static boolean isMiuiAppAuthority(Context context, String str) {
        String str2 = AUTHORITY_TO_MIUI_APP_PKG_MAP.get(str);
        return str2 == null || PkgInfoUtil.isPkgExist(context, str2);
    }

    public AuthoritiesModel getAllAuthorities() {
        Account account;
        for (SyncAdapterType syncAdapterType : ContentResolver.getSyncAdapterTypes()) {
            if (syncAdapterType.isUserVisible() && TextUtils.equals(this.mAccountType, syncAdapterType.accountType)) {
                String str = syncAdapterType.authority;
                if (this.mContext.getPackageManager().resolveContentProvider(str, 0) != null && ((account = this.mAccount) == null || ContentResolver.getIsSyncable(account, str) > 0 || syncAdapterType.isAlwaysSyncable())) {
                    this.mAuthorities.add(str);
                }
            }
        }
        return this;
    }

    public AuthoritiesModel sortBy(ISorter iSorter) {
        iSorter.sort(this.mContext, this.mAuthorities);
        return this;
    }

    public AuthoritiesModel filterBy(IFilter iFilter) {
        iFilter.filter(this.mContext, this.mAuthorities);
        return this;
    }

    public AuthoritiesModel filter(String[] strArr) {
        return filter(Arrays.asList(strArr));
    }

    public AuthoritiesModel filter(List<String> list) {
        this.mAuthorities.removeAll(list);
        return this;
    }

    public List<String> toList() {
        return this.mAuthorities;
    }

    private static class UnAvailableAuthoritiesFilter implements IFilter {
        private UnAvailableAuthoritiesFilter() {
        }

        @Override // miui.cloud.AuthoritiesModel.IFilter
        public void filter(Context context, List<String> list) {
            list.remove(CloudSyncUtils.MMSLITE_PROVIDER_AUTHORITY);
            list.remove("com.miui.player.cloud");
            list.remove("com.miui.player");
            if (!Build.IS_TABLET) {
                list.remove("com.miui.creation.provider");
            }
            if (!DeviceFeatureUtils.hasDeviceFeature("support_google_csp_sync")) {
                for (String str : AuthoritiesModel.AUTHORITY_TO_MIUI_APP_PKG_MAP.keySet()) {
                    if (!AuthoritiesModel.isMiuiAppAuthority(context, str)) {
                        list.remove(str);
                    }
                }
            }
            list.removeAll(getPkgInfoNotExistAuthorities(context));
            if (!SysHelper.hasSmsCapability(context)) {
                list.remove("sms");
            }
            if (!SysHelper.hasVoiceCapability(context)) {
                list.remove("call_log");
            }
            if (!SysHelper.hasModemCapability()) {
                list.remove("antispam");
            }
            if (BuildUtil.isInternationalBuild()) {
                list.remove("antispam");
            }
            if (MultiuserUtils.myUserId() != MultiuserUtils.get_USER_OWNER()) {
                list.remove("antispam");
                list.remove("sms");
            }
            if (list.contains("com.miui.browser") && list.contains(GlobalBrowserSyncInfoProvider.AUTHORITY)) {
                if (BuildUtil.isInternationalBuild()) {
                    list.remove("com.miui.browser");
                } else {
                    list.remove(GlobalBrowserSyncInfoProvider.AUTHORITY);
                }
            }
        }

        private List<String> getPkgInfoNotExistAuthorities(Context context) {
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : AuthoritiesModel.AUTHORITY_TO_PKG_MAP.entrySet()) {
                String str = (String) entry.getKey();
                String[] strArr = (String[]) entry.getValue();
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        if (PkgInfoUtil.isPkgExist(context, strArr[i])) {
                            break;
                        }
                        i++;
                    } else {
                        arrayList.add(str);
                        break;
                    }
                }
            }
            return arrayList;
        }
    }
}
