package com.xiaomi.phonenum.obtain;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.miui.handwriting.common.ai.anno.CreationType;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.sim.SIMInfo;
import com.xiaomi.phonenum.Constant;
import com.xiaomi.phonenum.bean.Error;
import com.xiaomi.phonenum.bean.Sim;
import com.xiaomi.phonenum.data.AccountCertification;
import com.xiaomi.phonenum.http.HttpFactory;
import com.xiaomi.phonenum.http.Request;
import com.xiaomi.phonenum.http.Response;
import com.xiaomi.phonenum.phone.PhoneInfo;
import com.xiaomi.phonenum.phone.PhoneUtil;
import com.xiaomi.phonenum.utils.TraceId;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class ObtainHandler {
    private static final String TAG = "ObtainHandler";
    private static volatile ObtainHandler sInstance;
    private final Context context;
    private final HttpFactory httpFactory;
    private final Parser parser;
    private final PhoneUtil phoneUtil;

    private ObtainHandler(Context context) {
        this.context = context;
        this.phoneUtil = PhoneInfo.get(context);
        EncryptHttpClient.HttpFactory httpFactory = new EncryptHttpClient.HttpFactory(context);
        this.httpFactory = httpFactory;
        DataProxyParser dataProxyParser = new DataProxyParser(httpFactory);
        this.parser = dataProxyParser;
        dataProxyParser.setNext(new HttpProxyParser(httpFactory));
    }

    public static ObtainHandler getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ObtainHandler.class) {
                if (sInstance == null) {
                    sInstance = new ObtainHandler(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private String getPhoneNumberConfig(int i, String str, String str2, String str3) throws IOException, PhoneException {
        AccountLogger.log(TAG, "PhoneNumberRequest#TraceId=" + str);
        Response responseRequestPhoneNum = requestPhoneNum(i, str, "data", str2, str3);
        while (!isPhoneResponse(responseRequestPhoneNum)) {
            try {
                responseRequestPhoneNum = this.parser.parse(i, responseRequestPhoneNum.body);
            } catch (JSONException e) {
                throw new PhoneException(Error.JSON, "getPhoneNumberConfig parse response failed", e);
            }
        }
        if (TextUtils.isEmpty(responseRequestPhoneNum.body)) {
            throw new PhoneException(Error.UNKNOW, "response with empty body");
        }
        return responseRequestPhoneNum.body;
    }

    public AccountCertification obtainAccountCertification(int i, String str, String str2) throws IOException, PhoneException {
        return parseAccountCertificationResult(getPhoneNumberConfig(i, TraceId.any(), str, str2), i);
    }

    private boolean isPhoneResponse(Response response) throws IOException, PhoneException {
        if (response == null || response.code != 200 || response.body == null) {
            throw new IOException("getInNetTime ObtainStrategy response:" + response);
        }
        try {
            JSONObject jSONObject = new JSONObject(response.body);
            int i = jSONObject.getInt("code");
            if (i != 0) {
                throw new PhoneException(Error.codeToError(i), jSONObject.optString("desc"));
            }
            return "phoneNumber".equals(jSONObject.getString("result"));
        } catch (JSONException e) {
            throw new PhoneException(Error.JSON, "isPhoneResponse parse failed", e);
        }
    }

    private AccountCertification parseAccountCertificationResult(String str, int i) throws PhoneException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.getJSONObject("phoneNumber");
            String string = jSONObject2.getString("numberHash");
            String string2 = jSONObject2.getString(Constants.VERIFY_SUCESS_TOKEN);
            AccountCertification.Source source = null;
            String strOptString = jSONObject.optString("operatorType", null);
            String strOptString2 = jSONObject.optString("operatorLink", null);
            if (!TextUtils.isEmpty(strOptString) && !TextUtils.isEmpty(strOptString2)) {
                source = new AccountCertification.Source(strOptString, strOptString2);
            }
            return new AccountCertification(i, string, string2, source);
        } catch (JSONException e) {
            throw new PhoneException(Error.JSON, "parseAccountCertificationResult failed", e);
        }
    }

    private Response requestPhoneNum(int i, String str, String str2, String str3, String str4) throws IOException {
        HashMap map = new HashMap();
        Sim simTryGetSimForSubId = this.phoneUtil.tryGetSimForSubId(i);
        putToMap(map, "iccid", simTryGetSimForSubId.iccid);
        putToMap(map, SIMInfo.SIM_INFO_TYPE_IMSI, simTryGetSimForSubId.imsi);
        putToMap(map, "simMccmnc", simTryGetSimForSubId.mccmnc);
        putToMap(map, "networkMccmnc", this.phoneUtil.getNetworkMccMncForSubId(i));
        putToMap(map, "sid", str3);
        putToMap(map, com.market.sdk.utils.Constants.JSON_IMEI_MD5, this.phoneUtil.getImei());
        putToMap(map, "deviceId", this.phoneUtil.getDeviceId());
        putToMap(map, "phoneType", "" + this.phoneUtil.getPhoneTypeForSubId(i));
        putToMap(map, "traceId", str);
        putToMap(map, "versionCode", CreationType.CREATION_TEXT);
        putToMap(map, "phoneLevel", str2);
        putToMap(map, "pip", getMobileIp());
        putToMap(map, "packageName", str4);
        Request requestBuild = new Request.Builder().url(Constant.OBTAIN_STRATEGY_URL).queryParams(map).ua(makeUA(this.context)).build();
        AccountLogger.log(TAG, "getCloudControl traceId=" + str);
        return this.httpFactory.createHttpClient().excute(requestBuild);
    }

    private String getMobileIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (!networkInterfaceNextElement.getName().toLowerCase().contains("wlan")) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                            return inetAddressNextElement.getHostAddress();
                        }
                    }
                }
            }
            return "";
        } catch (Exception e) {
            AccountLogger.log(TAG, "get mobile ip failed", e);
            return "";
        }
    }

    private void putToMap(Map<String, String> map, String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        map.put(str, str2);
    }

    private String makeUA(Context context) {
        return "Android/" + Build.VERSION.RELEASE + " SDK_INT/" + Build.VERSION.SDK_INT + " BRAND/" + Build.BRAND + " PRODUCT/" + Build.PRODUCT + " MODEL/" + Build.MODEL + " INCREMENTAL/" + Build.VERSION.INCREMENTAL + " APP/" + context.getPackageName();
    }
}
