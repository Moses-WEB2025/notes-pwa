package cn.kuaipan.android.http.client;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.utils.ContextUtils;
import defpackage.R2;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import miui.cloud.common.XLogger;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;

/* JADX INFO: loaded from: classes.dex */
public class KscHttpClient extends DefaultHttpClient {
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final int CONNECTION_TOTAL_COUNT = 30;
    private static final int ERROR_TIMEOUT = 10000;
    public static final String KSC_CONNECT_REDIRECTOR = "ksc.connect_redirector";
    public static final String KSC_CONNECT_START = "ksc.connect_start";
    public static final String KSC_CONNECT_TYPE = "ksc.connect_type";
    public static final String KSC_MESSAGE_LIST = "ksc.message_list";
    private static final String LOG_TAG = "KscHttpClient";
    private static final int SO_BUFFER_SIZE = 8192;
    private static final int SO_TIMEOUT = 30000;
    private static final int TIMEOUT = 30000;
    private static String sUserAgent;
    private volatile LoggingConfiguration curlConfiguration;
    private final boolean mForKssTransmission;
    private final boolean mKeepAlive;
    private RuntimeException mLeakedException;
    private final boolean mRequestSentRetryEnabled;

    public static KscHttpClient newInstance(String str, boolean z, boolean z2) {
        HttpParams httpParams = getHttpParams(str);
        return new KscHttpClient(getClientConnectionManager(httpParams), httpParams, z, z2);
    }

    public static KscHttpClient newKssInstance(String str) {
        HttpParams httpParams = getHttpParams(str);
        return new KscHttpClient(getClientConnectionManager(httpParams), httpParams);
    }

