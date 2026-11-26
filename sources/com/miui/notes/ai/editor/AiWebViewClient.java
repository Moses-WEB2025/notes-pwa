package com.miui.notes.ai.editor;

import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* JADX INFO: loaded from: classes4.dex */
public class AiWebViewClient extends WebViewClient {
    private onRenderProcessGoneListener mOnRenderProcessGoneListener;

    public interface onRenderProcessGoneListener {
        void onRenderProcessExit(WebView webView);
    }

    @Override // android.webkit.WebViewClient
    public boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail renderProcessGoneDetail) {
        onRenderProcessGoneListener onrenderprocessgonelistener = this.mOnRenderProcessGoneListener;
        if (onrenderprocessgonelistener == null) {
            return true;
        }
        onrenderprocessgonelistener.onRenderProcessExit(webView);
        return true;
    }

    public void setOnRenderProcessGoneListener(onRenderProcessGoneListener onrenderprocessgonelistener) {
        this.mOnRenderProcessGoneListener = onrenderprocessgonelistener;
    }
}
