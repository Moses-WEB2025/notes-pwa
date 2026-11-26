package androidx.appsearch.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class DateTimeFormatValidator {
    private DateTimeFormatValidator() {
    }

    public static boolean validateISO8601Date(String str) {
        return validateDateFormat("yyyy-MM-dd", str);
    }

    public static boolean validateISO8601DateTime(String str) {
        return validateDateFormat("yyyy-MM-dd'T'HH:mm", str) || validateDateFormat("yyyy-MM-dd'T'HH:mm:ss", str);
    }

    public static boolean validateDateFormat(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.US);
        simpleDateFormat.setLenient(false);
        try {
            Date date = simpleDateFormat.parse(str2);
            return date != null && str2.equals(simpleDateFormat.format(date));
        } catch (ParseException unused) {
            return false;
        }
    }
}
