package com.xiaomi.accountsdk.account.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneTicketLoginParams implements Parcelable {
    public static final Parcelable.Creator<PhoneTicketLoginParams> CREATOR = new Parcelable.Creator<PhoneTicketLoginParams>() { // from class: com.xiaomi.accountsdk.account.data.PhoneTicketLoginParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneTicketLoginParams createFromParcel(Parcel parcel) {
            Bundle bundle = parcel.readBundle();
            if (bundle == null) {
                return null;
            }
            String string = bundle.getString("phone");
            String string2 = bundle.getString(PhoneTicketLoginParams.KEY_TICKET_TOKEN);
            ActivatorPhoneInfo activatorPhoneInfo = (ActivatorPhoneInfo) bundle.getParcelable(PhoneTicketLoginParams.KEY_ACTIVATOR_PHONE_INFO);
            return new Builder().phoneTicketToken(string, string2).verifiedActivatorPhone(activatorPhoneInfo).activatorPhoneTicket(activatorPhoneInfo, bundle.getString(PhoneTicketLoginParams.KEY_TICKET)).deviceId(bundle.getString("device_id")).serviceId(bundle.getString("service_id")).hashedEnvFactors(bundle.getStringArray(PhoneTicketLoginParams.KEY_HASH_ENV)).returnStsUrl(bundle.getBoolean("return_sts_url", false)).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneTicketLoginParams[] newArray(int i) {
            return new PhoneTicketLoginParams[0];
        }
    };
    private static final String KEY_ACTIVATOR_PHONE_INFO = "activator_phone_info";
    private static final String KEY_DEVICE_ID = "device_id";
    private static final String KEY_HASH_ENV = "hash_env";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_RETURN_STS_URL = "return_sts_url";
    private static final String KEY_SERVICE_ID = "service_id";
    private static final String KEY_TICKET = "ticket";
    private static final String KEY_TICKET_TOKEN = "ticket_token";
    public final ActivatorPhoneInfo activatorPhoneInfo;
    public final String activatorToken;
    public final String deviceId;
    public final String[] hashedEnvFactors;
    public final String phone;
    public final String phoneHash;
    public final boolean returnStsUrl;
    public final String serviceId;
    public final String ticket;
    public final String ticketToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PhoneTicketLoginParams(Builder builder) {
        this.phone = builder.phone;
        this.ticketToken = builder.ticketToken;
        ActivatorPhoneInfo activatorPhoneInfo = builder.activatorPhoneInfo;
        this.activatorPhoneInfo = activatorPhoneInfo;
        this.phoneHash = activatorPhoneInfo != null ? activatorPhoneInfo.phoneHash : null;
        this.activatorToken = activatorPhoneInfo != null ? activatorPhoneInfo.activatorToken : null;
        this.ticket = builder.ticket;
        this.deviceId = builder.deviceId;
        this.serviceId = builder.serviceId;
        this.hashedEnvFactors = builder.hashedEnvFactors;
        this.returnStsUrl = builder.returnStsUrl;
    }

    public static Builder copyFrom(PhoneTicketLoginParams phoneTicketLoginParams) {
        if (phoneTicketLoginParams == null) {
            return null;
        }
        return new Builder().phoneTicketToken(phoneTicketLoginParams.phone, phoneTicketLoginParams.ticketToken).verifiedActivatorPhone(phoneTicketLoginParams.activatorPhoneInfo).activatorPhoneTicket(phoneTicketLoginParams.activatorPhoneInfo, phoneTicketLoginParams.ticket).deviceId(phoneTicketLoginParams.deviceId).serviceId(phoneTicketLoginParams.serviceId).hashedEnvFactors(phoneTicketLoginParams.hashedEnvFactors).returnStsUrl(phoneTicketLoginParams.returnStsUrl);
    }

    public static class Builder {
        private ActivatorPhoneInfo activatorPhoneInfo;
        private String deviceId;
        private String[] hashedEnvFactors;
        private String phone;
        private boolean returnStsUrl = false;
        private String serviceId;
        private String ticket;
        private String ticketToken;

        public Builder phoneTicketToken(String str, String str2) {
            this.phone = str;
            this.ticketToken = str2;
            return this;
        }

        public Builder verifiedActivatorPhone(ActivatorPhoneInfo activatorPhoneInfo) {
            this.activatorPhoneInfo = activatorPhoneInfo;
            return this;
        }

        public Builder activatorPhoneTicket(ActivatorPhoneInfo activatorPhoneInfo, String str) {
            this.activatorPhoneInfo = activatorPhoneInfo;
            this.ticket = str;
            return this;
        }

        public Builder deviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder serviceId(String str) {
            this.serviceId = str;
            return this;
        }

        public Builder hashedEnvFactors(String[] strArr) {
            this.hashedEnvFactors = strArr;
            return this;
        }

        public Builder returnStsUrl(boolean z) {
            this.returnStsUrl = z;
            return this;
        }

        public PhoneTicketLoginParams build() {
            return new PhoneTicketLoginParams(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("phone", this.phone);
        bundle.putString(KEY_TICKET_TOKEN, this.ticketToken);
        bundle.putParcelable(KEY_ACTIVATOR_PHONE_INFO, this.activatorPhoneInfo);
        bundle.putString(KEY_TICKET, this.ticket);
        bundle.putString("device_id", this.deviceId);
        bundle.putString("service_id", this.serviceId);
        bundle.putStringArray(KEY_HASH_ENV, this.hashedEnvFactors);
        bundle.putBoolean("return_sts_url", this.returnStsUrl);
        parcel.writeBundle(bundle);
    }
}
