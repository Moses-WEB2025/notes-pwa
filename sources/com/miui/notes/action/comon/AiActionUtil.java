package com.miui.notes.action.comon;

import android.net.Uri;
import android.os.Bundle;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class AiActionUtil {
    public static final int CTA_FAIL = -103;
    public static final int FAIL = -1;
    public static final int FAIL_TWO = -2;
    private static final String KEY_ACTION_CALLBACK_URI = "action_callback_uri";
    private static final String KEY_ACTION_REQUEST_ID = "action_request_id";
    private static final String KEY_CLIENT_REQUEST_ID = "client_request_id";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TARGET_CODE = "target_code";
    private static final String KEY_TARGET_OUT = "target_out";
    private static final String KEY_TARGET_RESPONSE_ID = "target_response_id";
    private static final String METHOD_SUBMIT_RESULT = "action_result";
    private static final String PACKAGE_NAME_CALL = "com.miui.notes";
    private static final String PERMISSION_READ_AIACTION = "com.xiaomi.aicr";
    public static final int SUCCESS = 0;
    public static final String TAG = "AiActionUtil";
    private static Bundle mActionBundleExtra;
    private static int mCount;
    private static int mStatus;

    public static void setExtra(Bundle bundle) {
        mCount = 0;
        mStatus = 0;
        mActionBundleExtra = bundle;
    }

    public static void setReturnCount(int i) {
        mCount = i;
    }

    public static synchronized void asyncNotifyResult(int i) {
        if (mActionBundleExtra == null) {
            Logger.INSTANCE.d(TAG, "asyncNotifyResult: actionBundleExtra is null.");
            return;
        }
        Logger.INSTANCE.d(TAG, "asyncNotifyResult: mCount = " + mCount);
        int i2 = mCount;
        if (i2 > 0) {
            if (i == -1) {
                mStatus = i;
            }
            mCount = i2 - 1;
            return;
        }
        int i3 = mStatus;
        if (i3 == -1) {
            i = i3;
        }
        String string = mActionBundleExtra.getString(KEY_ACTION_REQUEST_ID);
        String string2 = mActionBundleExtra.getString(KEY_ACTION_CALLBACK_URI);
        Logger.INSTANCE.i(TAG, "requestId: " + string);
        Logger.INSTANCE.i(TAG, "status: " + i);
        if (string2 == null) {
            Logger.INSTANCE.e(TAG, "callbackUriString is null ");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", i);
        } catch (JSONException e) {
            Logger.INSTANCE.e(TAG, "put status: " + e);
        }
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TARGET_CODE, i);
        bundle.putString(KEY_TARGET_RESPONSE_ID, string);
        bundle.putString(KEY_CLIENT_REQUEST_ID, string);
        bundle.putString(KEY_TARGET_OUT, jSONObject.toString());
        NoteApp.getInstance().getContentResolver().call(Uri.parse(string2), METHOD_SUBMIT_RESULT, (String) null, bundle);
        mActionBundleExtra = null;
        mCount = 0;
        mStatus = 0;
    }
}
