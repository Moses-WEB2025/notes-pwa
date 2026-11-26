package com.xiaomi.accountsdk.account.utils;

import android.text.TextUtils;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class XMPassportUtil {
    public static String getISOLocaleString(Locale locale) {
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return TextUtils.isEmpty(country) ? language : String.format("%s_%s", language, country);
    }
}
