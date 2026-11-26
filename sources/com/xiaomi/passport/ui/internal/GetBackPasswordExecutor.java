package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.hardware.DataSpace;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.ui.internal.util.Constants;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class GetBackPasswordExecutor {
    private static final String TAG = "GetBackPasswordExecutor";
    private static GetBackPasswordTask sGetBackPasswordTask;

    public static void execute(Activity activity) {
        if (activity == null) {
            return;
        }
        if (sGetBackPasswordTask == null || AsyncTask.Status.FINISHED == sGetBackPasswordTask.getStatus()) {
            GetBackPasswordTask getBackPasswordTask = new GetBackPasswordTask(activity);
            sGetBackPasswordTask = getBackPasswordTask;
            getBackPasswordTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
        }
    }

    public static void execute(Activity activity, Runnable runnable) {
        if (activity == null) {
            return;
        }
        if (sGetBackPasswordTask == null || AsyncTask.Status.FINISHED == sGetBackPasswordTask.getStatus()) {
            GetBackPasswordTask getBackPasswordTask = new GetBackPasswordTask(activity, runnable);
            sGetBackPasswordTask = getBackPasswordTask;
            getBackPasswordTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
        }
    }

    public static void stopIfNeeded() {
        GetBackPasswordTask getBackPasswordTask = sGetBackPasswordTask;
        if (getBackPasswordTask != null) {
            getBackPasswordTask.cancel(true);
            sGetBackPasswordTask = null;
        }
    }

    private static class GetBackPasswordTask extends AsyncTask<Void, Void, Intent> {
        private Activity mActivity;
        private Runnable mPostRunnable;

        public GetBackPasswordTask(Activity activity) {
            this.mActivity = activity;
        }

        public GetBackPasswordTask(Activity activity, Runnable runnable) {
            this(activity);
            this.mPostRunnable = runnable;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Intent doInBackground(Void... voidArr) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(getLocalizedPasswordRecoveryUri(this.mActivity.getResources().getConfiguration().locale, AccountHelper.getHashedDeviceId()));
            intent.addFlags(67108864);
            intent.addFlags(DataSpace.RANGE_LIMITED);
            return intent;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Intent intent) {
            Activity activity = this.mActivity;
            if (activity != null && !activity.isFinishing()) {
                try {
                    this.mActivity.startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    AccountLogger.log(GetBackPasswordExecutor.TAG, "cannot find browser");
                    AccountToast.showToastMessage(this.mActivity, "Cannot find the Browser App", 1);
                }
            }
            this.mActivity = null;
            GetBackPasswordTask unused2 = GetBackPasswordExecutor.sGetBackPasswordTask = null;
            Runnable runnable = this.mPostRunnable;
            if (runnable != null) {
                runnable.run();
            }
        }

        private static Uri getLocalizedPasswordRecoveryUri(Locale locale, String str) {
            Uri.Builder builderBuildUpon = Uri.parse(Constants.PASSWORD_RECOVERY_URL).buildUpon();
            if (str != null) {
                builderBuildUpon.appendQueryParameter("hint", str);
            }
            String iSOLocaleString = XMPassportUtil.getISOLocaleString(locale);
            if (iSOLocaleString != null) {
                builderBuildUpon.appendQueryParameter("_locale", iSOLocaleString);
            }
            return builderBuildUpon.build();
        }
    }
}
