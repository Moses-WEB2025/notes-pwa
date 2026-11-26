package com.xiaomi.passport.sim;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;
import com.xiaomi.accountsdk.account.data.MiuiActivatorInfo;
import com.xiaomi.phonenum.data.AccountCertification;
import com.xiaomi.phonenum.phone.PhoneInfo;
import com.xiaomi.phonenum.procedure.AccountPhoneNumberSourceFlag;

/* JADX INFO: loaded from: classes4.dex */
public class SIMInfo implements Parcelable {
    public static final Parcelable.Creator<SIMInfo> CREATOR = new Parcelable.Creator<SIMInfo>() { // from class: com.xiaomi.passport.sim.SIMInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SIMInfo createFromParcel(Parcel parcel) {
            return new SIMInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SIMInfo[] newArray(int i) {
            return new SIMInfo[i];
        }
    };
    public static final int INVALID_SLOT_INDEX = -1;
    public static final int INVALID_SUB_ID = -1;
    public static final String SIM_INFO_TYPE_ACTIVATION_ACCOUNT_CERT = "activationAccountCert";
    public static final String SIM_INFO_TYPE_ACTIVATION_OR_OPERATOR_ACCOUNT_CERT = "activationOrOperatorAccountCert";
    public static final String SIM_INFO_TYPE_ACTIVATOR_INFO = "activatorInfo";
    public static final String SIM_INFO_TYPE_ICCID = "iccid";
    public static final String SIM_INFO_TYPE_IMSI = "imsi";
    public static final String SIM_INFO_TYPE_IN_SERVICE = "inService";
    public static final String SIM_INFO_TYPE_LINE_1_NUMBER = "line1Number";
    public static final String SIM_INFO_TYPE_MCCMNC = "mccmnc";
    public static final String SIM_INFO_TYPE_MOBILE_DATA_ENABLE = "mobileDataEnable";
    public static final String SIM_INFO_TYPE_NETWORK_MCCMNC = "networkMCCMNC";
    public static final String SIM_INFO_TYPE_OPERATOR_ACCOUNT_CERT = "operatorAccountCert";
    public static final String SIM_INFO_TYPE_SIM_ID = "simId";
    public static final String SIM_INFO_TYPE_SLOT_INDEX = "slotIndex";
    public static final String SIM_INFO_TYPE_SUB_ID = "subId";
    public final AccountCertification accountCert;
    public final MiuiActivatorInfo activatorInfo;
    public final String iccid;
    public final String imsi;
    public final Boolean inService;
    public final String line1Number;
    public final String mccmnc;
    public final Boolean mobileDataEnable;
    public final String networkMCCMNC;
    public final String simId;
    public final int slotIndex;
    public final int subId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SIMInfo(int i, int i2, String str, String str2, String str3, String str4, String str5, String str6, Boolean bool, Boolean bool2, AccountCertification accountCertification, MiuiActivatorInfo miuiActivatorInfo) {
        this.slotIndex = i;
        this.subId = i2;
        this.simId = str;
        this.line1Number = str2;
        this.iccid = str3;
        this.imsi = str4;
        this.mccmnc = str5;
        this.networkMCCMNC = str6;
        this.inService = bool;
        this.mobileDataEnable = bool2;
        this.accountCert = accountCertification;
        this.activatorInfo = miuiActivatorInfo;
    }

    private static AccountPhoneNumberSourceFlag getAccountCertSourceFlag(String[] strArr) {
        AccountPhoneNumberSourceFlag flag = AccountPhoneNumberSourceFlag.getFlag(new int[0]);
        for (String str : strArr) {
            str.hashCode();
            switch (str) {
                case "activationOrOperatorAccountCert":
                    flag = flag.addFlag(1).addFlag(2);
                    break;
                case "operatorAccountCert":
                    flag = flag.addFlag(2);
                    break;
                case "activationAccountCert":
                    flag = flag.addFlag(1);
                    break;
            }
        }
        return flag;
    }

