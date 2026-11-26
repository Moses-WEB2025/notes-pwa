package com.miui.notes.ai.local;

/* JADX INFO: loaded from: classes4.dex */
public interface LLMSearchListener {
    void onConnected();

    void onDisConnected();

    void onFailed(int i);
}
