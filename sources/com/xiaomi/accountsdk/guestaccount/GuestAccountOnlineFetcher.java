package com.xiaomi.accountsdk.guestaccount;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccount;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountResult;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountType;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.Coder;
import com.xiaomi.accountsdk.utils.FidNonceBase;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class GuestAccountOnlineFetcher {
    private static final String ARG_APP_PACKAGE = "appPackage";
    private static final String ARG_CALLBACK = "callback";
    private static final String ARG_CLIENT_INFO = "clientInfo";
    private static final String ARG_DEVICE_ID = "visitorDeviceId";
    private static final String ARG_FID_NONCE = "fidNonce";
    private static final String ARG_FID_NONCE_SIGN = "fidNonceSign";
    private static final String ARG_PASS_TOKEN = "visitorPassToken";
    private static final String ARG_P_SID = "psid";
    private static final String ARG_SDK_VERSION = "visitorSdkVersion";
    private static final String ARG_SID = "sid";
    private static final String ARG_SIGN = "_sign";
    private static final String ARG_VISITOR_ID = "visitorId";
    private static final String ARG_VISITOR_TYPE = "visitorType";
    private static final String J_CALLBACK = "callback";
    private static final String J_CODE = "code";
    private static final String J_C_VISITOR_ID = "cVisitorId";
    private static final String J_DATA = "data";
    private static final String J_PASS_TOKEN = "visitorPassToken";
    private static final String J_RESULT = "result";
    private static final String J_SID_PH = "_ph";
    private static final String J_SID_SERVICE_TOKEN = "_serviceToken";
    private static final String J_SID_SLH = "_slh";
    private static final String J_SSECURITY = "ssecurity";
    private static final String J_USER_ID = "visitorId";
    private static final String J_VISITOR_ID = "visitorId";
    private static final String J_VISITOR_TYPE = "visitorType";
    static final String SIGNATURE_KEY = "BEYBuDbVZqYHzAVT+TAAAA==";
    private static final String TAG = "GuestAccountOnlineFetch";
    private final Context context;
    private GuestAccountHardwareInfoFetcher hardwareInfoFetcher;
    private GuestAccountHttpRequester request = new GuestAccountHttpRequesterImplDefault();

    static class URLs {
        URLs() {
        }

        static class Product {
            static final String URL_LOGIN = "https://v.id.mi.com/visitor/login";
            static final String URL_REGISTER = "https://v.id.mi.com/visitor/register";

            Product() {
            }
        }

        static class Staging {
            static final String URL_LOGIN = "http://visitor.preview.n.xiaomi.net/visitor/login";
            static final String URL_REGISTER = "http://visitor.preview.n.xiaomi.net/visitor/register";

            Staging() {
            }
        }
    }

    static boolean isStaging(Context context) {
        return new File(context.getFilesDir(), "staging.flag").exists();
    }

    static String getRegisterUrl(Context context) {
        return isStaging(context) ? "http://visitor.preview.n.xiaomi.net/visitor/register" : "https://v.id.mi.com/visitor/register";
    }

    static String getLoginUrl(Context context) {
        return isStaging(context) ? "http://visitor.preview.n.xiaomi.net/visitor/login" : "https://v.id.mi.com/visitor/login";
    }

    GuestAccountOnlineFetcher(Context context) {
        this.hardwareInfoFetcher = new GuestAccountHardwareInfoFetchImplDefault(context);
        this.context = context;
    }

    void setRequest(GuestAccountHttpRequester guestAccountHttpRequester) {
        if (guestAccountHttpRequester == null) {
            throw new IllegalArgumentException("request == null");
        }
        this.request = guestAccountHttpRequester;
    }

    void setHardwareInfoFetcher(GuestAccountHardwareInfoFetcher guestAccountHardwareInfoFetcher) {
        if (guestAccountHardwareInfoFetcher == null) {
            throw new IllegalArgumentException("hardwareInfoFetcher == null");
        }
        this.hardwareInfoFetcher = guestAccountHardwareInfoFetcher;
    }

    private String makeClientInfoJsonString() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.putOpt("macAddress", Coder.hash4SHA1(this.hardwareInfoFetcher.getMacAddress(), 10));
            jSONObject.putOpt("bluetooth", Coder.hash4SHA1(this.hardwareInfoFetcher.getBluetoothId(), 10));
            jSONObject.putOpt("aid", this.hardwareInfoFetcher.getAndroidId());
            return jSONObject.toString();
        } catch (JSONException unused) {
            throw new IllegalStateException("should never happen");
        }
    }

    GuestAccountResult registerGuestAccount(GuestAccountType guestAccountType, String str, String str2, String str3) {
        if (guestAccountType == null) {
            guestAccountType = GuestAccountType.DEFAULT;
        }
        FidNonceBase fidNonceBase = this.hardwareInfoFetcher.getFidNonceBase();
        if (fidNonceBase == null || fidNonceBase.plain == null || fidNonceBase.sign == null) {
            guestAccountType = GuestAccountType.DEFAULT;
        }
        String packageName = this.context.getPackageName();
        String strMakeClientInfoJsonString = makeClientInfoJsonString();
        String registerUrl = getRegisterUrl(this.context);
        String deviceId = this.hardwareInfoFetcher.getDeviceId();
        HashMap map = new HashMap();
        map.put("visitorType", String.valueOf(guestAccountType.serverValue));
        map.put(ARG_SID, str);
        map.put(ARG_P_SID, str3);
        map.put(ARG_APP_PACKAGE, packageName);
        if (!TextUtils.isEmpty(strMakeClientInfoJsonString)) {
            map.put(ARG_CLIENT_INFO, strMakeClientInfoJsonString);
        }
        if (!TextUtils.isEmpty(str2)) {
            map.put(CallMethod.ARG_CALLBACK, str2);
        }
        map.put(Constants.NONCE, String.valueOf(Coder.generateNonce(System.currentTimeMillis())));
        HashMap map2 = new HashMap();
        map2.put(ARG_DEVICE_ID, deviceId);
        map2.put(ARG_SDK_VERSION, GuestAccountConstants.GUEST_ACCOUNT_SDK_VERSION);
        if (fidNonceBase != null && fidNonceBase.plain != null && fidNonceBase.sign != null) {
            map2.put("fidNonce", fidNonceBase.plain);
            map2.put("fidNonceSign", fidNonceBase.sign);
        }
        TreeMap treeMap = new TreeMap();
        treeMap.putAll(map);
        treeMap.putAll(map2);
        map2.put(ARG_SIGN, Coder.generateSignature(PassportSimpleRequest.HTTP_METHOD_POST, registerUrl, treeMap, SIGNATURE_KEY));
        try {
            TypedResponse typedResponsePostAndExtractData = postAndExtractData(registerUrl, map, map2);
            if (typedResponsePostAndExtractData.code != null) {
                return new GuestAccountResult().setErrorCode(typedResponsePostAndExtractData.code.intValue());
            }
            JSONObject jSONObject = typedResponsePostAndExtractData.body;
            GuestAccountType fromServerValue = GuestAccountType.getFromServerValue(jSONObject.getInt("visitorType"));
            if (fromServerValue == null) {
                return GuestAccountConstants.RESULT_OF_SERVER_ERROR;
            }
            String string = jSONObject.getString("visitorPassToken");
            String string2 = jSONObject.getString("visitorId");
            String strOptString = jSONObject.optString(J_C_VISITOR_ID);
            String strOptString2 = jSONObject.optString(getServiceTokenResponseKey(str));
            String strOptString3 = typedResponsePostAndExtractData.extensionPragma == null ? null : typedResponsePostAndExtractData.extensionPragma.optString(J_SSECURITY);
            return new GuestAccountResult().setGuestAccount(new GuestAccount.Builder().userId(string2).cUserId(strOptString).sid(str).serviceToken(strOptString2).security(strOptString3).passToken(string).type(fromServerValue).callback(jSONObject.optString(CallMethod.ARG_CALLBACK)).slh(jSONObject.optString(getSlhResponseKey(str))).ph(jSONObject.optString(getPhResponseKey(str))).build());
        } catch (JSONException e) {
            AccountLogger.log(TAG, e);
            return GuestAccountConstants.RESULT_OF_SERVER_ERROR;
        }
    }

    GuestAccountResult loginByPassToken(GuestAccount guestAccount, String str) {
        if (guestAccount.sid == null) {
            throw new IllegalArgumentException("guestAccount.sid == null");
        }
        if (guestAccount.userId == null) {
            throw new IllegalArgumentException("guestAccount.userId == null");
        }
        if (guestAccount.passToken == null) {
            throw new IllegalArgumentException("guestAccount.passToken == null");
        }
        String loginUrl = getLoginUrl(this.context);
        HashMap map = new HashMap();
        map.put(ARG_SID, guestAccount.sid);
        map.put("visitorId", guestAccount.userId);
        if (!TextUtils.isEmpty(str)) {
            map.put(CallMethod.ARG_CALLBACK, str);
        }
        map.put(Constants.NONCE, String.valueOf(Coder.generateNonce(System.currentTimeMillis())));
        String deviceId = this.hardwareInfoFetcher.getDeviceId();
        HashMap map2 = new HashMap();
        map2.put(ARG_DEVICE_ID, deviceId);
        map2.put(ARG_SDK_VERSION, GuestAccountConstants.GUEST_ACCOUNT_SDK_VERSION);
        map2.put("visitorPassToken", guestAccount.passToken);
        TreeMap treeMap = new TreeMap();
        treeMap.putAll(map);
        treeMap.putAll(map2);
        map2.put(ARG_SIGN, Coder.generateSignature(PassportSimpleRequest.HTTP_METHOD_POST, loginUrl, treeMap, SIGNATURE_KEY));
        try {
            TypedResponse typedResponsePostAndExtractData = postAndExtractData(loginUrl, map, map2);
            if (typedResponsePostAndExtractData.code != null) {
                return new GuestAccountResult().setErrorCode(typedResponsePostAndExtractData.code.intValue());
            }
            JSONObject jSONObject = typedResponsePostAndExtractData.body;
            String string = jSONObject.getString("visitorId");
            String strOptString = jSONObject.optString(J_C_VISITOR_ID);
            String string2 = jSONObject.getString("visitorPassToken");
            String strOptString2 = jSONObject.optString(getServiceTokenResponseKey(guestAccount.sid));
            String strOptString3 = typedResponsePostAndExtractData.extensionPragma == null ? null : typedResponsePostAndExtractData.extensionPragma.optString(J_SSECURITY);
            String strOptString4 = jSONObject.optString(getSlhResponseKey(guestAccount.sid));
            return new GuestAccountResult().setGuestAccount(new GuestAccount.Builder().userId(string).cUserId(strOptString).passToken(string2).sid(guestAccount.sid).serviceToken(strOptString2).security(strOptString3).slh(strOptString4).ph(jSONObject.optString(getPhResponseKey(guestAccount.sid))).callback(jSONObject.optString(CallMethod.ARG_CALLBACK)).build());
        } catch (JSONException e) {
            AccountLogger.log(TAG, e);
            return GuestAccountConstants.RESULT_OF_SERVER_ERROR;
        }
    }

    GuestAccountResult visitSts(GuestAccount guestAccount) {
        if (guestAccount.callback == null) {
            throw new IllegalArgumentException("guestAccount.callback == null");
        }
        try {
            GuestAccountHttpRequester.Response response = this.request.get(guestAccount.callback);
            if (response.code < 100) {
                throw new IllegalStateException("rawresponse.responseCode = " + response.code + " < 100");
            }
            if (response.code != 200) {
                return new GuestAccountResult().setErrorCode(response.code);
            }
            if (response.cookies == null) {
                return new GuestAccountResult().setErrorCode(5);
            }
            String str = response.cookies.get("serviceToken");
            if (TextUtils.isEmpty(str)) {
                return new GuestAccountResult().setErrorCode(5);
            }
            String str2 = response.cookies.get(J_C_VISITOR_ID);
            String str3 = response.cookies.get(getSlhResponseKey(guestAccount.sid));
            String str4 = response.cookies.get(getPhResponseKey(guestAccount.sid));
            GuestAccount.Builder builderUserId = new GuestAccount.Builder().userId(guestAccount.userId);
            if (TextUtils.isEmpty(str2)) {
                str2 = guestAccount.cUserId;
            }
            return new GuestAccountResult().setGuestAccount(builderUserId.cUserId(str2).sid(guestAccount.sid).serviceToken(str).security(guestAccount.security).passToken(guestAccount.passToken).slh(str3).ph(str4).build());
        } catch (IOException e) {
            AccountLogger.log(TAG, e);
            return new GuestAccountResult().setErrorCode(6);
        }
    }

    String getServiceTokenResponseKey(String str) {
        return str + J_SID_SERVICE_TOKEN;
    }

    String getSlhResponseKey(String str) {
        return str + J_SID_SLH;
    }

    String getPhResponseKey(String str) {
        return str + J_SID_PH;
    }

    private TypedResponse getAndExtractData(String str) {
        JSONObject jSONObject = null;
        try {
            return parse(this.request.get(str));
        } catch (IOException e) {
            AccountLogger.log(TAG, e);
            return new TypedResponse(6, jSONObject, 0 == true ? 1 : 0);
        } catch (JSONException e2) {
            AccountLogger.log(TAG, e2);
            return new TypedResponse(5, 0 == true ? 1 : 0, 0 == true ? 1 : 0);
        }
    }

    private TypedResponse postAndExtractData(String str, Map<String, String> map, Map<String, String> map2) {
        JSONObject jSONObject = null;
        try {
            return parse(this.request.post(str, map, map2));
        } catch (IOException e) {
            AccountLogger.log(TAG, e);
            return new TypedResponse(6, jSONObject, 0 == true ? 1 : 0);
        } catch (JSONException e2) {
            AccountLogger.log(TAG, e2);
            return new TypedResponse(5, 0 == true ? 1 : 0, 0 == true ? 1 : 0);
        }
    }

    private TypedResponse parse(GuestAccountHttpRequester.Response response) throws JSONException {
        JSONObject jSONObject;
        JSONObject jSONObject2 = null;
        if (response.body == null) {
            if (response.code < 100) {
                throw new IllegalStateException("rawresponse.responseCode = " + response.code + " < 100");
            }
            return new TypedResponse(Integer.valueOf(response.code), jSONObject2, 0 == true ? 1 : 0);
        }
        JSONObject jSONObject3 = new JSONObject(response.body);
        int i = jSONObject3.getInt("code");
        if (i != 0) {
            return new TypedResponse(Integer.valueOf(i), 0 == true ? 1 : 0, 0 == true ? 1 : 0);
        }
        if (!TextUtils.equals(jSONObject3.getString("result"), "ok")) {
            return new TypedResponse(5, 0 == true ? 1 : 0, 0 == true ? 1 : 0);
        }
        JSONObject jSONObject4 = jSONObject3.getJSONObject("data");
        if (jSONObject4 == null) {
            return new TypedResponse(5, 0 == true ? 1 : 0, 0 == true ? 1 : 0);
        }
        try {
            jSONObject = new JSONObject(response.headerExtensionPragma);
        } catch (JSONException e) {
            AccountLogger.log(TAG, "", e);
            jSONObject = null;
        }
        return new TypedResponse(0 == true ? 1 : 0, jSONObject4, jSONObject);
    }

    static class TypedResponse {
        final JSONObject body;
        final Integer code;
        final JSONObject extensionPragma;

        private TypedResponse(Integer num, JSONObject jSONObject, JSONObject jSONObject2) {
            this.code = num;
            this.body = jSONObject;
            this.extensionPragma = jSONObject2;
        }
    }
}
