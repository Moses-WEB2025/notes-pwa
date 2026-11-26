package com.xiaomi.accounts.secure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class SecureDatabaseHelper extends SQLiteOpenHelper {
    public static final String ACCOUNTS_ID = "_id";
    public static final String ACCOUNTS_NAME = "name";
    public static final String ACCOUNTS_PASSWORD = "password";
    public static final String ACCOUNTS_TYPE = "type";
    public static final String ACCOUNTS_TYPE_COUNT = "count(type)";
    public static final String AUTHTOKENS_ACCOUNTS_ID = "accounts_id";
    public static final String AUTHTOKENS_AUTHTOKEN = "authtoken";
    public static final String AUTHTOKENS_ID = "_id";
    public static final String AUTHTOKENS_TYPE = "type";
    public static final int DATABASE_VERSION = 4;
    public static final String EXTRAS_ACCOUNTS_ID = "accounts_id";
    public static final String EXTRAS_ID = "_id";
    public static final String EXTRAS_KEY = "key";
    public static final String EXTRAS_VALUE = "value";
    public static final String GRANTS_ACCOUNTS_ID = "accounts_id";
    public static final String GRANTS_AUTH_TOKEN_TYPE = "auth_token_type";
    public static final String GRANTS_GRANTEE_UID = "uid";
    public static final String META_KEY = "key";
    public static final String META_VALUE = "value";
    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String TABLE_AUTHTOKENS = "authtokens";
    public static final String TABLE_EXTRAS = "extras";
    public static final String TABLE_GRANTS = "grants";
    public static final String TABLE_META = "meta";
    public static final String TAG = "SecureDatabaseHelper";

    public SecureDatabaseHelper(Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 4);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE accounts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, type TEXT NOT NULL, password TEXT, UNIQUE(name,type))");
        sQLiteDatabase.execSQL("CREATE TABLE authtokens (  _id INTEGER PRIMARY KEY AUTOINCREMENT,  accounts_id INTEGER NOT NULL, type TEXT NOT NULL,  authtoken TEXT,  UNIQUE (accounts_id,type))");
        createGrantsTable(sQLiteDatabase);
        sQLiteDatabase.execSQL("CREATE TABLE extras ( _id INTEGER PRIMARY KEY AUTOINCREMENT, accounts_id INTEGER, key TEXT NOT NULL, value TEXT, UNIQUE(accounts_id,key))");
        sQLiteDatabase.execSQL("CREATE TABLE meta ( key TEXT PRIMARY KEY NOT NULL, value TEXT)");
        createAccountsDeletionTrigger(sQLiteDatabase);
    }

    private void createAccountsDeletionTrigger(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(" CREATE TRIGGER accountsDelete DELETE ON accounts BEGIN   DELETE FROM authtokens     WHERE accounts_id=OLD._id ;   DELETE FROM extras     WHERE accounts_id=OLD._id ;   DELETE FROM grants     WHERE accounts_id=OLD._id ; END");
    }

    private void createGrantsTable(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE grants (  accounts_id INTEGER NOT NULL, auth_token_type STRING NOT NULL,  uid INTEGER NOT NULL,  UNIQUE (accounts_id,auth_token_type,uid))");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        AccountLogger.log(TAG, "upgrade from version " + i + " to version " + i2);
        if (i == 1) {
            i++;
        }
        if (i == 2) {
            createGrantsTable(sQLiteDatabase);
            sQLiteDatabase.execSQL("DROP TRIGGER accountsDelete");
            createAccountsDeletionTrigger(sQLiteDatabase);
            i++;
        }
        if (i == 3) {
            sQLiteDatabase.execSQL("UPDATE accounts SET type = 'com.google' WHERE type == 'com.google.GAIA'");
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        AccountLogger.log(TAG, "opened database " + getDatabaseName());
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    @Deprecated
    public SQLiteDatabase getWritableDatabase() {
        throw new IllegalStateException("use getSecureDatabase instead");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    @Deprecated
    public SQLiteDatabase getReadableDatabase() {
        throw new IllegalStateException("use getSecureDatabase instead");
    }

    public SecureDatabase getSecureDatabase(Context context) {
        return new SecureDatabase(context, super.getWritableDatabase());
    }
}
