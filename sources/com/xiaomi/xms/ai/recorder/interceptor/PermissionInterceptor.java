package com.xiaomi.xms.ai.recorder.interceptor;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.android.soundrecorder.ai.base.aop.BaseInterceptor;
import com.xiaomi.xms.ai.recorder.AIRecorderManager;
import kotlin.Metadata;

/* JADX INFO: compiled from: PermissionInterceptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lcom/xiaomi/xms/ai/recorder/interceptor/PermissionInterceptor;", "Lcom/android/soundrecorder/ai/base/aop/BaseInterceptor;", "()V", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "interceptReturnValue", "", "process", "", "AIRecorderSDK_debug"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class PermissionInterceptor extends BaseInterceptor {
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
        if (context() == null) {
            loge("context is null");
            return true;
        }
        if (permissionIsNotGranted("android.permission.RECORD_AUDIO")) {
            return true;
        }
        if (AIRecorderManager.instance.getCallSource() == 1 && permissionIsNotGranted("android.permission.CAPTURE_AUDIO_OUTPUT")) {
            return true;
        }
        if (AIRecorderManager.instance.getCallSource() == 10) {
            return permissionIsNotGranted("android.permission.CAPTURE_VOICE_COMMUNICATION_OUTPUT") || permissionIsNotGranted("android.permission.CALL_AUDIO_INTERCEPTION");
        }
        return false;
    }
}
