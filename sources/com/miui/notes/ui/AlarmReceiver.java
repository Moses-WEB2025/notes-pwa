package com.miui.notes.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.backup.AlarmService;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.provider.SignatureValidator;
import com.miui.notes.store.NoteStore;
import com.miui.todo.data.Todo;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.utils.TimeUtils;
import com.miui.todo.data.utils.TodoDataUtils;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String ACTION_CLOUD_PRIVACY_DENIED = "com.xiaomi.action.MICLOUD_PRIVACY_DENIED";
    protected static final String ACTION_LAUNCH_ALARM = "com.miui.notes.action.LAUNCH_ALARM";
    public static final String ACTION_LAUNCH_TODO_ALARM = "com.miui.notes.action.LAUNCH_TODO_ALARM";
    private static final int ALARM_JOB_ID = 1;
    private static final int COLUMN_ALERTED_DATE = 1;
    private static final int COLUMN_ID = 0;
    private static final String NOTE_PACKAGE_NAME = "com.miui.notes";
    private static final String[] PROJECTION = {"_id", Notes.Note.ALERTED_DATE};
    private static final String TAG = "AlarmReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, Intent intent) {
        String action;
        if (intent == null) {
            action = null;
        } else {
            try {
                action = intent.getAction();
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "AlarmReceiver,catch a exception:" + e);
                return;
            }
        }
        Logger.INSTANCE.i(TAG, "onReceive: action=" + action);
        if (!"android.intent.action.BOOT_COMPLETED".equals(action) && !Notes.Intents.INTENT_ACTION_REFRESH_ALARM.equals(action) && !"android.intent.action.TIME_SET".equals(action)) {
            if (ACTION_LAUNCH_ALARM.equals(action)) {
                launchAlarm(context, intent, true);
                return;
            }
            if (ACTION_LAUNCH_TODO_ALARM.equals(action)) {
                launchTodoAlarm(context, intent, true);
                return;
            }
            if (!ACTION_CLOUD_PRIVACY_DENIED.equals(action)) {
                if (intent != null) {
                    intent.toUri(0);
                }
                Logger.INSTANCE.d(TAG, "Receive unknown intent: ");
                return;
            } else {
                try {
                    NoteStore.clearSyncInfo(context);
                    return;
                } catch (Exception unused) {
                    Logger.INSTANCE.e(TAG, "onReceive error");
                    return;
                }
            }
        }
        new Thread(new Runnable() { // from class: com.miui.notes.ui.AlarmReceiver$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AlarmReceiver.lambda$onReceive$0(context);
            }
        }).start();
    }

    static /* synthetic */ void lambda$onReceive$0(Context context) {
        Thread.currentThread().setName("alarm-receiver-worker");
        setupAlarms(context);
        setupTodoAlarms(context);
        setupRepeatTodoAlarms(context);
    }

    public static void setupAlarms(Context context) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Logger.INSTANCE.i(TAG, "setupAlarms: currentDate=" + jCurrentTimeMillis);
        Cursor cursorQuery = context.getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), PROJECTION, "alert_date>? AND type=0 AND parent_id!=-3", new String[]{String.valueOf(jCurrentTimeMillis)}, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.moveToFirst()) {
                    while (true) {
                        Context context2 = context;
                        scheduleAlert(context2, cursorQuery.getLong(0), cursorQuery.getLong(1), false);
                        if (!cursorQuery.moveToNext()) {
                            break;
                        } else {
                            context = context2;
                        }
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Throwable th) {
                if (cursorQuery == null) {
                    throw th;
                }
                try {
                    cursorQuery.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        }
    }

    public static void setupTodoAlarms(Context context) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Logger.INSTANCE.i(TAG, "setupTodoAlarms: currentDate=" + jCurrentTimeMillis);
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "remind_time >? AND is_finish == 0 AND local_status != 3", new String[]{String.valueOf(jCurrentTimeMillis)}, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.moveToFirst()) {
                    while (true) {
                        long j = cursorQuery.getLong(cursorQuery.getColumnIndex("id"));
                        Logger.INSTANCE.i(TAG, "setupTodoAlarms: todoId=" + j);
                        Context context2 = context;
                        scheduleTodoAlert(context2, j, cursorQuery.getLong(cursorQuery.getColumnIndex(Todo.REMIND_TIME)), false);
                        if (!cursorQuery.moveToNext()) {
                            break;
                        } else {
                            context = context2;
                        }
                    }
                }
            } finally {
                cursorQuery.close();
            }
        }
    }

    public static void setupRepeatTodoAlarms(Context context) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Logger.INSTANCE.i(TAG, "setupRepeatTodoAlarms: currentDate=" + jCurrentTimeMillis);
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "remind_time <=? AND is_finish == 0 AND remind_repeat_type > 0 AND local_status != 3", new String[]{String.valueOf(jCurrentTimeMillis)}, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.moveToFirst()) {
                    while (true) {
                        TodoEntity todoEntity = TodoDataUtils.getTodoEntity(cursorQuery);
                        long id = todoEntity.getId();
                        Logger.INSTANCE.i(TAG, "setupRepeatTodoAlarms: todoId=" + id);
                        Context context2 = context;
                        scheduleTodoAlert(context2, id, TimeUtils.getNextRemindTime(todoEntity.getRemindRepeatType(), todoEntity.getRemindTime(), todoEntity.getFirstRemindTime()), false);
                        if (!cursorQuery.moveToNext()) {
                            break;
                        } else {
                            context = context2;
                        }
                    }
                }
            } finally {
                cursorQuery.close();
            }
        }
    }

    public static void launchAlarm(Context context, Intent intent, boolean z) {
        if (z) {
            String str = intent.getPackage();
            Logger.INSTANCE.i(TAG, "launchAlarm intentPackageName = " + str);
            if ("com.miui.notes".equals(str) && SignatureValidator.validateSignature(context, str)) {
                AlarmAlertHelper.getInstance().handleAlarmLaunchIntent(context, intent);
                return;
            }
            return;
        }
        AlarmAlertHelper.getInstance().handleAlarmLaunchIntent(context, intent);
    }

    public static void launchTodoAlarm(Context context, Intent intent, boolean z) {
        if (z) {
            String str = intent.getPackage();
            Logger.INSTANCE.i(TAG, "launchTodoAlarm intentPackageName = " + str);
            if ("com.miui.notes".equals(str) && SignatureValidator.validateSignature(context, str)) {
                AlarmAlertHelper.getInstance().handleTodoLaunchIntent(context, intent);
                return;
            }
            return;
        }
        AlarmAlertHelper.getInstance().handleTodoLaunchIntent(context, intent);
    }

    public static void scheduleAlert(Context context, long j, long j2, boolean z) {
        Logger.INSTANCE.i(TAG, "scheduleAlert: noteId=" + j + ", date=" + Utils.formatTime(j2) + ", cancel=" + z);
        Intent intent = new Intent(ACTION_LAUNCH_ALARM);
        intent.setPackage(context.getPackageName());
        intent.setClass(context, AlarmReceiver.class);
        intent.setData(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, j));
        intent.putExtra(Notes.Intents.INTENT_EXTRA_ALERT_DATE, j2);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 201326592);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (z) {
            alarmManager.cancel(broadcast);
        } else {
            BaseAlarmAlertActivity.scheduleAlarmExact(alarmManager, j2, broadcast);
        }
    }

    public static void scheduleTodoAlert(Context context, long j, long j2, boolean z) {
        Logger.INSTANCE.i(TAG, "scheduleTodoAlert: todoId=" + j + ", date=" + Utils.formatTime(j2) + ", cancel=" + z);
        if (z || j2 > System.currentTimeMillis()) {
            Intent intent = new Intent(ACTION_LAUNCH_TODO_ALARM);
            intent.setPackage(context.getPackageName());
            intent.setClass(context, AlarmReceiver.class);
            intent.setData(ContentUris.withAppendedId(Uri.parse(Todo.URI_TODO_OUTER), j));
            intent.putExtra(Notes.Intents.INTENT_EXTRA_ALERT_DATE, j2);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 201326592);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            if (z) {
                alarmManager.cancel(broadcast);
            } else {
                BaseAlarmAlertActivity.scheduleAlarmExact(alarmManager, j2, broadcast);
            }
        }
    }

    public static void jobScheduleAlert(Context context, long j) {
        context.startService(new Intent(context, (Class<?>) AlarmService.class));
        ComponentName componentName = new ComponentName(context.getPackageName(), AlarmService.class.getName());
        long jCurrentTimeMillis = System.currentTimeMillis();
        Cursor cursorQuery = context.getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), PROJECTION, "alert_date>? AND type=0", new String[]{String.valueOf(jCurrentTimeMillis)}, Notes.Note.ALERTED_DATE);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() > 0) {
                    cursorQuery.moveToFirst();
                    long j2 = cursorQuery.getLong(1);
                    if (j2 < j) {
                        j = j2;
                    }
                }
                cursorQuery.close();
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
        Cursor cursorQuery2 = context.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "remind_time >? AND is_finish == 0", new String[]{String.valueOf(jCurrentTimeMillis)}, Todo.REMIND_TIME);
        if (cursorQuery2 != null) {
            try {
                if (cursorQuery2.getCount() > 0) {
                    cursorQuery2.moveToFirst();
                    long j3 = cursorQuery2.getLong(cursorQuery2.getColumnIndex(Todo.REMIND_TIME));
                    if (j3 < j) {
                        j = j3;
                    }
                }
                cursorQuery2.close();
            } catch (Throwable th2) {
                cursorQuery2.close();
                throw th2;
            }
        }
        if ((cursorQuery == null && cursorQuery2 == null) || j == Long.MIN_VALUE) {
            return;
        }
        Logger.INSTANCE.i(TAG, "recent alarm clock:" + j);
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        long jCurrentTimeMillis2 = j - System.currentTimeMillis();
        jobScheduler.schedule(new JobInfo.Builder(1, componentName).setPersisted(true).setMinimumLatency(jCurrentTimeMillis2).setOverrideDeadline(jCurrentTimeMillis2).build());
    }

    public static void asyncJobScheduleAlert(final Context context, final long j) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.miui.notes.ui.AlarmReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AlarmReceiver.jobScheduleAlert(context, j);
                }
            });
        } else {
            jobScheduleAlert(context, j);
        }
    }

    public static void asyncJobScheduleAlert(Context context) {
        asyncJobScheduleAlert(context, Long.MIN_VALUE);
    }
}
