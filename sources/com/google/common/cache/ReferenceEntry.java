package com.google.common.cache;

import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
interface ReferenceEntry<K, V> {
    long getAccessTime();

    int getHash();

    @CheckForNull
    K getKey();

    @CheckForNull
    ReferenceEntry<K, V> getNext();

    ReferenceEntry<K, V> getNextInAccessQueue();

    ReferenceEntry<K, V> getNextInWriteQueue();

    ReferenceEntry<K, V> getPreviousInAccessQueue();

    ReferenceEntry<K, V> getPreviousInWriteQueue();

    @CheckForNull
    LocalCache.ValueReference<K, V> getValueReference();

    long getWriteTime();

    void setAccessTime(long time);

    void setNextInAccessQueue(ReferenceEntry<K, V> next);

    void setNextInWriteQueue(ReferenceEntry<K, V> next);

    void setPreviousInAccessQueue(ReferenceEntry<K, V> previous);

    void setPreviousInWriteQueue(ReferenceEntry<K, V> previous);

    void setValueReference(LocalCache.ValueReference<K, V> valueReference);

    void setWriteTime(long time);
}
