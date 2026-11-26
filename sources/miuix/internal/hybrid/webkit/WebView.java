package miuix.internal.hybrid.webkit;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import miuix.hybrid.HybridBackForwardList;
import miuix.hybrid.HybridSettings;
import miuix.hybrid.HybridView;
import miuix.internal.hybrid.provider.AbsWebChromeClient;
import miuix.internal.hybrid.provider.AbsWebView;
import miuix.internal.hybrid.provider.AbsWebViewClient;

/* JADX INFO: loaded from: classes5.dex */
public class WebView extends AbsWebView {
    protected android.webkit.WebView mWebView;

    public WebView(Context context, HybridView hybridView) {
        super(context, hybridView);
        this.mWebView = new android.webkit.WebView(this.mContext);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public View getBaseWebView() {
        return this.mWebView;
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public void setWebViewClient(AbsWebViewClient absWebViewClient) {
        Object webViewClient;
        if (absWebViewClient == null || (webViewClient = absWebViewClient.getWebViewClient()) == null) {
            return;
        }
        this.mWebView.setWebViewClient((android.webkit.WebViewClient) webViewClient);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public void setWebChromeClient(AbsWebChromeClient absWebChromeClient) {
        Object webChromeClient;
        if (absWebChromeClient == null || (webChromeClient = absWebChromeClient.getWebChromeClient()) == null) {
            return;
        }
        this.mWebView.setWebChromeClient((android.webkit.WebChromeClient) webChromeClient);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public void loadUrl(String str) {
        this.mWebView.loadUrl(str);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public void addJavascriptInterface(Object obj, String str) {
        this.mWebView.addJavascriptInterface(obj, str);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public HybridSettings getSettings() {
        return new WebSettings(this.mWebView.getSettings());
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public void destroy() {
        this.mWebView.destroy();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public void reload() {
        this.mWebView.reload();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public void clearCache(boolean z) {
        this.mWebView.clearCache(z);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public boolean canGoBack() {
        return this.mWebView.canGoBack();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public boolean canGoForward() {
        return this.mWebView.canGoForward();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public void goBack() {
        this.mWebView.goBack();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public String getUrl() {
        return this.mWebView.getUrl();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public String getTitle() {
        return this.mWebView.getTitle();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public int getContentHeight() {
        return this.mWebView.getContentHeight();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public float getScale() {
        return this.mWebView.getScale();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public Context getContext() {
        return this.mWebView.getContext();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public void setVisibility(int i) {
        this.mWebView.setVisibility(i);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public View getRootView() {
        return this.mWebView.getRootView();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public HybridBackForwardList copyBackForwardList() {
        return new WebBackForwardList(this.mWebView.copyBackForwardList());
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public void draw(Canvas canvas) {
        this.mWebView.draw(canvas);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public android.webkit.WebBackForwardList saveState(Bundle bundle) {
        return this.mWebView.saveState(bundle);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebView
    public android.webkit.WebBackForwardList restoreState(Bundle bundle) {
        return this.mWebView.restoreState(bundle);
    }
}
