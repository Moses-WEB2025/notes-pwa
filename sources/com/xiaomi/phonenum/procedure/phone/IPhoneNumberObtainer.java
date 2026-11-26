package com.xiaomi.phonenum.procedure.phone;

import android.content.Context;
import com.xiaomi.phonenum.data.PlainPhoneNumber;

/* JADX INFO: loaded from: classes4.dex */
public interface IPhoneNumberObtainer {
    PlainPhoneNumber obtain(Context context, int i) throws PhoneNumberObtainException;
}
