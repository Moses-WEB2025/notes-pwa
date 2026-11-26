package com.xiaomi.uplink.data;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
public class UplinkRemoteConfigInfo {
    public final String mDefaultGateway;
    public final boolean mIsEnableUplink;

    public UplinkRemoteConfigInfo(boolean z, String str) {
        this.mIsEnableUplink = z;
        this.mDefaultGateway = str;
    }

    public String getContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mIsEnableUplink ? "1" : "0");
        sb.append(";");
        sb.append(this.mDefaultGateway);
        return sb.toString();
    }

    public static UplinkRemoteConfigInfo fromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(";");
        boolean zEquals = "1".equals(strArrSplit[0]);
        String str2 = strArrSplit[1];
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        return new UplinkRemoteConfigInfo(zEquals, str2);
    }
}
