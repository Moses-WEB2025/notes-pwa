package com.xiaomi.xms.ai.recorder.interceptor;

import android.content.Context;
import androidx.core.content.ContextCompat;
import ch.qos.logback.core.CoreConstants;
import com.android.soundrecorder.ai.base.aop.BaseInterceptor;
import com.xiaomi.xms.ai.recorder.AIRecorderManager;
import kotlin.Metadata;

/* JADX INFO: compiled from: NotificationInterceptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lcom/xiaomi/xms/ai/recorder/interceptor/NotificationInterceptor;", "Lcom/android/soundrecorder/ai/base/aop/BaseInterceptor;", "()V", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "interceptReturnValue", "", "process", "", "AIRecorderSDK_debug"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class NotificationInterceptor extends BaseInterceptor {
    @Override // com.android.soundrecorder.ai.base.aop.BaseInterceptor
    public Context context() {
        return AIRecorderManager.instance.getContext();
    }

    @Override // com.android.soundrecorder.ai.base.aop.BaseInterceptor
    public Object interceptReturnValue() {
        return -3;
    }

    @Override // com.android.soundrecorder.ai.base.aop.BaseInterceptor
    public boolean process() {
        Context context = context();
        if (context == null) {
            loge("context is null");
            return true;
        }
        if (ContextCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS") == 0) {
            return false;
        }
        logd("permission: android.permission.POST_NOTIFICATIONS, not granted yet!");
        return true;
    }
}
