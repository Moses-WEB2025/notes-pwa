package com.xiaomi.accountsdk.account.data;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneInfo {
    final String mPhoneNum;
    final String mSimId;

    public PhoneInfo(String str, String str2) {
        this.mSimId = str;
        this.mPhoneNum = str2;
    }

    public String getSimId() {
        return this.mSimId;
    }

    public String getPhoneNum() {
        return this.mPhoneNum;
    }
}
