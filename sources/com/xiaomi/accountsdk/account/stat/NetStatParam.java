package com.xiaomi.accountsdk.account.stat;

/* JADX INFO: loaded from: classes4.dex */
public class NetStatParam {
    public final String exception;
    public final long netFlow;
    public final long requestStartTime;
    public final int responseCode;
    public final int resultType;
    public final int retryCount;
    public final long timeCost;
    public final String url;

    public NetStatParam(String str, long j, long j2, long j3, int i, int i2, int i3, String str2) {
        this.url = str;
        this.requestStartTime = j;
        this.timeCost = j2;
        this.netFlow = j3;
        this.resultType = i;
        this.responseCode = i2;
        this.retryCount = i3;
        this.exception = str2;
    }

    public static class Builder {
        private String exception;
        private long netFlow;
        private long requestStartTime;
        private int responseCode;
        private int resultType;
        private int retryCount;
        private long timeCost;
        private String url;

        public Builder setUrl(String str) {
            this.url = str;
            return this;
        }

        public Builder setRequestStartTime(long j) {
            this.requestStartTime = j;
            return this;
        }

        public Builder setTimeCost(long j) {
            this.timeCost = j;
            return this;
        }

        public Builder setNetFlow(long j) {
            this.netFlow = j;
            return this;
        }

        public Builder setResultType(int i) {
            this.resultType = i;
            return this;
        }

        public Builder setResponseCode(int i) {
            this.responseCode = i;
            return this;
        }

        public Builder setRetryCount(int i) {
            this.retryCount = i;
            return this;
        }

        public Builder setException(String str) {
            this.exception = str;
            return this;
        }

        public NetStatParam create() {
            return new NetStatParam(this.url, this.requestStartTime, this.timeCost, this.netFlow, this.resultType, this.responseCode, this.retryCount, this.exception);
        }
    }
}
