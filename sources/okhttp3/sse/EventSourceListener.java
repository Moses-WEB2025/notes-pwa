package okhttp3.sse;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.onetrack.api.ah;
import com.xiaomi.verificationsdk.internal.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Response;

/* JADX INFO: compiled from: EventSourceListener.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0000\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J,\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\fH\u0016J\u0010\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J$\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016¨\u0006\u0013"}, d2 = {"Lokhttp3/sse/EventSourceListener;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onOpen", "", "eventSource", "Lokhttp3/sse/EventSource;", ah.H, "Lokhttp3/Response;", "onEvent", "id", "", "type", "data", "onClosed", "onFailure", Constants.TIMESTAMP, "", "okhttp-sse"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class EventSourceListener {
    public void onClosed(EventSource eventSource) {
        Intrinsics.checkNotNullParameter(eventSource, "eventSource");
    }

    public void onEvent(EventSource eventSource, String id, String type, String data) {
        Intrinsics.checkNotNullParameter(eventSource, "eventSource");
        Intrinsics.checkNotNullParameter(data, "data");
    }

    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        Intrinsics.checkNotNullParameter(eventSource, "eventSource");
    }

    public void onOpen(EventSource eventSource, Response response) {
        Intrinsics.checkNotNullParameter(eventSource, "eventSource");
        Intrinsics.checkNotNullParameter(response, "response");
    }
}
