package com.miui.notes.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Process;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.xiaomi.passport.utils.LoginPreferenceConfig;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: compiled from: DowngradeReceiver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\u000b"}, d2 = {"Lcom/miui/notes/ui/DowngradeReceiver;", "Landroid/content/BroadcastReceiver;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onReceive", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DowngradeReceiver extends BroadcastReceiver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String DOWNGRADE_BROADCAST_ACTION = "com.miui.note.downgrade";

    @JvmStatic
    public static final void downgrade(Context context) {
        INSTANCE.downgrade(context);
    }

    @JvmStatic
    public static final void register(Activity activity, DowngradeReceiver downgradeReceiver) {
        INSTANCE.register(activity, downgradeReceiver);
    }

    @JvmStatic
    public static final void unRegister(Activity activity, DowngradeReceiver downgradeReceiver) {
        INSTANCE.unRegister(activity, downgradeReceiver);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        String strInvokeGetSender = CompatUtils.invokeGetSender(intent);
        if (strInvokeGetSender != null && Intrinsics.areEqual(strInvokeGetSender, "com.miui.notes") && Intrinsics.areEqual(intent.getAction(), DOWNGRADE_BROADCAST_ACTION)) {
            Logger.INSTANCE.d("DowngradeReceiver", "on Receive Downgrade broadcast");
            INSTANCE.downgrade(context);
        }
    }

    /* JADX INFO: compiled from: DowngradeReceiver.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007J\u001a\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0007J\u001a\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/notes/ui/DowngradeReceiver$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "DOWNGRADE_BROADCAST_ACTION", "", "downgrade", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", LoginPreferenceConfig.TYPE_REGISTER, "Landroid/app/Activity;", "downgradeReceiver", "Lcom/miui/notes/ui/DowngradeReceiver;", "unRegister", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void downgrade(final Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Logger.INSTANCE.d("DowngradeReceiver", "show downgrade dialog");
            String string = context.getResources().getString(R.string.db_downgrade_clear_button);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            String string2 = context.getResources().getString(R.string.db_downgrade_clear_date_reminder);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            new AlertDialog.Builder(context).setMessage(string2).setPositiveButton(string, new DialogInterface.OnClickListener() { // from class: com.miui.notes.ui.DowngradeReceiver$Companion$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    DowngradeReceiver.Companion.downgrade$lambda$0(context, dialogInterface, i);
                }
            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.ui.DowngradeReceiver$Companion$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    DowngradeReceiver.Companion.downgrade$lambda$1(dialogInterface, i);
                }
            }).setCancelable(false).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void downgrade$lambda$0(Context context, DialogInterface dialogInterface, int i) {
            Logger.INSTANCE.d("DowngradeReceiver", "do downgrade!!");
            Object systemService = context.getSystemService("activity");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
            ((ActivityManager) systemService).clearApplicationUserData();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void downgrade$lambda$1(DialogInterface dialogInterface, int i) {
            Process.killProcess(Process.myPid());
        }

        @JvmStatic
        public final void register(Activity context, DowngradeReceiver downgradeReceiver) {
            Intrinsics.checkNotNullParameter(context, "context");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(DowngradeReceiver.DOWNGRADE_BROADCAST_ACTION);
            if (Utils.isAboveSDK34()) {
                context.registerReceiver(downgradeReceiver, intentFilter, 2);
            } else {
                context.registerReceiver(downgradeReceiver, intentFilter);
            }
        }

        @JvmStatic
        public final void unRegister(final Activity context, final DowngradeReceiver downgradeReceiver) {
            Intrinsics.checkNotNullParameter(context, "context");
            new Thread(new Runnable() { // from class: com.miui.notes.ui.DowngradeReceiver$Companion$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DowngradeReceiver.Companion.unRegister$lambda$3(downgradeReceiver, context);
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void unRegister$lambda$3(DowngradeReceiver downgradeReceiver, Activity activity) {
            if (downgradeReceiver != null) {
                try {
                    activity.unregisterReceiver(downgradeReceiver);
                } catch (Exception e) {
                    Logger.INSTANCE.d("DowngradeReceiver", "unRegister error: " + e.getMessage());
                }
            }
        }
    }
}
