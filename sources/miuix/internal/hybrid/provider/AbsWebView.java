package miuix.internal.hybrid.provider;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebBackForwardList;
import miuix.hybrid.HybridBackForwardList;
import miuix.hybrid.HybridSettings;
import miuix.hybrid.HybridView;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbsWebView {
    protected Context mContext;
    protected HybridView mHybridView;

    public void addJavascriptInterface(Object obj, String str) {
    }

    public boolean canGoBack() {
        return false;
    }

    public boolean canGoForward() {
        return false;
    }

    public void clearCache(boolean z) {
    }

    public HybridBackForwardList copyBackForwardList() {
        return null;
    }

    public void destroy() {
    }

    public void draw(Canvas canvas) {
    }

    public View getBaseWebView() {
        return null;
    }

    public int getContentHeight() {
        return 0;
    }

    public Context getContext() {
        return null;
    }

    public View getRootView() {
        return null;
    }

    public float getScale() {
        return 1.0f;
    }

    public HybridSettings getSettings() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public void goBack() {
    }

    public void loadUrl(String str) {
    }

    public void reload() {
    }

    public WebBackForwardList restoreState(Bundle bundle) {
        return null;
    }

    public WebBackForwardList saveState(Bundle bundle) {
        return null;
    }

    public void setVisibility(int i) {
    }

    public void setWebChromeClient(AbsWebChromeClient absWebChromeClient) {
    }

    public void setWebViewClient(AbsWebViewClient absWebViewClient) {
    }

    public AbsWebView(Context context, HybridView hybridView) {
        this.mContext = context;
        this.mHybridView = hybridView;
    }
}
