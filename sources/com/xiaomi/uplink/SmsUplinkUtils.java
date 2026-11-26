package com.xiaomi.uplink;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.passport.compat.ContextCompat;
import com.xiaomi.passport.utils.SmsUtil;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public class SmsUplinkUtils {
    private SmsUplinkUtils() {
    }

    public static void sendSms(Activity activity, String str, String str2) {
        if (activity == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException(" checkout params before send sms  ");
        }
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + str));
        intent.putExtra("sms_body", str2);
        activity.startActivity(intent);
    }

    public static boolean sendSmsAndWaitForResult(Context context, int i, String str, String str2, long j) throws UplinkSendSmsFailedException, InterruptedException, TimeoutException {
        SmsUtil.SendSmsReceiver sendSmsReceiver = new SmsUtil.SendSmsReceiver();
        ContextCompat.registerReceiver(context, sendSmsReceiver, new IntentFilter(PassportUplink.SEND_SMS_ACTION), false);
        SmsUtil.sendTextMessage(i, str, null, str2, PendingIntent.getBroadcast(context, 0, new Intent(PassportUplink.SEND_SMS_ACTION).setPackage(context.getPackageName()), 1140850688), null);
        return sendSmsReceiver.waitResultCode(j, TimeUnit.MILLISECONDS) == -1;
    }
}
