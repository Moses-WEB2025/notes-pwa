package com.xiaomi.accountsdk.account.data;

import android.app.Application;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.XMPassportSettings;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneTokenRegisterParams implements Parcelable {
    public static final Parcelable.Creator<PhoneTokenRegisterParams> CREATOR = new Parcelable.Creator<PhoneTokenRegisterParams>() { // from class: com.xiaomi.accountsdk.account.data.PhoneTokenRegisterParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneTokenRegisterParams createFromParcel(Parcel parcel) {
            Bundle bundle = parcel.readBundle();
            if (bundle == null) {
                return null;
            }
            String string = bundle.getString("phone");
            String string2 = bundle.getString("password");
            String string3 = bundle.getString(PhoneTokenRegisterParams.KEY_TICKET_TOKEN);
            ActivatorPhoneInfo activatorPhoneInfo = (ActivatorPhoneInfo) bundle.getParcelable(PhoneTokenRegisterParams.KEY_ACTIVATOR_PHONE_INFO);
            String string4 = bundle.getString("region");
            return new Builder().phoneTicketToken(string, string3).phoneHashActivatorToken(activatorPhoneInfo).password(string2).region(string4).serviceId(bundle.getString("service_id")).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneTokenRegisterParams[] newArray(int i) {
            return new PhoneTokenRegisterParams[0];
        }
    };
    private static final String KEY_ACTIVATOR_PHONE_INFO = "activator_phone_info";
    private static final String KEY_IS_NO_PASSWORD = "is_no_password";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_REGION = "region";
    private static final String KEY_SERVICE_ID = "service_id";
    private static final String KEY_TICKET_TOKEN = "ticket_token";
    public final ActivatorPhoneInfo activatorPhoneInfo;
    public final String activatorToken;
    public final boolean noPwd;
    public final String password;
    public final String phone;
    public final String phoneHash;
    public final String region;
    public final String serviceId;
    public final String ticketToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PhoneTokenRegisterParams(Builder builder) {
        this.phone = builder.phone;
        this.ticketToken = builder.ticketToken;
        ActivatorPhoneInfo activatorPhoneInfo = builder.activatorPhoneInfo;
        this.activatorPhoneInfo = activatorPhoneInfo;
        this.phoneHash = activatorPhoneInfo != null ? activatorPhoneInfo.phoneHash : null;
        this.activatorToken = activatorPhoneInfo != null ? activatorPhoneInfo.activatorToken : null;
        this.password = builder.password;
        this.noPwd = builder.noPwd;
        this.region = builder.region;
        this.serviceId = builder.serviceId;
    }

    public static Builder copyFrom(PhoneTokenRegisterParams phoneTokenRegisterParams) {
        if (phoneTokenRegisterParams == null) {
            return null;
        }
        return new Builder().phoneTicketToken(phoneTokenRegisterParams.phone, phoneTokenRegisterParams.ticketToken).phoneHashActivatorToken(phoneTokenRegisterParams.activatorPhoneInfo).password(phoneTokenRegisterParams.password).region(phoneTokenRegisterParams.region).serviceId(phoneTokenRegisterParams.serviceId);
    }

    public static class Builder {
        private ActivatorPhoneInfo activatorPhoneInfo;
        private boolean noPwd;
        private String password;
        private String phone;
        private String region;
        private String serviceId;
        private String ticketToken;

        public Builder phoneTicketToken(String str, String str2) {
            this.phone = str;
            this.ticketToken = str2;
            return this;
        }

        public Builder phoneHashActivatorToken(ActivatorPhoneInfo activatorPhoneInfo) {
            this.activatorPhoneInfo = activatorPhoneInfo;
            return this;
        }

        public Builder password(String str) {
            this.password = str;
            return this;
        }

        public Builder region(String str) {
            this.region = str;
            return this;
        }

        public Builder application(Application application) {
            XMPassportSettings.setApplicationContext(application);
            return this;
        }

        public Builder serviceId(String str) {
            this.serviceId = str;
            return this;
        }

        public PhoneTokenRegisterParams build() {
            this.noPwd = TextUtils.isEmpty(this.password);
            return new PhoneTokenRegisterParams(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("phone", this.phone);
        bundle.putString(KEY_TICKET_TOKEN, this.ticketToken);
        bundle.putParcelable(KEY_ACTIVATOR_PHONE_INFO, this.activatorPhoneInfo);
        bundle.putString("password", this.password);
        bundle.putString("region", this.region);
        bundle.putBoolean(KEY_IS_NO_PASSWORD, this.noPwd);
        bundle.putString("password", this.password);
        bundle.putString("region", this.region);
        bundle.putString("service_id", this.serviceId);
        parcel.writeBundle(bundle);
    }
}
