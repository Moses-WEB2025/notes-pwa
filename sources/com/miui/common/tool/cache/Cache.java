package com.miui.common.tool.cache;

/* JADX INFO: loaded from: classes2.dex */
public interface Cache<K, V> {
    void clear();

    V get(K k);

    int getCount();

    void put(K k, V v, int i);

    void setMaxSize(int i);
}
