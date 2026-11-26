package com.miui.notes.page;

import android.content.res.TypedArray;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes4.dex */
public final /* synthetic */ class PhoneNoteListPageFragment$$ExternalSyntheticBackport0 {
    public static /* synthetic */ Object m(Object obj, Supplier supplier) {
        return obj != null ? obj : Objects.requireNonNull(((Supplier) Objects.requireNonNull(supplier, "supplier")).get(), "supplier.get()");
    }

    public static /* synthetic */ void m(Object obj) throws Exception {
        if (obj instanceof AutoCloseable) {
            ((AutoCloseable) obj).close();
            return;
        }
        if (obj instanceof ExecutorService) {
            m((ExecutorService) obj);
        } else if (obj instanceof TypedArray) {
            ((TypedArray) obj).recycle();
        } else {
            m$1(obj);
        }
    }

    public static /* synthetic */ void m(ExecutorService executorService) {
        boolean zIsTerminated;
        if (executorService == ForkJoinPool.commonPool() || (zIsTerminated = executorService.isTerminated())) {
            return;
        }
        executorService.shutdown();
        boolean z = false;
        while (!zIsTerminated) {
            try {
                zIsTerminated = executorService.awaitTermination(1L, TimeUnit.DAYS);
            } catch (InterruptedException unused) {
                if (!z) {
                    executorService.shutdownNow();
                    z = true;
                }
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    public static /* synthetic */ void m$1(Object obj) {
        throw new IllegalArgumentException();
    }
}
