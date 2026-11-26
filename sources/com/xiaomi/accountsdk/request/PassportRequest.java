package com.xiaomi.accountsdk.request;

import com.xiaomi.account.exception.PassportCAException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public abstract class PassportRequest {
    public abstract SimpleRequest.StringContent execute() throws PassportRequestException, IOException;

    public final SimpleRequest.StringContent executeEx() throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, NeedVerificationException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, PassportCAException, AuthenticationFailureException {
        try {
            return execute();
        } catch (PassportRequestException e) {
            Throwable cause = e.getCause();
            if (cause instanceof InvalidResponseException) {
                throw ((InvalidResponseException) cause);
            }
            if (cause instanceof InvalidCredentialException) {
                throw ((InvalidCredentialException) cause);
            }
            if (cause instanceof InvalidUserNameException) {
                throw ((InvalidUserNameException) cause);
            }
            if (cause instanceof NeedVerificationException) {
                throw ((NeedVerificationException) cause);
            }
            if (cause instanceof NeedCaptchaException) {
                throw ((NeedCaptchaException) cause);
            }
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            }
            if (cause instanceof AccessDeniedException) {
                throw ((AccessDeniedException) cause);
            }
            if (cause instanceof AuthenticationFailureException) {
                throw ((AuthenticationFailureException) cause);
            }
            if (cause instanceof NeedNotificationException) {
                throw ((NeedNotificationException) cause);
            }
            if (cause instanceof PassportCAException) {
                throw ((PassportCAException) cause);
            }
            throw new IllegalStateException("");
        }
    }
}
