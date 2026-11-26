package com.miui.notes.tool;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes4.dex */
public class NotesNotificationManager {
    private static final String CHANNEL_ID_NORNAL = "notes_channel_normal";
    private static final String CHANNEL_ID_NO_SOUND = "notes_normal_no_sound";
    private static final String CHANNEL_ID_NO_VIBRATION = "notes_normal_no_vibration";
    private static final String CHANNEL_ID_SILENT = "notes_channel_silent";
    private static final String TAG = "NotesNotificationManager";
    private Context mAppContext;
    private NotificationManager mNotificationManager;

    private NotesNotificationManager(Context context) {
        this.mAppContext = context.getApplicationContext();
        this.mNotificationManager = (NotificationManager) context.getSystemService("notification");
    }

    public static NotesNotificationManager getInstance(Context context) {
        return new NotesNotificationManager(context);
    }

    public void notify(NotesNotification notesNotification) {
        this.mNotificationManager.notify(notesNotification.getNotificationId(), makeNotification(notesNotification));
    }

    private Notification makeNotification(NotesNotification notesNotification) {
        Notification.Builder builderMakeNotificationBuilder = makeNotificationBuilder(notesNotification);
        builderMakeNotificationBuilder.setContentTitle(notesNotification.getContentTitle()).setContentText(notesNotification.getContentText()).setSmallIcon(notesNotification.getSmallIcon()).setContentIntent(notesNotification.getContentIntent()).setDeleteIntent(notesNotification.getDeleteIntent()).setFullScreenIntent(notesNotification.getFullscreenIntent(), true).setShowWhen(true);
        addAction(builderMakeNotificationBuilder, notesNotification.getAction());
        Notification notificationBuild = builderMakeNotificationBuilder.build();
        if (!notesNotification.canCancel()) {
            notificationBuild.flags |= 2;
            setMiuiNotificationExitFloatingIntent(notificationBuild, notesNotification.getDeleteIntent());
        }
        if (notesNotification.isEnableFloat()) {
            setMiuiNotificationEnableFloat(notificationBuild, true);
            setMiuiNotificationFloatTime(notificationBuild, notesNotification.getFloatTime());
        } else {
            setMiuiNotificationEnableFloat(notificationBuild, false);
        }
        addNotificationOptions(notificationBuild, notesNotification);
        return notificationBuild;
    }

    private void addAction(Notification.Builder builder, NotesNotification.Action action) {
        if (action == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("miui.showAction", true);
        builder.setExtras(bundle);
        builder.addAction(action.getIcon(), action.getTitle(), action.getPendingIntent());
    }

    private static void addNotificationOptions(Notification notification, NotesNotification notesNotification) {
        Logger.INSTANCE.i(TAG, "addNotificationOptions()");
        notification.flags |= 1;
        notification.defaults |= 4;
    }

    private Notification.Builder makeNotificationBuilder(NotesNotification notesNotification) {
        return new Notification.Builder(this.mAppContext, getChannelId(notesNotification));
    }

    private String getChannelId(NotesNotification notesNotification) {
        String str;
        Uri soundUri = notesNotification.getSoundUri();
        boolean zShouldVibration = notesNotification.shouldVibration();
        if (soundUri != null && zShouldVibration) {
            str = CHANNEL_ID_NORNAL;
        } else if (soundUri != null) {
            str = CHANNEL_ID_NO_VIBRATION;
        } else if (zShouldVibration) {
            str = CHANNEL_ID_NO_SOUND;
        } else {
            str = CHANNEL_ID_SILENT;
        }
        createChannel(this.mAppContext, str, soundUri, zShouldVibration);
        return str;
    }

    private void createChannel(Context context, String str, Uri uri, boolean z) {
        NotificationChannel notificationChannel = new NotificationChannel(str, context.getString(R.string.notify_alarm_title), 4);
        notificationChannel.setDescription("");
        if (uri == null) {
            notificationChannel.setSound(null, null);
        } else {
            notificationChannel.setSound(uri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
        }
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(z);
        this.mNotificationManager.createNotificationChannel(notificationChannel);
    }

    private static void setMiuiNotificationExitFloatingIntent(Notification notification, PendingIntent pendingIntent) {
        try {
            Object objNewInstance = Class.forName("android.app.MiuiNotification").newInstance();
            Field declaredField = objNewInstance.getClass().getDeclaredField("exitFloatingIntent");
            declaredField.setAccessible(true);
            declaredField.set(objNewInstance, pendingIntent);
            notification.getClass().getField("extraNotification").set(notification, objNewInstance);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "setMiuiNotificationExitFloatingIntent(),exception:" + e);
        }
    }

    private static void setMiuiNotificationEnableFloat(Notification notification, boolean z) {
        try {
            Object obj = notification.getClass().getDeclaredField("extraNotification").get(notification);
            obj.getClass().getDeclaredMethod("setEnableFloat", Boolean.TYPE).invoke(obj, Boolean.valueOf(z));
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "setMiuiNotificationEnableFloat(),exception:" + e);
        }
    }

    private static void setMiuiNotificationFloatTime(Notification notification, int i) {
        try {
            Object obj = notification.getClass().getDeclaredField("extraNotification").get(notification);
            obj.getClass().getDeclaredMethod("setFloatTime", Integer.TYPE).invoke(obj, Integer.valueOf(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
