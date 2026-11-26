package com.xiaomi.phonenum.http;

import android.text.TextUtils;
import com.xiaomi.phonenum.utils.MapUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class Request {
    public final boolean followRedirects;
    public final Map<String, String> formBody;
    public final Map<String, String> headers;
    public final URI uri;
    public final String url;

    private Request(Builder builder) {
        this.uri = builder.uri;
        this.url = builder.uri.toString();
        this.headers = builder.headers;
        this.formBody = builder.formBody;
        this.followRedirects = builder.followRedirects;
    }

    public static class Builder {
        boolean followRedirects = true;
        Map<String, String> formBody;
        Map<String, String> headers;
        URI uri;

        public Builder url(String str) {
            try {
                this.uri = new URI(str);
                return this;
            } catch (URISyntaxException unused) {
                throw new IllegalArgumentException("unexpected url: " + str);
            }
        }

        public Builder appendQuery(String str) {
            if (TextUtils.isEmpty(str)) {
                return this;
            }
            URI uri = this.uri;
            String query = uri.getQuery();
            try {
                this.uri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), query == null ? str : query + "&" + str, uri.getFragment());
                return this;
            } catch (URISyntaxException unused) {
                throw new IllegalArgumentException("unexpected newQuery: " + str);
            }
        }

        public Builder queryParams(Map<String, String> map) {
            appendQuery(MapUtil.joinToQuery(map));
            return this;
        }

        public Builder ua(String str) {
            if (this.headers == null) {
                this.headers = new HashMap();
            }
            this.headers.put("User-Agent", str);
            return this;
        }

        public Builder headers(Map<String, String> map) {
            this.headers = map;
            return this;
        }

        public Builder formBody(Map<String, String> map) {
            this.formBody = map;
            return this;
        }

        public Builder followRedirects(boolean z) {
            this.followRedirects = z;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
