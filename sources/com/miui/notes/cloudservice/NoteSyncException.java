package com.miui.notes.cloudservice;

/* JADX INFO: loaded from: classes4.dex */
public class NoteSyncException extends Exception {
    private static final long serialVersionUID = 1;
    private Exception mInnerException;
    private boolean mRecoverable;

    public NoteSyncException(String str) {
        this(str, null, true);
    }

    public NoteSyncException(String str, boolean z) {
        this(str, null, z);
    }

    public NoteSyncException(String str, Exception exc, boolean z) {
        super(str);
        this.mInnerException = exc;
        this.mRecoverable = z;
    }

    public NoteSyncException(Exception exc) {
        this(exc, true);
    }

    public NoteSyncException(Exception exc, boolean z) {
        this(null, exc, z);
    }

    public Exception getInnerException() {
        return this.mInnerException;
    }

    public boolean isRecoverable() {
        return this.mRecoverable;
    }

    public void setRecoverable(boolean z) {
        this.mRecoverable = z;
    }
}
