package miui.cloud.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.miui.doodle.document.Layer;
import com.xiaomi.micloudsdk.cloudinfo.utils.CloudInfoUtils;
import java.util.ArrayList;
import java.util.Map;
import miui.accounts.ExtraAccountManager;
import miui.cloud.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes5.dex */
public class MiCloudStatusInfo {
    private static final String TAG = "MiCloudStatusInfo";
    private QuotaInfo mQuotaInfo;
    private String mUserId;
    private boolean mVipEnable;

    public MiCloudStatusInfo(String str) {
        this.mUserId = str;
    }

    public String getUserId() {
        return this.mUserId;
    }

    public void parseQuotaString(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "parseQuotaString() quota is empty.");
            this.mQuotaInfo = null;
        } else {
            try {
                this.mQuotaInfo = CloudInfoUtils.getQuotaInfo(this, new JSONObject(str));
            } catch (JSONException unused) {
                Log.e(TAG, "catch JSONException in parseQuotaString()");
                this.mQuotaInfo = null;
            }
        }
    }

    public String parseToQuotaInfo() throws JSONException {
        QuotaInfo quotaInfo = this.mQuotaInfo;
        if (quotaInfo == null) {
            Log.e(TAG, "parseToQuotaInfo() mQuotaInfo is null.");
            return "";
        }
        return CloudInfoUtils.toJSONObject(quotaInfo).toString();
    }

    public void parseMap(Map map) {
        Object obj = map.get("quota");
        if (obj instanceof Map) {
            this.mQuotaInfo = mapToQuotaInfo((Map) obj);
        }
        Object obj2 = map.get("VIPAvailable");
        if (obj2 instanceof Boolean) {
            this.mVipEnable = ((Boolean) obj2).booleanValue();
        }
    }

    public boolean isVIPAvailable() {
        return this.mVipEnable;
    }

    public QuotaInfo getQuotaInfo() {
        return this.mQuotaInfo;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0045  */
    /* JADX WARN: Code duplicated, block: B:17:0x0049  */
    /* JADX WARN: Code duplicated, block: B:20:0x0055  */
    /* JADX WARN: Code duplicated, block: B:23:0x0064  */
    /* JADX WARN: Code duplicated, block: B:24:0x006c  */
    /* JADX WARN: Code duplicated, block: B:27:0x0078  */
    /* JADX WARN: Code duplicated, block: B:28:0x0080  */
    /* JADX WARN: Code duplicated, block: B:31:0x008c  */
    /* JADX WARN: Code duplicated, block: B:34:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:37:0x00b5 A[LOOP:0: B:35:0x00af->B:37:0x00b5, LOOP_END] */
    private QuotaInfo mapToQuotaInfo(Map map) {
        long j;
        long jLongValue;
        Object obj;
        String str;
        Object obj2;
        long jLongValue2;
        Object obj3;
        long jLongValue3;
        QuotaInfo quotaInfo;
        Object obj4;
        Map map2;
        Object obj5 = map.get("total");
        long jLongValue4 = obj5 instanceof Long ? ((Long) obj5).longValue() : 0L;
        Object obj6 = map.get("used");
        if (obj6 instanceof Integer) {
            jLongValue = ((Integer) obj6).intValue();
        } else {
            if (obj6 instanceof Long) {
                jLongValue = ((Long) obj6).longValue();
            } else {
                j = 0;
            }
            obj = map.get("warn");
            if (obj instanceof String) {
                str = (String) obj;
            } else {
                str = "";
            }
            Object obj7 = map.get("yearlyPackageType");
            String str2 = obj7 instanceof String ? (String) obj7 : "";
            obj2 = map.get("yearlyPackageSize");
            if (obj2 instanceof Long) {
                jLongValue2 = ((Long) obj2).longValue();
            } else {
                jLongValue2 = 0;
            }
            obj3 = map.get("yearlyPackageCreateTime");
            if (obj3 instanceof Long) {
                jLongValue3 = ((Long) obj3).longValue();
            } else {
                jLongValue3 = 0;
            }
            Object obj8 = map.get("yearlyPackageExpireTime");
            quotaInfo = new QuotaInfo(jLongValue4, j, str, str2, jLongValue2, jLongValue3, obj8 instanceof Long ? ((Long) obj8).longValue() : 0L);
            obj4 = map.get(Layer.KEY_ITEMS);
            if (obj4 instanceof Map) {
                map2 = (Map) obj4;
                for (String str3 : map2.keySet()) {
                    quotaInfo.addItemInfo(mapToItemInfo(str3, (Map) map2.get(str3)));
                }
            }
            return quotaInfo;
        }
        j = jLongValue;
        obj = map.get("warn");
        if (obj instanceof String) {
            str = (String) obj;
        } else {
            str = "";
        }
        Object obj9 = map.get("yearlyPackageType");
        String str4 = obj9 instanceof String ? (String) obj9 : "";
        obj2 = map.get("yearlyPackageSize");
        if (obj2 instanceof Long) {
            jLongValue2 = ((Long) obj2).longValue();
        } else {
            jLongValue2 = 0;
        }
        obj3 = map.get("yearlyPackageCreateTime");
        if (obj3 instanceof Long) {
            jLongValue3 = ((Long) obj3).longValue();
        } else {
            jLongValue3 = 0;
        }
        Object obj10 = map.get("yearlyPackageExpireTime");
        quotaInfo = new QuotaInfo(jLongValue4, j, str, str4, jLongValue2, jLongValue3, obj10 instanceof Long ? ((Long) obj10).longValue() : 0L);
        obj4 = map.get(Layer.KEY_ITEMS);
        if (obj4 instanceof Map) {
            map2 = (Map) obj4;
            while (r1.hasNext()) {
                quotaInfo.addItemInfo(mapToItemInfo(str3, (Map) map2.get(str3)));
            }
        }
        return quotaInfo;
    }

    private ItemInfo mapToItemInfo(String str, Map map) {
        String str2;
        long jLongValue;
        Object obj = map.get("localized_name");
        if (!(obj instanceof String)) {
            str2 = "";
        } else {
            str2 = (String) obj;
        }
        String str3 = str2;
        Object obj2 = map.get("used");
        if (obj2 instanceof Integer) {
            jLongValue = ((Integer) obj2).intValue();
        } else {
            jLongValue = obj2 instanceof Long ? ((Long) obj2).longValue() : 0L;
        }
        return new ItemInfo(str, str3, jLongValue);
    }

    public static MiCloudStatusInfo fromUserData(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(context);
        if (xiaomiAccount == null) {
            return null;
        }
        String userData = accountManager.getUserData(xiaomiAccount, Constants.UserData.EXTRA_MICLOUD_STATUS_INFO_QUOTA);
        MiCloudStatusInfo miCloudStatusInfo = new MiCloudStatusInfo(xiaomiAccount.name);
        miCloudStatusInfo.parseQuotaString(userData);
        QuotaInfo quotaInfo = miCloudStatusInfo.getQuotaInfo();
        if (quotaInfo != null && quotaInfo.getWarn() != null) {
            return miCloudStatusInfo;
        }
        Log.w(TAG, "deserialize failed");
        accountManager.setUserData(xiaomiAccount, Constants.UserData.EXTRA_MICLOUD_STATUS_INFO_QUOTA, "");
        return miCloudStatusInfo;
    }

    public static MiCloudStatusInfo fromProviderOrNull(Context context, Account account) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.miui.micloud/status_info"), null, null, null, null);
        if (cursorQuery != null) {
            try {
                cursorQuery.moveToFirst();
                String string = cursorQuery.getString(cursorQuery.getColumnIndex("column_status_info"));
                String string2 = cursorQuery.getString(cursorQuery.getColumnIndex("column_status_info_user_id"));
                if (!TextUtils.isEmpty(string) && TextUtils.equals(string2, account.name)) {
                    MiCloudStatusInfo miCloudStatusInfo = new MiCloudStatusInfo(account.name);
                    miCloudStatusInfo.parseQuotaString(string);
                    return miCloudStatusInfo;
                }
            } finally {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        }
        return null;
    }

    public class QuotaInfo {
        public static final String WARN_FULL = "full";
        public static final String WARN_LOW_PERCENT = "low_percent";
        public static final String WARN_NONE = "none";
        private ArrayList<ItemInfo> mItemInfoList = new ArrayList<>();
        private long mTotal;
        private long mUsed;
        private String mWarn;
        private long mYearlyPackageCreateTime;
        private long mYearlyPackageExpireTime;
        private long mYearlyPackageSize;
        private String mYearlyPackageType;

        public QuotaInfo(long j, long j2, String str, String str2, long j3, long j4, long j5) {
            this.mTotal = j;
            this.mUsed = j2;
            this.mWarn = str;
            this.mYearlyPackageType = str2;
            this.mYearlyPackageSize = j3;
            this.mYearlyPackageCreateTime = j4;
            this.mYearlyPackageExpireTime = j5;
        }

        public void addItemInfo(ItemInfo itemInfo) {
            this.mItemInfoList.add(itemInfo);
        }

        public long getTotal() {
            return this.mTotal;
        }

        public long getUsed() {
            return this.mUsed;
        }

        public String getWarn() {
            return this.mWarn;
        }

        public String getYearlyPackageType() {
            return this.mYearlyPackageType;
        }

        public long getYearlyPackageSize() {
            return this.mYearlyPackageSize;
        }

        public long getYearlyPackageCreateTime() {
            return this.mYearlyPackageCreateTime;
        }

        public long getYearlyPackageExpireTime() {
            return this.mYearlyPackageExpireTime;
        }

        public ArrayList<ItemInfo> getItemInfoList() {
            return this.mItemInfoList;
        }

        public boolean isSpaceFull() {
            return WARN_FULL.equals(getWarn());
        }

        public boolean isSpaceLowPercent() {
            return WARN_LOW_PERCENT.equals(getWarn());
        }

        public String toString() {
            return "QuotaInfo{mTotal=" + this.mTotal + ", mUsed=" + this.mUsed + ", mWarn='" + this.mWarn + "', mYearlyPackageType='" + this.mYearlyPackageType + "', mYearlyPackageSize=" + this.mYearlyPackageSize + ", mYearlyPackageCreateTime=" + this.mYearlyPackageCreateTime + ", mYearlyPackageExpireTime=" + this.mYearlyPackageExpireTime + ", mItemInfoList=" + this.mItemInfoList + CoreConstants.CURLY_RIGHT;
        }
    }

    public class ItemInfo {
        private String mLocalizedName;
        private String mName;
        private long mUsed;

        public ItemInfo(String str, String str2, long j) {
            this.mName = str;
            this.mLocalizedName = str2;
            this.mUsed = j;
        }

        public String getName() {
            return this.mName;
        }

        public String getLocalizedName() {
            return this.mLocalizedName;
        }

        public long getUsed() {
            return this.mUsed;
        }

        public String toString() {
            return "ItemInfo{mName=" + this.mName + ", mLocalizedName=" + this.mLocalizedName + ", mUsed='" + this.mUsed + CoreConstants.CURLY_RIGHT;
        }
    }
}
