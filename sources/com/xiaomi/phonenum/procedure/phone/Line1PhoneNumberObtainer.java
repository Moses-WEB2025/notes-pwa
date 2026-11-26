package com.xiaomi.phonenum.procedure.phone;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.phonenum.bean.Sim;
import com.xiaomi.phonenum.data.PlainPhoneNumber;
import com.xiaomi.phonenum.phone.PhoneInfo;

/* JADX INFO: loaded from: classes4.dex */
public class Line1PhoneNumberObtainer implements IPhoneNumberObtainer {
    private static final String TAG = "Line1PhoneNumberObtainer";

    @Override // com.xiaomi.phonenum.procedure.phone.IPhoneNumberObtainer
    public PlainPhoneNumber obtain(Context context, int i) throws PhoneNumberObtainException {
        Sim simTryGetSimForSubId = PhoneInfo.get(context).tryGetSimForSubId(i);
        if (TextUtils.isEmpty(simTryGetSimForSubId.line1Number)) {
            throw new PhoneNumberObtainException("empty line1number for subId=" + i);
        }
        AccountLogger.log(TAG, "get plain phone number from line1number succeed");
        return new PlainPhoneNumber(i, simTryGetSimForSubId.line1Number);
    }
}
