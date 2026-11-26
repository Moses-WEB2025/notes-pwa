package miuix.internal.webkit;

import miuix.internal.hybrid.webkit.WebView;

/* JADX INFO: loaded from: classes5.dex */
public class WebViewWrapper extends WebView {
    public WebViewWrapper(android.webkit.WebView webView) {
        super(webView.getContext(), null);
        this.mWebView = webView;
    }
}
