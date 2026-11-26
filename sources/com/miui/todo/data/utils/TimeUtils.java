package com.miui.todo.data.utils;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.utils.CalenderUtils;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class TimeUtils {
    private TimeUtils() {
    }

    public static long getTodayStartTimeMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 0);
        return calendar.getTimeInMillis();
    }

    public static int compareDay(long j, long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j2);
        if (calendar.get(1) > calendar2.get(1)) {
            return 1;
        }
        if (calendar.get(1) < calendar2.get(1)) {
            return -1;
        }
        if (calendar.get(2) > calendar2.get(2)) {
            return 1;
        }
        if (calendar.get(2) < calendar2.get(2)) {
            return -1;
        }
        return calendar.get(5) - calendar2.get(5);
    }

    public static long getDateEndMillis(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 0);
        return (calendar.getTimeInMillis() + TimeUnit.DAYS.toMillis(1L)) - 1;
    }

    public static long getTodayEndMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 0);
        return (calendar.getTimeInMillis() + TimeUnit.DAYS.toMillis(1L)) - 1;
    }

    public static long getTomorrowEndMillis() {
        return (getTodayEndMillis() + TimeUnit.DAYS.toMillis(1L)) - 1;
    }

    public static long getTodayRemindMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 8);
        return calendar.getTimeInMillis();
    }

    public static long getTomorrowRemindMillis() {
        return getTodayRemindMillis() + TimeUnit.DAYS.toMillis(1L);
    }

    public static long getDateRemindMillis(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 8);
        return calendar.getTimeInMillis();
    }

    public static long getTomorrowRemindMillis(long j) {
        return getTodayRemindMillis(j) + TimeUnit.DAYS.toMillis(1L);
    }

    public static long getTodayRemindMillis(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        int i = calendar.get(14);
        int i2 = calendar.get(13);
        int i3 = calendar.get(12);
        int i4 = calendar.get(11);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(14, i);
        calendar2.set(13, i2);
        calendar2.set(12, i3);
        calendar2.set(11, i4);
        return calendar2.getTimeInMillis();
    }

    public static long getNextRemindTime(int i, long j, long j2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();
        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTimeInMillis(System.currentTimeMillis());
        calendar2.setTimeInMillis(j2);
        calendar3.setTimeInMillis(j);
        Logger.INSTANCE.d("RepeatRemindSetting", "首次提醒日期： " + simpleDateFormat.format(calendar2));
        Logger.INSTANCE.d("RepeatRemindSetting", "上次提醒日期： " + simpleDateFormat.format(calendar3));
        calendar4.set(21, calendar3.get(21));
        if (calendar3.after(calendar)) {
            calendar4.setTimeInMillis(calendar3.getTimeInMillis());
            if (i == 1) {
                calendar4.add(5, 1);
                Logger.INSTANCE.d("RepeatRemindSetting", "每日提醒 上次提醒时间比现在晚： " + simpleDateFormat.format(calendar4));
            } else if (i == 2) {
                calendar4.add(5, 7);
                Logger.INSTANCE.d("RepeatRemindSetting", "每周提醒 上次提醒时间比现在晚： " + simpleDateFormat.format(calendar4));
            } else if (i == 3) {
                setNextWorkDay(calendar4);
                Logger.INSTANCE.d("RepeatRemindSetting", "周一至周五提醒 上次提醒时间比现在晚： " + simpleDateFormat.format(calendar4));
            } else if (i == 7) {
                calendar4.add(2, 1);
                calendar4.set(5, getNextDayOfMonth(calendar2.getActualMaximum(5) - calendar2.get(5), calendar2, calendar4));
                Logger.INSTANCE.d("RepeatRemindSetting", "每月提醒 上次提醒时间比现在晚： " + simpleDateFormat.format(calendar4));
            } else if (i == 8) {
                calendar4.add(1, 1);
                setNextDayOfNextYear(calendar2, calendar4);
                Logger.INSTANCE.d("RepeatRemindSetting", "每年提醒 上次提醒时间比现在晚： " + simpleDateFormat.format(calendar4));
            }
        } else if (i != 1) {
            if (i == 2) {
                calendar4.set(7, calendar3.get(7));
                if (!calendar4.after(calendar)) {
                    calendar4.add(5, 7);
                    Logger.INSTANCE.d("RepeatRemindSetting", "每周提醒 下次提醒时间比现在早，调整后为： " + simpleDateFormat.format(calendar4));
                } else {
                    Logger.INSTANCE.d("RepeatRemindSetting", "每周提醒 下次提醒时间比现在晚： " + simpleDateFormat.format(calendar4));
                }
            } else if (i == 3) {
                if (CalenderUtils.isSunOrSat(calendar4)) {
                    setNextWorkDay(calendar4);
                }
                if (!calendar4.after(calendar)) {
                    setNextWorkDay(calendar4);
                    Logger.INSTANCE.d("RepeatRemindSetting", "周一至周五提醒 下次提醒时间比现在早，调整后为： " + simpleDateFormat.format(calendar4));
                } else {
                    Logger.INSTANCE.d("RepeatRemindSetting", "周一至周五提醒 下次提醒时间比现在晚： " + simpleDateFormat.format(calendar4));
                }
            } else if (i == 7) {
                int actualMaximum = calendar2.getActualMaximum(5) - calendar2.get(5);
                calendar4.set(5, getNextDayOfMonth(actualMaximum, calendar2, calendar4));
                boolean zAfter = calendar4.after(calendar);
                if (!zAfter) {
                    calendar4.add(2, 1);
                    calendar4.set(5, getNextDayOfMonth(actualMaximum, calendar2, calendar4));
                }
                if (!zAfter) {
                    Logger.INSTANCE.d("RepeatRemindSetting", "每月提醒 下次提醒时间比现在早，调整后为： " + simpleDateFormat.format(calendar4));
                } else {
                    Logger.INSTANCE.d("RepeatRemindSetting", "每月提醒 下次提醒时间比现在晚： " + simpleDateFormat.format(calendar4));
                }
            } else if (i == 8) {
                setNextDayOfNextYear(calendar2, calendar4);
                if (!calendar4.after(calendar)) {
                    calendar4.add(1, 1);
                    setNextDayOfNextYear(calendar2, calendar4);
                    Logger.INSTANCE.d("RepeatRemindSetting", "每年提醒 下次提醒时间比现在早，调整后为： " + simpleDateFormat.format(calendar4));
                } else {
                    Logger.INSTANCE.d("RepeatRemindSetting", "每年提醒 下次提醒时间比现在晚： " + simpleDateFormat.format(calendar4));
                }
            }
        } else if (!calendar4.after(calendar)) {
            calendar4.add(5, 1);
            Logger.INSTANCE.d("RepeatRemindSetting", "每日提醒 下次提醒时间比现在早，调整后为： " + simpleDateFormat.format(calendar4));
        } else {
            Logger.INSTANCE.d("RepeatRemindSetting", "每日提醒 下次提醒时间比现在晚： " + simpleDateFormat.format(calendar4));
        }
        return calendar4.getTimeInMillis();
    }

    public static int getNextDayOfMonth(int i, Calendar calendar, Calendar calendar2) {
        calendar.get(2);
        int i2 = calendar2.get(2);
        if (calendar.getActualMaximum(5) == 31 && i == 1 && i2 == 1) {
            return calendar2.getActualMaximum(5) - 1;
        }
        return Math.min(calendar.get(5), calendar2.getActualMaximum(5));
    }

    public static void setNextDayOfNextYear(Calendar calendar, Calendar calendar2) {
        calendar2.set(2, calendar.get(2));
        calendar2.set(5, Math.min(calendar.get(5), calendar2.getActualMaximum(5)));
    }

    public static void setNextWorkDay(Calendar calendar) {
        calendar.add(5, 1);
        int i = calendar.get(7);
        if (i == 1) {
            calendar.add(5, 1);
        } else if (i == 7) {
            calendar.add(5, 2);
        }
    }
}
