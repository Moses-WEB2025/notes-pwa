package com.miui.notes.ai.network;

/* JADX INFO: loaded from: classes4.dex */
public class NetworkError {
    private int mDetailErrorCode;
    private String mDetailErrorMsg;
    private int mErrorCode;
    private String mErrorMsg;
    private String mTraceId;

    public NetworkError(int i, String str, int i2, String str2, String str3) {
        this.mErrorCode = i;
        this.mErrorMsg = str;
        this.mDetailErrorCode = i2;
        this.mDetailErrorMsg = str2;
        this.mTraceId = str3;
    }

    public String toString() {
        return "mErrorCode " + this.mErrorCode + " mErrorMsg " + this.mErrorMsg + " mDetailErrorCode " + this.mDetailErrorCode + " mDetailErrorMsg " + this.mDetailErrorMsg + " mTraceId " + this.mTraceId;
    }

    public int getErrorCodeNumber() {
        return this.mErrorCode;
    }

    public String getTraceId() {
        return this.mTraceId;
    }

    public static final class Builder {
        private int mDetailErrorCode;
        private int mErrorCode;
        private String mErrorMsg = "";
        private String mDetailErrorMsg = "";
        private String mTraceId = "";

        public Builder errorCode(int i) {
            this.mErrorCode = i;
            return this;
        }

        public Builder errorMsg(String str) {
            this.mErrorMsg = str;
            return this;
        }

        public Builder detailErrorCode(int i) {
            this.mDetailErrorCode = i;
            return this;
        }

        public Builder detailErrorMsg(String str) {
            this.mDetailErrorMsg = str;
            return this;
        }

        public Builder traceId(String str) {
            this.mTraceId = str;
            return this;
        }

        public NetworkError build() {
            return new NetworkError(this.mErrorCode, this.mErrorMsg, this.mDetailErrorCode, this.mDetailErrorMsg, this.mTraceId);
        }
    }
}
