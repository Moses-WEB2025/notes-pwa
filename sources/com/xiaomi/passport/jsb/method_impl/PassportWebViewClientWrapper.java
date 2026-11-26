package com.xiaomi.passport.jsb.method_impl;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class PassportWebViewClientWrapper extends WebViewClient {
    private static final String TAG = "PassportWebViewClient";
    private WebViewClient mBaseWebViewClient;

    public void setBaseWebViewClient(WebViewClient webViewClient) {
        this.mBaseWebViewClient = webViewClient;
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        AccountLogger.log(TAG, "shouldOverrideUrlLoading: url=" + str);
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            return webViewClient.shouldOverrideUrlLoading(webView, str);
        }
        return super.shouldOverrideUrlLoading(webView, str);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        if (this.mBaseWebViewClient != null) {
            return this.mBaseWebViewClient.shouldOverrideUrlLoading(webView, webResourceRequest);
        }
        return super.shouldOverrideUrlLoading(webView, webResourceRequest);
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        AccountLogger.log(TAG, "onPageStarted: url=" + str);
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onPageStarted(webView, str, bitmap);
        } else {
            super.onPageStarted(webView, str, bitmap);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        AccountLogger.log(TAG, "onPageFinished: url=" + str);
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onPageFinished(webView, str);
        } else {
            super.onPageFinished(webView, str);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onLoadResource(WebView webView, String str) {
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onLoadResource(webView, str);
        } else {
            super.onLoadResource(webView, str);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onPageCommitVisible(WebView webView, String str) {
        AccountLogger.log(TAG, "onPageCommitVisible: url=" + str);
        if (this.mBaseWebViewClient != null) {
            this.mBaseWebViewClient.onPageCommitVisible(webView, str);
        } else {
            super.onPageCommitVisible(webView, str);
        }
    }

    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            return webViewClient.shouldInterceptRequest(webView, str);
        }
        return super.shouldInterceptRequest(webView, str);
    }

    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        if (this.mBaseWebViewClient != null) {
            return this.mBaseWebViewClient.shouldInterceptRequest(webView, webResourceRequest);
        }
        return super.shouldInterceptRequest(webView, webResourceRequest);
    }

    @Override // android.webkit.WebViewClient
    public void onTooManyRedirects(WebView webView, Message message, Message message2) {
        AccountLogger.log(TAG, "onTooManyRedirects: cancelMsg=" + message + ", continueMsg=" + message2);
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onTooManyRedirects(webView, message, message2);
        } else {
            super.onTooManyRedirects(webView, message, message2);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        AccountLogger.log(TAG, "onReceivedError: errorCode=" + i + ", description=" + str + ", url=" + str2);
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onReceivedError(webView, i, str, str2);
        } else {
            super.onReceivedError(webView, i, str, str2);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        if (this.mBaseWebViewClient != null) {
            this.mBaseWebViewClient.onReceivedError(webView, webResourceRequest, webResourceError);
        } else {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        AccountLogger.log(TAG, "onReceivedHttpError: request=" + webResourceRequest.getUrl() + ", error=" + webResourceResponse.getStatusCode());
        if (this.mBaseWebViewClient != null) {
            this.mBaseWebViewClient.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        } else {
            super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onFormResubmission(WebView webView, Message message, Message message2) {
        AccountLogger.log(TAG, "onFormResubmission: dontResend=" + message + ", resend=" + message2);
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onFormResubmission(webView, message, message2);
        } else {
            super.onFormResubmission(webView, message, message2);
        }
    }

    @Override // android.webkit.WebViewClient
    public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
        AccountLogger.log(TAG, "doUpdateVisitedHistory: url=" + str + ", isReload=" + z);
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.doUpdateVisitedHistory(webView, str, z);
        } else {
            super.doUpdateVisitedHistory(webView, str, z);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        AccountLogger.log(TAG, "onReceivedSslError: error=" + sslError.getPrimaryError() + ", url=" + sslError.getUrl());
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onReceivedSslError(webView, sslErrorHandler, sslError);
        } else {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedClientCertRequest(WebView webView, ClientCertRequest clientCertRequest) {
        AccountLogger.log(TAG, "onReceivedClientCertRequest: request=" + clientCertRequest.getHost() + ":" + clientCertRequest.getPort() + " -> " + String.join(",", clientCertRequest.getKeyTypes()));
        if (this.mBaseWebViewClient != null) {
            this.mBaseWebViewClient.onReceivedClientCertRequest(webView, clientCertRequest);
        } else {
            super.onReceivedClientCertRequest(webView, clientCertRequest);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        AccountLogger.log(TAG, "onReceivedHttpAuthRequest: host=" + str + ", realm=" + str2);
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
        } else {
            super.onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
        }
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            return webViewClient.shouldOverrideKeyEvent(webView, keyEvent);
        }
        return super.shouldOverrideKeyEvent(webView, keyEvent);
    }

    @Override // android.webkit.WebViewClient
    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onUnhandledKeyEvent(webView, keyEvent);
        } else {
            super.onUnhandledKeyEvent(webView, keyEvent);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onScaleChanged(WebView webView, float f, float f2) {
        AccountLogger.log(TAG, "onScaleChanged: oldScale=" + f + ", newScale=" + f2);
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onScaleChanged(webView, f, f2);
        } else {
            super.onScaleChanged(webView, f, f2);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
        AccountLogger.log(TAG, "onReceivedLoginRequest: realm=" + str + ", account=" + str2 + ", args=" + str3);
        WebViewClient webViewClient = this.mBaseWebViewClient;
        if (webViewClient != null) {
            webViewClient.onReceivedLoginRequest(webView, str, str2, str3);
        } else {
            super.onReceivedLoginRequest(webView, str, str2, str3);
        }
    }

    @Override // android.webkit.WebViewClient
    public boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail renderProcessGoneDetail) {
        AccountLogger.log(TAG, "onRenderProcessGone: crashed=" + renderProcessGoneDetail.didCrash());
        if (this.mBaseWebViewClient != null) {
            return this.mBaseWebViewClient.onRenderProcessGone(webView, renderProcessGoneDetail);
        }
        super.onRenderProcessGone(webView, renderProcessGoneDetail);
        if (renderProcessGoneDetail.didCrash()) {
            return false;
        }
        if (webView == null) {
            return true;
        }
        ((ViewGroup) webView.getParent()).removeView(webView);
        webView.destroy();
        return true;
    }

    @Override // android.webkit.WebViewClient
    public void onSafeBrowsingHit(WebView webView, WebResourceRequest webResourceRequest, int i, SafeBrowsingResponse safeBrowsingResponse) {
        AccountLogger.log(TAG, "onReceivedLoginRequest: request=" + webResourceRequest.getUrl() + ", threatType=" + i);
        if (this.mBaseWebViewClient != null) {
            this.mBaseWebViewClient.onSafeBrowsingHit(webView, webResourceRequest, i, safeBrowsingResponse);
        } else {
            super.onSafeBrowsingHit(webView, webResourceRequest, i, safeBrowsingResponse);
        }
    }
}
