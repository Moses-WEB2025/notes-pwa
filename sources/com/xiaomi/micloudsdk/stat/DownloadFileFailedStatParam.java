package com.xiaomi.micloudsdk.stat;

/* JADX INFO: loaded from: classes4.dex */
public class DownloadFileFailedStatParam {
    public final String exceptionName;
    public final int httpStatusCode;
    public final String url;

    public DownloadFileFailedStatParam(String str, int i, String str2) {
        this.url = str;
        this.httpStatusCode = i;
        this.exceptionName = str2;
    }

    public static DownloadFileFailedStatParam createDownloadFileFailedStatParam(String str, int i, String str2) {
        return new DownloadFileFailedStatParam(str, i, str2);
    }

    public String toString() {
        return "DownloadFileFailedStatParam{url='" + this.url + "', httpStatusCode=" + this.httpStatusCode + ", exceptionName='" + this.exceptionName + "'}";
    }
}
