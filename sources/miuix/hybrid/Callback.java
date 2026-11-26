package miuix.hybrid;

import miuix.internal.hybrid.HybridManager;

/* JADX INFO: loaded from: classes5.dex */
public class Callback {
    private String mJsCallback;
    private HybridManager mManager;
    private PageContext mPageContext;

    public Callback(HybridManager hybridManager, PageContext pageContext, String str) {
        this.mManager = hybridManager;
        this.mPageContext = pageContext;
        this.mJsCallback = str;
    }

    public void callback(Response response) {
        this.mManager.callback(response, this.mPageContext, this.mJsCallback);
    }
}
