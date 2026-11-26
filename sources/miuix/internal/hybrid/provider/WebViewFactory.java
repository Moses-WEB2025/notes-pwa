package miuix.internal.hybrid.provider;

import android.content.Context;
import android.util.Log;
import miuix.internal.hybrid.HybridManager;
import miuix.internal.hybrid.webkit.WebkitFactoryProvider;

/* JADX INFO: loaded from: classes5.dex */
public class WebViewFactory {
    private static final String META_DATA_KEY = "com.miui.sdk.hybrid.webview";
    private static WebViewFactoryProvider sProviderInstance;
    private static final Object sProviderLock = new Object();

    public static WebViewFactoryProvider getProvider(Context context) {
        synchronized (sProviderLock) {
            WebViewFactoryProvider webViewFactoryProvider = sProviderInstance;
            if (webViewFactoryProvider != null) {
                return webViewFactoryProvider;
            }
            sProviderInstance = new WebkitFactoryProvider();
            if (Log.isLoggable(HybridManager.TAG, 3)) {
                Log.d(HybridManager.TAG, "loaded provider:" + sProviderInstance);
            }
            return sProviderInstance;
        }
    }
}
