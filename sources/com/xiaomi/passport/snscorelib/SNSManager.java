package com.xiaomi.passport.snscorelib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.passport.snscorelib.internal.entity.SNSBindParameter;
import com.xiaomi.passport.snscorelib.internal.entity.SNSLoginParameter;
import com.xiaomi.passport.snscorelib.internal.exception.SNSLoginException;
import com.xiaomi.passport.snscorelib.internal.request.SNSRequest;
import com.xiaomi.passport.snscorelib.internal.utils.SNSCookieManager;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
public class SNSManager {
    public static final int REQUEST_SNS_LOGIN = 1;
    public static final String SNS_BIND_PARAMETER = "sns_bind_parameter";
    public static final String SNS_WEB_LOGIN_URL = "sns_web_login_url";
    public static final String TAG = "SNSManager";
    private static Activity mActivity;
    private static SNSBindCallback sSNSBindCallback;
    private static WebView sWebView;
    private SimpleFutureTask<SNSBindParameter> mSNSBindParameterTask;
    private SimpleFutureTask<AccountInfo> mSnsLoginByAccessTokenTask;
    private static final ExecutorService mExecutorService = Executors.newCachedThreadPool();
    private static final Integer INT_0 = 0;
    public static final String URL_SNS_BIND_CANCLED = URLs.URL_ACCOUNT_BASE + "/sns/bind/cancel";
    public static final String URL_SNS_BIND_FINISH = URLs.URL_ACCOUNT_BASE + "/sns/bind/finish";
    static WebViewClient mWebViewClient = new WebViewClient() { // from class: com.xiaomi.passport.snscorelib.SNSManager.7
        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (SNSManager.sSNSBindCallback != null) {
                SNSManager.sSNSBindCallback.onPageFinished();
            }
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            String path = Uri.parse(SNSManager.URL_SNS_BIND_CANCLED).getPath();
            String path2 = Uri.parse(SNSManager.URL_SNS_BIND_FINISH).getPath();
            String path3 = Uri.parse(str).getPath();
            boolean zEquals = path2.equals(path3);
            boolean zEquals2 = path.equals(path3);
            if (zEquals) {
                SNSManager.sSNSBindCallback.onSNSBindFinished();
                return true;
            }
            if (!zEquals2) {
                return false;
            }
            SNSManager.sSNSBindCallback.onSNSBindCancel();
            return true;
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            SNSManager.showWebView();
        }
    };

    public static abstract class SNSBindByAccountCallback {
        protected abstract void onImplementSNSBind(SNSBindParameter sNSBindParameter);

        protected abstract void onNetWorkErrorException();

        protected abstract void onOtherException(Throwable th);

        protected abstract void onSNSBindFailed(PassThroughErrorInfo passThroughErrorInfo);
    }

    public static abstract class SNSBindCallback {
        protected abstract void onNetWorkErrorException();

        protected abstract void onPageFinished();

        protected abstract void onSNSBindCancel();

        protected abstract void onSNSBindFailed(int i, String str);

        protected abstract void onSNSBindFinished();
    }

    public interface SNSLoginCallback {
        void onBindLimit();

        void onNeedLoginForBind(SNSBindParameter sNSBindParameter);

        void onNeedNotificationException(String str, String str2);

        void onNetWorkErrorException();

        void onRedirectToWebLogin(SNSBindParameter sNSBindParameter);

        void onSnsLoginFailed(int i, String str);

        void onSnsLoginSucess(AccountInfo accountInfo);
    }

    public interface UnBindSNSCallback {
        void refreshAuthToken();
    }

    public SNSManager(Activity activity) {
        mActivity = activity;
    }

    public SNSManager() {
    }

    public void snsLogin(SNSLoginParameter sNSLoginParameter, SNSLoginCallback sNSLoginCallback) {
        String str = sNSLoginParameter.enToken;
        String str2 = sNSLoginParameter.token;
        String str3 = sNSLoginParameter.code;
        if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
            snsLoginByAccessToken(sNSLoginParameter, sNSLoginCallback);
        } else {
            if (!TextUtils.isEmpty(str3)) {
                snsLoginByCode(sNSLoginParameter, sNSLoginCallback);
                return;
            }
            throw new IllegalArgumentException("code and enToken parameters is null");
        }
    }

    private SimpleFutureTask<AccountInfo> snsLoginByCode(final SNSLoginParameter sNSLoginParameter, final SNSLoginCallback sNSLoginCallback) {
        SimpleFutureTask<AccountInfo> simpleFutureTask = new SimpleFutureTask<>(new Callable<AccountInfo>() { // from class: com.xiaomi.passport.snscorelib.SNSManager.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public AccountInfo call() throws Exception {
                return SNSRequest.snsLoginByCode(sNSLoginParameter);
            }
        }, new SimpleFutureTask.Callback<AccountInfo>() { // from class: com.xiaomi.passport.snscorelib.SNSManager.1
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<AccountInfo> simpleFutureTask2) {
                try {
                    sNSLoginCallback.onSnsLoginSucess(simpleFutureTask2.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException("getSNSAccessTokenByCode:interrupted");
                } catch (ExecutionException e2) {
                    SNSManager.this.handleExecutionException(e2, sNSLoginCallback);
                }
            }
        });
        this.mSnsLoginByAccessTokenTask = simpleFutureTask;
        mExecutorService.submit(simpleFutureTask);
        return this.mSnsLoginByAccessTokenTask;
    }

    private SimpleFutureTask<AccountInfo> snsLoginByAccessToken(final SNSLoginParameter sNSLoginParameter, final SNSLoginCallback sNSLoginCallback) {
        SimpleFutureTask<AccountInfo> simpleFutureTask = new SimpleFutureTask<>(new Callable<AccountInfo>() { // from class: com.xiaomi.passport.snscorelib.SNSManager.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public AccountInfo call() throws Exception {
                return SNSRequest.snsLoginByAccessToken(sNSLoginParameter);
            }
        }, new SimpleFutureTask.Callback<AccountInfo>() { // from class: com.xiaomi.passport.snscorelib.SNSManager.3
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<AccountInfo> simpleFutureTask2) {
                try {
                    sNSLoginCallback.onSnsLoginSucess(simpleFutureTask2.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException("getAccountInfo:interrupted");
                } catch (ExecutionException e2) {
                    SNSManager.this.handleExecutionException(e2, sNSLoginCallback);
                }
            }
        });
        this.mSnsLoginByAccessTokenTask = simpleFutureTask;
        mExecutorService.submit(simpleFutureTask);
        return this.mSnsLoginByAccessTokenTask;
    }

    public boolean unBindSNS(String str, PassportInfo passportInfo, UnBindSNSCallback unBindSNSCallback) throws IOException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("snsType is null");
        }
        if (unBindSNSCallback == null) {
            throw new IllegalArgumentException("unBindSNSCallback is null");
        }
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        try {
            return unBindSNS(passportInfo, str);
        } catch (AccessDeniedException e) {
            AccountLogger.log(TAG, "InvalidAccessTokenRunnable error", e);
            return false;
        } catch (AuthenticationFailureException e2) {
            AccountLogger.log(TAG, "InvalidAccessTokenRunnable error", e2);
            unBindSNSCallback.refreshAuthToken();
            return false;
        } catch (CipherException e3) {
            throw new RuntimeException(e3);
        } catch (InvalidResponseException e4) {
            AccountLogger.log(TAG, "InvalidAccessTokenRunnable error", e4);
            return false;
        }
    }

    private boolean unBindSNS(PassportInfo passportInfo, String str) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return requestUnBindSNS(passportInfo, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleExecutionException(ExecutionException executionException, SNSLoginCallback sNSLoginCallback) {
        Throwable cause = executionException.getCause();
        if (cause instanceof SNSLoginException) {
            sNSLoginCallback.onSnsLoginFailed(((SNSLoginException) cause).getCode(), cause.getMessage());
            return;
        }
        if (cause instanceof IOException) {
            sNSLoginCallback.onNetWorkErrorException();
            return;
        }
        if (cause instanceof SNSRequest.NeedLoginForBindException) {
            sNSLoginCallback.onNeedLoginForBind(((SNSRequest.NeedLoginForBindException) cause).getSNSBindParameter());
            return;
        }
        if (cause instanceof NeedNotificationException) {
            NeedNotificationException needNotificationException = (NeedNotificationException) cause;
            sNSLoginCallback.onNeedNotificationException(needNotificationException.getUserId(), needNotificationException.getNotificationUrl());
        } else if (cause instanceof SNSRequest.BindLimitException) {
            sNSLoginCallback.onBindLimit();
        } else {
            if (cause instanceof SNSRequest.RedirectToWebLoginException) {
                sNSLoginCallback.onRedirectToWebLogin(((SNSRequest.RedirectToWebLoginException) cause).getSNSBindParameter());
                return;
            }
            throw new RuntimeException(cause);
        }
    }

    private static boolean requestUnBindSNS(PassportInfo passportInfo, String str) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null || !TextUtils.isDigitsOnly(passportInfo.getUserId())) {
            throw new IllegalArgumentException("illegal param");
        }
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(SNSRequest.URL_UNBIND_SNS, new EasyMap().easyPut("snsType", str).easyPut("userId", passportInfo.getUserId()), new EasyMap().easyPut("cUserId", passportInfo.getEncryptedUserId()).easyPut("serviceToken", passportInfo.getServiceToken()), true, passportInfo.getSecurity());
        if (mapContentPostAsMap != null) {
            return INT_0.equals(mapContentPostAsMap.getFromBody("code"));
        }
        throw new IOException("failed to get response to delete sns accesstoken");
    }

    public static void snsBind(SNSBindParameter sNSBindParameter, AccountInfo accountInfo, WebView webView, SNSBindCallback sNSBindCallback) {
        sWebView = webView;
        sSNSBindCallback = sNSBindCallback;
        String localeString = getLocaleString(Locale.getDefault());
        WebSettings settings = webView.getSettings();
        webView.getSettings().setUserAgentString(getUserAgent(mActivity) + " AndroidSnsSDK/5.3.0.release.94");
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(mWebViewClient);
        HashMap map = new HashMap();
        map.put("userId", accountInfo.userId);
        map.put("cUserId", accountInfo.encryptedUserId);
        map.put("passToken", accountInfo.passToken);
        map.put("sns_token_ph", sNSBindParameter.sns_token_ph);
        map.put("sns_weixin_openId", sNSBindParameter.sns_weixin_openId);
        SNSCookieManager.setupCookiesForAccountWeb(webView, map);
        webView.loadUrl(sNSBindParameter.snsBindUrl + "&_locale=" + localeString);
    }

    public static String getUserAgent(Context context) {
        return WebSettings.getDefaultUserAgent(context);
    }

    public static String getLocaleString(Locale locale) {
        if (locale == null) {
            return null;
        }
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return TextUtils.isEmpty(country) ? language : String.format("%s_%s", language, country);
    }

    public void snsBindByAccountInfo(final SNSLoginParameter sNSLoginParameter, final AccountInfo accountInfo, final SNSBindByAccountCallback sNSBindByAccountCallback) {
        final String str = sNSLoginParameter.enToken;
        final String str2 = sNSLoginParameter.token;
        final String str3 = sNSLoginParameter.code;
        if (sNSBindByAccountCallback == null) {
            throw new IllegalArgumentException("snsBindByAccountCallback is null");
        }
        SimpleFutureTask<SNSBindParameter> simpleFutureTask = new SimpleFutureTask<>(new Callable<SNSBindParameter>() { // from class: com.xiaomi.passport.snscorelib.SNSManager.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public SNSBindParameter call() throws Exception {
                if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
                    return SNSRequest.getSNSBindParameterByToken(sNSLoginParameter, accountInfo);
                }
                if (!TextUtils.isEmpty(str3)) {
                    return SNSRequest.getSNSBindParameterByCode(sNSLoginParameter, accountInfo);
                }
                throw new IllegalArgumentException("code and enToken parameters is null");
            }
        }, new SimpleFutureTask.Callback<SNSBindParameter>() { // from class: com.xiaomi.passport.snscorelib.SNSManager.5
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<SNSBindParameter> simpleFutureTask2) {
                try {
                    SNSBindParameter sNSBindParameter = simpleFutureTask2.get();
                    SNSBindByAccountCallback sNSBindByAccountCallback2 = sNSBindByAccountCallback;
                    if (sNSBindByAccountCallback2 != null) {
                        sNSBindByAccountCallback2.onImplementSNSBind(sNSBindParameter);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException("snsBindByAccountInfo:interrupted");
                } catch (ExecutionException e2) {
                    Throwable cause = e2.getCause();
                    if (cause instanceof SNSLoginException) {
                        PassThroughErrorInfo serverError = ((SNSLoginException) cause).getServerError();
                        if (serverError != null) {
                            sNSBindByAccountCallback.onOtherException(cause);
                            return;
                        } else {
                            sNSBindByAccountCallback.onSNSBindFailed(serverError);
                            return;
                        }
                    }
                    if (cause instanceof IOException) {
                        sNSBindByAccountCallback.onNetWorkErrorException();
                    } else {
                        sNSBindByAccountCallback.onOtherException(cause);
                    }
                }
            }
        });
        this.mSNSBindParameterTask = simpleFutureTask;
        mExecutorService.submit(simpleFutureTask);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showWebView() {
        if (sWebView.getVisibility() != 0) {
            sWebView.setVisibility(0);
        }
    }
}
