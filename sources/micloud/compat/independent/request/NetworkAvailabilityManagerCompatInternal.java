package micloud.compat.independent.request;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.xiaomi.micloudsdk.Intents;
import com.xiaomi.micloudsdk.provider.MiCloudSettings;

/* JADX INFO: loaded from: classes5.dex */
public class NetworkAvailabilityManagerCompatInternal implements INetworkAvailabilityManagerCompat {
    private static final int FALSE_INT = 0;
    private static final String TAG = "NetworkAvailabilityManager";
    private static final int TRUE_INT = 1;

    @Override // micloud.compat.independent.request.INetworkAvailabilityManagerCompat
    public boolean getAvailability(Context context) {
        if (context != null) {
            return MiCloudSettings.getInt(context.getContentResolver(), "micloud_network_availability", 0) != 0;
        }
        throw new IllegalArgumentException("context cannot be null");
    }

    @Override // micloud.compat.independent.request.INetworkAvailabilityManagerCompat
    public void setAvailability(Context context, boolean z) {
        if (context == null) {
            Log.d(TAG, "context is null, ignore");
            return;
        }
        boolean availability = getAvailability(context);
        if (availability != z) {
            MiCloudSettings.putInt(context.getContentResolver(), "micloud_network_availability", z ? 1 : 0);
            sendNetworkAvailabilityChangedBroadcast(context, z);
            Log.d(TAG, "micloud network state changed from " + availability + " to " + z);
        }
    }

    private void sendNetworkAvailabilityChangedBroadcast(Context context, boolean z) {
        Intent intent = new Intent(Intents.ACTION_MICLOUD_NETWORK_AVAILABILITY_CHANGED);
        intent.putExtra("active", z);
        context.sendBroadcast(intent);
        Log.d(TAG, "sendNetworkAvailabilityChangedBroadcast availability: " + z);
    }
}
