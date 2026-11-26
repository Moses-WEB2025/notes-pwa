package com.miui.notes.ai.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.core.util.Pair;
import com.miui.ai.text.widget.R;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes4.dex */
public class NotificationUtils {
    private static final String GROUP_CHANNEL_NAME = "RealTimeSubtitles";
    private static final String NOTIFICATION_CHANNEL_ID = "ai_text_widget";
    private static final String NOTIFICATION_CHANNEL_NAME = "AiTextWidget";
    public static final int NOTIFICATION_ID = 1;

    private NotificationUtils() {
    }

    public static Pair<Integer, Notification> getNotification(Context context) {
        createNotificationChannel(context);
        Notification notificationCreateNotification = createNotification(context);
        ((NotificationManager) context.getSystemService("notification")).notify(1, notificationCreateNotification);
        return new Pair<>(1, notificationCreateNotification);
    }

    private static void createNotificationChannel(Context context) {
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, 2);
        notificationChannel.setLockscreenVisibility(0);
        ((NotificationManager) context.getSystemService("notification")).createNotificationChannel(notificationChannel);
        notificationChannel.setGroup(GROUP_CHANNEL_NAME);
    }

    private static Notification createNotification(Context context) {
        Notification.Builder builder = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ai_arrow_icon);
        builder.setContentTitle(context.getResources().getString(R.string.foregound_tile));
        builder.setContentText(context.getResources().getString(R.string.foregound_content));
        builder.setOngoing(true);
        builder.setCategory(NotificationCompat.CATEGORY_SERVICE);
        builder.setPriority(-1);
        builder.setShowWhen(true);
        return notificationSetFlag(builder, 64, true).build();
    }

    public static Notification.Builder notificationSetFlag(Notification.Builder builder, int i, boolean z) {
        Method declaredMethod;
        try {
            Class<?> cls = Class.forName("android.app.Notification$Builder");
            if (cls == null || (declaredMethod = cls.getDeclaredMethod("setFlag", Integer.TYPE, Boolean.TYPE)) == null) {
                return null;
            }
            return (Notification.Builder) declaredMethod.invoke(builder, Integer.valueOf(i), Boolean.valueOf(z));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
