package androidx.graphics.surface;

import java.util.concurrent.Executor;
import kotlin.Metadata;

/* JADX INFO: compiled from: SurfaceControlWrapper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, d2 = {"androidx/graphics/surface/SurfaceControlWrapper$Transaction$addTransactionCommittedListener$1", "Landroidx/graphics/surface/SurfaceControlCompat$TransactionCommittedListener;", "onTransactionCommitted", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SurfaceControlWrapper$Transaction$addTransactionCommittedListener$1 implements SurfaceControlCompat.TransactionCommittedListener {
    final /* synthetic */ Executor $executor;
    final /* synthetic */ SurfaceControlCompat.TransactionCommittedListener $listener;

    SurfaceControlWrapper$Transaction$addTransactionCommittedListener$1(Executor executor, SurfaceControlCompat.TransactionCommittedListener transactionCommittedListener) {
        this.$executor = executor;
        this.$listener = transactionCommittedListener;
    }

    @Override // androidx.graphics.surface.SurfaceControlCompat.TransactionCommittedListener
    public void onTransactionCommitted() {
        Executor executor = this.$executor;
        final SurfaceControlCompat.TransactionCommittedListener transactionCommittedListener = this.$listener;
        executor.execute(new Runnable() { // from class: androidx.graphics.surface.SurfaceControlWrapper$Transaction$addTransactionCommittedListener$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                transactionCommittedListener.onTransactionCommitted();
            }
        });
    }
}
