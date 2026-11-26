package com.miui.todo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.timepicker.TimeModel;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import miuix.core.util.SystemProperties;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TodoUtils {
    public static final int EDIT_TEXT_MAX_LENGTH = 2000;
    private static final String TAG = "TodoUtils";
    private static final AtomicBoolean sIsMenuSwiping = new AtomicBoolean(false);

    public static void setSwiping(boolean z, String str) {
        Logger.INSTANCE.i(TAG, "setSwiping: " + str + " isSwiping: " + z);
        sIsMenuSwiping.set(z);
    }

    public static boolean isMenuSwiping() {
        return sIsMenuSwiping.get();
    }

    public static String getString(int i) {
        return NoteApp.getInstance().getResources().getString(i);
    }

    public static String getQuantityString(int i, int i2, Object... objArr) {
        return NoteApp.getInstance().getResources().getQuantityString(i, i2, objArr);
    }

    public static Drawable getDrawable(int i) {
        return NoteApp.getInstance().getResources().getDrawable(i, null);
    }

    public static int getColor(int i) {
        return NoteApp.getInstance().getResources().getColor(i, null);
    }

    public static float getDimen(int i) {
        return NoteApp.getInstance().getResources().getDimension(i);
    }

    public static Configuration getConfiguration() {
        return NoteApp.getInstance().getResources().getConfiguration();
    }

    public static void showSoftInput(View view) {
        InputMethodManager inputMethodManager;
        Logger.INSTANCE.i(TAG, "showSoftInput");
        if (view == null || view.getContext() == null || (inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.showSoftInput(view, 0);
    }

    public static void hideSoftInput(Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = activity.getWindow().getDecorView();
        }
        hideSoftInput(currentFocus);
    }

    public static void hideSoftInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String getAllItemTerms(int i) {
        return NoteApp.getInstance().getResources().getQuantityString(R.plurals.todo_terms, i);
    }

    public static int getRealHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getRealWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean invokeIsInMultiWindowMode(Activity activity) {
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("isInMultiWindowMode", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(activity, new Object[0])).booleanValue();
        } catch (IllegalAccessException e) {
            Logger.INSTANCE.d(TAG, e.getMessage());
            return false;
        } catch (NoSuchMethodException e2) {
            Logger.INSTANCE.d(TAG, e2.getMessage());
            return false;
        } catch (InvocationTargetException e3) {
            Logger.INSTANCE.d(TAG, e3.getMessage());
            return false;
        }
    }

    public static String obtainLocalInteger(int i, int i2) {
        return obtainLocalNumber(NoteApp.getInstance().getResources().getQuantityText(i2, i).toString(), i);
    }

    public static String obtainLocalNumber(String str, int i) {
        return String.format(Locale.getDefault(), str, Integer.valueOf(i));
    }

    public static String obtainLocalInteger(int i) {
        return obtainLocalNumber(TimeModel.NUMBER_FORMAT, i);
    }

    public static boolean isInThisCountry(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return getRegion().equals(str);
    }

    public static String getRegion() {
        String str = SystemProperties.get("ro.miui.region");
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        String str2 = SystemProperties.get("ro.product.locale.region");
        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        String str3 = SystemProperties.get("persist.sys.country");
        return TextUtils.isEmpty(str3) ? Locale.getDefault().getCountry() : str3;
    }

    public static String getGroupName(int i) {
        StringBuilder sb = new StringBuilder("");
        if (i == 0) {
            sb.append(getString(R.string.todo_item_group_expired));
        } else if (i == 1) {
            sb.append(getString(R.string.todo_item_group_today)).append(StringUtils.SPACE).append(CalenderUtils.getStringByTimeStamp(System.currentTimeMillis(), getString(R.string.todo_schedule_time_md)));
        } else if (i == 2) {
            sb.append(getString(R.string.todo_item_group_tomorrow)).append(StringUtils.SPACE).append(CalenderUtils.getStringByTimeStamp(System.currentTimeMillis() + 86400000, getString(R.string.todo_schedule_time_md)));
        } else if (i == 3) {
            sb.append(getString(R.string.todo_item_group_further));
        } else if (i == 4) {
            sb.append(getString(R.string.todo_item_group_unschedule));
        } else if (i == 5) {
            sb.append(getString(R.string.todo_item_group_finished));
        }
        return sb.toString();
    }

    public static String getGroupNameInCustMode(int i) {
        StringBuilder sb = new StringBuilder("");
        if (i == 1) {
            sb.append(getString(R.string.todo_item_group_finished));
        }
        return sb.toString();
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }
}
