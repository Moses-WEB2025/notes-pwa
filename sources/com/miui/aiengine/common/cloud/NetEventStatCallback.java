package com.miui.aiengine.common.cloud;

import kotlin.Metadata;

/* JADX INFO: compiled from: NetEventStatCallback.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0012\u0010\u0006\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&¨\u0006\tÀ\u0006\u0003"}, d2 = {"Lcom/miui/aiengine/common/cloud/NetEventStatCallback;", "", "onAddNetSuccessEvent", "", "successStatParam", "Lcom/miui/aiengine/common/cloud/NetSuccessStatParam;", "onAddNetFailedEvent", "failedStatParam", "Lcom/miui/aiengine/common/cloud/NetFailedStatParam;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface NetEventStatCallback {
    void onAddNetFailedEvent(NetFailedStatParam failedStatParam);

    void onAddNetSuccessEvent(NetSuccessStatParam successStatParam);
}
