package cn.kuaipan.kss.implement;

import cn.kuaipan.kss.KssDef;
import java.net.URI;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

/* JADX INFO: loaded from: classes.dex */
public class HttpClientProxyHelper {
    private int m_proxyIndex = -1;
    private String[] m_ProxyURLs = null;

    public void init(String[] strArr) {
        this.m_ProxyURLs = strArr;
        this.m_proxyIndex = -1;
    }

    public boolean isUsingProxy() {
        return this.m_proxyIndex > -1;
    }

    public boolean nextProxy() {
        String[] strArr = this.m_ProxyURLs;
        if (strArr == null) {
            return false;
        }
        int i = this.m_proxyIndex;
        int i2 = i + 1;
        this.m_proxyIndex = i2;
        if (i2 < strArr.length) {
            return true;
        }
        this.m_proxyIndex = i;
        return false;
    }

    public String getCurrentProxy() {
        int i;
        String[] strArr = this.m_ProxyURLs;
        if (strArr != null && (i = this.m_proxyIndex) >= 0 && i < strArr.length) {
            return strArr[i];
        }
        return null;
    }

    public boolean reset() {
        this.m_proxyIndex = -1;
        return true;
    }

    public HttpGet createGet(URI uri) {
        if (!isUsingProxy()) {
            return new HttpGet(uri);
        }
        HttpGet httpGet = new HttpGet(getCurrentProxy());
        httpGet.addHeader(KssDef.KEY_DESTURL, uri.toASCIIString());
        return httpGet;
    }

    public HttpGet createGet(String str) {
        if (!isUsingProxy()) {
            return new HttpGet(str);
        }
        HttpGet httpGet = new HttpGet(getCurrentProxy());
        httpGet.addHeader(KssDef.KEY_DESTURL, str);
        return httpGet;
    }

    public HttpPost createPost(URI uri) {
        if (!isUsingProxy()) {
            return new HttpPost(uri);
        }
        HttpPost httpPost = new HttpPost(getCurrentProxy());
        httpPost.addHeader(KssDef.KEY_DESTURL, uri.toASCIIString());
        return httpPost;
    }
}