    public static class Builder {
        private AccountCertification accountCert;
        private MiuiActivatorInfo activatorInfo;
        private String iccid;
        private String imsi;
        private Boolean inService;
        private String line1Number;
        private String mccmnc;
        private Boolean mobileDataEnable;
        private String networkMCCMNC;
        private String simId;
        private int slotIndex;
        private int subId;

        public Builder(Context context, int i) {
            this.slotIndex = i;
            this.subId = -1;
            try {
                this.subId = Integer.parseInt(PrivacyDataMaster.forceGet(context, PrivacyDataType.SUB_ID, String.valueOf(i)));
            } catch (Exception unused) {
            }
        }

        public Builder simId(String str) {
            this.simId = str;
            return this;
        }

        public Builder autoGetSIMId(Context context) {
            return simId(SIMId.get(context, this.subId));
        }

        public Builder line1Number(String str) {
            this.line1Number = str;
            return this;
        }

        public Builder autoGetLine1Number(Context context) {
            return line1Number(PrivacyDataMaster.forceGet(context, PrivacyDataType.LINE_1_NUMBER, String.valueOf(this.subId)));
        }

        public Builder iccid(String str) {
            this.iccid = str;
            return this;
        }

        public Builder autoGetICCID(Context context) {
            return iccid(PrivacyDataMaster.forceGet(context, PrivacyDataType.ICCID, String.valueOf(this.subId)));
        }

        public Builder imsi(String str) {
            this.imsi = str;
            return this;
        }

        public Builder autoGetIMSI(Context context) {
            return imsi(PrivacyDataMaster.forceGet(context, PrivacyDataType.IMSI, String.valueOf(this.subId)));
        }

        public Builder mccmnc(String str) {
            this.mccmnc = str;
            return this;
        }

        public Builder autoGetMCCMNC(Context context) {
            return mccmnc(PrivacyDataMaster.forceGet(context, PrivacyDataType.MCCMNC, String.valueOf(this.subId)));
        }

        public Builder networkMCCMNC(String str) {
            this.networkMCCMNC = str;
            return this;
        }

        public Builder autoGetNetworkMCCMNC(Context context) {
            return networkMCCMNC(PrivacyDataMaster.forceGet(context, PrivacyDataType.NETWORK_MCCMNC, String.valueOf(this.subId)));
        }

        public Builder inService(Boolean bool) {
            this.inService = bool;
            return this;
        }

        public Builder autoGetInService(Context context) {
            String strForceGet = PrivacyDataMaster.forceGet(context, PrivacyDataType.SIM_IN_SERVICE, String.valueOf(this.subId), String.valueOf(EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS));
            return strForceGet == null ? this : inService(Boolean.valueOf(Boolean.parseBoolean(strForceGet)));
        }

        public Builder mobileDataEnable(Boolean bool) {
            this.mobileDataEnable = bool;
            return this;
        }

        public Builder autoGetMobileDataEnable(Context context) {
            try {
                return mobileDataEnable(Boolean.valueOf(Boolean.parseBoolean(PrivacyDataMaster.forceGet(context, PrivacyDataType.MOBILE_DATA_ENABLE, String.valueOf(this.subId)))));
            } catch (Exception unused) {
                return this;
            }
        }

        public Builder accountCert(AccountCertification accountCertification) {
            this.accountCert = accountCertification;
            return this;
        }

        public Builder activatorInfo(MiuiActivatorInfo miuiActivatorInfo) {
            this.activatorInfo = miuiActivatorInfo;
            return this;
        }

        public SIMInfo build() {
            return new SIMInfo(this.slotIndex, this.subId, this.simId, this.line1Number, this.iccid, this.imsi, this.mccmnc, this.networkMCCMNC, this.inService, this.mobileDataEnable, this.accountCert, this.activatorInfo);
        }
    }

