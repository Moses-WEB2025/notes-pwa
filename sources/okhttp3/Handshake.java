package okhttp3;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.api.ah;
import defpackage.R2;
import java.io.IOException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal._UtilJvmKt;

/* JADX INFO: compiled from: Handshake.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 '2\u00020\u0001:\u0001'B;\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n¢\u0006\u0004\b\u000b\u0010\fJ\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u0013J\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b\u0014J\u0013\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007¢\u0006\u0002\b\u0015J\u000f\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0007¢\u0006\u0002\b\u0019J\u0013\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007¢\u0006\u0002\b\u001aJ\u000f\u0010\u001b\u001a\u0004\u0018\u00010\u0017H\u0007¢\u0006\u0002\b\u001cJ\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020#H\u0016R\u0013\u0010\u0002\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\rR\u0013\u0010\u0004\u001a\u00020\u00058G¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000eR\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078G¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000fR!\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u00078GX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u00178G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u00178G¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0018R\u0018\u0010$\u001a\u00020#*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&¨\u0006("}, d2 = {"Lokhttp3/Handshake;", "", "tlsVersion", "Lokhttp3/TlsVersion;", "cipherSuite", "Lokhttp3/CipherSuite;", "localCertificates", "", "Ljava/security/cert/Certificate;", "peerCertificatesFn", "Lkotlin/Function0;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/TlsVersion;Lokhttp3/CipherSuite;Ljava/util/List;Lkotlin/jvm/functions/Function0;)V", "()Lokhttp3/TlsVersion;", "()Lokhttp3/CipherSuite;", "()Ljava/util/List;", "peerCertificates", "peerCertificates$delegate", "Lkotlin/Lazy;", "-deprecated_tlsVersion", "-deprecated_cipherSuite", "-deprecated_peerCertificates", "peerPrincipal", "Ljava/security/Principal;", "()Ljava/security/Principal;", "-deprecated_peerPrincipal", "-deprecated_localCertificates", "localPrincipal", "-deprecated_localPrincipal", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "name", "getName", "(Ljava/security/cert/Certificate;)Ljava/lang/String;", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Handshake {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CipherSuite cipherSuite;
    private final List<Certificate> localCertificates;

    /* JADX INFO: renamed from: peerCertificates$delegate, reason: from kotlin metadata */
    private final Lazy peerCertificates;
    private final TlsVersion tlsVersion;

    @JvmStatic
    public static final Handshake get(SSLSession sSLSession) throws IOException {
        return INSTANCE.get(sSLSession);
    }

    @JvmStatic
    public static final Handshake get(TlsVersion tlsVersion, CipherSuite cipherSuite, List<? extends Certificate> list, List<? extends Certificate> list2) {
        return INSTANCE.get(tlsVersion, cipherSuite, list, list2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Handshake(TlsVersion tlsVersion, CipherSuite cipherSuite, List<? extends Certificate> localCertificates, final Function0<? extends List<? extends Certificate>> peerCertificatesFn) {
        Intrinsics.checkNotNullParameter(tlsVersion, "tlsVersion");
        Intrinsics.checkNotNullParameter(cipherSuite, "cipherSuite");
        Intrinsics.checkNotNullParameter(localCertificates, "localCertificates");
        Intrinsics.checkNotNullParameter(peerCertificatesFn, "peerCertificatesFn");
        this.tlsVersion = tlsVersion;
        this.cipherSuite = cipherSuite;
        this.localCertificates = localCertificates;
        this.peerCertificates = LazyKt.lazy(new Function0() { // from class: okhttp3.Handshake$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Handshake.peerCertificates_delegate$lambda$0(peerCertificatesFn);
            }
        });
    }

    public final TlsVersion tlsVersion() {
        return this.tlsVersion;
    }

    public final CipherSuite cipherSuite() {
        return this.cipherSuite;
    }

    public final List<Certificate> localCertificates() {
        return this.localCertificates;
    }

    public final List<Certificate> peerCertificates() {
        return (List) this.peerCertificates.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List peerCertificates_delegate$lambda$0(Function0 function0) {
        try {
            return (List) function0.invoke();
        } catch (SSLPeerUnverifiedException unused) {
            return CollectionsKt.emptyList();
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "tlsVersion", imports = {}))
    /* JADX INFO: renamed from: -deprecated_tlsVersion, reason: not valid java name and from getter */
    public final TlsVersion getTlsVersion() {
        return this.tlsVersion;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cipherSuite", imports = {}))
    /* JADX INFO: renamed from: -deprecated_cipherSuite, reason: not valid java name and from getter */
    public final CipherSuite getCipherSuite() {
        return this.cipherSuite;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "peerCertificates", imports = {}))
    /* JADX INFO: renamed from: -deprecated_peerCertificates, reason: not valid java name */
    public final List<Certificate> m4413deprecated_peerCertificates() {
        return peerCertificates();
    }

    public final Principal peerPrincipal() {
        Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) peerCertificates());
        X509Certificate x509Certificate = objFirstOrNull instanceof X509Certificate ? (X509Certificate) objFirstOrNull : null;
        return x509Certificate != null ? x509Certificate.getSubjectX500Principal() : null;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "peerPrincipal", imports = {}))
    /* JADX INFO: renamed from: -deprecated_peerPrincipal, reason: not valid java name */
    public final Principal m4414deprecated_peerPrincipal() {
        return peerPrincipal();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "localCertificates", imports = {}))
    /* JADX INFO: renamed from: -deprecated_localCertificates, reason: not valid java name */
    public final List<Certificate> m4411deprecated_localCertificates() {
        return this.localCertificates;
    }

    public final Principal localPrincipal() {
        Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) this.localCertificates);
        X509Certificate x509Certificate = objFirstOrNull instanceof X509Certificate ? (X509Certificate) objFirstOrNull : null;
        return x509Certificate != null ? x509Certificate.getSubjectX500Principal() : null;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "localPrincipal", imports = {}))
    /* JADX INFO: renamed from: -deprecated_localPrincipal, reason: not valid java name */
    public final Principal m4412deprecated_localPrincipal() {
        return localPrincipal();
    }

    public boolean equals(Object other) {
        if (!(other instanceof Handshake)) {
            return false;
        }
        Handshake handshake = (Handshake) other;
        return handshake.tlsVersion == this.tlsVersion && Intrinsics.areEqual(handshake.cipherSuite, this.cipherSuite) && Intrinsics.areEqual(handshake.peerCertificates(), peerCertificates()) && Intrinsics.areEqual(handshake.localCertificates, this.localCertificates);
    }

    public int hashCode() {
        return ((((((R2.attr.buttonBackground + this.tlsVersion.hashCode()) * 31) + this.cipherSuite.hashCode()) * 31) + peerCertificates().hashCode()) * 31) + this.localCertificates.hashCode();
    }

    public String toString() {
        List<Certificate> listPeerCertificates = peerCertificates();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPeerCertificates, 10));
        Iterator<T> it = listPeerCertificates.iterator();
        while (it.hasNext()) {
            arrayList.add(getName((Certificate) it.next()));
        }
        StringBuilder sbAppend = new StringBuilder("Handshake{tlsVersion=").append(this.tlsVersion).append(" cipherSuite=").append(this.cipherSuite).append(" peerCertificates=").append(arrayList.toString()).append(" localCertificates=");
        List<Certificate> list = this.localCertificates;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList2.add(getName((Certificate) it2.next()));
        }
        return sbAppend.append(arrayList2).append(CoreConstants.CURLY_RIGHT).toString();
    }

    private final String getName(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            return ((X509Certificate) certificate).getSubjectDN().toString();
        }
        String type = certificate.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return type;
    }

    /* JADX INFO: compiled from: Handshake.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0011\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0007¢\u0006\u0002\b\u0007J\u0015\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\tJ4\u0010\u0007\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0007¨\u0006\u0012"}, d2 = {"Lokhttp3/Handshake$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", ah.R, "Lokhttp3/Handshake;", "Ljavax/net/ssl/SSLSession;", CallMethod.METHOD_GET, "sslSession", "-deprecated_get", "tlsVersion", "Lokhttp3/TlsVersion;", "cipherSuite", "Lokhttp3/CipherSuite;", "peerCertificates", "", "Ljava/security/cert/Certificate;", "localCertificates", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List get$lambda$3(List list) {
            return list;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List handshake$lambda$2(List list) {
            return list;
        }

        private Companion() {
        }

        @JvmStatic
        public final Handshake get(SSLSession sSLSession) throws IOException {
            final List listEmptyList;
            Intrinsics.checkNotNullParameter(sSLSession, "<this>");
            String cipherSuite = sSLSession.getCipherSuite();
            if (cipherSuite == null) {
                throw new IllegalStateException("cipherSuite == null".toString());
            }
            if (Intrinsics.areEqual(cipherSuite, "TLS_NULL_WITH_NULL_NULL") || Intrinsics.areEqual(cipherSuite, "SSL_NULL_WITH_NULL_NULL")) {
                throw new IOException("cipherSuite == " + cipherSuite);
            }
            CipherSuite cipherSuiteForJavaName = CipherSuite.INSTANCE.forJavaName(cipherSuite);
            String protocol = sSLSession.getProtocol();
            if (protocol == null) {
                throw new IllegalStateException("tlsVersion == null".toString());
            }
            if (Intrinsics.areEqual("NONE", protocol)) {
                throw new IOException("tlsVersion == NONE");
            }
            TlsVersion tlsVersionForJavaName = TlsVersion.INSTANCE.forJavaName(protocol);
            try {
                listEmptyList = _UtilJvmKt.toImmutableList(sSLSession.getPeerCertificates());
            } catch (SSLPeerUnverifiedException unused) {
                listEmptyList = CollectionsKt.emptyList();
            }
            return new Handshake(tlsVersionForJavaName, cipherSuiteForJavaName, _UtilJvmKt.toImmutableList(sSLSession.getLocalCertificates()), new Function0() { // from class: okhttp3.Handshake$Companion$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Handshake.Companion.handshake$lambda$2(listEmptyList);
                }
            });
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "sslSession.handshake()", imports = {}))
        /* JADX INFO: renamed from: -deprecated_get, reason: not valid java name */
        public final Handshake m4417deprecated_get(SSLSession sslSession) throws IOException {
            Intrinsics.checkNotNullParameter(sslSession, "sslSession");
            return get(sslSession);
        }

        @JvmStatic
        public final Handshake get(TlsVersion tlsVersion, CipherSuite cipherSuite, List<? extends Certificate> peerCertificates, List<? extends Certificate> localCertificates) {
            Intrinsics.checkNotNullParameter(tlsVersion, "tlsVersion");
            Intrinsics.checkNotNullParameter(cipherSuite, "cipherSuite");
            Intrinsics.checkNotNullParameter(peerCertificates, "peerCertificates");
            Intrinsics.checkNotNullParameter(localCertificates, "localCertificates");
            final List immutableList = _UtilJvmKt.toImmutableList(peerCertificates);
            return new Handshake(tlsVersion, cipherSuite, _UtilJvmKt.toImmutableList(localCertificates), new Function0() { // from class: okhttp3.Handshake$Companion$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Handshake.Companion.get$lambda$3(immutableList);
                }
            });
        }
    }
}
