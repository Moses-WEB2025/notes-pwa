package com.xiaomi.accountsdk.account;

import android.text.TextUtils;
import com.xiaomi.accountsdk.account.data.DevInfoKeys;
import com.xiaomi.accountsdk.account.data.DeviceModelInfo;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.data.PhoneInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.ObjectUtils;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.sim.SIMInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class DeviceInfoHelper {
    private static final Integer INT_0 = 0;
    private static final int RESULT_CODE_SUCCESS = 0;
    private static final String TAG = "DeviceInfoHelper";

    public static HashMap<String, Object> getDeviceInfo(PassportInfo passportInfo, String str, List<String> list) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("null passportInfo");
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        String str2 = URLs.URL_DEV_SETTING;
        JSONArray jSONArray = new JSONArray();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().toString());
        }
        SimpleRequest.MapContent asMap = SecureRequestForAccount.getAsMap(str2, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut(DevInfoKeys.DEVICEID, str).easyPut("meta", jSONArray.toString()), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (asMap == null) {
            throw new IOException("failed to get device info");
        }
        Object fromBody = asMap.getFromBody("code");
        AccountLogger.log(TAG, "getDeviceInfo code : " + fromBody);
        if (INT_0.equals(fromBody)) {
            Object fromBody2 = asMap.getFromBody("data");
            if (fromBody2 instanceof Map) {
                Object obj = ((Map) fromBody2).get("settings");
                if (obj instanceof ArrayList) {
                    HashMap<String, Object> map = new HashMap<>();
                    for (HashMap map2 : (ArrayList) obj) {
                        map.put((String) map2.get("name"), map2.get("value"));
                    }
                    processDevicesSettingResult(map);
                    return map;
                }
            }
        }
        throw new InvalidResponseException("failed to get device info : " + asMap.toString());
    }

    public static ArrayList<HashMap<String, Object>> getAllDevicesInfo(PassportInfo passportInfo, ArrayList<String> arrayList) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("null passportInfo");
        }
        if (arrayList == null) {
            return null;
        }
        String str = URLs.URL_DEVICES_SETTING;
        JSONArray jSONArray = new JSONArray();
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().toString());
        }
        SimpleRequest.MapContent asMap = SecureRequestForAccount.getAsMap(str, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("meta", jSONArray.toString()), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (asMap == null) {
            throw new IOException("failed to get devices list");
        }
        Object fromBody = asMap.getFromBody("code");
        AccountLogger.log(TAG, "getDeviceList code : " + fromBody);
        if (INT_0.equals(fromBody)) {
            Object fromBody2 = asMap.getFromBody("data");
            if (fromBody2 instanceof Map) {
                Object obj = ((Map) fromBody2).get("all_device_settings");
                if (obj instanceof ArrayList) {
                    ArrayList<HashMap<String, Object>> arrayList2 = (ArrayList) obj;
                    Iterator<HashMap<String, Object>> it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        processDevicesSettingResult(it2.next());
                    }
                    return arrayList2;
                }
            }
        }
        return null;
    }

    private static void processDevicesSettingResult(HashMap<String, Object> map) {
        if (map == null) {
            return;
        }
        if (map.get(DevInfoKeys.PHONE_INFO) != null) {
            map.put(DevInfoKeys.PHONE_INFO, processDevicePhoneInfoContent(map.get(DevInfoKeys.PHONE_INFO).toString()));
        }
        if (map.get("model") != null && map.get(DevInfoKeys.MODEL_INFO) != null) {
            map.put(DevInfoKeys.MODEL_INFO, processDeviceModelInfoContent(map.get("model").toString(), map.get(DevInfoKeys.MODEL_INFO).toString()));
        }
        if (map.get(DevInfoKeys.MI_CLOUND_FIND) != null) {
            map.put(DevInfoKeys.MI_CLOUND_FIND, map.get(DevInfoKeys.MI_CLOUND_FIND));
        }
        if (map.get(DevInfoKeys.MI_CLOUND_FIND_DEVICE_URL) != null) {
            map.put(DevInfoKeys.MI_CLOUND_FIND_DEVICE_URL, map.get(DevInfoKeys.MI_CLOUND_FIND_DEVICE_URL));
        }
    }

    private static ArrayList<PhoneInfo> processDevicePhoneInfoContent(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList<PhoneInfo> arrayList = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                arrayList.add(new PhoneInfo(jSONObjectOptJSONObject.optString(SIMInfo.SIM_INFO_TYPE_SIM_ID), jSONObjectOptJSONObject.optString("phone")));
            }
            return arrayList;
        } catch (JSONException e) {
            AccountLogger.log(TAG, "setPhoneInfo", e);
            return arrayList;
        }
    }

    private static DeviceModelInfo processDeviceModelInfoContent(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        DeviceModelInfo deviceModelInfo = new DeviceModelInfo(str);
        try {
            JSONObject jSONObject = new JSONObject(str2);
            deviceModelInfo.setImageUrl(jSONObject.optString("fullImageUrl"));
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(XMPassportUtil.getISOLocaleString(Locale.getDefault()));
            if (jSONObjectOptJSONObject == null) {
                jSONObjectOptJSONObject = jSONObject.optJSONObject("default");
            }
            if (jSONObjectOptJSONObject != null) {
                deviceModelInfo.setModelName(jSONObjectOptJSONObject.optString("modelName"));
                deviceModelInfo.setDefaultDeviceName(jSONObjectOptJSONObject.optString("deviceName"));
            }
            return deviceModelInfo;
        } catch (JSONException e) {
            AccountLogger.log(TAG, e);
            return deviceModelInfo;
        }
    }

    @Deprecated
    public static boolean uploadDeviceInfo(String str, String str2, String str3, String str4, String str5, Map<String, Object> map) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return uploadDeviceInfo(new PassportInfo(str, str2, null, str3, str4), str5, map);
    }

    public static boolean uploadDeviceInfo(PassportInfo passportInfo, String str, Map<String, Object> map) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null || map == null) {
            throw new IllegalArgumentException("invalid parameter");
        }
        String str2 = URLs.URL_DEV_SETTING;
        JSONArray jSONArrayConvertDevSettingValues = convertDevSettingValues(map);
        HashMap map2 = new HashMap();
        map2.put("userId", passportInfo.getUserId());
        map2.put(DevInfoKeys.DEVICEID, str);
        map2.put("content", jSONArrayConvertDevSettingValues.toString());
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(str2, map2, getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new IOException("failed to upload device settings info");
        }
        Object fromBody = mapContentPostAsMap.getFromBody("code");
        mapContentPostAsMap.getFromBody("description");
        if (INT_0.equals(fromBody)) {
            return true;
        }
        AccountLogger.log(TAG, "failed upload dev name, code: " + fromBody);
        return false;
    }

    private static JSONArray convertDevSettingValues(Map<String, Object> map) {
        JSONArray jSONArray = new JSONArray();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object objConvertObjectToJson = ObjectUtils.convertObjectToJson(entry.getValue());
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("name", key);
                jSONObject.put("value", objConvertObjectToJson);
            } catch (JSONException e) {
                AccountLogger.log(TAG, "convertDevSettingValues", e);
            }
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public static HashMap<String, DeviceModelInfo> getDeviceModelInfos(ArrayList<String> arrayList) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (arrayList == null) {
            throw new IllegalArgumentException("invalid parameter");
        }
        String str = URLs.URL_GET_DEVICE_MODEL_INFOS;
        HashMap<String, DeviceModelInfo> map = new HashMap<>();
        if (arrayList.size() != 0) {
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(str, new EasyMap().easyPut("models", new JSONArray((Collection) arrayList).toString()), null, true);
            if (asString == null) {
                throw new InvalidResponseException("failed to getModelInfos");
            }
            try {
                JSONObject jSONObject = new JSONObject(asString.getBody());
                if (jSONObject.getInt("code") == 0) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    String iSOLocaleString = XMPassportUtil.getISOLocaleString(Locale.getDefault());
                    for (int i = 0; i < arrayList.size(); i++) {
                        String str2 = arrayList.get(i);
                        JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject(str2);
                        DeviceModelInfo deviceModelInfo = new DeviceModelInfo(str2);
                        deviceModelInfo.setModelName(str2);
                        if (jSONObjectOptJSONObject != null) {
                            deviceModelInfo.setImageUrl(jSONObjectOptJSONObject.optString("fullImageUrl"));
                            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(iSOLocaleString);
                            if (jSONObjectOptJSONObject2 == null) {
                                jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("default");
                            }
                            if (jSONObjectOptJSONObject2 != null) {
                                deviceModelInfo.setDefaultDeviceName(jSONObjectOptJSONObject2.optString("deviceName"));
                                deviceModelInfo.setModelName(jSONObjectOptJSONObject2.optString("modelName"));
                            }
                        }
                        map.put(str2, deviceModelInfo);
                    }
                }
            } catch (JSONException e) {
                AccountLogger.log(TAG, "fail to parse JSONObject", e);
                throw new InvalidResponseException(asString.toString());
            }
        }
        return map;
    }

    private static EasyMap<String, String> getPassportCookie(PassportInfo passportInfo) {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        EasyMap<String, String> easyMapEasyPut = new EasyMap().easyPut("serviceToken", passportInfo.getServiceToken());
        if (TextUtils.isEmpty(passportInfo.getEncryptedUserId())) {
            easyMapEasyPut.easyPut("userId", passportInfo.getUserId());
            return easyMapEasyPut;
        }
        easyMapEasyPut.easyPut("cUserId", passportInfo.getEncryptedUserId());
        return easyMapEasyPut;
    }
}
