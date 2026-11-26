package com.miui.common.tool;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.ExtraRingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.R;
import com.miui.notes.ui.NotesListActivity;
import com.miui.todo.constant.TodoIntent;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotificationUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0003J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/common/tool/NotificationUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "RINGTONE_TYPE_MIUIXI_SCHEDULE", "", "RINGTONE_TYPE_MIUIXII_SCHEDULE", "createNotificationByOutInsertTodo", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "todoId", "", "createNotificationBuilder", "Landroid/app/Notification$Builder;", "createNotificationBuilderAboveSDK26", "createNotificationBuilderBelowSDK26", "getDefaultSoundActualUri", "Landroid/net/Uri;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NotificationUtils {
    public static final NotificationUtils INSTANCE = new NotificationUtils();
    private static final int RINGTONE_TYPE_MIUIXII_SCHEDULE = 8192;
    private static final int RINGTONE_TYPE_MIUIXI_SCHEDULE = 4096;

    private NotificationUtils() {
    }

    public final void createNotificationByOutInsertTodo(Context context, long todoId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Notification.Builder builderCreateNotificationBuilder = createNotificationBuilder(context);
        Object systemService = context.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        Intent intent = new Intent(context, (Class<?>) NotesListActivity.class);
        intent.setAction(TodoIntent.ACTION_INSERT_OR_EDIT);
        intent.putExtra(TodoIntent.EXTRA_TODO_ID, todoId);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 201326592);
        builderCreateNotificationBuilder.setContentTitle(context.getString(R.string.app_name));
        builderCreateNotificationBuilder.setContentIntent(activity);
        builderCreateNotificationBuilder.setContentText(context.getString(R.string.notification_content_add_todo_from_other_app));
        ((NotificationManager) systemService).notify(2, builderCreateNotificationBuilder.build());
    }

    private final Notification.Builder createNotificationBuilder(Context context) {
        return createNotificationBuilderAboveSDK26(context);
    }

    private final Notification.Builder createNotificationBuilderAboveSDK26(Context context) {
        NotificationChannel notificationChannel = new NotificationChannel("2", "create", 5);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Notification.Builder builder = new Notification.Builder(context, "2");
        builder.setSmallIcon(R.drawable.notifi_small_icon).setWhen(System.currentTimeMillis()).setCategory(NotificationCompat.CATEGORY_PROMO).setChannelId("2").setVisibility(1).setAutoCancel(true);
        return builder;
    }

    private final Notification.Builder createNotificationBuilderBelowSDK26(Context context) {
        Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
        builder.setContentIntent(PendingIntent.getBroadcast(context, 0, new Intent(""), 67108864)).setSmallIcon(R.drawable.notifi_small_icon).setPriority(2).setSound(getDefaultSoundActualUri(context)).setDefaults(4).setWhen(System.currentTimeMillis()).setAutoCancel(true);
        return builder;
    }

    @JvmStatic
    public static final Uri getDefaultSoundActualUri(Context context) {
        Uri defaultSoundActualUri = ExtraRingtoneManager.getDefaultSoundActualUri(context, UIUtils.isMiuiXIISdkSupported() ? 8192 : 4096);
        Logger.INSTANCE.d("NotificationUtils", "getDefaultSoundActualUri uri");
        return defaultSoundActualUri;
    }
}
