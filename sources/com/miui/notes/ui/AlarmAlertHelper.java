package com.miui.notes.ui;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.SpannableStringBuilder;
import androidx.hardware.DataSpace;
import com.market.sdk.DesktopRecommendInfo;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.DataUtils;
import com.miui.richeditor.util.EditorUtils;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.data.utils.TimeUtils;
import com.miui.todo.data.utils.TodoDataUtils;
import com.miui.todo.feature.remind.TodoAlarmAlertActivity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class AlarmAlertHelper {
    private static final String APP_LOCK_FLAG = "showOnLockScreen";
    public static final String EXTRA_NOTE_ID = "extra_note_id";
    public static final String EXTRA_TODO_ID = "extra_todo_id";
    private static final int FIVE_MINUTES_IN_MILLISECONDS = 300000;
    protected static final int SNIPPET_PREW_MAX_LEN = 60;
    private static final String TAG = "AlarmReceiver";
    private static volatile AlarmAlertHelper mInstance;
    ArrayList<Bundle> mAlarmDataBundles = new ArrayList<>();
    ArrayList<Bundle> mTodoDataBundles = new ArrayList<>();
    Handler mHandler = new Handler();
    Runnable mLaunchAlarmRunnable = new Runnable() { // from class: com.miui.notes.ui.AlarmAlertHelper$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$0();
        }
    };
    Runnable mLaunchTodoRunnable = new Runnable() { // from class: com.miui.notes.ui.AlarmAlertHelper$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$1();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        launchAlarm(NoteApp.getInstance(), this.mAlarmDataBundles);
        this.mAlarmDataBundles.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        launchTodo(NoteApp.getInstance(), this.mTodoDataBundles);
        this.mTodoDataBundles.clear();
    }

    private AlarmAlertHelper() {
    }

    public static AlarmAlertHelper getInstance() {
        if (mInstance == null) {
            synchronized (AlarmAlertHelper.class) {
                if (mInstance == null) {
                    mInstance = new AlarmAlertHelper();
                }
            }
        }
        return mInstance;
    }

    public void handleAlarmLaunchIntent(Context context, Intent intent) {
        handleAlarmData(context, intent, this.mAlarmDataBundles);
        this.mHandler.removeCallbacks(this.mLaunchAlarmRunnable);
        this.mHandler.postDelayed(this.mLaunchAlarmRunnable, 100L);
    }

    public void handleTodoLaunchIntent(Context context, Intent intent) {
        handleTodoData(context, intent, this.mTodoDataBundles);
        this.mHandler.removeCallbacks(this.mLaunchTodoRunnable);
        this.mHandler.postDelayed(this.mLaunchTodoRunnable, 100L);
    }

    public static void handleAlarmData(Context context, Intent intent, ArrayList<Bundle> arrayList) {
        if (context == null) {
            return;
        }
        long id = ContentUris.parseId(intent.getData());
        long longExtra = intent.getLongExtra(Notes.Intents.INTENT_EXTRA_ALERT_DATE, 0L);
        Logger.INSTANCE.i(TAG, "launchAlarm: noteId=" + id + ", alertDate=" + Utils.formatTime(longExtra));
        if (longExtra < System.currentTimeMillis() - DesktopRecommendInfo.DEFAULT_CACHE_TIME) {
            Logger.INSTANCE.w(TAG, "launchAlarm: invalid time " + longExtra);
            return;
        }
        if (id <= 0) {
            Logger.INSTANCE.i(TAG, "addIntent failed: noteId=" + id);
            return;
        }
        String strVerifyNoteAlert = DataUtils.verifyNoteAlert(context, id, longExtra);
        try {
            DataUtils.updateAlertTag(context, id);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "onReceive error");
            e.printStackTrace();
        }
        if (strVerifyNoteAlert != null) {
            CharSequence string = EditorUtils.normalizeSnippet(context, strVerifyNoteAlert).toString();
            if (string.length() > 60) {
                CharSequence charSequenceSubSequence = string.subSequence(0, 60);
                if (isLastEmojiCharacter(charSequenceSubSequence.toString(), false) && !isLastEmojiCharacter(charSequenceSubSequence.toString(), true)) {
                    charSequenceSubSequence = charSequenceSubSequence.subSequence(0, 59);
                }
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequenceSubSequence);
                spannableStringBuilder.append((CharSequence) context.getResources().getString(R.string.notelist_string_info));
                string = spannableStringBuilder;
            }
            Bundle bundle = new Bundle();
            bundle.putLong(EXTRA_NOTE_ID, id);
            Logger.INSTANCE.i(TAG, "addIntent success: noteId=" + id);
            bundle.putCharSequence(BaseAlarmAlertActivity.EXTRA_FORMATTED_SNIPPET, string);
            arrayList.add(bundle);
        }
    }

    public static void handleTodoData(Context context, Intent intent, ArrayList<Bundle> arrayList) {
        String plainText;
        if (context == null) {
            return;
        }
        long id = ContentUris.parseId(intent.getData());
        long longExtra = intent.getLongExtra(Notes.Intents.INTENT_EXTRA_ALERT_DATE, 0L);
        Logger.INSTANCE.i(TAG, "launchAlarm: todoId=" + id + ", alertDate=" + Utils.formatTime(longExtra));
        if (longExtra < System.currentTimeMillis() - DesktopRecommendInfo.DEFAULT_CACHE_TIME) {
            Logger.INSTANCE.w(TAG, "launchAlarm: todo invalid time " + longExtra);
            return;
        }
        TodoEntity alertTodoInfo = TodoDataUtils.getAlertTodoInfo(context, id);
        if (alertTodoInfo == null || alertTodoInfo.getLocalStatus() == 3) {
            return;
        }
        if (alertTodoInfo.getRemindRepeatType() > 0) {
            long nextRemindTime = TimeUtils.getNextRemindTime(alertTodoInfo.getRemindRepeatType(), alertTodoInfo.getRemindTime(), alertTodoInfo.getFirstRemindTime());
            Logger.INSTANCE.i(TAG, "launchAlarm: todoId=" + id + " is a repeatType(" + alertTodoInfo.getRemindRepeatType() + ") todo , will remind at next time point: " + nextRemindTime);
            AlarmReceiver.scheduleTodoAlert(context, alertTodoInfo.getId(), nextRemindTime, false);
        }
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_TODO_ID, id);
        if (alertTodoInfo.getListType() == 1) {
            plainText = new SubModeImpl(alertTodoInfo.getContent()).getPlainTextForAlert();
        } else {
            plainText = alertTodoInfo.getPlainText();
        }
        CharSequence string = EditorUtils.normalizeSnippet(context, plainText).toString();
        if (string.length() > 60) {
            CharSequence charSequenceSubSequence = string.subSequence(0, 60);
            if (isLastEmojiCharacter(charSequenceSubSequence.toString(), false) && !isLastEmojiCharacter(charSequenceSubSequence.toString(), true)) {
                charSequenceSubSequence = charSequenceSubSequence.subSequence(0, 59);
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequenceSubSequence);
            spannableStringBuilder.append((CharSequence) context.getResources().getString(R.string.notelist_string_info));
            string = spannableStringBuilder;
        }
        bundle.putCharSequence(BaseAlarmAlertActivity.EXTRA_FORMATTED_SNIPPET, string);
        arrayList.add(bundle);
    }

    private static boolean isLastEmojiCharacter(String str, boolean z) {
        String strSubstring;
        if (str == null || str.isEmpty()) {
            return false;
        }
        if (!z) {
            strSubstring = str.substring(str.length() - 1);
        } else {
            strSubstring = str.substring(str.length() - 2);
        }
        return strSubstring.matches("[\ud83c-\udc00-\udfff]+");
    }

    public static void launchAlarm(Context context, ArrayList<Bundle> arrayList) {
        PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "note-app:alarmWakelock");
        wakeLockNewWakeLock.acquire(10000L);
        Logger.INSTANCE.d(TAG, "launchAlarm,acquire wakelock with timeout 10s");
        if (arrayList != null && !arrayList.isEmpty()) {
            ArrayList<? extends Parcelable> arrayList2 = (ArrayList) arrayList.clone();
            Intent intent = new Intent("com.miui.notes.action.LAUNCH_ALARM");
            intent.setClass(context, AlarmAlertActivity.class);
            intent.addFlags(DataSpace.RANGE_LIMITED);
            intent.putParcelableArrayListExtra(Notes.Intents.INTENT_EXTRA_SNIPPET_LIST, arrayList2);
            intent.putExtra(APP_LOCK_FLAG, true);
            try {
                context.startActivity(intent);
                return;
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "failed to start alarm activity, " + e.getMessage());
                return;
            }
        }
        wakeLockNewWakeLock.release();
    }

    public static void launchTodo(Context context, ArrayList<Bundle> arrayList) {
        PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "note-app:todoWakelock");
        wakeLockNewWakeLock.acquire(10000L);
        Logger.INSTANCE.d(TAG, "launchTodo,acquire wakelock with timeout 10s");
        if (arrayList != null && !arrayList.isEmpty()) {
            ArrayList<? extends Parcelable> arrayList2 = (ArrayList) arrayList.clone();
            Intent intent = new Intent(AlarmReceiver.ACTION_LAUNCH_TODO_ALARM);
            intent.setClass(context, TodoAlarmAlertActivity.class);
            intent.addFlags(DataSpace.RANGE_LIMITED);
            intent.putParcelableArrayListExtra(Notes.Intents.INTENT_EXTRA_SNIPPET_LIST, arrayList2);
            intent.putExtra(APP_LOCK_FLAG, true);
            try {
                context.startActivity(intent);
                return;
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "failed to start todo activity, " + e.getMessage());
                return;
            }
        }
        wakeLockNewWakeLock.release();
    }
}
