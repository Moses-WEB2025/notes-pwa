package com.xiaomi.accountsdk.guestaccount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccount;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountType;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public final class GuestAccountDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "guest_account.db";
    private static final int DATABASE_VERSION = 2;
    private static final String FIELD_PASS_TOKEN = "passToken";
    private static final String FIELD_USER_ID = "userId";
    private static final String TABLE_NAME_ACCOUNT_TABLE = "account";
    private static final String TAG = "GuestAccountDatabaseHe";
    private static final String FIELD_C_USER_ID = "cUserId";
    private static final String FIELD_USER_TYPE = "userType";
    private static final String GUEST_ACCOUNT_TABLE_CREATE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT,%s TEXT, %s INTEGER DEFAULT 0)", "account", "_id", "userId", FIELD_C_USER_ID, "passToken", FIELD_USER_TYPE);
    private static final String TABLE_NAME_SERVICE_TOKEN = "service_token";
    private static final String FIELD_SID = "sid";
    private static final String FIELD_SERVICE_TOKEN = "serviceToken";
    private static final String FIELD_SECURITY = "security";
    private static final String FIELD_SLH = "slh";
    private static final String FIELD_PH = "ph";
    private static final String SERVICE_TOKEN_TABLE_CREATE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT,  %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME_SERVICE_TOKEN, "_id", "userId", FIELD_SID, FIELD_SERVICE_TOKEN, FIELD_SECURITY, FIELD_SLH, FIELD_PH);
    private static volatile GuestAccountDatabaseHelper sInstance = null;

    public static synchronized GuestAccountDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new GuestAccountDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public GuestAccountDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 2);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(GUEST_ACCOUNT_TABLE_CREATE);
        sQLiteDatabase.execSQL(SERVICE_TOKEN_TABLE_CREATE);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        AccountLogger.log(TAG, "downgrade ignore");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        AccountLogger.log(TAG, "upgrade from version " + i + " to version" + i2);
        if (i2 == 2 && i == 1) {
            try {
                sQLiteDatabase.beginTransaction();
                sQLiteDatabase.execSQL("ALTER TABLE account ADD COLUMN cUserId TEXT");
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
            } catch (Throwable th) {
                try {
                    AccountLogger.log(TAG, th.getMessage(), th);
                } finally {
                    sQLiteDatabase.endTransaction();
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x00b0  */
    public GuestAccount readGuestAccount(String str) {
        String string;
        String string2;
        String string3;
        Cursor cursorQuery = getReadableDatabase().query("account", new String[]{"userId", FIELD_C_USER_ID, "passToken", FIELD_USER_TYPE}, null, null, null, null, null);
        String string4 = null;
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() != 0) {
                    cursorQuery.moveToPosition(0);
                    String string5 = cursorQuery.getString(0);
                    String string6 = cursorQuery.getString(1);
                    String string7 = cursorQuery.getString(2);
                    int i = cursorQuery.getInt(3);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (TextUtils.isEmpty(string5)) {
                        return null;
                    }
                    if (TextUtils.isEmpty(str)) {
                        string = null;
                        string2 = null;
                        string3 = null;
                    } else {
                        Cursor cursorQuery2 = getReadableDatabase().query(TABLE_NAME_SERVICE_TOKEN, new String[]{FIELD_SERVICE_TOKEN, FIELD_SECURITY, FIELD_SLH, FIELD_PH}, "sid=?", new String[]{str}, null, null, null);
                        if (cursorQuery2 != null) {
                            try {
                                if (cursorQuery2.getCount() != 0) {
                                    cursorQuery2.moveToPosition(0);
                                    string4 = cursorQuery2.getString(0);
                                    string = cursorQuery2.getString(1);
                                    string2 = cursorQuery2.getString(2);
                                    string3 = cursorQuery2.getString(3);
                                } else {
                                    string = null;
                                    string2 = null;
                                    string3 = null;
                                }
                            } finally {
                                if (cursorQuery2 != null) {
                                    cursorQuery2.close();
                                }
                            }
                        } else {
                            string = null;
                            string2 = null;
                            string3 = null;
                        }
                    }
                    return new GuestAccount.Builder().userId(string5).cUserId(string6).passToken(string7).type(GuestAccountType.getFromServerValue(i)).sid(str).serviceToken(string4).security(string).slh(string2).ph(string3).build();
                }
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    public synchronized void updatePassToken(GuestAccount guestAccount) {
        try {
            if (guestAccount == null) {
                throw new IllegalArgumentException("ga == null");
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("userId", guestAccount.userId);
            contentValues.put(FIELD_C_USER_ID, guestAccount.cUserId);
            contentValues.put("passToken", guestAccount.passToken);
            contentValues.put(FIELD_USER_TYPE, Integer.valueOf(guestAccount.type != null ? guestAccount.type.serverValue : -1));
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase.update("account", contentValues, null, null) > 0) {
                AccountLogger.log(TAG, "1 entry updated in guest_account/account database");
            } else {
                writableDatabase.insert("account", null, contentValues);
                AccountLogger.log(TAG, "1 entry inserted in guest_account/account database");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void updateServiceToken(GuestAccount guestAccount) {
        try {
            if (guestAccount == null) {
                throw new IllegalArgumentException("ga == null");
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(FIELD_SID, guestAccount.sid);
            contentValues.put(FIELD_SERVICE_TOKEN, guestAccount.serviceToken);
            contentValues.put(FIELD_SECURITY, guestAccount.security);
            contentValues.put(FIELD_SLH, guestAccount.slh);
            contentValues.put(FIELD_PH, guestAccount.ph);
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase.update(TABLE_NAME_SERVICE_TOKEN, contentValues, "sid=?", new String[]{guestAccount.sid}) > 0) {
                AccountLogger.log(TAG, "1 entry updated in guest_account/serviceToken database");
            } else {
                writableDatabase.insert(TABLE_NAME_SERVICE_TOKEN, null, contentValues);
                AccountLogger.log(TAG, "1 entry inserted in guest_account/serviceToken database");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void removeAll() {
        getWritableDatabase().delete("account", null, null);
        getWritableDatabase().delete(TABLE_NAME_SERVICE_TOKEN, null, null);
    }

    public void removeServiceToken(String str) {
        updateServiceToken(new GuestAccount.Builder().sid(str).serviceToken("").security("").slh("").ph("").build());
    }
}
