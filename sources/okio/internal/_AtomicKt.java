package okio.internal;

import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: -Atomic.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0000¨\u0006\u0004"}, d2 = {"setBitsOrZero", "", "Ljava/util/concurrent/atomic/AtomicInteger;", "bits", "okio"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class _AtomicKt {
    public static final int setBitsOrZero(AtomicInteger atomicInteger, int i) {
        int i2;
        int i3;
        Intrinsics.checkNotNullParameter(atomicInteger, "<this>");
        do {
            i2 = atomicInteger.get();
            if ((i2 & i) != 0) {
                return 0;
            }
            i3 = i2 | i;
        } while (!atomicInteger.compareAndSet(i2, i3));
        return i3;
    }
}
