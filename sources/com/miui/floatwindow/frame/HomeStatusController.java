package com.miui.floatwindow.frame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Utils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HomeStatusController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\tJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\rR\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/miui/floatwindow/frame/HomeStatusController;", "", "mContext", "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mHomeStatusFilter", "Landroid/content/IntentFilter;", "mHomeStatusListener", "Lcom/miui/floatwindow/frame/HomeStatusListener;", "mHomeStatusReceiver", "Landroid/content/BroadcastReceiver;", "setHomeStatusListener", "", "listener", "startListen", "stopListen", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HomeStatusController {
    private static final String SYSTEM_DIALOG_REASON_FS_GESTURE = "fs_gesture";
    private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    private final Context mContext;
    private IntentFilter mHomeStatusFilter;
    private HomeStatusListener mHomeStatusListener;
    private final BroadcastReceiver mHomeStatusReceiver = new BroadcastReceiver() { // from class: com.miui.floatwindow.frame.HomeStatusController$mHomeStatusReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(intent, "intent");
            if (Intrinsics.areEqual("android.intent.action.CLOSE_SYSTEM_DIALOGS", intent.getAction())) {
                String stringExtra = intent.getStringExtra("reason");
                if (TextUtils.isEmpty(stringExtra)) {
                    return;
                }
                if (Intrinsics.areEqual("homekey", stringExtra)) {
                    if (this.this$0.mHomeStatusListener != null) {
                        HomeStatusListener homeStatusListener = this.this$0.mHomeStatusListener;
                        Intrinsics.checkNotNull(homeStatusListener);
                        homeStatusListener.onHomeKey();
                        return;
                    }
                    return;
                }
                if (Intrinsics.areEqual("recentapps", stringExtra)) {
                    if (this.this$0.mHomeStatusListener != null) {
                        HomeStatusListener homeStatusListener2 = this.this$0.mHomeStatusListener;
                        Intrinsics.checkNotNull(homeStatusListener2);
                        homeStatusListener2.onRecentApps();
                        return;
                    }
                    return;
                }
                if (!Intrinsics.areEqual("fs_gesture", stringExtra) || this.this$0.mHomeStatusListener == null) {
                    return;
                }
                HomeStatusListener homeStatusListener3 = this.this$0.mHomeStatusListener;
                Intrinsics.checkNotNull(homeStatusListener3);
                homeStatusListener3.onHomeKey();
            }
        }
    };

    public HomeStatusController(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        this.mHomeStatusFilter = intentFilter;
        Intrinsics.checkNotNull(intentFilter);
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
    }

    public final void setHomeStatusListener(HomeStatusListener listener) {
        this.mHomeStatusListener = listener;
    }

    public final void startListen() {
        if (this.mContext != null) {
            if (Utils.isAboveSDK34()) {
                this.mContext.registerReceiver(this.mHomeStatusReceiver, this.mHomeStatusFilter, 2);
            } else {
                this.mContext.registerReceiver(this.mHomeStatusReceiver, this.mHomeStatusFilter);
            }
        }
    }

    public final void stopListen() {
        Context context = this.mContext;
        if (context != null) {
            context.unregisterReceiver(this.mHomeStatusReceiver);
        }
    }
}
