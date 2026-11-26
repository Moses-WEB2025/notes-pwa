package com.miui.common.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.Logger;
import com.miui.notes.tool.PreferenceUtils;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseAlarmAlertActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/miui/common/base/AlarmHandler;", "Landroid/os/Handler;", "activityRef", "Ljava/lang/ref/WeakReference;", "Lcom/miui/common/base/BaseAlarmAlertActivity;", "looper", "Landroid/os/Looper;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/ref/WeakReference;Landroid/os/Looper;)V", "getActivityRef", "()Ljava/lang/ref/WeakReference;", "iCounter", "", "resetCounter", "", "handleMessage", "msg", "Landroid/os/Message;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
final class AlarmHandler extends Handler {
    private final WeakReference<BaseAlarmAlertActivity> activityRef;
    private int iCounter;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlarmHandler(WeakReference<BaseAlarmAlertActivity> activityRef, Looper looper) {
        super(looper);
        Intrinsics.checkNotNullParameter(activityRef, "activityRef");
        Intrinsics.checkNotNullParameter(looper, "looper");
        this.activityRef = activityRef;
    }

    public final WeakReference<BaseAlarmAlertActivity> getActivityRef() {
        return this.activityRef;
    }

    public final void resetCounter() {
        this.iCounter = 0;
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        BaseAlarmAlertActivity baseAlarmAlertActivity = this.activityRef.get();
        if (baseAlarmAlertActivity == null) {
            Logger.INSTANCE.i(BaseAlarmAlertActivity.TAG, "handleMessage: activity is null, remove all message");
            removeMessages(2);
            return;
        }
        Logger.INSTANCE.i(BaseAlarmAlertActivity.TAG, "handleMessage: what=" + msg.what);
        if (msg.what == 2) {
            baseAlarmAlertActivity.stopAlarmSound();
            if (!PreferenceUtils.getRemindType(BaseNoteApp.INSTANCE.getInstance())) {
                baseAlarmAlertActivity.getWindow().clearFlags(BaseAlarmAlertActivity.TURN_SCREEN_ON_FLAGS);
            }
            int i = this.iCounter + 1;
            this.iCounter = i;
            if (i < BaseAlarmAlertActivity.INSTANCE.getMUTE_DURATIONS().length) {
                baseAlarmAlertActivity.registerAlarmLater(System.currentTimeMillis() + ((long) BaseAlarmAlertActivity.INSTANCE.getMUTE_DURATIONS()[this.iCounter]));
            }
        }
    }
}
