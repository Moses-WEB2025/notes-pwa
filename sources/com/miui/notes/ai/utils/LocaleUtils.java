package com.miui.notes.ai.utils;

import android.content.res.Resources;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class LocaleUtils {
    public static boolean isChineseLocale() {
        Locale locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        return locale != null && "zh".equals(locale.getLanguage());
    }
}
