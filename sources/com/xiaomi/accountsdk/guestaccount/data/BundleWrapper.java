package com.xiaomi.accountsdk.guestaccount.data;

import android.os.Bundle;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes4.dex */
public class BundleWrapper {
    protected final Bundle bundle;

    protected BundleWrapper(Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("srcBundle == null");
        }
        Bundle bundle2 = new Bundle(bundle);
        this.bundle = bundle2;
        bundle2.setClassLoader(getClass().getClassLoader());
    }

    protected BundleWrapper() {
        this.bundle = new Bundle();
    }

    public final Bundle toBundle() {
        return new Bundle(this.bundle);
    }
}
