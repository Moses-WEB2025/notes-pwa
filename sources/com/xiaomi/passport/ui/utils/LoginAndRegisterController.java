package com.xiaomi.passport.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTicketLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTokenRegisterParams;
import com.xiaomi.accountsdk.account.data.QueryPhoneInfoParams;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.account.data.SendPhoneTicketParams;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.callback.LoginIdPasswordCallback;
import com.xiaomi.passport.callback.LoginPhoneAccountCallback;
import com.xiaomi.passport.callback.LoginPhoneUserInfoCallback;
import com.xiaomi.passport.callback.QueryPhoneUserInfoCallback;
import com.xiaomi.passport.callback.RegisterPhoneAccountCallback;
import com.xiaomi.passport.callback.RegisterPhoneUserInfoCallback;
import com.xiaomi.passport.callback.RequestPhoneLoginConfigCallback;
import com.xiaomi.passport.callback.RequestPhoneVerifyCodeCallback;
import com.xiaomi.passport.data.LoginPreference;
import com.xiaomi.passport.snscorelib.internal.exception.SNSLoginException;
import com.xiaomi.passport.snscorelib.internal.request.SNSRequest;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.internal.AuthCredential;
import com.xiaomi.passport.ui.internal.AuthProvider;
import com.xiaomi.passport.ui.internal.CaptchaCode;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import com.xiaomi.passport.ui.internal.VerificationCode;
import com.xiaomi.passport.ui.license.AgreementAndPrivacyHelper;
import com.xiaomi.passport.ui.license.LoginAgreementAndPrivacy;
import com.xiaomi.passport.ui.page.DefaultLoginCallback;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.CountryCodePhoneNumber;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.IOException;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class LoginAndRegisterController {
    private static final String TAG = "LoginAndRegController";

    public static void finishWithLoginResult(Activity activity, AccountInfo accountInfo, LoginAgreementAndPrivacy loginAgreementAndPrivacy) {
        int i = accountInfo == null ? 0 : -1;
        Intent intent = activity.getIntent();
        XiaomiAccountManager.get(activity).handleAccountAuthenticatorResponse(intent.getParcelableExtra("accountAuthenticatorResponse"), AccountHelper.getAccountAuthenticatorResponseResult(i, accountInfo, intent.getBooleanExtra("need_retry_on_authenticator_response_result", false)));
        SNSAuthProvider.invalidBindParameter();
        activity.setResult(i);
        if (accountInfo != null) {
            reportPrivacyAgree(activity, accountInfo, loginAgreementAndPrivacy);
        }
        if (activity.isFinishing()) {
            return;
        }
        activity.finish();
    }

    private static void reportPrivacyAgree(Activity activity, AccountInfo accountInfo, LoginAgreementAndPrivacy loginAgreementAndPrivacy) {
        ArrayList<LoginAgreementAndPrivacy.PrivacyReportInfo> arrayList = loginAgreementAndPrivacy.privacyReportInfoList;
        if (loginAgreementAndPrivacy.type != LoginAgreementAndPrivacy.Type.APP_CUSTOM) {
            arrayList.add(new LoginAgreementAndPrivacy.PrivacyReportInfo(AgreementAndPrivacyHelper.PRIVACY_NAME, accountInfo.encryptedUserId, null, null));
        }
        AgreementAndPrivacyHelper.privacyAgree(activity, arrayList);
    }

    public static void storePassToken(Context context, AccountInfo accountInfo) {
        if (TextUtils.isEmpty(accountInfo.passToken)) {
            return;
        }
        XiaomiAccountManager.get(context).addAccountOrUpdatePassToken(accountInfo);
    }

    public static SimpleFutureTask<LoginPreference> requestPhoneLoginConfig(Context context, String str, String str2, String str3, String str4, RequestPhoneLoginConfigCallback requestPhoneLoginConfigCallback) {
        return PhoneLoginController.getPhoneLoginConfigOnLine(CountryCodePhoneNumber.invalidate(str, str2), null, str3, str4, requestPhoneLoginConfigCallback);
    }

    public static SimpleFutureTask<AccountInfo> loginPhoneUserInfo(Context context, String str, String str2, String str3, RegisterUserInfo registerUserInfo, LoginPhoneUserInfoCallback loginPhoneUserInfoCallback) {
        return new PhoneLoginController().ticketLogin(new PhoneTicketLoginParams.Builder().serviceId(str).phoneTicketToken(CountryCodePhoneNumber.invalidate(str2, str3), registerUserInfo.ticketToken).build(), loginPhoneUserInfoCallback);
    }

    public static SimpleFutureTask<AccountInfo> registerPhoneUserInfo(String str, String str2, String str3, RegisterUserInfo registerUserInfo, RegisterPhoneUserInfoCallback registerPhoneUserInfoCallback) {
        String strInvalidate = CountryCodePhoneNumber.invalidate(str2, str3);
        PhoneTokenRegisterParams.Builder builder = new PhoneTokenRegisterParams.Builder();
        builder.serviceId(str);
        builder.phoneTicketToken(strInvalidate, registerUserInfo.ticketToken);
        return new PhoneLoginController().register(builder.build(), registerPhoneUserInfoCallback);
    }

    public static SimpleFutureTask<RegisterUserInfo> requestPhoneUserInfo(Context context, String str, String str2, String str3, String str4, String str5, QueryPhoneUserInfoCallback queryPhoneUserInfoCallback) {
        String strInvalidate = CountryCodePhoneNumber.invalidate(str2, str3);
        QueryPhoneInfoParams.Builder builder = new QueryPhoneInfoParams.Builder();
        builder.phoneTicket(strInvalidate, str5);
        builder.ticketType(str4);
        builder.serviceId(str);
        return new PhoneLoginController().queryPhoneUserInfo(builder.build(), queryPhoneUserInfoCallback);
    }

    public static SimpleFutureTask<Integer> requestPhoneNumberVerifyCode(Context context, String str, String str2, String str3, String str4, CaptchaCode captchaCode, VerificationCode verificationCode, RequestPhoneVerifyCodeCallback requestPhoneVerifyCodeCallback) {
        Intent intent = new Intent("com.android.app.action.GET_VERIFICATION_CODE_EVENT");
        intent.setPackage("com.android.phone");
        intent.putExtra("package_name", context.getPackageName());
        intent.putExtra("phone_number", str2);
        intent.putExtra("sid", str);
        intent.putExtra("country_code", str3);
        intent.putExtra("ticket_type", str4);
        intent.putExtra(Constants.APP_NAME, context.getPackageManager().getApplicationLabel(context.getApplicationInfo()));
        context.sendBroadcast(intent);
        SendPhoneTicketParams.Builder builderPhone = new SendPhoneTicketParams.Builder().serviceId(str).phone(CountryCodePhoneNumber.invalidate(str2, str3));
        if (captchaCode != null) {
            builderPhone.captchaCode(captchaCode.captchaCode, captchaCode.captchaIck);
        }
        if (verificationCode != null) {
            builderPhone.token(verificationCode.token);
            builderPhone.action(verificationCode.action);
        }
        builderPhone.ticketType(str4);
        return new PhoneLoginController().sendPhoneTicket(builderPhone.build(), requestPhoneVerifyCodeCallback);
    }

    public static SimpleFutureTask<AccountInfo> loginIdPassword(Context context, String str, String str2, String str3, String str4, String str5, String str6, LoginIdPasswordCallback loginIdPasswordCallback) {
        PasswordLoginParams.Builder builder = new PasswordLoginParams.Builder();
        builder.setUserId(str);
        builder.setPassword(str2);
        builder.setCountryCode(str3);
        builder.setServiceId(str4);
        builder.setCaptCode(str5);
        builder.setCaptIck(str6);
        return new PhoneLoginController().passwordLogin(builder.build(), loginIdPasswordCallback);
    }

    public static SimpleFutureTask<AccountInfo> loginPhoneAccount(Context context, String str, PhoneAccount phoneAccount, LoginPhoneAccountCallback loginPhoneAccountCallback) {
        ActivatorPhoneInfo.Builder builder = new ActivatorPhoneInfo.Builder();
        builder.phoneHash(phoneAccount.accountCertification.hashedPhoneNumber);
        builder.activatorToken(phoneAccount.accountCertification.activatorToken);
        ActivatorPhoneInfo activatorPhoneInfoBuild = builder.build();
        PhoneTicketLoginParams.Builder builder2 = new PhoneTicketLoginParams.Builder();
        builder2.serviceId(str);
        builder2.verifiedActivatorPhone(activatorPhoneInfoBuild);
        return new PhoneLoginController().ticketLogin(builder2.build(), loginPhoneAccountCallback);
    }

    public static void loginSNS(final Context context, AuthProvider authProvider, AuthCredential authCredential, final DefaultLoginCallback defaultLoginCallback) {
        authProvider.signInAndStoreAccount(context, authCredential, new BgTask.SuccessResultRunnable<AccountInfo>() { // from class: com.xiaomi.passport.ui.utils.LoginAndRegisterController.1
            @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
            public void run(AccountInfo accountInfo) {
                defaultLoginCallback.onLoginSuccess(accountInfo);
            }
        }, new BgTask.ErrorResultRunnable() { // from class: com.xiaomi.passport.ui.utils.LoginAndRegisterController.2
            @Override // com.xiaomi.passport.task.BgTask.ErrorResultRunnable
            public void run(Throwable th) {
                if (th instanceof NeedNotificationException) {
                    context.startActivity(XiaomiAccountManager.get(context).getAccountNotificationActivityIntent("passportapi", ((NeedNotificationException) th).getNotificationUrl(), null, null));
                    return;
                }
                if (th instanceof SNSRequest.RedirectToWebLoginException) {
                    Context context2 = context;
                    context2.startActivity(PassportPageIntent.getSNSBindPageIntent(context2, ((SNSRequest.RedirectToWebLoginException) th).getSNSBindParameter()));
                    return;
                }
                if (th instanceof SNSRequest.BindLimitException) {
                    AccountToast.showToastMessage(context, R.string.passport_sns_bind_limit);
                    return;
                }
                if (th instanceof IOException) {
                    AccountToast.showToastMessage(context, R.string.passport_request_error_network);
                    return;
                }
                if (th instanceof SNSLoginException) {
                    SNSLoginException sNSLoginException = (SNSLoginException) th;
                    Log.e(LoginAndRegisterController.TAG, "errorcode=" + sNSLoginException.getCode() + "errormessage = " + th.getMessage());
                    PassThroughErrorInfo serverError = sNSLoginException.getServerError();
                    if (serverError != null) {
                        Context context3 = context;
                        if (context3 instanceof Activity) {
                            ServerPassThroughErrorHandler.handleServerPassThroughError((Activity) context3, serverError, new PassThroughErrorInfo.Builder().setTitle(context.getString(R.string.passport_error_server)).setMsgContent(context.getString(R.string.passport_relogin_notice)).build());
                            return;
                        }
                    }
                    AccountToast.showToastMessage(context, th.getMessage());
                    return;
                }
                AccountToast.showToastMessage(context, R.string.passport_request_error_unknown);
            }
        });
    }

    public static SimpleFutureTask<AccountInfo> registerPhoneAccount(Context context, String str, PhoneAccount phoneAccount, RegisterPhoneAccountCallback registerPhoneAccountCallback) {
        ActivatorPhoneInfo.Builder builder = new ActivatorPhoneInfo.Builder();
        builder.phoneHash(phoneAccount.accountCertification.hashedPhoneNumber);
        builder.activatorToken(phoneAccount.accountCertification.activatorToken);
        ActivatorPhoneInfo activatorPhoneInfoBuild = builder.build();
        PhoneTokenRegisterParams.Builder builder2 = new PhoneTokenRegisterParams.Builder();
        builder2.serviceId(str);
        builder2.phoneHashActivatorToken(activatorPhoneInfoBuild);
        return new PhoneLoginController().register(builder2.build(), registerPhoneAccountCallback);
    }

    /* JADX INFO: renamed from: com.xiaomi.passport.ui.utils.LoginAndRegisterController$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$passport$uicontroller$PhoneLoginController$ErrorCode;

        static {
            int[] iArr = new int[PhoneLoginController.ErrorCode.values().length];
            $SwitchMap$com$xiaomi$passport$uicontroller$PhoneLoginController$ErrorCode = iArr;
            try {
                iArr[PhoneLoginController.ErrorCode.ERROR_UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$uicontroller$PhoneLoginController$ErrorCode[PhoneLoginController.ErrorCode.ERROR_SERVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$uicontroller$PhoneLoginController$ErrorCode[PhoneLoginController.ErrorCode.ERROR_NETWORK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$uicontroller$PhoneLoginController$ErrorCode[PhoneLoginController.ErrorCode.ERROR_PASSWORD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static String convertErrorCodeToMessage(Context context, PhoneLoginController.ErrorCode errorCode) {
        String string = context.getString(R.string.passport_unknown_error);
        int i = AnonymousClass3.$SwitchMap$com$xiaomi$passport$uicontroller$PhoneLoginController$ErrorCode[errorCode.ordinal()];
        if (i == 2) {
            return context.getString(R.string.passport_error_server);
        }
        if (i != 3) {
            return i != 4 ? string : context.getString(R.string.passport_error_user_password);
        }
        return context.getString(R.string.passport_error_unknow_host_network_error);
    }
}
