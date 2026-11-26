package com.xiaomi.passport.ui.internal.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.utils.BitmapUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class SysHelper {
    private static final String TAG = "SysHelper";

    public static int getEditViewInputType(boolean z) {
        return (z ? 144 : 128) | 1;
    }

    public static Bitmap createPhoto(Activity activity, String str) {
        Bitmap userAvatarByAbsPath = str != null ? BitmapUtils.getUserAvatarByAbsPath(activity, str) : null;
        if (userAvatarByAbsPath == null) {
            userAvatarByAbsPath = BitmapFactory.decodeResource(activity.getResources(), R.drawable.passport_default_avatar);
        }
        if (userAvatarByAbsPath == null) {
            return null;
        }
        Bitmap bitmapCreatePhoto = BitmapFactory.createPhoto(activity, userAvatarByAbsPath);
        userAvatarByAbsPath.recycle();
        return bitmapCreatePhoto;
    }

    public static void displaySoftInputIfNeed(Context context, View view, boolean z) {
        if (view == null) {
            AccountLogger.log(TAG, "no focused view");
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (z && context.getResources().getConfiguration().orientation == 1) {
            inputMethodManager.showSoftInput(view, 0);
        } else {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void delayDisplaySoftInputIfNeed(final Context context, final View view, final boolean z, int i) {
        if (view == null) {
            AccountLogger.log(TAG, "no focused view");
        } else {
            final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
            view.postDelayed(new Runnable() { // from class: com.xiaomi.passport.ui.internal.util.SysHelper.1
                @Override // java.lang.Runnable
                public void run() {
                    view.requestFocus();
                    if (z && context.getResources().getConfiguration().orientation == 1) {
                        inputMethodManager.showSoftInput(view, 0);
                    } else {
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }, i);
        }
    }

    public static void replaceToFragment(FragmentActivity fragmentActivity, Fragment fragment, boolean z) {
        replaceToFragment(fragmentActivity, fragment, z, false, android.R.id.content);
    }

    public static void replaceToFragment(FragmentActivity fragmentActivity, Fragment fragment, boolean z, boolean z2) {
        replaceToFragment(fragmentActivity, fragment, z, z2, android.R.id.content);
    }

    public static void replaceToFragment(FragmentActivity fragmentActivity, Fragment fragment, boolean z, boolean z2, int i) {
        if (fragmentActivity == null || fragmentActivity.isFinishing()) {
            return;
        }
        try {
            FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
            if (z2) {
                supportFragmentManager.popBackStack();
            }
            if (z) {
                int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
                for (int i2 = 0; i2 < backStackEntryCount; i2++) {
                    supportFragmentManager.popBackStack();
                }
            }
            fragmentTransactionBeginTransaction.setTransition(4099);
            fragmentTransactionBeginTransaction.replace(i, fragment);
            if (!z) {
                fragmentTransactionBeginTransaction.addToBackStack(null);
            }
            fragmentTransactionBeginTransaction.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            AccountLogger.log(TAG, "fragment", e);
        }
    }

    public static String checkPassword(Context context, String str, String str2) {
        String strCheckPasswordPattern = checkPasswordPattern(context, str);
        if (strCheckPasswordPattern != null) {
            return strCheckPasswordPattern;
        }
        if (str.equals(str2)) {
            return null;
        }
        return context.getString(R.string.password_inconsistent);
    }

    public static String checkPasswordPattern(Context context, String str) {
        if (str == null) {
            return context.getString(R.string.passport_error_empty_pwd);
        }
        int length = str.length();
        if (length < 8 || length > 16) {
            return String.format(context.getString(R.string.password_length_short_prompt), 8, 16);
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            char cCharAt = str.charAt(i4);
            if (cCharAt < ' ' || cCharAt > '~') {
                return context.getString(R.string.passport_error_illegal_pwd);
            }
            if ((cCharAt < 'a' || cCharAt > 'z') && (cCharAt < 'A' || cCharAt > 'Z')) {
                if (cCharAt < '0' || cCharAt > '9') {
                    if (i3 == 0) {
                        i3 = 1;
                    }
                } else if (i2 == 0) {
                    i2 = 1;
                }
            } else if (i == 0) {
                i = 1;
            }
        }
        if (i + i2 + i3 < 2) {
            return context.getString(R.string.password_invalid_format_prompt);
        }
        return null;
    }
}
