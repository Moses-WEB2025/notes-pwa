package com.miui.richeditor.share.utils;

import com.market.sdk.reflect.Field;

/* JADX INFO: loaded from: classes4.dex */
public class TextUtils {
    public static String getArb2Abc(int i) {
        if (i >= 0 && i <= 25) {
            return String.valueOf((char) (i + 97));
        }
        if (i > 25) {
            return getArb2Abc((i / 26) - 1) + getArb2Abc(i % 26);
        }
        return "-1";
    }

    public static String getArb2Roma(int i) {
        int[] iArr = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] strArr = {"M", "CM", Field.DOUBLE_SIGNATURE_PRIMITIVE, "CD", Field.CHAR_SIGNATURE_PRIMITIVE, "XC", "L", "XL", "X", "IX", "V", "IV", Field.INT_SIGNATURE_PRIMITIVE};
        if (i < 1 || i > 3999) {
            return "-1";
        }
        String str = "";
        for (int i2 = 0; i2 < 13; i2++) {
            while (i >= iArr[i2]) {
                str = str + strArr[i2];
                i -= iArr[i2];
            }
        }
        return str;
    }

    public static int getColorWithAlpha(float f, int i) {
        return (Math.min(255, Math.max(0, (int) (f * 255.0f))) << 24) + (i & 16777215);
    }
}
