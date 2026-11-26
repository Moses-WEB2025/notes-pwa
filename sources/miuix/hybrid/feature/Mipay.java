package miuix.hybrid.feature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.Map;
import miuix.hybrid.HybridFeature;
import miuix.hybrid.LifecycleListener;
import miuix.hybrid.NativeInterface;
import miuix.hybrid.Request;
import miuix.hybrid.Response;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes5.dex */
public class Mipay implements HybridFeature {
    private static final String ACTION_MIPAY_COUNTER = "com.xiaomi.action.MIPAY_PAY_ORDER";
    private static final String ACTION_PAY = "pay";
    private static final int ERROR_CODE_INVALID = -1;
    private static final String KEY_CODE = "code";
    private static final String KEY_EXTRA = "extra";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ORDER = "order";
    private static final String KEY_ORDER_INFO = "orderInfo";
    private static final String KEY_RESULT = "result";
    private static final String PACKAGE_MIPAY_WALLET = "com.mipay.wallet";
    private static final int REQUEST_MIPAY = 20140424;

    @Override // miuix.hybrid.HybridFeature
    public void setParams(Map<String, String> map) {
    }

    @Override // miuix.hybrid.HybridFeature
    public Response invoke(final Request request) {
        String string;
        if (!TextUtils.equals(request.getAction(), ACTION_PAY)) {
            return new Response(204, "no such action");
        }
        final NativeInterface nativeInterface = request.getNativeInterface();
        Activity activity = nativeInterface.getActivity();
        nativeInterface.addLifecycleListener(new LifecycleListener() { // from class: miuix.hybrid.feature.Mipay.1
            @Override // miuix.hybrid.LifecycleListener
            public void onActivityResult(int i, int i2, Intent intent) {
                Response response;
                if (i == Mipay.REQUEST_MIPAY) {
                    nativeInterface.removeLifecycleListener(this);
                    if (i2 == -1) {
                        response = new Response(0, Mipay.this.makeResult(intent));
                    } else if (i2 == 0) {
                        response = new Response(100, Mipay.this.makeResult(intent));
                    } else {
                        response = new Response(200);
                    }
                    request.getCallback().callback(response);
                }
            }
        });
        try {
            string = new JSONObject(request.getRawParams()).getString(KEY_ORDER_INFO);
        } catch (JSONException e) {
            e.printStackTrace();
            string = null;
        }
        return pay(activity, string, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject makeResult(Intent intent) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", intent.getIntExtra("code", -1));
            String stringExtra = intent.getStringExtra("message");
            if (!TextUtils.isEmpty(stringExtra)) {
                jSONObject.put("message", stringExtra);
            }
            String stringExtra2 = intent.getStringExtra("result");
            if (!TextUtils.isEmpty(stringExtra2)) {
                jSONObject.put("result", stringExtra2);
            }
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    private Response pay(Activity activity, String str, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            return new Response(204, "order cannot be empty");
        }
        Intent intent = new Intent(ACTION_MIPAY_COUNTER);
        intent.setPackage(PACKAGE_MIPAY_WALLET);
        if (activity.getPackageManager().resolveActivity(intent, 0) == null) {
            return new Response(204, "mipay feature not available");
        }
        intent.putExtra("order", str);
        intent.putExtra("extra", bundle);
        activity.startActivityForResult(intent, REQUEST_MIPAY);
        return null;
    }

    @Override // miuix.hybrid.HybridFeature
    public HybridFeature.Mode getInvocationMode(Request request) {
        if (TextUtils.equals(request.getAction(), ACTION_PAY)) {
            return HybridFeature.Mode.CALLBACK;
        }
        return null;
    }
}
