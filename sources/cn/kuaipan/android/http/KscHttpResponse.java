package cn.kuaipan.android.http;

import android.util.Log;
import cn.kuaipan.android.utils.HttpUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpUriRequest;

/* JADX INFO: loaded from: classes.dex */
public class KscHttpResponse {
    private static final String LOG_TAG = "KscHttpResponse";
    private final NetCacheManager mCache;
    private Throwable mError;
    private List<HttpMessage> mMessages;
    private HttpUriRequest mOrigRequest;
    private HttpResponse mResponse;

    public KscHttpResponse(NetCacheManager netCacheManager) {
        this.mCache = netCacheManager;
    }

    void setOrigRequest(HttpUriRequest httpUriRequest) {
        this.mOrigRequest = httpUriRequest;
    }

    void setMessage(List<HttpMessage> list) {
        this.mMessages = list;
    }

    void handleResponse(KscHttpRequest kscHttpRequest, HttpResponse httpResponse, boolean z) {
        this.mOrigRequest = kscHttpRequest.getRequest();
        this.mResponse = httpResponse;
        IKscDecoder decoder = kscHttpRequest.getDecoder();
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            if (z) {
                httpResponse.setEntity(KscHttpEntity.getRepeatableEntity(entity, decoder, this.mCache));
            } else if (decoder != null) {
                httpResponse.setEntity(new KscHttpEntity(entity, decoder));
            }
        }
    }

    void setError(Throwable th) {
        this.mError = th;
    }

    public Throwable getError() {
        return this.mError;
    }

    public HttpResponse getResponse() {
        return this.mResponse;
    }

    public InputStream getContent() throws IllegalStateException, IOException {
        HttpResponse httpResponse = this.mResponse;
        HttpEntity entity = httpResponse == null ? null : httpResponse.getEntity();
        if (entity == null) {
            return null;
        }
        InputStream content = entity.getContent();
        Header contentEncoding = entity.getContentEncoding();
        String value = contentEncoding != null ? contentEncoding.getValue() : null;
        return (value == null || !value.contains("gzip")) ? content : new GZIPInputStream(content);
    }

    public int getStatusCode() {
        StatusLine statusLine;
        HttpResponse httpResponse = this.mResponse;
        if (httpResponse == null || (statusLine = httpResponse.getStatusLine()) == null) {
            return -1;
        }
        return statusLine.getStatusCode();
    }

    public void release() throws IOException {
        HttpResponse httpResponse = this.mResponse;
        if (httpResponse == null || httpResponse.getEntity() == null) {
            return;
        }
        try {
            try {
                this.mResponse.getEntity().consumeContent();
                this.mResponse = null;
            } catch (IOException e) {
                throw e;
            } catch (Exception e2) {
                Log.w(LOG_TAG, "Meet exception when release a KscHttpResponse.", e2);
                this.mResponse = null;
            }
        } catch (Throwable th) {
            this.mResponse = null;
            throw th;
        }
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        List<HttpMessage> list = this.mMessages;
        int i = 0;
        if (list != null) {
            int i2 = 0;
            for (HttpMessage httpMessage : list) {
                if (httpMessage instanceof HttpRequest) {
                    sb.append("[Request " + i2 + "]\n");
                    sb.append(HttpUtils.toString((HttpRequest) httpMessage));
                    i2++;
                } else if (httpMessage instanceof HttpResponse) {
                    sb.append("[Response " + i + "]\n");
                    sb.append(HttpUtils.toString((HttpResponse) httpMessage));
                    i++;
                }
            }
        }
        if (sb.length() <= 0) {
            sb.append("[Origin Request]\n");
            sb.append(HttpUtils.toString(this.mOrigRequest));
        }
        sb.append("\n[Response " + i + "]\n");
        sb.append(HttpUtils.toString(this.mResponse));
        if (this.mError != null) {
            sb.append("\n[Error]\n");
            sb.append(Log.getStackTraceString(this.mError));
        }
        return sb.toString().replaceAll("password=.*&", "password=[secData]&");
    }
}
