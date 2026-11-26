package com.xiaomi.passport.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneTokenDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "phone_token.db";
    private static final int DATABASE_VERSION = 1;
    private static final String FIELD_ICCID = "iccid";
    private static final String FIELD_PHONE_TOKEN = "phone_token";
    private static final String TABLE_NAME_PHONE_TOKEN = "phone_token";
    private static final String TAG = "PhoneTokenDBHelper";
    private Context context;
    private static final String SIM_TABLE_CREATE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY, %s TEXT not null unique, %s TEXT not null)", "phone_token", "_id", "iccid", "phone_token");
    private static volatile PhoneTokenDBHelper sInstance = null;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public static synchronized PhoneTokenDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PhoneTokenDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    PhoneTokenDBHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.context = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(SIM_TABLE_CREATE);
    }

    public synchronized void updatePhoneToken(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("iccid", str);
        contentValues.put("phone_token", str2);
        if (0 < getWritableDatabase().replace("phone_token", null, contentValues)) {
            AccountLogger.log(TAG, "1 entry updated in phone_token database");
        } else {
            AccountLogger.log(TAG, "updatePhoneToken failed");
        }
    }

    public synchronized String queryPhoneToken(String str) {
        Cursor cursor = null;
        try {
            Cursor cursorQuery = getWritableDatabase().query("phone_token", new String[]{"phone_token"}, "iccid='" + str + "'", null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() > 0) {
                        cursorQuery.moveToPosition(0);
                        String string = cursorQuery.getString(0);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized boolean deletePhoneToken(String str) {
        if (getWritableDatabase().delete("phone_token", "iccid='" + str + "'", null) > 0) {
            AccountLogger.log(TAG, "1 entry deletePhoneToken from phone_token database");
            return true;
        }
        AccountLogger.log(TAG, "deletePhoneToken failed");
        return false;
    }
}
