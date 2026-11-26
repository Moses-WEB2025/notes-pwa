package com.miui.todo.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* JADX INFO: loaded from: classes4.dex */
public class TodoDatabaseHelper extends SQLiteOpenHelper {
    public static final String ADD_TODO_COLUMN_SQL = "Alter table todo add column ";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE todo(id INTEGER PRIMARY KEY AUTOINCREMENT,content TEXT, remind_type INTEGER NOT NULL DEFAULT 0,expire_time INTEGER NOT NULL DEFAULT 0,remind_time INTEGER NOT NULL DEFAULT 0,is_finish INTEGER NOT NULL DEFAULT 0,mark_finish_time INTEGER NOT NULL DEFAULT 0,last_modified_time NTEGER NOT NULL DEFAULT (strftime('%s','now')*1000),create_time INTEGER NOT NULL DEFAULT 0,input_type INTEGER NOT NULL DEFAULT 0,audio_file_name TEXT, custom_sort_id INTEGER NOT NULL DEFAULT 0,plain_text TEXT, hide_type INTEGER NOT NULL DEFAULT 0,priority INTEGER NOT NULL DEFAULT 0,remind_repeat_type INTEGER NOT NULL DEFAULT 0,category INTEGER NOT NULL DEFAULT 0,label TEXT, sync_id INTEGER NOT NULL DEFAULT 0,sync_tag TEXT, remind_tag INTEGER NOT NULL DEFAULT 0,words_count INTEGER NOT NULL DEFAULT 0,parent_id INTEGER NOT NULL DEFAULT 0,son_id INTEGER NOT NULL DEFAULT 0,type INTEGER NOT NULL DEFAULT 0,sort_id INTEGER NOT NULL DEFAULT 0,account_id INTEGER NOT NULL DEFAULT 0,version INTEGER NOT NULL DEFAULT 0,snippet TEXT, pre_integer1 INTEGER NOT NULL DEFAULT 0,pre_integer2 INTEGER NOT NULL DEFAULT 0,pre_integer3 INTEGER NOT NULL DEFAULT 0,pre_integer4 INTEGER NOT NULL DEFAULT 0,pre_integer5 INTEGER NOT NULL DEFAULT 0,pre_integer6 INTEGER NOT NULL DEFAULT 0,pre_integer7 INTEGER NOT NULL DEFAULT 0,pre_integer8 INTEGER NOT NULL DEFAULT 0,pre_text1 TEXT, pre_text2 TEXT, pre_text3 TEXT, pre_text4 TEXT, pre_text5 TEXT, pre_text6 TEXT, pre_text7 TEXT, pre_text8 TEXT, sync_etag INTEGER NOT NULL DEFAULT 0,server_status INTEGER NOT NULL DEFAULT 0,audo_file_field TEXT, first_remind_time INTEGER NOT NULL DEFAULT 0,folder_id INTEGER NOT NULL DEFAULT 0,local_status INTEGER NOT NULL DEFAULT 0,source INTEGER NOT NULL DEFAULT 0,audio_file_size INTEGER NOT NULL DEFAULT 0,color_label INTEGER NOT NULL DEFAULT 0,list_type INTEGER NOT NULL DEFAULT 0)";
    private static final String DB_NAME = "todo.db";
    private static final int DB_VERSION = 2;
    private static final String INTEGER_NOT_NULL_DEFAULT_0 = " INTEGER NOT NULL DEFAULT 0,";
    private static final String TEXT = " TEXT, ";
    private static TodoDatabaseHelper mInstance;

    private TodoDatabaseHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 2);
    }

    public static synchronized TodoDatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TodoDatabaseHelper(context);
        }
        return mInstance;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TODO_TABLE);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i == 1) {
            upgradeToV2(sQLiteDatabase);
        }
    }

    private void upgradeToV2(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("Alter table todo add column sync_etag INTEGER NOT NULL DEFAULT 0 ");
        sQLiteDatabase.execSQL("Alter table todo add column server_status INTEGER NOT NULL DEFAULT 0 ");
        sQLiteDatabase.execSQL("Alter table todo add column audo_file_field TEXT ");
        sQLiteDatabase.execSQL("Alter table todo add column first_remind_time TEXT ");
        sQLiteDatabase.execSQL("Alter table todo add column folder_id INTEGER NOT NULL DEFAULT 0 ");
        sQLiteDatabase.execSQL("Alter table todo add column local_status INTEGER NOT NULL DEFAULT 0 ");
        sQLiteDatabase.execSQL("Alter table todo add column source INTEGER NOT NULL DEFAULT 0 ");
        sQLiteDatabase.execSQL("Alter table todo add column audio_file_size INTEGER NOT NULL DEFAULT 0 ");
        sQLiteDatabase.execSQL("Alter table todo add column color_label INTEGER NOT NULL DEFAULT 0 ");
        sQLiteDatabase.execSQL("Alter table todo add column list_type INTEGER NOT NULL DEFAULT 0 ");
    }
}
