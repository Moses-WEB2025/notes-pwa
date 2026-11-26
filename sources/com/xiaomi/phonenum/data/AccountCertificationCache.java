package com.xiaomi.phonenum.data;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class AccountCertificationCache {
    private static final Map<Integer, AccountCertification> sCache = new HashMap();

    public static synchronized void put(AccountCertification accountCertification) {
        sCache.put(Integer.valueOf(accountCertification.subId), accountCertification);
    }

    public static synchronized AccountCertification get(int i) {
        AccountCertification accountCertification = sCache.get(Integer.valueOf(i));
        if (accountCertification == null) {
            return null;
        }
        return new AccountCertification(accountCertification.subId, accountCertification.hashedPhoneNumber, accountCertification.activatorToken, null);
    }

    public static synchronized void remove(AccountCertification accountCertification) {
        AccountCertification accountCertification2 = get(accountCertification.subId);
        if (accountCertification2 == null) {
            return;
        }
        if (accountCertification2.equals(accountCertification)) {
            sCache.remove(Integer.valueOf(accountCertification.subId));
        }
    }
}
