package miuix.hybrid.feature;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import com.miui.common.stat.ToDoStat;
import java.util.Map;
import miuix.hybrid.Callback;
import miuix.hybrid.HybridFeature;
import miuix.hybrid.LifecycleListener;
import miuix.hybrid.NativeInterface;
import miuix.hybrid.Request;
import miuix.hybrid.Response;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes5.dex */
public class Share implements HybridFeature {
    private static final String ACTION_SEND = "send";
    private static final String PARAM_DATA = "data";
    private static final String PARAM_TYPE = "type";
    private static final String TAG = "HybridShare";

    @Override // miuix.hybrid.HybridFeature
    public void setParams(Map<String, String> map) {
    }

    @Override // miuix.hybrid.HybridFeature
    public Response invoke(Request request) {
        if (ACTION_SEND.equals(request.getAction())) {
            return invokeShareTo(request);
        }
        return new Response(204, "no such action");
    }

    private Response invokeShareTo(Request request) {
        final NativeInterface nativeInterface = request.getNativeInterface();
        Activity activity = nativeInterface.getActivity();
        final Callback callback = request.getCallback();
        nativeInterface.addLifecycleListener(new LifecycleListener() { // from class: miuix.hybrid.feature.Share.1
            @Override // miuix.hybrid.LifecycleListener
            public void onActivityResult(int i, int i2, Intent intent) {
                Response response;
                nativeInterface.removeLifecycleListener(this);
                if (i2 == -1) {
                    response = new Response(0, "success");
                } else if (i2 == 0) {
                    response = new Response(100, ToDoStat.CANCEL);
                } else {
                    response = new Response(200);
                }
                callback.callback(response);
            }
        });
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        String rawParams = request.getRawParams();
        try {
            JSONObject jSONObject = new JSONObject(rawParams);
            String string = jSONObject.getString("type");
            String string2 = jSONObject.getString("data");
            if (string != null && string2 != null) {
                intent.setType(string);
                if (string.startsWith("text/")) {
                    intent.putExtra("android.intent.extra.TEXT", string2);
                } else {
                    intent.putExtra("android.intent.extra.STREAM", string2);
                }
                activity.startActivityForResult(intent, 1);
                return null;
            }
            callback.callback(new Response(200, "no data to share"));
            return null;
        } catch (JSONException unused) {
            Log.i(TAG, "invalid JSON string:" + rawParams);
            callback.callback(new Response(200, "invalid data to share"));
        }
    }

    @Override // miuix.hybrid.HybridFeature
    public HybridFeature.Mode getInvocationMode(Request request) {
        return HybridFeature.Mode.CALLBACK;
    }
}
