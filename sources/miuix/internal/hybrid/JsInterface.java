package miuix.internal.hybrid;

import android.util.Log;
import android.webkit.JavascriptInterface;

/* JADX INFO: loaded from: classes5.dex */
public class JsInterface {
    public static final String INTERFACE_NAME = "MiuiJsBridge";
    private HybridManager mManager;

    public JsInterface(HybridManager hybridManager) {
        this.mManager = hybridManager;
    }

    @JavascriptInterface
    public String config(String str) {
        String strConfig = this.mManager.config(str);
        if (Log.isLoggable(HybridManager.TAG, 3)) {
            Log.d(HybridManager.TAG, "config response is " + strConfig);
        }
        return strConfig;
    }

    @JavascriptInterface
    public String lookup(String str, String str2) {
        String strLookup = this.mManager.lookup(str, str2);
        if (Log.isLoggable(HybridManager.TAG, 3)) {
            Log.d(HybridManager.TAG, "lookup response is " + strLookup);
        }
        return strLookup;
    }

    @JavascriptInterface
    public String invoke(String str, String str2, String str3, String str4) {
        String strInvoke = this.mManager.invoke(str, str2, str3, str4);
        if (Log.isLoggable(HybridManager.TAG, 3)) {
            Log.d(HybridManager.TAG, "blocking response is " + strInvoke);
        }
        return strInvoke;
    }
}
