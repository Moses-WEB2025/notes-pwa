package okhttp3.internal.proxy;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: NullProxySelector.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J&\u0010\t\u001a\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lokhttp3/internal/proxy/NullProxySelector;", "Ljava/net/ProxySelector;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", SmartAction.Feature.SELECT, "", "Ljava/net/Proxy;", CallMethod.ARG_URI, "Ljava/net/URI;", "connectFailed", "", "sa", "Ljava/net/SocketAddress;", "ioe", "Ljava/io/IOException;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NullProxySelector extends ProxySelector {
    public static final NullProxySelector INSTANCE = new NullProxySelector();

    @Override // java.net.ProxySelector
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
    }

    private NullProxySelector() {
    }

    @Override // java.net.ProxySelector
    public List<Proxy> select(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri must not be null".toString());
        }
        return CollectionsKt.listOf(Proxy.NO_PROXY);
    }
}
