package com.xiaomi.phonenum.innetdate;

import com.xiaomi.phonenum.bean.Error;

/* JADX INFO: loaded from: classes4.dex */
public class InNetDateResult {
    private Error error;
    private long inNetDate;
    private String massage;
    private String phoneNum;

    InNetDateResult(long j, String str) {
        this.inNetDate = j;
        this.phoneNum = str;
    }

    InNetDateResult(Error error) {
        this(error, error.toString());
    }

    InNetDateResult(Error error, String str) {
        this.error = error;
        this.massage = str;
    }

    public boolean isSuccess() {
        return this.error == null;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    public long getInNetDate() {
        return this.inNetDate;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public Error getError() {
        if (isSuccess()) {
            return Error.NONE;
        }
        return this.error;
    }

    public String getMassage() {
        return this.massage;
    }

    public String toString() {
        return "IabResult: " + getMassage() + " date:" + this.inNetDate;
    }
}
