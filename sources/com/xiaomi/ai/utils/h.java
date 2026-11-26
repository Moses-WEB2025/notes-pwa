package com.xiaomi.ai.utils;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

/* JADX INFO: loaded from: classes4.dex */
public class h {
    public static CharSequence a(String str) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new StyleSpan(1), 0, str.length(), 17);
        return spannableString;
    }

    public static CharSequence a(String str, int i) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(i), 0, str.length(), 17);
        return spannableString;
    }
}
