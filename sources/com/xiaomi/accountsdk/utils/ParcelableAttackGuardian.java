package com.xiaomi.accountsdk.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.BadParcelableException;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;

/* JADX INFO: loaded from: classes4.dex */
public class ParcelableAttackGuardian {
    private static final String TAG = "ParcelableAttackGuardia";

    public boolean safeCheck(Activity activity) {
        if (activity != null && activity.getIntent() != null) {
            try {
                unparcelIntent(new Intent(activity.getIntent()));
                return true;
            } catch (RuntimeException e) {
                if (e instanceof BadParcelableException) {
                    AccountLogger.log(TAG, "fail checking ParcelableAttack for Activity " + activity.getClass().getName());
                    return false;
                }
                if (e.getCause() instanceof ClassNotFoundException) {
                    AccountLogger.log(TAG, "fail checking SerializableAttack for Activity " + activity.getClass().getName());
                    return false;
                }
                AccountLogger.log(TAG, SNSAuthProvider.VALUE_SNS_ERROR, e);
            }
        }
        return true;
    }

    void unparcelIntent(Intent intent) throws BadParcelableException {
        intent.getStringExtra("");
    }
}
