package com.xiaomi.ai;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class TrackInfo {
    public static final int TRACK_ACTION_TYPE_AUTO_SWITCH = 2;
    public static final int TRACK_ACTION_TYPE_GET_RES_FAILED = -1;
    public static final int TRACK_ACTION_TYPE_LIST_SWITCH = 3;
    public static final int TRACK_ACTION_TYPE_MANUAL_SWITCH = 1;
    public static final int TRACK_ACTION_TYPE_PAUSE = 0;
    public static final int TRACK_ACTION_TYPE_PLAY_RES_FAILED = -2;
    private String mCP;
    private long mEndTime;
    private String mRequestID;
    private String mResourceID;
    private String mVersion = "0.2";
    private long mLoadTime = -1;
    private long mStartTime = -1;
    private long mPosition = -1;
    private int mActionType = -1;
    private JSONObject mData = null;

    public int getActionType() {
        return this.mActionType;
    }

    public String getCP() {
        return this.mCP;
    }

    public JSONObject getData() {
        return this.mData;
    }

    public long getEndTime() {
        return this.mEndTime;
    }

    public long getLoadTime() {
        return this.mLoadTime;
    }

    public long getPosition() {
        return this.mPosition;
    }

    public String getRequestID() {
        return this.mRequestID;
    }

    public String getResourceID() {
        return this.mResourceID;
    }

    public long getStartTime() {
        return this.mStartTime;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public TrackInfo setActionType(int i) {
        this.mActionType = i;
        return this;
    }

    public TrackInfo setCP(String str) {
        this.mCP = str;
        return this;
    }

    public void setData(JSONObject jSONObject) {
        this.mData = jSONObject;
    }

    public TrackInfo setEndTime(long j) {
        this.mEndTime = j;
        return this;
    }

    public TrackInfo setLoadTime(long j) {
        this.mLoadTime = j;
        return this;
    }

    public TrackInfo setPosition(long j) {
        this.mPosition = j;
        return this;
    }

    public TrackInfo setRequestID(String str) {
        this.mRequestID = str;
        return this;
    }

    public TrackInfo setResourceID(String str) {
        this.mResourceID = str;
        return this;
    }

    public TrackInfo setStartTime(long j) {
        this.mStartTime = j;
        return this;
    }

    public TrackInfo setVersion(String str) {
        this.mVersion = str;
        return this;
    }
}
