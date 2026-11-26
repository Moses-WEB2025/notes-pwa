package com.market.sdk;

import android.content.pm.PackageManager;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.PkgUtils;

/* JADX INFO: loaded from: classes2.dex */
public enum MarketType {
    MARKET_PHONE("com.xiaomi.market"),
    MARKET_PAD("com.xiaomi.market"),
    MIPICKS("com.xiaomi.mipicks"),
    DISCOVER("com.xiaomi.discover");

    private final String mPackageName;
    private int mVersionCode = -1;
    private Boolean mIsEnabled = null;

    MarketType(String str) {
        this.mPackageName = str;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getVersionCode() {
        if (this.mVersionCode == -1) {
            try {
                this.mVersionCode = AppGlobal.getContext().getPackageManager().getPackageInfo(this.mPackageName, 0).versionCode;
            } catch (PackageManager.NameNotFoundException unused) {
                this.mVersionCode = -2;
            }
        }
        return this.mVersionCode;
    }

    public boolean isEnabled() {
        if (this.mIsEnabled == null) {
            this.mIsEnabled = Boolean.valueOf(PkgUtils.isPackageEnabled(this.mPackageName));
        }
        return this.mIsEnabled.booleanValue();
    }
}
