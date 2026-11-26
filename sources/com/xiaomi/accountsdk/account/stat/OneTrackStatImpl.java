package com.xiaomi.accountsdk.account.stat;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.accountsdk.request.intercept.NetworkInterceptor;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.ServiceQualityEvent;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class OneTrackStatImpl extends AccountStatInterface {
    private static final String APP_ID = "31000000503";
    private static final String CHANNEL = "default";
    private final OneTrack mOneTrack;

    public OneTrackStatImpl(Context context, boolean z, OneTrack.Mode mode) {
        this.mOneTrack = OneTrack.createInstance(context, new Configuration.Builder().setAppId(APP_ID).setMode(mode).setChannel("default").setAutoTrackActivityAction(true).setExceptionCatcherEnable(true).setInternational(z).build());
        setCommonProperty(getDefaultCommonProperty(context));
        NetworkInterceptor.set(new MiStatsNetworkReporter());
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void enableNetworkAccess(Context context) {
        OneTrack.setAccessNetworkEnable(context, true);
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void statHttpEvent(String str, long j) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mOneTrack.trackServiceQualityEvent(getGeneralOneTrackNetEventBuilder(str).setDuration(Long.valueOf(j)).build());
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void statHttpEvent(String str, Exception exc) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mOneTrack.trackServiceQualityEvent(getGeneralOneTrackNetEventBuilder(str).setExceptionTag(exc.getClass().getSimpleName()).build());
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void trackNetAvailable(NetStatParam netStatParam) {
        ServiceQualityEvent.ResultType resultType = ServiceQualityEvent.ResultType.SUCCESS;
        if (netStatParam.resultType == 1) {
            resultType = ServiceQualityEvent.ResultType.FAILED;
        } else if (netStatParam.resultType == 2) {
            resultType = ServiceQualityEvent.ResultType.TIMEOUT;
        }
        this.mOneTrack.trackServiceQualityEvent(getGeneralOneTrackNetEventBuilder(netStatParam.url).setResponseCode(Integer.valueOf(netStatParam.responseCode)).setResultType(resultType).setRequestTimestamp(Long.valueOf(netStatParam.requestStartTime)).setRetryCount(Integer.valueOf(netStatParam.retryCount)).setDuration(Long.valueOf(netStatParam.timeCost)).setExceptionTag(netStatParam.exception).build());
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void track(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        this.mOneTrack.track(str, map);
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void setCommonProperty(Map<String, Object> map) {
        this.mOneTrack.setCommonProperty(map);
    }

    private ServiceQualityEvent.Builder getGeneralOneTrackNetEventBuilder(String str) {
        HashMap map = new HashMap();
        Uri uri = Uri.parse(str);
        String scheme = uri.getScheme();
        String path = uri.getPath();
        String host = uri.getHost();
        if (!uri.isOpaque()) {
            for (String str2 : uri.getQueryParameterNames()) {
                map.put(str2, uri.getQueryParameter(str2));
            }
        }
        return new ServiceQualityEvent.Builder().setScheme(scheme).setHost(path).setPath(host).setExtraParams(map);
    }
}
