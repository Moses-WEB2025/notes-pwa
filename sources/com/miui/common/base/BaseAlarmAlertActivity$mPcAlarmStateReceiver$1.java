package com.miui.common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import ch.qos.logback.core.CoreConstants;
import com.miui.common.constant.FrameConstant;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseAlarmAlertActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"com/miui/common/base/BaseAlarmAlertActivity$mPcAlarmStateReceiver$1", "Landroid/content/BroadcastReceiver;", "onReceive", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BaseAlarmAlertActivity$mPcAlarmStateReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ BaseAlarmAlertActivity this$0;

    BaseAlarmAlertActivity$mPcAlarmStateReceiver$1(BaseAlarmAlertActivity baseAlarmAlertActivity) {
        this.this$0 = baseAlarmAlertActivity;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (Intrinsics.areEqual(FrameConstant.ACTION_DUAL_SCREEN_LAUNCH_ALARM_RESULT, intent.getAction())) {
            Scheduler schedulerIo = Schedulers.io();
            final BaseAlarmAlertActivity baseAlarmAlertActivity = this.this$0;
            schedulerIo.scheduleDirect(new Runnable() { // from class: com.miui.common.base.BaseAlarmAlertActivity$mPcAlarmStateReceiver$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAlarmAlertActivity$mPcAlarmStateReceiver$1.onReceive$lambda$0(baseAlarmAlertActivity);
                }
            });
            BaseAlarmAlertActivity baseAlarmAlertActivity2 = this.this$0;
            baseAlarmAlertActivity2.releaseWakeLock(baseAlarmAlertActivity2, baseAlarmAlertActivity2.mWakeLock);
            this.this$0.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onReceive$lambda$0(BaseAlarmAlertActivity baseAlarmAlertActivity) {
        baseAlarmAlertActivity.muteMusic();
        baseAlarmAlertActivity.cancelAlarmNotification();
    }
}
