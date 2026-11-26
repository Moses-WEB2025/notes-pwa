package io.reactivex.internal.fuseable;

import io.reactivex.Flowable;

/* JADX INFO: loaded from: classes.dex */
public interface FuseToFlowable<T> {
    Flowable<T> fuseToFlowable();
}
