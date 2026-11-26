package com.xiaomi.accountsdk.utils;

import java.lang.ref.SoftReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang3.time.TimeZones;

/* JADX INFO: loaded from: classes4.dex */
public final class DateUtils {
    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
    public static final TimeZone GMT;
    public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    private static final String[] DEFAULT_PATTERNS = {PATTERN_RFC1036, PATTERN_RFC1123, PATTERN_ASCTIME};

    static {
        TimeZone timeZone = TimeZone.getTimeZone(TimeZones.GMT_ID);
        GMT = timeZone;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        calendar.set(2000, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
    }

    public static Date parseDate(String str) throws ParseException {
        return parseDate(str, null, null);
    }

    public static Date parseDate(String str, String[] strArr) throws ParseException {
        return parseDate(str, strArr, null);
    }

    public static Date parseDate(String str, String[] strArr, Date date) throws ParseException {
        if (str == null) {
            throw new IllegalArgumentException("dateValue is null");
        }
        if (strArr == null) {
            strArr = DEFAULT_PATTERNS;
        }
        if (date == null) {
            date = DEFAULT_TWO_DIGIT_YEAR_START;
        }
        if (str.length() > 1 && str.startsWith("'") && str.endsWith("'")) {
            str = str.substring(1, str.length() - 1);
        }
        for (String str2 : strArr) {
            SimpleDateFormat simpleDateFormat = DateFormatHolder.formatFor(str2);
            simpleDateFormat.set2DigitYearStart(date);
            try {
                return simpleDateFormat.parse(str);
            } catch (ParseException unused) {
            }
        }
        throw new ParseException("Unable to parse the date " + str, 0);
    }

    public static String formatDate(Date date) {
        return formatDate(date, PATTERN_RFC1123);
    }

    public static String formatDate(Date date, String str) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("pattern is null");
        }
        return DateFormatHolder.formatFor(str).format(date);
    }

    private DateUtils() {
    }

    static final class DateFormatHolder {
        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>() { // from class: com.xiaomi.accountsdk.utils.DateUtils.DateFormatHolder.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // java.lang.ThreadLocal
            public SoftReference<Map<String, SimpleDateFormat>> initialValue() {
                return new SoftReference<>(new HashMap());
            }
        };

        DateFormatHolder() {
        }

        public static SimpleDateFormat formatFor(String str) {
            ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> threadLocal = THREADLOCAL_FORMATS;
            Map<String, SimpleDateFormat> map = threadLocal.get().get();
            if (map == null) {
                map = new HashMap<>();
                threadLocal.set(new SoftReference<>(map));
            }
            SimpleDateFormat simpleDateFormat = map.get(str);
            if (simpleDateFormat != null) {
                return simpleDateFormat;
            }
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str, Locale.US);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone(TimeZones.GMT_ID));
            map.put(str, simpleDateFormat2);
            return simpleDateFormat2;
        }
    }
}
