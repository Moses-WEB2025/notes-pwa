package com.xiaomi.accountsdk.account.data;

import android.app.Application;
import com.xiaomi.accountsdk.account.XMPassportSettings;

/* JADX INFO: loaded from: classes4.dex */
public class SendPhoneTicketParams {
    public final String action;
    public final ActivatorPhoneInfo activatorPhoneInfo;
    public final String activatorToken;
    public final String captCode;
    public final String captIck;
    public final String deviceId;
    public final String phone;
    public final String phoneHash;
    public final String region;
    public final String serviceId;
    public final String ticketType;
    public final String token;
    public final String verifyToken;

    private SendPhoneTicketParams(Builder builder) {
        this.phone = builder.phone;
        ActivatorPhoneInfo activatorPhoneInfo = builder.activatorPhoneInfo;
        this.activatorPhoneInfo = activatorPhoneInfo;
        this.phoneHash = activatorPhoneInfo != null ? activatorPhoneInfo.phoneHash : null;
        this.activatorToken = activatorPhoneInfo != null ? activatorPhoneInfo.activatorToken : null;
        this.deviceId = builder.deviceId;
        this.serviceId = builder.serviceId;
        this.verifyToken = builder.verifyToken;
        this.captCode = builder.captCode;
        this.captIck = builder.captIck;
        this.region = builder.region;
        this.token = builder.token;
        this.action = builder.action;
        this.ticketType = builder.ticketType;
    }

    public static Builder copyFrom(SendPhoneTicketParams sendPhoneTicketParams) {
        if (sendPhoneTicketParams == null) {
            return null;
        }
        return new Builder().phone(sendPhoneTicketParams.phone).phoneHashActivatorToken(sendPhoneTicketParams.activatorPhoneInfo).serviceId(sendPhoneTicketParams.serviceId).deviceId(sendPhoneTicketParams.deviceId).verifyToken(sendPhoneTicketParams.verifyToken).captchaCode(sendPhoneTicketParams.captCode, sendPhoneTicketParams.captIck).region(sendPhoneTicketParams.region).action(sendPhoneTicketParams.token).token(sendPhoneTicketParams.action).ticketType(sendPhoneTicketParams.ticketType);
    }

    public static class Builder {
        private String action;
        private ActivatorPhoneInfo activatorPhoneInfo;
        private String captCode;
        private String captIck;
        private String deviceId;
        private String phone;
        private String region;
        private String serviceId;
        private String ticketType;
        private String token;
        private String verifyToken;

        public Builder phone(String str) {
            this.phone = str;
            return this;
        }

        public Builder phoneHashActivatorToken(ActivatorPhoneInfo activatorPhoneInfo) {
            this.activatorPhoneInfo = activatorPhoneInfo;
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

        public Builder verifyToken(String str) {
            this.verifyToken = str;
            return this;
        }

        public Builder captchaCode(String str, String str2) {
            this.captCode = str;
            this.captIck = str2;
            return this;
        }

        public Builder token(String str) {
            this.token = str;
            return this;
        }

        public Builder action(String str) {
            this.action = str;
            return this;
        }

        public Builder ticketType(String str) {
            this.ticketType = str;
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

        public SendPhoneTicketParams build() {
            return new SendPhoneTicketParams(this);
        }
    }
}
