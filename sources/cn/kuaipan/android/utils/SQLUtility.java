package cn.kuaipan.android.utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class SQLUtility {
    private static final String AND = " AND ";
    private static final String CMD_AND = "(%s) AND (%s)";
    private static final String CMD_CREATE_INDEX = "CREATE INDEX IF NOT EXISTS %s ON %s (%s);";
    private static final String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (%s);";
    private static final String CMD_DROP_INDEX = "DROP INDEX IF EXISTS %s";
    private static final String CMD_DROP_TABLE = "DROP TABLE IF EXISTS %s";
    private static final String CMD_OR = "(%s) OR (%s)";
    private static final String CMD_REINDEX = "REINDEX %s";
    private static final String CMD_SELECT = "%s=?";
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    private static final String[] CONFLICT_VALUES = {"", "ROLLBACK", "ABORT", "FAIL", "IGNORE", "REPLACE"};
    private static final String LOG_TAG = "SQLUtility";
    private static final String OR = " OR ";
    public static final String SORT_ASC = " ASC";
    public static final String SORT_ASC_LOCALIZED = " COLLATE LOCALIZED ASC";
    public static final String SORT_DESC = " DESC";
    public static final String SORT_DESC_LOCALIZED = " COLLATE LOCALIZED DESC";
    public static final String WHERE_BETWEEN = "%s BETWEEN ? AND ?";
    public static final String WHERE_EQUSE = "%s=?";
    public static final String WHERE_GREATER = "%s>?";
    public static final String WHERE_GREATER_OR_EQUSE = "%s>=?";
    public static final String WHERE_IN = "%s IN ( %s )";
    public static final String WHERE_LESS = "%s<?";
    public static final String WHERE_LESS_OR_EQUSE = "%s<=?";
    public static final String WHERE_LIKE = "%s LIKE '%s'";
    public static final String WHERE_STR_IN = "( %s >= '%s' ) AND ( %s < '%s' )";
    public static final String WHERE_UNEQUSE = "%s<>?";

    private SQLUtility() {
    }

    public static void createTable(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        sQLiteDatabase.execSQL(String.format(CMD_CREATE_TABLE, str, str2));
    }

    public static void dropTable(SQLiteDatabase sQLiteDatabase, String str) {
        sQLiteDatabase.execSQL(String.format(CMD_DROP_TABLE, str));
    }

    public static void createIndex(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        sQLiteDatabase.execSQL(String.format(CMD_CREATE_INDEX, str2, str, str3));
    }

    public static void reIndex(SQLiteDatabase sQLiteDatabase, String str) {
        sQLiteDatabase.execSQL(String.format(CMD_REINDEX, str));
    }

    public static void dropIndex(SQLiteDatabase sQLiteDatabase, String str) {
        sQLiteDatabase.execSQL(String.format(CMD_DROP_INDEX, str));
    }

    public static String getSelection(String str) {
        return String.format("%s=?", str);
    }

    public static String getSelectionWithTemplete(String str, String... strArr) {
        return String.format(str, strArr);
    }

    public static String getSelectionAnd(String... strArr) {
        return getSelection(AND, strArr);
    }

    public static String getSelectionOr(String... strArr) {
        return getSelection(OR, strArr);
    }

    private static String getSelection(String str, String... strArr) {
        if (strArr == null || strArr.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s=?", strArr[0]));
        int length = strArr.length;
        for (int i = 1; i < length; i++) {
            sb.append(str);
            sb.append(String.format("%s=?", strArr[i]));
        }
        return sb.toString();
    }

    public static String or(String str, String str2) {
        return fun(CMD_OR, str, str2);
    }

    public static String and(String str, String str2) {
        return fun(CMD_AND, str, str2);
    }

    private static String fun(String str, String str2, String str3) {
        if (str2 == null || str3 == null) {
            return str2 != null ? str2 : str3;
        }
        return String.format(str, str2, str3);
    }

    public static String[] mergeSelectionArg(String[]... strArr) {
        int length = 0;
        for (String[] strArr2 : strArr) {
            if (strArr2 != null) {
                length += strArr2.length;
            }
        }
        String[] strArr3 = new String[length];
        int length2 = 0;
        for (String[] strArr4 : strArr) {
            if (strArr4 != null) {
                System.arraycopy(strArr4, 0, strArr3, length2, strArr4.length);
                length2 += strArr4.length;
            }
        }
        return strArr3;
    }

    public static boolean copyStringValue(ContentValues contentValues, ContentValues contentValues2, String str) {
        String asString = contentValues.getAsString(str);
        if (asString != null) {
            contentValues2.put(str, asString);
        }
        return asString != null;
    }

    public static boolean copyIntValue(ContentValues contentValues, ContentValues contentValues2, String str) {
        Integer asInteger = contentValues.getAsInteger(str);
        if (asInteger != null) {
            contentValues2.put(str, asInteger);
        }
        return asInteger != null;
    }

    public static boolean copyLongValue(ContentValues contentValues, ContentValues contentValues2, String str) {
        Long asLong = contentValues.getAsLong(str);
        if (asLong != null) {
            contentValues2.put(str, asLong);
        }
        return asLong != null;
    }

    public static boolean copyBooleanValue(ContentValues contentValues, ContentValues contentValues2, String str) {
        Boolean asBoolean = contentValues.getAsBoolean(str);
        if (asBoolean != null) {
            contentValues2.put(str, asBoolean);
        }
        return asBoolean != null;
    }

    public static long insertWithOnConflict(SQLiteDatabase sQLiteDatabase, String str, String str2, ContentValues contentValues, int i) {
        try {
            return ((Long) JavaCalls.callMethodOrThrow(sQLiteDatabase, "insertWithOnConflict", str, str2, contentValues, Integer.valueOf(i))).longValue();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Current SDK Version is " + Build.VERSION.SDK_INT);
            Log.e(LOG_TAG, "Failed to call insertWithOnConflict", e);
            return -1L;
        }
    }
}
