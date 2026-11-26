package com.xiaomi.phonenum.phone;

import com.xiaomi.phonenum.bean.Sim;

/* JADX INFO: loaded from: classes4.dex */
public interface PhoneUtil {
    boolean checkPermission(String str);

    boolean getDataEnabledForSubId(int i);

    String getDeviceId();

    @Deprecated
    String getImei();

    String getNetworkMccMncForSubId(int i);

    int getPhoneCount();

    int getPhoneTypeForSubId(int i);

    Sim getSimForSubId(int i);

    int getSubIdForSlotId(int i);

    boolean isNetWorkTypeMobile();

    boolean isSimStateReadyForSubId(int i);

    Sim tryGetSimForSubId(int i);
}
