package com.xiaomi.passport;

import android.content.Context;
import android.os.Build;
import android.util.Base64;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.NonceCoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class SecurityDeviceReporter implements SecurityDeviceSignManager.SecurityDeviceReport {
    private static final String TAG = "SecurityDeviceReporter";

    @Override // com.xiaomi.passport.SecurityDeviceSignManager.SecurityDeviceReport
    public void report(Context context, int i, String str, String str2) {
        reportNoService(context.getApplicationContext(), i, str, str2);
    }

    private static void reportNoService(Context context, int i, String str, String str2) {
        try {
            SimpleRequest.postAsString("https://tz.sec.xiaomi.com/session", new EasyMap().easyPut("fidNonce", Base64.encodeToString(buildPlain(context, i, str, str2).getBytes(StandardCharsets.UTF_8), 10)).easyPut("fidNonceSign", ""), null, true);
        } catch (AccessDeniedException | AuthenticationFailureException | IOException e) {
            AccountLogger.log(TAG, "reportNoService err msg:" + e.getMessage());
        }
    }

    private static String buildPlain(Context context, int i, String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("deviceType", Build.DEVICE);
            jSONObject.put("oaid", PrivacyDataMaster.get(context, PrivacyDataType.OAID, new String[0]));
            jSONObject.put("code", String.valueOf(i));
            jSONObject.put(com.xiaomi.verificationsdk.internal.Constants.NONCE, NonceCoder.generateNonce());
            jSONObject.put("deviceId", str2);
            jSONObject.put("cpuId", str);
            jSONObject.put("fid", "");
            jSONObject.put("publicKey", "");
            jSONObject.put(com.xiaomi.verificationsdk.internal.Constants.ROOT, "");
            return jSONObject.toString();
        } catch (JSONException unused) {
            throw new IllegalStateException("should not happen");
        }
    }
}
