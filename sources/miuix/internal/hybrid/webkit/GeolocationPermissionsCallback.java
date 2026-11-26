package miuix.internal.hybrid.webkit;

import miuix.hybrid.GeolocationPermissions;

/* JADX INFO: loaded from: classes5.dex */
public class GeolocationPermissionsCallback implements GeolocationPermissions.Callback {
    private android.webkit.GeolocationPermissions.Callback mCallback;

    public GeolocationPermissionsCallback(android.webkit.GeolocationPermissions.Callback callback) {
        this.mCallback = callback;
    }

    @Override // miuix.hybrid.GeolocationPermissions.Callback
    public void invoke(String str, boolean z, boolean z2) {
        this.mCallback.invoke(str, z, z2);
    }
}
