package com.xiaomi.accountsdk.account.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class PasswordLoginParams implements Parcelable {
    private static final String ACTIVATOR_PHONE_INFO = "activatorPhoneInfo";
    protected static final String COUNTRY_CODE = "countryCode";
    public static final Parcelable.Creator<PasswordLoginParams> CREATOR = new Parcelable.Creator<PasswordLoginParams>() { // from class: com.xiaomi.accountsdk.account.data.PasswordLoginParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PasswordLoginParams createFromParcel(Parcel parcel) {
            return new PasswordLoginParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PasswordLoginParams[] newArray(int i) {
            return new PasswordLoginParams[i];
        }
    };
    protected static final String DEVICE_ID = "deviceId";
    protected static final String HASHED_ENV_FACTORS = "hashedEnvFactors";
    protected static final String META_LOGIN_DATA = "metaLoginData";
    protected static final String NEED_PROCESS_NOTIFICATION = "needProcessNotification";
    protected static final String RETURN_STS_URL = "returnStsUrl";
    protected static final String TICKET_TOKEN = "ticketToken";
    public ActivatorPhoneInfo activatorPhoneInfo;
    public final String captCode;
    public final String captIck;
    public String countryCode;
    public String deviceId;
    public String[] hashedEnvFactors;
    public MetaLoginData metaLoginData;
    public boolean needProcessNotification;
    public final String password;
    public boolean returnStsUrl;
    public final String serviceId;
    public String ticketToken;
    public final String userId;
    public final String verifyToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private ActivatorPhoneInfo activatorPhoneInfo;
        private String captCode;
        private String captIck;
        private String countryCode;
        private String deviceId;
        private String[] hashedEnvFactors;
        private MetaLoginData metaLoginData;
        private String password;
        private String serviceId;
        private String ticketToken;
        private String userId;
        private String verifyToken;
        private boolean returnStsUrl = false;
        private boolean needProcessNotification = true;

        public Builder setUserId(String str) {
            this.userId = str;
            return this;
        }

        public Builder setPassword(String str) {
            this.password = str;
            return this;
        }

        public Builder setServiceId(String str) {
            this.serviceId = str;
            return this;
        }

        public Builder setVerifyToken(String str) {
            this.verifyToken = str;
            return this;
        }

        public Builder setCaptCode(String str) {
            this.captCode = str;
            return this;
        }

        public Builder setCaptIck(String str) {
            this.captIck = str;
            return this;
        }

        public Builder setDeviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder setTicketToken(String str) {
            this.ticketToken = str;
            return this;
        }

        public Builder setMetaLoginData(MetaLoginData metaLoginData) {
            this.metaLoginData = metaLoginData;
            return this;
        }

        public Builder setIsReturnStsUrl(boolean z) {
            this.returnStsUrl = z;
            return this;
        }

        public Builder setNeedProcessNotification(boolean z) {
            this.needProcessNotification = z;
            return this;
        }

        public Builder setHashedEnvFactors(String[] strArr) {
            this.hashedEnvFactors = strArr;
            return this;
        }

        public Builder setActivatorPhone(ActivatorPhoneInfo activatorPhoneInfo) {
            this.activatorPhoneInfo = activatorPhoneInfo;
            return this;
        }

        public Builder setCountryCode(String str) {
            this.countryCode = str;
            return this;
        }

        public PasswordLoginParams build() {
            return new PasswordLoginParams(this);
        }
    }

    public static Builder copyFrom(PasswordLoginParams passwordLoginParams) {
        if (passwordLoginParams == null) {
            return null;
        }
        return new Builder().setUserId(passwordLoginParams.userId).setPassword(passwordLoginParams.password).setServiceId(passwordLoginParams.serviceId).setVerifyToken(passwordLoginParams.verifyToken).setCaptCode(passwordLoginParams.captCode).setCaptIck(passwordLoginParams.captIck).setDeviceId(passwordLoginParams.deviceId).setTicketToken(passwordLoginParams.ticketToken).setMetaLoginData(passwordLoginParams.metaLoginData).setIsReturnStsUrl(passwordLoginParams.returnStsUrl).setNeedProcessNotification(passwordLoginParams.needProcessNotification).setHashedEnvFactors(passwordLoginParams.hashedEnvFactors).setCountryCode(passwordLoginParams.countryCode).setActivatorPhone(passwordLoginParams.activatorPhoneInfo);
    }

    private PasswordLoginParams(Builder builder) {
        this.userId = builder.userId;
        this.password = builder.password;
        this.serviceId = builder.serviceId;
        this.verifyToken = builder.verifyToken;
        this.captCode = builder.captCode;
        this.captIck = builder.captIck;
        this.deviceId = builder.deviceId;
        this.ticketToken = builder.ticketToken;
        this.metaLoginData = builder.metaLoginData;
        this.returnStsUrl = builder.returnStsUrl;
        this.needProcessNotification = builder.needProcessNotification;
        this.hashedEnvFactors = builder.hashedEnvFactors;
        this.activatorPhoneInfo = builder.activatorPhoneInfo;
        this.countryCode = builder.countryCode;
    }

    public PasswordLoginParams(Parcel parcel) {
        this.userId = parcel.readString();
        this.password = parcel.readString();
        this.serviceId = parcel.readString();
        this.verifyToken = parcel.readString();
        this.captCode = parcel.readString();
        this.captIck = parcel.readString();
        Bundle bundle = parcel.readBundle();
        if (bundle != null) {
            this.deviceId = bundle.getString("deviceId");
            this.ticketToken = bundle.getString(TICKET_TOKEN);
            this.metaLoginData = (MetaLoginData) bundle.getParcelable(META_LOGIN_DATA);
            this.returnStsUrl = bundle.getBoolean(RETURN_STS_URL, false);
            this.needProcessNotification = bundle.getBoolean(NEED_PROCESS_NOTIFICATION, true);
            this.hashedEnvFactors = bundle.getStringArray(HASHED_ENV_FACTORS);
            this.activatorPhoneInfo = (ActivatorPhoneInfo) bundle.getParcelable(ACTIVATOR_PHONE_INFO);
            this.countryCode = bundle.getString(COUNTRY_CODE);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.password);
        parcel.writeString(this.serviceId);
        parcel.writeString(this.verifyToken);
        parcel.writeString(this.captCode);
        parcel.writeString(this.captIck);
        Bundle bundle = new Bundle();
        bundle.putString("deviceId", this.deviceId);
        bundle.putString(TICKET_TOKEN, this.ticketToken);
        bundle.putParcelable(META_LOGIN_DATA, this.metaLoginData);
        bundle.putBoolean(RETURN_STS_URL, this.returnStsUrl);
        bundle.putBoolean(NEED_PROCESS_NOTIFICATION, this.needProcessNotification);
        bundle.putStringArray(HASHED_ENV_FACTORS, this.hashedEnvFactors);
        bundle.putParcelable(ACTIVATOR_PHONE_INFO, this.activatorPhoneInfo);
        bundle.putString(COUNTRY_CODE, this.countryCode);
        parcel.writeBundle(bundle);
    }
}
