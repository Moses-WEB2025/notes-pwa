package kotlinx.coroutines.guava;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;

/* JADX INFO: compiled from: ListenableFuture.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lkotlinx/coroutines/guava/Cancelled;", "", "exception", "Ljava/util/concurrent/CancellationException;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/concurrent/CancellationException;)V", "kotlinx-coroutines-guava"}, k = 1, mv = {2, 1, 0}, xi = 48)
final class Cancelled {
    public final CancellationException exception;

    public Cancelled(CancellationException cancellationException) {
        this.exception = cancellationException;
    }
}
