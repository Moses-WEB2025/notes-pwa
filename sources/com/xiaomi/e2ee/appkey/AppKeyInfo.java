package com.xiaomi.e2ee.appkey;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class AppKeyInfo {
    private static final String APPKEY_INFO_VERSION = "APPKEY_INFO_VERSION";
    public static final int APPKEY_INFO_VERSION_V1 = 1;
    private static final String APPKEY_IV = "APPKEY_IV";
    private static final String APPKEY_VERSION = "APPKEY_VERSION";
    public static final String DECRYPT_APPKEY = "DECRYPT_APPKEY";
    private static final String ENCRYPT_APPKEY = "ENCRYPT_APPKEY";
    public final String appKeyIV;
    public final int appKeyInfoVersion;
    public final long appKeyVersion;
    public final String decryptAppKey;
    public final String encryptAppKey;

    public AppKeyInfo(int i, long j, String str, String str2, String str3) {
        this.appKeyInfoVersion = i;
        this.appKeyVersion = j;
        this.encryptAppKey = str;
        this.decryptAppKey = str2;
        this.appKeyIV = str3;
    }

    public String beanToJsonString() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(APPKEY_VERSION, this.appKeyVersion);
        jSONObject.put(ENCRYPT_APPKEY, this.encryptAppKey);
        jSONObject.put(DECRYPT_APPKEY, this.decryptAppKey);
        jSONObject.put(APPKEY_IV, this.appKeyIV);
        jSONObject.put(APPKEY_INFO_VERSION, this.appKeyInfoVersion);
        return jSONObject.toString();
    }

    public static AppKeyInfo jsonStrToBean(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        long j = jSONObject.getLong(APPKEY_VERSION);
        String string = jSONObject.getString(ENCRYPT_APPKEY);
        String string2 = jSONObject.getString(APPKEY_IV);
        return new AppKeyInfo(jSONObject.getInt(APPKEY_INFO_VERSION), j, string, jSONObject.getString(DECRYPT_APPKEY), string2);
    }
}
