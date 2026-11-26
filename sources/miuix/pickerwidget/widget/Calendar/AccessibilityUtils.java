package miuix.pickerwidget.widget.Calendar;

import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.icu.util.TimeZone;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
class AccessibilityUtils {
    static final String CHINESE_DATE_FORMAT = "农历%d月%s";

    AccessibilityUtils() {
    }

    static String getYearMonthDayOfWeekDay(long j) {
        return getYearMonthWeekdayDayFormat(Locale.getDefault()).format(new Date(j));
    }

    static DateFormat getYearMonthWeekdayDayFormat(Locale locale) {
        DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton("yMMMMEEEEd", locale);
        instanceForSkeleton.setTimeZone(TimeZone.getDefault());
        instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
        return instanceForSkeleton;
    }
}
