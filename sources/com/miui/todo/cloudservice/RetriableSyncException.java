package com.miui.todo.cloudservice;

/* JADX INFO: loaded from: classes4.dex */
public class RetriableSyncException extends Exception {
    public long mRetryTime;

    RetriableSyncException(String str, long j) {
        super(str);
        this.mRetryTime = j;
    }
}
