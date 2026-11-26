package com.xiaomi.accountsdk.utils;

import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class EasyMap<K, V> extends HashMap<K, V> {
    private static final long serialVersionUID = -7846033686833655451L;

    public EasyMap() {
    }

    public EasyMap(K k, V v) {
        put(k, v);
    }

    public EasyMap<K, V> easyPut(K k, V v) {
        put(k, v);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EasyMap<K, V> easyPutOpt(K k, V v) {
        boolean zIsEmpty;
        if (k == null || v == 0) {
            zIsEmpty = false;
        } else {
            zIsEmpty = true;
            if (v instanceof String) {
                zIsEmpty = true ^ ((String) v).isEmpty();
            }
        }
        if (zIsEmpty) {
            put(k, v);
        }
        return this;
    }
}
