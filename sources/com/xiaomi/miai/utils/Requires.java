package com.xiaomi.miai.utils;

import com.xiaomi.miai.SDKException;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class Requires {
    private Requires() {
    }

    public static void isTrue(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalArgumentException(String.format(str, objArr));
        }
    }

    public static void isTrue(boolean z, String str) {
        if (!z) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void notNull(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void notEmpty(Object[] objArr, String str) {
        if (objArr == null || objArr.length == 0) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void notEmpty(Collection collection, String str) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void notEmpty(Map map, String str) {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void notEmpty(String str, String str2) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException(str2);
        }
    }

    public static void isValidResult(boolean z, String str, Object... objArr) throws SDKException {
        if (!z) {
            throw new SDKException(String.format(str, objArr), null);
        }
    }
}
