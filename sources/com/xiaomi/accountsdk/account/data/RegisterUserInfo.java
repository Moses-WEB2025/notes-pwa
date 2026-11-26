package com.xiaomi.accountsdk.account.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class RegisterUserInfo implements Parcelable {
    public static final Parcelable.Creator<RegisterUserInfo> CREATOR = new Parcelable.Creator<RegisterUserInfo>() { // from class: com.xiaomi.accountsdk.account.data.RegisterUserInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RegisterUserInfo createFromParcel(Parcel parcel) {
            Bundle bundle = parcel.readBundle();
            if (bundle == null) {
                return null;
            }
            return new Builder(bundle.getInt(RegisterUserInfo.KEY_REGISTER_STATUS)).userId(bundle.getString(RegisterUserInfo.KEY_USER_ID)).userName(bundle.getString(RegisterUserInfo.KEY_USER_NAME)).avatarAddress(bundle.getString(RegisterUserInfo.KEY_AVATAR_ADDRESS)).ticketToken(bundle.getString(RegisterUserInfo.KEY_TICKET_TOKEN)).phone(bundle.getString("phone")).maskedUserId(bundle.getString(RegisterUserInfo.KEY_MASKED_USER_ID)).hasPwd(bundle.getBoolean(RegisterUserInfo.KEY_HAS_PASSWORD)).bindTime(bundle.getLong(RegisterUserInfo.KEY_BIND_TIME)).needToast(bundle.getBoolean(RegisterUserInfo.KEY_NEED_TOAST)).needGetActiveTime(bundle.getBoolean(RegisterUserInfo.KEY_NEED_GET_ACTIVE_TIME)).registerPwd(bundle.getBoolean(RegisterUserInfo.KEY_REGISTER_PWD)).tmpPhoneToken(bundle.getString(RegisterUserInfo.KEY_TMP_PHONE_TOKEN)).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RegisterUserInfo[] newArray(int i) {
            return new RegisterUserInfo[0];
        }
    };
    private static final String KEY_AVATAR_ADDRESS = "avatar_address";
    private static final String KEY_BIND_TIME = "bind_time";
    private static final String KEY_HAS_PASSWORD = "has_pwd";
    private static final String KEY_MASKED_USER_ID = "masked_user_id";
    private static final String KEY_NEED_GET_ACTIVE_TIME = "need_get_active_time";
    private static final String KEY_NEED_TOAST = "need_toast";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_REGISTER_PWD = "register_pwd";
    private static final String KEY_REGISTER_STATUS = "register_status";
    private static final String KEY_TICKET_TOKEN = "ticket_token";
    private static final String KEY_TMP_PHONE_TOKEN = "tmp_phone_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";

    @Deprecated
    public static final int STATUS_NOT_REGISTERED = 0;

    @Deprecated
    public static final int STATUS_USED_NOT_RECYCLED = 2;

    @Deprecated
    public static final int STATUS_USED_POSSIBLY_RECYCLED = 1;
    public final String avatarAddress;
    public final long bindTime;
    public final boolean hasPwd;
    public final String maskedUserId;
    public final boolean needGetActiveTime;
    public final boolean needToast;
    public final String phone;
    public final boolean registerPwd;
    public final RegisterStatus status;
    public final String ticketToken;
    public final String tmpPhoneToken;
    public final String userId;
    public final String userName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public enum RegisterStatus {
        STATUS_NOT_REGISTERED(0),
        STATUS_USED_POSSIBLY_RECYCLED(1),
        STATUS_REGISTERED_NOT_RECYCLED(2);

        public final int value;

        RegisterStatus(int i) {
            this.value = i;
        }

        public static RegisterStatus getInstance(int i) {
            for (RegisterStatus registerStatus : values()) {
                if (i == registerStatus.value) {
                    return registerStatus;
                }
            }
            AccountLogger.log("RegisterStatus", "has not this status value: " + i);
            return null;
        }
    }

    @Deprecated
    public RegisterUserInfo(int i, String str, String str2, String str3, String str4) {
        this.status = RegisterStatus.getInstance(i);
        this.userId = str;
        this.userName = str2;
        this.avatarAddress = str3;
        this.ticketToken = str4;
        this.phone = null;
        this.maskedUserId = null;
        this.hasPwd = false;
        this.bindTime = -1L;
        this.needGetActiveTime = false;
        this.needToast = false;
        this.registerPwd = true;
        this.tmpPhoneToken = null;
    }

    private RegisterUserInfo(Builder builder) {
        this.status = RegisterStatus.getInstance(builder.status);
        this.userId = builder.userId;
        this.userName = builder.userName;
        this.avatarAddress = builder.avatarAddress;
        this.ticketToken = builder.ticketToken;
        this.phone = builder.phone;
        this.maskedUserId = builder.maskedUserId;
        this.hasPwd = builder.hasPwd;
        this.bindTime = builder.bindTime;
        this.needGetActiveTime = builder.needGetActiveTime;
        this.needToast = builder.needToast;
        this.registerPwd = builder.registerPwd;
        this.tmpPhoneToken = builder.tmpPhoneToken;
    }

    @Deprecated
    public int getStatus() {
        return this.status.value;
    }

    @Deprecated
    public String getUserName() {
        return this.userName;
    }

    @Deprecated
    public String getAvartarAddress() {
        return this.avatarAddress;
    }

    @Deprecated
    public String getUserId() {
        return this.userId;
    }

    @Deprecated
    public String getTicketToken() {
        return this.ticketToken;
    }

    public static Builder copyFrom(RegisterUserInfo registerUserInfo) {
        if (registerUserInfo == null) {
            return null;
        }
        return new Builder(registerUserInfo.status.value).userId(registerUserInfo.userId).userName(registerUserInfo.userName).avatarAddress(registerUserInfo.avatarAddress).ticketToken(registerUserInfo.ticketToken).phone(registerUserInfo.phone).tmpPhoneToken(registerUserInfo.tmpPhoneToken).maskedUserId(registerUserInfo.maskedUserId).hasPwd(registerUserInfo.hasPwd).bindTime(registerUserInfo.bindTime).needGetActiveTime(registerUserInfo.needGetActiveTime).needToast(registerUserInfo.needToast);
    }

    public static class Builder {
        private String avatarAddress;
        private long bindTime;
        private boolean hasPwd;
        private String maskedUserId;
        private boolean needGetActiveTime;
        private boolean needToast;
        private String phone;
        private boolean registerPwd;
        private int status;
        private String ticketToken;
        private String tmpPhoneToken;
        private String userId;
        private String userName;

        public Builder(int i) {
            this.status = i;
        }

        public Builder status(int i) {
            this.status = i;
            return this;
        }

        public Builder userId(String str) {
            this.userId = str;
            return this;
        }

        public Builder userName(String str) {
            this.userName = str;
            return this;
        }

        public Builder avatarAddress(String str) {
            this.avatarAddress = str;
            return this;
        }

        public Builder ticketToken(String str) {
            this.ticketToken = str;
            return this;
        }

        public Builder phone(String str) {
            this.phone = str;
            return this;
        }

        public Builder maskedUserId(String str) {
            this.maskedUserId = str;
            return this;
        }

        public Builder hasPwd(boolean z) {
            this.hasPwd = z;
            return this;
        }

        public Builder bindTime(long j) {
            this.bindTime = j;
            return this;
        }

        public Builder needGetActiveTime(boolean z) {
            this.needGetActiveTime = z;
            return this;
        }

        public Builder needToast(boolean z) {
            this.needToast = z;
            return this;
        }

        public Builder registerPwd(boolean z) {
            this.registerPwd = z;
            return this;
        }

        public Builder tmpPhoneToken(String str) {
            this.tmpPhoneToken = str;
            return this;
        }

        public RegisterUserInfo build() {
            return new RegisterUserInfo(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_REGISTER_STATUS, this.status.value);
        bundle.putString(KEY_USER_ID, this.userId);
        bundle.putString(KEY_USER_NAME, this.userName);
        bundle.putString(KEY_AVATAR_ADDRESS, this.avatarAddress);
        bundle.putString(KEY_TICKET_TOKEN, this.ticketToken);
        bundle.putString("phone", this.phone);
        bundle.putString(KEY_MASKED_USER_ID, this.maskedUserId);
        bundle.putBoolean(KEY_HAS_PASSWORD, this.hasPwd);
        bundle.putLong(KEY_BIND_TIME, this.bindTime);
        bundle.putBoolean(KEY_NEED_TOAST, this.needToast);
        bundle.putBoolean(KEY_NEED_GET_ACTIVE_TIME, this.needGetActiveTime);
        bundle.putBoolean(KEY_REGISTER_PWD, this.registerPwd);
        bundle.putString(KEY_TMP_PHONE_TOKEN, this.tmpPhoneToken);
        parcel.writeBundle(bundle);
    }
}