    /* JADX WARN: Code duplicated, block: B:86:0x0112  */
    public static SIMInfo[] build(Context context, String[] strArr, AccountCertification.Getter getter, MiuiActivatorInfo.Getter getter2) {
        int phoneCount = PhoneInfo.get(context).getPhoneCount();
        SIMInfo[] sIMInfoArr = new SIMInfo[phoneCount];
        int length = strArr.length;
        AccountCertification[] accountCertificationArr = null;
        MiuiActivatorInfo[] miuiActivatorInfoArr = null;
        int i = 0;
        while (true) {
            if (i >= length) {
                for (int i2 = 0; i2 < phoneCount; i2++) {
                    Builder builder = new Builder(context, i2);
                    if (builder.subId != r11) {
                        for (String str : strArr) {
                            str.hashCode();
                            switch (str) {
                                case "activationOrOperatorAccountCert":
                                case "operatorAccountCert":
                                case "activationAccountCert":
                                    if (accountCertificationArr != null) {
                                        builder.accountCert(accountCertificationArr[i2]);
                                        break;
                                    } else {
                                        break;
                                    }
                                    break;
                                case "networkMCCMNC":
                                    builder.autoGetNetworkMCCMNC(context);
                                    break;
                                case "mccmnc":
                                    builder.autoGetMCCMNC(context);
                                    break;
                                case "mobileDataEnable":
                                    builder.autoGetMobileDataEnable(context);
                                    break;
                                case "imsi":
                                    builder.autoGetIMSI(context);
                                    break;
                                case "iccid":
                                    builder.autoGetICCID(context);
                                    break;
                                case "simId":
                                    builder.autoGetSIMId(context);
                                    break;
                                case "inService":
                                    builder.autoGetInService(context);
                                    break;
                                case "line1Number":
                                    builder.autoGetLine1Number(context);
                                    break;
                                case "activatorInfo":
                                    if (miuiActivatorInfoArr != null) {
                                        builder.activatorInfo(miuiActivatorInfoArr[i2]);
                                        break;
                                    } else {
                                        break;
                                    }
                                    break;
                            }
                        }
                    }
                    sIMInfoArr[i2] = builder.build();
                }
                return sIMInfoArr;
            }
            String str2 = strArr[i];
            str2.hashCode();
            switch (str2) {
                case "activationOrOperatorAccountCert":
                case "operatorAccountCert":
                case "activationAccountCert":
                    if (getter != null) {
                        accountCertificationArr = getter.get(context, getAccountCertSourceFlag(strArr));
                        break;
                    } else {
                        break;
                    }
                    break;
                case "activatorInfo":
                    if (getter2 != null) {
                        miuiActivatorInfoArr = getter2.get(context);
                        break;
                    } else {
                        break;
                    }
                    break;
            }
            i++;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.slotIndex);
        parcel.writeInt(this.subId);
        parcel.writeString(this.simId);
        parcel.writeString(this.line1Number);
        parcel.writeString(this.iccid);
        parcel.writeString(this.imsi);
        parcel.writeString(this.mccmnc);
        parcel.writeString(this.networkMCCMNC);
        parcel.writeValue(this.inService);
        parcel.writeValue(this.mobileDataEnable);
        parcel.writeParcelable(this.accountCert, i);
        parcel.writeParcelable(this.activatorInfo, i);
    }

    protected SIMInfo(Parcel parcel) {
        this.slotIndex = parcel.readInt();
        this.subId = parcel.readInt();
        this.simId = parcel.readString();
        this.line1Number = parcel.readString();
        this.iccid = parcel.readString();
        this.imsi = parcel.readString();
        this.mccmnc = parcel.readString();
        this.networkMCCMNC = parcel.readString();
        this.inService = (Boolean) parcel.readValue(Boolean.class.getClassLoader());
        this.mobileDataEnable = (Boolean) parcel.readValue(Boolean.class.getClassLoader());
        this.accountCert = (AccountCertification) parcel.readParcelable(AccountCertification.class.getClassLoader());
        this.activatorInfo = (MiuiActivatorInfo) parcel.readParcelable(MiuiActivatorInfo.class.getClassLoader());
    }
}
