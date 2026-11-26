package cn.kuaipan.android.kss.transferclient.retry;

import miui.cloud.common.XLogger;

/* JADX INFO: loaded from: classes.dex */
public class RetryControl {
    private static int mMaxRetryLimit;
    private int mRetryCountDown = mMaxRetryLimit;
    private boolean mDoneOrRetryCalled = true;
    private boolean mDone = false;

    public static void setMaxRetryLimit(int i) {
        mMaxRetryLimit = i;
    }

    public boolean loopForRetry() {
        if (!this.mDoneOrRetryCalled) {
            throw new IllegalStateException("either *done* or *retry* must be called at the end of a loop");
        }
        this.mDoneOrRetryCalled = false;
        if (this.mDone) {
            return false;
        }
        this.mRetryCountDown--;
        return true;
    }

    public int getRetryCountDown() {
        return this.mRetryCountDown;
    }

    public void done() {
        if (this.mDoneOrRetryCalled) {
            throw new IllegalStateException("*done* or *retry* has been called");
        }
        this.mDoneOrRetryCalled = true;
        this.mDone = true;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: TypeRetryReason extends java.lang.Exception */
    public <TypeRetryReason extends Exception> void retry(TypeRetryReason typeretryreason, long j) throws Exception {
        if (this.mDoneOrRetryCalled) {
            throw new IllegalStateException("*done* or *retry* has been called");
        }
        this.mDoneOrRetryCalled = true;
        if (this.mRetryCountDown <= 0) {
            throw typeretryreason;
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        long delay = RetryDelayConfig.getDelay(j);
        XLogger.logi("Need retry after " + delay + " ms, because " + typeretryreason);
        Thread.sleep(delay);
    }
}
