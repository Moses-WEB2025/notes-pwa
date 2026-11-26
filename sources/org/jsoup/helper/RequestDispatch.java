package org.jsoup.helper;

import java.lang.reflect.Constructor;
import org.jsoup.internal.SharedConstants;

/* JADX INFO: loaded from: classes6.dex */
class RequestDispatch {
    static Constructor<RequestExecutor> clientConstructor;

    RequestDispatch() {
    }

    static {
        try {
            clientConstructor = Class.forName("org.jsoup.helper.HttpClientExecutor").getConstructor(HttpConnection.Request.class, HttpConnection.Response.class);
        } catch (Exception unused) {
        }
    }

    static RequestExecutor get(HttpConnection.Request request, HttpConnection.Response response) {
        Constructor<RequestExecutor> constructor;
        if (Boolean.parseBoolean(System.getProperty(SharedConstants.UseHttpClient, "true")) && (constructor = clientConstructor) != null) {
            try {
                return constructor.newInstance(request, response);
            } catch (Exception unused) {
                return new UrlConnectionExecutor(request, response);
            }
        }
        return new UrlConnectionExecutor(request, response);
    }
}
