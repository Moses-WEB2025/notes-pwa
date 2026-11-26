package cn.kuaipan.android.http.client;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: classes.dex */
public class KscHttpRequestRetryHandler implements HttpRequestRetryHandler {
    private final int errorTimeOut;
    private final boolean requestSentRetryEnabled;
    private final int retryCount;

    public KscHttpRequestRetryHandler(int i, boolean z, int i2) {
        this.retryCount = i;
        this.requestSentRetryEnabled = z;
        this.errorTimeOut = i2;
    }

    public KscHttpRequestRetryHandler() {
        this(3, false, 0);
    }

    @Override // org.apache.http.client.HttpRequestRetryHandler
    public boolean retryRequest(IOException iOException, int i, HttpContext httpContext) {
        if (iOException == null) {
            throw new IllegalArgumentException("Exception parameter may not be null");
        }
        if (httpContext == null) {
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        if (i > this.retryCount) {
            return false;
        }
        if (iOException instanceof NoHttpResponseException) {
            return true;
        }
        if ((iOException instanceof InterruptedIOException) || (iOException instanceof UnknownHostException) || (iOException instanceof SSLHandshakeException)) {
            return false;
        }
        Boolean bool = (Boolean) httpContext.getAttribute("http.request_sent");
        Long l = (Long) httpContext.getAttribute(KscHttpClient.KSC_CONNECT_START);
        RequestWrapper requestWrapper = (RequestWrapper) httpContext.getAttribute("http.request");
        URIRedirector uRIRedirector = (URIRedirector) httpContext.getAttribute(KscHttpClient.KSC_CONNECT_REDIRECTOR);
        boolean z = bool != null && bool.booleanValue();
        long jLongValue = l != null ? l.longValue() : 0L;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (z && ((!this.requestSentRetryEnabled || !requestWrapper.isRepeatable()) && (!(iOException instanceof SocketException) || jElapsedRealtime - jLongValue > this.errorTimeOut))) {
            return false;
        }
        if (uRIRedirector != null) {
            return uRIRedirector.redirect(httpContext);
        }
        return true;
    }

    public boolean isRequestSentRetryEnabled() {
        return this.requestSentRetryEnabled;
    }

    public int getRetryCount() {
        return this.retryCount;
    }
}
