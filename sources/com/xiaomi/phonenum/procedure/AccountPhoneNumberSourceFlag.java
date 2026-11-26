package com.xiaomi.phonenum.procedure;

/* JADX INFO: loaded from: classes4.dex */
public class AccountPhoneNumberSourceFlag {
    public static final int SOURCE_ACTIVATION = 1;
    public static final int SOURCE_LINE1NUMBER = 4;
    public static final int SOURCE_OPERATOR = 2;
    public final int sourceFlag;

    public AccountPhoneNumberSourceFlag(int i) {
        this.sourceFlag = i;
    }

    public static AccountPhoneNumberSourceFlag getFlagAll() {
        return new AccountPhoneNumberSourceFlag(-1);
    }

    public static AccountPhoneNumberSourceFlag getFlag(int... iArr) {
        int i = 0;
        for (int i2 : iArr) {
            i |= i2;
        }
        return new AccountPhoneNumberSourceFlag(i);
    }

    public AccountPhoneNumberSourceFlag addFlag(int i) {
        return new AccountPhoneNumberSourceFlag(this.sourceFlag | i);
    }

    public boolean containsFlag(int i) {
        return (this.sourceFlag & i) != 0;
    }

    public boolean isValid() {
        return containsFlag(1) || containsFlag(2) || containsFlag(4);
    }
}
