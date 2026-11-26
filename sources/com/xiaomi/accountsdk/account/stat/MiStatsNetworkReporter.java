package com.xiaomi.accountsdk.account.stat;

import com.xiaomi.accountsdk.request.intercept.NetworkInterceptCallback;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.net.SocketTimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public class MiStatsNetworkReporter implements NetworkInterceptCallback {
    public static final int RESULT_TYPE_FAILED = 1;
    public static final int RESULT_TYPE_SUCCESS = 0;
    public static final int RESULT_TYPE_TIMEOUT = 2;
    private static final String TAG = "MiStatsNetworkReporter";

    private void trackSafely(NetStatParam netStatParam) {
        try {
            AccountStatInterface.getInstance().trackNetAvailable(netStatParam);
        } catch (Exception e) {
            AccountLogger.log(TAG, "Why does trackNetAvailable() throw exception?", e);
        }
    }

    @Override // com.xiaomi.accountsdk.request.intercept.NetworkInterceptCallback
    public void onBegin(String str, String str2, String str3) {
        trackSafely(new NetStatParam.Builder().setUrl(str3).setRequestStartTime(System.currentTimeMillis()).create());
    }

    @Override // com.xiaomi.accountsdk.request.intercept.NetworkInterceptCallback
    public void onSuccess(String str, String str2, String str3, long j, int i, int i2) {
        trackSafely(new NetStatParam.Builder().setUrl(str3).setRequestStartTime(j).setResponseCode(i).setResultType(0).create());
    }

    @Override // com.xiaomi.accountsdk.request.intercept.NetworkInterceptCallback
    public void onException(String str, String str2, String str3, long j, Exception exc, Integer num) {
        NetStatParam.Builder resultType = new NetStatParam.Builder().setUrl(str3).setRequestStartTime(j).setException(exc.getClass().getSimpleName()).setResultType(exc instanceof SocketTimeoutException ? 2 : 1);
        if (num != null) {
            resultType.setResponseCode(num.intValue());
        }
        trackSafely(resultType.create());
    }
}
