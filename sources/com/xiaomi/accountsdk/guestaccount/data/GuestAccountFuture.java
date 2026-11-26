package com.xiaomi.accountsdk.guestaccount.data;

import com.xiaomi.accountsdk.futureservice.SimpleClientFuture;
import com.xiaomi.accountsdk.guestaccount.GuestAccountConstants;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public final class GuestAccountFuture extends SimpleClientFuture<GuestAccountResult> {
    private static final String TAG = "GuestAccountFuture";

    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture, java.util.concurrent.FutureTask, java.util.concurrent.Future
    public GuestAccountResult get() throws InterruptedException {
        try {
            return (GuestAccountResult) super.get();
        } catch (ExecutionException e) {
            AccountLogger.log(TAG, "get()", e);
            return GuestAccountConstants.RESULT_OF_UNKNOWN_ERROR;
        }
    }

    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture, java.util.concurrent.FutureTask, java.util.concurrent.Future
    public GuestAccountResult get(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        try {
            return (GuestAccountResult) super.get(j, timeUnit);
        } catch (ExecutionException e) {
            AccountLogger.log(TAG, "get(,)", e);
            return GuestAccountConstants.RESULT_OF_UNKNOWN_ERROR;
        }
    }
}
