package com.xiaomi.phonenum.utils;

import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public class TraceId {
    public static String any() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
