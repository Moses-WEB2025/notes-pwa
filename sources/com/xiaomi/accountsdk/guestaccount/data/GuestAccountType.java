package com.xiaomi.accountsdk.guestaccount.data;

/* JADX INFO: loaded from: classes4.dex */
public enum GuestAccountType {
    DEFAULT(1),
    FID(3);

    public static final int SERVER_VALUE_AS_NULL = -1;
    public final int serverValue;

    GuestAccountType(int i) {
        this.serverValue = i;
    }

    public static GuestAccountType getFromServerValue(int i) {
        for (GuestAccountType guestAccountType : values()) {
            if (guestAccountType.serverValue == i) {
                return guestAccountType;
            }
        }
        return null;
    }
}
