package com.xiaomi.xms.ai.recorder;

import android.net.Uri;

/* JADX INFO: loaded from: classes4.dex */
public class Constants {
    public static final String AUTHORITY_AI_RECORD_PROVIDER = "com.android.soundrecorder.airecordprovider";
    public static final String AUTHORITY_CTA = "com.android.soundrecorder.cloudservices";
    public static final String IS_ACCEPT = "is_accept";
    public static final String KEY_AI_CAPABILITY = "is_support_ai_capability";
    public static final String KEY_AI_RECORDER_PRESTATE = "ai_recoder_prestate";
    public static final String KEY_CTA_PERMISSION_STATE = "cta_permission_state";
    public static final String KEY_KOREA_PERMISSION_STATE = "korea_permission_state";
    public static final String RECORDER_PACKAGE_NAME = "com.android.soundrecorder";
    public static final String TAG = "AIRecorderServiceSdk";
    public static final Uri SUMMARY_CONTENT_URI = Uri.parse("content://records/summary");
    public static final Uri RECOG_CONTENT_URI = Uri.parse("content://records/recognition_sentence");
}
