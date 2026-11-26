package com.xiaomi.micloudsdk.stat;

/* JADX INFO: loaded from: classes4.dex */
public interface IMiCloudDownloadFileNetEventStatCallback {
    boolean isGetDownloadFileRequestUrl(String str, String str2);

    void onAddDownloadFileFailedEvent(DownloadFileFailedStatParam downloadFileFailedStatParam);

    void onAddGetDownloadFileUrlsFailedEvent(GetDownloadFileUrlFailedStatParam getDownloadFileUrlFailedStatParam);
}
