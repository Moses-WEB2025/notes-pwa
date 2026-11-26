package com.miui.notes.backup;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.ui.AlarmReceiver;
import com.miui.todo.data.Todo;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AlarmService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0017R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/notes/backup/AlarmTask;", "Ljava/lang/Runnable;", "ctx", "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "ctxRef", "Ljava/lang/ref/WeakReference;", "ALARM_DELAY", "", "run", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
final class AlarmTask implements Runnable {
    private final long ALARM_DELAY;
    private final WeakReference<Context> ctxRef;

    public AlarmTask(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctxRef = new WeakReference<>(ctx);
        this.ALARM_DELAY = 30000L;
    }

    @Override // java.lang.Runnable
    public void run() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime < this.ALARM_DELAY) {
            try {
                Logger.INSTANCE.d("AlarmTask", "onTaskRun,sleep 30s after reboot");
                Thread.sleep(this.ALARM_DELAY - jElapsedRealtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        Uri uriAppendFlag = NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
        Context context = this.ctxRef.get();
        Intrinsics.checkNotNull(context);
        Context context2 = context;
        ContentResolver contentResolver = context2.getContentResolver();
        Intrinsics.checkNotNull(contentResolver);
        Cursor cursorQuery = contentResolver.query(uriAppendFlag, new String[]{"_id", Notes.Note.ALERTED_DATE}, "alert_date<=? AND type=0 AND alert_tag=?", new String[]{String.valueOf(jCurrentTimeMillis), "1"}, null);
        try {
            Cursor cursor = cursorQuery;
            while (cursor != null && cursor.moveToNext()) {
                long j = cursor.getLong(0);
                long j2 = cursor.getLong(1);
                Intent intent = new Intent();
                intent.setData(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, j));
                intent.putExtra(Notes.Intents.INTENT_EXTRA_ALERT_DATE, j2);
                AlarmReceiver.launchAlarm(this.ctxRef.get(), intent, false);
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursorQuery, null);
            final Cursor cursorQuery2 = context2.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "remind_time <=? AND is_finish == 0", new String[]{String.valueOf(jCurrentTimeMillis)}, null);
            if (cursorQuery2 != null) {
                NoteEntityHelper.INSTANCE.useWithClose(cursorQuery2, "AlarmService", new Function1() { // from class: com.miui.notes.backup.AlarmTask$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AlarmTask.run$lambda$1(cursorQuery2, this, (Cursor) obj);
                    }
                });
            }
            AlarmReceiver.setupAlarms(context2);
            AlarmReceiver.setupTodoAlarms(context2);
            AlarmReceiver.setupRepeatTodoAlarms(context2);
            AlarmReceiver.asyncJobScheduleAlert(context2);
            com.miui.notes.base.utils.Logger.INSTANCE.i("AlarmService", "alarm task:setupAlarms");
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(cursorQuery, th);
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit run$lambda$1(Cursor cursor, AlarmTask alarmTask, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        while (it.moveToNext()) {
            long j = cursor.getLong(cursor.getColumnIndex("id"));
            long j2 = cursor.getLong(cursor.getColumnIndex(Todo.REMIND_TIME));
            Intent intent = new Intent();
            intent.setData(ContentUris.withAppendedId(Uri.parse(Todo.URI_TODO_OUTER), j));
            intent.putExtra(Notes.Intents.INTENT_EXTRA_ALERT_DATE, j2);
            AlarmReceiver.launchTodoAlarm(alarmTask.ctxRef.get(), intent, false);
        }
        return Unit.INSTANCE;
    }
}
