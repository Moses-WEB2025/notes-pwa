package com.xiaomi.micloudsdk.request.utils;

/* JADX INFO: loaded from: classes4.dex */
public class RomCountryUtil {
    private RomCountryUtil() {
    }

    public static String getRomCountry() {
        try {
            return (String) Class.forName("miui.os.Build").getDeclaredMethod("getRegion", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
