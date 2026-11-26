package miui.cloud.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes5.dex */
public class XBroadcast {

    public static class BroadcastResult {
        public int code;
        public String data;
        public Bundle extra;

        public BroadcastResult(int i, String str, Bundle bundle) {
            this.code = i;
            this.data = str;
            this.extra = bundle;
        }
    }

    public static BroadcastResult syncSendBroadcast(Context context, Intent intent, String str) throws InterruptedException {
        try {
            return syncSendBroadcast(context, intent, str, -1L);
        } catch (TimeoutException unused) {
            throw new IllegalStateException("Never reach here. ");
        }
    }

    public static BroadcastResult syncSendBroadcast(Context context, Intent intent, String str, long j) throws InterruptedException, TimeoutException {
        HandlerThread handlerThread = new HandlerThread(XBroadcast.class.getName());
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final XWrapper xWrapper = new XWrapper();
        context.sendOrderedBroadcast(intent, str, new BroadcastReceiver() { // from class: miui.cloud.common.XBroadcast.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent2) {
                XLogger.log("result received. ");
                xWrapper.set(new BroadcastResult(getResultCode(), getResultData(), getResultExtras(true)));
                countDownLatch.countDown();
            }
        }, new Handler(looper), -1, null, null);
        try {
            if (j < 0) {
                countDownLatch.await();
            } else if (!countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
                throw new TimeoutException();
            }
            looper.quit();
            return (BroadcastResult) xWrapper.get();
        } catch (Throwable th) {
            looper.quit();
            throw th;
        }
    }
}
