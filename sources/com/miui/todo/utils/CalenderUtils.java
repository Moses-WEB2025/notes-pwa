package com.miui.todo.utils;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.text.format.DateFormat;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import defpackage.R2;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import miuix.pickerwidget.date.DateUtils;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CalenderUtils {
    public static final String TIMEZONE_UTC = "UTC";

    public static String getScheduleTimeString(int i, long j) {
        return getScheduleTimeString(i, j, 0);
    }

    public static String getScheduleTimeString(int i, long j, int i2) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j);
        StringBuilder sb = new StringBuilder("");
        boolean z = calendar.getTimeInMillis() > j;
        if (i == 0) {
            return sb.toString();
        }
        int julianDay = getJulianDay(calendar2) - getJulianDay(calendar);
        if (julianDay == 0) {
            if (isSystem24HourFormat()) {
                sb.append(TodoUtils.getString(R.string.todo_schedule_time_today));
            }
        } else if (julianDay == 1) {
            sb.append(TodoUtils.getString(R.string.todo_schedule_time_tomorrow));
        } else if (julianDay == -1) {
            sb.append(TodoUtils.getString(R.string.todo_schedule_time_yesterday));
        } else if (!isInThisYear(calendar2)) {
            sb.append(getStringByTimeStamp(j, TodoUtils.getString(R.string.todo_schedule_time_ymdhm)));
        } else {
            sb.append(getStringByTimeStamp(j, TodoUtils.getString(R.string.todo_schedule_time_md)));
        }
        if (i == 2 || i == 1) {
            if (isInThisYear(calendar2)) {
                if (julianDay <= 1 || julianDay >= -1) {
                    sb.append("");
                }
                if (isSystem24HourFormat()) {
                    sb.append(getStringByTimeStamp(j, TodoUtils.getString(R.string.todo_schedule_time_hm)));
                } else {
                    sb.append(getStringByTimeStamp(j, TodoUtils.getString(R.string.todo_schedule_time_hm_12)));
                }
            } else if (julianDay == 1 || julianDay == -1) {
                sb.append(StringUtils.SPACE);
                if (isSystem24HourFormat()) {
                    sb.append(getStringByTimeStamp(j, TodoUtils.getString(R.string.todo_schedule_time_hm)));
                } else {
                    sb.append(getStringByTimeStamp(j, TodoUtils.getString(R.string.todo_schedule_time_hm_12)));
                }
            }
        }
        if (i2 == 1) {
            sb.append(TodoUtils.getString(R.string.todo_schedule_time_repeat_day));
        } else if (i2 == 2) {
            sb.append(TodoUtils.getString(R.string.todo_schedule_time_repeat_week));
        } else if (i2 == 3) {
            sb.append(TodoUtils.getString(R.string.todo_schedule_time_repeat_normal_workday));
        } else if (i2 == 7) {
            sb.append(TodoUtils.getString(R.string.todo_schedule_time_repeat_month));
        } else if (i2 == 8) {
            sb.append(TodoUtils.getString(R.string.todo_schedule_time_repeat_year));
        }
        if (z) {
            sb.append(StringUtils.SPACE);
            sb.append(TodoUtils.getString(R.string.todo_item_group_expired));
        }
        return sb.toString();
    }

    public static boolean isTimeExpired(long j) {
        return Calendar.getInstance().getTimeInMillis() > j;
    }

    public static String getRemindTimeStringForDialog(long j) {
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        if (Locale.getDefault().getLanguage().equals(Locale.CHINESE.getLanguage())) {
            String str = new SimpleDateFormat(TodoUtils.getString(R.string.todo_schedule_time_for_picker_hm), Locale.getDefault()).format(calendar.getTime());
            sb.append(DateUtils.formatDateTime(NoteApp.getInstance(), calendar.getTimeInMillis(), !isInThisYear(calendar) ? R2.color.miuix_color_red_light_primary_pressed : R2.color.miuix_appcompat_list_secondary_text_color_normal_dark).replace(StringUtils.SPACE, "")).append(StringUtils.SPACE).append(DateUtils.formatDateTime(NoteApp.getInstance(), calendar.getTimeInMillis(), R2.dimen.note_edit_checkbox_margin_end));
            sb.append(StringUtils.SPACE).append(str);
        } else {
            sb.append(DateUtils.formatDateTime(NoteApp.getInstance(), calendar.getTimeInMillis(), !isInThisYear(calendar) ? R2.id.tracking_progress_up : R2.id.parentPanel));
        }
        return sb.toString();
    }

    public static boolean isTomorrow(long j) {
        return getDurationFromToday(j) == 1;
    }

    public static int getDurationFromToday(long j) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j);
        return getJulianDay(calendar2) - getJulianDay(calendar);
    }

    public static boolean isInThisYear(Calendar calendar) {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        return calendar2.get(1) == calendar.get(1);
    }

    public static long createDayRemindTimeByTimeStamp(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 8, 0, 0);
        return calendar.getTimeInMillis();
    }

    public static long createDayRemindTimeFromToday(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 8, 0, 0);
        if (i != 0) {
            calendar.add(5, i);
        }
        return calendar.getTimeInMillis();
    }

    public static long[] createDayStartAndExpireTime(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(j);
        calendar.set(10, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return new long[]{calendar.getTimeInMillis(), calendar.getTimeInMillis() + 86399000};
    }

    public static String getStringByTimeStampWithYear(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return new SimpleDateFormat(TodoUtils.getString(R.string.todo_custom_remind_tag_pattern_with_ymd), Locale.getDefault()).format(calendar.getTime());
    }

    public static String getStringByTimeStamp(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return new SimpleDateFormat(TodoUtils.getString(isInThisYear(calendar) ? R.string.todo_custom_remind_tag_pattern_with_md : R.string.todo_custom_remind_tag_pattern_with_ymd), Locale.getDefault()).format(calendar.getTime());
    }

    public static String getStringByTimeStamp(long j, String str) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return new SimpleDateFormat(str, Locale.getDefault()).format(calendar.getTime());
    }

    public static int[] getDateFromMillis(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return new int[]{calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13)};
    }

    public static long[] getTodayTimeMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 0);
        long timeInMillis = calendar.getTimeInMillis();
        return new long[]{timeInMillis, (TimeUnit.DAYS.toMillis(1L) + timeInMillis) - 1};
    }

    public static boolean isToday(long j) {
        long[] todayTimeMillis = getTodayTimeMillis();
        return j >= todayTimeMillis[0] && j <= todayTimeMillis[1];
    }

    public static boolean isToday(Calendar calendar) {
        return isToday(calendar.getTimeInMillis());
    }

    public static boolean isThisMonth(int i) {
        return i == Calendar.getInstance().get(2);
    }

    public static int compareMonth(Calendar calendar, Calendar calendar2) {
        if (calendar.get(1) > calendar2.get(1)) {
            return 1;
        }
        if (calendar.get(1) < calendar2.get(1)) {
            return -1;
        }
        return calendar.get(2) - calendar2.get(2);
    }

    public static int calMonthOffset(Calendar calendar, Calendar calendar2) {
        return (((calendar2.get(1) - calendar.get(1)) * 12) + calendar2.get(2)) - calendar.get(2);
    }

    public static String getDateFormatStr(Context context, long j, int i) {
        return new SimpleDateFormat(context.getString(i)).format(Long.valueOf(j));
    }

    public static int getJulianDay(Calendar calendar) {
        int i = calendar.get(1);
        int i2 = calendar.get(2) + 1;
        int i3 = calendar.get(5);
        int i4 = (14 - i2) / 12;
        int i5 = (i + R2.color.miuix_color_deeporange_solid_10) - i4;
        return (((((i3 + (((((i2 + (i4 * 12)) - 3) * R2.array.ai_panel_widget_blend_colors) + 2) / 5)) + (i5 * R2.attr.actionTextColorAlpha)) + (i5 / 4)) - (i5 / 100)) + (i5 / 400)) - 32045;
    }

    public static int getJulianDay(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return getJulianDay(calendar);
    }

    public static Calendar getCalendarFromJulianDay(int i, TimeZone timeZone) {
        if (i >= 588829) {
            int i2 = (int) ((((double) (i - 1867216)) - 0.25d) / 36524.25d);
            i = ((i + 1) + i2) - (i2 / 4);
        }
        int i3 = i + R2.attr.listViewGroupHeaderBackground;
        int i4 = (int) (((((double) (i - 2438346)) - 122.1d) / 365.25d) + 6680.0d);
        int i5 = i3 - ((i4 * R2.attr.actionTextColorAlpha) + (i4 / 4));
        int i6 = (int) (((double) i5) / 30.6001d);
        int i7 = i5 - ((int) (((double) i6) * 30.6001d));
        int i8 = i6 - 1;
        if (i8 > 12) {
            i8 = i6 - 13;
        }
        int i9 = i4 - 4715;
        if (i8 > 2) {
            i9 = i4 - 4716;
        }
        if (i9 <= 0) {
            i9--;
        }
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(i9, i8 - 1, i7);
        return calendar;
    }

    public static String getDiffDaysString(Context context, Calendar calendar) {
        int julianDay = getJulianDay(calendar) - getJulianDay(Calendar.getInstance());
        if (julianDay == 0) {
            return context.getString(R.string.todo_schedule_time_today);
        }
        if (julianDay > 0) {
            return context.getResources().getQuantityString(R.plurals.todo_N_days_after, julianDay, Integer.valueOf(julianDay));
        }
        int i = -julianDay;
        return context.getResources().getQuantityString(R.plurals.todo_N_days_ago, i, Integer.valueOf(i));
    }

    public static boolean isSameDay(long j, long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j2);
        return isSameDay(calendar, calendar2);
    }

    public static boolean isSameWeekEndDay(Calendar calendar, Calendar calendar2) {
        return calendar != null && calendar2 != null && calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6) + 6;
    }

    public static boolean isSameDay(Calendar calendar, Calendar calendar2) {
        return calendar != null && calendar2 != null && calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6);
    }

    public static String getDateDesc(Context context, long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return getDateDesc(context, getJulianDay(calendar));
    }

    public static boolean isSameMonth(Calendar calendar, Calendar calendar2) {
        return calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2);
    }

    public static String getDateDesc(Context context, int i) {
        int julianDay = getJulianDay(Calendar.getInstance());
        if (i == julianDay) {
            return context.getString(R.string.todo_event_description_today);
        }
        if (i == julianDay + 1) {
            return context.getString(R.string.todo_event_description_tomorrow);
        }
        if (i == julianDay - 1) {
            return context.getString(R.string.todo_event_description_yesterday);
        }
        if (i == julianDay + 2) {
            return context.getString(R.string.todo_event_description_day_after_tomorrow);
        }
        if (i == julianDay - 2) {
            return context.getString(R.string.todo_event_description_day_before_yesterday);
        }
        if (i == julianDay + 7) {
            return context.getString(R.string.todo_event_description_a_week_later);
        }
        if (i == julianDay - 7) {
            return context.getString(R.string.todo_event_description_a_week_ago);
        }
        if (i > julianDay) {
            int i2 = i - julianDay;
            return context.getResources().getQuantityString(R.plurals.todo_event_description_days_later, i2, Integer.valueOf(i2));
        }
        if (i < julianDay) {
            int i3 = julianDay - i;
            return context.getResources().getQuantityString(R.plurals.todo_event_description_days_ago, i3, Integer.valueOf(i3));
        }
        return "";
    }

    public static String getTimeText(Context context, int i) {
        return getTimeText(context, i / 60, i % 60);
    }

    public static String getTimeText(Context context, int i, boolean z) {
        return getTimeText(context, i / 60, i % 60, z);
    }

    public static String getTimeText(Context context, int i, int i2) {
        return getTimeText(context, i, i2, DateFormat.is24HourFormat(context));
    }

    public static String getTimeText(Context context, int i, int i2, boolean z) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, i);
        calendar.set(12, i2);
        return DateUtils.formatDateTime(context, calendar.getTimeInMillis(), (z ? 32 : 16) | 15);
    }

    public static Calendar getDateBeforeOrAfter(long j, int i, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        if (i == 0) {
            calendar.add(5, i2);
            return calendar;
        }
        calendar.add(5, -i2);
        return calendar;
    }

    public static int getWorkDaysBetweenTwoDates(Context context, long j, long j2) {
        Calendar.getInstance().setTimeInMillis(j);
        Calendar.getInstance().setTimeInMillis(j2);
        return 0;
    }

    public static String getIntervalDays(Context context, Calendar calendar, Calendar calendar2) {
        int julianDay = getJulianDay(calendar2) - getJulianDay(calendar);
        if (julianDay == 0) {
            return context.getString(R.string.date_calculate_today);
        }
        if (julianDay > 0) {
            return context.getResources().getQuantityString(R.plurals.date_calculate_days_after, julianDay, Integer.valueOf(julianDay));
        }
        int i = -julianDay;
        return context.getResources().getQuantityString(R.plurals.date_calculate_days_ago, i, Integer.valueOf(i));
    }

    public static String convertToStandardTime(String str) {
        return (str == null || !str.matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) ? str : str.substring(0, str.lastIndexOf(":"));
    }

    public static boolean isSunOrSat(Calendar calendar) {
        int i = calendar.get(7);
        return i == 1 || i == 7;
    }

    public static boolean isSystem24HourFormat() {
        return DateFormat.is24HourFormat(NoteApp.getInstance());
    }

    public static boolean isCurrentTimeAM() {
        return Calendar.getInstance().get(9) == 0;
    }
}
