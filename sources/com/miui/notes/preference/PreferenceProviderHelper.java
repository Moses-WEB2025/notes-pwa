package com.miui.notes.preference;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Binder;
import android.os.Process;
import android.text.TextUtils;
import com.miui.notes.base.utils.Logger;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class PreferenceProviderHelper {
    public static final Uri CONTENT_URI = Uri.parse("content://notes/pref");
    private static final int MAX_PARAM_LENGTH = 1000;
    private static final String TAG = "PreferenceProviderHelper";
    private static final String URI_PARAM_CALLER = "caller";
    public static final String URI_PARAM_KEYS = "keys";
    public static final String URI_PARAM_KEYS_DELIMITER = ",";
    public static final String URI_PATH = "pref";
    private final Context mContext;
    private final SQLiteOpenHelper mDbHelper;

    public static String[] getKeysParam(Uri uri) {
        if (uri == null) {
            return null;
        }
        String queryParameter = uri.getQueryParameter(URI_PARAM_KEYS);
        if (TextUtils.isEmpty(queryParameter) || queryParameter.length() >= 1000) {
            return null;
        }
        return TextUtils.split(queryParameter, ",");
    }

    public static Uri setKeysParam(Uri uri, Iterable<String> iterable) {
        String strJoin = TextUtils.join(",", iterable);
        return strJoin.length() < 1000 ? uri.buildUpon().appendQueryParameter(URI_PARAM_KEYS, strJoin).build() : uri;
    }

    public static boolean isCallerMyself(Uri uri) {
        return String.valueOf(Process.myPid()).equals(uri.getQueryParameter(URI_PARAM_CALLER));
    }

    private static Uri setCallerParam(Uri uri) {
        return uri.buildUpon().appendQueryParameter(URI_PARAM_CALLER, String.valueOf(Binder.getCallingPid())).build();
    }

    public PreferenceProviderHelper(Context context, SQLiteOpenHelper sQLiteOpenHelper) {
        this.mContext = context;
        this.mDbHelper = sQLiteOpenHelper;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Logger.INSTANCE.d(TAG, "query " + uri.getHost());
        Object[] objArrAppendParamsToSelection = appendParamsToSelection(str, strArr2, uri);
        return this.mDbHelper.getReadableDatabase().query("pref", strArr, (String) objArrAppendParamsToSelection[0], (String[]) objArrAppendParamsToSelection[1], null, null, str2);
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        Logger.INSTANCE.d(TAG, "insert " + uri.getHost());
        Uri callerParam = setCallerParam(setKeysParam(CONTENT_URI, replace(contentValues)));
        this.mContext.getContentResolver().notifyChange(callerParam, (ContentObserver) null, false);
        return callerParam;
    }

    public int update(Uri uri, ContentValues contentValues) {
        Logger.INSTANCE.d(TAG, "update  " + uri.getHost());
        Set<String> setReplace = replace(contentValues);
        this.mContext.getContentResolver().notifyChange(setCallerParam(setKeysParam(CONTENT_URI, setReplace)), (ContentObserver) null, false);
        return setReplace.size();
    }

    private Set<String> replace(ContentValues contentValues) {
        Logger.INSTANCE.d(TAG, "replace  ");
        SQLiteDatabase readableDatabase = this.mDbHelper.getReadableDatabase();
        ContentValues contentValues2 = new ContentValues();
        Set<String> setKeySet = contentValues.keySet();
        HashSet hashSet = new HashSet();
        readableDatabase.beginTransaction();
        try {
            for (String str : setKeySet) {
                contentValues2.put("key", str);
                contentValues2.put("value", contentValues.getAsString(str));
                if (readableDatabase.replace("pref", null, contentValues2) >= 0) {
                    hashSet.add(str);
                } else {
                    Logger.INSTANCE.e(TAG, "Fail to update preference: " + str);
                }
            }
            readableDatabase.setTransactionSuccessful();
            return hashSet;
        } finally {
            readableDatabase.endTransaction();
        }
    }

    public int delete(Uri uri, String str, String[] strArr) {
        Logger.INSTANCE.d(TAG, "delete  " + uri.getHost());
        Object[] objArrAppendParamsToSelection = appendParamsToSelection(str, strArr, uri);
        int iDelete = this.mDbHelper.getReadableDatabase().delete("pref", (String) objArrAppendParamsToSelection[0], (String[]) objArrAppendParamsToSelection[1]);
        if (iDelete > 0) {
            this.mContext.getContentResolver().notifyChange(setCallerParam(CONTENT_URI), (ContentObserver) null, false);
        }
        return iDelete;
    }

    private Object[] appendParamsToSelection(String str, String[] strArr, Uri uri) {
        String[] keysParam = getKeysParam(uri);
        if (keysParam == null || keysParam.length == 0) {
            return new Object[]{str, strArr};
        }
        Object[] objArr = new Object[2];
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append("(").append(str).append(") AND (");
        }
        sb.append("key=?");
        for (int i = 1; i < keysParam.length; i++) {
            sb.append(" OR key=?");
        }
        if (str != null) {
            sb.append(")");
        }
        objArr[0] = sb.toString();
        if (strArr == null) {
            objArr[1] = new String[keysParam.length];
            return objArr;
        }
        String[] strArr2 = new String[strArr.length + keysParam.length];
        objArr[1] = strArr2;
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        System.arraycopy(keysParam, 0, objArr[1], strArr.length, keysParam.length);
        return objArr;
    }
}
