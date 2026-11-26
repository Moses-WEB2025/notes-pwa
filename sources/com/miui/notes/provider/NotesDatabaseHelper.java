package com.miui.notes.provider;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.RomUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.DecryptFailedException;
import com.miui.notes.tool.util.EncryptFailedException;
import com.miui.notes.tool.util.EncryptUtil;
import com.miui.notes.tool.util.SecurityUtil;
import com.miui.notes.ui.DowngradeReceiver;
import com.miui.richeditor.schema.NoteSchema;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NotesDatabaseHelper extends SQLiteOpenHelper {
    public static final int ALARM_TAG_HAS_ALARMED = 2;
    public static final int ALERT_TAG_GO_TO_ALARM = 1;
    public static final int ALERT_TAG_NO_ALARM = 0;
    private static final String CREATE_ACCOUNT_TABLE_SQL = "CREATE TABLE account(_id INTEGER PRIMARY KEY AUTOINCREMENT,account_name TEXT NOT NULL,account_type TEXT NOT NULL,data TEXT)";
    private static final String CREATE_DATA_CALL_NOTE_SQL = "CREATE VIEW data_call_view AS SELECT note_id , data3 FROM data WHERE mime_type = 'vnd.android.cursor.item/call_note'";
    private static final String CREATE_DATA_NOTE_ID_INDEX_SQL = "CREATE INDEX IF NOT EXISTS note_id_index ON data(note_id);";
    private static final String CREATE_DATA_TABLE_SQL = "CREATE TABLE data(_id INTEGER PRIMARY KEY AUTOINCREMENT,mime_type TEXT NOT NULL,note_id INTEGER NOT NULL DEFAULT 0,created_date INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000),modified_date INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000),content TEXT NOT NULL DEFAULT '',data1 INTEGER,data2 INTEGER,data3 TEXT NOT NULL DEFAULT '',data4 TEXT NOT NULL DEFAULT '',data5 TEXT NOT NULL DEFAULT '',file_id TEXT NOT NULL DEFAULT '',dirty INTEGER NOT NULL DEFAULT 1,need_update INTEGER NOT NULL DEFAULT 0,need_push INTEGER NOT NULL DEFAULT 0,data_content_type TEXT DEFAULT 'common')";
    private static final String CREATE_DATA_VIEW_SQL = "CREATE VIEW data_view AS SELECT data.* FROM data INNER JOIN note_view ON data.note_id=note_view._id AND parent_id!=-4";
    private static final String CREATE_NOTE_CALL_NOTE_SQL = "CREATE VIEW note_call_view AS SELECT note.* ,data3 FROM note LEFT OUTER JOIN data_call_view ON data_call_view.note_id=note._id";
    private static final String CREATE_NOTE_PARENT_ID_INDEX_SQL = "CREATE INDEX IF NOT EXISTS parent_id_index ON note(parent_id);";
    private static final String CREATE_NOTE_TABLE_SQL = "CREATE TABLE note(_id INTEGER PRIMARY KEY AUTOINCREMENT,parent_id INTEGER NOT NULL DEFAULT 0,alert_date INTEGER NOT NULL DEFAULT 0,bg_color_id INTEGER NOT NULL DEFAULT 0,theme_id INTEGER NOT NULL DEFAULT 0,created_date INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000),has_attachment INTEGER NOT NULL DEFAULT 0,modified_date INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000),notes_count INTEGER NOT NULL DEFAULT 0,snippet TEXT NOT NULL DEFAULT '',type INTEGER NOT NULL DEFAULT 0,widget_id INTEGER NOT NULL DEFAULT 0,widget_type INTEGER NOT NULL DEFAULT -1,sync_id INTEGER NOT NULL DEFAULT 0,local_modified INTEGER NOT NULL DEFAULT 1,origin_parent_id INTEGER NOT NULL DEFAULT 0,gtask_id TEXT NOT NULL DEFAULT '',version INTEGER NOT NULL DEFAULT 0,sort INTEGER NOT NULL DEFAULT 0,subject TEXT UNIQUE,sync_tag TEXT NOT NULL DEFAULT '',account_id INTEGER NOT NULL DEFAULT 0,stick_date INTEGER NOT NULL DEFAULT 0,source_name TEXT,source_package TEXT,source_intent TEXT,plain_text TEXT NOT NULL DEFAULT '',sync_data1 INTEGER NOT NULL DEFAULT 0,sync_data2 INTEGER NOT NULL DEFAULT 0,sync_data3 INTEGER NOT NULL DEFAULT 0,sync_data4 INTEGER NOT NULL DEFAULT 0,sync_data5 INTEGER NOT NULL DEFAULT 0,sync_data6 TEXT NOT NULL DEFAULT '',sync_data7 TEXT NOT NULL DEFAULT '',sync_data8 TEXT NOT NULL DEFAULT '',moved_date INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000) ,alert_tag INTEGER NOT NULL DEFAULT 0 ,deletion_tag INTEGER NOT NULL DEFAULT 0 ,title TEXT DEFAULT '',note_content_type TEXT NOT NULL DEFAULT 'common',mind_content TEXT NOT NULL DEFAULT '',note_text_count INTEGER NOT NULL DEFAULT 0 ,mind_content_plain_text TEXT NOT NULL DEFAULT '',ent_name TEXT DEFAULT '',images TEXT DEFAULT '',thumbnail TEXT DEFAULT '',recognized_file_names TEXT DEFAULT '',is_empty INTEGER NOT NULL DEFAULT 0 ,web_images TEXT DEFAULT '',external_id TEXT, ai_text_used INTEGER NOT NULL DEFAULT 0, aigc_data_id TEXT DEFAULT '' ,current_page_num INTEGER NOT NULL DEFAULT 0,current_page_scale REAL NOT NULL DEFAULT 1.0)";
    private static final String CREATE_NOTE_VIEW_SQL = "CREATE VIEW note_view AS SELECT * FROM note WHERE (parent_id>=0 OR parent_id=-2 OR parent_id=-4  OR parent_id=-5 OR parent_id =- 6 OR parent_id =- 7 OR parent_id =- 8) AND parent_id!=-4";
    static final String DB_NAME = "note.db";
    public static final int DB_VERSION = 53;
    public static final int DELETION_TAG_DELETED = 1;
    public static final int DELETION_TAG_REMAIN = 0;
    private static final String FOLDER_DELETE_NOTES_ON_DELETE_TRIGGER = "CREATE TRIGGER folder_delete_notes_on_delete  AFTER DELETE ON note WHEN old.type=1 BEGIN  DELETE FROM note   WHERE parent_id=old._id;  UPDATE note   SET origin_parent_id=0   WHERE origin_parent_id=old._id; END";
    private static final String FOLDER_MOVE_NOTES_ON_TRASH_TRIGGER = "CREATE TRIGGER folder_move_notes_on_trash  AFTER UPDATE OF parent_id ON note WHEN new.parent_id=-3  AND old.parent_id=0  AND old.type=1 BEGIN  UPDATE note   SET parent_id=-3    ,local_modified=1   WHERE parent_id=old._id; END";
    private static final String NOTE_DECREASE_FOLDER_COUNT_ON_DELETE_TRIGGER = "CREATE TRIGGER decrease_folder_count_on_delete  AFTER DELETE ON note WHEN old.type=0 AND old.deletion_tag = 0 BEGIN   UPDATE note   SET notes_count=notes_count-1  WHERE _id=old.parent_id; END";
    private static final String NOTE_DELETE_DATA_ON_DELETE_TRIGGER = "CREATE TRIGGER delete_data_on_delete  AFTER DELETE ON note BEGIN  DELETE FROM data   WHERE note_id=old._id; END";
    private static final String NOTE_DELETE_EMPTY_ON_UPDATE_TRIGGER = "CREATE TRIGGER note_delete_empty_on_update  AFTER UPDATE OF parent_id ON note WHEN new.parent_id=-3  AND old.parent_id<>-3  AND new.type=0  AND new.snippet=''  AND new.sync_id=0 BEGIN  DELETE FROM note   WHERE _id=new._id; END";
    private static final String NOTE_DELETE_EMPTY_ON_UPDATE_TRIGGER_V43 = "CREATE TRIGGER note_delete_empty_on_update  AFTER UPDATE OF parent_id ON note WHEN new.parent_id=-3  AND old.parent_id<>-3  AND new.type=0  AND new.snippet=''  AND (new.title='' OR new.title is null )  AND new.sync_id=0 BEGIN  DELETE FROM note   WHERE _id=new._id; END";
    private static final String NOTE_INCREASE_FOLDER_COUNT_ON_INSERT_TRIGGER = "CREATE TRIGGER increase_folder_count_on_insert  AFTER INSERT ON note WHEN new.type=0 BEGIN   UPDATE note   SET notes_count=notes_count + 1  WHERE _id=new.parent_id; END";
    private static final String NOTE_INCREASE_VERSION_ON_UPDATE_TRIGGER = "CREATE TRIGGER note_increase_version_on_update  AFTER UPDATE ON note BEGIN  UPDATE note   SET version=version+1   WHERE _id=old._id; END";
    private static final String NOTE_MOVE_TO_OTHER_FOLDER_TRIGGER = " CREATE TRIGGER note_move_to_other_folder_on_update  AFTER UPDATE OF parent_id ON note WHEN new.parent_id<> old.parent_id AND new.type=0 BEGIN UPDATE note SET moved_date=strftime('%s','now') * 1000  WHERE old._id = _id; END";
    private static final String NOTE_UPDATE_ALERT_TAG_ON_INSERT_TRIGGER = "CREATE TRIGGER note_update_alert_tag_on_insert  AFTER INSERT ON note BEGIN    UPDATE note   SET alert_tag = 1   WHERE _id=new._id AND new.alert_date>(strftime('%s','now') * 1000);   UPDATE note   SET alert_tag = 2   WHERE _id=new._id AND new.alert_date<=(strftime('%s','now') * 1000)   AND alert_date <>0; END";
    private static final String NOTE_UPDATE_ALERT_TAG_ON_UPDATE_TRIGGER = "CREATE TRIGGER note_update_alert_tag_on_update  AFTER UPDATE ON note BEGIN   UPDATE note   SET alert_tag = 1   WHERE _id=old._id AND new.alert_date>(strftime('%s','now') * 1000);   UPDATE note   SET alert_tag = 2   WHERE _id=old._id AND new.alert_date<=(strftime('%s','now') * 1000)   AND alert_date <>0;   UPDATE note   SET alert_tag = 0   WHERE _id=old._id AND new.alert_date=0; END";
    private static final String NOTE_UPDATE_FOLDER_COUNT_ON_DELETION_UPDATE_TRIGGER = "CREATE TRIGGER update_folder_count_on_deletion_update  AFTER UPDATE OF deletion_tag ON note WHEN old.type=0 AND old.parent_id = -3 AND old.deletion_tag = 0 AND new.deletion_tag = 1 BEGIN   UPDATE note  SET notes_count=notes_count - 1  WHERE _id = -3; END";
    private static final String NOTE_UPDATE_FOLDER_COUNT_ON_UPDATE_TRIGGER = "CREATE TRIGGER update_folder_count_on_update  AFTER UPDATE OF parent_id ON note WHEN old.type=0 BEGIN   UPDATE note   SET notes_count=notes_count + 1  WHERE _id=new.parent_id;  UPDATE note   SET notes_count=notes_count-1  WHERE _id=old.parent_id  AND notes_count>0; END";
    private static final String NOTE_UPDATE_ORIGIN_PARENT_ID_ON_TRASH_TRIGGER = "CREATE TRIGGER note_update_origin_parent_id_on_trash  AFTER UPDATE OF parent_id ON note WHEN new.parent_id=-3  AND old.parent_id<>-3  AND old.type=0 BEGIN  UPDATE note   SET origin_parent_id=old.parent_id   WHERE _id=old._id; END";
    private static final String NOTE_UPDATE_SORT_ON_INSERT_TRIGGER = "CREATE TRIGGER update_sort_on_insert  AFTER INSERT ON note BEGIN   UPDATE note   SET sort=(    SELECT MAX(sort)     FROM note     WHERE parent_id=new.parent_id)+1  WHERE _id=new._id; END";
    private static final String NOTE_UPDATE_SORT_ON_UPDATE_TRIGGER = "CREATE TRIGGER update_sort_on_update  AFTER UPDATE OF parent_id ON note BEGIN   UPDATE note   SET sort=(    SELECT MAX(sort)     FROM note     WHERE parent_id=new.parent_id)+1  WHERE _id=new._id; END";
    private static final String TAG = "NotesDatabaseHelper";
    private static volatile NotesDatabaseHelper mInstance;
    private Context mContext;

    public interface TABLE {
        public static final String ACCOUNT = "account";
        public static final String DATA = "data";
        public static final String NOTE = "note";
    }

    public interface VIEW {
        public static final String DATA = "data_view";
        public static final String DATA_CALL = "data_call_view";
        public static final String NOTE = "note_view";
        public static final String NOTE_CALL = "note_call_view";
    }

    private void upgradeToV31(SQLiteDatabase sQLiteDatabase) {
    }

    public NotesDatabaseHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 53);
        this.mContext = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onConfigure(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.enableWriteAheadLogging();
    }

    public void createNoteTable(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.d(TAG, "createNoteTable: 53");
        sQLiteDatabase.execSQL(CREATE_NOTE_TABLE_SQL);
        sQLiteDatabase.execSQL(CREATE_NOTE_PARENT_ID_INDEX_SQL);
        reCreateNoteTableTriggers(sQLiteDatabase);
        createSystemFolder(sQLiteDatabase);
    }

    private void reCreateNoteTableTriggers(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_folder_count_on_update");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS decrease_folder_count_on_delete");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS delete_data_on_delete");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS increase_folder_count_on_insert");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS folder_delete_notes_on_delete");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS folder_move_notes_on_trash");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_sort_on_insert");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_sort_on_update");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS note_increase_version_on_update");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS note_update_origin_parent_id_on_trash");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS note_delete_empty_on_update");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS note_move_to_other_folder_on_update");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS note_update_alert_tag_on_update");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS note_update_alert_tag_on_insert");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_folder_count_on_deletion_update");
        sQLiteDatabase.execSQL(NOTE_UPDATE_FOLDER_COUNT_ON_UPDATE_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_DECREASE_FOLDER_COUNT_ON_DELETE_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_DELETE_DATA_ON_DELETE_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_INCREASE_FOLDER_COUNT_ON_INSERT_TRIGGER);
        sQLiteDatabase.execSQL(FOLDER_DELETE_NOTES_ON_DELETE_TRIGGER);
        sQLiteDatabase.execSQL(FOLDER_MOVE_NOTES_ON_TRASH_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_INCREASE_VERSION_ON_UPDATE_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_UPDATE_ORIGIN_PARENT_ID_ON_TRASH_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_DELETE_EMPTY_ON_UPDATE_TRIGGER_V43);
        sQLiteDatabase.execSQL(NOTE_MOVE_TO_OTHER_FOLDER_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_UPDATE_ALERT_TAG_ON_UPDATE_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_UPDATE_ALERT_TAG_ON_INSERT_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_UPDATE_FOLDER_COUNT_ON_DELETION_UPDATE_TRIGGER);
    }

    private void createSystemFolder(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.d(TAG, "createSystemFolder  53");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", (Integer) (-2));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        contentValues.clear();
        contentValues.put("_id", (Integer) 0);
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        contentValues.clear();
        contentValues.put("_id", (Integer) (-1));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        contentValues.clear();
        contentValues.put("_id", (Integer) (-3));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        contentValues.clear();
        contentValues.put("_id", (Integer) (-4));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        contentValues.clear();
        contentValues.put("_id", (Integer) (-5));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        contentValues.clear();
        contentValues.put("_id", (Integer) (-6));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        contentValues.clear();
        contentValues.put("_id", (Integer) (-7));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        contentValues.clear();
        contentValues.put("_id", (Integer) (-8));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
    }

    public void createDataTable(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.d(TAG, "createDataTable: 53");
        sQLiteDatabase.execSQL(CREATE_DATA_TABLE_SQL);
        sQLiteDatabase.execSQL(CREATE_DATA_NOTE_ID_INDEX_SQL);
    }

    public void createAccountTable(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.d(TAG, "createAccountTable: 53");
        sQLiteDatabase.execSQL(CREATE_ACCOUNT_TABLE_SQL);
    }

    public void createViews(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.d(TAG, "createViews: 53");
        sQLiteDatabase.execSQL(CREATE_NOTE_VIEW_SQL);
        sQLiteDatabase.execSQL(CREATE_DATA_VIEW_SQL);
        sQLiteDatabase.execSQL(CREATE_DATA_CALL_NOTE_SQL);
        sQLiteDatabase.execSQL(CREATE_NOTE_CALL_NOTE_SQL);
    }

    public static synchronized NotesDatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (NotesDatabaseHelper.class) {
                if (mInstance == null) {
                    mInstance = new NotesDatabaseHelper(context);
                }
            }
        }
        return mInstance;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.d(TAG, "onCreate: 53");
        createNoteTable(sQLiteDatabase);
        createDataTable(sQLiteDatabase);
        createAccountTable(sQLiteDatabase);
        createViews(sQLiteDatabase);
        PreferenceUtils.setDowngrade(this.mContext, false);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        boolean z;
        boolean z2;
        Logger.INSTANCE.d(TAG, "onUpgrade oldVersion: " + i + ", newVersion: " + i2);
        if (i == 1) {
            upgradeToV2(sQLiteDatabase);
            i++;
            z = true;
        } else {
            z = false;
        }
        if (i != 2 || z) {
            z2 = false;
        } else {
            upgradeToV3(sQLiteDatabase);
            i++;
            z2 = true;
        }
        if (i == 3) {
            upgradeToV4(sQLiteDatabase);
            i++;
        }
        if (i == 4) {
            upgradeToV5(sQLiteDatabase);
            i++;
        }
        if (i == 5) {
            upgradeToV6(sQLiteDatabase);
            i++;
        }
        if (i == 6) {
            upgradeToV7(sQLiteDatabase);
            i++;
        }
        if (i == 7) {
            upgradeToV8(sQLiteDatabase);
            i++;
        }
        if (i == 8) {
            upgradeToV9(sQLiteDatabase);
            i++;
        }
        if (i == 9) {
            upgradeToV10(sQLiteDatabase);
            i++;
        }
        if (i == 10) {
            upgradeToV11(sQLiteDatabase);
            i++;
        }
        if (i == 11) {
            upgradeToV12(sQLiteDatabase);
            i++;
        }
        if (i == 12) {
            upgradeToV13(sQLiteDatabase);
            i++;
            z2 = true;
        }
        if (i == 13) {
            upgradeToV14(sQLiteDatabase);
            i++;
            z2 = true;
        }
        if (i == 14) {
            upgradeToV15(sQLiteDatabase);
            i++;
            z2 = true;
        }
        if (i == 15) {
            upgradeToV16(sQLiteDatabase);
            i++;
        }
        if (i == 16) {
            upgradeToV17(sQLiteDatabase);
            i++;
        }
        if (i == 17) {
            upgradeToV18(sQLiteDatabase);
            i++;
        }
        if (i == 18) {
            upgradeToV19(sQLiteDatabase);
            i++;
        }
        if (i == 19) {
            upgradeToV20(sQLiteDatabase);
            i++;
        }
        if (i == 20) {
            upgradeToV21(sQLiteDatabase);
            i++;
        }
        if (i == 21) {
            upgradeToV22(sQLiteDatabase);
            i++;
        }
        if (i == 22) {
            upgradeToV23(sQLiteDatabase);
            i++;
        }
        if (i == 23) {
            upgradeToV24(sQLiteDatabase);
            i++;
        }
        if (i == 24) {
            upgradeToV25(sQLiteDatabase);
            i++;
        }
        if (i == 25) {
            upgradeToV26(sQLiteDatabase);
            i++;
        }
        if (i == 26) {
            upgradeToV27(sQLiteDatabase);
            i++;
        }
        if (i == 27) {
            upgradeToV28(sQLiteDatabase);
            i++;
        }
        if (i == 28) {
            upgradeToV29(sQLiteDatabase);
            i++;
        }
        if (i == 29) {
            upgradeToV30(sQLiteDatabase);
            i++;
        }
        if (i == 30) {
            upgradeToV31(sQLiteDatabase);
            i++;
        }
        if (i == 31) {
            updateToV32(sQLiteDatabase);
            i++;
        }
        if (i == 32) {
            updateToV33(sQLiteDatabase);
            i++;
        }
        if (i == 33) {
            updateToV34(sQLiteDatabase);
            i++;
        }
        if (i == 34) {
            updateToV35(sQLiteDatabase);
            i++;
        }
        if (i == 35) {
            updateToV36(sQLiteDatabase);
            i++;
        }
        if (i == 36) {
            updateToV37(sQLiteDatabase);
            i++;
        }
        if (i == 37) {
            updateToV38(sQLiteDatabase);
            i++;
            PreferenceUtils.setIsNeedSyncTitle(true);
        }
        if (i == 38) {
            updateToV39(sQLiteDatabase);
            i++;
            PreferenceUtils.setIsNeedFullUpgrade(true);
        }
        if (i == 39) {
            updateToV40(sQLiteDatabase);
            i++;
        }
        if (i == 40) {
            updateToV41(sQLiteDatabase);
            i++;
        }
        if (i == 41) {
            updateToV42(sQLiteDatabase);
            i++;
        }
        if (i == 42) {
            i++;
            z2 = true;
        }
        if (i == 43) {
            i++;
            if (!RomUtils.isPadMode()) {
                PreferenceUtils.setIsNeedFullUpgradeWithHandwrite(true);
            }
        }
        if (i == 44) {
            updateToV45(sQLiteDatabase);
            i++;
        }
        if (i == 45) {
            updateToV46(sQLiteDatabase);
            i++;
        }
        if (i == 46) {
            updateToV47(sQLiteDatabase);
            i++;
            PreferenceUtils.setIsNeedFullUpgradeWithMultiHandwrite(true);
        }
        if (i == 47) {
            updateToV48(sQLiteDatabase);
            i++;
        }
        if (i == 48) {
            updateToV49(sQLiteDatabase);
            i++;
        }
        if (i == 49) {
            updateToV50(sQLiteDatabase);
            i++;
        }
        if (i == 50) {
            updateToV51(sQLiteDatabase);
            i++;
        }
        if (i == 51) {
            updateToV52(sQLiteDatabase);
            i++;
        }
        if (i == 52) {
            updateToV53(sQLiteDatabase);
            i++;
        }
        if (z2) {
            reCreateNoteTableTriggers(sQLiteDatabase);
        }
        Logger.INSTANCE.i(TAG, "onUpgrade end, final oldVersion: " + i + ", newVersion: " + i2);
        if (i != i2) {
            throw new IllegalStateException("Upgrade notes database to version " + i2 + "fails");
        }
        PreferenceUtils.setDowngrade(this.mContext, false);
    }

    private void upgradeToV2(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS note");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS data");
        createNoteTable(sQLiteDatabase);
        createDataTable(sQLiteDatabase);
    }

    private void upgradeToV3(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_note_modified_date_on_insert");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_note_modified_date_on_delete");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_note_modified_date_on_update");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN gtask_id TEXT NOT NULL DEFAULT ''");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", (Integer) (-3));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
    }

    private void upgradeToV4(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN version INTEGER NOT NULL DEFAULT 0");
    }

    private void upgradeToV5(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS increase_folder_count_on_update");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS decrease_folder_count_on_update");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_folder_count_on_update");
        sQLiteDatabase.execSQL(NOTE_UPDATE_FOLDER_COUNT_ON_UPDATE_TRIGGER);
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS increase_folder_count_on_insert");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS decrease_folder_count_on_delete");
        sQLiteDatabase.execSQL(NOTE_INCREASE_FOLDER_COUNT_ON_INSERT_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_DECREASE_FOLDER_COUNT_ON_DELETE_TRIGGER);
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN sort INTEGER NOT NULL DEFAULT 0");
    }

    private void upgradeToV6(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(NOTE_UPDATE_SORT_ON_INSERT_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_UPDATE_SORT_ON_UPDATE_TRIGGER);
    }

    private void upgradeToV7(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS delete_data_on_delete");
        sQLiteDatabase.execSQL(NOTE_DELETE_DATA_ON_DELETE_TRIGGER);
    }

    private void upgradeToV8(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN subject TEXT NOT NULL DEFAULT ''");
        sQLiteDatabase.execSQL(String.format(Locale.US, "UPDATE %s SET %s=%s WHERE %s!=%d", "note", "subject", "snippet", "type", 0));
        sQLiteDatabase.execSQL("UPDATE note SET bg_color_id=(  SELECT t.bg_color_id   FROM note AS t   WHERE t.parent_id=note._id   ORDER BY t.sort DESC, t.modified_date DESC LIMIT 1), snippet=(  SELECT t.snippet   FROM note AS t   WHERE t.parent_id=note._id   ORDER BY t.sort DESC, t.modified_date DESC LIMIT 1) WHERE type!=0 AND notes_count>0");
    }

    private void upgradeToV9(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_note_content_on_delete");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_note_content_on_insert");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_note_content_on_update");
        Cursor cursorQuery = sQLiteDatabase.query("note", new String[]{"_id"}, null, null, null, null, "sort ASC, modified_date ASC");
        if (cursorQuery != null) {
            sQLiteDatabase.beginTransaction();
            try {
                ContentValues contentValues = new ContentValues(1);
                int i = 0;
                while (cursorQuery.moveToNext()) {
                    long j = cursorQuery.getLong(0);
                    contentValues.clear();
                    contentValues.put("sort", Integer.valueOf(i));
                    sQLiteDatabase.update("note", contentValues, "_id=?", new String[]{Long.toString(j)});
                    i++;
                }
                sQLiteDatabase.setTransactionSuccessful();
                cursorQuery.close();
                sQLiteDatabase.endTransaction();
            } catch (Throwable th) {
                cursorQuery.close();
                sQLiteDatabase.endTransaction();
                throw th;
            }
        }
        Cursor cursorQuery2 = sQLiteDatabase.query("note", new String[]{"snippet"}, "type=0", null, null, null, null);
        if (cursorQuery2 != null) {
            try {
                Pattern patternCompile = Pattern.compile("^(\\u221A |\\u25A1 )", 8);
                while (cursorQuery2.moveToNext()) {
                    if (patternCompile.matcher(cursorQuery2.getString(0)).find()) {
                        PreferenceUtils.setTouchThroughTip(this.mContext, true);
                        break;
                    }
                }
            } finally {
                cursorQuery2.close();
            }
        }
    }

    private void upgradeToV10(SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues(2);
        contentValues.put("widget_id", (Integer) 0);
        contentValues.put("widget_type", (Integer) (-1));
        sQLiteDatabase.update("note", contentValues, "widget_type=0", null);
    }

    private void upgradeToV11(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_sort_on_insert");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_sort_on_update");
    }

    private void upgradeToV12(SQLiteDatabase sQLiteDatabase) {
        HashMap map = new HashMap();
        int i = 1;
        ContentValues contentValues = new ContentValues(1);
        Cursor cursorQuery = sQLiteDatabase.query("data", new String[]{"_id", "content"}, "mime_type!=? AND mime_type!=?", new String[]{Notes.TextData.CONTENT_ITEM_TYPE, Notes.CallData.CONTENT_ITEM_TYPE}, null, null, "note_id");
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                try {
                    String string = cursorQuery.getString(1);
                    String strUpdateImageToV12 = updateImageToV12(string);
                    if (strUpdateImageToV12 != null) {
                        map.put(string, strUpdateImageToV12);
                        contentValues.clear();
                        contentValues.put("content", strUpdateImageToV12);
                        sQLiteDatabase.update("data", contentValues, "_id=" + cursorQuery.getInt(0), null);
                    }
                } catch (Throwable th) {
                    cursorQuery.close();
                    throw th;
                }
            }
            cursorQuery.close();
        }
        Cursor cursorQuery2 = sQLiteDatabase.query("data", new String[]{"_id", "content", "note_id"}, "mime_type=?", new String[]{Notes.TextData.CONTENT_ITEM_TYPE}, null, null, "note_id");
        if (cursorQuery2 != null) {
            while (cursorQuery2.moveToNext()) {
                try {
                    String string2 = cursorQuery2.getString(i);
                    Matcher matcher = Pattern.compile("^☺ ([^\r\n]+)$", 8).matcher(string2);
                    ArrayList arrayList = new ArrayList();
                    while (matcher.find()) {
                        arrayList.add(new Pair(Integer.valueOf(matcher.start(i)), Integer.valueOf(matcher.end(i))));
                    }
                    if (!arrayList.isEmpty()) {
                        StringBuilder sb = new StringBuilder(string2);
                        for (int size = arrayList.size() - i; size >= 0; size--) {
                            Pair pair = (Pair) arrayList.get(size);
                            String str = (String) map.get(string2.substring(((Integer) pair.first).intValue(), ((Integer) pair.second).intValue()));
                            if (str != null) {
                                sb.replace(((Integer) pair.first).intValue(), ((Integer) pair.second).intValue(), str);
                            }
                        }
                        contentValues.clear();
                        contentValues.put("content", sb.toString());
                        sQLiteDatabase.update("data", contentValues, "_id=" + cursorQuery2.getLong(0), null);
                        contentValues.clear();
                        contentValues.put("snippet", sb.toString());
                        sQLiteDatabase.update("note", contentValues, "_id=" + cursorQuery2.getLong(2), null);
                        i = 1;
                    }
                } catch (Throwable th2) {
                    cursorQuery2.close();
                    throw th2;
                }
            }
            cursorQuery2.close();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("UPDATE note SET notes_count=(  SELECT count(*)   FROM note AS t   WHERE t.parent_id=note._id    AND t.type=0) WHERE type!=0");
        sQLiteDatabase.execSQL(sb2.toString());
        sb2.setLength(0);
        sb2.append("UPDATE note SET");
        sb2.append(" snippet=(");
        sb2.append("  SELECT t.snippet");
        sb2.append("   FROM note AS t");
        sb2.append("   WHERE t.parent_id=note._id");
        sb2.append("   ORDER BY t.modified_date DESC LIMIT 1)");
        sb2.append(" WHERE type!=").append(0).append(" AND notes_count>0");
        sQLiteDatabase.execSQL(sb2.toString());
    }

    private String updateImageToV12(String str) throws Throwable {
        String attachmentPath = AttachmentUtils.getAttachmentPath(this.mContext, str);
        String str2 = attachmentPath + ".v12";
        File file = new File(str2);
        Bitmap bitmapCreateThumbnail = ImageUtils.INSTANCE.createThumbnail(attachmentPath, false, false);
        String fileSha1Digest = null;
        if (bitmapCreateThumbnail != null) {
            if (AttachmentUtils.INSTANCE.saveBitmap(bitmapCreateThumbnail, file)) {
                fileSha1Digest = AttachmentUtils.getFileSha1Digest(str2);
                File file2 = new File(AttachmentUtils.getAttachmentPath(this.mContext, fileSha1Digest));
                file.renameTo(file2);
                file2.setReadable(true, false);
                new File(attachmentPath).delete();
            }
            bitmapCreateThumbnail.recycle();
        }
        return fileSha1Digest;
    }

    private void upgradeToV13(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.delete("note", "parent_id<0 AND parent_id<>-2", null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_id", (Integer) 0);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        contentValues.put("version", (Integer) 0);
        contentValues.put("origin_parent_id", (Integer) 0);
        sQLiteDatabase.update("note", contentValues, null, null);
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN sync_tag TEXT NOT NULL DEFAULT ''");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN account_id INTEGER NOT NULL DEFAULT 0");
        sQLiteDatabase.execSQL("ALTER TABLE data ADD COLUMN file_id TEXT NOT NULL DEFAULT ''");
        sQLiteDatabase.execSQL("ALTER TABLE data ADD COLUMN dirty INTEGER NOT NULL DEFAULT 1");
        createAccountTable(sQLiteDatabase);
        sQLiteDatabase.execSQL("CREATE TABLE note_temp(_id INTEGER PRIMARY KEY AUTOINCREMENT,parent_id INTEGER NOT NULL DEFAULT 0,alert_date INTEGER NOT NULL DEFAULT 0,bg_color_id INTEGER NOT NULL DEFAULT 0,created_date INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000),has_attachment INTEGER NOT NULL DEFAULT 0,modified_date INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000),notes_count INTEGER NOT NULL DEFAULT 0,snippet TEXT NOT NULL DEFAULT '',type INTEGER NOT NULL DEFAULT 0,widget_id INTEGER NOT NULL DEFAULT 0,widget_type INTEGER NOT NULL DEFAULT -1,sync_id INTEGER NOT NULL DEFAULT 0,local_modified INTEGER NOT NULL DEFAULT 1,origin_parent_id INTEGER NOT NULL DEFAULT 0,gtask_id TEXT NOT NULL DEFAULT '',version INTEGER NOT NULL DEFAULT 0,sort INTEGER NOT NULL DEFAULT 0,subject TEXT NOT NULL DEFAULT '',sync_tag TEXT NOT NULL DEFAULT '',account_id INTEGER NOT NULL DEFAULT 0)");
        sQLiteDatabase.execSQL("INSERT INTO note_temp SELECT * from note;");
        sQLiteDatabase.execSQL("DROP TABLE note;");
        sQLiteDatabase.execSQL("ALTER TABLE note_temp RENAME TO note;");
    }

    private void upgradeToV14(SQLiteDatabase sQLiteDatabase) {
        Cursor cursorQuery = sQLiteDatabase.query("note", new String[]{"_id"}, "type=1 AND subject=''", null, null, null, null);
        if (cursorQuery != null) {
            try {
                String string = this.mContext.getString(R.string.default_folder_name);
                ContentValues contentValues = new ContentValues();
                int i = 0;
                while (cursorQuery.moveToNext()) {
                    long j = cursorQuery.getLong(0);
                    contentValues.put("subject", string + StringUtils.SPACE + i);
                    sQLiteDatabase.update("note", contentValues, "_id=" + j, null);
                    i++;
                }
                cursorQuery.close();
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
        sQLiteDatabase.execSQL(String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT,parent_id INTEGER NOT NULL DEFAULT 0,alert_date INTEGER NOT NULL DEFAULT 0,bg_color_id INTEGER NOT NULL DEFAULT 0,created_date INTEGER NOT NULL DEFAULT (strftime('%%s','now') * 1000),has_attachment INTEGER NOT NULL DEFAULT 0,modified_date INTEGER NOT NULL DEFAULT (strftime('%%s','now') * 1000),notes_count INTEGER NOT NULL DEFAULT 0,snippet TEXT NOT NULL DEFAULT '',type INTEGER NOT NULL DEFAULT 0,widget_id INTEGER NOT NULL DEFAULT 0,widget_type INTEGER NOT NULL DEFAULT -1,sync_id INTEGER NOT NULL DEFAULT 0,local_modified INTEGER NOT NULL DEFAULT 1,origin_parent_id INTEGER NOT NULL DEFAULT 0,gtask_id TEXT NOT NULL DEFAULT '',version INTEGER NOT NULL DEFAULT 0,sort INTEGER NOT NULL DEFAULT 0,subject TEXT %s,sync_tag TEXT NOT NULL DEFAULT '',account_id INTEGER NOT NULL DEFAULT 0)", "note_temp", ""));
        sQLiteDatabase.execSQL("INSERT INTO note_temp SELECT * from note;");
        sQLiteDatabase.execSQL("UPDATE note_temp SET subject=NULL WHERE _id<=0 OR type=0;");
        sQLiteDatabase.execSQL("DROP TABLE note;");
        sQLiteDatabase.execSQL(String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT,parent_id INTEGER NOT NULL DEFAULT 0,alert_date INTEGER NOT NULL DEFAULT 0,bg_color_id INTEGER NOT NULL DEFAULT 0,created_date INTEGER NOT NULL DEFAULT (strftime('%%s','now') * 1000),has_attachment INTEGER NOT NULL DEFAULT 0,modified_date INTEGER NOT NULL DEFAULT (strftime('%%s','now') * 1000),notes_count INTEGER NOT NULL DEFAULT 0,snippet TEXT NOT NULL DEFAULT '',type INTEGER NOT NULL DEFAULT 0,widget_id INTEGER NOT NULL DEFAULT 0,widget_type INTEGER NOT NULL DEFAULT -1,sync_id INTEGER NOT NULL DEFAULT 0,local_modified INTEGER NOT NULL DEFAULT 1,origin_parent_id INTEGER NOT NULL DEFAULT 0,gtask_id TEXT NOT NULL DEFAULT '',version INTEGER NOT NULL DEFAULT 0,sort INTEGER NOT NULL DEFAULT 0,subject TEXT %s,sync_tag TEXT NOT NULL DEFAULT '',account_id INTEGER NOT NULL DEFAULT 0)", "note", "UNIQUE"));
        sQLiteDatabase.execSQL("INSERT INTO note SELECT * from note_temp;");
        sQLiteDatabase.execSQL("DROP TABLE note_temp;");
    }

    private void upgradeToV15(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE data_temp(_id INTEGER PRIMARY KEY AUTOINCREMENT,mime_type TEXT NOT NULL,note_id INTEGER NOT NULL DEFAULT 0,created_date INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000),modified_date INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000),content TEXT NOT NULL DEFAULT '',data1 INTEGER,data2 INTEGER,data3 TEXT NOT NULL DEFAULT '',data4 TEXT NOT NULL DEFAULT '',data5 TEXT NOT NULL DEFAULT '',file_id TEXT NOT NULL DEFAULT '',dirty INTEGER NOT NULL DEFAULT 1)");
        sQLiteDatabase.execSQL("INSERT INTO data_temp SELECT * from data;");
        sQLiteDatabase.execSQL("DROP TABLE data;");
        sQLiteDatabase.execSQL("ALTER TABLE data_temp RENAME TO data;");
    }

    private void upgradeToV16(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("UPDATE note SET notes_count=(  SELECT count(*)   FROM note AS t   WHERE t.parent_id=note._id    AND t.type=0) WHERE type!=0");
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS update_folder_count_on_update");
        sQLiteDatabase.execSQL(NOTE_UPDATE_FOLDER_COUNT_ON_UPDATE_TRIGGER);
    }

    private void upgradeToV17(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN stick_date INTEGER NOT NULL DEFAULT 0");
    }

    private void upgradeToV18(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_NOTE_PARENT_ID_INDEX_SQL);
        sQLiteDatabase.execSQL(CREATE_NOTE_VIEW_SQL);
        sQLiteDatabase.execSQL(CREATE_DATA_VIEW_SQL);
    }

    private void upgradeToV19(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN source_name TEXT");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN source_package TEXT");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN source_intent TEXT");
    }

    private void upgradeToV20(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS folder_move_notes_on_trash");
        sQLiteDatabase.execSQL(FOLDER_MOVE_NOTES_ON_TRASH_TRIGGER);
    }

    private void upgradeToV21(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS note_update_origin_parent_id_on_trash");
        sQLiteDatabase.execSQL(NOTE_UPDATE_ORIGIN_PARENT_ID_ON_TRASH_TRIGGER);
    }

    private void upgradeToV22(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN plain_text TEXT NOT NULL DEFAULT ''");
        Cursor cursorQuery = sQLiteDatabase.query("note", new String[]{"_id", "snippet"}, "type=0", null, null, null, null);
        if (cursorQuery != null) {
            try {
                ContentValues contentValues = new ContentValues();
                while (cursorQuery.moveToNext()) {
                    long j = cursorQuery.getLong(0);
                    contentValues.put("plain_text", NoteSchema.extractPlainText(cursorQuery.getString(1)));
                    sQLiteDatabase.update("note", contentValues, "_id=" + j, null);
                }
                cursorQuery.close();
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
    }

    private void upgradeToV23(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN sync_data1 INTEGER NOT NULL DEFAULT 0");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN sync_data2 INTEGER NOT NULL DEFAULT 0");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN sync_data3 INTEGER NOT NULL DEFAULT 0");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN sync_data4 INTEGER NOT NULL DEFAULT 0");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN sync_data5 INTEGER NOT NULL DEFAULT 0");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN sync_data6 TEXT NOT NULL DEFAULT ''");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN sync_data7 TEXT NOT NULL DEFAULT ''");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN sync_data8 TEXT NOT NULL DEFAULT ''");
    }

    private void upgradeToV24(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("UPDATE note SET local_modified=1 WHERE _id IN(   SELECT DISTINCT(note_id) FROM data   WHERE mime_type='image/jpg')");
        sQLiteDatabase.execSQL("UPDATE data SET mime_type='image/jpeg',dirty=1 WHERE mime_type='image/jpg'");
    }

    private void upgradeToV25(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN theme_id INTEGER NOT NULL DEFAULT 0");
        sQLiteDatabase.execSQL("UPDATE note SET theme_id = bg_color_id;");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", (Integer) (-4));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        sQLiteDatabase.execSQL("DROP VIEW IF EXISTS note_view");
        sQLiteDatabase.execSQL(CREATE_NOTE_VIEW_SQL);
    }

    private void upgradeToV26(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP VIEW IF EXISTS note_view");
        sQLiteDatabase.execSQL(CREATE_NOTE_VIEW_SQL);
        sQLiteDatabase.execSQL("DROP VIEW IF EXISTS data_view");
        sQLiteDatabase.execSQL(CREATE_DATA_VIEW_SQL);
        Cursor cursorQuery = sQLiteDatabase.query("note", new String[]{"_id", "snippet", "plain_text"}, "type=0 AND parent_id = -4", null, null, null, null);
        if (cursorQuery != null) {
            try {
                ContentValues contentValues = new ContentValues();
                while (cursorQuery.moveToNext()) {
                    long j = cursorQuery.getLong(0);
                    String string = cursorQuery.getString(1);
                    String string2 = cursorQuery.getString(2);
                    try {
                        String strEncrypt = SecurityUtil.encrypt(this.mContext, string);
                        String strEncrypt2 = SecurityUtil.encrypt(this.mContext, string2);
                        contentValues.put("snippet", strEncrypt);
                        contentValues.put("plain_text", strEncrypt2);
                        sQLiteDatabase.update("note", contentValues, "_id=" + j, null);
                        contentValues.clear();
                        contentValues.put("content", strEncrypt);
                        sQLiteDatabase.update("data", contentValues, "note_id = ? AND mime_type = ?", new String[]{String.valueOf(j), Notes.TextData.CONTENT_ITEM_TYPE});
                        contentValues.clear();
                    } catch (EncryptFailedException e) {
                        throw new RuntimeException("can not encrypt notes", e);
                    }
                }
                cursorQuery.close();
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void upgradeToV27(SQLiteDatabase sQLiteDatabase) {
        int i = 0;
        int i2 = 1;
        String str = "plain_text";
        Cursor cursorQuery = sQLiteDatabase.query("note", new String[]{"_id", "snippet", "plain_text"}, "type=0 AND parent_id = -4", null, null, null, null);
        if (cursorQuery != null) {
            try {
                ContentValues contentValues = new ContentValues();
                int i3 = 1;
                while (cursorQuery.moveToNext()) {
                    long j = cursorQuery.getLong(i);
                    String string = cursorQuery.getString(i2);
                    String string2 = cursorQuery.getString(2);
                    i = i;
                    boolean z = i2;
                    try {
                        String strDecrypt = SecurityUtil.decrypt(this.mContext, string);
                        String strDecrypt2 = SecurityUtil.decrypt(this.mContext, string2);
                        try {
                            String strEncrypt = EncryptUtil.encrypt(this.mContext, strDecrypt);
                            String strEncrypt2 = EncryptUtil.encrypt(this.mContext, strDecrypt2);
                            contentValues.put("snippet", strEncrypt);
                            String str2 = str;
                            contentValues.put(str2, strEncrypt2);
                            sQLiteDatabase.update("note", contentValues, "_id=" + j, null);
                            contentValues.clear();
                            contentValues.put("content", strEncrypt);
                            String[] strArr = new String[2];
                            strArr[i] = String.valueOf(j);
                            strArr[z ? 1 : 0] = Notes.TextData.CONTENT_ITEM_TYPE;
                            sQLiteDatabase.update("data", contentValues, "note_id = ? AND mime_type = ?", strArr);
                            contentValues.clear();
                            str = str2;
                            i2 = z ? 1 : 0;
                        } catch (EncryptFailedException e) {
                            throw new RuntimeException("can not encrypt notes", e);
                        }
                    } catch (DecryptFailedException unused) {
                        String str3 = str;
                        if (i3 != 0) {
                            PreferenceUtils.setShowRecoverNotesHint(this.mContext, z);
                            i3 = i;
                        }
                        sQLiteDatabase.delete("note", "_id=" + j, null);
                        sQLiteDatabase.delete("data", "note_id=" + j, null);
                        str = str3;
                        i2 = 1;
                    }
                }
                cursorQuery.close();
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
    }

    private void upgradeToV28(SQLiteDatabase sQLiteDatabase) {
        Cursor cursorQuery = sQLiteDatabase.query("note", new String[]{"_id", "snippet"}, "type=0 AND ( parent_id = -2 OR parent_id >= 0 ) AND plain_text=''", null, null, null, null);
        if (cursorQuery != null) {
            try {
                ContentValues contentValues = new ContentValues();
                while (cursorQuery.moveToNext()) {
                    long j = cursorQuery.getLong(0);
                    String string = cursorQuery.getString(1);
                    if (!TextUtils.isEmpty(string)) {
                        contentValues.put("plain_text", NoteSchema.extractPlainText(string));
                        sQLiteDatabase.update("note", contentValues, "_id = " + j, null);
                    }
                    contentValues.clear();
                }
            } finally {
                cursorQuery.close();
            }
        }
    }

    private void upgradeToV29(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(NOTE_DELETE_EMPTY_ON_UPDATE_TRIGGER);
    }

    private void upgradeToV30(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS folder_delete_notes_on_delete");
        sQLiteDatabase.execSQL(FOLDER_DELETE_NOTES_ON_DELETE_TRIGGER);
        ContentValues contentValues = new ContentValues();
        contentValues.put("origin_parent_id", (Integer) 0);
        sQLiteDatabase.update("note", contentValues, "origin_parent_id NOT IN (SELECT _id FROM note    WHERE type=1       OR _id=-2       OR _id=-4)", null);
    }

    private void updateToV32(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN moved_date INTEGER NOT NULL DEFAULT 0");
        sQLiteDatabase.execSQL("UPDATE note SET moved_date = strftime('%s','now') * 1000");
        sQLiteDatabase.execSQL(NOTE_MOVE_TO_OTHER_FOLDER_TRIGGER);
    }

    private void updateToV33(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN alert_tag INTEGER NOT NULL DEFAULT 0");
        sQLiteDatabase.execSQL("UPDATE note SET alert_tag = 1 WHERE alert_date > (strftime('%s','now') * 1000)");
        sQLiteDatabase.execSQL("UPDATE note SET alert_tag = 2 WHERE alert_date <= (strftime('%s','now') * 1000) AND alert_tag <> 0");
        sQLiteDatabase.execSQL(NOTE_UPDATE_ALERT_TAG_ON_UPDATE_TRIGGER);
        sQLiteDatabase.execSQL(NOTE_UPDATE_ALERT_TAG_ON_INSERT_TRIGGER);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (PreferenceUtils.getDowngrade(this.mContext)) {
            return;
        }
        PreferenceUtils.setDowngrade(this.mContext, true);
        Iterator<ActivityManager.RunningAppProcessInfo> it = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses().iterator();
        while (it.hasNext()) {
            if (it.next().processName.equals("com.miui.notes")) {
                Intent intent = new Intent();
                intent.setAction(DowngradeReceiver.DOWNGRADE_BROADCAST_ACTION);
                intent.setPackage("com.miui.notes");
                this.mContext.sendBroadcast(intent);
                return;
            }
        }
    }

    private void updateToV34(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN deletion_tag INTEGER NOT NULL DEFAULT 0");
            sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS decrease_folder_count_on_delete");
            sQLiteDatabase.execSQL(NOTE_DECREASE_FOLDER_COUNT_ON_DELETE_TRIGGER);
            sQLiteDatabase.execSQL(NOTE_UPDATE_FOLDER_COUNT_ON_DELETION_UPDATE_TRIGGER);
        } catch (SQLException e) {
            Logger.INSTANCE.e(TAG, e.getMessage() + ",exception:" + e);
        }
    }

    private void updateToV35(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(CREATE_DATA_CALL_NOTE_SQL);
            sQLiteDatabase.execSQL(CREATE_NOTE_CALL_NOTE_SQL);
        } catch (SQLException e) {
            Logger.INSTANCE.e(TAG, e.getMessage() + ",exception:" + e);
        }
    }

    private void updateToV36(SQLiteDatabase sQLiteDatabase) {
        AudioUtils.checkNoteAudioValidity(this.mContext, sQLiteDatabase);
    }

    private void updateToV37(SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", (Integer) (-5));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        sQLiteDatabase.execSQL("DROP VIEW IF EXISTS note_view");
        sQLiteDatabase.execSQL(CREATE_NOTE_VIEW_SQL);
    }

    private void updateToV38(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN title TEXT DEFAULT ''");
    }

    private void updateToV39(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN note_content_type TEXT NOT NULL DEFAULT 'common' ");
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN mind_content TEXT NOT NULL DEFAULT '' ");
    }

    private void updateToV40(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE note ADD COLUMN mind_content_plain_text TEXT NOT NULL DEFAULT '' ");
    }

    private void updateToV41(SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", (Integer) (-6));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        sQLiteDatabase.execSQL("DROP VIEW IF EXISTS note_view");
        sQLiteDatabase.execSQL(CREATE_NOTE_VIEW_SQL);
    }

    private void updateToV42(SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", (Integer) (-7));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        sQLiteDatabase.execSQL("DROP VIEW IF EXISTS note_view");
        sQLiteDatabase.execSQL(CREATE_NOTE_VIEW_SQL);
    }

    private void updateToV45(SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", (Integer) (-8));
        contentValues.put("type", (Integer) 2);
        sQLiteDatabase.insert("note", null, contentValues);
        sQLiteDatabase.execSQL("DROP VIEW IF EXISTS note_view");
        sQLiteDatabase.execSQL(CREATE_NOTE_VIEW_SQL);
    }

    private void updateToV46(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.i(TAG, "updateToV46");
        addIntColumn(sQLiteDatabase, "note", Notes.Note.NOTE_TEXT_COUNT);
    }

    private void updateToV47(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.i(TAG, "updateToV47");
        addTextColumn(sQLiteDatabase, "note", "ent_name");
        addTextColumn(sQLiteDatabase, "note", Notes.Note.IMAGES);
        addTextColumn(sQLiteDatabase, "note", "thumbnail");
        addTextColumn(sQLiteDatabase, "note", "recognized_file_names");
        addIntColumn(sQLiteDatabase, "note", Notes.Note.IS_EMPTY);
        addIntColumn(sQLiteDatabase, "data", Notes.Data.DATA_NEED_UPDATE);
        addIntColumn(sQLiteDatabase, "data", Notes.Data.DATA_NEED_PUSH);
        addTextColumn(sQLiteDatabase, "data", Notes.Data.DATA_CONTENT_TYPE);
    }

    private void updateToV48(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.i(TAG, "updateToV48");
        addTextColumn(sQLiteDatabase, "note", Notes.Note.WEB_IMAGES);
    }

    private void updateToV49(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.i(TAG, "updateToV49");
        addTextColumn(sQLiteDatabase, "note", Notes.Note.EXTERNAL_ID);
    }

    private void updateToV50(SQLiteDatabase sQLiteDatabase) {
        Logger.INSTANCE.i(TAG, "updateToV50");
        addIntColumn(sQLiteDatabase, "note", Notes.Note.NOTE_TEXT_COUNT);
    }

    private void addTextColumn(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        try {
            if (isColumnExist(sQLiteDatabase, str, str2)) {
                return;
            }
            Logger.INSTANCE.i(TAG, "addTextColumn tableName:" + str + " columnName:" + str2);
            sQLiteDatabase.execSQL("ALTER TABLE " + str + " ADD COLUMN " + str2 + " TEXT DEFAULT '' ");
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "addTextColumn error " + e.getMessage());
        }
    }

    private void addIntColumn(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        try {
            if (isColumnExist(sQLiteDatabase, str, str2)) {
                return;
            }
            Logger.INSTANCE.i(TAG, "addIntColumn tableName:" + str + " columnName:" + str2);
            sQLiteDatabase.execSQL("ALTER TABLE " + str + " ADD COLUMN " + str2 + " INTEGER NOT NULL DEFAULT 0 ");
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "addIntColumn error " + e.getMessage());
        }
    }

    private void addFloatColumn(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        try {
            if (isColumnExist(sQLiteDatabase, str, str2)) {
                return;
            }
            Logger.INSTANCE.i(TAG, "addIntColumn tableName:" + str + " columnName:" + str2);
            sQLiteDatabase.execSQL("ALTER TABLE " + str + " ADD COLUMN " + str2 + " REAL NOT NULL DEFAULT 1.0 ");
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "addIntColumn error " + e.getMessage());
        }
    }

    private boolean isColumnExist(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        boolean z = false;
        Cursor cursorRawQuery = null;
        try {
            cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM " + str + " LIMIT 0", null);
            if (cursorRawQuery != null && cursorRawQuery.getColumnIndex(str2) != -1) {
                z = true;
            }
            Logger.INSTANCE.i(TAG, "isColumnExist tableName:" + str + " columnName:" + str2 + " result:" + z);
            return z;
        } catch (Exception e) {
            Log.e(TAG, "isColumnExist get error:" + e.getMessage());
            return z;
        } finally {
            if (cursorRawQuery != null && !cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
    }

    private void updateToV51(SQLiteDatabase sQLiteDatabase) {
        try {
            Log.i(TAG, "updateToV51");
            addIntColumn(sQLiteDatabase, "note", Notes.Note.AI_TEXT_USED);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void updateToV52(SQLiteDatabase sQLiteDatabase) {
        try {
            Log.i(TAG, "updateToV52");
            addIntColumn(sQLiteDatabase, "note", Notes.Note.AIGC_DATA_ID);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void updateToV53(SQLiteDatabase sQLiteDatabase) {
        try {
            Log.i(TAG, "updateToV53");
            addIntColumn(sQLiteDatabase, "note", "current_page_num");
            addFloatColumn(sQLiteDatabase, "note", Notes.Note.CURRENT_PAGE_SCALE);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
