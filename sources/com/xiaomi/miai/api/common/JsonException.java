package com.xiaomi.miai.api.common;

/* JADX INFO: loaded from: classes4.dex */
public class JsonException extends RuntimeException {
    static final long serialVersionUID = -61348186355322741L;

    public JsonException() {
    }

    public JsonException(String str) {
        super(str);
    }

    public JsonException(String str, Throwable th) {
        super(str, th);
    }
}
