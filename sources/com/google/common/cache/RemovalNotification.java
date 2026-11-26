package com.google.common.cache;

import com.google.common.base.Preconditions;
import java.util.AbstractMap;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public final class RemovalNotification<K, V> extends AbstractMap.SimpleImmutableEntry<K, V> {
    private static final long serialVersionUID = 0;
    private final RemovalCause cause;

    public static <K, V> RemovalNotification<K, V> create(@CheckForNull K key, @CheckForNull V value, RemovalCause cause) {
        return new RemovalNotification<>(key, value, cause);
    }

    private RemovalNotification(@CheckForNull K key, @CheckForNull V value, RemovalCause cause) {
        super(key, value);
        this.cause = (RemovalCause) Preconditions.checkNotNull(cause);
    }

    public RemovalCause getCause() {
        return this.cause;
    }

    public boolean wasEvicted() {
        return this.cause.wasEvicted();
    }
}
