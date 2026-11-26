package com.xiaomi.dist.handoff.sdk.callback;

/* JADX INFO: loaded from: classes4.dex */
public interface DeepLinkCallback {
    void onRestoreState(byte[] bArr);

    byte[] onSaveState();
}
