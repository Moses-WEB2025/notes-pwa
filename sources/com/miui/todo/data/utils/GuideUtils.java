package com.miui.todo.data.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.provider.TodoDatabaseHelper;
import com.miui.todo.data.provider.TodoProvider;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class GuideUtils {
    public static TodoGuideConstant sTodoGuideConstant = new TodoGuideConstant();

    public static boolean isGuideTodo(TodoBaseEntity todoBaseEntity) {
        if (todoBaseEntity.getListType() == 0) {
            return todoBaseEntity.getContent() != null && todoBaseEntity.getContent().equals(sTodoGuideConstant.getGuideCommonTip());
        }
        return SubTodoUtils.isSameContentTodo(todoBaseEntity, sTodoGuideConstant.getGuideSubTodo());
    }

    public static void deleteGuideTodo() {
        TodoGuideConstant todoGuideConstant = new TodoGuideConstant();
        SQLiteDatabase readableDatabase = TodoDatabaseHelper.getInstance(NoteApp.getInstance()).getReadableDatabase();
        String str = "'" + todoGuideConstant.getGuideCommonTip().replaceAll("'", "''") + "'";
        readableDatabase.execSQL("delete from todo WHERE plain_text = " + str + " OR content = " + str);
        String str2 = "'" + todoGuideConstant.getGuideSubPlainText().replaceAll("'", "''") + "'";
        readableDatabase.execSQL("delete from todo WHERE plain_text = " + str2 + " OR content = " + str2);
        ContentResolver contentResolver = NoteApp.getInstance().getContentResolver();
        if (contentResolver != null) {
            contentResolver.notifyChange(Uri.parse(TodoProvider.URI_TODO_PRIVATE), (ContentObserver) null, false);
        }
    }

    public static void updateGuideTodo() {
        String str;
        String str2;
        TodoGuideConstant todoGuideConstant = new TodoGuideConstant();
        if (!Locale.getDefault().getLanguage().equals(Locale.CHINESE.getLanguage())) {
            str = "";
        } else {
            String str3 = "'" + "贴心待办，帮你梳理工作和生活".replaceAll("'", "''") + "'";
            str = "plain_text = " + str3 + " OR content = " + str3;
        }
        String str4 = "'" + todoGuideConstant.getGuideCommonTip().replaceAll("'", "''") + "'";
        String str5 = "plain_text = " + str4 + " OR content = " + str4;
        String str6 = "'" + todoGuideConstant.getGuideSubPlainText().replaceAll("'", "''") + "'";
        String str7 = "plain_text = " + str6 + " OR content = " + str6;
        if (TextUtils.isEmpty(str)) {
            str2 = str5 + " or " + str7;
        } else {
            str2 = str + " or " + str5 + " or " + str7;
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("local_status", (Integer) 3);
        Logger.INSTANCE.i("CloudDebug：TodoSyncManager", "TodoSyncManager deleteGuideTodo count:" + NoteApp.getInstance().getContentResolver().update(Uri.parse(TodoProvider.URI_TODO_PRIVATE), contentValues, str2, null));
    }
}
