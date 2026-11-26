package miuix.internal.hybrid.webkit;

import android.net.Uri;
import miuix.hybrid.GeolocationPermissions;
import miuix.hybrid.HybridChromeClient;
import miuix.hybrid.HybridView;
import miuix.internal.hybrid.provider.AbsWebChromeClient;

/* JADX INFO: loaded from: classes5.dex */
public class WebChromeClient extends AbsWebChromeClient {
    public WebChromeClient(HybridChromeClient hybridChromeClient, HybridView hybridView) {
        super(hybridChromeClient, hybridView);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebChromeClient
    public Object getWebChromeClient() {
        return new InternalWebChromeClient();
    }

    @Override // miuix.internal.hybrid.provider.AbsWebChromeClient
    public boolean onJsAlert(HybridView hybridView, String str, String str2, miuix.hybrid.JsResult jsResult) {
        return this.mHybridChromeClient.onJsAlert(hybridView, str, str2, jsResult);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebChromeClient
    public boolean onJsConfirm(HybridView hybridView, String str, String str2, miuix.hybrid.JsResult jsResult) {
        return this.mHybridChromeClient.onJsConfirm(hybridView, str, str2, jsResult);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebChromeClient
    public void onProgressChanged(HybridView hybridView, int i) {
        this.mHybridChromeClient.onProgressChanged(hybridView, i);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebChromeClient
    public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
        this.mHybridChromeClient.onGeolocationPermissionsShowPrompt(str, callback);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebChromeClient
    public void onReceivedTitle(HybridView hybridView, String str) {
        this.mHybridChromeClient.onReceivedTitle(hybridView, str);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebChromeClient
    public void openFileChooser(miuix.hybrid.ValueCallback<Uri> valueCallback, String str, String str2) {
        this.mHybridChromeClient.openFileChooser(valueCallback, str, str2);
    }

    @Override // miuix.internal.hybrid.provider.AbsWebChromeClient
    public boolean onShowFileChooser(HybridView hybridView, miuix.hybrid.ValueCallback<Uri[]> valueCallback, miuix.hybrid.FileChooserParams fileChooserParams) {
        return this.mHybridChromeClient.onShowFileChooser(hybridView, valueCallback, fileChooserParams);
    }

    class InternalWebChromeClient extends android.webkit.WebChromeClient {
        InternalWebChromeClient() {
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsAlert(android.webkit.WebView webView, String str, String str2, android.webkit.JsResult jsResult) {
            JsResult jsResult2 = new JsResult(jsResult);
            WebChromeClient webChromeClient = WebChromeClient.this;
            return webChromeClient.onJsAlert(webChromeClient.mHybridView, str, str2, jsResult2);
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsConfirm(android.webkit.WebView webView, String str, String str2, android.webkit.JsResult jsResult) {
            JsResult jsResult2 = new JsResult(jsResult);
            WebChromeClient webChromeClient = WebChromeClient.this;
            return webChromeClient.onJsConfirm(webChromeClient.mHybridView, str, str2, jsResult2);
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(android.webkit.WebView webView, int i) {
            WebChromeClient webChromeClient = WebChromeClient.this;
            webChromeClient.onProgressChanged(webChromeClient.mHybridView, i);
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsShowPrompt(String str, android.webkit.GeolocationPermissions.Callback callback) {
            WebChromeClient.this.onGeolocationPermissionsShowPrompt(str, new GeolocationPermissionsCallback(callback));
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTitle(android.webkit.WebView webView, String str) {
            WebChromeClient webChromeClient = WebChromeClient.this;
            webChromeClient.onReceivedTitle(webChromeClient.mHybridView, str);
        }

        @Override // android.webkit.WebChromeClient
        public boolean onShowFileChooser(android.webkit.WebView webView, android.webkit.ValueCallback<Uri[]> valueCallback, android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
            ValueCallback valueCallback2 = new ValueCallback(valueCallback);
            FileChooserParams fileChooserParams2 = new FileChooserParams(fileChooserParams);
            WebChromeClient webChromeClient = WebChromeClient.this;
            return webChromeClient.onShowFileChooser(webChromeClient.mHybridView, valueCallback2, fileChooserParams2);
        }

        public void openFileChooser(android.webkit.ValueCallback<Uri> valueCallback, String str, String str2) {
            WebChromeClient.this.openFileChooser(new ValueCallback(valueCallback), str, str2);
        }
    }
}
