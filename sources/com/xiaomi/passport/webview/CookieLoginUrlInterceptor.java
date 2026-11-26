package com.xiaomi.passport.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.CookieManager;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.NotificationAuthResult;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.snscorelib.SNSManager;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.HttpCookies;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class CookieLoginUrlInterceptor implements UrlInterceptor {
    private static final String AUTH_END = "auth-end";
    public static final Parcelable.Creator<CookieLoginUrlInterceptor> CREATOR = new Parcelable.Creator<CookieLoginUrlInterceptor>() { // from class: com.xiaomi.passport.webview.CookieLoginUrlInterceptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CookieLoginUrlInterceptor createFromParcel(Parcel parcel) {
            return new CookieLoginUrlInterceptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CookieLoginUrlInterceptor[] newArray(int i) {
            return new CookieLoginUrlInterceptor[i];
        }
    };
    private static final String LOGIN_END = "login-end";
    private static final String PASS_INFO = "passInfo";
    private BgTask mAuthEndTask;
    private final boolean mCloseAfterLogin;
    private final Activity mHostActivity;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CookieLoginUrlInterceptor(Activity activity, boolean z) {
        this.mHostActivity = activity;
        this.mCloseAfterLogin = z;
    }

    @Override // com.xiaomi.passport.webview.UrlInterceptor
    public boolean shouldIntercept(Context context, String str) {
        String cookie;
        CookieManager cookieManagerOrNull = HttpCookies.getCookieManagerOrNull();
        if (cookieManagerOrNull == null) {
            cookie = "";
        } else {
            cookie = cookieManagerOrNull.getCookie(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN);
        }
        String str2 = HttpCookies.parse(cookie).get(PASS_INFO);
        HttpCookies.remove(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, PASS_INFO);
        if (LOGIN_END.equals(str2)) {
            AccountInfo accountInfoBuild = new AccountInfo.Builder().userId(XMPassportUtil.extractUserIdFromNotificationLoginEndCookie(cookie)).passToken(XMPassportUtil.extractPasstokenFromNotificationLoginEndCookie(cookie)).build();
            if (XiaomiAccountManager.get(this.mHostActivity).getXiaomiAccount() == null) {
                XiaomiAccountManager.get(this.mHostActivity).addAccountOrUpdatePassToken(accountInfoBuild);
            }
            Bundle authenticatorResponseBundle = AccountHelper.getAuthenticatorResponseBundle(accountInfoBuild, this.mHostActivity.getIntent().getBooleanExtra("need_retry_on_authenticator_response_result", false));
            handleResponse(authenticatorResponseBundle);
            if (this.mCloseAfterLogin) {
                Intent intent = new Intent();
                intent.putExtras(authenticatorResponseBundle);
                this.mHostActivity.setResult(-1, intent);
                this.mHostActivity.finish();
            }
            return false;
        }
        if (!AUTH_END.equals(str2) || !this.mCloseAfterLogin) {
            return false;
        }
        BgTask bgTask = new BgTask(new BgRunnableAuthEnd(str), this.mCloseAfterLogin ? new PostRunnableAuthEnd(this.mHostActivity) : null, null);
        this.mAuthEndTask = bgTask;
        bgTask.execute();
        return true;
    }

    @Override // com.xiaomi.passport.webview.UrlInterceptor
    public void release() {
        BgTask bgTask = this.mAuthEndTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
            this.mAuthEndTask = null;
        }
    }

    private void handleResponse(Bundle bundle) {
        Intent intent = this.mHostActivity.getIntent();
        if (intent != null) {
            XiaomiAccountManager.get(this.mHostActivity).handleAccountAuthenticatorResponse(intent.getParcelableExtra("accountAuthenticatorResponse"), bundle);
        }
    }

    private static class BgRunnableAuthEnd implements BgTask.BgTaskRunnable<NotificationAuthResult> {
        public final String stsUrl;

        public BgRunnableAuthEnd(String str) {
            this.stsUrl = str;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
        public NotificationAuthResult run() {
            Map<String, String> headers;
            try {
                SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(this.stsUrl, null, null, false);
                if (asString != null && (headers = asString.getHeaders()) != null) {
                    return new NotificationAuthResult.Builder().setUserId(headers.get("userId")).setServiceToken(headers.get("serviceToken")).setPSecurity_ph(headers.get("passportsecurity_ph")).setPSecurity_slh(headers.get("passportsecurity_slh")).build();
                }
            } catch (AccessDeniedException e) {
                AccountLogger.log(SNSManager.TAG, "access denied", e);
            } catch (AuthenticationFailureException e2) {
                AccountLogger.log(SNSManager.TAG, "auth error", e2);
            } catch (IOException e3) {
                AccountLogger.log(SNSManager.TAG, "network error", e3);
            } catch (RuntimeException e4) {
                AccountLogger.log(SNSManager.TAG, "runtime exception", e4);
                return null;
            }
            return null;
        }
    }

    private static class PostRunnableAuthEnd implements BgTask.SuccessResultRunnable<NotificationAuthResult> {
        public final Activity activity;

        public PostRunnableAuthEnd(Activity activity) {
            this.activity = activity;
        }

        @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
        public void run(NotificationAuthResult notificationAuthResult) {
            Intent intent = new Intent();
            if (notificationAuthResult != null) {
                intent.putExtra("notification_auth_end", notificationAuthResult);
                this.activity.setResult(-1, intent);
            } else {
                this.activity.setResult(0, intent);
            }
            this.activity.finish();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        throw new IllegalStateException("can not write to parcel");
    }

    protected CookieLoginUrlInterceptor(Parcel parcel) {
        throw new IllegalStateException("can not be created from parcel");
    }
}
