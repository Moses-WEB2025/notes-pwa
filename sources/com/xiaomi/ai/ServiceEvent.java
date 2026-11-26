package com.xiaomi.ai;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class ServiceEvent {
    public static final int SERVICE_EVENT_TRANSACTION_STATUS_ENDED = 2;
    public static final int SERVICE_EVENT_TRANSACTION_STATUS_STILL = 1;
    public static final int SERVICE_EVENT_TRANSACTION_STATUS_UNKNOWN = 0;
    public static final int SERVICE_EVENT_TRUNCATED = 201;
    private int mEventCode;
    private String mEventLevel;
    private String mEventMsg;
    JSONObject mEventNode;
    private int mEventTransactionStatus = 0;

    public ServiceEvent(int i, String str, String str2) {
        this.mEventCode = i;
        this.mEventLevel = str;
        this.mEventMsg = str2;
    }

    public ServiceEvent(String str, JSONObject jSONObject) {
        this.mEventLevel = str;
        this.mEventNode = jSONObject;
        try {
            this.mEventCode = jSONObject.getInt("code");
            this.mEventMsg = jSONObject.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getEventCode() {
        return this.mEventCode;
    }

    public String getEventLevel() {
        return this.mEventLevel;
    }

    public String getEventMsg() {
        return this.mEventMsg;
    }

    public JSONObject getEventNode() {
        return this.mEventNode;
    }

    public int getTransactionStatus() {
        return this.mEventTransactionStatus;
    }

    public void setEventCode(int i) {
        this.mEventCode = i;
    }

    public void setEventLevel(String str) {
        this.mEventLevel = str;
    }

    public void setEventMsg(String str) {
        this.mEventMsg = str;
    }

    public void setEventNode(JSONObject jSONObject) {
        this.mEventNode = jSONObject;
    }
}
