package com.miui.todo.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class MamlDataReceiver extends BroadcastReceiver {
    private static final String ACTION_FINISH_SUB_TODO = "miui.intent.action.FINISH_SUB_TODO";
    private static final String ACTION_FINISH_TODO = "miui.intent.action.FINISH_TODO";
    private static final String ACTION_UNFINISH_SUB_TODO = "miui.intent.action.UNFINISH_SUB_TODO";
    private static final String ACTION_UNFINISH_TODO = "miui.intent.action.UNFINISH_TODO";
    private static final String INDEX_ID = "index_id";
    private static final int INVAILD = -1;
    private static final String TAG = "MamlDataReceiver";
    private static final String TODO_ID = "todo_id";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equals(ACTION_FINISH_TODO)) {
            String stringExtra = intent.getStringExtra(TODO_ID);
            Logger.INSTANCE.d(TAG, "todo_id " + stringExtra);
            try {
                TodoProviderDemo.changeItemFinishStatus(NoteApp.getInstance(), Long.parseLong(stringExtra), true);
                return;
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "fail in changeItemFinishStatus ,exception:" + e);
                return;
            }
        }
        if (intent.getAction().equals(ACTION_FINISH_SUB_TODO)) {
            String stringExtra2 = intent.getStringExtra(TODO_ID);
            int intExtra = intent.getIntExtra(INDEX_ID, -1);
            Logger.INSTANCE.d(TAG, "todo_id " + stringExtra2 + " index " + intExtra);
            if (intExtra != -1) {
                try {
                    TodoProviderDemo.changeItemFinishStatus(NoteApp.getInstance(), Long.parseLong(stringExtra2), intExtra, true);
                    return;
                } catch (Exception e2) {
                    Logger.INSTANCE.e(TAG, "fail in changeItemFinishStatus ,exception:" + e2);
                    return;
                }
            }
            return;
        }
        if (intent.getAction().equals(ACTION_UNFINISH_TODO)) {
            String stringExtra3 = intent.getStringExtra(TODO_ID);
            Logger.INSTANCE.d(TAG, "todo_id " + stringExtra3);
            try {
                TodoProviderDemo.changeItemFinishStatus(NoteApp.getInstance(), Long.parseLong(stringExtra3), false);
                return;
            } catch (Exception e3) {
                Logger.INSTANCE.e(TAG, "fail in changeItemFinishStatus ,exception:" + e3);
                return;
            }
        }
        if (intent.getAction().equals(ACTION_UNFINISH_SUB_TODO)) {
            String stringExtra4 = intent.getStringExtra(TODO_ID);
            int intExtra2 = intent.getIntExtra(INDEX_ID, -1);
            Logger.INSTANCE.d(TAG, "todo_id " + stringExtra4 + " index " + intExtra2);
            if (intExtra2 != -1) {
                try {
                    TodoProviderDemo.changeItemFinishStatus(NoteApp.getInstance(), Long.parseLong(stringExtra4), intExtra2, false);
                } catch (Exception e4) {
                    Logger.INSTANCE.e(TAG, "fail in changeItemFinishStatus ,exception:" + e4);
                }
            }
        }
    }
}
