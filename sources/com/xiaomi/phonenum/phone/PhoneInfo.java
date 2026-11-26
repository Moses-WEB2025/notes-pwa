package com.xiaomi.phonenum.phone;

import android.content.Context;
import android.os.Process;
import android.telephony.TelephonyManager;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.phonenum.bean.Sim;
import com.xiaomi.phonenum.utils.SubId;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneInfo implements PhoneUtil {
    private static final String TAG = "PhoneInfo";
    private static volatile PhoneInfo sInstance;
    protected Context mContext;

    public PhoneInfo(Context context) {
        this.mContext = context;
    }

    public static PhoneInfo get(Context context) {
        if (sInstance == null) {
            synchronized (PhoneInfo.class) {
                if (sInstance == null) {
                    sInstance = new PhoneInfo(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public boolean isNetWorkTypeMobile() {
        return Boolean.parseBoolean(PrivacyDataMaster.forceGet(this.mContext, PrivacyDataType.MOBILE_DATA_ENABLE, new String[0]));
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public String getImei() {
        return PrivacyDataMaster.get(this.mContext, PrivacyDataType.DEVICE_ID, new String[0]);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public String getDeviceId() {
        return getImei();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public int getPhoneCount() {
        return ((TelephonyManager) this.mContext.getSystemService("phone")).getPhoneCount();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public int getSubIdForSlotId(int i) {
        return SubId.get(this.mContext, i);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public boolean getDataEnabledForSubId(int i) {
        return Boolean.parseBoolean(PrivacyDataMaster.forceGet(this.mContext, PrivacyDataType.MOBILE_DATA_ENABLE, String.valueOf(i)));
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public Sim getSimForSubId(int i) {
        String iccid = getIccid(i);
        String imsi = getImsi(i);
        String mccMnc = getMccMnc(i);
        String line1Number = getLine1Number(i);
        if (iccid == null || imsi == null) {
            return null;
        }
        return new Sim(iccid, imsi, mccMnc, line1Number);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public Sim tryGetSimForSubId(int i) {
        String iccid;
        String imsi;
        String line1Number = null;
        try {
            iccid = getIccid(i);
            try {
                imsi = getImsi(i);
                try {
                    line1Number = getLine1Number(i);
                } catch (SecurityException e) {
                    e = e;
                    AccountLogger.log(TAG, "tryGetSimForSubId", e);
                }
            } catch (SecurityException e2) {
                e = e2;
                imsi = null;
            }
        } catch (SecurityException e3) {
            e = e3;
            iccid = null;
            imsi = null;
        }
        return new Sim(iccid, imsi, getMccMnc(i), line1Number);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public boolean isSimStateReadyForSubId(int i) {
        return getSimForSubId(i) != null;
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public String getNetworkMccMncForSubId(int i) {
        return PrivacyDataMaster.forceGet(this.mContext, PrivacyDataType.NETWORK_MCCMNC, String.valueOf(i));
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public int getPhoneTypeForSubId(int i) {
        return ((TelephonyManager) this.mContext.getSystemService("phone")).createForSubscriptionId(i).getPhoneType();
    }

    protected String getIccid(int i) {
        return PrivacyDataMaster.forceGet(this.mContext, PrivacyDataType.ICCID, String.valueOf(i));
    }

    protected String getImsi(int i) {
        return PrivacyDataMaster.forceGet(this.mContext, PrivacyDataType.IMSI, String.valueOf(i));
    }

    protected String getMccMnc(int i) {
        return PrivacyDataMaster.forceGet(this.mContext, PrivacyDataType.MCCMNC, String.valueOf(i));
    }

    protected String getLine1Number(int i) {
        return PrivacyDataMaster.forceGet(this.mContext, PrivacyDataType.LINE_1_NUMBER, String.valueOf(i));
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public boolean checkPermission(String str) {
        return this.mContext.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }
}
