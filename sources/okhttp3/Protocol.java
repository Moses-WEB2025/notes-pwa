package okhttp3;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: Protocol.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u0000 \u000e2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000eB\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\r\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\u000f"}, d2 = {"Lokhttp3/Protocol;", "", "protocol", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;ILjava/lang/String;)V", "HTTP_1_0", "HTTP_1_1", "SPDY_3", "HTTP_2", "H2_PRIOR_KNOWLEDGE", "QUIC", "HTTP_3", "toString", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public enum Protocol {
    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1"),
    HTTP_2("h2"),
    H2_PRIOR_KNOWLEDGE("h2_prior_knowledge"),
    QUIC("quic"),
    HTTP_3("h3");

    private final String protocol;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final Protocol get(String str) throws IOException {
        return INSTANCE.get(str);
    }

    public static EnumEntries<Protocol> getEntries() {
        return $ENTRIES;
    }

    Protocol(String str) {
        this.protocol = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.protocol;
    }

    /* JADX INFO: compiled from: Protocol.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"}, d2 = {"Lokhttp3/Protocol$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", CallMethod.METHOD_GET, "Lokhttp3/Protocol;", "protocol", "", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Protocol get(String protocol) throws IOException {
            Intrinsics.checkNotNullParameter(protocol, "protocol");
            if (Intrinsics.areEqual(protocol, Protocol.HTTP_1_0.protocol)) {
                return Protocol.HTTP_1_0;
            }
            if (Intrinsics.areEqual(protocol, Protocol.HTTP_1_1.protocol)) {
                return Protocol.HTTP_1_1;
            }
            if (Intrinsics.areEqual(protocol, Protocol.H2_PRIOR_KNOWLEDGE.protocol)) {
                return Protocol.H2_PRIOR_KNOWLEDGE;
            }
            if (Intrinsics.areEqual(protocol, Protocol.HTTP_2.protocol)) {
                return Protocol.HTTP_2;
            }
            if (Intrinsics.areEqual(protocol, Protocol.SPDY_3.protocol)) {
                return Protocol.SPDY_3;
            }
            if (Intrinsics.areEqual(protocol, Protocol.QUIC.protocol)) {
                return Protocol.QUIC;
            }
            if (StringsKt.startsWith$default(protocol, Protocol.HTTP_3.protocol, false, 2, (Object) null)) {
                return Protocol.HTTP_3;
            }
            throw new IOException("Unexpected protocol: " + protocol);
        }
    }
}
