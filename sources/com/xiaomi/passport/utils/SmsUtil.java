package com.xiaomi.passport.utils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.uplink.UplinkSendSmsFailedException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public class SmsUtil {
    public static final String TAG = "SmsUtil";

    private SmsUtil() {
    }

    public static class SendSmsReceiver extends BroadcastReceiver {
        private volatile CountDownLatch countDownLatch = new CountDownLatch(1);
        private volatile int result;

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(this);
            this.result = getResultCode();
            AccountLogger.log(SmsUtil.TAG, "received sms result code :" + this.result);
            this.countDownLatch.countDown();
        }

        public int waitResultCode(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new IllegalStateException("can not wait at main Thread");
            }
            if (!this.countDownLatch.await(j, timeUnit)) {
                throw new TimeoutException();
            }
            return this.result;
        }
    }

    public static void sendTextMessage(int i, String str, String str2, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws UplinkSendSmsFailedException {
        try {
            Class<?> cls = Class.forName("miui.telephony.SmsManager");
            cls.getMethod("sendTextMessage", String.class, String.class, String.class, PendingIntent.class, PendingIntent.class).invoke(cls.getDeclaredMethod("getDefault", Integer.TYPE).invoke(null, Integer.valueOf(i)), str, str2, str3, pendingIntent, pendingIntent2);
            AccountLogger.log(TAG, "successfully send text message");
        } catch (ClassNotFoundException e) {
            AccountLogger.log(TAG, "error when send text message: ClassNotFoundException", e);
            throw new UplinkSendSmsFailedException(e);
        } catch (IllegalAccessException e2) {
            AccountLogger.log(TAG, "error when send text message: IllegalAccessException", e2);
            throw new UplinkSendSmsFailedException(e2);
        } catch (NoSuchMethodException e3) {
            AccountLogger.log(TAG, "error when send text message: NoSuchMethodException", e3);
            throw new UplinkSendSmsFailedException(e3);
        } catch (SecurityException e4) {
            AccountLogger.log(TAG, "sendTextMessage", e4);
        } catch (InvocationTargetException e5) {
            AccountLogger.log(TAG, "error when send text message: InvocationTargetException", e5);
            throw new UplinkSendSmsFailedException(e5);
        }
    }
}
