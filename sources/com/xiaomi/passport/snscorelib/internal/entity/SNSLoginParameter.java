package com.xiaomi.passport.snscorelib.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class SNSLoginParameter implements Parcelable {
    public static final Parcelable.Creator<SNSLoginParameter> CREATOR = new Parcelable.Creator<SNSLoginParameter>() { // from class: com.xiaomi.passport.snscorelib.internal.entity.SNSLoginParameter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSLoginParameter createFromParcel(Parcel parcel) {
            return new SNSLoginParameter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSLoginParameter[] newArray(int i) {
            return new SNSLoginParameter[i];
        }
    };
    public final String appid;
    public boolean autoGenerateAccount;
    public final String callback;
    public final String code;
    public final String enToken;
    public final String expires_in;
    public final String openId;
    public String phones;
    public final String region;
    public final String requestStartTime;
    public final String sid;
    public boolean snsQuickLogin;
    public final String snsVersionName;
    public final String token;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SNSLoginParameter(Builder builder) {
        this.code = builder.code;
        this.sid = builder.sid;
        this.callback = builder.callback;
        this.appid = builder.appid;
        this.enToken = builder.enToken;
        this.token = builder.token;
        this.expires_in = builder.expires_in;
        this.openId = builder.openId;
        this.autoGenerateAccount = builder.autoGenerateAccount;
        this.phones = builder.phones;
        this.snsQuickLogin = builder.snsQuickLogin;
        this.region = builder.region;
        this.requestStartTime = builder.requestStartTime;
        this.snsVersionName = builder.snsVersionName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.code);
        parcel.writeString(this.sid);
        parcel.writeString(this.callback);
        parcel.writeString(this.appid);
        parcel.writeString(this.enToken);
        parcel.writeString(this.token);
        parcel.writeString(this.expires_in);
        parcel.writeString(this.openId);
        parcel.writeString(this.phones);
        parcel.writeByte(this.autoGenerateAccount ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.snsQuickLogin ? (byte) 1 : (byte) 0);
        parcel.writeString(this.region);
        parcel.writeString(this.requestStartTime);
        parcel.writeString(this.snsVersionName);
    }

    private SNSLoginParameter(Parcel parcel) {
        this.code = parcel.readString();
        this.sid = parcel.readString();
        this.callback = parcel.readString();
        this.appid = parcel.readString();
        this.enToken = parcel.readString();
        this.token = parcel.readString();
        this.expires_in = parcel.readString();
        this.openId = parcel.readString();
        this.phones = parcel.readString();
        this.autoGenerateAccount = parcel.readByte() != 0;
        this.snsQuickLogin = parcel.readByte() != 0;
        this.region = parcel.readString();
        this.requestStartTime = parcel.readString();
        this.snsVersionName = parcel.readString();
    }

    public static class Builder {
        private String appid;
        private String callback;
        private String code;
        private String enToken;
        private String expires_in;
        private String openId;
        private String phones;
        private String region;
        private String requestStartTime;
        private String sid;
        private String snsVersionName;
        private String token;
        private boolean autoGenerateAccount = true;
        private boolean snsQuickLogin = true;

        public Builder code(String str) {
            this.code = str;
            return this;
        }

        public Builder sid(String str) {
            this.sid = str;
            return this;
        }

        public Builder callback(String str) {
            this.callback = str;
            return this;
        }

        public Builder appid(String str) {
            this.appid = str;
            return this;
        }

        public Builder enToken(String str) {
            this.enToken = str;
            return this;
        }

        public Builder token(String str) {
            this.token = str;
            return this;
        }

        public Builder expires_in(String str) {
            this.expires_in = str;
            return this;
        }

        public Builder openId(String str) {
            this.openId = str;
            return this;
        }

        public Builder phones(String str) {
            this.phones = str;
            return this;
        }

        public Builder region(String str) {
            this.region = str;
            return this;
        }

        public Builder snsVersionName(String str) {
            this.snsVersionName = str;
            return this;
        }

        public Builder requestStartTime(String str) {
            this.requestStartTime = str;
            return this;
        }

        public Builder autoGenerateAccount(boolean z) {
            this.autoGenerateAccount = z;
            return this;
        }

        public Builder snsQuickLogin(boolean z) {
            this.snsQuickLogin = z;
            return this;
        }

        public SNSLoginParameter build() {
            return new SNSLoginParameter(this);
        }
    }
}
