package miuix.internal.hybrid.provider;

import android.graphics.Bitmap;
import android.net.http.SslError;
import miuix.hybrid.HybridResourceResponse;
import miuix.hybrid.HybridView;
import miuix.hybrid.HybridViewClient;
import miuix.hybrid.SslErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbsWebViewClient {
    protected HybridView mHybridView;
    protected HybridViewClient mHybridViewClient;

    public Object getWebViewClient() {
        return null;
    }

    public void onPageFinished(HybridView hybridView, String str) {
    }

    public void onPageStarted(HybridView hybridView, String str, Bitmap bitmap) {
    }

    public void onReceivedError(HybridView hybridView, int i, String str, String str2) {
    }

    public void onReceivedLoginRequest(HybridView hybridView, String str, String str2, String str3) {
    }

    public void onReceivedSslError(HybridView hybridView, SslErrorHandler sslErrorHandler, SslError sslError) {
    }

    public HybridResourceResponse shouldInterceptRequest(HybridView hybridView, String str) {
        return null;
    }

    public boolean shouldOverrideUrlLoading(HybridView hybridView, String str) {
        return false;
    }

    public AbsWebViewClient(HybridViewClient hybridViewClient, HybridView hybridView) {
        this.mHybridViewClient = hybridViewClient;
        this.mHybridView = hybridView;
    }
}
