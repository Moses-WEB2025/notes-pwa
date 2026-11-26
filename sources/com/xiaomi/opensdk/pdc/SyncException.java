package com.xiaomi.opensdk.pdc;

/* JADX INFO: loaded from: classes4.dex */
public class SyncException extends Exception {
    private static final long serialVersionUID = 1;
    protected String mDescription;

    public SyncException() {
    }

    public SyncException(String str) {
        this.mDescription = str;
    }

    public SyncException(Throwable th) {
        super(th);
    }

    public String getDescription() {
        return this.mDescription;
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
        return this.mDescription != null ? simpleName + ",  " + this.mDescription : simpleName;
    }
}
