package com.xiaomi.passport.utils;

import android.accounts.Account;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.hardware.DataSpace;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.DevInfoKeys;
import com.xiaomi.accountsdk.account.utils.XMPassportUtil;
import com.xiaomi.accountsdk.hasheddeviceidlib.AccountDeviceIdManager;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.jsb.PassportJsbWebViewPageConfig;
import com.xiaomi.passport.ui.internal.PassportJsbWebViewActivity;
import com.xiaomi.verificationsdk.internal.CommonUtils;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class NotifyAccountLoginAbnormal {
    private static final int ACCOUNT_LOGIN_NOTIFICATION_ID = 1002;
    private static final String NOTIFY_CHANNEL_NAME = "account_login_notify_channel_name";
    private static final String TAG = "NotifyAccountLoginAbnormal";

    public static void messageNotification(String str) {
        AccountLogger.log(TAG, "messageNotification>>>pushInfo: " + str);
        CommonUtils.checkWorkThreadAndThrow();
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Application applicationContext = XMPassportSettings.getApplicationContext();
        String deviceId = AccountDeviceIdManager.getInstance().getDeviceId();
        boolean zIsSystemAccount = XiaomiAccountManager.get(applicationContext).isSystemAccount();
        try {
            JSONObject jSONObject = new JSONObject(str);
            boolean zEquals = TextUtils.equals(deviceId, jSONObject.optString(DevInfoKeys.DEVICEID));
            AccountLogger.log(TAG, "messageNotification>>>isSystemAccount=" + zIsSystemAccount + "  isCurDevice=" + zEquals);
            if (zIsSystemAccount || zEquals) {
                return;
            }
            showNotification(applicationContext, tryRequestNotifyMsgByLocal(jSONObject));
        } catch (JSONException e) {
            AccountLogger.log(TAG, "messageNotification>>>Failed to parse pushInfo", e);
        }
    }

    public static JSONObject tryRequestNotifyMsgByLocal(JSONObject jSONObject) throws JSONException {
        String iSOLocaleString = XMPassportUtil.getISOLocaleString(Locale.getDefault());
        if (!TextUtils.equals(jSONObject.optString("locale"), iSOLocaleString)) {
            JSONObject notificationContentByLocale = XMPassport.getNotificationContentByLocale(jSONObject.getString("userId"), jSONObject.optString("loginDevice"), iSOLocaleString, jSONObject.optString(DevInfoKeys.DEVICEID));
            if (notificationContentByLocale != null) {
                AccountLogger.log(TAG, "tryRequestNotifyMsgByLocal>>>get notification content by locale");
                notificationContentByLocale.put(CallMethod.ARG_URI, jSONObject.getString(CallMethod.ARG_URI));
            }
            if (notificationContentByLocale != null) {
                return notificationContentByLocale;
            }
        }
        return jSONObject;
    }

    public static void showNotification(Context context, JSONObject jSONObject) throws JSONException {
        int i;
        if (jSONObject == null) {
            return;
        }
        String string = jSONObject.getString("title");
        String string2 = jSONObject.getString("body");
        String string3 = jSONObject.getString(CallMethod.ARG_URI);
        try {
            i = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).icon;
        } catch (PackageManager.NameNotFoundException unused) {
            AccountLogger.log(TAG, "showNotification>>>No application icon found");
            i = 0;
        }
        Intent intentMakeIntent = PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().cookieFillConfig(PassportJsbWebViewPageConfig.CookieFillConfig.create(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, false, "passportapi")).url(string3).build());
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        PendingIntent activity = PendingIntent.getActivity(context, 0, intentMakeIntent, 201326592);
        notificationManager.createNotificationChannel(new NotificationChannel(NOTIFY_CHANNEL_NAME, NOTIFY_CHANNEL_NAME, 4));
        notificationManager.notify(1002, new NotificationCompat.Builder(context, NOTIFY_CHANNEL_NAME).setSmallIcon(i).setContentTitle(string).setContentText(string2).setContentIntent(activity).setPriority(1).setAutoCancel(true).build());
    }

    public static void jumpToAccountLoginAbnormalPage(Context context, String str) {
        String string;
        AccountLogger.log(TAG, "jumpToAccountLoginAbnormalPage>>>pushInfo=" + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            string = new JSONObject(str).getString(CallMethod.ARG_URI);
        } catch (JSONException e) {
            AccountLogger.log(TAG, "jumpToAccountLoginAbnormalPage>>>Failed to parse pushInfo", e);
            string = null;
        }
        if (TextUtils.isEmpty(string)) {
            return;
        }
        Intent intentMakeIntent = PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().cookieFillConfig(PassportJsbWebViewPageConfig.CookieFillConfig.create(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, true, "passportapi")).url(string).build());
        intentMakeIntent.addFlags(DataSpace.RANGE_LIMITED);
        context.startActivity(intentMakeIntent);
    }

    public static void jumpToDeviceSettingListPage(Context context) {
        Account xiaomiAccount = XiaomiAccountManager.get(context).getXiaomiAccount();
        if (xiaomiAccount == null) {
            AccountLogger.log(TAG, "jumpToDeviceSettingListPage>> no xiaomi account");
            return;
        }
        HashMap map = new HashMap();
        map.put("userId", xiaomiAccount.name);
        map.put("_locale", com.xiaomi.accountsdk.utils.XMPassportUtil.getISOLocaleString(Locale.getDefault()));
        Intent intentMakeIntent = PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().cookieFillConfig(PassportJsbWebViewPageConfig.CookieFillConfig.create(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, true, "passportapi")).url(AnyUrl.make(URLs.URL_DEVICE_SETTING_LIST, map)).build());
        intentMakeIntent.addFlags(DataSpace.RANGE_LIMITED);
        context.startActivity(intentMakeIntent);
    }
}
