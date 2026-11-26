package com.miui.common.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NetworkCallbackImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0002\u0016\u0017B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/miui/common/tool/NetworkCallbackImpl;", "Landroid/net/ConnectivityManager$NetworkCallback;", "mNetworkCall", "Lcom/miui/common/tool/NetworkCallbackImpl$NetworkCall;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/common/tool/NetworkCallbackImpl$NetworkCall;)V", "value", "", "isNetWorkAvailable", "()Z", "initNetWorkState", "", "onAvailable", "network", "Landroid/net/Network;", "onLost", "onCapabilitiesChanged", "networkCapabilities", "Landroid/net/NetworkCapabilities;", "isNetworkAvailable", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "NetworkCall", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    private static final String TAG = "NetworkCallbackImpl";
    private boolean isNetWorkAvailable;
    private final NetworkCall mNetworkCall;

    /* JADX INFO: compiled from: NetworkCallbackImpl.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&¨\u0006\u0005À\u0006\u0003"}, d2 = {"Lcom/miui/common/tool/NetworkCallbackImpl$NetworkCall;", "", "onAvailable", "", "onLost", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface NetworkCall {
        void onAvailable();

        void onLost();
    }

    public NetworkCallbackImpl(NetworkCall mNetworkCall) {
        Intrinsics.checkNotNullParameter(mNetworkCall, "mNetworkCall");
        this.mNetworkCall = mNetworkCall;
    }

    /* JADX INFO: renamed from: isNetWorkAvailable, reason: from getter */
    public final boolean getIsNetWorkAvailable() {
        return this.isNetWorkAvailable;
    }

    public final void initNetWorkState() {
        this.isNetWorkAvailable = isNetworkAvailable(BaseNoteApp.INSTANCE.getInstance());
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onAvailable(Network network) {
        Intrinsics.checkNotNullParameter(network, "network");
        Logger.INSTANCE.e(TAG, "onAvailable: Net is OK");
        super.onAvailable(network);
        this.isNetWorkAvailable = true;
        this.mNetworkCall.onAvailable();
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onLost(Network network) {
        Intrinsics.checkNotNullParameter(network, "network");
        Logger.INSTANCE.e(TAG, "onLost: Net is disconnect");
        super.onLost(network);
        this.isNetWorkAvailable = false;
        this.mNetworkCall.onLost();
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        Intrinsics.checkNotNullParameter(network, "network");
        Intrinsics.checkNotNullParameter(networkCapabilities, "networkCapabilities");
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(16)) {
            if (networkCapabilities.hasTransport(1)) {
                Logger.INSTANCE.d(TAG, "onCapabilitiesChanged: Network type is wifi");
            } else if (networkCapabilities.hasTransport(0)) {
                Logger.INSTANCE.d(TAG, "onCapabilitiesChanged:  Network type is CELLULAR");
            } else {
                Logger.INSTANCE.d(TAG, "onCapabilitiesChanged: Network type is OTHER");
            }
        }
    }

    private final boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        Intrinsics.checkNotNullExpressionValue(allNetworkInfo, "getAllNetworkInfo(...)");
        for (NetworkInfo networkInfo : allNetworkInfo) {
            Intrinsics.checkNotNull(networkInfo);
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
