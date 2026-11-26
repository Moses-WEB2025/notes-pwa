package okhttp3.internal.http;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.onetrack.api.ah;
import java.io.IOException;
import kotlin.Metadata;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.connection.RealCall;
import okio.Sink;
import okio.Source;

/* JADX INFO: compiled from: ExchangeCodec.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 \u001f2\u00020\u0001:\u0002\u001e\u001fJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\fH&J\b\u0010\u0011\u001a\u00020\u0010H&J\b\u0010\u0012\u001a\u00020\u0010H&J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0007H&J\u0010\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u0018H&J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u0018H&J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH&J\b\u0010\u001d\u001a\u00020\u0010H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b¨\u0006 À\u0006\u0003"}, d2 = {"Lokhttp3/internal/http/ExchangeCodec;", "", "carrier", "Lokhttp3/internal/http/ExchangeCodec$Carrier;", "getCarrier", "()Lokhttp3/internal/http/ExchangeCodec$Carrier;", "isResponseComplete", "", "()Z", "createRequestBody", "Lokio/Sink;", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "contentLength", "", "writeRequestHeaders", "", "flushRequest", "finishRequest", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "reportedContentLength", ah.H, "Lokhttp3/Response;", "openResponseBodySource", "Lokio/Source;", "peekTrailers", "Lokhttp3/Headers;", ToDoStat.CANCEL, "Carrier", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface ExchangeCodec {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;

    /* JADX INFO: compiled from: ExchangeCodec.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH&J\b\u0010\f\u001a\u00020\u0007H&J\b\u0010\r\u001a\u00020\u0007H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u000eÀ\u0006\u0003"}, d2 = {"Lokhttp3/internal/http/ExchangeCodec$Carrier;", "", "route", "Lokhttp3/Route;", "getRoute", "()Lokhttp3/Route;", "trackFailure", "", "call", "Lokhttp3/internal/connection/RealCall;", "e", "Ljava/io/IOException;", "noNewExchanges", ToDoStat.CANCEL, "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Carrier {
        /* JADX INFO: renamed from: cancel */
        void mo4513cancel();

        Route getRoute();

        void noNewExchanges();

        void trackFailure(RealCall call, IOException e);
    }

    void cancel();

    Sink createRequestBody(Request request, long contentLength) throws IOException;

    void finishRequest() throws IOException;

    void flushRequest() throws IOException;

    Carrier getCarrier();

    boolean isResponseComplete();

    Source openResponseBodySource(Response response) throws IOException;

    Headers peekTrailers() throws IOException;

    Response.Builder readResponseHeaders(boolean expectContinue) throws IOException;

    long reportedContentLength(Response response) throws IOException;

    void writeRequestHeaders(Request request) throws IOException;

    /* JADX INFO: compiled from: ExchangeCodec.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lokhttp3/internal/http/ExchangeCodec$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "DISCARD_STREAM_TIMEOUT_MILLIS", "", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;

        private Companion() {
        }
    }
}
