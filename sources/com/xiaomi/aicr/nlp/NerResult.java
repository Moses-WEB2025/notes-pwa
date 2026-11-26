package com.xiaomi.aicr.nlp;

import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes4.dex */
public class NerResult {
    private int exactType;
    private String sdkVersion;
    private int serviceVersion;
    private int statusCode;

    public NerResult() {
        this.statusCode = 0;
    }

    public NerResult(int i, int i2, String str, int i3) {
        this.exactType = i;
        this.statusCode = i2;
        this.sdkVersion = str;
        this.serviceVersion = i3;
    }

    public int getExactType() {
        return this.exactType;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public int getServiceVersion() {
        return this.serviceVersion;
    }

    public String toString() {
        return "NerResult{type=" + this.exactType + ", statusCode=" + this.statusCode + ", sdkVersion=" + this.sdkVersion + ", serviceVersion=" + this.serviceVersion + CoreConstants.CURLY_RIGHT;
    }
}
