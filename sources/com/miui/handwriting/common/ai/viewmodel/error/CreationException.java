package com.miui.handwriting.common.ai.viewmodel.error;

/* JADX INFO: loaded from: classes3.dex */
public class CreationException extends IllegalStateException {
    private int code;
    private String message;

    public CreationException(int i, String str) {
        this.code = i;
        this.message = str;
    }

    public CreationException() {
    }

    public CreationException(String str) {
        super(str);
        this.message = this.message;
    }

    public CreationException(String str, Throwable th) {
        super(str, th);
        this.message = str;
    }

    public CreationException(Throwable th) {
        super(th);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
