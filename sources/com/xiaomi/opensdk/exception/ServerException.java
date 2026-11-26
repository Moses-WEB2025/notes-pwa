package com.xiaomi.opensdk.exception;

/* JADX INFO: loaded from: classes4.dex */
public class ServerException extends Exception {
    private int statusCode;

    public ServerException() {
    }

    public ServerException(String str, Throwable th) {
        super(str, th);
    }

    public ServerException(String str) {
        super(str);
    }

    public ServerException(Throwable th) {
        super(th);
    }

    public static boolean isOpenServerException(int i) {
        return i == 400 || i == 401 || i == 403 || i == 406 || i / 100 == 5;
    }

    public ServerException(int i, String str) {
        this(str);
        this.statusCode = i;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
