package com.miui.aiengine.common.cloud;

import kotlin.Metadata;

/* JADX INFO: compiled from: CloudStatCallback.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&J4\u0010\u0007\u001a\u00020\u00032\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\tH&¨\u0006\u0010À\u0006\u0003"}, d2 = {"Lcom/miui/aiengine/common/cloud/CloudStatCallback;", "", "onInitialize", "", "onSetUploadPolicy", "onSetEventFilter", "onEnableAutoRecord", "onAddHttpEvent", "url", "", "timeCost", "", "netFlow", "responseCode", "", "exceptionName", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface CloudStatCallback {
    void onAddHttpEvent(String url, long timeCost, long netFlow, int responseCode, String exceptionName);

    void onEnableAutoRecord();

    void onInitialize();

    void onSetEventFilter();

    void onSetUploadPolicy();
}
