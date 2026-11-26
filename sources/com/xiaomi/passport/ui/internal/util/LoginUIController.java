package com.xiaomi.passport.ui.internal.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTicketLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTokenRegisterParams;
import com.xiaomi.accountsdk.account.data.QueryPhoneInfoParams;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.account.data.SendPhoneTicketParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidStep2codeException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.ui.view.PassportDialog;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.AsyncTestMarker;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes4.dex */
public class LoginUIController {
    private static final String TAG = "LoginUIController";
    private final Activity mActivity;
    private PhoneLoginController.PhoneUserInfoHelper mPhoneUserInfoHelper;
    private PassportDialog mProgressDialog;
    private Map<UIControllerType, FutureTask> mUIControllerFutures = new HashMap();
    private PhoneLoginController mPhoneLoginController = new PhoneLoginController();

    public interface OnLoginSuccessRunnable {
        void run(AccountInfo accountInfo);
    }

    public interface PasswordLoginCallback {
        void onLoginByStep2(Step2LoginParams step2LoginParams);

        void onLoginFailed(int i);

        void onLoginSuccess(AccountInfo accountInfo);

        void onNeedCaptchaCode(boolean z, String str);

        void onNeedNotification(String str, String str2);
    }

    public interface QueryDualPhoneUserInfoCallback {
        void onFailed(int i);

        void onSuccess(List<RegisterUserInfo> list);

        void onTokenExpired();
    }

    public interface Step2LoginCallback {
        void onInvalidStep2Code(int i);

        void onLoginFailed(int i);

        void onLoginSuccess(AccountInfo accountInfo);

        void onPwdError();
    }

    private enum UIControllerType {
        PASSWORD_LOGIN,
        PHONE_LOGIN,
        PHONE_REGISTER,
        SEND_PHONE_TICKET,
        ADD_OR_UPDATE_ACCOUNT_MANAGER,
        QUERY_PHONE_USER_INFO
    }

