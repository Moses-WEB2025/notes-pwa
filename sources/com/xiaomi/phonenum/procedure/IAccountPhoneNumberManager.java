package com.xiaomi.phonenum.procedure;

import android.content.Context;
import com.xiaomi.passport.sim.SIMInfo;
import com.xiaomi.phonenum.data.AccountCertification;
import com.xiaomi.phonenum.data.PlainPhoneNumber;

/* JADX INFO: loaded from: classes4.dex */
public interface IAccountPhoneNumberManager {
    AccountCertification[] getAccountCertifications(Context context, String str, AccountPhoneNumberSourceFlag accountPhoneNumberSourceFlag);

    PlainPhoneNumber[] getPlainPhoneNumbers(Context context, String str, AccountPhoneNumberSourceFlag accountPhoneNumberSourceFlag);

    SIMInfo[] getSIMInfos(Context context, String str, String[] strArr);

    void invalidateAccountCertification(Context context, String str, AccountCertification accountCertification);
}
