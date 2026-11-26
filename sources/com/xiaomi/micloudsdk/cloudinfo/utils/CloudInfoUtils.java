package com.xiaomi.micloudsdk.cloudinfo.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.Field;
import com.market.sdk.utils.Language;
import com.xiaomi.micloudsdk.request.utils.HttpUtils;
import com.xiaomi.micloudsdk.request.utils.Request;
import com.xiaomi.micloudsdk.utils.JsonUtils;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.micloudsdk.utils.SystemUtils;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.UnretriableException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import miui.cloud.os.SystemProperties;
import miui.cloud.sync.FamilyInfo;
import miui.cloud.sync.MiCloudStatusInfo;
import miui.cloud.sync.MiCloudStatusInfo.ItemInfo;
import miui.cloud.sync.MiCloudStatusInfo.QuotaInfo;
import miui.cloud.sync.VipInfo;
import net.lingala.zip4j.util.InternalZipConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class CloudInfoUtils {
    private static final String TAG = "CloudInfoUtils";
    private static String sUserAgent;

    public static String getUserAgent() {
        if (sUserAgent == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Build.MODEL + InternalZipConstants.ZIP_FILE_SEPARATOR);
            String str = SystemProperties.get("ro.product.mod_device");
            if (!TextUtils.isEmpty(str)) {
                sb.append(str);
            } else {
                sb.append(Build.MODEL);
            }
            sb.append("; MIUI/");
            sb.append(Build.VERSION.INCREMENTAL);
            sb.append(" E/");
            sb.append(SystemUtils.getMiSystemVersionName());
            sb.append(" B/");
            if (miui.os.Build.IS_ALPHA_BUILD) {
                sb.append(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
            } else if (miui.os.Build.IS_DEVELOPMENT_VERSION) {
                sb.append(Field.DOUBLE_SIGNATURE_PRIMITIVE);
            } else if (miui.os.Build.IS_STABLE_VERSION) {
                sb.append("S");
            } else {
                sb.append("null");
            }
            sb.append(" L/");
            Locale locale = Locale.getDefault();
            String language = locale.getLanguage();
            if (language != null) {
                sb.append(convertObsoleteLanguageCodeToNew(language));
                String country = locale.getCountry();
                if (country != null) {
                    sb.append("-");
                    sb.append(country.toUpperCase());
                }
            } else {
                sb.append("EN");
            }
            sb.append(" LO/");
            String region = miui.os.Build.getRegion();
            if (!TextUtils.isEmpty(region)) {
                sb.append(region.toUpperCase());
            } else {
                sb.append("null");
            }
            sUserAgent = sb.toString();
        }
        return sUserAgent;
    }

    public static MiCloudStatusInfo getMiCloudStatusInfo(String str, JSONObject jSONObject, String str2) throws AuthenticationException, RetriableException, UnretriableException {
        JSONObject jSONObject2;
        String userAgent = getUserAgent();
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("version", userAgent);
        arrayMap.put("_locale", str2);
        if (jSONObject != null) {
            String string = jSONObject.toString();
            Log.d(TAG, "query status with status.");
            arrayMap.put("clientStatus", string);
        }
        JSONObject jSONObjectRequestServer = Request.requestServer(MiCloudConstants.URL.URL_MICLOUD_STATUS_QUERY, HttpUtils.HttpMethod.POST, arrayMap, null);
        try {
            jSONObject2 = jSONObjectRequestServer.getInt("code") == 0 ? jSONObjectRequestServer.getJSONObject("data") : null;
        } catch (JSONException e) {
            Log.e(TAG, "JSONException in getMiCloudStatusInfo", e);
        }
        if (jSONObject2 == null) {
            return null;
        }
        Map<String, Object> mapJsonToMap = JsonUtils.jsonToMap(jSONObject2);
        MiCloudStatusInfo miCloudStatusInfo = new MiCloudStatusInfo(str);
        miCloudStatusInfo.parseMap(mapJsonToMap);
        return miCloudStatusInfo;
    }

    public static VipInfo getMiCloudMemberStatusInfo(String str, String str2) throws AuthenticationException, RetriableException, UnretriableException {
        JSONObject jSONObject;
        String userAgent = getUserAgent();
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("version", userAgent);
        arrayMap.put("_locale", str2);
        JSONObject jSONObjectRequestServer = Request.requestServer(MiCloudConstants.URL.URL_MICLOUD_MEMBER_STATUS_QUERY, HttpUtils.HttpMethod.GET, arrayMap, null);
        try {
            jSONObject = jSONObjectRequestServer.getInt("code") == 0 ? jSONObjectRequestServer.getJSONObject("data") : null;
        } catch (JSONException e) {
            Log.e(TAG, "JSONException in getMiCloudMemberStatusInfo", e);
        }
        if (jSONObject == null) {
            return null;
        }
        return getVipInfo(jSONObject);
    }

    public static FamilyInfo getMiCloudFamilyInfo(String str) throws AuthenticationException, RetriableException, UnretriableException {
        JSONObject jSONObject;
        String userAgent = getUserAgent();
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("version", userAgent);
        arrayMap.put("_locale", str);
        JSONObject jSONObjectRequestServer = Request.requestServer(MiCloudConstants.URL.URL_MICLOUD_FAMILY_INFO, HttpUtils.HttpMethod.GET, arrayMap, null);
        try {
            jSONObject = jSONObjectRequestServer.getInt("code") == 0 ? jSONObjectRequestServer.getJSONObject("data") : null;
        } catch (JSONException e) {
            Log.e(TAG, "JSONException in getMiCloudFamilyInfo", e);
        }
        if (jSONObject == null) {
            return null;
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("usedDetails");
        return new FamilyInfo(jSONObject.optString("role", ""), jSONObject.optString("status", ""), jSONArrayOptJSONArray != null ? jSONArrayOptJSONArray.length() : -1);
    }

    public static VipInfo getVipInfo(JSONObject jSONObject) {
        return new VipInfo(jSONObject.optString("vipName"), jSONObject.optString("level"), jSONObject.optLong("expireTime", -1L), jSONObject.optBoolean("autoRenew", false));
    }

    public static MiCloudStatusInfo.QuotaInfo getQuotaInfo(MiCloudStatusInfo miCloudStatusInfo, JSONObject jSONObject) {
        long jOptLong = jSONObject.optLong("Total");
        long jOptLong2 = jSONObject.optLong("Used");
        String strOptString = jSONObject.optString("Warn");
        String strOptString2 = jSONObject.optString("YearlyPackageType");
        long jOptLong3 = jSONObject.optLong("YearlyPackageSize");
        long jOptLong4 = jSONObject.optLong("YearlyPackageCreateTime");
        long jOptLong5 = jSONObject.optLong("YearlyPackageExpireTime");
        Objects.requireNonNull(miCloudStatusInfo);
        MiCloudStatusInfo.QuotaInfo quotaInfo = miCloudStatusInfo.new QuotaInfo(jOptLong, jOptLong2, strOptString, strOptString2, jOptLong3, jOptLong4, jOptLong5);
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("ItemInfoList");
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                MiCloudStatusInfo.ItemInfo itemInfo = getItemInfo(miCloudStatusInfo, jSONArrayOptJSONArray.optJSONObject(i));
                if (itemInfo != null) {
                    quotaInfo.addItemInfo(itemInfo);
                }
            }
        }
        return quotaInfo;
    }

    public static MiCloudStatusInfo.ItemInfo getItemInfo(MiCloudStatusInfo miCloudStatusInfo, JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        String strOptString = jSONObject.optString("Name");
        String strOptString2 = jSONObject.optString("LocalizedName");
        long jOptLong = jSONObject.optLong("Used");
        Objects.requireNonNull(miCloudStatusInfo);
        return miCloudStatusInfo.new ItemInfo(strOptString, strOptString2, jOptLong);
    }

    public static JSONObject toJSONObject(MiCloudStatusInfo.QuotaInfo quotaInfo) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("Total", quotaInfo.getTotal());
        jSONObject.put("Used", quotaInfo.getUsed());
        jSONObject.put("Warn", quotaInfo.getWarn());
        jSONObject.put("YearlyPackageType", quotaInfo.getYearlyPackageType());
        jSONObject.put("YearlyPackageSize", quotaInfo.getYearlyPackageSize());
        jSONObject.put("YearlyPackageCreateTime", quotaInfo.getYearlyPackageCreateTime());
        jSONObject.put("YearlyPackageExpireTime", quotaInfo.getYearlyPackageExpireTime());
        JSONArray jSONArray = new JSONArray();
        Iterator<MiCloudStatusInfo.ItemInfo> it = quotaInfo.getItemInfoList().iterator();
        while (it.hasNext()) {
            jSONArray.put(toJSONObject(it.next()));
        }
        jSONObject.put("ItemInfoList", jSONArray);
        return jSONObject;
    }

    public static JSONObject toJSONObject(MiCloudStatusInfo.ItemInfo itemInfo) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("Name", itemInfo.getName());
        jSONObject.put("Used", itemInfo.getUsed());
        jSONObject.put("LocalizedName", itemInfo.getLocalizedName());
        return jSONObject;
    }

    private static String convertObsoleteLanguageCodeToNew(String str) {
        if (str == null) {
            return null;
        }
        if ("iw".equals(str)) {
            return "he";
        }
        if (Language.LA_IN.equals(str)) {
            return "id";
        }
        return "ji".equals(str) ? "yi" : str;
    }
}
