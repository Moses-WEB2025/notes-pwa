package com.xiaomi.phonenum.procedure.cert;

import android.content.Context;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.phonenum.data.AccountCertification;
import com.xiaomi.phonenum.data.AccountCertificationCache;
import com.xiaomi.phonenum.phone.PhoneInfo;
import com.xiaomi.phonenum.utils.SimState;
import com.xiaomi.phonenum.utils.SubId;

/* JADX INFO: loaded from: classes4.dex */
public class AccountCertificationFetchHelper {
    private static final String TAG = "AccountCertificationFetchHelper";

    public static AccountCertification[] getAccountCertifications(Context context, IAccountCertificationFetcher... iAccountCertificationFetcherArr) {
        int phoneCount = PhoneInfo.get(context).getPhoneCount();
        AccountCertification[] accountCertificationArr = new AccountCertification[phoneCount];
        for (int i = 0; i < phoneCount; i++) {
            int i2 = SubId.get(context, i);
            if (i2 == -1) {
                AccountLogger.log(TAG, "getAccountCertifications invalid subId for simIndex=" + i);
            } else if (!SimState.isValid(context, i2)) {
                AccountLogger.log(TAG, "getAccountCertifications sim not valid subId=" + i2);
            } else {
                AccountCertification accountCertificationFetch = AccountCertificationCache.get(i2);
                if (accountCertificationFetch == null) {
                    for (IAccountCertificationFetcher iAccountCertificationFetcher : iAccountCertificationFetcherArr) {
                        try {
                            accountCertificationFetch = iAccountCertificationFetcher.fetch(context, i2);
                            AccountCertificationCache.put(accountCertificationFetch);
                            break;
                        } catch (AccountCertificationFetchException e) {
                            AccountLogger.log(TAG, "get AccountCertification failed simIndex=" + i + ", subId=" + i2, e);
                        }
                    }
                }
                accountCertificationArr[i] = accountCertificationFetch;
            }
        }
        return accountCertificationArr;
    }
}
