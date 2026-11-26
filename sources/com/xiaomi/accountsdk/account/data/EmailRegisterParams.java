package com.xiaomi.accountsdk.account.data;

import android.app.Application;
import com.xiaomi.accountsdk.account.XMPassportSettings;

/* JADX INFO: loaded from: classes4.dex */
public class EmailRegisterParams {
    public final String captCode;
    public final String captIck;
    public final String emailAddress;
    public final String password;
    public final String region;
    public final String serviceId;
    public final String verifyToken;

    public EmailRegisterParams(Builder builder) {
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
        this.verifyToken = builder.verifyToken;
        this.captCode = builder.captCode;
        this.captIck = builder.captIck;
        this.region = builder.region;
        this.serviceId = builder.serviceId;
    }

    public static final class Builder {
        private String captCode;
        private String captIck;
        private String emailAddress;
        private String password;
        private String region;
        private String serviceId;
        private String verifyToken;

        public Builder email(String str) {
            this.emailAddress = str;
            return this;
        }

        public Builder password(String str) {
            this.password = str;
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

        public EmailRegisterParams build() {
            return new EmailRegisterParams(this);
        }
    }
}
