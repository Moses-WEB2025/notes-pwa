package com.xiaomi.micloudsdk.request;

import com.xiaomi.micloudsdk.request.utils.RequestContext;
import java.io.IOException;
import java.util.Locale;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: classes4.dex */
public class CloudHttpClient implements HttpClient {
    private HttpClient mProxy;

    protected CloudHttpClient(HttpClient httpClient) {
        this.mProxy = httpClient;
    }

    public static CloudHttpClient newInstance() {
        return new CloudHttpClient(initClient());
    }

    protected static DefaultHttpClient initClient() {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpProtocolParams.setUserAgent(defaultHttpClient.getParams(), RequestContext.getUserAgent());
        return defaultHttpClient;
    }

    @Override // org.apache.http.client.HttpClient
    public HttpParams getParams() {
        return this.mProxy.getParams();
    }

    @Override // org.apache.http.client.HttpClient
    public ClientConnectionManager getConnectionManager() {
        return this.mProxy.getConnectionManager();
    }

    @Override // org.apache.http.client.HttpClient
    public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException {
        addHeader(httpUriRequest);
        return this.mProxy.execute(httpUriRequest);
    }

    @Override // org.apache.http.client.HttpClient
    public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException {
        addHeader(httpUriRequest);
        return this.mProxy.execute(httpUriRequest, httpContext);
    }

    @Override // org.apache.http.client.HttpClient
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        addHeader(httpRequest);
        return this.mProxy.execute(httpHost, httpRequest);
    }

    @Override // org.apache.http.client.HttpClient
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException {
        addHeader(httpRequest);
        return this.mProxy.execute(httpHost, httpRequest);
    }

    @Override // org.apache.http.client.HttpClient
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws IOException {
        addHeader(httpUriRequest);
        return (T) this.mProxy.execute(httpUriRequest, responseHandler);
    }

    @Override // org.apache.http.client.HttpClient
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException {
        addHeader(httpUriRequest);
        return (T) this.mProxy.execute(httpUriRequest, responseHandler, httpContext);
    }

    @Override // org.apache.http.client.HttpClient
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws IOException {
        addHeader(httpRequest);
        return (T) this.mProxy.execute(httpHost, httpRequest, responseHandler);
    }

    @Override // org.apache.http.client.HttpClient
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException {
        addHeader(httpRequest);
        return (T) this.mProxy.execute(httpHost, httpRequest, responseHandler, httpContext);
    }

    private void addHeader(HttpRequest httpRequest) {
        httpRequest.addHeader("Accept-Language", Locale.getDefault().toString());
    }
}
