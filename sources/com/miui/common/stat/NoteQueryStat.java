package com.miui.common.stat;

import android.text.TextUtils;
import android.util.Log;
import com.miui.notes.NoteApp;
import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class NoteQueryStat extends BaseStat {
    protected static final String APK = "apk";
    protected static final String CONTENT_TEXT_COUNT = "content_text_count";
    protected static final String DURATION = "duration";
    protected static final String ELEMENT_NAME = "element_name";
    public static final String ELEMENT_NAME_EXIT = "退出搜索";
    protected static final String IS_AI = "is_ai";
    protected static final String LOCATION = "location";
    public static int RESULT_COUNT = 0;
    protected static final String SEARCH_QUERY_TEXT = "search_query_text";
    public static final String SEARCH_RESULT = "729.33.0.1.38432";
    public static final String SEARCH_RESULT_NEGATIVE_CLICK = "729.33.0.1.39612";
    public static final String SEARCH_RESULT_POSITIVE_CLICK = "729.33.0.1.38434";
    protected static final String SEARCH_RESULT_TEXT = "search_result_text";

    public static void reportNoteSearchResultEvent(int i, String str, int i2, boolean z) {
        if (TextUtils.isEmpty(str) || i2 <= 0) {
            return;
        }
        HashMap map = new HashMap();
        map.put("apk", NoteApp.getInstance().getPackageName());
        map.put("content_text_count", Integer.valueOf(i));
        map.put(SEARCH_QUERY_TEXT, str);
        map.put("duration", Integer.valueOf(i2));
        map.put(IS_AI, z ? "true" : "false");
        reportSearchResultEvent(SEARCH_RESULT, map);
    }

    public static void reportNoteSearchResultPositiveClickEvent(String str, String str2, int i, String str3) {
        HashMap map = new HashMap();
        map.put("apk", NoteApp.getInstance().getPackageName());
        map.put("content_text_count", str);
        map.put(SEARCH_QUERY_TEXT, str2);
        map.put(LOCATION, Integer.valueOf(i));
        map.put(IS_AI, str3);
        reportClickEvent(SEARCH_RESULT_POSITIVE_CLICK, map);
        Log.d("NoteAppStat", "reportNoteSearchResultPositiveClickEvent: ");
    }

    public static void reportNoteSearchResultNegativeClickEvent(String str, String str2, int i, String str3, String str4) {
        HashMap map = new HashMap();
        map.put("apk", NoteApp.getInstance().getPackageName());
        map.put("content_text_count", str);
        map.put(LOCATION, Integer.valueOf(i));
        map.put(ELEMENT_NAME, str3);
        map.put(IS_AI, str4);
        reportClickEvent(SEARCH_RESULT_NEGATIVE_CLICK, map);
    }
}
