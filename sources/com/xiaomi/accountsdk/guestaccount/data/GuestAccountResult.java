package com.xiaomi.accountsdk.guestaccount.data;

import android.content.Intent;
import android.os.Bundle;

/* JADX INFO: loaded from: classes4.dex */
public final class GuestAccountResult extends BundleWrapper {
    private static final String BUNDLE_KEY_ACCOUNT = "sdk_version";
    private static final String BUNDLE_KEY_ERROR_CODE = "error_code";
    private static final String BUNDLE_KEY_ERROR_MESSAGE = "error_msg";
    private static final String BUNDLE_KEY_INTENT = "intent";

    public GuestAccountResult(Bundle bundle) {
        super(bundle);
    }

    public GuestAccountResult() {
    }

    public int getErrorCode() {
        return this.bundle.getInt("error_code");
    }

    public GuestAccountResult setErrorCode(int i) {
        this.bundle.putInt("error_code", i);
        return this;
    }

    public String getErrorMessage() {
        return this.bundle.getString(BUNDLE_KEY_ERROR_MESSAGE);
    }

    public GuestAccountResult setErrorMessage(String str) {
        this.bundle.putString(BUNDLE_KEY_ERROR_MESSAGE, str);
        return this;
    }

    public GuestAccount getGuestAccount() {
        return (GuestAccount) this.bundle.getParcelable("sdk_version");
    }

    public GuestAccountResult setGuestAccount(GuestAccount guestAccount) {
        this.bundle.putParcelable("sdk_version", guestAccount);
        return this;
    }

    public Intent getIntent() {
        return (Intent) this.bundle.getParcelable("intent");
    }

    public GuestAccountResult setIntent(Intent intent) {
        this.bundle.putParcelable("intent", intent);
        return this;
    }
}
