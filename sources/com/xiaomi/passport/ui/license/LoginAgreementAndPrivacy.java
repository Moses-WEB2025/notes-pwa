package com.xiaomi.passport.ui.license;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class LoginAgreementAndPrivacy implements Parcelable {
    public static final Parcelable.Creator<LoginAgreementAndPrivacy> CREATOR = new Parcelable.Creator<LoginAgreementAndPrivacy>() { // from class: com.xiaomi.passport.ui.license.LoginAgreementAndPrivacy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LoginAgreementAndPrivacy createFromParcel(Parcel parcel) {
            return new LoginAgreementAndPrivacy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LoginAgreementAndPrivacy[] newArray(int i) {
            return new LoginAgreementAndPrivacy[i];
        }
    };
    public final String clickLineHexColor;
    public final boolean isShowClinkLineUnderLine;
    public final String normalTextHexColor;
    public final ArrayList<PrivacyReportInfo> privacyReportInfoList;
    public final String tripartiteAppAgreementUrl;
    public final String tripartiteAppCustomLicense;
    public final String tripartiteAppPrivacyUrl;
    public final Type type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private LoginAgreementAndPrivacy(Type type, String str, String str2, String str3, boolean z, String str4, String str5, ArrayList<PrivacyReportInfo> arrayList) {
        this.type = type;
        this.tripartiteAppAgreementUrl = str;
        this.tripartiteAppPrivacyUrl = str2;
        this.tripartiteAppCustomLicense = str3;
        this.isShowClinkLineUnderLine = z;
        this.clickLineHexColor = str4;
        this.normalTextHexColor = str5;
        this.privacyReportInfoList = arrayList;
    }

    protected LoginAgreementAndPrivacy(Parcel parcel) {
        this.type = Type.getTypeByValue(parcel.readInt());
        this.tripartiteAppAgreementUrl = parcel.readString();
        this.tripartiteAppPrivacyUrl = parcel.readString();
        this.tripartiteAppCustomLicense = parcel.readString();
        this.isShowClinkLineUnderLine = parcel.readByte() != 0;
        this.clickLineHexColor = parcel.readString();
        this.normalTextHexColor = parcel.readString();
        this.privacyReportInfoList = parcel.readArrayList(getClass().getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type.getValue());
        parcel.writeString(this.tripartiteAppAgreementUrl);
        parcel.writeString(this.tripartiteAppPrivacyUrl);
        parcel.writeString(this.tripartiteAppCustomLicense);
        parcel.writeByte(this.isShowClinkLineUnderLine ? (byte) 1 : (byte) 0);
        parcel.writeString(this.clickLineHexColor);
        parcel.writeString(this.normalTextHexColor);
        parcel.writeList(this.privacyReportInfoList);
    }

    public String toString() {
        return "LoginAgreementAndPrivacy{type=" + this.type + ", tripartiteAppAgreementUrl='" + this.tripartiteAppAgreementUrl + "', tripartiteAppPrivacyUrl='" + this.tripartiteAppPrivacyUrl + "', tripartiteAppCustomLicense='" + this.tripartiteAppCustomLicense + "', isShowClinkLineUnderLine=" + this.isShowClinkLineUnderLine + ", clickLineHexColor='" + this.clickLineHexColor + "', normalTextHexColor='" + this.normalTextHexColor + "', privacyReportInfoList='" + this.privacyReportInfoList + "'}";
    }

    public static class Builder {
        private String clickLineHexColor;
        private String normalTextHexColor;
        private String tripartiteAppAgreementUrl;
        private String tripartiteAppCustomLicense;
        private String tripartiteAppPrivacyUrl;
        private final Type type;
        private boolean isShowClinkLineUnderLine = true;
        private final ArrayList<PrivacyReportInfo> privacyReportInfoList = new ArrayList<>();

        public Builder(Type type) {
            this.type = type;
        }

        public Builder setTripartiteAppAgreementUrl(String str) {
            this.tripartiteAppAgreementUrl = str;
            return this;
        }

        public Builder setTripartiteAppPrivacyUrl(String str) {
            this.tripartiteAppPrivacyUrl = str;
            return this;
        }

        public Builder setTripartiteAppCustomLicense(String str) {
            this.tripartiteAppCustomLicense = str;
            return this;
        }

        public Builder setIsShowClinkLineUnderLine(boolean z) {
            this.isShowClinkLineUnderLine = z;
            return this;
        }

        public Builder setClickLineHexColor(String str) {
            this.clickLineHexColor = str;
            return this;
        }

        public Builder setNormalTextHexColor(String str) {
            this.normalTextHexColor = str;
            return this;
        }

        public Builder addPrivacyReportInfo(PrivacyReportInfo privacyReportInfo) {
            this.privacyReportInfoList.add(privacyReportInfo);
            return this;
        }

        public LoginAgreementAndPrivacy build() {
            return new LoginAgreementAndPrivacy(this.type, this.tripartiteAppAgreementUrl, this.tripartiteAppPrivacyUrl, this.tripartiteAppCustomLicense, this.isShowClinkLineUnderLine, this.clickLineHexColor, this.normalTextHexColor, this.privacyReportInfoList);
        }
    }

    public static class PrivacyReportInfo implements Parcelable {
        public static final Parcelable.Creator<PrivacyReportInfo> CREATOR = new Parcelable.Creator<PrivacyReportInfo>() { // from class: com.xiaomi.passport.ui.license.LoginAgreementAndPrivacy.PrivacyReportInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PrivacyReportInfo createFromParcel(Parcel parcel) {
                return new PrivacyReportInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PrivacyReportInfo[] newArray(int i) {
                return new PrivacyReportInfo[i];
            }
        };
        public final String apkVersionName;
        public final String idContent;
        public final String packageName;
        public final String policyName;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public PrivacyReportInfo(String str, String str2, String str3, String str4) {
            this.policyName = str;
            this.idContent = str2;
            this.packageName = str3;
            this.apkVersionName = str4;
        }

        protected PrivacyReportInfo(Parcel parcel) {
            this.policyName = parcel.readString();
            this.idContent = parcel.readString();
            this.packageName = parcel.readString();
            this.apkVersionName = parcel.readString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.policyName);
            parcel.writeString(this.idContent);
            parcel.writeString(this.packageName);
            parcel.writeString(this.apkVersionName);
        }

        public String toString() {
            return "PrivacyReportInfo{policyName='" + this.policyName + "', idContent='" + this.idContent + "', packageName='" + this.packageName + "', apkVersionName='" + this.apkVersionName + "'}";
        }
    }

    public enum Type {
        DEF(0),
        APP_JOIN(1),
        APP_CUSTOM(2);

        private final int value;

        Type(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        public static Type getTypeByValue(int i) {
            for (Type type : values()) {
                if (type.getValue() == i) {
                    return type;
                }
            }
            return DEF;
        }
    }
}
