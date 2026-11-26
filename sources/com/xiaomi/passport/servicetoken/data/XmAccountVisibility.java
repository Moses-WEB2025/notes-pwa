package com.xiaomi.passport.servicetoken.data;

import android.accounts.Account;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
public class XmAccountVisibility implements Parcelable {
    public static final Parcelable.Creator<XmAccountVisibility> CREATOR = new Parcelable.Creator<XmAccountVisibility>() { // from class: com.xiaomi.passport.servicetoken.data.XmAccountVisibility.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public XmAccountVisibility createFromParcel(Parcel parcel) {
            return new XmAccountVisibility(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public XmAccountVisibility[] newArray(int i) {
            return new XmAccountVisibility[0];
        }
    };
    private static final String KEY_ACCOUNT = "account";
    private static final String KEY_ACCOUNT_VISIBLE = "visible";
    private static final String KEY_BUILD_SDK_VERSION = "build_sdk_version";
    private static final String KEY_ERROR_CODE = "error_code";
    private static final String KEY_ERROR_MSG = "error_msg";
    private static final String KEY_NEW_CHOOSE_ACCOUNT_INTENT = "new_choose_account_intent";
    public final Account account;
    public final int buildSdkVersion;
    public final ErrorCode errorCode;
    public final String errorMsg;
    public final Intent newChooseAccountIntent;
    public final boolean visible;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public enum ErrorCode {
        ERROR_NONE("successful"),
        ERROR_NOT_SUPPORT("no support account service"),
        ERROR_PRE_ANDROID_O("no support account service, and pre o version"),
        ERROR_NO_ACCOUNT("no account"),
        ERROR_NO_PERMISSION("no access account service permission"),
        ERROR_CANCELLED("task cancelled"),
        ERROR_EXECUTION("execution error"),
        ERROR_UNKNOWN("unknown");

        String errorMsg;

        ErrorCode(String str) {
            this.errorMsg = str;
        }
    }

    public static class Builder {
        private Account account;
        private int buildSdkVersion = Build.VERSION.SDK_INT;
        private final ErrorCode errorCode;
        private final String errorMsg;
        private Intent newChooseAccountIntent;
        private boolean visible;

        public Builder(ErrorCode errorCode, String str) {
            this.errorCode = errorCode;
            this.errorMsg = TextUtils.isEmpty(str) ? errorCode.errorMsg : str;
        }

        public Builder accountVisible(boolean z, Account account) {
            this.visible = z;
            this.account = account;
            return this;
        }

        public Builder newChooseAccountIntent(Intent intent) {
            this.newChooseAccountIntent = intent;
            return this;
        }

        public XmAccountVisibility build() {
            return new XmAccountVisibility(this);
        }
    }

    public XmAccountVisibility(Builder builder) {
        this.errorCode = builder.errorCode;
        this.errorMsg = builder.errorMsg;
        this.visible = builder.visible;
        this.account = builder.account;
        this.buildSdkVersion = builder.buildSdkVersion;
        this.newChooseAccountIntent = builder.newChooseAccountIntent;
    }

    public XmAccountVisibility(Parcel parcel) {
        Bundle bundle = parcel.readBundle();
        this.errorCode = ErrorCode.values()[bundle.getInt("error_code")];
        this.errorMsg = bundle.getString(KEY_ERROR_MSG);
        this.visible = bundle.getBoolean(KEY_ACCOUNT_VISIBLE);
        this.account = (Account) bundle.getParcelable("account");
        this.buildSdkVersion = bundle.getInt(KEY_BUILD_SDK_VERSION);
        this.newChooseAccountIntent = (Intent) bundle.getParcelable(KEY_NEW_CHOOSE_ACCOUNT_INTENT);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("error_code", this.errorCode.ordinal());
        bundle.putString(KEY_ERROR_MSG, this.errorMsg);
        bundle.putBoolean(KEY_ACCOUNT_VISIBLE, this.visible);
        bundle.putParcelable("account", this.account);
        bundle.putInt(KEY_BUILD_SDK_VERSION, this.buildSdkVersion);
        bundle.putParcelable(KEY_NEW_CHOOSE_ACCOUNT_INTENT, this.newChooseAccountIntent);
        parcel.writeBundle(bundle);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("AccountVisibility{, errorCode=");
        stringBuffer.append(this.errorCode);
        stringBuffer.append(", errorMessage='").append(this.errorMsg).append("', accountVisible='");
        stringBuffer.append(this.visible).append("'}");
        return stringBuffer.toString();
    }
}