    private static HttpParams getHttpParams(String str) {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, false);
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 15000);
        HttpConnectionParams.setSoTimeout(basicHttpParams, 30000);
        HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
        ConnManagerParams.setTimeout(basicHttpParams, 30000L);
        ConnManagerParams.setMaxTotalConnections(basicHttpParams, 30);
        ConnManagerParams.setMaxConnectionsPerRoute(basicHttpParams, new ConnPerRoute() { // from class: cn.kuaipan.android.http.client.KscHttpClient.1
            @Override // org.apache.http.conn.params.ConnPerRoute
            public int getMaxForRoute(HttpRoute httpRoute) {
                return 32;
            }
        });
        HttpClientParams.setRedirecting(basicHttpParams, true);
        HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
        HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
        HttpProtocolParams.setHttpElementCharset(basicHttpParams, "UTF-8");
        if (TextUtils.isEmpty(str)) {
            str = getDefaultUserAgent();
        }
        HttpProtocolParams.setUserAgent(basicHttpParams, str);
        return basicHttpParams;
    }

    private static ClientConnectionManager getClientConnectionManager(HttpParams httpParams) {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), R2.attr.backgroundPrimaryDisableColor));
        return new ThreadSafeClientConnManager(httpParams, schemeRegistry);
    }

    private static String getDefaultUserAgent() {
        if (sUserAgent == null) {
            sUserAgent = String.format("KscClient/1.0 (Linux; U; Android %s)", ContextUtils.getFrameworkVersion());
        }
        return sUserAgent;
    }

    private KscHttpClient(ClientConnectionManager clientConnectionManager, HttpParams httpParams, boolean z, boolean z2) {
        super(clientConnectionManager, httpParams);
        this.mLeakedException = new IllegalStateException("AndroidHttpClient created and never closed");
        this.mForKssTransmission = false;
        this.mKeepAlive = z;
        this.mRequestSentRetryEnabled = z2;
    }

    private KscHttpClient(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        super(clientConnectionManager, httpParams);
        this.mLeakedException = new IllegalStateException("AndroidHttpClient created and never closed");
        this.mForKssTransmission = true;
        this.mKeepAlive = true;
        this.mRequestSentRetryEnabled = true;
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    protected BasicHttpProcessor createHttpProcessor() {
        BasicHttpProcessor basicHttpProcessorCreateHttpProcessor;
        if (this.mForKssTransmission) {
            basicHttpProcessorCreateHttpProcessor = new BasicHttpProcessor();
            basicHttpProcessorCreateHttpProcessor.addInterceptor(new RequestDefaultHeaders());
            basicHttpProcessorCreateHttpProcessor.addInterceptor(new RequestContent());
            basicHttpProcessorCreateHttpProcessor.addInterceptor(new RequestTargetHost());
            basicHttpProcessorCreateHttpProcessor.addInterceptor(new RequestConnControl());
            basicHttpProcessorCreateHttpProcessor.addInterceptor(new RequestUserAgent());
        } else {
            basicHttpProcessorCreateHttpProcessor = super.createHttpProcessor();
        }
        basicHttpProcessorCreateHttpProcessor.addRequestInterceptor(new TimeMarker());
        basicHttpProcessorCreateHttpProcessor.addRequestInterceptor(new CurlLogger());
        return basicHttpProcessorCreateHttpProcessor;
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    protected HttpRequestRetryHandler createHttpRequestRetryHandler() {
        return new KscHttpRequestRetryHandler(3, this.mRequestSentRetryEnabled, 10000);
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    protected HttpRoutePlanner createHttpRoutePlanner() {
        return new KscHttpRoutePlanner(getConnectionManager().getSchemeRegistry());
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    protected RedirectHandler createRedirectHandler() {
        return new KscRedirectHandler();
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    protected ConnectionReuseStrategy createConnectionReuseStrategy() {
        if (this.mKeepAlive) {
            return new DefaultConnectionReuseStrategy();
        }
        return new NoConnectionReuseStrategy();
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void close() {
        if (this.mLeakedException != null) {
            getConnectionManager().shutdown();
            this.mLeakedException = null;
        }
    }

    private static class LoggingConfiguration {
        private final int level;
        private final String tag;

        private LoggingConfiguration(String str, int i) {
            this.tag = str;
            this.level = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isLoggable() {
            return Log.isLoggable(this.tag, this.level);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void println(String str) {
            Log.println(this.level, this.tag, str);
        }
    }

    public void enableCurlLogging(String str, int i) {
        if (str == null) {
            throw new NullPointerException("name");
        }
        if (i < 2 || i > 7) {
            throw new IllegalArgumentException("Level is out of range [2..7]");
        }
        this.curlConfiguration = new LoggingConfiguration(str, i);
    }

    public void disableCurlLogging() {
        this.curlConfiguration = null;
    }

    private class CurlLogger implements HttpRequestInterceptor {
        private CurlLogger() {
        }

        @Override // org.apache.http.HttpRequestInterceptor
        public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
            LoggingConfiguration loggingConfiguration = KscHttpClient.this.curlConfiguration;
            boolean z = httpRequest instanceof HttpUriRequest;
            if (z) {
                XLogger.logi("CurlLogger", KscHttpClient.toCurl((HttpUriRequest) httpRequest, false));
            }
            if (loggingConfiguration != null && loggingConfiguration.isLoggable() && z) {
                loggingConfiguration.println(KscHttpClient.toCurl((HttpUriRequest) httpRequest, false));
            }
        }
    }

    private class TimeMarker implements HttpRequestInterceptor {
        private TimeMarker() {
        }

        @Override // org.apache.http.HttpRequestInterceptor
        public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
            httpContext.setAttribute(KscHttpClient.KSC_CONNECT_START, Long.valueOf(SystemClock.elapsedRealtime()));
            List linkedList = (List) httpContext.getAttribute(KscHttpClient.KSC_MESSAGE_LIST);
            if (linkedList == null) {
                linkedList = new LinkedList();
                httpContext.setAttribute(KscHttpClient.KSC_MESSAGE_LIST, linkedList);
            }
            linkedList.add(httpRequest);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String toCurl(HttpUriRequest httpUriRequest, boolean z) throws IOException {
        HttpEntity entity;
        StringBuilder sb = new StringBuilder("curl ");
        for (Header header : httpUriRequest.getAllHeaders()) {
            if (z || (!header.getName().equals("Authorization") && !header.getName().equals("Cookie"))) {
                sb.append("--header \"");
                sb.append(header.toString().trim());
                sb.append("\" ");
            }
        }
        URI uri = httpUriRequest.getURI();
        if (httpUriRequest instanceof RequestWrapper) {
            HttpRequest original = ((RequestWrapper) httpUriRequest).getOriginal();
            if (original instanceof HttpUriRequest) {
                uri = ((HttpUriRequest) original).getURI();
            }
        }
        sb.append("\"");
        sb.append(uri);
        sb.append("\"");
        if ((httpUriRequest instanceof HttpEntityEnclosingRequest) && (entity = ((HttpEntityEnclosingRequest) httpUriRequest).getEntity()) != null && entity.isRepeatable()) {
            if (entity.getContentLength() < 1024) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                entity.writeTo(byteArrayOutputStream);
                sb.append(" --data-ascii \"").append(byteArrayOutputStream.toString()).append("\"");
            } else {
                sb.append(" [TOO MUCH DATA TO INCLUDE]");
            }
        }
        return sb.toString();
    }
}
