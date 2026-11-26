package com.xiaomi.opensdk.pdc;

/* JADX INFO: loaded from: classes4.dex */
public class Constants {
    public static final int NULL_ETAG = -1;

    public enum ConflictType {
        NONE,
        ETAG,
        ID,
        UNIQUE_KEY
    }

    public enum ErrorType {
        OK,
        AUTH_FAILED,
        NEED_RESYNC,
        RETRIABLE_ERROR,
        UNRETRIABLE_ERROR
    }

    public enum RecordStatus {
        NORMAL,
        DELETED
    }
}
