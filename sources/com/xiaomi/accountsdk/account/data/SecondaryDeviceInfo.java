package com.xiaomi.accountsdk.account.data;

import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.exception.RequestSecondaryDeviceLoginDataException;

/* JADX INFO: loaded from: classes4.dex */
public class SecondaryDeviceInfo {
    public final String bindUid;
    public final String bizIdcInfo;
    public final String deviceId;
    public final boolean exceptPwdChange;
    public final String nonce;
    public final String sign;
    public final String ua;

    private SecondaryDeviceInfo(Builder builder) {
        this.ua = builder.ua;
        this.deviceId = builder.deviceId;
        this.nonce = builder.nonce;
        this.sign = builder.sign;
        this.bindUid = builder.bindUid;
        this.exceptPwdChange = builder.exceptPwdChange;
        this.bizIdcInfo = builder.bizIdcInfo;
    }

    public void paramsCheckAndThrow() throws RequestSecondaryDeviceLoginDataException {
        if (TextUtils.isEmpty(this.ua) || TextUtils.isEmpty(this.deviceId) || TextUtils.isEmpty(this.nonce) || TextUtils.isEmpty(this.sign) || TextUtils.isEmpty(this.bindUid)) {
            throw new RequestSecondaryDeviceLoginDataException("ua, deviceId, nonce, sign, bindUid can not be empty");
        }
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("ua", this.ua);
        bundle.putString("deviceId", this.deviceId);
        bundle.putString(com.xiaomi.verificationsdk.internal.Constants.NONCE, this.nonce);
        bundle.putString("sign", this.sign);
        bundle.putString("bindUid", this.bindUid);
        bundle.putBoolean("exceptPwdChange", this.exceptPwdChange);
        bundle.putString("bizIdcInfo", this.bizIdcInfo);
        return bundle;
    }

    public static SecondaryDeviceInfo fromBundle(Bundle bundle) {
        return new Builder().ua(bundle.getString("ua")).deviceId(bundle.getString("deviceId")).nonce(bundle.getString(com.xiaomi.verificationsdk.internal.Constants.NONCE)).sign(bundle.getString("sign")).bindUid(bundle.getString("bindUid")).exceptPwdChange(bundle.getBoolean("exceptPwdChange")).bizIdcInfo(bundle.getString("bizIdcInfo")).build();
    }

    public static class Builder {
        private String bindUid;
        private String bizIdcInfo;
        private String deviceId;
        private boolean exceptPwdChange = true;
        private String nonce;
        private String sign;
        private String ua;

        public Builder ua(String str) {
            this.ua = str;
            return this;
        }

        public Builder deviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder nonce(String str) {
            this.nonce = str;
            return this;
        }

        public Builder sign(String str) {
            this.sign = str;
            return this;
        }

        public Builder bindUid(String str) {
            this.bindUid = str;
            return this;
        }

        public Builder exceptPwdChange(boolean z) {
            this.exceptPwdChange = z;
            return this;
        }

        public Builder bizIdcInfo(String str) {
            this.bizIdcInfo = str;
            return this;
        }

        public SecondaryDeviceInfo build() {
            return new SecondaryDeviceInfo(this);
        }
    }
}
