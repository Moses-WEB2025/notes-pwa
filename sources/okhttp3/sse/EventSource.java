package okhttp3.sse;

import com.miui.common.stat.ToDoStat;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import kotlin.Metadata;
import okhttp3.Request;

/* JADX INFO: compiled from: EventSource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0006J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lokhttp3/sse/EventSource;", "", StatConstants.Event.REQUEST, "Lokhttp3/Request;", ToDoStat.CANCEL, "", "Factory", "okhttp-sse"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface EventSource {

    /* JADX INFO: compiled from: EventSource.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lokhttp3/sse/EventSource$Factory;", "", "newEventSource", "Lokhttp3/sse/EventSource;", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "listener", "Lokhttp3/sse/EventSourceListener;", "okhttp-sse"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Factory {
        EventSource newEventSource(Request request, EventSourceListener listener);
    }

    void cancel();

    Request request();
}
