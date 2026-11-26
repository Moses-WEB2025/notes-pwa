package com.market.sdk.utils;

import android.text.TextUtils;
import com.market.sdk.MarketManager;

/* JADX INFO: loaded from: classes2.dex */
public abstract class VersionConstraint {
    public abstract boolean isAfter();

    public static VersionConstraint of(final String str, final String str2) {
        return new VersionConstraint() { // from class: com.market.sdk.utils.VersionConstraint.1
            @Override // com.market.sdk.utils.VersionConstraint
            public boolean isAfter() {
                try {
                    if (miui.os.Build.IS_STABLE_VERSION && !TextUtils.isEmpty(str)) {
                        return VersionUtils.isStableVersionLaterThan(str);
                    }
                    if ((miui.os.Build.IS_DEVELOPMENT_VERSION || miui.os.Build.IS_ALPHA_BUILD) && !TextUtils.isEmpty(str2)) {
                        return VersionUtils.isDevVersionLaterThan(str2);
                    }
                    return true;
                } catch (Throwable th) {
                    Log.e(MarketManager.TAG, th.toString());
                    return true;
                }
            }
        };
    }
}
