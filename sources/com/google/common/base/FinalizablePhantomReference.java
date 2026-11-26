package com.google.common.base;

import java.lang.ref.PhantomReference;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public abstract class FinalizablePhantomReference<T> extends PhantomReference<T> implements FinalizableReference {
    protected FinalizablePhantomReference(@CheckForNull T referent, FinalizableReferenceQueue queue) {
        super(referent, queue.queue);
        queue.cleanUp();
    }
}
