package okhttp3.sse;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.miai.SDKConstants;
import com.xiaomi.onetrack.api.ah;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.sse.internal.RealEventSource;

/* JADX INFO: compiled from: EventSources.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0007J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007¨\u0006\u0010"}, d2 = {"Lokhttp3/sse/EventSources;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createFactory", "Lokhttp3/sse/EventSource$Factory;", "client", "Lokhttp3/OkHttpClient;", "callFactory", "Lokhttp3/Call$Factory;", "processResponse", "", ah.H, "Lokhttp3/Response;", "listener", "Lokhttp3/sse/EventSourceListener;", "okhttp-sse"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EventSources {
    public static final EventSources INSTANCE = new EventSources();

    private EventSources() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "required for binary-compatibility!")
    @JvmStatic
    public static final /* synthetic */ EventSource.Factory createFactory(OkHttpClient client) {
        Intrinsics.checkNotNullParameter(client, "client");
        return createFactory((Call.Factory) client);
    }

    @JvmStatic
    public static final EventSource.Factory createFactory(final Call.Factory callFactory) {
        Intrinsics.checkNotNullParameter(callFactory, "callFactory");
        return new EventSource.Factory() { // from class: okhttp3.sse.EventSources$$ExternalSyntheticLambda0
            @Override // okhttp3.sse.EventSource.Factory
            public final EventSource newEventSource(Request request, EventSourceListener eventSourceListener) {
                return EventSources.createFactory$lambda$1(callFactory, request, eventSourceListener);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final EventSource createFactory$lambda$1(Call.Factory factory, Request request, EventSourceListener listener) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(listener, "listener");
        if (request.header("Accept") == null) {
            request = request.newBuilder().addHeader("Accept", SDKConstants.MimeType.EVENT_STREAM).build();
        }
        RealEventSource realEventSource = new RealEventSource(request, listener);
        realEventSource.connect(factory);
        return realEventSource;
    }

    @JvmStatic
    public static final void processResponse(Response response, EventSourceListener listener) {
        Intrinsics.checkNotNullParameter(response, "response");
        Intrinsics.checkNotNullParameter(listener, "listener");
        new RealEventSource(response.request(), listener).processResponse(response);
    }
}
