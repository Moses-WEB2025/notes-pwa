package com.xiaomi.accountsdk.account.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;

/* JADX INFO: loaded from: classes4.dex */
public class MiLoginResult implements Parcelable {
    public static final Parcelable.Creator<MiLoginResult> CREATOR = new Parcelable.Creator<MiLoginResult>() { // from class: com.xiaomi.accountsdk.account.data.MiLoginResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiLoginResult createFromParcel(Parcel parcel) {
            return new MiLoginResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiLoginResult[] newArray(int i) {
            return new MiLoginResult[i];
        }
    };
    public static final int ERROR_ACCESS_DENIED = 7;
    public static final int ERROR_CAPTCHA = 1;
    public static final int ERROR_ILLEGAL_DEVICE_ID = 9;
    public static final int ERROR_NEED_NOTIFICATION = 3;
    public static final int ERROR_NEED_STEP2_LOGIN = 2;
    public static final int ERROR_NETWORK = 5;
    public static final int ERROR_PASSWORD = 4;
    public static final int ERROR_REMOTE_FATAL_ERROR = 13;
    public static final int ERROR_SERVER = 6;
    public static final int ERROR_SSL = 10;

    @Deprecated
    public static final int ERROR_SSL_HAND_SHAKE = 10;
    public static final int ERROR_STEP2_CODE = 11;
    public static final int ERROR_UNKNOWN = 12;
    public static final int ERROR_USER_NAME = 8;
    private static final String KEY_HAS_PWD = "has_pwd";
    private static final String KEY_STS_ERROR = "sts_error";
    public static final int SUCCESS = 0;
    public final AccountInfo accountInfo;
    public final String captchaType;
    public final String captchaUrl;
    public final boolean hasPwd;
    public boolean isStsCallbackError;
    public final MetaLoginData metaLoginData;
    public final String notificationUrl;
    public final int resultCode;
    public PassThroughErrorInfo serverError;
    public final String serviceId;
    public final String step1Token;
    public final String userId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MiLoginResult(Parcel parcel) {
        this.userId = parcel.readString();
        this.serviceId = parcel.readString();
        this.accountInfo = (AccountInfo) parcel.readParcelable(AccountInfo.class.getClassLoader());
        this.captchaUrl = parcel.readString();
        this.captchaType = parcel.readString();
        this.notificationUrl = parcel.readString();
        this.metaLoginData = (MetaLoginData) parcel.readParcelable(MetaLoginData.class.getClassLoader());
        this.step1Token = parcel.readString();
        this.resultCode = parcel.readInt();
        Bundle bundle = parcel.readBundle();
        this.hasPwd = bundle != null ? bundle.getBoolean(KEY_HAS_PWD) : true;
        this.isStsCallbackError = bundle != null ? bundle.getBoolean(KEY_STS_ERROR) : false;
        this.serverError = (PassThroughErrorInfo) parcel.readParcelable(PassThroughErrorInfo.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.serviceId);
        parcel.writeParcelable(this.accountInfo, i);
        parcel.writeString(this.captchaUrl);
        parcel.writeString(this.captchaType);
        parcel.writeString(this.notificationUrl);
        parcel.writeParcelable(this.metaLoginData, i);
        parcel.writeString(this.step1Token);
        parcel.writeInt(this.resultCode);
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_HAS_PWD, this.hasPwd);
        bundle.putBoolean(KEY_STS_ERROR, this.isStsCallbackError);
        parcel.writeBundle(bundle);
        parcel.writeParcelable(this.serverError, i);
    }

    private MiLoginResult(Builder builder) {
        this.userId = builder.userId;
        this.serviceId = builder.serviceId;
        this.accountInfo = builder.accountInfo;
        this.captchaUrl = builder.captchaUrl;
        this.captchaType = builder.captchaType;
        this.notificationUrl = builder.notificationUrl;
        this.metaLoginData = builder.metaLoginData;
        this.step1Token = builder.step1Token;
        this.resultCode = builder.resultCode;
        this.hasPwd = builder.hasPwd;
        this.isStsCallbackError = builder.isStsCallbackError;
        this.serverError = builder.serverError;
    }

    public static class Builder {
        private AccountInfo accountInfo;
        private String captchaType;
        private String captchaUrl;
        private boolean hasPwd;
        private boolean isStsCallbackError;
        private MetaLoginData metaLoginData;
        private String notificationUrl;
        private int resultCode;
        private PassThroughErrorInfo serverError;
        private final String serviceId;
        private String step1Token;
        private final String userId;

        public Builder(String str, String str2) {
            this.userId = str;
            this.serviceId = str2;
        }

        public Builder setAccountInfo(AccountInfo accountInfo) {
            this.accountInfo = accountInfo;
            return this;
        }

        public Builder setMetaLoginData(MetaLoginData metaLoginData) {
            this.metaLoginData = metaLoginData;
            return this;
        }

        public Builder setStep1Token(String str) {
            this.step1Token = str;
            return this;
        }

        public Builder setCaptchaUrl(String str) {
            this.captchaUrl = str;
            return this;
        }

        public Builder setCaptchaType(String str) {
            this.captchaType = str;
            return this;
        }

        public Builder setNotificationUrl(String str) {
            this.notificationUrl = str;
            return this;
        }

        public Builder setResultCode(int i) {
            this.resultCode = i;
            return this;
        }

        public Builder setHasPwd(boolean z) {
            this.hasPwd = z;
            return this;
        }

        public Builder setIsStsCallbackError(boolean z) {
            this.isStsCallbackError = z;
            return this;
        }

        public Builder setServerError(PassThroughErrorInfo passThroughErrorInfo) {
            this.serverError = passThroughErrorInfo;
            return this;
        }

        public MiLoginResult build() {
            return new MiLoginResult(this);
        }
    }
}
