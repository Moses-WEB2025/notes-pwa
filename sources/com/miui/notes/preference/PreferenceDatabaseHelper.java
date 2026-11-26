package com.miui.notes.preference;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class PreferenceDatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_PREF_TABLE_SQL = "CREATE TABLE pref(_id INTEGER PRIMARY KEY AUTOINCREMENT,key TEXT NOT NULL UNIQUE,value TEXT)";
    private static final String DB_NAME = "dbpref.db";
    private static final int DB_VERSION = 1;
    private static final Object INSTANCE_LOCK = new Object();
    public static final String TABLE = "pref";
    private static final String TAG = "PreferenceDatabaseHelper";
    private static PreferenceDatabaseHelper sInstance;
    private final SharedPreferenceProvider mPreferenceProvider;

    public interface PrefColumns extends BaseColumns {
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_VALUE = "value";
    }

    public interface SharedPreferenceProvider {
        void dispose(SharedPreferences sharedPreferences);

        SharedPreferences getSharedPreference();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    private static class DefaultSharedPreferenceProvider implements SharedPreferenceProvider {
        private Context iContext;
        private String iName;

        @Override // com.miui.notes.preference.PreferenceDatabaseHelper.SharedPreferenceProvider
        public void dispose(SharedPreferences sharedPreferences) {
        }

        public DefaultSharedPreferenceProvider(Context context, String str) {
            this.iContext = context;
            this.iName = str;
        }

        @Override // com.miui.notes.preference.PreferenceDatabaseHelper.SharedPreferenceProvider
        public SharedPreferences getSharedPreference() {
            return this.iContext.getSharedPreferences(this.iName, 0);
        }
    }

    public static PreferenceDatabaseHelper getInstance(Context context, String str) {
        return getInstance(context, new DefaultSharedPreferenceProvider(context, str));
    }

    public static PreferenceDatabaseHelper getInstance(Context context, SharedPreferenceProvider sharedPreferenceProvider) {
        PreferenceDatabaseHelper preferenceDatabaseHelper;
        synchronized (INSTANCE_LOCK) {
            if (sInstance == null) {
                sInstance = new PreferenceDatabaseHelper(context, sharedPreferenceProvider);
            }
            preferenceDatabaseHelper = sInstance;
        }
        return preferenceDatabaseHelper;
    }

    protected PreferenceDatabaseHelper(Context context, SharedPreferenceProvider sharedPreferenceProvider) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.mPreferenceProvider = sharedPreferenceProvider;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_PREF_TABLE_SQL);
        SharedPreferenceProvider sharedPreferenceProvider = this.mPreferenceProvider;
        if (sharedPreferenceProvider != null) {
            SharedPreferences sharedPreference = sharedPreferenceProvider.getSharedPreference();
            migrate(sharedPreference, sQLiteDatabase);
            this.mPreferenceProvider.dispose(sharedPreference);
        }
    }

    private void migrate(SharedPreferences sharedPreferences, SQLiteDatabase sQLiteDatabase) {
        String string;
        Set<Map.Entry<String, ?>> setEntrySet = sharedPreferences.getAll().entrySet();
        ContentValues contentValues = new ContentValues();
        sQLiteDatabase.beginTransaction();
        try {
            for (Map.Entry<String, ?> entry : setEntrySet) {
                contentValues.clear();
                contentValues.put("key", entry.getKey());
                Object value = entry.getValue();
                if (value instanceof Set) {
                    string = AbstractSharedPreferences.INSTANCE.buildStringSet((Set) value);
                } else {
                    string = value.toString();
                }
                contentValues.put("value", string);
                sQLiteDatabase.insert("pref", null, contentValues);
            }
            sQLiteDatabase.setTransactionSuccessful();
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }
}
