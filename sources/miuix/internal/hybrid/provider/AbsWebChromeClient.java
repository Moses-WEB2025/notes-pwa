package miuix.internal.hybrid.provider;

import android.net.Uri;
import miuix.hybrid.FileChooserParams;
import miuix.hybrid.GeolocationPermissions;
import miuix.hybrid.HybridChromeClient;
import miuix.hybrid.HybridView;
import miuix.hybrid.JsResult;
import miuix.hybrid.ValueCallback;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbsWebChromeClient {
    protected HybridChromeClient mHybridChromeClient;
    protected HybridView mHybridView;

    public Object getWebChromeClient() {
        return null;
    }

    public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
    }

    public boolean onJsAlert(HybridView hybridView, String str, String str2, JsResult jsResult) {
        return false;
    }

    public boolean onJsConfirm(HybridView hybridView, String str, String str2, JsResult jsResult) {
        return false;
    }

    public void onProgressChanged(HybridView hybridView, int i) {
    }

    public void onReceivedTitle(HybridView hybridView, String str) {
    }

    public boolean onShowFileChooser(HybridView hybridView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
        return false;
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
    }

    public AbsWebChromeClient(HybridChromeClient hybridChromeClient, HybridView hybridView) {
        this.mHybridChromeClient = hybridChromeClient;
        this.mHybridView = hybridView;
    }
}
