package com.xiaomi.passport.ui.license;

import android.content.Context;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class AgreementAndPrivacyHelper {
    private static final String AGREEMENT_URL = "https://account.xiaomi.com/about/protocol/agreement?_locale=%s";
    public static final String PRIVACY_NAME = "miaccount";
    private static final String PRIVACY_URL = "https://privacy.mi.com/%s/%s/";

    public static String getUserAgreementUrl() {
        return String.format(AGREEMENT_URL, XMPassportUtil.getISOLocaleString(Locale.getDefault()));
    }

    public static String getUserPrivacyUrl() {
        return String.format(PRIVACY_URL, PRIVACY_NAME, XMPassportUtil.getISOLocaleString(Locale.getDefault()));
    }

    public static void privacyAgree(Context context, ArrayList<LoginAgreementAndPrivacy.PrivacyReportInfo> arrayList) {
        new PrivacyAgreeTask(context, arrayList).execute(new Void[0]);
    }
}
