package com.xiaomi.uplink;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidParameterException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.SharedPreferencesUtil;
import com.xiaomi.uplink.data.UpLinkVerifyResult;
import com.xiaomi.uplink.data.UplinkRemoteConfigInfo;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes4.dex */
public class PassportUplink {
    public static final String GW_SP_FILE = "gateway_sp_name";
    public static final String SEND_SMS_ACTION = "com.xiaomi.account.service.SMS_SENT";
    private static final String TAG = "PassportUplink";
    private static final int[] SERVER_RETRY_INTERVALS = {2000, 5000, 10000, 20000, 20000, 20000, 20000};
    private static final Object sQueryGatewayLock = new Object();
    private static AtomicBoolean sIsQueryingGateway = new AtomicBoolean(false);

    public static UplinkRemoteConfigInfo querySmsGateway(Context context, String str, String str2, String str3) throws AccessDeniedException, InterruptedException, IOException, InvalidResponseException, IllegalArgumentException, AuthenticationFailureException {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException(" query gateways invalid params ");
        }
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(context, GW_SP_FILE);
        if (sIsQueryingGateway.getAndSet(true)) {
            Object obj = sQueryGatewayLock;
            synchronized (obj) {
                AccountLogger.log(TAG, "Waiting for an existing querySmsGateway to finish");
                obj.wait();
                AccountLogger.log(TAG, "The existing querySmsGateway finished");
                if (TextUtils.isEmpty(sharedPreferencesUtil.getString(SmsUplinkConfig.SMS_GW_V2))) {
                    return null;
                }
                return UplinkRemoteConfigInfo.fromString(sharedPreferencesUtil.getString(SmsUplinkConfig.SMS_GW_V2));
            }
        }
        try {
            UplinkRemoteConfigInfo uplinkRemoteConfigInfoQuerySmsGateway = XMPassport.querySmsGateway(str, "PH", SmsUplinkConfig.UplinkScene.LOGIN.getSceneId(), str3, str2);
            String content = uplinkRemoteConfigInfoQuerySmsGateway.getContent();
            AccountLogger.log(TAG, "QuerySmsGwTask: Fetched sms gateways, writing into system settings.");
            sharedPreferencesUtil.saveString(SmsUplinkConfig.SMS_GW_V2, content);
            sIsQueryingGateway.set(false);
            Object obj2 = sQueryGatewayLock;
            synchronized (obj2) {
                obj2.notifyAll();
            }
            return uplinkRemoteConfigInfoQuerySmsGateway;
        } catch (Throwable th) {
            sIsQueryingGateway.set(false);
            Object obj3 = sQueryGatewayLock;
            synchronized (obj3) {
                obj3.notifyAll();
                throw th;
            }
        }
    }

    public UpLinkVerifyResult verifyWithRetry(String str, String str2, String str3, String str4, String str5) throws AccessDeniedException, InvalidParameterException, InterruptedException, IOException, InvalidResponseException, IllegalArgumentException, AuthenticationFailureException {
        if (TextUtils.isEmpty(str5)) {
            throw new IllegalArgumentException("verify param ticket is null ");
        }
        int i = 0;
        while (true) {
            try {
                return XMPassport.verifyMobileOrignal(str, str2, str3, str4, str5);
            } catch (InvalidCredentialException unused) {
                AccountLogger.log(TAG, " verify ticket faild and retry again !");
                if (i >= SERVER_RETRY_INTERVALS.length) {
                    return null;
                }
                StringBuilder sb = new StringBuilder("Wait ");
                int[] iArr = SERVER_RETRY_INTERVALS;
                AccountLogger.log(TAG, sb.append(iArr[i]).append(" ms for retry[#").append(i).append("]").toString());
                Thread.sleep(iArr[i]);
                i++;
            }
        }
    }

    public static String makeActivateSms(String str, String str2) {
        return String.format("%s#%s#%s", SmsUplinkConfig.ACTIVATION_SMS_PREFIX, str, str2);
    }

    public static String createVerifyCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
