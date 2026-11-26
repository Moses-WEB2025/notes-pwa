package com.xiaomi.phonenum.procedure;

import android.content.Context;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.sim.SIMInfo;
import com.xiaomi.phonenum.data.AccountCertification;
import com.xiaomi.phonenum.data.AccountCertificationCache;
import com.xiaomi.phonenum.data.PlainPhoneNumber;
import com.xiaomi.phonenum.procedure.cert.AccountCertificationFetchHelper;
import com.xiaomi.phonenum.procedure.cert.IAccountCertificationFetcher;
import com.xiaomi.phonenum.procedure.cert.OperatorAccountCertificationFetcher;
import com.xiaomi.phonenum.procedure.phone.IPhoneNumberObtainer;
import com.xiaomi.phonenum.procedure.phone.Line1PhoneNumberObtainer;
import com.xiaomi.phonenum.procedure.phone.PhoneNumberObtainHelper;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class OtherOsAccountPhoneNumberManager implements IAccountPhoneNumberManager {
    private static final String TAG = "OtherOsAccountPhoneNumberManager";

    @Override // com.xiaomi.phonenum.procedure.IAccountPhoneNumberManager
    public PlainPhoneNumber[] getPlainPhoneNumbers(Context context, String str, AccountPhoneNumberSourceFlag accountPhoneNumberSourceFlag) {
        AccountLogger.log(TAG, "call getPlainPhoneNumbers sid=" + str + ", flag=" + Integer.toBinaryString(accountPhoneNumberSourceFlag.sourceFlag));
        ArrayList arrayList = new ArrayList();
        if (accountPhoneNumberSourceFlag.containsFlag(4)) {
            AccountLogger.log(TAG, "add Line1PhoneNumberObtainer for flag=" + Integer.toBinaryString(accountPhoneNumberSourceFlag.sourceFlag));
            arrayList.add(new Line1PhoneNumberObtainer());
        }
        return PhoneNumberObtainHelper.getPlainPhoneNumbers(context, (IPhoneNumberObtainer[]) arrayList.toArray(new IPhoneNumberObtainer[0]));
    }

    @Override // com.xiaomi.phonenum.procedure.IAccountPhoneNumberManager
    public AccountCertification[] getAccountCertifications(Context context, String str, AccountPhoneNumberSourceFlag accountPhoneNumberSourceFlag) {
        AccountLogger.log(TAG, "call getAccountCertifications sid=" + str + ", flag=" + Integer.toBinaryString(accountPhoneNumberSourceFlag.sourceFlag));
        ArrayList arrayList = new ArrayList();
        if (accountPhoneNumberSourceFlag.containsFlag(2)) {
            AccountLogger.log(TAG, "add OperatorAccountCertificationFetcher for flag=" + Integer.toBinaryString(accountPhoneNumberSourceFlag.sourceFlag));
            arrayList.add(new OperatorAccountCertificationFetcher(str, context.getPackageName()));
        }
        return AccountCertificationFetchHelper.getAccountCertifications(context, (IAccountCertificationFetcher[]) arrayList.toArray(new IAccountCertificationFetcher[0]));
    }

    @Override // com.xiaomi.phonenum.procedure.IAccountPhoneNumberManager
    public void invalidateAccountCertification(Context context, String str, AccountCertification accountCertification) {
        AccountLogger.log(TAG, "call invalidateAccountCertification sid=" + str + ", accountCertification=" + accountCertification);
        AccountCertificationCache.remove(accountCertification);
    }

    @Override // com.xiaomi.phonenum.procedure.IAccountPhoneNumberManager
    public SIMInfo[] getSIMInfos(Context context, final String str, String[] strArr) {
        AccountLogger.log(TAG, "call getSIMInfos sid=" + str + ", simInfoTypes=" + String.join(",", strArr));
        return SIMInfo.build(context, strArr, new AccountCertification.Getter() { // from class: com.xiaomi.phonenum.procedure.OtherOsAccountPhoneNumberManager.1
            @Override // com.xiaomi.phonenum.data.AccountCertification.Getter
            public AccountCertification[] get(Context context2, AccountPhoneNumberSourceFlag accountPhoneNumberSourceFlag) {
                return OtherOsAccountPhoneNumberManager.this.getAccountCertifications(context2, str, accountPhoneNumberSourceFlag);
            }
        }, null);
    }
}
