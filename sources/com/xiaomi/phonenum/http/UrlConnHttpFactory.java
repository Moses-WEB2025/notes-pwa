package com.xiaomi.phonenum.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.SystemClock;
import android.util.Pair;
import com.xiaomi.account.privacy_data.lib.PermissionChecker;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.phonenum.bean.HttpError;
import com.xiaomi.phonenum.phone.PhoneInfo;
import com.xiaomi.phonenum.phone.PhoneUtil;
import com.xiaomi.phonenum.utils.MapUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes4.dex */
public class UrlConnHttpFactory extends HttpFactory {
    private static CookieManager cookieHandler;
    private Context context;
    private PhoneUtil phoneUtil;

    static {
        CookieManager cookieManager = new CookieManager();
        cookieHandler = cookieManager;
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    }

    public UrlConnHttpFactory(Context context) {
        this.context = context;
        this.phoneUtil = PhoneInfo.get(context);
        CookieHandler.setDefault(cookieHandler);
    }

    @Override // com.xiaomi.phonenum.http.HttpFactory
    public HttpClient createHttpClient(HttpClientConfig httpClientConfig) {
        return new HttpUrlConnClient(httpClientConfig);
    }

    private class HttpUrlConnClient implements HttpClient {
        private static final String TAG = "HttpUrlConnClient";
        private HttpClientConfig config;

        private HttpUrlConnClient(HttpClientConfig httpClientConfig) {
            this.config = httpClientConfig;
        }

        /* JADX WARN: Code duplicated, block: B:18:0x0062  */
        @Override // com.xiaomi.phonenum.http.HttpClient
        public Response excute(Request request) throws IOException {
            Pair<Network, ConnectivityManager.NetworkCallback> pairWaitForCellular;
            long jUptimeMillis = SystemClock.uptimeMillis();
            ConnectivityManager connectivityManager = (ConnectivityManager) UrlConnHttpFactory.this.context.getApplicationContext().getSystemService("connectivity");
            if (this.config.netWorkSubId >= 0) {
                if (UrlConnHttpFactory.this.phoneUtil.getDataEnabledForSubId(this.config.netWorkSubId)) {
                    if (UrlConnHttpFactory.this.phoneUtil.isNetWorkTypeMobile()) {
                        pairWaitForCellular = null;
                    } else {
                        if (!PermissionChecker.hasPermission(UrlConnHttpFactory.this.context, "android.permission.CHANGE_NETWORK_STATE")) {
                            return HttpError.NO_CHANGE_NETWORK_STATE_PERMISSION.result();
                        }
                        pairWaitForCellular = waitForCellular();
                        if (pairWaitForCellular == null) {
                            return HttpError.CELLULAR_NETWORK_NOT_AVAILABLE.result();
                        }
                    }
                } else {
                    return HttpError.DATA_NOT_ENABLED.result();
                }
            } else {
                pairWaitForCellular = null;
            }
            Response.Builder builderExcute = excute(request, openConnection(request.url, pairWaitForCellular != null ? (Network) pairWaitForCellular.first : null));
            if (pairWaitForCellular != null) {
                connectivityManager.unregisterNetworkCallback((ConnectivityManager.NetworkCallback) pairWaitForCellular.second);
            }
            return builderExcute.time(SystemClock.uptimeMillis() - jUptimeMillis).build();
        }

        private Response.Builder excute(Request request, HttpURLConnection httpURLConnection) throws IOException {
            BufferedReader bufferedReader;
            Throwable th;
            try {
                httpURLConnection.setConnectTimeout((int) this.config.connectTimeoutMs);
                httpURLConnection.setReadTimeout((int) this.config.readTimeoutMs);
                if (request.formBody != null) {
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_POST);
                } else {
                    httpURLConnection.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_GET);
                }
                httpURLConnection.setInstanceFollowRedirects(request.followRedirects);
                if (request.headers != null) {
                    for (Map.Entry<String, String> entry : request.headers.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                httpURLConnection.connect();
                if (request.formBody != null) {
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(MapUtil.joinToQuery(request.formBody));
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                }
                int responseCode = httpURLConnection.getResponseCode();
                Response.Builder builderHeaders = new Response.Builder().code(responseCode).location(httpURLConnection.getHeaderField("Location")).setCookie(httpURLConnection.getHeaderField("Set-Cookie")).headers(httpURLConnection.getHeaderFields());
                if (responseCode == 200) {
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()), 1024);
                        try {
                            StringBuilder sb = new StringBuilder();
                            while (true) {
                                String line = bufferedReader.readLine();
                                if (line != null) {
                                    sb.append(line);
                                } else {
                                    Response.Builder builderBody = builderHeaders.body(sb.toString());
                                    bufferedReader.close();
                                    httpURLConnection.disconnect();
                                    return builderBody;
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        bufferedReader = null;
                        th = th3;
                    }
                } else {
                    httpURLConnection.disconnect();
                    return builderHeaders;
                }
            } catch (Throwable th4) {
                httpURLConnection.disconnect();
                throw th4;
            }
        }

        private HttpURLConnection openConnection(String str, Network network) throws IOException {
            if (network != null) {
                return (HttpURLConnection) network.openConnection(new URL(str));
            }
            return (HttpURLConnection) new URL(str).openConnection();
        }

        private Pair<Network, ConnectivityManager.NetworkCallback> waitForCellular() {
            try {
                return waitForCellular(UrlConnHttpFactory.this.context, this.config.waitCellularTimeoutMs);
            } catch (InterruptedException e) {
                AccountLogger.log(TAG, "waitForCellular", e);
                return null;
            } catch (TimeoutException e2) {
                AccountLogger.log(TAG, "waitForCellular Timeout " + this.config.waitCellularTimeoutMs, e2);
                return null;
            }
        }

        private Pair<Network, ConnectivityManager.NetworkCallback> waitForCellular(Context context, long j) throws InterruptedException, TimeoutException {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            final AtomicReference atomicReference = new AtomicReference(null);
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addCapability(12).addTransportType(0).build();
            ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.xiaomi.phonenum.http.UrlConnHttpFactory.HttpUrlConnClient.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                    if (networkInfo == null || networkInfo.getState() != NetworkInfo.State.CONNECTED) {
                        return;
                    }
                    atomicReference.set(network);
                    countDownLatch.countDown();
                }
            };
            connectivityManager.requestNetwork(networkRequestBuild, networkCallback);
            if (!countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
                connectivityManager.unregisterNetworkCallback(networkCallback);
                throw new TimeoutException();
            }
            Network network = (Network) atomicReference.get();
            if (network == null) {
                connectivityManager.unregisterNetworkCallback(networkCallback);
                return null;
            }
            return new Pair<>(network, networkCallback);
        }
    }
}
