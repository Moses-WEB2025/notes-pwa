package miuix.internal.hybrid.webkit;

import android.content.Context;
import miuix.hybrid.CookieManager;
import miuix.hybrid.HybridChromeClient;
import miuix.hybrid.HybridView;
import miuix.hybrid.HybridViewClient;
import miuix.internal.hybrid.provider.AbsCookieSyncManager;
import miuix.internal.hybrid.provider.AbsWebChromeClient;
import miuix.internal.hybrid.provider.AbsWebView;
import miuix.internal.hybrid.provider.AbsWebViewClient;
import miuix.internal.hybrid.provider.WebViewFactoryProvider;

/* JADX INFO: loaded from: classes5.dex */
public class WebkitFactoryProvider extends WebViewFactoryProvider {
    private CookieManager mCookieManager;
    private AbsCookieSyncManager mCookieSyncManager;

    @Override // miuix.internal.hybrid.provider.WebViewFactoryProvider
    public AbsWebView createWebView(Context context, HybridView hybridView) {
        return new WebView(context, hybridView);
    }

    @Override // miuix.internal.hybrid.provider.WebViewFactoryProvider
    public AbsWebViewClient createWebViewClient(HybridViewClient hybridViewClient, HybridView hybridView) {
        return new WebViewClient(hybridViewClient, hybridView);
    }

    @Override // miuix.internal.hybrid.provider.WebViewFactoryProvider
    public AbsWebChromeClient createWebChromeClient(HybridChromeClient hybridChromeClient, HybridView hybridView) {
        return new WebChromeClient(hybridChromeClient, hybridView);
    }

    @Override // miuix.internal.hybrid.provider.WebViewFactoryProvider
    public CookieManager getCookieManager() {
        if (this.mCookieManager == null) {
            this.mCookieManager = new CookieManagerAdapter(android.webkit.CookieManager.getInstance());
        }
        return this.mCookieManager;
    }

    @Override // miuix.internal.hybrid.provider.WebViewFactoryProvider
    public AbsCookieSyncManager getCookieSyncManager() {
        if (this.mCookieSyncManager == null) {
            this.mCookieSyncManager = new CookieSyncManagerDelegate();
        }
        return this.mCookieSyncManager;
    }
}