    public LoginUIController(Activity activity) {
        this.mActivity = activity;
        PhoneLoginController.PhoneUserInfoHelper phoneUserInfoHelper = new PhoneLoginController.PhoneUserInfoHelper() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.1
            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoHelper
            public RegisterUserInfo query(QueryPhoneInfoParams queryPhoneInfoParams) throws Exception {
                return InNetDateHelper.updateRegisterStatusIfNeed(LoginUIController.this.mActivity.getApplicationContext(), LoginUIController.this.mActivity.getFragmentManager(), super.query(queryPhoneInfoParams), queryPhoneInfoParams);
            }
        };
        this.mPhoneUserInfoHelper = phoneUserInfoHelper;
        this.mPhoneLoginController.setPhoneUserInfoHelper(phoneUserInfoHelper);
    }

    public void cancel() {
        clearLoginFutureTasks();
    }

    public void loginByPassword(final PasswordLoginParams passwordLoginParams, final PasswordLoginCallback passwordLoginCallback) {
        if (isFutureTaskRunning(UIControllerType.PASSWORD_LOGIN)) {
            AccountLogger.log(TAG, "password login has not finished");
            return;
        }
        if (passwordLoginCallback == null) {
            throw new IllegalArgumentException("should implements login callback");
        }
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            AccountLogger.log(TAG, "activity non exist");
            return;
        }
        Activity activity2 = this.mActivity;
        showLoginLoadingDialog(activity2, activity2.getString(R.string.passport_checking_account));
        AsyncTestMarker.increment();
        SimpleFutureTask simpleFutureTask = new SimpleFutureTask(new Callable<AccountInfo>() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public AccountInfo call() throws Exception {
                return AccountHelper.loginByPassword(passwordLoginParams);
            }
        }, new SimpleFutureTask.Callback<AccountInfo>() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.2
            /* JADX WARN: Code duplicated, block: B:61:? A[RETURN, SYNTHETIC] */
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<AccountInfo> simpleFutureTask2) {
                int i;
                if (LoginUIController.this.mActivity == null || LoginUIController.this.mActivity.isFinishing()) {
                    AccountLogger.log(LoginUIController.TAG, "activity not alive");
                    return;
                }
                try {
                    try {
                        passwordLoginCallback.onLoginSuccess(simpleFutureTask2.get());
                        LoginUIController.this.dismissLoginLoadingDialog();
                    } catch (InterruptedException e) {
                        AccountLogger.log(LoginUIController.TAG, "interrupted", e);
                        i = R.string.passport_error_unknown;
                        LoginUIController.this.dismissLoginLoadingDialog();
                        if (i == -1) {
                            return;
                        }
                        passwordLoginCallback.onLoginFailed(i);
                    } catch (ExecutionException e2) {
                        AccountLogger.log(LoginUIController.TAG, "execution error", e2);
                        Throwable cause = e2.getCause();
                        if (cause instanceof NeedNotificationException) {
                            AccountLogger.log(LoginUIController.TAG, "need notification", e2);
                            passwordLoginCallback.onNeedNotification(passwordLoginParams.serviceId, ((NeedNotificationException) cause).getNotificationUrl());
                        } else if (cause instanceof NeedVerificationException) {
                            AccountLogger.log(LoginUIController.TAG, "need step2 login", e2);
                            NeedVerificationException needVerificationException = (NeedVerificationException) cause;
                            passwordLoginCallback.onLoginByStep2(new Step2LoginParams.Builder().setUserId(needVerificationException.getUserId()).setMetaLoginData(needVerificationException.getMetaLoginData()).setServiceId(passwordLoginParams.serviceId).setStep1Token(needVerificationException.getStep1Token()).build());
                        } else {
                            if (!(cause instanceof NeedCaptchaException)) {
                                if (cause instanceof InvalidCredentialException) {
                                    AccountLogger.log(LoginUIController.TAG, "wrong password", e2);
                                    InvalidCredentialException invalidCredentialException = (InvalidCredentialException) cause;
                                    if (TextUtils.isEmpty(invalidCredentialException.getCaptchaUrl())) {
                                        i = ((InvalidCredentialException) cause).getHasPwd() ? R.string.passport_bad_authentication : R.string.passport_error_no_password_user;
                                    } else {
                                        passwordLoginCallback.onNeedCaptchaCode(true, invalidCredentialException.getCaptchaUrl());
                                    }
                                } else if (cause instanceof IOException) {
                                    AccountLogger.log(LoginUIController.TAG, "network error", e2);
                                    i = R.string.passport_error_network;
                                } else if (cause instanceof InvalidUserNameException) {
                                    AccountLogger.log(LoginUIController.TAG, "nonExist user name", e2);
                                    i = R.string.passport_error_user_name;
                                } else if (cause instanceof AccessDeniedException) {
                                    AccountLogger.log(LoginUIController.TAG, "access denied", e2);
                                    i = R.string.passport_access_denied;
                                } else if (cause instanceof InvalidResponseException) {
                                    AccountLogger.log(LoginUIController.TAG, "invalid response", e2);
                                    i = R.string.passport_error_server;
                                } else {
                                    i = R.string.passport_error_unknown;
                                }
                                LoginUIController.this.dismissLoginLoadingDialog();
                                if (i == -1) {
                                    return;
                                }
                                passwordLoginCallback.onLoginFailed(i);
                            }
                            AccountLogger.log(LoginUIController.TAG, "need captcha");
                            passwordLoginCallback.onNeedCaptchaCode(false, ((NeedCaptchaException) cause).getCaptchaUrl());
                        }
                        i = -1;
                        LoginUIController.this.dismissLoginLoadingDialog();
                        if (i == -1) {
                            return;
                        }
                        passwordLoginCallback.onLoginFailed(i);
                    }
                } catch (Throwable th) {
                    LoginUIController.this.dismissLoginLoadingDialog();
                    throw th;
                }
            }
        });
        XiaomiPassportExecutor.getSingleton().submit(simpleFutureTask);
        this.mUIControllerFutures.put(UIControllerType.PASSWORD_LOGIN, simpleFutureTask);
    }

    public void loginByStep2(final Step2LoginParams step2LoginParams, final Step2LoginCallback step2LoginCallback) {
        if (isFutureTaskRunning(UIControllerType.PASSWORD_LOGIN)) {
            AccountLogger.log(TAG, "password login has not finished");
            return;
        }
        if (step2LoginCallback == null) {
            throw new IllegalArgumentException("should implements login callback");
        }
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            AccountLogger.log(TAG, "activity non exist");
            return;
        }
        Activity activity2 = this.mActivity;
        showLoginLoadingDialog(activity2, activity2.getString(R.string.passport_checking_account));
        AsyncTestMarker.increment();
        SimpleFutureTask simpleFutureTask = new SimpleFutureTask(new Callable<AccountInfo>() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public AccountInfo call() throws Exception {
                return AccountHelper.getServiceTokenByStep2(step2LoginParams.userId, step2LoginParams.step2code, step2LoginParams.metaLoginData, step2LoginParams.trust, step2LoginParams.step1Token, step2LoginParams.serviceId);
            }
        }, new SimpleFutureTask.Callback<AccountInfo>() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.4
            /* JADX WARN: Code duplicated, block: B:49:? A[RETURN, SYNTHETIC] */
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<AccountInfo> simpleFutureTask2) {
                int i;
                if (LoginUIController.this.mActivity == null || LoginUIController.this.mActivity.isFinishing()) {
                    AccountLogger.log(LoginUIController.TAG, "activity not alive");
                    return;
                }
                try {
                    try {
                        step2LoginCallback.onLoginSuccess(simpleFutureTask2.get());
                        LoginUIController.this.dismissLoginLoadingDialog();
                    } catch (InterruptedException e) {
                        AccountLogger.log(LoginUIController.TAG, "interrupted", e);
                        i = R.string.passport_error_unknown;
                        LoginUIController.this.dismissLoginLoadingDialog();
                        if (i == -1) {
                            return;
                        }
                        step2LoginCallback.onLoginFailed(i);
                    } catch (ExecutionException e2) {
                        AccountLogger.log(LoginUIController.TAG, "execution error", e2);
                        Throwable cause = e2.getCause();
                        if (!(cause instanceof InvalidStep2codeException)) {
                            if (cause instanceof InvalidCredentialException) {
                                AccountLogger.log(LoginUIController.TAG, "wrong password", e2);
                                step2LoginCallback.onPwdError();
                            } else if (cause instanceof IOException) {
                                AccountLogger.log(LoginUIController.TAG, "network error", e2);
                                i = R.string.passport_error_network;
                            } else if (cause instanceof InvalidUserNameException) {
                                AccountLogger.log(LoginUIController.TAG, "nonExist user name", e2);
                                i = R.string.passport_error_user_name;
                            } else if (cause instanceof AccessDeniedException) {
                                AccountLogger.log(LoginUIController.TAG, "access denied", e2);
                                i = R.string.passport_access_denied;
                            } else if (cause instanceof InvalidResponseException) {
                                AccountLogger.log(LoginUIController.TAG, "invalid response", e2);
                                i = R.string.passport_error_server;
                            } else {
                                i = R.string.passport_error_unknown;
                            }
                            LoginUIController.this.dismissLoginLoadingDialog();
                            if (i == -1) {
                                return;
                            }
                            step2LoginCallback.onLoginFailed(i);
                        }
                        AccountLogger.log(LoginUIController.TAG, "wrong step2 code", e2);
                        step2LoginCallback.onInvalidStep2Code(R.string.passport_wrong_vcode);
                        i = -1;
                        LoginUIController.this.dismissLoginLoadingDialog();
                        if (i == -1) {
                            return;
                        }
                        step2LoginCallback.onLoginFailed(i);
                    }
                } catch (Throwable th) {
                    LoginUIController.this.dismissLoginLoadingDialog();
                    throw th;
                }
            }
        });
        XiaomiPassportExecutor.getSingleton().submit(simpleFutureTask);
        this.mUIControllerFutures.put(UIControllerType.PASSWORD_LOGIN, simpleFutureTask);
    }

    public void sendPhoneLoginTicket(SendPhoneTicketParams sendPhoneTicketParams, final PhoneLoginController.SendPhoneTicketCallback sendPhoneTicketCallback) {
        if (isFutureTaskRunning(UIControllerType.SEND_PHONE_TICKET)) {
            AccountLogger.log(TAG, "send phone ticket task has not finished");
            return;
        }
        if (sendPhoneTicketCallback == null) {
            throw new IllegalArgumentException("should implements login callback");
        }
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            AccountLogger.log(TAG, "activity non exist");
            return;
        }
        Activity activity2 = this.mActivity;
        showLoginLoadingDialog(activity2, activity2.getString(R.string.passport_sending_vcode));
        this.mUIControllerFutures.put(UIControllerType.SEND_PHONE_TICKET, this.mPhoneLoginController.sendPhoneTicket(sendPhoneTicketParams, new PhoneLoginController.SendPhoneTicketExtensionCallback() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.6
            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
            public void onSentSuccess(int i) {
                LoginUIController.this.dismissLoginLoadingDialog();
                sendPhoneTicketCallback.onSentSuccess(i);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
            public void onNeedCaptchaCode(String str, String str2) {
                LoginUIController.this.dismissLoginLoadingDialog();
                sendPhoneTicketCallback.onNeedCaptchaCode(str);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
            public void onActivatorTokenExpired() {
                LoginUIController.this.dismissLoginLoadingDialog();
                sendPhoneTicketCallback.onActivatorTokenExpired();
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
            public void onSMSReachLimit() {
                LoginUIController.this.dismissLoginLoadingDialog();
                sendPhoneTicketCallback.onSMSReachLimit();
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
            public void onPhoneNumInvalid() {
                LoginUIController.this.dismissLoginLoadingDialog();
                sendPhoneTicketCallback.onPhoneNumInvalid();
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
            public void onSentFailed(PhoneLoginController.ErrorCode errorCode, String str) {
                LoginUIController.this.dismissLoginLoadingDialog();
                sendPhoneTicketCallback.onSentFailed(errorCode, str);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
            public void onServerError(PhoneLoginController.ErrorCode errorCode, String str, PassThroughErrorInfo passThroughErrorInfo) {
                LoginUIController.this.dismissLoginLoadingDialog();
                sendPhoneTicketCallback.onSentFailed(errorCode, passThroughErrorInfo.getTips());
            }
        }));
    }

    public void queryPhoneUserInfo(QueryPhoneInfoParams queryPhoneInfoParams, final PhoneLoginController.PhoneUserInfoCallback phoneUserInfoCallback, boolean z) {
        if (isFutureTaskRunning(UIControllerType.QUERY_PHONE_USER_INFO)) {
            AccountLogger.log(TAG, "send phone ticket task has not finished");
            return;
        }
        if (phoneUserInfoCallback == null) {
            throw new IllegalArgumentException("should implements phone user info callback");
        }
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            AccountLogger.log(TAG, "activity non exist");
            return;
        }
        if (z) {
            Activity activity2 = this.mActivity;
            showLoginLoadingDialog(activity2, activity2.getString(R.string.passport_querying_phone_info));
        }
        this.mUIControllerFutures.put(UIControllerType.QUERY_PHONE_USER_INFO, this.mPhoneLoginController.queryPhoneUserInfo(queryPhoneInfoParams, new PhoneLoginController.PhoneUserInfoCallback() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.7
            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
            public void onRecycledOrNotRegisteredPhone(RegisterUserInfo registerUserInfo) {
                LoginUIController.this.dismissLoginLoadingDialog();
                phoneUserInfoCallback.onRecycledOrNotRegisteredPhone(registerUserInfo);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
            public void onNotRecycledRegisteredPhone(RegisterUserInfo registerUserInfo) {
                LoginUIController.this.dismissLoginLoadingDialog();
                phoneUserInfoCallback.onNotRecycledRegisteredPhone(registerUserInfo);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
            public void onProbablyRecycleRegisteredPhone(RegisterUserInfo registerUserInfo) {
                LoginUIController.this.dismissLoginLoadingDialog();
                phoneUserInfoCallback.onProbablyRecycleRegisteredPhone(registerUserInfo);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
            public void onPhoneNumInvalid() {
                LoginUIController.this.dismissLoginLoadingDialog();
                phoneUserInfoCallback.onPhoneNumInvalid();
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
            public void onTicketOrTokenInvalid() {
                LoginUIController.this.dismissLoginLoadingDialog();
                phoneUserInfoCallback.onTicketOrTokenInvalid();
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
            public void onQueryFailed(PhoneLoginController.ErrorCode errorCode, String str) {
                LoginUIController.this.dismissLoginLoadingDialog();
                phoneUserInfoCallback.onQueryFailed(errorCode, str);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
            public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
                LoginUIController.this.dismissLoginLoadingDialog();
                phoneUserInfoCallback.onServerError(errorCode, passThroughErrorInfo);
            }
        }));
    }

    public void queryDualPhoneUserInfo(final QueryPhoneInfoParams queryPhoneInfoParams, final QueryPhoneInfoParams queryPhoneInfoParams2, final QueryDualPhoneUserInfoCallback queryDualPhoneUserInfoCallback, boolean z) {
        if (isFutureTaskRunning(UIControllerType.QUERY_PHONE_USER_INFO)) {
            AccountLogger.log(TAG, "send phone ticket task has not finished");
            return;
        }
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            AccountLogger.log(TAG, "activity non exist");
            return;
        }
        if (z) {
            Activity activity2 = this.mActivity;
            showLoginLoadingDialog(activity2, activity2.getString(R.string.passport_querying_phone_info));
        }
        SimpleFutureTask simpleFutureTask = new SimpleFutureTask(new Callable<List<RegisterUserInfo>>() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.9
            @Override // java.util.concurrent.Callable
            public List<RegisterUserInfo> call() throws Exception {
                boolean z2;
                ArrayList arrayList = new ArrayList();
                try {
                    arrayList.add(LoginUIController.this.mPhoneUserInfoHelper.query(queryPhoneInfoParams));
                    z2 = false;
                } catch (InvalidVerifyCodeException e) {
                    AccountLogger.log(LoginUIController.TAG, "phone1", e);
                    throw e;
                } catch (Exception e2) {
                    AccountLogger.log(LoginUIController.TAG, "phone1", e2);
                    z2 = true;
                }
                try {
                    arrayList.add(LoginUIController.this.mPhoneUserInfoHelper.query(queryPhoneInfoParams2));
                } catch (InvalidVerifyCodeException e3) {
                    AccountLogger.log(LoginUIController.TAG, "phone2", e3);
                    throw e3;
                } catch (Exception e4) {
                    AccountLogger.log(LoginUIController.TAG, "phone2", e4);
                    if (z2) {
                        throw e4;
                    }
                }
                return arrayList;
            }
        }, new SimpleFutureTask.Callback<List<RegisterUserInfo>>() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.8
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<List<RegisterUserInfo>> simpleFutureTask2) {
                try {
                    queryDualPhoneUserInfoCallback.onSuccess(simpleFutureTask2.get());
                } catch (InterruptedException e) {
                    AccountLogger.log(LoginUIController.TAG, "query user phone info", e);
                    queryDualPhoneUserInfoCallback.onFailed(ErrorInfo.ERROR_UNKNOWN.errorMessageId);
                } catch (ExecutionException e2) {
                    AccountLogger.log(LoginUIController.TAG, "query user phone info", e2);
                    Throwable cause = e2.getCause();
                    if (cause instanceof InvalidVerifyCodeException) {
                        queryDualPhoneUserInfoCallback.onTokenExpired();
                    } else {
                        queryDualPhoneUserInfoCallback.onFailed(ErrorInfo.getMsgIdGivenException(cause));
                    }
                } finally {
                    LoginUIController.this.dismissLoginLoadingDialog();
                }
            }
        });
        XiaomiPassportExecutor.getSingleton().submit(simpleFutureTask);
        this.mUIControllerFutures.put(UIControllerType.QUERY_PHONE_USER_INFO, simpleFutureTask);
    }

    public void loginByPhone(PhoneTicketLoginParams phoneTicketLoginParams, final PhoneLoginController.TicketLoginCallback ticketLoginCallback) {
        if (isFutureTaskRunning(UIControllerType.PHONE_LOGIN)) {
            AccountLogger.log(TAG, "phone ticket login task has not finished");
            return;
        }
        if (ticketLoginCallback == null) {
            throw new IllegalArgumentException("should implements login callback");
        }
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            AccountLogger.log(TAG, "activity non exist");
            return;
        }
        Activity activity2 = this.mActivity;
        showLoginLoadingDialog(activity2, activity2.getString(R.string.passport_checking_account));
        this.mUIControllerFutures.put(UIControllerType.PHONE_LOGIN, this.mPhoneLoginController.ticketLogin(phoneTicketLoginParams, new PhoneLoginController.TicketLoginCallback() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.10
            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
            public void onLoginSuccess(AccountInfo accountInfo) {
                LoginUIController.this.dismissLoginLoadingDialog();
                if (XiaomiAccountManager.get(LoginUIController.this.mActivity).addAccountOrUpdatePassToken(accountInfo)) {
                    ticketLoginCallback.onLoginSuccess(accountInfo);
                } else {
                    AccountLogger.log(LoginUIController.TAG, "loginByPhone: fail to add account manager");
                    ticketLoginCallback.onLoginFailed(PhoneLoginController.ErrorCode.ERROR_UNKNOWN, "fail to add account manager", false);
                }
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
            public void onNeedNotification(String str, String str2) {
                LoginUIController.this.dismissLoginLoadingDialog();
                ticketLoginCallback.onNeedNotification(str, str2);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
            public void onPhoneNumInvalid() {
                LoginUIController.this.dismissLoginLoadingDialog();
                AccountLogger.log(LoginUIController.TAG, "loginByPhone:invalid phone num");
                ticketLoginCallback.onPhoneNumInvalid();
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
            public void onTicketOrTokenInvalid() {
                LoginUIController.this.dismissLoginLoadingDialog();
                AccountLogger.log(LoginUIController.TAG, "loginByPhone:token expired");
                ticketLoginCallback.onTicketOrTokenInvalid();
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
            public void onTzSignInvalid() {
                LoginUIController.this.dismissLoginLoadingDialog();
                AccountLogger.log(LoginUIController.TAG, "loginByPhone:tz sign invalid");
                ticketLoginCallback.onTzSignInvalid();
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
            public void onLoginFailed(PhoneLoginController.ErrorCode errorCode, String str, boolean z) {
                LoginUIController.this.dismissLoginLoadingDialog();
                AccountLogger.log(LoginUIController.TAG, "loginByPhone: " + str);
                ticketLoginCallback.onLoginFailed(errorCode, str, z);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
            public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
                ServerPassThroughErrorHandler.handleServerPassThroughError(LoginUIController.this.mActivity, passThroughErrorInfo);
            }
        }));
    }

    public void registerByPhone(PhoneTokenRegisterParams phoneTokenRegisterParams, final PhoneLoginController.PhoneRegisterCallback phoneRegisterCallback) {
        if (isFutureTaskRunning(UIControllerType.PHONE_REGISTER)) {
            AccountLogger.log(TAG, "send phone ticket task has not finished");
            return;
        }
        if (phoneRegisterCallback == null) {
            throw new IllegalArgumentException("should implements login callback");
        }
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            AccountLogger.log(TAG, "activity non exist");
            return;
        }
        Activity activity2 = this.mActivity;
        showLoginLoadingDialog(activity2, activity2.getString(R.string.passport_reging));
        this.mUIControllerFutures.put(UIControllerType.PHONE_REGISTER, this.mPhoneLoginController.register(phoneTokenRegisterParams, new PhoneLoginController.PhoneRegisterCallback() { // from class: com.xiaomi.passport.ui.internal.util.LoginUIController.11
            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
            public void onRegisterSuccess(AccountInfo accountInfo) {
                LoginUIController.this.dismissLoginLoadingDialog();
                phoneRegisterCallback.onRegisterSuccess(accountInfo);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
            public void onRegisterReachLimit() {
                LoginUIController.this.dismissLoginLoadingDialog();
                AccountLogger.log(LoginUIController.TAG, "registerByPhone: reach register limit");
                phoneRegisterCallback.onRegisterReachLimit();
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
            public void onTokenExpired() {
                LoginUIController.this.dismissLoginLoadingDialog();
                AccountLogger.log(LoginUIController.TAG, "registerByPhone: token expired");
                phoneRegisterCallback.onTokenExpired();
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
            public void onRegisterFailed(PhoneLoginController.ErrorCode errorCode, String str) {
                LoginUIController.this.dismissLoginLoadingDialog();
                AccountLogger.log(LoginUIController.TAG, "registerByPhone: " + str);
                phoneRegisterCallback.onRegisterFailed(errorCode, str);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
            public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
                ServerPassThroughErrorHandler.handleServerPassThroughError(LoginUIController.this.mActivity, passThroughErrorInfo);
            }
        }));
    }

    private void showLoginLoadingDialog(Context context, String str) {
        if (this.mProgressDialog != null) {
            dismissLoginLoadingDialog();
        }
        PassportDialog passportDialog = new PassportDialog(context);
        this.mProgressDialog = passportDialog;
        passportDialog.setMessage(str);
        this.mProgressDialog.setLoadingProgressVisible(true);
        this.mProgressDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissLoginLoadingDialog() {
        PassportDialog passportDialog = this.mProgressDialog;
        if (passportDialog != null) {
            passportDialog.dismiss();
            this.mProgressDialog = null;
        }
    }

    private boolean isFutureTaskRunning(UIControllerType uIControllerType) {
        FutureTask futureTask = this.mUIControllerFutures.get(uIControllerType);
        return (futureTask == null || futureTask.isDone()) ? false : true;
    }

    private void clearLoginFutureTasks() {
        Iterator<UIControllerType> it = this.mUIControllerFutures.keySet().iterator();
        while (it.hasNext()) {
            FutureTask futureTask = this.mUIControllerFutures.get(it.next());
            if (futureTask != null && !futureTask.isDone()) {
                futureTask.cancel(true);
            }
        }
        this.mUIControllerFutures.clear();
    }
}
