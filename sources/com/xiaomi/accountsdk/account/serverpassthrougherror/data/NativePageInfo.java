package com.xiaomi.accountsdk.account.serverpassthrougherror.data;

import ch.qos.logback.core.CoreConstants;
import com.xiaomi.accountsdk.account.serverpassthrougherror.base.CustomJumpIntentInterface;

/* JADX INFO: loaded from: classes4.dex */
public class NativePageInfo {
    public final CustomJumpIntentInterface customJumpIntentInterface;
    public final Class<?> pageClass;

    public NativePageInfo(Class<?> cls) {
        this(cls, null);
    }

    public NativePageInfo(Class<?> cls, CustomJumpIntentInterface customJumpIntentInterface) {
        this.pageClass = cls;
        this.customJumpIntentInterface = customJumpIntentInterface;
    }

    public String toString() {
        return "NativePageInfo{pageClass=" + this.pageClass + CoreConstants.CURLY_RIGHT;
    }
}
