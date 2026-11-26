package com.xiaomi.micloudsdk.stat;

/* JADX INFO: loaded from: classes4.dex */
public interface IMiCloudNetEventStatCallback {
    void onAddNetFailedEvent(NetFailedStatParam netFailedStatParam);

    void onAddNetSuccessEvent(NetSuccessStatParam netSuccessStatParam);
}
