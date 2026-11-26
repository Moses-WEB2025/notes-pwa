package cn.kuaipan.android.http;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import cn.kuaipan.android.KssConfig;
import cn.kuaipan.android.exception.ErrorHelper;
import cn.kuaipan.android.http.client.KscHttpClient;
import cn.kuaipan.android.http.client.URIRedirector;
import cn.kuaipan.android.utils.ContextUtils;
import cn.kuaipan.android.utils.HttpUtils;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;

/* JADX INFO: loaded from: classes.dex */
public class KscHttpTransmitter {
    private static final int FLAG_MASK = 7;
    private static final int FLAG_TRANSMISSION = 4;
    private static final int FLAG_TRYRESEND = 1;
    private static final int FLAG_UNKEEPALIVE = 2;
    private static final String LOG_TAG = "KscHttpTransmitter";
    private static final int SAFE_REUSE_DURATION = 180000;
    private static final int SPEED_RECODE_DURATION = 1800;
    public static final int TYPE_KEEPALIVE = 0;
    public static final int TYPE_KEEPALIVE_TRYRESEND = 1;
    public static final int TYPE_KSS_TRANSMISSION = 4;
    public static final int TYPE_UNKEEPALIVE = 2;
    public static final int TYPE_UNKEEPALIVE_TRYRESEND = 3;
    private final NetCacheManager mCacheManager;
    private final SparseArray<Pair<Long, ? extends HttpClient>> mClients;
    private final Context mContext;
    private final KscSpeedManager mDownloadSpeedManager;
    private URIRedirector mRedirector;
    private final KscSpeedManager mUploadSpeedManager;
    private final SparseArray<String> mUserAgents;

