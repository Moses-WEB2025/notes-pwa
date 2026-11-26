package com.miui.aiengine.common.cloud;

import kotlin.Metadata;

/* JADX INFO: compiled from: ICloudDownloadFileNetEventStatCallback.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H&J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fH&¨\u0006\rÀ\u0006\u0003"}, d2 = {"Lcom/miui/aiengine/common/cloud/ICloudDownloadFileNetEventStatCallback;", "", "isGetDownloadFileRequestUrl", "", "var1", "", "var2", "onAddGetDownloadFileUrlsFailedEvent", "", "failedStatParam", "Lcom/miui/aiengine/common/cloud/GetDownloadFileUrlFailedStatParam;", "onAddDownloadFileFailedEvent", "Lcom/miui/aiengine/common/cloud/DownloadFileFailedStatParam;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface ICloudDownloadFileNetEventStatCallback {
    boolean isGetDownloadFileRequestUrl(String var1, String var2);

    void onAddDownloadFileFailedEvent(DownloadFileFailedStatParam failedStatParam);

    void onAddGetDownloadFileUrlsFailedEvent(GetDownloadFileUrlFailedStatParam failedStatParam);
}
