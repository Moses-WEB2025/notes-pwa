package okhttp3;

import com.miui.common.stat.ToDoStat;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import java.io.IOException;
import kotlin.Metadata;
import okio.Timeout;

/* JADX INFO: compiled from: Call.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001\u0011J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u0007H&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\fH&J\b\u0010\u000e\u001a\u00020\u000fH&J\b\u0010\u0010\u001a\u00020\u0000H&¨\u0006\u0012À\u0006\u0003"}, d2 = {"Lokhttp3/Call;", "", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "execute", "Lokhttp3/Response;", "enqueue", "", "responseCallback", "Lokhttp3/Callback;", ToDoStat.CANCEL, "isExecuted", "", "isCanceled", "timeout", "Lokio/Timeout;", "clone", "Factory", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface Call extends Cloneable {

    /* JADX INFO: compiled from: Call.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006À\u0006\u0003"}, d2 = {"Lokhttp3/Call$Factory;", "", "newCall", "Lokhttp3/Call;", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Factory {
        Call newCall(Request request);
    }

    void cancel();

    /* JADX INFO: renamed from: clone */
    Call mo4516clone();

    void enqueue(Callback responseCallback);

    Response execute() throws IOException;

    boolean isCanceled();

    boolean isExecuted();

    Request request();

    Timeout timeout();
}
