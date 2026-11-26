package com.xiaomi.accountsdk.account.utils;

/* JADX INFO: loaded from: classes4.dex */
public class ReferenceHolder<T> {
    private T mReference;

    public ReferenceHolder(T t) {
        this.mReference = t;
    }

    public synchronized T get() {
        return this.mReference;
    }

    public synchronized void set(T t) {
        this.mReference = t;
    }
}
