package com.xiaomi.xms.ai.recorder.util;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaomi.xms.ai.recorder.Constants;

/* JADX INFO: loaded from: classes4.dex */
public class DatabaseHelper {
    private static final String JSON_KEY_RESULT_CONTENT = "resultContent";
    private static final String JSON_KEY_THEME = "theme";
    private static final String TABLE_INDEX_RECOGNIZE_SENTENCE = "content";
    private static final String TABLE_INDEX_SUMMARY_RESULT = "summary_result";
    public static final String TAG = "DatabaseHelper";

    /* JADX WARN: Code duplicated, block: B:15:0x007e  */
    /* JADX WARN: Code duplicated, block: B:26:0x00b0 A[PHI: r2 r9 r12
  0x00b0: PHI (r2v2 java.lang.String) = (r2v5 java.lang.String), (r2v17 java.lang.String) binds: [B:34:0x00d5, B:25:0x00ae] A[DONT_GENERATE, DONT_INLINE]
  0x00b0: PHI (r9v3 java.lang.String) = (r9v14 java.lang.String), (r9v7 java.lang.String) binds: [B:34:0x00d5, B:25:0x00ae] A[DONT_GENERATE, DONT_INLINE]
  0x00b0: PHI (r12v2 android.database.Cursor) = (r12v3 android.database.Cursor), (r12v6 android.database.Cursor) binds: [B:34:0x00d5, B:25:0x00ae] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:43:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:58:? A[SYNTHETIC] */
    public static String querySummaryOrRecognizeData(String str, Context context) throws Throwable {
        Throwable th;
        Exception exc;
        String string;
        Cursor cursorQuery;
        Cursor cursor = null;
        string = null;
        string = null;
        string = null;
        string = null;
        string = null;
        String string2 = null;
        try {
            cursorQuery = context.getContentResolver().query(Constants.SUMMARY_CONTENT_URI, null, "sha1 = ?", new String[]{str}, null);
            if (cursorQuery != null) {
                try {
                    try {
                        Log.d(TAG, "querySummaryTable count: " + cursorQuery.getCount());
                        if (cursorQuery.getCount() > 0) {
                            string = null;
                            while (cursorQuery.moveToNext()) {
                                try {
                                    string = cursorQuery.getString(cursorQuery.getColumnIndex(TABLE_INDEX_SUMMARY_RESULT));
                                    JsonObject asJsonObject = JsonParser.parseString(string).getAsJsonObject();
                                    Log.v(TAG, "querySummaryTable summary: " + string);
                                    if (asJsonObject.has(JSON_KEY_RESULT_CONTENT)) {
                                        string = asJsonObject.get(JSON_KEY_RESULT_CONTENT).getAsString();
                                    }
                                } catch (Exception e) {
                                    exc = e;
                                    Log.e(TAG, "querySummaryOrRecognizeData error :" + exc);
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                }
                            }
                        } else {
                            string = null;
                        }
                        if (TextUtils.isEmpty(string) && (cursorQuery = context.getContentResolver().query(Constants.RECOG_CONTENT_URI, null, "sha1 = ?", new String[]{str}, "_id ASC")) != null && cursorQuery.getCount() > 0 && cursorQuery.moveToFirst()) {
                            string2 = cursorQuery.getString(cursorQuery.getColumnIndex("content"));
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = cursorQuery;
                        if (cursor != null) {
                            throw th;
                        }
                        cursor.close();
                        throw th;
                    }
                } catch (Exception e2) {
                    exc = e2;
                    string = null;
                    Log.e(TAG, "querySummaryOrRecognizeData error :" + exc);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                }
            } else {
                string = null;
                if (TextUtils.isEmpty(string)) {
                    string2 = cursorQuery.getString(cursorQuery.getColumnIndex("content"));
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        } catch (Exception e3) {
            exc = e3;
            string = null;
            cursorQuery = null;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                throw th;
            }
            cursor.close();
            throw th;
        }
        return string != null ? string : string2;
    }

    public static String querySummary(String str, Context context) throws Throwable {
        Throwable th;
        Exception exc;
        String str2;
        Cursor cursor = null;
        string = null;
        string = null;
        String string = null;
        cursor = null;
        try {
            try {
                Cursor cursorQuery = context.getContentResolver().query(Constants.SUMMARY_CONTENT_URI, null, "sha1 = ?", new String[]{str}, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.getCount() > 0) {
                            Log.d(TAG, "querySummaryTable count: " + cursorQuery.getCount());
                            if (cursorQuery.moveToNext()) {
                                string = cursorQuery.getString(cursorQuery.getColumnIndex(TABLE_INDEX_SUMMARY_RESULT));
                                JsonObject asJsonObject = JsonParser.parseString(string).getAsJsonObject();
                                Log.v(TAG, "querySummaryTable summary: " + string);
                                if (asJsonObject.has("theme")) {
                                    string = asJsonObject.get("theme").getAsString();
                                }
                            }
                        }
                    } catch (Exception e) {
                        exc = e;
                        cursor = cursorQuery;
                        str2 = null;
                        Log.e(TAG, "querySummary error :" + exc);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return str2;
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = cursorQuery;
                        if (cursor == null) {
                            throw th;
                        }
                        cursor.close();
                        throw th;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return string;
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e2) {
            exc = e2;
            str2 = null;
        }
    }

    public static String queryRecognizeData(String str, Context context) throws Throwable {
        Throwable th;
        Exception exc;
        String str2;
        Cursor cursor = null;
        string = null;
        string = null;
        string = null;
        String string = null;
        cursor = null;
        try {
            try {
                Cursor cursorQuery = context.getContentResolver().query(Constants.RECOG_CONTENT_URI, null, "sha1 = ?", new String[]{str}, "_id ASC");
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.getCount() > 0) {
                            Log.d(TAG, "queryRecognizeData count: " + cursorQuery.getCount());
                            if (cursorQuery.moveToFirst()) {
                                string = cursorQuery.getString(cursorQuery.getColumnIndex("content"));
                                Log.v(TAG, "queryRecognizeData sentence: " + string);
                            }
                        }
                    } catch (Exception e) {
                        exc = e;
                        String str3 = string;
                        cursor = cursorQuery;
                        str2 = str3;
                        Log.e(TAG, "queryRecognizeData error :" + exc);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return str2;
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = cursorQuery;
                        if (cursor == null) {
                            throw th;
                        }
                        cursor.close();
                        throw th;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return string;
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e2) {
            exc = e2;
            str2 = null;
        }
    }
}
