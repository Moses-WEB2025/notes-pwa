package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import com.miui.common.stat.ToDoStat;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;

/* JADX INFO: compiled from: BroadcastChannel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Deprecated(level = DeprecationLevel.ERROR, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0003\n\u0000\bg\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H&J\u001f\u0010\u0005\u001a\u00020\u00062\u0010\b\u0002\u0010\u0007\u001a\n\u0018\u00010\bj\u0004\u0018\u0001`\tH&¢\u0006\u0002\u0010\nJ\u0014\u0010\u0005\u001a\u00020\u000b2\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\fH'¨\u0006\r"}, d2 = {"Lkotlinx/coroutines/channels/BroadcastChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/SendChannel;", "openSubscription", "Lkotlinx/coroutines/channels/ReceiveChannel;", ToDoStat.CANCEL, "", "cause", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "(Ljava/util/concurrent/CancellationException;)V", "", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
public interface BroadcastChannel<E> extends SendChannel<E> {
    void cancel(CancellationException cause);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility only")
    /* synthetic */ boolean cancel(Throwable cause);

    ReceiveChannel<E> openSubscription();

    /* JADX INFO: compiled from: BroadcastChannel.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
        public static <E> boolean offer(BroadcastChannel<E> broadcastChannel, E e) {
            return SendChannel.DefaultImpls.offer(broadcastChannel, e);
        }

        public static /* synthetic */ void cancel$default(BroadcastChannel broadcastChannel, CancellationException cancellationException, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: cancel");
            }
            if ((i & 1) != 0) {
                cancellationException = null;
            }
            broadcastChannel.cancel(cancellationException);
        }

        public static /* synthetic */ boolean cancel$default(BroadcastChannel broadcastChannel, Throwable th, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: cancel");
            }
            if ((i & 1) != 0) {
                th = null;
            }
            return broadcastChannel.cancel(th);
        }
    }
}
