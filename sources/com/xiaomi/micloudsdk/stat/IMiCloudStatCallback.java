package com.xiaomi.micloudsdk.stat;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public interface IMiCloudStatCallback {
    void onAddHttpEvent(String str, long j, long j2, int i, String str2);

    void onEnableAutoRecord();

    void onInitialize();

    void onSetEventFilter();

    void onSetUploadPolicy();
}
