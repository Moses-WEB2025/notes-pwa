package com.xiaomi.passport.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.market.sdk.reflect.Field;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneNumUtil {
    private static final String TAG = "PhoneNumUtil";

    public static class CountryPhoneNumData {
        public final String countryCode;
        public final String countryCodeWithPrefix;
        public final String countryISO;
        public final Pair<String, Boolean> countryIndex;
        public final String countryName;

        CountryPhoneNumData(String str, String str2, String str3, Pair<String, Boolean> pair) {
            if (TextUtils.isEmpty(str) || str2 == null || TextUtils.isEmpty(str3)) {
                throw new IllegalStateException("country code and iso should't be empty");
            }
            this.countryISO = str;
            this.countryName = str2;
            this.countryCode = str3;
            if (str3.startsWith(Marker.ANY_NON_NULL_MARKER)) {
                this.countryCodeWithPrefix = str3;
            } else {
                this.countryCodeWithPrefix = Marker.ANY_NON_NULL_MARKER + str3;
            }
            this.countryIndex = pair;
        }
    }

    public static class CloudCountryCodeInfo {
        public final List<CountryPhoneNumData> countryCodeList;
        public final boolean fromCloud;

        CloudCountryCodeInfo(boolean z, List<CountryPhoneNumData> list) {
            if (list == null) {
                throw new IllegalStateException("country code list shouldn't be null");
            }
            this.fromCloud = z;
            this.countryCodeList = list;
        }
    }

    public static synchronized CloudCountryCodeInfo getCloudCountryCodeInfo(Context context) {
        return new CloudCountryCodeInfo(true, CountryCodePhoneNumber.getCountryCodeOnLocale(getLocale()));
    }

    private static List<CountryPhoneNumData> processCloudCountryCodeRawData(String str) {
        if (str == null) {
            return null;
        }
        try {
            List<CountryPhoneNumData> listProcessCountryCodeFromCloudJsonLocked = processCountryCodeFromCloudJsonLocked(str);
            if (listProcessCountryCodeFromCloudJsonLocked.isEmpty()) {
                return null;
            }
            return listProcessCountryCodeFromCloudJsonLocked;
        } catch (JSONException e) {
            AccountLogger.log(TAG, "load cloud data JSONException", e);
            return null;
        }
    }

    public static synchronized void saveCloudCountryCodeInfoToStorage(String str, String str2) {
        CountryCodePhoneNumber.saveCountryCodeOnLocale(processCloudCountryCodeRawData(str), str2);
    }

    public static String getLocale() {
        return XMPassportUtil.getISOLocaleString(Locale.getDefault());
    }

    private static List<CountryPhoneNumData> processCountryCodeFromCloudJsonLocked(String str) throws JSONException {
        Pair pair;
        JSONObject jSONObject = (JSONObject) new JSONObject(str).get("data");
        ArrayList arrayList = new ArrayList();
        Iterator<String> itKeys = jSONObject.keys();
        boolean z = jSONObject.length() != 1;
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            JSONArray jSONArray = jSONObject.getJSONArray(next);
            int i = 0;
            while (i < jSONArray.length()) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                String strOptString = jSONObject2.optString("B", "");
                String strOptString2 = jSONObject2.optString(Field.CHAR_SIGNATURE_PRIMITIVE, "");
                String strOptString3 = jSONObject2.optString("N", "");
                if (TextUtils.isEmpty(strOptString) || TextUtils.isEmpty(strOptString3)) {
                    AccountLogger.log(TAG, "discard unqualified data " + jSONObject2);
                } else {
                    if (strOptString3.startsWith(Marker.ANY_NON_NULL_MARKER)) {
                        strOptString3 = strOptString3.substring(1);
                    }
                    if (z) {
                        pair = new Pair(next, Boolean.valueOf(i == 0));
                    } else {
                        pair = null;
                    }
                    arrayList.add(new CountryPhoneNumData(strOptString, strOptString2, strOptString3, pair));
                }
                i++;
            }
        }
        return arrayList;
    }
}
