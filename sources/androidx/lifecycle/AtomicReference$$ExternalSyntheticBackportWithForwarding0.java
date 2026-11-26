package androidx.lifecycle;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class AtomicReference$$ExternalSyntheticBackportWithForwarding0 {
    public static /* synthetic */ boolean m(java.util.concurrent.atomic.AtomicReference atomicReference, Object obj, Object obj2) {
        while (!atomicReference.compareAndSet(obj, obj2)) {
            if (atomicReference.get() != obj) {
                return false;
            }
        }
        return true;
    }
}
