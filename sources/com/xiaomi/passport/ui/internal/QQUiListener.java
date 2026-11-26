package com.xiaomi.passport.ui.internal;

import android.content.Context;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.xiaomi.passport.ui.utils.AccountToast;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class QQUiListener implements IUiListener {
    public final SNSAuthProvider authProvider;
    public final Context context;

    public QQUiListener(Context context, SNSAuthProvider sNSAuthProvider) {
        this.context = context;
        this.authProvider = sNSAuthProvider;
    }

    public void onComplete(Object obj) {
        if (obj instanceof JSONObject) {
            try {
                this.authProvider.storeSnsToken(this.context, ((JSONObject) obj).getString("access_token"));
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }
        this.authProvider.dispatchResult(this.context, "ok");
    }

    public void onError(UiError uiError) {
        AccountToast.showToastMessage(this.context, "onError");
        this.authProvider.dispatchResult(this.context, SNSAuthProvider.VALUE_SNS_ERROR);
    }

    public void onCancel() {
        this.authProvider.dispatchResult(this.context, SNSAuthProvider.VALUE_SNS_CANCELLED);
    }
}
