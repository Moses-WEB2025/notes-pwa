package cn.kuaipan.android.http.client;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: classes.dex */
public class KscRedirectHandler extends DefaultRedirectHandler {
    @Override // org.apache.http.impl.client.DefaultRedirectHandler, org.apache.http.client.RedirectHandler
    public URI getLocationURI(HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
        URI locationURI = super.getLocationURI(httpResponse, httpContext);
        List linkedList = (List) httpContext.getAttribute(KscHttpClient.KSC_MESSAGE_LIST);
        if (linkedList == null) {
            linkedList = new LinkedList();
            httpContext.setAttribute(KscHttpClient.KSC_MESSAGE_LIST, linkedList);
        }
        linkedList.add(httpResponse);
        return locationURI;
    }
}
