package com.xiaomi.passport.sim;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.phonenum.phone.PhoneInfo;

/* JADX INFO: loaded from: classes4.dex */
public class SIMId {
    private static String TAG = "SIMId";

    public static String get(Context context, int i) {
        TypedSimId simIdByPhoneTypeForSubId = getSimIdByPhoneTypeForSubId(context, i);
        if (simIdByPhoneTypeForSubId != null) {
            return simIdByPhoneTypeForSubId.toPlain();
        }
        return null;
    }

    private static TypedSimId getSimIdByPhoneTypeForSubId(Context context, int i) {
        int phoneTypeForSubId = PhoneInfo.get(context).getPhoneTypeForSubId(i);
        AccountLogger.log(TAG, "phone type: " + phoneTypeForSubId);
        if (phoneTypeForSubId == 2) {
            String strForceGet = PrivacyDataMaster.forceGet(context, PrivacyDataType.ICCID, String.valueOf(i));
            if (TextUtils.isEmpty(strForceGet)) {
                return null;
            }
            return new TypedSimId(1, strForceGet);
        }
        if (phoneTypeForSubId == 1) {
            String strForceGet2 = PrivacyDataMaster.forceGet(context, PrivacyDataType.IMSI, String.valueOf(i));
            if (TextUtils.isEmpty(strForceGet2)) {
                return null;
            }
            return new TypedSimId(2, strForceGet2);
        }
        AccountLogger.log(TAG, "unknown phone type, use iccid and imsi combination");
        String strForceGet3 = PrivacyDataMaster.forceGet(context, PrivacyDataType.ICCID, String.valueOf(i));
        String strForceGet4 = PrivacyDataMaster.forceGet(context, PrivacyDataType.IMSI, String.valueOf(i));
        if (TextUtils.isEmpty(strForceGet3) || TextUtils.isEmpty(strForceGet4)) {
            return null;
        }
        return new TypedSimId(0, String.format("%s&%s", strForceGet3, strForceGet4));
    }

    static final class TypedSimId {
        private static final String SP = ",";
        public static final int TYPE_ICCID = 1;
        public static final int TYPE_IMSI = 2;
        public static final int TYPE_UNKNOWN = 0;
        public final int type;
        public final String value;

        TypedSimId(int i, String str) {
            this.type = i;
            this.value = str;
        }

        public String toPlain() {
            return this.type + "," + this.value;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TypedSimId typedSimId = (TypedSimId) obj;
            if (this.type != typedSimId.type) {
                return false;
            }
            String str = this.value;
            return str == null ? typedSimId.value == null : str.equals(typedSimId.value);
        }

        public int hashCode() {
            int i = this.type * 31;
            String str = this.value;
            return i + (str != null ? str.hashCode() : 0);
        }

        public String toString() {
            return toPlain();
        }

        static TypedSimId parse(String str) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length == 2 && TextUtils.isDigitsOnly(strArrSplit[0])) {
                return new TypedSimId(Integer.parseInt(strArrSplit[0]), strArrSplit[1]);
            }
            return new TypedSimId(0, str);
        }
    }
}
