package com.xiaomi.opensdk.pdc.asset;

import com.xiaomi.opensdk.pdc.Constants;

/* JADX INFO: loaded from: classes4.dex */
public class AssetException extends Exception {
    private static final long serialVersionUID = 1;
    public String description;
    public int errorCode;
    public Constants.ErrorType errorType;
    public boolean retriable;
    public long retrytime;

    public AssetException(String str, Throwable th) {
        super(th);
        this.description = str;
    }

    public AssetException(String str, Constants.ErrorType errorType, int i, boolean z, long j) {
        this.description = str;
        this.errorType = errorType;
        this.errorCode = i;
        this.retriable = z;
        this.retrytime = j;
    }

    @Override // java.lang.Throwable
    public String toString() {
        String simpleName = getClass().getSimpleName();
        Throwable cause = getCause();
        if (cause != null) {
            String str = simpleName + "[" + cause.getClass().getSimpleName();
            if (cause.getMessage() != null) {
                str = str + ":" + cause.getMessage();
            }
            simpleName = str + "]";
        }
        return this.description != null ? simpleName + ",  " + this.description : simpleName;
    }
}
