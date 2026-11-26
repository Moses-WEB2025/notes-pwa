package com.xiaomi.passport.uicontroller;

import android.text.TextUtils;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.CheckRegPhoneParams;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTicketLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTokenRegisterParams;
import com.xiaomi.accountsdk.account.data.QueryPhoneInfoParams;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.account.data.SendPhoneTicketParams;
import com.xiaomi.accountsdk.account.data.SetPasswordParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.account.exception.AccountException;
import com.xiaomi.accountsdk.account.exception.HttpException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidParameterException;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneOrTicketException;
import com.xiaomi.accountsdk.account.exception.InvalidTzSignException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.account.exception.PassportIOException;
import com.xiaomi.accountsdk.account.exception.ReachLimitException;
import com.xiaomi.accountsdk.account.exception.TokenExpiredException;
import com.xiaomi.accountsdk.account.exception.UserRestrictedException;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.PassportUserEnvironment;
import com.xiaomi.passport.data.LoginPreference;
import com.xiaomi.passport.utils.LoginPreferenceConfig;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneLoginController {
    private static final String TAG = "PhoneLoginController";
    private static final ExecutorService mExecutorService = Executors.newCachedThreadPool();
    private PhoneUserInfoHelper mPhoneUserInfoHelper = new PhoneUserInfoHelper();

    public interface CheckAppIsCanUseSnsLoginCallback {
        void onServerError(ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo);

        void onSuccess(boolean z);
    }

    public enum ErrorCode {
        NONE,
        ERROR_UNKNOWN,
        ERROR_AUTH_FAIL,
        ERROR_NETWORK,
        ERROR_SERVER,
        ERROR_ACCESS_DENIED,
        ERROR_INVALID_PARAM,
        ERROR_USER_ACTION_OVER_LIMIT,
        ERROR_PASSWORD,
        ERROR_NON_EXIST_USER,
        ERROR_NO_PHONE
    }

    public interface PasswordLoginCallback {
        void onLoginByStep2(Step2LoginParams step2LoginParams);

        void onLoginFailed(ErrorCode errorCode, String str, boolean z);

        void onLoginSuccess(AccountInfo accountInfo);

        void onNeedCaptchaCode(boolean z, String str);

        void onNeedNotification(String str, String str2);

        void onServerError(ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo);
    }

    public interface PhoneLoginConfigCallback {
        void onError(ErrorCode errorCode, String str);

        void onPhoneNumInvalid();

        void onSuccess(LoginPreference loginPreference);
    }

    public interface PhoneLoginConfigExtensionCallback {
        void onError(ErrorCode errorCode, String str);

        void onPhoneNumInvalid();

        void onServerError(ErrorCode errorCode, String str, PassThroughErrorInfo passThroughErrorInfo);

        void onSuccess(LoginPreference loginPreference);
    }

    public interface PhoneRegisterCallback {
        void onRegisterFailed(ErrorCode errorCode, String str);

        void onRegisterReachLimit();

        void onRegisterSuccess(AccountInfo accountInfo);

        void onServerError(ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo);

        void onTokenExpired();
    }

    public interface PhoneUserInfoCallback {
        void onNotRecycledRegisteredPhone(RegisterUserInfo registerUserInfo);

        void onPhoneNumInvalid();

        void onProbablyRecycleRegisteredPhone(RegisterUserInfo registerUserInfo);

        void onQueryFailed(ErrorCode errorCode, String str);

        void onRecycledOrNotRegisteredPhone(RegisterUserInfo registerUserInfo);

        void onServerError(ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo);

        void onTicketOrTokenInvalid();
    }

    public interface SendPhoneTicketCallback {
        void onActivatorTokenExpired();

        void onNeedCaptchaCode(String str);

        void onPhoneNumInvalid();

        void onSMSReachLimit();

        void onSentFailed(ErrorCode errorCode, String str);

        void onSentSuccess(int i);
    }

    public interface SendPhoneTicketExtensionCallback {
        void onActivatorTokenExpired();

        void onNeedCaptchaCode(String str, String str2);

        void onPhoneNumInvalid();

        void onSMSReachLimit();

        void onSentFailed(ErrorCode errorCode, String str);

        void onSentSuccess(int i);

        void onServerError(ErrorCode errorCode, String str, PassThroughErrorInfo passThroughErrorInfo);
    }

    public interface SendSetPwdTicketCallback {
        void onSMSReachLimit();

        void onSentFailed(ErrorCode errorCode, String str);

        void onSentServiceFailed(PassThroughErrorInfo passThroughErrorInfo);

        void onSentSuccess(String str);
    }

    public interface SetPasswordCallback {
        void onHasPassword();

        void onNeedTicketOrTicketInvalid();

        void onPassTokenInvalid();

        void onSetFailed(ErrorCode errorCode, String str);

        void onSetServiceFailed(PassThroughErrorInfo passThroughErrorInfo);

        void onSetSuccess(String str);
    }

    public interface TicketLoginCallback {
        void onLoginFailed(ErrorCode errorCode, String str, boolean z);

        void onLoginSuccess(AccountInfo accountInfo);

        void onNeedNotification(String str, String str2);

        void onPhoneNumInvalid();

        void onServerError(ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo);

        void onTicketOrTokenInvalid();

        void onTzSignInvalid();
    }

    public void setPhoneUserInfoHelper(PhoneUserInfoHelper phoneUserInfoHelper) {
        this.mPhoneUserInfoHelper = phoneUserInfoHelper;
    }

    public SimpleFutureTask<Integer> sendPhoneTicket(final SendPhoneTicketParams sendPhoneTicketParams, final SendPhoneTicketExtensionCallback sendPhoneTicketExtensionCallback) {
        if (sendPhoneTicketExtensionCallback == null) {
            throw new IllegalArgumentException("should implements login callback");
        }
        SimpleFutureTask<Integer> simpleFutureTask = new SimpleFutureTask<>(new Callable<Integer>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Integer call() throws Exception {
                return Integer.valueOf(XMPassport.sendPhoneLoginTicket(sendPhoneTicketParams));
            }
        }, new SimpleFutureTask.Callback<Integer>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.1
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<Integer> simpleFutureTask2) {
                try {
                    sendPhoneTicketExtensionCallback.onSentSuccess(simpleFutureTask2.get().intValue());
                } catch (InterruptedException e) {
                    AccountLogger.log(PhoneLoginController.TAG, "sendPhoneLoginTicket", e);
                    sendPhoneTicketExtensionCallback.onSentFailed(ErrorCode.ERROR_UNKNOWN, e.getMessage());
                } catch (ExecutionException e2) {
                    AccountLogger.log(PhoneLoginController.TAG, "sendPhoneLoginTicket", e2);
                    Throwable cause = e2.getCause();
                    if (cause instanceof NeedCaptchaException) {
                        NeedCaptchaException needCaptchaException = (NeedCaptchaException) cause;
                        sendPhoneTicketExtensionCallback.onNeedCaptchaCode(needCaptchaException.getCaptchaUrl(), needCaptchaException.getCaptchaType());
                        return;
                    }
                    if (cause instanceof TokenExpiredException) {
                        sendPhoneTicketExtensionCallback.onActivatorTokenExpired();
                        return;
                    }
                    if (cause instanceof ReachLimitException) {
                        sendPhoneTicketExtensionCallback.onSMSReachLimit();
                        return;
                    }
                    if (!(cause instanceof InvalidPhoneNumException)) {
                        ErrorCode errorCodeConvertErrorCode = PhoneLoginController.convertErrorCode(cause);
                        if (cause instanceof InvalidResponseException) {
                            PassThroughErrorInfo serverError = ((InvalidResponseException) cause).getServerError();
                            if (serverError != null) {
                                sendPhoneTicketExtensionCallback.onServerError(errorCodeConvertErrorCode, e2.getMessage(), serverError);
                                return;
                            } else {
                                sendPhoneTicketExtensionCallback.onSentFailed(errorCodeConvertErrorCode, null);
                                return;
                            }
                        }
                        sendPhoneTicketExtensionCallback.onSentFailed(errorCodeConvertErrorCode, null);
                        return;
                    }
                    sendPhoneTicketExtensionCallback.onPhoneNumInvalid();
                }
            }
        });
        mExecutorService.submit(simpleFutureTask);
        return simpleFutureTask;
    }

    public SimpleFutureTask<RegisterUserInfo> queryPhoneUserInfo(final QueryPhoneInfoParams queryPhoneInfoParams, final PhoneUserInfoCallback phoneUserInfoCallback) {
        if (phoneUserInfoCallback == null) {
            throw new IllegalArgumentException("should implements phone user info callback");
        }
        SimpleFutureTask<RegisterUserInfo> simpleFutureTask = new SimpleFutureTask<>(new Callable<RegisterUserInfo>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public RegisterUserInfo call() throws Exception {
                return PhoneLoginController.this.mPhoneUserInfoHelper.query(queryPhoneInfoParams);
            }
        }, new SimpleFutureTask.Callback<RegisterUserInfo>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.3
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<RegisterUserInfo> simpleFutureTask2) {
                try {
                    RegisterUserInfo registerUserInfo = simpleFutureTask2.get();
                    RegisterUserInfo.RegisterStatus registerStatus = registerUserInfo.status;
                    if (registerStatus == RegisterUserInfo.RegisterStatus.STATUS_NOT_REGISTERED) {
                        phoneUserInfoCallback.onRecycledOrNotRegisteredPhone(registerUserInfo);
                    } else if (registerStatus == RegisterUserInfo.RegisterStatus.STATUS_REGISTERED_NOT_RECYCLED) {
                        phoneUserInfoCallback.onNotRecycledRegisteredPhone(registerUserInfo);
                    } else {
                        phoneUserInfoCallback.onProbablyRecycleRegisteredPhone(registerUserInfo);
                    }
                } catch (InterruptedException e) {
                    AccountLogger.log(PhoneLoginController.TAG, "query user phone info", e);
                    phoneUserInfoCallback.onQueryFailed(ErrorCode.ERROR_UNKNOWN, e.getMessage());
                } catch (ExecutionException e2) {
                    AccountLogger.log(PhoneLoginController.TAG, "query user phone info", e2);
                    Throwable cause = e2.getCause();
                    if (cause instanceof InvalidVerifyCodeException) {
                        phoneUserInfoCallback.onTicketOrTokenInvalid();
                        return;
                    }
                    if (cause instanceof InvalidPhoneNumException) {
                        phoneUserInfoCallback.onPhoneNumInvalid();
                        return;
                    }
                    if (cause instanceof InvalidResponseException) {
                        ErrorCode errorCodeConvertErrorCode = PhoneLoginController.convertErrorCode(cause);
                        PassThroughErrorInfo serverError = ((InvalidResponseException) cause).getServerError();
                        if (serverError != null) {
                            phoneUserInfoCallback.onServerError(errorCodeConvertErrorCode, serverError);
                            return;
                        } else {
                            phoneUserInfoCallback.onQueryFailed(errorCodeConvertErrorCode, e2.getMessage());
                            return;
                        }
                    }
                    phoneUserInfoCallback.onQueryFailed(PhoneLoginController.convertErrorCode(cause), e2.getMessage());
                }
            }
        });
        mExecutorService.submit(simpleFutureTask);
        return simpleFutureTask;
    }

    public SimpleFutureTask<AccountInfo> ticketLogin(final PhoneTicketLoginParams phoneTicketLoginParams, final TicketLoginCallback ticketLoginCallback) {
        if (ticketLoginCallback == null) {
            throw new IllegalArgumentException("should implements login callback");
        }
        SimpleFutureTask<AccountInfo> simpleFutureTask = new SimpleFutureTask<>(new Callable<AccountInfo>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public AccountInfo call() throws Exception {
                PhoneTicketLoginParams phoneTicketLoginParamsBuild;
                if (phoneTicketLoginParams.hashedEnvFactors == null) {
                    phoneTicketLoginParamsBuild = PhoneTicketLoginParams.copyFrom(phoneTicketLoginParams).hashedEnvFactors(PassportUserEnvironment.Holder.getInstance().getEnvInfoArray(XMPassportSettings.getApplicationContext())).build();
                } else {
                    phoneTicketLoginParamsBuild = phoneTicketLoginParams;
                }
                return XMPassport.loginByPhone(phoneTicketLoginParamsBuild);
            }
        }, new SimpleFutureTask.Callback<AccountInfo>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.5
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<AccountInfo> simpleFutureTask2) {
                try {
                    ticketLoginCallback.onLoginSuccess(simpleFutureTask2.get());
                } catch (InterruptedException e) {
                    AccountLogger.log(PhoneLoginController.TAG, "loginByPhoneTicket", e);
                    ticketLoginCallback.onLoginFailed(ErrorCode.ERROR_UNKNOWN, e.getMessage(), false);
                } catch (ExecutionException e2) {
                    AccountLogger.log(PhoneLoginController.TAG, "loginByPhoneTicket", e2);
                    Throwable cause = e2.getCause();
                    if (cause instanceof NeedNotificationException) {
                        ticketLoginCallback.onNeedNotification(phoneTicketLoginParams.serviceId, ((NeedNotificationException) cause).getNotificationUrl());
                        return;
                    }
                    if (cause instanceof InvalidPhoneNumException) {
                        ticketLoginCallback.onPhoneNumInvalid();
                        return;
                    }
                    if (cause instanceof InvalidVerifyCodeException) {
                        ticketLoginCallback.onTicketOrTokenInvalid();
                        return;
                    }
                    if (cause instanceof InvalidTzSignException) {
                        ticketLoginCallback.onTzSignInvalid();
                        return;
                    }
                    if (!(cause instanceof InvalidResponseException)) {
                        ticketLoginCallback.onLoginFailed(PhoneLoginController.convertErrorCode(cause), e2.getMessage(), PhoneLoginController.this.checkIsStsUrlRequestError(cause));
                        return;
                    }
                    AccountLogger.log(PhoneLoginController.TAG, "invalid response", e2);
                    ErrorCode errorCodeConvertErrorCode = PhoneLoginController.convertErrorCode(cause);
                    InvalidResponseException invalidResponseException = (InvalidResponseException) cause;
                    PassThroughErrorInfo serverError = invalidResponseException.getServerError();
                    if (serverError != null) {
                        ticketLoginCallback.onServerError(errorCodeConvertErrorCode, serverError);
                    } else {
                        ticketLoginCallback.onLoginFailed(errorCodeConvertErrorCode, e2.getMessage(), invalidResponseException.isStsUrlRequestError);
                    }
                }
            }
        });
        mExecutorService.submit(simpleFutureTask);
        return simpleFutureTask;
    }

    public SimpleFutureTask<AccountInfo> passwordLogin(final PasswordLoginParams passwordLoginParams, final PasswordLoginCallback passwordLoginCallback) {
        if (passwordLoginCallback == null) {
            throw new IllegalArgumentException("should implements login callback");
        }
        SimpleFutureTask<AccountInfo> simpleFutureTask = new SimpleFutureTask<>(new Callable<AccountInfo>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public AccountInfo call() throws Exception {
                return XMPassport.loginByPassword(passwordLoginParams);
            }
        }, new SimpleFutureTask.Callback<AccountInfo>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.7
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<AccountInfo> simpleFutureTask2) {
                try {
                    passwordLoginCallback.onLoginSuccess(simpleFutureTask2.get());
                } catch (InterruptedException e) {
                    AccountLogger.log(PhoneLoginController.TAG, "passwordLogin", e);
                    passwordLoginCallback.onLoginFailed(ErrorCode.ERROR_UNKNOWN, e.getMessage(), false);
                } catch (ExecutionException e2) {
                    AccountLogger.log(PhoneLoginController.TAG, "passwordLogin", e2);
                    Throwable cause = e2.getCause();
                    if (cause instanceof NeedNotificationException) {
                        passwordLoginCallback.onNeedNotification(passwordLoginParams.serviceId, ((NeedNotificationException) cause).getNotificationUrl());
                        return;
                    }
                    if (cause instanceof NeedVerificationException) {
                        NeedVerificationException needVerificationException = (NeedVerificationException) cause;
                        passwordLoginCallback.onLoginByStep2(new Step2LoginParams.Builder().setUserId(needVerificationException.getUserId()).setMetaLoginData(needVerificationException.getMetaLoginData()).setServiceId(passwordLoginParams.serviceId).setStep1Token(needVerificationException.getStep1Token()).build());
                        return;
                    }
                    if (cause instanceof NeedCaptchaException) {
                        passwordLoginCallback.onNeedCaptchaCode(false, ((NeedCaptchaException) cause).getCaptchaUrl());
                        return;
                    }
                    if (cause instanceof InvalidCredentialException) {
                        InvalidCredentialException invalidCredentialException = (InvalidCredentialException) cause;
                        if (!TextUtils.isEmpty(invalidCredentialException.getCaptchaUrl())) {
                            passwordLoginCallback.onNeedCaptchaCode(true, invalidCredentialException.getCaptchaUrl());
                            return;
                        } else {
                            passwordLoginCallback.onLoginFailed(ErrorCode.ERROR_PASSWORD, e2.getMessage(), false);
                            return;
                        }
                    }
                    if (!(cause instanceof InvalidResponseException)) {
                        passwordLoginCallback.onLoginFailed(PhoneLoginController.convertErrorCode(cause), e2.getMessage(), PhoneLoginController.this.checkIsStsUrlRequestError(cause));
                        return;
                    }
                    AccountLogger.log(PhoneLoginController.TAG, "invalid response", e2);
                    ErrorCode errorCodeConvertErrorCode = PhoneLoginController.convertErrorCode(cause);
                    InvalidResponseException invalidResponseException = (InvalidResponseException) cause;
                    PassThroughErrorInfo serverError = invalidResponseException.getServerError();
                    if (serverError != null) {
                        passwordLoginCallback.onServerError(errorCodeConvertErrorCode, serverError);
                    } else {
                        passwordLoginCallback.onLoginFailed(errorCodeConvertErrorCode, e2.getMessage(), invalidResponseException.isStsUrlRequestError);
                    }
                }
            }
        });
        mExecutorService.submit(simpleFutureTask);
        return simpleFutureTask;
    }

    public SimpleFutureTask<AccountInfo> loginWithDynamicToken(final Step2LoginParams step2LoginParams, final PasswordLoginCallback passwordLoginCallback) {
        if (passwordLoginCallback == null) {
            throw new IllegalArgumentException("should implements login callback");
        }
        SimpleFutureTask<AccountInfo> simpleFutureTask = new SimpleFutureTask<>(new Callable<AccountInfo>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.10
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public AccountInfo call() throws Exception {
                return XMPassport.loginByStep2(step2LoginParams);
            }
        }, new SimpleFutureTask.Callback<AccountInfo>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.9
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<AccountInfo> simpleFutureTask2) {
                try {
                    passwordLoginCallback.onLoginSuccess(simpleFutureTask2.get());
                } catch (InterruptedException e) {
                    AccountLogger.log(PhoneLoginController.TAG, "sendPhoneLoginTicket", e);
                    passwordLoginCallback.onLoginFailed(ErrorCode.ERROR_UNKNOWN, e.getMessage(), false);
                } catch (ExecutionException e2) {
                    AccountLogger.log(PhoneLoginController.TAG, "sendPhoneLoginTicket", e2);
                    Throwable cause = e2.getCause();
                    if (cause instanceof NeedNotificationException) {
                        passwordLoginCallback.onNeedNotification(step2LoginParams.serviceId, ((NeedNotificationException) cause).getNotificationUrl());
                        return;
                    }
                    if (cause instanceof NeedVerificationException) {
                        NeedVerificationException needVerificationException = (NeedVerificationException) cause;
                        passwordLoginCallback.onLoginByStep2(new Step2LoginParams.Builder().setUserId(needVerificationException.getUserId()).setMetaLoginData(needVerificationException.getMetaLoginData()).setServiceId(step2LoginParams.serviceId).setStep1Token(needVerificationException.getStep1Token()).build());
                    } else {
                        if (cause instanceof NeedCaptchaException) {
                            passwordLoginCallback.onNeedCaptchaCode(false, ((NeedCaptchaException) cause).getCaptchaUrl());
                            return;
                        }
                        if (!(cause instanceof InvalidCredentialException)) {
                            passwordLoginCallback.onLoginFailed(PhoneLoginController.convertErrorCode(cause), e2.getMessage(), PhoneLoginController.this.checkIsStsUrlRequestError(cause));
                        } else {
                            InvalidCredentialException invalidCredentialException = (InvalidCredentialException) cause;
                            if (!TextUtils.isEmpty(invalidCredentialException.getCaptchaUrl())) {
                                passwordLoginCallback.onNeedCaptchaCode(true, invalidCredentialException.getCaptchaUrl());
                            } else {
                                passwordLoginCallback.onLoginFailed(ErrorCode.ERROR_PASSWORD, e2.getMessage(), false);
                            }
                        }
                    }
                }
            }
        });
        mExecutorService.submit(simpleFutureTask);
        return simpleFutureTask;
    }

    public SimpleFutureTask<AccountInfo> register(final PhoneTokenRegisterParams phoneTokenRegisterParams, final PhoneRegisterCallback phoneRegisterCallback) {
        if (phoneRegisterCallback == null) {
            throw new IllegalArgumentException("should implements register callback");
        }
        SimpleFutureTask<AccountInfo> simpleFutureTask = new SimpleFutureTask<>(new Callable<AccountInfo>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.12
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public AccountInfo call() throws Exception {
                return XMPassport.regByPhoneWithToken(phoneTokenRegisterParams);
            }
        }, new SimpleFutureTask.Callback<AccountInfo>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.11
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<AccountInfo> simpleFutureTask2) {
                try {
                    phoneRegisterCallback.onRegisterSuccess(simpleFutureTask2.get());
                } catch (InterruptedException e) {
                    AccountLogger.log(PhoneLoginController.TAG, "registerByPhone", e);
                    phoneRegisterCallback.onRegisterFailed(ErrorCode.ERROR_UNKNOWN, e.getMessage());
                } catch (ExecutionException e2) {
                    AccountLogger.log(PhoneLoginController.TAG, "registerByPhone", e2);
                    Throwable cause = e2.getCause();
                    if (cause instanceof UserRestrictedException) {
                        phoneRegisterCallback.onRegisterReachLimit();
                        return;
                    }
                    if (cause instanceof TokenExpiredException) {
                        phoneRegisterCallback.onTokenExpired();
                        return;
                    }
                    if (cause instanceof ReachLimitException) {
                        phoneRegisterCallback.onRegisterFailed(ErrorCode.ERROR_USER_ACTION_OVER_LIMIT, e2.getMessage());
                        return;
                    }
                    if (!(cause instanceof InvalidResponseException)) {
                        phoneRegisterCallback.onRegisterFailed(PhoneLoginController.convertErrorCode(cause), e2.getMessage());
                        return;
                    }
                    AccountLogger.log(PhoneLoginController.TAG, "invalid response", e2);
                    ErrorCode errorCodeConvertErrorCode = PhoneLoginController.convertErrorCode(cause);
                    InvalidResponseException invalidResponseException = (InvalidResponseException) cause;
                    PassThroughErrorInfo serverError = invalidResponseException.getServerError();
                    if (serverError != null) {
                        phoneRegisterCallback.onServerError(errorCodeConvertErrorCode, serverError);
                    } else {
                        boolean z = invalidResponseException.isStsUrlRequestError;
                        phoneRegisterCallback.onRegisterFailed(errorCodeConvertErrorCode, e2.getMessage());
                    }
                }
            }
        });
        mExecutorService.submit(simpleFutureTask);
        return simpleFutureTask;
    }

    public SimpleFutureTask<String> sendSetPasswordTicket(final PassportInfo passportInfo, final String str, final SendSetPwdTicketCallback sendSetPwdTicketCallback) {
        if (sendSetPwdTicketCallback == null) {
            throw new IllegalArgumentException("should implement send set pwd ticket callback");
        }
        SimpleFutureTask<String> simpleFutureTask = new SimpleFutureTask<>(new Callable<String>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.14
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                return XMPassport.sendSetPasswordTicket(passportInfo, str);
            }
        }, new SimpleFutureTask.Callback<String>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.13
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<String> simpleFutureTask2) {
                try {
                    sendSetPwdTicketCallback.onSentSuccess(simpleFutureTask2.get());
                } catch (InterruptedException e) {
                    AccountLogger.log(PhoneLoginController.TAG, "sendSetPasswordTicket", e);
                    sendSetPwdTicketCallback.onSentFailed(ErrorCode.ERROR_UNKNOWN, e.getMessage());
                } catch (ExecutionException e2) {
                    AccountLogger.log(PhoneLoginController.TAG, "sendSetPasswordTicket", e2);
                    Throwable cause = e2.getCause();
                    if (cause instanceof ReachLimitException) {
                        sendSetPwdTicketCallback.onSMSReachLimit();
                        return;
                    }
                    if (cause instanceof InvalidPhoneNumException) {
                        sendSetPwdTicketCallback.onSentFailed(ErrorCode.ERROR_NO_PHONE, e2.getMessage());
                    } else if (!(cause instanceof InvalidResponseException)) {
                        sendSetPwdTicketCallback.onSentFailed(PhoneLoginController.convertErrorCode(cause), e2.getMessage());
                    } else {
                        sendSetPwdTicketCallback.onSentServiceFailed(((InvalidResponseException) cause).getServerError());
                    }
                }
            }
        });
        mExecutorService.submit(simpleFutureTask);
        return simpleFutureTask;
    }

    public SimpleFutureTask<String> setPassword(final SetPasswordParams setPasswordParams, final SetPasswordCallback setPasswordCallback) {
        if (setPasswordCallback == null) {
            throw new IllegalArgumentException("should implement set password callback");
        }
        SimpleFutureTask<String> simpleFutureTask = new SimpleFutureTask<>(new Callable<String>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.16
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                return XMPassport.setPassword(setPasswordParams);
            }
        }, new SimpleFutureTask.Callback<String>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.15
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<String> simpleFutureTask2) {
                try {
                    setPasswordCallback.onSetSuccess(simpleFutureTask2.get());
                } catch (InterruptedException e) {
                    AccountLogger.log(PhoneLoginController.TAG, "setPassword", e);
                    setPasswordCallback.onSetFailed(ErrorCode.ERROR_UNKNOWN, e.getMessage());
                } catch (ExecutionException e2) {
                    AccountLogger.log(PhoneLoginController.TAG, "setPassword", e2);
                    Throwable cause = e2.getCause();
                    if (cause instanceof InvalidVerifyCodeException) {
                        setPasswordCallback.onNeedTicketOrTicketInvalid();
                        return;
                    }
                    if (cause instanceof InvalidCredentialException) {
                        setPasswordCallback.onPassTokenInvalid();
                        return;
                    }
                    if (cause instanceof UserRestrictedException) {
                        setPasswordCallback.onHasPassword();
                    } else if (!(cause instanceof InvalidResponseException)) {
                        setPasswordCallback.onSetFailed(PhoneLoginController.convertErrorCode(cause), e2.getMessage());
                    } else {
                        setPasswordCallback.onSetServiceFailed(((InvalidResponseException) cause).getServerError());
                    }
                }
            }
        });
        mExecutorService.submit(simpleFutureTask);
        return simpleFutureTask;
    }

    public static SimpleFutureTask<LoginPreference> getPhoneLoginConfigOnLine(final String str, final String str2, final String str3, final String str4, final PhoneLoginConfigExtensionCallback phoneLoginConfigExtensionCallback) {
        SimpleFutureTask<LoginPreference> simpleFutureTask = new SimpleFutureTask<>(new Callable<LoginPreference>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.18
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public LoginPreference call() throws Exception {
                return LoginPreferenceConfig.getPhoneLoginConfigOnLine(str, str2, str3, str4);
            }
        }, phoneLoginConfigExtensionCallback == null ? null : new SimpleFutureTask.Callback<LoginPreference>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.17
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<LoginPreference> simpleFutureTask2) {
                try {
                    phoneLoginConfigExtensionCallback.onSuccess(simpleFutureTask2.get());
                } catch (InterruptedException e) {
                    AccountLogger.log(PhoneLoginController.TAG, "getPhoneLoginConfigOnLine", e);
                    phoneLoginConfigExtensionCallback.onError(ErrorCode.ERROR_UNKNOWN, e.getMessage());
                } catch (ExecutionException e2) {
                    AccountLogger.log(PhoneLoginController.TAG, "getPhoneLoginConfigOnLine", e2);
                    Throwable cause = e2.getCause();
                    if (!(cause instanceof InvalidPhoneNumException)) {
                        ErrorCode errorCodeConvertErrorCode = PhoneLoginController.convertErrorCode(cause);
                        if (cause instanceof InvalidResponseException) {
                            PassThroughErrorInfo serverError = ((InvalidResponseException) cause).getServerError();
                            if (serverError != null) {
                                phoneLoginConfigExtensionCallback.onServerError(errorCodeConvertErrorCode, e2.getMessage(), serverError);
                                return;
                            } else {
                                phoneLoginConfigExtensionCallback.onError(errorCodeConvertErrorCode, e2.getMessage());
                                return;
                            }
                        }
                        phoneLoginConfigExtensionCallback.onError(errorCodeConvertErrorCode, e2.getMessage());
                        return;
                    }
                    phoneLoginConfigExtensionCallback.onPhoneNumInvalid();
                }
            }
        });
        mExecutorService.submit(simpleFutureTask);
        return simpleFutureTask;
    }

    public static SimpleFutureTask<Boolean> checkAppIsCanUseSnsLogin(final String str, final String str2, final String str3, final CheckAppIsCanUseSnsLoginCallback checkAppIsCanUseSnsLoginCallback) {
        SimpleFutureTask<Boolean> simpleFutureTask = new SimpleFutureTask<>(new Callable<Boolean>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.20
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Boolean call() throws Exception {
                return Boolean.valueOf(XMPassport.isSNSLoginAvailable(str, str2, str3));
            }
        }, checkAppIsCanUseSnsLoginCallback == null ? null : new SimpleFutureTask.Callback<Boolean>() { // from class: com.xiaomi.passport.uicontroller.PhoneLoginController.19
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<Boolean> simpleFutureTask2) {
                try {
                    checkAppIsCanUseSnsLoginCallback.onSuccess(simpleFutureTask2.get().booleanValue());
                } catch (InterruptedException e) {
                    AccountLogger.log(PhoneLoginController.TAG, "snsLogin", e);
                    checkAppIsCanUseSnsLoginCallback.onServerError(ErrorCode.ERROR_UNKNOWN, null);
                } catch (ExecutionException e2) {
                    AccountLogger.log(PhoneLoginController.TAG, "snsLogin", e2);
                    Throwable cause = e2.getCause();
                    checkAppIsCanUseSnsLoginCallback.onServerError(PhoneLoginController.convertErrorCode(cause), cause instanceof InvalidResponseException ? ((InvalidResponseException) cause).getServerError() : null);
                }
            }
        });
        mExecutorService.submit(simpleFutureTask);
        return simpleFutureTask;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkIsStsUrlRequestError(Throwable th) {
        if (th instanceof AccountException) {
            return ((AccountException) th).isStsUrlRequestError;
        }
        if (th instanceof HttpException) {
            return ((HttpException) th).isStsUrlRequestError;
        }
        if (th instanceof PassportIOException) {
            return ((PassportIOException) th).isStsUrlRequestError;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ErrorCode convertErrorCode(Throwable th) {
        if (th instanceof InvalidResponseException) {
            return ErrorCode.ERROR_SERVER;
        }
        if (th instanceof IOException) {
            return ErrorCode.ERROR_NETWORK;
        }
        if (th instanceof AuthenticationFailureException) {
            return ErrorCode.ERROR_AUTH_FAIL;
        }
        if (th instanceof AccessDeniedException) {
            return ErrorCode.ERROR_ACCESS_DENIED;
        }
        if (th instanceof InvalidParameterException) {
            return ErrorCode.ERROR_INVALID_PARAM;
        }
        if (th instanceof InvalidUserNameException) {
            return ErrorCode.ERROR_NON_EXIST_USER;
        }
        if (th instanceof InvalidCredentialException) {
            return ErrorCode.ERROR_PASSWORD;
        }
        return ErrorCode.ERROR_UNKNOWN;
    }

    public static class PhoneUserInfoHelper {
        public RegisterUserInfo query(QueryPhoneInfoParams queryPhoneInfoParams) throws Exception {
            return XMPassport.queryPhoneUserInfo(queryPhoneInfoParams);
        }

        public RegisterUserInfo checkRegisterPhone(CheckRegPhoneParams checkRegPhoneParams) throws AccessDeniedException, InvalidPhoneOrTicketException, IOException, InvalidResponseException, UserRestrictedException, AuthenticationFailureException {
            return XMPassport.checkRegisterPhone(checkRegPhoneParams);
        }
    }
}
