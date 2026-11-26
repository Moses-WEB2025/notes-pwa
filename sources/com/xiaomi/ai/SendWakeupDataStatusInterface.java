package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public interface SendWakeupDataStatusInterface {
    void onSendEnd();

    void onSendError(String str);

    void onSendResult(String str);

    void onSendStart();
}
