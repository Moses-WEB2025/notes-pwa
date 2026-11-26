package com.xiaomi.miai.auth;

import com.xiaomi.miai.utils.Requires;

/* JADX INFO: loaded from: classes4.dex */
public class UserInfo {
    private final String deviceId;
    private String miotDid;
    private final String userId;

    public UserInfo(String str, String str2) {
        Requires.isTrue((str == null && str2 == null) ? false : true, "Both userId (%s) and deviceId (%s) are null.", str, str2);
        this.userId = str;
        this.deviceId = str2;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getMiotDid() {
        return this.miotDid;
    }

    public void setMiotDid(String str) {
        this.miotDid = str;
    }
}
