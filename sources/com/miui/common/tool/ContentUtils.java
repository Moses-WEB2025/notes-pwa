package com.miui.common.tool;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import ch.qos.logback.classic.spi.CallerData;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes2.dex */
public class ContentUtils {
    public static String escapeExprSpecialWord(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] strArr = {"\\", "$", "(", ")", "*", Marker.ANY_NON_NULL_MARKER, ".", "[", "]", CallerData.NA, "^", "{", StringSubstitutor.DEFAULT_VAR_END, "|"};
            for (int i = 0; i < 14; i++) {
                String str2 = strArr[i];
                if (str.contains(str2)) {
                    str = str.replace(str2, "\\" + str2);
                }
            }
        }
        return str;
    }

    public static SpannableStringBuilder changeHighLight(CharSequence charSequence, String str) {
        SpannableStringBuilder spannableStringBuilder;
        if (charSequence != null) {
            spannableStringBuilder = new SpannableStringBuilder(charSequence);
        } else {
            spannableStringBuilder = new SpannableStringBuilder();
        }
        if (!TextUtils.isEmpty(str)) {
            try {
                Matcher matcher = Pattern.compile(escapeExprSpecialWord(str.toLowerCase())).matcher(spannableStringBuilder.toString().toLowerCase());
                while (matcher.find()) {
                    Logger.INSTANCE.w("ContentUtils", "m.find()");
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(NoteApp.getInstance().getColor(R.color.user_query_highlight)), matcher.start(), matcher.end(), 33);
                }
            } catch (Exception e) {
                Logger.INSTANCE.w("ContentUtils", "error in changeHighLight： " + e.toString());
            }
        }
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder changeArrHighLight(CharSequence charSequence, ArrayList<String> arrayList) {
        SpannableStringBuilder spannableStringBuilder;
        if (charSequence != null) {
            spannableStringBuilder = new SpannableStringBuilder(charSequence);
        } else {
            spannableStringBuilder = new SpannableStringBuilder();
        }
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (!TextUtils.isEmpty(arrayList.get(i))) {
                    try {
                        Matcher matcher = Pattern.compile(escapeExprSpecialWord(arrayList.get(i).toLowerCase())).matcher(spannableStringBuilder.toString().toLowerCase());
                        while (matcher.find()) {
                            Logger.INSTANCE.w("ContentUtils", "m.find()");
                            spannableStringBuilder.setSpan(new ForegroundColorSpan(NoteApp.getInstance().getColor(R.color.user_query_highlight)), matcher.start(), matcher.end(), 33);
                        }
                    } catch (Exception e) {
                        Logger.INSTANCE.w("ContentUtils", "error in changeHighLight： " + e.toString());
                        return spannableStringBuilder;
                    }
                }
            }
        }
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder changePcHighLight(CharSequence charSequence, String str) {
        SpannableStringBuilder spannableStringBuilder;
        if (charSequence != null) {
            spannableStringBuilder = new SpannableStringBuilder(charSequence);
        } else {
            spannableStringBuilder = new SpannableStringBuilder();
        }
        if (!TextUtils.isEmpty(str)) {
            try {
                Matcher matcher = Pattern.compile(escapeExprSpecialWord(str.toLowerCase())).matcher(spannableStringBuilder.toString().toLowerCase());
                while (matcher.find()) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(NoteApp.getInstance().getColor(R.color.pc_user_query_highlight)), matcher.start(), matcher.end(), 33);
                }
            } catch (Exception e) {
                Logger.INSTANCE.w("ContentUtils", "error in changeHighLight： " + e.toString());
            }
        }
        return spannableStringBuilder;
    }
}
