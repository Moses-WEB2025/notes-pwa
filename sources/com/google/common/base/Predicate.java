package com.google.common.base;

import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public interface Predicate<T> {
    boolean apply(@ParametricNullness T input);

    boolean equals(@CheckForNull Object object);
}
