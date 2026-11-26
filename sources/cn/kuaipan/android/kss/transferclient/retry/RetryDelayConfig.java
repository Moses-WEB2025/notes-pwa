package cn.kuaipan.android.kss.transferclient.retry;

import java.util.Random;
import miui.cloud.common.XLogger;

/* JADX INFO: loaded from: classes.dex */
public class RetryDelayConfig {
    private static long mMaxRetryDelay;
    private static long mMinRetryDelay;
    private static Random sRandom = new Random();

    private RetryDelayConfig() {
        throw new IllegalStateException("RetryDelayConfig class");
    }

    public static void setRetryDelaySeconds(long j, long j2) {
        mMaxRetryDelay = j2 * 1000;
        mMinRetryDelay = j * 1000;
    }

    public static long getDelay(long j) {
        long randomDelay = (j < mMinRetryDelay || j > mMaxRetryDelay) ? getRandomDelay() : j;
        XLogger.logi("hint: " + j + ", delay: " + randomDelay);
        return randomDelay;
    }

    private static long getRandomDelay() {
        long j = mMaxRetryDelay;
        long j2 = mMinRetryDelay;
        return (Math.abs(sRandom.nextLong()) % ((j - j2) + 1)) + j2;
    }
}
