package com.xiaomi.phonenum.procedure.phone;

import android.content.Context;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.phonenum.data.PlainPhoneNumber;
import com.xiaomi.phonenum.data.PlainPhoneNumberCache;
import com.xiaomi.phonenum.phone.PhoneInfo;
import com.xiaomi.phonenum.utils.SubId;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneNumberObtainHelper {
    private static final String TAG = "PhoneNumberObtainHelper";

    public static PlainPhoneNumber[] getPlainPhoneNumbers(Context context, IPhoneNumberObtainer... iPhoneNumberObtainerArr) {
        int phoneCount = PhoneInfo.get(context).getPhoneCount();
        PlainPhoneNumber[] plainPhoneNumberArr = new PlainPhoneNumber[phoneCount];
        for (int i = 0; i < phoneCount; i++) {
            int i2 = SubId.get(context, i);
            if (i2 == -1) {
                AccountLogger.log(TAG, "getPlainPhoneNumbers invalid subId for simIndex=" + i);
            } else {
                PlainPhoneNumber plainPhoneNumberObtain = PlainPhoneNumberCache.get(context, i2);
                if (plainPhoneNumberObtain == null) {
                    for (IPhoneNumberObtainer iPhoneNumberObtainer : iPhoneNumberObtainerArr) {
                        try {
                            plainPhoneNumberObtain = iPhoneNumberObtainer.obtain(context, i2);
                            PlainPhoneNumberCache.put(context, plainPhoneNumberObtain);
                            break;
                        } catch (PhoneNumberObtainException e) {
                            AccountLogger.log(TAG, "getPlainPhoneNumbers failed simIndex=" + i + ", subId=" + i2, e);
                        }
                    }
                }
                plainPhoneNumberArr[i] = plainPhoneNumberObtain;
            }
        }
        return plainPhoneNumberArr;
    }
}
