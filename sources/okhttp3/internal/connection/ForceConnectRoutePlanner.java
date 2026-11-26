package okhttp3.internal.connection;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.HttpUrl;

/* JADX INFO: compiled from: ForceConnectRoutePlanner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0096\u0001J\t\u0010\f\u001a\u00020\tH\u0096\u0001J\u0011\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096\u0001R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0010\u001a\u00020\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0015X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u0018"}, d2 = {"Lokhttp3/internal/connection/ForceConnectRoutePlanner;", "Lokhttp3/internal/connection/RoutePlanner;", "delegate", "Lokhttp3/internal/connection/RealRoutePlanner;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/connection/RealRoutePlanner;)V", "plan", "Lokhttp3/internal/connection/RoutePlanner$Plan;", "hasNext", "", "failedConnection", "Lokhttp3/internal/connection/RealConnection;", "isCanceled", "sameHostAndPort", "url", "Lokhttp3/HttpUrl;", RemoteDeviceInfo.KEY_ADDRESS, "Lokhttp3/Address;", "getAddress", "()Lokhttp3/Address;", "deferredPlans", "Lkotlin/collections/ArrayDeque;", "getDeferredPlans", "()Lkotlin/collections/ArrayDeque;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ForceConnectRoutePlanner implements RoutePlanner {
    private final RealRoutePlanner delegate;

    @Override // okhttp3.internal.connection.RoutePlanner
    public Address getAddress() {
        return this.delegate.getAddress();
    }

    @Override // okhttp3.internal.connection.RoutePlanner
    public ArrayDeque<RoutePlanner.Plan> getDeferredPlans() {
        return this.delegate.getDeferredPlans();
    }

    @Override // okhttp3.internal.connection.RoutePlanner
    public boolean hasNext(RealConnection failedConnection) {
        return this.delegate.hasNext(failedConnection);
    }

    @Override // okhttp3.internal.connection.RoutePlanner
    public boolean isCanceled() {
        return this.delegate.isCanceled();
    }

    @Override // okhttp3.internal.connection.RoutePlanner
    public boolean sameHostAndPort(HttpUrl url) {
        Intrinsics.checkNotNullParameter(url, "url");
        return this.delegate.sameHostAndPort(url);
    }

    public ForceConnectRoutePlanner(RealRoutePlanner delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // okhttp3.internal.connection.RoutePlanner
    public RoutePlanner.Plan plan() {
        return this.delegate.planConnect$okhttp();
    }
}
