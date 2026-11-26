package com.xiaomi.onetrack.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes4.dex */
class f extends Handler {
    final /* synthetic */ e a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    f(e eVar, Looper looper) {
        super(looper);
        this.a = eVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message.what != 100) {
            return;
        }
        this.a.c(message.getData().getString("hint"));
    }
}
