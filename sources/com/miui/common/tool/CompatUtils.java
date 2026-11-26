package com.miui.common.tool;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.content.FileProvider;
import androidx.hardware.DataSpace;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class CompatUtils {
    private static final String TAG = "CompatUtils";

    public static String invokeGetSender(Intent intent) {
        try {
            Method method = Intent.class.getMethod("getSender", new Class[0]);
            method.setAccessible(true);
            return (String) method.invoke(intent, new Object[0]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "";
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return "";
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            return "";
        }
    }

    public static StaticLayout createStaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, TextUtils.TruncateAt truncateAt, int i4) {
        try {
            Constructor constructor = StaticLayout.class.getConstructor(CharSequence.class, Integer.TYPE, Integer.TYPE, TextPaint.class, Integer.TYPE, Layout.Alignment.class, TextDirectionHeuristic.class, Float.TYPE, Float.TYPE, Boolean.TYPE, TextUtils.TruncateAt.class, Integer.TYPE, Integer.TYPE);
            constructor.setAccessible(true);
            return (StaticLayout) constructor.newInstance(charSequence, Integer.valueOf(i), Integer.valueOf(i2), textPaint, Integer.valueOf(i3), Layout.Alignment.ALIGN_NORMAL, TextDirectionHeuristics.FIRSTSTRONG_LTR, Float.valueOf(1.0f), Float.valueOf(0.0f), true, truncateAt, Integer.valueOf(i3), Integer.valueOf(i4));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static int invokeLayoutDirection(Drawable drawable) {
        try {
            Method declaredMethod = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke(drawable, new Object[0])).intValue();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return 0;
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            return 0;
        }
    }

    public static void invokeCursorColor(EditText editText, int i, int i2, int i3, int i4) {
        try {
            Field declaredField = TextView.class.getDeclaredField("mCursorDrawableRes");
            declaredField.setAccessible(true);
            int i5 = declaredField.getInt(editText);
            int identifier = NoteApp.getInstance().getResources().getIdentifier("text_select_handle_left", "drawable", "android.miui");
            int identifier2 = NoteApp.getInstance().getResources().getIdentifier("text_select_handle_right", "drawable", "android.miui");
            int identifier3 = NoteApp.getInstance().getResources().getIdentifier("text_select_handle_middle", "drawable", "android.miui");
            if (identifier == 0 || identifier2 == 0 || identifier3 == 0) {
                identifier = R.drawable.text_select_handle_left_native;
                identifier2 = R.drawable.text_select_handle_right_native;
                identifier3 = R.drawable.text_select_handle_middle_native;
            }
            Field declaredField2 = TextView.class.getDeclaredField("mEditor");
            declaredField2.setAccessible(true);
            Object obj = declaredField2.get(editText);
            Class<?> cls = obj.getClass();
            Field declaredField3 = cls.getDeclaredField("mDrawableForCursor");
            Field declaredField4 = cls.getDeclaredField("mSelectHandleLeft");
            Field declaredField5 = cls.getDeclaredField("mSelectHandleRight");
            Field declaredField6 = cls.getDeclaredField("mSelectHandleCenter");
            declaredField3.setAccessible(true);
            declaredField4.setAccessible(true);
            declaredField5.setAccessible(true);
            declaredField6.setAccessible(true);
            Resources resources = editText.getContext().getResources();
            Drawable drawable = resources.getDrawable(i5);
            drawable.setColorFilter(i, PorterDuff.Mode.SRC_IN);
            Drawable drawable2 = resources.getDrawable(i5);
            drawable2.setColorFilter(i, PorterDuff.Mode.SRC_IN);
            declaredField3.set(obj, new Drawable[]{drawable, drawable2}[0]);
            Drawable drawableMutate = resources.getDrawable(identifier2).mutate();
            drawableMutate.setColorFilter(i4, PorterDuff.Mode.SRC_IN);
            declaredField5.set(obj, drawableMutate);
            Drawable drawableMutate2 = resources.getDrawable(identifier).mutate();
            drawableMutate2.setColorFilter(i3, PorterDuff.Mode.SRC_IN);
            declaredField4.set(obj, drawableMutate2);
            Drawable drawableMutate3 = resources.getDrawable(identifier3).mutate();
            drawableMutate3.setColorFilter(i2, PorterDuff.Mode.SRC_IN);
            declaredField6.set(obj, drawableMutate3);
        } catch (IllegalAccessException e) {
            Logger.INSTANCE.d(TAG, e.getMessage());
        } catch (NoSuchFieldException e2) {
            Logger.INSTANCE.d(TAG, e2.getMessage());
        }
    }

    public static void invokeCursorColor(EditText editText, int i, int i2) {
        invokeCursorColor(editText, i, i2, i2, i2);
    }

    public static void invokeCursorController(EditText editText) {
        try {
            Field declaredField = TextView.class.getDeclaredField("mEditor");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(editText);
            Field declaredField2 = obj.getClass().getDeclaredField("mSelectionModifierCursorController");
            declaredField2.setAccessible(true);
            declaredField2.set(obj, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    public static boolean isOrientationValid(Activity activity) {
        return (activity.getResources().getConfiguration().orientation == 1) || invokeIsInMultiWindowMode(activity);
    }

    public static boolean invokeIsInMultiWindowMode(Activity activity) {
        try {
            return activity.isInMultiWindowMode();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "invokeIsInMultiWindowMode error: " + e.getMessage());
            return false;
        }
    }

    public static int obtainActivityHeight(Activity activity) {
        if (invokeIsInMultiWindowMode(activity)) {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            return rect.bottom;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) activity.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static Uri getCompatUri(Context context, File file) {
        return getFileProviderUri(context, file);
    }

    public static Uri getFileProviderUri(Context context, File file) {
        try {
            return FileProvider.getUriForFile(context, "com.miui.notes", file);
        } catch (IllegalArgumentException e) {
            Logger.INSTANCE.e("FileProvider", "file provider get uri from file:illegalArgumentException");
            e.printStackTrace();
            return null;
        } catch (Exception unused) {
            Logger.INSTANCE.e("FileProvider", "file provider get uri from file:error");
            return null;
        }
    }

    public static Uri getCompatUri(Context context, Uri uri) {
        Uri fileProviderUri = getFileProviderUri(context, new File(uri.getEncodedPath()));
        return fileProviderUri != null ? fileProviderUri : uri;
    }

    public static boolean invokeIsScreenLocked(Context context) {
        boolean zIsKeyguardSecure = ((KeyguardManager) context.getSystemService("keyguard")).isKeyguardSecure();
        if (zIsKeyguardSecure) {
            try {
                Constructor<?> constructor = Class.forName("android.security.MiuiLockPatternUtils").getConstructor(Context.class);
                constructor.setAccessible(true);
                Object objNewInstance = constructor.newInstance(context);
                Method method = objNewInstance.getClass().getMethod("getActivePasswordQuality", Integer.TYPE);
                method.setAccessible(true);
                return ((Integer) method.invoke(objNewInstance, 0)).intValue() != 0;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            } catch (NoSuchMethodException e4) {
                e4.printStackTrace();
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
            }
        }
        return zIsKeyguardSecure;
    }

    public static void sendImplicitBroadcast(Context context, Intent intent) {
        for (ResolveInfo resolveInfo : context.getPackageManager().queryBroadcastReceivers(intent, 0)) {
            intent.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            intent.addFlags(DataSpace.RANGE_LIMITED);
            context.sendBroadcast(intent);
        }
    }
}
