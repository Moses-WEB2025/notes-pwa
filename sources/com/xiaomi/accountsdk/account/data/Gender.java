package com.xiaomi.accountsdk.account.data;

/* JADX INFO: loaded from: classes4.dex */
public enum Gender {
    MALE("m"),
    FEMALE("f");

    private String mGender;

    Gender(String str) {
        this.mGender = str;
    }

    public String getType() {
        return this.mGender;
    }
}
