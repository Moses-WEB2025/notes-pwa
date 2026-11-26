package com.xiaomi.verificationsdk.internal;

/* JADX INFO: loaded from: classes4.dex */
public class RegisterInfo {
    private String clientId;
    private String eventId;

    public RegisterInfo(String str, String str2) {
        this.eventId = str;
        this.clientId = str2;
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String str) {
        this.eventId = str;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String str) {
        this.clientId = str;
    }
}
