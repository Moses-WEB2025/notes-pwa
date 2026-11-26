package com.google.common.base;

import java.lang.ref.WeakReference;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public abstract class FinalizableWeakReference<T> extends WeakReference<T> implements FinalizableReference {
    protected FinalizableWeakReference(@CheckForNull T referent, FinalizableReferenceQueue queue) {
        super(referent, queue.queue);
        queue.cleanUp();
    }
}
