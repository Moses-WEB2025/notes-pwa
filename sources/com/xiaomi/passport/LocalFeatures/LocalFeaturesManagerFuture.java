package com.xiaomi.passport.LocalFeatures;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import com.xiaomi.accountsdk.account.exception.IllegalDeviceException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public interface LocalFeaturesManagerFuture<V> {
    boolean cancel(boolean z);

    V getResult() throws InvalidUserNameException, AccessDeniedException, IllegalDeviceException, InvalidCredentialException, OperationCanceledException, IOException, InvalidResponseException, AuthenticatorException, AuthenticationFailureException;

    V getResult(long j, TimeUnit timeUnit) throws InvalidUserNameException, AccessDeniedException, IllegalDeviceException, InvalidCredentialException, OperationCanceledException, IOException, InvalidResponseException, AuthenticatorException, AuthenticationFailureException;

    boolean isCancelled();

    boolean isDone();
}
