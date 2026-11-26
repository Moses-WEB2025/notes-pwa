package com.miui.ai.text.widget.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.ai.utils.PreferenceUtils;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes2.dex */
public class AIcrDownloadHelper {
    private static final Uri AICR_URI = Uri.parse("content://com.xiaomi.aicr.provider.OpenCapabilityProvider");
    private static final int CAPABILITY_VALUE = 9000;
    public static final String DOWNLOAD_ACTION = "com.xiaomi.aicr.download.ACTION";
    private static final String KEY_CAPABILITY = "Capability";
    private static final String KEY_COT_CONFIG = "cotConfig";
    private static final String KEY_MODEL_ABLE = "modelAble";
    private static final String KEY_MODEL_NAME = "model";
    private static final String KEY_NOT_TURN = "notification_turn";
    private static final String KEY_PKG_NAME = "package";
    private static final String KEY_RECEIVER_NAME = "receiver";
    private static final String MODEL_CHECK_AND_UPDATE = "check_and_update";
    private static final String MODEL_CHECK_COT_CONFIG = "check_cotConfig";
    private static final String MODEL_CHECK_STATUS = "get_model_status";
    private static final String MODEL_NAME_VALUE = "llmtask";
    private static final String TAG = "AIcrDownloadHelper";

    public static boolean getModelStatus(Context context) {
        Bundle bundle = new Bundle();
        bundle.putString("model", MODEL_NAME_VALUE);
        bundle.putInt("Capability", 9000);
        try {
            return context.getContentResolver().call(AICR_URI, MODEL_CHECK_STATUS, context.getPackageName(), bundle).getBoolean("modelAble");
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "getModelStatus error, e:" + e);
            return false;
        }
    }

    public static boolean getCotConfig(Context context) {
        Bundle bundleCall;
        Bundle bundle = new Bundle();
        bundle.putInt("Capability", 9000);
        try {
            bundleCall = context.getContentResolver().call(AICR_URI, MODEL_CHECK_COT_CONFIG, context.getPackageName(), bundle);
        } catch (IllegalArgumentException e) {
            Logger.INSTANCE.e(TAG, "illeagalArgrument !!" + e.getMessage());
            bundleCall = null;
        }
        if (bundleCall != null) {
            return context.getContentResolver().call(AICR_URI, MODEL_CHECK_COT_CONFIG, context.getPackageName(), bundle).getBoolean(KEY_COT_CONFIG);
        }
        Logger.INSTANCE.e(TAG, "result is null");
        return false;
    }

    public static void checkAndDownload(Context context, String str, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putInt("Capability", 9000);
        bundle.putString("model", MODEL_NAME_VALUE);
        bundle.putBoolean(KEY_NOT_TURN, z);
        bundle.putString("package", str);
        bundle.putString(KEY_RECEIVER_NAME, "com.miui.ai.text.widget.download.AIcrDownloadHelper$AIcrDownloadReceiver");
        context.getContentResolver().call(AICR_URI, MODEL_CHECK_AND_UPDATE, context.getPackageName(), bundle);
    }

    public static class AIcrDownloadReceiver extends BroadcastReceiver {
        public static final String DOWNLOAD_ACTION = "com.xiaomi.aicr.download.ACTION";
        private static final String MODEL_NAME_VALUE = "llmtask";

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            boolean booleanExtra;
            if (intent == null || intent.getAction() == null || !intent.getAction().equals("com.xiaomi.aicr.download.ACTION.llmtask") || !(booleanExtra = intent.getBooleanExtra("model_name", false))) {
                return;
            }
            PreferenceUtils.setModelSwitch(context, booleanExtra);
            AITextWidgetStat.reportAiLocalSettingClick("true", context);
        }
    }
}
