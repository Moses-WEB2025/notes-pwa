package org.jsoup.helper;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes6.dex */
abstract class RequestExecutor {
    final HttpConnection.Response prevRes;
    final HttpConnection.Request req;

    abstract HttpConnection.Response execute() throws IOException;

    abstract InputStream responseBody() throws IOException;

    abstract void safeClose();

    RequestExecutor(HttpConnection.Request request, HttpConnection.Response response) {
        this.req = request;
        this.prevRes = response;
    }
}
