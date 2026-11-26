package okhttp3.sse.internal;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.onetrack.api.ah;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.UnreadableResponseBodyKt;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okio.Timeout;

/* JADX INFO: compiled from: RealEventSource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0018\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014J\f\u0010\u0016\u001a\u00020\r*\u00020\u0017H\u0002J\u0018\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u001b\u001a\u00020\u000fH\u0016J$\u0010\u001c\u001a\u00020\u000f2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\u0006\u0010 \u001a\u00020\u001eH\u0016J\u0010\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020#H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lokhttp3/sse/internal/RealEventSource;", "Lokhttp3/sse/EventSource;", "Lokhttp3/sse/internal/ServerSentEventReader$Callback;", "Lokhttp3/Callback;", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "listener", "Lokhttp3/sse/EventSourceListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/Request;Lokhttp3/sse/EventSourceListener;)V", "call", "Lokhttp3/Call;", "canceled", "", "connect", "", "callFactory", "Lokhttp3/Call$Factory;", "onResponse", ah.H, "Lokhttp3/Response;", "processResponse", "isEventStream", "Lokhttp3/ResponseBody;", "onFailure", "e", "Ljava/io/IOException;", ToDoStat.CANCEL, "onEvent", "id", "", "type", "data", "onRetryChange", "timeMs", "", "okhttp-sse"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RealEventSource implements EventSource, ServerSentEventReader.Callback, Callback {
    private Call call;
    private volatile boolean canceled;
    private final EventSourceListener listener;
    private final Request request;

    @Override // okhttp3.sse.internal.ServerSentEventReader.Callback
    public void onRetryChange(long timeMs) {
    }

    public RealEventSource(Request request, EventSourceListener listener) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.request = request;
        this.listener = listener;
    }

    public final void connect(Call.Factory callFactory) {
        Intrinsics.checkNotNullParameter(callFactory, "callFactory");
        Call callNewCall = callFactory.newCall(this.request);
        callNewCall.enqueue(this);
        this.call = callNewCall;
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
        processResponse(response);
    }

    public final void processResponse(Response response) {
        Timeout timeout;
        Intrinsics.checkNotNullParameter(response, "response");
        Response response2 = response;
        try {
            if (!response.getIsSuccessful()) {
                this.listener.onFailure(this, null, response);
                CloseableKt.closeFinally(response2, null);
                return;
            }
            ResponseBody responseBodyBody = response.body();
            if (!isEventStream(responseBodyBody)) {
                this.listener.onFailure(this, new IllegalStateException("Invalid content-type: " + responseBodyBody.getMediaType()), response);
                CloseableKt.closeFinally(response2, null);
                return;
            }
            Call call = this.call;
            if (call != null && (timeout = call.timeout()) != null) {
                timeout.cancel();
            }
            Response responseStripBody = UnreadableResponseBodyKt.stripBody(response);
            ServerSentEventReader serverSentEventReader = new ServerSentEventReader(responseBodyBody.getBodySource(), this);
            try {
                if (!this.canceled) {
                    this.listener.onOpen(this, responseStripBody);
                    while (!this.canceled && serverSentEventReader.processNextEvent()) {
                    }
                }
                if (this.canceled) {
                    this.listener.onFailure(this, new IOException("canceled"), responseStripBody);
                } else {
                    this.listener.onClosed(this);
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(response2, null);
            } catch (Exception e) {
                e = e;
                if (this.canceled) {
                    e = new IOException("canceled", e);
                }
                this.listener.onFailure(this, e, responseStripBody);
                CloseableKt.closeFinally(response2, null);
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(response2, th);
                throw th2;
            }
        }
    }

    private final boolean isEventStream(ResponseBody responseBody) {
        MediaType mediaType = responseBody.getMediaType();
        return mediaType != null && Intrinsics.areEqual(mediaType.type(), "text") && Intrinsics.areEqual(mediaType.subtype(), "event-stream");
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, IOException e) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(e, "e");
        this.listener.onFailure(this, e, null);
    }

    @Override // okhttp3.sse.EventSource
    /* JADX INFO: renamed from: request, reason: from getter */
    public Request getRequest() {
        return this.request;
    }

    @Override // okhttp3.sse.EventSource
    public void cancel() {
        this.canceled = true;
        Call call = this.call;
        if (call != null) {
            call.cancel();
        }
    }

    @Override // okhttp3.sse.internal.ServerSentEventReader.Callback
    public void onEvent(String id, String type, String data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.listener.onEvent(this, id, type, data);
    }
}
