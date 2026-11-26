package miuix.hybrid.feature;

import android.os.Build;
import android.util.Log;
import java.util.Locale;
import java.util.Map;
import miuix.hybrid.HybridFeature;
import miuix.hybrid.Request;
import miuix.hybrid.Response;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes5.dex */
public class Device implements HybridFeature {
    private static final String ACTION_GET_DEVICE_INFO = "getDeviceInfo";
    private static final String KEY_DEVICE_ID = "deviceId";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_MODEL = "model";
    private static final String KEY_REGION = "region";
    private static final String KEY_ROM_VERSION = "romVersion";
    private static final String LOG_TAG = "Device";

    @Override // miuix.hybrid.HybridFeature
    public void setParams(Map<String, String> map) {
    }

    @Override // miuix.hybrid.HybridFeature
    public Response invoke(Request request) {
        if (ACTION_GET_DEVICE_INFO.equals(request.getAction())) {
            return getDeviceInfo();
        }
        return new Response(204, "no such action");
    }

    @Override // miuix.hybrid.HybridFeature
    public HybridFeature.Mode getInvocationMode(Request request) {
        if (ACTION_GET_DEVICE_INFO.equals(request.getAction())) {
            return HybridFeature.Mode.SYNC;
        }
        return null;
    }

    private Response getDeviceInfo() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("model", Build.MODEL);
            jSONObject.put(KEY_ROM_VERSION, Build.VERSION.RELEASE);
            jSONObject.put(KEY_LANGUAGE, Locale.getDefault().getLanguage());
            jSONObject.put("region", Locale.getDefault().getCountry());
            return new Response(jSONObject);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
            return new Response(200, e.getMessage());
        }
    }
}
