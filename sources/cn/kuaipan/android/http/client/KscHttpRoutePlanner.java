package cn.kuaipan.android.http.client;

import cn.kuaipan.android.utils.NetworkHelpers;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.DefaultHttpRoutePlanner;
import org.apache.http.params.AbstractHttpParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: classes.dex */
public class KscHttpRoutePlanner extends DefaultHttpRoutePlanner {
    public KscHttpRoutePlanner(SchemeRegistry schemeRegistry) {
        super(schemeRegistry);
    }

    @Override // org.apache.http.impl.conn.DefaultHttpRoutePlanner, org.apache.http.conn.routing.HttpRoutePlanner
    public HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        HttpHost currentProxy = NetworkHelpers.getCurrentProxy();
        if (currentProxy != null) {
            KscHttpParams kscHttpParams = new KscHttpParams(httpRequest.getParams());
            ConnRouteParams.setDefaultProxy(kscHttpParams, currentProxy);
            httpRequest.setParams(kscHttpParams);
        }
        return super.determineRoute(httpHost, httpRequest, httpContext);
    }

    private class KscHttpParams extends AbstractHttpParams {
        private final HttpParams mExtParams;
        private final HttpParams mOrgParams;

        public KscHttpParams(HttpParams httpParams) {
            this.mOrgParams = httpParams;
            this.mExtParams = new BasicHttpParams();
        }

        private KscHttpParams(HttpParams httpParams, HttpParams httpParams2) {
            this.mOrgParams = httpParams;
            this.mExtParams = httpParams2;
        }

        @Override // org.apache.http.params.HttpParams
        public Object getParameter(String str) {
            Object parameter = this.mExtParams.getParameter(str);
            return parameter == null ? this.mOrgParams.getParameter(str) : parameter;
        }

        @Override // org.apache.http.params.HttpParams
        public HttpParams setParameter(String str, Object obj) {
            this.mExtParams.setParameter(str, obj);
            return this;
        }

        @Override // org.apache.http.params.HttpParams
        public HttpParams copy() {
            return KscHttpRoutePlanner.this.new KscHttpParams(this.mOrgParams, this.mExtParams.copy());
        }

        @Override // org.apache.http.params.HttpParams
        public boolean removeParameter(String str) {
            boolean zRemoveParameter = this.mExtParams.removeParameter(str);
            if (!zRemoveParameter) {
                try {
                    return this.mOrgParams.removeParameter(str);
                } catch (Exception unused) {
                }
            }
            return zRemoveParameter;
        }
    }
}
