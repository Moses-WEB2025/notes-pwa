package okhttp3.internal.platform;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.security.NetworkSecurityPolicy;
import android.util.CloseGuard;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.android.Android10SocketAdapter;
import okhttp3.internal.platform.android.AndroidCertificateChainCleaner;
import okhttp3.internal.platform.android.AndroidSocketAdapter;
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter;
import okhttp3.internal.platform.android.ConscryptSocketAdapter;
import okhttp3.internal.platform.android.DeferredSocketAdapter;
import okhttp3.internal.platform.android.SocketAdapter;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;

/* JADX INFO: compiled from: Android10Platform.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 )2\u00020\u00012\u00020\u0002:\u0001)B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J(\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\fH\u0016J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\u001bH\u0016J\u001a\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u001b2\b\u0010$\u001a\u0004\u0018\u00010 H\u0016J\u0010\u0010%\u001a\u00020&2\u0006\u0010\u001a\u001a\u00020\u001bH\u0017J\u0010\u0010'\u001a\u00020(2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lokhttp3/internal/platform/Android10Platform;", "Lokhttp3/internal/platform/Platform;", "Lokhttp3/internal/platform/ContextAwarePlatform;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "applicationContext", "Landroid/content/Context;", "getApplicationContext", "()Landroid/content/Context;", "setApplicationContext", "(Landroid/content/Context;)V", "socketAdapters", "", "Lokhttp3/internal/platform/android/SocketAdapter;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "buildTrustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "Lokhttp3/Protocol;", "getSelectedProtocol", "getStackTraceForCloseable", "", "closer", "logCloseableLeak", "message", "stackTrace", "isCleartextTrafficPermitted", "", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Android10Platform extends Platform implements ContextAwarePlatform {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final boolean isSupported = Platform.INSTANCE.isAndroid();
    private Context applicationContext;
    private final List<SocketAdapter> socketAdapters;

    public Android10Platform() {
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new SocketAdapter[]{Android10SocketAdapter.INSTANCE.buildIfSupported(), new DeferredSocketAdapter(AndroidSocketAdapter.INSTANCE.getPlayProviderFactory()), new DeferredSocketAdapter(ConscryptSocketAdapter.INSTANCE.getFactory()), new DeferredSocketAdapter(BouncyCastleSocketAdapter.INSTANCE.getFactory())});
        ArrayList arrayList = new ArrayList();
        for (Object obj : listListOfNotNull) {
            if (((SocketAdapter) obj).isSupported()) {
                arrayList.add(obj);
            }
        }
        this.socketAdapters = arrayList;
    }

    @Override // okhttp3.internal.platform.ContextAwarePlatform
    public Context getApplicationContext() {
        return this.applicationContext;
    }

    @Override // okhttp3.internal.platform.ContextAwarePlatform
    public void setApplicationContext(Context context) {
        this.applicationContext = context;
    }

    @Override // okhttp3.internal.platform.Platform
    public X509TrustManager trustManager(SSLSocketFactory sslSocketFactory) {
        Object next;
        Intrinsics.checkNotNullParameter(sslSocketFactory, "sslSocketFactory");
        Iterator<T> it = this.socketAdapters.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((SocketAdapter) next).matchesSocketFactory(sslSocketFactory));
        SocketAdapter socketAdapter = (SocketAdapter) next;
        if (socketAdapter != null) {
            return socketAdapter.trustManager(sslSocketFactory);
        }
        return null;
    }

    @Override // okhttp3.internal.platform.Platform
    public SSLContext newSSLContext() {
        StrictMode.noteSlowCall("newSSLContext");
        return super.newSSLContext();
    }

    @Override // okhttp3.internal.platform.Platform
    public TrustRootIndex buildTrustRootIndex(X509TrustManager trustManager) {
        Intrinsics.checkNotNullParameter(trustManager, "trustManager");
        StrictMode.noteSlowCall("buildTrustRootIndex");
        return super.buildTrustRootIndex(trustManager);
    }

    @Override // okhttp3.internal.platform.Platform
    public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<? extends Protocol> protocols) {
        Object next;
        Intrinsics.checkNotNullParameter(sslSocket, "sslSocket");
        Intrinsics.checkNotNullParameter(protocols, "protocols");
        Iterator<T> it = this.socketAdapters.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((SocketAdapter) next).matchesSocket(sslSocket));
        SocketAdapter socketAdapter = (SocketAdapter) next;
        if (socketAdapter != null) {
            socketAdapter.configureTlsExtensions(sslSocket, hostname, protocols);
        }
    }

    @Override // okhttp3.internal.platform.Platform
    public String getSelectedProtocol(SSLSocket sslSocket) {
        Object next;
        Intrinsics.checkNotNullParameter(sslSocket, "sslSocket");
        Iterator<T> it = this.socketAdapters.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((SocketAdapter) next).matchesSocket(sslSocket));
        SocketAdapter socketAdapter = (SocketAdapter) next;
        if (socketAdapter != null) {
            return socketAdapter.getSelectedProtocol(sslSocket);
        }
        return null;
    }

    @Override // okhttp3.internal.platform.Platform
    public Object getStackTraceForCloseable(String closer) {
        Intrinsics.checkNotNullParameter(closer, "closer");
        if (Build.VERSION.SDK_INT >= 30) {
            CloseGuard closeGuard = new CloseGuard();
            closeGuard.open(closer);
            return closeGuard;
        }
        return super.getStackTraceForCloseable(closer);
    }

    @Override // okhttp3.internal.platform.Platform
    public void logCloseableLeak(String message, Object stackTrace) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (Build.VERSION.SDK_INT >= 30) {
            Intrinsics.checkNotNull(stackTrace, "null cannot be cast to non-null type android.util.CloseGuard");
            ((CloseGuard) stackTrace).warnIfOpen();
        } else {
            super.logCloseableLeak(message, stackTrace);
        }
    }

    @Override // okhttp3.internal.platform.Platform
    public boolean isCleartextTrafficPermitted(String hostname) {
        Intrinsics.checkNotNullParameter(hostname, "hostname");
        return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(hostname);
    }

    @Override // okhttp3.internal.platform.Platform
    public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager trustManager) {
        Intrinsics.checkNotNullParameter(trustManager, "trustManager");
        AndroidCertificateChainCleaner androidCertificateChainCleanerBuildIfSupported = AndroidCertificateChainCleaner.INSTANCE.buildIfSupported(trustManager);
        return androidCertificateChainCleanerBuildIfSupported != null ? androidCertificateChainCleanerBuildIfSupported : super.buildCertificateChainCleaner(trustManager);
    }

    /* JADX INFO: compiled from: Android10Platform.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0007\u001a\u0004\u0018\u00010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0006¨\u0006\t"}, d2 = {"Lokhttp3/internal/platform/Android10Platform$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isSupported() {
            return Android10Platform.isSupported;
        }

        public final Platform buildIfSupported() {
            if (isSupported()) {
                return new Android10Platform();
            }
            return null;
        }
    }
}
