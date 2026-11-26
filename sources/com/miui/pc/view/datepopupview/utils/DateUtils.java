package com.miui.pc.view.datepopupview.utils;

import com.miui.pc.view.datepopupview.bean.DateEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/* JADX INFO: loaded from: classes4.dex */
public class DateUtils {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

    public static void main(String[] strArr) {
        getMonthDate("2020-09");
    }

    public static ArrayList<DateEntity> getMonthDate(String str) {
        ArrayList<DateEntity> arrayList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(monthFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(5, 1);
        int i = calendar.get(7) - 1;
        if (i > 0) {
            calendar.add(5, -i);
        }
        for (int i2 = 1; i2 <= 42; i2++) {
            DateEntity dateEntity = new DateEntity();
            dateEntity.mMonth = getValue(calendar.get(2) + 1);
            dateEntity.mDate = getValue(calendar.get(1)) + "-" + dateEntity.mMonth + "-" + getValue(calendar.get(5));
            dateEntity.mWeekNum = calendar.get(7);
            dateEntity.mIsToday = isToday(dateEntity.mDate);
            dateEntity.mDay = getValue(calendar.get(5));
            arrayList.add(dateEntity);
            calendar.add(5, 1);
        }
        return arrayList;
    }

    public static String getValue(int i) {
        return String.valueOf(i > 9 ? Integer.valueOf(i) : "0" + i);
    }

    public static boolean isToday(String str) {
        Date date;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        return date != null && dateFormat.format(new Date()).equals(dateFormat.format(date));
    }

    public static boolean isCurrentMonth(String str) {
        Date date;
        try {
            date = monthFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        return date != null && monthFormat.format(new Date()).equals(monthFormat.format(date));
    }

    public static String getCurrDate(String str) {
        return new SimpleDateFormat(str).format(new Date(System.currentTimeMillis()));
    }

    public static String getFirstMonthDay(String str, int i) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(monthFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(2, calendar.get(2) + i);
        return dateFormat.format(calendar.getTime());
    }

    public static String getRealMonth(String str) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(monthFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getValue(calendar.get(2) + 1);
    }

    public static String getRealYearAndMonth(String str) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(monthFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getValue(calendar.get(1)) + "年" + getValue(calendar.get(2) + 1) + "月";
    }

    public static String getRealYearAndMonthAndDay(String str) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getValue(calendar.get(1)) + "年" + getValue(calendar.get(2) + 1) + "月" + getValue(calendar.get(5)) + "日";
    }

    public static int compareDate(String str, String str2) {
        try {
            return dateFormat.parse(str).compareTo(dateFormat.parse(str2));
        } catch (ParseException e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }
}