    public KscHttpTransmitter(Context context) {
        ContextUtils.init(context);
        this.mContext = context;
        this.mUploadSpeedManager = new KscSpeedManager(1800);
        this.mDownloadSpeedManager = new KscSpeedManager(1800);
        this.mCacheManager = NetCacheManager.getInstance(context, true);
        this.mClients = new SparseArray<>(4);
        this.mUserAgents = new SparseArray<>(4);
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setRedirector(URIRedirector uRIRedirector) {
        this.mRedirector = uRIRedirector;
    }

    public KscHttpResponse execute(KscHttpRequest kscHttpRequest, int i) throws InterruptedException {
        return execute(kscHttpRequest, i, null);
    }

    public KscHttpResponse execute(KscHttpRequest kscHttpRequest, int i, KssTransferStopper kssTransferStopper) throws InterruptedException {
        KscSpeedMonitor kscSpeedMonitor;
        URIRedirector uRIRedirector;
        KssTransferStopper kssTransferStopper2 = kssTransferStopper == null ? KssConfig.DEFAULT_TRANSFER_STOPPER : kssTransferStopper;
        IKscTransferListener listener = kscHttpRequest.getListener();
        KscHttpResponse kscHttpResponse = new KscHttpResponse(this.mCacheManager);
        BasicHttpContext basicHttpContext = new BasicHttpContext();
        try {
            HttpUriRequest request = kscHttpRequest.getRequest();
            kscHttpResponse.setOrigRequest(request);
            HttpClient client = getClient(i);
            if ((i & 1) != 0 && (uRIRedirector = this.mRedirector) != null) {
                basicHttpContext.setAttribute(KscHttpClient.KSC_CONNECT_REDIRECTOR, uRIRedirector);
            }
            String requestHost = getRequestHost(kscHttpRequest.getRequest());
            KscSpeedMonitor moniter = this.mUploadSpeedManager.getMoniter(requestHost);
            KscSpeedMonitor moniter2 = this.mDownloadSpeedManager.getMoniter(requestHost);
            setMonitor(request, moniter, listener, kssTransferStopper2);
            long jCurrent = KscSpeedManager.current();
            HttpResponse httpResponseExecute = client.execute(request, basicHttpContext);
            long jCurrent2 = KscSpeedManager.current();
            Object attribute = basicHttpContext.getAttribute(KscHttpClient.KSC_MESSAGE_LIST);
            if (attribute instanceof List) {
                try {
                    List<HttpMessage> list = (List) attribute;
                    if (list != null && !list.isEmpty()) {
                        kscHttpResponse.setMessage(list);
                    }
                    long requestSize = HttpUtils.getRequestSize(getRequest(list));
                    long responseSize = HttpUtils.getResponseSize(getResponse(list)) + HttpUtils.getResponseSize(httpResponseExecute, false);
                    moniter.recode(jCurrent, jCurrent2, requestSize);
                    kscSpeedMonitor = moniter2;
                    try {
                        kscSpeedMonitor.recode(jCurrent, jCurrent2, responseSize);
                    } catch (Exception e) {
                        e = e;
                        Log.w(LOG_TAG, "Failed get requestList from context.", e);
                    }
                } catch (Exception e2) {
                    e = e2;
                    kscSpeedMonitor = moniter2;
                }
            } else {
                kscSpeedMonitor = moniter2;
            }
            setMonitor(httpResponseExecute, kscSpeedMonitor, listener, kssTransferStopper2);
            kscHttpResponse.handleResponse(kscHttpRequest, httpResponseExecute, (i & 4) == 0);
        } catch (Throwable th) {
            ErrorHelper.handleInterruptException(th);
            Log.w(LOG_TAG, "Meet exception when execute a KscHttpRequest.", th);
            kscHttpResponse.setError(th);
        }
        return kscHttpResponse;
    }

    private static void setMonitor(HttpMessage httpMessage, KscSpeedMonitor kscSpeedMonitor, IKscTransferListener iKscTransferListener, KssTransferStopper kssTransferStopper) {
        HttpResponse httpResponse;
        HttpEntity entity;
        if (httpMessage instanceof HttpEntityEnclosingRequestBase) {
            HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = (HttpEntityEnclosingRequestBase) httpMessage;
            HttpEntity entity2 = httpEntityEnclosingRequestBase.getEntity();
            if (entity2 != null) {
                httpEntityEnclosingRequestBase.setEntity(new ProcessMonitorEntity(entity2, kscSpeedMonitor, iKscTransferListener, kssTransferStopper, true));
                return;
            }
            return;
        }
        if (!(httpMessage instanceof HttpResponse) || (entity = (httpResponse = (HttpResponse) httpMessage).getEntity()) == null) {
            return;
        }
        httpResponse.setEntity(new ProcessMonitorEntity(entity, kscSpeedMonitor, iKscTransferListener, kssTransferStopper, false));
    }

    private static HttpResponse[] getResponse(List<HttpMessage> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (HttpMessage httpMessage : list) {
            if (httpMessage instanceof HttpResponse) {
                arrayList.add((HttpResponse) httpMessage);
            }
        }
        return (HttpResponse[]) arrayList.toArray(new HttpResponse[arrayList.size()]);
    }

    private HttpRequest[] getRequest(List<HttpMessage> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (HttpMessage httpMessage : list) {
            if (httpMessage instanceof HttpRequest) {
                arrayList.add((HttpRequest) httpMessage);
            }
        }
        return (HttpRequest[]) arrayList.toArray(new HttpRequest[arrayList.size()]);
    }

    public void setUserAgent(int i, String str) {
        this.mUserAgents.put(i, str);
        Pair<Long, ? extends HttpClient> pair = this.mClients.get(i);
        if (pair == null || pair.second == null) {
            return;
        }
        HttpProtocolParams.setUserAgent(((HttpClient) pair.second).getParams(), str);
    }

    private HttpClient getClient(int i) {
        KscHttpClient kscHttpClientNewInstance;
        int i2 = i & 7;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        Pair<Long, ? extends HttpClient> pair = this.mClients.get(i2);
        if (pair == null || jElapsedRealtime - ((Long) pair.first).longValue() > 180000) {
            String str = this.mUserAgents.get(i2);
            if ((i & 4) != 0) {
                kscHttpClientNewInstance = KscHttpClient.newKssInstance(str);
            } else {
                kscHttpClientNewInstance = KscHttpClient.newInstance(str, (i & 2) == 0, (i & 1) != 0);
            }
            this.mClients.put(i2, Pair.create(Long.valueOf(jElapsedRealtime), kscHttpClientNewInstance));
            return kscHttpClientNewInstance;
        }
        return (HttpClient) pair.second;
    }

    public KscSpeedManager getUploadSpeeder() {
        return this.mUploadSpeedManager;
    }

    public KscSpeedManager getDownloadSpeeder() {
        return this.mDownloadSpeedManager;
    }

    private static String getRequestHost(HttpUriRequest httpUriRequest) {
        URI uri;
        if (httpUriRequest == null || (uri = httpUriRequest.getURI()) == null) {
            return null;
        }
        return uri.getHost();
    }
}
