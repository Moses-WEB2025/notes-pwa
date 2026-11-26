package com.xiaomi.passport.ui.license;

import android.content.Context;
import android.os.AsyncTask;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public class PrivacyAgreeTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "PrivacyAgreeTask";
    private final Context mAppCtx;
    private final ArrayList<LoginAgreementAndPrivacy.PrivacyReportInfo> mPrivacyReportInfoList;

    public PrivacyAgreeTask(Context context, ArrayList<LoginAgreementAndPrivacy.PrivacyReportInfo> arrayList) {
        this.mAppCtx = context.getApplicationContext();
        this.mPrivacyReportInfoList = arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Void doInBackground(Void... voidArr) {
        Iterator<LoginAgreementAndPrivacy.PrivacyReportInfo> it = this.mPrivacyReportInfoList.iterator();
        while (it.hasNext()) {
            reportPrivacyAgree(it.next());
        }
        return null;
    }

    private void reportPrivacyAgree(LoginAgreementAndPrivacy.PrivacyReportInfo privacyReportInfo) {
        try {
            PrivacyPolicyProtocol.agree(this.mAppCtx, privacyReportInfo);
            AccountLogger.log(TAG, "suc when agree:policyName=" + privacyReportInfo.policyName);
        } catch (PrivacyPolicyProtocol.OperationCallFrequentException e) {
            AccountLogger.log(TAG, "fail because too frequent: policyName=" + privacyReportInfo.policyName + "\u3000\u3000err msg:" + e);
        } catch (PrivacyPolicyProtocol.OperationFailedException e2) {
            AccountLogger.log(TAG, "fail when agree: policyName=" + privacyReportInfo.policyName + "\u3000\u3000err msg:" + e2);
        }
    }
}
