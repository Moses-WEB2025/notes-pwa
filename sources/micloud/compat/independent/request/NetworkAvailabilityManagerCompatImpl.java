package micloud.compat.independent.request;

import android.content.Context;

/* JADX INFO: loaded from: classes5.dex */
class NetworkAvailabilityManagerCompatImpl implements INetworkAvailabilityManagerCompat {
    @Override // micloud.compat.independent.request.INetworkAvailabilityManagerCompat
    public boolean getAvailability(Context context) {
        return true;
    }

    @Override // micloud.compat.independent.request.INetworkAvailabilityManagerCompat
    public void setAvailability(Context context, boolean z) {
    }

    NetworkAvailabilityManagerCompatImpl() {
    }
}
