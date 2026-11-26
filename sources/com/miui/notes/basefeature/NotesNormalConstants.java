package com.miui.notes.basefeature;

import android.net.Uri;

/* JADX INFO: loaded from: classes4.dex */
public interface NotesNormalConstants {
    public static final String ACCESS_NOTE_ABOVE_OS1 = "com.hyperos.permission.READ_NOTE";
    public static final String AICR_PACKAGE = "com.xiaomi.aicr";
    public static final String AICR_SEARCH_ACTIVITY = "com.xiaomi.aicr.aisearch.progress.AISearchProgressActivity";
    public static final String AICR_SEARCH_DB_PROGRESS = "com.xiaomi.aicr.SEARCH_DB_PROGRESS";
    public static final String AUTHORITY = "records";
    public static final int COMPLETE_BUILD_DATABASE = 0;
    public static final String GLOBAL_DELETE_VIEW = "com.miui.home.settings.action.DELETED_SYSTEM_APPS_SETTINGS";
    public static final String GLOBAL_UPDATE_CLASS = "com.xiaomi.market.ui.UpdateAppsActivity";
    public static final String GLOBAL_UPDATE_PACKAGE = "com.xiaomi.discover";
    public static final int HANDWRITE_NOTE = 10;
    public static final int IN_BUILD_DATABASE = 1;
    public static final String MI_MARKET_CN = "mimarket://details?id=com.android.soundrecorder&detailStyle=3";
    public static final int OTHER_NOTE = 11;
    public static final String RECOGNIZE_TABLE_NAME = "ai_recognize_queue";
    public static final int STOP_BUILD_DATABASE = 2;
    public static final String SUMMARY_TABLE_NAME = "summary";
    public static final Uri SUMMARY_CONTENT_URI = Uri.parse("content://records/summary");
    public static final Uri RECOGNIZE_CONTENT_URI = Uri.parse("content://records/ai_recognize_queue");
}
