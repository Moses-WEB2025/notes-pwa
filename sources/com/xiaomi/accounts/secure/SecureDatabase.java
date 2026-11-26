package com.xiaomi.accounts.secure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class SecureDatabase {
    public static final String TAG = "SecureDatabase";
    private final Context context;
    private final SQLiteDatabase database;

    public SecureDatabase(Context context, SQLiteDatabase sQLiteDatabase) {
        this.context = context;
        this.database = sQLiteDatabase;
    }

    public void beginTransaction() {
        this.database.beginTransaction();
    }

    public void setTransactionSuccessful() {
        this.database.setTransactionSuccessful();
    }

    public void endTransaction() {
        this.database.endTransaction();
    }

    private String encrypt(String str) {
        return TextUtils.isEmpty(str) ? str : SecureDataManager.encrypt(this.context, str);
    }

    private String[] encrypt(Object[] objArr) {
        String[] strArr = objArr == null ? null : new String[objArr.length];
        if (objArr != null) {
            for (int i = 0; i < objArr.length; i++) {
                Object obj = objArr[i];
                if (obj instanceof String) {
                    strArr[i] = encrypt((String) obj);
                } else {
                    strArr[i] = String.valueOf(obj);
                }
            }
        }
        return strArr;
    }

    private ContentValues encrypt(ContentValues contentValues) {
        if (contentValues == null) {
            return null;
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.putAll(contentValues);
        for (String str : contentValues.keySet()) {
            Object obj = contentValues.get(str);
            if (obj instanceof String) {
                contentValues2.put(str, encrypt((String) obj));
            }
        }
        return contentValues2;
    }

    private String decrypt(String str) {
        return TextUtils.isEmpty(str) ? str : SecureDataManager.decrypt(this.context, str);
    }

    private Cursor decrypt(Cursor cursor) {
        if (cursor == null) {
            AccountLogger.log(TAG, "query: null cursor");
            return null;
        }
        AccountLogger.log(TAG, "query: cursor count=" + cursor.getCount());
        MatrixCursor matrixCursor = new MatrixCursor(cursor.getColumnNames());
        int columnCount = matrixCursor.getColumnCount();
        while (cursor.moveToNext()) {
            Object[] objArr = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                int type = cursor.getType(i);
                if (type == 0) {
                    objArr[i] = null;
                } else if (type == 1) {
                    objArr[i] = Long.valueOf(cursor.getLong(i));
                } else if (type == 2) {
                    objArr[i] = Double.valueOf(cursor.getDouble(i));
                } else if (type == 3) {
                    objArr[i] = decrypt(cursor.getString(i));
                } else if (type == 4) {
                    objArr[i] = cursor.getBlob(i);
                } else {
                    throw new IllegalStateException("unknown data type");
                }
            }
            AccountLogger.log(TAG, "query: row=" + stringify(objArr));
            matrixCursor.addRow(objArr);
        }
        return matrixCursor;
    }

    public Cursor query(boolean z, String str, String[] strArr, String str2, Object[] objArr, String str3, String str4, String str5, String str6) {
        Cursor cursorQuery = this.database.query(z, str, strArr, str2, encrypt(objArr), str3, str4, str5, str6);
        AccountLogger.log(TAG, "query: " + str + ", columns=" + stringify(strArr) + ", selection=" + str2 + ", selectionArgs=" + stringify(objArr) + ", groupBy=" + str3 + ", having=" + str4 + ", orderBy=" + str5 + ", limit=" + str6);
        return decrypt(cursorQuery);
    }

    public Cursor rawQuery(String str, Object[] objArr) {
        Cursor cursorRawQuery = this.database.rawQuery(str, encrypt(objArr));
        AccountLogger.log(TAG, "query: sql=" + str + ", selectionArgs=" + stringify(objArr));
        return decrypt(cursorRawQuery);
    }

    public long insert(String str, String str2, ContentValues contentValues) {
        long jInsert = this.database.insert(str, str2, encrypt(contentValues));
        AccountLogger.log(TAG, "update: " + str + ", values=" + contentValues + ", result=" + jInsert);
        return jInsert;
    }

    public int delete(String str, String str2, Object[] objArr) {
        int iDelete = this.database.delete(str, str2, encrypt(objArr));
        AccountLogger.log(TAG, "update: " + str + ", where" + str2 + ", args=" + stringify(objArr) + ", result=" + iDelete);
        return iDelete;
    }

    public int update(String str, ContentValues contentValues, String str2, Object[] objArr) {
        int iUpdate = this.database.update(str, encrypt(contentValues), str2, encrypt(objArr));
        AccountLogger.log(TAG, "update: " + str + ", values=" + contentValues + ", where" + str2 + ", args=" + stringify(objArr) + ", result=" + iUpdate);
        return iUpdate;
    }

    private static String stringify(Object[] objArr) {
        if (objArr == null) {
            return stringify((String[]) null);
        }
        if (objArr.length == 0) {
            return stringify(new String[0]);
        }
        String[] strArr = new String[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            strArr[i] = String.valueOf(objArr[i]);
        }
        return stringify(strArr);
    }

    private static String stringify(String[] strArr) {
        if (strArr == null) {
            return "null";
        }
        if (strArr.length == 0) {
            return "empty";
        }
        return "[" + String.join(",", strArr) + "]";
    }
}
