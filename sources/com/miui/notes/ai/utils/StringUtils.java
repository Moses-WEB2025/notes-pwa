package com.miui.notes.ai.utils;

import android.content.Context;
import java.lang.reflect.Field;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class StringUtils {
    public static String obtainLocalInteger(int i, int i2, Context context) {
        return String.format(Locale.getDefault(), context.getResources().getQuantityText(i2, i).toString(), Integer.valueOf(i));
    }

    public static int getResId(String str, Class<?> cls) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            return declaredField.getInt(declaredField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
