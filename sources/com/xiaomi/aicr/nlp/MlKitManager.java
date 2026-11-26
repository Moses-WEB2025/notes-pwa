package com.xiaomi.aicr.nlp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
public class MlKitManager {
    private static final String AUTHORITY = "com.miui.privacycomputing.provider.mlkitprovider";
    private static final String KEY_NER_REPLY = "key_ner_reply";
    private static final String KEY_NER_SEND = "key_ner_send";
    private static final String KEY_SERVICE_VERSION = "key_service_version";
    private static final String KEY_STATUS_CODE = "key_status_code";
    private static final String METHOD_GET_NER_RESULT = "getNerResult";
    public static final String PRIVACY_COMPUTING_SDK_VERSION = "0.0.2";
    private static final String URI_MLKIT = "content://com.miui.privacycomputing.provider.mlkitprovider";
    private static volatile MlKitManager sInstance;
    private final Context mContext;

    private MlKitManager(Context context) {
        this.mContext = context;
    }

    public static MlKitManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (MlKitManager.class) {
                if (sInstance == null) {
                    sInstance = new MlKitManager(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public NerResult getExactNerResult(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("ner source data should not be null or empty");
        }
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NER_SEND, str);
        Bundle bundleCall = this.mContext.getApplicationContext().getContentResolver().call(Uri.parse(URI_MLKIT), METHOD_GET_NER_RESULT, PRIVACY_COMPUTING_SDK_VERSION, bundle);
        return new NerResult(bundleCall.getInt(KEY_NER_REPLY), bundleCall.getInt(KEY_STATUS_CODE, -1), PRIVACY_COMPUTING_SDK_VERSION, bundleCall.getInt(KEY_SERVICE_VERSION));
    }

    public boolean isSupportNer() {
        return this.mContext.getPackageManager().resolveContentProvider(AUTHORITY, 0) != null;
    }
}
