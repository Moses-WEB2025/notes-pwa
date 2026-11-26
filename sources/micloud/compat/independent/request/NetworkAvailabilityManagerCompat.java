package micloud.compat.independent.request;

import android.content.Context;

/* JADX INFO: loaded from: classes5.dex */
public class NetworkAvailabilityManagerCompat {
    private static INetworkAvailabilityManagerCompat sNetworkAvailabilityManagerCompatImpl = new NetworkAvailabilityManagerCompatImpl();

    private NetworkAvailabilityManagerCompat() {
    }

    public static void setNetworkAvailabilityManagerCompatImpl(INetworkAvailabilityManagerCompat iNetworkAvailabilityManagerCompat) {
        sNetworkAvailabilityManagerCompatImpl = iNetworkAvailabilityManagerCompat;
    }

    public static void setAvailability(Context context, boolean z) {
        sNetworkAvailabilityManagerCompatImpl.setAvailability(context, z);
    }

    public static boolean getAvailability(Context context) {
        return sNetworkAvailabilityManagerCompatImpl.getAvailability(context);
    }
}
