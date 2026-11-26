package kotlinx.coroutines.debug.internal;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;

/* JADX INFO: compiled from: Comparisons.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
public final class DebugProbesImpl$dumpCoroutinesInfoImpl$$inlined$sortedBy$1<T> implements Comparator {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        return ComparisonsKt.compareValues(Long.valueOf(((DebugProbesImpl.CoroutineOwner) t).info.sequenceNumber), Long.valueOf(((DebugProbesImpl.CoroutineOwner) t2).info.sequenceNumber));
    }
}
