package miuix.hybrid.feature;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
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
public class Network implements HybridFeature {
    private static final String ACTION_DISABLE_NOTIFICATION = "disableNotification";
    private static final String ACTION_ENABLE_NOTIFICATION = "enableNotification";
    private static final String ACTION_GET_TYPE = "getType";
    private static final String KEY_CONNECTED = "connected";
    private static final String KEY_METERED = "metered";
    private static final String LOG_TAG = "Network";
    private Callback mCallback;
    private IntentFilter mFilter;
    private LifecycleListener mLifecycleListener;
    private NetworkStateReceiver mReceiver;

    @Override // miuix.hybrid.HybridFeature
    public void setParams(Map<String, String> map) {
    }

    private class NetworkStateReceiver extends BroadcastReceiver {
        private NetworkStateReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                boolean z = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED;
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("connected", z);
                    Network.this.mCallback.callback(new Response(jSONObject));
                } catch (JSONException e) {
                    Log.e(Network.LOG_TAG, e.getMessage());
                }
            }
        }
    }

    public Network() {
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    }

    @Override // miuix.hybrid.HybridFeature
    public Response invoke(Request request) {
        String action = request.getAction();
        if (ACTION_GET_TYPE.equals(action)) {
            return isMetered(request);
        }
        if (ACTION_ENABLE_NOTIFICATION.equals(action)) {
            return enableNotification(request);
        }
        if (ACTION_DISABLE_NOTIFICATION.equals(action)) {
            return disableNotification(request);
        }
        return new Response(204, "no such action");
    }

    private Response isMetered(Request request) {
        boolean zIsActiveNetworkMetered = ((ConnectivityManager) request.getNativeInterface().getActivity().getSystemService("connectivity")).isActiveNetworkMetered();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEY_METERED, zIsActiveNetworkMetered);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return new Response(jSONObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterNotification(NativeInterface nativeInterface) {
        if (this.mReceiver != null) {
            Activity activity = nativeInterface.getActivity();
            nativeInterface.removeLifecycleListener(this.mLifecycleListener);
            activity.unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    private Response enableNotification(Request request) {
        final NativeInterface nativeInterface = request.getNativeInterface();
        unregisterNotification(nativeInterface);
        Activity activity = nativeInterface.getActivity();
        this.mCallback = request.getCallback();
        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        this.mReceiver = networkStateReceiver;
        activity.registerReceiver(networkStateReceiver, this.mFilter);
        LifecycleListener lifecycleListener = new LifecycleListener() { // from class: miuix.hybrid.feature.Network.1
            @Override // miuix.hybrid.LifecycleListener
            public void onPageChange() {
                unregister();
            }

            @Override // miuix.hybrid.LifecycleListener
            public void onDestroy() {
                unregister();
            }

            private void unregister() {
                Network.this.unregisterNotification(nativeInterface);
                Network.this.mCallback.callback(new Response(100));
            }
        };
        this.mLifecycleListener = lifecycleListener;
        nativeInterface.addLifecycleListener(lifecycleListener);
        return null;
    }

    private Response disableNotification(Request request) {
        unregisterNotification(request.getNativeInterface());
        return new Response(100);
    }

    @Override // miuix.hybrid.HybridFeature
    public HybridFeature.Mode getInvocationMode(Request request) {
        String action = request.getAction();
        if (ACTION_GET_TYPE.equals(action)) {
            return HybridFeature.Mode.SYNC;
        }
        if (ACTION_ENABLE_NOTIFICATION.equals(action)) {
            return HybridFeature.Mode.CALLBACK;
        }
        if (ACTION_DISABLE_NOTIFICATION.equals(action)) {
            return HybridFeature.Mode.SYNC;
        }
        return null;
    }
}
