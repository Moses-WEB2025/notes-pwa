package com.xiaomi.passport.ui.license;

import android.content.Context;
import com.miui.privacypolicy.PrivacyManager;

/* JADX INFO: loaded from: classes4.dex */
public class PrivacyPolicyProtocol {
    private PrivacyPolicyProtocol() {
    }

    public static class OperationFailedException extends Exception {
        public OperationFailedException(int i, String str) {
            super("errCode: " + i + ", errMsg: " + str);
        }
    }

    public static class OperationCallFrequentException extends Exception {
        public OperationCallFrequentException() {
            super("request called too frequent. ");
        }
    }

    public static void agree(Context context, LoginAgreementAndPrivacy.PrivacyReportInfo privacyReportInfo) throws OperationFailedException, OperationCallFrequentException {
        ensureOperationSuccess(PrivacyManager.privacyAgree(context, privacyReportInfo.policyName, privacyReportInfo.idContent, privacyReportInfo.packageName, privacyReportInfo.apkVersionName));
    }

    private static void ensureOperationSuccess(int i) throws OperationFailedException, OperationCallFrequentException {
        if (1 == i || -7 == i || -6 == i) {
            return;
        }
        throwOperationException(i);
    }

    private static void throwOperationException(int i) throws OperationFailedException, OperationCallFrequentException {
        if (i == -5) {
            throw new OperationCallFrequentException();
        }
        if (i == -4) {
            throw new OperationFailedException(i, "ERROR_INTERNATIONAL_REGION");
        }
        if (i == -3) {
            throw new OperationFailedException(i, "ERROR_PARESE_SERVICE_DATA");
        }
        if (i == -2) {
            throw new OperationFailedException(i, "ERROR_SERVICE_NOT_RESPONSE");
        }
        if (i == -1) {
            throw new OperationFailedException(i, "ERROR_NO_NETWORK");
        }
        throw new OperationFailedException(i, "UNKNOWN_FAILED_REASON");
    }
}
