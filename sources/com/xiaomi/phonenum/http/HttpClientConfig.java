package com.xiaomi.phonenum.http;

/* JADX INFO: loaded from: classes4.dex */
public class HttpClientConfig {
    private static volatile long defaultConnectTimeoutMs = 10000;
    private static volatile long defaultWaitCellularTimeoutMs = 5000;
    public final long connectTimeoutMs;
    public final int netWorkSubId;
    public final long readTimeoutMs;
    public final long waitCellularTimeoutMs;
    public final long writeTimeoutMs;

    private HttpClientConfig(Builder builder) {
        this.netWorkSubId = builder.netWorkSubId;
        this.connectTimeoutMs = defaultConnectTimeoutMs;
        this.waitCellularTimeoutMs = defaultWaitCellularTimeoutMs;
        this.readTimeoutMs = 15000L;
        this.writeTimeoutMs = 15000L;
    }

    public static void setDefaultConnectTimeoutMs(long j) {
        defaultConnectTimeoutMs = j;
    }

    public static void setDefaultWaitCellularTimeoutMs(long j) {
        defaultWaitCellularTimeoutMs = j;
    }

    public static class Builder {
        int netWorkSubId = -1;

        public Builder useDataNetWork(int i) {
            this.netWorkSubId = i;
            return this;
        }

        public HttpClientConfig build() {
            return new HttpClientConfig(this);
        }
    }
}
