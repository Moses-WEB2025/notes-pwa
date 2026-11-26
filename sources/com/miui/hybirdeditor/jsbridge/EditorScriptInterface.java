package com.miui.hybirdeditor.jsbridge;

import kotlin.Metadata;

/* JADX INFO: compiled from: EditorScriptInterface.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&J\u0012\u0010\f\u001a\u00020\u00072\b\u0010\r\u001a\u0004\u0018\u00010\u000eH&J\u0012\u0010\u000f\u001a\u00020\u00072\b\u0010\u0010\u001a\u0004\u0018\u00010\u000eH&J\u0010\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\tH&J\b\u0010\u0013\u001a\u00020\u0007H&J\b\u0010\u0014\u001a\u00020\u0007H&J\b\u0010\u0015\u001a\u00020\u0007H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016À\u0006\u0003"}, d2 = {"Lcom/miui/hybirdeditor/jsbridge/EditorScriptInterface;", "", "contentLength", "", "getContentLength", "()J", "onPagePrepared", "", "deviceFlag", "", "getDeviceFlag", "()I", "showToast", "key", "", "saveData", "data", "onCorrectNumChanged", "changeCount", "onFlowDone", "notifySetNewContent", "notifyRefreshComplete", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface EditorScriptInterface {
    long getContentLength();

    int getDeviceFlag();

    void notifyRefreshComplete();

    void notifySetNewContent();

    void onCorrectNumChanged(int changeCount);

    void onFlowDone();

    void onPagePrepared();

    void saveData(String data);

    void showToast(String key);
}
