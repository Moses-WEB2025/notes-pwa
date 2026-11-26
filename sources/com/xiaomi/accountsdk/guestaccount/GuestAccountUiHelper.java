package com.xiaomi.accountsdk.guestaccount;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.RemoteException;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountResult;
import java.lang.ref.WeakReference;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class GuestAccountUiHelper {
    StringResourceInjector stringResourceInjector;
    private static final StringResourceInjector defaultStringResourceSimplifiedChinese = new StringResourceInjector() { // from class: com.xiaomi.accountsdk.guestaccount.GuestAccountUiHelper.1
        @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountUiHelper.StringResourceInjector
        public CharSequence getErrorDialogTitle(int i) {
            return "获取游客账号信息出错";
        }

        @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountUiHelper.StringResourceInjector
        public CharSequence getErrorDialogMessage(int i, String str) {
            StringBuilder sb = new StringBuilder("获取游客账号信息出错:");
            if (i > 100) {
                sb.append("错误码=");
                sb.append(i);
            } else if (i == 6) {
                sb.append("网络错误");
            } else if (i == 5) {
                sb.append("服务器错误");
            } else if (i == 2) {
                sb.append("APP权限错误");
            } else if (i == 4) {
                sb.append("未同意“我的小米”联网");
            } else if (i == 7) {
                sb.append("本地参数错误：" + str);
            } else {
                sb.append("未错误(错误码=");
                sb.append(i);
                sb.append(")");
            }
            return sb.toString();
        }
    };
    private static final StringResourceInjector defaultStringResourceEnglish = new StringResourceInjector() { // from class: com.xiaomi.accountsdk.guestaccount.GuestAccountUiHelper.2
        @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountUiHelper.StringResourceInjector
        public CharSequence getErrorDialogTitle(int i) {
            return "Guest Account Error";
        }

        @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountUiHelper.StringResourceInjector
        public CharSequence getErrorDialogMessage(int i, String str) {
            StringBuilder sb = new StringBuilder("Guest Account Error: ");
            if (i > 100) {
                sb.append("code=");
                sb.append(i);
            } else if (i == 6) {
                sb.append("Network Error");
            } else if (i == 5) {
                sb.append("Server Error");
            } else if (i == 2) {
                sb.append("APP Permission Error");
            } else if (i == 4) {
                sb.append("Please allow XiaomiAccount app for internet");
            } else if (i == 7) {
                sb.append("Local parameter error:" + str);
            } else {
                sb.append("System Error(code=");
                sb.append(i);
                sb.append(")");
            }
            return sb.toString();
        }
    };

    public interface StringResourceInjector {
        CharSequence getErrorDialogMessage(int i, String str);

        CharSequence getErrorDialogTitle(int i);
    }

    static class Holder {
        static GuestAccountUiHelper sInstance = new GuestAccountUiHelper();

        Holder() {
        }
    }

    final void injectStringResources(StringResourceInjector stringResourceInjector) {
        if (stringResourceInjector == null) {
            throw new IllegalArgumentException("stringResourceInjector == null");
        }
        this.stringResourceInjector = stringResourceInjector;
    }

    final void showErrorDialog(Activity activity, GuestAccountResult guestAccountResult) {
        if (guestAccountResult == null) {
            return;
        }
        Intent intent = guestAccountResult.getIntent();
        if (intent != null) {
            activity.startActivity(intent);
            return;
        }
        int errorCode = guestAccountResult.getErrorCode();
        String errorMessage = guestAccountResult.getErrorMessage();
        if (errorCode == 0) {
            return;
        }
        StringResourceInjector nonNullStringResourceInjector = getNonNullStringResourceInjector();
        new AlertDialog.Builder(activity).setTitle(nonNullStringResourceInjector.getErrorDialogTitle(errorCode)).setMessage(nonNullStringResourceInjector.getErrorDialogMessage(errorCode, errorMessage)).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
    }

    final StringResourceInjector getNonNullStringResourceInjector() {
        StringResourceInjector stringResourceInjector = this.stringResourceInjector;
        if (stringResourceInjector != null) {
            return stringResourceInjector;
        }
        if (Locale.getDefault().equals(Locale.SIMPLIFIED_CHINESE)) {
            return defaultStringResourceSimplifiedChinese;
        }
        return defaultStringResourceEnglish;
    }

    static final class GuestAccountIntentHandlerImpl extends IGuestAccountIntentHandler.Stub {
        private final WeakReference<Activity> activityWeakReference;

        public GuestAccountIntentHandlerImpl(Context context) {
            this.activityWeakReference = new WeakReference<>(context instanceof Activity ? (Activity) context : null);
        }

        @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountIntentHandler
        public boolean handleIntent(Intent intent) throws RemoteException {
            Activity activity = this.activityWeakReference.get();
            if (intent == null || activity == null || activity.isFinishing()) {
                return false;
            }
            activity.startActivity(intent);
            return true;
        }
    }
}
