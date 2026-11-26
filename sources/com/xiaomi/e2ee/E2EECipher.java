package com.xiaomi.e2ee;

import com.xiaomi.e2ee.appkey.KeyEntry;

/* JADX INFO: loaded from: classes4.dex */
abstract class E2EECipher<T> {
    abstract T decodeData(T t, KeyEntry keyEntry, int i) throws InterruptedException, E2EEException;

    E2EECipher() {
    }

    public T encrypt(T t, KeyEntry keyEntry) throws InterruptedException, E2EEException {
        return decodeData(t, keyEntry, 1);
    }

    public T decrypt(T t, KeyEntry keyEntry) throws InterruptedException, E2EEException {
        return decodeData(t, keyEntry, 2);
    }
}
