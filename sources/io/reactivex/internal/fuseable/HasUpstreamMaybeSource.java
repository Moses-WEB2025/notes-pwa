package io.reactivex.internal.fuseable;

import io.reactivex.MaybeSource;

/* JADX INFO: loaded from: classes.dex */
public interface HasUpstreamMaybeSource<T> {
    MaybeSource<T> source();
}
